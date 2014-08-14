/**
 * Created by Administrator on 11.08.2014.
 */


define(["jquery", "handlebars", "Util", "text!templates/term-mark-template.html"],
    function ($, Handlebars, util, templateTermMarkContent) {
        "use strict";
        var studentId,
            templateTermMark = Handlebars.compile(templateTermMarkContent);

        function init() {
            util.initAccessRoleForInfo();
            studentId = util.parseRequestForId(window.location.search);
            fillOptions();
            fillEducationOptions();
            fillCommonInfo();
        }

        function fillOptions() {
            $.ajax({
                type: "GET",
                url: "/info/getOptions",
                success: function (data) {
                    //filling
                    var $stateSelect = $("#state"),
                        options = JSON.parse(data),
                        stateOptions = options.states;

                    $stateSelect.empty();

                    stateOptions.forEach(function (element) {
                        $stateSelect.append($("<option value=" + element + ">" + element + "</option>"));
                    });
                }
            });
        }

        function fillEducationOptions() {
            var $institutionSelect = $("#institution"),
                universities = getUniversities(),
                universityId;

            $institutionSelect.empty();

            require(["text!templates/university-or-faculty-option-template.html"], function (template) {
                $institutionSelect.append(Handlebars.compile(template)({
                        values: universities
                    })
                );
                //selected first
                $("#institution :first").attr("selected", "selected");
                universityId = $("#institution :selected").val();
                getFaculties(universityId);
            });
        }

        function checkState() {
            var state = $("#state :selected").text();
            if (state === 'working') {
                $("#exadelHeader").show();

            }
            else {
                if ($("#exadelContent").is(":visible")) {
                    $("#exadelContent").slideToggle(100);
                }
                $("#exadelHeader").hide(200);
            }
        }

        function getUniversities() {
            var universities,
                getAllUniversities = $.ajax({
                    type: "GET",
                    url: "/info/getUniversities",
                    dataType: 'json',
                    async: false
                });
            getAllUniversities.done(function (data) {
                universities = data;
            });
            getAllUniversities.fail(function () {
                alert("failed loading");
            });
            return universities;
        }

        function getFaculties(universityId) {
            var $facultySelect = $("#faculty"),
                getFacultiesForUniversity = $.ajax({
                    type: "GET",
                    url: "/info/getFacultiesForUniversity",
                    dataType: 'json',
                    data: {
                        universityId: universityId
                    }
                });
            getFacultiesForUniversity.done(function (data) {
                $facultySelect.empty();
                require(["text!templates/university-or-faculty-option-template.html"], function (template) {
                    $facultySelect.append(Handlebars.compile(template)({
                            values: data
                        })
                    );
                });
            });
            getFacultiesForUniversity.fail(function () {
                alert("failed loading");
            });
        }

        function fillCommonInfo() {
            var getStudent = $.ajax({
                type: "GET",
                url: "/info/getCommonInformation",
                cashe: false,
                data: {
                    "id": studentId
                }
            });
            getStudent.done(function (data) {
                var gottenUser = JSON.parse(data),
                    gottenStudent = gottenUser.studentInfo,
                    marks,
                    universityId,
                    facultyId,
                    teamLeadName,
                    projectManagerName;
                $("#sessionUsername").text(sessionStorage.getItem("username"));

                $("#headerName").text(gottenUser.name);
                $("#name").val(gottenUser.name);
                $("#birthDate").val(gottenUser.birthdate);
                $("#login").val(gottenUser.login);
                $("#password").val(gottenUser.password);
                $("#email").val(gottenUser.email);
                $("#skype").val(gottenUser.skype);
                $("#phone").val(gottenUser.telephone);
                $("#state").find("option:contains(" + "\'" + gottenStudent.state + "\')").attr("selected", "selected");
                checkState();

                if (gottenStudent.university) {
                    universityId = "\'" + gottenStudent.university.id + "\'";
                    $("#institution [value =" + universityId + "]").attr("selected", "selected");
                    if (gottenStudent.faculty) {
                        facultyId = "\'" + gottenStudent.faculty.id + "\'";
                        $("#faculty [value =" + facultyId + "]").attr("selected", "selected");
                    }
                }
                $("#speciality").val(gottenStudent.speciality);
                $("#course").val(gottenStudent.course);
                $("#group").val(gottenStudent.group);
                $("#graduationDate").val(gottenStudent.graduationDate);

                $("#workingHours").val(gottenStudent.workingHours);
                $("#hireDate").val(gottenStudent.hireDate);
                $("#billable").val(gottenStudent.billable);
                $("#wishingHours").val(gottenStudent.wishesHoursNumber);
                $("#courseStartWorking").val(gottenStudent.courseWhenStartWorking);
                if (gottenStudent.trainingBeforeStartWorking) {
                    $("#trainingBeforeWorking").attr('checked', 'checked');
                }
                $("#trainingExadel").val(gottenStudent.trainingsInExadel);
                $("#currentProject").val(gottenStudent.currentProject);
                $("#roleCurrentProject").val(gottenStudent.roleCurrentProject);

                teamLeadName = getTeamLeadName(gottenStudent.teamLeadId);
                projectManagerName = getProjectManagerName(gottenStudent.projectManagerId);

                $("#currentTeamLead").text(teamLeadName);
                $("#currentTeamLead").attr("data-id", gottenStudent.teamLeadId);


                $("#currentProjectManager").text(projectManagerName);
                $("#currentProjectManager").attr("data-id", gottenStudent.projectManagerId);

                $("#techsCurrentProject").val(gottenStudent.techsCurrentProject);

                //termMarks
                marks = gottenStudent.termMarks;
                if (marks !== "") {
                    marks = marks.split(";");
                    $.each(marks, function (index, value) {
                        $("#termMarkList").append(templateTermMark({
                            value: value
                        }));
                        var role = sessionStorage.getItem("role");
                        if (role === '2' || role === '3' || role === '4') {
                            $("#termMarkList input").prop("disabled", true);
                        }
                    });
                }
            });
            getStudent.fail(function () {
                alert("error");

            });

        }

        function getTeamLeadName(teamLeadId) {
            var teamLeadName,
                getTeamLead;
            if (teamLeadId) {
                getTeamLead = $.ajax({
                    type: "GET",
                    url: "/info/getTeamLead",
                    async: false,
                    dataType: 'json',
                    data: {
                        teamLeadId: teamLeadId
                    }
                });
                getTeamLead.done(function (data) {
                    teamLeadName = data.name;
                });
                getTeamLead.fail(function () {
                    alert("error");
                });
            }
            else {
                teamLeadName = "Not set";
            }
            return teamLeadName;
        }

        function getProjectManagerName(projectManagerId) {
            var projectManagerName,
                getProjectManager;
            if (projectManagerId) {
                getProjectManager = $.ajax({
                    type: "GET",
                    url: "/info/getProjectManager",
                    async: false,
                    dataType: 'json',
                    data: {
                        projectManagerId: projectManagerId
                    }
                });
                getProjectManager.done(function (data) {
                    projectManagerName = data.name;
                });
                getProjectManager.fail(function () {
                    alert("error");
                });
            }
            else {
                projectManagerName = "Not set";
            }
            return projectManagerName;
        }

        return {
            studentId: function () {
                return studentId;
            },
            init: init,
            checkState: checkState,
            getFaculties: getFaculties,
            getTeamLeadName: getTeamLeadName,
            getProjectManagerName: getProjectManagerName
        };
    });
