package com.prgm.aroundthetown.host.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.host.dto.HostCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs
class HostControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveHost() throws Exception {
        final HostCreateRequestDto hostCreateRequestDto = HostCreateRequestDto.builder()
                .hostName("강민희")
                .hostPhoneNumber("01066669999")
                .hostEmail("kang@naver.com")
                .build();

        mockMvc.perform(post("/api/v1/hosts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hostCreateRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("host-save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("hostName").type(JsonFieldType.STRING).description("사장님이름"),
                                fieldWithPath("hostEmail").type(JsonFieldType.STRING).description("사장님이메일"),
                                fieldWithPath("hostPhoneNumber").type(JsonFieldType.STRING).description("사장님전화번호")
                        )
                ));
    }


}