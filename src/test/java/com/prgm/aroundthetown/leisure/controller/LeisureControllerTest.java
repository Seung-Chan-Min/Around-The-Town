package com.prgm.aroundthetown.leisure.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.host.service.HostServiceImpl;
import com.prgm.aroundthetown.leisure.repository.LeisureRepository;
import com.prgm.aroundthetown.leisure.service.LeisureServiceImpl;
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
class LeisureControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private LeisureServiceImpl leisureService;
    @Autowired
    private HostServiceImpl hostService;
    @Autowired
    private LeisureRepository leisureRepository;
    @Autowired
    private HostRepository hostRepository;

    @BeforeEach
    void setUp() throws Exception {

    }

    @Test
    void createLeisureTest() throws Exception {

    }

    @Test
    void findLeisureByIdTest() throws Exception {

    }

    @Test
    void findAllLeisureByLeisureCategoryTest() throws Exception {

    }

    @Test
    void findAllLeisureByHostTest() throws Exception {

    }

    @Test
    void updateLeisureTest() throws Exception {

    }


    @Test
    void deleteLeisureByIdTest() throws Exception {

    }

    @AfterEach
    void tearDown() {
        leisureRepository.deleteAll();
        hostRepository.deleteAll();
    }

}