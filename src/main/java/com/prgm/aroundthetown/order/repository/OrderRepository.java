package com.prgm.aroundthetown.order.repository;

import com.prgm.aroundthetown.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
