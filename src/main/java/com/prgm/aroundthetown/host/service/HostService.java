package com.prgm.aroundthetown.host.service;

import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.host.converter.HostConverter;
import com.prgm.aroundthetown.host.dto.HostCreateDto;
import com.prgm.aroundthetown.host.dto.HostDto;
import com.prgm.aroundthetown.host.dto.HostUpdateDto;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostService {
    private final HostRepository repository;
    private final HostConverter converter;

    @Transactional
    public Long createHost(final HostCreateDto dto) {
        return repository.save(converter.toEntity(dto)).getId();
    }

    public HostDto findById(final Long hostId) {
        return repository.findById(hostId)
                .map(converter::toDto)
                .orElseThrow(() -> new NotFoundException("Host is not found"));
    }

    @Transactional
    public Long updateHost(final HostUpdateDto dto) {
        final Host entity = repository.findById(dto.getId()).get();
        entity.update(dto.getHostName(), dto.getHostEmail(), dto.getHostPhoneNumber());
        return repository.save(entity).getId();
    }

    @Transactional
    public void deleteHost(final Long hostId) {
        final Host entity = repository.findById(hostId).get();
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
