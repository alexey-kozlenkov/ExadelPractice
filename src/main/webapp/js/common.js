/**
 * Created by ala'n on 30.07.2014.
 */

$(document).ready(function () {
    "use strict";
    require(["jquery", "Util"], function ($, util) {
        $(".logout-link").click(util.logout);
    });
});

/* Close dialog on current page */
function closeDialog() {
    "use strict";
    require(["Dialog"], function (dialog) {
        dialog.closeDialog();
    });
}
/* Show dialog with content [data-dialog_number='content_name'] on current page */
function showDialog(contentName) {
    "use strict";
    require(["Dialog"], function (dialog) {
        dialog.showDialog();
    });
}
