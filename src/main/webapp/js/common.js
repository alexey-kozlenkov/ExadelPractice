/**
 * Created by ala'n on 30.07.2014.
 */

$(document).ready(function () {
    "use strict";
    $(".logout-link").click(logout);
});
function logout() {
    "use strict";
    sessionStorage.removeItem("filter");
}

/* Close dialog on current page */
function closeDialog() {
    "use strict";
    $(".content-locker").fadeOut(500);
}
/* Show dialog with content [data-dialog_number='content_name'] on current page */
function showDialog(contentName) {
    "use strict";
    $(".content-locker").fadeIn(500);
    $(".dialog-content").hide();
    $(".dialog-content[data-dialog-name='" + contentName + "']").show();
}

function setMenuLocationRelativeTo(menu, owner) {
    "use strict";
    //TODO optimize!
    var winOffsetW = $(window).width(),
        position = owner.offset(),
        left = position.left,
        top = position.top + owner.height(),
        space = position.left + menu.width() - winOffsetW + 4;
    if (space > 0) {
        top -= space;
    }
    menu.css(
        {
            left: left,
            top: top
        }
    );
}