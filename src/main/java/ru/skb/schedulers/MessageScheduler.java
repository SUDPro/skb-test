package ru.skb.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import ru.skb.dto.MessageStatementDto;
import ru.skb.entities.Message;
import ru.skb.entities.Statement;
import ru.skb.services.MessagingService;
import ru.skb.services.StatementService;

// Used scheduler and "statement" table for Outbox pattern. In real system it can be kafka-connect like programs.
// Maybe it's better to use another table like "statement_message" because we can clean it and in future it will be better perfomance.
@Component
@RequiredArgsConstructor
public class MessageScheduler {

    private final StatementService statementService;
    private final MessagingService<MessageStatementDto> messagingService;

    @Scheduled(cron = "0 0/5 * * * *")
    public synchronized void sendMessages() {
        List<Statement> unsendedStatements = statementService.getAllBySendedStatus(false);
        List<Message<MessageStatementDto>> sendedMessages = new ArrayList<>();
        unsendedStatements.stream()
            .map((Statement statement) -> MessageStatementDto.builder()
                .statementId(statement.getId())
                .userId(statement.getUser().getId())
                .build())
            .forEach((MessageStatementDto statement) -> {
                Message<MessageStatementDto> message = Message.<MessageStatementDto>builder()
                    .id(UUID.randomUUID())
                    .payload(statement)
                    .build();
                boolean isSended = messagingService.send(message);
                if (isSended) {
                    message.getPayload().setSended(true);
                    sendedMessages.add(message);
                }
            });
        statementService.updateAllSendedStatus(sendedMessages.stream()
            .map(Message::getPayload)
            .collect(Collectors.toList()));
    }
}
