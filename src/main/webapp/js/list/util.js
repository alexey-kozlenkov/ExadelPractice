/**
 * Created by ala'n on 14.07.2014.
 */

var ListUtil = (function(){
    function bindMenuBtn() {
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

    function sendMessage() {
        var subject = $("#subjectField").val(),
            messageText = $("#sentMessage").val(),
            studIds = JSON.stringify(ListController.getCheckedRowsId());

        $.ajax({
            type: "POST",
            url: "/list/sendMail",
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
            }
        }).fail(function () {
            alert("Error");
        });
    }

    function exportExcel() {
        var studIds = JSON.stringify(ListController.getCheckedRowsId());
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

    return {
        init: function(){
            bindMenuBtn();
            bindDialog();
        }
    };
}());

var ListLoader = (function(){
    var actualVersion = 0;

    function initSearchLine() {
        $("#startSearchButton").click(function () {
            loadTable();
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

    function loadTable() {
        var version = Date.now(),
            search = $("#searchLine").val(),
            filter = sessionStorage.getItem("filter");

        ListController.setTableLoadingState(true);
        actualVersion = version;

        promise = $.ajax({
            url: "/list/data",
            cache: false,
            data: {
                'version': version,
                'searchName': search,
                'filter': filter
            }
        });

        promise.done(function (data) {
            updateListByResponse(JSON.parse(data));
        });
        promise.fail(function () {
            alert("error");
            ListController.setTableLoadingState(false);
        });
    }

    function updateListByResponse(response) {
        if (actualVersion == response.version) {
            console.log("Get actual response (", actualVersion, ")");
            ListController.clearList();
            ListController.addAllStudents(response.studentViews);
            ListController.setTableLoadingState(false);
        } else {
            console.log("Response (", response.version, ") was ignored; actual: ", actualVersion, "; now: ", Date.now());
        }
    }

    return{
        init: function(){
            initSearchLine();
        },
        load: function(){
            loadTable();
        }
    };
}());

$(document).ready(function () {
    ListLoader.init();
    ListUtil.init();

    $("#toggleNav").click(
        function(){
            var f =  $("#filter");
            if(f.is(":visible"))
                f.fadeOut(500, ListHeader.check);
            else
                f.fadeIn(500, ListHeader.check);

        }
    );
});








