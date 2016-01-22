

define(['dojo/i18n!app/nls/constants', 'dojo/i18n!app/nls/labels',
    'dojo/dom', 'dojo/dom-construct', 'dijit/layout/TabContainer', 'dijit/layout/ContentPane',
    'plugins/router', 'global/services/session',
    'global/services/security',
    'global/services/layout',
    'global/services/logger'], function(constants, labels,
        dom, domConstruct, TabContainer, ContentPane,
        router, session, security,layout,logger) {
    "use strict";

   
   
    var attached = function(view, paren) {

    };






    return {
        //activate: activate,
        attached: attached,
        userSearchData: userSearchData(),
        userdblclick: userdblclick
    };


    function userSearchData() {
        var userData = {},
                userId,
                usersName,
                usersFullname,
                userDataArr = session.userSearch();
        $.each(userDataArr, function(index, value) {
            // console.log(value);
            userId = value.userId;
            usersName = value.usersName;
            usersFullname = value.usersLastname + " " + value.usersFirstname;
        });
        userData.id = userId;
        userData.img = constants.userImgFolder + userId + '/' + userId + constants.extJPG;
        userData.usersName = usersName;
        userData.usersFullname = usersFullname;
        return userData;
    }


    function userdblclick(data, event) {
        ko.postbox.publish('OPEN_USER_PROFILE_PANEL', event.currentTarget.id);
    }
    
});

