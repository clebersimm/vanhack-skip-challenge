package com.vanhack.skipthedishes.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.skipthedishes.orderservice.entity.Orders;
import com.vanhack.skipthedishes.orderservice.repository.OrdersRepository;

@RestController
@RequestMapping("/Order")
public class OrdersController {

	@Autowired
	private final OrdersRepository ordersRepository;
	
	public OrdersController(OrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public void create(@RequestBody Orders order) {
		ordersRepository.save(order);
	}
	
	@RequestMapping(value="/{orderId}", method = RequestMethod.GET)
	public void findById(@PathVariable Long orderId) {
		
	}
		
}
