var studentId;

window.onload = function () {
    // alert(window.location.search);
    parseRequestForId(window.location.search)
    fillOptions();
    fill();
};

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
    //event handler for submit button
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

function fill() {
    $.ajax
    ({
        type: "GET",
        //SEND TO CONTROLLER
        url: "/info/getInformation",
//        dataType: 'json',
        async: true,
        data: studentId,
        success: function (data) {
            // alert("" + data);
            var gottenStudent = JSON.parse(data);
            $("#headerName").text(gottenStudent.name);
            $("#name").val(gottenStudent.name);
            $("#login").val(gottenStudent.login);
            $("#password").val(gottenStudent.password);
            $("#email").val(gottenStudent.email);
            $("#role").find("option:contains(" + "\'" + gottenStudent.role + "\')").attr("selected", "selected");
            $("#state").find("option:contains(" + "\'" + gottenStudent.studentInfo.state + "\')").attr("selected", "selected");
        }
    });
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
            //alert("success");
            $("#headerName").text(editedName);
        },
        error: function () {
            alert("error");
        }
    });
}