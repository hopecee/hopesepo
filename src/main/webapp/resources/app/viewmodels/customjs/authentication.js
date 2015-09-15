
define(['durandal/events'], function(events) {


    var authentication = function() {
    };
    
     events.includeIn(authentication);
     
    //perform login then trigger events to whoever is listening...
      authentication.trigger('logged:on',user);

      //perfom logoff then trigger events to whoever is listening...
      authentication.trigger('logged:off');

 
/*
    var authenticate = {};

    events.includeIn(authenticate);

    authentication.prototype.getLoggedIn = function() {
        //perform login then trigger events to whoever is listening...
        return   authenticateq.trigger('logged:on', { user: 'Child2' });

    };


    authentication.prototype.getLoggedOff = function() {
        //perfom logoff then trigger events to whoever is listening...
        return   authenticateq.trigger('logged:off');

    };
*/

 return {authentication : authentication}

});
