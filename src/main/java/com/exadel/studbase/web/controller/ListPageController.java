package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.service.IMailService;
import com.exadel.studbase.service.IStudentViewService;
import com.exadel.studbase.service.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by ala'n on 29.07.2014.
 */
@Controller
@Secured({"ROLE_CURATOR", "ROLE_FEEDBACKER", "ROLE_SUPERADMIN", "ROLE_OFFICE"})
@RequestMapping("/")
public class ListPageController {
    @Autowired
    IUserService userService;
    @Autowired
    IStudentViewService studentViewService;
    @Autowired
    IMailService mailService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        System.out.println("List page redirect");
        return "list";
    }

    //TODO! reorganized (listData, getViewByName) (mast be one rest-service)

    // Provide sanding list data
    @RequestMapping(value = "/list/data", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String listData(@RequestParam("name") String searchName,
                           @RequestParam("filter") Object filter) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();//= new Gson();

        Map<String, String[]> map = new HashMap<String, String[]>();
        System.out.println(searchName);
        System.out.println(gson.fromJson((String) filter, map.getClass()));

        Collection<StudentView> studList = studentViewService.getAll();

        return gson.toJson(studList);
    }

    @RequestMapping(value = "/list/name", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getViewByName(@RequestParam("searchName") String desiredName) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        Map<String, String[]> map = new HashMap<String, String[]>();
        System.out.println(desiredName);
        //    System.out.println(gson.fromJson((String) filter, map.getClass()));

        Collection<StudentView> studList = studentViewService.getViewByStudentName(desiredName);

        return gson.toJson(studList);
    }

    @Secured("ROLE_SUPERADMIN")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/list/sendMail", method = RequestMethod.POST)
    public String sendMail(@RequestParam("students") String students,
                           @RequestParam("message") String body) {
        Gson gson = new Gson();

        Long[] studentId = gson.fromJson(students, Long[].class);

        List<String> inaccessibleEmail = new ArrayList<String>();
        for (Long id : studentId) {
            User user = userService.getById(id);
            if (!mailService.sendMail(user.getEmail(), "", body)) {
                inaccessibleEmail.add(user.getEmail());
            }
        }
        return gson.toJson(inaccessibleEmail);
    }

    @RequestMapping(value = "/list/export", method = RequestMethod.GET)
    public ModelAndView export(@RequestParam("students") String students) {
        Gson gson = new Gson();

        Long[] studentId = gson.fromJson(students, Long[].class);
        List<User> listOfUsers = new ArrayList<User>();
        for (Long id : studentId) {
            User user = userService.getById(id);
            listOfUsers.add(user);
        }

        return new ModelAndView("excelView", "users", listOfUsers);
    }

    @RequestMapping(value = "/list/quickAdd", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addUser(@RequestParam("user") Object name,
                        @RequestParam("role") Object role) {

        //TODO ! Real service
    }
}
