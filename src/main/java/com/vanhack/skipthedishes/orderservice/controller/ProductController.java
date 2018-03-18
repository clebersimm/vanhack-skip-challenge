package com.vanhack.skipthedishes.orderservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.skipthedishes.orderservice.entity.Product;
import com.vanhack.skipthedishes.orderservice.repository.ProductRepository;
import com.vanhack.skipthedishes.orderservice.service.ProductService;

@RestController
@RequestMapping("/Product")
public class ProductController {

	private static final Logger logger = LogManager.getLogger(ProductController.class);

	@Autowired
	private final ProductRepository productRepository;
	@Autowired
	private final ProductService productService;

	public ProductController(ProductRepository productRepository, ProductService productService) {
		this.productRepository = productRepository;
		this.productService = productService;
	}

	@GetMapping
	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	@Cacheable(value = "product-list-search", key = "#searchText")//Not working, more study required
	@GetMapping("/search/{searchText}")
	public List<Product> search(@PathVariable String searchText) {
		try {
			return productService.findProductsByNameOrDescription(searchText);
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e);
			throw new RuntimeException("Oops! Something is wrong! We are not able to get the products. Try latter");
		}
	}

	@Cacheable(value = "product", key = "#productId")//Not working, more study required
	@GetMapping("/{productId}")
	public Optional<Product> findById(@PathVariable Long productId) {
		return productRepository.findById(productId);
	}

}
