/**
 * Created by ala'n on 08.08.2014.
 */

define(["jquery"], function ($) {
    "use strict";

    var pastHeaderHeight;

    function init() {
        $(window).resize(function () {
            var height = $("#header").outerHeight();
            if (pastHeaderHeight !== height) {
                pastHeaderHeight = height;
                checkTopMarginOfList();
            }
        });
        // Correct header vertical position according to position of list on scrolling
        $(window).scroll(function () {
            $("#headerScrollingBlock").scrollLeft($(this).scrollLeft());
        });
        checkTopMarginOfList();
    }

    function checkTopMarginOfList() {
        $("#listContent").css("margin-top", $("#header").height() + "px");
    }

    return {
        init: init,
        check: checkTopMarginOfList
    };
});
