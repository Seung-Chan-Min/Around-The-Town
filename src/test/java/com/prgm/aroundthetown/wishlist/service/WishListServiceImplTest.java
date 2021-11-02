package com.prgm.aroundthetown.wishlist.service;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.product.entity.Location;
import com.prgm.aroundthetown.product.entity.Region;
import com.prgm.aroundthetown.wishlist.dto.WishListCreateRequestDto;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import com.prgm.aroundthetown.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class WishListServiceImplTest {

    @Autowired
    private WishListServiceImpl wishListServiceImpl;

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Member savedMember1;
    private Long savedAccommodationId;
    private Long savedWishListId;

    @BeforeEach
    void setUp() {
        final Member member1 = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("@skfm")
                .build();
        savedMember1 = memberRepository.save(member1);
        final Member member2 = Member.builder()
                .password("123456")
                .phoneNumber("01011112222")
                .email("asdf@skfm")
                .build();
        final Member savedMember2 = memberRepository.save(member2);

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
                .build();
        final Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        savedAccommodationId = savedAccommodation.getProductId();

        final WishList wishList = WishList.builder()
                .product(savedAccommodation)
                .member(savedMember2)
                .build();
        savedWishListId = wishListRepository.save(wishList).getWishlistId();
    }

    @Test
    @DisplayName("Create를 할 수 있다.")
    @Transactional
    void testCreateWishList() {
        // Given
        final WishListCreateRequestDto dto = WishListCreateRequestDto.builder()
                .productId(savedAccommodationId)
                .memberId(savedMember1.getId())
                .build();

        // When
        wishListServiceImpl.createWishList(dto);

        // Then
        assertThat(wishListRepository.findAll().size(), is(2));
    }

    @Test
    @DisplayName("FindById를 할 수 있다.")
    @Transactional
    void testFindById() throws Exception {
        assertThat(wishListServiceImpl.findById(savedWishListId).getWishListId(), is(savedWishListId));
    }

    @Test
    @DisplayName("Delete를 할 수 있다.")
    @Transactional
    void testDeleteWishList() {
        assertThat(wishListRepository.getById(savedWishListId).getIsDeleted(), is(false));
        wishListServiceImpl.deleteWishList(savedWishListId);
        assertThat(wishListRepository.getById(savedWishListId).getIsDeleted(), is(true));
    }
}