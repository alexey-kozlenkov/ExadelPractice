/**
 * Created by Administrator on 14.08.2014.
 */

define(["jquery", "handlebars", "FillBasicEmployee", "jquery-animate-colors"],
    function ($, Handlebars, fillBasicEmployee) {
        "use strict";
        function init() {
            //event handlers for submit button save
            $("#saveManualInformation").click(function () {
                var editedName = $("#name").val(),
                    editedBirthDate = $("#birthDate").val(),
                    editedLogin = $("#login").val(),
                    editedEmail = $("#email").val(),
                    editedSkype = $("#skype").val(),
                    editedPhone = $("#phone").val(),
                    editedPassword = $("#password").val();

                saveManualInfoChanges(editedName, editedBirthDate, editedLogin, editedEmail, editedSkype, editedPhone, editedPassword);
            });
        }

        function saveManualInfoChanges(editedName, editedBirthDate, editedLogin, editedEmail, editedSkype, editedPhone, editedPassword) {
            var postManual =   $.ajax({
                type: "POST",
                //SEND
                url: "/info/postEmployeeManualInformation",
                dataType: 'json', // from the server!
                data: {
                    'employeeId': fillBasicEmployee.employeeId(),
                    'employeeName': editedName,
                    'employeeBirthDate': editedBirthDate,
                    'employeeLogin': editedLogin,
                    'employeePassword': editedPassword,
                    'employeeEmail': editedEmail,
                    'employeeSkype': editedSkype,
                    'employeePhone': editedPhone
                }
            });
            postManual.done(function () {
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
            });
            postManual.fail(function () {
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
            });
        }


        return {
            init : init
        };


    });
