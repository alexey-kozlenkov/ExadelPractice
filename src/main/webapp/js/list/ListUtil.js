/**
 * Created by ala'n on 14.07.2014.
 */

define(["jquery", "handlebars", "ListController", "Dialog"],
    function ($, Handlebars, ListController, Dialog) {
    "use strict";
    function bindMenuBtn() {
        $("#addMenuButton").click(function () {
            Dialog.showDialog("add-student");
        });
        $("#exportMenuButton").click(function () {
            exportExcel();
        });
        $("#distributionMenuButton").click(function () {
            Dialog.showDialog('send-message');
        });
    }
    function bindDialog() {
        $("#closeDialog").click(function () {
            Dialog.closeDialog();
        });
        $("#sendButton").click(function () {
            sendMessage();
            Dialog.closeDialog();
        });
        $("#closeMessageButton").click(function () {
            Dialog.closeDialog();
        });
        $("#createUser").click(function () {
            createUser();
            Dialog.closeDialog();
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
            var mails = JSON.parse(data),
                mailList,
                mailTemplate;
            if (mails && mails.length > 0) {
                mailList = $("#inaccessibleMailList");
                mailTemplate = Handlebars.compile($("#mailListTemplate").html());
                mailList.empty();
                mailList.append(mailTemplate({mails: mails}));
                Dialog.showDialog('inaccessible-mail');
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
        init: function () {
            bindMenuBtn();
            bindDialog();
        }
    };
});









