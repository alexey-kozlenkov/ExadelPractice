/**
 * Created by ala'n on 14.07.2014.
 */

$(document).ready(function () {
    bindMenuBlock();
    bindSearchBlock();
    bindSendMessageDialog();
});

function bindMenuBlock() {
    $("#addMenuButton").click(function () {
        // FOR TESTING TODO!
        addRow(0, 'Vasya Pupkin', ['16.07.2014', 'FPM', '1-1', '2018', 12, '-', 'tester', 'php', 'Intermediate'], 9);
        addRow(0, 'Vasya Uan Hun Pupkin', ['16.07.2014', 'FPM', '1-1', '2018', 12, '-', 'tester', 'html js php java json hibitrnate spring ', 'Intermediate'], 9);
        updateInfoLabel();
    });
    $("#exportMenuButton").click(function () {
        // FOR TESTING TODO!
        clearTable();
        updateInfoLabel();
    });
    $("#distributionMenuButton").click(function () {
        showDialog(1);
        // alert(getCheckedRowsId());
    });
}

function bindSearchBlock(){
    $("#startSearchButton").click(function () {
        loadTable();
        //pickFilters();
        updateInfoLabel();
    });
    $("#searchLine").focus(function () {
        $(this).animate({ width: "250pt"}, 1000);
    }).blur(function () {
        $(this).animate({ width: "150pt"}, 500);
    });
    $('#searchLine').on('input', function () {
        loadTable();
    });
}

function bindSendMessageDialog(){
    $("#sendButton").click(function(){
        var messageText = $("#sendedMessage").val();
        var studIds = JSON.stringify(getCheckedRowsId());

        $.ajax({
            type: "POST",
            url: "/?",
            async: true,
            data: {
                message: messageText,
                students: studIds
            }
        }).done(function (data) {
            console.log("Done ! - ", data, ' -');
        }).fail(function () {
            alert("error");
        });
     });
}

function loadTable() {
    setTableLoadingState(true);

    var search = $("#searchLine").val();
    var filter = pickFilters();

    var filterPack = JSON.stringify(filter);

    $.ajax({
        type: "GET",
        url: "/list/name",
        async: true,
        data: {
            'searchName': search
            //'filter': filterPack
        }
    }).done(function (data) {
        var obj = JSON.parse(data);
        if (obj) {
            clearTable();
            addAllStudents(obj);
            setTableLoadingState(false);
        }
    }).fail(function () {
        alert("error");
        setTableLoadingState(false);
    });
}
