package com.prgm.aroundthetown.member.service;

import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.dto.MemberCreateDto;
import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.member.dto.MemberUpdateDto;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final MemberConverter converter;

    @Transactional
    public Long createMember(final MemberCreateDto dto) {
        return repository.save(converter.toEntity(dto)).getId();
    }

    @Transactional(readOnly = true)
    public MemberDto findById(final Long memberId) {
        return repository.findById(memberId)
                .map(converter::toDto)
                .orElseThrow(() -> new NotFoundException("Member is not found"));
    }

    @Transactional
    public Long updateMember(final MemberUpdateDto dto) {
        final Member entity = repository.findById(dto.getId()).get();
        entity.update(dto.getPassword(), dto.getPhoneNumber(), dto.getEmail());
        return repository.save(entity).getId();
    }

    @Transactional
    public void deleteMember(final Long memberId) {
        final Member entity = repository.findById(memberId).get();
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
