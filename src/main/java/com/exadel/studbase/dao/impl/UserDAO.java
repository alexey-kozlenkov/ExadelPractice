package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IUserDAO;
import com.exadel.studbase.domain.impl.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Алексей on 18.07.14.
 */
@Repository
public class UserDAO extends GenericDAOImpl<User, Long> implements IUserDAO {

}
