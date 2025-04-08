package com.slowv.udemi.integration.mail.impl;

import com.slowv.udemi.controller.errors.BusinessException;
import com.slowv.udemi.entity.AccountEntity;
import com.slowv.udemi.integration.mail.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Component
@RequiredArgsConstructor
public class GmailService implements MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    @Override
    public void sendEmailActiveAccount(final String to, final String subject, AccountEntity account, String code) {
        final var message = mailSender.createMimeMessage();
        try {
            final var helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);

            final var context = new Context();
            context.setVariable("account", account);
            context.setVariable("code", code);

            // Load nội dung từ template
            final var htmlContent = templateEngine.process("email/active-account", context);

            helper.setText(htmlContent, true); // `true` để hỗ trợ HTML
            mailSender.send(message);
        } catch (final MessagingException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

}
