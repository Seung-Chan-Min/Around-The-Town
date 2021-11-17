package com.prgm.aroundthetown.host.controller;

import com.prgm.aroundthetown.common.response.ApiResponse;
import com.prgm.aroundthetown.host.dto.HostCreateRequestDto;
import com.prgm.aroundthetown.host.service.HostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class HostController {

    private final HostService hostService;

    @PostMapping("/hosts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Long>> saveHost(@RequestBody final HostCreateRequestDto hostCreateRequestDto) {
        return new ResponseEntity<>(ApiResponse.created(hostService.createHost(hostCreateRequestDto)), HttpStatus.CREATED);
    }

}
