/**
 * Created by ala'n on 08.08.2014.
 */
define(["jquery"], function () {
    "use strict";

    function logout() {
        sessionStorage.removeItem("filter");
    }
    function logoutBind() {
        $(document).ready(function () {
            $(".logout-link").click(logout);
        });
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
        menuLocationRelativeTo : setMenuLocationRelativeTo
    };
});