package com.prgm.aroundthetown.member.service;

import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.dto.MemberCreateRequestDto;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.member.dto.MemberFindByEmailResponseDto;
import com.prgm.aroundthetown.member.dto.MemberFindByPhoneNumberResponseDto;
import com.prgm.aroundthetown.member.dto.MemberUpdateRequestDto;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository repository;
    private final MemberConverter converter;

    @Override
    @Transactional
    public Long createMember(final MemberCreateRequestDto dto) {
        return repository.save(converter.toEntity(dto)).getId();
    }

    @Override
    public MemberResponseDto findById(final Long memberId) {
        return repository.findById(memberId)
            .map(converter::toDto)
            .orElseThrow(() -> new NotFoundException("Member is not found."));
    }

    @Override
    public MemberFindByEmailResponseDto findByEmail(String email) {
        return repository.findByEmail(email)
            .map(converter::toFindByEmailResponseDto)
            .orElseThrow(() -> new NotFoundException("Member is not found."));
    }

    @Override
    public MemberFindByPhoneNumberResponseDto findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber)
            .map(converter::toFindByPhoneNumberResponseDto)
            .orElseThrow(() -> new NotFoundException("Member is not found."));
    }

    @Override
    @Transactional
    public Long updateMember(final Long memberId, final MemberUpdateRequestDto dto) {
        final Member entity = repository.getById(memberId);
        entity.update(dto.getPassword(), dto.getPhoneNumber(), dto.getEmail());
        return memberId;
    }

    @Override
    @Transactional
    public void deleteMember(final Long memberId) {
        final Member entity = repository.getById(memberId);
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
