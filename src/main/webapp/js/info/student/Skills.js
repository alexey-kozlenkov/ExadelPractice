/**
 * Created by ala'n on 14.08.2014.
 */
define(["jquery", "handlebars", "Util", "text!templates/skill-template.html", "text!templates/skill-menu-template.html"],
    function ($, Handlebars, util, templateContent, templateMenuContent) {
        "use strict";

        var skillTemplate,
            skillMenuTemplate,
            skillList,
            loadPromise;

        function init() {
            initHandlebar();
            skillTemplate = Handlebars.compile(templateContent);
            skillMenuTemplate = Handlebars.compile(templateMenuContent);
            initButtons();
        }


        function initHandlebar() {
            Handlebars.registerHelper('selected', function (level, val) {
                return level == val ? ' selected' : '';
            });
        }
        function fillSelect() {
            var $list = $("#skillSelector");
            $list.empty();
            $list.append(skillMenuTemplate({skills: skillList}));
        }
        function initButtons() {
            var $skill = $("#addToSkillList");
            $skill.click(function () {
                var index = $("#skillSelector").val(),
                    newSkill = skillList[index];
                $("#skillControlLine").before(skillTemplate({skills: [newSkill]}));
            });
            $("#skillList").on("click", ".remove-btn", function () {
                $(this).parent().eq(0).parent().eq(0).remove();
            });
            $("#saveSkillInformation").click(save);
        }

        function save() {
            var studentId = sessionStorage.getItem("id"),
                englishLevel = $("#endlishLevel").val(),
                skillEntries = pickSkills(),
                promise;
            promise = $.ajax({
                url: "",
                type: "POST",
                data: {
                    studentId: studentId,
                    englishLevel: englishLevel,
                    skillIds: JSON.stringify(skillEntries[0]),
                    skillLevels: JSON.stringify(skillEntries[1])
                }
            });

            promise.done(function () {
                util.stateAnimate($("#saveSkillInformation"), "success", "Saved!");
            });
            promise.fail(function () {
                util.stateAnimate($("#saveSkillInformation"), "fail", "Not saved!");
            });
        }

        function fillList(skills) {
            $(".skill-list-item").remove();
            $("#englishLevelItem").after(skillTemplate({skills: skills}));
        }
        function pickSkills() {
            var $list  = $("#skillList"),
                $items = $list.find(".skill-level-select"),
                levels = [],
                skillIds = [];
            $items.each(function (index, element) {
                var $element = $(element);
                levels.push($element.val());
                skillIds.push($element.data('id'));
            });
            return [skillIds, levels];
        }

        function load() {
            var studentId = sessionStorage.getItem("id");
            loadPromise = $.ajax({
                url: "",
                data: {
                    studentId: studentId
                }
            });

            loadPromise.fail(function () {
                alert("skillTypesNotLoaded");
            });
            loadPromise.done(function (data) {
                var setEntity = JSON.parse(data);

                skillList = setEntity.skillList;
                $("#endlishLevel").val(setEntity.englishLevel);
                fillSelect();
                fillList(setEntity.skills);
            });
        }

        return {
            init: init,
            load: load
        };
    });