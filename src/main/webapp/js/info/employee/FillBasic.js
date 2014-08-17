/**
 * Created by Administrator on 14.08.2014.
 */

define(["jquery", "handlebars", "Util", "text!templates/term-mark-template.html"], function ($, Handlebars, util, templateTermMarkContent) {
    "use strict";
    var employeeId;

    function init() {
        util.initAccessRoleForInfo();
        employeeId = util.parseRequestForId(window.location.search);
        fillCommonInfo();
    }
    function fillCommonInfo() {
            var getEmployee =   $.ajax({
                type: "GET",
                url: "/info/getCommonInformation",
                cache: false,
                data: {
                    "id": employeeId
                }
            });
            getEmployee.done(function (data) {
                var gottenUser = JSON.parse(data);
                $("#sessionUsername").text(sessionStorage.getItem("username"));

                $("#headerName").text(gottenUser.name);
                $("#name").val(gottenUser.name);
                $("#birthDate").val(gottenUser.birthdate);
                $("#login").val(gottenUser.login);
                $("#password").val(gottenUser.password);
                $("#email").val(gottenUser.email);
                $("#skype").val(gottenUser.skype);
                $("#phone").val(gottenUser.telephone);
            });
            getEmployee.fail(function () {
                alert("loading failed");
            });

        }
    return {
        employeeId: function () { return employeeId; },
        init: init
    };
});
