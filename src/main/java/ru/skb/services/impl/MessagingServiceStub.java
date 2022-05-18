package ru.skb.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.RequiredArgsConstructor;
import ru.skb.dto.MessageStatementDto;
import ru.skb.entities.Message;
import ru.skb.handlers.MessagingHandler;
import ru.skb.services.MessagingService;

@Service
@RequiredArgsConstructor
public class MessagingServiceStub implements MessagingService<MessageStatementDto> {

    //it's stub for handle message
    @Qualifier("statementMessagingHandlerImpl")
    private final MessagingHandler<MessageStatementDto> messagingHandler;

    @Override
    public boolean send(Message<MessageStatementDto> msg) {

        //ИСпользовал executor для имитации асинхронного ответа
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            Thread.sleep(1000);
            messagingHandler.handleMessage(msg);
            return true;
        });
        return msg.getPayload() != null;
    }
}
