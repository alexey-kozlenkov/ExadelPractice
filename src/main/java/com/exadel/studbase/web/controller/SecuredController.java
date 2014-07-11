package com.exadel.studbase.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/secured")
public class SecuredController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "secured/index";
    }

}
