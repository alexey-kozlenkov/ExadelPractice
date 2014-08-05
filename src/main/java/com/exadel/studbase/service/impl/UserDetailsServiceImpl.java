package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Алексей on 30.07.2014.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserDAO userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }
}
