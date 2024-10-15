package com.evehunt.evehuntjava.global.infra.aop;

import com.evehunt.evehuntjava.domain.event.dto.EventResponse;
import com.evehunt.evehuntjava.domain.event.service.EventEntityService;
import com.evehunt.evehuntjava.domain.member.service.MemberService;
import com.evehunt.evehuntjava.global.exception.exception.UnAuthorizedAccessException;
import jakarta.validation.constraints.NotNull;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

@Aspect
@Component
public class MemberAOP {

    private final EventEntityService eventService;
    private final MemberService memberService;

    @Before("@annotation(com.evehunt.evehuntjava.global.infra.aop.annotation.CheckEventLoginMember)")
    public void checkEventMember(@NotNull JoinPoint joinPoint) {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        String username =  ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Object[] parameterValues = joinPoint.getArgs();
        Parameter[] parameters = method.getParameters();
        for(int i = 0 ;i < parameters.length; i++)
            if(parameters[i].getName().equals("eventId"))
            {
                EventResponse event = eventService.getEvent((Long) parameterValues[i]);
                String email = memberService.getMember(event.getHostId()).getEmail();
                if(!Objects.equals(email, username))
                    throw new UnAuthorizedAccessException("Event");
            }
    }

    public MemberAOP(EventEntityService eventService, MemberService memberService)
    {
        this.memberService = memberService;
        this.eventService = eventService;
    }
}
