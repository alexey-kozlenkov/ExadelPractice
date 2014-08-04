package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.Student;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.service.IMailService;
import com.exadel.studbase.service.IStudentService;
import com.exadel.studbase.service.IStudentViewService;
import com.exadel.studbase.service.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by ala'n on 29.07.2014.
 */
@Controller
@Secured({"ROLE_CURATOR", "ROLE_FEEDBACKER", "ROLE_SUPERADMIN", "ROLE_OFFICE"})
@RequestMapping("/")
public class ListPageController {
    @Autowired
    IUserService userService;
    @Autowired
    IStudentService studentService;
    @Autowired
    IStudentViewService studentViewService;
    @Autowired
    IMailService mailService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        System.out.println("List page redirect");
        return "list";
    }

    // Provide sanding list data
    @RequestMapping(value = "/list/data", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getStudentsByRequest(@RequestParam("version") Long version,
                                       @RequestParam(value = "searchName", required = false) String desiredName,
                                       @RequestParam(value = "filter", required = false) String filter) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        Map<String, String[]> map = new HashMap<String, String[]>();

        System.out.println("Version: " + version);
        System.out.println("Search name: " + desiredName);
        System.out.println("Filter: " + filter);
        //    System.out.println(gson.fromJson((String) filter, map.getClass()));

        StudResponse response = new StudResponse(version,
                studentViewService.getViewByStudentName(desiredName));

        return gson.toJson(response, StudResponse.class);
    }

    @Secured("ROLE_SUPERADMIN")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/list/sendMail", method = RequestMethod.POST)
    public String sendMail(@RequestParam("students") String students,
                           @RequestParam(value = "subject", required = false) String subject,
                           @RequestParam("message") String body) {
        Gson gson = new Gson();

        Long[] studentId = gson.fromJson(students, Long[].class);

        List<String> inaccessibleEmail = new ArrayList<String>();
        for (Long id : studentId) {
            User user = userService.getById(id);
            if (!mailService.sendMail(user.getEmail(), subject, body)) {
                inaccessibleEmail.add(user.getEmail());
            }
        }
        return gson.toJson(inaccessibleEmail);
    }

    @RequestMapping(value = "/list/export", method = RequestMethod.GET)
    public ModelAndView export(@RequestParam("students") String students) {
        Gson gson = new Gson();

        Long[] studentId = gson.fromJson(students, Long[].class);
        List<User> listOfUsers = new ArrayList<User>();
        for (Long id : studentId) {
            User user = userService.getById(id);
            listOfUsers.add(user);
        }

        return new ModelAndView("excelView", "users", listOfUsers);
    }

    @Secured("ROLE_SUPERADMIN")
    @RequestMapping(value = "/list/quickAdd", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addUser(@RequestParam("name") String name,
                        @RequestParam("login") String login,
                        @RequestParam("state") String state) {

        User newUser = new User();
        newUser.setName(name);
        newUser.setLogin(login);
        newUser.setRole("ROLE_STUDENT");
        userService.save(newUser);
        Student newStudent = new Student();
        newStudent.setId(newUser.getId());
        newStudent.setState(state);
        newUser.setStudentInfo(newStudent);
        userService.save(newUser);
    }

    public class StudResponse {
        private Long version;
        private Collection<StudentView> studentViews;

        public StudResponse() {
        }

        public StudResponse(Long version, Collection<StudentView> studentViews) {
            this.version = version;
            this.studentViews = studentViews;
        }

        public Long getVersion() {
            return version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

        public Collection<StudentView> getStudentViews() {
            return studentViews;
        }

        public void setStudentViews(Collection<StudentView> studentViews) {
            this.studentViews = studentViews;
        }
    }
}
