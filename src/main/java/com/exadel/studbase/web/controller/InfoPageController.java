package com.exadel.studbase.web.controller;

import com.exadel.studbase.domain.impl.*;
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
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Controller
@Secured("IS_AUTHENTICATED_FULLY")
@RequestMapping("/info")
public class InfoPageController {

    @Autowired
    IUserService userService;
    @Autowired
    IStudentService studentService;
    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IEmployeeViewService employeeViewService;
    @Autowired
    IFeedbackService feedbackService;
    @Autowired
    IDocumentService documentService;
    @Autowired
    ISkillTypeService skillTypeService;
    @Autowired
    ISkillSetService skillSetService;
    @Autowired
    ICuratoringService curatoringService;
    @Autowired
    IUniversityService universityService;
    @Autowired
    IFacultyService facultyService;

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String studentInfoPage(@RequestParam("id") Long id) {
        MySecurityUser principal = (MySecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT")) && !principal.getId().equals(id)) {
            throw new AccessDeniedException("Pfff");
        }
        return "studentInfo";
    }

    @RequestMapping(value = "/getOptions", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String stateData() {
        Gson gson = new Gson();
        Options options = new Options();
        return gson.toJson(options, Options.class);
    }

    @RequestMapping(value = "/getUniversities", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String universitiesData() {
        Gson gson = new Gson();
        Collection<University> universities = universityService.getAll();
        return gson.toJson(universities);
    }

    @RequestMapping(value = "/getFacultiesForUniversity", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String universitiesData(@RequestParam("universityId") Long universityId) {
        Gson gson = new Gson();
        Collection<Faculty> faculties = facultyService.getAllForUniversity(universityId);
        return gson.toJson(faculties);
    }

    @RequestMapping(value = "/getCommonInformation", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String manualData(@RequestParam("id") Long id) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        User user = userService.getById(id);
        return gson.toJson(user, User.class);
    }
//
    @RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String allEmployees() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Collection<EmployeeView> employees = employeeViewService.getAll();
        return gson.toJson(employees);
    }

    @RequestMapping(value = "/getActualDocuments", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String documentsActualData(@RequestParam("studentId") Long studentId) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Collection<Document> userDocuments = documentService.getActualForUser(studentId);
        return gson.toJson(userDocuments);
    }

    @RequestMapping(value = "/getExpiredDocuments", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String documentsExpiredData(@RequestParam("studentId") Long studentId) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Collection<Document> userDocuments = documentService.getNotActualForUser(studentId);
        return gson.toJson(userDocuments);
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_OFFICE"})
    @RequestMapping(value = "/getAllFeedbacks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String feedbacksData(@RequestParam("studentId") Long studentId) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Collection<Feedback> userFeedbacks = feedbackService.getAllAboutStudent(studentId);
        return gson.toJson(userFeedbacks);
    }

    @Secured({"ROLE_FEEDBACKER", "ROLE_CURATOR"})
    @RequestMapping(value = "/getMyFeedbacks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String myFeedbacksData(@RequestParam("studentId") Long studentId,
                                  @RequestParam("employeeId") Long employeeId) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Collection<Feedback> userFeedbacks = feedbackService.getAllAboutStudentByEmployee(studentId, employeeId);
        return gson.toJson(userFeedbacks);
    }

    @RequestMapping(value = "/getFeedbacker", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String feedbacker(@RequestParam("feedbackerId") Long feedbackerId) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        User feedbacker = userService.getById(feedbackerId);
        return gson.toJson(feedbacker, User.class);
    }

    @RequestMapping(value = "/getTeamLead", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String teamLead(@RequestParam("teamLeadId") Long teamLeadId) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        User teamLead = userService.getById(teamLeadId);
        return gson.toJson(teamLead, User.class);
    }

    @RequestMapping(value = "/getProjectManager", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String projectManager(@RequestParam("projectManagerId") Long projectManagerId) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        User projectManager = userService.getById(projectManagerId);
        return gson.toJson(projectManager, User.class);
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_OFFICE", "ROLE_STUDENT"})
    @RequestMapping(value = "/postStudentManualInformation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String editStudentManualInformation(@RequestParam("studentId") Long id,
                                        @RequestParam("studentName") String name,
                                        @RequestParam("studentBirthDate") String birthDate,
                                        @RequestParam("studentLogin") String login,
                                        @RequestParam("studentPassword") String password,
                                        @RequestParam("studentEmail") String email,
                                        @RequestParam("studentSkype") String skype,
                                        @RequestParam("studentPhone") String phone,
                                        @RequestParam("studentState") String state) {
        User editedUser = userService.getById(id);
        editedUser.setName(name);
        editedUser.setBirthdate(formatField(birthDate, Date.class));
        editedUser.setLogin(login);
        editedUser.setPassword(password);
        editedUser.setEmail(email);
        editedUser.setSkype(skype);
        editedUser.setTelephone(phone);
        editedUser.getStudentInfo().setState(state);

        userService.save(editedUser);

        return "{\"post\":\"ok\"}"; //string in double quotes
    }



    @Secured({"ROLE_SUPERADMIN", "ROLE_STUDENT"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/postEducation", method = RequestMethod.POST)
    public String editEducation(@RequestParam("studentId") Long id,
                                @RequestParam("studentUniversity") Long universityId,
                                @RequestParam("studentFaculty") Long facultyId,
                                @RequestParam("studentSpeciality") String speciality,
                                @RequestParam("studentCourse") String course,
                                @RequestParam("studentGroup") String group,
                                @RequestParam("studentGraduationDate") String graduationDate,
                                @RequestParam("studentTermMarks") String termMarks) {

        Student editedStudent = studentService.getById(id);
        editedStudent.setUniversity(universityService.getById(universityId));
        editedStudent.setFaculty(facultyService.getById(facultyId));
        editedStudent.setCourse(formatField(course, Integer.class));
        editedStudent.setGroup(formatField(group, Integer.class));
        editedStudent.setSpeciality(speciality);
        editedStudent.setGraduationDate(formatField(graduationDate, Integer.class));
        editedStudent.setTermMarks(termMarks);
        studentService.save(editedStudent);

        return ("{\"post\":\"ok\"}");
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_STUDENT", "ROLE_OFFICE"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/postExadel", method = RequestMethod.POST)
    public String editExadel(@RequestParam("studentId") Long id,
                             @RequestParam("studentWorkingHours") Integer workingHours,
                             @RequestParam("studentHireDate") Date hireDate,
                             @RequestParam("studentBillable") String billable,
                             @RequestParam("studentWishingHours")  String wishingHours,
                             @RequestParam("studentCourseStartWorking") String courseStartWorking,
                             @RequestParam("studentTrainingBeforeWorking") Boolean trainingBeforeWorking,
                             @RequestParam("studentTrainingExadel") String trainingInExadel,
                             @RequestParam("studentCurrentProject") String currentProject,
                             @RequestParam("studentRoleCurrentProject") String roleCurrentProject,
                             @RequestParam("studentCurrentTeamLead") String currentTeamLead,
                             @RequestParam("studentCurrentProjectManager") String currentProjectManager,
                             @RequestParam("studentTechsCurrentProject") String techsCurrentProject) {

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Student editedStudent = studentService.getById(id);
        editedStudent.setWorkingHours(workingHours);
        editedStudent.setHireDate(hireDate);
        editedStudent.setBillable(formatField(billable, Date.class));
        editedStudent.setWishesHoursNumber(formatField(wishingHours, Integer.class));
        editedStudent.setCourseWhenStartWorking(formatField(courseStartWorking, Integer.class));
        editedStudent.setTrainingBeforeStartWorking(trainingBeforeWorking);
        editedStudent.setTrainingsInExadel(trainingInExadel);
        editedStudent.setCurrentProject(currentProject);
        editedStudent.setRoleCurrentProject(roleCurrentProject);
        //team-lead
        //project-manager
        editedStudent.setTechsCurrentProject(techsCurrentProject);
        studentService.save(editedStudent);

        System.out.println("*********************");
        System.out.println(currentTeamLead);
        System.out.println(currentProjectManager);
        System.out.println("*********************");

        return ("{\"post\":\"ok\"}");
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_STUDENT"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/postDocuments", method = RequestMethod.POST)
    public String editDocuments(@RequestParam("documents") String newDocuments) {

          Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
          Document[] newDocs = gson.fromJson(newDocuments, Document[].class);

            for(Document document : newDocs){
                documentService.save(document);
            }

        return ("{\"post\":\"ok\"}");
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_CURATOR", "ROLE_FEEDBACKER"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/postFeedbacks", method = RequestMethod.POST)
    public String editFeedbacks(@RequestParam("feedbacks") String newFeedbacks) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Feedback[] newFeeds = gson.fromJson(newFeedbacks, Feedback[].class);

        for(Feedback feedback : newFeeds){
            feedbackService.save(feedback);
        }

        return ("{\"post\":\"ok\"}");
    }


    @RequestMapping(value = "/redirectInfo")
    public String loadInfo(@RequestParam("login") String login) {
        Long id = userService.getByLogin(login).getId();
        return "redirect:/info/student?id=" + id;
    }

    @RequestMapping(value = "/exportPDF", method = RequestMethod.GET)
    public ModelAndView exportPdf(@RequestParam("studentId") Long id) {
        User user = userService.getById(id);
        return new ModelAndView("pdfView", "user", user);
    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public ModelAndView export(@RequestParam("studentId") Long id) {
        List<User> listOfUsers = new ArrayList<User>();
        listOfUsers.add(userService.getById(id));
        return new ModelAndView("excelView", "users", listOfUsers);
    }

    @ResponseBody
    private <T> T formatField (String value, Class<T> pClass) {
        if(pClass == Integer.class) {
            return value.equals("") ? null : (T) Integer.valueOf(value);
        } else if (pClass == Date.class ) {
            return value.equals("") ? null : (T) Date.valueOf(value);
        }
        return null;
    }
}
