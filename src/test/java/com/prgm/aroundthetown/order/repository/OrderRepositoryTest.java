package com.prgm.aroundthetown.order.repository;

import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.order.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        final Member member = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("@skfm")
                .build();
        savedMember = memberRepository.save(member);
    }

    @Test
    @DisplayName("order를 save 할 수 있다.")
    @Transactional
    void orderSaveTest() {
        final Order order = Order.builder()
                .count(10)
                .member(savedMember)
                .build();
        orderRepository.save(order);

        assertThat(orderRepository.findAll().size(), is(1));

        // 연관관계 mapping
        assertThat(memberRepository.findAll().get(0).getOrders().size(), is(1));
    }

}