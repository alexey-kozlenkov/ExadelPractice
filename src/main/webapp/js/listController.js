/**
 * Created by ala'n on 14.07.2014.
 */
////////////////////////////////////////////////////////////////////////////////////////////////
var MAX_FILTER_COUNT = 10;
var filterTypes = {
    changed: true,
    keys: ['age', 'working hour', 'billable', 'skill', 'english', 'date'],
    keyType: {
        'age': 'number',
        'working hour': 'number',
        'billable': 'date',
        'skill': 'leveled-list',
        'english': 'list',
        'date': 'date'
    },
    keyMap: {
        'skill': ['java', 'C++', '.NET', 'HTML', 'Mongo DB', 'SQL'],
        'english': ['1', '2', '3', '4', '5']
    },
    multyType: {
        'skill': true
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
        addRow(0, 'Vasya Pupkin', ['16.07.2014', 'FPM', '1-1', '2018', 12, '-', 'tester', 'php', 'Intermediate'], 9);
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

    $("#filterMenu").mouseleave(
        function () {
            $('#filterMenu').hide(400);
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

    $("#studTable").click(function () {
        updateInfoLabel();
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
    updateInfoLabel();
}
///////////////////////////////////// Table Utils //////////////////////////////////////////////
function addAllStudents(arrStudents) {
    arrStudents.forEach(function (student) {
        addStudent(student);
    });
}
function addStudent(student) {
    var arg = ["", "", "", "", "", "", "", "", "", ""];

    if (student.hireDate) {
        arg[0] = student.hireDate;
    }
    if (student.faculty) {
        arg[1] = student.faculty;
    }

    arg[2] = ((student.course) ? student.course : "?");
    arg[2] += "-";
    arg[2] += ((student.group) ? student.group : "?");

    if (student.graduationDate) {
        arg[3] = student.graduationDate;
    }
    if (student.workingHours) {
        arg[4] = student.workingHours;
    }

    arg[5] = (student.billable ? student.billable : "-");

    if (student.roleCurrentProject) {
        arg[6] = student.roleCurrentProject;
    }
    if (student.techsCurrentProject) {
        arg[7] = student.techsCurrentProject;
    }
    if (student.englishLevel) {
        arg[8] = student.englishLevel;
    }

    addRow(student.id, student.name, arg, 9);
}

function addRow(rowId, name, columnValues, count) {
    var content;
    content = "<tr> <td><input id='cb_" + rowId + "' type='checkbox' class='item-checkbox'></td>";
    content += "<td><a href = '/info?id="+rowId+"'>" + name + "</td>";
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
    var itCount = $("#studTable > tbody > tr").length;
    if (itCount > 0) {
        var selCount = $(".item-checkbox:checked").length;
        $("#infoLabel").text("-------------------- " + itCount + " item in list " + selCount + " selected ---------------------");

    } else {
        $("#infoLabel").text("-------------------- List is empty --------------------");
    }
}

//////////////////////////////////////// Filter ////////////////////////////////////////////////
const STATIC_SEPARATOR =
    "<span class='static-separator'>,</span>";
const LOGIC_SEPARATOR =
    "<span class='static-green filter-separator'>and</span>";

//---------------------------------
function addFilterAttribute(name) {
    var filterElementContent = createFilterAttributeContent(name);
    if (filterTypes.multyType[name]) {
        var filtersExist = existFilterAttribute(name);
        if (filtersExist && filtersExist.length > 0) {
            filtersExist.last().after(LOGIC_SEPARATOR + filterElementContent);
        } else {
            $("#addFilterButton").before(filterElementContent + STATIC_SEPARATOR);
        }
    } else {
        $("#addFilterButton").before(filterElementContent + STATIC_SEPARATOR);
        $("#filterMenu > li[name='filter_" + name + "']").hide();
    }
}

function createFilterAttributeContent(name) {
    var htmContent = "";

    htmContent += "<div name ='filter_" + name + "' class='btn-group input-group filter-item'>";
    htmContent += "<button class='btn prj-btn remove-btn item-btn-attr' ";
    htmContent += "onclick=\"removeFilterAttribute( '" + name + "', this );\">";
    htmContent += name;
    htmContent += "</button>";

    switch (filterTypes.keyType[name]) {
        case 'number':
            htmContent += "<input type='text' class='form-control value-field' placeholder='??'>";
            break;
        case 'date':
            htmContent += "<input type='text' class='form-control value-field' placeholder='date'>";
            break;
        case 'list':
            htmContent += "<select class='selectpicker value-field' style='width:60%'>";
            filterTypes.keyMap[name].forEach(function (value) {
                htmContent += "<option>";
                htmContent += value;
                htmContent += "</option>";
            });
            htmContent += "</select>";
            break;
        case 'leveled-list':
            htmContent += "<select class='selectpicker value-field' style='width:50%'>";
            filterTypes.keyMap[name].forEach(function (value) {
                htmContent += "<option>";
                htmContent += value;
                htmContent += "</option>";
            });
            htmContent += "</select>";
            htmContent += "<select class='selectpicker sub-value-field' style='width:40pt'>";
            for (var level = 1; level <= 5; level++) {
                htmContent += "<option>";
                htmContent += level;
                htmContent += "</option>";
            }
            ;
            htmContent += "</select>";
            break;
        default :
            console.error("Not defined filter type");
    }
    htmContent += "</div>";
    return htmContent;
}

function existFilterAttribute(name) {
    return $(".filter-item[name='filter_" + name + "']");
}
function removeFilterAttribute(name, element) {
    var selfItem = $($(element).parent().get(0));
    var prevItem = selfItem.prev();
    var nextItem = selfItem.next();
    if (prevItem.is('.filter-separator')) {
        prevItem.remove();
    } else if (nextItem.is('.filter-separator')) {
        nextItem.remove();
    } else if (nextItem.is('.static-separator')) {
        nextItem.remove();
    }

    selfItem.remove();
    $("#filterMenu > li[name='filter_" + name + "']").show();
    checkFilterCount();
}

function checkFilterCount() {
    var count = $(".filter-item").length;
    var filterBtn = $("#addFilterButton");
    if (count < MAX_FILTER_COUNT) {
        filterBtn.prev().show();
        filterBtn.show();
    } else {
        filterBtn.prev().hide();
        filterBtn.hide();
    }
    checkEmptyFieldSize();
}

//TODO! rewrite
function pickFilters() {
    var returnStatement = {};
    var lastAtrName = "";

    var itms = $("#filterBlock").find(".filter-item");

    for (var i = 0; i < itms.length; i++) {
        var element = $(itms[i]);

        var atr_name = $(element.find(".item-btn-attr")).text();
        var atr_value = $(element.find(".value-field")).val();

        if (!returnStatement[atr_name])
            returnStatement[atr_name] = '';
        else
            returnStatement[atr_name] += '&';
        switch (filterTypes.keyType[atr_name]) {
            case 'number':
            case 'date':
            case 'list':
                returnStatement[atr_name] += atr_value;
                break;
            case 'leveled-list':
                var atr_sub_value = $(element.find(".sub-value-field")).val();
                returnStatement[atr_name] += atr_value + '=' + atr_sub_value;
                break;
            default :
                console.error("Not defined filter type");
                break;
        }
        lastAtrName = atr_name;
    }
    ;
    return returnStatement;
}
////////////////////////////////////////////////////////////////////////////////////////////////

//Menu Listener body
function menuEvent(name) {
    $("#filterMenu").hide();
    addFilterAttribute(name);
    checkFilterCount();
}
function addFilterAction() {
    var $menu = $("#filterMenu");
    if ($menu.is(':visible')) {
        $menu.hide(300);
    } else {
        setPositionrevilTo($menu, $("#addFilterButton"));
        if (filterTypes.changed) {
            filterTypes.changed = false;
            clearMenu($menu);
            fillMenu($menu, filterTypes.keys, 'filter_');
        }
        $('#filterMenu').animate({ opacity: 'toggle', height: 'toggle'}, 300);
    }
}
function clearMenu(menu) {
    menu.empty();
}
function fillMenu(menu, data, name_pref) {
    var liCont = "";
    for (var i = 0; i < data.length; i++) {
        liCont += "<li class='menu-item' name='" + name_pref + data[i] + "'>" +
            "<a onclick='menuEvent(\"" + data[i] + "\");' role='menuitem'>" +
            data[i] + "</a></li>";
    }
    menu.append(liCont);
}
function setPositionrevilTo(element, parent) {
    var winOffsetW = $(window).width();

    var pos = parent.offset();

    var x = pos.left;
    var space = pos.left + element.width() - winOffsetW + 4;
    if (space > 0)
        x -= space;
    var y = pos.top + parent.height();

    element.css(
        {
            left: x,
            top: y
        }
    );
}
////////////////////////////////////////////////////////////////////////////////////////////////
