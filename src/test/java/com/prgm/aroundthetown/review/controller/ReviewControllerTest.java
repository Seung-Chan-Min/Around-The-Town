package com.prgm.aroundthetown.review.controller;

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
import com.prgm.aroundthetown.review.dto.ReviewCreateRequestDto;
import com.prgm.aroundthetown.review.dto.ReviewUpdateRequestDto;
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.review.entity.ReviewImage;
import com.prgm.aroundthetown.review.repository.ReviewImageRepository;
import com.prgm.aroundthetown.review.repository.ReviewRepository;
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
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Long savedMemberId1;
    private Accommodation savedAccommodation;
    private Accommodation savedAccommodation2;
    private Long savedAccommodationId2;
    private Long savedReviewId1;

    @BeforeEach
    void setUp() {
        final Member member1 = Member.builder()
                .password("1234221")
                .phoneNumber("2341")
                .email("aaa@skfm")
                .build();
        final Member savedMember1 = memberRepository.save(member1);
        savedMemberId1 = savedMember1.getId();

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

        final Accommodation accommodation2 = Accommodation.builder()
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
        savedAccommodation2 = accommodationRepository.save(accommodation);
        savedAccommodationId2 = savedAccommodation.getProductId();

        final Review review1 = Review.builder()
                .content("?????? ???????????????")
                .score(5)
                .accommodation(savedAccommodation)
                .member(savedMember1)
                .build();
        final Review savedReview1 = reviewRepository.save(review1);
        final Long reviewId1 = savedReview1.getReviewId();

        final ReviewImage reviewImage1 = ReviewImage.builder()
                .review(savedReview1)
                .IMAGE_PATH("image/path/1")
                .build();
        final ReviewImage reviewImage2 = ReviewImage.builder()
                .review(savedReview1)
                .IMAGE_PATH("image/path/2")
                .build();
        savedReviewId1 = reviewImageRepository.save(reviewImage2).getId();

        assertThat(reviewRepository.findAll().get(0).getReviewImages().size(), is(2));
    }

    @Test
    @DisplayName("member??? review??? ????????? ??? ??????.")
    @Transactional
    void testCreateReview() throws Exception {
        final ReviewCreateRequestDto req = ReviewCreateRequestDto.builder()
                .content("?????? ??? ?????????")
                .score(4)
                .memberId(savedMemberId1)
                .accommodationId(savedAccommodationId2)
                .reviewImagePaths(List.of("image/path/01"))
                .build();

        mockMvc.perform(post("/api/v1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("ReviewController/create-review",
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("score").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("accommodationId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("reviewImagePaths.[]").type(JsonFieldType.ARRAY).description("?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("?????? Id")
                        )
                ));

        assertThat(reviewRepository.findAll().size(), is(2));
        assertThat(reviewRepository.findAll().get(1).getReviewImages().size(), is(1));
    }

    @Test
    @DisplayName("member??? ?????? review??? ????????? ??? ??????.")
    @Transactional
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/reviews/{reviewId}", reviewRepository.findAll().get(0).getReviewId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("ReviewController/find-review",
                        pathParameters(
                                parameterWithName("reviewId").description("?????? Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.memberResponseDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.memberResponseDto.memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.memberResponseDto.password").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.memberResponseDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.memberResponseDto.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.accommodationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.accommodationDto.guide").type(JsonFieldType.STRING).description("???????????????"),
                                fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.score").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("data.reviewImagePaths.[]").type(JsonFieldType.ARRAY).description("?????? ?????????")
                        )
                ));
    }

    @Test
    @DisplayName("member??? ???????????? ?????? review??? ????????? ??? ??????.")
    @Transactional
    void testFindAllByMember() throws Exception {
        mockMvc.perform(get("/api/v1/reviews")
                        .param("memberId", String.valueOf(savedMemberId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("ReviewController/find-reviews-of-member",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.[]").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                                fieldWithPath("data.[].reviewId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.[].content").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.[].score").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("data.[].accommodationName").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.[].accommodationCategory").type(JsonFieldType.STRING).description("????????????")
                        )
                ));
    }

    @Test
    @DisplayName("member??? review??? ????????? ??? ??????.")
    @Transactional
    void testUpdateReview() throws Exception {
        final ReviewUpdateRequestDto req = ReviewUpdateRequestDto.builder()
                .content("?????? ?????? ??????")
                .score(5)
                .reviewImagePaths(List.of("update/path/1", "update/path/2"))
                .build();

        mockMvc.perform(patch("/api/v1/reviews/{reviewId}", reviewRepository.findAll().get(0).getReviewId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("ReviewController/update-review",
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("score").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("reviewImagePaths.[]").type(JsonFieldType.ARRAY).description("?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.memberResponseDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.memberResponseDto.memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.memberResponseDto.password").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.memberResponseDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.memberResponseDto.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.accommodationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.accommodationDto.guide").type(JsonFieldType.STRING).description("???????????????"),
                                fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.score").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("data.reviewImagePaths.[]").type(JsonFieldType.ARRAY).description("?????? ?????????")
                        )
                ));

        assertThat(reviewRepository.findAll().get(0).getContent(), is("?????? ?????? ??????"));
//        assertThat(reviewRepository.findAll().get(0).getReviewImages().size(), is(2));
        assertThat(reviewRepository.findAll().get(0).getReviewImages().size(), is(4));
    }

    @Test
    @DisplayName("member??? review??? ????????? ??? ??????.")
    @Transactional
    void testDeleteReview() throws Exception {
        assertThat(reviewRepository.findAll().get(0).getIsDeleted(), is(false));

        mockMvc.perform(delete("/api/v1/reviews/{reviewId}", reviewRepository.findAll().get(0).getReviewId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("ReviewController/delete-review",
                        pathParameters(
                                parameterWithName("reviewId").description("?????? Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("StatusCode"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("ServerDatetime"),
                                fieldWithPath("data.memberResponseDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.memberResponseDto.memberId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.memberResponseDto.password").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.memberResponseDto.phoneNumber").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.memberResponseDto.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.accommodationDto").type(JsonFieldType.OBJECT).description("?????? ?????? DTO"),
                                fieldWithPath("data.accommodationDto.accommodationName").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.accommodationDto.accommodationCategory").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.accommodationDto.accommodationNotice").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.accommodationDto.optionNotice").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.accommodationDto.guide").type(JsonFieldType.STRING).description("???????????????"),
                                fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("?????? Id"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.score").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("data.reviewImagePaths.[]").type(JsonFieldType.ARRAY).description("?????? ?????????")
                        )
                ));

        assertThat(reviewRepository.findAll().get(0).getIsDeleted(), is(true));
    }
}