define([], function() {
    "use strict";
  

    var attached = function(view, paren) {
        //busy.userSearchBusy();
        ko.postbox.publish('USER_SEARCH_BUSY_START', null);
       //  var busy = getBusyOverlay(document.getElementById('userSearchBusy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 32, type: 'o'});
    };

    return {
        //compositionComplete : compositionComplete ,
        attached: attached
    };
});