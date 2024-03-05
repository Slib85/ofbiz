<link href="<@ofbizContentUrl>/html/css/folders/account/orderView.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="foldersContainer orderView">
	<div class="foldersBreadcrumbs">
		<a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a>
                > <a href="<@ofbizUrl>/orderList</@ofbizUrl>">All Orders</a>
				> Order Detail
	</div>
	<div class="foldersContainerContent">
		<div id="jqs-error" data-alert class="alert-box alert radius margin-bottom-xs" style="display: none">
			<span></span>
			<a href="#" class="close">&times;</a>
		</div>

		<#if orderInfo?has_content && (orderInfo.orderId)?has_content>
		<h2 class="ftc-blue orderNumber">Order Number: ${orderInfo.orderId}</h3>
		<div class="orderSummary">
			<div class="foldersRow">
				<div class="foldersColumn small12 medium6 large6">
					<div class="padding-xs generic-container">
						<div>
							<h3 class="ftc-blue">Shipping Information</h3>
						</div>
						<div>
							<div>${orderInfo.shippingAddress.name}</div>
						<#if orderInfo.shippingAddress.company?has_content>
							<div>${orderInfo.shippingAddress.company}</div>
						</#if>
							<div>${orderInfo.shippingAddress.address1}</div>
						<#if orderInfo.shippingAddress.address2?has_content>
							<div>${orderInfo.shippingAddress.address2}</div>
						</#if>
							<div>${orderInfo.shippingAddress.city}, ${orderInfo.shippingAddress.stateProvince}, ${orderInfo.shippingAddress.postalCode}</div>
						</div>
					</div>
				</div>
				<div class="foldersColumn small12 medium6 large6">
					<div class="padding-xs generic-container">
						<div>
							<h3 class="ftc-blue">Billing Information</h3>
						</div>
						<div>
							<div>${orderInfo.billingAddress.name}</div>
						<#if orderInfo.billingAddress.company?has_content>
							<div>${orderInfo.billingAddress.company}</div>
						</#if>
							<div>${orderInfo.billingAddress.address1}</div>
						<#if orderInfo.billingAddress.address2?has_content>
							<div>${orderInfo.billingAddress.address2}</div>
						</#if>
							<div>${orderInfo.billingAddress.city}, ${orderInfo.billingAddress.stateProvince}, ${orderInfo.billingAddress.postalCode}</div>
						</div>
					</div>
				</div>
			</div>
			<#--<div class="foldersRow">
				<div class="pullRight foldersButton buttonGreen jqs-reorder" data-itemUUIDs="${orderInfo.reorderInfo}">Reorder <i class="fa fa-caret-right"></i></div>
			</div>-->
			<div id="jqs-order-summary">
			${request.setAttribute("showLineItemStatus", "true")}
			${screens.render("component://folders/widget/CheckoutScreens.xml#orderSummary")}
			</div>
			<#--
			<#if orderInfo.trackingNumber?default('') != '' && orderInfo.trackingURL?default('') != ''>
			<div class="padding-xs generic-container margin-top-xs tracking-number">
				<div>
					<h3>Tracking Number</h3>
				</div>
				<div>
					<a target="_blank" href='${orderInfo.trackingURL}'>${orderInfo.trackingNumber}</a>
				</div>
			</div>
			</#if>
			-->
			<div class="foldersRow">
				<div class="foldersColumn large12">
					<div>
						<h3 class="ftc-blue">Payment Information</h3>
					</div>
					<div>
						<#if orderInfo.paymentInfo.paymentMethodTypeId == "CREDIT_CARD">
							You paid with ${orderInfo.paymentInfo.cardType}.
						<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_AMAZON">
							Your Amazon order reference ${orderInfo.externalId}.
						<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_PAYPAL_CHECKOUT">
							Your PayPal order reference ${orderInfo.externalId}.
						<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "PERSONAL_CHECK">
							Check Payment.
						<#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_OFFLINE">
							No payment needed.
						<#else>
							No payment found.
						</#if>
					</div>
				</div>
			</div>
		</div>
		<#else>
			No Order Found.
		</#if>
	</div>
</div>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/reorder.js</@ofbizContentUrl>"></script>