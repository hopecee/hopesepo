define(['durandal/app'], function (app) {

    // because alert is removed from the dom when dismissed, there must be a template
    var template =
        '<div id={{id}} class="alert alert-{{level}} alert-dismissible fade in" role="alert">' +
        '<button type="button" class="close" data-dismiss="alert" aria-label="Close"> <span aria-hidden="true">&times;</span></button>' +
        '{{text}}</div>';

    // setup event listener. use durandal's app events
    function onMsg(id, level, self) {
        // the event will be in the format id:level
        app.on(id + ':' + level).then(function (msg) {
            // get a unique id
            var alertId = id + new Date().getTime();
            // render the template
            var rendered = template.replace("{{level}}", level).replace("{{text}}", msg).replace('{{id}}', alertId);
            // push to the observable array
            self.content.push(rendered);
            // auto-close
            setTimeout(function () { return $('#' + alertId, self.$elem).alert('close'); }, 3500);
        });
    };

    function constructor() {
        this.content = ko.observableArray();
    }

    // on activation, setup event listeners
    constructor.prototype.activate = function (settings) {
        onMsg(settings.id, 'success', this);
        onMsg(settings.id, 'info', this);
        onMsg(settings.id, 'warning', this);
        onMsg(settings.id, 'danger', this);
    };

    // hook up to the durandal lifecycle to get the dom node
    constructor.prototype.bindingComplete = function (domNode) {
        this.$elem = domNode;
    };

    return constructor;
});