/**
 * Created by ala'n on 31.07.2014.
 */

$(document).ready(function () {
    $("#checkAll").click(function(){
        setCheckedAll($(this).prop("checked"));
    });
    $("#studTable").click(function () {
        updateInfoLabel();
    });
    updateInfoLabel();
});

function addAllStudents(arrStudents) {
    var rowTemplate = Handlebars.compile( $('#listContentTemplate').html() );
    $("#studTable > tbody").append(rowTemplate({list:arrStudents}));
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