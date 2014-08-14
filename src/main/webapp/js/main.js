/**
 * Created by ala'n on 08.08.2014.
 */

require.config({
    baseUrl: "../",
    paths: {
        "jquery": "js/lib/jquery-2.1.1.min",
        "jquery-animate-colors": "js/lib/jquery.animate-colors-min",
        "jquery-tablesorter": "js/lib/jquery.tablesorter.min",
        "handlebars": "js/lib/handlebars-v1.3.0",

        "text":             "js/lib/text",
        "templates":        "html/templates",

        "Dialog":           "js/common/Dialog",
        "Util":             "js/common/Util",

        "Accordion":        "js/info/Accordion",
        "Rest":             "js/info/student/Rest",
        "FillBasic":        "js/info/student/FillBasic",
        "Skills":           "js/info/student/Skills",

        "ListHeader":       "js/list/ListHeader",
        "ListController":   "js/list/ListController",
        "ListLoader":       "js/list/ListLoader",
        "Filter"   :        "js/list/Filter",
        "ListUtil" :        "js/list/ListUtil",
        "AppointDlg" :    "js/list/AppointDlg"
    },
    shim: {
        'handlebars': {
            exports: 'Handlebars'
        },
        'jquery-animate-colors': {
            exports: '$',
            deps: ['jquery']
        },
        'jquery-tablesorter': {
            expopts: '$',
            deps: ['jquery']
        }
    }
});


