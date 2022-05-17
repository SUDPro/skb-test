package ru.skb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatementDTO {
    private String name;
    private String surname;
    private String patronomyc;
    private String login;
    private String email;
    private String password;
}