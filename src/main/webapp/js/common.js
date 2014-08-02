/**
 * Created by ala'n on 30.07.2014.
 */
/* Close dialog on current page */
function closeDialog(){
    $(".content-locker").fadeOut(500);
    $(".dialog-area").hide();
    $(".dialog-content").hide();
}
/* Show dialog with content [dialog_number='content_number'] on current page */
function showDialog(content_number){
    $(".content-locker").fadeIn(500);
    $(".dialog-area").show();
    $(".dialog-content[dialog-number='"+content_number+"']").show();
}

function setLocationRelativeTo(element, parent) {
    var winOffsetW = $(window).width();

    var pos = parent.offset();

    var x = pos.left;
    var space = pos.left + element.width() - winOffsetW + 4;
    if (space > 0)
        x -= space;
    var y = pos.top + parent.height();

    element.css(
        {
            left: x,
            top: y
        }
    );
}