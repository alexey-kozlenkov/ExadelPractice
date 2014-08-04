/**
 * Created by ala'n on 31.07.2014.
 */

var MAX_FILTER_COUNT = 10,
    filter = {
        changed: true,
        types: [
            {
                name: 'age',
                type: 'number'
            },
            {
                name: 'workingHours',
                type: 'number'
            },
            {
                name: 'billable',
                type: 'date'
            },
            {
                name: 'skill',
                type: 'leveled-list',
                values: ['java', 'C++', '.NET', 'HTML', 'Mongo DB', 'SQL'],
                multiset: true
            },
            {
                name: 'english',
                type: 'list',
                values: ['1', '2', '3', '4', '5']
            },
            {
                name: 'date',
                type: 'date'
            }
        ]
    };


$(window).ready(function () {
    $("#filterMenu").mouseleave(function () {
        $('#filterMenu').hide(400);
    });
    $("#addFilterButton").click(function () {
        toggleFilterChooseMenu();
    });
    $("#filterMenu").on("click","a",function(){
            $("#filterMenu").hide();
            addFilterAttribute($(this).text());
            checkFilterCount();
    });
    $("#filter").on("click",".filter-name-btn", function(){
        var selfItem = $($(this).parent().get(0));
        var prevItem = selfItem.prev();
        var nextItem = selfItem.next();
        var filterName = selfItem.attr("data-filter");
        if (prevItem.is('.filter-separator')) {
            prevItem.remove();
        } else if (nextItem.is('.filter-separator')) {
            nextItem.remove();
        }
        selfItem.remove();
        $("#filterMenu > li[data-filter-name='" + filterName + "']").show();
        checkFilterCount();
    });
});

function toggleFilterChooseMenu() {
    var $menu = $("#filterMenu");
    if ($menu.is(':visible')) {
        $menu.hide(300);
    } else {
        setMenuLocationRelativeTo($menu, $("#addFilterButton"));
        if (filter.changed) {
            filter.changed = false;
            var filterMenuTemplate = Handlebars.compile($('#filterMenuTemplate').html());
            $menu.empty();
            $menu.append(filterMenuTemplate(filter));
        }
        $('#filterMenu').animate({ opacity: 'toggle', height: 'toggle'}, 300);
    }
}

function addFilterAttribute(name) {
    var filterElementTemplate = Handlebars.compile($('#filterTemplate').html());
    var filterContext = _.find(filter.types, function(element){return element.name == name;});
    if(filterContext) {
        if (filterContext.multiset) {
            var filterEqType = $(".filter-item[data-filter='" + name + "']");
            if (filterEqType && filterEqType.length > 0) {
                filterEqType.last().after(filterElementTemplate({name: name, separate: true}));
            } else {
                $("#addFilterButton").before(filterElementTemplate({name: name}));
            }
        } else {
            $("#addFilterButton").before(filterElementTemplate({name: name}));
            $("#filterMenu > li[data-filter-name='" + name + "']").hide();
        }
    }
}

function checkFilterCount() {
    var count = $(".filter-item").length;
    var filterBtn = $("#addFilterButton");
    if (count < MAX_FILTER_COUNT) {
        filterBtn.show();
    } else {
        filterBtn.hide();
    }
    checkTopMarginOfList();
}

function pickFilters() {
    var returnStatement = {};
    var lastAtrName = "";

    var filterItems = $("#filter").find(".filter-item");

    for (var i = 0; i < filterItems.length; i++) {
        var element = $(filterItems[i]);

        var atr_name = element.attr('data-filter');
        var atr_value = $(element.find(".value-field")).val();

        if (!returnStatement[atr_name])
            returnStatement[atr_name] = '';
        else
            returnStatement[atr_name] += '&';

        switch (filterTypes.keyType[atr_name]) {
            case 'date':
                //TODO ! Date can be parsed as a long ( [RFC 3339] not god for working)
                returnStatement[atr_name] += atr_value;
                break;
            case 'number':
            case 'list':
                returnStatement[atr_name] += atr_value;
                break;
            case 'leveled-list':
                var atr_sub_value = $(element.find(".sub-value-field")).val();
                returnStatement[atr_name] += atr_value + '=' + atr_sub_value;
                break;
            default :
                console.error("Not defined filter type");
                break;
        }
        lastAtrName = atr_name;
    };
    return returnStatement;
}
