var studentId;
var MAX_NUMBER_TERMS = 10,
    MIN_MARK = 0,
    MAX_MARK = 10;

//templates
var templateTermMark = Handlebars.compile($('#termMarkTemplate').html());
var templateDocument = Handlebars.compile($('#documentTemplate').html());

$(window).ready(function () {
    //alert(window.location.search);
    "use strict";
    parseRequestForId(window.location.search);
    fillOptions();
    fillCommonInfo();
});

$(document).ready(function () {
    "use strict";
    //export info
    $("#exportInfoExcel").click(function () {
        exportStudentExcel();
    });

    $("#exportInfoPDF").click(function () {
        exportStudentPDF();
    });

    //sortable table
    $('#documentTable').tablesorter();
});

$(document).ready(function () {
    "use strict";
    //event handlers for submit button save
    $("#saveManualInformation").click(function () {
        //collect data entered by users
        var editedName = $("#name").val(),
            editedBirthDate = $("#birthDate").val(),
            editedLogin = $("#login").val(),
            editedEmail = $("#email").val(),
            editedSkype = $("#skype").val(),
            editedPhone = $("#phone").val(),
            editedPassword = $("#password").val(),
            editedState = $("#state :selected").val();

        saveManualInfoChanges(editedName, editedBirthDate, editedLogin, editedEmail, editedSkype, editedPhone, editedPassword, editedState);
    });

    $("#saveEducationInformation").click(function () {
        //collect data entered by users
        var $termMark = $(".term-mark"),
            editedUniversity = $("#institution").val(),
            editedFaculty = $("#faculty").val(),
            editedSpeciality = $("#speciality").val(),
            editedCourse = $("#course").val(),
            editedGroup = $("#group").val(),
            editedGraduationDate = $("#graduationDate").val(),
            editedTermMarks = "",
            numberTerms = $termMark.length - 1;
        $termMark.each(function (index) {
            if (index < numberTerms) {
                editedTermMarks = editedTermMarks.concat($(this).val() + ";");
            }
            else {
                editedTermMarks = editedTermMarks.concat($(this).val());
            }
        });

        saveEducationChanges(editedUniversity, editedFaculty, editedSpeciality, editedCourse, editedGroup, editedGraduationDate, editedTermMarks);
    });

    $("#saveExadelInformation").click(function () {
        //collect data entered by users
        var editedHireDate = $("#hireDate").val(),
            editedWorkingHours = $("#workingHours").val(),
            editedBillable = $("#billable").val(),
            editedWishingHours = $("#wishingHours").val(),
            editedCourseStartWorking = $("#courseStartWorking").val(),
            editedTrainingBeforeWorking = $("#trainingBeforeWorking").is(':checked'),
            editedTrainingExadel = $("#trainingExadel").val(),

            editedCurrentProject = $("#currentProject").val(),
            editedRoleCurrentProject = $("#roleCurrentProject").val(),
            editedCurrentTeamLead = $("#currentTeamLead").text(),
            editedCurrentProjectManager = $("#currentProjectManager").text(),
            editedTechsCurrentProject = $("#techsCurrentProject").val();

        saveExadelChanges(editedWorkingHours, editedHireDate, editedBillable, editedWishingHours, editedCourseStartWorking, editedTrainingBeforeWorking, editedTrainingExadel, editedCurrentProject, editedRoleCurrentProject, editedCurrentTeamLead, editedCurrentProjectManager, editedTechsCurrentProject);
    });

    $("#saveDocumentsInformation").click(function () {
        var fields = ['doctype', 'issueDate', 'expirationDate', 'info'],
         newDocuments = [];
        $(".new-document").each(function () {
            var $this = $(this),
                cells = $this.find("td"),
                value = {},
                i,
                cellsLength = cells.length;

            for (i = 0; i < cellsLength; i++) {
                value[fields[i]] = $(cells[i]).text();
            }

            value.studentId = studentId;

            console.log(value);
            newDocuments.push(value);
            $this.removeClass("new-document");
        });

        newDocuments = JSON.stringify(newDocuments);

        saveDocumentsInformation(newDocuments);
    });

    //load documents
    $("#documentsHeader").click(function () {
        $.ajax({
            type: "GET",
            url: "/info/getActualDocuments",
            cashe: false,
            data: {
                "studentId": studentId
            },
            success: function (data) {
                $("#documents").empty();

                var documents = JSON.parse(data);
                jQuery.each(documents, function (index, value) {
                    $("#documents").append(templateDocument(value));
                    if (expiredSoon(value.expirationDate)) {
                        $("#documents tr").last().addClass("expired-soon-document");
                    }
                    $("#documentTable").trigger("update");
                });
            },
            error: function () {
                alert("error");
            }
        });

    });

    // show/hide expired docs
    $("#expiredDocs").click(function () {
        var $expiredDocs = $("#expiredDocs"),
            action = $expiredDocs.attr('data-do');

        if (action === 'show') {
            $.ajax({
                type: "GET",
                url: "/info/getExpiredDocuments",
                cashe: false,
                data: {
                    "studentId": studentId
                },
                success: function (data) {
                    var documents = JSON.parse(data);

                    jQuery.each(documents, function (index, value) {
                        $("#documents").append(templateDocument(value));
                        $("#documents tr").last().addClass("expired-document");
                        $("#documentTable").trigger("update");
                    });
                    $expiredDocs.attr('data-do', 'hide');
                    $expiredDocs.text("Hide expired");
                },
                error: function () {
                    alert("error");
                }
            });
        }
        else if (action === 'hide') {
            $(".expired-document").each(function () {
                $(this).remove();

                $("#documentTable").trigger("update");
            });
            $expiredDocs.attr('data-do', 'show');
            $expiredDocs.text("Show expired");
        }
    });

    //add document
    $("#addDocument").click(function () {
        showDialog("add-document");

    });

    ////TODO wtf dialog
    $("#okButton").click(function () {
        var doctype = $("#doctype").val(),
            issueDate = $("#issueDate").val(),
            expirationDate = $("#expirationDate").val(),
            info = $("#info").val(),
            newDocument = {
                doctype: doctype,
                issueDate: issueDate,
                expirationDate: expirationDate,
                info: info
            };

        $("#doctype").val("");
        $("#issueDate").val("");
        $("#expirationDate").val("");
        $("#info").val("");
        closeDialog();
        $("#documents").prepend(templateDocument(newDocument));
        $("#documents tr").first().addClass("new-document");
        $("#documentTable").trigger("update");
    });
    //close dialog
    $("#closeDialog").click(function () {
        closeDialog();
    });
    //handler for state select
    $("#state").change(
        checkState
    );

//handler termMark changed
    $(".term-mark-list").on("change", 'input', function () {
        var $this = $(this);
        if (!validInputTermMark($this.val())) {
            $this.addClass("term-mark-invalid", 1000);
            $this.val(null);
        }
        else {
            $this.removeClass("term-mark-invalid", 200);
        }
    });

    $("#addNextTerm").click(function () {
        var numberTerms = $("#termMarkList").children().length,
            lastTerm;
        lastTerm = $(".term-mark-list li input").last();

        if (lastTerm.val() === "") {
            lastTerm.focus();
        }
        else {
            ++numberTerms;
            $("#termMarkList").append(templateTermMark);

            if (numberTerms === MAX_NUMBER_TERMS) {
                $("#addNextTerm").attr("disabled", "true");
            }
        }
    });

});


function validInputTermMark(termMarkVal) {
    "use strict";
    if (termMarkVal <= MIN_MARK || termMarkVal > MAX_MARK) {
        return false;
    }
    return true;
}

function parseRequestForId(string) {
    "use strict";
    var gottenId,
        regExpForId = /id=[0-9]+/,
        regExp = /[0-9]+/;

    gottenId = string.match(regExpForId);
    studentId = (gottenId[0].match(regExp))[0];
}

function expiredSoon(expirationDate) {
    "use strict";
    var twoWeeks = 1000 * 60 * 60 * 24 * 14,
        twoWeeksAfterNow = Date.now() + twoWeeks;
    expirationDate = Date.parse(expirationDate);
    if (expirationDate <= twoWeeksAfterNow) {
        return true;
    }
    else {
        return false;
    }
}

function exportStudentExcel() {
    "use strict";
    window.open("/info/exportExcel?studentId=" + studentId, "Export file");
}

function exportStudentPDF() {
    "use strict";
    window.open("/info/exportPDF?studentId=" + studentId, "Export file");
}

function fillOptions() {
    "use strict";
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

function fillCommonInfo() {
    "use strict";
    $.ajax({
        type: "GET",
        //SEND TO CONTROLLER
        url: "/info/getCommonInformation",
        cashe: false,
        data: {
            "studentId": studentId
        },
        success: function (data) {
            // alert("" + data);
            var gottenUser = JSON.parse(data),
                gottenStudent = gottenUser.studentInfo,
                marks;

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
            if (marks) {
                marks = marks.split(";");
                jQuery.each(marks, function (index, value) {
                    $("#termMarkList").append(templateTermMark);
                    $("#termMarkList li input").last().attr('value', value);
                });
            }
        }
    });
}

function checkState() {
    "use strict";
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

function saveManualInfoChanges(editedName, editedBirthDate, editedLogin, editedEmail, editedSkype, editedPhone, editedPassword, editedState) {
    "use strict";
    $.ajax({
        type: "POST",
        //SEND
        url: "/info/postManualInformation",
        dataType: 'json', // from the server!
        data: {
            'studentId': studentId,
            'studentName': editedName,
            'studentBirthDate': editedBirthDate,
            'studentLogin': editedLogin,
            'studentPassword': editedPassword,
            'studentEmail': editedEmail,
            'studentSkype': editedSkype,
            'studentPhone': editedPhone,
            'studentState': editedState
        },
        success: function () {
            $("#saveManualInformation").text("Saved!");
            $("#saveManualInformation").animate({
                backgroundColor: '#5cb85c',
                borderColor: '#4cae4c'
            }, {
                duration: 500,
                easing: "swing",
                complete: setTimeout(function () {
                    $("#saveManualInformation").animate({
                        backgroundColor: '#4A5D80',
                        borderColor: '#2D3E5C'
                    }, 500);
                    $("#saveManualInformation").text("Save");
                }, 1000)
            });

            $("#headerName").text(editedName);
        },
        error: function () {
            $("#saveManualInformation").text("Check out!");
            $("#saveManualInformation").animate({
                backgroundColor: '#CD5C5C',
                borderColor: '#C16868'
            }, {
                duration: 500,
                easing: "swing",
                complete: setTimeout(function () {
                    $("#saveManualInformation").animate({
                        backgroundColor: '#4A5D80',
                        borderColor: '#2D3E5C'
                    }, 500);
                    $("#saveManualInformation").text("Save");
                }, 1000)
            });
        }
    });
}

function saveEducationChanges(editedUniversity, editedFaculty, editedSpeciality, editedCourse, editedGroup, editedGraduationDate, editedTermMarks) {
    "use strict";
    $.ajax({
        type: "POST",
        //SEND
        url: "/info/postEducation",
        dataType: 'json', // from the server!
        data: {
            'studentId': studentId,
            'studentUniversity': editedUniversity,
            'studentFaculty': editedFaculty,
            'studentSpeciality': editedSpeciality,
            'studentCourse': editedCourse,
            'studentGroup': editedGroup,
            'studentGraduationDate': editedGraduationDate,
            'studentTermMarks': editedTermMarks
        },
        success: function () {
            $("#saveEducationInformation").text("Saved!");
            $("#saveEducationInformation").animate({
                backgroundColor: '#5cb85c',
                borderColor: '#4cae4c'
            }, {
                duration: 500,
                easing: "swing",
                complete: setTimeout(function () {
                    $("#saveEducationInformation").animate({
                        backgroundColor: '#4A5D80',
                        borderColor: '#2D3E5C'
                    }, 500);
                    $("#saveEducationInformation").text("Save");
                }, 1000)
            });
        },
        error: function () {
            $("#saveEducationInformation").text("Check out!");
            $("#saveEducationInformation").animate({
                backgroundColor: '#CD5C5C',
                borderColor: '#C16868'
            }, {
                duration: 500,
                easing: "swing",
                complete: setTimeout(function () {
                    $("#saveEducationInformation").animate({
                        backgroundColor: '#4A5D80',
                        borderColor: '#2D3E5C'
                    }, 500);
                    $("#saveEducationInformation").text("Save");
                }, 1000)
            });
        }
    });
}

function saveExadelChanges(editedWorkingHours, editedHireDate, editedBillable, editedWishingHours, editedCourseStartWorking, editedTrainingBeforeWorking, editedTrainingExadel, editedCurrentProject, editedRoleCurrentProject, editedCurrentTeamLead, editedCurrentProjectManager, editedTechsCurrentProject) {
    "use strict";
    var defferedPostExadel = $.ajax({
        type: "POST",
        //SEND
        url: "/info/postExadel",
        dataType: 'json', // from the server!
        data: {
            'studentId': studentId,
            'studentWorkingHours': editedWorkingHours,
            'studentHireDate': editedHireDate,
            'studentBillable': editedBillable,
            'studentWishingHours': editedWishingHours,
            'studentCourseStartWorking': editedCourseStartWorking,
            'studentTrainingBeforeWorking': editedTrainingBeforeWorking,
            'studentTrainingExadel': editedTrainingExadel,
            'studentCurrentProject': editedCurrentProject,
            'studentRoleCurrentProject': editedRoleCurrentProject,
            'studentCurrentTeamLead': editedCurrentTeamLead,
            'studentCurrentProjectManager': editedCurrentProjectManager,
            'studentTechsCurrentProject': editedTechsCurrentProject
        }
    });
    defferedPostExadel.done(function () {
        $("#saveExadelInformation").text("Saved!");
        $("#saveExadelInformation").animate({
            backgroundColor: '#5cb85c',
            borderColor: '#4cae4c'
        }, {
            duration: 500,
            easing: "swing",
            complete: setTimeout(function () {
                $("#saveExadelInformation").animate({
                    backgroundColor: '#4A5D80',
                    borderColor: '#2D3E5C'
                }, 500);
                $("#saveExadelInformation").text("Save");
            }, 1000)
        });
    });
    defferedPostExadel.fail(function () {
        $("#saveExadelInformation").text("Check out!");
        $("#saveExadelInformation").animate({
            backgroundColor: '#CD5C5C',
            borderColor: '#C16868'
        }, {
            duration: 500,
            easing: "swing",
            complete: setTimeout(function () {
                $("#saveExadelInformation").animate({
                    backgroundColor: '#4A5D80',
                    borderColor: '#2D3E5C'
                }, 500);
                $("#saveExadelInformation").text("Save");
            }, 1000)
        });
    });

}

function saveDocumentsInformation(newDocuments) {
    "use strict";
    var defferedPostDocuments = $.ajax({
        type: "POST",
        //SEND
        url: "/info/postDocuments",
        dataType: 'json', // from the server!
        data: {'documents': newDocuments}

    });
    defferedPostDocuments.done(function () {
        $("#saveDocumentsInformation").text("Saved!");
        $("#saveDocumentsInformation").animate({
            backgroundColor: '#5cb85c',
            borderColor: '#4cae4c'
        }, {
            duration: 500,
            easing: "swing",
            complete: setTimeout(function () {
                $("#saveDocumentsInformation").animate({
                    backgroundColor: '#4A5D80',
                    borderColor: '#2D3E5C'
                }, 500);
                $("#saveDocumentsInformation").text("Save");
            }, 1000)
        });
    });
    defferedPostDocuments.fail(function () {
        alert("error");
        $("#saveDocumentsInformation").text("Check out!");
        $("#saveDocumentsInformation").animate({
            backgroundColor: '#CD5C5C',
            borderColor: '#C16868'
        }, {
            duration: 500,
            easing: "swing",
            complete: setTimeout(function () {
                $("#saveDocumentsInformation").animate({
                    backgroundColor: '#4A5D80',
                    borderColor: '#2D3E5C'
                }, 500);
                $("#saveDocumentsInformation").text("Save");
            }, 1000)
        });
    });

}







