package ru.skb.handlers.impl;

import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.skb.entities.MailBody;
import ru.skb.entities.Message;
import ru.skb.entities.Statement;
import ru.skb.entities.StatementStatus;
import ru.skb.entities.User;
import ru.skb.handlers.MessagingHandler;
import ru.skb.services.MailSenderService;
import ru.skb.services.StatementService;
import ru.skb.services.UserService;

@Component
@RequiredArgsConstructor
public class StatementMessagingHandlerImpl implements MessagingHandler<Statement> {

    private final StatementService statementService;
    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final RetryTemplate retryTemplate;

    @Override
    @Transactional
    public void handleMessage(Message<Statement> incomingMessage) {
        if (incomingMessage != null && incomingMessage.getPayload() != null) {
            Statement statement = statementService.getById(incomingMessage.getPayload().getId());
            statement.setStatus(incomingMessage.getPayload().getStatus());
            statementService.save(statement);
            User user = userService.getById(statement.getUser().getId());
            if (statement.getStatus().equals(StatementStatus.ACCESS)) {
                user.setActive(true);
                userService.save(user);
            }
            retryTemplate.execute(context -> {
                try {
                    mailSenderService.sendMail(user.getEmail(), MailBody.builder().status(statement.getStatus())
                        .login(user.getLogin())
                        .fullName(user.getFullName())
                        .status(statement.getStatus())
                        .build());
                } catch (Exception e) {
                    throw new IllegalStateException("Cannot send email");
                }
                statement.setMailConfirmed(true);
                statementService.save(statement);
                return true;
            }, retryContext -> addToDeadLetterQueue());
        }
    }

    private boolean addToDeadLetterQueue() {
        /* add to dead letter queue */
        return true;
    }
}