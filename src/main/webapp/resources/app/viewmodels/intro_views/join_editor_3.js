



define(['dojo/i18n!app/nls/labels',
    'dojo/dom', 'dojo/dom-construct',
    'durandal/app', 
    //'plugins/router',
    'global/services/security',
    'global/services/dataservice',
    'global/services/layout',
    'global/services/logger'],
        function(labels, dom, domConstruct,
                app, 
                //router,
                security,
                dataservice,
                layout,
                logger) {

            "use strict";
            $(document).ready(function() {

            });


            //Inject toastrOptions.
            //  var toastrService = new toastrOptions();
            //  toastrService.getOptions();

            // validate joinEditorform form on keyup and submit
            var doValidation = function() {
                $.validator.setDefaults({
                    submitHandler: function() {
                        // alert("submitted!");
                    },
                    showErrors: function(map, list) {
                        // there's probably a way to simplify this
                        var focussed = document.activeElement;
                        if (focussed && $(focussed).is("input, textarea")) {
                            $(this.currentForm).tooltip("close", {currentTarget: focussed}, true);
                        }

                        this.currentElements.removeAttr("title").removeClass("error");
                        $.each(list, function(index, error) {
                            $(error.element).attr("title", error.message).addClass("tooltip").addClass("error");

                        });
                        if (focussed && $(focussed).is("input, textarea")) {
                            $(this.currentForm).tooltip("open", {target: focussed});

                        }
                    }
                });


                // use custom tooltip; 
                $("#joinEditorSecurityform").tooltip({
                    position: {
                        my: "left top+5",
                        at: "left bottom"
                    },
                    show: {
                        effect: "slideDown",
                        delay: 250
                    }
                });



                $("#joinEditorSecurityform").validate({
                    errorClass: "error",
                    validClass: "valid",
                    rules: {
                        'dob': {
                            required: true,
                            minlength: 10,
                            maxlength: 10
                        },
                        'question1': {
                            required: true
                        },
                        'answer1': {
                            required: true
                        },
                        'question2': {
                            required: true
                        },
                        'answer2': {
                            required: true
                        },
                        'pin': {
                            required: true,
                            digits: true,
                            minlength: 4,
                            maxlength: 4
                        }
                    },
                    messages: {
                        'pin': {
                            'digits': "Please enter only Four digits.",
                            'minlength': "Please enter at least 4 digits.",
                            'maxlength': "Please enter no more than 4 digits."
                        }
                    }
                });
            };






            var dataserviceV = new dataservice();

            var activate = function() {
                dataserviceV.getAllQuestions(this);
                /*
                 app.trigger('authentication.loggedIn', {
                 firstTopPanel: "loggedIn",
                 showMenuLogin: false,
                 showHeader: true
                 }); 
                 */



            };



            var layoutV = new layout();

            var attached = function(view, paren) {
//function compositionComplete(view, parent) {
                //$(view).find('#calendar2').css("color", "red");
                //$('#calendar2').css("color", "red");

                /*
                 $("#joinEditorform3Calendar").wijcalendar({
                 popupMode: true,
                 selectedDatesChanged: function() {
                 var selDate = $(this).wijcalendar("getSelectedDate");
                 var selectDate = new Date(selDate);
                 // var _selDate = dateFormat(selDate, "mm/dd/yyyy");
                 if (!!selDate) {
                 // $("#dob").val(selDate.toDateString());
                 $("#dob").val(selectDate.getDate() + "/" + selectDate.getMonth() + 1  + "/" + selectDate.getFullYear());
                 }
                 }
                 });
                 */

                //var labelemailNode = dom.byId('labelemail');
                // domConstruct.place('<span>' + labels.emailAddress + '</span>', dom.byId('labelemail'));
                dom.byId('joinEditor3Greeting').innerHTML = labels.thanksForReg3;
                dom.byId('labelSecurityHeader').innerHTML = labels.userSecurityHeader;
                dom.byId('dob').placeholder = labels.userDob;
                dom.byId('labelSelectQuestions').innerHTML = labels.userSelectQuestions;
                //dom.byId('labelQuestion1').innerHTML = labels.userQuestion1;
                dom.byId('answer1').placeholder = labels.userAnswer1;
                //dom.byId('labelQuestion2').innerHTML = labels.userQuestion2;
                dom.byId('answer2').placeholder = labels.userAnswer2;
                dom.byId('labelEnterPin').innerHTML = labels.userEnterPin;
                dom.byId('pin').placeholder = labels.userPin;
                dom.byId('btnContinue2').innerHTML = labels.btnContinue2;



                layoutV.setInputStyle();//TODO should it be at the bottom element?

                /*
                 //hide label text and change input value to label text.
                 $('label.inputWithLabel').each(function() {
                 var label = $(this);
                 var input = $('#' + label.attr('for'));
                 var initial = label.hide().text().replace(':', '');
                 // var placeholder = input.atr('placeholder',initial);
                 input.focus(function() {
                 // alert(input.attr('class').match('error'));
                 // if(input.attr('class').match('error')=='error'){alert(input.css('background-color'));};
                 input.css('color', '#000');
                 input.removeAttr('placeholder');
                 if (input.val() === initial) {
                 input.val('');
                 }
                 }).blur(function() {
                 if (input.val() === '') {
                 input.attr('placeholder', initial).css('color', '#000');
                 // .attr('placeholder',labelText);
                 }
                 }).css('color', '#000').attr('placeholder', initial);
                 });
                 //input backgrand colour on mouseover and mouseout.
                 $('input.bgColour').each(function() {
                 var input = $(this);
                 input.mouseover(function() {
                 input.addClass('inputOnmouseOver');
                 }).mouseout(function() {
                 input.removeClass('inputOnmouseOver');
                 });
                 });
                 //No cache for input field.
                 $('input.noCache').each(function() {
                 $(this).val('');
                 });
                 
                 */





                $("#dob").click(function() {
                    /*
                     $("#joinEditorform3Calendar").wijcalendar("popup", {
                     of: $("#dob"),
                     offset: '0 2'
                     });
                     */
                    $("#dob").datepicker({
                        changeMonth: true,
                        changeYear: true,
                        showOtherMonths: true,
                        constrainInput: true,
                        dateFormat: "dd/mm/yy",
                        yearRange: "c-100:c"
                    });

                });

            };




            // var serviceAuthentication = new authentication();

            return {
                /// displayName: 'What is your name?',
                //firstTopPanel: ko.observable('Hello, world!'),
                // firstTopPanel2: ko.observable(),
                //activate: activate,

                attached: attached,
                activate: activate,
                //
                dob: ko.observable(),
                // question1: ko.observable(),
                questions1: ko.observableArray([]),
                optionsCaptionLabel1: ko.observable(labels.userOptionsCaptionLabel1),
                selectedQuestion1: ko.observable(),
                answer1: ko.observable(),
                questions2: ko.observableArray([]),
                optionsCaptionLabel2: ko.observable(labels.userOptionsCaptionLabel2),
                selectedQuestion2: ko.observable(),
                answer2: ko.observable(),
                pin: ko.observable(),
                onSubmit: function() {
                    // alert("submitted!" + $("#dob").val());
                    doValidation();
                    // alert($("#joinEditorform").valid());
                    var jData = {
                        // dob: this.dob(),
                        dob: $("#dob").val(), //TODO is it not // dob: this.dob(),
                        selectedQuestion1: this.selectedQuestion1(),
                        answer1: this.answer1(),
                        selectedQuestion2: this.selectedQuestion2(),
                        answer2: this.answer2(),
                        pin: this.pin()
                    }; // prepare request data

                    var url = '/tuJoinEditorServlet';

                    //validate data first.
                    if ($("#joinEditorSecurityform").valid()) {
                        var busy = getBusyOverlay(document.getElementById('joinEditorSecurityBusy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 50, type: 'o'});

                        security.joinEditor3(url, jData, busy);

                        /*
                         //post data.
                         $.ajax({
                         type: 'POST',
                         url: '/joinEditor3Servlet',
                         data: jData,
                         // dataType: 'JSON',
                         success: function() {
                         //alert("submitted!");
                         busy.remove();
                         // alert("submitted!");
                         
                         router.navigate('#/home');
                         
                         // $(".reload").click(); // what is this?
                         }
                         
                         });
                         */

                        // return true;
                    } else {
                        // Display an error toast, with a title
                        logger.log({
                            message: "Please fill the Form correctly and continue.",
                            showToast: true,
                            type: "error"
                        });
                    }


                }


            };
        });


