package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.init.Options;
import com.exadel.studbase.domain.user.User;
import com.exadel.studbase.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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

            response.getWriter().print(gson.toJson(new Object[]{new String("Smth"), 123}));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String infoPage() {
        return "studentInfo";
    }


    @RequestMapping(value = "/info/getOptions", method = RequestMethod.GET)
    public void studentData(HttpServletRequest request, HttpServletResponse response) {
        //Object smth = request.getAttribute("id");
        System.out.println("get it2q222222222222222222222222222!!!");
        try {
            Gson gson = new Gson();
            Options options = new Options();
            response.getWriter().print(gson.toJson(options, Options.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }


    @RequestMapping(value = "/info/getInformation", method = RequestMethod.GET)
    public void optionsData(HttpServletRequest request, HttpServletResponse response) {
        // System.out.println("get it3333333333333333333!!!");
        try {
            Gson gson = new Gson();
            //System.out.println(request.getQueryString());
            User user = userService.getById(Long.parseLong(request.getQueryString()));
            System.out.println(user);
            response.getWriter().print(gson.toJson(user, User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
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


    @RequestMapping(value = "/info/postManualInformation", method = RequestMethod.POST)
    public void editInformation(HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("post it44444!");
        try {

//            Map<String, String[]> params = request.getParameterMap();
//            Set<Map.Entry<String, String[]>> entry = params.entrySet();
//            for (Map.Entry<String, String[]> element : params.entrySet()) {
//                System.out.print("Key = " + element.getKey()); //+ ", Value = " + element.getValue()[0]);
//                for(String s : element.getValue())
//                    System.out.print("\t" + s);
//                System.out.println();
//            }
            User editedUser = userService.getById(Long.parseLong(request.getParameter("studentId")));
            editedUser.setName(request.getParameter("studentName"));
            editedUser.setLogin(request.getParameter("studentLogin"));
            editedUser.setPassword(request.getParameter("studentPassword"));
            editedUser.setEmail(request.getParameter("studentEmail"));
            editedUser.setRole(request.getParameter("studentRole"));
            editedUser.getStudentInfo().setState(request.getParameter("studentState"));
            userService.save(editedUser);
            studentService.save(editedUser.getStudentInfo());

            response.getWriter().print("{\"post\":\"ok\"}"); //string in double quotes
            response.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
