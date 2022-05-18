package ru.skb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatementDTO {
    private String name;
    private String surname;
    private String patronomyc;
    private String login;
    private String email;
    private String password;
}