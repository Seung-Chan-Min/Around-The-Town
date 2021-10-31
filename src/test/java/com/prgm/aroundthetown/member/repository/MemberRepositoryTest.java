package com.prgm.aroundthetown.member.repository;

import com.prgm.aroundthetown.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("member가 save 될 수 있다.")
    @Transactional
    void memberSaveTest() {
        final Member member = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("@skfm")
                .build();
        memberRepository.save(member);

        assertThat(memberRepository.findAll().size(), is(1));
    }
}