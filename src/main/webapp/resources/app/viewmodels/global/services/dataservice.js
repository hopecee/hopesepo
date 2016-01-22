define(['dojo/i18n!app/nls/userSearchTypes',
    'dojo/dom', 'global/services/logger', 'global/services/security', 'global/services/functionz'],
        function( userSTypes, dom, logger, security, fxz) {
            "use strict";

            var jData = {};
            /*
             function addRequestVerificationToken(jData) {
             //Append RequestVerificationToken value to the jData before posting.
             jData['RequestVerificationToken'] = $('input[name="__RequestVerificationToken"]').val();
             }
             function setMethod(jData, method) {
             jData['method'] = method;
             }
             */
            var dataservice = function() {
            };


            dataservice.prototype.getAllCountries = function(self) {
                security.getAllCountries(self);
            };

            dataservice.prototype.getAllQuestions = function(self) {
               security.getAllQuestions(self); 
            };


            dataservice.prototype.getAllSearchTypes = function(self) {
                var that = self,
                        array = [],
                        userSTypeObj = {};
                $.each(userSTypes, function(id, value) {
                    // alert(key.indexOf("y") > -1);//to check whether strng contains chr.
                    if ((id.indexOf("$") < 0) && (id.indexOf("__") < 0)) { //exclude strings that contains "$" and "__".
                        userSTypeObj[value] = id;//reverse, the value becomes the id.
                    }
                });
                $.each(userSTypeObj, function(id, value) {
                    // push the string to display which is the id.
                    array.push(id);
                });
                that.types(array);
                that.typesObj(userSTypeObj);
            };







            return dataservice;
        });