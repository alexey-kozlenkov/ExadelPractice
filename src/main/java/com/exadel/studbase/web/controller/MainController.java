package com.exadel.studbase.web.controller;

import org.codehaus.jackson.annotate.JsonCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import com.google.gson.Gson;

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
    public String secIndex() {

        System.out.println("log!!");

        return "secured/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {

        System.out.println("log!!");

        return "listPage";
    }


    // Provide sanding list data
    @RequestMapping(value = "/list/data", method = RequestMethod.GET)
    public void listData(HttpServletRequest request, HttpServletResponse response){
        Object smth = request.getAttribute("item");
        System.out.println("get it!!!");
        try {
            Gson gson = new Gson();

            response.getWriter().print(gson.toJson(new Object[]{new String("Smth"), new Date(), 123}));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }
}
