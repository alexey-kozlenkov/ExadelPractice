package com.exadel.studbase.web.service;

import com.exadel.studbase.web.domain.user.User;

import java.util.Collection;

/**
 * Created by Алексей on 18.07.14.
 */
public interface IUserService {
    User save(User user);

    User getById(Long id);

    void delete(User user);

    Collection<User> getAll();
}
