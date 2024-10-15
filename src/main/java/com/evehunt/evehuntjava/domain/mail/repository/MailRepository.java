package com.evehunt.evehuntjava.domain.mail.repository;

import com.evehunt.evehuntjava.domain.mail.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Long>, QueryDslMailRepository {
}
