/**
 * Created by ala'n on 14.07.2014.
 */

window.onload = function(){
    headerControl();
};
document.getElementById('checkAll').onclick=function(){
    var globalChecked = document.getElementById('checkAll').checked;
    var itemCheckBoxes = document.getElementsByClassName("item-checkbox");
    if(itemCheckBoxes) {
        for( var i =0 ; i < itemCheckBoxes.length; i++) {
            itemCheckBoxes[i].checked = globalChecked;
        }
    }
};

//document.getElementById('studTable').

/////////////////////////////////////////////////////////////////////////////
function headerControl(){
    var scrolligBlock = document.getElementById("headerScrollingBlock");
    //////// Moving header with table //////////
    $(window).scroll(function () {
        var offset = $(this).scrollLeft();
        console.log("Script : ",offset);
        scrolligBlock.scrollLeft = offset;
    });
};

function upateInfoLabel(){
    var itCount = document.getElementById("studTable").getElementsByTagName('tr').length;
    if( itCount > 1 ) {
        $("#infoLabel").show();
        $("#infoLabel").text(itCount + " item in list");
    }else {
        $("#infoLabel").hide();
    }
}

function addRow(entry, count){
    //console.log("Add "+entry+" count " + count);
    var content = "<tr> <td><input type='checkbox' class='item-checkbox'></td>";
    for(var i=0; i<count; i++ ){
        content += "<td>" + entry[i]+"</td>";
    };

    content += "</tr>";
 //   console.log("Code :"+content);

     $("#studTable").append(content);
};
function addStudents(arrStud){
    arrStud.forEach(function (element, index, array) {
        if (element) {
            var arg = ["", "", "", "", "", "", "", "", "", ""];
            if (element.name) {
                arg[0] = element.name;
            }
            if (element.hireDate) {
                arg[1] = element.hireDate;
            }
            if (element.faculty) {
                arg[2] = element.faculty;
            }
            arg[3] = ((element.course) ? element.course : "?") + "-" + ((element.studentGroup) ? element.studentGroup : "?");
            if (element.graduationDate) {
                arg[4] = element.graduationDate.getYear();
            }
            if (element.wokingHours) {
                arg[5] = element.wokingHours;
            }
            arg[6] = (element.billable ? element.billable : "-");
            if (element.roleCurrentProject) {
                arg[7] = element.roleCurrentProject;
            }
            if (element.technologyCurrentProject) {
                arg[8] = element.technologyCurrentProject;
            }
            if (element.englishLevel) {
                arg[9] = element.englishLevel;
            }
            addRow(arg, 10);
        }
    });
};

function clearTable(){
    var table = document.getElementById('studTable');
    for (; table.getElementsByTagName('tr').length > 1; ) {
        table.deleteRow(1);
    }
};
function setTableLoadingState(loading){
    if(loading){
        $("#studTable").hide();
        $("#loading_image").show();
       // $("#infoLabel").hide();
    }else{
        $("#studTable").show();
        $("#loading_image").hide();
       // $("#infoLabel").show();
    }
};
/////////////////////////////////////////////////////////////////////////////

function loadTable(){
 //   setTableLoadingState(true);

    var searchText = $("#searchLine").val();//document.getElementById("searchLine").;

    $.get("/list/data",
        {
            'name': searchText
        }
    ).done(function( data ) {
            var obj = JSON.parse(data);
            if(obj) {
                clearTable();
                addStudents(obj);
                setTableLoadingState(false);
            }
    }).fail(function() {
        alert( "error" );
    });
};