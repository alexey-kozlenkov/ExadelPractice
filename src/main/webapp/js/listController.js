/**
 * Created by ala'n on 14.07.2014.
 */

window.onload = function(){
    headerScrollControl();
    checkEmptyFieldSize();
    updateInfoLabel();
};

document.getElementById('checkAll').onclick = onCheckedAll();
//document.getElementById('headerBlock').onresize = checkEmptyFieldSize(); TODO!!!!

function loadTable(){
    setTableLoadingState(true);

    var searchName = $("#searchLine").val();

    $.ajax({
        type: "GET",
        url: "/list/data",
        async: true,
        data: {
            'name': searchName
        }
    }).done(function( data ) {
            var obj = JSON.parse(data);
            if(obj) {
                clearTable();
                addAllStudents(obj);
                setTableLoadingState(false);
            }
    }).fail(function() {
        alert( "error" );
        setTableLoadingState(false);
    });
    updateInfoLabel();
};

function showFilter(){
//    var $popup = $("#filterPopup");
//    $popup.showPopup();
    checkEmptyFieldSize();
};

////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////    Checker   /////////////////////////////////////////////
function getCheckedRowsId(){
    var checkedList = [];
    var count = 0;

    $('.item-checkbox:checked').each(function(index, element){
        checkedList[count] = Number(element.id.replace("cb_",""));
        count++;
    });
    return checkedList;
};
function onCheckedAll(){//TODO! review
    var globalChecked = document.getElementById('checkAll').checked;
    var itemCheckBoxes = document.getElementsByClassName("item-checkbox");
    if(itemCheckBoxes) {
        for( var i =0 ; i < itemCheckBoxes.length; i++) {
            itemCheckBoxes[i].checked = globalChecked;
        }
    }
};
///////////////////////////////////// Table Utils //////////////////////////////////////////////
function addAllStudents(arrStudents){
    arrStudents.forEach(function(student){
        addStudent(student);
    });
};
function addStudent(student){
    var arg = ["", "", "", "", "", "", "", "", "", ""];

    if (student.name) {
        arg[0] = element.name;
    }
    if (student.hireDate) {
        arg[1] = element.hireDate;
    }
    if (student.faculty) {
        arg[2] = element.faculty;
    }

    arg[3]  = ((student.course) ? student.course : "?");
    arg[3] += "-";
    arg[3] += ((student.studentGroup) ? student.studentGroup : "?");

    if (student.graduationDate) {
        arg[4] = student.graduationDate.getYear();
    }
    if (student.wokingHours) {
        arg[5] = student.wokingHours;
    }

    arg[6] = (student.billable ? student.billable : "-");

    if (student.roleCurrentProject) {
        arg[7] = student.roleCurrentProject;
    }
    if (element.technologyCurrentProject) {
        arg[8] = student.technologyCurrentProject;
    }
    if (element.englishLevel) {
        arg[9] = student.englishLevel;
    }

    addRow(student.id, arg, 10);
};

function addRow(rowId, columnValues, count){
    var content;
    content = "<tr> <td><input id='cb_"+rowId+"' type='checkbox' class='item-checkbox'></td>";
    for(var i=0; i < count; i++ ){
        content += "<td>" + columnValues[i]+"</td>";
    };
    content += "</tr>";

    $("#studTable").append(content);
};
function clearTable(){
    $("#studTable > tbody").empty();
};
///////////////////////////////////// Table Subutil ////////////////////////////////////////////
function headerScrollControl(){
    var scrolligBlock = document.getElementById("headerScrollingBlock");
    //////// Moving header with table //////////
    $(window).scroll(function () {
        var offset = $(this).scrollLeft();
        console.log("Script : ",offset);
        scrolligBlock.scrollLeft = offset;
    });
};
function checkEmptyFieldSize(){
    var headerHeight = $("#headerBlock").height();
    headerHeight    -= $("#header-fixed").height();
    $("#emptyField").css("height", headerHeight+"px");
};
function setTableLoadingState(loading){
    if(loading){
        $("#studTable").hide();
        $("#loading_image").show();
    }else{
        $("#studTable").show();
        $("#loading_image").hide();
    }
};
function updateInfoLabel(){
    var itCount = document.getElementById("studTable").getElementsByTagName('tr').length;
    if( itCount > 1 ) {
        $("#infoLabel").text("-------------------- "+(itCount-1) + " item in list ---------------------");
    }else {
        $("#infoLabel").text("---------------------------------------------------------------------");
    }
};
////////////////////////////////////////////////////////////////////////////////////////////////