/**
 * Created by ala'n on 30.07.2014.
 */
function closeDialog(){
    $(".content-locker").fadeOut(500);
    $(".dialog-area").hide();
    $(".dialog-content").hide();
}
function showDialog(content_number){
    $(".content-locker").fadeIn(500);
    $(".dialog-area").show();
    $(".dialog-content[dialog-number='"+content_number+"']").show();
}