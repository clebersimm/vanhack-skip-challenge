package com.vanhack.skipthedishes.orderservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
		List<Product> finalList = new ArrayList<>();
		Callable<List<Product>> findByName = () -> {
			return productRepository.findByNameContaining(query);
		};

		Callable<List<Product>> findByDescription = () -> {
			return productRepository.findByDescriptionContaining(query);
		};

		ExecutorService executorservice = Executors.newFixedThreadPool(2);
		CompletionService executor = new ExecutorCompletionService<>(executorservice);
		
		Future<List<Product>> findByNameFuture = executor.submit(findByName);
		Future<List<Product>> findByDescriptionFuture = executor.submit(findByDescription);
		
		if (findByNameFuture.isDone() && findByDescriptionFuture.isDone()) {
			executor.take().get();
			List<Product> nameFind = findByNameFuture.get();
			List<Product> descriptionFind = findByDescriptionFuture.get();
			finalList = nameFind.stream().filter(not(descriptionFind::contains)).collect(Collectors.toList());
		}

		return finalList;
	}

	public static <T> Predicate<T> not(Predicate<T> t) {
		return t.negate();
	}

}
