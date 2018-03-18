package com.vanhack.skipthedishes.orderservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	@RequestMapping(value = "/search/{searchText}", method = RequestMethod.GET)
	public List<Product> search(@PathVariable String searchText) {
		try {
			return productService.findProductsByNameOrDescription(searchText);
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e);
			throw new RuntimeException("Oops! Something is wrong! We are not able to get the products. Try latter");
		}
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public Optional<Product> findById(@PathVariable Long productId) {
		return productRepository.findById(productId);
	}

}
