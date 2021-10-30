package com.prgm.aroundthetown.product.room.repository;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.product.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.product.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.product.room.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

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
    @DisplayName("room을 save 할 수 있다.")
    @Transactional
    void saveRoomTest() {
        final Room room = Room.builder()
                .roomName("name")
                .reservationNotice("notice")
                .roomInformation("info")
                .standardPeople(3)
                .maximumPeople(6)
                .price(60000)
                .stock(5)
                .accommodation(accommodationRepository.findAll().get(0))
                .build();
        roomRepository.save(room);

        assertThat(roomRepository.findAll().size(), is(1));

        // 연관관계 mapping
        assertThat(accommodationRepository.findAll().get(0).getRooms().size(), is(1));
    }

}