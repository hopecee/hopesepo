define([], function() {
    "use strict";
    var imageSelectService = {
        userPhoto: userPhoto,
        remove: remove
    };
    return imageSelectService;

    function userPhoto(imgElement, preview) {
        $(imgElement).imgAreaSelect({instance: true, x1: 30, y1: 5, x2: 110, y2: 105,
            aspectRatio: '8:10',
            handles: true, show: true,
            persistent: true,
            fadeSpeed: 200, onInit: preview, onSelectChange: preview
        });
    }

    //remove imgareaselect.
    function remove() {
        $(".imgareaselect-selection").parent().remove();
        $(".imgareaselect-outer").remove();
    }

});

