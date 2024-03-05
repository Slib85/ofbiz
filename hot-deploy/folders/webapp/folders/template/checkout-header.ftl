<div id="main-header" class="container checkout-header">
	<div id="header-white-bar" class="padding-right-sm nav-bar padding-top-xxs padding-bottom-xxs padding-left-sm">
		<div class="logo">
			<a href="<@ofbizUrl>/main</@ofbizUrl>"><img class="padding-top-xxs" src="<@ofbizContentUrl>/html/img/logo/<#if globalContext.webSiteId?default("envelopes") == "ae">actionEnvelope.gif<#else>logo.png</#if></@ofbizContentUrl>" alt="<#if globalContext.webSiteId?default("envelopes") == "ae">ActionEnvelopes.com<#else>Envelopes.com</#if>" /></a>
		</div>
		<div class="geo-trust">
			<img src="<@ofbizContentUrl>/html/img/temp/geotrust.gif</@ofbizContentUrl>" alt="Geo Trust"/>
		</div>
		<div class="cart padding-left-xxs">
			<a id="cartContainer" data-dropdown-target="dropdown-nav-cart" data-dropdown-options="hover reverse-horizontal" data-dropdown-alternate-parent="header-white-bar" href="<@ofbizUrl>/cart</@ofbizUrl>">
				<span id="jqs-mini-cart-count">(0)</span>
				<i class="fa fa-shopping-cart"></i>
			</a>
		</div>
		<script>$().updateMiniCart();</script>
	</div>
	<div id="dropdown-nav-cart" class="drop-down nav-cart">
		<div class="popup-border-fade">
			<div class="nav-cart-header popup-title">
				<i class="fa fa-shopping-cart padding-right-xxs"></i> Your Cart
			</div>
			<div id="jqs-mini-cart-items" class="nav-cart-items"></div>
			<div class="row nav-cart-footer padding-xxs">
				<div>
					<a href="<@ofbizUrl>/cart</@ofbizUrl>"><div class="button-regular button-non-cta">Edit Cart</div></a>
				</div>
				<div class="nav-total text-right padding-right-xxs">
					Subtotal: <span class="jqs-mini-cart-total">$0.00</span>
				</div>
			</div>
		</div>
	</div>
	<div class="shop-with-confidence padding-top-xxs padding-bottom-xxs padding-left-md padding-right-md">
		<div class="swc-1">
			<i class="fa fa-lock"></i> Shop with Confidence:
		</div>
		<div class="swc-2">
			<a href="<@ofbizUrl>/privacy</@ofbizUrl>">Security 100% Guaranteed</a>
		</div>
		<div class="swc-3">
			<a href="<@ofbizUrl>/privacy</@ofbizUrl>">We Respect Your Privacy</a>
		</div>
		<div class="swc-4">
			<a href="http://support.envelopes.com/customer/portal/articles/828488-returns-">Return Policy</a>
		</div>
		<div class="swc-5" class="text-right">
			Need help? Call <a href="tel:1-877-683-5673">1-877-683-5673</a>
		</div>
	</div>
</div>
