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
            dataType: 'json'
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

    function btnStateAnimate(btn, state, stateText) {
        var text = btn.text(),
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
            if (stateText) {
                btn.text(text);
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
        if (stateText) {
            btn.text(stateText);
        }
        btn.animate(stateColors, options);
    }

    function setMenuLocationRelativeTo(menu, owner) {
        //TODO optimize!
        var winOffsetW = $(window).width(),
            position = owner.offset(),
            left = position.left,
            top = position.top + owner.height(),
            space = position.left + menu.width() - winOffsetW + 4;
        if (space > 0) {
            top -= space;
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
        login : login,
        logout: logout,
        menuLocationRelativeTo : setMenuLocationRelativeTo,
        formatDate : formatDate,
        stateAnimate: btnStateAnimate
       
    };
});