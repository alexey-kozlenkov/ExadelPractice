/**
 * Created by ala'n on 08.08.2014.
 */

define(["jquery", "ListController", "ListHeader"], function ($, ListController, ListHeader) {
    "use strict";
    var TIME_DELAY = 300,
        actualVersion = 0,
        isStudentTab = true,
        sendTimeout;

    function init() {
        bindSearchLine();
        $("body").on("listRequestChanged", onRequestChange);
        $("#studTab").click(function () {
            tab(true);
            updateList();
        });
        $("#emplTab").click(function () {
            tab(false);
            updateList();
        });
        storageRevision();
    }

    function bindSearchLine() {
        $("#startSearchButton").click(function () {
            sessionStorage.setItem("search", $(this).val());
            tab(isStudentTab);
            $("body").trigger("listRequestChanged", {by: "button"});
        });
        $("#searchLine").focus(function () {
            $(this).animate({ width: "250pt"}, 1000);
        }).blur(function () {
            $(this).animate({ width: "150pt"}, 500);
        });
        $('#searchLine').on('input', function () {
            sessionStorage.setItem("search", $(this).val());
            $("body").trigger("listRequestChanged", {by: "search"});
        });
    }

    function storageRevision() {
        var isStudTab = sessionStorage.getItem("isStudentTab"),
            search = sessionStorage.getItem("search");
        tab(isStudTab == "true");
        if (search) {
            $("#searchLine").val(search);
        }
        if (isStudTab || search) {
            updateList();
        }
    }

    function tab(isStudTab) {
        if (isStudTab !== null && isStudTab !== undefined) {
            isStudentTab = isStudTab;
            sessionStorage.setItem("isStudentTab", isStudentTab);
            updateButtons();
        }
        return isStudentTab;
    }
    function updateButtons() {
        $("#studTab").attr("enabled", isStudentTab);
        $("#emplTab").attr("enabled", !isStudentTab);
        if (isStudentTab) {
            $("#exportMenuButton").fadeIn(100);
            $("#filter").fadeIn(100, ListHeader.check);
        }
        else {
            $("#exportMenuButton").fadeOut(100);
            $("#filter").fadeOut(100, ListHeader.check);
        }
    }

    function onRequestChange(e) {
        console.log("# search or filter update #");
        if (sendTimeout) {
            clearTimeout(sendTimeout);
        }
        sendTimeout = setTimeout(updateList, TIME_DELAY);
    }

    function updateList() {
        console.log('------- update list -------');
        var version = Date.now(),
            isStud = sessionStorage.getItem("isStudentTab"),
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
                'isStudent': isStud
            }
        });

        promise.done(function (data) {
            updateListByResponse(JSON.parse(data));
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

            ListController.addAll(response.views, tab());

            ListController.setTableLoadingState(false);
        } else {
            console.log("Response (", response.version, ") was ignored; actual: ", actualVersion, "; now: ", Date.now());
        }
    }

    return {
        init: init,
        forseUpdate: updateList,
        update: function () {
            $("body").trigger("listRequestChanged");
        },
        tab: tab
    };
});