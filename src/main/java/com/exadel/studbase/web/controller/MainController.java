package com.exadel.studbase.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ala'n on 10.07.2014.
 */

@Controller
//@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/")
public class MainController {

   // @Autowired
   // IStudBaseMainService service;

    @RequestMapping(value = "/secured/index", method = RequestMethod.GET)
    public String index() {

        System.out.println("log!!");

        return "secured/index";
    }

}
