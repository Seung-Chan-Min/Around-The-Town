package com.prgm.aroundthetown.leisure.controller;

import com.prgm.aroundthetown.leisure.service.LeisureService;
import com.prgm.aroundthetown.leisure.service.LeisureServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeisureController {
    private final LeisureService leisureService;

    // Note : service가 추가될 경우를 고려하여, @RequiredArgsConstructor 사용하지 않음
    public LeisureController(LeisureServiceImpl leisureService) {
        this.leisureService = leisureService;
    }


    
}
