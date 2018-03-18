package com.vanhack.skipthedishes.orderservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.vanhack.skipthedishes.orderservice.entity.Orders;

public interface OrdersRepository extends CrudRepository<Orders, Long> {

}
