define(['jquery'], function($) {
    "use strict";

    var backend = function() {
    };
    backend.prototype.getCustomers = function() {
        //do some ajax and return a promise
        return  ['username', 'password', 'password2'];
    };
    backend.prototype.getCountries = function(self) {
        //do some ajax and return a promise
        var that = self;

        /*
         $.getJSON('/countryListServlet', {}, function(data) {
         
         $.each(data, function(id, value) {
         if(id === "data") {
         var array = [];
         $.each(value, function(dataId, value1) {
         array.push(value1);
         });
         that.countries(array);
         }
         if(id === "sessionData") {
         // var array2 = [];
         $.each(value, function(sessionDataId, value2) {
         //array2.push(value2);
         //if(sessionDataId === "Hope") {
         that.sessionDataId(value2);   
         //}
         alert(value2);
         });
         
         }
         
         });
         
         
         
         
         });
         */

        $.post('/tuCountryListServlet', null, function(data) {
            $.each(data, function(id, value) {
                if (id === "data") {
                    var array = [];
                    $.each(value, function(dataId, value1) {
                        array.push(value1);
                    });
                    that.countries(array);
                }
                if (id === "sessionData") {
                    // var array2 = [];
                    $.each(value, function(sessionDataId, value2) {
                        //array2.push(value2);
                        //if(sessionDataId === "Hope") {
                        that.sessionDataId(value2);
                        //}
                        alert(value2);
                    });

                }

            });
        }

        );


    };

    backend.prototype.getQuestions = function(self) {
        //do some ajax and return a promise
        var that = self;
        $.post('/questionListServlet', null, function(data) {
            $.each(data, function(id, value) {
                if (id === "data") {
                    var array = [];
                    $.each(value, function(dataId, dataValue) {
                        array.push(dataValue);
                    });
                    that.questions1(array); //For that.questions1.
                    that.questions2(array); //For that.questions2.
                }
                /*
                if (id === "sessionData") {
                    // var array2 = [];
                    $.each(value, function(sessionDataId, value2) {
                        //array2.push(value2);
                        //if(sessionDataId === "Hope") {
                        that.sessionDataId(value2);
                        //}
                        alert(value2);
                    });

                }
                */

            });
        }

        );


    };








    return backend;
});

