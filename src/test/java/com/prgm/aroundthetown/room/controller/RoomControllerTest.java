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
                .hostName("?????????")
                .hostPhoneNumber("01066669999")
                .hostEmail("kang@naver.com")
                .build();
        accommodation = Accommodation.builder().accommodationName("???????????? ??????")
                .accommodationNotice("???????????? ??????")
                .optionNotice("optionNotice")
                .guide("guide")
                .accommodationCategory(AccommodationCategory.HOTEL)
                .productType(ProductType.ACCOMMODATION)
                .businessName("???????????????")
                .refundRule("?????? ??????")
                .location(Location.builder()
                        .howToVisit("???????????? ??????")
                        .latitude(31.10000)
                        .longitude(111.11111)
                        .content("???????????? 50???")
                        .build())
                .phoneNumber("01022223333")
                .businessRegistrationNumber("192293293847")
                .businessAddress("????????? ????????? ?????????")
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
    @DisplayName("???????????? ????????? ??????????????? ?????? ??? ????????? ???????????? ???????????? roomReservation ????????? ????????????.")
    @Order(1)
    void saveRoom() throws Exception {
        final RoomCreateRequestDto roomCreateResponseDto = RoomCreateRequestDto.builder()
                .roomName("?????? ??????")
                .reservationNotice("????????????")
                .roomInformation("??? ??????")
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
                                parameterWithName("productId").description("?????? ?????????")
                        ),
                        requestFields(
                                fieldWithPath("roomName").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("roomInformation").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("standardPeople").type(JsonFieldType.NUMBER).description("????????????"),
                                fieldWithPath("maximumPeople").type(JsonFieldType.NUMBER).description("????????????"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("maxStock").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                fieldWithPath("reservationNotice").type(JsonFieldType.STRING).description("?????? ??????")

                        ),
                        responseFields(
                                fieldWithPath("roomName").type(JsonFieldType.STRING).description("????????? ??? ??????")
                        )
                ));
        ;
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("accommodation ??? ?????????????????? ????????? ????????? ??? ??????.")
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
                                parameterWithName("accommodationId").description("????????????")
                        ),
                        requestParameters(
                                parameterWithName("check-in").description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("[].accommodationDto").type(JsonFieldType.OBJECT).description("??????DTO"),
                                fieldWithPath("[].accommodationDto.accommodationName").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("[].accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("???????????? ????????????"),
                                fieldWithPath("[].accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].accommodationDto.optionNotice").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].accommodationDto.guide").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("[].roomName").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("[].reservationNotice").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("[].roomInformation").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("[].standardPeople").type(JsonFieldType.NUMBER).description("????????????"),
                                fieldWithPath("[].maximumPeople").type(JsonFieldType.NUMBER).description("????????????"),
                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("[].remains").type(JsonFieldType.NUMBER).description("?????? ????????????")
                        )
                ));
        ;
    }
}