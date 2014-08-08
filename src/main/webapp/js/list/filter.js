/**
 * Created by ala'n on 31.07.2014.
 */
define(["jquery", "underscore", "handlebars", "Util"], function ($, _, Handlebars, util) {
    "use strict";
    var shellTemplate = Handlebars.compile($('#filterTemplate').html()),
        maxValueCount = 8,
        description = [],
        model = {};

    function initMenu() {
        var $menu = $("#filterMenu");
        $menu.mouseleave(function () {
            $('#filterMenu').hide(400);
        });
        $menu.on("click", "a", function () {
            $("#filterMenu").hide();
            var field = $(this).parent().attr("data-filter");
            addValue(field);
            updateValue(field);
            checkValueCount();
        });
    }

    function updateMenu() {
        var $menu = $("#filterMenu"),
            filterMenuTemplate = Handlebars.compile($('#filterMenuTemplate').html());
        $menu.empty();
        $menu.append(filterMenuTemplate({description: description}));
    }

    function toggleMenu() {
        var $menu = $("#filterMenu");
        if ($menu.is(':visible')) {
            $menu.hide(300);
        } else {
            util.menuLocationRelativeTo($menu, $("#addFilterButton"));
            $menu.animate({ opacity: 'toggle', height: 'toggle'}, 300);
        }
    }

    function addValue(field, value) {
        var filterContext,
            editorTemplate,
            $shell,
            prev,
            $editorElement;
        filterContext = _.find(description, function (element) {
            return element.field === field;
        });
        if (filterContext) {
            editorTemplate = getValueEditorTemplate(filterContext.type);
            $shell = $(shellTemplate(filterContext));
            prev = $(".filter-item[data-filter='" + field + "']");
            $shell.append(editorTemplate(filterContext));
            if (prev.length > 0) {
                prev.last().after($shell);
                $shell.before($("#filterSeparator").html());
            } else {
                $("#addFilterButton").before($shell);
            }
            if (!filterContext.multiset) {
                $("#filterMenu > li[data-filter='" + field + "']").hide();
            }
            if (value) {
                $editorElement = $shell.find(".filter-value");
                if ($editorElement.is("input[type='checkbox']")) {
                    $editorElement.attr('checked', value === "true");
                }
                else {
                    $editorElement.val(value);
                }
            }
            checkValueCount();
        }
    }

    function getValueEditorTemplate(type) {
        switch (type) {
            case 'bool':
                return Handlebars.compile($('#filterBooleanValueTemplate').html());
            case 'text':
                return Handlebars.compile($('#filterTextValueTemplate').html());
            case 'number':
                return Handlebars.compile($('#filterNumberValueTemplate').html());
            case 'date':
                return Handlebars.compile($('#filterDataValueTemplate').html());
            case 'enumeration':
                return Handlebars.compile($('#filterEnumValueTemplate').html());
            case 'list':
                return Handlebars.compile($('#filterListValueTemplate').html());
            default :
                return null;
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
        if (value !== undefined) {
            model[field] = value;
        }
        else if (model[field] !== undefined) {
            delete model[field];
        }
        sessionStorage.setItem('filter', JSON.stringify(model));
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
        var keys = Object.keys(model);
        keys.forEach(function (key) {
            var value = model[key];
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
        if (count < maxValueCount) {
            filterBtn.show();
        } else {
            filterBtn.hide();
        }
    }

    function clear() {
        model = {};
        rebuild();
        sessionStorage.removeItem('filter');
    }

    function loadDecription(url) {
        $.ajax({
            url: url,
            data: {}
        }).done(function (desc) {
            var descParsed = JSON.parse(desc),
                filterStore;
            if (descParsed) {
                description = descParsed;
                updateMenu();
            }
            filterStore = JSON.parse(sessionStorage.getItem('filter'));
            if (filterStore) {
                model = filterStore;
                rebuild();
            }
        }).fail(function () {
            console.log("Fail to load filter description!");
        });
    }

    return {
        init: function () {
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

        },
        descript: function (param) {
            if (param) {
                description = param;
                updateMenu();
            } else {
                return description;
            }
        },
        values: function (filterData) {
            if (filterData) {
                model = filterData;
                rebuild();
            }
            else {
                return model;
            }
        },

        loadDescription: loadDecription
    };
});




