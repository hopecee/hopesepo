define(['plugins/router', 'global/session'//, //'services/security', 
            // 'knockout', 
            // 'jquery'
            //, 'jquery.utilities'
],
        function(router, session//, //security,  // ko, // $
                ) {

            var stickyheaderSetter = {
                set: set
            };
            return stickyheaderSetter;

            function set() {
                // Check veiwpiont before setting the Stikcky height.
                var menuHeight = 40;
                var oHH = 40;//(oficialheaderHeight) get it from header.html (height: 40px).
                var sPad = 2;//(spanPadding) get it from header.html (<span> 2px).
                var h, hi, hOpen, hiOpen, extrH, extrHOpen = 0;
               // alert('session.isLoggedIn : ' + session.isLoggedIn());
                if (!session.isLoggedIn()) {
                    if ($('#headContainer').width() <= 360) {
                        h = $('#headContainer').height();
                        //alert(h);
                        hi = $('.headerInlineblock').height();
                        //alert('hi :' + hi);
                        if (hi > 0) {
                            extrH = hi - oHH + sPad;
                        }
                        //alert('extrH :' + extrH);
                        ko.observable(oHH + extrH + (menuHeight * 2) + 'px').publishOn('HEADER_HEIGHT');
                    } else {
                        h = $('#headContainer').height();
                        hi = $('.headerInlineblock').height();
                        //alert('hi :' + hi);
                        if (hi > 0) {
                            extrH = hi - oHH + sPad;
                        }
                        //alert(h);
                        ko.observable(oHH + extrH + menuHeight + 'px').publishOn('HEADER_HEIGHT');
                    }
                    // Check veiwpiont through 'Animation' before changing the Stikcky height.
                    $('body').on('animationend', function(event) {
                        //var totalHeight;
                        if (event.originalEvent.animationName === 'max-width-360px') {
                            h = $('#headContainer').height();
                            //alert('b ' + h);
                            hi = $('.headerInlineblock').height();
                            //alert('hi :' + hi);
                            if (hi > 0) {
                                extrH = hi - oHH + sPad;
                            }
                            // alert('extrH :' + extrH);
                            //totalHeight = (oHH + (extrH) + (menuHeight * 2));// + totalMenuHeight;
                            // alert(totalHeight);
                            ko.observable(oHH + extrH + (menuHeight * 2) + 'px').publishOn('HEADER_HEIGHT');
                            //document.body.innerHTML = 'Min width is 360px';
                        } else if (event.originalEvent.animationName === 'min-width-800px') {
                            h = $('#headContainer').height();
                            //alert('b ' + h);
                            hi = $('.headerInlineblock').height();
                            //alert('hi :' + hi);
                            if (hi > 0) {
                                extrH = hi - oHH + sPad;
                            }
                            //alert('extrH :' + extrH);
                            //totalHeight = (oHH + extrH + menuHeight);// + totalMenuHeight;
                            //alert(totalHeight);
                            ko.observable(oHH + extrH + menuHeight + 'px').publishOn('HEADER_HEIGHT');

                            //document.body.innerHTML = 'Min width is 800px';
                        }
                    });
                } else {
                    // alert('session.isLoggedIn else : ' + session.isLoggedIn());
                    hOpen = $('#headContainer').height();
                    //alert('b ' + h);
                    hiOpen = $('.headerInlineblock').height();
                    //alert('hi :' + hi);
                    if (hiOpen > 0) {
                        extrHOpen = hiOpen - oHH + sPad;
                    }
                    //alert('extrH :' + extrH);
                    //totalHeight = (oHH + extrH + menuHeight);// + totalMenuHeight;
                    //alert(totalHeight);
                    ko.observable(oHH + extrHOpen + menuHeight + 'px').publishOn('HEADER_HEIGHT');

                    // Check veiwpiont through 'Animation' before changing the Stikcky height.
                    $('body').on('animationend', function(event) {
                        //alert('totalHeight');
                        if (event.originalEvent.animationName === 'max-width-360px') {
                            h = $('#headContainer').height();
                            // alert('b ' + h);
                            hi = $('.headerInlineblock').height();
                            //alert('hi :' + hi);
                            if (hi > 0) {
                                extrH = hi - oHH + sPad;
                            }
                            // alert('extrH :' + extrH);
                            //totalHeight = (oHH + (extrH) + (menuHeight * 2));// + totalMenuHeight;
                            // alert(totalHeight);
                            ko.observable(oHH + extrH + (menuHeight) + 'px').publishOn('HEADER_HEIGHT');
                            //document.body.innerHTML = 'Min width is 360px';
                        } else if (event.originalEvent.animationName === 'min-width-800px') {
                            h = $('#headContainer').height();
                            //alert('b ' + h);
                            hi = $('.headerInlineblock').height();
                            //alert('hi :' + hi);
                            if (hi > 0) {
                                extrH = hi - oHH + sPad;
                            }
                            //alert('extrH :' + extrH);
                            //totalHeight = (oHH + extrH + menuHeight);// + totalMenuHeight;
                            //alert(totalHeight);
                            ko.observable(oHH + extrH + menuHeight + 'px').publishOn('HEADER_HEIGHT');

                            //document.body.innerHTML = 'Min width is 800px';
                        }
                    });
                }
            }
        });



