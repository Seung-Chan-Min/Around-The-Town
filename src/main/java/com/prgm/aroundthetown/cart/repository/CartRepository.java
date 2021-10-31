package com.prgm.aroundthetown.cart.repository;

import com.prgm.aroundthetown.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
