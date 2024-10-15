package com.evehunt.evehuntjava.domain.mail.repository;

import com.evehunt.evehuntjava.domain.mail.model.Mail;

import java.util.List;

public interface QueryDslMailRepository {
    List<Mail> getUnsentMails();
}
