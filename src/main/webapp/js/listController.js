/**
 * Created by ala'n on 14.07.2014.
 */
////////////////////////////////////////////////////////////////////////////////////////////////
var counter;
var filterTypes = {
    changed: true,
    keys: ['age', 'enum', 'role', 'name'],
    keyMap: {
        'age': null,
        'enum': ['first', 'second', 'third'],
        'role': ['admin', 'user', 'guest'],
        'name': null
    }
};
////////////////////////////////////////////////////////////////////////////////////////////////
$(window).ready(function () {
    checkEmptyFieldSize();
});
$(document).ready(function () {
    console.log("initialize page controllers...");
    headerScrollControl();
    updateInfoLabel();
    resizeControl();
    checkEmptyFieldSize();
    bindEventControl();
    console.log("initialize ended.");
});
function bindEventControl() {
    $("#checkAll").click(onCheckedAll);

    $("#addMenuButton").click(function () {
        addRow(0, ['Vasya Pupkin', '16.07.2014', 'FPM', '1-1', '2018', 12, '-', 'tester', 'php', 'Intermediate'], 10);
        updateInfoLabel();
    });
    $("#exportMenuButton").click(function () {
        clearTable();
        updateInfoLabel();
    });
    $("#distributionMenuButton").click(function () {
        alert(getCheckedRowsId());
    });
    $("#startSearchButton").click(function () {
        loadTable();
        updateInfoLabel();
    });

    $("#menu").mouseleave(
        function () {
            $('#menu').hide(400);
        }
    );

    $("#addFilterButton").click(function () {
        addFilterAction();
    });


    $("#searchLine").focus(function () {
        $(this).animate({ width: "250pt"}, 1000);
    }).blur(function () {
        $(this).animate({ width: "150pt"}, 500);
    });
}

function loadTable() {
    setTableLoadingState(true);

    var search = $("#searchLine").val();
    var filter = pickFilters();

    var filterPack = JSON.stringify(filter);

    $.ajax({
        type: "GET",
        url: "/list/data",
        async: true,
        data: {
            'name': search,
            'filter': filterPack
        }
    }).done(function (data) {
        var obj = JSON.parse(data);
        if (obj) {
            clearTable();
            addAllStudents(obj);
            updateInfoLabel();
            setTableLoadingState(false);
        }
    }).fail(function () {
        alert("error");
        setTableLoadingState(false);
    });
}
////////////////////////////////////// Toggle popup ////////////////////////////////////////////
function togglePopup(popup) {
    if (popup.is(':visible')) {
        popup.hide();
        return false;
    } else {
        popup.show();
        return true;
    }
}
function centrePopup(popup, x, y) {
    var WIDTH = $(window).width();
    var HEIGHT = $(window).height();
    var popupWidth = popup.width();
    var popupHeight = popup.height();
    if (x == undefined || x == null) {
        x = (WIDTH - popupWidth) / 2;
    }
    if (y == undefined || y == null) {
        y = (HEIGHT - popupHeight) / 2;
    }
    popup.css({
        left: x,
        top: y
    });
}
////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////    Checker   /////////////////////////////////////////////
function getCheckedRowsId() {
    var checkedList = [];
    var count = 0;

    $('.item-checkbox:checked').each(function (index, element) {
        checkedList[count] = Number(element.id.replace("cb_", ""));
        count++;
    });
    return checkedList;
};
function onCheckedAll() {
    var globalChecked = $("#checkAll").prop("checked");
    $('.item-checkbox').each(function () {
        this.checked = globalChecked;
    });
}
///////////////////////////////////// Table Utils //////////////////////////////////////////////
function addAllStudents(arrStudents) {
    arrStudents.forEach(function (student) {
        addStudent(student);
    });
}
function addStudent(student) {
    var arg = ["", "", "", "", "", "", "", "", "", ""];

    if (student.name) {
        arg[0] = student.name;
    }
    if (student.studentInfo.hireDate) {
        arg[1] = student.studentInfo.hireDate;
    }
    if (student.studentInfo.faculty) {
        arg[2] = student.studentInfo.faculty;
    }

    arg[3] = ((student.studentInfo.course) ? student.studentInfo.course : "?");
    arg[3] += "-";
    arg[3] += ((student.studentInfo.studentGroup) ? student.studentInfo.studentGroup : "?");

    if (student.studentInfo.graduationDate) {
        arg[4] = student.studentInfo.graduationDate;
    }
    if (student.studentInfo.workingHours) {
        arg[5] = student.studentInfo.workingHours;
    }

    arg[6] = (student.studentInfo.billable ? student.studentInfo.billable : "-");

    if (student.studentInfo.roleCurrentProject) {
        arg[7] = student.studentInfo.roleCurrentProject;
    }
    if (student.studentInfo.techsCurrentProject) {
        arg[8] = student.studentInfo.techsCurrentProject;
    }
    if (student.studentInfo.englishLevel) {
        arg[9] = student.studentInfo.englishLevel;
    }

    addRow(student.id, arg, 10);
}

function addRow(rowId, columnValues, count) {
    var content;
    content = "<tr> <td><input id='cb_" + rowId + "' type='checkbox' class='item-checkbox'></td>";
    for (var i = 0; i < count; i++) {
        content += "<td>" + columnValues[i] + "</td>";
    }

    content += "</tr>";

    $("#studTable").append(content);
}
function clearTable() {
    $("#studTable > tbody").empty();
}
///////////////////////////////////// Table Subutil ////////////////////////////////////////////
function resizeControl() {
    var HEIGHT = $("#headerBlock").outerHeight();
    $(window).resize(function () {
        var localHeight = $("#headerBlock").outerHeight();
        if (HEIGHT != localHeight) {
            HEIGHT = localHeight;
            checkEmptyFieldSize();
        }
    });
}
function headerScrollControl() {
    var $scrolligBlock = $("#headerScrollingBlock");
    //////// Moving header with table //////////
    $(window).scroll(function () {
        var offset = $(this).scrollLeft();
        $scrolligBlock.scrollLeft(offset);
    });
}
function checkEmptyFieldSize() {
    var headerHeight = $("#headerBlock").height();
    headerHeight -= $("#header-fixed").height();
    $("#emptyField").css("height", headerHeight + "px");
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
    var itCount = document.getElementById("studTable").getElementsByTagName('tr').length;
    if (itCount > 1) {
        $("#infoLabel").text("-------------------- " + (itCount - 1) + " item in list ---------------------");
    } else {
        $("#infoLabel").text("-------------------- List is empty --------------------");
    }
}

//////////////////////////////////////// Filter ////////////////////////////////////////////////


//Menu Listener body
function menuEvent(name) {
    $("#menu").hide();
    addFilterAttribute(nextId(), name, filterTypes.keyMap[name]);
    checkFilterCount();
}

function checkFilterCount(){
    var count = $(".filter-itm").length;
    if(count<6){
        $("#addFilterButton").show();
    }else{
        $("#addFilterButton").hide();
    }
}
function addFilterAction() {
    var $menu = $("#menu");
    if ($menu.is(':visible')) {
        $menu.hide(300);
    } else {
        setPositionrevilTo($menu, $("#addFilterButton"));
        if (filterTypes.changed) {
            filterTypes.changed = false;
            clearMenu($menu);
            fillMenu($menu, filterTypes.keys);
        }
        $('#menu').animate({ opacity: 'toggle', height: 'toggle'}, 300);
    }
}
function addFilterAttribute(id, name, values) {
    var htmContent = "";
    htmContent += "<div class='btn-group input-group filter-itm'>";

    htmContent += "<button class='btn prj-btn remove-btn item-btn-attr' onclick='removeFilterAttribute(this);'>";
    htmContent += name;
    htmContent += "</button>";

    if (values) {
        htmContent += "<select class='selectpicker value-field' style='width:60%'>";
        values.forEach(function (value) {
            htmContent += "<option>";
            htmContent += value;
            htmContent += "</option>";
        });
        htmContent += "</select>";
    } else {
        htmContent += "<input type='text' class='form-control value-field' value='some'>";
    }
    htmContent += "</div>";

    $("#addFilterButton").before(htmContent);
}
function removeFilterAttribute(btn){
    $(btn).parent().get(0).remove();
    checkFilterCount();
}
function pickFilters(){
    var returnStatement={};
    $(".filter-itm").each(function(id, element){
        var itm = $(element);
        var atr_name = $(itm.find(".item-btn-attr")).text();
        var atr_value = $(itm.find(".value-field")).val();
        if(returnStatement[atr_name] == undefined)
            returnStatement[atr_name]=[];
        returnStatement[atr_name].push(atr_value);
    });
    return returnStatement;
}

function clearMenu(menu) {
    menu.empty();
}
function fillMenu(menu, data) {
    var liCont = "";
    for (var i = 0; i < data.length; i++) {
        liCont += "<li class='menu-item' role='presentation'>" +
            "<a onclick='menuEvent(\"" + data[i] + "\");' " +
            "name='menu_itm' role='menuitem'>" + data[i] + "</a></li>";
    }
    menu.append(liCont);
}

function setPositionrevilTo(element, parent) {
    var pos = parent.offset();
    var x = pos.left;
    var y = pos.top + parent.height();
    element.css(
        {
            left: x,
            top: y
        }
    );
}
////////////////////////////////////////////////////////////////////////////////////////////////

function nextId(){
    if(!counter) {
        counter = 1;
    }
    return counter++;
}