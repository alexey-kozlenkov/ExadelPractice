/**
 * Created by ala'n on 31.07.2014.
 */
define(["jquery"], function ($, Handlebars) {
    "use strict";

    var viewStudents = undefined;

    function init() {
        var $table = $("#studTable");
//        initHeader(true);
        $("#listHeader").on("click", "#checkAll", function () {//input[type=checkbox]
            setCheckedAll($(this).prop("checked"));
        });
        $table.click(function () {
            updateInfoLabel();
        });
        updateInfoLabel();
    }

    function initHeader(isStudents) {
        require(["handlebars", "text!templates/header-list-template.html", "ListHeader"],
            function (Handlebars, template, header) {
                $("#listHeader").html(Handlebars.compile(template)(
                    {
                        isStudents: isStudents
                    }
                ));
                header.check();
            }
        );
    }

    function addAll(data, isStudents) {
        if (viewStudents !== isStudents) {
            viewStudents = isStudents;
            initHeader(isStudents);
        }
        require(["handlebars", "text!templates/user-list-template.html"],
            function (Handlebars, template) {
                $("#studTable > tbody").append(Handlebars.compile(template)(
                    {
                        isStudents: isStudents,
                        list: data
                    }
                ));
                $("#checkAll").attr('checked', false);
                updateInfoLabel();
            }
        );
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
        addAll: addAll,
        clearList: clearList,

        getCheckedRowsId: getCheckedRowsId,
        setCheckedAll: setCheckedAll,
        setTableLoadingState: setTableLoadingState
    };
});

