package com.prgm.aroundthetown.leisure.service;

import com.prgm.aroundthetown.leisure.converter.LeisureConverter;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureDeleteByIdResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureFindByIdResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateResponse;
import com.prgm.aroundthetown.leisure.repository.LeisureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeisureServiceImpl implements LeisureService{
    private final LeisureConverter leisureConverter;
    private final LeisureRepository leisureRepository;


    @Override
    public LeisureCreateResponse create(LeisureCreateRequest dto) {
        return null;
    }

    @Override
    public LeisureFindByIdResponse findById(Long id) {
        return null;
    }

    @Override
    public LeisureUpdateResponse update(LeisureUpdateRequest dto) {
        return null;
    }

    @Override
    public LeisureDeleteByIdResponse deleteById(Long dto) {
        return null;
    }
}
