package com.prgm.aroundthetown.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.cart.repository.CartRepository;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
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
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Long savedMemberId1;
    private Long savedMemberId2;
    private Accommodation savedAccommodation;
    private Long savedAccommodationId;

    @BeforeEach
    void setUp() {
        final Member member1 = Member.builder()
                .password("1234221")
                .phoneNumber("2341")
                .email("aaa@skfm")
                .build();
        final Member savedMember1 = memberRepository.save(member1);
        savedMemberId1 = savedMember1.getId();
        final Member member2 = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("@skfm")
                .build();
        savedMemberId2 = memberRepository.save(member2).getId();

        final Host host = Host.builder()
                .hostName("name")
                .hostEmail("email")
                .hostPhoneNumber("0106666")
                .build();
        final Host savedHost = hostRepository.save(host);

        final Accommodation accommodation = Accommodation.builder()
                .host(savedHost)
                .refundRule("rule")
                .location(Location.builder().build())
                .phoneNumber("phone")
                .businessRegistrationNumber("number")
                .businessAddress("address")
                .businessName("businessName")
                .region(Region.SEOUL)
                .accommodationName("name")
                .accommodationNotice("notice")
                .optionNotice("option")
                .guide("guide")
                .accommodationCategory(AccommodationCategory.MOTEL)
                .productType(ProductType.ACCOMMODATION)
                .build();
        savedAccommodation = accommodationRepository.save(accommodation);
        savedAccommodationId = savedAccommodation.getProductId();

        final Cart cart1 = Cart.builder()
                .product(savedAccommodation)
                .member(savedMember1)
                .count(3)
                .build();
        cartRepository.save(cart1);
        cartRepository.save(Cart.builder()
                .product(savedAccommodation)
                .member(savedMember1)
                .count(2)
                .build());
    }

    @Test
    @DisplayName("member??? cart??? ????????? ??? ??????.")
    @Transactional
    void testCreateCart() throws Exception {
        final CartCreateRequestDto createReq = CartCreateRequestDto.builder()
                .productId(savedAccommodationId)
                .memberId(savedMemberId2)
                .count(2)
                .build();

        mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("CartController/create-cart",
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("?????? Id")
                        )
                ));

        assertThat(cartRepository.findAll().size(), is(3));
        assertThat(cartRepository.findAll().get(1).getProduct().getProductId(), is(savedAccommodationId));
        assertThat(cartRepository.findAll().get(1).getProduct().getBusinessName(), is("businessName"));
        assertThat(cartRepository.findAll().get(1).getProduct().getProductType(), is(ProductType.ACCOMMODATION));
    }

    @Test
    @DisplayName("member??? ?????? cart ????????? ??? ??? ??????.")
    @Transactional
    void testFindById() throws Exception {
        final Long req = cartRepository.findAll().get(0).getCartId();

        mockMvc.perform(get("/api/v1/carts/{cartId}", req)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("CartController/find-cart",
                        pathParameters(
                                parameterWithName("cartId").description("?????? Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.cartId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.memberResponseDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.memberResponseDto.memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.memberResponseDto.password").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.memberResponseDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.memberResponseDto.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.productDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.productDto.productId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.productDto.accommodationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.productDto.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("?????????").optional(),
                                fieldWithPath("data.productDto.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.accommodationDto.guide").type(JsonFieldType.STRING).description("???????????????").optional(),
                                fieldWithPath("data.productDto.leisureDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.productDto.leisureDto.leisureCategory").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.productDto.leisureDto.leisureInfomation").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.productDto.leisureDto.usecase").type(JsonFieldType.STRING).description("usecase").optional(),
                                fieldWithPath("data.productDto.leisureDto.leisureNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.leisureDto.expirationDate").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.region").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.productDto.refundRule").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.productDto.locationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.productDto.locationDto.howToVisit").type(JsonFieldType.STRING).description("???????????? ??????").optional(),
                                fieldWithPath("data.productDto.locationDto.latitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.productDto.locationDto.longitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.productDto.locationDto.content").type(JsonFieldType.STRING).description("????????? ????????????").optional(),
                                fieldWithPath("data.productDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.productDto.businessAddress").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.productDto.businessName").type(JsonFieldType.STRING).description("????????? ???"),
                                fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("member??? ???????????? ?????? cart ????????? ??? ??? ??????.")
    @Transactional
    void testFindAll() throws Exception {
        assertThat(memberRepository.getById(savedMemberId1).getCarts().size(), is(2));
        mockMvc.perform(get("/api/v1/carts")
                        .param("memberId", String.valueOf(savedMemberId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("CartController/find-carts-of-member",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.[]").type(JsonFieldType.ARRAY).description("?????? ??????"),
                                fieldWithPath("data.[].cartId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.[].memberResponseDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.[].memberResponseDto.memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.[].memberResponseDto.password").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.[].memberResponseDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.[].memberResponseDto.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.[].productDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.[].productDto.productId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.[].productDto.accommodationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.[].productDto.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("?????????").optional(),
                                fieldWithPath("data.[].productDto.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].productDto.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].productDto.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].productDto.accommodationDto.guide").type(JsonFieldType.STRING).description("???????????????").optional(),
                                fieldWithPath("data.[].productDto.leisureDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.[].productDto.leisureDto.leisureCategory").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.[].productDto.leisureDto.leisureInfomation").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.[].productDto.leisureDto.usecase").type(JsonFieldType.STRING).description("usecase").optional(),
                                fieldWithPath("data.[].productDto.leisureDto.leisureNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].productDto.leisureDto.expirationDate").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.[].productDto.region").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.[].productDto.refundRule").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.[].productDto.locationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.[].productDto.locationDto.howToVisit").type(JsonFieldType.STRING).description("???????????? ??????").optional(),
                                fieldWithPath("data.[].productDto.locationDto.latitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.[].productDto.locationDto.longitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.[].productDto.locationDto.content").type(JsonFieldType.STRING).description("????????? ????????????").optional(),
                                fieldWithPath("data.[].productDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.[].productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.[].productDto.businessAddress").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.[].productDto.businessName").type(JsonFieldType.STRING).description("????????? ???"),
                                fieldWithPath("data.[].count").type(JsonFieldType.NUMBER).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("member??? cart??? ?????? ?????? ??? ??? ??????.")
    @Transactional
    void testDeleteCart() throws Exception {
        final Long req = cartRepository.findAll().get(0).getCartId();

        mockMvc.perform(delete("/api/v1/carts/{cartId}", req)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("CartController/delete-cart",
                        pathParameters(
                                parameterWithName("cartId").description("?????? Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.cartId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.memberResponseDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.memberResponseDto.memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.memberResponseDto.password").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.memberResponseDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.memberResponseDto.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.productDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.productDto.productId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.productDto.accommodationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.productDto.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("?????????").optional(),
                                fieldWithPath("data.productDto.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.accommodationDto.guide").type(JsonFieldType.STRING).description("???????????????").optional(),
                                fieldWithPath("data.productDto.leisureDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.productDto.leisureDto.leisureCategory").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.productDto.leisureDto.leisureInfomation").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.productDto.leisureDto.usecase").type(JsonFieldType.STRING).description("usecase").optional(),
                                fieldWithPath("data.productDto.leisureDto.leisureNotice").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.leisureDto.expirationDate").type(JsonFieldType.STRING).description("????????????").optional(),
                                fieldWithPath("data.productDto.region").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.productDto.refundRule").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.productDto.locationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.productDto.locationDto.howToVisit").type(JsonFieldType.STRING).description("???????????? ??????").optional(),
                                fieldWithPath("data.productDto.locationDto.latitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.productDto.locationDto.longitude").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("data.productDto.locationDto.content").type(JsonFieldType.STRING).description("????????? ????????????").optional(),
                                fieldWithPath("data.productDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.productDto.businessRegistrationNumber").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.productDto.businessAddress").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.productDto.businessName").type(JsonFieldType.STRING).description("????????? ???"),
                                fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("?????? ??????")
                        )
                ));

        assertThat(cartRepository.findAll().get(0).getIsDeleted(), is(true));
    }
}