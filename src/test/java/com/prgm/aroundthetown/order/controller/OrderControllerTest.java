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
                .hostName("최승은")
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
    @DisplayName("member가 order를 생성할 수 있다.")
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
                .andDo(document("create_order",
                        requestFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 Id"),
                                fieldWithPath("orderProductCreateRequestDtos.[]").type(JsonFieldType.ARRAY).description("주문 상품 생성 DTO 목록"),
                                fieldWithPath("orderProductCreateRequestDtos.[].productId").type(JsonFieldType.NUMBER).description("상품 Id"),
                                fieldWithPath("orderProductCreateRequestDtos.[].count").type(JsonFieldType.NUMBER).description("상품 갯수")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("주문 Id")
                        )
                ));

        assertThat(orderRepository.findAll().size(), is(2));
        assertThat(orderRepository.findAll().get(1).getOrderProducts().get(1).getCount()
                , is(3));
    }

    @Test
    @DisplayName("member가 단일 order를 조회할 수 있다.")
    @Transactional
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/orders/{orderId}", orderRepository.findAll().get(0).getOrderId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find_order",
                        pathParameters(
                                parameterWithName("orderId").description("주문 Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.memberResponseDto").type(JsonFieldType.OBJECT).description("멤버 정보 DTO"),
                                fieldWithPath("data.memberResponseDto.memberId").type(JsonFieldType.NUMBER).description("회원 Id"),
                                fieldWithPath("data.memberResponseDto.password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("data.memberResponseDto.phoneNumber").type(JsonFieldType.STRING).description("회원 전화번호"),
                                fieldWithPath("data.memberResponseDto.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("data.orderProductDtos.[]").type(JsonFieldType.ARRAY).description("주문 상품 정보 DTO 목록"),
                                fieldWithPath("data.orderProductDtos.[].count").type(JsonFieldType.NUMBER).description("주문 갯수"),
                                fieldWithPath("data.orderProductDtos.[].productDto").type(JsonFieldType.OBJECT).description("상품 정보 DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.productId").type(JsonFieldType.NUMBER).description("상품 Id"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto").type(JsonFieldType.OBJECT).description("숙소 정보 DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("숙소명"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("숙소종류"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("안내사항"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("숙소옵션"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.guide").type(JsonFieldType.STRING).description("숙소가이드"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto").type(JsonFieldType.OBJECT).description("레저 정보 DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureCategory").description("레저 카테고리"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureInfomation").description("레저 정보"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.usecase").description("usecase"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureNotice").description("안내사항"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.expirationDate").description("유효기한"),
                                fieldWithPath("data.orderProductDtos.[].productDto.region").type(JsonFieldType.STRING).description("지역"),
                                fieldWithPath("data.orderProductDtos.[].productDto.refundRule").type(JsonFieldType.STRING).description("환불규정"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto").type(JsonFieldType.OBJECT).description("위치 정보 DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.howToVisit").description("오시는길 정보"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.latitude").description("위도"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.longitude").description("경도"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.content").description("교통편 상세정보"),
                                fieldWithPath("data.orderProductDtos.[].productDto.phoneNumber").type(JsonFieldType.STRING).description("회사 전화번호"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("사업자 번호"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessAddress").type(JsonFieldType.STRING).description("사업자 주소"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessName").type(JsonFieldType.STRING).description("사업자 명")
                        )
                ));
    }

    @Test
    @DisplayName("member에 해당하는 모든 order를 조회할 수 있다.")
    @Transactional
    void testFindAllByMember() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .param("memberId", String.valueOf(savedMemberId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find_orders",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.[].orderId").type(JsonFieldType.NUMBER).description("주문 Id"),
                                fieldWithPath("data.[].memberId").type(JsonFieldType.NUMBER).description("회원 Id"),
                                fieldWithPath("data.[].orderProductDtos.[]").type(JsonFieldType.ARRAY).description("주문 상품 정보 DTO 목록"),
                                fieldWithPath("data.[].orderProductDtos.[].count").type(JsonFieldType.NUMBER).description("주문 갯수"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto").type(JsonFieldType.OBJECT).description("상품 정보 DTO"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.productId").type(JsonFieldType.NUMBER).description("상품 Id"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto").type(JsonFieldType.OBJECT).description("숙소 정보 DTO"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("숙소명"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("숙소종류"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("안내사항"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("숙소옵션"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.accommodationDto.guide").type(JsonFieldType.STRING).description("숙소가이드"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto").type(JsonFieldType.OBJECT).description("레저 정보 DTO"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.leisureCategory").description("레저 카테고리"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.leisureInfomation").description("레저 정보"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.usecase").description("usecase"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.leisureNotice").description("안내사항"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.leisureDto.expirationDate").description("유효기한"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.region").type(JsonFieldType.STRING).description("지역"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.refundRule").type(JsonFieldType.STRING).description("환불규정"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto").type(JsonFieldType.OBJECT).description("위치 정보 DTO"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto.howToVisit").description("오시는길 정보"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto.latitude").description("위도"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto.longitude").description("경도"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.locationDto.content").description("교통편 상세정보"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.phoneNumber").type(JsonFieldType.STRING).description("회사 전화번호"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("사업자 번호"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.businessAddress").type(JsonFieldType.STRING).description("사업자 주소"),
                                fieldWithPath("data.[].orderProductDtos.[].productDto.businessName").type(JsonFieldType.STRING).description("사업자 명")
                        )
                ));
    }

    @Test
    @DisplayName("member가 order를 삭제할 수 있다.")
    @Transactional
    void testDeleteOrder() throws Exception {
        assertThat(orderRepository.findAll().get(0).getIsDeleted(), is(false));

        mockMvc.perform(delete("/api/v1/orders/{orderId}", orderRepository.findAll().get(0).getOrderId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delete_orders",
                        pathParameters(
                                parameterWithName("orderId").description("주문 Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.orderId").type(JsonFieldType.NUMBER).description("주문 Id"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 Id"),
                                fieldWithPath("data.orderProductDtos.[]").type(JsonFieldType.ARRAY).description("주문 상품 정보 DTO 목록"),
                                fieldWithPath("data.orderProductDtos.[].count").type(JsonFieldType.NUMBER).description("주문 갯수"),
                                fieldWithPath("data.orderProductDtos.[].productDto").type(JsonFieldType.OBJECT).description("상품 정보 DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.productId").type(JsonFieldType.NUMBER).description("상품 Id"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto").type(JsonFieldType.OBJECT).description("숙소 정보 DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("숙소명"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("숙소종류"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("안내사항"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("숙소옵션"),
                                fieldWithPath("data.orderProductDtos.[].productDto.accommodationDto.guide").type(JsonFieldType.STRING).description("숙소가이드"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto").type(JsonFieldType.OBJECT).description("레저 정보 DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureCategory").description("레저 카테고리"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureInfomation").description("레저 정보"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.usecase").description("usecase"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.leisureNotice").description("안내사항"),
                                fieldWithPath("data.orderProductDtos.[].productDto.leisureDto.expirationDate").description("유효기한"),
                                fieldWithPath("data.orderProductDtos.[].productDto.region").type(JsonFieldType.STRING).description("지역"),
                                fieldWithPath("data.orderProductDtos.[].productDto.refundRule").type(JsonFieldType.STRING).description("환불규정"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto").type(JsonFieldType.OBJECT).description("위치 정보 DTO"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.howToVisit").description("오시는길 정보"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.latitude").description("위도"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.longitude").description("경도"),
                                fieldWithPath("data.orderProductDtos.[].productDto.locationDto.content").description("교통편 상세정보"),
                                fieldWithPath("data.orderProductDtos.[].productDto.phoneNumber").type(JsonFieldType.STRING).description("회사 전화번호"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("사업자 번호"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessAddress").type(JsonFieldType.STRING).description("사업자 주소"),
                                fieldWithPath("data.orderProductDtos.[].productDto.businessName").type(JsonFieldType.STRING).description("사업자 명")
                        )
                ));

        assertThat(orderRepository.findAll().get(0).getIsDeleted(), is(true));
    }
}