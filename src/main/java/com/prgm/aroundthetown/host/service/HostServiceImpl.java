package com.prgm.aroundthetown.host.service;

import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.host.converter.HostConverter;
import com.prgm.aroundthetown.host.dto.HostCreateRequestDto;
import com.prgm.aroundthetown.host.dto.HostRequestDto;
import com.prgm.aroundthetown.host.dto.HostUpdateRequestDto;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostServiceImpl implements HostService{

    private final HostRepository repository;

    private final HostConverter converter;

    @Transactional
    @Override
    public Long createHost(final HostCreateRequestDto dto) {
        return repository.save(converter.toEntity(dto)).getId();
    }

    public HostRequestDto findById(final Long hostId) {
        return repository.findById(hostId)
                .map(converter::toDto)
                .orElseThrow(() -> new NotFoundException("Host is not found"));
    }

    @Transactional
    @Override
    public Long updateHost(final HostUpdateRequestDto dto) {
        final Host entity = repository.findById(dto.getId()).get();
        entity.update(dto.getHostName(), dto.getHostEmail(), dto.getHostPhoneNumber());
        return repository.save(entity).getId();
    }

    @Transactional
    @Override
    public void deleteHost(final Long hostId) {
        final Host entity = repository.findById(hostId).get();
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
