package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.Document;
import com.exadel.studbase.domain.impl.Student;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.domain.init.Options;
import com.exadel.studbase.service.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


@Controller

@RequestMapping("/")
public class InfoPageController {

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

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String infoPage() {
        return "studentInfo";
    }

    @RequestMapping(value = "/info/getOptions", method = RequestMethod.GET)
    public void studentData(HttpServletRequest request, HttpServletResponse response) {
        try {
            Gson gson = new Gson();
            Options options = new Options();
            response.getWriter().print(gson.toJson(options, Options.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }

    @RequestMapping(value = "/info/getCommonInformation", method = RequestMethod.GET)
    public void manualData(HttpServletRequest request, HttpServletResponse response) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String currentStudentId = request.getParameter("studentId");
            User user = userService.getById(Long.parseLong(currentStudentId));//request.getQueryString()));
            System.out.println(user.getStudentInfo().toString());
            response.getWriter().print(gson.toJson(user, User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }

    @RequestMapping(value="/info/getDocuments", method = RequestMethod.GET)
    public void documents(HttpServletRequest request, HttpServletResponse response){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String currentStudentId = request.getParameter("studentId");
        Collection<Document> documents =  documentService.getAllForUser(Long.parseLong(currentStudentId));
        try {
            response.getWriter().print(gson.toJson(documents));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/info/postManualInformation", method = RequestMethod.POST)
    public void editManualInformation(HttpServletRequest request, HttpServletResponse response) {
        try {

            Map<String, String[]> params = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entry = params.entrySet();
            for (Map.Entry<String, String[]> element : params.entrySet()) {
                System.out.print("Key = " + element.getKey()); //+ ", Value = " + element.getValue()[0]);
                for (String s : element.getValue())
                    System.out.print("\t" + s);
                System.out.println();
            }
            User editedUser = userService.getById(Long.parseLong(request.getParameter("studentId")));
            editedUser.setName(request.getParameter("studentName"));
            editedUser.setLogin(request.getParameter("studentLogin"));
            editedUser.setPassword(request.getParameter("studentPassword"));
            editedUser.setEmail(request.getParameter("studentEmail"));
            editedUser.getStudentInfo().setState(request.getParameter("studentState"));
            userService.save(editedUser);
            studentService.save(editedUser.getStudentInfo());

            response.getWriter().print("{\"post\":\"ok\"}"); //string in double quotes
            response.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/info/postEducation", method = RequestMethod.POST)
    public void editEducation(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> params = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entry = params.entrySet();
            for (Map.Entry<String, String[]> element : params.entrySet()) {
                System.out.print("Key = " + element.getKey()); //+ ", Value = " + element.getValue()[0]);
                for (String s : element.getValue())
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

    @RequestMapping(value = "/info/postExadel", method = RequestMethod.POST)
    public void editExadelInformation(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> params = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entry = params.entrySet();
            for (Map.Entry<String, String[]> element : params.entrySet()) {
                System.out.print("Key = " + element.getKey()); //+ ", Value = " + element.getValue()[0]);
                for (String s : element.getValue())
                    System.out.print("\t" + s);
                System.out.println();
            }

            Student editedStudent = studentService.getById(Long.parseLong(request.getParameter("studentId")));
            editedStudent.setWorkingHours(Integer.parseInt(request.getParameter("studentWorkingHours")));
            editedStudent.setHireDate(Date.valueOf(request.getParameter("studentHireDate")));
            editedStudent.setBillable(Date.valueOf(request.getParameter("studentBillable")));
            editedStudent.setRoleCurrentProject(request.getParameter("studentRoleCurrentProject"));
            editedStudent.setTechsCurrentProject(request.getParameter("studentTechsCurrentProject"));
            studentService.save(editedStudent);


            response.getWriter().print("{\"post\":\"ok\"}"); //string in double quotes
            response.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/redirectInfo")
    public String loadInfo(@RequestParam("login") String login) {
        Long id = userService.getByLogin(login).getId();
        return "redirect:/info?id=" + id;
    }


}
