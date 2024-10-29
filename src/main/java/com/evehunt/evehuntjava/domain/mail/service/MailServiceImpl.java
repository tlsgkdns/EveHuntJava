package com.evehunt.evehuntjava.domain.mail.service;

import com.evehunt.evehuntjava.domain.mail.dto.MailRequest;
import com.evehunt.evehuntjava.domain.mail.model.Mail;
import com.evehunt.evehuntjava.domain.mail.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MailServiceImpl implements MailService{
    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;
    @NotNull
    private final String from;

    @Transactional
    public void sendMail(@NotNull String email, @NotNull String title, @NotNull String content) throws MessagingException {

        if (email.matches("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(content);
            helper.setFrom(from);
            this.javaMailSender.send(message);
        }
    }

    @Transactional
    public void sendMails() throws MessagingException {
        List<Mail> mailList = mailRepository.getUnsentMails();
        for(Mail mail : mailList)
        {
            sendMail(mail.getEmail(), mail.getTitle(), mail.getMailTxt());
            mail.setSent(true);
        }
    }

    public void addMail(@NotNull MailRequest mailRequest) {
        mailRepository.save(mailRequest.to());
    }

    public int getUnsentMailCount() {
        return mailRepository.getUnsentMails().size();
    }

    @NotNull
    public String getFrom() {
        return this.from;
    }

    public MailServiceImpl(@NotNull JavaMailSender javaMailSender, @NotNull MailRepository mailRepository, @Value("${spring.mail.username}") @NotNull String from) {
        this.javaMailSender = javaMailSender;
        this.mailRepository = mailRepository;
        this.from = from;
    }
}
