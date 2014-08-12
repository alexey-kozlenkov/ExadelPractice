/**
 * Created by ala'n on 08.08.2014.
 */
define(["jquery", "jquery-animate-colors"], function ($) {
    "use strict";

    function logout() {
        sessionStorage.removeItem("filter");
        sessionStorage.removeItem("search");
        sessionStorage.removeItem("isStudent");
    }
    function logoutBind() {
        $(document).ready(function () {
            $(".logout-link").click(logout);
        });
    }

    function btnStateAnimate(btn, state, stateText) {
        var text = btn.val(),
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
                btn.val(text);
            }
        }, 1000);

        switch (state) {
            case 'good':
                stateColors.backgroundColor = '#5cb85c';
                stateColors.borderColor = '#4cae4c';
                break;
            case 'bad':
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

    return {
        logout: logout,
        stateAnimate: btnStateAnimate,
        menuLocationRelativeTo : setMenuLocationRelativeTo
    };
});