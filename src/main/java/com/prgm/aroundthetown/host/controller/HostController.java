package com.prgm.aroundthetown.host.controller;

import com.prgm.aroundthetown.host.dto.HostCreateRequestDto;
import com.prgm.aroundthetown.host.service.HostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class HostController {

    private final HostService hostService;

    @PostMapping("/hosts")
    @ResponseStatus(code= HttpStatus.CREATED)
    public Long saveHost(@RequestBody HostCreateRequestDto hostCreateRequestDto){
        return hostService.createHost(hostCreateRequestDto);
    }

    @GetMapping("/hosts/{hostId}")
    public void getAccommodations(@PathVariable Long hostId){

    }
}
