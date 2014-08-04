package com.exadel.studbase.web.controller;

import com.exadel.studbase.dao.ICuratoringDAO;
import com.exadel.studbase.domain.impl.Student;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.domain.init.Options;
import com.exadel.studbase.security.MySecurityUser;
import com.exadel.studbase.service.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;


@Controller
@Secured("IS_AUTHENTICATED_FULLY")
@RequestMapping("/info")
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
    @Autowired
    ICuratoringDAO curatoringService;

    @RequestMapping(method = RequestMethod.GET)
    public String infoPage(@RequestParam("id") Long id) {
        MySecurityUser principal = (MySecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT")) && !principal.getId().equals(id)) {
            throw new AccessDeniedException("Pfff");
        }
        return "studentInfo";
    }

    @RequestMapping(value = "/getOptions", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String studentData() {
            Gson gson = new Gson();
            Options options = new Options();
            return gson.toJson(options, Options.class);
    }

    @RequestMapping(value = "/getManualInformation", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String manualData(@RequestParam("studentId") Long studentId) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            User user = userService.getById(studentId);
            System.out.println(user.getStudentInfo().toString());
        return gson.toJson(user, User.class);
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_OFFICE", "ROLE_STUDENT"})
    @RequestMapping(value = "/postManualInformation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String editManualInformation(@RequestParam("studentId") Long id,
                                        @RequestParam("studentName") String name,
                                        @RequestParam("studentLogin") String login,
                                        @RequestParam("studentPassword") String password,
                                        @RequestParam("studentEmail") String email,
                                        @RequestParam("studentState") String state) {
            User editedUser = userService.getById(id);
            editedUser.setName(name);
            editedUser.setLogin(login);
            editedUser.setPassword(password);
            editedUser.setEmail(email);
            editedUser.getStudentInfo().setState(state);

            userService.save(editedUser);

             return "{\"post\":\"ok\"}"; //string in double quotes
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_OFFICE", "ROLE_STUDENT"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/postEducation", method = RequestMethod.POST)
    public String editEducation(@RequestParam("studentId") Long id,
                              @RequestParam("studentUniversity") String university,
                              @RequestParam("studentFaculty") String faculty,
                              @RequestParam("studentCourse") int course,
                              @RequestParam("studentGroup") int group,
                              @RequestParam("studentGraduationDate") Date graduationDate) {

            Student editedStudent = studentService.getById(id);
            editedStudent.setUniversity(university);
            editedStudent.setFaculty(faculty);
            editedStudent.setCourse(course);
            editedStudent.setGroup(group);
            editedStudent.setGraduationDate(graduationDate);
            studentService.save(editedStudent);

            return ("{\"post\":\"ok\"}");
    }

    @RequestMapping(value = "/redirectInfo")
    public String loadInfo(@RequestParam("login") String login) {
        Long id = userService.getByLogin(login).getId();
        return "redirect:/info?id=" + id;
    }
}
