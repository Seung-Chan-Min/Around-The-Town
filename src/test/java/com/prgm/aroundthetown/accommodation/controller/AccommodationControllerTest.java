package com.prgm.aroundthetown.accommodation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationOptionDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationUpdateRequestDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.entity.AccommodationOptionCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.common.ClassLevelTestConfig;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductCreateRequestDto;
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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs
class AccommodationControllerTest extends ClassLevelTestConfig {

    @Autowired
    public MockMvc mockMvc;
    Host host;
    Accommodation accommodation;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    @BeforeAll
    void setUp() {
        host = Host.builder()
                .hostName("?????????")
                .hostPhoneNumber("01066669999")
                .hostEmail("kang@naver.com")
                .build();

        accommodation = Accommodation.builder()
                .host(host)
                .accommodationName("accommodation2")
                .accommodationCategory(AccommodationCategory.MOTEL)
                .accommodationNotice("noti")
                .businessAddress("??????")
                .businessName("sample business")
                .optionNotice("option")
                .location(Location.builder()
                        .latitude(30.1212)
                        .longitude(123.1231231)
                        .content("???????????????")
                        .howToVisit("???????????????")
                        .build())
                .businessRegistrationNumber("122000929391")
                .phoneNumber("01022223333")
                .refundRule("????????????")
                .region(Region.SEOUL)
                .guide("test")
                .build();

        host.addProduct(accommodation);
        hostRepository.save(host);
    }

    @Test
    @DisplayName("???????????? ??????(Accommodation)??? ????????? ??? ??????.")
    @Transactional
    @Rollback(value = false)
    @Order(1)
    void saveAccommodation() throws Exception {
        //given
        final var option1 = AccommodationOptionDto.builder()
                .accommodationOptionCategory(AccommodationOptionCategory.NETFLIX)
                .build();
        final var option2 = AccommodationOptionDto.builder()
                .accommodationOptionCategory(AccommodationOptionCategory.BREAKFAST)
                .build();
        final List<AccommodationOptionDto> accommodationOptions = Arrays.asList(option1, option2);
        final AccommodationCreateRequestDto accommodationCreateRequestDto = AccommodationCreateRequestDto.builder()
                .accommodationName("???????????? ??????")
                .accommodationNotice("???????????? ??????")
                .optionNotice("?????? ??????")
                .optionNotice("optionNotice")
                .guide("guide")
                .accommodationCategory(AccommodationCategory.HOTEL)
                .productDto(ProductCreateRequestDto.builder()
                        .businessName("???????????????")
                        .refundRule("?????? ??????")
                        .location(LocationDto.builder()
                                .howToVisit("???????????? ??????")
                                .latitude(31.10000)
                                .longitude(111.11111)
                                .content("???????????? 50???")
                                .build())
                        .phoneNumber("01022223333")
                        .businessRegistrationNumber("192293293847")
                        .businessAddress("????????? ????????? ?????????")
                        .region(Region.GYEONGGI)
                        .build())
                .accommodationOptions(accommodationOptions)
                .hostId(host.getId())
                .build();

        //when
        mockMvc.perform(post("/api/v1/hosts/accommodations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(accommodationCreateRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("accommodation-save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("hostId").type(JsonFieldType.NUMBER).description("??????????????????"),
                                fieldWithPath("productDto").type(JsonFieldType.OBJECT).description("??????????????????"),
                                fieldWithPath("productDto.hostId").type(JsonFieldType.NULL).description("?????? id null"),
                                fieldWithPath("productDto.refundRule").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("productDto.phoneNumber").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                fieldWithPath("productDto.businessAddress").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("productDto.businessName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("productDto.region").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("productDto.location").type(JsonFieldType.OBJECT).description("???????????? ??????"),
                                fieldWithPath("productDto.location.howToVisit").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("productDto.location.latitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("productDto.location.longitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("productDto.location.content").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("accommodationName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("accommodationNotice").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("optionNotice").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("guide").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("accommodationCategory").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("accommodationOptions[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????????"),
                                fieldWithPath("accommodationOptions[].*").type(JsonFieldType.STRING).description("?????? ?????? ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("businessName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("accommodationName").type(JsonFieldType.STRING).description("???????????? ??????")
                        )

                ));

        //then (???????????? set(??????) ????????? ??? ?????????????????? ??????)
        final List<Accommodation> accommodations = accommodationRepository.findAll();
        final var lastInsertValue = accommodations.get(accommodations.size() - 1).getOptions();
        assertThat(lastInsertValue.size(), is(2));
    }

    @Test
    @DisplayName("???????????? ????????? ??????????????? ????????? ??? ??????.")
    @Rollback(value = false)
    @Transactional
    @Order(2)
    void getAccommodationByHostId() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/hosts/{hostId}/accommodations", host.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("accommodation-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("hostId").description("????????? ?????????")),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("???????????? ?????????"),
                                fieldWithPath("[].accommodationName").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("[].accommodationNotice").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].optionNotice").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].guide").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("[].accommodationCategory").type(JsonFieldType.STRING).description("???????????? ????????????")
                        )
                ));
    }

    @Test
    @DisplayName("????????? ???????????? ????????? ??????????????? ?????? ????????? ????????? ??? ??????.")
    @Rollback(value = false)
    @Transactional
    @Order(3)
    void getAccommodationByCategoryAndRegion() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/accommodations")
                        .param("category", String.valueOf(AccommodationCategory.HOTEL))
                        .param("region", String.valueOf(Region.GYEONGGI))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("category-region-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("category").description("????????????"),
                                parameterWithName("region").description("??????")
                        ),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("???????????? ?????????"),
                                fieldWithPath("[].accommodationName").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("[].accommodationNotice").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].optionNotice").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].guide").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("[].accommodationCategory").type(JsonFieldType.STRING).description("???????????? ????????????")
                        )
                ));
    }

    @Test
    @DisplayName("???????????? ??????(Accommodation)??? ????????? ??? ??????.")
    @Transactional
    @Rollback(value = false)
    @Order(4)
    void updateAccommodation() throws Exception {
        //given
        final var option1 = AccommodationOptionDto.builder()
                .accommodationOptionCategory(AccommodationOptionCategory.NETFLIX)
                .build();
        final var option2 = AccommodationOptionDto.builder()
                .accommodationOptionCategory(AccommodationOptionCategory.BREAKFAST)
                .build();
        final List<AccommodationOptionDto> accommodationOptions = Arrays.asList(option1, option2);
        final AccommodationUpdateRequestDto accommodationUpdateRequestDto = AccommodationUpdateRequestDto.builder()
                .accommodationName("update ???????????? ??????")
                .accommodationNotice("update ???????????? ??????")
                .optionNotice("update ?????? ??????")
                .optionNotice("update optionNotice")
                .guide("update guide")
                .accommodationCategory(AccommodationCategory.HOTEL)
                .productDto(ProductCreateRequestDto.builder()
                        .businessName("update ???????????????")
                        .refundRule("update ?????? ??????")
                        .location(LocationDto.builder()
                                .howToVisit("update ???????????? ??????")
                                .latitude(31.10000)
                                .longitude(111.11111)
                                .content("update ???????????? 50???")
                                .build())
                        .phoneNumber("update 01022223333")
                        .businessRegistrationNumber("update 192293293847")
                        .businessAddress("update ????????? ????????? ?????????")
                        .region(Region.GYEONGGI)
                        .build())
                .accommodationOptions(accommodationOptions)
                .hostId(host.getId())
                .build();

        //when
        mockMvc.perform(
                        RestDocumentationRequestBuilders.patch("/api/v1/hosts/accommodations/{accommodationId}", accommodation.getProductId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(objectMapper.writeValueAsString(accommodationUpdateRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("accommodation-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("accommodationId").description("?????? ?????????")),
                        requestFields(
                                fieldWithPath("hostId").type(JsonFieldType.NUMBER).description("??????????????????"),
                                fieldWithPath("productDto").type(JsonFieldType.OBJECT).description("??????????????????"),
                                fieldWithPath("productDto.hostId").type(JsonFieldType.NULL).description("?????? id null"),
                                fieldWithPath("productDto.refundRule").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("productDto.phoneNumber").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                fieldWithPath("productDto.businessAddress").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("productDto.businessName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("productDto.region").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("productDto.location").type(JsonFieldType.OBJECT).description("???????????? ??????"),
                                fieldWithPath("productDto.location.howToVisit").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("productDto.location.latitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("productDto.location.longitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("productDto.location.content").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("accommodationName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("accommodationNotice").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("optionNotice").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("guide").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("accommodationCategory").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("accommodationOptions[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????????"),
                                fieldWithPath("accommodationOptions[].*").type(JsonFieldType.STRING).description("?????? ?????? ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("businessName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("accommodationName").type(JsonFieldType.STRING).description("???????????? ??????")
                        )

                ));

        //then (???????????? set(??????) ????????? ??? ?????????????????? ??????)
        final List<Accommodation> accommodations = accommodationRepository.findAll();
        final var lastInsertValue = accommodations.get(accommodations.size() - 1).getOptions();
        assertThat(lastInsertValue.size(), is(2));
    }

    @Test
    @DisplayName("????????? ????????? ?????? ?????? ????????????.")
    @Rollback(value = false)
    @Transactional
    @Order(5)
    void getAccommodations() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/accommodation-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("accommodation-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("???????????? ?????????"),
                                fieldWithPath("[].accommodationName").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("[].accommodationNotice").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].optionNotice").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].guide").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("[].accommodationCategory").type(JsonFieldType.STRING).description("???????????? ????????????")
                        )
                ));
        ;
    }

    @Test
    @DisplayName("?????? ??? soft delete ??? ??????.")
    @Transactional
    @Order(6)
    @Rollback(value = false)
    void deleteById() throws Exception {
        final Long accommodationId = accommodationRepository.findAll().get(0).getProductId();
        //given
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/v1/hosts/{hostId}/accommodations/{accommodationId}", host.getId(),
                                        accommodationId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("accommodation-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("hostId").description("????????? ?????????"),
                                parameterWithName("accommodationId").description("?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("accommodationName").type(JsonFieldType.STRING).description("????????? ?????? ?????? ??????")
                        )
                ));
        ;
        //when

        //then
        assertThat(accommodationRepository.findAll().get(0).getIsDeleted(), is(true));

    }


}
