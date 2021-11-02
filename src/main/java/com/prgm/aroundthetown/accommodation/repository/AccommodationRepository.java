package com.prgm.aroundthetown.accommodation.repository;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.product.vo.Region;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    List<Accommodation> getAccommodationsByHostId(Long hostId);

    List<Accommodation> getAccommodationByAccommodationCategoryAndRegion(AccommodationCategory accommodationCategory, Region region);
}
