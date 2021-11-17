package com.prgm.aroundthetown.member.converter;

import com.prgm.aroundthetown.member.dto.MemberCreateRequestDto;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.member.dto.MemberFindByEmailResponseDto;
import com.prgm.aroundthetown.member.dto.MemberFindByPhoneNumberResponseDto;
import com.prgm.aroundthetown.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public MemberResponseDto toDto(final Member entity) {
        return MemberResponseDto.builder()
                .memberId(entity.getId())
                .password(entity.getPassword())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .build();
    }

    public MemberFindByEmailResponseDto toFindByEmailResponseDto(final Member entity){
        return MemberFindByEmailResponseDto.builder()
            .memberId(entity.getId())
            .password(entity.getPassword())
            .phoneNumber(entity.getPhoneNumber())
            .email(entity.getEmail())
            .build();
    }

    public MemberFindByPhoneNumberResponseDto toFindByPhoneNumberResponseDto(final Member entity){
        return MemberFindByPhoneNumberResponseDto.builder()
            .memberId(entity.getId())
            .password(entity.getPassword())
            .phoneNumber(entity.getPhoneNumber())
            .email(entity.getEmail())
            .build();
    }

    public Member toEntity(final MemberCreateRequestDto dto) {
        return Member.builder()
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }

}
