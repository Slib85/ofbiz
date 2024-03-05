<link href="<@ofbizContentUrl>/html/css/checkout/order-summary.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/checkout/receipt.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<#if invalidSession == 'true'>
<script>window.location.href = '<@ofbizUrl>/main</@ofbizUrl>';</script>
<#else>

<script>
	localStorage.removeItem('addToCartData');
	$().updateMiniCart();
</script>

<div class="content container receipt">
	<div class="margin-top-xs double-container">
		<div>
			<div class="padding-xs generic-container order-summary">
				<div>
					<h3>Order Number</h3>
				</div>
				<div>
					<span class="order-number">${orderInfo.orderId}</span>
				</div>
				<#if request.getSession().getAttribute("userLogin")?has_content?string('true', 'false') == 'false'><div class="createdAccountText">An account has been created for you.  Check your email for your login information.</div></#if>
			</div>
			<div class="row billing-shipping-information">
				<div class="small-12 medium-6 large-6 columns no-padding shipping-information">
					<div class="padding-xs generic-container">
						<div>
							<h3>Shipping Information</h3>
						</div>
						<div>
							<span>${orderInfo.shippingAddress.name}</span>
							<#if orderInfo.shippingAddress.company?has_content>
								<span>${orderInfo.shippingAddress.company}</span>
							</#if>
							<span>${orderInfo.shippingAddress.address1}</span>
							<#if orderInfo.shippingAddress.address2?has_content>
								<span>${orderInfo.shippingAddress.address2}</span>
							</#if>
							<span>${orderInfo.shippingAddress.city}, ${orderInfo.shippingAddress.stateProvince}, ${orderInfo.shippingAddress.postalCode}</span>
						</div>
					</div>
				</div>
				<div class="small-12 medium-6 large-6 columns no-padding billing-information">
					<div class="padding-xs generic-container">
						<div>
							<h3>Billing Information</h3>
						</div>
						<div>
							<span>${orderInfo.billingAddress.name}</span>
							<#if orderInfo.billingAddress.company?has_content>
								<span>${orderInfo.billingAddress.company}</span>
							</#if>
							<span>${orderInfo.billingAddress.address1}</span>
							<#if orderInfo.billingAddress.address2?has_content>
								<span>${orderInfo.billingAddress.address2}</span>
							</#if>
							<span>${orderInfo.billingAddress.city}, ${orderInfo.billingAddress.stateProvince}, ${orderInfo.billingAddress.postalCode}</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<#if orderInfo.isUpload?c == 'true' || orderInfo.hasAddressing?c == 'true'>
		<div class="padding-left-xs proofSteps">
			<div>
				<div class="padding-xs">
					<h3>Next Steps:</h3>
					<ol>
						<li><span>You'll receive an emailed proof within 24 hours. Once you approve your proof, your order will enter print production. <div>If you do not review your proof within 5 business days, your order will automatically enter print production.</div></span></li>
						<li><span>Once your order enters production, you will receive an email.</span></li>
						<li><span>Once your order is packed an shipped, you will receive a shipping notification with a tracking number.</span></li>
					</ol>
				</div>
			</div>
		</div>
		</#if>
	</div>
	<div class="padding-xs margin-top-xs generic-container order-summary">
		<div class="row">
			<h3>Order Summary</h3>
		</div>
		<div id="jqs-order-summary">
        ${screens.render("component://envelopes/widget/CheckoutScreens.xml#receipt-items")}
		</div>
	</div>
	<div class="tableRow">
		<div class="paddingRight tableCell widthAuto">
			<div class="padding-xs generic-container margin-top-xs payment-information">
				<div>
					<h3>Payment Information</h3>
				</div>
				<div>
						<span>
		                    <#if orderInfo.paymentInfo.paymentMethodTypeId == "CREDIT_CARD">
								Your ${orderInfo.paymentInfo.cardType} ${orderInfo.paymentInfo.cardNumber} will soon be authorized.
		                    <#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_AMAZON">
								Your Amazon order reference ${orderInfo.paymentInfo.cardType!} will soon be authorized.
							<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_PAYPAL_CHECKOUT">
								Your PayPal order reference ${orderInfo.paymentInfo.cardType!} will soon be authorized.
		                    <#elseif orderInfo.paymentInfo.paymentMethodTypeId == "PERSONAL_CHECK">
								Check Payment
		                    <#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_OFFLINE" || orderInfo.paymentInfo.paymentMethodTypeId == "EXT_IPARCEL">
								No payment needed.
							<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_NET30" || orderInfo.paymentInfo.paymentMethodTypeId == "EXT_NET30">
								Net 30.
		                    <#else>
								No payment found.
		                    </#if>
						</span>
				</div>
			</div>
			<div class="padding-xs generic-container margin-top-xs payment-information">
				<div>
					<h3>Notes</h3>
				</div>
				<div>
						<span>
		                    <#if orderInfo.notes?has_content>
		                        <#list orderInfo.notes as notes>
		                        ${notes.comment?if_exists}
		                        </#list>
		                    <#else>
								No notes entered.
		                    </#if>
						</span>
				</div>
			</div>
		</div>
		<div class="margin-top-xs tableCell imgBlock">
			<a class="hide-mobile" target="_blank" href="https://www.folders.com"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folderBrandEnvelopeReceiptPage?fmt=png-alpha&amp;wid=618&amp;hei=194</@ofbizScene7Url>" alt="Placeholder Folder" /></a>
			<a class="mobile-only" target="_blank" href="https://www.folders.com"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folderBrandEnvelopeReceiptPageMobile?fmt=png-alpha&amp;wid=609&amp;hei=194</@ofbizScene7Url>" alt="Placeholder Folder" /></a>
		</div>
	</div>
</div>
</#if>