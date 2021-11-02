package com.prgm.aroundthetown.order.service;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderProductCreateRequestDto;
import com.prgm.aroundthetown.order.repository.OrderRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
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
class OrderServiceImplTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private Long savedMemberId;
    private Long savedAccommodationId1;
    private Long savedAccommodationId2;

    @BeforeEach
    void setUp() {
        final Member member1 = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("g787@naver.com")
                .build();
        savedMemberId = memberRepository.save(member1).getId();

        final Host host = Host.builder()
                .hostName("name")
                .hostEmail("email")
                .hostPhoneNumber("0106666")
                .build();
        final Host savedHost = hostRepository.save(host);

        final Accommodation accommodation1 = Accommodation.builder()
                .host(savedHost)
                .refundRule("ru1le")
                .location(Location.builder().build())
                .phoneNumber("phone1")
                .businessRegistrationNumber("number1")
                .businessAddress("address1")
                .businessName("businessName1")
                .region(Region.SEOUL)
                .accommodationName("name1")
                .accommodationNotice("notice1")
                .optionNotice("option1")
                .guide("guide1")
                .accommodationCategory(AccommodationCategory.MOTEL)
                .build();
        final Accommodation accommodation2 = Accommodation.builder()
                .host(savedHost)
                .refundRule("rule2")
                .location(Location.builder().build())
                .phoneNumber("phone2")
                .businessRegistrationNumber("number2")
                .businessAddress("address2")
                .businessName("businessName2")
                .region(Region.JAEJU)
                .accommodationName("name2")
                .accommodationNotice("notice2")
                .optionNotice("option2")
                .guide("guide2")
                .accommodationCategory(AccommodationCategory.HOTEL)
                .build();
        final Accommodation savedAccommodation1 = accommodationRepository.save(accommodation1);
        savedAccommodationId1 = savedAccommodation1.getProductId();
        final Accommodation savedAccommodation2 = accommodationRepository.save(accommodation2);
        savedAccommodationId2 = savedAccommodation2.getProductId();
    }

    @Test
    @DisplayName("Create를 할 수 있다.")
    @Transactional
    void testCreateOrder() {
        // Given
        final OrderProductCreateRequestDto orderProductReq1 = OrderProductCreateRequestDto.builder()
                .productId(savedAccommodationId1)
                .count(3)
                .build();
        final OrderProductCreateRequestDto orderProductReq2 = OrderProductCreateRequestDto.builder()
                .productId(savedAccommodationId2)
                .count(4)
                .build();
        final List<OrderProductCreateRequestDto> orderProductReqs = new ArrayList<>();
        orderProductReqs.add(orderProductReq1);
        orderProductReqs.add(orderProductReq2);

        final OrderCreateRequestDto orderCreateReq = OrderCreateRequestDto.builder()
                .memberId(savedMemberId)
                .orderProductCreateRequestDtos(orderProductReqs)
                .build();

        // When
        final Long orderId = orderService.createOrder(orderCreateReq);

        // Then
        assertAll("createOrder",
                () -> assertThat(orderRepository.findAll().size(), is(1)),
                () -> assertThat(orderRepository.getById(orderId).getOrderId(), is(orderId)),
                () -> assertThat(orderRepository.getById(orderId).getMember().getId(), is(savedMemberId)),
                () -> assertThat(orderRepository.getById(orderId).getOrderProducts().size(), is(2)),
                () -> assertThat(orderRepository.getById(orderId).getOrderProducts().get(0).getProduct().getProductId(), is(savedAccommodationId1)),
                () -> assertThat(orderRepository.getById(orderId).getOrderProducts().get(1).getProduct().getProductId(), is(savedAccommodationId2)),
                () -> assertThat(orderRepository.getById(orderId).getOrderProducts().get(1).getOrder().getOrderId(), is(orderId))
        );
    }
}