package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.service.IStudentViewService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ala'n on 29.07.2014.
 */
@Controller
@Secured({"ROLE_CURATOR", "ROLE_FEEDBACKER", "ROLE_SUPERADMIN", "ROLE_OFFICE"})
@RequestMapping("/")
public class ListPageController {
    @Autowired
    IStudentViewService studentViewService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        System.out.println("List page redirect");
        return "listPage";
    }

    // Provide sanding list data
    @RequestMapping(value = "/list/data", method = RequestMethod.GET)
    public void listData(HttpServletRequest request, HttpServletResponse response){
        Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd").create();//= new Gson();
        String searchName = (String) request.getParameter("name");
        Object filter = request.getParameter("filter");

        Map<String, String[]> map = new HashMap<String, String[]>();
        System.out.println(searchName);
        System.out.println(gson.fromJson((String) filter, map.getClass()));

        Collection<StudentView> studList  = studentViewService.getAll();

        try {
            response.getWriter().print(gson.toJson(studList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
    }
}
