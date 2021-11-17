package com.prgm.aroundthetown.member.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.member.dto.MemberCreateRequestDto;
import com.prgm.aroundthetown.member.dto.MemberUpdateRequestDto;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.member.service.MemberServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
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

    private Long memberId;

    @BeforeEach
    void setUp() throws Exception {
        MemberCreateRequestDto dto = MemberCreateRequestDto.builder()
            .password("1234")
            .phoneNumber("01073788888")
            .email("jcu011@naver.com")
            .build();

        memberId = memberService.createMember(dto);

    }

    @AfterEach
    void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    void createMemberTest() throws Exception {
        // given
        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
            .password("4321")
            .phoneNumber("01088888888")
            .email("jcu022@naver.com")
            .build();

        // when // then
        mockMvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("MemberController/createMember",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("password").type(JsonFieldType.STRING).description("Member password"),
                    fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("Member phoneNumber"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("Member email")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data").description(JsonFieldType.NUMBER).description("Member Id")
                )
            ));
    }

    @Test
    void findMemberByIdTest() throws Exception {
        // given // when // then
        mockMvc.perform(get("/api/v1/members/{memberId}", memberId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("MemberController/findMemberById",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("memberId").description(JsonFieldType.NUMBER).description("Member Id")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.memberId").description(JsonFieldType.NUMBER).description("Member Id"),
                    fieldWithPath("data.password").description(JsonFieldType.STRING).description("Member Name"),
                    fieldWithPath("data.phoneNumber").description(JsonFieldType.STRING).description("Member Name"),
                    fieldWithPath("data.email").description(JsonFieldType.STRING).description("Member price")
                )
            ));
    }

    @Test
    void findMemberByEmailTest() throws Exception {
        String email = "jcu011@naver.com";
        // given // when // then
        mockMvc.perform(get("/api/v1/members/email/{email}", email)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("MemberController/findMemberByEmail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("email").description(JsonFieldType.STRING).description("Member email")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.memberId").description(JsonFieldType.NUMBER).description("Member Id"),
                    fieldWithPath("data.password").description(JsonFieldType.STRING).description("Member Name"),
                    fieldWithPath("data.phoneNumber").description(JsonFieldType.STRING).description("Member Name"),
                    fieldWithPath("data.email").description(JsonFieldType.STRING).description("Member price")
                )
            ));
    }

    @Test
    void findMemberByPhoneNumberTest() throws Exception {
        String phoneNumber = "01073788888";
        // given // when // then
        mockMvc.perform(get("/api/v1/members/phoneNumber/{phoneNumber}", phoneNumber)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("MemberController/findMemberByPhoneNumber",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("phoneNumber").description(JsonFieldType.STRING).description("Member phoneNumber")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data.memberId").description(JsonFieldType.NUMBER).description("Member Id"),
                    fieldWithPath("data.password").description(JsonFieldType.STRING).description("Member Name"),
                    fieldWithPath("data.phoneNumber").description(JsonFieldType.STRING).description("Member Name"),
                    fieldWithPath("data.email").description(JsonFieldType.STRING).description("Member price")
                )
            ));
    }

    @Test
    void updateMemberTest() throws Exception {
        // given
        MemberUpdateRequestDto request = MemberUpdateRequestDto.builder()
            .password("5678")
            .phoneNumber("0101111111")
            .email("jcu033@naver.com")
            .build();

        // when // then
        mockMvc.perform(put("/api/v1/members/{memberId}", memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("MemberController/updateMember",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("memberId").description(JsonFieldType.NUMBER).description("Member Id")
                ),
                requestFields(
                    fieldWithPath("password").type(JsonFieldType.STRING).description("Member password"),
                    fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("Member phoneNumber"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("Member email")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data").description(JsonFieldType.NUMBER).description("Member Id")
                )
            ));
    }

    @Test
    void deleteMemberTest() throws Exception {
        // given // when // then
        mockMvc.perform(delete("/api/v1/members/{memberId}", memberId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("MemberController/deleteMember",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("memberId").description(JsonFieldType.NUMBER).description("Member Id")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("StatusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("ServerDatetime"),
                    fieldWithPath("data").description(JsonFieldType.NUMBER).description("Member Id")
                )
            ));
    }

}