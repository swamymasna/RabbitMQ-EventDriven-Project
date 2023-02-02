package com.swamy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swamy.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}
