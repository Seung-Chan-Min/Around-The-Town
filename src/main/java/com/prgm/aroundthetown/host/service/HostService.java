package com.prgm.aroundthetown.host.service;

import com.prgm.aroundthetown.host.dto.HostCreateRequest;
import com.prgm.aroundthetown.host.dto.HostCreateResponse;
import com.prgm.aroundthetown.host.dto.HostDeleteRequest;
import com.prgm.aroundthetown.host.dto.HostDeleteResponse;
import com.prgm.aroundthetown.host.dto.HostFindRequest;
import com.prgm.aroundthetown.host.dto.HostFindResponse;
import com.prgm.aroundthetown.host.dto.HostUpdateRequest;
import com.prgm.aroundthetown.host.dto.HostUpdateResponse;

public interface HostService {
    HostCreateResponse createHost(HostCreateRequest hostCreateRequest);

    HostFindResponse findHostById(HostFindRequest hostFindRequest);

    HostUpdateResponse updateHost(HostUpdateRequest hostUpdateRequest);

    HostDeleteResponse deleteHost(HostDeleteRequest hostDeleteRequest);
}
