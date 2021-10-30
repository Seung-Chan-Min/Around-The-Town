package com.prgm.aroundthetown.product.accommodation.repository;

import com.prgm.aroundthetown.product.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
