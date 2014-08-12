/**
 * Created by ala'n on 08.08.2014.
 */
define(['jquery', 'jquery-animate-colors'], function ($) {
    "use strict";

    function logout() {
        sessionStorage.removeItem("filter");
        sessionStorage.removeItem("search");
        sessionStorage.removeItem("isStudent");
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
        logout: logout,
        menuLocationRelativeTo : setMenuLocationRelativeTo,
        formatDate : formatDate,
        stateAnimate: btnStateAnimate
       
    };
});