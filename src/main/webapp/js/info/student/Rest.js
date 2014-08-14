/**
 * Created by Administrator on 11.08.2014.
 */


define(["jquery", "handlebars", "FillBasicStudent", "Util", "Dialog", "text!templates/document-template.html", "text!templates/feedback-template.html", "jquery-tablesorter", "jquery-animate-colors"],
    function ($, Handlebars, fillBasic, util, dialog, templateDocumentContent, templateFeedbackContent) {
    "use strict";
    var MAX_NUMBER_TERMS = 10,
        MIN_MARK = 0,
        MAX_MARK = 10,
        templateDocument = Handlebars.compile(templateDocumentContent),
        templateFeedback = Handlebars.compile(templateFeedbackContent);

    function init() {
        //export info
        $("#exportInfoExcel").click(function () {
            exportStudentExcel();
        });

        $("#exportInfoPDF").click(function () {
            exportStudentPDF();
        });

        //sortable table
        $('#documentTable').tablesorter();

        //event handlers for submit button save
        $("#saveManualInformation").click(function () {
            //collect data entered by users
            var editedName = $("#name").val(),
                editedBirthDate = $("#birthDate").val(),
                editedLogin = $("#login").val(),
                editedEmail = $("#email").val(),
                editedSkype = $("#skype").val(),
                editedPhone = $("#phone").val(),
                editedPassword = $("#password").val(),
                editedState = $("#state :selected").val();

            saveManualInfoChanges(editedName, editedBirthDate, editedLogin, editedEmail, editedSkype, editedPhone, editedPassword, editedState);
        });

        $("#saveEducationInformation").click(function () {
            //collect data entered by users
            var $termMark = $(".term-mark"),
                editedUniversity = $("#institution :selected").val(),
                editedFaculty = $("#faculty :selected").val(),
                editedSpeciality = $("#speciality").val(),
                editedCourse = $("#course").val(),
                editedGroup = $("#group").val(),
                editedGraduationDate = $("#graduationDate").val(),
                editedTermMarks = "",
                numberTerms = $termMark.length - 1;
            $termMark.each(function (index) {
                if (index < numberTerms) {
                    editedTermMarks = editedTermMarks.concat($(this).val() + ";");
                }
                else {
                    editedTermMarks = editedTermMarks.concat($(this).val());
                }
            });

            saveEducationChanges(editedUniversity, editedFaculty, editedSpeciality, editedCourse, editedGroup, editedGraduationDate, editedTermMarks);
        });

        $("#saveExadelInformation").click(function () {
            //collect data entered by users
            var editedHireDate = $("#hireDate").val(),
                editedWorkingHours = $("#workingHours").val(),
                editedBillable = $("#billable").val(),
                editedWishingHours = $("#wishingHours").val(),
                editedCourseStartWorking = $("#courseStartWorking").val(),
                editedTrainingBeforeWorking = $("#trainingBeforeWorking").is(':checked'),
                editedTrainingExadel = $("#trainingExadel").val(),

                editedCurrentProject = $("#currentProject").val(),
                editedRoleCurrentProject = $("#roleCurrentProject").val(),
                editedCurrentTeamLead = $("#currentTeamLead").text(),
                editedCurrentProjectManager = $("#currentProjectManager").text(),
                editedTechsCurrentProject = $("#techsCurrentProject").val();

            saveExadelChanges(editedWorkingHours, editedHireDate, editedBillable, editedWishingHours, editedCourseStartWorking, editedTrainingBeforeWorking, editedTrainingExadel, editedCurrentProject, editedRoleCurrentProject, editedCurrentTeamLead, editedCurrentProjectManager, editedTechsCurrentProject);
        });

        $("#saveDocumentsInformation").click(function () {
            var fields = ['doctype', 'issueDate', 'expirationDate', 'info'],
                newDocuments = [],
                cellValue;
            $(".new-document").each(function () {
                var $this = $(this),
                    cells = $this.find("td"),
                    value = {},
                    i,
                    cellsLength = cells.length;

                for (i = 0; i < cellsLength; i++) {
                    cellValue = $(cells[i]).text();
                    if (i === 2) {
                        cellValue = (cellValue === "") ? undefined : cellValue;
                    }
                    value[fields[i]] = cellValue;
                }
                value.studentId = fillBasic.studentId();

                newDocuments.push(value);
                $this.removeClass("new-document");
            });

            newDocuments = JSON.stringify(newDocuments);

            saveDocumentsInformation(newDocuments);
        });

        $("#saveFeedbacksInformation").click(function () {
            var fields = ['professionalCompetence', 'attitudeToWork', 'collectiveRelations', 'professionalProgress', 'needMoreHours', 'isOnProject', 'content', 'feedbackDate'],
                newFeedbacks = [];
            $(".new-feedback").each(function () {
                var $this = $(this),
                    $thisFields = $this.find("dd"),
                    feedback = {},
                    feedbacker = getFeedbacker(),
                    feedbackId = $this.attr('data-id');
                if (feedbackId) {
                    feedback.id  = feedbackId;
                }
                feedback.feedbacker = feedbacker;
                feedback.studentId = fillBasic.studentId();
                $thisFields.each(function (index, value) {
                    if (index === 4) {
                        var needMoreHoursBoolean;
                        needMoreHoursBoolean = $(value).text() === 'Yes';
                        feedback[fields[index]] = needMoreHoursBoolean + "";
                    }
                    else {
                        feedback[fields[index]] = $(value).text();
                    }
                });
                console.log(feedback);
                newFeedbacks.push(feedback);
                $this.removeClass("new-feedback");
            });
            newFeedbacks = JSON.stringify(newFeedbacks);
            console.log(newFeedbacks);
            saveFeedbacksInformation(newFeedbacks);
        });

        //load documents
        $("#documentsHeader").click(function () {
            if ($("#documentsHeader").attr("data-visible") === "false") {
                $.ajax({
                    type: "GET",
                    url: "/info/getActualDocuments",
                    cashe: false,
                    data: {
                        "studentId": fillBasic.studentId()
                    },
                    success: function (data) {
                        $("#documents").empty();

                        var documents = JSON.parse(data);
                        $.each(documents, function (index, value) {
                                $("#documents").append(templateDocument({
                                    doctype: value.doctype,
                                    issueDate: value.issueDate,
                                    expirationDate: value.expirationDate,
                                    info: value.info

                                }));

                                if (expiredSoon(value.expirationDate)) {
                                    $("#documents tr").last().addClass("expired-soon-document");
                                }
                                $("#documentTable").trigger("update");
                            });
                    },
                    error: function () {
                        alert("error");
                    }
                });
                $("#documentsHeader").attr("data-visible", "true");
            }
            else {
                $("#documentsHeader").attr("data-visible", "false");
            }

        });

        //load feedbacks
        $("#feedbacksHeader").click(function () {
            if ($("#feedbacksHeader").attr("data-visible") === "false") {

                var role = sessionStorage.getItem("role"),
                    getFeedbacks;

                if (role === '2' || role === '3') {
                    getFeedbacks = $.ajax({
                        type: "GET",
                        url: "/info/getMyFeedbacks",
                        cashe: false,
                        data: {
                            "employeeId": sessionStorage.getItem("id"),
                            "studentId" : fillBasic.studentId()
                        }
                    });
                    getFeedbacks.done(function (data) {
                        $(".feedback-list").empty();

                        var feedbacks = JSON.parse(data);
                        $.each(feedbacks, function (index, value) {
                            var role = sessionStorage.getItem("role"),
                                date = new Date(value.feedbackDate),
                                needMoreHours = (value.needMoreHours === true) ? 'Yes' : 'No',
                                feedback = {
                                    feedbackId : value.id,
                                    feedbacker: value.feedbacker.name,
                                    professionalQuality: value.professionalCompetence,
                                    relevantToWork: value.attitudeToWork,
                                    relationshipWithStaff: value.collectiveRelations,
                                    professionalGrowth: value.professionalProgress,
                                    needMoreHours: needMoreHours,
                                    realProject: value.isOnProject,
                                    additionalInfo: value.content,
                                    feedbackDate: util.formatDate(date)
                                };
                            $(".feedback-list").prepend(templateFeedback(feedback));
                        });
                    });
                    getFeedbacks.fail(function () {
                        alert("error");
                    });
                }
                else {
                    getFeedbacks = $.ajax({
                        type: "GET",
                        url: "/info/getAllFeedbacks",
                        cashe: false,
                        data: {
                            "studentId": fillBasic.studentId()
                        }
                    });
                    getFeedbacks.done(function (data) {
                        $(".feedback-list").empty();

                        var feedbacks = JSON.parse(data);
                        $.each(feedbacks, function (index, value) {
                            var role = sessionStorage.getItem("role"),
                                date = new Date(value.feedbackDate),
                                needMoreHours = (value.needMoreHours === true) ? 'Yes' : 'No',
                                feedback = {
                                    feedbackId : value.id,
                                    feedbacker: value.feedbacker.name,
                                    professionalQuality: value.professionalCompetence,
                                    relevantToWork: value.attitudeToWork,
                                    relationshipWithStaff: value.collectiveRelations,
                                    professionalGrowth: value.professionalProgress,
                                    needMoreHours: needMoreHours,
                                    realProject: value.isOnProject,
                                    additionalInfo: value.content,
                                    feedbackDate: util.formatDate(date)
                                };
                            $(".feedback-list").prepend(templateFeedback(feedback));

                            $(".feedback-list button").prop("hidden", true);

                        });
                    });
                    getFeedbacks.fail(function () {
                        alert("error");
                    });
                }


                $("#feedbacksHeader").attr("data-visible", "true");
            }
            else {
                $("#feedbacksHeader").attr("data-visible", "false");
            }

        });

        // show/hide expired docs
        $("#expiredDocs").click(function () {
            var $expiredDocs = $("#expiredDocs"),
                action = $expiredDocs.attr('data-do');

            if (action === 'show') {
                $.ajax({
                    type: "GET",
                    url: "/info/getExpiredDocuments",
                    cashe: false,
                    data: {
                        "studentId": fillBasic.studentId()
                    },
                    success: function (data) {
                        var documents = JSON.parse(data);

                        $.each(documents, function (index, value) {
                            $("#documents").append(templateDocument({
                                doctype: value.doctype,
                                issueDate: value.issueDate,
                                expirationDate: value.expirationDate,
                                info: value.info

                            }));
                            $("#documents tr").last().addClass("expired-document");
                            $("#documentTable").trigger("update");
                        });
                        $expiredDocs.attr('data-do', 'hide');
                        $expiredDocs.text("Hide expired");
                    },
                    error: function () {
                        alert("error");
                    }
                });
            }
            else if (action === 'hide') {
                $(".expired-document").each(function () {
                    $(this).remove();
                    $("#documentTable").trigger("update");
                });
                $expiredDocs.attr('data-do', 'show');
                $expiredDocs.text("Show expired");
            }
        });

        //add document
        $("#addNewDocument").click(function () {
            dialog.showDialog("add-document", "280px");
        });

        $("#addDocument").click(function () {
            var doctype = $("#doctype").val(),
                issueDate = $("#issueDate").val(),
                expirationDate = $("#expirationDate").val(),
                info = $("#info").val(),
                newDocument = {
                    doctype: doctype,
                    issueDate: issueDate,
                    expirationDate: expirationDate,
                    info: info
                };

            $("#doctype").val("");
            $("#issueDate").val("");
            $("#expirationDate").val("");
            $("#info").val("");
            dialog.closeDialog();
            $("#documents").prepend(templateDocument(newDocument));
            $("#documents tr").first().addClass("new-document");
            $("#documentTable").trigger("update");
        });

        //write or edit feedback
        $("#writeFeedback").click(function () {
            dialog.showDialog("add-feedback", "500px");
        });

        $(".feedback-list").on("click", 'button', function () {
            var currentFeedback = $(this).parent().eq(0),
                feedbackFields = currentFeedback.find("dd");
            $("#feedbackId").text(currentFeedback.attr("data-id"));
            $("#professionalQuality").val($(feedbackFields[0]).text());
            $("#relevantToWork").val($(feedbackFields[1]).text());
            $("#relationshipWithStaff").val($(feedbackFields[2]).text());
            $("#professionalGrowth").val($(feedbackFields[3]).text());
            $("#realProject").val($(feedbackFields[5]).text());
            $("#additionalInfo").val($(feedbackFields[6]).text());
            $("#needMoreHours :contains(" + "\'" + $(feedbackFields[4]).text() + "\'" + ")").attr("selected", "selected");
            //TODO where
            currentFeedback.addClass("insert-here");
            // or simply delete it and prepend edited feedback
            currentFeedback.remove();

            dialog.showDialog("add-feedback", "500px");
        });

        $("#addFeedback").click(function () {
            var date = new Date(Date.now()),
                feedbackId =  $("#feedbackId").text(),
                feedbacker = sessionStorage.getItem("username"),
                professionalQuality = $("#professionalQuality").val(),
                relevantToWork = $("#relevantToWork").val(),
                relationshipWithStaff = $("#relationshipWithStaff").val(),
                professionalGrowth = $("#professionalGrowth").val(),
                realProject = $("#realProject").val(),
                additionalInfo = $("#additionalInfo").val(),
                needMoreHours = $("#needMoreHours option:selected").text(),
                data = {
                    feedbackId : feedbackId,
                    feedbacker : feedbacker,
                    professionalQuality : professionalQuality,
                    relevantToWork : relevantToWork,
                    relationshipWithStaff : relationshipWithStaff,
                    professionalGrowth : professionalGrowth,
                    needMoreHours : needMoreHours,
                    realProject : realProject,
                    additionalInfo : additionalInfo,
                    feedbackDate : util.formatDate(date)
                },
                newFeedback;

            $(".feedback-list").prepend(templateFeedback(data));

            newFeedback = $(".feedback-list li").first();
            newFeedback.addClass("new-feedback");

            //clearFields
            $("dialog-content").find("textarea").each(function () {
                $(this).val("");
            });


            dialog.closeDialog();
        });
        // close any dialog
        $("#closeDialog").click(function () {
            dialog.closeDialog();
        });

        //handler for state select
        $("#state").change(
            fillBasic.checkState
        );

        //handler for institution select
        $("#institution").change(
            syncInstitutionAndFaculties
        );

        //handler termMark changed
        $(".term-mark-list").on("change", 'input', function () {
            var $this = $(this);
            if (!validInputTermMark($this.val())) {
                $this.addClass("term-mark-invalid", 1000);
                $this.val(null);
            }
            else {
                $this.removeClass("term-mark-invalid", 200);
            }
        });

        $("#addNextTerm").click(function () {
            var numberTerms = $("#termMarkList").children().length,
                lastTerm;
            lastTerm = $(".term-mark-list li input").last();

            if (lastTerm.val() === "") {
                lastTerm.focus();
            }
            else {
                ++numberTerms;
                require(["text!templates/term-mark-template.html"],
                    function (template) {
                        var content = Handlebars.compile(template)({});
                        $("#termMarkList").append(content);
                    }
                );

                if (numberTerms === MAX_NUMBER_TERMS) {
                    $("#addNextTerm").attr("disabled", "true");
                }
            }
        });

        //handler go to employee
        $("#currentTeamLead").click(function () {
            var $this = $(this),
                href = "/info/employee?id=" + $this.attr("data-id");
            $this.attr("href", href);
        });

        $("#currentProjectManager").click(function () {
            var $this = $(this),
                href = "/info/employee?id=" + $this.attr("data-id");
            $this.attr("href", href);
        });

       // change team lead or project manager
        $("#changeTeamLead").click(function () {
            loadAllEmployees();
            dialog.showDialog('change-appointed-employee', '200px');
            $("#employeeToSelect").attr("data-whom-change", "team-lead");

        });

        $("#changeProjectManager").click(function () {
            loadAllEmployees();
            dialog.showDialog('change-appointed-employee', '200px');
            $("#employeeToSelect").attr("data-whom-change", "project-manager");
        });

        $("#changeAppointedEmployee").click(function () {
            var $employeeSelected = $("#employeeToSelect :selected"),
                whomChange =  $("#employeeToSelect").attr("data-whom-change");
            if (whomChange === 'team-lead') {
                $("#currentTeamLead").text($employeeSelected.text());
                $("#currentTeamLead").attr("data-id", $employeeSelected.val());
            }
            else if (whomChange === 'project-manager') {
                $("#currentProjectManager").text($employeeSelected.text());
                $("#currentProjectManager").attr("data-id", $employeeSelected.val());
            }
            dialog.closeDialog();

        });

    }

    function validInputTermMark(termMarkVal) {
            if (termMarkVal <= MIN_MARK || termMarkVal > MAX_MARK) {
                return false;
            }
            return true;
        }

    function expiredSoon(expirationDate) {
            var twoWeeks = 1000 * 60 * 60 * 24 * 14,
                twoWeeksAfterNow = Date.now() + twoWeeks;
            expirationDate = Date.parse(expirationDate);
            if (expirationDate <= twoWeeksAfterNow) {
                return true;
            }
            else {
                return false;
            }
        }

    function syncInstitutionAndFaculties() {
        var universityId = $("#institution :selected").val();
        fillBasic.getFaculties(universityId);
    }

    function loadAllEmployees() {
        var $employeeSelect = $("#employeeToSelect"),
            loadAllEmpl = $.ajax({
                type: "GET",
                url: "/info/getAllEmployees",
                dataType: 'json'
            });
        loadAllEmpl.done(function (data) {
            $employeeSelect.empty();
            require(["text!templates/employee-option-template.html"], function (template) {
                $employeeSelect.append(Handlebars.compile(template) ({
                        values : data
                    })
                );
            });
        });
        loadAllEmpl.fail(function () {
            alert("failed loading");
        });
    }

    function getFeedbacker() {
        var feedbacker,
            feedbackerId = sessionStorage.getItem("id"),
            get = $.ajax({
            type: "GET",
            url: "/info/getFeedbacker",
            dataType: 'json', // from the server!
            async : false,
            data: {
                'feedbackerId': feedbackerId
            }
        });
        get.done(function (data) {
            feedbacker = data;
        });
        get.fail(function () {
            alert("error get feedbacker");
        });
        return feedbacker;
    }

    function exportStudentExcel() {
            window.open("/info/exportExcel?studentId=" + fillBasic.studentId(), "Export file");
        }

    function exportStudentPDF() {
            window.open("/info/exportPDF?studentId=" + fillBasic.studentId(), "Export file");
        }

    function saveManualInfoChanges(editedName, editedBirthDate, editedLogin, editedEmail, editedSkype, editedPhone, editedPassword, editedState) {
            $.ajax({
                type: "POST",
                //SEND
                url: "/info/postStudentManualInformation",
                dataType: 'json', // from the server!
                data: {
                    'studentId': fillBasic.studentId(),
                    'studentName': editedName,
                    'studentBirthDate': editedBirthDate,
                    'studentLogin': editedLogin,
                    'studentPassword': editedPassword,
                    'studentEmail': editedEmail,
                    'studentSkype': editedSkype,
                    'studentPhone': editedPhone,
                    'studentState': editedState
                },
                success: function () {
                    $("#saveManualInformation").text("Saved!");
                    $("#saveManualInformation").animate({
                        backgroundColor: '#5cb85c',
                        borderColor: '#4cae4c'
                    }, {
                        duration: 500,
                        easing: "swing",
                        complete: setTimeout(function () {
                            $("#saveManualInformation").animate({
                                backgroundColor: '#4A5D80',
                                borderColor: '#2D3E5C'
                            }, 500);
                            $("#saveManualInformation").text("Save");
                        }, 1000)
                    });

                    $("#headerName").text(editedName);
                },
                error: function () {
                    $("#saveManualInformation").text("Check out!");
                    $("#saveManualInformation").animate({
                        backgroundColor: '#CD5C5C',
                        borderColor: '#C16868'
                    }, {
                        duration: 500,
                        easing: "swing",
                        complete: setTimeout(function () {
                            $("#saveManualInformation").animate({
                                backgroundColor: '#4A5D80',
                                borderColor: '#2D3E5C'
                            }, 500);
                            $("#saveManualInformation").text("Save");
                        }, 1000)
                    });
                }
            });
        }

    function saveEducationChanges(editedUniversity, editedFaculty, editedSpeciality, editedCourse, editedGroup, editedGraduationDate, editedTermMarks) {
            $.ajax({
                type: "POST",
                //SEND
                url: "/info/postEducation",
                dataType: 'json', // from the server!
                data: {
                    'studentId': fillBasic.studentId(),
                    'studentUniversity': editedUniversity,
                    'studentFaculty': editedFaculty,
                    'studentSpeciality': editedSpeciality,
                    'studentCourse': editedCourse,
                    'studentGroup': editedGroup,
                    'studentGraduationDate': editedGraduationDate,
                    'studentTermMarks': editedTermMarks
                },
                success: function () {
                    $("#saveEducationInformation").text("Saved!");
                    $("#saveEducationInformation").animate({
                        backgroundColor: '#5cb85c',
                        borderColor: '#4cae4c'
                    }, {
                        duration: 500,
                        easing: "swing",
                        complete: setTimeout(function () {
                            $("#saveEducationInformation").text("Saved!");
                            $("#saveEducationInformation").animate({
                                backgroundColor: '#4A5D80',
                                borderColor: '#2D3E5C'
                            }, 500);
                            $("#saveEducationInformation").text("Save");
                        }, 1000)
                    });
                },
                error: function () {
                    $("#saveEducationInformation").text("Check out!");
                    $("#saveEducationInformation").animate({
                        backgroundColor: '#CD5C5C',
                        borderColor: '#C16868'
                    }, {
                        duration: 500,
                        easing: "swing",
                        complete: setTimeout(function () {
                            $("#saveEducationInformation").animate({
                                backgroundColor: '#4A5D80',
                                borderColor: '#2D3E5C'
                            }, 500);
                            $("#saveEducationInformation").text("Save");
                        }, 1000)
                    });
                }
            });
        }

    function saveExadelChanges(editedWorkingHours, editedHireDate, editedBillable, editedWishingHours, editedCourseStartWorking, editedTrainingBeforeWorking, editedTrainingExadel, editedCurrentProject, editedRoleCurrentProject, editedCurrentTeamLead, editedCurrentProjectManager, editedTechsCurrentProject) {
            var postExadel = $.ajax({
                type: "POST",
                //SEND
                url: "/info/postExadel",
                dataType: 'json', // from the server!
                data: {
                    'studentId': fillBasic.studentId(),
                    'studentWorkingHours': editedWorkingHours,
                    'studentHireDate': editedHireDate,
                    'studentBillable': editedBillable,
                    'studentWishingHours': editedWishingHours,
                    'studentCourseStartWorking': editedCourseStartWorking,
                    'studentTrainingBeforeWorking': editedTrainingBeforeWorking,
                    'studentTrainingExadel': editedTrainingExadel,
                    'studentCurrentProject': editedCurrentProject,
                    'studentRoleCurrentProject': editedRoleCurrentProject,
                    'studentCurrentTeamLead': editedCurrentTeamLead,
                    'studentCurrentProjectManager': editedCurrentProjectManager,
                    'studentTechsCurrentProject': editedTechsCurrentProject
                }
            });
            postExadel.done(function () {
                $("#saveExadelInformation").text("Saved!");
                $("#saveExadelInformation").animate({
                    backgroundColor: '#5cb85c',
                    borderColor: '#4cae4c'
                }, {
                    duration: 500,
                    easing: "swing",
                    complete: setTimeout(function () {
                        $("#saveExadelInformation").animate({
                            backgroundColor: '#4A5D80',
                            borderColor: '#2D3E5C'
                        }, 500);
                        $("#saveExadelInformation").text("Save");
                    }, 1000)
                });
            });
            postExadel.fail(function () {
                $("#saveExadelInformation").text("Check out!");
                $("#saveExadelInformation").animate({
                    backgroundColor: '#CD5C5C',
                    borderColor: '#C16868'
                }, {
                    duration: 500,
                    easing: "swing",
                    complete: setTimeout(function () {
                        $("#saveExadelInformation").animate({
                            backgroundColor: '#4A5D80',
                            borderColor: '#2D3E5C'
                        }, 500);
                        $("#saveExadelInformation").text("Save");
                    }, 1000)
                });
            });

        }

    function saveDocumentsInformation(newDocuments) {
            var postDocuments = $.ajax({
                type: "POST",
                //SEND
                url: "/info/postDocuments",
                dataType: 'json', // from the server!
                data: {'documents': newDocuments}

            });
            postDocuments.done(function () {
                $("#saveDocumentsInformation").text("Saved!");
                $("#saveDocumentsInformation").animate({
                    backgroundColor: '#5cb85c',
                    borderColor: '#4cae4c'
                }, {
                    duration: 500,
                    easing: "swing",
                    complete: setTimeout(function () {
                        $("#saveDocumentsInformation").animate({
                            backgroundColor: '#4A5D80',
                            borderColor: '#2D3E5C'
                        }, 500);
                        $("#saveDocumentsInformation").text("Save");
                    }, 1000)
                });
            });
            postDocuments.fail(function () {
                $("#saveDocumentsInformation").text("Check out!");
                $("#saveDocumentsInformation").animate({
                    backgroundColor: '#CD5C5C',
                    borderColor: '#C16868'
                }, {
                    duration: 500,
                    easing: "swing",
                    complete: setTimeout(function () {
                        $("#saveDocumentsInformation").animate({
                            backgroundColor: '#4A5D80',
                            borderColor: '#2D3E5C'
                        }, 500);
                        $("#saveDocumentsInformation").text("Save");
                    }, 1000)
                });
            });

        }

    function  saveFeedbacksInformation(newFeedbacks) {
        var postFeedbacks = $.ajax({
            type: "POST",
            url: "/info/postFeedbacks",
            dataType: 'json',
            data: {'feedbacks': newFeedbacks}
        });
        postFeedbacks.done(function () {
            $("#saveFeedbacksInformation").text("Saved!");
            $("#saveFeedbacksInformation").animate({
                backgroundColor: '#5cb85c',
                borderColor: '#4cae4c'
            }, {
                duration: 500,
                easing: "swing",
                complete: setTimeout(function () {
                    $("#saveFeedbacksInformation").animate({
                        backgroundColor: '#4A5D80',
                        borderColor: '#2D3E5C'
                    }, 500);
                    $("#saveFeedbacksInformation").text("Save");
                }, 1000)
            });
        });
        postFeedbacks.fail(function () {
            $("#saveFeedbacksInformation").text("Check out!");
            $("#saveFeedbacksInformation").animate({
                backgroundColor: '#CD5C5C',
                borderColor: '#C16868'
            }, {
                duration: 500,
                easing: "swing",
                complete: setTimeout(function () {
                    $("#saveFeedbacksInformation").animate({
                        backgroundColor: '#4A5D80',
                        borderColor: '#2D3E5C'
                    }, 500);
                    $("#saveFeedbacksInformation").text("Save");
                }, 1000)
            });
        });

    }

    return {
        init : init
    };


});