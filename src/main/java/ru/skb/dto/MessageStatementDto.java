package ru.skb.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skb.entities.StatementStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageStatementDto {

    private Long statementId;
    private Long userId;
    private StatementStatus statementStatus;
    private boolean isSended;
}
