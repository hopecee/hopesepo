define(['dojo/i18n!app/nls/countryIsoCodes',
    'dojo/dom', 'global/services/logger'],
        function(countryIsoCodes, dom, logger) {
              "use strict";

            var jData = {};

            function addRequestVerificationToken(jData) {
                //Append RequestVerificationToken value to the jData before posting.
                jData['RequestVerificationToken'] = $('input[name="__RequestVerificationToken"]').val();
            }
            function setMethod(jData, method) {
                jData['method'] = method;
            }

            var dataservice = function() {
            };


            dataservice.prototype.getAllCountries = function(self) {
                //do some ajax and return a promise
                var that = self;
                var url = '/tuCountryListServlet';
                //Append RequestVerificationToken value to the jData before posting.
                addRequestVerificationToken(jData);
                setMethod(jData, "countryList");

                $.post(url, jData, function(data) {
                    $.each(data, function(id, value) {
                        if (id === "BadToken") {
                            alert("BadToken");
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                        } else {
                            if (id === "countryData") {
                                var array = [];
                                var obj = {};
                                $.each(value, function(dataId, value1) {
                                    // push dataId which is the "Country_iso_code_3"
                                    // rather value1 which is "Country_name".
                                    array.push(countryIsoCodes[dataId]);
                                    obj[countryIsoCodes[dataId]] = dataId;
                                });
                                that.countries(array);
                                that.countriesObj(obj);
                            }
                        }
                    });
                }

                );


            };

            dataservice.prototype.getAllQuestions = function(self) {
                //do some ajax and return a promise
                var that = self;
                var url = '/tuQuestionListServlet';
                //Append RequestVerificationToken value to the jData before posting.
                addRequestVerificationToken(jData);
                setMethod(jData, "getAllQuestions");

                $.post(url, jData, function(data) {
                    $.each(data, function(id, value) {
                        if (id === "BadToken") {
                            alert("BadToken");
                            logger.log({
                                message: "Please fill the Form correctly and accept our Terms and Policy. Request Verification",
                                showToast: true,
                                type: "error"
                            });
                        } else {
                            if (id === "data") {
                                var array = [];
                                $.each(value, function(dataId, dataValue) {
                                    array.push(dataValue);
                                });
                                //alert(array);
                                that.questions1(array); //For that.questions1.
                                that.questions2(array); //For that.questions2.
                            }
                        }
                    });
                }
                );
            };
            
            
            
            
            
            
            
            return dataservice;
        });