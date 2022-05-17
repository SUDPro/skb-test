package ru.skb.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.skb.dto.UserStatementDTO;
import ru.skb.services.RegistrationService;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public void register(@RequestBody UserStatementDTO statementDTO) {
        registrationService.save(statementDTO);
    }
}
