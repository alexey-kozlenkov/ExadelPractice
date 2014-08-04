package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.service.IMailService;
import com.exadel.studbase.service.IStudentViewService;
import com.exadel.studbase.service.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public void listData(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();//= new Gson();
        String searchName = (String) request.getParameter("name");
        Object filter = request.getParameter("filter");

        Map<String, String[]> map = new HashMap<String, String[]>();
        System.out.println(searchName);
        System.out.println(gson.fromJson((String) filter, map.getClass()));

        Collection<StudentView> studList = studentViewService.getAll();

        try {
            response.getWriter().print(gson.toJson(studList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }

    @RequestMapping(value = "/list/name", method = RequestMethod.GET)
    public void getViewByName(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();//= new Gson();
        String desiredName = (String) request.getParameter("searchName");
//        Object filter = request.getParameter("filter");

        Map<String, String[]> map = new HashMap<String, String[]>();
        System.out.println(desiredName);
        //    System.out.println(gson.fromJson((String) filter, map.getClass()));

        Collection<StudentView> studList = studentViewService.getViewByStudentName(desiredName);

        try {
            response.getWriter().print(gson.toJson(studList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }

    @Secured("ROLE_SUPERADMIN")
    @RequestMapping(value = "/list/sendMail", method = RequestMethod.POST)
    public void sendMail(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();

        String students = (String) request.getParameter("students");
        String body = (String) request.getParameter("message");

        Long[] studentId = gson.fromJson(students, Long[].class);

        List<String> inaccessibleEmail = new ArrayList<String>();
        for (Long id : studentId) {
            User user = userService.getById(id);
            if (!mailService.sendMail(user.getEmail(), "", body)) {
                inaccessibleEmail.add(user.getEmail());
            }
        }

        try {
            response.getWriter().print(gson.toJson(inaccessibleEmail));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }

    @RequestMapping(value = "/list/export", method = RequestMethod.GET)
    public ModelAndView export(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();

        String students = (String) request.getParameter("students");

        Long[] studentId = gson.fromJson(students, Long[].class);
        List<User> listOfUsers = new ArrayList<User>();
        for (Long id : studentId) {
            User user = userService.getById(id);
            listOfUsers.add(user);
        }

        return new ModelAndView("excelView", "users", listOfUsers);
    }

    @RequestMapping(value = "/list/quickAdd", method = RequestMethod.POST)
    public void addUser(HttpServletRequest request, HttpServletResponse response){
        Object name = request.getParameter("user");
        Object role = request.getParameter("role");

        //TODO ! Real service

        response.setStatus(200);
    }
}
