

define(['dojo/i18n!app/nls/labels', 'dojo/i18n!app/nls/userSearchTypes',
    'dojo/dom', 'dojo/dom-construct', 'dijit/layout/TabContainer', 'dijit/layout/ContentPane',
    'plugins/router', 'global/services/session',
    'global/services/security', 'global/services/dataservice',
    'global/services/layout',
    'global/services/logger', 'global/services/functionz'],
        function(labels, userSTypes, dom, domConstruct, TabContainer, ContentPane,
                router, session, security, dataservice,
                layout, logger, fxz) {
            "use strict";
            var subscriptions = [];
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








            function disableSearchInput() {
                //unsubscribe them first if any, by disposing them.
                fxz.clearSubscriptions(subscriptions);
                subscriptions.push(ko.postbox.subscribe('USER_SEARCH_TYPE', function(type) {
                    // var obj = self.typesObj()[type],
                    var defaultValue = labels.searchUser,
                            //The Types to disable.
                            cf = userSTypes.customerFriends,
                            sf = userSTypes.supplerFriends,
                            af = userSTypes.allFriends;
                    var $searchValue = $("#searchValue");
                    //|| obj === "supplerFriends" || obj === "allFriends"
                    if ((type === cf) || (type === sf) || (type === af)) {
                        $searchValue.val(''); //clear entry into the input first.
                        $searchValue.prop('disabled', true).attr('placeholder', type); //.val(type);
                    } else {
                        $searchValue.prop('disabled', false).attr('placeholder', defaultValue);
                    }
                }));
            }



            var activate = function() {
                var service = new dataservice();
                service.getAllSearchTypes(this);
                disableSearchInput();
            };
            var attached = function(view, parent) {
// var layoutV = new layout();
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
                dom.byId('searchValue').placeholder = labels.searchUser;
                //dom.byId('searchType').placeholder = labels.userName;

            };
            return {
                activate: activate,
                attached: attached,
                // subscriptions: subscriptions,
                searchValue: ko.observable(),
                selectType: ko.observable(labels.userSelectType),
                types: ko.observableArray([]),
                typesObj: ko.observableArray([]),
                selectedType: ko.observable().publishOn('USER_SEARCH_TYPE'),
                postedType: postedType,
                userTabs: userTabs(),
                userTabsTemplate: userTabsTemplate,
                userTabsTemplate_ul: userTabsTemplate_ul,
                onSearch: onSearch,
                userClose: userClose
            };

            function postedType() {
                var obj = {};
                $.each(this.typesObj(), function(data, value) {
                    obj[data] = value;
                });
                return obj[this.selectedType()];
            }

            function userTabs() {
                var subscrptns = [];//we could not get subscriptions, so we used this.
                var tabs = ko.observableArray([{id: 'uSearchOutput', userName: 'Persons', closable: ko.observable(false), userSearchPanel: ko.observable().subscribeTo("USER_SEARCH_PANEL")}]);
                //unsubscribe them first if any, by disposing them.
                fxz.clearSubscriptions(subscrptns);
                subscrptns.push(ko.postbox.subscribe('OPEN_USER_PROFILE_PANEL', function(userId) {
                    var userData = getUserFromAllSearch(userId);
                    var array = tabs();
                    if (!checkUserExistInArr(array, userId)) {
                        var newArr = [{id: userId, userName: userData.usersName, closable: ko.observable(true)}];
                        ko.utils.arrayPushAll(array, newArr);
                    }
                    // self.valueHasMutated();
                    tabs.refresh();
                    var $tabContainer = $('#tab-container-userInner');
                    $tabContainer.data('easytabs', myeasytabsObj($tabContainer));
                    $tabContainer.easytabs('select', '#' + userId);

                    scroller();
                }));
                subscrptns.push(ko.postbox.subscribe('REMOVE_USER_PROFILE_PANEL', function(tabhref) {
                    var userId = tabhref.slice(1);

                    var array = tabs();
                    //get the tab.
                    var tab;
                    $.each(array, function(id, value) {
                        if (value.id === userId) {
                            tab = value;
                        }
                    });
                    //remove the tab.
                    ko.utils.arrayRemoveItem(array, tab);
                    tabs.refresh();
                    var $tabContainer = $('#tab-container-userInner');
                    $tabContainer.data('easytabs', myeasytabsObj($tabContainer));
                    //TODO check position and know which to open.
                    //$tabContainer.easytabs('select', '#' + userId);
                    scroller();
                }));
                return tabs;
            }

            function userTabsTemplate(tab) {
                return tab.closable() ? "closable" : "unclosable";
            }

            function userTabsTemplate_ul(tab) {
                return tab.closable() ? "closable_ul" : "unclosable_ul";
            }

            function onSearch() {
                fxz.preventDbClick("onSearch");
                doValidation();
                var jData = {
                    searchValue: this.searchValue(),
                    searchType: this.postedType()
                };
                var url = '/tuUsersSearchServlet';
                //validate data first.
                if ($("#userSearchform").valid()) {
                    security.searchUser(url, jData);
                } else {
                    // Display an error toast, without a title
                    logger.log({
                        message: "Please fill the Form correctly and accept our Terms and Policy.",
                        showToast: true,
                        type: "error"
                    });
                }
            }

            function userClose(data, event) {
                ko.postbox.publish('REMOVE_USER_PROFILE_PANEL', $(event.currentTarget).prev().attr('href'));
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
                var userOneObj = session.userSearch(); //check on single user data store first.
                $.each(userOneObj, function(id, value) {
                    if (userId.toString() === value.userId.toString()) { //convert, so that === will work well.
                        userData.userId = value.userId;
                        userData.usersName = value.usersName;
                        control = 1;
                    }
                });
                if (control === undefined) {//check on all user data store if not on all user store.
                    var userAllObj = session.userAllSearch();
                    $.each(userAllObj, function(id, value) {
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

