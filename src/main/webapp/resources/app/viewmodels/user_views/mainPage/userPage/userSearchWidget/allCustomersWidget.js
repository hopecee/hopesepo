

define(['dojo/i18n!app/nls/constants', 'dojo/i18n!app/nls/labels',
    'dojo/dom', 'dojo/dom-construct', 'dijit/layout/TabContainer', 'dijit/layout/ContentPane',
    'plugins/router', 'global/services/session',
    'global/services/security',
    'global/services/layout',
    'global/services/logger'], function(constants, labels,
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
         layoutV.setInputStyle();//TODO should it be at the bottom element?
         */
        //var labelemailNode = dom.byId('labelemail');
        // domConstruct.place('<span>' + labels.emailAddress + '</span>', dom.byId('labelemail'));
        // dom.byId('searchValue').placeholder = labels.emailAddress;
        //dom.byId('searchType').placeholder = labels.userName;

        // dom.byId('labelTuShopTerms').innerHTML = labels.tuShopTerms;

        /*
         $('#allCustomersWidget').on('dblclick',  function (event) {
         
         $.each(event, function(id, value) {
         alert(id+ " : " +value);
         });
         
         var myViewModelFragment = ko.dataFor(this);
         $.each(myViewModelFragment, function(id, value) {
         alert(id+ " : " +value);
         });
         
         });
         */
    };



    var userSearchDataArr = function() {
        var userDataArr = [],
                userData = {},
                userDataObjArr = session.userAllSearch();

        var userId, usersName, usersLastname, usersFirstname, usersFullname;

        $.each(userDataObjArr, function(id, value) {
            userId = value.userId;
            usersName = value.usersName;
            usersLastname = value.usersLastname;
            usersFirstname = value.usersFirstname;
            usersFullname = usersLastname + " " + usersFirstname;

            userData = {}; //Clear First. 
            userData.userId = userId;
            userData.img = constants.userImgFolder + userId + '/' + userId + constants.extJPG;
            userData.usersName = usersName;
            userData.usersFullname = usersFullname;

            userDataArr.push(userData);
        });

        return userDataArr;

    };


    return {
//activate: activate,
        attached: attached,
        //userIdSearch: ko.observable('1'),
        userSearchDataArr: userSearchDataArr(),
        userdblclick: function(data, event) {
            ko.postbox.publish('OPEN_USER_PROFILE_PANEL', event.currentTarget.id);
        }
    };




});

