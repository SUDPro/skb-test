package ru.skb.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

import ru.skb.dto.UserStatementDTO;
import ru.skb.entities.User;
import ru.skb.services.impl.RegistrationServiceImpl;

public class RegistrationServiceTest {

    private RegistrationServiceImpl registrationService;

    private StatementService statementService;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        statementService = Mockito.mock(StatementService.class);
        userService = Mockito.mock(UserService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        registrationService = new RegistrationServiceImpl(statementService, userService, passwordEncoder);
    }

    @ParameterizedTest
    @MethodSource("provideUserStatementDTO")
    void save(UserStatementDTO dto) {
        //given
        when(userService.save(any())).thenReturn(new User());

        //when
        registrationService.save(dto);

        //then
        verify(userService, times(1)).save(any());
        verify(statementService, times(1)).save(any());
    }

    public static Stream<Arguments> provideUserStatementDTO() {
        UserStatementDTO userStatementDTO = UserStatementDTO.builder()
            .login("login")
            .email("email@email.com")
            .name("name")
            .surname("surname")
            .patronomyc("patronomyc")
            .password("pass")
            .build();

        UserStatementDTO invalidEmail = UserStatementDTO.builder()
            .login("213")
            .email("email@mail.com")
            .name("name")
            .surname("surname2")
            .patronomyc("patronomyc2")
            .password("pass123")
            .build();
        return Stream.of(
            Arguments.of(userStatementDTO),
            Arguments.of(invalidEmail)
        );
    }
}
