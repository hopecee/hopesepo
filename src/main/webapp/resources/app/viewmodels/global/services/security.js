define(['dojo/i18n!app/nls/countryIsoCodes', 'dojo/on', 'global/services/session', 'global/services/logger',
    'global/services/stickyheaderSetter',
    'global/services/crypto', 'global/services/dialogView', 'global/services/functionz'],
        function(countryIsoCodes, on, session, logger, stickyheaderSetter,
                crypto, dialogView, fxz) {
            "use strict";
            var router = new Router().init();
            var subscriptions = [];
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
            /*
             function addRequestVerificationToken(jData) {
             //Append RequestVerificationToken value to the jData before posting.
             jData['RequestVerificationToken'] = $('input[name="__RequestVerificationToken"]').val();
             }
             function setMethod(jData, method) {
             jData['method'] = method;
             
             
             function setEmailAddress(jData, usersEmailAddress) {
             jData['usersEmailAddress'] = usersEmailAddress;
             }
             function setUserNeo4jIdString(jData, id) {
             jData['userNeo4jIdString'] = id;
             }
             function getUserSession(jData) {
             var userSession = {};
             userSession.userNeo4jIdString = session.userNeo4jIdString();
             jData['userSession'] = userSession;
             }
             */

            function getAuthentication() {
                var k = makeId();
                var deferred = new $.Deferred();
                $.jCryption.authenticate(k, "/tuCryptoServlet", //?generateKeyPair=true&RequestVerificationToken=",
                        "/tuCryptoServlet", //?handshake=true&RequestVerificationToken=",
                        function(AESKey) {
                            deferred.resolve(true, k);
                        },
                        function() {
                            console.log("Authentication failed");
                            deferred.resolve(false);
                        }
                );
                return deferred.promise();
            }

            function makeId() {    //randomKey
                var text = "";
                var textLenght = 32;
                var possible = "ACDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz2345679"; //remove l,1,0,o,8,B.
                for (var i = 0; i < textLenght; i++)
                    text += possible.charAt(Math.floor(Math.random() * possible.length));
                return text;
            }


            function keepSessionAlive() {
                getAuthentication().then(function(promise, k) {
                    if (promise) {
                        session.makeId(k);
                    }
                });
            }

            function idletimeoutSubscriptions() {
                //unsubscribe them first if any, by disposing them.
                fxz.clearSubscriptions(subscriptions);
                //Triger to keep session Alive.
                subscriptions.push(ko.postbox.subscribe('KEEP_SESSION_ALIVE', function(data) {
                    // alert("alive");
                    keepSessionAlive();
                }));
                //Logout Triger.
                subscriptions.push(ko.postbox.subscribe('USER_LOGOUT', function(data) {
                    logout(data);
                }));
            }




            /*
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
             */

            var securityService = {
                //addExternalLogin: addExternalLogin,
                //changePassword: changePassword,
                //getExternalLogins: getExternalLogins,
                //getManageInfo: getManageInfo,
                //getUserInfo: getUserInfo,
                //addRequestVerificationToken: addRequestVerificationToken,
                //setMethod: setMethod,
                //getAuthentication: getAuthentication,
                login: login,
                logout: logout,
                joinEditor: joinEditor,
                joinEditor2: joinEditor2,
                joinEditor3: joinEditor3,
                localeManager: localeManager,
                searchUser: searchUser,
                addFriend: addFriend,
                getAllCountries: getAllCountries,
                getAllQuestions: getAllQuestions
                        // register: register,
                        //registerExternal: registerExternal,
                        //removeLogin: removeLogin,
                        //setPassword: setPassword,
                        //returnUrl: siteUrl,
                        //toErrorString: toErrorString,
                        //subscriptions: subscriptions
            };
            return securityService;
            /*
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
             
             */

            /*
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
             
             */


            function login(url, jData) {
                idletimeoutSubscriptions();
                /*
                 // Encrypt with the public key...
                 var encrypt = new JSEncrypt();
                 var decrypt = new JSEncrypt();
                 var urls = "/tuCryptoServlet?generateKeyPair=true&RequestVerificationToken=ZmVBZ59oUad1Fr33BuPxANKY9q3Srr56fGBtLZZmVBZ59oUad1FrZZmVBZ59oUad";
                 $.getJSON(urls, function(data1) {
                 //$.each(data1.publickey, function(id, value) {
                 //   alert(id + " :" + value);
                 // });
                 alert( " : bnh " );
                 
                 
                 encrypt.setPublicKey(data1.publickey);
                 var encrypted = encrypt.encrypt("hope");
                 alert("encrypted :" + encrypted);
                 
                 
                 decrypt.setPrivateKey(data1.privateKey);
                 var uncrypted = decrypt.decrypt(encrypted);
                 alert("uncrypted :" + uncrypted);
                 
                 
                 
                 });
                 
                 */




                //encrypt.setPublicKey($('#pubkey').val());


                // Decrypt with the private key...


                /*
                 // Encrypt with the public key...
                 var encrypt = new JSEncrypt();
                 var decrypt = new JSEncrypt();
                 var urls = "/tuCryptoServlet?generateKeyPair=true&RequestVerificationToken=ZmVBZ59oUad1Fr33BuPxANKY9q3Srr56fGBtLZZmVBZ59oUad1FrZZmVBZ59oUad";
                 $.getJSON(urls, function(data1) {
                 $.each(data1.publickey, function(id, value) {
                 alert(id + " :" + value);
                 });
                 
                 encrypt.setPublicKey($('#pubkey').val());
                 
                 });
                 
                 var encrypted = encrypt.encrypt('hope');
                 alert("encrypted :" + encrypted);
                 
                 decrypt.setPrivateKey($('#privkey').val());
                 
                 var uncrypted = decrypt.decrypt(encrypted);
                 alert("uncrypted :" + uncrypted);
                 */

                // Decrypt with the private key...
                // var urls2 = "/tuCryptoServlet?handshake=true&RequestVerificationToken=ZmVBZ59oUad1Fr33BuPxANKY9q3Srr56fGBtLZZmVBZ59oUad1FrZZmVBZ59oUad";
                // var decrypt = new JSEncrypt();
                // $.post(urls2, null, function(data2) {

                //   $.each(data2.privateKey, function(id, value) {
                //       alert(id + "privateKey  ==========:" + value);
                //  });

                // decrypt.setPrivateKey(data2.privateKey);

                // });

                //var uncrypted = decrypt.decrypt(encrypted);
                // alert("uncrypted :" + uncrypted);




                //var hashObj = new jsSHA("SHA-1", "TEXT");
                // hashObj.setHMACKey("abc", "TEXT");
                //  hashObj.update("m");
                //  var password = hashObj.getHMAC("HEX");
                // var password = hashObj.getHash("HEX");
                //password="password";
                //  alert('password : ' + password);

                // var key = makeId();

                // alert("key : " + key);

                //alert('makeid : ' + makeid());

                // shaObj.setHMACKey("abc", "TEXT");
                //shaObj.update("This is a test");
                //var hmac = shaObj.getHMAC("HEX");
                //  var encryptedString = $.jCryption.encrypt("hope 12 Ten @ & $%^&*()!~p+", key);





                //  alert(encryptedString);

                // var decryptedString = $.jCryption.decrypt(encryptedString, password);

                // alert(decryptedString);

//url= "/TuCryptoServlet";

                // jData.hope = $.jCryption.encrypt("hope", password);
                getAuthentication().then(function(promise, k) {
                    if (promise) {
                        session.clearUser(); // Clear session first.
                        fxz.addRequestVerificationToken(jData);
                        fxz.setMethod(jData, "login");
                        crypto.en(jData, k);
                        $.post(url, jData, function(data) {    // var roles = [];
                            crypto.de(data, k);
                            ko.postbox.publish('USER_LOGIN_MENU_BUSY_REMOVE', null);
                            var user = {
                                emailAddress: ko.observable(undefined),
                                userNeo4jIdString: ko.observable(undefined),
                                makeId: ko.observable(undefined),
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
                            var logedIn = false;
                            $.each(data, function(id, value) {
                                //alert(id + "  :  " + value);
                                if (id === "BadToken") {
                                    logger.log({
                                        message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                        showToast: true,
                                        type: "error"
                                    });
                                    router.setRoute(intro);
                                } else if (id === "isLoggedIn" && value === false) {//check if loggedIn.
                                    logger.log({
                                        message: "Please log in  and try again",
                                        showToast: true,
                                        type: "error"
                                    });
                                    router.setRoute(intro);
                                } else {
                                    if (id === "userId") {
                                        user.emailAddress = value;
                                    }

                                    if (id === "userNeo4jIdString") {
                                        user.userNeo4jIdString = value;
                                    }

                                    if (id === "isUserImg") {
                                        var val2 = (value === "true");
                                        user.isUserImg = val2;
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

                                    if (id === "isLoggedIn" && value === "true") {
                                        var val = (value === "true");
                                        user.isLoggedIn = val;
                                        user.makeId = k;
                                        logedIn = true;
                                    }

                                }
                            });
                            if (logedIn) {
                                session.setUser(user, false); //set User session.
                                router.setRoute(home);
                                stickyheaderSetter.set();
                                //User idle timeout syncronized with server. 
                                ko.postbox.publish('START_IDLE_TIMEOUT', null);
                            }
                        });
                    } else {
                        alert(promise + " : login false :" + k);
                    }
                });
            }

            function logout(data) {
                session.clearUser(); // Clear session first.
                router.setRoute(intro);
                //User idle timeout syncronized with server.
                if (data === "idletimeout") {
                    ko.postbox.publish('STOP_IDLE_TIMEOUT', null);
                }
            }


            function joinEditor(url, jData) {
                getAuthentication().then(function(promise, k) {
                    if (promise) {
                        idletimeoutSubscriptions();
                        session.clearUser(); // Clear session first.
                        //Append RequestVerificationToken value to the jData before posting.
                        fxz.addRequestVerificationToken(jData);
                        fxz.setMethod(jData, "joinEditor");
                        crypto.en(jData, k);
                        //post data.
                        $.post(url, jData, function(data) {
                            crypto.de(data, k);
                            ko.postbox.publish('JOINT_EDITOR_BUSY_REMOVE', null);
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
                            var logedIn = false;
                            $.each(data, function(id, value) {
                                if (id === "BadToken") {
                                    logger.log({
                                        message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                        showToast: true,
                                        type: "error"
                                    });
                                    router.setRoute(intro);
                                } else if (id === "userStatus") {

                                    var name = crypto.deOne(jData.usersEmailAddress, k);
                                    var tt = "User Registered",
                                            tx = "This User, </br><span style='display:inline;font-weight: bold;'>" + name + "</span></br>is already a registered User.",
                                            at = "Login to continue.";

                                    var data = {};
                                    var buttonsArr = [],
                                            buttons1 = {};

                                    buttons1.text = "OK";
                                    buttons1.click = function() {
                                        $(this).dialog("close");
                                    };
                                    buttonsArr.push(buttons1);
                                    data.buttons = buttonsArr;

                                    dialogView.alert(tt, tx, null, at, data);


                                    /*
                                     alert(value);
                                     
                                     var currentConfig = {};
                                     alert(jData.usersEmailAddress);
                                     currentConfig.dialogContinueText = "Login to continue.";
                                     currentConfig.dialogOKButton = "OK";
                                     // currentConfig.dialogLogOutNowButton=
                                     
                                     
                                     var dialogContent = "<div id='idletimer_warning_dialog' ><p>" + currentConfig.dialogText + "</p><p>" + currentConfig.dialogContinueText + "</p></div>";
                                     $(dialogContent).dialog({
                                     buttons: [{
                                     text: currentConfig.dialogOKButton,
                                     click: function() {
                                     $(this).dialog("close");
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
                                     document.title = currentConfig.dialogTitle;
                                     */

                                } else if (id === "isLoggedIn" && value === false) {//check if loggedIn.
                                    logger.log({
                                        message: "Please check your E-mail Address and Password.",
                                        showToast: true,
                                        type: "error",
                                        title: "Access Denied."
                                    });
                                    router.setRoute(intro);
                                } else {
                                    if (id === "userId") {
                                        user.emailAddress = value;
                                    }

                                    if (id === "userNeo4jIdString") {
                                        user.userNeo4jIdString = value;
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

                                    if (id === "isLoggedIn" && value === "true") {
                                        var val = (value === "true");
                                        user.isLoggedIn = val;
                                        user.makeId = k;
                                        logedIn = true;
                                    }
                                }




                            });
                            if (logedIn) {
                                // alert("logedIn");
                                session.setUser(user, false); //set User session.
                                router.setRoute(join_Editor_2);
                                stickyheaderSetter.set();
                                //User idle timeout syncronized with server. 
                                ko.postbox.publish('START_IDLE_TIMEOUT', null);
                            }

                        });
                    } else {
                        alert(promise + " : joinEditor falsed :" + k);
                    }
                });
            }

            function joinEditor2(url, jData) {
                //Append RequestVerificationToken value to the jData before posting.
                fxz.addRequestVerificationToken(jData);
                fxz.setMethod(jData, "joinEditor2");
                fxz.setEmailAddress(jData, session.userEmailAddress());
                var k = session.makeId();
                crypto.en(jData, k);
                //post data.
                $.post(url, jData, function(data) {
                    crypto.de(data, k);
                    //Remove Busy first.
                    ko.postbox.publish('JOINT_EDITOR_2_BUSY_REMOVE', null);
                    $.each(data, function(id, value) {

                        if (id === "BadToken") {
                            alert("BadToken");
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                        } else {
                            router.setRoute(join_Editor_3);
                        }
                    });
                });
                // alert(arrayName2);
            }


            function joinEditor3(url, jData) {
                //Append RequestVerificationToken value to the jData before posting.
                fxz.addRequestVerificationToken(jData);
                fxz.setMethod(jData, "joinEditor3");
                fxz.setEmailAddress(jData, session.userEmailAddress());
                var k = session.makeId();
                crypto.en(jData, k);
                //post data.
                $.post(url, jData, function(data) {
                    crypto.de(data, k);
                    ko.postbox.publish('JOINT_EDITOR_2_BUSY_REMOVE', null);
                    // busy.remove();
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
                fxz.addRequestVerificationToken(jData);
                fxz.setMethod(jData, "localeManager");
                var form = $('<form action="' + url + '" method="post">' +
                        '<input type="hidden"  name="RequestVerificationToken" value="' + jData['RequestVerificationToken'] + '" />' +
                        '<input type="hidden"  name="language" value="' + jData['language'] + '" />' +
                        '<input type="hidden"  name="method" value="' + jData['method'] + '" />' +
                        '</form>');
                $('body').append(form);
                form.submit();
                // $.get(url, jData);

            }



            function searchUser(url, jData) {
                ko.observable('viewmodels/task/busyWidget').publishOn('USER_SEARCH_PANEL');
                //_busy.userSearchBusyRemove();

                // Clear session first.
                //session.clearUser();

                //Append RequestVerificationToken value to the jData before posting.
                fxz.addRequestVerificationToken(jData);
                fxz.setMethod(jData, "searchUser");
                //setEmailAddress(jData, session.userEmailAddress);
                //setUserNeo4jIdString(jData, session.userNeo4jIdString);//TODO use this.
                //setUserNeo4jIdString(jData, "13421");
                fxz.getUserSession(jData);
                var k = session.makeId();
                crypto.en(jData, k);
                // crypto.en(jData, makeId());
                //alert(jData.searchValue);
                //post data.
                $.post(url, jData, function(data) {
                    // console.log(Object.prototype.toString.call(data).slice(8, -1));

                    //var obj = $.parseJSON( data );
//console.log(obj);
                    crypto.de(data, k);
                    //Remove Busy first.
                    ko.postbox.publish('USER_SEARCH_BUSY_REMOVE', null);
                    /*
                     var userSearch = {
                     userId: ko.observable(undefined),
                     name: ko.observable(undefined),
                     usersName: ko.observable(undefined),
                     usersFirstname: ko.observable(undefined),
                     usersLastname: ko.observable(undefined),
                     usersStatus: ko.observable(undefined)
                     };
                     */

                    $.each(data, function(id, value) {
                        //   userSearch = {};
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
                        } else if (id === "isLoggedIn" && value === false) {//check if loggedIn.
                            logger.log({
                                message: "Please log in  and try again",
                                showToast: true,
                                type: "error"
                            });
                        } else {
                            if (id === "findAllCustomers") {
                                //alert("findAllCustomers ===== " + value);
                                var allUserArr = value;
                                // alert( "arrayT ");
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
                                // alert( "array1 " +session.userAllSearch());
                                session.userAllSearch([]); //Clear First.
                                // alert( "array " +session.userAllSearch());
                                session.userAllSearch(allUserArr);
                                //alert( "arrayo " +session.userAllSearch());


                                ko.observable('viewmodels/user_views/mainPage/userPage/userSearchWidget/allCustomersWidget').publishOn('USER_SEARCH_PANEL');
                            }

                            if (id === "findUser") {
                                // alert("findUser ==== " + value);
                                var userObj = value;
                                /*
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
                                 alert("usersLastname : " + value);
                                 }
                                 if (id === "usersStatus") {
                                 userSearch.usersStatus = value;
                                 //alert("usersStatus : " +value);
                                 }
                                 */



//alert("usersStatus1 : " + session.userSearch());
                                session.userSearch([]); //Clear First.
                                // alert("usersStatus2 : " + session.userSearch());
                                session.userSearch([userObj]);
                                //alert("usersStatus3 : " + session.userSearch());
                                //Remove Busy first.
                                //  busy.remove();
                                ko.observable('viewmodels/user_views/mainPage/userPage/userSearchWidget/findUserWidget').publishOn('USER_SEARCH_PANEL');
                            }


                        }
                    });
                });
            }




            function addFriend(url, jData) {
                // ko.observable('viewmodels/task/busyWidget').publishOn('USER_SEARCH_PANEL');
                //_busy.userSearchBusyRemove();

                // Clear session first.
                //session.clearUser();

                //Append RequestVerificationToken value to the jData before posting.
                fxz.addRequestVerificationToken(jData);
                fxz.setMethod(jData, "addFriend");
                //setEmailAddress(jData, session.userEmailAddress);
                //setUserNeo4jIdString(jData, session.userNeo4jIdString);//TODO use this.
                fxz.setUserNeo4jIdString(jData, "13421");
                // alert(jData.userNeo4jIdString);
                //post data.
                $.post(url, jData, function(data) {

                });
            }


            function getAllCountries(self) {
                var jData = {};
                //do some ajax and return a promise
                var that = self;
                var url = '/tuCountryListServlet';
                //Append RequestVerificationToken value to the jData before posting.
                fxz.addRequestVerificationToken(jData);
                fxz.setMethod(jData, "countryList");
                var k = session.makeId();
                crypto.en(jData, k);
                $.post(url, jData, function(data) {
                    crypto.de(data, k);
                    $.each(data, function(id, value) {
                        if (id === "BadToken") {
                            alert("BadToken");
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                        } else {
                            if (id === "countryData") {
                                var array = [];
                                var obj = {};
                                $.each(value, function(key, value1) {
                                    // push dataId which is the "Country_iso_code_3"
                                    // rather value1 which is "Country_name".
                                    array.push(countryIsoCodes[key]);
                                    obj[countryIsoCodes[key]] = key;
                                });
                                that.countries(array);
                                that.countriesObj(obj);
                            }
                        }
                    });
                });
            }


            function getAllQuestions(self) {
                var jData = {};
                var that = self;
                var url = '/tuQuestionListServlet';
                //Append RequestVerificationToken value to the jData before posting.
                fxz.addRequestVerificationToken(jData);
                fxz.setMethod(jData, "getAllQuestions");
                var k = session.makeId();
                crypto.en(jData, k);
                $.post(url, jData, function(data) {
                    crypto.de(data, k);
                    $.each(data, function(id, value) {
                        alert(id + " : " + value);
                        if (id === "BadToken") {
                            alert("BadToken");
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                        } else {
                            if (id === "questionData") {
                                var array = [];
                                $.each(value, function(dataId, dataValue) {
                                    alert(dataId + " : " + dataValue);
                                    array.push(dataValue);
                                });
                                that.questions1(array); //For that.questions1.
                                that.questions2(array); //For that.questions2.
                            }
                        }
                    });
                });
            }






            /*
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
             */









            // var securitySubscriptionArr = [];

            function runOnce() {

            }






        });