define(['dojo/i18n!app/nls/labels',
    'dojo/dom', 'dojo/dom-construct',
    'durandal/app', 'plugins/router',
    'global/services/layout',
    'global/session', 'global/services/security',
    'global/services/logger'],
        function(labels, dom, domConstruct, app, router, layout, session, security, logger
                ) {
            "use strict";
//'wijmo.wijmenu'
            //var start = function() {
            $(document).ready(function() {

//no DoubleClick. not used yet.
                $('.noDoubleClick').dblclick(function(e) {
                    e.preventDefault();
                });



            });
            // validate loginForm form on keyup and submit
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
                            //$(error.element).attr("title", error.message).addClass("tooltip").addClass("error");
                            $(error.element).attr("title", error.message).addClass("error");
                        });
                        if (focussed && $(focussed).is("input, textarea")) {
                            $(this.currentForm).tooltip("open", {target: focussed});
                        }
                    }
                });
                // use custom tooltip; 
                $("#loginForm").tooltip({
                    position: {
                        my: "left top+5",
                        at: "left bottom"
                    },
                    show: {
                        effect: "slideDown",
                        delay: 250
                    }, tooltipClass: "ui-tooltip-tuShop"
                });
                $("#loginForm").validate({
                    errorClass: "error",
                    validClass: "valid",
                    rules: {
                        'emailLogin': {
                            required: true
                                    //,
                                    // email: true
                        },
                        'passwordLogin': {
                            required: true
                                    //,
                                    // minlength: 6
                        }
                    },
                    messages: {
                        'emailLogin': {
//  "email": "Please enter a valid Email Address.",
                            "required": "Please enter your email address."
                        },
                        'passwordLogin': "Please enter your password."
                    }
                });
            };


            var layoutV = new layout();

            var attached = function(view, paren) {
                var optionStore = {};
                optionStore.slideMenu = {
                    orientation: "vertical",
                    mode: "sliding"
                };
                optionStore.toolbarMenu = {
                    orientation: "horizontal",
                    trigger: ".wijmo-wijmenu-item",
                    triggerEvent: "mouseenter"
                };
                var animationStore = {};
                animationStore.toolbarMenu = {
                    animated: "slide",
                    duration: 400,
                    easing: null
                };
                //Menu
                //$("#mainmenu").wijmenu();
                //$("#mainmenu").wijmenu(optionStore.slideMenu);
                //$("#mainmenu").wijmenu(optionStore.toolbarMenu);
                //Our Menu
                $("#flyoutmenu_1").wijmenu(optionStore.toolbarMenu);
                $("#flyoutmenu_1").removeClass("ui-helper-hidden-accessible");
                $("#flyoutmenu_2").wijmenu(optionStore.toolbarMenu);
                $("#flyoutmenu_2").removeClass("ui-helper-hidden-accessible");



                //var labelemailNode = dom.byId('labelemail');
                // domConstruct.place('<span>' + labels.emailAddress + '</span>', dom.byId('labelemail'));
                dom.byId('home').innerHTML = labels.home;
                dom.byId('joinTodayMenu').innerHTML = labels.joinTodayMenu;
                dom.byId('labelEmailLogin').innerHTML = labels.emailLogin;
                dom.byId('labelpasswordLogin').innerHTML = labels.passLogin;
                dom.byId('signInMenu').innerHTML = labels.signIn;
                dom.byId('home2').innerHTML = labels.home;
                dom.byId('showMenu').innerHTML = labels.showMenu;



                // layoutV.setInputStyle();// input.bgColour does not work . WHY? TODO should it be at the bottom element?



                //hide label text and change input value to label text.
                $('label.inputWithLabel').each(function() {
                    var label = $(this);
                    var input = $('#' + label.attr('for'));

                    var initial = label.hide().text().replace(':', '');
                    // var placeholder = input.atr('placeholder',initial);
                    input.focus(function() {
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
                        // input.addClass('inputOnmouseOver'); 
                        // TODO check what is wrong here. 
                        // class inputOnmouseOver is not seem.
                        //alert(input.attr('class'));
                        input.css('background-color', '#ffffcc');
                    }).mouseout(function() {
                        input.css('background-color', '#fff');
                    });
                });

                //No cache for input field.
                $('input.noCache').each(function() {
                    $(this).val('');
                });



            };





            var activate = function() {

                //loggedInHandler(this);
                var self = this;
                //  self.showMenuLogin(ko.observable(false).subscribeTo('IS_SHOWMENU'))  ;
            };


            //Inject toastrOptions.
            //var toastrService = new toastrOptions();
            // toastrService.getOptions();


            var loggedInHandler = function(self) {
                // app.on('authentication.loggedIn').then(function(data) {

                //  self.showMenuLogin(data.showMenuLogin);
                // });
            };


            return {
                attached: attached,
                activate: activate,
                showMenu: ko.observable(true).subscribeTo('SHOWMENU'),
                showMenuNot: ko.observable(false).subscribeTo('SHOWMENU_NOT'),
                usersEmailAddressMenu: ko.observable(),
                usersPasswordMenu: ko.observable(),
                onSubmitMenu: function() {
                    doValidation();
                    var jData = {
                        usersEmailAddressMenu: this.usersEmailAddressMenu(),
                        usersPasswordMenu: this.usersPasswordMenu()
                    };
                    var loginUrl = '/tuLogInOutServlet';
                    //validate data first.
                    if ($("#loginForm").valid()) {
                        var busy = getBusyOverlay(document.getElementById('menuBusy'), {color: '#000', opacity: 0.9, text: '', style: 'text-decoration:blink;font-weight:bold;font-size:12px;color:white'}, {color: '#fff', size: 20, type: 'o'});
                        //post data.
                        security.login(loginUrl, jData, busy);
                    } else {
                        // Display an error toast, with a title
                        logger.log({
                            message: "Please enter your E-mail Address and Password.",
                            showToast: true,
                            type: "error"
                        });
                    }
                }
            };
        });