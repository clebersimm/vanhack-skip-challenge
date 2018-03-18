package com.vanhack.skipthedishes.orderservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanhack.skipthedishes.orderservice.entity.Orders;
import com.vanhack.skipthedishes.orderservice.repository.OrdersRepository;
import com.vanhack.skipthedishes.orderservice.util.OrderStatus;

@Service
public class OrdersService {

	@Autowired
	private OrdersRepository ordersRepository;

	public void cancel(Long orderId) {
		Optional<Orders> findById = ordersRepository.findById(orderId);
		if (findById.isPresent()) {
			Orders orders = findById.get();
			orders.setStatus(OrderStatus.CANCELLED.status());
			ordersRepository.save(orders);
		}
	}

}
