package com.prgm.aroundthetown.product.accommodation.repository;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.entity.AccommodationOption;
import com.prgm.aroundthetown.accommodation.entity.AccommodationOptionCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationOptionRepository;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.entity.Location;
import com.prgm.aroundthetown.product.entity.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class AccommodationOptionRepositoryTest {

    @Autowired
    private AccommodationOptionRepository optionRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private HostRepository hostRepository;

    @BeforeEach
    void setUp() {
        final Host host = Host.builder()
                .hostName("name")
                .hostEmail("email")
                .hostPhoneNumber("0106666")
                .build();
        final Host savedHost = hostRepository.save(host);

        final Accommodation accommodation = Accommodation.builder()
                .host(savedHost)
                .refundRule("rule")
                .location(Location.builder().build())
                .phoneNumber("phone")
                .businessRegistrationNumber("number")
                .businessAddress("address")
                .businessName("namebu")
                .region(Region.SEOUL)
                .accommodationName("name")
                .accommodationNotice("notice")
                .optionNotice("option")
                .guide("guide")
                .accommodationCategory(AccommodationCategory.MOTEL)
                .build();
        accommodationRepository.save(accommodation);
    }

    @Test
    @DisplayName("accommodation option를 save 할 수 있다.")
    @Transactional
    void saveOptionTest() {
        final Accommodation findedAccommodation = accommodationRepository.findAll().get(0);
        final AccommodationOption option1 = AccommodationOption.builder()
                .option(AccommodationOptionCategory.PARKING)
                .accommodation(findedAccommodation)
                .build();
        final AccommodationOption option2 = AccommodationOption.builder()
                .option(AccommodationOptionCategory.NETFLIX)
                .accommodation(findedAccommodation)
                .build();
        optionRepository.save(option2);

        assertThat(optionRepository.findAll().size(), is(2));

        // 연관관계 mapping
        assertThat(accommodationRepository.findAll().get(0).getOptions().size(), is(2));
    }

}