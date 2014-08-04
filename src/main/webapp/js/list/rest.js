/**
 * Created by ala'n on 14.07.2014.
 */
var actualVersion = 0;

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
    $("#closeDialog").click(function () {
        closeDialog();
    });
    $("#sendButton").click(function () {
        sendMessage();
        closeDialog();
    });
    $("#closeMessageButton").click(function () {
        closeDialog();
    });
    $("#createUser").click(function () {
        createUser();
        closeDialog();
    });
}

function loadTable() {
    var version = Date.now();
    search = $("#searchLine").val(),
        filter = pickFilters(),
        filterPack = "";// JSON.stringify(filter);
    actualVersion = version;
    setTableLoadingState(true);

    promise = $.ajax({
        type: "GET",
        url: "/list/data",
        cache: false,
        async: true,
        data: {
            'version': version,
            'searchName': search,
            'filter': filterPack
        }
    });

    promise.done(function (data) {
        updateListByResponse(JSON.parse(data));
    });
    promise.fail(function () {
        alert("error");
        setTableLoadingState(false);
    });
}

function updateListByResponse(response) {
    if (actualVersion == response.version) {
        console.log("Get actual response (", actualVersion, ")");
        clearList();
        addAllStudents(response.studentViews);
        setTableLoadingState(false);
    } else {
        console.log("Response (", response.version, ") was ignored; actual: ", actualVersion, "; now: ", Date.now());
    }
}

function sendMessage() {
    var subject = $("#subjectField").val(),
        messageText = $("#sentMessage").val(),
        studIds = JSON.stringify(getCheckedRowsId());

    $.ajax({
        type: "POST",
        url: "/list/sendMail",
        async: true,
        data: {
            message: messageText,
            subject: subject,
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
//        $("#distributionMenuButton").animate({
//            borderColor: '#090',
//            backgroundColor: '#090'
//        }, 100, function(){
//            $(this).animate({
//                borderColor: '#2D3E5C',
//                backgroundColor: '#4A5D80'
//            }, 2000);
//        });
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
    var login = $("#loginField").val(),
        name = $("#nameField").val(),
        state = $("#stateField").val(),
        createStudPromise;
    createStudPromise = $.ajax({
        type: "POST",
        url: "list/quickAdd",
        async: true,
        data: {
            'login': login,
            'name': name,
            'state': state
        }
    });
    createStudPromise.done(
        function () {
            console.log("Good!");
        }
    );
    createStudPromise.fail(
        function () {
            alert("Error creating");
        }
    );
}
