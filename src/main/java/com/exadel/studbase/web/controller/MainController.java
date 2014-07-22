package com.exadel.studbase.web.controller;

import com.exadel.studbase.web.domain.employee.Employee;
import com.exadel.studbase.web.domain.feedback.Feedback;
import com.exadel.studbase.web.domain.student.Student;
import com.exadel.studbase.web.domain.user.User;
import com.exadel.studbase.web.service.IEmployeeService;
import com.exadel.studbase.web.service.IFeedbackService;
import com.exadel.studbase.web.service.IStudentService;
import com.exadel.studbase.web.service.IUserService;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {

        Student stud = new Student();
        stud.setName("test_name");
        stud.setEmail("test_email");
        stud.setFaculty("BSU");

        System.out.println("Trying to save student: " + stud);
        studentService.save(stud);
        System.out.println("Saved success!");

        User user = new User();
        user.setLogin("test_login");
        user.setRole("stud");
        user.setStudentInfo(stud);

        System.out.println("Trying to save user: " + user);
        userService.save(user);
        System.out.println("Saved success!");

        user = null;

        Employee empl = new Employee();
        empl.setName(stud.getName());
        empl.setEmail(stud.getEmail());

        System.out.println("Trying to save employee: " + empl);
        employeeService.save(empl);
        System.out.println("Saved success!");

        System.out.println("trying to get User id by student: ");
        Long id = studentService.getUserId(stud.getId());
        System.out.println("Get success!");

        System.out.println("Trying to get user by id:...");
        User newUser = userService.getById(id);
        System.out.println("Get success!");

        System.out.println("try to save employee id..");
        newUser.setEmployeeInfo(empl);
        System.out.println("Saved success!");

        System.out.println("trying to update user...");
        userService.save(newUser);
        System.out.println("Updated success!");

        return "login";
    }

    @RequestMapping(value = "/feedback")
    public String test2() {

        Feedback feedback = new Feedback();
        Student student = studentService.getById(Long.valueOf(17));
        Employee employee = employeeService.getById(Long.valueOf(6));
        feedback.setStudent(student);
        feedback.setEmployee(employee);
        feedback.setContent("test_content");
        feedback.setProfessionalCompetence("test_prof_comp");
        feedback.setAttitudeToWork("test_attitude");
        feedback.setCollectiveRelations("test_coll_rel");
        feedback.setProfessionalProgress("test_prof_progress");
        feedback.setNeedMoreHours(true);
        feedback.setOnProject(true);
        feedback.setFeedbackDate(java.sql.Date.valueOf("2014-07-18"));

        System.out.println("trying to save feedback");
        feedbackService.save(feedback);
        System.out.println("Saved success!");

        System.out.println(feedbackService.getById(feedback.getId()));

        System.out.println("Trying to update feedback..");
        feedback.setOnProject(false);
        System.out.println("-----Changed: " + feedback);
        System.out.println(feedbackService.getById(feedback.getId()));
        System.out.println("Updated success!");

        System.out.println("Trying to delete feedback...");
        feedbackService.delete(feedback);
        System.out.println("Deleted success!");

        System.out.println("Trying to get all about someone...");
        System.out.println(feedbackService.getAllAboutStudent(student.getId()));
        System.out.println("Get success!");

        return "login";
    }

    @RequestMapping(value="/employee")
    public String employee() {

        Employee employee = new Employee();
        employee.setEmail("email_1");
        employee.setName("name_1");
        employee.setFeedbacks(null);

        System.out.println("Trying to save employee...");
        employeeService.save(employee);
        System.out.println("Saved success!");

        System.out.println("Trying to get all...");
        System.out.println(employeeService.getAll());
        System.out.println("Get success!");

        System.out.println("Trying to update: " + employee);
        employee.setName("update_name_1");
        System.out.println("Updated: " + employee);
        employeeService.save(employee);
        System.out.println("Trying to get all...");
        System.out.println(employeeService.getAll());
        System.out.println("Get success!");

        System.out.println("Trying to delete: " + employee);
        employeeService.delete(employee);
        System.out.println("Trying to get all...");
        System.out.println(employeeService.getAll());
        System.out.println("Get success!");
        System.out.println("==================================================================");

        return "login";
    }
}
