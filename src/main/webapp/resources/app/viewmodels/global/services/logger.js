define(['durandal/system'//, 'toastr'
    ],
        function(system//, toastr
        ) {
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
