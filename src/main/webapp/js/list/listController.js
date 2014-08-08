/**
 * Created by ala'n on 31.07.2014.
 */
var ListController = (function () {
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
        var rowTemplate = Handlebars.compile($('#listContentTemplate').html());
        $("#studTable > tbody").append(rowTemplate({list: arrStudents}));
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
}());

var ListHeader = (function () {
    "use strict";

    var pastHeaderHeight;

    function init() {
        $(window).resize(function () {
            var height = $("#header").outerHeight();
            if (pastHeaderHeight !== height) {
                pastHeaderHeight = height;
                checkTopMarginOfList();
            }
        });
        // Correct header vertical position according to position of list on scrolling
        $(window).scroll(function () {
            $("#headerScrollingBlock").scrollLeft($(this).scrollLeft());
        });
        checkTopMarginOfList();
    }

    function checkTopMarginOfList() {
        $("#listContent").css("margin-top", $("#header").height() + "px");
    }

    return {
        init: init,
        check: checkTopMarginOfList
    };
}());

$(document).ready(ListController.init());
$(window).ready(ListHeader.init());


