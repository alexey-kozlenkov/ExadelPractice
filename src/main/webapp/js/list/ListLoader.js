/**
 * Created by ala'n on 08.08.2014.
 */

define(["jquery", "ListController"], function ($, ListController) {
    "use strict";
    var actualVersion = 0;

    function initSearchLine() {
        $("#startSearchButton").click(function () {
            loadTable();
        });
        $("#searchLine").focus(function () {
            $(this).animate({ width: "250pt"}, 1000);
        }).blur(function () {
            $(this).animate({ width: "150pt"}, 500);
        });
        $('#searchLine').on('input', function () {
            loadTable();
        });
    }

    function loadTable() {
        var version = Date.now(),
            search = $("#searchLine").val(),
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
                'filter': filter
            }
        });

        promise.done(function (data) {
            updateListByResponse(JSON.parse(data));
        });
        promise.fail(function () {
            alert("error");
            ListController.setTableLoadingState(false);
        });
    }

    function updateListByResponse(response) {
        if (actualVersion === response.version) {
            console.log("Get actual response (", actualVersion, ")");
            ListController.clearList();
            ListController.addAllStudents(response.studentViews);
            ListController.setTableLoadingState(false);
        } else {
            console.log("Response (", response.version, ") was ignored; actual: ", actualVersion, "; now: ", Date.now());
        }
    }

    return {
        init: initSearchLine,
        load: loadTable
    };
});