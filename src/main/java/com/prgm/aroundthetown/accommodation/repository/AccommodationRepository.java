package com.prgm.aroundthetown.accommodation.repository;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    List<Accommodation> getAccommodationsByHostId(Long hostId);
}
