define(["dojo/on", 'global/services/session', 'global/services/logger', 'global/services/stickyheaderSetter'],
        function(on, session, logger, stickyheaderSetter) {

            var router = new Router().init();

            var intro = 'intro',
                    home = 'home',
                    join_Editor_2 = 'join_editor_2',
                    join_Editor_3 = 'join_editor_3';

            /*
             // Routes
             var baseUrl = $.getBasePath(),
             addExternalLoginUrl = baseUrl + "api/Account/AddExternalLogin",
             changePasswordUrl = baseUrl + "api/Account/changePassword",
             loginUrl = baseUrl + "Token",
             logoutUrl = baseUrl + "api/Account/Logout",
             registerUrl = baseUrl + "api/Account/Register",
             registerExternalUrl = baseUrl + "api/Account/RegisterExternal",
             removeLoginUrl = baseUrl + "api/Account/RemoveLogin",
             setPasswordUrl = baseUrl + "api/Account/setPassword",
             siteUrl = baseUrl,
             userInfoUrl = "api/Account/UserInfo";
             
             
             */

            function addRequestVerificationToken(jData) {
                //Append RequestVerificationToken value to the jData before posting.
                jData['RequestVerificationToken'] = $('input[name="__RequestVerificationToken"]').val();
            }
            function setMethod(jData, method) {
                jData['method'] = method;
            }
            function setEmailAddress(jData, usersEmailAddress) {
                jData['usersEmailAddress'] = usersEmailAddress;
            }
            function setUserNeo4jIdString(jData, id) {
                jData['userNeo4jIdString'] = id;
            }



            // Route operations
            function externalLoginsUrl(returnUrl, generateState) {
                return baseUrl + "api/Account/ExternalLogins?returnUrl=" + (encodeURIComponent(returnUrl)) +
                        "&generateState=" + (generateState ? "true" : "false");
            }
            function manageInfoUrl(returnUrl, generateState) {
                return baseUrl + "api/Account/ManageInfo?returnUrl=" + (encodeURIComponent(returnUrl)) +
                        "&generateState=" + (generateState ? "true" : "false");
            }


// Other private operations
            function getSecurityHeaders() {
                var accessToken = sessionStorage["accessToken"] || localStorage["accessToken"];
                if (accessToken) {
                    return {"Authorization": "Bearer " + accessToken};
                }
                return {};
            }
            function toErrorString(data) {
                var errors, items;
                if (!data || !data.message) {
                    return null;
                }
                if (data.modelState) {
                    for (var key in data.modelState) {
                        items = data.modelState[key];
                        if (items.length) {
                            errors = items.join(",");
                        }
                    }
                }
                if (errors === undefined) {
                    errors = data.message;
                }
                return errors;
            }


            var securityService = {
                addExternalLogin: addExternalLogin,
                changePassword: changePassword,
                getExternalLogins: getExternalLogins,
                getManageInfo: getManageInfo,
                getUserInfo: getUserInfo,
                login: login,
                joinEditor: joinEditor,
                joinEditor2: joinEditor2,
                joinEditor3: joinEditor3,
                localeManager: localeManager,
                logout: logout,
                findUser: findUser,
                register: register,
                registerExternal: registerExternal,
                removeLogin: removeLogin,
                setPassword: setPassword,
                //returnUrl: siteUrl,
                toErrorString: toErrorString
            };


            $.ajaxPrefilter(function(options, originalOptions, jqXHR) {
                jqXHR.failJSON = function(callback) {
                    jqXHR.fail(function(jqXHR, textStatus, error) {
                        var data;
                        try {
                            data = $.parseJSON(jqXHR.responseText);
                        }
                        catch (e) {
                            data = null;
                        }
                        callback(data, textStatus, jqXHR);
                    });
                };
            });
            return securityService;



// Data access operations
            function addExternalLogin(data) {
                return $.ajax(addExternalLoginUrl, {
                    type: "POST",
                    data: data,
                    headers: getSecurityHeaders()
                });
            }
            function changePassword(data) {
                return $.ajax(changePasswordUrl, {
                    type: "POST",
                    data: data,
                    headers: getSecurityHeaders()
                });
            }
            function getExternalLogins(returnUrl, generateState) {
                return $.ajax(externalLoginsUrl(returnUrl, generateState), {
                    cache: false,
                    headers: getSecurityHeaders()
                });
            }
            function getManageInfo(returnUrl, generateState) {
                return $.ajax(manageInfoUrl(returnUrl, generateState), {
                    cache: false,
                    headers: getSecurityHeaders()
                });
            }
            function getUserInfo(accessToken) {
                var headers;
                if (typeof (accessToken) !== "undefined") {
                    headers = {
                        "Authorization": "Bearer " + accessToken
                    };
                } else {
                    headers = getSecurityHeaders();
                }
                return $.ajax(userInfoUrl, {
                    cache: false,
                    headers: headers
                });
            }




//Hopecee
            function login(url, jData, busy) {
                // Clear session first.
                session.clearUser();
                //Append RequestVerificationToken value to the jData before posting.
                addRequestVerificationToken(jData);
                setMethod(jData, "login");
                //post data.
                $.post(url, jData, function(data) {    // var roles = [];
                    var user = {
                        emailAddress: ko.observable(undefined),
                        userNeo4jIdString: ko.observable(undefined),
                        isUserImg: ko.observable(false),
                        isLoggedIn: ko.observable(false),
                        //isBusy: ko.observable(false),
                        // userGroups: ko.observableArray(),
                        userRoles: ko.observableArray(),
                        userIsInRole: ko.observable(false)
                                //  setUser: setUser,
                                // clearUser: clearUser,
                                // archiveSessionStorageToLocalStorage: archiveSessionStorageToLocalStorage,
                                // isAuthCallback: isAuthCallback,
                                // userRemembered: userRemembered,
                                //rememberedToken: rememberedToken
                    };
                    $.each(data, function(id, value) {
                        if (id === "BadToken") {
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                            router.setRoute(intro);
                        } else {
                            if (id === "userId") {
                                user.emailAddress = value;
                                alert(value);
                            }

                            if (id === "userNeo4jIdString") {
                                user.userNeo4jIdString = value;
                                alert(value);
                            }

                            if (id === "isUserImg") {
                                user.isUserImg = value;
                            }

                            if (id === "rolesData") {
                                $.each(value, function(id, v) {
                                    user.userRoles.push(v);
                                });
                                //alert(user.userRoles());
                                //alert(user.userRoles()[0]);
                                //alert(user.userRoles()[1]);
                                //alert(user.userRoles()[2]);
                            }

                            if (id === "isLoggedIn") {
                                if (value === true) {
                                    user.isLoggedIn = true;

                                    if (user.isLoggedIn) {
                                        // alert("home");
                                        session.setUser(user, false);
                                        busy.remove();
                                        router.setRoute(home);
                                        stickyheaderSetter.set();
                                    }

                                } else {
                                    busy.remove();
                                    logger.log({
                                        message: "Please check your E-mail Address and Password.",
                                        showToast: true,
                                        type: "error",
                                        title: "Access Denied."
                                    });
                                    router.setRoute(intro);
                                }
                            }

                        }

                    });
                });
            }



            function joinEditor(url, jData, busy) {
                //alert("surrrho");
                // Clear session first.
                session.clearUser();

                //Append RequestVerificationToken value to the jData before posting.
                addRequestVerificationToken(jData);
                setMethod(jData, "joinEditor");
                //post data.
                $.post(url, jData, function(data) {
                    /* $.ajax({
                     type: 'POST',
                     url: url,
                     data: jData,
                     // dataType: 'JSON',
                     success: function(data) {
                     */
                    busy.remove();
                    var user = {
                        emailAddress: ko.observable(undefined),
                        userNeo4jIdString: ko.observable(undefined),
                        // isUserImg: ko.observable(false),
                        isLoggedIn: ko.observable(false),
                        //isBusy: ko.observable(false),
                        // userGroups: ko.observableArray(),
                        userRoles: ko.observableArray()//,
                                // userIsInRole: ko.observable(false)
                                //  setUser: setUser,
                                // clearUser: clearUser,
                                // archiveSessionStorageToLocalStorage: archiveSessionStorageToLocalStorage,
                                // isAuthCallback: isAuthCallback,
                                // userRemembered: userRemembered,
                                //rememberedToken: rememberedToken
                    };
                    //var arrayName = [];
                    $.each(data, function(id, value) {

                        if (id === "BadToken") {
                            //array.push(value);
                            alert("BadToken1");
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                        }

                        if (id === "userId") {
                            user.emailAddress = value;
                        }

                        if (id === "userNeo4jIdString") {
                            user.userNeo4jIdString = value;
                        }

                        // if (id === "isUserImg") {
                        //   user.isUserImg = value;
                        //}

                        if (id === "rolesData") {
                            $.each(value, function(id, v) {
                                user.userRoles.push(v);
                            });
                            //alert(user.userRoles());
                            //alert(user.userRoles()[0]);
                            //alert(user.userRoles()[1]);
                            //alert(user.userRoles()[2]);
                        }

                        if (id === "isLoggedIn") {
                            if (value === true) {
                                user.isLoggedIn = true;

                                if (user.isLoggedIn) {
                                    // alert("home");
                                    session.setUser(user, false);
                                    router.setRoute(join_Editor_2);
                                    stickyheaderSetter.set();
                                }
                            } else {
                                busy.remove();
                                logger.log({
                                    message: "Please check your E-mail Address and Password.",
                                    showToast: true,
                                    type: "error",
                                    title: "Access Denied."
                                });
                                router.setRoute(intro);
                            }
                        }
                    });
                    //alert("op " + arrayName);
                });

            }

            function joinEditor2(url, jData, busy) {
                // Clear session first.
                // session.clearUser();
//router.navigate('#/join_editor_3');

                //Append RequestVerificationToken value to the jData before posting.
                addRequestVerificationToken(jData);
                setMethod(jData, "joinEditor2");
                setEmailAddress(jData, session.userEmailAddress);
                //alert(session.userEmailAddress());

                //var arrayName2 = [];
                //post data.
                $.post(url, jData, function(data) {
                    //alert("post!hhhhhhho");
                    busy.remove();
                    $.each(data, function(id, value) {
                        //arrayName2.push(value);

                        // alert("surrrho");
                        //  router.navigate('#/join_editor_2');

                        if (id === "BadToken") {
                            alert("BadToken");
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                        } else {

                            router.setRoute(join_Editor_3);
                            // alert("submitted!hhhhhhho");
                            // $(".reload").click(); // what is this?
                        }
                    });
                });
                // alert(arrayName2);
            }


            function joinEditor3(url, jData, busy) {
                // Clear session first.
                //session.clearUser();

                //Append RequestVerificationToken value to the jData before posting.
                addRequestVerificationToken(jData);
                setMethod(jData, "joinEditor3");
                setEmailAddress(jData, session.userEmailAddress);

                //post data.
                $.post(url, jData, function(data) {

                    busy.remove();

                    var user = {
                        emailAddress: ko.observable(undefined),
                        userNeo4jIdString: ko.observable(undefined),
                        isUserImg: ko.observable(false),
                        isLoggedIn: ko.observable(false),
                        //isBusy: ko.observable(false),
                        // userGroups: ko.observableArray(),
                        userRoles: ko.observableArray(),
                        userIsInRole: ko.observable(false)
                                //  setUser: setUser,
                                // clearUser: clearUser,
                                // archiveSessionStorageToLocalStorage: archiveSessionStorageToLocalStorage,
                                // isAuthCallback: isAuthCallback,
                                // userRemembered: userRemembered,
                                //rememberedToken: rememberedToken
                    };

                    $.each(data, function(id, value) {
                        // alert("submitted!hhhhhhho");
                        //  router.navigate('#/join_editor_2');
                        // arrayName3.push(value);
                        if (id === "BadToken") {
                            alert("BadToken");
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                        } else {
                            //  alert("submitted!hhhhhhho");
                            router.setRoute(home);
                            // $(".reload").click(); // what is this?
                        }
                    });
                    // alert(arrayName3);
                });
            }

            function localeManager(url, jData, busy) {
                addRequestVerificationToken(jData);
                setMethod(jData, "localeManager");

                var form = $('<form action="' + url + '" method="post">' +
                        '<input type="hidden"  name="RequestVerificationToken" value="' + jData['RequestVerificationToken'] + '" />' +
                        '<input type="hidden"  name="language" value="' + jData['language'] + '" />' +
                        '<input type="hidden"  name="method" value="' + jData['method'] + '" />' +
                        '</form>');
                $('body').append(form);
                form.submit();
                // $.get(url, jData);

            }


            function logout() {
                return $.ajax(logoutUrl, {
                    type: "POST",
                    headers: getSecurityHeaders()
                });
            }


            function findUser(url, jData, busy) {
                // Clear session first.
                //session.clearUser();

                //Append RequestVerificationToken value to the jData before posting.
                addRequestVerificationToken(jData);
                setMethod(jData, "findUser");
                //setEmailAddress(jData, session.userEmailAddress);
                //setUserNeo4jIdString(jData, session.userNeo4jIdString);//TODO use this.
                setUserNeo4jIdString(jData, "13421");
               // alert(jData.userNeo4jIdString);
                //post data.
                $.post(url, jData, function(data) {

                    busy.remove();
                    $("#userSearchBusy").addClass('ui-helper-hidden');

                    var userSearch = {
                        userId: ko.observable(undefined),
                        name: ko.observable(undefined),
                        usersName: ko.observable(undefined),
                        usersFirstname: ko.observable(undefined),
                        usersLastname: ko.observable(undefined),
                        usersStatus: ko.observable(undefined)
                    };

                    $.each(data, function(id, value) {
                        userSearch = {};
                        // alert("submitted!hhhhhhho");
                        //  router.navigate('#/join_editor_2');
                        // arrayName3.push(value);
                        if (id === "BadToken") {
                            alert("BadToken");
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                        } else {
                            if (id === "array") {
                                var userArray = value;
                               
                                /* 
                                alert(userArray.length);
                                var allUserArray = [];

                               
                                 for (var i = 0; i < userArray.length; i++) {
                                 var map = userArray[i];
                                 
                                 $.each(map, function(id, value) {
                                 userSearch = {};
                                 
                                 if (id === "userId") {
                                 userSearch.userId = value;
                                 //alert(value);
                                 }
                                 if (id === "name") {
                                 userSearch.name = value;
                                 //alert(value);
                                 }
                                 if (id === "usersName") {
                                 userSearch.usersName = value;
                                 //alert(value);
                                 }
                                 if (id === "usersFirstname") {
                                 userSearch.usersFirstname = value;
                                 // alert(value);
                                 }
                                 if (id === "usersLastname") {
                                 userSearch.usersLastname = value;
                                 //alert(value);
                                 }
                                 if (id === "usersStatus") {
                                 userSearch.usersStatus = value;
                                 // alert(value);
                                 }
                                 
                                 allUserArray.push(userSearch);
                                 
                                 });
                                 
                                 }
                                 */
                                //allUserArray.push(userSearch);

                                /*
                                 for (var i = 0; i < allUserArray.length; i++) {
                                 var map1 = allUserArray[i];
                                 
                                 $.each(map1, function(id, value) {
                                 
                                 alert(id + " " +value);
                                 });
                                 }
                                 */

                                session.userSearchArr(userArray);

                                // session.userSearchArr(allUserArray);
                                ko.observable('viewmodels/user_views/mainPage/userPage/userSearchWidget/allCustomersWidget').publishOn('USER_SEARCH_PANEL');


                            } else {
                                //  session.userSearch('');//Clear First.
                                  
                                if (id === "userId") {
                                    userSearch.userId = value;
                                    //alert("userId : " +value);
                                }
                                if (id === "name") {
                                    userSearch.name = value;
                                   // alert("name : " +value);
                                }
                                if (id === "usersName") {
                                    userSearch.usersName = value;
                                    //alert("usersName : " +value);
                                }
                                if (id === "usersFirstname") {
                                    userSearch.usersFirstname = value;
                                    //alert("usersFirstname : " +value);
                                }
                                if (id === "usersLastname") {
                                    userSearch.usersLastname = value;
                                    alert("usersLastname : " +value);
                                }
                                if (id === "usersStatus") {
                                    userSearch.usersStatus = value;
                                    //alert("usersStatus : " +value);
                                }



                                //TODO what is this?
                                if (id === "isLoggedIn" && value === false) {
                                    //alert(value);
                                    logger.log({
                                        message: "Please log in  and try again",
                                        showToast: true,
                                        type: "error"
                                    });
                                }

                                session.userSearch(userSearch);
                                ko.observable('viewmodels/user_views/mainPage/userPage/userSearchWidget/findUserWidget').publishOn('USER_SEARCH_PANEL');

                            }


                        }
                    });

                });
            }


















            function register(data) {
                return $.ajax(registerUrl, {
                    type: "POST",
                    data: data
                });
            }
            function registerExternal(accessToken, data) {
                return $.ajax(registerExternalUrl, {
                    type: "POST",
                    data: data,
                    headers: {
                        "Authorization": "Bearer " + accessToken
                    }
                });
            }
            function removeLogin(data) {
                return $.ajax(removeLoginUrl, {
                    type: "POST",
                    data: data,
                    headers: getSecurityHeaders()
                });
            }
            function setPassword(data) {
                return $.ajax(setPasswordUrl, {
                    type: "POST",
                    data: data,
                    headers: getSecurityHeaders()
                });
            }




        });