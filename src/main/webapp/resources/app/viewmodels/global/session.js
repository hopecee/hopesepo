define(['plugins/router'//, //'services/security', 
            // 'knockout', 
            // 'jquery'
            //, 'jquery.utilities'
],
        function(router) {
            "use strict";

            var img = '/faces/javax.faces.resource/images/proshop32.png';

            function restoreSessionStorageFromLocalStorage() {
                var backupText = localStorage["sessionStorageBackup"],
                        backup;

                if (backupText) {
                    backup = JSON.parse(backupText);
                    for (var key in backup) {
                        sessionStorage[key] = backup[key];
                    }
                    localStorage.removeItem("sessionStorageBackup");
                }
            }
            function setAccessToken(accessToken, persistent) {
                if (persistent) {
                    localStorage["accessToken"] = accessToken;
                } else {
                    sessionStorage["accessToken"] = accessToken;
                }
            }
            function clearAccessToken() {
                localStorage.removeItem("accessToken");
                sessionStorage.removeItem("accessToken");
            }
            function init() {
                restoreSessionStorageFromLocalStorage();
            }
            init();
            var session = {
                userEmailAddress: ko.observable(undefined).publishOn('USER_EMAIL_ADDRESS'),
                userNeo4jIdString: ko.observable(undefined).publishOn('USER_NEO4J_ID_STR'),
                isLoggedIn: ko.observable(false).publishOn('IS_LOGGEDIN'),
                showMenu: ko.observable(true).publishOn('SHOWMENU'),
                showMenuNot: ko.observable(false).publishOn('SHOWMENU_NOT'),
                //headContainerClass: ko.observable().publishOn('HEADCONTAINER_CLASS'),
                headerHeight: ko.observable().publishOn('HEADER_HEIGHT'),
                isUserImg: ko.observable(false),
                userImgFolder: ko.observable().publishOn('USER_IMG'),
                isBusy: ko.observable(false),
                userRoles: ko.observableArray(),
                userIsInRole: userIsInRole,
                setUser: setUser,
                clearUser: clearUser,
                archiveSessionStorageToLocalStorage: archiveSessionStorageToLocalStorage,
                isAuthCallback: isAuthCallback,
                userRemembered: userRemembered,
                rememberedToken: rememberedToken
                        //==============================
                        // userphotoImg: ko.observable(undefined),
                        // setActiveEditor: setActiveEditor
            };
            return session;

            function setUser(user, remember) {
                if (user) {
                    session.userEmailAddress(user.emailAddress);
                    session.userNeo4jIdString(user.userNeo4jIdString);
                    session.userImgFolder(img);
                    session.isUserImg(user.isUserImg);

                    if (user.isLoggedIn) {
                        session.showMenu(false);
                        session.showMenuNot(true);

                        session.headerHeight($('#headContainer').height()); // second head class.
                    }
///
                    if (user.hasOwnProperty("accessToken")) {
                        alert("roles : 1");
                        setAccessToken(user.accessToken, remember);
                    } else if (user.hasOwnProperty("access_token")) {
                        alert("roles 2: ");
                        setAccessToken(user.access_token, remember);
                    }


                    // alert(user.userRoles());

                    /* 
                     var roles = user.userRoles.split(";");
                     $.each(roles, function(i, v) {
                     alert("roles : "+ v);
                     
                     session.userRoles.push(v);
                     });
                     */
                    $.each(user.userRoles(), function(i, v) {
                        //  alert(i + " roles : " + v);
                        session.userRoles.push(v);

                    });
                    //==
                    session.isLoggedIn(true);
                    // alert("roles=== : pop");
                }
            }
            function clearUser() {
                clearAccessToken();
                session.userEmailAddress('');
                session.userNeo4jIdString('');

                session.userRoles.removeAll();
                session.isLoggedIn(false);
            }
            function userIsInRole(requiredRole)
            {
                if (requiredRole === undefined) {
                    return true;
                } else if (session.userRoles() === undefined) {
                    return false;
                } else {
                    if ($.isArray(requiredRole)) {
                        if (requiredRole.length === 0) {
                            return true;
                        } else {
                            return $.arrayIntersect(session.userRoles(), requiredRole).length > 0;
                        }
                    } else {
                        return $.inArray(requiredRole, session.userRoles()) > -1;
                    }
                }
            }








            function isAuthCallback() {
                return sessionStorage["associatingExternalLogin"] || sessionStorage["externalLogin"];
            }
            function userRemembered() {
                return sessionStorage["accessToken"] !== undefined || localStorage["accessToken"] !== undefined;
            }
            function rememberedToken() {
                return sessionStorage["accessToken"] || localStorage["accessToken"];
            }
            function redirectCallback(redirectToManage) {
                if (redirectToManage) {
                    router.navigate('#/manage', 'replace');
                } else {
                    router.navigate('#/', 'replace');
                }
            }
            function archiveSessionStorageToLocalStorage() {
                var backup = {};
                for (var i = 0; i < sessionStorage.length; i++) {
                    backup[sessionStorage.key(i)] = sessionStorage[sessionStorage.key(i)];
                }
                localStorage["sessionStorageBackup"] = JSON.stringify(backup);
                sessionStorage.clear();
            }
        });

