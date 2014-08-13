package com.exadel.studbase.web.controller;

import com.exadel.studbase.security.MySecurityUser;
import com.exadel.studbase.service.IStudentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    IStudentService studentService;

    @RequestMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getCommonInformation() {
        Gson gson = new Gson();
        Map commonInfo = new HashMap<String, String>();
        String role = "";
        MySecurityUser principal = (MySecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            String state = studentService.getById(principal.getId()).getState();
            role = state.equalsIgnoreCase("training") ? "0" : "1";
        } else if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CURATOR"))) {
            role = "3";
        } else if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FEEDBACKER"))) {
            role = "2";
        } else if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OFFICE"))) {
            role = "4";
        } else if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERADMIN"))) {
            role = "5";
        }

        commonInfo.put("id", principal.getId().toString());
        commonInfo.put("username", principal.getName());
        commonInfo.put("role", role);

        return gson.toJson(commonInfo);
    }
}
