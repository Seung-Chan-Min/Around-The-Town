package com.prgm.aroundthetown.leisure.service;


import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureDeleteByIdResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureFindByIdResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateResponse;

public interface LeisureService {

    LeisureCreateResponse create(LeisureCreateRequest dto);
    LeisureFindByIdResponse findById(Long id);
    LeisureUpdateResponse update(LeisureUpdateRequest dto);
    LeisureDeleteByIdResponse deleteById(Long id);

}
