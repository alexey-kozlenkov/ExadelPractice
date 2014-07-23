package com.exadel.studbase.web.controller;

import com.exadel.studbase.web.domain.document.Document;
import com.exadel.studbase.web.domain.employee.Employee;
import com.exadel.studbase.web.domain.feedback.Feedback;
import com.exadel.studbase.web.domain.student.Student;
import com.exadel.studbase.web.domain.user.User;
import com.exadel.studbase.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    IDocumentService documentService;

    // @Autowired
    // IStudBaseMainService service;


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

    @RequestMapping(value = "/updateStudent")
    public String updateStudent(@RequestParam("stId") Long studentId) {

        Student studentToUpdate = studentService.getById(studentId);
        studentToUpdate.setRoleCurrentProject(studentToUpdate.getRoleCurrentProject() + ", The King!");
        studentService.save(studentToUpdate);

        return "login";
    }

    @RequestMapping(value = "/updateEmployee")
    public String updateEmployee(@RequestParam("empId") Long employeeId) {
        User userToUpdate = userService.getById(employeeId);
        userToUpdate.setRole("developer");
        userService.save(userToUpdate);
        return "login";
    }

    @RequestMapping(value = "/addFeedback")
    public String addFeedback(@RequestParam("stId") Long studentId, @RequestParam("fbId") Long feedbackerId) {

        Student student = studentService.getById(studentId);
        Employee employee =  employeeService.getById(feedbackerId);

        Feedback feedback = new Feedback();
        feedback.setStudent(student);
        feedback.setFeedbacker(employee);
        feedback.setContent("Nice girl, but she should stop hmurit' brovi");
        feedback.setProfessionalCompetence("norm");
        feedback.setAttitudeToWork("norm");
        feedback.setCollectiveRelations("Cats love her");
        feedback.setProfessionalProgress("norm");
        feedback.setNeedMoreHours(false);
        feedback.setOnProject(true);
        feedback.setFeedbackDate(Date.valueOf("2014-07-22"));

        System.out.println(feedbackService.save(feedback));
        System.out.println("Saved success!");

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

    @RequestMapping(value = "/addDocument")
    public String addDocument(@RequestParam("stId") Long studentId) {

        Document document = new Document();
        document.setStudent(studentService.getById((studentId)));
        document.setDoctype("Certificate from school of kings");
        document.setIssueDate(new Date(20140723));

        documentService.save(document);

        return "login";
    }
}
