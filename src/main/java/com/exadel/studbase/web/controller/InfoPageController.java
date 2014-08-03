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
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.Map;
import java.util.Set;


@Controller
@Secured("IS_AUTHENTICATED_FULLY")
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
    @Autowired
    ICuratoringDAO curatoringService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String infoPage(@RequestParam("id") Long id) {
        MySecurityUser principal = (MySecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT")) && !principal.getId().equals(id)) {
            throw new AccessDeniedException("Pfff");
        }
        return "studentInfo";
    }

    @RequestMapping(value = "/info/getOptions", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String studentData(HttpServletResponse response) {
        //try {
            Gson gson = new Gson();
            Options options = new Options();
            //response.getWriter().print(gson.toJson(options, Options.class));
            return gson.toJson(options, Options.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //response.setStatus(200);
    }

    @RequestMapping(value = "/info/getManualInformation", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String manualData(@RequestParam("studentId") Long studentId, HttpServletResponse response) {
        //try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            User user = userService.getById(studentId);
            System.out.println(user.getStudentInfo().toString());
            //response.getWriter().print(gson.toJson(user, User.class));
        return gson.toJson(user, User.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //response.setStatus(200);
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_OFFICE", "ROLE_STUDENT"})
    @RequestMapping(value = "/info/postManualInformation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String editManualInformation(@RequestParam("studentId") Long id,
                                        @RequestParam("studentName") String name,
                                        @RequestParam("studentLogin") String login,
                                        @RequestParam("studentPassword") String password,
                                        @RequestParam("studentEmail") String email,
                                        @RequestParam("studentState") String state) {
//
//            Map<String, String[]> params = request.getParameterMap();
//            Set<Map.Entry<String, String[]>> entry = params.entrySet();
//            for (Map.Entry<String, String[]> element : params.entrySet()) {
//                System.out.print("Key = " + element.getKey()); //+ ", Value = " + element.getValue()[0]);
//                for (String s : element.getValue())
//                    System.out.print("\t" + s);
//                System.out.println();
//            }
//            User editedUser = userService.getById(Long.parseLong(request.getParameter("studentId")));
//            editedUser.setName(request.getParameter("studentName"));
//            editedUser.setLogin(request.getParameter("studentLogin"));
//            editedUser.setPassword(request.getParameter("studentPassword"));
//            editedUser.setEmail(request.getParameter("studentEmail"));
//            //editedUser.setRole(request.getParameter("studentRole"));
//            editedUser.getStudentInfo().setState(request.getParameter("studentState"));
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
    @RequestMapping(value = "/info/postEducation", method = RequestMethod.POST)
    public void editEducation(@RequestParam("studentId") Long id,
                              @RequestParam("studentUniversity") String university,
                              @RequestParam("studentFaculty") String faculty,
                              @RequestParam("studentCourse") int course,
                              @RequestParam("studentGroup") int group,
                              @RequestParam("studentGraduationDate") Date graduationDate,
                              HttpServletResponse response) {
        try {

//            Map<String, String[]> params = request.getParameterMap();
//            Set<Map.Entry<String, String[]>> entry = params.entrySet();
//            for (Map.Entry<String, String[]> element : params.entrySet()) {
//                System.out.print("Key = " + element.getKey()); //+ ", Value = " + element.getValue()[0]);
//                for (String s : element.getValue())
//                    System.out.print("\t" + s);
//                System.out.println();
//            }

            Student editedStudent = studentService.getById(id);
            editedStudent.setUniversity(university);
            editedStudent.setFaculty(faculty);
            editedStudent.setCourse(course);
            editedStudent.setGroup(group);
            editedStudent.setGraduationDate(graduationDate);
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
