package com.prgm.aroundthetown.room.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.common.ClassLevelTestConfig;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.room.dto.RoomCreateRequestDto;
import com.prgm.aroundthetown.room.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class RoomControllerTest extends ClassLevelTestConfig {

    @Autowired
    public MockMvc mockMvc;
    Accommodation accommodation;
    Host host;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private RoomRepository RoomRepository;

    @BeforeAll
    void setUp() {
        host = Host.builder()
                .hostName("강민희")
                .hostPhoneNumber("01066669999")
                .hostEmail("kang@naver.com")
                .build();
        accommodation = Accommodation.builder().accommodationName("숙박업체 이름")
                .accommodationNotice("숙박업체 공지")
                .optionNotice("optionNotice")
                .guide("guide")
                .accommodationCategory(AccommodationCategory.HOTEL)
                .businessName("미니컴퍼니")
                .refundRule("환불 규정")
                .location(Location.builder()
                        .howToVisit("방문하는 방법")
                        .latitude(31.10000)
                        .longitude(111.11111)
                        .content("버스타고 50분")
                        .build())
                .phoneNumber("01022223333")
                .businessRegistrationNumber("192293293847")
                .businessAddress("경기도 용인시 죽전동")
                .region(Region.SEOUL)
                .host(host)
                .build();

        host.addProduct(accommodation);
        hostRepository.save(host);
//        accommodationRepository.save(accommodation);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("사장님이 보유한 숙박업체에 대한 방 정보를 저장하면 자동으로 roomReservation 정보가 입력된다.")
    @Order(1)
    void saveRoom() throws Exception {
        final RoomCreateRequestDto roomCreateResponseDto = RoomCreateRequestDto.builder()
                .roomName("랜덤 객실")
                .reservationNotice("예약공지")
                .roomInformation("방 정보")
                .standardPeople(2)
                .maximumPeople(2)
                .price(50000)
                .maxStock(10)
                .build();

        mockMvc.perform(post("/api/v1/hosts/accommodations/{productId}", accommodation.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomCreateResponseDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("체크인 날짜에 따라서 남은 객실들의 정보를 확인할 수 있다.")
    @Order(2)
    void getRoomCheckIn() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/accommodations/{accommodationId}", accommodation.getProductId())
                        .param("check-in", String.valueOf(LocalDate.now().plusDays(1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}