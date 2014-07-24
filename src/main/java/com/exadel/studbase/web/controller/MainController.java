package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.document.Document;
import com.exadel.studbase.domain.employee.Employee;
import com.exadel.studbase.domain.feedback.Feedback;
import com.exadel.studbase.domain.student.Student;
import com.exadel.studbase.domain.user.User;
import com.exadel.studbase.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;
import java.util.Set;


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
    public void listData(HttpServletRequest request, HttpServletResponse response) {
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


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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


    @RequestMapping(value = "/info/get", method = RequestMethod.GET)
    public void optionsData(HttpServletRequest request, HttpServletResponse response) {
        //Object smth = request.getAttribute("id");
        System.out.println("get it3333333333333333333!!!");
        try {
            Gson gson = new Gson();
            User user = new User();
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

       
            response.getWriter().print(gson.toJson(user, User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String infoPage() {
        return "studentInfo";
    }


    @RequestMapping(value = "/info/post", method = RequestMethod.POST)
    public void editInformation(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("post it44444!");
        try {

            Map<String, String[]> params = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entry = params.entrySet();
            for (Map.Entry<String, String[]> element : params.entrySet()) {
                System.out.println("Key = " + element.getKey() + ", Value = " + element.getValue()[0]);
            }
            response.getWriter().print("{\"post\":\"ok\"}"); //string in double quotes
            response.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

   
}
