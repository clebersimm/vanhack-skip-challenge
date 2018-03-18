package com.vanhack.skipthedishes.orderservice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.skipthedishes.orderservice.entity.Customer;
import com.vanhack.skipthedishes.orderservice.entity.Product;
import com.vanhack.skipthedishes.orderservice.repository.ProductRepository;
import com.vanhack.skipthedishes.orderservice.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductRepository productRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void createCustomer() {
		for (int i = 0; i < 10; i++) {
			Customer customer = new Customer();
			customer.setAddress("test st " + i);
			customer.setName("test name " + i);
			customer.setEmail("email" + i + "@test.com");
			customer.setPassword("123");
			customerService.save(customer);
		}
	}

	@Test
	public void createProduct() {
		String[] foods = { "sushi", "pizza", "pasta" };

		for (int i = 0; i < 10; i++) {
			
			Product product = new Product();
			Random random = new Random();
			String food = foods[random.nextInt(3)];
			product.setDescription("the best " + food);
			product.setName(food);
			product.setPrice(ThreadLocalRandom.current().nextDouble(1.0, 99.0));
			product.setStoreId(Integer.valueOf(random.nextInt(10)).longValue());
			productRepository.save(product);
		}
	}

}
