package ru.skb.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import ru.skb.dto.MessageStatementDto;
import ru.skb.entities.Statement;
import ru.skb.repositories.StatementRepository;
import ru.skb.services.StatementService;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;

    @Override
    @Transactional
    public Statement save(Statement statement) {
        return statementRepository.save(statement);
    }

    @Override
    public List<Statement> getAllBySendedStatus(boolean sendedStatus) {
        return statementRepository.getAllByIsSended(sendedStatus);
    }

    @Override
    public void updateAllSendedStatus(List<MessageStatementDto> statements) {
        statementRepository.updateAllSendedStatus(statements.stream()
            .map(MessageStatementDto::getStatementId)
            .collect(Collectors.toList()));
    }

    @Override
    public Statement getById(Long id) {
        return statementRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
