/**
 * Created by ala'n on 08.08.2014.
 */

define(["jquery"], function ($) {
    "use strict";

    /* Close dialog on current page */
    function closeDialog() {
        $(".content-locker").fadeOut(500);
    }
    /* Show dialog with content [data-dialog_number='content_name'] on current page */
    function showDialog(contentName, width) {
        if (!width) {
            width = "200px";
        }
        $(".dialog-area").css({
            width : width
        });
        $(".content-locker").fadeIn(500);
        $(".dialog-content").hide();
        $(".dialog-content[data-dialog-name='" + contentName + "']").show();
    }
    return {
        showDialog : showDialog,
        closeDialog : closeDialog
    };
});

