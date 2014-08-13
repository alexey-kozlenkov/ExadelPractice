/**
 * Created by ala'n on 13.08.2014.
 */

define(["jquery", "Dialog", "Util", "ListController"],
    function ($, Dlg, Util, List) {
        "use strict";
        var selectedStudentsId,
            curators = [
                {id: 101, name: "First Curator"},
                {id: 201, name: "Second Curator"}
            ];

        function init() {
            load();

            $("#appointCurators").click(
                function () {
                    clearList();
                    fillSelect();
                    Dlg.showDialog("appointer");
                }
            );
            $("#appointButton").click(
                function () {
                    sendAppoint();
                    Dlg.closeDialog();
                }
            );

            $("#addCuratorToApplied").click(
                function () {
                    var i = $("#appointCuratorSelector").val();
                    if (!curators[i].selected) {
                        addCurator(i);
                    }
                }
            );
            $("#appointCuratorsList").on("click", ".remove-appointed", function () {
                var $listItem = $(this).parent().eq(0);
                $listItem.remove();
            });
        }

        function load() {
            $.ajax({
                url: "/list/curatorList",
                data: {}
            }).done(
                function (data) {
                    curators = JSON.parse(data);
                }
            ).fail(
                function () {
                    console.error("curators not loaded!");
                }
            );
        }

        function fillSelect() {
            require(["handlebars", "text!templates/curators-list-template.html"],
                function (Handlebars, template) {
                    var $select = $("#appointCuratorSelector");
                    $select.empty();
                    $select.append(Handlebars.compile(template)(curators));
                }
            );
        }

        function addCurator(index) {
            require(["handlebars", "text!templates/appoint-curators-template.html"],
                function (Handlebars, template) {
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
            for (i = 0 ; i < $items.length ; i++) {
                result.push($items.eq(i).data("id"));
            }
            return result;
        }

        function sendAppoint() {
            var studId = JSON.stringify(List.getCheckedRowsId()),
                emplId = JSON.stringify(pick());
            $.ajax({
                url: "/list/appoint",
                data: {
                    studentsId : studId,
                    curatorsId : emplId
                }
            }).done(
                function (data) {
                    curators = JSON.parse(data);
                }
            ).fail(
                function () {
                    console.error("curators not loaded!");
                }
            );
        }

        return {
            init: init
        };
    });
