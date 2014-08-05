package com.exadel.studbase.web.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Алексей on 05.08.2014.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String loginError(@RequestParam("error") Boolean error) {
        if(error) {
            return "403";
        }
        return null;
    }
}
