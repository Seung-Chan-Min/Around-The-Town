package com.prgm.aroundthetown.review.repository;

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
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.review.entity.ReviewImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ReviewImageRepositoryTest {

    @Autowired
    private ReviewImageRepository imageRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Review savedReview;

    @BeforeEach
    void setUp() {
        final Host host = Host.builder()
                .hostName("name")
                .hostEmail("email")
                .hostPhoneNumber("0106666")
                .build();
        final Host savedHost = hostRepository.save(host);

        final Member member = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("@skfm")
                .build();
        final Member savedMember = memberRepository.save(member);

        final Accommodation accommodation = Accommodation.builder()
                .host(savedHost)
                .refundRule("rule")
                .location(Location.builder().build())
                .phoneNumber("phone")
                .businessRegistrationNumber("number")
                .businessAddress("address")
                .businessName("namebu")
                .region(Region.SEOUL)
                .accommodationName("name")
                .accommodationNotice("notice")
                .optionNotice("option")
                .guide("guide")
                .accommodationCategory(AccommodationCategory.MOTEL)
                .productType(ProductType.ACCOMMODATION)
                .build();
        final Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        final Review review = Review.builder()
                .content("content")
                .score(4)
                .member(savedMember)
                .accommodation(savedAccommodation)
                .build();
        savedReview = reviewRepository.save(review);
    }

    @Test
    @DisplayName("review image??? save ??? ??? ??????.")
    @Transactional
    void saveReviewTest() {
        final ReviewImage reviewImage = ReviewImage.builder()
                .IMAGE_PATH("path")
                .review(savedReview)
                .build();
        imageRepository.save(reviewImage);

        assertThat(imageRepository.findAll().size(), is(1));

        // ???????????? mapping
        assertThat(reviewRepository.findAll().get(0).getReviewImages().size(), is(1));
    }

}