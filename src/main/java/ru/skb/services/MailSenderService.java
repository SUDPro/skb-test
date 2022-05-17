package ru.skb.services;

import java.util.concurrent.TimeoutException;

import ru.skb.entities.MailBody;

public interface MailSenderService {

    void sendMail(String toAddress, MailBody messageBody) throws TimeoutException;
}
