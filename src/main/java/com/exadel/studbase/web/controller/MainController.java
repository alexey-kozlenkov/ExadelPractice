package com.exadel.studbase.web.controller;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {

        System.out.println("log!!");

        return "listPage";
    }


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


    // Provide sanding list data
    @RequestMapping(value = "/list/data", method = RequestMethod.GET)
    public void listData(HttpServletRequest request, HttpServletResponse response){
        Gson gson = new Gson();
        String searchName = (String) request.getParameter("name");
        Object filter = request.getParameter("filter");

        Map<String, String[]> map = new HashMap<String, String[]>();
        System.out.println(searchName);
        System.out.println(gson.fromJson((String) filter, map.getClass()));


        //System.out.println("get it!!!  "+smth);


        List<User> someStudentList  = getTestList();

        try {
            response.getWriter().print(gson.toJson(someStudentList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
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
        userToUpdate.setRole("superadmin");
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
    public String addDocument(@RequestParam("stId") Long studentId) throws ParseException {

        Document document = new Document();
        document.setStudent(studentService.getById((studentId)));
        document.setDoctype("Certificate from Belhard");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        document.setIssueDate(new Date(dateFormat.parse("2014").getTime()));

        documentService.save(document);

        return "login";
    }

    @RequestMapping(value = "/updateDocument")
    public String updateDocument(@RequestParam("docId") Long documentId) throws ParseException {
        Document document = documentService.getById(documentId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date date = dateFormat.parse("2014-07-22");
        document.setIssueDate(new Date (date.getTime()));

        documentService.save(document);

        return "login";
    }

    @RequestMapping(value = "/deleteDocument")
    public String deleteDocument(@RequestParam("docId") Long documentId) throws ParseException {

        documentService.delete(documentService.getById(documentId));
        return "login";
    }

    @RequestMapping(value = "/addSkillType")
    public String addSkillType(@RequestParam("name") String skillName) {
        SkillType skillType = new SkillType();
        skillType.setName(skillName);
        skillTypeService.save(skillType);
        return "login";
    }

    @RequestMapping(value = "/updateSkillType")
    public String updateSkillType(@RequestParam("id") Long skillTypeId, @RequestParam("newName") String newName) {
        SkillType skillType = skillTypeService.getById(skillTypeId);
        skillType.setName(newName);
        skillTypeService.save(skillType);
        return "login";
    }

    @RequestMapping(value = "/deleteSkillType")
    public String deleteSkillType(@RequestParam("id") Long skillTypeId) {
        skillTypeService.delete(skillTypeService.getById(skillTypeId));
        return "login";
    }

    @RequestMapping (value = "/addSkillSet")
    public String addSkillSet(@RequestParam("usId") Long usertId, @RequestParam("skTId") Long skillTypeId) {
        SkillSet skillSet = new SkillSet();
        skillSet.setUser(userService.getById(usertId));
        skillSet.setSkillType(skillTypeService.getById(skillTypeId));
        skillSet.setLevel(5);

        skillSetService.save(skillSet);

        return "login";
    }

    @RequestMapping(value="/updateUser")
    public String updateUser(@RequestParam("usId") Long userId) {
        User userToUpdate = userService.getById(userId);
        return "login";
    }

    @RequestMapping(value="/getUsers")
    public String getUsers(@RequestParam("skTId")  Long skillTypeId) {
        ArrayList<User> users = (ArrayList<User>) skillSetService.getAllWithSkill(skillTypeId);
        return "login";
    }
}
