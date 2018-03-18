package com.vanhack.skipthedishes.orderservice.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.vanhack.skipthedishes.orderservice.entity.Customer;
import com.vanhack.skipthedishes.orderservice.repository.CustomerRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class CustomerService {

	private static final Logger logger = LogManager.getLogger(CustomerService.class);

	@Autowired
	private final CustomerRepository customerRepository;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public String save(Customer customer) {
		// TODO must check how change db
		Boolean hasKey = stringRedisTemplate.hasKey(customer.getEmail());
		if (hasKey) {
			throw new RuntimeException("There is already an account with this email!");
		}

		// customer.setCreation(new Timestamp(System.currentTimeMillis()));
		try {
			customer.setPassword(encryptPassword(customer.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
		customerRepository.save(customer);
		stringRedisTemplate.opsForSet().add(customer.getEmail(), customer.getName());
		return generateToken(customer.getEmail(), customer.getPassword());
	}

	// TODO implement the token generator
	private String generateToken(String email, String password) {
		String token = Jwts.builder().setSubject(email).claim("role", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, password).compact();

		return token;
	}

	private String encryptPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());
		byte byteData[] = md.digest();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				stringBuffer.append('0');
			stringBuffer.append(hex);
		}
		return stringBuffer.toString();
	}

	public String login(Customer customer) throws NoSuchAlgorithmException {
		// TODO not cool, maybe something else than password
		String encryptPassword = encryptPassword(customer.getPassword());
		Customer findByNamePassword = customerRepository.findByEmailAndPassword(customer.getEmail(), encryptPassword);
		String generateToken = null;
		if (findByNamePassword != null) {
			generateToken = generateToken(customer.getEmail(), encryptPassword);
		} else {
			throw new RuntimeException("Email/password don't match. Or user not exists");
		}
		return generateToken;
	}

}
