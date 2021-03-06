package com.prgm.aroundthetown.product;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByHost(Host host);
}
