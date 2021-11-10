package com.prgm.aroundthetown.leisure.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequestDto;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.leisure.repository.LeisureRepository;
import com.prgm.aroundthetown.leisure.service.LeisureServiceImpl;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductCreateRequestDto;
import com.prgm.aroundthetown.product.dto.ProductUpdateRequestDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class LeisureControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private LeisureServiceImpl leisureService;
    @Autowired private LeisureRepository leisureRepository;
    @Autowired private HostRepository hostRepository;

    private Long hostId;
    private LocationDto location;
    private Long leisureId;

    @BeforeEach
    void setUp() throws Exception {
        Host host = Host.builder()
            .hostName("전찬의")
            .hostEmail("jcu011@naver.com")
            .hostPhoneNumber("01073788888")
            .build();
        hostId = hostRepository.save(host).getId();

        location = LocationDto.builder()
            .howToVisit("길찾기")
            .latitude(15.0)
            .longitude(15.0)
            .content("Location")
            .build();
        ProductCreateRequestDto productRequest = ProductCreateRequestDto.builder()
            .hostId(hostId)
            .refundRule("refundRule request")
            .phoneNumber("phoneNumber request")
            .businessRegistrationNumber("businessRegistrationNumber request")
            .businessAddress("businessAddress request")
            .businessName("businessName request")
            .region(Region.SEOUL)
            .location(location)
            .build();

        LeisureCreateRequestDto request = LeisureCreateRequestDto.builder()
            .leisureInformation("leisureInformation request")
            .usecase("usecase request")
            .leisureNotice("leisureNotice request")
            .expirationDate(LocalDateTime.now())
            .leisureCategory(LeisureCategory.SURFING)
            .productCreateRequestDto(productRequest)
            .build();
        leisureId = leisureService.create(request);

    }

    @AfterEach
    void tearDown() {
        leisureRepository.deleteAll();
        hostRepository.deleteAll();
    }

    @Test
    void createLeisureTest() throws Exception {
        // given
        ProductCreateRequestDto productRequest = ProductCreateRequestDto.builder()
            .hostId(hostId)
            .refundRule("refundRule create request")
            .phoneNumber("phoneNumber create request")
            .businessRegistrationNumber("businessRegistrationNumber create request")
            .businessAddress("businessAddress create request")
            .businessName("businessName create request")
            .region(Region.SEOUL)
            .location(location)
            .build();

        LeisureCreateRequestDto request = LeisureCreateRequestDto.builder()
            .leisureInformation("leisureInformation create request")
            .usecase("usecase create request")
            .leisureNotice("leisureNotice create request")
            .expirationDate(LocalDateTime.now())
            .leisureCategory(LeisureCategory.AQUARIUM)
            .productCreateRequestDto(productRequest)
            .build();

        // when // then
        mockMvc.perform(post("/api/v1/leisure")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("LeisureController/createLeisure",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("leisureInformation").type(JsonFieldType.STRING).description("Leisure leisureInformation"),
                    fieldWithPath("usecase").type(JsonFieldType.STRING).description("Leisure usecase"),
                    fieldWithPath("leisureNotice").type(JsonFieldType.STRING).description("Leisure leisureNotice"),
                    fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("Leisure expirationDate"),
                    fieldWithPath("leisureCategory").type(JsonFieldType.STRING).description("Leisure leisureCategory"),
                    fieldWithPath("productCreateRequestDto.hostId").type(JsonFieldType.NUMBER).description("Leisure productCreateRequestDto.hostId"),
                    fieldWithPath("productCreateRequestDto.refundRule").type(JsonFieldType.STRING).description("Leisure refundRule"),
                    fieldWithPath("productCreateRequestDto.phoneNumber").type(JsonFieldType.STRING).description("Leisure phoneNumber"),
                    fieldWithPath("productCreateRequestDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("Leisure businessRegistrationNumber"),
                    fieldWithPath("productCreateRequestDto.businessAddress").type(JsonFieldType.STRING).description("Leisure businessAddress."),
                    fieldWithPath("productCreateRequestDto.businessName").type(JsonFieldType.STRING).description("Leisure businessName."),
                    fieldWithPath("productCreateRequestDto.region").type(JsonFieldType.STRING).description("Leisure productCreateRequestDto.region"),
                    fieldWithPath("productCreateRequestDto.location.howToVisit").type(JsonFieldType.STRING).description("Leisure location.howToVisit"),
                    fieldWithPath("productCreateRequestDto.location.latitude").type(JsonFieldType.NUMBER).description("Leisure location.latitude"),
                    fieldWithPath("productCreateRequestDto.location.longitude").type(JsonFieldType.NUMBER).description("Leisure location.longitude"),
                    fieldWithPath("productCreateRequestDto.location.content").type(JsonFieldType.STRING).description("Leisure location.content")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data").description(JsonFieldType.NUMBER).description("Leisure Id")
                )
            ));
    }

    @Test
    void findLeisureByIdTest() throws Exception {
        // given // when // then
        mockMvc.perform(get("/api/v1/leisure/{leisureId}", leisureId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("LeisureController/findLeisureById",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("leisureId").description(JsonFieldType.NUMBER).description("Leisure Id")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("Leisure Id"),
                    fieldWithPath("data.leisureInformation").description(JsonFieldType.STRING).description("Leisure leisureInformation"),
                    fieldWithPath("data.usecase").description(JsonFieldType.STRING).description("Leisure usecase"),
                    fieldWithPath("data.leisureNotice").description(JsonFieldType.STRING).description("Leisure leisureNotice"),
                    fieldWithPath("data.expirationDate").description(JsonFieldType.STRING).description("Leisure expirationDate"),
                    fieldWithPath("data.leisureCategory").description(JsonFieldType.STRING).description("Leisure leisureCategory"),
                    subsectionWithPath("data.productResponseDto").description("Leisure productResponseDto")
                )
            ));
    }

    @Test
    void findAllLeisureByLeisureCategoryTest() throws Exception {
        // given
        LeisureCategory leisureCategory = LeisureCategory.SURFING;

        // when // then
        mockMvc.perform(get("/api/v1/leisure/leisureCategories/{leisureCategory}", leisureCategory)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("LeisureController/findAllLeisureByLeisureCategory",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("leisureCategory").description(JsonFieldType.STRING).description("Leisure leisureCategory")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.[].id").description(JsonFieldType.NUMBER).description("Leisure Id"),
                    fieldWithPath("data.[].leisureInformation").description(JsonFieldType.STRING).description("Leisure leisureInformation"),
                    fieldWithPath("data.[].usecase").description(JsonFieldType.STRING).description("Leisure usecase"),
                    fieldWithPath("data.[].leisureNotice").description(JsonFieldType.STRING).description("Leisure leisureNotice"),
                    fieldWithPath("data.[].expirationDate").description(JsonFieldType.STRING).description("Leisure expirationDate"),
                    fieldWithPath("data.[].leisureCategory").description(JsonFieldType.STRING).description("Leisure leisureCategory"),
                    subsectionWithPath("data.[].productResponseDto").description("Leisure productResponseDto")
                )
            ));
    }

    @Test
    void findAllLeisureByHostTest() throws Exception {
        // given // when // then
        mockMvc.perform(get("/api/v1/leisure/hosts/{hostId}", hostId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("LeisureController/findAllLeisureByHost",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("hostId").description(JsonFieldType.NUMBER).description("Leisure hostId")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.[].id").description(JsonFieldType.NUMBER).description("Leisure Id"),
                    fieldWithPath("data.[].leisureInformation").description(JsonFieldType.STRING).description("Leisure leisureInformation"),
                    fieldWithPath("data.[].usecase").description(JsonFieldType.STRING).description("Leisure usecase"),
                    fieldWithPath("data.[].leisureNotice").description(JsonFieldType.STRING).description("Leisure leisureNotice"),
                    fieldWithPath("data.[].expirationDate").description(JsonFieldType.STRING).description("Leisure expirationDate"),
                    fieldWithPath("data.[].leisureCategory").description(JsonFieldType.STRING).description("Leisure leisureCategory"),
                    subsectionWithPath("data.[].productResponseDto").description("Leisure productResponseDto")
                )
            ));
    }

    @Test
    void updateLeisureTest() throws Exception {
        // given
        ProductUpdateRequestDto productRequest = ProductUpdateRequestDto.builder()
            .refundRule("refundRule create request")
            .phoneNumber("phoneNumber create request")
            .businessRegistrationNumber("businessRegistrationNumber create request")
            .businessAddress("businessAddress create request")
            .businessName("businessName create request")
            .region(Region.SEOUL)
            .location(location)
            .build();

        LeisureUpdateRequestDto request = LeisureUpdateRequestDto.builder()
            .id(leisureId)
            .leisureInformation("leisureInformation create request")
            .usecase("usecase create request")
            .leisureNotice("leisureNotice create request")
            .expirationDate(LocalDateTime.now())
            .leisureCategory(LeisureCategory.AQUARIUM)
            .productUpdateRequestDto(productRequest)
            .build();

        // when // then
        mockMvc.perform(put("/api/v1/leisure")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("LeisureController/updateLeisure",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("Leisure id"),
                    fieldWithPath("leisureInformation").type(JsonFieldType.STRING).description("Leisure leisureInformation"),
                    fieldWithPath("usecase").type(JsonFieldType.STRING).description("Leisure usecase"),
                    fieldWithPath("leisureNotice").type(JsonFieldType.STRING).description("Leisure leisureNotice"),
                    fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("Leisure expirationDate"),
                    fieldWithPath("leisureCategory").type(JsonFieldType.STRING).description("Leisure leisureCategory"),
                    fieldWithPath("productUpdateRequestDto.refundRule").type(JsonFieldType.STRING).description("Leisure refundRule"),
                    fieldWithPath("productUpdateRequestDto.phoneNumber").type(JsonFieldType.STRING).description("Leisure phoneNumber"),
                    fieldWithPath("productUpdateRequestDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("Leisure businessRegistrationNumber"),
                    fieldWithPath("productUpdateRequestDto.businessAddress").type(JsonFieldType.STRING).description("Leisure businessAddress."),
                    fieldWithPath("productUpdateRequestDto.businessName").type(JsonFieldType.STRING).description("Leisure businessName."),
                    fieldWithPath("productUpdateRequestDto.region").type(JsonFieldType.STRING).description("Leisure productCreateRequestDto.region"),
                    fieldWithPath("productUpdateRequestDto.location.howToVisit").type(JsonFieldType.STRING).description("Leisure location.howToVisit"),
                    fieldWithPath("productUpdateRequestDto.location.latitude").type(JsonFieldType.NUMBER).description("Leisure location.latitude"),
                    fieldWithPath("productUpdateRequestDto.location.longitude").type(JsonFieldType.NUMBER).description("Leisure location.longitude"),
                    fieldWithPath("productUpdateRequestDto.location.content").type(JsonFieldType.STRING).description("Leisure location.content")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("Leisure Id"),
                    fieldWithPath("data.leisureInformation").description(JsonFieldType.STRING).description("Leisure leisureInformation"),
                    fieldWithPath("data.usecase").description(JsonFieldType.STRING).description("Leisure usecase"),
                    fieldWithPath("data.leisureNotice").description(JsonFieldType.STRING).description("Leisure leisureNotice"),
                    fieldWithPath("data.expirationDate").description(JsonFieldType.STRING).description("Leisure expirationDate"),
                    fieldWithPath("data.leisureCategory").description(JsonFieldType.STRING).description("Leisure leisureCategory"),
                    subsectionWithPath("data.productUpdateResponseDto").description("Leisure productUpdateResponseDto")
                )
            ));
    }

    @Test
    void deleteLeisureByIdTest() throws Exception {
        // given // when // then
        mockMvc.perform(delete("/api/v1/leisure/{leisureId}", leisureId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("LeisureController/deleteLeisureById",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("leisureId").description(JsonFieldType.NUMBER).description("Leisure Id")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("Leisure Id"),
                    fieldWithPath("data.leisureInformation").description(JsonFieldType.STRING).description("Leisure leisureInformation"),
                    fieldWithPath("data.usecase").description(JsonFieldType.STRING).description("Leisure usecase"),
                    fieldWithPath("data.leisureNotice").description(JsonFieldType.STRING).description("Leisure leisureNotice"),
                    fieldWithPath("data.expirationDate").description(JsonFieldType.STRING).description("Leisure expirationDate"),
                    fieldWithPath("data.leisureCategory").description(JsonFieldType.STRING).description("Leisure leisureCategory"),
                    subsectionWithPath("data.productDeleteResponseDto").description("Leisure productDeleteResponseDto")
                )
            ));
    }

}