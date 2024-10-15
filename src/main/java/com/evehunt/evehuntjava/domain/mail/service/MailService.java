package com.evehunt.evehuntjava.domain.mail.service;

import com.evehunt.evehuntjava.domain.mail.dto.MailRequest;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendMails() throws MessagingException;

    void addMail(MailRequest request);

    int getUnsentMailCount();
}
