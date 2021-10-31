package com.prgm.aroundthetown.host.converter;

import com.prgm.aroundthetown.host.dto.HostCreateRequest;
import com.prgm.aroundthetown.host.dto.HostCreateResponse;
import com.prgm.aroundthetown.host.entity.Host;
import org.springframework.stereotype.Component;

@Component
public class HostConverter {

    public Host convertToHost(HostCreateRequest dto) {
        return Host.builder()
            .name(dto.getName())
            .password(dto.getPassword())
            .email(dto.getEmail())
            .phoneNumber(dto.getPhoneNumber())
            .build();
    }

    public HostCreateResponse convertToHostCreateResponse(Host host) {
        return HostCreateResponse.builder()
            .id(host.getId())
            .name(host.getName())
            .email(host.getEmail())
            .phoneNumber(host.getPhoneNumber())
            .build();
    }

}
