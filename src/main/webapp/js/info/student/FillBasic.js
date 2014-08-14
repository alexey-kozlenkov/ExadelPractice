/**
 * Created by Administrator on 11.08.2014.
 */


define(["jquery", "handlebars", "Util", "text!templates/term-mark-template.html"], function ($, Handlebars, util, templateTermMarkContent) {
    "use strict";
    var studentId,
        templateTermMark = Handlebars.compile(templateTermMarkContent);

    function init() {
        util.initAccessRoleForStudentInfo();
        parseRequestForId(window.location.search);
        fillOptions();
        fillEducationOptions();
        fillCommonInfo();
    }

    function parseRequestForId(string) {
        var gottenId,
            regExpForId = /id=[0-9]+/,
            regExp = /[0-9]+/;

        gottenId = string.match(regExpForId);
        studentId = (gottenId[0].match(regExp))[0];
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
        var  $institutionSelect = $("#institution"),
            universities = getUniversities(),
            universityId;

        $institutionSelect.empty();

        require(["text!templates/university-or-faculty-option-template.html"], function (template) {
            $institutionSelect.append(Handlebars.compile(template) ({
                    values : universities
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
            async : false
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
                data : {
                    universityId : universityId
                }
            });
        getFacultiesForUniversity.done(function (data) {
            $facultySelect.empty();
            require(["text!templates/university-or-faculty-option-template.html"], function (template) {
                $facultySelect.append(Handlebars.compile(template) ({
                        values : data
                    })
                );
            });
        });
        getFacultiesForUniversity.fail(function () {
            alert("failed loading");
        });
    }

    function fillCommonInfo() {
        $.ajax({
            type: "GET",
            //SEND TO CONTROLLER
            url: "/info/getCommonInformation",
            cashe: false,
            data: {
                "studentId": studentId
            },
            success: function (data) {
                var gottenUser = JSON.parse(data),
                    gottenStudent = gottenUser.studentInfo,
                    marks,
                    universityId,
                    facultyId;
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
                $("#currentTeamLead").text(gottenStudent.teamLeadId);
                $("#currentProjectManager").text(gottenStudent.projectManagerId);
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
            },
            fail : function () {
                alert("error");

            }
        });
    }

    return {
        studentId: function () { return studentId; },
        init: init,
        checkState: checkState,
        getFaculties : getFaculties
    };
});
