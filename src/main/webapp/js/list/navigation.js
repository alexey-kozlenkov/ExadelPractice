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
        var list = [
            {
                id: 1,
                name: 'Vasya Pupkin',
                hireDate: '16.07.2014',
                faculty: 'FPM',
                course: 3,
                group: 8,
                graduationDate: '2018',
                workingHours: 8,
                billable: null,
                roleCurrentProject: 'tester',
                techsCurrentProject: 'java javascript css html sql',
                englishLevel:'Intermediate'
            },
            {
                id: 2,
                name: 'Vasya Uan Hun Pupkin',
                hireDate: '16.07.2014',
                faculty: 'FPM',
                course: 3,
                graduationDate: '2018',
                workingHours: 8,
                billable: null,
                roleCurrentProject: 'tester',
                techsCurrentProject:  'html js php java json hibitrnate spring',
                englishLevel:'Intermediate'
            }
        ];
        addAllStudents(list);
    });
    $("#exportMenuButton").click(function () {
        // FOR TESTING TODO!
        clearList();
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
        var messageText = $("#sentMessage").val();
        var studIds = JSON.stringify(getCheckedRowsId());

        $.ajax({
            type: "POST",
            url: "/list/sendMail",
            async: true,
            data: {
                message: messageText,
                subject: '',
                students: studIds
            }
        }).done(function (data) {
            console.log("Done ! - ", data, ' -');
        }).fail(function () {
            alert("error");
        });
        closeDialog();
     });
}

function loadTable() {
    var search = $("#searchLine").val(),
        filter = pickFilters(),
        filterPack = JSON.stringify(filter);

    setTableLoadingState(true);

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
            clearList();
            addAllStudents(obj);
            setTableLoadingState(false);
        }
    }).fail(function () {
        alert("error");
        setTableLoadingState(false);
    });
}
