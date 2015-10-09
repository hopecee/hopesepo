

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


    };



    var userSearchDataArr = function() {
        var userDataArr = [],
                userData = {},
       userDataObjArr = session.userSearchArr();
/*
        userDataObjArr = [{
                userId: '1',
                usersName: 'usersName@hb.vom',
                usersLastname: 'usersLastname',
                usersFirstname: 'usersFirstname'

            }, {
                userId: '2',
                usersName: '2usersName@hb.vom',
                usersLastname: '2usersLastname',
                usersFirstname: '2usersFirstname'
            }, {userId: '3',
                usersName: '3usersName@hb.vom',
                usersLastname: '2usersLastname',
                usersFirstname: '2usersFirstname'}];
        */

        //alert(userDataObjArr.length);

        var userId, usersName, usersLastname, usersFirstname, usersFullname;
       // var count = 0;
        for (var i = 0; i < userDataObjArr.length; i++) {
            var uObj = userDataObjArr[i];
            $.each(uObj, function(id, value) {
                if (id === 'userId') {
                    userId = value;
                    // alert('userId : ' + userId);
                }
                if (id === 'usersName') {
                    usersName = value;
                    // alert('usersName : ' + usersName);
                }
                if (id === 'usersLastname') {
                    usersLastname = value;
                    // alert('usersName : ' + usersLastname);
                }
                if (id === 'usersFirstname') {
                    usersFirstname = value;
                    // alert('usersFirstname : ' + usersFirstname);
                }
                usersFullname = usersLastname + " " + usersFirstname;
                // alert('userId' + " :1 " + userId + " :2 " + 'usersLastname' + " :3 " + usersLastname);

                // var userId = userDataObj[id];


            });
            userData = {}; //Clear First.  
            userData.img = constants.userImgFolder + userId + '/' + userId + constants.extJPG;
            userData.usersName = usersName;
            userData.usersFullname = usersFullname;

          //  alert('img : ' + userData.img + ' usersName : ' + userData.usersName + ' usersName : ' + userData.usersFullname);

            userDataArr.push(userData);
            //count++;

        }

        //alert(count);
        return userDataArr;
        /*
         var userId = userDataObj.userId,
         usersName = userDataObj.usersName,
         usersLastname = " ",
         usersFirstname = " ";
         if (userDataObj.usersLastname !== undefined) {
         usersLastname = userDataObj.usersLastname;
         }
         if (userDataObj.usersFirstname !== undefined) {
         usersFirstname = userDataObj.usersFirstname;
         }
         var usersFullname = usersLastname + " " + usersFirstname;
         alert(userDataObj.userId + " : " + userDataObj.name + " : " + usersName + " : " + usersFullname);
         //TODO you can get more data.
         userData = {}; //Clear First.  
         userData['img'] = constants.userImgFolder + userId + '/' + userId + constants.extJPG;
         userData['usersName'] = usersName;
         userData['usersFullname'] = usersFullname;
         
         */
        //add to the array.
        // userDataArr.push(userData);

        /* 
         }
         
         
         for (var i = 0; i < userDataArr.length; i++) {
         var map = userDataArr[i];
         
         //alert(userDataArr[i]);
         
         $.each(map, function(id, value) {
         
         // alert(id + " " +value);
         
         
         });
         
         }
         ;
         */







    };


    return {
//activate: activate,
        attached: attached,
        //userIdSearch: ko.observable('1'),
        userSearchDataArr: userSearchDataArr()
    };




});

