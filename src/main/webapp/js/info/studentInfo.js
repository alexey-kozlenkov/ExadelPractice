var studentId;
var MAX_NUMBER_TERMS = 10,
    MIN_MARK = 0,
    MAX_MARK = 10;

//templates
var templateTermMark = Handlebars.compile($('#termMarkTemplate').html());
var templateDocument = Handlebars.compile($('#documentTemplate').html());

$(window).ready(function () {
    //alert(window.location.search);
    parseRequestForId(window.location.search);
    fillOptions();
    fillManualInfo();
});

$(document).ready(function () {

    //hide content after manual information
    $(".category-list .category-content:gt(0)").hide();

    //toggle category-content
    $(".category-header").click(function () {
        $(this).next(".category-content").slideToggle(500);
        return false;
    });

    //export info
    $("#exportInfoExcel").click(function () {
        exportStudentExcel();
    });

    $("#exportInfoPDF").click(function () {
        exportStudentPDF();
    });

    //sortable table
   // $('#documentTable').tablesorter();
});

$(document).ready(function () {
    //event handlers for submit button save
    $("#saveManualInformation").click(function () {
        //collect data entered by users
        var editedName = $("#name").val();
        var editedLogin = $("#login").val();
        var editedEmail = $("#email").val();
        var editedPassword = $("#password").val();
        var editedState = $("#state :selected").val();

        saveManualInfoChanges(editedName, editedLogin, editedEmail, editedPassword, editedState);
    });

    $("#saveEducationInformation").click(function () {
        //collect data entered by users
        var editedUniversity = $("#institution").val();
        var editedFaculty = $("#faculty").val();
        var editedSpeciality = $("#speciality").val();
        var editedCourse = $("#course").val();
        var editedGroup = $("#group").val();
        var editedGraduationDate = $("#graduationDate").val();
        var editedTermMarks = "";
        var numberTerms = $(".term-mark").length - 1;
        $(".term-mark").each(function (index) {
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
        var editedHireDate = $("#hireDate").val();
        var editedWorkingHours = $("#workingHours").val();
        var editedBillable = $("#billable").val();
        var editedRoleCurrentProject = $("#roleCurrentProject").val();
        var editedTechsCurrentProject = $("#techsCurrentProject").val();

        saveExadelChanges(editedWorkingHours, editedHireDate, editedBillable, editedRoleCurrentProject, editedTechsCurrentProject);
    });



    $("#saveDocumentsInformation").click(function () {
        var fields = ['doctype', 'issueDate', 'expirationDate', 'info'];
        var newDocuments = [];
        $(".new-document").each(function (index) {
            var cells  = $(this).find("td"),
                value = {};

            for(var i=0;i<cells.length; i++){
                value[fields[i]] = $(cells[i]).text();
            }

            value['studentId'] = studentId;

            console.log(value);
            newDocuments.push(value);
            $(this).removeClass("new-document");
        });

        newDocuments = JSON.stringify(newDocuments);

        saveDocumentsInformation(newDocuments);
    });


    //load documents
    $("#documentsHeader").click(function () {
        $.ajax
        ({
            type: "GET",
            url: "/info/getDocuments",
            async: true,
            cashe: false,
            data: {
                "studentId": studentId
            },
            success: function (data) {
                // alert("" + data);
                $("#documents").empty();
                var documents = JSON.parse(data);
                jQuery.each(documents, function (index, value) {
                    $("#documents").append(templateDocument(value));
                });
            },
            error: function () {
                alert("error");
            }});

    });

    //add document
    $("#addDocument").click(function () {
        showDialog("add-document");

    });
    $("#okButton").click(function () {
        var doctype = $("#doctype").val();
        var issueDate = $("#issueDate").val();
        var expirationDate = $("#expirationDate").val();
        var info = $("#info").val();
        var newDocument = {
            doctype: doctype,
            issueDate: issueDate,
            expirationDate: expirationDate,
            info: info
        };
        closeDialog();
        $("#documents").append(templateDocument(newDocument));
        $("#documents tr").last().addClass("new-document");


        $("#doctype").val("");
        $("#issueDate").val("");
        $("#expirationDate").val("");
        $("#info").val("");

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
        if (!validInputTermMark($(this).val())) {
            $(this).addClass("term-mark-invalid", 1000);
            $(this).val(null);
        }
        else {
            $(this).removeClass("term-mark-invalid", 200);
        }
    });

    $("#addNextTerm").click(function () {
        var numberTerms;
        numberTerms = $("#termMarkList").children().length;
        var lastTerm;
        lastTerm = $(".term-mark-list li input").last();

        if (lastTerm.val() == MIN_MARK) {
            lastTerm.focus();
        }
        else {
            ++numberTerms;
            $("#termMarkList").append(templateTermMark);

            if (numberTerms === MAX_NUMBER_TERMS)
                $("#addNextTerm").attr("disabled", "true");
        }
    });

});


function validInputTermMark(termMarkVal) {
    if (termMarkVal <= MIN_MARK || termMarkVal > MAX_MARK)
        return false;
    return true;
}

function parseRequestForId(string) {
    var gottenId;
    var regExpForId = /id=[0-9]+/;
    gottenId = string.match(regExpForId);
    var regExp = /[0-9]+/;
    studentId = (gottenId[0].match(regExp))[0];
}

function exportStudentExcel(){
    window.open("/info/exportExcel?studentId=" + studentId, "Export file");
}

function exportStudentPDF(){
    window.open("/info/exportPDF?studentId=" + studentId, "Export file");
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
            var options = JSON.parse(data);

            var stateOptions = options.states;
            stateOptions.forEach(function (element, index, array) {
                $("#state").append($("<option value=" + element + ">" + element + "</option>"));
            })
        }
    });
}

function fillManualInfo() {
    $.ajax
    ({
        type: "GET",
        //SEND TO CONTROLLER
        url: "/info/getCommonInformation",
        async: true,
        cashe: false,
        data: {
            "studentId": studentId
        },
        success: function (data) {
            // alert("" + data);
            var gottenUser = JSON.parse(data);
            var gottenStudent = gottenUser.studentInfo;

            $("#headerName").text(gottenUser.name);
            $("#name").val(gottenUser.name);
            $("#login").val(gottenUser.login);
            $("#password").val(gottenUser.password);
            $("#email").val(gottenUser.email);
            $("#state").find("option:contains(" + "\'" + gottenStudent.state + "\')").attr("selected", "selected");
            checkState();

            $("#institution").val(gottenStudent.university);
            $("#faculty").val(gottenStudent.faculty);
            $("#speciality").val("applied maths");
            $("#course").val(gottenStudent.course);
            $("#group").val(gottenStudent.group);
            $("#graduationDate").val(gottenStudent.graduationDate);

            $("#workingHours").val(gottenStudent.workingHours);
            $("#hireDate").val(gottenStudent.hireDate);
            $("#billable").val(gottenStudent.billable);
            $("#roleCurrentProject").val(gottenStudent.roleCurrentProject);
            $("#techsCurrentProject").val(gottenStudent.techsCurrentProject);

            //termMarks
            var marks = gottenStudent.termMarks;
            marks = marks.split(";");
            jQuery.each(marks, function (index, value) {
                $("#termMarkList").append(templateTermMark);
                $("#termMarkList li input").last().attr('value', value);
            });


        }
    });
}

function checkState() {
    var state = $("#state :selected").text();
    if (state == 'working') {
        $("#exadelHeader").show();

    }
    else {
        if ($("#exadelContent").is(":visible")) {
            $("#exadelContent").slideToggle(100);
        }
        $("#exadelHeader").hide(200);
    }
}

function saveManualInfoChanges(editedName, editedLogin, editedEmail, editedPassword, editedState) {
    $.ajax
    ({
        type: "POST",
        //SEND
        url: "/info/postManualInformation",
        dataType: 'json', // from the server!
        data: {
            'studentId': studentId,
            'studentName': editedName,
            'studentLogin': editedLogin,
            'studentPassword': editedPassword,
            'studentEmail': editedEmail,
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
//            dimensionPopup($("#manualContent"));
//            $("#saved").fadeIn(600);
//            hidePopupSave();

        },
        error: function () {
            alert("error");
        }
    });
}

function saveEducationChanges(editedUniversity, editedFaculty, editedSpeciality, editedCourse, editedGroup, editedGraduationDate, editedTermMarks) {
    $.ajax
    ({
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
            alert("error");
        }
    })
}

function saveExadelChanges(editedWorkingHours, editedHireDate, editedBillable, editedRoleCurrentProject, editedTechsCurrentProject) {
    var defferedPostExadel = $.ajax
    ({
        type: "POST",
        //SEND
        url: "/info/postExadel",
        dataType: 'json', // from the server!
        data: {
            'studentId': studentId,
            'studentWorkingHours': editedWorkingHours,
            'studentHireDate': editedHireDate,
            'studentBillable': editedBillable,
            'studentRoleCurrentProject': editedRoleCurrentProject,
            'studentTechsCurrentProject': editedTechsCurrentProject
        }});
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
        alert("error");
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
    var defferedPostDocuments = $.ajax
    ({
        type: "POST",
        //SEND
        url: "/info/postDocuments",
        dataType: 'json', // from the server!
        data: {'documents' : newDocuments}

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







