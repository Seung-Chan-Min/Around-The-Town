package com.prgm.aroundthetown.accommodation.repository;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    //TODO: host repository
    List<Accommodation> getAccommodationsByHostId(Long hostId);

    List<Accommodation> getAccommodationByAccommodationCategoryAndRegion(AccommodationCategory category, Region region);

    //    @Modifying
//    @Query("UPDATE Accommodation SET isDeleted = true WHERE Host =:host and productId =:productId")
    void deleteAccommodationByHostAndProductId(@Param("host") Host host, @Param("productId") Long productId);
}
