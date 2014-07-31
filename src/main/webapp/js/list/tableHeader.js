/**
 * Created by ala'n on 31.07.2014.
 */


var pastHeaderHeight = $("#headerBlock").outerHeight();

$(window).ready(function () {
    checkEmptyFieldSize();
});

$(document).ready(function () {
    $(window).resize(function () {
        var height = $("#headerBlock").outerHeight();
        if (pastHeaderHeight != height) {
            pastHeaderHeight = height;
            checkEmptyFieldSize();
        }
    });

    $(window).scroll(function () {
        var offset = $(this).scrollLeft();
        $("#headerScrollingBlock").scrollLeft(offset);
    });

    checkEmptyFieldSize();
});

function checkEmptyFieldSize() {
    var headerHeight = $("#headerBlock").height();
    $("#emptyField").css("height", headerHeight + "px");
}