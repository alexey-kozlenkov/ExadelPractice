package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.*;
import com.exadel.studbase.security.MySecurityUser;
import com.exadel.studbase.service.filter.Filter;
import com.exadel.studbase.service.filter.FilterUtils;
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
    IEmployeeViewService employeeViewService;
    @Autowired
    ISkillTypeService skillTypeService;
    @Autowired
    IMailService mailService;
    @Autowired
    ICuratoringService curatoringService;
    @Autowired
    IUniversityService universityService;
    @Autowired
    IFacultyService facultyService;
    @Autowired
    IFeedbackService feedbackService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        System.out.println("List page redirect");
        return "list.xhtml";
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
        Collection result = null;

        if (isStudent) {
            Collection search = studentViewService.getViewByStudentName(desiredName);

            if (!filterString.equals("")) {
                Map filterSpecification = new HashMap<String, Object>();
                filterSpecification = gson.fromJson(filterString, filterSpecification.getClass());

                Map mainFilter = new HashMap<String, Filter<StudentView>>();
                FilterUtils.buildFilterToSpecification(mainFilter, filterSpecification);

                if (mainFilter.size() > 0) {
                    result = studentViewService.getView(mainFilter);
                }

                ArrayList skills = (ArrayList<String>) filterSpecification.get("skills");
                if (skills != null) {
                    Collection<StudentView> viewBySkills = studentViewService.getViewBySkills(skills);
                    result = (result != null) ? CollectionUtils.intersection(result, viewBySkills) : viewBySkills;
                }

                if (filterSpecification.get("curator") != null) {
                    Long curatorId = (Long.parseLong((String) filterSpecification.get("curator")));
                    Collection<StudentView> viewByCurator = curatoringService.getAllStudentsForEmployee(curatorId);

                    result = (result != null) ? CollectionUtils.intersection(result, viewByCurator) : viewByCurator;
                }
            }

            MySecurityUser principal = (MySecurityUser) SecurityContextHolder.getContext().getAuthentication().
                    getPrincipal();

            if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CURATOR"))) {
                Long curatorId = principal.getId();
                Collection<StudentView> viewByCurator = curatoringService.getAllStudentsForEmployee(curatorId);
                result = (result != null) ? CollectionUtils.intersection(result, viewByCurator) : viewByCurator;
            }

            result = (result != null && search != null) ? CollectionUtils.intersection(result, search) :
                    (result != null) ? result : search;
            if (desiredName.equals("")) {
                Collections.sort((List) result);
            }
            ListResponse response = new ListResponse(version, result);
            return gson.toJson(response, ListResponse.class);
        } else {
            result = employeeViewService.getViewByEmployeeName(desiredName);
            reformatRoles(result);
            ListResponse response = new ListResponse(version, result);
            return gson.toJson(response, ListResponse.class);
        }
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

        Collection<University> universities = universityService.getAll();
        String[] university = new String[universities.size()];
        Iterator<University> universityIterator = universities.iterator();
        for (int i = 0; i < universities.size(); i++) {
            university[i] = universityIterator.next().getName();
        }

        Collection<Faculty> faculties = facultyService.getAll();
        String[] faculty = new String[faculties.size()];
        Iterator<Faculty> facultyIterator = faculties.iterator();
        for (int i = 0; i < faculties.size(); i++) {
            faculty[i] = facultyIterator.next().getName();
        }

        List<FilterDescription.FilterDescriptor> description
                = FilterDescription.createFilterDescription(isCurator, curators, skills, university, faculty);

        Gson gson = new Gson();

        return gson.toJson(description);
    }

    @Secured("ROLE_SUPERADMIN")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/curatorList", method = RequestMethod.GET)
    public String getAllCurators() {
        Gson gson = new Gson();
        Map<Long, String> curators = new HashMap<Long, String>();
        Collection<User> listOfUsers = employeeService.getAllCurators();
        for (User u : listOfUsers) {
            curators.put(u.getId(), u.getName());
        }
        return gson.toJson(curators);
    }

    @Secured("ROLE_SUPERADMIN")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public String sendMail(@RequestParam("usermail") String from,
                           @RequestParam("password") String password,
                           @RequestParam("students") String students,
                           @RequestParam(value = "subject", required = false) String subject,
                           @RequestParam("message") String body) {
        Gson gson = new Gson();
        Long[] studentId = gson.fromJson(students, Long[].class);
        List<String> inaccessibleEmail = new ArrayList<String>();

        for (Long id : studentId) {
            User user = userService.getById(id);

            if (user.getEmail() != null || user.getEmail().equals("")) {
                if (!mailService.sendMail(from, password, user.getEmail(), subject, body)) {
                    inaccessibleEmail.add(user.getName() + " ( " + user.getEmail() + " )");
                }
            } else {
                inaccessibleEmail.add(user.getName() + "( haven't mail )");
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
                        @RequestParam("state") String state,
                        @RequestParam("isStudent") Boolean isStudent) {

        if(isStudent) {
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
        } else {
            User newUser = new User();
            newUser.setName(name);
            newUser.setLogin(login);
            newUser.setRole(state);
            userService.save(newUser);
            Employee newEmployee = new Employee();
            newEmployee.setId(newUser.getId());
            newUser.setEmployeeInfo(newEmployee);
            userService.save(newUser);
        }
    }

    @Secured("ROLE_SUPERADMIN")
    @RequestMapping(value = "/appoint", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void appointCurators(@RequestParam("curatorsId") String curators,
                                @RequestParam("studentsId") String students) {
        Gson gson = new Gson();
        Long[] studentsIds = gson.fromJson(students, Long[].class);
        Long[] curatorsIds = gson.fromJson(curators, Long[].class);
        curatoringService.appointCuratorsToStudents(studentsIds, curatorsIds);
        feedbackService.addFeedbacksWhenAppointingCurators(studentsIds, curatorsIds);
    }

    public class ListResponse {
        private Long version;
        private Collection views;

        public ListResponse() {
        }

        public ListResponse(Long version, Collection studentViews) {
            this.version = version;
            this.views = studentViews;
        }

        public Long getVersion() {
            return version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

        public Collection getViews() {
            return views;
        }

        public void setViews(Collection views) {
            this.views = views;
        }
    }

    private void reformatRoles(Collection<EmployeeView> view) {
        for (EmployeeView employeeView : view) {
            String[] roles = employeeView.getRole().split(";");
            String resultRole = "";
            for (String role : roles) {
                if (role.equalsIgnoreCase("ROLE_CURATOR")) {
                    resultRole += resultRole.equals("") ? "Curator" : ", Curator";
                } else if (role.equalsIgnoreCase("ROLE_FEEDBACKER")) {
                    resultRole += resultRole.equals("") ? "Feedbacker" : ", Feedbacker";
                } else if (role.equalsIgnoreCase("ROLE_OFFICE")) {
                    resultRole += resultRole.equals("") ? "Personnel officer" : ", Personal officer";
                } else if (role.equalsIgnoreCase("ROLE_SUPERADMIN")) {
                    resultRole += resultRole.equals("") ? "SUPERADMIN" : ", SUPERADMIN";
                }
            }
            employeeView.setRole(resultRole);
        }
    }
}
