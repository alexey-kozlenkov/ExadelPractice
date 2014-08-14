/**
 * Created by Administrator on 14.08.2014.
 */

define(["jquery", "handlebars", "Util", "text!templates/term-mark-template.html"], function ($, Handlebars, util, templateTermMarkContent) {
    "use strict";
    var employeeId;

    function init() {
        util.initAccessRoleForInfo();
        util.parseRequestForId(window.location.search, employeeId);
        fillCommonInfo();
    }
    function fillCommonInfo() {


    }
    return {
        employeeId: function () { return employeeId; },
        init: init
    };
});
