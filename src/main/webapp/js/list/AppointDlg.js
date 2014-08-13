/**
 * Created by ala'n on 13.08.2014.
 */

define(["jquery", "Dialog", "Util", "ListController"],
    function ($, Dlg, Util, List) {
        "use strict";

        function init() {
            $("#appointCurators").click(
                function () {
                    Dlg.showDialog("appointer");
                }
            );
            $("#appointButton").click(
                function () {
                    Dlg.closeDialog();
                }
            );
        }

        return {
            init: init
        };
    });
