/**
 * Created by Administrator on 11.08.2014.
 */


define(["jquery", "handlebars", "Util", "text!templates/term-mark-template.html"], function ($, Handlebars, util, templateTermMarkContent) {
    "use strict";
    var studentId,
        templateTermMark = Handlebars.compile(templateTermMarkContent);
    function init() {
        util.login();
        parseRequestForId(window.location.search);
        fillOptions();
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
            async: true,
            success: function (data) {
                // alert("" + data);
                $("#state").empty();
                //filling
                var options = JSON.parse(data),
                    stateOptions = options.states;
                stateOptions.forEach(function (element) {
                    $("#state").append($("<option value=" + element + ">" + element + "</option>"));
                });
            }
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
               // alert(data);
                var gottenUser = JSON.parse(data),
                    gottenStudent = gottenUser.studentInfo,
                    marks;
                $("#sesssionUsername").text(sessionStorage.getItem("username"));

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

                $("#institution").val(gottenStudent.university);
                $("#faculty").val(gottenStudent.faculty);
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
                    });
                }
            },
            fail : function () {
                alert("error");

            }
        });
    }
    function rights() {

    }

    return {
        studentId: function () { return studentId; },
        init: init,
        checkState: checkState
    };
});
