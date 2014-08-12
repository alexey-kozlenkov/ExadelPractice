/**
 * Created by ala'n on 14.07.2014.
 */

define(["jquery", "handlebars", "ListController", "Dialog", "Util"],
    function ($, Handlebars, ListController, Dialog, Util) {
        "use strict";

        function bindMenuBtn() {
            $("#addMenuButton").click(function () {
                Dialog.showDialog("add-student");
            });
            $("#exportMenuButton").click(function () {
                var students = ListController.getCheckedRowsId();
                if (students.length > 0) {
                    exportExcel(students);
                }
                else {
                    Util.stateAnimate($("#exportMenuButton"), 'fail');
                }
            });
            $("#distributionMenuButton").click(function () {
                var students = ListController.getCheckedRowsId();
                if (students.length <= 0) {
                    Util.stateAnimate($(this), 'fail');
                    return;
                }
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

        function exportExcel(students) {
            var studIds = JSON.stringify(students);
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
                    $("body").trigger("searchOrFieldUpdate", {type: "all"});
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









