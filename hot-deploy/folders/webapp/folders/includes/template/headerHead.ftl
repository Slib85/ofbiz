<head>
	<meta name="format-detection" content="telephone=no">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<@compress single_line=true><title>${metaTitle?if_exists}</title></@compress>
	<@compress single_line=true><meta name="description" content="${metaDesc?if_exists?html}" /></@compress>
	<@compress single_line=true><meta name="keywords" content="${metaKeywords?if_exists?html}" /></@compress>
	<@compress single_line=true><meta name="author" content="${author?if_exists}" /></@compress>
	<@compress single_line=true><meta name="google-site-verification" content="3oZ8FIOlY4sAM6DFmyyvWLLMXz3ynDuWdV7f2YYHBd0" /></@compress>
	<@compress single_line=true><meta name="classification" content="Folders" /></@compress>
	<@compress single_line=true><meta name="revisit-after" content="30 days" /></@compress>
	<@compress single_line=true><meta name="copyright" content="Copyright ${Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()?string("yyyy")} ${author?if_exists}" /></@compress>
	<@compress single_line=true><meta name="distribution" content="global" /></@compress>
	<@compress single_line=true><meta name="language" content="English" /></@compress>
	<@compress single_line=true><meta name="robots" content="ALL" /></@compress>
	<@compress single_line=true><meta name="rating" content="General" /></@compress>
	<@compress single_line=true><meta property="fb:admins" content="100000774498210,100000743907043,734599182" /></@compress>
	<@compress single_line=true><meta name="msvalidate.01" content="EC4278670939A08F39DEB734383C511F" /></@compress>

	<#if currentView == "product" && product?exists>
	<@compress single_line=true><meta property="og:title" content="${metaTitle?if_exists}" /></@compress>
	<@compress single_line=true><meta property="og:description" content="${metaDesc?if_exists?html}" /></@compress>
	<@compress single_line=true><meta property="og:type" content="product" /></@compress>
	<@compress single_line=true><meta property="og:site_name" content="Folders.com" /></@compress>
	<@compress single_line=true><meta property="og:image" content="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}?wid=500&amp;hei=350&amp;fmt=png-alpha" /></@compress>
	<#if canonicalUrl?has_content><@compress single_line=true><meta property="og:url" content="<@ofbizUrl>${canonicalUrl?replace("&#x2f;","/")?replace("&#x7e;","~")?replace("&#x3d;","=")?replace("&#x3f;","?")?replace("&#x3a;",":")?replace("&#x25;","%")}</@ofbizUrl>" /></@compress></#if>
	</#if>
	<#if canonicalUrl?has_content><link href="<@ofbizUrl>${canonicalUrl?replace("&#x2f;","/")?replace("&#x7e;","~")?replace("&#x3d;","=")?replace("&#x3f;","?")?replace("&#x3a;",":")?replace("&#x25;","%")}</@ofbizUrl>" rel="canonical" /></#if>


    <link href="/html/css/addons/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/css/util/slideout.min.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/css/util/dropdown.min.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/css/util/bigNameReveal.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
    <link href="<@ofbizContentUrl>/html/css/util/bigNameValidation.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
	<link href="<@ofbizContentUrl>/html/css/folders/global-redesign.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
    <link href="<@ofbizContentUrl>/html/css/util/spinner.min.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
    <link href="<@ofbizContentUrl>/html/img/icon/favicon-folders.png</@ofbizContentUrl>" rel="shortcut icon" type="image/x-icon"  />
    <link href="<@ofbizContentUrl>/html/css/util/slideIt.css</@ofbizContentUrl>" rel="stylesheet" />
	${screens.render("component://folders/widget/CommonScreens.xml#analyticsTop")}
	<script src="<@ofbizContentUrl>/html/js/addons/top.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
	<script src="<@ofbizContentUrl>/html/js/util/slideout.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
	<script src="<@ofbizContentUrl>/html/js/global.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
	<script src="<@ofbizContentUrl>/html/js/util/dropdown.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
	<script src="<@ofbizContentUrl>/html/js/account/login.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>

    <script type="text/javascript">
        var websiteId = '${globalContext.webSiteId?default("folders")}';
        var gAddToCartUrl = '<@ofbizUrl>/addToCart</@ofbizUrl>';
        var gCartUrl = '<@ofbizUrl>/cart</@ofbizUrl>';
        var gCheckoutUrl = '<@ofbizUrl>/checkout</@ofbizUrl>';
        var freeShippingAmount = parseInt('${globalContext.freeShippingAmount?default("299")}');
    </script>
</head>