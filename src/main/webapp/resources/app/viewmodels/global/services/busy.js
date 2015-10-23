define([], function() {
    "use strict";
    var bs;
    return {
        userSearchBusy: function() {
            bs = getBusyOverlay(document.getElementById('userSearchBusy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 32, type: 'o', top: 5, marginTop: 0, marginLeft: 0});
            return bs;
        },
        userSearchBusyRemove: function() {
            return bs.remove();
        }
    };



});