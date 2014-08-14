define(["jquery", "jquery-animate-colors"], function ($) {
    "use strict";
    /*
     roles : 0 - student  1 - student_employee
      2 - feedbacker  3 - curator
      4 - admin  5 - super_admin
     */
    function login() {
        var loginGet = $.ajax({
            type: "GET",
            url: "/login/info",
            dataType: 'json',
            async: false
        });
        loginGet.done(function (data) {
            sessionStorage.setItem("username", data.username);
            sessionStorage.setItem("role", data.role);
            sessionStorage.setItem("id", data.id);
        });
        loginGet.fail(function (data) {
            alert("Server has failed");
        });
    }
    function logout() {
        sessionStorage.clear();
    }

    function initAccessRoleForStudentInfo() {
        login();
        var role = sessionStorage.getItem("role");
        switch (role) {
            case '0' :
            case '1' :
                $(".back-link").prop("hidden", true);
                $(".select-state").prop("disabled", true);
                $(".current-project-content input, .current-project-content textarea").prop("disabled", true);
                $("#feedbacksHeader").prop("hidden", true);
                break;
            case '2' :
                $(".info-edit input, .info-edit select, .info-edit textarea").prop("disabled", true);
                $(".info-edit button").prop("hidden", true);
                $(".for-feedbackers").removeAttr('hidden');
                break;
            case '3' :
                $(".info-edit input, .info-edit select, .info-edit textarea").prop("disabled", true);
                $(".info-edit button").prop("hidden", true);
                break;
            case '4' :
                $(".info-edit input, .info-edit textarea").prop("disabled", true);
                $(".info-edit button").prop("hidden", true);

                $(".select-state").prop("disabled", false);
                $("#saveManualInformation").prop("hidden", false);

                $("#educationContent select").prop("disabled", true);

                $(".exadel-content input, .exadel-content textarea").prop("disabled", false);
                $(".exadel-content button").prop("hidden", false);

                $(".feedback-edit button").prop("hidden", true);
                break;
            case '5' :
                $(".feedback-edit button").prop("hidden", true);
                break;
        }
    }

    function initAccessRoleForList() {
        login();
        var role = sessionStorage.getItem("role");
        switch (role) {
            case '0' :
            case '1' :
            case '2' :
            case '3' :
            case '4' :
                $(".super-admin-role").prop("disabled", true);
                break;
            case '5' :
                $(".super-admin-role").prop("disabled", false);
                break;
        }
    }

    function btnStateAnimate(btn, state, stateContent) {
        var defContent = btn.html(),
            backgroundColor = btn.css('backgroundColor'),
            borderColor = btn.css('borderColor'),
            stateColors = {},
            options = {
                duration: 500,
                easing: "swing"
            };

        options.complete = setTimeout(function () {
            btn.animate({
                backgroundColor: backgroundColor,
                borderColor: borderColor
            }, 500);
            if (stateContent) {
                btn.html(defContent);
            }
        }, 1000);

        switch (state) {
            case 'success':
                stateColors.backgroundColor = '#5cb85c';
                stateColors.borderColor = '#4cae4c';
                break;
            case 'fail':
                stateColors.backgroundColor = '#CD5C5C';
                stateColors.borderColor = '#C16868';
                break;
            default:
                return;
        }
        if (stateContent) {
            btn.html(stateContent);
        }
        btn.animate(stateColors, options);
    }

    function setMenuLocationRelativeTo(menu, owner) {
        var winOffsetW = $(window).width(),
            position = owner.offset(),
            left = position.left,
            top = position.top + owner.height(),
            space = position.left + menu.width() - winOffsetW + 4;
        if (space > 0) {
            left -= space;
        }
        menu.css(
            {
                left: left,
                top: top
            }
        );
    }
    function formatDate(date) {
        var DD = date.getDate(),
            MM = date.getMonth() + 1,
            YYYY = date.getFullYear();
        if (DD < 10) {
            DD = '0' + DD;
        }
        if (MM < 10) {
            MM = '0' + MM;
        }
        return YYYY + "-" + MM + "-" + DD;
    }
    return {
        initAccessRoleForStudentInfo : initAccessRoleForStudentInfo,
        initAccessRoleForList: initAccessRoleForList,
        logout: logout,
        menuLocationRelativeTo : setMenuLocationRelativeTo,
        formatDate : formatDate,
        stateAnimate: btnStateAnimate
       
    };
});