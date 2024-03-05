(function() {
    if (typeof window.janrain !== 'object') window.janrain = {};
    if (typeof window.janrain.settings !== 'object') window.janrain.settings = {};

    janrain.settings.providers = ['microsoftonline', 'googleplus'];
    janrain.settings.tokenUrl = 'https://pps.envelopes.com/admin/control/getJanRainToken';
    janrain.settings.tokenAction = 'event';

    function isReady() { janrain.ready = true; };
    if (document.addEventListener) {
        document.addEventListener("DOMContentLoaded", isReady, false);
    } else {
        window.attachEvent('onload', isReady);
    }

    var e = document.createElement('script');
    e.type = 'text/javascript';
    e.id = 'janrainAuthWidget';

    if (document.location.protocol === 'https:') {
        e.src = 'https://rpxnow.com/js/lib/envelopes/engage.js';
    } else {
        e.src = 'http://widget-cdn.rpxnow.com/js/lib/envelopes/engage.js';
    }

    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(e, s);
})();

function janrainWidgetOnload() {
    janrain.events.onProviderLoginToken.addHandler(function(response) {
        if(typeof response.token != 'undefined') {
            $('#token').val(response.token);
            $('#form_login').submit();
        }
    });
}