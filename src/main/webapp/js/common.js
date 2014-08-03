/**
 * Created by ala'n on 30.07.2014.
 */
/* Close dialog on current page */
function closeDialog(){
    $(".content-locker").fadeOut(500);
    $(".dialog-area").hide();
    $(".dialog-content").hide();
}
/* Show dialog with content [dialog_number='content_name'] on current page */
function showDialog(content_name){
    $(".content-locker").fadeIn(500);
    $(".dialog-area").show();
    $(".dialog-content[dialog-name='"+content_name+"']").show();
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