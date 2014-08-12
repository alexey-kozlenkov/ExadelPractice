/**
 * Created by Administrator on 11.08.2014.
 */


define(["jquery", "handlebars", "FillBasic", "Util", "Dialog", "text!templates/document-template.html", "text!templates/feedback-template.html", "jquery-tablesorter", "jquery-animate-colors"],
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
                editedUniversity = $("#institution").val(),
                editedFaculty = $("#faculty").val(),
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
                newDocuments = [];
            $(".new-document").each(function () {
                var $this = $(this),
                    cells = $this.find("td"),
                    value = {},
                    i,
                    cellsLength = cells.length;

                for (i = 0; i < cellsLength; i++) {
                    value[fields[i]] = $(cells[i]).text();
                }

                value.studentId = fillBasic.studentId;

                console.log(value);
                newDocuments.push(value);
                $this.removeClass("new-document");
            });

            newDocuments = JSON.stringify(newDocuments);

            saveDocumentsInformation(newDocuments);
        });

        //load documents
        $("#documentsHeader").click(function () {
            if ($("#documentsHeader").attr("data-visible") === "false") {
                $.ajax({
                    type: "GET",
                    url: "/info/getActualDocuments",
                    cashe: false,
                    data: {
                        "studentId": fillBasic.studentId
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

                var getFeedbacks =  $.ajax({
                    type: "GET",
                    url: "/info/getFeedbacks",
                    cashe: false,
                    data: {
                        "studentId": fillBasic.studentId
                    }
                });
                getFeedbacks.done(function (data) {
                    $(".feedback-list").empty();

                    var feedbacks = JSON.parse(data);
                    $.each(feedbacks, function (index, value) {
                        var date = new Date(value.feedbackDate),
                            feedback = {
                                feedbacker : value.feedbacker.id,
                                professionalQuality : value.professionalCompetence,
                                relevantToWork : value.attitudeToWork,
                                relationshipWithStaff : value.collectiveRelations,
                                professionalGrowth : value.professionalProgress,
                                needMoreHours : value.needMoreHours,
                                realProject : value.isOnProject,
                                additionalInfo : value.content,
                                feedbackDate : util.formatDate(date)
                            };
                        $(".feedback-list").prepend(templateFeedback(feedback));
                    });
                });
                getFeedbacks.fail(function () {
                    alert("error");
                });


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
                        "studentId": fillBasic.studentId
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
            dialog.showDialog("add-document", "210px");
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
        $("#addFeedback").click(function () {
            var date = new Date(Date.now()),
                data = {
                    feedbacker : "John White",
                    professionalQuality : "ok quality",
                    relevantToWork : "ok relevant",
                    relationshipWithStaff : "ok relationship",
                    professionalGrowth : "ok progress",
                    needMoreHours : "yes need",
                    realProject : "no project",
                    additionalInfo : "comment",
                    feedbackDate : util.formatDate(date)
                };
            $(".feedback-list").prepend(templateFeedback(data));
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

    function exportStudentExcel() {
            window.open("/info/exportExcel?studentId=" + fillBasic.studentId, "Export file");
        }

    function exportStudentPDF() {
            window.open("/info/exportPDF?studentId=" + fillBasic.studentId, "Export file");
        }

    function saveManualInfoChanges(editedName, editedBirthDate, editedLogin, editedEmail, editedSkype, editedPhone, editedPassword, editedState) {
            $.ajax({
                type: "POST",
                //SEND
                url: "/info/postManualInformation",
                dataType: 'json', // from the server!
                data: {
                    'studentId': fillBasic.studentId,
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
                    'studentId': fillBasic.studentId,
                    'studentUniversity': editedUniversity,
                    'studentFaculty': editedFaculty,
                    'studentSpeciality': editedSpeciality,
                    'studentCourse': editedCourse,
                    'studentGroup': editedGroup,
                    'studentGraduationDate': editedGraduationDate,
                    'studentTermMarks': editedTermMarks
                },
                success: function () {
                    $("#saveEducationInformation").animate({
                        backgroundColor: '#5cb85c',
                        borderColor: '#4cae4c'
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
                },
                error: function () {
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
                    'studentId': fillBasic.studentId,
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

    return {
        init : init
    };


});