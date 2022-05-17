package ru.skb.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.skb.dto.UserStatementDTO;
import ru.skb.entities.Statement;
import ru.skb.entities.StatementStatus;
import ru.skb.entities.User;
import ru.skb.services.RegistrationService;
import ru.skb.services.StatementService;
import ru.skb.services.UserService;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final StatementService statementService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void save(UserStatementDTO userStatementDTO) {
        final User user = User.builder()
            .email(userStatementDTO.getEmail())
            .login(userStatementDTO.getLogin())
            .name(userStatementDTO.getName())
            .surname(userStatementDTO.getSurname())
            .patronomyc(userStatementDTO.getPatronomyc())
            .passwordHash(passwordEncoder.encode(userStatementDTO.getPassword()))
            .isActive(false)
            .build();
        final User savedUser = userService.save(user);

        final Statement statement = Statement.builder()
            .status(StatementStatus.PENDING)
            .isMailConfirmed(false)
            .isSended(false)
            .user(savedUser)
            .build();
        statementService.save(statement);
    }
}