package com.vanhack.skipthedishes.orderservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.skipthedishes.orderservice.entity.Orders;
import com.vanhack.skipthedishes.orderservice.exceptions.NotFoundException;
import com.vanhack.skipthedishes.orderservice.repository.OrdersRepository;
import com.vanhack.skipthedishes.orderservice.service.OrdersService;

@RestController
@RequestMapping("/Order")
public class OrdersController {

	@Autowired
	private final OrdersRepository ordersRepository;
	@Autowired
	private OrdersService ordersService;

	public OrdersController(OrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}

	/**
	 * Receive new order
	 * 
	 * @param order
	 */
	@PostMapping
	public void create(@RequestBody Orders order) {
		ordersRepository.save(order);
	}

	/**
	 * Get all informations about a order
	 * 
	 * @param orderId
	 * @return
	 */
	@GetMapping("/{orderId}")
	public Orders findById(@PathVariable Long orderId) {
		Optional<Orders> orders = ordersRepository.findById(orderId);
		if (orders.isPresent()) {
			return orders.get();
		} else {
			throw new NotFoundException("Order not found");
		}
	}

	/**
	 * Get a order status
	 * 
	 * @param orderId
	 * @return
	 */
	@GetMapping("/status/{orderId}")
	public String orderStatus(@PathVariable Long orderId) {
		String statusById = ordersRepository.findStatusById(orderId);
		if(StringUtils.isEmpty(statusById)) {
			throw new NotFoundException("Order not found");
		}
		return statusById;
	}

	/**
	 * Cancel a Order
	 * 
	 * @param orderId
	 */
	@PutMapping("/{orderId}")
	public void cancel(@PathVariable Long orderId) {
		ordersService.cancel(orderId);
	}

}
