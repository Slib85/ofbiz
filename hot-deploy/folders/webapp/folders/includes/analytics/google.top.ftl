<#assign serverRootURL = request.getServerName()?if_exists />

<!-- Google Tag Manager -->
<script>
    var rootURL = "${serverRootURL?default("")}";
    var currentview = '${currentView?default("_NA_")}';
    dataLayer = [{
        'currentview': '${currentView?default("_NA_")}'
    }];
</script>
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-N93M3PQ"
height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-N93M3PQ');</script>
<!-- End Google Tag Manager -->

<!-- Google Analytics -->
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

    ga('create', '<#if serverRootURL?exists && !serverRootURL?contains("www.folders.com")>UA-26298423-X<#else>UA-26298423-1</#if>', 'auto', {'siteSpeedSampleRate': 35});
    ga('require', 'linkid');
    ga('require', 'ec');
    ga('require', 'GTM-TJB7V6J');

    <#if currentView?default("_NA_") != "receipt">
        ga('send', 'pageview');
    </#if>
</script>
<!-- End Google Analytics -->