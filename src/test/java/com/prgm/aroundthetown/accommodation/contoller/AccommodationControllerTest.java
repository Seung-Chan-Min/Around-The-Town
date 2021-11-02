package com.prgm.aroundthetown.accommodation.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.dto.LocationDTO;
import com.prgm.aroundthetown.product.vo.Region;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class AccommodationControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HostRepository hostRepository;

    Host host;

    @BeforeAll
    void setUp() throws Exception {
        host = Host.builder()
                .hostName("강민희")
                .hostPhoneNumber("01066669999")
                .hostEmail("kang@naver.com")
                .build();
        hostRepository.save(host);
    }

    @Test
    @DisplayName("사장님이 숙소(Accommodation)를 등록할 수 있다.")
    @Transactional
    @Rollback(value = false)
    @Order(1)
    void saveAccommodation() throws Exception {
        //given
        AccommodationCreateRequestDto accommodationCreateRequestDto = AccommodationCreateRequestDto.builder()
                .accommodationName("숙박업체 이름")
                .accommodationNotice("숙박업체 공지")
                .optionNotice("옵션 공지")
                .optionNotice("optionNotice")
                .guide("guide")
                .accommodationCategory(AccommodationCategory.HOTEL)
                .businessName("미니컴퍼니")
                .refundRule("환불 규정")
                .location(LocationDTO.builder()
                        .howToVisit("방문하는 방법")
                        .latitude(31.10000)
                        .longitude(111.11111)
                        .content("버스타고 50분")
                        .build())
                .phoneNumber("01022223333")
                .businessRegistrationNumber("192293293847")
                .businessAddress("경기도 용인시 죽전동")
                .region(Region.GYEONGGI)
                .hostId(host.getId())
                .build();

        //when
        //then
        mockMvc.perform(post("/api/v1/hosts/accommodations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationCreateRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    @DisplayName("사장님이 보유한 숙박업체를 조회할 수 있다.")
    @Rollback(value = false)
    @Transactional
    @Order(2)
    void getAccommodationByHostId() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/hosts/{hostId}/accommodations", host.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 숙소들중 지역과 카테고리에 맞는 정보만 가져올 수 있다.")
    @Rollback(value = false)
    @Transactional
    @Order(3)
    void getAccommodationByCategoryAndRegion() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/accommodations")
                        .param("category", String.valueOf(AccommodationCategory.HOTEL))
                        .param("region", String.valueOf(Region.GYEONGGI))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 숙소들중 지역과 카테고리에 맞는 정보만 가져올 수 있다.")
    @Rollback(value = false)
    @Transactional
    @Order(4)
    void getAccommodations() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/accommodation-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }




}