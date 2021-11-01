package com.prgm.aroundthetown.wishlist.repository;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.product.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.product.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Accommodation savedAccommodation;
    private Member savedMember;

    @BeforeEach
    void setUp() {
        final Member member = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("@skfm")
                .build();
        savedMember = memberRepository.save(member);

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
                .businessName("namebu")
                .region(Region.SEOUL)
                .accommodationName("name")
                .accommodationNotice("notice")
                .optionNotice("option")
                .guide("guide")
                .accommodationCategory(AccommodationCategory.MOTEL)
                .build();
        savedAccommodation = accommodationRepository.save(accommodation);

    }

    @Test
    @DisplayName("찜목록(wishList)이 save 될 수 있다.")
    @Transactional
    void wishListSaveTest() {
        final WishList wishList = WishList.builder()
                .product(savedAccommodation)
                .member(savedMember)
                .build();
        wishListRepository.save(wishList);
        assertThat(wishListRepository.findAll().size(), is(1));

        // 연관관계 mapping
        assertThat(accommodationRepository.findAll().get(0).getWishLists().size(), is(1));
        assertThat(memberRepository.findAll().get(0).getWishLists().size(), is(1));
    }


}