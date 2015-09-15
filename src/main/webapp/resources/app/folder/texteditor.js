define(['plugins/http', 'durandal/app', 'knockout'], function (http, app, ko) {
    //Note: This module exports an object.
    //That means that every module that "requires" it will get the same object instance.
    //If you wish to be able to create multiple instances, instead export a function.
    //See the "welcome" module for an example of function export.

    
        disabled = ko.observable(false);
        editorMode = ko.observable('wysiwyg');
        showPathSelector = ko.observable(true);
        mode = ko.observable('ribbon');
        showFooter = ko.observable(true);
        text = ko.observable("text");
           
    return {
        displayName: 'Wijmo',

        canDeactivate: function () {
            //the router's activator calls this function to see if it can leave the screen
            return app.showMessage('Are you sure you want to leave this page?', 'Navigate', ['Yes', 'No']);
        }
    };
});