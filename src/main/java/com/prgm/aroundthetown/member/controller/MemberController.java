package com.prgm.aroundthetown.member.controller;

import com.prgm.aroundthetown.common.response.ApiResponse;
import com.prgm.aroundthetown.member.dto.MemberCreateRequestDto;
import com.prgm.aroundthetown.member.dto.MemberFindByEmailResponseDto;
import com.prgm.aroundthetown.member.dto.MemberFindByPhoneNumberResponseDto;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.member.dto.MemberUpdateRequestDto;
import com.prgm.aroundthetown.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;

    @PostMapping("/members")
    public ResponseEntity<ApiResponse<Long>> createMember(@RequestBody final MemberCreateRequestDto request) {
        return ResponseEntity.ok(ApiResponse.created(memberService.createMember(request)));
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> findMemberById(@PathVariable final Long memberId) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.findById(memberId)));
    }

    @GetMapping("/members/email/{email}")
    public ResponseEntity<ApiResponse<MemberFindByEmailResponseDto>> findMemberByEmail(@PathVariable final String email) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.findByEmail(email)));
    }

    @GetMapping("/members/phoneNumber/{phoneNumber}")
    public ResponseEntity<ApiResponse<MemberFindByPhoneNumberResponseDto>> findMemberByPhoneNumber(@PathVariable final String phoneNumber) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.findByPhoneNumber(phoneNumber)));
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<Long>> updateMember(
        @PathVariable final Long memberId,
        @RequestBody final MemberUpdateRequestDto request){
        return ResponseEntity.ok(ApiResponse.ok(memberService.updateMember(memberId, request)));
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<Long>> deleteMember(@PathVariable final Long memberId) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.deleteMember(memberId)));
    }
}
