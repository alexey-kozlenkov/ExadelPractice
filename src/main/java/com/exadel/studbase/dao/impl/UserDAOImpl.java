package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IUserDAO;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.security.MySecurityUser;
import org.hibernate.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, StudentView, Long> implements IUserDAO {

    @Override
    public User getByLogin(String login) {
        Query query = getSession().createQuery("FROM User where login=:login");
        query.setParameter("login", login);
        List<User> executeResult = query.list();
        if (executeResult.size() > 0) {
            return executeResult.get(0);
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getByLogin(username);
        final String[] roles = user.getRole().trim().split(";");
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>(roles.length) {{
            for (int i = 0; i < roles.length; i++) {
                add(i, new SimpleGrantedAuthority(roles[i]));
            }
        }};
        return new MySecurityUser(user.getId(), user.getLogin(), user.getPassword(), authorities);
    }
}
