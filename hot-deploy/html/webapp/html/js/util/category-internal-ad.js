$(function(){
    var urlMatch = window.location.href.match(/\/((?:cardstock|paper))$/);
    if (urlMatch != null && urlMatch.length > -1) {
        // console.log(" URL = " + urlMatch[1]);
        $( "#env-internal-ad" ).css( "display", "block" );
    }
});