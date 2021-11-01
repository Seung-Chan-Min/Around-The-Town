package com.prgm.aroundthetown.accommodation.repository;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
