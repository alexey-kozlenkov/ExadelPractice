/**
 * Created by ala'n on 08.08.2014.
 */

define(["jquery", "ListController"], function ($, ListController) {
    "use strict";
    var TIME_DELAY = 200,
        actualVersion = 0,
        isStudents = true,
        sendTimeout;

    function init() {
        bindSearchLine();
        $("body").on("searchOrFieldUpdate", onSearchOrFieldUpdate);
        $("#studTab").click(function () {
            isStudents = true;
            updateList();
        });
        $("#emplTab").click(function () {
            isStudents = false;
            updateList();
        });
    }
    function bindSearchLine() {
        $("#startSearchButton").click(function () {
            $("body").trigger("searchOrFieldUpdate", {type: "all"});
            sessionStorage.setItem("search", $(this).val());
        });
        $("#searchLine").focus(function () {
            $(this).animate({ width: "250pt"}, 1000);
        }).blur(function () {
            $(this).animate({ width: "150pt"}, 500);
        });
        $('#searchLine').on('input', function () {
            $("body").trigger("searchOrFieldUpdate", {type: "search"});
            sessionStorage.setItem("search", $(this).val());
        });
    }

    function onSearchOrFieldUpdate(e) {
        console.log("# search or filter update #");
        if (sendTimeout) {
            clearTimeout(sendTimeout);
        }
        sendTimeout = setTimeout(updateList, TIME_DELAY);
    }

    function updateList() {
        console.log('------- update list -------');
        var version = Date.now(),
            search = sessionStorage.getItem("search"),
            filter = sessionStorage.getItem("filter"),
            promise;
        ListController.setTableLoadingState(true);
        actualVersion = version;
        promise = $.ajax({
            url: "/list/data",
            cache: false,
            data: {
                'version': version,
                'searchName': search,
                'filter': filter,
                'isStudent': isStudents
            }
        });

        promise.done(function (data) {
            var state = updateListByResponse(JSON.parse(data));
            ListController.setTableLoadingState(false);
        });
        promise.fail(function () {
            console.error("data not get!");
            ListController.setTableLoadingState(false);
        });
    }
    function updateListByResponse(response) {
        if (actualVersion === response.version) {
            console.log("Get actual response (", actualVersion, ")");
            ListController.clearList();
            ListController.addAll(response.views, isStudents);

            ListController.setTableLoadingState(false);
        } else {
            console.log("Response (", response.version, ") was ignored; actual: ", actualVersion, "; now: ", Date.now());
        }
    }

    return {
        init: init,
        forseUpdate: updateList,
        update: function () {
            $("body").trigger("searchOrFieldUpdate");
        }
    };
});