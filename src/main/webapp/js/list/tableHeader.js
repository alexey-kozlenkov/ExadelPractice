/**
 * Created by ala'n on 31.07.2014.
 */


var pastHeaderHeight = $("#header").outerHeight();

$(window).ready(function () {
    checkContentMargin();
});

$(document).ready(function () {
    $(window).resize(function () {
        var height = $("#header").outerHeight();
        if (pastHeaderHeight != height) {
            pastHeaderHeight = height;
            checkContentMargin();
        }
    });

    $(window).scroll(function () {
        var offset = $(this).scrollLeft();
        $("#headerScrollingBlock").scrollLeft(offset);
    });

    checkContentMargin();
});

function checkContentMargin() {
    var headerHeight = $("#header").height();
    $("#listContent").css("margin-top", headerHeight + "px");
}