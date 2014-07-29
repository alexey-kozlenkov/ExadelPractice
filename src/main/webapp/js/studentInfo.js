
var studentId;

$(window).ready(function () {
    // alert(window.location.search);
    parseRequestForId(window.location.search);
    fillOptions();
    fillManualInfo();
});

$(document).ready(function () {

    //hide content after manual
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
        var editedRole = $("#role :selected").val();
        var editedState = $("#state :selected").val();

        saveManualInfoChanges(editedName, editedLogin, editedEmail, editedPassword, editedRole, editedState);
    });
    $("#saveEducationInformation").click(function () {
        //collect data entered by users
        var editedUniversity = $("#institution").val();
        var editedFaculty = $("#faculty").val();
        var editedSpeciality = $("#speciality").val();
        var editedCourse = $("#course").val();
        var editedGroup = $("#group").val();
        var editedGraduationDate = $("#graduationDate").val();

        saveEducationChanges(editedUniversity,editedFaculty,editedSpeciality,editedCourse,editedGroup,editedGraduationDate);
    });

    //handler for state select
    $("#state").change(
        checkState
    );

});

function parseRequestForId(string) {
    var gottenId;
    var regExpForId = /id=[0-9]+/;
    gottenId = string.match(regExpForId);
    var regExp = /[0-9]+/;
    studentId = (gottenId[0].match(regExp))[0];
};

function fillOptions() {
    $.ajax({
        type: "GET",
        url: "/info/getOptions",
        async: true,
        success: function (data) {
            // alert("" + data);

            $("#role").empty();
            $("#state").empty();
            //filling
            var options = JSON.parse(data);
            var roleOptions = options.roles;
            roleOptions.forEach(function (element, index, array) {
                $("#role").append($("<option value=" + element + ">" + element + "</option>"));
            })

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
        url: "/info/getManualInformation",
        async: true,
        data: studentId,
        success: function (data) {
           //  alert("" + data);
            var gottenStudent = JSON.parse(data);
            $("#headerName").text(gottenStudent.name);
            $("#name").val(gottenStudent.name);
            $("#login").val(gottenStudent.login);
            $("#password").val(gottenStudent.password);
            $("#email").val(gottenStudent.email);
            $("#role").find("option:contains(" + "\'" + gottenStudent.role + "\')").attr("selected", "selected");
            $("#state").find("option:contains(" + "\'" + gottenStudent.studentInfo.state + "\')").attr("selected", "selected");
            checkState();
            $("#institution").val(gottenStudent.studentInfo.university);
            $("#faculty").val(gottenStudent.studentInfo.faculty);
            $("#speciality").val("applied maths");
            $("#course").val(gottenStudent.studentInfo.course);
            $("#group").val(gottenStudent.studentInfo.group);
           $("#graduationDate").val(gottenStudent.studentInfo.graduationDate);
        }
    });
}

function checkState(){
    var state = $("#state :selected").text();
    if(state == 'working'){
        $("#exadelHeader").show();

    }
    else{
        if($("#exadelContent").is(":visible")){
            $("#exadelContent").slideToggle(100);
        }
        $("#exadelHeader").hide(200);
    }
}

function saveManualInfoChanges(editedName, editedLogin, editedEmail, editedPassword, editedRole, editedState) {
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
            'studentRole': editedRole,
            'studentState': editedState
        },
        success: function () {
            $("#saveManualInformation").text("Saved!");
            $("#saveManualInformation").animate({
                backgroundColor: '#5cb85c',
                borderColor : '#4cae4c'
            }, {
                duration: 500,
                easing: "swing",
                complete: setTimeout(function () {
                    $("#saveManualInformation").animate({
                        backgroundColor: '#4A5D80',
                        borderColor : '#2D3E5C'
                    }, 500);
                    $("#saveManualInformation").text("Save");
                },1000)
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

function saveEducationChanges(editedUniversity,editedFaculty,editedSpeciality,editedCourse,editedGroup,editedGraduationDate){
    $.ajax
    ({
        type: "POST",
        //SEND
        url: "/info/postEducation",
        dataType: 'json', // from the server!
        data: {
            'studentId': studentId,
            'studentUniversity' : editedUniversity,
            'studentFaculty' : editedFaculty,
            'studentSpeciality' : editedSpeciality,
            'studentCourse' : editedCourse,
            'studentGroup' : editedGroup,
            'studentGraduationDate' : editedGraduationDate
        },
        success: function () {
            $("#saveEducationInformation").text("Saved!");
            $("#saveEducationInformation").animate({
                backgroundColor: '#5cb85c',
                borderColor : '#4cae4c'
            }, {
                duration: 500,
                easing: "swing",
                complete: setTimeout(function () {
                    $("#saveEducationInformation").animate({
                        backgroundColor: '#4A5D80',
                        borderColor : '#2D3E5C'
                    }, 500);
                    $("#saveEducationInformation").text("Save");
                },1000)
            });
        },
        error: function () {
            alert("error");
        }
    });
}








