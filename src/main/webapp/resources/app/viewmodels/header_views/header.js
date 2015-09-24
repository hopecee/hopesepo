define(['dojo/i18n!app/nls/labels', 'dojo/i18n!app/nls/locales',
    'dojo/dom', 'dojo/dom-construct', 'dojo/dom-attr',
    'durandal/app', 'global/services/session', 'global/services/security', 'global/services/stickyheaderSetter'],
        function(labels, locales, dom, domConstruct, domAttr, app, session,
                security, stickyheaderSetter) {

            $(document).ready(function() {

            });


            var localeWidget = function localeWidget(locale, value) {
                return {locale: locale, value: value};
            };


            var localeWidgets = function() {
                var arr = [];
                $.each(locales, function(key, value) {
                    if (key.length < 5) {
                        arr.push(new localeWidget(key, value));
                        // alert(key.length + " : " + key + "  :  " + value);

                        // var li = domConstruct.create("li", {id: key}, "dropdownLocale");
                        //var a = domConstruct.create("a", {href: "#", innerHTML: value}, li);

                        // var img = domConstruct.create("img", {src: "/faces/javax.faces.resource/app/lib/images/flags/tb_" + key + ".png",innerHTML: value}, a);
//var span = domConstruct.create("span", {id: key+"-",innerHTML: value}, a);
                        //domConstruct.create("span", {id: key+"-",innerHTML: value}, img);

                        //domAttr.set("span", {innerHTML: value});

                        // var node = domConstruct.toDom("<li id='" + key + "' data-bind='click: onSubmit_" + key + "' ><a  href='#'  ><img  id='imgHome3' src='" + window.dojoConfig.lib + "/images/flags/tb_" + key + ".png' alt='" + key + "'/><span style='padding: 0 0.3em'>" + value + "</span></a></li>");
                        //domConstruct.place(node, "dropdownLocale");
                    }
                });
                return arr;
            };


            var onSubmit = function(data, event) {
                var url = '/tuLocaleManagerServlet';
                var jData = {language: event.currentTarget.id};
                security.localeManager(url, jData, null);
            };


            var attached = function(view, paren) {
                stickyheaderSetter.set();

                dom.byId('imgHome').title = labels.home;
                dom.byId('imgHome2').title = labels.home;
            };

            var compositionComplete = function(view, parent) {

            };

            return {
                //activate: function() {},
                //compositionComplete: compositionComplete,
                attached: attached,
                firstTopPanel: ko.observable().subscribeTo('USER_NEO4J_ID_STR'), //activate:   activate
                localeWidgets: ko.observableArray(localeWidgets()),
                onSubmit: onSubmit
            };





        });
