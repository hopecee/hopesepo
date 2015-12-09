define([], function() {
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

    

    var functionz = {
        clearSubscriptions: clearSubscriptions
    };

    return functionz;
});
