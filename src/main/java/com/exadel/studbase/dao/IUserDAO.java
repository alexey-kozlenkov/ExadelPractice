package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserDAO extends GenericDAO<User, StudentView, Long> {
    User getByLogin(String login);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
