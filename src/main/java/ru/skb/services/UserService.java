package ru.skb.services;

import ru.skb.entities.User;

public interface UserService {

    User save(User user);

    User getById(Long id);
}
