package com.prgm.aroundthetown.order.repository;

import com.prgm.aroundthetown.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
