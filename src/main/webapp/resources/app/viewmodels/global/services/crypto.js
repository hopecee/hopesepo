define(["dojo/on", 'global/services/session', 'global/services/logger', 'global/services/stickyheaderSetter', 'global/services/busy'],
        function(on, session, logger, stickyheaderSetter, _busy) {



            var crypto = {
                en: en,
                de: de
            };

            return crypto;

            function en(jData, k) {
               
                $.each(jData, function(id, value) {
                   // alert(id + " : " + value);
                    var d = $.jCryption.encrypt(value, k);
                    jData[id] = d;
                });
                 //Add control to show that the data is encrypted.
                jData["en"] = true;
               // $.each(jData, function(id, value) {
                  //  alert(id + " : " + value);
                    //var d = $.jCryption.encrypt(value, k);
                    //jData.id = d;
               // });
               // de(jData, k);
            }

            function de(jData, k) {
                $.each(jData, function(id, value) {
                   // alert(id + " : " + value);
                    var d = $.jCryption.decrypt(value, k);
                    jData[id] = d;
                });
                //$.each(jData, function(id, value) {
                  //  alert(id + " := " + value);
                    //var d = $.jCryption.encrypt(value, k);
                    //jData.id = d;
                //});
            }



        });