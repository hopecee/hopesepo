

define(['dojo/i18n!app/nls/labels',
    'dojo/dom', 'dojo/dom-construct', 'dijit/layout/TabContainer', 'dijit/layout/ContentPane',
    'plugins/router', 'global/services/session',
    'global/services/security',
    'global/services/layout',
    'global/services/logger'], function(labels,
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
        $("#userSearchform").tooltip({
            position: {
                my: "left top+5",
                at: "left bottom"
            },
            show: {
                effect: "slideDown",
                delay: 250
            }, tooltipClass: "ui-tooltip-tuShop"
        });
        $("#userSearchform").validate({
            errorClass: "error",
            validClass: "valid",
            rules: {
                'searchValue': {
                    required: true
                },
                'searchType': {
                    required: true//,
                            // email: true
                }
            },
            messages: {
                'searchValue': {
                    "required": "Please enter your email address."
                },
                'searchType': {
                    "required": "Please accept our terms and policy."
                }
            }
        });
    };

    // var layoutV = new layout();


    var attached = function(view, paren) {
/*
        //$(function() {
        // $("#tabs").tabs();
        // });
        $('#tab-container').easytabs({
            uiTabs: false,
            updateHash: false,
            tabs: "> div > div > ul > li"
                    
        });


        var firstIdx = 0;
        var container = $('.scroll-container').find('ul li');
        var num = container.length;

        $(".scroll-left").on("click", function(e) {
            if (firstIdx > 0) {
                container.eq(firstIdx - 1).show();

                firstIdx--;
            }

        });

        $(".scroll-right").on("click", function(e) {

            if (firstIdx < (num - 1)) {
                container.eq(firstIdx).hide();
                firstIdx++;
            }

        });
*/


        /*
         layoutV.setInputStyle();//TODO should it be at the bottom element?
         */
        //var labelemailNode = dom.byId('labelemail');
        // domConstruct.place('<span>' + labels.emailAddress + '</span>', dom.byId('labelemail'));
        dom.byId('searchValue').placeholder = labels.emailAddress;
        dom.byId('searchType').placeholder = labels.userName;
};



    return {
//activate: activate,
        attached: attached,
        searchValue: ko.observable(),
        searchType: ko.observable(),
        userSearchPanel: ko.observable().subscribeTo("USER_SEARCH_PANEL"),
        
        onSearch: function() {
            doValidation();
            var jData = {
                searchValue: this.searchValue(),
                searchType: this.searchType()
            };
            var url = '/tuUsersSearchServlet';

            //validate data first.
            if ($("#userSearchform").valid()) {
                $("#userSearchBusy").removeClass('ui-helper-hidden');
                var busy = getBusyOverlay(document.getElementById('userSearchBusy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 32, type: 'o'});

                //post data.
                security.searchUser(url, jData, busy);
                // $("#userSearchBusy").addClass('ui-helper-hidden');

            } else {
                // Display an error toast, without a title
                logger.log({
                    message: "Please fill the Form correctly and accept our Terms and Policy.",
                    showToast: true,
                    type: "error"
                });
               
            }


        }








    };
});

