package com.vanhack.skipthedishes.orderservice.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vanhack.skipthedishes.orderservice.entity.Customer;
import com.vanhack.skipthedishes.orderservice.exceptions.BadRequestException;
import com.vanhack.skipthedishes.orderservice.repository.CustomerRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;

	public String save(Customer customer) {
		// TODO must check how change db
		Boolean hasKey = stringRedisTemplate.hasKey(customer.getEmail());
		if (hasKey) {
			throw new BadRequestException("There is already an account with this email!");
		}

		// customer.setCreation(new Timestamp(System.currentTimeMillis()));
		//customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		customerRepository.save(customer);
		stringRedisTemplate.opsForSet().add(customer.getEmail(), customer.getName());
		return generateToken(customer.getEmail(), customer.getPassword());
	}

	//TODO must check when the spring security is implemented to remove this code
	private String generateToken(String email, String password) {
		String token = Jwts.builder().setSubject(email).claim("role", "user").setIssuedAt(Calendar.getInstance().getTime())
				.signWith(SignatureAlgorithm.HS256, password).compact();

		return token;
	}
}
