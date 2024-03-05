<link rel="stylesheet" type="text/css" href="<@ofbizContentUrl>/html/css/checkout/shopping-cart.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}">
<link href="<@ofbizContentUrl>/html/css/includes/before_you_go.css</@ofbizContentUrl>" rel="stylesheet" />

<script>
    var reloadOnLogin = true;;
</script>

<#assign isCartEmpty = emptyCart?string('true', 'false') />
<#--main container-->
<div class="content container shopping-cart padding-sm text-size-md">
	<#--Cart Content-->
	<div bns-loadcertonalist class="row" style="display: table; vertical-align: top;">
		<#--Cart Section-->
		<div style="display: table-cell;">
			<#--Cart Title-->
			<div class="title row no-padding no-margin">
				<span>Shopping Cart</span>
			</div>
            <#if isCartEmpty == "false">
			<div class="row no-margin">
                <div class="small-12 medium-6 large-6 columns no-padding h5b text-right show-for-small-only">
					<#if pCart?has_content && (pCart.getStoredData().locked)?has_content && pCart.getStoredData().locked == "Y" && (pCart.getStoredData().agentId)?has_content && request.getSession().getId() == pCart.getStoredData().agentId><div class="button-regular releaseCart button-cta">Release</div></#if> <a href="<@ofbizUrl>/checkout</@ofbizUrl>"><div class="jqs-checkout button-regular checkout button-cta mobile">Checkout</div></a>
                </div>
				<div class="small-6 medium-6 large-6 columns h5b text-left no-padding">
					<div class="jqs-continue-shopping button-regular button-non-cta" style="padding: 15px 10px;">Continue Shopping</div>
				</div>
                <div class="small-6 medium-6 large-6 columns h5b text-right no-padding show-for-small-only">
                    <a href="<@ofbizUrl>/checkout</@ofbizUrl>"><div class="jqs-checkout button-regular payWithAmazon" style="padding: 15px 10px;"><i class="fa fa-amazon"></i> Pay with Amazon</div></a>
                </div>
				<div class="small-12 medium-6 large-6 columns no-padding h5b text-right hide-for-small-only">
					<#if pCart?has_content && (pCart.getStoredData().locked)?has_content && pCart.getStoredData().locked == "Y" && (pCart.getStoredData().agentId)?has_content && request.getSession().getId() == pCart.getStoredData().agentId><div class="button-regular releaseCart button-cta">Release</div></#if> <a href="<@ofbizUrl>/checkout</@ofbizUrl>"><div class="jqs-checkout button-regular checkout button-cta">Checkout</div></a>
				</div>
			</div>
            </#if>
			<#--Cart Title END-->
			<div class="coupon-response margin-top-xxs alert-box hidden"></div>
			<#--Line Items-->
            <div id="jqs-order-summary">
            ${screens.render("component://envelopes/widget/CheckoutScreens.xml#cart-items")}
            </div>
            <#--Line Items END-->
		</div>
		<#--Cart Section END-->
		<#--Info Section-->
		<div class="info-section padding-left-xs" style="display: table-cell; width: 250px; padding-top: <#if isCartEmpty == 'true'>45<#else>105</#if>px; vertical-align: top;">
			<#--Shop with Confidence Section-->
			<div class="shop-with-confidence section-container text-size-md">
				<div class="caption h5b">
					<i class="fa fa-lock h3"></i><span>Shop With Confidence</span>
				</div>
				<ul class="no-margin">
					<li>
						<div class="caption blue h7b">Privacy Policy</div>
						<span>We will never share or sell any of your personal information, including your e-mail address.</span>
					</li>
					<li>
						<div class="caption blue h7b">Shipping Policy</div>
						<span>95% of plain orders ship the same or next business day.</span>
					</li>
					<li>
						<div class="caption blue h7b">Security Guarantee</div>
						<span>We guarantee that every transaction you make at Envelopes.com will be safe.</span>
					</li>
					<li>
						<div class="caption blue h7b">Returns</div>
						<span>We aren't happy unless you are 100% satisfied. We accept returns of plain products if returned within 14 days of receiving the shipment.</span>
					</li>
				</ul>
			</div>
			<#--Shop with Confidence Section END-->
			<#--Help Section-->
			<div class="help section-container">
				<div class="caption h5b">
					<i class="fa fa-info-circle h3"></i><span>Help</span>
				</div>
				<ul class="no-margin">
					<li>
						<div class="caption"><i class="h5 fa fa-phone"></i> <span class="h7b"><a href="tel:1-877-683-5673">1-877-NVELOPE</a></span></div>
					</li>
					<li>
						<div class="caption"><i class="h5 fa fa-comment"></i> <span class="h7b"><a href="javascript:void(0);" onclick="olark('api.box.expand')">Live Chat</a></span></div>
					</li>
				</ul>
			</div>
			<#--Help Section END-->
			<#--Delivery and Returns Section-->
			<div class="delivery-and-returns section-container ">
				<div class="caption h5b">
					<i class="fa fa-info-circle h3"></i><span>Delivery &amp; Returns</span>
				</div>
				<ul class="no-margin">
				<!--
					<li>
						<div class="caption"><span class="h7b"><a href="#">Shipping to P.O. Box</a></span></div>
					</li>
					<li>
						<div class="caption"><span class="h7b"><a href="#">Canadian Shipments</a></span></div>
					</li>
				-->
					<li>
						<div class="caption"><span class="h7b"><a href="http://support.envelopes.com/customer/portal/articles/828485-shipping-policy">Shipping Time</a></span></div>
					</li>
					<li>
						<div class="caption"><span class="h7b"><a href="http://support.envelopes.com/customer/portal/articles/828488-returns-">Return Policy</a></span></div>
					</li>
				</ul>
			</div>
			<#--Delivery and Returns Section END-->
		</div>
		<#--Info Section END-->
	</div>
	<#--Cart Content END-->
</div>

<div id="beforeYouLeave" class="beforeYouGo reveal-modal reveal-modal-limiter" data-reveal>
	<div class="jqs-response"></div>
	<div class="byg-container padding-top-sm padding-bottom-sm text-center">
		<div class="bygHeader">Before You Go...</div>
		<div class="bygSubHeaderOffer padding-top-xxs padding-left-xs padding-right-xs">Take <span class="infostrix">*</span>$10 off your order</div>
		<div class="bygSubHeaderRequirement">of $50 or more by signing up</div>
		<div class="bygEnvelopeContainer">
			<div class="bygEnvelopePiece1"></div>
			<div class="bygEnvelopePiece2"></div>
			<div class="bygEnvelopePiece3"></div>
			<div class="bygEnvelopePiece4"></div>
			<div class="bygEnvelopePiece5">$</div>
		</div>
		<div class="jqs-errorBox errorBox hidden"></div>
		<div class="jqs-successBox successBox hidden"></div>
		<div class="row options jqs-options">
			<div class="columns small-12 medium-6 large-6">
				<div class="padding-top-xxs bygEmailInputContainer">
					<input type="text" name="email_address" value="" placeholder="Enter your Email Address" class="no-margin" />
				</div>
			</div>
			<div class="columns small-12 medium-6 large-6">
				<div class="no-margin button button-cta padding-left-sm padding-right-sm margin-top-xxs jqs-submit_email">Join Our List</div>
			</div>
		</div>
		<a class="close-reveal-modal"><i class="fa fa-times"></i></a>
	</div>
</div>
<script>
	cartFreeShipAmount = ${globalContext.freeShippingAmount?default("250")};
</script>
<#--###################################################################-->
<#--###################################################################-->
<#--####Hack to avoid freemarker encoding of javascript variables######-->
<#--###################################################################-->
<#--###################################################################-->
<span id="jqs-lastVisitedURL" class="hidden">${lastVisitedURL?default('/')}</span>
<script>var lastVisitedURL = $('#jqs-lastVisitedURL').text();</script>
<#--###################################################################-->
<#--###################################################################-->
<#--###################################################################-->
<#--###################################################################-->
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/checkout/jquery.envelopes.checkout.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/checkout/cart.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/checkout/abandon-popup.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>