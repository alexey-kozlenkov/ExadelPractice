package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Алексей on 18.07.14.
 */
public interface IUserDAO extends GenericDAO<User, StudentView, Long> {
    User getByLogin(String login);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
