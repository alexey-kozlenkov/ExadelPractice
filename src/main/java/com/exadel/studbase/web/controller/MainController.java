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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

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

            response.getWriter().print(gson.toJson(new Object[]{new String("Smth"), Date.valueOf("2014"), 123}));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }

    @RequestMapping(value = "/test/user", method = RequestMethod.GET)
    public String testUser() {



        return "login";
    }

    @RequestMapping(value = "/test/feedback")
    public String testFeedback() {


        Feedback feedback = new Feedback();
        Student student = studentService.getById(Long.valueOf(17));
        Employee employee = employeeService.getById(Long.valueOf(6));
        feedback.setStudent(student);
        feedback.setFeedbacker(employee);
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
        feedbackService.save(feedback);
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

    @RequestMapping(value="/test/employee")
    public String testEmployee() {

        Employee employee = new Employee();
        employee.setFeedbacks(null);

        System.out.println("Trying to save employee...");
        employeeService.save(employee);
        System.out.println("Saved success!");

        System.out.println("Trying to get all...");
        System.out.println(employeeService.getAll());
        System.out.println("Get success!");

        System.out.println("Trying to update: " + employee);
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

    @RequestMapping(value = "/test/student")
    public String testStudent(){

        Student student = new Student();
        student.setState("training");
        student.setUniversity("BSU");
        student.setFaculty("FAMCS");
        student.setCourse(3);
        student.setGroup(6);
        student.setGraduationDate(java.sql.Date.valueOf("2017-07-01"));
        student.setRoleCurrentProject("Business Analytic");
        student.setTechsCurrentProject("Hibernate, Spring MVC, Java");

        System.out.println("Trying to save me...");
        System.out.println(studentService.save(student));
        System.out.println("Saved success!");

        System.out.println("Trying to update my role on curr project...");
        student.setRoleCurrentProject(student.getRoleCurrentProject() + ", backend developer, King!");
        System.out.println(studentService.save(student));
        System.out.println("Coronation success!");

        System.out.println("Trying to kill me...");
        studentService.delete(student);
        System.out.println("харе кришна.");

       return "login";
    }

    @RequestMapping(value = "/addMe")
    public String addMe() {

        User me = new User();
        me.setName("Alexey Kozlenkov");
        me.setEmail("nidecker95@gmail.com");
        me.setLogin("a.kozlenkov");
        me.setRole("stud");

        System.out.println("Trying to save me as user...");
        userService.save(me);
        System.out.println("Saved success!");

        Student alexeyKozlenkov = new Student();
        alexeyKozlenkov.setId(me.getId());
        alexeyKozlenkov.setState("training");
        alexeyKozlenkov.setUniversity("BSU");
        alexeyKozlenkov.setFaculty("FAMCS");
        alexeyKozlenkov.setCourse(3);
        alexeyKozlenkov.setGroup(6);
        alexeyKozlenkov.setGraduationDate(java.sql.Date.valueOf("2017-07-01"));
        alexeyKozlenkov.setRoleCurrentProject("Business Analytic");
        alexeyKozlenkov.setTechsCurrentProject("Hibernate, Spring MVC, Java");

        System.out.println("Trying to set property in me as user me as student...");
        me.setStudentInfo(alexeyKozlenkov);
        System.out.println("Saved success!");

        System.out.println("trying to save me as student...");
        studentService.save(alexeyKozlenkov);
        System.out.println("Saved success!");

        System.out.println("Trying to get student info about me using me as user...");
        User gettingMe = userService.getById(me.getId());
        System.out.println(gettingMe.getName() + " studies at " + gettingMe.getStudentInfo().getFaculty());
        System.out.println("Success!!!!!");

        System.out.println("---------------------------------------------------------------------");

        return "login";
    }

    @RequestMapping(value = "/addSergey")
    public String addSergey() {

        System.out.println("Creating user for Sergey...");
        User userTereshko = new User();
        userTereshko.setName("Sergey Tereshko");
        userTereshko.setEmail("tereshko@exadel.com");
        userTereshko.setRole("developer");
        userTereshko.setLogin("s.tereshko");
        System.out.println("Created success!");

        System.out.println("trying to save user for Segey...");
        System.out.println(userService.save(userTereshko));
        System.out.println("Saved success!");

        Employee sergeyTereshko = new Employee();

        sergeyTereshko.setId(userTereshko.getId());

        System.out.println("Trying to save Sergey...");
        System.out.println(employeeService.save(sergeyTereshko));
        System.out.println("Saved success!");
        userTereshko.setEmployeeInfo(sergeyTereshko);

        System.out.println("---------------------------------------------------------------------");


        return "login";
    }

    @RequestMapping(value = "/updateMe")
    public String updateMe(@RequestParam("stId") Long studentId) {

        Student studentToUpdate = studentService.getById(studentId);
        studentToUpdate.setRoleCurrentProject(studentToUpdate.getRoleCurrentProject() + ", The King!");
        studentService.save(studentToUpdate);

        return "login";
    }

    @RequestMapping(value = "/addFeedbackBySergeyAboutMe")
    public String addFeedback(@RequestParam("stId") Long studentId, @RequestParam("fbId") Long feedbackerId) {

        Student alexeyKozlenkov = studentService.getById(studentId);
        Employee sergeyTereshko =  employeeService.getById(feedbackerId);

        System.out.println("Sergey tries to make a feedback about me...");
        Feedback feedbackAboutKozlenkovByTereshko = new Feedback();
        feedbackAboutKozlenkovByTereshko.setStudent(alexeyKozlenkov);
        feedbackAboutKozlenkovByTereshko.setFeedbacker(sergeyTereshko);
        feedbackAboutKozlenkovByTereshko.setContent("goof");
        feedbackAboutKozlenkovByTereshko.setProfessionalCompetence("norm");
        feedbackAboutKozlenkovByTereshko.setAttitudeToWork("norm");
        feedbackAboutKozlenkovByTereshko.setCollectiveRelations("Everybody loves him!");
        feedbackAboutKozlenkovByTereshko.setProfessionalProgress("norm");
        feedbackAboutKozlenkovByTereshko.setNeedMoreHours(false);
        feedbackAboutKozlenkovByTereshko.setOnProject(true);
        feedbackAboutKozlenkovByTereshko.setFeedbackDate(Date.valueOf("2014-07-22"));

        System.out.println("Trying to save feedback by Sergey about me...");
        System.out.println(feedbackService.save(feedbackAboutKozlenkovByTereshko));
        System.out.println("Saved success!");

        System.out.println("Setting to Sergey his feedback..");
        sergeyTereshko.addFeedback(feedbackAboutKozlenkovByTereshko);
        System.out.println("Setted success!");

        System.out.println("Setting to me feedback by Sergey...");
        alexeyKozlenkov.addFeedback(feedbackAboutKozlenkovByTereshko);
        System.out.println("Setted success!");

        System.out.println("Trying to update me...");
        studentService.save(alexeyKozlenkov);
        System.out.println("Updated success!");

        System.out.println("Trying to update Sergey...");
        employeeService.save(sergeyTereshko);
        System.out.println("Updated success!");

        return "login";
    }

    @RequestMapping(value = "/deleteStudentTest")
    public String deleteStudentTest(@RequestParam("id") Long id) {
        studentService.delete(studentService.getById(id));
        return "login";
    }

    @RequestMapping(value= "/deleteEmployeeTest")
    public String deleteEmployeeTest(@RequestParam("id") Long id) {
        employeeService.delete(employeeService.getById(id));
        return "login";
    }
}
