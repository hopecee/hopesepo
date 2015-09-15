define(['dojo/i18n!app/nls/notes',
    'dojo/dom', 'dojo/dom-construct'
], function(notes, dom, domConstruct) {
    "use strict";

    var attached = function() {
        dom.byId('intro').innerHTML = notes.intro;

    };

    return {
        attached: attached
    };
});