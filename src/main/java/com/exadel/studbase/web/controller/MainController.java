package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.entity.Student;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

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


    public static List<Student> testList;
    public static List<Student> getTestList(){
        if(testList==null){
            testList = new ArrayList<Student>();
            Student st;
            Random rand  =new Random();
            for(int id_ = 1; id_<1000; id_++) {
                st = new Student();

                st.setId(id_);
                st.setName("User_" + id_);
                st.setLogin("Login_" + id_);
                st.setPassword("");
                st.setRole("user");
                st.setEmail("email_" + id_ + "@mail.ru");
                st.setCourse((id_ % 10 < 5 ? 1 : 2));
                st.setStudentGroup(id_ % 10);
                st.setUniversity((id_ < 10) ? "MSU" : "BSU");
                st.setFaculty((id_ < 10) ? "IP" : "FPM");
                st.setWokingHours(4 + rand.nextInt(20));
                if(rand.nextBoolean())
                    st.setBillable(null);//TODO!
                else
                    st.setBillable(null);
                st.setGraduationDate(null);//TODO!
                st.setRoleCurrentProject(rand.nextBoolean() ? "developer" : "test");
                st.setTechnologyCurrentProject(rand.nextBoolean() ? "java" : "php");
                st.setEnglishLevel("advansed");
                testList.add(st);
            }
        }
        return testList;
    }


    class SearchParam{
        public String name;
    }

    // Provide sanding list data
    @RequestMapping(value = "/list/data", method = RequestMethod.GET)
    public void listData(HttpServletRequest request, HttpServletResponse response){
        Gson gson = new Gson();
        Object smth = request.getParameter("name");

        if(smth!=null){
            //gson.fromJson((String)smth, SearchParam.class).name
        }

        System.out.println("get it!!!  "+smth);


        List<Student> someStudentList  = getTestList();

        try {


            response.getWriter().print(gson.toJson(someStudentList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }
}
