package com.prgm.aroundthetown.cart.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Member savedMember1;
    private Long savedAccommodationId;
    private Long savedCartId;

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

        final Cart cart = Cart.builder()
                .product(savedAccommodation)
                .member(savedMember2)
                .build();
        savedCartId = cartRepository.save(cart).getCartId();
    }

    @Test
    @DisplayName("Create를 할 수 있다.")
    @Transactional
    void testCreateCart() {
        // Given
        final CartCreateRequestDto dto = CartCreateRequestDto.builder()
                .productId(savedAccommodationId)
                .memberId(savedMember1.getId())
                .build();

        // When
        cartServiceImpl.createCart(dto);

        // Then
        assertThat(cartRepository.findAll().size(), is(2));
    }

    @Test
    @DisplayName("FindById를 할 수 있다.")
    @Transactional
    void testFindById() throws Exception {
        assertThat(cartServiceImpl.findById(savedCartId).getCartId(), is(savedCartId));
    }

    @Test
    @DisplayName("Delete를 할 수 있다.")
    @Transactional
    void testDeleteCart() {
//        assertThat(cartRepository.getById(savedCartId).getIsDeleted(), is(false));
        cartServiceImpl.deleteCart(savedCartId);
        assertThat(cartRepository.getById(savedCartId).getIsDeleted(), is(true));
    }
}