package com.prgm.aroundthetown.accommodation.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.vo.Region;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.product.vo.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AccommodationControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HostRepository hostRepository;

    Host host;

    @BeforeEach
    void setUp() throws Exception {
        host = Host.builder()
                .hostName("강민희")
                .hostPhoneNumber("01066669999")
                .hostEmail("kang@naver.com")
                .build();
        hostRepository.save(host);
    }

    //사장님이 숙소를 잘 등록할 수 있다.
    @Test
    void saveAccommodation() throws Exception {
        //given
        AccommodationCreateRequestDto accommodationCreateRequestDto = AccommodationCreateRequestDto.builder()
                .accommodationName("숙박업체 이름")
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


    //등록된 숙소들중 지역과 카테고리에 맞는 정보만 가져올 수 있다.

    //등록된 전체 숙박업체 정보를 조회할 수 있다.
}