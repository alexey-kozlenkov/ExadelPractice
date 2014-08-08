package com.exadel.studbase.web.controller;

import com.exadel.studbase.dao.filter.Filter;
import com.exadel.studbase.dao.filter.FilterUtils;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.Student;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import com.exadel.studbase.service.*;
import com.exadel.studbase.service.filter.FilterDescription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by ala'n on 29.07.2014.
 */
@Controller
@Secured({"ROLE_CURATOR", "ROLE_FEEDBACKER", "ROLE_SUPERADMIN", "ROLE_OFFICE"})
@RequestMapping("/list")
public class ListPageController {
    @Autowired
    IUserService userService;
    @Autowired
    IStudentService studentService;
    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IStudentViewService studentViewService;
    @Autowired
    ISkillTypeService skillTypeService;
    @Autowired
    IMailService mailService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        System.out.println("List page redirect");
        return "list";
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getStudentsByRequest(@RequestParam("version") Long version,
                                       @RequestParam(value = "searchName", required = false) String desiredName,
                                       @RequestParam(value = "filter", required = false) String filterString) {
        //TODO: Optimise request queue (for big counts in short time)



        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        Map<String, String[]> map = new HashMap<String, String[]>();

        System.out.println("Version: " + version);
        System.out.println("Search name: " + desiredName);
        System.out.println("Filter: " + filterString);


        Collection<StudentView> result;

        if(!filterString.equals("")) {
            Map<String, Object> filterSpecification = new HashMap<String, Object>();
            filterSpecification = gson.fromJson((String) filterString, filterSpecification.getClass());

            Map<String, Filter<StudentView>> filter = new HashMap<String, Filter<StudentView>>();
            FilterUtils.buildFilterToSpecification(filter, filterSpecification);
            Collection<StudentView> viewByMainFilter = studentViewService.getView(filter);

            result = viewByMainFilter;

            ArrayList<String> skills = (ArrayList<String>) filterSpecification.get("skills");
            if(skills!=null) {
                Collection<StudentView> viewBySkills = studentViewService.filterBySkillTypeId(skills);
                result = CollectionUtils.intersection(result, viewBySkills);
            }

            StudResponse response = new StudResponse(version, result);
            return gson.toJson(response, StudResponse.class);
        } else {
            StudResponse response = new StudResponse(version,
                studentViewService.getViewByStudentName(desiredName));
            return gson.toJson(response, StudResponse.class);
        }
    }

    @RequestMapping(value = "/filterDescription", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getDescription() {
        boolean isCurator = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CURATOR"));

        Collection<User> listOfUsers = employeeService.getAllCurators();
        Map<Long, String> curators = new HashMap();
        for (User u : listOfUsers) {
            curators.put(u.getId(), u.getName());
        }
        Collection<SkillType> listOfSkillTypes = skillTypeService.getAll();
        Map<Long, String> skills = new HashMap();
        for (SkillType st : listOfSkillTypes) {
            skills.put(st.getId(), st.getName());
        }

        List<FilterDescription.FilterDescriptor> description
                = FilterDescription.createFilterDescription(isCurator, curators, skills);

        Gson gson = new Gson();

        return gson.toJson(description);
    }


    @Secured("ROLE_SUPERADMIN")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public String sendMail(@RequestParam("students") String students,
                           @RequestParam(value = "subject", required = false) String subject,
                           @RequestParam("message") String body) {
        Gson gson = new Gson();

        Long[] studentId = gson.fromJson(students, Long[].class);
        List<String> inaccessibleEmail = new ArrayList<String>();
        for (Long id : studentId) {
            User user = userService.getById(id);
            String userName = user.getName();
            if(user.getEmail() != null){
                if (!mailService.sendMail(user.getEmail(), subject, body)) {
                    inaccessibleEmail.add(userName + " ( " + user.getEmail() + " )");
                }
            }else{
                inaccessibleEmail.add(userName + "( haven't mail )");
            }
        }
        return gson.toJson(inaccessibleEmail);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView export(@RequestParam("students") String students) {
        Gson gson = new Gson();
        Long[] studentId = gson.fromJson(students, Long[].class);
        List<User> listOfUsers = new ArrayList<User>();
        for (Long id : studentId) {
            listOfUsers.add(userService.getById(id));
        }
        return new ModelAndView("excelView", "users", listOfUsers);
    }

    @Secured("ROLE_SUPERADMIN")
    @RequestMapping(value = "/quickAdd", method = RequestMethod.POST)
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
