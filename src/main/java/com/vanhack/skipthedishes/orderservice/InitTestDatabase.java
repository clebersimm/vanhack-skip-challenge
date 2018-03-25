package com.vanhack.skipthedishes.orderservice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vanhack.skipthedishes.orderservice.entity.Customer;
import com.vanhack.skipthedishes.orderservice.entity.Product;
import com.vanhack.skipthedishes.orderservice.repository.ProductRepository;
import com.vanhack.skipthedishes.orderservice.service.CustomerService;

@Component
public class InitTestDatabase implements InitializingBean {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerService customerService;

	@Override
	public void afterPropertiesSet() throws Exception {
		String[] foods = { "sushi", "pizza", "pasta", "beans", "BBQ", "mexican", "hamburger" };

		for (int i = 0; i < 100; i++) {

			Product product = new Product();
			Random random = new Random();
			String food = foods[random.nextInt(foods.length-1)];
			product.setDescription("the best " + food);
			product.setName(food);
			product.setPrice(ThreadLocalRandom.current().nextDouble(1.0, 99.0));
			product.setStoreId(Integer.valueOf(random.nextInt(10)).longValue());
			productRepository.save(product);
		}
		
		for(int i=0;i<10;i++) {
			Customer customer = new Customer();
			customer.setAddress("Address"+i);
			customer.setEmail("test"+i+"@test.com");
			customer.setName("Name"+i);
			customer.setPassword("12345");
			customerService.save(customer);
		}
		
	}
}
