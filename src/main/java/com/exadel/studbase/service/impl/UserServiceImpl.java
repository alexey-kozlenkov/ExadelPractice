package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.IUserDAO;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Алексей on 18.07.14.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) {
        return userDAO.saveOrUpdate(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User getById(Long id) {
        return userDAO.find(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<User> getAll() {
        return userDAO.getAll();
    }
}
