package ru.skb.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import ru.skb.Application;
import ru.skb.entities.Statement;
import ru.skb.entities.StatementStatus;
import ru.skb.entities.User;
import ru.skb.repositories.StatementRepository;
import ru.skb.repositories.UserRepository;

//TODO better to use test container
@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class RegistrationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {

        //when
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                    + "    \"name\": \"Name\",\n"
                    + "    \"surname\": \"Surname\",\n"
                    + "    \"patronomyc\": \"Patron\",\n"
                    + "    \"login\": \"login\",\n"
                    + "    \"email\": \"email@email.com\",\n"
                    + "    \"password\": \"password\"\n"
                    + "}"))
            .andExpect(status().isOk());

        //then
        Statement statement = statementRepository.findById(1L).orElseThrow(NotFoundException::new);
        Assertions.assertEquals(statement.getStatus(), StatementStatus.PENDING);
        Assertions.assertFalse(statement.isSended());
        Assertions.assertFalse(statement.isMailConfirmed());
        Assertions.assertEquals(statement.getUser().getEmail(), "email@email.com");

        User user = userRepository.getById(1L);
        Assertions.assertEquals(user.getLogin(), "login");
        Assertions.assertNotEquals(user.getPasswordHash(), "password");

    }
}
