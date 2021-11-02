package com.prgm.aroundthetown.accommodation.service;

import com.prgm.aroundthetown.accommodation.dto.AccommodationResponseDto;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.accommodation.common.AccommodationConverter;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.product.entity.Product;
import com.prgm.aroundthetown.product.repository.ProductRepository;
import com.prgm.aroundthetown.product.vo.Region;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AccommodationServiceImpl implements AccommodationService{
    private final ProductRepository productRepository;

    private final AccommodationRepository accommodationRepository;

    private final HostRepository hostRepository;

    private final AccommodationConverter accommodationConverter;

    @Override
    @Transactional
    public AccommodationCreateResponseDto save(AccommodationCreateRequestDto accommodationCreateDto) {
        Host host = hostRepository.getById(accommodationCreateDto.getHostId()); //login 대체제
        Product retrievedProduct = productRepository
                .save(accommodationConverter.createDtoToEntity(accommodationCreateDto, host));
        Accommodation accommodation = accommodationRepository.getById(retrievedProduct.getProductId());

        return accommodationConverter.entityToResponseAccommodationCreateDto(accommodation);
    }

    @Override
    public List<AccommodationResponseDto> getAccommodations() {
        return accommodationConverter.AccommodationEntityToResponseDto
                (accommodationRepository.findAll());
    }

    @Override
    public List<AccommodationResponseDto> geAccommodationByHostId(Long hostId) {
        return accommodationConverter.AccommodationEntityToResponseDto
                (accommodationRepository.getAccommodationsByHostId(hostId));
    }

    @Override
    public List<AccommodationResponseDto> getAccommodationsByCategoryAndRegion(AccommodationCategory category, Region region) {
        return accommodationConverter.AccommodationEntityToResponseDto
                (accommodationRepository.getAccommodationByAccommodationCategoryAndRegion(category, region));
    }

}
