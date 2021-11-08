package com.prgm.aroundthetown.member.service;

import com.prgm.aroundthetown.member.dto.MemberCreateRequestDto;
import com.prgm.aroundthetown.member.dto.MemberUpdateRequestDto;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private MemberServiceImpl memberServiceImpl;
    @Autowired
    private MemberRepository memberRepository;

    private Long savedMemberId;

    @BeforeEach
    void setUp() {
        final Member member = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("seung@naver.com")
                .build();
        savedMemberId = memberRepository.save(member).getId();
    }

    @Test
    @DisplayName("Create를 할 수 있다.")
    @Transactional
    void testCreateMember() {
        // Given
        final MemberCreateRequestDto dto = MemberCreateRequestDto.builder()
                .password("1234")
                .phoneNumber("01011112222")
                .email("test@naver.com")
                .build();

        // When
        memberServiceImpl.createMember(dto);

        // Then
        assertThat(memberRepository.findAll().size(), is(2));
        assertThat(memberRepository.findAll().get(1).getPhoneNumber(), is("01011112222"));
    }

    @Test
    @DisplayName("FindById를 할 수 있다.")
    @Transactional
    void testFindById() {
        assertThat(memberServiceImpl.findById(savedMemberId).getEmail(), is("seung@naver.com"));
    }

    @Test
    @DisplayName("Update를 할 수 있다.")
    @Transactional
    void testUpdateMember() {
        // Given
        final MemberUpdateRequestDto dto = MemberUpdateRequestDto.builder()
                .password("바뀐비밀번호")
                .phoneNumber("01012345678")
                .email("seung@naver.com")
                .build();

        // When
        memberServiceImpl.updateMember(savedMemberId, dto);

        // Then
        final Member updatedEntity = memberRepository.findAll().get(0);
        assertThat(updatedEntity.getId(), is(savedMemberId));
        assertThat(updatedEntity.getPassword(), is("바뀐비밀번호"));
    }

    @Test
    @DisplayName("Delete를 할 수 있다.")
    @Transactional
    void testDeleteMember() {
        assertThat(memberRepository.getById(savedMemberId).getIsDeleted(), is(false));
        memberServiceImpl.deleteMember(savedMemberId);
        assertThat(memberRepository.getById(savedMemberId).getIsDeleted(), is(true));
    }
}