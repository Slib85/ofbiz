<meta name="format-detection" content="telephone=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<@compress single_line=true><title>${metaTitle?if_exists}</title></@compress>
<@compress single_line=true><meta name="description" content="${metaDesc?if_exists?html}" /></@compress>
<@compress single_line=true><meta name="keywords" content="${metaKeywords?if_exists?html}" /></@compress>
<@compress single_line=true><meta name="author" content="${author?if_exists}" /></@compress>
<@compress single_line=true><meta name="google-site-verification" content="3oZ8FIOlY4sAM6DFmyyvWLLMXz3ynDuWdV7f2YYHBd0" /></@compress>
<@compress single_line=true><meta name="classification" content="Envelopes" /></@compress>
<@compress single_line=true><meta name="revisit-after" content="30 days" /></@compress>
<@compress single_line=true><meta name="copyright" content="Copyright ${Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()?string("yyyy")} ${author?if_exists}" /></@compress>
<@compress single_line=true><meta name="distribution" content="global" /></@compress>
<@compress single_line=true><meta name="language" content="English" /></@compress>
<@compress single_line=true><meta name="robots" content="ALL" /></@compress>
<@compress single_line=true><meta name="rating" content="General" /></@compress>
<@compress single_line=true><meta property="fb:admins" content="100000774498210,100000743907043,734599182" /></@compress>
<#--<@compress single_line=true><link rel="alternate" media="only screen and (max-width: 640px)" href="${alternateUrl}"></@compress>-->

<#if currentView == "product" && product?exists>
	<@compress single_line=true><meta property="og:title" content="${metaTitle?if_exists}" /></@compress>
	<@compress single_line=true><meta property="og:description" content="${metaDesc?if_exists?html}" /></@compress>
	<@compress single_line=true><meta property="og:type" content="product" /></@compress>
	<@compress single_line=true><meta property="og:site_name" content="Envelopes.com" /></@compress>
	<@compress single_line=true><meta property="og:price:amount" content="<#if product.getPrices()?has_content><#list product.getPrices().keySet() as quantity>${product.getPrices().get(quantity).price?string["0.##"]}<#break></#list></#if>" /></@compress>
	<@compress single_line=true><meta property="og:price:currency" content="USD" /></@compress>
	<@compress single_line=true><meta property="og:image" content="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}?wid=500&amp;hei=350&amp;fmt=png-alpha" /></@compress>
	<#if canonicalUrl?has_content><@compress single_line=true><meta property="og:url" content="<@ofbizUrl>${canonicalUrl?replace("&#x2f;","/")?replace("&#x7e;","~")?replace("&#x3d;","=")}</@ofbizUrl>" /></@compress></#if>
</#if>