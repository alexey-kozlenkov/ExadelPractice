package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.init.Options;

import com.google.gson.Gson;
import com.exadel.studbase.domain.document.Document;
import com.exadel.studbase.domain.employee.Employee;
import com.exadel.studbase.domain.feedback.Feedback;
import com.exadel.studbase.domain.skills.SkillSet;
import com.exadel.studbase.domain.skills.SkillType;
import com.exadel.studbase.domain.student.Student;
import com.exadel.studbase.domain.user.User;
import com.exadel.studbase.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.List;
import java.util.Random;


@Controller
//@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/")
public class MainController {

    @Autowired
    IUserService userService;
    @Autowired
    IStudentService studentService;
    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IFeedbackService feedbackService;
    @Autowired
    IDocumentService documentService;
    @Autowired
    ISkillTypeService skillTypeService;
    @Autowired
    ISkillSetService skillSetService;


    @RequestMapping(value = "/secured/index", method = RequestMethod.GET)
    public String secIndex() {

        System.out.println("log!!");

        return "secured/index";
    }


    @RequestMapping(value = "/addStudent")
    public String addStudent() {

        User user = new User();
        user.setName("Julia Malyshko");
        user.setEmail("julia@gmail.com");
        user.setLogin("jul.malyshko");
        user.setRole("stud");

        System.out.println("Trying to student me as user...");
        userService.save(user);
        System.out.println("Saved success!");

        Student student = new Student();
        student.setId(user.getId());
        student.setState("training");
        student.setUniversity("BSU");
        student.setFaculty("FAMCS");
        student.setCourse(3);
        student.setGroup(7);
        student.setGraduationDate(java.sql.Date.valueOf("2017-07-01"));
        student.setRoleCurrentProject("Junior developer");
        student.setTechsCurrentProject("CSS, JS, JSON");

        System.out.println("trying to save student as student...");
        studentService.save(student);
        System.out.println("Saved success!");

        System.out.println("---------------------------------------------------------------------");

        return "login";
    }

    @RequestMapping(value = "/addEmployee")
    public String addEmployee() {

        System.out.println("Creating user for Employee...");
        User user = new User();
        user.setName("Timofej Sakharchuk");
        user.setEmail("tim.sakharchuk@exadel.com");
        user.setRole("developer");
        user.setLogin("tim.sakharchuk");
        System.out.println("Created success!");

        System.out.println("trying to save user as user...");
        System.out.println(userService.save(user));
        System.out.println("Saved success!");

        Employee employee = new Employee();
        employee.setId(user.getId());

        System.out.println("Trying to save employee...");
        System.out.println(employeeService.save(employee));
        System.out.println("Saved success!");

        System.out.println("---------------------------------------------------------------------");

        return "login";
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String infoPage() {
        return "studentInfo";
    }


    @RequestMapping(value = "/info/getOptions", method = RequestMethod.GET)
    public void studentData(HttpServletRequest request, HttpServletResponse response) {
        //Object smth = request.getAttribute("id");
        System.out.println("get it2q222222222222222222222222222!!!");
        try {
            Gson gson = new Gson();
            Options options = new Options();
            response.getWriter().print(gson.toJson(options, Options.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }


    @RequestMapping(value = "/info/getInformation", method = RequestMethod.GET)
    public void optionsData(HttpServletRequest request, HttpServletResponse response) {
        // System.out.println("get it3333333333333333333!!!");
        try {
            Gson gson = new Gson();
            //System.out.println(request.getQueryString());
            User user = userService.getById(Long.parseLong(request.getQueryString()));
            System.out.println(user);
            response.getWriter().print(gson.toJson(user, User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }


    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public ModelAndView test(@PathVariable long id) {
        ModelAndView mv = new ModelAndView("studentInfo");
        User user_ = new User();
  //      mv.addObject("user", user_);
        mv.addObject("id",id);
//        ${user.name}
        return mv;
    }


    @RequestMapping(value = "/info/postManualInformation", method = RequestMethod.POST)
    public void editInformation(HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("post it44444!");
        try {

//            Map<String, String[]> params = request.getParameterMap();
//            Set<Map.Entry<String, String[]>> entry = params.entrySet();
//            for (Map.Entry<String, String[]> element : params.entrySet()) {
//                System.out.print("Key = " + element.getKey()); //+ ", Value = " + element.getValue()[0]);
//                for(String s : element.getValue())
//                    System.out.print("\t" + s);
//                System.out.println();
//            }
            User editedUser = userService.getById(Long.parseLong(request.getParameter("studentId")));
            editedUser.setName(request.getParameter("studentName"));
            editedUser.setLogin(request.getParameter("studentLogin"));
            editedUser.setPassword(request.getParameter("studentPassword"));
            editedUser.setEmail(request.getParameter("studentEmail"));
            editedUser.setRole(request.getParameter("studentRole"));
            editedUser.getStudentInfo().setState(request.getParameter("studentState"));
            userService.save(editedUser);
            studentService.save(editedUser.getStudentInfo());

            response.getWriter().print("{\"post\":\"ok\"}"); //string in double quotes
            response.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
