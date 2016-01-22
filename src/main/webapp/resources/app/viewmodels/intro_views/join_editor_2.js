

define(['dojo/i18n!app/nls/labels',
    'dojo/dom', 'dojo/dom-construct',
    'plugins/router', 'global/services/session',
    'global/services/security',
    'global/services/layout',
    'global/services/logger',
    'global/services/dataservice', 'global/services/functionz'],
        function(labels, dom, domConstruct, router, session,
                security, layout, logger, dataservice, fxz) {
            "use strict";


            // validate joinEditorform form on keyup and submit
            var doValidation = function() {
                $.validator.setDefaults({
                    submitHandler: function() {
                        // alert("submitted!");
                    },
                    showErrors: function(map, list) {
                        // there's probably a way to simplify this
                        var focussed = document.activeElement;
                        if (focussed && $(focussed).is("input, textarea")) {
                            $(this.currentForm).tooltip("close", {currentTarget: focussed}, true);
                        }

                        this.currentElements.removeAttr("title").removeClass("error");
                        $.each(list, function(index, error) {
                            $(error.element).attr("title", error.message).addClass("tooltip").addClass("error");
                        });
                        if (focussed && $(focussed).is("input, textarea")) {
                            $(this.currentForm).tooltip("open", {target: focussed});
                        }
                    }
                });


                // use custom tooltip; 
                $("#joinEditorContactform").tooltip({
                    position: {
                        my: "left top+5",
                        at: "left bottom"
                    },
                    show: {
                        effect: "slideDown",
                        delay: 250
                    }
                });
                $("#joinEditorContactform").validate({
                    errorClass: "error",
                    validClass: "valid",
                    rules: {
                        'street': {
                            required: true
                        },
                        'street2': {
                            required: false
                        },
                        'city': {
                            required: true
                        },
                        'state': {
                            required: true
                        },
                        'zip': {
                            required: true,
                            number: true
                        },
                        'country': {
                            required: true
                        },
                        'fax': {
                            required: false
                        },
                        'url': {
                            url: true,
                            required: false
                        },
                        'phoneDefault': {
                            required: true,
                            number: true
                        },
                        'phone2': {
                            required: false,
                            number: true
                        },
                        'occupation': {
                            required: true
                        }
                    },
                    messages: {
                        'url': {
                            'url': "Please enter a valid URL. e.g, http://hope-cee.com"
                        },
                        'phoneDefault': {
                            'number': "Please enter a valid Phone number."
                        },
                        'phone2': {
                            'number': "Please enter a valid Phone number."
                        }
                    }
                });
            };



            var attached = function(view, paren) {
                var layoutV = new layout();
                layoutV.setInputStyle();//TODO should it be at the bottom element?

                //var labelemailNode = dom.byId('labelemail');
                // domConstruct.place('<span>' + labels.emailAddress + '</span>', dom.byId('labelemail'));
                dom.byId('joinEditor2Greeting').innerHTML = labels.thanksForReg;
                dom.byId('userContactHeader').innerHTML = labels.userContactHeader;
                dom.byId('street').placeholder = labels.userStreet;
                dom.byId('street2').placeholder = labels.userStreet2;
                dom.byId('city').placeholder = labels.userCity;
                dom.byId('state').placeholder = labels.userState;
                dom.byId('zip').placeholder = labels.userZip;
                // dom.byId('country').innerHTML = labels.userCountry; 'Select Country...'

                dom.byId('fax').placeholder = labels.userFax;
                dom.byId('url').placeholder = labels.userUrl;
                dom.byId('phoneDefault').placeholder = labels.userPhoneDefault;
                dom.byId('phone2').placeholder = labels.userPhone2;
                dom.byId('occupation').placeholder = labels.userOccupation;
                dom.byId('btnContinue').innerHTML = labels.btnContinue;




                /*
                 //hide label text and change input value to label text.
                 $('label.inputWithLabel').each(function() {
                 var label = $(this);
                 var input = $('#' + label.attr('for'));
                 var initial = label.hide().text().replace(':', '');
                 // var placeholder = input.atr('placeholder',initial);
                 input.focus(function() {
                 // alert(input.attr('class').match('error'));
                 // if(input.attr('class').match('error')=='error'){alert(input.css('background-color'));};
                 input.css('color', '#000');
                 input.removeAttr('placeholder');
                 if (input.val() === initial) {
                 input.val('');
                 }
                 }).blur(function() {
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
                 input.addClass('inputOnmouseOver');
                 }).mouseout(function() {
                 input.removeClass('inputOnmouseOver');
                 });
                 });
                 
                 //No cache for input field.
                 $('input.noCache').each(function() {
                 $(this).val('');
                 });
                 */
            };



            var activate = function() {
                var service = new dataservice();
                service.getAllCountries(this);
                /*
                 app.trigger('authentication.loggedIn', {
                 firstTopPanel: "loggedIn",
                 showMenuLogin: false,
                 showHeader: true
                 });
                 */

                // setTimeout(app.trigger('authentication.loggedIn',function(){firstTopPanel: 'loggedIn'}),30000);

//setTimeout(function(){alert('Hello')},30000);


                /*
                 var service = new Backend();
                 return service.getCountries().then(function(results)
                 {
                 alert(results);
                 var array = [];
                 $.each(results, function(id, value) {
                 array.push(value);
                 // alert(value);
                 });
                 alert('results'+results);
                 that.countries(results);
                 });
                 
                 
                 */
            };
            var postedCountry = function() {
                var obj = {};
                $.each(this.countriesObj(), function(data, value) {
                    obj[data] = value;
                });
                return obj[this.selectedCountry()];
            };



            return {
                /// displayName: 'What is your name?',
                //firstTopPanel: ko.observable('Hello, world!'),
                // firstTopPanel2: ko.observable(),
                attached: attached,
                activate: activate,
                street: ko.observable(),
                street2: ko.observable(),
                city: ko.observable(),
                state: ko.observable(),
                zip: ko.observable(),
                selectCountry: ko.observable(labels.userSelectCountry),
                countries: ko.observableArray([]),
                countriesObj: ko.observableArray([]),
                selectedCountry: ko.observable(),
                postedCountry: postedCountry,
                fax: ko.observable(),
                url: ko.observable(),
                phoneDefault: ko.observable(),
                phone2: ko.observable(), // initially false. 
                occupation: ko.observable(),
                onSubmit: onSubmit
            };


            function onSubmit() {
                fxz.preventDbClick("onSubmit",10);
                doValidation();
                var jData = {
                    street: this.street(),
                    street2: this.street2(),
                    city: this.city(),
                    state: this.state(),
                    zip: this.zip(),
                    postedCountry: this.postedCountry(),
                    fax: this.fax(),
                    url: this.url(),
                    phoneDefault: this.phoneDefault(),
                    phone2: this.phone2(),
                    occupation: this.occupation()
                }; // prepare request data
                var url = '/tuJoinEditorServlet';

                //router.navigate('#/join_editor_3');
                //validate data first.
                if ($("#joinEditorContactform").valid()) {
                    //var busy = getBusyOverlay(document.getElementById('joinEditor2Busy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 50, type: 'o'});
                    ko.postbox.publish('JOINT_EDITOR_2_BUSY_START', null);

                    security.joinEditor2(url, jData);
                    /*
                     //post data.
                     $.ajax({
                     type: 'POST',
                     url: '/joinEditor2Servlet',
                     data: jData,
                     // dataType: 'JSON',
                     success: function() {
                     //alert("submitted!");
                     busy.remove();
                     // alert("submitted!");
                     
                     router.navigate('#/join_editor_3');
                     
                     // $(".reload").click(); // what is this?
                     }
                     
                     });
                     */
                    // return true;
                } else {
                    // Display an error toast, with a title
                    logger.log({
                        message: "Please fill the Form correctly and continue.",
                        showToast: true,
                        type: "error"
                    });
                    // return false;
                }
            }




        });
