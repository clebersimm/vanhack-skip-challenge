package com.vanhack.skipthedishes.orderservice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vanhack.skipthedishes.orderservice.entity.Product;
import com.vanhack.skipthedishes.orderservice.repository.ProductRepository;

@Component
public class InitTestDatabase implements InitializingBean {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
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
