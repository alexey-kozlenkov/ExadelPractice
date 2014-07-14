<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Student list</title>
</head>
<body>

<div>
    <button id="Start" onclick="load();">Start</button>

</div>

<div id="div" style="border: solid #000000 1pt; width: 90%; height: 400pt;background-repeat: no-repeat; background-position: center">
    <table id="list" hidden="true">
    </table>
</div>

<script>
    function createXMLHttpRequest(){
        // Provide the XMLHttpRequest class for IE 5.x-6.x:
        if( typeof XMLHttpRequest == "undefined" ) XMLHttpRequest = function() {
            try { return new ActiveXObject("Msxml2.XMLHTTP.6.0") } catch(e) {}
            try { return new ActiveXObject("Msxml2.XMLHTTP.3.0") } catch(e) {}
            try { return new ActiveXObject("Msxml2.XMLHTTP") } catch(e) {}
            try { return new ActiveXObject("Microsoft.XMLHTTP") } catch(e) {}
            throw new Error( "This browser does not support XMLHttpRequest." )
        };
        return new XMLHttpRequest();
    }

    var AJAX = createXMLHttpRequest();

    function load(){
        var divTable = document.getElementById('div');
        var list = document.getElementById('list');

        AJAX.onreadystatechange = listMessageHandler;
        AJAX.open("GET", "/list/data");
        AJAX.send("item: all");// There must be filter/sercher information
    }
    function listMessageHandler() {
        if(AJAX.readyState == 4){
            if(AJAX.status == 200){
                console.log("get : "+AJAX.responseText);
                var data = JSON.parse(AJAX.responseText);
                alert(data);
            }else{
                alert("Exception!!");
            }
        }
    }
    function buildTable(dataArray){

    }
</script>

</body>
</html>