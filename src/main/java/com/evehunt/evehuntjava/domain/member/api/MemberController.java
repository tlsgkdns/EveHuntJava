package com.evehunt.evehuntjava.domain.member.api;

import com.evehunt.evehuntjava.domain.member.dto.*;
import com.evehunt.evehuntjava.domain.member.service.MemberService;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipantResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import com.evehunt.evehuntjava.global.exception.exception.InvalidRequestException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping({"/api/members"})
public class MemberController {
    private final MemberService memberService;
    public void checkBindingResult(@NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder messages = new StringBuilder();
            List<String> list = bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            for(int i = 0; i < list.size(); i++)
            {
                messages.append(list.get(i));
                if(i != list.size() - 1)
                    messages.append('\n');
            }
            throw new InvalidRequestException("Event", messages.toString());
        }
    }
    @GetMapping({"/{memberId}"})
    @NotNull
    public ResponseEntity<MemberResponse> getMember(@PathVariable long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(memberId));
    }

    @GetMapping({"/isLogin"})
    @NotNull
    public ResponseEntity<Boolean> isLogin(@AuthenticationPrincipal UserDetails userDetails) {
        boolean body = userDetails != null && !Objects.equals(userDetails.getUsername(), "anonymous");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping
    @NotNull
    public ResponseEntity<MemberResponse> getLoginMember(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails == null) return null;
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(userDetails.getUsername()));
    }

    @GetMapping({"/username/{username}"})
    @NotNull
    public ResponseEntity<MemberResponse> getMember(@PathVariable @NotNull String username) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(username));
    }

    @PostMapping({"/signUp"})
    @NotNull
    public ResponseEntity<MemberResponse> registerMember(@RequestBody @Valid @NotNull MemberRegisterRequest memberRegisterRequest, @NotNull BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(memberRegisterRequest));
    }

    @PostMapping({"/signIn"})
    @NotNull
    public ResponseEntity<MemberSignInResponse> signInMember(@RequestBody @NotNull MemberSignInRequest memberSignInRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.signIn(memberSignInRequest));
    }

    @PatchMapping({"/{memberId}/profile"})
    @NotNull
    public ResponseEntity<MemberResponse> editMember(@PathVariable @Valid long memberId,
                                                     @RequestBody @NotNull MemberEditRequest memberEditRequest, @NotNull BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.editMember(memberId, memberEditRequest));
    }

    @DeleteMapping({"/{memberId}"})
    @NotNull
    public ResponseEntity<Long> withdrawMember(@PathVariable long memberId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(memberService.withdrawMember(memberId));
    }

    @GetMapping({"/events"})
    @NotNull
    public ResponseEntity<PageResponse<ParticipantResponse>> getParticipateHistoryByMember(@AuthenticationPrincipal @NotNull UserDetails user, @NotNull PageRequest pageRequest) {
        System.out.println(user);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getParticipatedEvents(pageRequest, user.getUsername()));
    }

    @PatchMapping({"/{memberId}/password"})
    @NotNull
    public ResponseEntity<MemberResponse> editPassword(@RequestBody @Valid @NotNull
                                                           MemberPasswordEditRequest passwordEditRequest,
                                                       @PathVariable long memberId, @NotNull BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.editPassword(memberId, passwordEditRequest));
    }

    public MemberController(@NotNull MemberService memberService) {
        this.memberService = memberService;
    }
}
