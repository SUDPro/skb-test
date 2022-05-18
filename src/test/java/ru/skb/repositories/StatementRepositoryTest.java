package ru.skb.repositories;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import ru.skb.entities.Statement;
import ru.skb.entities.StatementStatus;

//TODO Not good at all. Better to use test containers or Embedded solutions
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StatementRepositoryTest {

    @Autowired
    private StatementRepository subject;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllBySendedStatus() {
        //given
        Statement sendedStatement = Statement.builder()
            .isMailConfirmed(false)
            .isSended(true)
            .status(StatementStatus.PENDING)
            .build();
        Statement unsendedStatement = Statement.builder()
            .isMailConfirmed(false)
            .isSended(false)
            .status(StatementStatus.PENDING)
            .build();
        subject.saveAll(Lists.list(sendedStatement, unsendedStatement));

        //when
        List<Statement> result = subject.getAllByIsSended(false);

        //then
        Assertions.assertEquals(result.size(), 1);
    }

    @Test
    public void updateAll() {
        //given
        Statement sendedStatement = Statement.builder()
            .isMailConfirmed(false)
            .isSended(false)
            .status(StatementStatus.PENDING)
            .build();
        Statement unsendedStatement = Statement.builder()
            .isMailConfirmed(false)
            .isSended(false)
            .status(StatementStatus.PENDING)
            .build();
        List<Statement> statements = subject.saveAll(Lists.list(sendedStatement, unsendedStatement));

        //when
        subject.updateAllSendedStatus(statements.stream().map(Statement::getId).collect(Collectors.toList()));

        //then
        List<Statement> result = subject.getAllByIsSended(true);
        Assertions.assertEquals(result.size(), 2);
    }
}
