
define(['durandal/app', 'global/session'], function(app, session) {


    //var start = function() {
    $(document).ready(function() {

    });



    var loggedInHandler = function(self) {
        app.on('authentication.loggedIn').then(function(data) {
            //alert(data.firstTopPanel);
            self.firstTopPanel(data.firstTopPanel);
        });
    };



    var attached = function(view, paren) {

        var optionStore = {};

        optionStore.slideMenu = {
            orientation: "vertical",
            mode: "sliding"
        };
        optionStore.toolbarMenu = {
            orientation: "horizontal",
            trigger: ".wijmo-wijmenu-item",
            triggerEvent: "mouseenter"
        };

        var animationStore = {};
        animationStore.toolbarMenu = {
            animated: "slide",
            duration: 400,
            easing: null
        };


        //Menu
        //$("#mainmenu").wijmenu();
        //$("#mainmenu").wijmenu(optionStore.slideMenu);
        //$("#mainmenu").wijmenu(optionStore.toolbarMenu);
        $("#flyoutmenu2").wijmenu(optionStore.toolbarMenu);

        //alert("home loaded!");

        $("#flyoutmenu2").removeClass("ui-helper-hidden-accessible");







        //hide label text and change input value to label text.
        $('label.inputWithLabel').each(function() {
            var label = $(this);
            var input = $('#' + label.attr('for'));


            var initial = label.hide().text().replace(':', '');
            // var placeholder = input.atr('placeholder',initial);
            input.focus(function() {
                input.css('color', '#000');
                input.removeAttr('placeholder');
                if (input.val() === initial) {
                    input.val('');

                }
            })
                    .blur(function() {
                if (input.val() === '') {
                    input.attr('placeholder', initial).css('color', '#000');
                    // .attr('placeholder',labelText);
                }
            }).css('color', '#000').attr('placeholder', initial);
        });

        //input backgrand colour on mouseover and mouseout.
        $('input.bgColour').each(function() {
            var input = $(this);
            input.mouseover(function() {
                input.css('background-color', '#ffffcc');
            }).mouseout(function() {
                input.css('background-color', '#fff');
            });
        });

        //No cache for input field.
        $('input.noCache').each(function() {
            $(this).val('');
        });




    };



    return {
        attached: attached,
        userphotoPanelImg: ko.observable().subscribeTo('USER_IMG'),
        activeEditorLink: ko.observable().publishOn('ACTIVE_EDITOR'),
        onMyPhoto: function() {
            this.activeEditorLink('viewmodels/user_views/editor_panel/user_photo_editor');
        },
        onCustomers: function() {
            this.activeEditorLink('viewmodels/user_views/editor_panel/user1_photo_editor');
        }
    };


});
