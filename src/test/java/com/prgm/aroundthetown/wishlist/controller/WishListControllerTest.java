package com.prgm.aroundthetown.wishlist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.entity.ProductType;
import com.prgm.aroundthetown.wishlist.dto.WishListCreateRequestDto;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import com.prgm.aroundthetown.wishlist.repository.WishListRepository;
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
class WishListControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WishListRepository wishListRepository;
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

        final WishList wishList1 = WishList.builder()
                .product(savedAccommodation)
                .member(savedMember1)
                .build();
        wishListRepository.save(wishList1);
    }

    @Test
    @DisplayName("member??? wishList??? ????????? ??? ??????.")
    @Transactional
    void testCreateWishList() throws Exception {
        final WishListCreateRequestDto createReq = WishListCreateRequestDto.builder()
                .productId(savedAccommodationId)
                .memberId(savedMemberId2)
                .build();

        mockMvc.perform(post("/api/v1/wishList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("WishListController/create-wishList",
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("????????? Id")
                        )
                ));

        assertThat(wishListRepository.findAll().size(), is(2));
    }

    @Test
    @DisplayName("member??? ?????? wishList ????????? ??? ??? ??????.")
    @Transactional
    void testFindById() throws Exception {
        final Long req = wishListRepository.findAll().get(0).getWishlistId();

        mockMvc.perform(get("/api/v1/wishLists/{wishListId}", req)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("WishListController/find-wishList",
                        pathParameters(
                                parameterWithName("wishListId").description("????????? Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.wishListId").type(JsonFieldType.NUMBER).description("????????? Id"),
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
                                fieldWithPath("data.productDto.businessName").type(JsonFieldType.STRING).description("????????? ???")
                        )
                ));
    }

    @Test
    @DisplayName("member??? ???????????? ?????? wishList ????????? ??? ??? ??????.")
    @Transactional
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/wishLists")
                        .param("memberId", String.valueOf(savedMemberId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("WishListController/find-wishLists-of-member",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.[]").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                fieldWithPath("data.[].wishListId").type(JsonFieldType.NUMBER).description("????????? Id"),
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
                                fieldWithPath("data.[].productDto.businessName").type(JsonFieldType.STRING).description("????????? ???")
                        )
                ));
    }

    @Test
    @DisplayName("member??? wishList??? ?????? ?????? ??? ??? ??????.")
    @Transactional
    void testDeleteWishList() throws Exception {
        final Long req = wishListRepository.findAll().get(0).getWishlistId();

        mockMvc.perform(delete("/api/v1/wishLists/{wishListId}", req)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("WishListController/delete-wishList",
                        pathParameters(
                                parameterWithName("wishListId").description("????????? Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.wishListId").type(JsonFieldType.NUMBER).description("????????? Id"),
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
                                fieldWithPath("data.productDto.businessName").type(JsonFieldType.STRING).description("????????? ???")
                        )
                ));

        assertThat(wishListRepository.findAll().get(0).getIsDeleted(), is(true));
    }
}