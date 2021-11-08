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
import com.prgm.aroundthetown.product.entity.ProductType;
import com.prgm.aroundthetown.room.dto.RoomCreateRequestDto;
import com.prgm.aroundthetown.room.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
@AutoConfigureRestDocs
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
                .productType(ProductType.ACCOMMODATION)
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

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/hosts/accommodations/{productId}", accommodation.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomCreateResponseDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("room-save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("productId").description("상품 아이디")
                        ),
                        requestFields(
                                fieldWithPath("roomName").type(JsonFieldType.STRING).description("방이름"),
                                fieldWithPath("roomInformation").type(JsonFieldType.STRING).description("방정보"),
                                fieldWithPath("standardPeople").type(JsonFieldType.NUMBER).description("기준인원"),
                                fieldWithPath("maximumPeople").type(JsonFieldType.NUMBER).description("최대인원"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("maxStock").type(JsonFieldType.NUMBER).description("최초 재고수량"),
                                fieldWithPath("reservationNotice").type(JsonFieldType.STRING).description("예약 공지")

                        ),
                        responseFields(
                                fieldWithPath("roomName").type(JsonFieldType.STRING).description("등록된 방 이름")
                        )
                ));
        ;
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("accommodation 에 등록되어있는 방들을 검색할 수 있다.")
    @Order(2)
    void getRoomCheckIn() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/accommodations/{accommodationId}", accommodation.getProductId())
                        .param("check-in", String.valueOf(LocalDate.now().plusDays(1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("room-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("accommodationId").description("숙박정보")
                        ),
                        requestParameters(
                                parameterWithName("check-in").description("체크인 날짜")
                        ),
                        responseFields(
                                fieldWithPath("[].accommodationDto").type(JsonFieldType.OBJECT).description("숙박DTO"),
                                fieldWithPath("[].accommodationDto.accommodationName").type(JsonFieldType.STRING).description("공지사항"),
                                fieldWithPath("[].accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("숙박업소 카테고리"),
                                fieldWithPath("[].accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("숙소 공지"),
                                fieldWithPath("[].accommodationDto.optionNotice").type(JsonFieldType.STRING).description("옵션 공지"),
                                fieldWithPath("[].accommodationDto.guide").type(JsonFieldType.STRING).description("가이드"),
                                fieldWithPath("[].roomName").type(JsonFieldType.STRING).description("방이름"),
                                fieldWithPath("[].reservationNotice").type(JsonFieldType.STRING).description("방공지"),
                                fieldWithPath("[].roomInformation").type(JsonFieldType.STRING).description("방정보"),
                                fieldWithPath("[].standardPeople").type(JsonFieldType.NUMBER).description("기준인원"),
                                fieldWithPath("[].maximumPeople").type(JsonFieldType.NUMBER).description("최대인원"),
                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("[].remains").type(JsonFieldType.NUMBER).description("최초 재고수량")
                        )
                ));
        ;
    }
}