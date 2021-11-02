package com.prgm.aroundthetown.order.repository;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.order.entity.Order;
import com.prgm.aroundthetown.order.entity.OrderProduct;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class OrderProductRepositoryTest {

    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HostRepository hostRepository;

    private Order savedOrder;
    private Product savedAccommodation;

    @BeforeEach
    void setUp() {
        final Member member = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("add@skfm")
                .build();
        final Member savedMember = memberRepository.save(member);
        final Order order = Order.builder()
                .member(savedMember)
                .build();
        savedOrder = orderRepository.save(order);
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
    @DisplayName("OrderProduct를 save 할 수 있다.")
    @Transactional
    void orderProductSaveTest() {
        final OrderProduct orderProduct = OrderProduct.builder()
                .order(savedOrder)
                .product(savedAccommodation)
                .build();
        orderProductRepository.save(orderProduct);

        assertThat(orderProductRepository.findAll().size(), is(1));
    }

    @Test
    @DisplayName("OrderProduct가 생성되면 order에 add 된다.")
    @Transactional
    void mappingOrderTest() {
        final OrderProduct orderProduct = OrderProduct.builder()
                .order(savedOrder)
                .product(savedAccommodation)
                .build();
        orderProductRepository.save(orderProduct);

        assertThat(orderRepository.findAll().get(0).getOrderProducts().size(), is(1));
    }

    @Test
    @DisplayName("OrderProduct가 생성되면 product에 add 된다.")
    @Transactional
    void mappingProductTest() {
        final OrderProduct orderProduct = OrderProduct.builder()
                .order(savedOrder)
                .product(savedAccommodation)
                .build();
        orderProductRepository.save(orderProduct);

        assertThat(accommodationRepository.findAll().get(0).getOrderProducts().size(), is(1));
    }
}
