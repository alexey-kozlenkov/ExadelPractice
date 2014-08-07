/**
 * Created by ala'n on 31.07.2014.
 */
var ListController = (function () {
        function updateInfoLabel() {
            var itCount = $("#studTable > tbody > tr").length;
            if (itCount > 0) {
                var selCount = $(".item-checkbox:checked").length;
                $("#infoLabel").text("------------- " + itCount + " item in list " + selCount + " selected -------------");

            } else {
                $("#infoLabel").text("------------- List is empty -------------");
            }
        }

        return{
            init: function () {
                var $table = $("#studTable");
                $("#checkAll").click(function () {
                    setCheckedAll($(this).prop("checked"));
                });
                $table.click(function () {
                    updateInfoLabel();
                });
                updateInfoLabel();
            },
            addAllStudents: function (arrStudents) {
                var rowTemplate = Handlebars.compile($('#listContentTemplate').html());
                $("#studTable > tbody").append(rowTemplate({list: arrStudents}));
                updateInfoLabel();
            },
            clearList: function () {
                $("#studTable > tbody").empty();
                updateInfoLabel();
            },

            getCheckedRowsId: function () {
                var checkedList = [],
                    count = 0;

                $('.item-checkbox:checked').each(function (index, element) {
                    checkedList[count] = Number($(element).attr("data-id"));
                    count++;
                });
                return checkedList;
            },
            setCheckedAll: function (state) {
                $('.item-checkbox').each(function () {
                    this.checked = state;
                });
                updateInfoLabel();
            },
            setTableLoadingState: function (loading) {
                if (loading) {
                    $("#studTable").hide();
                    $("#loading_image").show();
                } else {
                    $("#studTable").show();
                    $("#loading_image").hide();
                }
            }
        }
    }());
var ListHeader = (function(){
    var pastHeaderHeight;
    function checkTopMarginOfList() {
        $("#listContent").css("margin-top", $("#header").height() + "px");
    }
    return {
        init: function(){
            $(window).resize(function () {
                var height = $("#header").outerHeight();
                if (pastHeaderHeight != height) {
                    pastHeaderHeight = height;
                    checkTopMarginOfList();
                }
            });
            // Correct header vertical position according to position of list on scrolling
            $(window).scroll(function () {
                $("#headerScrollingBlock").scrollLeft($(this).scrollLeft());
            });
            checkTopMarginOfList();
        },
        check: checkTopMarginOfList
    };
}());

$(document).ready(ListController.init());
$(window).ready(ListHeader.init());


