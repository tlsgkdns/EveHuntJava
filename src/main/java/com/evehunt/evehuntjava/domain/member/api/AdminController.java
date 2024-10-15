package com.evehunt.evehuntjava.domain.member.api;

import com.evehunt.evehuntjava.domain.member.dto.MemberRegisterRequest;
import com.evehunt.evehuntjava.domain.member.dto.MemberResponse;
import com.evehunt.evehuntjava.domain.member.service.MemberService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/admin")
public class AdminController {
    private final MemberService memberService;

    @PostMapping()
    @NotNull
    public ResponseEntity<MemberResponse> registerAdmin(@RequestBody @NotNull MemberRegisterRequest memberRegisterRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(memberRegisterRequest));
    }

    public AdminController(@NotNull MemberService memberService) {
        this.memberService = memberService;
    }
}
