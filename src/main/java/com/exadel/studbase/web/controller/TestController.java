package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.*;
import com.exadel.studbase.service.*;
import com.exadel.studbase.service.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ala'n on 29.07.2014.
 */
@Controller
//@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/test/")
public class TestController {

    public static List<User> testList;
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




    @RequestMapping(value = "/secured/index", method = RequestMethod.GET)
    public String secIndex() {

        System.out.println("log!!");

        return "secured/index";
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
        mv.addObject("id", id);
//        ${user.name}
        return mv;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse response) {

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

    @RequestMapping(value = "/add")
    public void add() {
        User user = userService.getById(Long.parseLong("32"));
        user.setEmail("test2test");
        userService.save(user);

        System.out.println(userService.save(user));
        System.out.println("blabla");
    }

    @RequestMapping(value = "/filter")
    public void filter() {
        Map<String, String[]> filterSpecification = new HashMap<String, String[]>();

        Map<String, Filter<StudentView>> filter = new HashMap<String, Filter<StudentView>>();
        String paramName = "university";
        String[] paramValue = new String[1];
        paramValue[0] = "BSU";
        filterSpecification.put(paramName, paramValue);
        paramName = "graduation year";
        String[] newParam = new String[1];
        newParam[0] = "2017";
        filterSpecification.put(paramName, newParam);
        paramName = "billable";
        String[] newNewParam = new String[1];
        newNewParam[0] = "true";
        filterSpecification.put(paramName, newNewParam);

       // FilterUtils.buildFilterToSpecification(filter, filterSpecification);
        Collection<StudentView> mainFilter = studentViewService.getView(filter);

       // Collection<StudentView> filterBySkills = studentViewService.filterBySkillTypeId(new String[] {"5"});

        //Collection<StudentView> result = CollectionUtils.intersection(mainFilter, filterBySkills);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/mega", method = RequestMethod.POST)
    public String megaF(@RequestParam(value = "message", required = false) String mess) {
        System.out.println(mess);
        return "Good";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public void feedback () {
        Collection<StudentView> view = studentViewService.getAll();
    }

}
