package com.prgm.aroundthetown.member.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.member.service.MemberServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() throws Exception {

    }

    @Test
    void createMemberTest() throws Exception {

    }

    @Test
    void findMemberByIdTest() throws Exception {

    }

    @Test
    void findMemberByEmailTest() throws Exception {

    }

    @Test
    void findMemberByPhoneNumberTest() throws Exception {

    }

    @Test
    void updateMemberTest() throws Exception {

    }

    @Test
    void deleteMemberTest() throws Exception {

    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

}