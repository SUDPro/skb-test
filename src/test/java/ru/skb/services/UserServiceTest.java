package ru.skb.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ru.skb.entities.User;
import ru.skb.repositories.UserRepository;
import ru.skb.services.impl.UserServiceImpl;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void save() {
        //when
        userService.save(new User());

        //then
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void getById() {
        //when
        userService.getById(any());

        //then
        verify(userRepository, times(1)).getById(any());
    }
}
