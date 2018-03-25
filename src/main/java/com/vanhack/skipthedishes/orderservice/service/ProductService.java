package com.vanhack.skipthedishes.orderservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanhack.skipthedishes.orderservice.entity.Product;
import com.vanhack.skipthedishes.orderservice.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> findProductsByNameOrDescription(String query) throws InterruptedException, ExecutionException {

		Stream<Supplier<List<Product>>> products = Stream.of(() -> productRepository.findByNameContaining(query),
				() -> productRepository.findByDescriptionContaining(query));
		List<Product> finalList = products.map(CompletableFuture::supplyAsync).collect(Collectors.toList())
				.stream().map(CompletableFuture::join).collect(Collectors.toList()).stream().flatMap(List::stream).distinct()
				.collect(Collectors.toList());

		return finalList;
	}

	public static <T> Predicate<T> not(Predicate<T> t) {
		return t.negate();
	}

}
