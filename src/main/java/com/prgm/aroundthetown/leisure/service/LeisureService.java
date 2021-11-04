package com.prgm.aroundthetown.leisure.service;


import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureDeleteResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureFindAllByCategoryResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureFindAllByHostResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateResponseDto;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import java.util.List;

public interface LeisureService {

    Long create(LeisureCreateRequestDto dto);
    LeisureResponseDto findById(Long id);
    List<LeisureFindAllByCategoryResponseDto> findAllByLeisureCategory(LeisureCategory category);
    List<LeisureFindAllByHostResponseDto> findAllByHost(Long hostId);
    LeisureUpdateResponseDto update(LeisureUpdateRequestDto dto);
    LeisureDeleteResponseDto deleteById(Long id);

}
