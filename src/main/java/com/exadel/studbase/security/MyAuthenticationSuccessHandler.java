package com.exadel.studbase.security;

import com.exadel.studbase.web.SessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Алексей on 30.07.2014.
 */
@Service
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    SessionEntity sessionEntity;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String targetUrl = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().isEmpty() ? null : userDetails.getAuthorities().toArray()[0].toString();
            String login = userDetails.getUsername();

            String roles[] = role.split(";");
            if (roles.length == 1) {
                sessionEntity.setRole(roles[0]);
                request.getSession(true).setAttribute("role2", roles[0]);
            } else {
                sessionEntity.setRole("ROLE_FEEDBACKER");
            }

            if (role.equals("ROLE_STUDENT")) {
                targetUrl = "/info/redirectInfo?login=" + login;
            } else {
                targetUrl = "/list";
            }
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    protected void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

}
