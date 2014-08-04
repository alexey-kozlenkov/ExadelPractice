/**
 * Created by ala'n on 30.07.2014.
 */
/* Close dialog on current page */
function closeDialog(){
    $(".content-locker").fadeOut(500);
    $(".dialog-area").hide();
}
/* Show dialog with content [data-dialog_number='content_name'] on current page */
function showDialog(content_name){
    //centrePopup($(".dialog-area"));
    $(".content-locker").fadeIn(500);
    $(".dialog-area").show();
    $(".dialog-content").hide();
    $(".dialog-content[data-dialog-name='"+content_name+"']").show();
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

function setMenuLocationRelativeTo(menu, owner) {
    var winOffsetW = $(window).width();

    var pos = owner.offset();

    var x = pos.left;
    var space = pos.left + menu.width() - winOffsetW + 4;
    if (space > 0)
        x -= space;
    var y = pos.top + owner.height();

    menu.css(
        {
            left: x,
            top: y
        }
    );
}