package com.prgm.aroundthetown.order.service;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.repository.OrderRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.entity.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
                .productType(ProductType.ACCOMMODATION)
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
                .productType(ProductType.ACCOMMODATION)
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
        final OrderCreateRequestDto orderCreateReq = OrderCreateRequestDto.builder()
                .memberId(savedMemberId)
                .build();

        // When
        final Long orderId = orderService.createOrder(orderCreateReq);

        // Then
        assertAll("createOrder",
                () -> assertThat(orderRepository.findAll().size(), is(1)),
                () -> assertThat(orderRepository.getById(orderId).getOrderId(), is(orderId)),
                () -> assertThat(orderRepository.getById(orderId).getMember().getId(), is(savedMemberId))
        );
    }
}