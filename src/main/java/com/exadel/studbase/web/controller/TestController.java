package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.Student;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.service.*;
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
import java.util.List;
import java.util.Random;

/**
 * Created by ala'n on 29.07.2014.
 */
@Controller
//@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/test/")
public class TestController {

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

    public static List<User> testList;
    public static List<User> getTestList(){
        if(testList==null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            testList = new ArrayList<User>();
            User st;
            Random rand  =new Random();
            for(int id_ = 1; id_<=1000; id_++) {
                st = new User();

                st.setId(Long.valueOf(id_));
                st.setName("User_" + id_);
                st.setLogin("Login_" + id_);
                st.setPassword("");
                st.setRole("user");
                st.setEmail("email_" + id_ + "@mail.ru");
                st.setStudentInfo(new Student());
                st.getStudentInfo().setCourse((id_ % 10 < 5 ? 1 : 2));
                st.getStudentInfo().setGroup(id_ % 10);
                st.getStudentInfo().setUniversity((id_ < 10) ? "MSU" : "BSU");
                st.getStudentInfo().setFaculty((id_ < 10) ? "IP" : "FPM");
                st.getStudentInfo().setWorkingHours(4 + rand.nextInt(20));
                try {
                    st.getStudentInfo().setHireDate(new Date(sdf.parse("07.07.2014").getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(rand.nextBoolean())
                    try {
                        st.getStudentInfo().setBillable(new Date(sdf.parse("02.09.2014").getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                else
                    st.getStudentInfo().setBillable(null);
                try {
                    st.getStudentInfo().setGraduationDate(new Date(sdf.parse("01.05.2018").getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                st.getStudentInfo().setRoleCurrentProject(rand.nextBoolean() ? "developer" : "test");
                st.getStudentInfo().setTechsCurrentProject(rand.nextBoolean() ? "java" : "php");
                st.getStudentInfo().setEnglishLevel("advansed");
                testList.add(st);
            }
        }
        return testList;
    }


    @RequestMapping(value = "/secured/index", method = RequestMethod.GET)
    public String secIndex() {

        System.out.println("log!!");

        return "secured/index";
    }

    @RequestMapping(value = "/addStudent")
    public String addStudent() {

        User user = new User();
        user.setName("Alexey Kozlenkov");
        user.setEmail("alexey@gmail.com");
        user.setLogin("al.ov");
        user.setPassword("122345");
        user.setRole("student");

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

    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public ModelAndView test(@PathVariable long id) {
        ModelAndView mv = new ModelAndView("studentInfo");
        User user_ = new User();
        //      mv.addObject("user", user_);
        mv.addObject("id",id);
//        ${user.name}
        return mv;
    }

    @RequestMapping(value= "/test", method = RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse response){

        try {
            System.out.println("Get post req.!!");
            response.getWriter().print("good!!!");
            response.setStatus(200);
            //   response.flushBuffer();
            System.out.println("All sended!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
