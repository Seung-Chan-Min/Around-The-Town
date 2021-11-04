package com.prgm.aroundthetown.leisure.service;


import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureDeleteResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateResponseDto;

public interface LeisureService {

    Long create(LeisureCreateRequestDto dto);
    LeisureResponseDto findById(Long id);
    LeisureUpdateResponseDto update(LeisureUpdateRequestDto dto);
    LeisureDeleteResponseDto deleteById(Long id);

}
