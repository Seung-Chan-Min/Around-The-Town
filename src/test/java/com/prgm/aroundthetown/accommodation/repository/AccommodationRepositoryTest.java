package com.prgm.aroundthetown.accommodation.repository;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.vo.Location;
import com.prgm.aroundthetown.product.vo.Region;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class AccommodationRepositoryTest {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private HostRepository hostRepository;

    private Host savedHost;

    private Accommodation findedAccommodation;

    @BeforeEach
    void setUp() {
        final Host host = Host.builder()
                .hostName("name")
                .hostEmail("email")
                .hostPhoneNumber("0106666")
                .build();
        savedHost = hostRepository.save(host);
    }

    @Test
    @Order(1)
    @DisplayName("accommodation을 수정할 수 있다.")
    @Transactional
    @Rollback(value = false)
    void accommodationUpdateTest() {
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
        assertThat(accommodationRepository.findAll().size(), is(1));

        findedAccommodation = accommodationRepository.findAll().get(0);
        findedAccommodation.update(
                "바뀐이름",
                findedAccommodation.getAccommodationNotice(),
                findedAccommodation.getOptionNotice(),
                findedAccommodation.getAccommodationName(),
                findedAccommodation.getAccommodationCategory());
        accommodationRepository.save(findedAccommodation);

        assertThat(accommodationRepository.findAll().get(0).getAccommodationName(), is("바뀐이름"));
    }

}