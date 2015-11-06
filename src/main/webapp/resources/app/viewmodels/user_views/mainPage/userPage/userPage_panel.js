

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
    /*
     function easytabs(tabToSelect) {
     var select = "";
     if (tabToSelect !== undefined) {
     select = 'select', '#' + tabToSelect;
     }
     return   $('#tab-container-userInner').easytabs({
     uiTabs: false,
     updateHash: false,
     tabs: "> div > div > ul > li"
     //,
     / * tabsClass: "ui-tabs-nav",
     tabClass: "",
     panelClass: "ui-tabs-panel",
     containerClass: ""  * /
     }, select);
     }
     
     */

    // var $tabContainer = $('#tab-container-userInner');

    /*
     function $easytabs(container,tabToSelect) {
     var select = "";
     if (tabToSelect !== undefined) {
     select = 'select', '#' + tabToSelect;
     }
     // var container = new $tabContainer();
     return   container.easytabs({
     uiTabs: false,
     updateHash: false,
     tabs: "> div > div > ul > li"
     //,
     / * tabsClass: "ui-tabs-nav",
     tabClass: "",
     panelClass: "ui-tabs-panel",
     containerClass: ""  * /
     });
     }
     */



    // var $tabContainer;

    //var deEasyTabs = 



    // var layoutV = new layout();


    var attached = function(view, parent) {
// $tabContainer = $('#tab-container-userInner');
//   easytabs();
//var v =  new $tabContainer;
// alert($tabContainer);
// alert(v);
// $easytabs =
        $('#tab-container-userInner').easytabs({
            uiTabs: false,
            updateHash: false,
            tabs: "> div > div > ul > li"
                    //,
                    /* tabsClass: "ui-tabs-nav",
                     tabClass: "",
                     panelClass: "ui-tabs-panel",
                     containerClass: ""  */
        });
        //tabs scroller.
        scroller();
        /*
         layoutV.setInputStyle();//TODO should it be at the bottom element?
         */
        //var labelemailNode = dom.byId('labelemail');
        // domConstruct.place('<span>' + labels.emailAddress + '</span>', dom.byId('labelemail'));
        dom.byId('searchValue').placeholder = labels.emailAddress;
        dom.byId('searchType').placeholder = labels.userName;
    };
    //var userSearchPanel = {
//var obj={};
//obj.persons = ko.observable().subscribeTo("USER_SEARCH_PANEL");
    // return  {
    // persons: ko.observable().subscribeTo("USER_SEARCH_PANEL")//,
    //profile: ko.observable().subscribeTo("USER_SEARCH_PANEL")
    // };
    //return obj;
    // };




    /*
     var newTab = function(userId) {
     var array = userTabsObjArr.users();
     console.log("all friends2: " + userTabsObjArr.users());
     
     var newArr = [{id: userId, userName: 'userName', closable: ko.observable(true), userProfilePanel: ko.observable().subscribeTo("USER_PROFILE_PANEL")}];
     //var arr = JSON.parse(array); 
     // userTabsObjArr.push(arr);
     //var array = userTabsObjArr();
     ko.utils.arrayPushAll(array, newArr);
     userTabsObjArr.users.valueHasMutated();
     
     $.each(userTabsObjArr.users(), function(id, value) {
     console.log("all 1:" + id + " : " + value);
     $.each(value, function(id, value) {
     console.log("all 1:" + id + " : " + value);
     
     });
     });
     alert("all friends: " + userTabsObjArr.users);
     console.log("all friends: " + userTabsObjArr.users);
     
     
     //{id: 'userId1', userName: 'userName', closable: ko.observable(true), userProfilePanel: ko.observable().subscribeTo("USER_PROFILE_PANEL")},
     //{id: 'userId', userName: 3, closable: ko.observable(true), userProfilePanel: 'viewmodels/user_views/mainPage/userPage/userProfileWidget/userProfileWidget-'}
     //];
     };
     
     */
    // var userTabs = ko.observableArray(userTabsObjArr);

    var addTab1 = function() {
// return ko.postbox.subscribe('OPEN_USER_PROFILE_PANEL', function(newValue) {
//   alert("Value: " + newValue);
//   newTab(newValue);
//});
    };
    return {
//activate: activate,
        attached: attached,
        searchValue: ko.observable(),
        searchType: ko.observable(),
        userTabs: userTabsObjArr(),
        //  userProfilePanel: ko.observable().subscribeTo('LOAD_USER_PROFILE_PANEL'),
        userTabsTemplate: function(tab) {
            return tab.closable() ? "closable" : "unclosable";
        },
        userTabsTemplate_ul: function(tab) {
            return tab.closable() ? "closable_ul" : "unclosable_ul";
        },
        //addTab1: ko.postbox.subscribe('OPEN_USER_PROFILE_PANEL', function(newValue) {
        //   alert("Value: " + newValue);
        ///  newTab(newValue);
        //}),
        //userSearchPanel: ko.observable().subscribeTo("USER_SEARCH_PANEL"),
        onSearch: function() {
            doValidation();
            var jData = {
                searchValue: this.searchValue(),
                searchType: this.searchType()
            };
            var url = '/tuUsersSearchServlet';
            //validate data first.
            if ($("#userSearchform").valid()) {

//$("#uSearchOutput").html('');

// $("#userSearchBusy").removeClass('ui-helper-hidden');
//     ko.observable('viewmodels/task/busyWidget').publishOn('USER_SEARCH_PANEL');
// var _busy = busy.userSearchBusy(); //getBusyOverlay(document.getElementById('userSearchBusy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 32, type: 'o'});
//ko.observable().publishOn('USER_PROFILE_PANEL');

//post data.
                security.searchUser(url, jData);
                // $("#userSearchBusy").addClass('ui-helper-hidden');

            } else {
// Display an error toast, without a title
                logger.log({
                    message: "Please fill the Form correctly and accept our Terms and Policy.",
                    showToast: true,
                    type: "error"
                });
            }
        },
        userClose: function(data, event) {
            ko.postbox.publish('REMOVE_USER_PROFILE_PANEL', $(event.currentTarget).prev().attr('href'));
        }

    };
    function userTabsObjArr() {

        var self = ko.observableArray([{id: 'uSearchOutput', userName: 'Persons', closable: ko.observable(false), userSearchPanel: ko.observable().subscribeTo("USER_SEARCH_PANEL")
            }]);
        ko.postbox.subscribe('OPEN_USER_PROFILE_PANEL', function(userId) {
            var userData = getUserFromAllSearch(userId);
            var array = self();
            if (!checkUserExistInArr(array, userId)) {
                //  var userProfilePanel = 'userProfilePanel_'+userId;
                // var obj = {};
                // obj.id = userId;
                // obj.userName = userData.usersName;
                //obj.closable = ko.observable(true);
                //obj['userProfilePanel'] = ko.observable('viewmodels/user_views/mainPage/userPage/userProfileWidget/userProfileWidget');

                var newArr = [{id: userId, userName: userData.usersName, closable: ko.observable(true)//, userProfilePanel: ko.observable().subscribeTo("LOAD_USER_PROFILE_PANEL")
                    }];
                // var newArr = [obj];

                ko.utils.arrayPushAll(array, newArr);
            }
            // self.valueHasMutated();
            self.refresh();
            var $tabContainer = $('#tab-container-userInner');
            $tabContainer.data('easytabs', myeasytabsObj($tabContainer));
            $tabContainer.easytabs('select', '#' + userId);
            //  alert('select#' + userId);
            //Load user profile panel.
            //ko.observable('viewmodels/user_views/mainPage/userPage/userProfileWidget/userProfileWidget').publishOn('LOAD_USER_PROFILE_PANEL');
            //ko.observable(userId).publishOn("USER");

            scroller();
        });
        ko.postbox.subscribe('REMOVE_USER_PROFILE_PANEL', function(tabhref) {
            var userId = tabhref.slice(1);
            // var userData = getUserFromAllSearch(userId);

            var array = self();
            //get the tab.
            var tab;
            $.each(array, function(id, value) {
                if (value.id === userId) {
                    tab = value;
                }
            });
            //alert(array.length);
            //gremove the tab.
            ko.utils.arrayRemoveItem(array, tab);
            // alert(array.length);
            self.refresh();
            var $tabContainer = $('#tab-container-userInner');
            $tabContainer.data('easytabs', myeasytabsObj($tabContainer));
            //TODO check position and know which to open.
            //$tabContainer.easytabs('select', '#' + userId);
            scroller();
        });
        return self;
    }

    function myeasytabsObj($tabContainer) {
        var myeasytabs = $tabContainer.data('easytabs');
// alert(  myeasytabs.settings.panelContext.find("#" ).not(myeasytabs.settings.panelActiveClass));
        myeasytabs.tabs.removeClass(myeasytabs.settings.tabActiveClass);
        myeasytabs.panels.removeClass(myeasytabs.settings.panelActiveClass);
        myeasytabs.getTabs();
        myeasytabs.init();
        // myeasytabs.publicMethods.select('#' + userId);
        return myeasytabs;
    }

    function getUserFromAllSearch(userId) {
        var userData = {},
                control;
        var userOneObjArr = session.userSearch(); //check on single user data store first.
        $.each(userOneObjArr, function(id, value) {
            if (userId.toString() === value.userId.toString()) { //convert, so that === will work well.
                userData.userId = value.userId;
                userData.usersName = value.usersName;
                control = 1;
            }
        });
        if (control === undefined) {//check on all user data store if not on all user store.
            var userAllObjArr = session.userAllSearch();
            $.each(userAllObjArr, function(id, value) {
               if (userId.toString() === value.userId.toString()) { //convert, so that === will work well.
                userData.userId = value.userId;
                userData.usersName = value.usersName;
            }
            });
        }
        return userData;
    }

    function checkUserExistInArr(array, userId) {
        var result = false;
        $.each(array, function(id, value) {
//  alert(userId.toString() + ' ' + value.id.toString());
            if (userId.toString() === value.id.toString()) { //convert, so that === will work well.
                result = true;
            }
        });
        return result;
    }


    function scroller() {
        var firstIdx = 0;
        var container = $('.scroll-container-userInner').find('ul li');
        var num = container.length;
        $(".scroll-left-userInner").on("click", function(e) {
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
        $(".scroll-right-userInner").on("click", function(e) {
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
    }





});

