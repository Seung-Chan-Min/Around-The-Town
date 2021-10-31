package com.prgm.aroundthetown.product.entity;

import static org.assertj.core.api.Assertions.*;

import com.prgm.aroundthetown.host.dto.HostCreateRequest;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.host.service.HostService;
import com.prgm.aroundthetown.product.entity.vo.Location;
import com.prgm.aroundthetown.product.repository.AccommodationRepository;
import com.prgm.aroundthetown.product.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional(readOnly = true)
class ProductTest {

    @Autowired private HostService hostService;
    @Autowired private AccommodationRepository accommodationRepository;
    @Autowired private RoomRepository roomRepository;

    private Long hostId;
    private Long accommodationId;
    private Long roomId;

    @BeforeEach
    @Transactional
    public void setUp() {
        HostCreateRequest hostDto = HostCreateRequest.builder()
            .name("전찬의")
            .password("secret")
            .email("jcu011@naver.com")
            .phoneNumber("01011112797")
            .build();
        hostId = hostService.createHost(hostDto).getId();

        Accommodation accommodation = Accommodation.builder()
            .name("대명이튼캐슬")
            .accommodationNotice("동생이살고있음")
            .howToUse("치킨한마리납품")
            .themeNotice("흡연금지구역")
            .refundRule("환불불가")
            .phoneNumber("01073781111")
            .businessRegistrationNumber("1234")
            .businessAddress("군자로10길")
            .businessName("Lombok최고")
            .location(new Location("네이버지도", "GS앞", 15.0, 15.0))
            .build();
        // accommodation.setHost(host);
        accommodation = accommodationRepository.save(accommodation);
        accommodationId = accommodation.getId();

        Room room = Room.builder()
            .name("404호")
            .reservationNotice("Single Bed")
            .roomInformation("Chans")
            .price(18000)
            .standardPeople(1)
            .maximumPeople(2)
            .maxStock(2)
            .build();
        room.setAccommodation(accommodation);
        room = roomRepository.save(room);
        roomId = room.getId();

    }

    @AfterEach
    void tearDown() throws InterruptedException {
        hostRepository.deleteAll();
        accommodationRepository.deleteAll();
        roomRepository.deleteAll();
    }
    
    @Test
    public void createRoomTest() {
        Room foundRoom = roomRepository.getById(roomId);

        assertThat(foundRoom.getId()).isEqualTo(roomId);
        assertThat(foundRoom.getName()).isEqualTo("404호");

        assertThat(foundRoom.getAccommodation().getId()).isEqualTo(accommodationId);
        assertThat(foundRoom.getAccommodation().getName()).isEqualTo("대명이튼캐슬");

        assertThat(foundRoom.getAccommodation().getHost().getId()).isEqualTo(hostId);
        assertThat(foundRoom.getAccommodation().getHost().getName()).isEqualTo("전찬의");
    }
}