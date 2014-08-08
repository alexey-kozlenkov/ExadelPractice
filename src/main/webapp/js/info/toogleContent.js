/**
 * Created by Administrator on 08.08.2014.
 */

$(document).ready(function () {
    "use strict";
    //hide content after manual information
    $(".category-list .category-content:gt(0)").hide();

    //toggle category-content
    $(".category-header").click(function () {
        $(this).next(".category-content").slideToggle(500);
        return false;
    });
});

