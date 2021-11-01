package com.prgm.aroundthetown.member.converter;

import com.prgm.aroundthetown.member.dto.MemberCreateDto;
import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public MemberDto toDto(final Member entity) {
        return MemberDto.builder()
                .id(entity.getId())
                .password(entity.getPassword())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .carts(entity.getCarts())
                .wishLists(entity.getWishLists())
                .reviews(entity.getReviews())
                .orders(entity.getOrders())
                .build();
    }

    public Member toEntity(final MemberCreateDto dto) {
        return Member.builder()
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }
}
