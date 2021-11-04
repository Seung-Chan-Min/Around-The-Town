package com.prgm.aroundthetown.cart.repository;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.cart.entity.Cart;
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
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
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
                .productType(ProductType.ACCOMMODATION)
                .build();
        savedAccommodation = accommodationRepository.save(accommodation);

    }

    @Test
    @DisplayName("장바구니(cart)가 save 될 수 있다.")
    @Transactional
    void cartSaveTest() {
        final Cart cart = Cart.builder()
                .product(savedAccommodation)
                .member(savedMember)
                .build();
        cartRepository.save(cart);
        assertThat(cartRepository.findAll().size(), is(1));

        // 연관관계 mapping
        assertThat(accommodationRepository.findAll().get(0).getCarts().size(), is(1));
        assertThat(memberRepository.findAll().get(0).getCarts().size(), is(1));
    }

}