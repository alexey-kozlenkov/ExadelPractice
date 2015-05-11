package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * Created by Administrator on 14.08.2014.
 */
@Controller
@Secured("IS_AUTHENTICATED_FULLY")
@RequestMapping("/info")
public class EmployeeInfoPageController {
    @Autowired
    IUserService userService;
    @Autowired
    IEmployeeService employeeService;
    @Autowired
    ICuratoringService curatoringService;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String employeeInfoPage(@RequestParam("id") Long id) {
        return "employeeInfo.html";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_CURATOR", "ROLE_FEEDBACKER"})
    @RequestMapping(value = "/postEmployeeManualInformation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String editEmployeeManualInformation(@RequestParam("employeeId") Long id,
                                                @RequestParam("employeeName") String name,
                                                @RequestParam("employeeBirthDate") String birthDate,
                                                @RequestParam("employeeLogin") String login,
                                                @RequestParam("employeePassword") String password,
                                                @RequestParam("employeeEmail") String email,
                                                @RequestParam("employeeSkype") String skype,
                                                @RequestParam("employeePhone") String phone ) {
        User editedUser = userService.getById(id);
        editedUser.setName(name);
        editedUser.setBirthdate(formatField(birthDate, Date.class));
        editedUser.setLogin(login);
        editedUser.setPassword(password);
        editedUser.setEmail(email);
        editedUser.setSkype(skype);
        editedUser.setTelephone(phone);

        userService.save(editedUser);

        return "{\"post\":\"ok\"}";
    }


    @ResponseBody
    private <T> T formatField (String value, Class<T> pClass) {
        if(pClass == Integer.class) {
            return value.equals("") ? null : (T) Integer.valueOf(value);
        } else if (pClass == Date.class ) {
            return value.equals("") ? null : (T) Date.valueOf(value);
        }
        return null;
    }
}
