

define(['dojo/i18n!app/nls/constants', 'dojo/i18n!app/nls/labels',
    'dojo/dom', 'dojo/dom-construct', 'dijit/layout/TabContainer', 'dijit/layout/ContentPane',
    'plugins/router', 'global/services/session',
    'global/services/security',
    'global/services/layout',
    'global/services/logger', 'plugins/widget', 'durandal/app'], function(constants, labels,
        dom, domConstruct, TabContainer, ContentPane,
        router, session, security,
        layout,
        logger, widget, app) {
    "use strict";

    widget.registerKind('expander');

    $(document).ready(function() {


    });


    var activate = function(activationData) {
        alert("id : " + activationData.id);
        // this.that(activationData.id);
   };

    var vc;
    var attached = function(view, parent, settings) {
       // app.trigger('alert1:info', "All good!");
       // alert("tagName : " + $(view).parent().attr('id'));
       // alert("activationData : " + settings.activationData.id);

// alert($(this).prop("tagName"));
        // var v = ;
        // alert($('#userPro').parent().attr('id'));

        //  $.each(settings, function(id, value) {
        //   alert(id+" : "+value);
        //  });

        // this.test: ko.observable(v);

//this.test();

    };
    var test = ko.observable(function() {
        return 'hope';
    });

    var Project = function(name, description) {
        this.name = name;
        this.description = description;
    };

    return {
//activate: activate,
        attached: attached//,
                //userIdSearch: ko.observable('1'),
                //id: ko.observable().subscribeTo("USER"),
                // test: ko.observable('test'),//.subscribeTo("USER")
    , 
name: ko.observable('hope')

    };






});

