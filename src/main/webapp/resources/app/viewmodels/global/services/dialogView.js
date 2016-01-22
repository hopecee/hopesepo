define([], function() {
    "use strict";



    return {
        alert: alert
    };


    function alert(dialogTitle, dialogText, dialogTimeRemaining, dialogActionText, data) {
        var dialogContent = "<div id='idletimer_warning_dialog'><p>" + dialogText + "</p>";
        if (dialogTimeRemaining) {
            dialogContent += "<p style='display:inline'>" + dialogTimeRemaining + ": <div style='display:inline;font-weight: bold;' id='countdownDisplay'></div></p>";
        }
        if (dialogActionText) {
            dialogContent += "<p>" + dialogActionText + "</p>";
        }
        dialogContent += "</div>";


        var obj = {};
        obj.closeOnEscape = false;
        obj.modal = true;
        obj.position = {my: "center", at: "top ", of: "body"};
        obj.open = function() {
            $(this).closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
        };
        if (dialogTitle !== null) {
            obj.title = dialogTitle;
            document.title = dialogTitle;
        }

        $.each(data, function(id, value) {
            if (id === "buttons") {
                obj.buttons = [];
                $.each(value, function(id, value) {
                    var o = {};
                    $.each(value, function(id, value) {
                        o[id] = value;
                    });
                    obj.buttons.push(o);
                });
            }
        });



        $(dialogContent).dialog(obj);/*
         
         var dialogContent = "<div id='idletimer_warning_dialog' ><p>" + currentConfig.dialogText + "</p><p>" + currentConfig.dialogContinueText + "</p></div>";
         $(dialogContent).dialog({
         buttons: [{
         text: currentConfig.dialogOKButton,
         click: function() {
         $(this).dialog("close");
         }
         }
         ],
         closeOnEscape: false,
         modal: true,
         title: currentConfig.dialogTitle,
         position: {my: "center", at: "top ", of: "body"},
         open: function() {
         $(this).closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
         }
         });
         document.title = currentConfig.dialogTitle;
         */
    }

});
