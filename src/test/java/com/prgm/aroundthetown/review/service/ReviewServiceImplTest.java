package com.prgm.aroundthetown.review.service;

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
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.review.entity.ReviewImage;
import com.prgm.aroundthetown.review.repository.ReviewImageRepository;
import com.prgm.aroundthetown.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ReviewServiceImplTest {

    @Autowired
    private ReviewServiceImpl reviewService;

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Long savedMemberId;
    private Long savedAccommodationId;

    @BeforeEach
    void setUp() {
        final Member member = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("g787@naver.com")
                .build();
        final Member savedMember = memberRepository.save(member);
        savedMemberId = savedMember.getId();

        final Host host = Host.builder()
                .hostName("?????????")
                .hostEmail("host@gmail.com")
                .hostPhoneNumber("01066668888")
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
        final Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        savedAccommodationId = savedAccommodation.getProductId();

        final Review review1 = reviewRepository.save(Review.builder()
                .member(savedMember)
                .content("?????????")
                .score(5)
                .accommodation(savedAccommodation)
                .build());

        reviewImageRepository.save(ReviewImage.builder()
                .review(review1)
                .IMAGE_PATH("resources/image/test/1")
                .build());
        reviewImageRepository.save(ReviewImage.builder()
                .review(review1)
                .IMAGE_PATH("resources/image/test/2")
                .build());
    }

    @Test
    @DisplayName("review??? ????????? ??? ??????.")
    @Transactional
    void testCreateReview() {
        // Given
        final List<String> imagePaths = new ArrayList<>();
        imagePaths.add("resources/image/path/1");
//        imagePaths.add("resources/image/path/2");
//        imagePaths.add("resources/image/path/3");
        final ReviewCreateRequestDto dto = ReviewCreateRequestDto.builder()
                .content("??? ?????? ??? ???????")
                .score(5)
                .memberId(savedMemberId)
                .accommodationId(savedAccommodationId)
                .reviewImagePaths(imagePaths)
                .build();

        // When
        reviewService.createReview(dto);

        // Then
        assertAll("create_review_test",
                () -> assertThat(reviewRepository.findAll().size(), is(2)),
                () -> assertThat(reviewRepository.findAll().get(1).getContent(), is("??? ?????? ??? ???????"))
        );
    }
}