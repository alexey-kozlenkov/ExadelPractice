package com.exadel.studbase.web.controller;

import com.exadel.studbase.service.filter.Filter;
import com.exadel.studbase.service.filter.FilterUtils;
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
    IStudentViewService employeeViewService;
    @Autowired
    ISkillTypeService skillTypeService;
    @Autowired
    IMailService mailService;
    @Autowired
    ICuratoringService curatoringService;

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
                                       @RequestParam(value = "filter", required = false) String filterString,
                                       @RequestParam(value = "isStudent") boolean isStudent) {
        //TODO: Optimise request queue (for big counts in short time)

        System.out.println("Version: " + version);
        System.out.println("Search name: " + desiredName);
        System.out.println("Filter: " + filterString);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Collection<StudentView> result = null;
        Collection<StudentView> search = studentViewService.getViewByStudentName(desiredName);

        if (isStudent) {
            if (!filterString.equals("")) {
                Map<String, Object> filterSpecification = new HashMap<String, Object>();
                Map<String, Filter<StudentView>> mainFilter = new HashMap<String, Filter<StudentView>>();
                ArrayList<String> skills = (ArrayList<String>) filterSpecification.get("skills");

                filterSpecification = gson.fromJson(filterString, filterSpecification.getClass());
                FilterUtils.buildFilterToSpecification(mainFilter, filterSpecification);

                if (mainFilter.size() > 0) {
                    result = studentViewService.getView(mainFilter);
                }

                if (skills != null) {
                    Collection<StudentView> viewBySkills = studentViewService.getViewBySkills(skills);

                    result = (result != null) ? CollectionUtils.intersection(result, viewBySkills)
                            : viewBySkills;
                }

                if (filterSpecification.get("curator") != null) {
                    Long curatorId = (Long.parseLong((String) filterSpecification.get("curator")));
                    Collection<StudentView> viewByCurator = curatoringService.getAllStudentsForEmployee(curatorId);

                    result = (result != null) ? CollectionUtils.intersection(result, viewByCurator)
                            : viewByCurator;
                }
            }

            result = (result != null && search != null) ? CollectionUtils.intersection(result, search) :
                    (result != null) ? result : search;
            StudResponse response = new StudResponse(version, result);
            return gson.toJson(response, StudResponse.class);
        }

        result = employeeViewService.getAll();
        StudResponse response = new StudResponse(version, result);
        return gson.toJson(response, StudResponse.class);
    }

    @RequestMapping(value = "/filterDescription", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getDescription() {
        boolean isCurator = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CURATOR"));

        Map<Long, String> curators = null;
        if (!isCurator) {
            Collection<User> listOfUsers = employeeService.getAllCurators();
            curators = new HashMap<Long, String>();
            for (User u : listOfUsers) {
                curators.put(u.getId(), u.getName());
            }
        }
        Collection<SkillType> listOfSkillTypes = skillTypeService.getAll();
        Map<Long, String> skills = new HashMap<Long, String>();
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

            if (user.getEmail() != null) {
                if (!mailService.sendMail(user.getEmail(), subject, body)) {
                    inaccessibleEmail.add(user.getName() + " ( " + user.getEmail() + " )");
                }
            } else {
                inaccessibleEmail.add(user.getName() + "( haven't mail )");
            }
        }

        /*User user = userService.getById(4L);
        mailService.sendMail("vasia-94@tut.by", "xxxPASSWORD", user.getEmail(), "s", "b");*/

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
