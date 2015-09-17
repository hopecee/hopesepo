define(['dojo/i18n!app/nls/countryIsoCodes',
    'dojo/dom', 'global/session', 'global/services/logger'],
        function(countryIsoCodes, dom, session, logger) {
            "use strict";


            var pageRouter = function() {
            };


            pageRouter.prototype.intro = function() {
                return  {
                    on: function() {
                        ko.observable('viewmodels/intro').publishOn('ROUTE');
                    }
                };
            };
            pageRouter.prototype.join_editor_2 = function(data) {
                //alert(data);
                return {
                    on: function() {
                        ko.observable('viewmodels/intro_views/join_editor_2').publishOn('ROUTE');
                    },
                    before: function(data) {
                        //if (typeof (session.userEmailAddress()) !== "undefined") {
                        var res = session.isLoggedIn();
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
            };



            pageRouter.prototype.join_editor_3 = function() {
                return {
                    on: function() {
                        ko.observable('viewmodels/intro_views/join_editor_3').publishOn('ROUTE');
                    },
                    before: function(data) {
                        //if (typeof (session.userEmailAddress()) !== "undefined") {
                        var res = session.isLoggedIn();
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
                    }};
            };

            pageRouter.prototype.join_editor_complete = function() {
                return {
                    on: function() {
                        ko.observable('viewmodels/intro_views/join_editor_complete').publishOn('ROUTE');
                    },
                    before: function(data) {
                        //if (typeof (session.userEmailAddress()) !== "undefined") {
                        var res = session.isLoggedIn();
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
                    }};
            };

            pageRouter.prototype.home = function() {
                return {
                    on: function() {
                        ko.observable('viewmodels/user_views/home').publishOn('ROUTE');
                    },
                    before: function(data) {
                        //if (typeof (session.userEmailAddress()) !== "undefined") {
                        var res = session.isLoggedIn();
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
                    }};
            };

            pageRouter.prototype.notfound = function() {
                ko.observable('viewmodels/intro').publishOn('ROUTE');
            };












            return pageRouter;
        });
