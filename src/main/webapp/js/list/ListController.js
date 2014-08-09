/**
 * Created by ala'n on 31.07.2014.
 */
define(["jquery"], function ($, Handlebars) {
    "use strict";

    function init() {
        var $table = $("#studTable");
        $("#checkAll").click(function () {
            setCheckedAll($(this).prop("checked"));
        });
        $table.click(function () {
            updateInfoLabel();
        });
        updateInfoLabel();
    }

    function addAllStudents(arrStudents) {
        require(["handlebars", "text!templates/user-list-template.html"],
            function (Handlebars, template) {
                $("#studTable > tbody").append(Handlebars.compile(template)({list: arrStudents}));
            }
        );
        $("#checkAll").attr('checked', false);
        updateInfoLabel();
    }

    function clearList() {
        $("#studTable > tbody").empty();
        updateInfoLabel();
    }

    function getCheckedRowsId() {
        var checkedList = [],
            count = 0;

        $('.item-checkbox:checked').each(function (index, element) {
            checkedList[count] = Number($(element).attr("data-id"));
            count++;
        });
        return checkedList;
    }

    function setCheckedAll(state) {
        $('.item-checkbox').each(function () {
            this.checked = state;
        });
        updateInfoLabel();
    }

    function setTableLoadingState(loading) {
        if (loading) {
            $("#studTable").hide();
            $("#loading_image").show();
        } else {
            $("#studTable").show();
            $("#loading_image").hide();
        }
    }

    function updateInfoLabel() {
        var itCount = $("#studTable > tbody > tr").length,
            selCount;
        if (itCount > 0) {
            selCount = $(".item-checkbox:checked").length;
            $("#infoLabel").text("------------- " + itCount + " item in list " + selCount + " selected -------------");

        } else {
            $("#infoLabel").text("------------- List is empty -------------");
        }
    }

    return {
        init: init,
        addAllStudents: addAllStudents,
        clearList: clearList,

        getCheckedRowsId: getCheckedRowsId,
        setCheckedAll: setCheckedAll,
        setTableLoadingState: setTableLoadingState
    };
});

