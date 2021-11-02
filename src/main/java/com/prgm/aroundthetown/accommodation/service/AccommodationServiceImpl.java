package com.prgm.aroundthetown.accommodation.service;

import com.prgm.aroundthetown.accommodation.converter.AccommodationConverter;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationResponseDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.ProductRepository;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AccommodationServiceImpl implements AccommodationService {
    private final ProductRepository productRepository;

    private final AccommodationRepository accommodationRepository;

    private final HostRepository hostRepository;

    private final AccommodationConverter accommodationConverter;

    @Override
    @Transactional
    public AccommodationCreateResponseDto save(final AccommodationCreateRequestDto accommodationCreateDto) {
        final Host host = hostRepository.getById(accommodationCreateDto.getHostId()); //login 대체제
        final Product retrievedProduct = productRepository
                .save(accommodationConverter.createDtoToEntity(accommodationCreateDto, host));
        final Accommodation accommodation = accommodationRepository.getById(retrievedProduct.getProductId());

        return accommodationConverter.entityToResponseAccommodationCreateDto(accommodation);
    }

    @Override
    public List<AccommodationResponseDto> getAccommodations() {
        return accommodationConverter.AccommodationEntityToResponseDto
                (accommodationRepository.findAll());
    }

    @Override
    public List<AccommodationResponseDto> geAccommodationByHostId(final Long hostId) {
        return accommodationConverter.AccommodationEntityToResponseDto
                (accommodationRepository.getAccommodationsByHostId(hostId));
    }

    @Override
    public List<AccommodationResponseDto> getAccommodationsByCategoryAndRegion(final AccommodationCategory category, final Region region) {
        return accommodationConverter.AccommodationEntityToResponseDto
                (accommodationRepository.getAccommodationByAccommodationCategoryAndRegion(category, region));
    }

}
