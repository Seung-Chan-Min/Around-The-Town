package com.prgm.aroundthetown.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderProductCreateRequestDto;
import com.prgm.aroundthetown.order.entity.Order;
import com.prgm.aroundthetown.order.entity.OrderProduct;
import com.prgm.aroundthetown.order.repository.OrderProductRepository;
import com.prgm.aroundthetown.order.repository.OrderRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.entity.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Long savedMemberId1;
    private Accommodation savedAccommodation;
    private Long savedAccommodationId;

    @BeforeEach
    void setUp() {
        final Member savedMember1 = memberRepository.save(Member.builder()
                .password("123456")
                .phoneNumber("01012345678")
                .email("g787@naver.com")
                .build());
        savedMemberId1 = savedMember1.getId();

        final Host host1 = Host.builder()
                .hostName("?????????")
                .hostEmail("host@email.com")
                .hostPhoneNumber("01066667788")
                .build();
        final Host savedHost1 = hostRepository.save(host1);

        final Accommodation accommodation = Accommodation.builder()
                .host(savedHost1)
                .refundRule("rule")
                .location(Location.builder().build())
                .phoneNumber("phoneNumber")
                .businessRegistrationNumber("number")
                .businessAddress("address")
                .businessName("businessName")
                .region(Region.SEOUL)
                .accommodationName("accommodationName")
                .accommodationNotice("accommodationNotice")
                .optionNotice("option")
                .guide("guide")
                .accommodationCategory(AccommodationCategory.MOTEL)
                .productType(ProductType.ACCOMMODATION)
                .build();
        savedAccommodation = accommodationRepository.save(accommodation);
        savedAccommodationId = savedAccommodation.getProductId();

        final Order savedOrder = orderRepository.save(
                Order.builder()
                        .member(savedMember1)
                        .build());
        orderProductRepository.save(
                OrderProduct.builder()
                        .order(savedOrder)
                        .product(savedAccommodation)
                        .count(4)
                        .build());
    }

    @Test
    @DisplayName("member??? order??? ????????? ??? ??????.")
    @Transactional
    void testCreateOrder() throws Exception {
        final OrderProductCreateRequestDto orderProductCreateRequestDto1 =
                OrderProductCreateRequestDto.builder()
                        .productId(savedAccommodationId)
                        .count(2)
                        .build();
        final OrderProductCreateRequestDto orderProductCreateRequestDto2 =
                OrderProductCreateRequestDto.builder()
                        .productId(savedAccommodationId)
                        .count(3)
                        .build();

        final OrderCreateRequestDto req = OrderCreateRequestDto.builder()
                .memberId(savedMemberId1)
                .orderProductCreateRequestDtos(
                        List.of(orderProductCreateRequestDto1,
                                orderProductCreateRequestDto2))
                .build();

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("OrderController/create-order",
                        requestFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("orderProductCreateRequestDtos.[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? DTO ??????"),
                                fieldWithPath("orderProductCreateRequestDtos.[].productId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("orderProductCreateRequestDtos.[].count").type(JsonFieldType.NUMBER).description("?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("?????? Id")
                        )
                ));

        assertThat(orderRepository.findAll().size(), is(2));
        assertThat(orderRepository.findAll().get(1).getOrderProducts().get(1).getCount()
                , is(3));
    }

    @Test
    @DisplayName("member??? ?????? order??? ????????? ??? ??????.")
    @Transactional
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/orders/{orderId}", orderRepository.findAll().get(0).getOrderId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("OrderController/find-order",
                        pathParameters(
                                parameterWithName("orderId").description("?????? Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.memberResponseDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.memberResponseDto.memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.memberResponseDto.password").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.memberResponseDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.memberResponseDto.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.orderProductDtos.[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? DTO ??????"),
                                fieldWithPath("data.orderProductDtos.[].count").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("data.orderProductDtos.[].productDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.productId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("?????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.guide").type(JsonFieldType.STRING).description("???????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureCategory").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureInfomation").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.usecase").type(JsonFieldType.STRING).description("usecase").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.expirationDate").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.region").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.refundRule").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.howToVisit").type(JsonFieldType.STRING).description("???????????? ??????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.latitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.longitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.content").type(JsonFieldType.STRING).description("????????? ????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessAddress").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessName").type(JsonFieldType.STRING).description("????????? ???")
                        )
                ));
    }

    @Test
    @DisplayName("member??? ???????????? ?????? order??? ????????? ??? ??????.")
    @Transactional
    void testFindAllByMember() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .param("memberId", String.valueOf(savedMemberId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("OrderController/find-orders-of-member",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.[].orderId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.[].memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.[].orderProductDtos.[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? DTO ??????"),
                                fieldWithPath("data.[].orderProductDtos.[].count").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.productId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("?????????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.guide").type(JsonFieldType.STRING).description("???????????????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.leisureCategory").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.leisureInfomation").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.usecase").type(JsonFieldType.STRING).description("usecase").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.leisureNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.expirationDate").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.region").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.refundRule").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto.howToVisit").type(JsonFieldType.STRING).description("???????????? ??????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto.latitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto.longitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto.content").type(JsonFieldType.STRING).description("????????? ????????????").optional(),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.businessAddress").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.businessName").type(JsonFieldType.STRING).description("????????? ???")
                        )
                ));
    }

    @Test
    @DisplayName("member??? order??? ????????? ??? ??????.")
    @Transactional
    void testDeleteOrder() throws Exception {
        assertThat(orderRepository.findAll().get(0).getIsDeleted(), is(false));

        mockMvc.perform(delete("/api/v1/orders/{orderId}", orderRepository.findAll().get(0).getOrderId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("OrderController/delete-order",
                        pathParameters(
                                parameterWithName("orderId").description("?????? Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.orderId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.orderProductDtos.[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? DTO ??????"),
                                fieldWithPath("data.orderProductDtos.[].count").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("data.orderProductDtos.[].productDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.productId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("?????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.guide").type(JsonFieldType.STRING).description("???????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureCategory").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureInfomation").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.usecase").type(JsonFieldType.STRING).description("usecase").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.expirationDate").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.region").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.refundRule").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.howToVisit").type(JsonFieldType.STRING).description("???????????? ??????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.latitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.longitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.content").type(JsonFieldType.STRING).description("????????? ????????????").optional(),
                                fieldWithPath("data.orderProductDtos.[].productDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessAddress").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessName").type(JsonFieldType.STRING).description("????????? ???")
                        )
                ));

        assertThat(orderRepository.findAll().get(0).getIsDeleted(), is(true));
    }
}