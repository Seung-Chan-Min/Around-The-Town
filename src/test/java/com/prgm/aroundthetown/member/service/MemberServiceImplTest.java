package com.prgm.aroundthetown.member.service;

import com.prgm.aroundthetown.member.dto.MemberCreateDto;
import com.prgm.aroundthetown.member.dto.MemberUpdateDto;
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

    private Long setupMemberId;

    @BeforeEach
    void setUp() {
        final Member member = Member.builder()
                .password("1234")
                .phoneNumber("01012345678")
                .email("seung@naver.com")
                .build();
        setupMemberId = memberRepository.save(member).getId();
    }

    @Test
    @DisplayName("Create를 할 수 있다.")
    @Transactional
    void testCreateMember() {
        // Given
        final MemberCreateDto dto = MemberCreateDto.builder()
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
        assertThat(memberServiceImpl.findById(setupMemberId).getEmail(), is("seung@naver.com"));
    }

    @Test
    @DisplayName("Update를 할 수 있다.")
    @Transactional
    void testUpdateMember() {
        // Given
        final MemberUpdateDto dto = MemberUpdateDto.builder()
                .id(setupMemberId)
                .password("바뀐비밀번호")
                .phoneNumber("01012345678")
                .email("seung@naver.com")
                .build();

        // When
        memberServiceImpl.updateMember(dto);

        // Then
        final Member updatedEntity = memberRepository.findAll().get(0);
        assertThat(updatedEntity.getId(), is(setupMemberId));
        assertThat(updatedEntity.getPassword(), is("바뀐비밀번호"));
    }

    @Test
    @DisplayName("Delete를 할 수 있다.")
    @Transactional
    void testDeleteMember() {
//        assertThat(memberRepository.findById(setupMemberId).get().getIsDeleted(), is(false));
        memberServiceImpl.deleteMember(setupMemberId);
        assertThat(memberRepository.findById(setupMemberId).get().getIsDeleted(), is(true));
    }
}