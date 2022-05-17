package ru.skb.handlers;

import ru.skb.entities.Message;

public interface MessagingHandler<T> {

    void handleMessage(Message<T> incomingMessage);
}
