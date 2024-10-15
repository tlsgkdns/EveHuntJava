package com.evehunt.evehuntjava.domain.member.service;

import com.evehunt.evehuntjava.domain.image.model.Image;
import com.evehunt.evehuntjava.domain.member.dto.*;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.domain.member.model.MemberType;
import com.evehunt.evehuntjava.domain.member.repository.MemberRepository;
import com.evehunt.evehuntjava.global.exception.exception.AlreadyExistModelException;
import com.evehunt.evehuntjava.global.exception.exception.ModelNotFoundException;
import com.evehunt.evehuntjava.global.exception.exception.UnMatchedValueException;
import com.evehunt.evehuntjava.global.infra.security.TokenProvider;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MemberEntityServiceImpl implements MemberEntityService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;

    private Member getExistMember(Long id)
    {
        return memberRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("Member", String.valueOf(id)));
    }
    private Member getExistMember(String email)
    {
        Member member = memberRepository.findMemberByEmail(email);
        if(member == null) throw new ModelNotFoundException("Member", email);
        return member;
    }

    @Override
    @Transactional
    public MemberResponse registerMember(MemberRegisterRequest memberRegisterRequest) {
        if(memberRepository.existsByEmail(memberRegisterRequest.getEmail()))
            throw new AlreadyExistModelException(memberRegisterRequest.getEmail());
        Member member = memberRegisterRequest.to(encoder);
        memberRepository.save(member); memberRepository.flush();
        return MemberResponse.from(member);
    }

    @Override
    @Transactional
    public MemberSignInResponse signIn(MemberSignInRequest memberSignInRequest) {
        Member member = getExistMember(memberSignInRequest.getEmail());
        if(!encoder.matches(memberSignInRequest.getPassword(), member.getPassword()))
            throw new RuntimeException("");
        String token = tokenProvider.createToken(member.getEmail() + ":" + member.getRoleSet() + ":" + member.getName());
        return new MemberSignInResponse(member.getEmail(), token);
    }

    @Override
    public MemberResponse getMember(Long memberId) {
        return MemberResponse.from(getExistMember(memberId));
    }

    @Override
    @Transactional
    public MemberResponse editMember(Long memberId, MemberEditRequest memberEditRequest) {
        Member member = getExistMember(memberId);
        String name = memberEditRequest.getNewName() == null ? member.getName() : memberEditRequest.getNewName();
        Image profileImage = memberEditRequest.getNewProfileImage() == null ? member.getProfileImage() : Image.from(memberEditRequest.getNewProfileImage());
        member.setName(name);
        member.setProfileImage(profileImage);
        return MemberResponse.from(memberRepository.save(member));
    }

    @Override
    @Transactional
    public long withdrawMember(Long memberId) {
        Member member = getExistMember(memberId);
        memberRepository.delete(member);
        return memberId;
    }

    @Override
    @Transactional
    public void deleteAllMember() {
        memberRepository.deleteAll();
    }

    @Override
    public MemberResponse getMember(String email) {

        return MemberResponse.from(getExistMember(email));
    }

    @Override
    @Transactional
    public MemberResponse editPassword(Long memberId, MemberPasswordEditRequest memberPasswordEditRequest) {
        if(!Objects.equals(memberPasswordEditRequest.getPasswordCheck(), memberPasswordEditRequest.getNewPassword()))
            throw new UnMatchedValueException("새 비밀번호", "새 비밀번호 확인");
        Member member = getExistMember(memberId);
        if(!encoder.matches(memberPasswordEditRequest.getCurrentPassword(), member.getPassword()))
            throw new UnMatchedValueException("현재 비밀번호", "입력된 비밀번호");
        member.setPassword(memberPasswordEditRequest.getNewPassword());
        return MemberResponse.from(memberRepository.save(member));
    }

    @Override
    @Transactional
    public MemberResponse addAdminAuthority(Long memberId) {
        Member member = getExistMember(memberId);
        member.getRoleSet().add(MemberType.ADMIN);
        return MemberResponse.from(memberRepository.save(member));
    }

    @Override
    @Transactional
    public MemberResponse suspendMember(Long memberId, int days) {
        Member member = getExistMember(memberId);
        ZonedDateTime now = (member.getSuspendedTime() == null) ? ZonedDateTime.now() : member.getSuspendedTime();
        member.setSuspendedTime(now.plusDays(days));
        return MemberResponse.from(memberRepository.save(member));
    }

    @Override
    @Transactional
    public MemberResponse cancelSuspend(Long memberId) {
        Member member = getExistMember(memberId);
        member.setSuspendedTime(null);
        return MemberResponse.from(memberRepository.save(member));
    }

    @Override
    @Transactional
    public void cancelExpiredSuspend() {
        List<Member> members = memberRepository.getMembersBySuspendedTimeIsNotNull();
        for(Member member : members)
            if(member.getSuspendedTime() != null && member.getSuspendedTime().toLocalDateTime().isBefore(LocalDateTime.now()))
            {
                member.setSuspendedTime(null);
                memberRepository.save(member);
            }
    }
    public MemberEntityServiceImpl(@NotNull MemberRepository memberRepository, @NotNull PasswordEncoder encoder, @NotNull TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
        this.tokenProvider = tokenProvider;
    }
}
