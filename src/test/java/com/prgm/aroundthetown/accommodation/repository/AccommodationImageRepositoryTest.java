package com.prgm.aroundthetown.accommodation.repository;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.vo.Location;
import com.prgm.aroundthetown.product.vo.Region;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.entity.AccommodationImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class AccommodationImageRepositoryTest {

    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private AccommodationImageRepository imageRepository;
    @Autowired
    private HostRepository hostRepository;

    private Host savedHost;

    @BeforeEach
    void setUp() {
        final Host host = Host.builder()
                .hostName("name")
                .hostEmail("email")
                .hostPhoneNumber("0106666")
                .build();
        savedHost = hostRepository.save(host);

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
    @DisplayName("accommodation image를 save 할 수 있다.")
    @Transactional
    void saveImageTest() {
        final Accommodation findedAccommodation = accommodationRepository.findAll().get(0);
        final AccommodationImage image = AccommodationImage.builder()
                .IMAGE_PATH("path")
                .accommodation(findedAccommodation)
                .build();
        imageRepository.save(image);

        assertThat(imageRepository.findAll().size(), is(1));

        // 연관관계 mapping
        assertThat(accommodationRepository.findAll().get(0).getImages().size(), is(1));
    }

}