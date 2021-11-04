package com.prgm.aroundthetown.leisure.service;

import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.leisure.converter.LeisureConverter;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureDeleteResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateResponseDto;
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
    public Long create(LeisureCreateRequestDto dto) {
        Host host = hostRepository.findById(dto.getProductCreateRequestDto().getHostId())
            .orElseThrow(() -> new NotFoundException("Host is not found."));
        Leisure leisure = leisureConverter.toLeisure(dto, host);
        host.addProduct(leisure);
        return leisureRepository.save(leisure).getProductId();
    }

    @Override
    public LeisureResponseDto findById(Long id) {
        return leisureRepository.findById(id)
            .map(leisureConverter::toFindResponse)
            .orElseThrow(() -> new NotFoundException("Leisure is not found."));
    }

    @Override
    @Transactional
    public LeisureUpdateResponseDto update(LeisureUpdateRequestDto dto) {
        Leisure leisure = leisureRepository.findById(dto.getId())
            .orElseThrow(() -> new NotFoundException("Leisure is not found."));
        return leisureConverter.toUpdateResponse(leisure.update(leisureConverter.toLeisure(dto)));
    }

    @Override
    @Transactional
    public LeisureDeleteResponseDto deleteById(Long id) {
        LeisureDeleteResponseDto leisureDeleteResponseDto = leisureRepository.findById(id)
            .map(leisureConverter::toDeleteResponse)
            .orElseThrow(() -> new NotFoundException("Leisure is not found."));
        leisureRepository.deleteById(id);
        return leisureDeleteResponseDto;
    }
}
