package com.prgm.aroundthetown.host.service;

import com.prgm.aroundthetown.host.converter.HostConverter;
import com.prgm.aroundthetown.host.dto.HostCreateRequest;
import com.prgm.aroundthetown.host.dto.HostCreateResponse;
import com.prgm.aroundthetown.host.dto.HostDeleteRequest;
import com.prgm.aroundthetown.host.dto.HostDeleteResponse;
import com.prgm.aroundthetown.host.dto.HostFindRequest;
import com.prgm.aroundthetown.host.dto.HostFindResponse;
import com.prgm.aroundthetown.host.dto.HostUpdateRequest;
import com.prgm.aroundthetown.host.dto.HostUpdateResponse;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostServiceImpl implements HostService{
    private final HostRepository hostRepository;
    private final HostConverter hostConverter;

    @Override
    @Transactional
    public HostCreateResponse createHost(HostCreateRequest dto) {
        Host host = hostRepository.save(hostConverter.convertToHost(dto));
        return hostConverter.convertToHostCreateResponse(host);
    }

    @Override
    public HostFindResponse findHostById(HostFindRequest dto) {
        return null;
    }

    @Override
    @Transactional
    public HostUpdateResponse updateHost(HostUpdateRequest dto) {
        return null;
    }

    @Override
    @Transactional
    public HostDeleteResponse deleteHost(HostDeleteRequest dto) {
        return null;
    }
}
