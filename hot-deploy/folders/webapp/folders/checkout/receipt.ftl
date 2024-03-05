<link rel="stylesheet" href="/html/css/folders/checkout/receipt.css" type="text/css" />

<#if invalidSession == 'true'>
<script>window.location.href = '<@ofbizUrl>/main</@ofbizUrl>';</script>
<#else>

<script>
	localStorage.removeItem('addToCartData');
	$().updateMiniCart();
</script>

<div class="foldersContainer receipt foldersNewLimiter">

	<div class="foldersRow receiptTopHeaderContent">
		<div>
			<h1>Order Confirmation</h1>
		</div>
	</div>

	<div class="foldersRow foldersContainerContent" style="background-color: #f1f1f1;">
		<div class="foldersOrderConfirmationNumber">
			<div>
				<h3>Order Number</h3>
				<div class="orderId ftc-green">${orderInfo.orderId}</div>
				<#if request.getSession().getAttribute("userLogin")?has_content?string('true', 'false') == 'false'><div class="createdAccountText">An account has been created for you.  Check your email for your login information.</div></#if>
			</div>
		</div>
	</div>
	<div class="foldersRow foldersShippingBillingContent">
		<div class="foldersColumn small12 medium6 large6 noPaddingLeft">
			<div class="foldersContainerContent">
				<h3 class="ftc-blue">Shipping Information</h3>
				<div>${orderInfo.shippingAddress.name?if_exists}</div>
				<#if orderInfo.shippingAddress.company?has_content>
				<div>${orderInfo.shippingAddress.company?if_exists}</div>
				</#if>
				<div>${orderInfo.shippingAddress.address1?if_exists}</div>
				<#if orderInfo.shippingAddress.address2?has_content>
				<div>${orderInfo.shippingAddress.address2?if_exists}</div>
				</#if>
				<div>${orderInfo.shippingAddress.city?if_exists}, ${orderInfo.shippingAddress.stateProvince?if_exists}, ${orderInfo.shippingAddress.postalCode?if_exists}</div>
			</div>
		</div>
		<div class="foldersColumn small12 medium6 large6 noPaddingRight">
			<div class="foldersContainerContent">
				<h3 class="ftc-blue">Billing Information</h3>
				<div>${orderInfo.billingAddress.name?if_exists}</div>
				<#if orderInfo.billingAddress.company?has_content>
				<div>${orderInfo.billingAddress.company?if_exists}</div>
				</#if>
				<div>${orderInfo.billingAddress.address1?if_exists}</div>
				<#if orderInfo.billingAddress.address2?has_content>
				<div>${orderInfo.billingAddress.address2?if_exists}</div>
				</#if>
				<div>${orderInfo.billingAddress.city?if_exists}, ${orderInfo.billingAddress.stateProvince?if_exists}, ${orderInfo.billingAddress.postalCode?if_exists}</div>
			</div>
		</div>
	</div>
	<div class="foldersContainerContent foldersOrderSummaryContent marginTop20">
		<h3 class="ftc-blue">Order Summary</h3>
		<div id="jqs-order-summary">
        ${screens.render("component://folders/widget/CheckoutScreens.xml#orderSummary")}
		</div>
	</div>
	<div class="foldersTabularRow foldersPaymentInfoContent marginBottom20" bns-loadcertonalist>
		<div class="widthAuto paddingRight">
			<div class="foldersContainerContent">
				<h3 class="ftc-blue">Payment Information</h3>
				<div>
					<#if orderInfo.paymentInfo.paymentMethodTypeId == "CREDIT_CARD">
						Your ${orderInfo.paymentInfo.cardType} ${orderInfo.paymentInfo.cardNumber} will soon be authorized.
					<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_AMAZON">
						Your Amazon order reference ${orderInfo.paymentInfo.cardType} will soon be authorized.
					<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_PAYPAL_CHECKOUT">
						Your PayPal order reference ${orderInfo.paymentInfo.cardType} will soon be authorized.
					<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "PERSONAL_CHECK">
						Check Payment
					<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_OFFLINE" || orderInfo.paymentInfo.paymentMethodTypeId == "EXT_IPARCEL">
						No payment needed.
					<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_NET30" || orderInfo.paymentInfo.paymentMethodTypeId == "EXT_NET30">
						No payment needed.
					<#else>
						No payment found.
					</#if>
				</div>
			</div>
			<div class="foldersContainerContent">
				<h3 class="ftc-blue">Notes</h3>
				<div>
					<#if orderInfo.notes?has_content>
						<#list orderInfo.notes as notes>
						${notes.comment?if_exists}
						</#list>
					<#else>
						No notes entered.
					</#if>
				</div>
			</div>
		</div>
		<div class="imgBlock noPadding">
			<div class="envelopeBlock margin10">
				<a class="noMobile" target="_blank" href="https://www.envelopes.com">
					<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopeBrandFolderReceiptPage?fmt=png-alpha&amp;wid=618&amp;hei=194</@ofbizScene7Url>" alt="Placeholder Folder" />
				</a>
				<a class="mobile-block" target="_blank" href="https://www.envelopes.com">
					<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopeBrandFolderReceiptPageMobile?fmt=png-alpha&amp;wid=609&amp;hei=194</@ofbizScene7Url>" alt="Placeholder Folder" />
				</a>
			</div>
		</div>
	</div>
</div>
</#if>