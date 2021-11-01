package com.prgm.aroundthetown.host.converter;

import com.prgm.aroundthetown.host.dto.HostCreateDto;
import com.prgm.aroundthetown.host.dto.HostDto;
import com.prgm.aroundthetown.host.entity.Host;
import org.springframework.stereotype.Component;

@Component
public class HostConverter {
    public HostDto toDto(final Host entity) {
        return HostDto.builder()
                .hostId(entity.getId())
                .hostName(entity.getHostName())
                .hostEmail(entity.getHostEmail())
                .hostPhoneNumber(entity.getHostPhoneNumber())
                .build();
    }

    public Host toEntity(final HostCreateDto dto) {
        return Host.builder()
                .hostName(dto.getHostName())
                .hostEmail(dto.getHostEmail())
                .hostPhoneNumber(dto.getHostPhoneNumber())
                .build();
    }
}
