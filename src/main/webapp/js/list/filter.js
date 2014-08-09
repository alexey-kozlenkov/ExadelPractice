/**
 * Created by ala'n on 31.07.2014.
 */
define(["jquery", "handlebars"], function ($, Handlebars) {
    "use strict";
    var separatorContent = "<span class='static-green filter-separator'>and</span>",
        filterMaxCount = 8,
        filterDescriptions = [],
        filterValue = {};

    function init() {
        initHandlebar();
        initMenu();
        $("#addFilterButton").click(function () {
            toggleMenu();
        });
        $("#clearFilter").click(function () {
            clear();
        });
        $("#filter").on("click", ".filter-name-btn", function () {
            removeValue(this);
        });
        $('#filter').on("change", ".filter-value", function () {
            updateValue($(this).parent().attr("data-filter"));
        });
    }
    function initHandlebar() {
        Handlebars.registerHelper('ifEqual', function (v1, v2, options) {
            if (v1 === v2) {
                return options.fn(this);
            }
        });
    }
    function initMenu() {
        var $menu = $("#filterMenu");
        $menu.mouseleave(function () {
            $('#filterMenu').hide(400);
        });
        $menu.on("click", "a", function () {
            $("#filterMenu").hide();
            var field = $(this).parent().attr("data-filter");
            addValue(field);
            checkValueCount();
        });
    }

    function updateMenu() {
        require(["handlebars", "text!templates/filter-menu-template.html"],
            function (Handlebars, template) {
                var $menu = $("#filterMenu");
                $menu.empty();
                $menu.append(Handlebars.compile(template)({description: filterDescriptions}));
            }
        );
    }
    function toggleMenu() {
        var $menu = $("#filterMenu");
        if ($menu.is(':visible')) {
            $menu.hide(300);
        } else {
            require(["Util"],
                function (util) {
                    util.menuLocationRelativeTo($menu, $("#addFilterButton"));
                }
            );
            $menu.animate({ opacity: 'toggle', height: 'toggle'}, 300);
        }
    }

    function getFilterContext(field) {
        for (var i = 0; i < filterDescriptions.length ; i++) {
            if (filterDescriptions[i].field === field) {
                return filterDescriptions[i];
            }
        }
        return null;
    }

    function addValue(field, value) {
        var filterContext,
            filterTemplate,
            $filterPrevious,
            $filterElement,
            $editorElement;

        filterContext = getFilterContext(field);

        if (filterContext) {
            require(["text!templates/filter-value-template.html"],
                function (template) {
                    filterTemplate  = Handlebars.compile(template);
                    $filterPrevious = $(".filter-item[data-filter='" + field + "']");
                    $filterElement  = $(filterTemplate(filterContext));
                    if ($filterPrevious.length > 0) {
                        $filterPrevious.last().after($filterElement);
                        $filterPrevious.last().after(separatorContent);
                    } else {
                        $("#addFilterButton").before($filterElement);
                    }

                    if (!filterContext.multiset) {
                        $("#filterMenu > li[data-filter='" + field + "']").hide();
                    }

                    if (value) {
                        $editorElement = $filterElement.find(".filter-value");
                        if ($editorElement.is("input[type='checkbox']")) {
                            $editorElement.attr('checked', value === "true");
                        }
                        else {
                            $editorElement.val(value);
                        }
                    }
                    updateValue(field);
                }
            );
        }
    }
    function pickValue(field) {
        var elements,
            returnVal;
        elements = $(".filter-item[data-filter=" + field + "] .filter-value");
        if (elements.length === 0) {
            return null;
        }
        else {
            if (elements.is("[data-multi='true']")) {
                returnVal = [];
                elements.each(function (id, element) {
                    returnVal.push($(element).val());
                });
                return returnVal;
            } else {
                if (elements.is("input[type='checkbox']")) {
                    return String(elements.prop("checked"));
                }
                else {
                    return elements.val();
                }
            }
        }
    }
    function updateValue(field) {
        var value = pickValue(field);
        if (value) {
            filterValue[field] = value;
        }
        else {
            delete filterValue[field];
        }
        sessionStorage.setItem('filter', JSON.stringify(filterValue));
    }
    function removeValue(ovner) {
        var selfItem = $($(ovner).parent().get(0)),
            prevItem = selfItem.prev(),
            nextItem = selfItem.next(),
            field = selfItem.attr("data-filter");
        if (prevItem.is('.filter-separator')) {
            prevItem.remove();
        } else if (nextItem.is('.filter-separator')) {
            nextItem.remove();
        }
        selfItem.remove();
        $("#filterMenu > li[data-filter='" + field + "']").show();
        updateValue(field);
        checkValueCount();
    }

    function rebuild() {
        $(".filter-item, .filter-separator").remove();
        $("#filterMenu > li").show();
        $("#addFilterButton").show();
        var keys = Object.keys(filterValue);
        keys.forEach(function (key) {
            var value = filterValue[key];
            if (Array.isArray(value)) {
                value.forEach(function (subVal) {
                    addValue(key, subVal);
                });
            }
            else {
                addValue(key, value);
            }
        });
    }
    function checkValueCount() {
        var count = $(".filter-item").length,
            filterBtn = $("#addFilterButton");
        if (count < filterMaxCount) {
            filterBtn.show();
        } else {
            filterBtn.hide();
        }
    }

    function clear() {
        values({});
        sessionStorage.removeItem('filter');
    }

    function values(filterData) {
        if (filterData) {
            filterValue = filterData;
            rebuild();
        }
        else {
            return filterValue;
        }
    }
    function description(param) {
        if (param) {
            filterDescriptions = param;
            updateMenu();
        } else {
            return filterDescriptions;
        }
    }

    function loadDescription(url) {
        $.ajax({
            url: url,
            data: {}
        }).done(function (desc) {
            var filterStore = JSON.parse(sessionStorage.getItem('filter'));
            description(JSON.parse(desc));
            values(filterStore);
        }).fail(function () {
            console.log("Fail to load filter description!");
        });
    }
    return {
        init: init,
        description: description,
        values: values,

        loadDescription: loadDescription
    };
});




