package com.prgm.aroundthetown.ticket.controller;

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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequestDto;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.leisure.repository.LeisureRepository;
import com.prgm.aroundthetown.leisure.service.LeisureServiceImpl;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductCreateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketCreateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateRequestDto;
import com.prgm.aroundthetown.ticket.repository.TicketRepository;
import com.prgm.aroundthetown.ticket.service.TicketServiceImpl;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class TicketControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private TicketServiceImpl ticketService;
    @Autowired private LeisureServiceImpl leisureService;
    @Autowired private TicketRepository ticketRepository;
    @Autowired private LeisureRepository leisureRepository;
    @Autowired private HostRepository hostRepository;

    private Long ticketId;
    private Long leisureId;

    @BeforeEach
    void setUp() throws Exception {
        Host host = Host.builder()
            .hostName("전찬의")
            .hostEmail("jcu011@naver.com")
            .hostPhoneNumber("01073788888")
            .build();
        Long hostId = hostRepository.save(host).getId();

        LocationDto location = LocationDto.builder()
            .howToVisit("길찾기")
            .latitude(15.0)
            .longitude(15.0)
            .content("Location")
            .build();
        ProductCreateRequestDto productCreateRequest = ProductCreateRequestDto.builder()
            .hostId(hostId)
            .refundRule("환불 규정")
            .phoneNumber("전화번호")
            .businessRegistrationNumber("사업자 등록번호")
            .businessAddress("사업자 주소")
            .businessName("상호명")
            .region(Region.SEOUL)
            .location(location)
            .build();
        LeisureCreateRequestDto leisureCreateRequest = LeisureCreateRequestDto.builder()
            .leisureInformation("스키장")
            .usecase("주간,야간 리프트권")
            .leisureNotice("12:00 ~ 13:00 점심시간")
            .expirationDate(LocalDateTime.now())
            .leisureCategory(LeisureCategory.AMUSEMENTPARK)
            .productCreateRequestDto(productCreateRequest)
            .build();
        leisureId = leisureService.create(leisureCreateRequest);

        TicketCreateRequestDto ticketCreateRequestDto = TicketCreateRequestDto.builder()
            .leisureId(leisureId)
            .ticketName("주간리프트권")
            .price(70000)
            .build();
        ticketId = ticketService.create(ticketCreateRequestDto);
    }

    @AfterEach
    void tearDown() {
        ticketRepository.deleteAll();
        leisureRepository.deleteAll();
        hostRepository.deleteAll();
    }

    @Test
    void createTicketTest() throws Exception {
        // given
        TicketCreateRequestDto request = TicketCreateRequestDto.builder()
            .leisureId(leisureId)
            .ticketName("야간리프트권")
            .price(50000)
            .build();

        // when // then
        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("TicketController/createTicket",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("leisureId").type(JsonFieldType.NUMBER).description("Leisure Id"),
                    fieldWithPath("ticketName").type(JsonFieldType.STRING).description("Ticket Name"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER).description("ticket price")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data").description(JsonFieldType.NUMBER).description("Ticket Id")
                )
            ));
    }

    @Test
    void findTicketByIdTest() throws Exception {
        // given // when // then
        mockMvc.perform(get("/api/v1/tickets/{ticketId}", ticketId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("TicketController/findTicketById",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("ticketId").description(JsonFieldType.NUMBER).description("Ticket Id")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("Ticket Id"),
                    fieldWithPath("data.ticketName").description(JsonFieldType.STRING).description("Ticket Name"),
                    fieldWithPath("data.price").description(JsonFieldType.NUMBER).description("Ticket price")
                )
            ));
    }

    @Test
    void findAllTicketByLeisureTest() throws Exception {
        // given
        TicketCreateRequestDto request = TicketCreateRequestDto.builder()
            .leisureId(leisureId)
            .ticketName("야간리프트권")
            .price(50000)
            .build();
        ticketService.create(request);

        // when // then
        mockMvc.perform(get("/api/v1/tickets/leisure/{leisureId}", leisureId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("TicketController/findAllTicketByLeisure",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("leisureId").description(JsonFieldType.NUMBER).description("Leisure Id")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.[]").description(JsonFieldType.NUMBER).description("Tickets"),
                    fieldWithPath("data.[].id").description(JsonFieldType.NUMBER).description("Ticket Id"),
                    fieldWithPath("data.[].ticketName").description(JsonFieldType.STRING).description("Ticket Name"),
                    fieldWithPath("data.[].price").description(JsonFieldType.NUMBER).description("Ticket price")
                )
            ));
    }

    @Test
    void updateTicketTest() throws Exception {
        // given
        TicketUpdateRequestDto request = TicketUpdateRequestDto.builder()
            .id(ticketId)
            .ticketName("통합리프트권")
            .price(95000)
            .build();

        // when // then
        mockMvc.perform(put("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("TicketController/updateTicket",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("Ticket Id"),
                    fieldWithPath("ticketName").type(JsonFieldType.STRING).description("Ticket Name"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER).description("Ticket price")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("Ticket Id"),
                    fieldWithPath("data.ticketName").description(JsonFieldType.NUMBER).description("Ticket Name"),
                    fieldWithPath("data.price").description(JsonFieldType.NUMBER).description("Ticket Price")
                )
            ));
    }

    @Test
    void deleteTicketByIdTest() throws Exception {
        // given // when // then
        mockMvc.perform(delete("/api/v1/tickets/{ticketId}", ticketId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("TicketController/deleteTicketById",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("ticketId").description(JsonFieldType.NUMBER).description("Ticket Id")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("Ticket Id"),
                    fieldWithPath("data.ticketName").description(JsonFieldType.STRING).description("Ticket Name"),
                    fieldWithPath("data.price").description(JsonFieldType.NUMBER).description("Ticket price")
                )
            ));
    }

}