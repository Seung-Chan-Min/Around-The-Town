package com.prgm.aroundthetown.ticket.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.host.service.HostServiceImpl;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequestDto;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.leisure.repository.LeisureRepository;
import com.prgm.aroundthetown.leisure.service.LeisureServiceImpl;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductCreateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketCreateRequestDto;
import com.prgm.aroundthetown.ticket.repository.TicketRepository;
import com.prgm.aroundthetown.ticket.service.TicketServiceImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TicketServiceImpl ticketService;
    @Autowired
    private LeisureServiceImpl leisureService;
    @Autowired
    private HostServiceImpl hostService;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private LeisureRepository leisureRepository;
    @Autowired
    private HostRepository hostRepository;


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
            .leisureInformation("레저 정보")
            .usecase("레저 사용명세")
            .leisureNotice("레저 공지")
            .expirationDate(LocalDateTime.now())
            .leisureCategory(LeisureCategory.AMUSEMENTPARK)
            .productCreateRequestDto(productCreateRequest)
            .build();
        leisureId = leisureService.create(leisureCreateRequest);
    }

    @AfterEach
    void tearDown() {
        //ticketRepository.deleteAll();
        //leisureRepository.deleteAll();
        //hostRepository.deleteAll();
    }

    @Test
    void createTicketTest() throws Exception {
        // given
        Leisure leisure = leisureRepository.findById(leisureId).get();
        TicketCreateRequestDto request = TicketCreateRequestDto.builder()
            .leisureId(leisure.getProductId())
            .ticketName("주간리프트권")
            .price(50000)
            .build();

        // when // then
        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(print());
//            .andDo(document("TicketController/createTicket",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                requestFields(
//                    fieldWithPath("leisureId").type(JsonFieldType.NUMBER).description("ticket: leisureId"),
//                    fieldWithPath("ticketName").type(JsonFieldType.STRING).description("ticket: ticketName"),
//                    fieldWithPath("price").type(JsonFieldType.NUMBER).description("ticket: price")
//                ),
//                responseFields(
//                    fieldWithPath("body").description(JsonFieldType.NUMBER).description("ticket: id")
//                    // fieldWithPath("body.hostId").description(JsonFieldType.NUMBER).description("Leisure: hostId")
//                )
//            ));
    }

    @Test
    void findTicketByIdTest() throws Exception {

    }

    @Test
    void findAllTicketByLeisureTest() throws Exception {

    }

    @Test
    void updateTicketTest() throws Exception {

    }

    @Test
    void deleteTicketByIdTest() throws Exception {

    }

}