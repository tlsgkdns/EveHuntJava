package com.evehunt.evehuntjava;

import com.evehunt.evehuntjava.domain.event.dto.EventHostRequest;
import com.evehunt.evehuntjava.domain.event.service.EventService;
import com.evehunt.evehuntjava.domain.mail.service.MailService;
import com.evehunt.evehuntjava.domain.member.dto.MemberRegisterRequest;
import com.evehunt.evehuntjava.domain.member.service.MemberService;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipateRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipateResponse;
import com.evehunt.evehuntjava.domain.participant.service.ParticipantService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource(properties = "app.scheduling.enable=false")
public class EventTests {
    @Autowired
    private EventService eventService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MemberService memberService;

    long eventId = 1L;
    @BeforeAll
    static void registerMember(
            @Autowired MemberService memberService
    )
    {
        for(int i = 1; i <= 1000; i++)
        {
            MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(
                    String.valueOf(i).repeat(2) + "@aaaaaaver.com",
                    String.valueOf(i),
                    String.valueOf(Math.random()),
                    null
            );
            memberService.registerMember(memberRegisterRequest);
        }
    }
    void hostEvent()
    {
        EventHostRequest event = new EventHostRequest("Hello World", "I am here", "Winner Winner",
                null, 990, LocalDateTime.now().plusDays(2) ,"Do you love me?", null);
        eventId = eventService.hostEvent(event, "11@aaaaaaver.com").getId();
    }

    @Test
    void participateEvent()
    {
        hostEvent();
        participantService.participateEvent(eventId,
                String.valueOf(1).repeat(2) + "@aaaaaaver.com", new ParticipateRequest("Winner Pick"));
    }
    @Test
    void testMultiThreadEnvironment() throws InterruptedException {
        hostEvent();
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for(long id = 1L; id <= threadCount; id++)
        {
            long finalId = id;
            executorService.execute(() -> {
                participantService.participateEvent(eventId,
                        String.valueOf(finalId).repeat(2) + "@aaaaaaver.com", new ParticipateRequest("Winner Pick"));
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        List<ParticipateResponse> list = participantService.getParticipantsByEvent(eventId);
        System.out.println(list.size());
    }
}
