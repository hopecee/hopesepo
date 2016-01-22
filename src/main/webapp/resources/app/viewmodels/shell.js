define(['dojo/topic', "dojo/request", 'durandal/app',
    //'plugins/router',
    'global/services/pageRouter', 'global/services/session', 'global/services/logger',
    'global/services/idletimeout', 'global/services/busy'

], function(topic, request, app, pageRouter, session, logger, idletimeout, busy
        ) {
    "use strict";


    $(document).ready(function() {

    });




    var activate = function(view, parent) {
        //Initialization.
        // Check veiwpiont through 'Animation' and set the session mediaViewType.
        if ($('body').width() < 500) {
            session.mediaViewType(2);
        } else {
            session.mediaViewType(1);
        }
        $('body').on('animationend webkitAnimationEnd ', function(event) {
            if (event.originalEvent.animationName === 'max-width-500px') {
                session.mediaViewType(2);
            } else {
                session.mediaViewType(1);
            }
        });



//Initialize User idle timeout syncronized with server. 
        idletimeout.init();
        busy.init();

        //Initialize all needed functions. 
        // allRunOnce();



//$("#headContainer").css( "background-color","red" );
        //  loggedInHandler(this);

        /*
         var routes = {
         '/intro': function() {
         alert("author");
         },
         '/join_editor_2': function() {
         alert("author");
         },
         '/join_editor_3': function() {
         alert("author");
         },
         '/home': function() {
         alert("An inline route handler.");
         },
         '/books/view/:bookId': function() {
         alert("An inline route handler.");
         }
         };
         
         
         var allroutes = function() {
         //var page = document.getElementById('page');
         // var route = window.location.hash.slice(2);
         // var pages = $('.page');
         //var page = pages.filter('[data-bind="compose:' + route + ']');
         };
         */
        /*
         router.makeRelative({moduleId: 'viewmodels'});
         router.map([
         //{route: '', title: 'Menu', moduleId: 'redirect', nav: true, visible: true, login: false},
         {route: ['intro', ''], title: 'intro', moduleId: 'intro', nav: true, visible: true, login: false},
         {route: 'join_editor_2', title: 'join_editor_2', moduleId: 'intro_views/join_editor_2', nav: true, visible: true, login: false}, // requiredRoles: ['RegisteredUsers']},
         {route: 'join_editor_3', title: 'join_editor_3', moduleId: 'intro_views/join_editor_3', nav: true, visible: true, login: false},
         {route: 'home', title: 'home', moduleId: 'user_views/home', nav: true, visible: true, login: false},
         //{route: 'flickr', title: 'My flickr', moduleId: 'viewmodels/flickr', nav: true, visible: true, login: false},
         // {route: 'login', title: 'My flickr', moduleId: 'viewmodels/login', nav: false, visible: false, login: false},
         {route: 'texteditor', title: 'texteditor', moduleId: 'texteditor', nav: true, visible: true, login: false}
         ]).buildNavigationModel()
         .mapUnknownRoutes('intro', 'Route not found')
         .activate();
         
         
         
         
         router.guardRoute = function(routeInfo, params, instance) {
         if (typeof (params.config.requiredRoles) !== "undefined") {
         var res = true;//session.userIsInRole(params.config.requiredRoles);
         if (!res)
         {
         logger.log({
         message: "Access denied. Navigation cancelled.",
         showToast: true,
         type: "warning"
         });
         }
         return res;
         } else {
         return true;
         }
         };
         
         */

        //router.navigate('#/start');

        //setTimeout 30 seconds (30000)
        //   setTimeout(function() {app.trigger('authenticaterequired');}, 300000);
        //setTimeout(function(){alert('Hello')},3000000);

        // app.on('authenticaterequired', function() {
        //  alert('viewmodels/login');
        // router.navigate('#/login');
        //app.showModal('viewmodels/login');

        // });

        /*
         var vm = 'viewmodels';
         router.register("/intro", function(evt) {
         evt.preventDefault();
         //alert("intro");
         ko.observable(vm + '/intro').publishOn('ROUTE');
         });
         router.register("/join_editor_2", function(evt) {
         evt.preventDefault();
         //alert("join_editor_2");
         ko.observable(vm + '/intro_views/join_editor_2').publishOn('ROUTE');
         });
         router.register("/join_editor_3", function(evt) {
         evt.preventDefault();
         // alert("join_editor_3");
         ko.observable(vm + '/intro_views/join_editor_3').publishOn('ROUTE');
         });
         router.register("/home", function(evt) {
         evt.preventDefault();
         // alert("home");
         ko.observable(vm + '/user_views/user_panel/user_panel').publishOn('ROUTE');
         });
         
         router.startup();
         */

        //starting page.
        //  var startPage = ko.observable('viewmodels/user_views/user_panel/user_panel').publishOn('ROUTE');
        // return router.navigate('#/intro');
        //  return router.go("/home");
        //////// return router.navigate('#/home');
    };
    var pageR = new pageRouter();
    var attached = function(view, paren) {

        var stickyPanelOptions = {
            topPadding: 0,
            afterDetachCSSClass: "BoxGlow_Grey2",
            savePanelSpace: true
        };
        $("#headContainer").stickyPanel(stickyPanelOptions);



        /*
         var intro = {
         on: function() {
         ko.observable('viewmodels/intro').publishOn('ROUTE');
         }
         };
         var join_editor_2 = {
         on: function() {
         ko.observable('viewmodels/intro_views/join_editor_2').publishOn('ROUTE');
         },
         before: function() {
         //if (typeof (session.userEmailAddress()) !== "undefined") {
         var res = true;//session.userIsInRole(params.config.requiredRoles);
         if (!res)
         {
         ko.observable('viewmodels/intro').publishOn('ROUTE');
         
         logger.log({
         message: "Access denied. Navigation cancelled. Please login",
         showToast: true,
         type: "warning"
         });
         }
         return res;
         // } else {
         //   return true;
         //}
         }
         
         };
         
         var join_editor_3 = {
         on: function() {
         ko.observable('viewmodels/intro_views/join_editor_3').publishOn('ROUTE');
         }};
         
         var join_editor_complete = {
         on: function() {
         ko.observable('viewmodels/intro_views/join_editor_complete').publishOn('ROUTE');
         }};
         
         var home = {
         on: function() {
         ko.observable('viewmodels/user_views/home').publishOn('ROUTE');
         }};
         
         var notfound = function() {
         ko.observable('viewmodels/intro').publishOn('ROUTE');
         };
         */

        //From director.js.
        Router({
            '/intro': pageR.intro(),
            '/join_editor_2': pageR.join_editor_2(),
            '/join_editor_3': pageR.join_editor_3(),
            '/join_editor_complete': pageR.join_editor_complete(),
            '/home': pageR.home()
        })//.configure({notfound: pageR.notfound()})
                .init('intro');
        //router.init('#/intro');


    };
//Not used
    var loggedInHandler = function(self) {
        app.on('authentication.loggedIn').then(function(data) {
//alert(data.firstTopPanel);
//self.showHeader(false);
// alert(data.firstTopPanel);
            self.showHeader(data.showHeader);
        });
    };
// var headerHeight = ko.observable(height);

//alert('viewmodels/login');
    return {
        activate: activate,
        showHeader: ko.observable(true), // initially false. TODO
        //headContainerClass: ko.observable('headContainer_1').subscribeTo('HEADCONTAINER_CLASS'), // initially 'headContainer_1'.
        headerHeight: ko.observable().subscribeTo('HEADER_HEIGHT'),
        // router: router,
        ActiveView: ko.observable().subscribeTo('ROUTE'),
        attached: attached
                //activeEditorLink: ko.observable().publishOn('ACTIVE_EDITOR'),
                //  onMyPhoto: function() {
                //     this.activeEditorLink('viewmodels/user_views/editor_panel/user_photo_editor');
                // },

    };


    function allRunOnce() {
        //Initialize User idle timeout syncronized with server. 
        //idletimeout.init();

        //Initialize all runOnce.
        // security.runOnce();

    }

});
