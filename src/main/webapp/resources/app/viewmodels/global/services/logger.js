define(['durandal/system'//, 'toastr'
],
        function(system//, toastr 
                ) {
            "use strict";
// Internal properties and functions
            var defaults = {
                source: "app",
                title: "",
                message: "no message provided",
                data: "",
                showToast: true,
                type: "info"
            };
            function init() {

                //In case you want to escape HTML charaters in title and message
                toastr.options.escapeHtml = true;//new
                //Optionally override the close button's HTML.
                //toastr.options.closeHtml = '<button><i class="icon-off"></i></button>';//new
                toastr.options.closeMethod = 'fadeOut';//new
                toastr.options.closeDuration = 300;//new
                toastr.options.closeEasing = 'swing';//new
                toastr.options.newestOnTop = false;//new
                // Define a callback for when the toast is shown/hidden
                // toastr.options.onShown = function() {
                //   console.log('hello');
                // };//new
                // toastr.options.onHidden = function() {
                //    console.log('goodbye');
                //};//new


                toastr.options.closeButton = false;
                toastr.options.positionClass = 'toast-top-left';
                toastr.options.backgroundpositionClass = 'toast-top-left';
                toastr.options.fadeOut = 1000;
                toastr.options.debug = false;
                toastr.options.onclick = null;
                toastr.options.showDuration = 300;
                toastr.options.hideDuration = 1000;
                toastr.options.timeOut = 5000;
                toastr.options.extendedTimeOut = 1000;
                toastr.options.showEasing = "swing";
                toastr.options.hideEasing = "linear";
                toastr.options.showMethod = "fadeIn";
                toastr.options.hideMethod = "fadeOut";

                toastr.options.preventDuplicates = true;//new
                toastr.options.progressBar = true;//new

            }

            init();

            var logger = {
                log: log
            };
            function log(options) {
                var opns = $.extend({}, defaults, options);
                system.log(opns.source + ", " + opns.type + ", " + opns.message + ", " + opns.data + " ");
                if (opns.showToast) {
                    toastr[opns.type](opns.message, opns.title);
                }
            }
            return logger;

        });
