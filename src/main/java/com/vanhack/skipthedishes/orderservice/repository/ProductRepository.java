package com.vanhack.skipthedishes.orderservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vanhack.skipthedishes.orderservice.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findByNameContaining(String name);

	List<Product> findByDescriptionContaining(String description);

}
