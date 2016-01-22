define(['global/services/session'], function(session) {
    "use strict";

    function clearSubscriptions(subscriptions) {
        //unsubscribe them first if any, by disposing them..
        $.each(subscriptions, function(id, value) {
            //subscriptions.splice(id, 1);
            //delete subscriptions[id];
            subscriptions[id].dispose();
        });
        //cleans the Array.
        $.each(subscriptions, function(id, value) {
            subscriptions.splice(id, 1);
        });
    }

    function preventDbClick(bindId,seconds) {
        var $bindedEl = $('[data-bind*=' + bindId + ']');
        $bindedEl.prop('disabled', true);
        //bindId.preventDefault();
        setTimeout(function() {
            $bindedEl.prop('disabled', false);
        }, seconds*1000);
    }

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
    
    function getUserSession(jData) {
        var userSession = {};
        userSession.userNeo4jIdString = session.userNeo4jIdString();
        jData['userSession'] = userSession;
    }

    var functionz = {
        clearSubscriptions: clearSubscriptions,
        preventDbClick: preventDbClick,
        addRequestVerificationToken: addRequestVerificationToken,
        setMethod: setMethod,
        setEmailAddress: setEmailAddress,
        setUserNeo4jIdString: setUserNeo4jIdString,
        getUserSession: getUserSession
    };

    return functionz;
});
