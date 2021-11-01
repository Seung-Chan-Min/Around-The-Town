package com.prgm.aroundthetown.room.repository;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.room.entity.Room;
import com.prgm.aroundthetown.room.entity.RoomImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class RoomImageRepositoryTest {

    @Autowired
    private RoomImageRepository roomImageRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Host savedHost;
    private Room savedRoom;

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
        savedRoom = roomRepository.save(room);
    }

    @Test
    @DisplayName("room image을 save 할 수 있다.")
    @Transactional
    void saveRoomTest() {
        final RoomImage image = RoomImage.builder()
                .IMAGE_PATH("path")
                .room(savedRoom)
                .build();
        roomImageRepository.save(image);

        assertThat(roomImageRepository.findAll().size(), is(1));

        // 연관관계 mapping
        assertThat(roomRepository.findAll().get(0).getRoomImages().size(), is(1));
    }

}