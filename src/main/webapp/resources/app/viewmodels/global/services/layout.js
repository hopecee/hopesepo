
define([],
        function() {

            var layout = function() {
            };

            layout.prototype.setInputStyle =  function() {
            
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
               
            };
            
            return layout;
        });

