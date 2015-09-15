define(['durandal/app', 'plugins/router', 'global/session', 'global/services/logger', 'global/services/imageSelectService'], function(app, router, session, logger, imageSelectService) {
 "use strict";


   // var attached = function(view, paren) {
    //    viewHandler(this);
   // };

/*
    var viewHandler = function(self) {
        app.on('authentication.loggedIn').then(function(data) {
            //  alert(data.activeScreen);
            //var  data1 = 'user_photo_editor';
            // self.showMenuLogin(data.showMenuLogin);
            self.activeEditor('viewmodels/user_views/editor_panel/' + data.activeEditor);
        });
    };
*/


   

    return {
        //  attached: attached,
        activeEditor: ko.observable().subscribeTo("ACTIVE_EDITOR"),
        // activeScreen: activeScreen//, // initially undefined.
        imgareaselectRemover: ko.postbox.subscribe("ACTIVE_EDITOR", function() {
         imageSelectService.remove();
        })
    };



});
