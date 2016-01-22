define(['global/services/functionz'], function(fxz) {
    "use strict";

    var subscriptions = [];

    var busy = {
        init: init//,
                //subscriptions: subscriptions
    };
    return busy;

    function init() {
        //unsubscribe them first if any, by disposing them.
        fxz.clearSubscriptions(subscriptions);

        var joinEditorBusy;
        subscriptions.push(ko.postbox.subscribe('JOINT_EDITOR_BUSY_START', function(data) {
            joinEditorBusy = getBusyOverlay(document.getElementById('joinEditorBusy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 50, type: 'o'});
        }));
        subscriptions.push(ko.postbox.subscribe('JOINT_EDITOR_BUSY_REMOVE', function(data) {
            joinEditorBusy.remove();
        }));
        
        var joinEditor2Busy;
        subscriptions.push(ko.postbox.subscribe('JOINT_EDITOR_2_BUSY_START', function(data) {
            joinEditor2Busy = getBusyOverlay(document.getElementById('joinEditor2Busy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 50, type: 'o'});
        }));
        subscriptions.push(ko.postbox.subscribe('JOINT_EDITOR_2_BUSY_REMOVE', function(data) {
            joinEditor2Busy.remove();
        }));
        var joinEditor3Busy;
        subscriptions.push(ko.postbox.subscribe('JOINT_EDITOR_3_BUSY_START', function(data) {
            joinEditor3Busy = getBusyOverlay(document.getElementById('joinEditor3Busy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 50, type: 'o'});
        }));
        subscriptions.push(ko.postbox.subscribe('JOINT_EDITOR_3_BUSY_REMOVE', function(data) {
            joinEditor3Busy.remove();
        }));
        
        var userSearchBusy;
        subscriptions.push(ko.postbox.subscribe('USER_SEARCH_BUSY_START', function(data) {
            userSearchBusy = getBusyOverlay(document.getElementById('userSearchBusy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 32, type: 'o', top: 5, marginTop: 0, marginLeft: 0});
        }));
        subscriptions.push(ko.postbox.subscribe('USER_SEARCH_BUSY_REMOVE', function(data) {
            userSearchBusy.remove();
        }));

        var userLogInMenuBusy;
        subscriptions.push(ko.postbox.subscribe('USER_LOGIN_MENU_BUSY_START', function(data) {
            userLogInMenuBusy = getBusyOverlay(document.getElementById('menuBusy'), {color: '#000', opacity: 0.9, text: '', style: 'text-decoration:blink;font-weight:bold;font-size:12px;color:white'}, {color: '#fff', size: 25, type: 'o'});
        }));
        subscriptions.push(ko.postbox.subscribe('USER_LOGIN_MENU_BUSY_REMOVE', function(data) {
            userLogInMenuBusy.remove();
        }));




    }


});