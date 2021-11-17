package com.prgm.aroundthetown.host.service;

import com.prgm.aroundthetown.host.dto.HostCreateRequestDto;
import com.prgm.aroundthetown.host.dto.HostUpdateRequestDto;

public interface HostService {
    Long createHost(final HostCreateRequestDto dto);

    Long updateHost(final HostUpdateRequestDto dto);

    void deleteHost(final Long hostId);

}
