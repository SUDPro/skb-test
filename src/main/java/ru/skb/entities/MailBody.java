package ru.skb.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MailBody {
    private String fullName;
    private String email;
    private String login;
    private StatementStatus status;
}