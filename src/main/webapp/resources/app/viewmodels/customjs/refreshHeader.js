
define(['durandal/events'], function(events) {


    var refreshHeader = function() {
    };

    var doRefresh = {};

    events.includeIn(doRefresh);

    refreshHeader.prototype.getRefresh = function() {
        //perform login then trigger events to whoever is listening...
        return   doRefresh.trigger('logged:onl', {user: 'Child2'});

    };


    refreshHeader.prototype.getLoggedOff = function() {
        //perfom logoff then trigger events to whoever is listening...
        return   doRefresh.trigger('logged:offl');

    };


    return refreshHeader;

});


