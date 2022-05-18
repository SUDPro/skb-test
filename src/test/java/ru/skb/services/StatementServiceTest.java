package ru.skb.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import ru.skb.repositories.StatementRepository;
import ru.skb.services.impl.StatementServiceImpl;

public class StatementServiceTest {

    private StatementRepository statementRepository;
    private StatementService service;

    @BeforeEach
    public void setUp() {
        statementRepository = Mockito.mock(StatementRepository.class);
        service = new StatementServiceImpl(statementRepository);
    }

    @Test
    public void updateAllSendedStatusTest() {
        //when
        service.updateAllSendedStatus(new ArrayList<>());

        //then
        verify(statementRepository, times(1)).updateAllSendedStatus(any());
    }
}
