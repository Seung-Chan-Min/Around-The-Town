package com.prgm.aroundthetown.member.controller;

import com.prgm.aroundthetown.member.dto.MemberCreateDto;
import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.member.dto.MemberFindByEmailResponseDto;
import com.prgm.aroundthetown.member.dto.MemberFindByPhoneNumberResponseDto;
import com.prgm.aroundthetown.member.dto.MemberUpdateDto;
import com.prgm.aroundthetown.member.service.MemberServiceImpl;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    // Todo : 중복코드 리펙토링
    @ExceptionHandler  // Todo : Test
    private ResponseEntity<String> exceptionHandle(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Todo : 중복코드 리펙토링
    @ExceptionHandler(NotFoundException.class)  // Todo : Test
    private ResponseEntity<String> notFoundHandle(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/members")
    public ResponseEntity<Long> createMember(@RequestBody final MemberCreateDto request) {
        return new ResponseEntity<>(memberService.createMember(request), HttpStatus.CREATED);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberDto> findMemberById(@PathVariable final Long memberId) {
        return new ResponseEntity<>(memberService.findById(memberId), HttpStatus.OK);
    }

    @GetMapping("/members/email/{email}")
    public ResponseEntity<MemberFindByEmailResponseDto> findMemberByEmail(@PathVariable final String email) {
        return new ResponseEntity<>(memberService.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/members/phoneNumber/{phoneNumber}")
    public ResponseEntity<MemberFindByPhoneNumberResponseDto> findMemberByPhoneNumber(@PathVariable final String phoneNumber) {
        return new ResponseEntity<>(memberService.findByPhoneNumber(phoneNumber), HttpStatus.OK);
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity<Long> updateMember(
        @PathVariable final Long memberId,
        @RequestBody final MemberUpdateDto request){
        return new ResponseEntity<>(memberService.updateMember(memberId, request), HttpStatus.OK);
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity deleteMember(@PathVariable final Long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
