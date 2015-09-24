
define(['dojo/i18n!app/nls/constants', 'durandal/app', 'global/services/session'], function(constants, app, session) {


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


    var userphotoPanelImg = function() {
        var observable = ko.observable().subscribeTo('USER_NEO4J_ID_STR');
        return   ko.pureComputed({
            read: function() {
                return  constants.userImgFolder + observable() + '/' + observable() + constants.extJPG;
            }
        });

    };

    return {
        attached: attached,
        userphotoPanelImg: userphotoPanelImg(),
        activeEditorLink: ko.observable().publishOn('ACTIVE_EDITOR'),
         userImgFolder2: ko.observable('13421').publishOn('USER_NEO4J_ID_STR'),
        onMyPhoto: function() {
            this.activeEditorLink('viewmodels/user_views/editor_panel/user_photo_editor');
        },
        onCustomers: function() {
            this.activeEditorLink('viewmodels/user_views/editor_panel/user1_photo_editor');
        }
    };


});
