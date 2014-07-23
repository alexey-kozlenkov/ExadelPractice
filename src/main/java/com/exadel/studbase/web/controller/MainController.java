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
        newUser.setRole("updated_role");
        userService.save(newUser);
        System.out.println("Updated success!");

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

    @RequestMapping(value = "/test/student")
    public String testStudent(){

        Student student = new Student();
        student.setName("Alexey Kozlenkov");
        student.setEmail("nidecker95@gmail.com");
        student.setState("training");
        student.setUniversity("BSU");
        student.setFaculty("FAMCS");
        student.setCourse(3);
        student.setGroup(6);
        student.setGraduation_date(java.sql.Date.valueOf("2017-07-01"));
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

        Student alexeyKozlenkov = new Student();
        alexeyKozlenkov.setName("Alexey Kozlenkov");
        alexeyKozlenkov.setEmail("nidecker95@gmail.com");
        alexeyKozlenkov.setState("training");
        alexeyKozlenkov.setUniversity("BSU");
        alexeyKozlenkov.setFaculty("FAMCS");
        alexeyKozlenkov.setCourse(3);
        alexeyKozlenkov.setGroup(6);
        alexeyKozlenkov.setGraduation_date(java.sql.Date.valueOf("2017-07-01"));
        alexeyKozlenkov.setRoleCurrentProject("Business Analytic");
        alexeyKozlenkov.setTechsCurrentProject("Hibernate, Spring MVC, Java");

        System.out.println("Trying to save me....");
        System.out.println(studentService.save(alexeyKozlenkov));
        System.out.println("Saved success!");

        System.out.println("Creating user for me...");
        User userKozlenkov = new User();
        userKozlenkov.setLogin("a.kozlenkov");
        userKozlenkov.setRole("stud");
        userKozlenkov.setStudentInfo(alexeyKozlenkov);
        System.out.println("Created success!");

        System.out.println("trying to save user for me..");
        System.out.println(userService.save(userKozlenkov));
        System.out.println("Saved success!");

        System.out.println("---------------------------------------------------------------------");

        return "login";
    }

    @RequestMapping(value = "/addSergey")
    public String addSergey() {

        Employee sergeyTereshko = new Employee();
        sergeyTereshko.setName("Sergey Tereshko");
        sergeyTereshko.setEmail("tereshko@exadel.com");

        System.out.println("Trying to save Sergey...");
        System.out.println(employeeService.save(sergeyTereshko));
        System.out.println("Saved success!");

        System.out.println("Creating user for Sergey...");
        User userTereshko = new User();
        userTereshko.setRole("developer");
        userTereshko.setLogin("s.tereshko");
        userTereshko.setEmployeeInfo(sergeyTereshko);
        System.out.println("Created success!");

        System.out.println("trying to save user for Segey...");
        System.out.println(userService.save(userTereshko));
        System.out.println("Saved success!");

        System.out.println("---------------------------------------------------------------------");


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
