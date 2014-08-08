/**
 * Created by ala'n on 31.07.2014.
 */
var Filter = (function () {

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
            setMenuLocationRelativeTo($menu, $("#addFilterButton"));
            $menu.animate({ opacity: 'toggle', height: 'toggle'}, 300);
        }
    }

    function addValue(field, value) {
        var filterContext = _.find(description, function (element) {
            return element.field == field;
        });
        if (filterContext) {
            var editorTemplate = getValueEditorTemplate(filterContext.type),
                $shell = $(shellTemplate(filterContext)),
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
                var $editorElement = $shell.find(".filter-value");
                if ($editorElement.is("input[type='checkbox']"))
                    $editorElement.attr('checked', value);
                else
                    $editorElement.val(value);
            }
            checkValueCount();
        }
    }

    function getValueEditorTemplate(type) {
        switch (type) {
            case 'bool':
                return Handlebars.compile($('#filterBooleanValueTemplate').html());
                break;
            case 'text':
                return Handlebars.compile($('#filterTextValueTemplate').html());
                break;
            case 'number':
                return Handlebars.compile($('#filterNumberValueTemplate').html());
                break;
            case 'date':
                return Handlebars.compile($('#filterDataValueTemplate').html());
                break;
            case 'enumeration':
                return Handlebars.compile($('#filterEnumValueTemplate').html());
                break;
            case 'list':
                return Handlebars.compile($('#filterListValueTemplate').html());
                break;
            default :
                break;
        }
    }

    function pickValue(field) {
        var elements = $(".filter-item[data-filter=" + field + "] .filter-value");
        if (elements.length == 0) {
            return null;
        }
        else {
           if(elements.is("[data-multi='true']")) {
               var returnVal = [];
               elements.each(function (id, element) {
                   returnVal.push($(element).val());
               });
               return returnVal;
           }else{
               if (elements.is("input[type='checkbox']"))
                   return elements.prop("checked");
               else
                   return elements.val();
           }
        }
    }

    function updateValue(field) {
        var value = pickValue(field);
        if (value != undefined)
            model[field] = value;
        else if (model[field] != undefined)
            delete model[field];
        sessionStorage.setItem('filter', JSON.stringify(model));
        console.log(model);
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
            if (Array.isArray(value))
                value.forEach(function (subVal) {
                    addValue(key, subVal);
                });
            else
                addValue(key, value);
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
            } else
                return description;
        },
        values: function (filterData) {
            if (filterData) {
                model = filterData;
                rebuild();
            }
            else
                return model;
        }
    }
}());

$(document).ready(function () {
    Filter.init();
    $.ajax({
        url: "/list/filterDescription",
        data: {}
    }).done(function (desc) {
        var descParsed = JSON.parse(desc);
        Filter.descript(descParsed);
        console.log("Filter description load successfully!");
        var filterStore = JSON.parse(sessionStorage.getItem('filter'));
        if (filterStore)Filter.values(filterStore);
    }).fail(function () {
        Filter.descript([
            {
                field: 'age',
                type: 'number',
                name: 'Age',
                minVal: 1
            },
            {
                field: 'workingHours',
                type: 'number',
                name: 'Working hours',
                minVal: 0
            },
            {
                field: 'billable',
                type: 'bool',
                name: 'Billable'
            },
            {
                field: 'skill',
                type: 'list',
                name: 'Skill',
                values: {101: 'Java', 102: 'C++', 103: '.NET', 104: 'HTML', 105: 'Mongo DB', 106: 'SQL'},
                multiset: true
            },
            {
                field: 'english',
                type: 'list',
                name: 'English',
                values: {1: 'Begginer', 2: 'Elementary', 3: 'Pre-Intermediate', 4: 'Intermediate', 5: 'Upper-Intermediate', 6: 'Advanced'}
            },
            {
                field: 'curator',
                type: 'text',
                name: 'Curator',
                placeholder: ' name '
            },
            {
                field: 'university',
                type: 'text',
                name: 'University',
                placeholder: ' ... '
            },
            {
                field: 'faculty',
                type: 'text',
                name: 'Faculty',
                placeholder: ' ... '
            },
            {
                field: 'course',
                type: 'enumeration',
                name: 'Course',
                values: [1, 2, 3, 4, 5]
            },
            {
                field: 'graduationYear',
                type: 'number',
                name: 'Grad. year',
                minVal: 2000
            }
        ]);
        console.log("Fail to load filter description!");
        var filterStore = JSON.parse(sessionStorage.getItem('filter'));
        if (filterStore)Filter.values(filterStore);
    });
});


