/**
 * Created by ala'n on 31.07.2014.
 */
var Filter = (function () {

    var MAX_FILTER_COUNT = 10,
        filterDescription = [
            {
                field: 'age',
                name: 'Age',
                type: 'number',
                minval: 1
            },
            {
                field: 'workingHours',
                name: 'Working hours',
                type: 'number',
                minval: 0
            },
            {
                name: 'Billable',
                type: 'boolean'
            },
            {
                name: 'Skill',
                type: 'list',
                values: ['java', 'C++', '.NET', 'HTML', 'Mongo DB', 'SQL'],
                multiset: true,
                placeholder: 'Tech.'
            },
            {
                name: 'English',
                type: 'list',
                values: ['Begginer', 'Elementary', 'Pre-Intermediate', 'Intermediate', 'Upper-Intermediate', 'Advanced'],
                placeholder: 'English level'
            },
            {
                name: 'Curator',
                type: 'text',
                placeholder: ' name '
            },
            {
                name: 'University',
                type: 'text',
                placeholder: ' ... '
            },
            {
                name: 'Faculty',
                type: 'text',
                placeholder: ' ... '
            },
            {
                name: 'Course',
                type: 'list',
                values: [1,2,3,4,5]
            },
            {
                name: 'Grad. year',
                type: 'number',
                minval: 2000,
                placeholder: '>2000'
            }
        ];

    function toggleFilterChooseMenu() {
        var $menu = $("#filterMenu");
        if ($menu.is(':visible')) {
            $menu.hide(300);
        } else {
            setMenuLocationRelativeTo($menu, $("#addFilterButton"));
            $menu.animate({ opacity: 'toggle', height: 'toggle'}, 300);
        }
    }

    function addFilterAttribute(name) {
        var filterElementTempl = Handlebars.compile($('#filterTemplate').html()),
            filterContext = _.find(filterDescription, function (element) {
            return element.name == name;
        });
        if (filterContext) {
            var prev = $(".filter-item[data-filter='" + name + "']"),
                filterEl = $(filterElementTempl({name: name})),
                template;

            switch (filterContext.type){
                case 'boolean':
                    template = Handlebars.compile($('#filterBooleanValueTemplate').html());
                    break;
                case 'text':
                    template = Handlebars.compile($('#filterTextValueTemplate').html());
                    break;
                case 'number':
                    template = Handlebars.compile($('#filterNumberValueTemplate').html());
                    break;
                case 'date':
                    template = Handlebars.compile($('#filterDataValueTemplate').html());
                    break;
                case 'list':
                    template = Handlebars.compile($('#filterListValueTemplate').html());
                    break;
                default :
                    break;
            }
            filterEl.append(template(filterContext));
            if (filterContext.multiset && prev.length > 0) {
                prev.last().after(filterEl);
            } else {
                $("#addFilterButton").before(filterEl);
            }
            if (!filterContext.multiset) {
                $("#filterMenu > li[data-filter-name='" + name + "']").hide();
            }
        }
    }

    function checkFilterCount() {
        var count = $(".filter-item").length,
            filterBtn = $("#addFilterButton");
        if (count < MAX_FILTER_COUNT) {
            filterBtn.show();
        } else {
            filterBtn.hide();
        }
        ListHeader.check();
    }

    return {
        init: function () {
            var $menu = $("#filterMenu"),
                filterMenuTemplate = Handlebars.compile($('#filterMenuTemplate').html());
            $menu.empty();
            $menu.append(filterMenuTemplate({filter: filterDescription}));

            $("#filterMenu").mouseleave(function () {
                $('#filterMenu').hide(400);
            });
            $("#addFilterButton").click(function () {
                toggleFilterChooseMenu();
            });
            $("#filterMenu").on("click", "a", function () {
                $("#filterMenu").hide();
                addFilterAttribute($(this).text());
                checkFilterCount();
            });
            $("#filter").on("click", ".filter-name-btn", function () {
                var selfItem = $($(this).parent().get(0)),
                    prevItem = selfItem.prev(),
                    nextItem = selfItem.next(),
                    filterName = selfItem.attr("data-filter");
                if (prevItem.is('.filter-separator')) {
                    prevItem.remove();
                } else if (nextItem.is('.filter-separator')) {
                    nextItem.remove();
                }
                selfItem.remove();
                $("#filterMenu > li[data-filter-name='" + filterName + "']").show();
                checkFilterCount();
            });
        },
        pick: function () {
            var returnStatement = {},
                filterItems = $("#filter").find(".filter-item");

            for (var i = 0; i < filterItems.length; i++) {
                var $element = $(filterItems[i]),
                    $valueElement = $($element.find(".filter-value")),
                    atrName = $element.attr('data-filter'),
                    value;

                if($valueElement.is("input[type='checkbox']"))
                    value = $valueElement.is(':checked');
                else
                if($valueElement.is("input[type='date'], input[type='text'], input[type='number'], select"))
                    value = $valueElement.val();
                else
                    console.error("Filter value type not defined;");

                if (returnStatement[atrName])
                    returnStatement[atrName] += '&' + value;
                else
                    returnStatement[atrName] = value;
            }
            return returnStatement;
        }
    }
}());

$(document).ready(Filter.init);

