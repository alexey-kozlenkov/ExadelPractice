/**
 * Created by Administrator on 08.08.2014.
 */

var Accordion = (function () {
    "use strict";
    function init() {
        //hide content after manual information
        $(".category-list .category-content:gt(0)").hide();

        //toggle category-content
        $(".category-header").click(function () {
            $(this).next(".category-content").slideToggle(500);
            return false;
        });
    }
    return {
        init: init
    };
}());


