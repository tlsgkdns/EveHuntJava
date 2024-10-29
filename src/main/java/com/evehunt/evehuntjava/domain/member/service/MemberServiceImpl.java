package com.evehunt.evehuntjava.domain.member.service;

import com.evehunt.evehuntjava.domain.mail.dto.MailRequest;
import com.evehunt.evehuntjava.domain.mail.service.MailService;
import com.evehunt.evehuntjava.domain.member.dto.*;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipantResponse;
import com.evehunt.evehuntjava.domain.participant.service.ParticipantService;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService{
    private final String welcomeTitleMessage;
    private final String withdrawTitleMessage;
    private final MemberEntityService memberService;
    private final ParticipantService participantService;
    private final MailService mailService;

    private final String welcomeContentMessage(String name) {
        return name + "님 가입을 환영합니다!";
    }

    private final String withdrawContentMessage(String name) {
        return name + "님의 탈퇴가 무사히 완료되었습니다. 다시 만날 날을 기대하겠습니다.";
    }

    @Override
    @Transactional
    public MemberResponse registerMember(MemberRegisterRequest request) {
        MemberResponse member = memberService.registerMember(request);
        mailService.addMail(new MailRequest(member.getEmail(), welcomeTitleMessage, welcomeContentMessage(member.getName())));
        return member;
    }

    @Override
    public MemberResponse registerAdmin(MemberRegisterRequest request) {
        Long memberId = memberService.registerMember(request).getMemberId();
        return memberService.addAdminAuthority(memberId);
    }

    @Override
    public MemberSignInResponse signIn(MemberSignInRequest request) {
        return memberService.signIn(request);
    }

    @Override
    public MemberResponse getMember(Long id) {
        return memberService.getMember(id);
    }

    @Override
    public MemberResponse editMember(Long id, MemberEditRequest request) {
        return memberService.editMember(id, request);
    }

    @Override
    public MemberResponse getMember(String email) {
        return memberService.getMember(email);
    }

    @Override
    public Long withdrawMember(Long id) {
        String email = getMember(id).getEmail();
        String name = getMember(id).getName();
        Long ret = memberService.withdrawMember(id);
        mailService.addMail(new MailRequest(email, withdrawTitleMessage, withdrawContentMessage(name)));
        return ret;
    }

    @Override
    public void deleteAllMember() {
        memberService.deleteAllMember();
    }

    @Override
    public PageResponse<ParticipantResponse> getParticipatedEvents(PageRequest request, String email) {
        return participantService.getParticipateHistoryByMember(email, request);
    }

    @Override
    public MemberResponse editPassword(Long id, MemberPasswordEditRequest request) {
        return memberService.editPassword(id, request);
    }

    @Override
    public MemberResponse suspendMember(Long id, int days) {
        return memberService.suspendMember(id, days);
    }

    @Override
    public MemberResponse cancelSuspend(Long id) {
        return memberService.cancelSuspend(id);
    }

    @Override
    public void cancelExpiredSuspend() {
        memberService.cancelExpiredSuspend();
    }
    public MemberServiceImpl(@NotNull MemberEntityService memberService, @NotNull ParticipantService participantService, @NotNull MailService mailService) {
        this.memberService = memberService;
        this.participantService = participantService;
        this.mailService = mailService;
        this.welcomeTitleMessage = "가입을 환영합니다!";
        this.withdrawTitleMessage = "탈퇴가 완료되었습니다.";
    }
}
