package com.vanhack.skipthedishes.orderservice.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.skipthedishes.orderservice.entity.Customer;
import com.vanhack.skipthedishes.orderservice.service.CustomerService;

@RestController
@RequestMapping("/Customer")
public class CustomerController {

	@Autowired
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@RequestBody Customer customer) {
		String response = null;
		try {
			response = customerService.save(customer);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public String auth(@RequestBody Customer customer) {
		if (customer.getEmail().trim().isEmpty() && customer.getEmail().trim().isEmpty()) {
			throw new RuntimeException("Email and/or password must not be null");
		}
		try {
			return customerService.login(customer);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unable to process, try again later");
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
