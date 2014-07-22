package com.exadel.studbase.web.dao.impl;

import com.exadel.studbase.web.dao.impl.GenericDAOImpl;
import com.exadel.studbase.web.dao.IUserDAO;
import com.exadel.studbase.web.domain.user.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Алексей on 18.07.14.
 */
@Repository
public class UserDAO extends GenericDAOImpl<User, Long> implements IUserDAO {

}
