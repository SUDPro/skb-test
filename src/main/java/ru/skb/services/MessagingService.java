package ru.skb.services;

import ru.skb.entities.Message;

public interface MessagingService<T> {

    boolean send(Message<T> msg);
}
