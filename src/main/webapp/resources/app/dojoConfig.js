
/*
 /alert('kdojoConfigl' );
 function gup(name) // from Netlobo.com
 {
 name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
 var regexS = "[\\?&]"+name+"=([^&#]*)";
 var regex = new RegExp( regexS );
 var results = regex.exec( window.location.href );
 if( results == null ){
 alert("s "+results);
 return "";
 } else{
 alert("surrrho "+results);
 return results[1];
 }
 }
 //Get url
 var ln = 'de';
 var languageSetting = gup('language');
 if (languageSetting != null && languageSetting == 'de') {
 ln = 'de';
 }
 */



if (typeof window !== 'undefined') {
    var language = window.navigator.languages ? window.navigator.languages[0]: ( window.navigator.userLanguage || window.navigator.language);
 
     var req = new XMLHttpRequest();
    req.open('GET', 'tuLocaleManagerServlet', false);
    req.send(null);
    //alert(req.getResponseHeader('Content-language'));
//alert(req.getResponseHeader('Content-language'));
//alert(req.getResponseHeader('control'));
//alert(req.getAllResponseHeaders());
//alert(req.getResponseHeader('control'));
var respH = req.getResponseHeader('control');
if((respH != 'control')&&(respH != null)){
  language =  req.getResponseHeader('control')|| req.getResponseHeader('Content-language');
  // alert('window.navigator.languages' ); 
}

   
 // We're running inside a browser.
//window.dojoConfig = window.dojoConfig || {};
//window.dojoConfig.locale = language.toLowerCase() || window.dojoConfig.locale;
//alert("language4 : " + window.dojoConfig.locale);
//alert("location : " + language.toLowerCase());
var locale = language.toLowerCase();
}






var dojoConfig = {
    baseUrl: '/faces/javax.faces.resource/', //  baseUrl: './', TODO find the correct path HOPE
    // baseUrl: '.', //  baseUrl: './', TODO find the correct path HOPE
    async: true,
    // Enable debugging
    isDebug: true,
    // Fix the loader to use normal AMD resolution of unregistered module paths (relative to `baseUrl`)
// instead of the legacy Dojo resolution method (relative to the parent directory of `baseUrl`)
    tlmSiblingOfDojo: false,
    // tlmSiblingOfDojo: true,
// Use the smaller, faster "lite" CSS selector engine, which works in all browsers IE8+
    selectorEngine: 'lite',
    parseOnLoad: true,
    locale: locale,
    //extraLocale: ['fr,de'],
    aliases: [['text', 'dojo/text']],
    lib: '/faces/javax.faces.resource/app/lib',
    // Register the packages we are going to be using. These same packages should be defined in the
    // build profile in `app.profile.js`.
    packages: [
        // {name: 'dojo', location: 'app/lib/dojo/1.10.0/dojo'},
        {name: 'dojo', location: 'app/lib/dojo/dojo-release-1.10.4/dojo'},
        {name: 'dojox', location: 'app/lib/dojo/dojo-release-1.10.4/dojox'},
        {name: 'dijit', location: 'app/lib/dojo/dojo-release-1.10.4/dijit'},
        {name: 'app', location: 'app'},
        
        //  {name: 'jquery', location: 'app/lib/wijmo/jquery-1.9.1.min'},

        //  {name: 'jquery-ui', location: 'app/lib/wijmo/jquery-ui-1.10.1.custom.min'},
        // {name: 'jquery.wijmo-open.all', location: 'app/lib/wijmo/jquery.wijmo-open.all.2.3.1.min'},
        //  {name: 'knockout', location: 'app/lib/knockout/knockout-3.2.0'},
        // {name: 'knockout-postbox', location: 'app/lib/knockout/knockout-postbox.min'},
        
        {name: 'durandal', location: 'app/lib/durandal/js'},
        {name: 'plugins', location: 'app/lib/durandal/js/plugins'},
        {name: 'transitions', location: 'app/lib/durandal/js/transitions'},
        {name: 'views', location: 'app/views'},
        {name: 'viewmodels', location: 'app/viewmodels'},
        {name: 'customjs', location: 'app/viewmodels/customjs'},
        {name: 'global', location: 'app/viewmodels/global'}
        //{name: 'knockout', location: 'app/lib/knockout/knockout-2.3.0'}
        //   {name: 'jquery.bgiframe', location: 'app/lib/wijmo/jquery.bgiframe'},
        //  {name: 'jquery.cookie', location: 'app/lib/wijmo/jquery.cookie'},
        // {name: 'jquery.validate', location: 'app/lib/wijmo/jquery.validate'},
        //  {name: 'jquery.mousewheel', location: 'app/lib/wijmo/jquery.mousewheel.min'},
        //  {name: 'globalize', location: 'app/lib/wijmo/globalize.min'},
        //  {name: 'knockout.wijmo', location: 'app/lib/wijmo/knockout.wijmo'},
        //  {name: 'wijmo.widget', location: 'app/lib/wijmo/wijmo.widget'},
        //  {name: 'wijmo.wijpager', location: 'app/lib/wijmo/wijmo.wijpager'},
        //  {name: 'wijmo.data', location: 'app/lib/wijmo/wijmo.data'},
        //  {name: 'wijmo.wijinputcore', location: 'app/lib/wijmo/wijmo.wijinputcore'},
        //  {name: 'wijmo.wijinputnumberformat', location: 'app/lib/wijmo/wijmo.wijinputnumberformat'},
        //  {name: 'wijmo.wijinputdateformat', location: 'app/lib/wijmo/wijmo.wijinputdateformat'},
        //  {name: 'wijmo.wijinputtextformat', location: 'app/lib/wijmo/wijmo.wijinputtextformat'},
        //  {name: 'wijmo.wijcharex', location: 'app/lib/wijmo/wijmo.wijcharex'},
        //  {name: 'wijmo.wijpopup', location: 'app/lib/wijmo/wijmo.wijpopup'},
        //  {name: 'wijmo.wijtooltip', location: 'app/lib/wijmo/wijmo.wijtooltip'},
        // {name: 'wijmo.wijtouchutil', location: 'app/lib/wijmo/wijmo.wijtouchutil'},
        // {name: 'wijmo.wijcalendar', location: 'app/lib/wijmo/wijmo.wijcalendar'},
        // {name: 'wijmo.wijinputdate', location: 'app/lib/wijmo/wijmo.wijinputdate'},
        //  {name: 'wijmo.wijinputtext', location: 'app/lib/wijmo/wijmo.wijinputtext'},
        //  {name: 'wijmo.wijinputnumber', location: 'app/lib/wijmo/wijmo.wijinputnumber'},
        //  {name: 'wijmo.wijutil', location: 'app/lib/wijmo/wijmo.wijutil'},
        //  {name: 'wijmo.wijlist', location: 'app/lib/wijmo/wijmo.wijlist'},
        //   {name: 'wijmo.wijsuperpanel', location: 'app/lib/wijmo/wijmo.wijsuperpanel'},
        //  {name: 'wijmo.wijgrid', location: 'app/lib/wijmo/wijmo.wijgrid'},
        //  {name: 'wijmo.wijcombobox', location: 'app/lib/wijmo/wijmo.wijcombobox'},
        // {name: 'wijmo.wijeditor', location: 'app/lib/wijmo/wijmo.wijeditor'},
        // {name: 'wijmo.wijsplitter', location: 'app/lib/wijmo/wijmo.wijsplitter'},
        ///  {name: 'wijmo.wijdialog', location: 'app/lib/wijmo/wijmo.wijdialog'},
        //  {name: 'wijmo.wijribbon', location: 'app/lib/wijmo/wijmo.wijribbon'},
        //  {name: 'wijmo.wijtabs', location: 'app/lib/wijmo/wijmo.wijtabs'},
        //  {name: 'wijmo.wijmenu', location: 'app/lib/wijmo/wijmo.wijmenu'},
        //  {name: 'jquery.stickyPanel', location: 'custom/stickyPanel/jquery.stickyPanel.min'},
        //   {name: 'domReady', location: 'app/lib/require/domReady'},
        // {name: 'toastr', location: 'app/lib/toastr/203/toastr'},
        //  {name: 'imgareaselect', location: 'app/lib/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min'},
        // {name: 'plupload', location: 'app/lib/plupload_2_1_2/js/plupload.full.min'}



    ]};



