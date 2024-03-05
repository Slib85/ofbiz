<head>
	${screens.render("component://envelopes/widget/CommonScreens.xml#headerHead")}
	${screens.render("component://folders/widget/CommonScreens.xml#analyticsTop")}
	<#--${screens.render("component://envelopes/widget/CommonScreens.xml#thirdPartiesTop")}-->
	<link href="<@ofbizContentUrl>/html/css/top.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/css/global.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/img/icon/favicon-envelopes.png</@ofbizContentUrl>" rel="shortcut icon" type="image/x-icon"  />
	<link href="<@ofbizContentUrl>/html/css/includes/stay_in_the_loop.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<#if canonicalUrl?has_content><link href="https://${siteDomain?default("www.folders.com")}<@ofbizUrl>${canonicalUrl?replace("&#x2f;","/")?replace("&#x7e;","~")?replace("&#x3d;","=")}</@ofbizUrl>" rel="canonical" /></#if>

	<script type="text/javascript">
		var websiteId = '${globalContext.webSiteId?default("folders")}';
		var gAddToCartUrl = '<@ofbizUrl>/addToCart</@ofbizUrl>';
		var gCartUrl = '<@ofbizUrl>/cart</@ofbizUrl>';
		var gCrossUrl = '<@ofbizUrl>/cross-sell</@ofbizUrl>';
		var gCheckoutUrl = '<@ofbizUrl>/checkout</@ofbizUrl>';
		var freeShippingAmount = parseInt('${globalContext.freeShippingAmount?default("250")}');
	</script>

	<script src="<@ofbizContentUrl>/html/js/addons/top.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
	<!--[if lt IE 9]>
	<link rel="stylesheet" type="text/css" media="all" href="<@ofbizContentUrl>/html/css/ie.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" />
	<![endif]-->
	<script src="<@ofbizContentUrl>/html/js/global.min.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
	<script src="<@ofbizContentUrl>/html/js/global.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
	<script src="<@ofbizContentUrl>/html/js/account/login.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>

	<script type="text/javascript">
		$(function() {
			if (navigator.appVersion.indexOf('MSIE 8.') == -1) {
				$(document).foundation({
					abide: {
						live_validate : false,
						focus_on_invalid : false
					}
				});
			}
		});

		if(typeof dataLayer != 'undefined') {
			dataLayer.push({'event': 'header.loaded'});
		}
	</script>
</head>
<body>