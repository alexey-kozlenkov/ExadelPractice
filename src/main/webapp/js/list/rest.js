/**
 * Created by ala'n on 14.07.2014.
 */

$(document).ready(function () {
    bindMenuBlock();
    bindSearchBlock();
    bindDialog();
});

function bindMenuBlock() {
    $("#addMenuButton").click(function () {
        showDialog("add-student");
    });
    $("#exportMenuButton").click(function () {
        exportExcel();
    });
    $("#distributionMenuButton").click(function () {
        showDialog('send-message');
    });
}

function bindSearchBlock() {
    $("#startSearchButton").click(function () {
        loadTable();
        updateInfoLabel();
    });
    $("#searchLine").focus(function () {
        $(this).animate({ width: "250pt"}, 1000);
    }).blur(function () {
        $(this).animate({ width: "150pt"}, 500);
    });
    $('#searchLine').on('input', function () {
        loadTable();
    });
}

function bindDialog() {
    $("#sendButton").click(function () {
        sendMessage();
        closeDialog();
    });
    $("#createUser").click(function () {
        createUser();
        closeDialog();
    });
}

function loadTable() {
    var search = $("#searchLine").val(),
        filter = pickFilters(),
        filterPack = JSON.stringify(filter);

    setTableLoadingState(true);

    $.ajax({
        type: "GET",
        url: "/list/name",
        async: true,
        data: {
            'searchName': search
            //'filter': filterPack
        }
    }).done(function (data) {
        var obj = JSON.parse(data);
        if (obj) {
            clearList();
            addAllStudents(obj);
            setTableLoadingState(false);
        }
    }).fail(function () {
        alert("error");
        setTableLoadingState(false);
    });
}

function sendMessage(){
    var messageText = $("#sentMessage").val(),
        studIds = JSON.stringify(getCheckedRowsId());

    $.ajax({
        type: "POST",
        url: "/list/sendMail",
        async: true,
        data: {
            message: messageText,
            subject: '',
            students: studIds
        }
    }).done(function (data) {
        var mails = JSON.parse(data);
        if (mails && mails.length > 0) {
            var mailList = $("#inaccessibleMailList");
            var mailTemplate = Handlebars.compile($("#mailListTemplate").html());
            mailList.empty();
            mailList.append(mailTemplate({mails: mails}));
            showDialog('inaccessible-mail');
        } else {
            console.log("Done ! - ");
        }
    }).fail(function () {
        alert("Error");
    });
}

function exportExcel() {
    var studIds = JSON.stringify(getCheckedRowsId());
    window.open("/list/export?students=" + studIds, "exportFile");
}

function createUser() {

}
