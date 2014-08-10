package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.User;

import java.util.Collection;

public interface IUserService {
    User save(User user);

    User getById(Long id);

    User getByLogin(String login);

    void delete(User user);

    Collection<User> getAll();
}
