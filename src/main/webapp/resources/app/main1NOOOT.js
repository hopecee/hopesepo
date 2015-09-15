define('jquery', [], function() {
    return jQuery;
});
define('knockout', [], function() {
     alert('knockout' );
    return ko;
});

 alert('durandal  njhm' );

require(['durandal/system', 'durandal/app', 'durandal/viewLocator'],
     

function(system, app, viewLocator) {
          
    alert('durandal' );
    
    system.debug(false);
            app.start().then(function() {
                viewLocator.useConvention();
                app.setRoot('viewmodels/shell');
            });
        }
);
