<link href="<@ofbizContentUrl>/html/css/folders/checkout/cart-redesign.css</@ofbizContentUrl>" rel="stylesheet" />

<script>
    var reloadOnLogin = true;;
</script>
<#if isCartEmpty == true>
<script>
	localStorage.removeItem('addToCartData');
	$().updateMiniCart();
</script>
</#if>

<div class="foldersContainer cart foldersContainer foldersContainerLimiter">

	<div class="foldersRow cartTopSection">
		<div>
			<h1>Shopping Cart</h1>
		</div>
		<div class="mobile-block">
			<a href="<@ofbizUrl>/checkout</@ofbizUrl>" class="foldersButton buttonGreen mobile-block">Checkout</a>
		</div>
	</div>
	<#--  <div class="foldersRow marginTop10 jqs-requiredCartContent">
		<a href="<@ofbizUrl>/main</@ofbizUrl>" class="foldersButton fbc-darkGray pullLeft">Continue Shopping</a>
		<a href="<@ofbizUrl>/checkout</@ofbizUrl>" class="foldersButton buttonGreen pullRight">Checkout</a>
	</div>  -->
	<#--  <div id="bnc_header_bar" class="foldersTabularRow summary-header">
		<div class="cart-item-header">
			<span>Item</span>
		</div>
		<div class="customization-header">
			<span>Customization</span>
		</div>
		<div class="production-header">
			<span>Production Time</span>
		</div>
		<div class="price-header">
			<span>Quantity &amp; Price</span>
		</div>
	</div>  -->


	<div id="foldersMainCartContent" class="foldersCartColumnsWrap">

		<div class="foldersContainerContent jqs-cartContent"></div>


		<div class="foldersRow cartActions jqs-requiredCartContent">
			<a href="<@ofbizUrl>/checkout</@ofbizUrl>" class="foldersButton buttonGreen noMobile" style="font-weight: 700; font-size: 16px; letter-spacing: 1px;">Checkout</a>
			
			<#--  <p>
				<a class="toggle" href="#example">Toggle Div</a>
			</p>  -->

			<#--  <div class="toggle-content" id="example">
				Here's some text we want to toggle visibility of. Let's do it!
			</div>  -->

			<div class="foldersContainerContent shippingMethodContainer">
				<div class="columnHeader ftc-blue">
					<a class="couponToggle" href="#couponInput">Apply Coupon</a>
				</div>
				<div style="display:none;" class="columnResult couponToggle-content" id="couponInput">
					<input class="setWidth100 jqs-couponCode" type="text" value="" placeholder="Coupon Code" name="couponCode" />
					<div class="foldersButton foldersCustomCTA setDisplayBlock jqs-submitCouponCode">Apply</div>
				</div>
			</div>
			<form id="shippingOptionsForm" class="foldersContainerContent shippingMethodContainer" name="shippingOptionsForm" data-bigNameValidateForm="shippingOptionsForm">
				<div class="columnHeader ftc-blue">Shipping Method</div>

				<div class="columnShipping">

					<div class="foldersRow paddingTop10">
						<div class="small6 medium6 large6 foldersColumn">
							<div class="paddingLeft10">
								<div class="pullLeft">
									<input id="ship_to_business" name="ship_to" type="radio" value="BUSINESS_LOCATION" checked />
								</div>
								<div class="pullLeft">
									<span class="marginLeft10">
										<label for="ship_to_business">Business</label>
									</span>
								</div>
							</div>
						</div>
						<div class="small6 medium6 large6 foldersColumn	">
							<div class="paddingLeft10">
								<div class="pullLeft">
									<input id="ship_to_residential" name="ship_to" type="radio" value="RESIDENTIAL_LOCATION" />
								</div>
								<div class="pullLeft">
									<span class="marginLeft10">
										<label for="ship_to_residential">Residential</label>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="foldersRow">
						<div class="small6 medium6 large6 foldersColumn" data-bignamevalidateid="shipping_countryGeoId">
							<label class="bigNameSelect noMargin" data-bigNameValidateParent="shipping_countryGeoId">
								<select name="shipping_countryGeoId" data-bignamevalidate="shipping_countryGeoId" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Country">
									<option value="">Country</option>
									${screens.render("component://envelopes/widget/CommonScreens.xml#countries")}
								</select>
							</label>
							<script>
								$('[name=shipping_countryGeoId]').val('USA');
							</script>
						</div>
						<div class="small6 medium6 large6 foldersColumn" data-bignamevalidateid="shipping_postalCode">
							<input class="noMargin" id="shipping-postal-code" name="shipping_postalCode" data-type="shipping" type="text" value="" placeholder="Zip" data-bignamevalidate="shipping_postalCode" data-bignamevalidatetype="required postalCode" data-bignamevalidateerrortitle="Zip" />
						</div>
					</div>
					<div class="marginLeft10">
						<div class="foldersButton foldersCustomCTA setDisplayBlock" data-bigNameValidateSubmit="shippingOptionsForm" data-bigNameValidateAction="shippingOptionsFormSubmit">Calculate</div>
					</div>
					<#--
					<div class="columnResult">
						<input class="autoWidth jqs-postalCode" type="text" value="" placeholder="Zip Code" name="shipping_postalCode" />
						<div class="foldersButton fbc-darkGray marginLeft10 jqs-submitPostalCode">Calculate</div>
					</div>
					-->
					<div id="jqs-shipping-options"></div>

				</div>
			</form>
			<div class="orderPrices jqs-orderPrices"></div>
		</div>

	</div>


	<div class="foldersRow jqs-requiredCartContent">
		<a bns-checkoutbutton href="<@ofbizUrl>/checkout</@ofbizUrl>" class="foldersButton buttonGreen" style="display: block; cursor: pointer;text-align: center;padding: 0.8em;font-size: 22px;font-weight: 700;">Checkout</a>
		<div class="creditCardRelax hidden">
			<div class="creditCardMessageContent">
				<i class="fa fa-credit-card"></i>
				<p class="ftc-blue textCenter">Relax, we will not charge your card until your proof has been approved!</p>
			</div>
			<div class="cartCheckoutContent">
				<a href="<@ofbizUrl>/checkout</@ofbizUrl>" class="foldersButton buttonGreen">Checkout</a>
			</div>
		</div>
	</div>
	<div class="foldersRow padding10 preOrderPromiseBlock hidden">
		<i bns-closePreOrderPromise class="fa fa-times ftc-blue"></i>
		<div class="preOrderServiceDetail textCenter">
			<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/preOrderPromiseBanner?fmt=png-alpha&amp;wid=250</@ofbizScene7Url>" alt="Pre Order Promise">
			<h3 class="paddingTop5">Before your order goes into production, you'll receive a <span>Free Digital Proof </span>and <span>Artwork Assistance </span>from our Expert Folder Fanatics! <br /><span>Your proof approval is key!</span><br /><span>Your credit card will not be charged until your proof has been approved.</span></h3>
		</div>
		<div class="foldersRow textCenter marginTop10">
			<div class="preOrderBlocks marginRight10">
				<h3>Peace of Mind:</h3>
				<p><span>Your satisfaction is our only concern! </span>We promise your order will be delivered exactly the way you expect it to, or better!
			</div>
			<div class="preOrderBlocks marginRight10">
				<h3>Deadline Dedication:</h3>
				<p>We are dedicated to meeting your deadline, <span>we guarantee on-time shipment on every order!</span>
			</div>
			<div class="preOrderBlocks">
				<h3>Free Samples:</h3>
				<p><span>Fast and Free Samples </span>shipped to your door to make decision making it easier!
			</div>
		</div>
	</div>
</div>

<script>
	cartFreeShipAmount = ${globalContext.freeShippingAmount?default("250")};
</script>
<script>
var show = function (elem) {
	elem.style.display = 'block';
};

var hide = function (elem) {
	elem.style.display = 'none';
};

var toggle = function (elem) {

	// If the element is visible, hide it
	if (window.getComputedStyle(elem).display === 'block') {
		hide(elem);
		return;
	}

	// Otherwise, show it
	show(elem);

};

// Listen for click events
document.addEventListener('click', function (event) {

	// Make sure clicked element is our toggle
	if (!event.target.classList.contains('couponToggle')) return;

	// Prevent default link behavior
	event.preventDefault();

	// Get the content
	var content = document.querySelector(event.target.hash);
	if (!content) return;

	// Toggle the content
	toggle(content);

}, false);
</script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/folders/checkout/cart.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
