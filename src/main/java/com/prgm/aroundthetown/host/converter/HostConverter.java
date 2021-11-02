package com.prgm.aroundthetown.host.converter;

import com.prgm.aroundthetown.host.dto.HostCreateRequestDto;
import com.prgm.aroundthetown.host.dto.HostRequestDto;
import com.prgm.aroundthetown.host.entity.Host;
import org.springframework.stereotype.Component;

@Component
public class HostConverter {
    public HostRequestDto toDto(final Host entity) {
        return HostRequestDto.builder()
                .hostName(entity.getHostName())
                .hostEmail(entity.getHostEmail())
                .hostPhoneNumber(entity.getHostPhoneNumber())
                .build();
    }

    public Host toEntity(final HostCreateRequestDto dto) {
        return Host.builder()
                .hostName(dto.getHostName())
                .hostEmail(dto.getHostEmail())
                .hostPhoneNumber(dto.getHostPhoneNumber())
                .build();
    }
}
