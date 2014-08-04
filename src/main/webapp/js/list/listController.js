/**
 * Created by ala'n on 31.07.2014.
 */

$(window).ready(function () {
    console.log("window.ready");
    var pastHeaderHeight;// check on start
    $(window).resize(function () {
        var height = $("#header").outerHeight();
        if (pastHeaderHeight != height) {
            pastHeaderHeight = height;
            checkTopMarginOfList();
        }
    });
    // Correct header vertical position according to position of list on scrolling
    $(window).scroll(function () {
        $("#headerScrollingBlock").scrollLeft($(this).scrollLeft());
    });
    checkTopMarginOfList();
});

$(document).ready(function () {
    console.log('doc.ready');
    $("#checkAll").click(function () {
        setCheckedAll($(this).prop("checked"));
    });
    $("#studTable").click(function () {
        updateInfoLabel();
    });
    updateInfoLabel();
    checkTopMarginOfList();
});

function checkTopMarginOfList() {
    $("#listContent").css("margin-top", $("#header").height() + "px");
}

function addAllStudents(arrStudents) {
    var rowTemplate = Handlebars.compile($('#listContentTemplate').html());
    $("#studTable > tbody").append(rowTemplate({list: arrStudents}));
    updateInfoLabel();
}
function clearList() {
    $("#studTable > tbody").empty();
    updateInfoLabel();
}

function getCheckedRowsId() {
    var checkedList = [];
    var count = 0;

    $('.item-checkbox:checked').each(function (index, element) {
        checkedList[count] = Number($(element).attr("data-id"));
        count++;
    });
    return checkedList;
};
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
    var itCount = $("#studTable > tbody > tr").length;
    if (itCount > 0) {
        var selCount = $(".item-checkbox:checked").length;
        $("#infoLabel").text("------------- " + itCount + " item in list " + selCount + " selected -------------");

    } else {
        $("#infoLabel").text("------------- List is empty -------------");
    }
}