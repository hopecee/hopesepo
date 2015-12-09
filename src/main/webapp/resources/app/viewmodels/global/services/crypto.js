define([], function() {
    "use strict";
    
    var crypto = {
        en: en,
        de: de
    };

    return crypto;

    function en(jData, k) {
        $.each(jData, function(id, value) {
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
        //Add control to show that the data is encrypted.
        jData["en"] = true;
    }

    function de(jData, k) {
        $.each(jData, function(id, value) {
            if (typeof value === "object" && !Array.isArray(value) && value !== null) {
                $.each(value, function(id2, value2) {
                    var d2 = $.jCryption.decrypt(value2, k);
                    jData[id][id2] = d2;
                });
            } else {
                var d = $.jCryption.decrypt(value, k);
                jData[id] = d;
            }
        });
    }
});