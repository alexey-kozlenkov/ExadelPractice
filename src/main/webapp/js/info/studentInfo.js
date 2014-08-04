var studentId;
var MAX_NUMBER_TERMS = 10,
    MIN_MARK = 0,
    MAX_MARK = 10;

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

        saveEducationChanges(editedUniversity, editedFaculty, editedSpeciality, editedCourse, editedGroup, editedGraduationDate);
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
    //load documents
    $("#documentsHeader").click(function () {
      $.ajax
        ({
            type: "GET",
            url: "/info/getDocuments",
            async: true,
            data: {
                "studentId": studentId
            },
       success : function (data) {
            alert("" + data);
        },
        error: function(){
            alert("error");
        }});

    });

//handler for state select
    $("#state").change(
        checkState
    );
//handler termMark changed

    $(".term-mark-list").on("change", 'input', function () {
        if (!validInputTermMark($(this).val())) {
            $(this).addClass("termMarkInvalid", 1000);
            $(this).val(null);
        }
        else {
            $(this).removeClass("termMarkInvalid", 200);
        }
    });

    $("#addNextTerm").click(function () {
        var numberTerms;
        numberTerms = $("#termMarkList").children().length;
        var lastTerm;
        lastTerm = $(".term-mark-list li input").last();
//        console.log(document.getElementById("termMarkList").lastElementChild.lastElementChild);
//        console.log($(".termMarkList li input").last().val() + " is val");
        if (lastTerm.val() == MIN_MARK) {
            lastTerm.focus();
        }
        else {
            ++numberTerms;
            var nextTerm;
            nextTerm = "<li><input type=\"number\" class=\"termMark\" placeholder=\"10.00\"></li>";

            $(nextTerm).appendTo("#termMarkList");

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

function checkTermMark(termMark) {
    if (!validInputTermMark(termMark.val())) {
        termMark.animate({
            'background-color': "red",
            opacity: 0.5,
            'color': 'black'
        }, 1000);
        termMark.val(null);
    }
    else {
        termMark.animate({
            'background-color': "#ffffff",
            opacity: 1,
            color: "#333"
        }, 200);
    }
}

function parseRequestForId(string) {
    var gottenId;
    var regExpForId = /id=[0-9]+/;
    gottenId = string.match(regExpForId);
    var regExp = /[0-9]+/;
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

function saveEducationChanges(editedUniversity, editedFaculty, editedSpeciality, editedCourse, editedGroup, editedGraduationDate) {
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
            'studentGraduationDate': editedGraduationDate
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








