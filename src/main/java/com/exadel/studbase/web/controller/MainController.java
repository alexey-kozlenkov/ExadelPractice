package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.domain.init.Options;
import com.exadel.studbase.service.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.sql.Date;
import java.util.Set;


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

    @Autowired
    IStudentViewService studentViewService;

////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String infoPage() {
        return "studentInfo";
    }


    @RequestMapping(value = "/info/getOptions", method = RequestMethod.GET)
    public void studentData(HttpServletRequest request, HttpServletResponse response) {
        //Object smth = request.getAttribute("id");
        try {
            Gson gson = new Gson();
            Options options = new Options();
            response.getWriter().print(gson.toJson(options, Options.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }


    @RequestMapping(value = "/info/getManualInformation", method = RequestMethod.GET)
    public void manualData(HttpServletRequest request, HttpServletResponse response) {
        // System.out.println("get it3333333333333333333!!!");
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            System.out.println(request.getQueryString());
            User user = userService.getById(Long.parseLong(request.getQueryString()));
            System.out.println(user.getStudentInfo().toString());
            response.getWriter().print(gson.toJson(user, User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }



    @RequestMapping(value = "/info/postManualInformation", method = RequestMethod.POST)
    public void editManualInformation(HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("post it44444!");
        try {

            Map<String, String[]> params = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entry = params.entrySet();
            for (Map.Entry<String, String[]> element : params.entrySet()) {
                System.out.print("Key = " + element.getKey()); //+ ", Value = " + element.getValue()[0]);
                for(String s : element.getValue())
                    System.out.print("\t" + s);
                System.out.println();
            }
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

    @RequestMapping(value="/info/postEducation", method = RequestMethod.POST)
    public void editEducation(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String[]> params = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entry = params.entrySet();
            for (Map.Entry<String, String[]> element : params.entrySet()) {
                System.out.print("Key = " + element.getKey()); //+ ", Value = " + element.getValue()[0]);
                for(String s : element.getValue())
                    System.out.print("\t" + s);
                System.out.println();
            }

            Student editedStudent = studentService.getById(Long.parseLong(request.getParameter("studentId")));
            editedStudent.setUniversity(request.getParameter("studentUniversity"));
            editedStudent.setFaculty(request.getParameter("studentFaculty"));
            editedStudent.setCourse(Integer.parseInt(request.getParameter("studentCourse")));
            editedStudent.setGroup(Integer.parseInt(request.getParameter("studentGroup")));
            editedStudent.setGraduationDate(Date.valueOf(request.getParameter("studentGraduationDate")));
            studentService.save(editedStudent);


            response.getWriter().print("{\"post\":\"ok\"}"); //string in double quotes
            response.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
/////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        System.out.println("List page redirect");
        return "listPage";
    }

    // Provide sanding list data
    @RequestMapping(value = "/list/data", method = RequestMethod.GET)
    public void listData(HttpServletRequest request, HttpServletResponse response){
        Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd").create();//= new Gson();
        String searchName = (String) request.getParameter("name");
        Object filter = request.getParameter("filter");

        Map<String, String[]> map = new HashMap<String, String[]>();
        System.out.println(searchName);
        System.out.println(gson.fromJson((String) filter, map.getClass()));

        Collection<StudentView> studList  = studentViewService.getAll();


        try {
            response.getWriter().print(gson.toJson(studList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }

}
