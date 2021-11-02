package com.prgm.aroundthetown.host.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.host.dto.HostCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class HostControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveHost() throws Exception {
        HostCreateRequestDto hostCreateRequestDto = HostCreateRequestDto.builder()
                .hostName("강민희")
                .hostPhoneNumber("01066669999")
                .hostEmail("kang@naver.com")
                .build();

        mockMvc.perform(post("/api/v1/hosts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hostCreateRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }


}