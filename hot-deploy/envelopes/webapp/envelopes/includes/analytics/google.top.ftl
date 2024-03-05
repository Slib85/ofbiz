<#assign serverRootURL = request.getServerName()?if_exists />

<!-- Google Tag Manager -->
<script>
	var rootURL = "${serverRootURL?default("")}";
	var currentview = '${currentView?default("_NA_")}';
	dataLayer = [{
		'currentview': '${currentView?default("_NA_")}'
	}];
</script>
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-TQ3CD2');</script>
<!-- End Google Tag Manager -->

<!-- Google Analytics -->
<script>
	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

	<#if globalContext.webSiteId?default("envelopes") == "envelopes">
	ga('create', '<#if serverRootURL?exists && !serverRootURL?contains("www.envelopes.com")>UA-12487422-3<#else>UA-12487422-1</#if>', 'auto', {'siteSpeedSampleRate': 35});
	<#elseif globalContext.webSiteId?default("envelopes") == "ae">
	ga('create', 'UA-60025232-1', 'auto', {'siteSpeedSampleRate': 35});
	</#if>
	ga('require', 'linkid');
	ga('require', 'ec');
	<#if serverRootURL?exists && serverRootURL?contains("www.envelopes.com")>ga('require', 'GTM-N6MV7PP');</#if>
	<#if currentView?default("_NA_") != "receipt">
	ga('send', 'pageview');
	</#if>
</script>
<!-- End Google Analytics -->