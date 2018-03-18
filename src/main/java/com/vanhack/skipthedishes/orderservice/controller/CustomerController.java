package com.vanhack.skipthedishes.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping
	public String create(@RequestBody Customer customer) {
		String response = null;
		try {
			response = customerService.save(customer);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return response;
	}

	//TODO the method auth was removed until spring security is implemented. Last thing to do.

}
