define([], function() {
    "use strict";

    var crypto = {
        en: en,
        de: de,
        enOne:enOne,
        deOne:deOne
    };

    return crypto;

    function en(jData, k) {
        $.each(jData, function(id, val) {
            var value = val;
            // alert(id + " : " + value + " : " + (Object.prototype.toString.call(value)));
            //convert to String, if Boolean.
            if ((Object.prototype.toString.call(value).slice(8, -1) === "Boolean")) {
                value = val.toString();
            }

            //if (typeof value === "object" && !Array.isArray(value) && value !== null) {//Object.
            if ((Object.prototype.toString.call(value).slice(8, -1) === "Object") && (Object.prototype.toString.call(value).slice(8, -1) !== "Array") && value !== null) {//Object.
                $.each(value, function(id2, value2) {
                    var e2 = $.jCryption.encrypt(value2, k);
                    jData[id][id2] = e2;
                });
            } else {
                if ((Object.prototype.toString.call(value).slice(8, -1) !== "Object") && (Object.prototype.toString.call(value).slice(8, -1) === "Array") && value !== null) {//Array.
                    var arr = [];
                    $.each(value, function(id2, value2) {
                        var obj = {};
                        if ((Object.prototype.toString.call(value2).slice(8, -1) === "Object") && (Object.prototype.toString.call(value2).slice(8, -1) !== "Array") && value2 !== null) {//Object.
                            $.each(value2, function(id3, value3) {
                                var e3 = $.jCryption.encrypt(value3, k);
                                obj[id3] = e3;
                            });
                            arr.push(obj);
                        } else {
                            //next  
                        }
                    });
                    jData[id] = arr;
                } else {//String.
                    var e = $.jCryption.encrypt(value, k);
                    jData[id] = e;
                }
            }



        });

        //========================= 
        /*   $.each(jData, function(id, value) {
         if (typeof value === "object" && !Array.isArray(value) && value !== null) {
         $.each(value, function(id2, value2) {
         var e2 = $.jCryption.encrypt(value2, k);
         jData[id][id2] = e2;
         });
         } else {
         var e = $.jCryption.encrypt(value, k);
         jData[id] = e;
         }
         });
         */

        //Add control to show that the data is encrypted.
        jData["en"] = true;
    }

    function de(jData, k) {
        $.each(jData, function(id, value) {
            //if (typeof value === "object" && !Array.isArray(value) && value !== null) {//Object.
            if ((Object.prototype.toString.call(value).slice(8, -1) === "Object") && (Object.prototype.toString.call(value).slice(8, -1) !== "Array") && value !== null) {//Object.
                $.each(value, function(id2, value2) {
                    var d2 = $.jCryption.decrypt(value2, k);
                    jData[id][id2] = d2;
                });
            } else {
                if ((Object.prototype.toString.call(value).slice(8, -1) !== "Object") && (Object.prototype.toString.call(value).slice(8, -1) === "Array") && value !== null) {//Array.
                    var arr = [];
                    $.each(value, function(id2, value2) {
                        var obj = {};
                        if ((Object.prototype.toString.call(value2).slice(8, -1) === "Object") && (Object.prototype.toString.call(value2).slice(8, -1) !== "Array") && value2 !== null) {//Object.
                            $.each(value2, function(id3, value3) {
                                var d3 = $.jCryption.decrypt(value3, k);
                                obj[id3] = d3;
                            });
                            arr.push(obj);
                        } else {
                            //next  
                        }
                    });
                    jData[id] = arr;
                } else {//String.
                    var d = $.jCryption.decrypt(value, k);
                    jData[id] = d;
                }
            }
        });
    }
    
    function enOne(value, k) {
      return  $.jCryption.encrypt(value, k);
    }
    
    function deOne(value, k) {
       return $.jCryption.decrypt(value, k);
    }

});