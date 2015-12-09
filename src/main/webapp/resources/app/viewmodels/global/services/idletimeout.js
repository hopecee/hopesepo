define(['global/services/session', 'global/services/functionz'],
        function(session, fxz) {
            "use strict";


            var store = {
                enabled: function() {
                    return true;
                },
                set: function(name, value) {
                    session[name] = function() {
                        return value;
                    };
                },
                get: function(name) {
                    return  session[name]();
                }
            };

            var init = function(userRuntimeConfig) {
                var subscriptions = [];
                //================================
                // Public Configuration Variables
                //=================================
                var defaultConfig = {
                    // redirectUrl: '/logout',//not needed now // redirect to this url on logout. Set to "redirectUrl: false" to disable redirect

                    // idle settings
                    idleTimeLimit: 600, // 'No activity' time limit in seconds. 1200 = 20 Minutes
                    idleCheckHeartbeat: 2, // Frequency to check for idle timeouts in seconds

                    // optional custom callback to perform before logout
                    customCallback: false, // set to false for no customCallback
                    // customCallback:    function () {    // define optional custom js function
                    // perform custom action before logout
                    // },

                    // configure which activity events to detect
                    // http://www.quirksmode.org/dom/events/
                    // https://developer.mozilla.org/en-US/docs/Web/Reference/Events
                    activityEvents: 'click.idletimeout keypress.idletimeout scroll.idletimeout wheel.idletimeout mousewheel.idletimeout mousemove.idletimeout', //qualified by event namespaces separate each event with a space

                    // warning dialog box configuration
                    enableDialog: true, // set to false for logout without warning dialog
                    dialogDisplayLimit: 182, // Time to display the warning dialog before logout (and optional callback) in seconds. 180 = 3 Minutes
                    dialogTitle: 'Session Expiration Warning', // also displays on browser title bar
                    dialogText: 'Because you have been inactive, your session is about to expire.',
                    dialogTimeRemaining: 'Time remaining',
                    dialogStayLoggedInButton: 'Yes',
                    dialogLogOutNowButton: 'No',
                    dialogStayLoggedInText: 'Do you want to Stay Logged In',
                    // error message if https://github.com/marcuswestin/store.js not enabled
                    errorAlertMessage: 'Please disable "Private Mode", or upgrade to a modern browser. Or perhaps a dependent file missing. Please see: https://github.com/marcuswestin/store.js',
                    // server-side session keep-alive timer
                    sessionKeepAliveTimer: 300 // ping the server at this interval in seconds. 600 = 10 Minutes. Set to false to disable pings
                            //sessionKeepAliveUrl: window.location.href // no need for this now// set URL to ping - does not apply if sessionKeepAliveTimer: false
                },
                //===============================
                //## Private Variables
                //===============================
                currentConfig = $.extend(defaultConfig, userRuntimeConfig), // merge default and user runtime configuration
                        origTitle = document.title, // save original browser title
                        activityDetector, stopActivityDetector,
                        startKeepSessionAlive, stopKeepSessionAlive, keepSession, keepAlivePing, // session keep alive
                        idleTimer, remainingTimer, checkIdleTimeout, checkIdleTimeoutLoop, startIdleTimer, stopIdleTimer, // idle timer
                        openWarningDialog, dialogTimer, checkDialogTimeout, startDialogTimer, stopDialogTimer, isDialogOpen, destroyWarningDialogAndOn,
                        destroyWarningDialogAndOff, countdownDisplay, // warning dialog
                        logoutUser;
                //
                //==============================
                //## Public Functions
                //==============================
                // trigger a manual user logout
                // use this code snippet on your site's Logout button: $.fn.idleTimeout().logout();
                var logout = function() {
                    store.set('idleTimerLoggedOut', true);
                };
                //=============================
                //## Private Functions
                //============================

                //----------- KEEP SESSION ALIVE FUNCTIONS --------------//
                startKeepSessionAlive = function() {
                    var keepSession = function() {
                        ko.postbox.publish('KEEP_SESSION_ALIVE', null);
                        startKeepSessionAlive();
                    };

                    keepAlivePing = setTimeout(keepSession, (currentConfig.sessionKeepAliveTimer * 1000));
                };

                stopKeepSessionAlive = function() {
                    clearTimeout(keepAlivePing);
                };

                //----------- ACTIVITY DETECTION FUNCTION --------------//
                activityDetector = function() {
                    $('body').on(currentConfig.activityEvents, function() {
                        if (!currentConfig.enableDialog || (currentConfig.enableDialog && isDialogOpen() !== true)) {
                            startIdleTimer();
                        }
                    });
                };

                stopActivityDetector = function() {
                    $('body').off(currentConfig.activityEvents);
                };
                //----------- IDLE TIMER FUNCTIONS --------------//
                checkIdleTimeout = function() {
                    var timeIdleTimeout = (store.get('idleTimerLastActivity') + (currentConfig.idleTimeLimit * 1000));

                    if ($.now() > timeIdleTimeout) {

                        if (!currentConfig.enableDialog) { // warning dialog is disabled
                            logoutUser(); // immediately log out user when user is idle for idleTimeLimit
                        } else if (currentConfig.enableDialog && isDialogOpen() !== true) {
                            openWarningDialog();
                            startDialogTimer(); // start timing the warning dialog
                        }
                    } else if (store.get('idleTimerLoggedOut') === true) { //a 'manual' user logout?
                        logoutUser();
                    } else {

                        if (currentConfig.enableDialog && isDialogOpen() === true) {
                            destroyWarningDialogAndOff();
                            stopDialogTimer();
                        }
                    }
                };

                startIdleTimer = function() {
                    stopIdleTimer();
                    store.set('idleTimerLastActivity', $.now());
                    checkIdleTimeoutLoop();
                };

                checkIdleTimeoutLoop = function() {
                    checkIdleTimeout();
                    idleTimer = setTimeout(checkIdleTimeoutLoop, (currentConfig.idleCheckHeartbeat * 1000));
                };

                stopIdleTimer = function() {
                    clearTimeout(idleTimer);
                };

                //----------- WARNING DIALOG FUNCTIONS --------------//
                openWarningDialog = function() {
                    var dialogContent = "<div id='idletimer_warning_dialog'><p>" + currentConfig.dialogText + "</p><p style='display:inline'>" + currentConfig.dialogTimeRemaining + ": <div style='display:inline;font-weight: bold;' id='countdownDisplay'></div></p><p>" + currentConfig.dialogStayLoggedInText + "</p></div>";

                    $(dialogContent).dialog({
                        buttons: [{
                                text: currentConfig.dialogStayLoggedInButton,
                                click: function() {
                                    destroyWarningDialogAndOn();
                                    stopDialogTimer();
                                    startIdleTimer();
                                }
                            },
                            {
                                text: currentConfig.dialogLogOutNowButton,
                                click: function() {
                                    destroyWarningDialogAndOff();
                                    stopDialogTimer();
                                    logoutUser();
                                }
                            }
                        ],
                        closeOnEscape: false,
                        modal: true,
                        title: currentConfig.dialogTitle,
                        position: {my: "center", at: "top ", of: "body"},
                        open: function() {
                            $(this).closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
                        }
                    });

                    countdownDisplay();

                    document.title = currentConfig.dialogTitle;

                    if (currentConfig.sessionKeepAliveTimer) {
                        stopKeepSessionAlive();
                    }
                };

                checkDialogTimeout = function() {
                    var timeDialogTimeout = (store.get('idleTimerLastActivity') + (currentConfig.idleTimeLimit * 1000) + (currentConfig.dialogDisplayLimit * 1000));

                    if (($.now() > timeDialogTimeout) || (store.get('idleTimerLoggedOut') === true)) {
                        logoutUser();
                    }
                };

                startDialogTimer = function() {
                    dialogTimer = setInterval(checkDialogTimeout, (currentConfig.idleCheckHeartbeat * 1000));
                };

                stopDialogTimer = function() {
                    clearInterval(dialogTimer);
                    clearInterval(remainingTimer);
                };

                isDialogOpen = function() {
                    var dialogOpen = $("#idletimer_warning_dialog").is(":visible");
                    if (dialogOpen === true) {
                        return true;
                    }
                    return false;
                };

                destroyWarningDialogAndOff = function() {
                    $("#idletimer_warning_dialog").dialog('destroy').remove();
                    document.title = origTitle;
                };
                destroyWarningDialogAndOn = function() {
                    $("#idletimer_warning_dialog").dialog('destroy').remove();
                    document.title = origTitle;
                    if (currentConfig.sessionKeepAliveTimer) {
                        startKeepSessionAlive();
                    }
                };
                countdownDisplay = function() {
                    var dialogDisplaySeconds = currentConfig.dialogDisplayLimit, mins, secs;
                    var correction = 2;// zero correction.

                    remainingTimer = setInterval(function() {
                        mins = Math.floor(dialogDisplaySeconds / 60); // minutes
                        if (mins < 10) {
                            mins = '0' + mins;
                        }
                        secs = (dialogDisplaySeconds - (mins * 60)) - correction; // seconds
                        if (secs < 10) {
                            secs = '0' + secs;
                        }
                        $('#countdownDisplay').html(mins + ':' + secs);
                        dialogDisplaySeconds -= 1;
                    }, 1000);
                };

                //----------- LOGOUT USER FUNCTION --------------//
                logoutUser = function() {
                    ko.postbox.publish('USER_LOGOUT', "idletimeout");
                };

                //===================================
                // Build & Return the instance of the item as a plugin
                // This is your construct.
                //===================================
                var start = function() {
                    if (store.enabled()) {
                        store.set('idleTimerLastActivity', $.now());
                        store.set('idleTimerLoggedOut', false);

                        activityDetector();

                        if (currentConfig.sessionKeepAliveTimer) {
                            startKeepSessionAlive();
                        }

                        startIdleTimer();

                    } else {
                        alert(currentConfig.errorAlertMessage);
                    }
                };

                var stop = function() {
                    stopIdleTimer();
                    stopDialogTimer();
                    stopActivityDetector();

                    if (isDialogOpen() === true) {
                        destroyWarningDialogAndOff();
                    }
                    if (currentConfig.sessionKeepAliveTimer) {
                        stopKeepSessionAlive();
                    }
                    if (currentConfig.customCallback) {
                        currentConfig.customCallback();
                    }
                    store.set('idleTimerLoggedOut', true);
                    store.set('idleTimerLastActivity', $.now());
                };

                //unsubscribe them first if any, by disposing them.
                fxz.clearSubscriptions(subscriptions);

                subscriptions.push(ko.postbox.subscribe('START_IDLE_TIMEOUT', function(data) {
                    start();
                }));
                subscriptions.push(ko.postbox.subscribe('STOP_IDLE_TIMEOUT', function(data) {
                    stop();
                }));

            };

            var idletimeout = {
                init: init
            };

            return idletimeout;

        });
