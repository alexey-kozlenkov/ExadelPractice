/**
 * Created by ala'n on 13.08.2014.
 */

define(["jquery", "Dialog", "Util", "ListController", "handlebars"],
    function ($, Dlg, Util, List, Handlebars) {
        "use strict";
        var selectedStudentsId,
            curators,
            loadPromise;

        function init() {
            $("#appointCurators").click(
                function () {
                    selectedStudentsId = List.getCheckedRowsId();
                    if (selectedStudentsId.length > 0) {
                        loadCurators();
                        clearList();
                        loadPromise.done(function (data) {
                            curators = JSON.parse(data);
                            fillSelect();
                            Dlg.showDialog("appointer");
                        });
                    }
                    else {
                        Util.stateAnimate($(this), "fail");
                    }
                }
            );
            $("#appointButton").click(
                function () {
                    var selectedCuratorIds = pick();
                    if (selectedCuratorIds.length > 0) {
                        Dlg.closeDialog();
                        sendAppoint(selectedStudentsId, selectedCuratorIds);
                    }
                    else {
                        Util.stateAnimate($(this), "fail", "No one selected");
                    }
                }
            );

            $("#addCuratorToApplied").click(
                function () {
                    var index = $("#appointCuratorSelector").val(),
                        $exist = $("#appointCuratorsList > li[data-id = " + index + "]");
                    if ($exist.length === 0) {
                        addCurator(index);
                    }
                    else {
                        Util.stateAnimate($(this), "fail");
                    }
                }
            );
            $("#appointCuratorsList").on("click", ".remove-appointed", function () {
                var $listItem = $(this).parent().eq(0);
                $listItem.remove();
            });
        }

        function loadCurators() {
            loadPromise = $.ajax({
                url: "/list/curatorList",
                cache: false,
                data: {}
            });
            loadPromise.fail(
                function () {
                    alert("Curators list not loaded!");
                }
            );
        }

        function fillSelect() {
            require(["text!templates/curators-list-template.html"], function (template) {
                var $select = $("#appointCuratorSelector");
                $select.empty();
                $select.append(Handlebars.compile(template)(curators));
            });
        }

        function addCurator(index) {
            require(["text!templates/appoint-curators-template.html"],
                function (template) {
                    $("#appointCuratorsList").append(Handlebars.compile(template)({name: curators[index], index: index}));
                }
            );
        }

        function clearList() {
            $("#appointCuratorsList").empty();
        }

        function pick() {
            var i,
                result = [],
                $items = $("#appointCuratorsList > li");
            for (i = 0; i < $items.length; i++) {
                result.push($items.eq(i).data("id"));
            }
            return result;
        }

        function sendAppoint(studentIds, curatorIds) {
            $.ajax({
                url: "/list/appoint",
                type: "POST",
                data: {
                    studentsId: JSON.stringify(studentIds),
                    curatorsId: JSON.stringify(curatorIds)
                }
            }).done(
                function (data) {
                    curators = JSON.parse(data);
                    Util.stateAnimate($("#appointCurators"), "success");
                }
            ).fail(
                function () {
                    alert("Appoint curators request not executed !");
                }
            );
        }

        return {
            init: init
        };
    });
