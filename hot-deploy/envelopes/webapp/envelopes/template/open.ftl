<html lang="en">
<head>
	${screens.render("component://envelopes/widget/CommonScreens.xml#headerHead")}
	${screens.render("component://envelopes/widget/CommonScreens.xml#analyticsTop")}
	<#--${screens.render("component://envelopes/widget/CommonScreens.xml#thirdPartiesTop")}-->
	<link href="<@ofbizContentUrl>/html/css/top.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/css/global.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="/html/css/addons/font-awesome-4.7.0/css/font-awesome.min.css?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/img/icon/favicon-envelopes.png</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="shortcut icon" type="image/x-icon"  />
	<link href="<@ofbizContentUrl>/html/css/includes/stay_in_the_loop.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/css/util/slideIt.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/css/util/bigNameValidation.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<#if canonicalUrl?has_content><link href="<@ofbizUrl>${canonicalUrl?replace("&#x2f;","/")?replace("&#x3f;","?")?replace("&#x7e;","~")?replace("&#x3a;",":")?replace("&#x3d;","=")?replace("&#x25;","%")?replace("&amp;","&")}</@ofbizUrl>" rel="canonical" /></#if>

	<script type="text/javascript">
		var websiteId = '${globalContext.webSiteId?default("envelopes")}';
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

		if (localStorageEnabled && typeof $.cookie('__ES_ll') != 'undefined' && typeof localStorage.showPromoBasedOnLogin == 'undefined') {
			localStorage.showPromoBasedOnLogin = false;
		} else if (localStorageEnabled && typeof $.cookie('__ES_ll') == 'undefined' && typeof localStorage.showPromoBasedOnLogin == 'undefined') {
			localStorage.showPromoBasedOnLogin = true;
		}

		if(typeof dataLayer != 'undefined') {
			dataLayer.push({'event': 'header.loaded'});
		}
	</script>
</head>
<body id="myBody">
