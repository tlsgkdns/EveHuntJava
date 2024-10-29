package com.evehunt.evehuntjava.global.infra.scheduler;

import com.evehunt.evehuntjava.domain.event.service.EventService;
import com.evehunt.evehuntjava.domain.mail.service.MailService;
import com.evehunt.evehuntjava.domain.member.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final EventService eventService;
    private final MemberService memberService;
    private final MailService mailService;

    @Async
    @Scheduled(
            fixedRate = 1000L
    )
    public void closeExpiredEvents() {
        eventService.setExpiredEventsClose();
    }

    @Async
    @Scheduled(
            cron = "0 0 6 * * *"
    )
    public void cancelExpiredSuspend() {
        memberService.cancelExpiredSuspend();
    }

    @Async
    @Scheduled(
            fixedRate = 200000L
    )
    @CacheEvict({"popularTags", "popularEvents"})
    public void clearCache() {
    }

    @Scheduled(fixedDelay = 100000L)
    public void sendMails() throws MessagingException {
        mailService.sendMails();
    }

    public Scheduler(@NotNull EventService eventService, @NotNull MemberService memberService, @NotNull MailService mailService) {
        this.eventService = eventService;
        this.memberService = memberService;
        this.mailService = mailService;
    }
}
