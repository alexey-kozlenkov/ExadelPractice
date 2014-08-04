/**
 * Created by ala'n on 31.07.2014.
 */

var MAX_FILTER_COUNT = 10,
    filterTypes = {
        changed: true,
        keys: ['age', 'working hour', 'billable', 'skill', 'english', 'date'],
        keyType: {
            'age': 'number',
            'working hour': 'number',
            'billable': 'date',
            'skill': 'leveled-list',
            'english': 'list',
            'date': 'date'
        },
        keyMap: {
            'skill': ['java', 'C++', '.NET', 'HTML', 'Mongo DB', 'SQL'],
            'english': ['1', '2', '3', '4', '5']
        },
        multiType: {
            'skill': true
        }
    },
    SEPARATOR = "<span class='static-green filter-separator'>and</span>";


$(window).ready(function(){
    $("#filterMenu").mouseleave(function () {
        $('#filterMenu').hide(400);
    });
    $("#addFilterButton").click(function () {
        addFilterAction();
    });
});

function addFilterAttribute(name) {
    var filterElementContent = createFilterAttributeContent(name);
    if (filterTypes.multiType[name]) {
        var filtersExist = existFilterAttribute(name);
        if (filtersExist && filtersExist.length > 0) {
            filtersExist.last().after(SEPARATOR + filterElementContent);
        } else {
            $("#addFilterButton").before(filterElementContent);
        }
    } else {
        $("#addFilterButton").before(filterElementContent);
        $("#filterMenu > li[data-filter-name='" + name + "']").hide();
    }
}
function createFilterAttributeContent(name) {
    var typeName = filterTypes.keyType[name];
    var htmContent = "";

    htmContent += "<div filter='" + name + "' class='btn-group input-group filter-item";
    htmContent += " data-filter-" + typeName + "'>";
    htmContent += "<button class='btn prj-btn remove-btn item-btn-attr' ";
    htmContent += "onclick=\"removeFilterAttribute( '" + name + "', this );\">";
    htmContent += name;
    htmContent += "</button>";

    switch (typeName) {
        case 'number':
            htmContent += "<input type='number' class='form-control value-field' placeholder='??'>";
            break;
        case 'date':
            htmContent += "<input type='date' class='form-control value-field' placeholder='date'>";
            break;
        case 'list':
            htmContent += "<select class='selectpicker value-field' style='width:60%'>";
            filterTypes.keyMap[name].forEach(function (value) {
                htmContent += "<option>";
                htmContent += value;
                htmContent += "</option>";
            });
            htmContent += "</select>";
            break;
        case 'leveled-list':
            htmContent += "<select class='selectpicker value-field' style='width:50%'>";
            filterTypes.keyMap[name].forEach(function (value) {
                htmContent += "<option>";
                htmContent += value;
                htmContent += "</option>";
            });
            htmContent += "</select>";
            htmContent += "<select class='selectpicker sub-value-field' style='width:40pt'>";
            for (var level = 1; level <= 5; level++) {
                htmContent += "<option>";
                htmContent += level;
                htmContent += "</option>";
            }
            htmContent += "</select>";
            break;
        default :
            console.error("Not defined filter type");
    }
    htmContent += "</div>";
    return htmContent;
}

function existFilterAttribute(name) {
    return $(".filter-item[data-filter='" + name + "']");
}
function removeFilterAttribute(name, element) {
    var selfItem = $($(element).parent().get(0));
    var prevItem = selfItem.prev();
    var nextItem = selfItem.next();
    if (prevItem.is('.filter-separator')) {
        prevItem.remove();
    } else if (nextItem.is('.filter-separator')) {
        nextItem.remove();
    }

    selfItem.remove();
    $("#filterMenu > li[data-filter-name='" + name + "']").show();
    checkFilterCount();
}

function checkFilterCount() {
    var count = $(".filter-item").length;
    var filterBtn = $("#addFilterButton");
    if (count < MAX_FILTER_COUNT) {
        filterBtn.prev().show();
        filterBtn.show();
    } else {
        filterBtn.prev().hide();
        filterBtn.hide();
    }
    checkContentMargin();
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
    }
    ;
    return returnStatement;
}


//Menu Listener body
function menuEvent(name) {
    $("#filterMenu").hide();
    addFilterAttribute(name);
    checkFilterCount();
}

function addFilterAction() {
    var $menu = $("#filterMenu");
    if ($menu.is(':visible')) {
        $menu.hide(300);
    } else {
        setLocationRelativeTo($menu, $("#addFilterButton"));
        if (filterTypes.changed) {
            filterTypes.changed = false;
            clearMenu($menu);
            fillMenu($menu, filterTypes.keys);
        }
        $('#filterMenu').animate({ opacity: 'toggle', height: 'toggle'}, 300);
    }
}

function clearMenu(menu) {
    menu.empty();
}
function fillMenu(menu, data) {
    var liCont = "";
    for (var i = 0; i < data.length; i++) {
        liCont += "<li class='menu-item' data-filter-name='" + data[i] + "'>" +
            "<a onclick='menuEvent(\"" + data[i] + "\");' role='menuitem'>" +
            data[i] + "</a></li>";
    }
    menu.append(liCont);
}

function setLocationRelativeTo(element, parent) {
    var winOffsetW = $(window).width();

    var pos = parent.offset();

    var x = pos.left;
    var space = pos.left + element.width() - winOffsetW + 4;
    if (space > 0)
        x -= space;
    var y = pos.top + parent.height();

    element.css(
        {
            left: x,
            top: y
        }
    );
}
