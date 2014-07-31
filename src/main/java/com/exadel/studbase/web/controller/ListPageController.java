package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.service.IStudentViewService;
import com.exadel.studbase.service.IUserService;
import com.exadel.studbase.service.mail.MailService;
import com.exadel.studbase.service.mail.MailServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        System.out.println("List page redirect");
        return "listPage";
    }

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
        String desiredName = (String) request.getParameter("desiredName");
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

    @RequestMapping(value = "/list/sendMail", method = RequestMethod.POST)
    public void sendMail(HttpServletRequest request, HttpServletResponse response) {
        MailService mailService= new MailServiceImpl();

        Gson gson = new Gson();

        String students =(String) request.getParameter("students");
        //String subject =(String) request.getParameter("subject");
        String body =(String) request.getParameter("message");

        ArrayList<Long> studentId = gson.fromJson(students, ArrayList.class);

        for(Long id : studentId) {
            User user = userService.getById(id);
            mailService.sendMail(user.getEmail(), "", body);
        }
    }
}
