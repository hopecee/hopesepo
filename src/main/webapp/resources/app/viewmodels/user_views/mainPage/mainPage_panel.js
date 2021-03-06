
define(['durandal/app','dojo/i18n!app/nls/labels',
    'dojo/dom', 'dojo/dom-construct', 'dijit/layout/TabContainer', 'dijit/layout/ContentPane',
    'plugins/router', 'global/services/session',
    'global/services/security',
    'global/services/layout',
    'global/services/logger'], function(app,labels,
        dom, domConstruct, TabContainer, ContentPane,
        router, session, security,
        layout,
        logger) {
    "use strict";
    /* 
     $(document).ready(function() {
     //  $("*").css("color","red");
     // $("#joinEditorButton").addClass("error");
     // alert("#email1");
     // var doCalendar = function() {
     //    $('#calendar2').attr("style", "background-color: red").click(function () {
     
     // });
     // };
     
     / *    
     $("#calendar").wijcalendar({
     popupMode: true,
     selectedDatesChanged: function () {
     var selDate = $(this).wijcalendar("getSelectedDate");
     if (selDate) {
     $("#email1").val(selDate.toDateString());
     }
     }
     });
     
     $('#email1').click(function () {
     alert("#email1");
     
     
     $("#calendar").wijcalendar("popup", {
     of: $("#email1"),
     offset: '0 2'
     });
     });
     * /
     });
     
     */

    //Inject toastrOptions.
//var toastrService = new toastrOptions();
//toastrService.getOptions();






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
// $(error.element).attr("title", error.message).addClass("tooltip").addClass("error");
                    $(error.element).attr("title", error.message).addClass("error").removeClass("ui-tooltip");
                });
                if (focussed && $(focussed).is("input, textarea")) {
                    $(this.currentForm).tooltip("open", {target: focussed});
                }
            }
        });
        // use custom tooltip; 
        $("#joinEditorform").tooltip({
            position: {
                my: "left top+5",
                at: "left bottom"
            },
            show: {
                effect: "slideDown",
                delay: 250
            }, tooltipClass: "ui-tooltip-tuShop"
        });
        $("#joinEditorform").validate({
            errorClass: "error",
            validClass: "valid",
            rules: {
                'email': {
                    required: true,
                    email: true
                },
                'usersName': {
                    required: true//,
                            // email: true
                },
                'firstName': {
                    required: true
                },
                'lastName': {
                    required: true
                },
                'password': {
                    required: true,
                    minlength: 6
                },
                'confirm': {
                    required: true,
                    minlength: 6,
                    equalTo: '#password'
                },
                'agree': {
                    required: true  /* TO DO not working properly */
                }
            },
            messages: {
                'email': {
                    "email": "Please enter a valid Email Address.",
                    "required": "Please enter your email address."
                },
                'agree': "Please accept our terms and policy."
            }
        });
    };

    // var layoutV = new layout();


    var attached = function(view, paren) {

        //$(function() {
        // $("#tabs").tabs();
        // });
        $('#tab-container').easytabs({
            uiTabs: false,
            updateHash: false,
            tabs: "> div > div > ul > li"
                    //,
                    /* tabsClass: "ui-tabs-nav",
                     tabClass: "",
                     panelClass: "ui-tabs-panel",
                     containerClass: ""  */
        });


        var firstIdx = 0;
        var container = $('.scroll-container').find('ul li');
        var num = container.length;
        //alert(num);
//$(function() {
        $(".scroll-left").on("click", function(e) {

//alert(firstIdx);
            if (firstIdx > 0) {
                container.eq(firstIdx - 1).show();

                firstIdx--;
            }
            /*
             var position = container.position();
             container.animate({
             left: position.left - 100
             }, 1000);
             */


        });

        $(".scroll-right").on("click", function(e) {
            //var container = $('.scroll-container').find('ul li');



// alert(firstIdx);
            if (firstIdx < (num - 1)) {
                container.eq(firstIdx).hide();
                firstIdx++;
            }
            // container.data('current-child');
            // container.find('li:lt(3)').show();
            //alert(container.eq(1).hide());


            /*
             var position = container.position();
             container.animate({
             left: position.left + 100
             }, 1000);
             */

        });


        //return false;


        /*/ Clears animation queue and jumps to the last would-be position
         container.stop(true, true);
         alert(container.data('current-child'));
         // Retrieve the current child, set the data if not set already
         var nCurrentChild = container.data('current-child');
         if (nCurrentChild == null) {
         container.data('current-child', 0);
         nCurrentChild = 0;
         }
         
         // Jump to top if last child, otherwise bump by the next child's offset
         if (nCurrentChild == container.children().length - 1) {
         container.animate(
         { 
         
         // { top: 0 },
         // 300
         //);
         
         // container.data('current-child', 0);
         
         scrollLeft: container.data('current-child', 0).offset().left
         }, 600);
         currentIdx++;
         
         
         } else {
         var jNextChild = $(container.children()[nCurrentChild + 1]);
         var nDiff = jNextChild.offset().top - container.offset().top;
         
         container.animate(
         { top: -nDiff },
         300
         );
         container.data('current-child', nCurrentChild + 1);
         }
         
         */






//});

        /*
         var tabs;
         $(function() {
         tabs = $('.tabscontent').tabbedContent({loop: true}).data('api');
         
         // switch to tab...
         $('a[href=#click-to-switch]').on('click', function(e) {
         var tab = prompt('Tab to switch to (number or id)?');
         if (!tabs.switchTab(tab)) {
         alert('That tab does not exist :\\');
         }
         e.preventDefault();
         });
         
         // Next and prev actions
         $('.controls a').on('click', function(e) {
         var action = $(this).attr('href').replace('#', '');
         tabs[action]();
         e.preventDefault();
         });
         });
         */
        /*
         layoutV.setInputStyle();//TODO should it be at the bottom element?
         
         //var labelemailNode = dom.byId('labelemail');
         // domConstruct.place('<span>' + labels.emailAddress + '</span>', dom.byId('labelemail'));
         dom.byId('joinEditorHeader').innerHTML = labels.joinToday;
         dom.byId('email').placeholder = labels.emailAddress;
         dom.byId('usersName').placeholder = labels.userName;
         dom.byId('firstName').placeholder = labels.firstName;
         dom.byId('lastName').placeholder = labels.lastName;
         dom.byId('password').placeholder = labels.password;
         dom.byId('confirm').placeholder = labels.confirm;
         dom.byId('labelTuShopTerms').innerHTML = labels.tuShopTerms;
         dom.byId('joinEditorButtonJoinNow').innerHTML = labels.joinNow;
         dom.byId('labelAlreadyJoined').innerHTML = labels.alreadyJoined;
         dom.byId('labelSignIn').innerHTML = labels.signIn;
         
         */


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
        ///-------- 
        //if (window.navigator.language != "n") {       
        //doLangSelect(window.navigator.language); 
        //alert("window.navigator.language : "+window.navigator.language);
//alert("window.navigator.language : "+window.navigator.userLanguage);

//} 
// ready(function(){
//var dlg = dijit.byId("dialog");
//dom.byId('dialog').innerHTML = window.dojo.toJson(window.dojo.config, true);
// });

        //alert("window.navigator.language : "+window.dojoConfig.locale);

        //I ended up resorting to adding this to my testconfig.js:

//if (typeof window !== 'undefined') {
        // We're running inside a browser.
        // window.dojoConfig = window.dojoConfig || {};
        // window.dojoConfig.locale = window.dojoConfig.locale || "en-us";
//}
//====
    };



    return {
//activate: activate,
        attached: attached,
        usersEmailAddress: ko.observable(),
        usersName: ko.observable(),
        usersFirstname: ko.observable(),
        usersLastname: ko.observable(),
        usersPassword: ko.observable(),
        passwordConfirm: ko.observable(),
        agree: ko.observable(false), // initially false.  


        onSubmit: function() {

            doValidation();
            var jData = {
                usersEmailAddress: this.usersEmailAddress(),
                usersName: this.usersName(),
                usersFirstname: this.usersFirstname(),
                usersLastname: this.usersLastname(),
                usersPassword: this.usersPassword(),
                passwordConfirm: this.passwordConfirm(),
                agree: this.agree()
            };
            var url = '/tuJoinEditorServlet';

            //validate data first.
            if ($("#joinEditorform").valid()) {
                var busy = getBusyOverlay(document.getElementById('joinEditorBusy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 50, type: 'o'});

                // router.navigate('#/join_editor_3');


                // alert("surrrho====");
                //post data.
                security.joinEditor(url, jData, busy);


                /*
                 //post data.
                 $.ajax({
                 type: 'POST',
                 url: '/tuJoinEditorServlet',
                 data: jData,
                 // dataType: 'JSON',
                 success: function(data) {
                 busy.remove();
                 $.each(data, function(id, value) {
                 if (id === "BadToken") {
                 logger.log({
                 message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                 showToast: true,
                 type: "error"
                 });
                 } else {
                 
                 alert("submitted!==ooho");
                 router.navigate('#/join_editor_2');
                 // $(".reload").click(); // what is this?
                 
                 }
                 });
                 
                 
                 }
                 
                 });
                 */
                // return true;
            } else {
                // Display an error toast, without a title
                logger.log({
                    message: "Please fill the Form correctly and accept our Terms and Policy.",
                    showToast: true,
                    type: "error"
                });
                // return false;
            }


        }








    };
});

