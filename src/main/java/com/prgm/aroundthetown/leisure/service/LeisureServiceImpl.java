package com.prgm.aroundthetown.leisure.service;

import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.leisure.converter.LeisureConverter;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureDeleteByIdResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureFindByIdResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateResponse;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.leisure.repository.LeisureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeisureServiceImpl implements LeisureService{
    private final LeisureRepository leisureRepository;
    private final HostRepository hostRepository;
    private final LeisureConverter leisureConverter;

    @Override
    @Transactional
    public Long create(LeisureCreateRequest dto) {
        Host host = hostRepository.findById(dto.getHostId())
            .orElseThrow(() -> new NotFoundException("Host is not found."));
        Leisure leisure = leisureConverter.toLeisure(dto, host);
        host.addProduct(leisure);
        return leisureRepository.save(leisure).getProductId();
    }

    @Override
    public LeisureFindByIdResponse findById(Long id) {
        return leisureRepository.findById(id)
            .map(leisureConverter::toFindByIdResponse)
            .orElseThrow(() -> new NotFoundException("Leisure is not found."));
    }

    @Override
    @Transactional
    public LeisureUpdateResponse update(LeisureUpdateRequest dto) {
        return null;
    }

    @Override
    @Transactional
    public LeisureDeleteByIdResponse deleteById(Long dto) {
        return null;
    }
}
