/**
 * Created by ala'n on 14.07.2014.
 */

window.onload = function(){
    headerControl();
};

document.getElementById('checkAll').onclick=function(){
    alert("Function is not suport");
};

function headerControl(){
   // var tableOffset = $("#studTable").offset().top;
   // var $header = $("#studTable > thead").clone();
   // var $fixedHeader = $("#header-fixed").append($header);
}

function addRow(entry, count){
    console.log("Add "+entry+" count " + count);
    var content = "<tr> <td><input type='checkbox'></td>";
    for(var i=0; i<count; i++ ){
        content += "<td>" + entry[i]+"</td>";
    };

    content += "</tr>";
    console.log("Code :"+content);

     $("#studTable").append(content);
}

function clearTable(){
    var table = document.getElementById('studTable');
   // table.removeChild('tbody');
    for (; table.getElementsByTagName('tr').length > 1; ) {
        table.deleteRow(1);
    }

}