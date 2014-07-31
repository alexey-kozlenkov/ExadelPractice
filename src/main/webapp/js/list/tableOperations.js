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
    content += "<td><a href = '/info?id=" + rowId + "'>" + name + "</td>";
    for (var i = 0; i < count; i++) {
        content += "<td>" + columnValues[i] + "</td>";
    }

    content += "</tr>";

    $("#studTable").append(content);
    updateInfoLabel();
}

function clearTable() {
    $("#studTable > tbody").empty();
    updateInfoLabel();
}

function getCheckedRowsId() {
    var checkedList = [];
    var count = 0;

    $('.item-checkbox:checked').each(function (index, element) {
        checkedList[count] = Number(element.id.replace("cb_", ""));
        count++;
    });
    return checkedList;
};

function setCheckedAll(sample) {
    $('.item-checkbox').each(function () {
        this.checked = sample;
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
        $("#infoLabel").text("-------------------- " + itCount + " item in list " + selCount + " selected ---------------------");

    } else {
        $("#infoLabel").text("-------------------- List is empty --------------------");
    }
}