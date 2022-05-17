package ru.skb.services;

import java.util.List;

import ru.skb.dto.MessageStatementDto;
import ru.skb.entities.Statement;

public interface StatementService {

    Statement save(Statement statement);

    List<Statement> getAllBySendedStatus(boolean sendedStatus);

    void updateAllSendedStatus(List<MessageStatementDto> statements);

    Statement getById(Long id);
}
