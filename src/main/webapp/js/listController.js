/**
 * Created by ala'n on 14.07.2014.
 */

function load() {
    var divTable = document.getElementById('div');
    var list = document.getElementById('list');


    console.log("Script!!!");

    $.ajax({
            url:"/list/data",
            data:"",
            success: function(data, textStatus, jqXHR)
            {
                alert("----"+data);
            },
            error: function (jqXHR, textStatus, errorThrown)
            {
                alert("Error !!!!");
            }
    });

//    $.getJSON("list/data", function(data){
//        alert(data);
//    });

}
function buildTable(dataArray) {

}