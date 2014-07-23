package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.init.Options;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by ala'n on 10.07.2014.
 */

@Controller
//@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/")
public class MainController {

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
    public void listData(HttpServletRequest request, HttpServletResponse response){
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


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/info/getOptions", method = RequestMethod.GET)
    public void studentData(HttpServletRequest request, HttpServletResponse response){
        //Object smth = request.getAttribute("id");
        System.out.println("get it2q222222222222222222222222222!!!");
        try {
            Gson gson = new Gson();
            Options options = new Options();
            response.getWriter().print(gson.toJson(options,Options.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }


    @RequestMapping(value = "/info/get", method = RequestMethod.GET)
    public void optionsData(HttpServletRequest request, HttpServletResponse response){
        //Object smth = request.getAttribute("id");
        System.out.println("get it3333333333333333333!!!");
//        try {
            Gson gson = new Gson();
           // Student student = new Student("Cherry", "12345", "Bulllls@yandex.ru","Ruslan Filistovich","employee","working");
           // response.getWriter().print(gson.toJson(student,Student.class));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        response.setStatus(200);
    }



    @RequestMapping(value="/info", method = RequestMethod.GET)
    public String infoPage(){
        return "studentInfo";
    }




    @RequestMapping(value="/info/post",method = RequestMethod.POST)
    public void editInformation(HttpServletRequest request, HttpServletResponse response){
        System.out.println("post it44444!");
        try {

            Map<String,String[]> params =  request.getParameterMap();
            Set<Map.Entry<String,String[]>> entry = params.entrySet();
            for (Map.Entry<String, String[]> element : params.entrySet()) {
                System.out.println("Key = " + element.getKey() + ", Value = " + element.getValue()[0]);
            }
            response.getWriter().print("{\"post\":\"ok\"}"); //string in double quotes
            response.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
