/**
 * Created by ala'n on 08.08.2014.
 */

require.config({
    baseUrl: "../",
    paths: {
        "jquery": "js/lib/jquery-2.1.1.min",
        "jquery-animate-colors": "js/lib/jquery.animate-colors-min",
        "underscore": "js/lib/underscore-min",
        "handlebars": "js/lib/handlebars-v1.3.0",

        "Dialog":           "js/common/Dialog",
        "Util":             "js/common/Util",

        "ListHeader":       "js/list/ListHeader",
        "ListController":   "js/list/ListController",
        "ListLoader":       "js/list/ListLoader",
        "Filter"   :        "js/list/Filter",
        "ListUtil" :        "js/list/ListUtil"

    },
    shim: {
        'handlebars': {
            exports: 'Handlebars'
        }
    }

});

require(["jquery", "ListHeader", "ListController", "Filter",
         "ListLoader", "ListUtil", "Util"],
    function ($, ListHeader, ListController, Filter, ListLoader, ListUtil, util) {
    "use strict";
    $(window).ready(function () {
        ListHeader.init();
    });
    $(document).ready(function () {
        $(".logout-link").click(util.logout);

        ListController.init();
        Filter.init();
        Filter.loadDescription("/list/filterDescription");
        ListLoader.init();
        ListUtil.init();
    });

    $(document).ready(function () {
        $("#toggleNav").click(
            function () {
                var f =  $("#filter");
                if (f.is(":visible")) {
                    f.fadeOut(500, ListHeader.check);
                }
                else {
                    f.fadeIn(500);
                    ListHeader.check();
                }
            }
        );
    });
});

