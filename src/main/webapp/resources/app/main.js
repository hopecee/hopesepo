/*
 requirejs.config({
 paths: {
 'text': '../lib/require/text',
 'durandal': '../lib/durandal/js',
 'plugins': '../lib/durandal/js/plugins',
 'transitions': '../lib/durandal/js/transitions',
 'customjs': './viewmodels/customjs',
 'global': './viewmodels/global',
 'knockout': '../lib/knockout/knockout-3.2.0',
 'knockout-postbox': '../lib/knockout/knockout-postbox.min',
 'bootstrap': '../lib/bootstrap/js/bootstrap',
 'jquery': '../lib/wijmo/jquery-1.9.1.min',
 // 'jquery': '/faces/javax.faces.resource/lib/wijmo/jquery-1.8.2.min',
 
 // 'jquery': 'http://code.jquery.com/jquery-1.9.1.min',
 
 
 
 'jquery.bgiframe': '../lib/wijmo/jquery.bgiframe',
 'jquery.cookie': '../lib/wijmo/jquery.cookie',
 'jquery-ui': '../lib/wijmo/jquery-ui-1.10.1.custom.min',
 // 'jquery-ui': '/faces/javax.faces.resource/open/development-bundle/external/jquery-ui-1.9.1.custom.min',
 // 'jquery-ui': 'http://code.jquery.com/ui/1.10.1/jquery-ui.min',
 
 // 'jquery.ui': '/faces/javax.faces.resource/app/jquery-ui',
 'jquery.validate': '../lib/wijmo/jquery.validate',
 'jquery.mousewheel': '../lib/wijmo/jquery.mousewheel.min',
 'globalize': '../lib/wijmo/globalize.min',
 'knockout.wijmo': '../lib/wijmo/knockout.wijmo',
 'wijmo.widget': '../lib/wijmo/wijmo.widget',
 'wijmo.wijpager': '../lib/wijmo/wijmo.wijpager',
 'wijmo.data': '../lib/wijmo/wijmo.data',
 'wijmo.wijinputcore': '../lib/wijmo/wijmo.wijinputcore',
 'wijmo.wijstringinfo': '../lib/wijmo/wijmo.wijstringinfo',
 'wijmo.wijinputnumberformat': '../lib/wijmo/wijmo.wijinputnumberformat',
 'wijmo.wijinputdateformat': '../lib/wijmo/wijmo.wijinputdateformat',
 'wijmo.wijinputtextformat': '../lib/wijmo/wijmo.wijinputtextformat',
 'wijmo.wijcharex': '../lib/wijmo/wijmo.wijcharex',
 'wijmo.wijpopup': '../lib/wijmo/wijmo.wijpopup',
 'wijmo.wijtooltip': '../lib/wijmo/wijmo.wijtooltip',
 'wijmo.wijtouchutil': '../lib/wijmo/wijmo.wijtouchutil',
 'wijmo.wijcalendar': '../lib/wijmo/wijmo.wijcalendar',
 'wijmo.wijinputdate': '../lib/wijmo/wijmo.wijinputdate',
 'wijmo.wijinputtext': '../lib/wijmo/wijmo.wijinputtext',
 'wijmo.wijinputnumber': '../lib/wijmo/wijmo.wijinputnumber',
 'wijmo.wijutil': '../lib/wijmo/wijmo.wijutil',
 'wijmo.wijlist': '../lib/wijmo/wijmo.wijlist',
 'wijmo.wijsuperpanel': '../lib/wijmo/wijmo.wijsuperpanel',
 'wijmo.wijgrid': '../lib/wijmo/wijmo.wijgrid',
 'wijmo.wijcombobox': '../lib/wijmo/wijmo.wijcombobox',
 'wijmo.wijeditor': '../lib/wijmo/wijmo.wijeditor',
 'wijmo.wijsplitter': '../lib/wijmo/wijmo.wijsplitter',
 'wijmo.wijdialog': '../lib/wijmo/wijmo.wijdialog',
 'wijmo.wijribbon': '../lib/wijmo/wijmo.wijribbon',
 'wijmo.wijtabs': '../lib/wijmo/wijmo.wijtabs',
 'wijmo.wijmenu': '../lib/wijmo/wijmo.wijmenu',
 'jquery.stickyPanel': '../custom/stickyPanel/jquery.stickyPanel.min',
 //  'jquery.sticky': '../custom/stickyheader/jquery.sticky',
 // 'jquery.stickyheader': '../custom/stickyheader/jquery.stickyheader.min',
 //'sammy': '/faces/javax.faces.resource/lib/sammy/sammy',
 'domReady': '../lib/require/domReady',
 'toastr': '../lib/toastr/203/toastr',
 // 'breeze': '../lib/breeze/breeze.debug',
 // 'breeze.savequeuing': '../lib/breeze/Labs/breeze.savequeuing',
 // 'Q': '../lib/breeze/q.min'
 'imgareaselect': '../lib/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min',
 // 'plupload': '../lib/plupload_2_1_2/js/jquery.plupload.queue/jquery.plupload.queue.min'
 'plupload': '../lib/plupload_2_1_2/js/plupload.full.min'//,
 //'uploads':'../uploads'
 //'knockout.reactor':'../lib/knockout/reactor/knockout.reactor'//,
 
 
 
 }
 ,
 shim: {
 // 'jquery.sticky': ['jquery']
 //    knockout: [ "jquery"],
 //   knockout: [ "jquery"],
 //   "jquery.cookie": ["jquery"]
 
 // 'jquery.validate': ['jquery'],
 // 'jquery.ui': ['jquery'],
 // 'jquery.validate.unobtrusive': ['jquery','jquery.validate'],
 // 'sammy': ['jquery']
 }
 
 
 });
 
 // Require that pre-requisites be loaded immediately, before anything else
 // ToDo: Pare back the ones that don't have plugins?
 requirejs([
 // 3rd party libraries
 'jquery',
 'knockout',
 'knockout-postbox'//,
 // 'imgareaselect',
 // 'plupload'
 //'json2',
 //underscore',
 //'moment',
 //'sammy',
 //'amplify',
 //'toastr'
 
 // 'domReady!'   
 
 ],
 function(
 $,
 ko
 // ,imgareaselect              
 //,plupload
 ) {
 // ensure KO is in the global namespace ('this')
 if (!this.ko) {
 this.ko = ko;
 }
 ;
 
 */
// Require that plugins be loaded, after the prerequisite libraries
// We load the plugins here and now so that we don't have to
// name them specifically in the modules that use them because
// we don't want those modules to know that they use plugins. 
define('jquery', [], function() {
    return $;
});
define('knockout', [], function() {
    return ko;
});








require(['durandal/system', 'durandal/app', 'durandal/viewLocator'
], function(system, app, viewLocator) {

 //adding function to ko.observableArray(),ko.observable().
    ko.observableArray.fn.refresh = function() {
        // var data = this().slice(0);
        // this([]);
        // this(data);
        var data = this()/*.slice()*/;
        this(null);
        this(data);
    };
    
    


    //>>excludeStart("build", true);
    system.debug(true);
    //>>excludeEnd("build");

    app.title = 'tuShop';

//specify which plugins to install and their configuration
    app.configurePlugins({
        //router: true,//TODO Router From director.js is now used.
        dialog: true,
      widget: true
    });

    app.start().then(function() {
        //Replace 'viewmodels' in the moduleId with 'views' to locate the view.
        //Look for partial views in a 'views' folder in the root.
        viewLocator.useConvention();

        //configure routing
        //  router.useConvention();

        // router.mapNav("viewmodels/menu");


        // router.mapNav("movies/add");
        // router.mapNav("movies/details/:id");

        //  app.adaptToDevice();


        //Show the app by setting the root view model for our application.
        app.setRoot('viewmodels/shell');
    });

}

);


// });

