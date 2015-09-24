define(['plugins/router', 'global/services/session'//, //'services/security', 
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
            function koSet(menuLayer) {
                var menuHeight = 2.0625; //33 2.667
                var oHH = 2.5; //40 3.333 (oficialheaderHeight) get it from header.html (height: 40px).
                var sPad = 0.1875; //2 0.167 (spanPadding) get it from header.html (<span> 2px).
                var h, hi, hiR, h2, menuTotalHeight, menuContainer, menuContainerD16, hOpen, hiOpen, extrH, extrHOpen = 0;
                h = $('.header').height();
                //alert(h);
                hi = $('.headerInlineblock').height();
                var menuContainer = $('.menuContainer_1').height();
                //Convert to em @16.
                menuContainerD16 = menuContainer / 16;
                h2 = $('#headContainer').height();
                //alert('h: ' + h + ': hi: ' + hi + ': h2: ' + h2 + ': h2: ' + menuContainer);
                // convert px to em (my base is 12px not 16pc.)
                hiR = hi * (1 / 16);
                // alert('hi :' + hiR);
                if (hiR > 0) {
                    extrH = (hiR - oHH + sPad);
                }

                if (session.mediaViewType() === 2) {
                    if (menuContainer !== null) {
                        if (session.isLoggedIn()) {
                            menuTotalHeight = (menuContainerD16 / 2) - sPad;
                        } else {
                            // menuTotalHeight = ((menuHeight * menuLayer) - sPad);
                            menuTotalHeight = menuContainerD16;
                        }
                    } else {
                        // menuTotalHeight = ((menuHeight * menuLayer) - sPad);
                        menuTotalHeight = ((menuHeight * menuLayer) - sPad);
                    }
                }
                if (session.mediaViewType() === 1) {
                    if (menuContainer !== null) {
                        menuTotalHeight = menuContainerD16;// - sPad;
                    } else {
                        menuTotalHeight = ((menuHeight * menuLayer));
                    }
                }
                return    ko.observable((h / 16) + menuTotalHeight + 'em').publishOn('HEADER_HEIGHT');
            }
            function set() {
                if (!session.isLoggedIn()) {
                    if ($('#headContainer').width() <= 500) {
                        koSet(2);
                    } else {
                        koSet(1);
                    }
                    // Check veiwpiont through 'Animation' before changing the Stikcky height.
                    $('body').on('animationend webkitAnimationEnd ', function(event) {
                        if (event.originalEvent.animationName === 'max-width-500px') {
                            koSet(2);
                        } else{
                            koSet(1);
                        }
                    });
                } else {
                    koSet(1);
            //loggedIn, no need for this.
            // Check veiwpiont through 'Animation' before changing the Stikcky height.
            // $('body').on('animationend ', function(event) {
            //  if (event.originalEvent.animationName === 'max-width-500px') {
            //     koSet(1);
            // } else  {
            //     koSet(1);
            // }
            // });
            
                }
            }
        });



