<link href="<@ofbizContentUrl>/html/css/account/account-redesign.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/account/order-detail-redesign.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/checkout/order-summary.css</@ofbizContentUrl>" rel="stylesheet" />
<div class="content account">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a>
                > <a href="<@ofbizUrl>/all-orders</@ofbizUrl>">All Orders</a>
				> Order Detail
	</div>

    <div id="jqs-error" data-alert class="alert-box alert radius margin-bottom-xs" style="display: none">
        <span></span>
        <a href="#" class="close">&times;</a>
    </div>

    <#if orderInfo?has_content && (orderInfo.orderId)?has_content>
	<div class="container order-detail padding-xs">
		<div>
			<div class="padding-xs generic-container order-summary">
				<div class="row margin-bottom-sm">
					<h3>Order Number: ${orderInfo.orderId}</h3>
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
                <div class="row quick_links">
                	<a class="button-regular proof_approval_link button-cta" href="<@ofbizUrl>/proofApproval</@ofbizUrl>">Proof Approval Center</a>
                    <div class="button-regular reorder-button button-cta jqs-reorder element-inactive" data-itemUUIDs="${orderInfo.reorderInfo}">Reorder <i class="fa fa-caret-right"></i></div>
                </div>
				<div id="jqs-order-summary">
                ${request.setAttribute("showLineItemStatus", "true")}
				${screens.render("component://envelopes/widget/CheckoutScreens.xml#order-items")}
				</div>

                <#--
                <div class="padding-xs generic-container margin-top-xs tracking-number">
                    <div>
                        <h3>Tracking Number</h3>
                    </div>
                    <div>
                        <#if orderInfo.trackingNumber?default('') != '' && orderInfo.trackingURL?default('') != ''><a target="_blank" href='${orderInfo.trackingURL}'>${orderInfo.trackingNumber}</a><#else>No tracking currently available</#if>
                    </div>
                </div>
                -->

                <div class="padding-xs generic-container margin-top-xs payment-information">
                    <div>
                        <h3>Payment Information</h3>
                    </div>
                    <div>
                        <#if orderInfo.paymentInfo.paymentMethodTypeId == "CREDIT_CARD">
                            You paid with ${orderInfo.paymentInfo.cardType}.
                        <#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_AMAZON">
                            Your Amazon order reference ${orderInfo.externalId!}.
                        <#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_PAYPAL_CHECKOUT">
                            Your PayPal order reference ${orderInfo.externalId!}.
                        <#elseif orderInfo.paymentInfo.paymentMethodTypeId == "PERSONAL_CHECK">
                            Check Payment.
                        <#elseif orderInfo.paymentInfo.paymentMethodTypeId == "EXT_OFFLINE">
                            No payment needed.
                        <#else>
                            No payment found.
                        </#if>
                    </div>
                </div>
                <div class="button-regular reorder-button button-cta jqs-reorder element-inactive mobile-only" data-itemUUIDs="${orderInfo.reorderInfo}">Reorder <i class="fa fa-caret-right"></i></div>
			</div>
		</div>
	</div>
    <#else>
        No Order Found.
    </#if>
</div>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/reorder.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>