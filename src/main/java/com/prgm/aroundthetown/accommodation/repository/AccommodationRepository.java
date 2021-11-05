package com.prgm.aroundthetown.accommodation.repository;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    // TODO: host repository
    List<Accommodation> getAccommodationsByHostId(Long hostId);

    List<Accommodation> getAccommodationByAccommodationCategoryAndRegion(AccommodationCategory category, Region region);

    void deleteAccommodationByHostAndProductId(Host host, Long productId);

    Accommodation findByProductId(Long productId);
}
