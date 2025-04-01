package com.slowv.udemi.integration.mail;

import com.slowv.udemi.entity.AccountEntity;

public interface MailService {
    void sendEmailActiveAccount(String to, String subject, AccountEntity account, String code);
}
