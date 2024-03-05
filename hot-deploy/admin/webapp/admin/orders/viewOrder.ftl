<#assign orderWebSiteId = Static["com.envelopes.util.EnvUtil"].getWebsiteId(order.salesChannelEnumId) />

<script type="text/javascript" src="https://js.braintreegateway.com/web/3.78.2/js/hosted-fields.js"></script>
<script type="text/javascript" src="https://js.braintreegateway.com/web/3.78.2/js/client.js"></script>
<script type="text/javascript" src="https://js.braintreegateway.com/web/3.78.2/js/paypal-checkout.js"></script>

<style>
	.hosted-field {
		-webkit-appearance: none;
		color: rgba(0,0,0,.75);
		display: block;
		width: 100%;
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		box-shadow: none;
		border: 1px solid #e3e3e3;
		background-color: #F1F1F1;
		color: #1a345f;
		font-weight: bold;
		margin-bottom: 15px;
		height: 35px;
		margin-bottom: 10px !important;
    	margin-top: 10px !important;
	}

	.braintree-hosted-fields-focused {
		border: 1px solid #64d18a;
		border-radius: 1px;
		background-position: left bottom;
	}

	.braintree-hosted-fields-invalid {
		border: 1px solid #ed574a;
	}

	.braintree-hosted-fields-valid {

	}
</style>


<style id="designerCSS">
    .designerContainer {
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0px;
        left: 0px;
        border: 55px solid rgba(0,0,0,.45);
        box-shadow: inset 0 0 0 2px rgba(254,126,3,.75);
        -moz-background-clip: padding;
        -webkit-background-clip: padding;
        background-clip: padding-box;
        z-index: 9999;
        background-color: #ffffff;
    }

    .canvas-container {
        position: relative !important;
        top: 10px !important;
        left: 50% !important;
        opacity: 0 !important;
        -webkit-box-shadow: rgba(128, 128, 128, 0.6) 0 0 5px 0 !important;
        -moz-box-shadow: rgba(128, 128, 128, 0.6) 0 0 5px 0 !important;
        box-shadow: rgba(128, 128, 128, 0.6) 0 0 5px 0 !important;
    }

    .canvas-container canvas {
        width: 100% !important;
        height: 100% !important;
    }

    .designerHidden {
        position: absolute !important;
        visibility: none !important;
        left: -99999px !important;
    }

    /* Texel CSS */
    .texelSelect {
        height: 14px;
        font-size: 13px;
        padding: 4px 31px 9px 5px;
        background: url(/html/texel/img/dropDownArrow.png) no-repeat right 5px center,#e8e8e8;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        cursor: pointer;
        display: inline-block;
        margin-right: 5px;
    }
    .texelSelect[bns-texel_select="texel_fontFamily"] {
        width: 125px;
    }
        .texelSelect .selectedColor {
            height: 18px;
            width: 18px;
            left: 0;
            top: 1px;
            position: relative;
        }

    .texelSelectListPopup {
        display: none;
        overflow-y: auto;
        max-height: 300px;
        position: absolute;
        background-color: #fff;
        z-index: 9999;
        border: 1px solid #ccc;
        min-width: 185px;
        margin: 0px;
    }
        .texelSelectListOption {
            box-sizing: border-box;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
            padding: 5px 10px 5px 15px;
            cursor: pointer;
            white-space: nowrap;
            border: 1px solid transparent;
        }

        .texelSelectListOption:hover {
            border: 1px solid #00a4e4;
        }
        .texelSelectListSelected {
            border: 1px solid #00a4e4;
        }

    .texelRadio {
        background-color: #e8e8e8;
        display: inline-block;
        width: 17px;
        height: 17px;
        position: relative;
        padding: 5px;
        cursor: pointer;
        margin: 0 5px 0 0;
    }
    .texelRadio[data-selected="true"] {
        background-color: #545554;
    }
        .texelRadio [class*="-fontAlignment"] {
            border-top: 2px solid #555;
            position: relative;
            height: 0;
        }
        .texelRadio[data-selected="true"] [class*="-fontAlignment"] {
            border-top: 2px solid #fff;
        }
    .canvas-container .addressRotator {
        position: absolute;
        width: 120px;
    }
    .canvas-container .addPrev,
    .canvas-container .addNext {
        display: inline-block;
        border: 1px solid #000000;
        color: #000000;
        padding: 1px;
        cursor: pointer;
    }
    .canvas-container .addNext {
        margin-left: 5px;
    }
</style>
<link href="<@ofbizContentUrl>/html/css/util/fonts.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/addons/jquery.minicolors.min.css</@ofbizContentUrl>?ts=2" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/texel/texel.css</@ofbizContentUrl>?ts=3" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/util/spinner.css</@ofbizContentUrl>?ts=3" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/addressing/grid1.css</@ofbizContentUrl>?ts=3" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/addressing/slideout.css</@ofbizContentUrl>?ts=3" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/admin/order-view.css</@ofbizContentUrl>?ts=6" rel="stylesheet" />
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">

<script>
	var orderIds = '${requestParameters.orderIds?default("")}';
</script>

<#if requestParameters.seqNum?has_content && requestParameters.orderIds?has_content && requestParameters.orderIds?contains(",")>
	<#assign orders = requestParameters.orderIds?split(",") />
	<#list orders as ord>
		<#if ord?split("_")[0] == order.orderId && ord?split("_")[1] == requestParameters.seqNum && ord_index-1 gte 0>
			<form id="previousOrder" name="previousOrder" method="POST" action="<@ofbizUrl>/viewOrder?orderId=${orders[ord_index-1]?split("_")[0]}&seqNum=${orders[ord_index-1]?split("_")[1]}#item-${orders[ord_index-1]?split("_")[1]}</@ofbizUrl>">
				<input type="hidden" name="orderIds" value="${requestParameters.orderIds}" />
			</form>
			<button type="button" class="ordernavprev btn btn-floating btn-warning btn-xs" onclick="$('#previousOrder').submit()"><i class="icon wb-arrow-left" aria-hidden="true"></i></button>
		</#if>
		<#if ord?split("_")[0] == order.orderId && ord?split("_")[1] == requestParameters.seqNum && ord_has_next>
			<form id="nextOrder" name="nextOrder" method="POST" action="<@ofbizUrl>/viewOrder?orderId=${orders[ord_index+1]?split("_")[0]}&seqNum=${orders[ord_index+1]?split("_")[1]}#item-${orders[ord_index+1]?split("_")[1]}</@ofbizUrl>">
				<input type="hidden" name="orderIds" value="${requestParameters.orderIds}" />
			</form>
			<button type="button" class="ordernavnext btn btn-floating btn-success btn-xs" onclick="$('#nextOrder').submit()"><i class="icon wb-arrow-right" aria-hidden="true"></i></button>
			<#break>
		</#if>
	</#list>
</#if>

<div class="container order-view">
	<div class="card bg-white p-20">
		<div class="row head">
			<div class="col-xs-3 col-lg-3 exportGroup">
				<a id="exportOrder" href="#"><i class="fa fa-cloud-upload <#if order.exportedDate?has_content>order-uploaded</#if> col-xs-1"></i></a>
				<div class="col-xs-10 order-header">
					<span data-websiteid="${Static["com.envelopes.util.EnvUtil"].getWebsiteId(order.salesChannelEnumId)}" class="order-id jqs-orderId">${order.orderId}</span><span class="is-rush"><div class="env-badge jqs-rush-badge tag tag-danger<#if isRush?string('true', 'false') != 'true'> hidden</#if>">RUSH</div></span>
					<span class="order-status">Current Status:
						<button
							type="button"
							class="btn btn-default status-change"
							data-container="body"
							data-placement="right"
							data-html="true"
							data-currentStatus="${order.statusId}"
							data-content="">
							${Static["com.envelopes.order.OrderHelper"].getStatusDesc(delegator, order.statusId)} (update)
						</button>
					</span>
					<span class="order-date">Order Date: ${order.orderDate?string.medium}</span>
					<#if order.exportedDate?has_content><span class="order-date">Exported: ${order.exportedDate?string.medium}</span></#if>
					<div id="resendConfirmation" class="env-badge tag tag-warning">Resend Confirmation</div>
                    <div id="sendBusinessReviewEmail" class="env-badge tag tag-success">Request Google Review</div>
				</div>
			</div>
			<div class="col-xs-3 col-lg-3 status-list">
				<span class="status-header">Status History</span>
				<#list status as stat>
					<span class="status-item">${stat.status} - ${stat.statusDatetime?string.medium} (${stat.statusUserLogin?default("system")})</span>
				</#list>
			</div>
			<div class="col-xs-3 col-lg-2">
				<div id="barcodeTarget" class="barcodeTarget">${order.orderId}</div>
			</div>
			<div class="col-xs-3 col-lg-4">
				<div class="row pull-xs-right userInfo">
					<#if externalUser?has_content && externalUser.get(0).photo?has_content>
						<div class="col-xs-2">
							<img src="${externalUser.get(0).photo}" alt="" class="img-circle margin-top-8" width="44">
						</div>
					<#else>
						<i class="fa fa-user col-xs-2"></i>
					</#if>
					<div class="col-xs-9 customer-info">
						${(person.firstName)?if_exists} ${(person.lastName)?if_exists} (<a href="<@ofbizUrl>/customers</@ofbizUrl>?id=ID:${person.partyId}">${person.partyId}</a>)<br />
						<span>
							Customer since: ${person.createdStamp?string("MM/yyyy")}<br />
							Loyalty Points: ${loyaltyPoints?default(0)}
						</span>
						<span class="user-type"><div class="env-badge tag tag-danger<#if isWholesaler?string('true', 'false') != 'true'> hidden</#if>">WHOLESALER</div><div class="env-badge tag tag-success<#if isNonProfit?string('true', 'false') != 'true'> hidden</#if>">NON-PROFIT</div><div class="env-badge tag tag-info<#if isNonTaxable?string('true', 'false') != 'true'> hidden</#if>">NON-TAXABLE</div></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="card bg-white p-20">
		<div class="row payment-info">
			<div class="col-xs-9 left">
				<div class="jqs-editable-info jqs-payment col-xs-3">
					<form>
						<span class="info-header">Payment:</span>
						<span class="jqs-billing-name">${(billingAddress.toName)?if_exists}</span><br />
						<#list payments as payment>
							<div<#if payment.paymentMethodTypeId?exists && (payment.paymentMethodTypeId == "EXT_AMAZON" || payment.paymentMethodTypeId == "EXT_PAYPAL_CHECKOUT")> class="payment_bg_color"</#if>>
								<span class="locked-payment_paymentMethodTypeIdDesc" data-selected-value="${payment.paymentMethodTypeId?if_exists}">${payment.paymentMethodTypeIdDesc?if_exists}</span><br />
								<div class="jqs-pay-by-check" style="display: <#if payment.paymentMethodTypeId?if_exists == "CERTIFIED_CHECK">block<#else>none</#if>;">
									Check Number: <span class="locked-payment_checkNumber">${payment.checkNumber?if_exists}</span><br />
								</div>
								
								<#if orderWebSiteId != "envelopes">
								<div class="jqs-pay-by-card" style="display: <#if payment.paymentMethodTypeId?if_exists == "CREDIT_CARD">block<#else>none</#if>;">
									Payment Type: <span>${payment.cardType?if_exists}</span><br />
									Card Number: <span class="locked-payment_cardNumber">${payment.cardNumber?if_exists}</span><br />
									Expiration Date: <span class="locked-payment_expireDate">${payment.expireDate?if_exists}</span><br />
									<div class="jqs-security-code hidden">
										Securty Code: <span class="locked-payment_cvv"></span><br />
									</div>
								</div>
								</#if>

								Payment Status: <span>${payment.statusId?if_exists}</span><br />
								<#if payment.statusId?has_content && (payment.statusId == "Settled" || payment.statusId == "Refunded")>
									<input name="button-refund" class="jqs-button-refund" type="button" value="Refund" data-toggle="modal" data-target="#refundPop" />
									<div class="modal fade" id="refundPop" role="dialog">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
													<h4 class="modal-title" id="myModalLabel">Process Refund</h4>
												</div>
												<div class="modal-body row">
													<div class="col-xs-12">
														<div class="form-group has-feedback">
                                                            <input type="hidden" id="paymentMethodTypeId" value="${payment.paymentMethodTypeId}" />
															<input type="hidden" id="maxAmount" value="${payment.maxAmount}" />
															<input type="hidden" id="orderPaymentPreferenceId" value="${payment.orderPaymentPreferenceId}" />
															<label class="control-label" for="refundAmount">Refund Amount:</label>
															<input type="text" class="form-control" id="refundAmount" />
														</div>
													</div>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
													<button type="button" class="btn btn-primary j-btn-add">Refund</button>
												</div>
											</div>
										</div>
									</div>
								</#if>
							</div>

							<#if payment.paymentMethodTypeId?exists && payment.paymentMethodTypeId == "CREDIT_CARD" && orderWebSiteId == "envelopes">
								<button style="margin: 10px 0px;" type="button" class="btn btn-primary btn-icon btn-sm" data-toggle="modal" data-target="#modal-braintree_payment">
									Submit New Credit Card Payment <i class="wb-globe"></i>
								</button>
							</#if>
						</#list>
						<#if order.externalId?has_content>External Order ID: ${order.externalId}</#if>
						<div class="edit-mode">
							<input name="button-cancel" class="jqs-button-cancel" type="button" value="Cancel" />
							<input data-save-type="payment" class="jqs-button-save" name="button-save" type="button" value="Save" />
						</div>
						<div class="non-edit-mode">
							<div name="button-edit" class="jqs-button-edit btn btn-default btn-xs">Edit Info.</div>
						</div>
						<input name="orderId" type="hidden" value="${order.orderId}">
					</form>
					
					<div class="modal fade" id="modal-braintree_payment" role="dialog">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">Braintree Credit Card Payment</h4>
								</div>
								<div class="modal-body row">
									<div class="col-xs-12">
										<div class="form-group has-feedback">
											<div>
												<div id="card-number" class="hosted-field"></div>
												<div id="expiration-date" class="hosted-field"></div>
												<div id="cvv" class="hosted-field"></div>
												<div id="postal-code" class="hosted-field"></div>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
									<button onclick="$('#modal-braintree_payment').trigger('getToken');" type="button" class="btn btn-primary">Process</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<span class="info-header">Billing:</span>
					<#if billingAddress?has_content>
						<div class="jqs-editable-info jqs-billing-address">
							<form>
								<span class="locked-billingAddress_toName">${billingAddress.toName?if_exists}</span><br />
								<span class="locked-billingAddress_companyName"><#if billingAddress.companyName?has_content>${billingAddress.companyName?if_exists}</#if></span><br />
								<span class="locked-billingAddress_address1">${billingAddress.address1?if_exists}</span><br />
								<span class="locked-billingAddress_address2"><#if billingAddress.address2?has_content>${billingAddress.address2?if_exists}</#if></span><br />
								<span class="locked-billingAddress_city">${billingAddress.city?if_exists}</span>, <span class="locked-billingAddress_stateProvinceGeoId">${billingAddress.stateProvinceGeoId?if_exists}</span> <span class="locked-billingAddress_postalCode">${billingAddress.postalCode?if_exists}</span><br />
								<span class="locked-billingAddress_countryGeoId">${billingAddress.countryGeoId?if_exists}</span><br />
								<div class="edit-mode">
									<input name="button-cancel" class="jqs-button-cancel" type="button" value="Cancel" />
									<input data-save-type="billing" class="jqs-button-save" name="button-save" type="button" value="Save" />
								</div>
								<div class="non-edit-mode">
									<div name="button-edit" class="jqs-button-edit btn btn-default btn-xs">Edit Info.</div>
								</div>
								<input type="hidden" name="contactMechId" value="${billingAddress.contactMechId?if_exists}" />
								<input name="orderId" type="hidden" value="${order.orderId}">
							</form>
						</div>
						<div class="jqs-editable-info jqs-billing-phone">
							<form>
								<span class="locked-billingTelecom_contactNumber">${(billingTelecom.contactNumber)?if_exists}</span>
								<div class="edit-mode">
									<input name="button-cancel" class="jqs-button-cancel" type="button" value="Cancel" />
									<input data-save-type="billing" class="jqs-button-save" name="button-save" type="button" value="Save" />
								</div>
								<div class="non-edit-mode">
									<div name="button-edit" class="jqs-button-edit btn btn-default btn-xs">Edit Info.</div>
								</div>
								<input type="hidden" name="contactMechId" value="<#if billingTelecom?has_content>${billingTelecom.contactMechId?if_exists}</#if>" />
								<input name="orderId" type="hidden" value="${order.orderId}">
							</form>
						</div>
					<#else>
						NO BILLING ADDRESS FOUND
					</#if>
				</div>
				<div class="col-xs-3">
					<span class="info-header">Shipping: <div class="env-badge tag tag-warning <#if isBlindShipment?string('true', 'false') != 'true'> hidden</#if>" style="font-size: 11px;">BLIND SHIP</div></span>
					<#if shippingAddress?has_content>
						<div class="jqs-editable-info jqs-shipping-address">
							<form>
								<span class="locked-shippingAddress_toName">${shippingAddress.toName?if_exists}</span><br />
								<span class="locked-shippingAddress_companyName"><#if shippingAddress.companyName?has_content>${shippingAddress.companyName?if_exists}</#if></span><br />
								<span class="locked-shippingAddress_address1">${shippingAddress.address1?if_exists}</span><br />
								<span class="locked-shippingAddress_address2"><#if shippingAddress.address2?has_content>${shippingAddress.address2?if_exists}</#if></span><br />
								<span class="locked-shippingAddress_city">${shippingAddress.city?if_exists}</span>, <span class="locked-shippingAddress_stateProvinceGeoId">${shippingAddress.stateProvinceGeoId?if_exists}</span> <var class="shipPostal"><span class="locked-shippingAddress_postalCode">${shippingAddress.postalCode?if_exists}</span></var><br />
								<span class="locked-shippingAddress_countryGeoId">${shippingAddress.countryGeoId?if_exists}</span><br />
								<div class="edit-mode">
									<input name="button-cancel" class="jqs-button-cancel" type="button" value="Cancel" />
									<input data-save-type="shipping" class="jqs-button-save" name="button-save" type="button" value="Save" />
								</div>
								<div class="non-edit-mode">
									<div name="button-edit" class="jqs-button-edit btn btn-default btn-xs">Edit Info.</div>
								</div>
								<input type="hidden" name="contactMechId" value="${shippingAddress.contactMechId?if_exists}" />
								<input name="orderId" type="hidden" value="${order.orderId}">
							</form>
						</div>
						<div class="jqs-editable-info jqs-shipping-phone">
							<form>
								<span class="locked-shippingTelecom_contactNumber">${shippingTelecom.contactNumber?if_exists}</span>
								<div class="edit-mode">
									<input name="button-cancel" class="jqs-button-cancel" type="button" value="Cancel" />
									<input data-save-type="shipping" class="jqs-button-save" name="button-save" type="button" value="Save" />
								</div>
								<div class="non-edit-mode">
									<div name="button-edit" class="jqs-button-edit btn btn-default btn-xs">Edit Info.</div>
								</div>
								<input type="hidden" name="contactMechId" value="${shippingTelecom.contactMechId?if_exists}" />
								<input name="orderId" type="hidden" value="${order.orderId}">
							</form>
						</div>
                        <div>
                            <span class="info-header">Blind Ship:</span>
                            <div class="row">
                                <div class="col-sm-6">
                                    Blind Ship:
                                </div>
                                <div class="col-sm-6">
                                    <input class="tgl tgl-ios isBlindShipUpdate" name="isBlindShip" id="isBlindShipUpdate" value="Y" type="checkbox" <#if isBlindShipment?c == "true">checked</#if>>
                                    <label class="tgl-btn" for="isBlindShipUpdate"></label>
                                </div>
                            </div>
                        </div>
					<#else>
						NO SHIPPING ADDRESS FOUND
					</#if>
				</div>
				<div class="col-xs-3">
					<div class="jqs-editable-info jqs-email">
						<form>
							<span class="info-header">Email:</span>
							<span class="locked-email">${email}</span>
							<div class="edit-mode">
								<input name="button-cancel" class="jqs-button-cancel" type="button" value="Cancel" />
								<input data-save-type="email" class="jqs-button-save" name="button-save" type="button" value="Save" />
							</div>
							<div class="non-edit-mode">
								<div name="button-edit" class="jqs-button-edit btn btn-default btn-xs">Edit Info.</div>
							</div>
							<input name="orderId" type="hidden" value="${order.orderId}">
						</form>
					</div>
					<div>
						${shipping}
					</div>
					<div class="<#if outsourced?has_content && outsourced == "Y">payment_bg_color</#if>">
						<span class="info-header">Outsourcing:</span>
						<div class="row">
							<div class="col-sm-6">
								Outsource Inquiry:
							</div>
							<div class="col-sm-6">
								<input class="tgl tgl-ios outsourcechange" name="outsourceable" id="outsourceable" value="Y" type="checkbox" <#if outsourceable?c == "true">checked</#if>>
								<label class="tgl-btn" for="outsourceable"></label>
							</div>
							<#--
							<div class="col-sm-6">
								Outsourced:
							</div>
							<div class="col-sm-6">
								<select class="outsourcechange" name="outsource">
									<option value="" ></option>
									<option value="Y" <#if outsourced?has_content && outsourced == "Y">selected</#if>>Yes</option>
									<option value="N" <#if outsourced?has_content && outsourced == "N">selected</#if>>No</option>
								</select>
							</div>
							-->
						</div>
					</div>
                    <div>
                        <span class="info-header">Syracuseable:</span>
                        <div class="row">
                            <div class="col-sm-6">
                                Can Print in Syracuse:
                            </div>
                            <div class="col-sm-6">
                                <input class="tgl tgl-ios syracuseableToggle" name="printableSyracuse" id="syracuseableToggle" value="Y" type="checkbox" <#if isSyracuseable?c == "true">checked</#if>>
                                <label class="tgl-btn" for="syracuseableToggle"></label>
                            </div>
                        </div>
                    </div>
					<div>
						<span class="info-header">Netsuite Change:</span>
						<div class="row">
							<div class="col-sm-6">
								Change:
							</div>
							<div class="col-sm-6">
								<input class="tgl tgl-ios pendingNetsuiteChange" name="pendingChange" id="pendingNetsuiteChange" value="Y" type="checkbox" <#if pendingChange?c == "true">checked</#if>>
								<label class="tgl-btn" for="pendingNetsuiteChange"></label>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3 right jqs-comment">
				<span class="info-header">Comments:</span>
				<div class="comment-list">
					<#list notes as note>
						<div class="comment">
							<div class="row">
								<div class="col-xs-12 commenter-name">${note.name}:</div>
								<div class="col-xs-12">
									${note.comment}
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 date text-right">
									${note.createdStamp?string.short}
								</div>
							</div>
						</div>
					</#list>
				</div>
				<div class="non-add-comment">
					<input name="button-add" class="form-control form-control-sm" type="button" value="Add Comment" />
				</div>
				<div id="comment-add" class="comment-add">
					<textarea id="jqs-canned_response_input-order_comment" type="text" name="orderNote" value="" placeholder="Your Comment"></textarea>
					<div class="add-comment">
						<div class="row">
						<div class="col-xs-12 col-sm-6">
						<div class="pull-xs-left" style="margin-top: 10px;">
							<div class="btn btn-primary jqs-button-cancel" href="#">Cancel</div>
							<div class="btn btn-primary jqs-button-save" href="#">Save</div>
						</div>
						</div>
						<div class="col-xs-12 col-sm-6">
						<div class="pull-xs-right" style="margin-top: 10px;" data-dropdown-target="dropdown-canned_responses-order_comment" data-dropdown-options="click ignore-reverse-dropdown" data-dropdown-alternate-parent="comment-add">
							<div class="btn btn-primary btn-wrap">Add Canned Response</div>
						</div>
						</div>
						</div>
					</div>
					<div style="width: 600px;" id="dropdown-canned_responses-order_comment" class="drop-down canned_responses">
						<div>
							<div>
								<div>
									Premade Comments...
								</div>
								<div>
									<p class="jqs-canned_response response_text" data-response-id="order_comment">Left voicemail and emailed regarding declined Credit Card.</p>
									<p class="jqs-canned_response response_text" data-response-id="order_comment">Got new Credit Card, approved and exported.</p>
									<p class="jqs-canned_response response_text" data-response-id="order_comment">Spoke with customer.  Tried Credit Card and was declined.</p>
									<p class="jqs-canned_response response_text" data-response-id="order_comment">Got new Credit Card and was declined.</p>
									<p class="jqs-canned_response response_text" data-response-id="order_comment">Spoke with the customer and said will have to call back.</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="card bg-white p-20 caseTickets hidden">
		<table class="table table-bordered">
			<thead>
			<tr>
				<th>Case</th>
				<th>Status</th>
				<th>Subject</th>
				<th>Body</th>
			</tr>
			</thead>
			<tbody class="bodyData">
			</tbody>
		</table>
	</div>

	<div class="card bg-white p-20">
		<div class="row order-summary">
			<div class="col-xs-12 col-lg-12">
				<div class="panel-heading">
					<span class="line-item-summary pull-xs-left">ORDER SUMMARY (<span class="j-item-count">${items?size}</span>) ITEMS</span>
					<div class="item-link pull-xs-left">&nbsp;</div>
					<#if (items?size > 1)>
					<#list items as item>
					<#assign orderItemSeq = Static["com.envelopes.util.EnvUtil"].removeChar("0", item.item.orderItemSeqId, true, false, false) />
					<div class="item-link pull-xs-left"><a href="#item-${orderItemSeq}">${orderItemSeq}</a></div>
					</#list>
					</#if>
					<div class="pull-xs-right add-item btn-link" data-toggle="modal" data-target="#add-item">
						<i class="fa fa-plus-square"></i>Add Item
					</div>
					<!-- Modal -->
					<div class="modal fade" id="add-item" role="dialog">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">Add an item to this order</h4>
								</div>
								<div class="modal-body row">
									<div class="col-xs-5 text-center">
										<img class="product-image" src="" alt="" title="">
										<div class="text-center product-name"></div>
									</div>
									<div class="col-xs-7">
										<div class="form-group has-feedback">
											<label class="control-label" for="productID">Product ID:</label>
											<input type="text" class="form-control" id="productID">
											<span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
										</div>
										<div class="form-group has-feedback">
											<label class="control-label" for="productQty">Quantity:</label>
											<input type="text" class="form-control" id="productQty">
											<span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
									<button type="button" class="btn btn-primary j-btn-add">Add Item</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<#assign pressIds = Static["com.envelopes.plating.PlateHelper"].getPressList() />
	<script>
		var phraseList = [<#list pressIds.keySet() as ids>'${ids}'<#if ids_has_next>,</#if></#list>];
	</script>


	<#list items as item>
		<#assign product = item.product />
		<#assign features = item.features />
		<#assign orderItem = item.item />
		<#assign remainingQty = orderItem.quantity-orderItem.cancelQuantity?default(0) />
		<#assign orderItemSeq = Static["com.envelopes.util.EnvUtil"].removeChar("0", orderItem.orderItemSeqId, true, false, false) />
		<#assign artworkSource = orderItem.artworkSource?default('')/>

		<#assign frontJPG = "" />
		<#assign backJPG = "" />
		<#assign proofPDF = "" />
		<#assign workerFront = "" />
		<#assign workerFrontNoKeyline = "" />
		<#assign workerFrontWhiteInk = "" />
		<#assign workerBack= "" />
		<#assign workerBackNoKeyline= "" />
		<#assign workerBackWhiteInk= "" />
		<#assign printFrontPDF = "" />
		<#assign printBackPDF = "" />
		<div class="card bg-white p-20">
			<div class="row order-summary">
				<div id="item-${orderItemSeq}" class="order-item jqs-orderItem" data-product-hex="" data-seqid="${orderItem.orderItemSeqId}" data-artwork-id="${(item.artwork.orderItemArtworkId)?if_exists}" data-productid="${product.productId?if_exists}" data-channel="${Static["com.envelopes.util.EnvUtil"].getWebsiteId(order.salesChannelEnumId)}" data-isprinted="${orderItem.artworkSource?has_content?c}">
					<div class="panel-body row">
						<div class="col-xs-2 prod-head">
							<div class="row">
								<div class="col-xs-4 no-pad">
									<span class="pull-xs-left <#if orderItem.artworkSource?has_content>is_printed</#if>">${orderItemSeq}</span>
								</div>
								<div class="col-xs-8 no-pad">
									<img class="product-image" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.productId}?hei=85&amp;wid=150&amp;fmt=png-alpha" alt="" title="" />
								</div>
							</div>
						</div>
						<div class="col-xs-2 prod-info">
							<div>
								<span bns-editname="productDescription" class="product-name"><#if orderItem.productId == "CUSTOM-P" || orderItem.productId == "SAMPLE" || (orderItem.artworkSource?has_content && product.productTypeId == "FOLDER")>${orderItem.itemDescription}<#else>${product.productName}</#if> - ${features["COLOR"]?default("No Color Data")}</span><br />
								<span>Product #: <span class="product-id">${orderItem.productId}</span></span>
								<span>Bin: <span class="product-bin">${product.binLocation?default("")}</span></span>
								<#if orderItem.referenceQuoteId?has_content>
                                    <br />
									<span>
										Quote: 
										<span class="product-quote"><a href="<@ofbizUrl>/foldersQuoteView?quoteId=${orderItem.referenceQuoteId}</@ofbizUrl>">${orderItem.referenceQuoteId}-${orderItem.referenceQuoteItemSeqId}</a> 
									</span>
								</#if>
								<#if orderItem.productId == "CUSTOM-P"><div bns-edittrigger="productDescription" class="btn btn-default btn-xs">Edit</div></#if>
								<div><#if item.vendorOrder?exists && item.vendorOrder.purchaseOrderId?has_content> PO: ${item.vendorOrder.purchaseOrderId}</#if></span></div>
								<div><#if item.item.correspondingPoId?exists> Customer PO#: ${item.item.correspondingPoId}</#if></span></div>
							</div>
						</div>
						<div bns-item_quantity="${remainingQty}" class="col-xs-1 quantity">
							<div>
								QTY:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>${remainingQty}</span>
								<div>
									TOTAL: <span class="item-total">
											<#if orderItem.statusId != "ITEM_CANCELLED">
												<@ofbizCurrency amount=Static["org.apache.ofbiz.order.order.OrderReadHelper"].getOrderItemSubTotal(orderItem, adjustments) isoCode=currencyUomId/>
											<#--($${orderItem.unitPrice})-->
											<#else>
												<@ofbizCurrency amount=0.00 isoCode=currencyUomId/>
											</#if>
									   </span>
								</div>
								<!--<button class="btn btn-white btn-xs reorder" data-reorder="${orderItem.orderId}|${orderItem.orderItemSeqId}">RE-ORDER</button>-->
							</div>
						</div>
						<div class="col-xs-2 itemStatus" data-toggle="tooltip" data-placement="left" data-html="true" data-container="body" <#--title="<#list item.status as status><span style='display: block; white-space: nowrap;'>${status.status?if_exists} - ${status.statusDatetime?string.medium}<#if status_has_next></span></#if></#list>"-->>
							<div>
								<select name="item-status" class="form-control  form-control-sm pull-xs-left<#if orderItem.statusId == "ART_OUTSOURCED" || item.vendorOrder?has_content> payment_bg_color<#elseif orderItem.statusId == "ART_SYRACUSE"> syracused_color</#if>">
									<option value="ITEM_CREATED" <#if orderItem.statusId == "ITEM_CREATED">selected</#if>>Created</option>
									<#--<option value="ITEM_APPROVED" <#if orderItem.statusId == "ITEM_APPROVED">selected</#if>>Approved</option>-->
									<option value="ITEM_CANCELLED" <#if orderItem.statusId == "ITEM_CANCELLED">selected</#if>>Cancelled</option>
									<option value="ITEM_BACKORDERED" <#if orderItem.statusId == "ITEM_BACKORDERED">selected</#if>>Backordered</option>
									<option value="ITEM_READY_FOR_SHIP" <#if orderItem.statusId == "ITEM_READY_FOR_SHIP">selected</#if>>Ready to Ship</option>
                                    <option value="SENT_PURCHASE_ORDER" <#if orderItem.statusId == "SENT_PURCHASE_ORDER">selected</#if>>Sent Purchase Order</option>
                                    <option value="ITEM_SHIPPED" <#if orderItem.statusId == "ITEM_SHIPPED">selected</#if>>Shipped</option>
									<#if orderItem.artworkSource?has_content && item.artwork?has_content>
									<#assign printStatuses = delegator.findByAnd("StatusItem", Static["org.apache.ofbiz.base.util.UtilMisc"].toMap("statusTypeId", "AE_ARTWORK_STTS"), Static["org.apache.ofbiz.base.util.UtilMisc"].toList("sequenceId ASC"), true) />
									<#list printStatuses?sort as printStatus>
										<#if printStatus.statusId != "ART_GOOD_TO_GO">
											<option value="${printStatus.statusId}" <#if orderItem.statusId == printStatus.statusId>selected</#if>>${printStatus.description?if_exists}</option>
										</#if>
									</#list>
									</#if>
								</select>
								<button id="changeItemStatus_${orderItem.orderItemSeqId}" data-current-item-status="${orderItem.statusId}" class="btn btn-primary btn-sm" style="display: inline-block;">Save</button>
								<div class="pull-xs-right update-item btn-link" data-toggle="modal" data-target="#update-item_${orderItem.orderItemSeqId}">
									<i class="fa fa-pencil-square pull-xs-right"></i>
								</div>
								<!-- Modal -->
								<div class="modal fade" id="update-item_${orderItem.orderItemSeqId}" role="dialog">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
												<h4 class="modal-title" class="myModalLabel">Edit Item</h4>
											</div>
											<div class="modal-body row">
												<div class="col-xs-12">
													<div class="form-group">
														<label class="control-label" for="productId">SKU:</label>
														<input type="text" class="form-control productId" value="${orderItem.productId}">
														<label class="control-label" for="productQty">Quantity:</label>
														<input type="text" class="form-control productQty" value="${remainingQty}">
														<input type="radio" name="isPrinted_${orderItem.orderItemSeqId}" value="false" <#if !orderItem.artworkSource?has_content>checked</#if>> Plain &nbsp;&nbsp;&nbsp;
														<input type="radio" name="isPrinted_${orderItem.orderItemSeqId}" value="true" <#if orderItem.artworkSource?has_content>checked</#if>> Printed
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
												<button type="button" class="btn btn-primary j-btn-add">Update Item</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-3">
							<div style="font-size: 11px;color:#000000;">
                                <span class="info-header">Tracking:</span>
								<#if item.shipGroupAssoc?has_content && item.shipGroupAssoc.trackingNumber?has_content>
									<#assign trackingUrl = Static["com.envelopes.util.EnvUtil"].getTrackingURL(item.shipGroupAssoc.trackingNumber)?if_exists />
                                    <a target="_blank" href="${trackingUrl?if_exists}"><span>${item.shipGroupAssoc.trackingNumber}</span></a>
								<#else>
                                    <#if orderItem.statusId == "ITEM_SHIPPED">NO TRACKING AVAILABLE<#else>NOT YET SHIPPED</#if>
								</#if>
                                <br />
								<span class="status-header" style="font-weight: bold;text-decoration: underline">Item Status History</span>
								<#assign status_limit = 3 />
								<#assign hasExtraStatus = 0 />
								<#list item.status as status>
									<#if status_limit gt item.status?seq_index_of(status)>
										<div style="white-space: nowrap;">${status.status?if_exists} - ${status.statusDatetime?string.medium} <#if (status.statusUserLogin)?has_content>(${status.statusUserLogin?if_exists})</#if></div>
									<#else>
										<#if status_limit == item.status?seq_index_of(status) && hasExtraStatus == 0>
											<div class="jqs-extra_status" style="display: none;">
											<#assign hasExtraStatus = 1 />
										</#if>
										<div style="white-space: nowrap;">${status.status?if_exists} - ${status.statusDatetime?string.medium} <#if (status.statusUserLogin)?has_content>(${status.statusUserLogin?if_exists})</#if></div>
									</#if>
								</#list>
								<#if item.status?size gt 3 && hasExtraStatus == 1>
									</div>
									<input class="jqs-status_button" data-action="more" type="button" value="See More" name="status_button" />
								</#if>
							</div>
						</div>
						<div class="col-xs-2 outsource">
							<#if item.autoOutsourceable?c == "true">
								<div>
									<button data-outsource data-orderitemseqid="${orderItem.orderItemSeqId}" type="button" class="btn btn-primary btn-icon btn-sm" data-toggle="modal" data-target="#outsource-item_${orderItem.orderItemSeqId}">
										Outsource <i class="wb-globe"></i>
									</button>
									<!-- Modal -->
									<div class="modal fade" id="outsource-item_${orderItem.orderItemSeqId}" role="dialog">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
													<h4 class="modal-title" id="myModalLabel">Outsource Item</h4>
												</div>
												<div class="modal-body row">
													<div class="col-xs-12">
														<div class="form-group has-feedback">
															<div class="envRow fillData"></div>
															<div class="envRow comments mt-10">
																Comments
																<textarea name="vendorComments" class="form-control" placeholder="Enter comments"></textarea>
															</div>
															<div class="envRow outsourcePrice mt-10">
																Vendor Cost
																<div>
																	$<input style="text-align: right; display: inline-block; width: 60px; padding: 5px;" type="text" name="vendorPriceWhole_${orderItem.orderItemSeqId}" class="form-control" value="0" maxlength="6" /> . <input style="text-align: right; display: inline-block; width: 30px; padding: 5px;" type="text" name="vendorPriceDecimal_${orderItem.orderItemSeqId}" class="form-control" value="00" maxlength="2" />
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
													<button type="button" class="btn btn-primary j-btn-add">Outsource</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							<#elseif orderItem.statusId == "ART_OUTSOURCED" || item.vendorOrder?has_content>
								<div>
									<button type="button" class="btn btn-success btn-icon" data-toggle="modal" data-target="#outsource-item_${orderItem.orderItemSeqId}">
										Outsourced <i class="wb-globe"></i>
									</button>
									<!-- Modal -->
									<div class="modal fade" id="outsource-item_${orderItem.orderItemSeqId}" role="dialog">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
													<h4 class="modal-title" id="myModalLabel">Outsource Data</h4>
												</div>
												<div class="modal-body row">
													<div class="col-xs-12">
														<div><strong>Vendor:</strong> ${item.vendorOrder.partyId?default("Not Available")}</div>
														<div><strong>Date Sent:</strong> ${item.vendorOrder.createdStamp?string.short}</div>
														<div><strong>PO #:</strong> ${item.vendorOrder.purchaseOrderId?default("Not Available")}</div>
														<div><strong>Comments:</strong> <span bns-editable="updateOutsourceComments" data-fieldname="comments">${item.vendorOrder.comments?default("Not Available")}</span> <span bns-buttonedit="updateOutsourceComments" class="btn btn-default btn-xs">Edit</span></div>
														<if item.vendorOrder.priceData?has_content>
															<#assign priceData = Static["com.envelopes.util.EnvUtil"].stringToMap(item.vendorOrder.priceData)?if_exists />
															<div><strong>Price List:</strong></div>
															<div><strong>Net Item Revenue:</strong> <@ofbizCurrency amount=priceData.get("netItemRevenue") isoCode=currencyUomId/></div>
															<#list priceData.get("grossProfit").keySet() as vendorId>
															<#assign vPriceData = Static["com.envelopes.util.EnvUtil"].stringToMap(priceData.get("grossProfit").get(vendorId))?if_exists />
															<#assign grossProfit = priceData.get("netItemRevenue") - vPriceData.get("shipping") - vPriceData.get("cost") />
															<div><strong>${vendorId}:</strong> <@ofbizCurrency amount=grossProfit isoCode=currencyUomId/></div>
															</#list>
														</if>
													</div>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</#if>
						</div>
					</div>
					<#if orderItem.artworkSource?has_content && item.artwork?has_content && order.salesChannelEnumId != "FOLD_SALES_CHANNEL">
						<#if item.content?has_content>
							<#list item.content as content>
								<#if content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_FRONT">
									<#assign frontJPG = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_BACK">
									<#assign backJPG = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_PDF">
									<#assign proofPDF = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_WORKER_FRONT">
									<#assign workerFront = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_WORKER_NOKEY_FRONT">
									<#assign workerFrontNoKeyline = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_WORKER_WHITE_FRONT">
									<#assign workerFrontWhiteInk = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_WORKER_BACK">
									<#assign workerBack = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_WORKER_NOKEY_BACK">
									<#assign workerBackNoKeyline = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_WORKER_WHITE_BACK">
									<#assign workerBackWhiteInk = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_SC7_FRNT_PDF">
									<#assign printFrontPDF = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_SC7_BACK_PDF">
									<#assign printBackPDF = content?if_exists />
								</#if>
							</#list>
						</#if>
						<div class="order-actions">
							<div class="row">
								<div class="col-xs-4 col-lg-4">
									<div class="input-group">
										<div class="input-group-addon">Due Date:</div>
										<input type="text" class="form-control" data-plugin="datepicker" data-format="yyyy-mm-dd" value="<#if orderItem.dueDate?exists>${orderItem.dueDate?string["yyyy-MM-dd"]}<#else>Not Yet Set</#if>">
										<span class="input-group-btn">
											<button data-duedate="" class="btn btn-default" type="button"><i class="wb-check"></i></button>
											<button data-removedate="" class="btn btn-default" type="button"><i class="wb-close"></i></button>
										</span>
									</div>
								</div>
								<div class="col-xs-4 col-lg-4">
									<div class="input-group">
										<div class="input-group-addon"><a href="<#if item.artwork.printPlateId?has_content><@ofbizUrl>/plateEdit</@ofbizUrl>?plateId=${item.artwork.printPlateId}<#else>#</#if>" />Plate ID:</a></div>
										<input type="text" class="form-control" value="<#if item.artwork.printPlateId?has_content>${item.artwork.printPlateId}<#else>Not Yet Plated</#if>" data-auto-fill />
										<span class="input-group-btn">
											<button data-plateid="" class="btn btn-default" type="button"><i class="wb-check"></i></button>
										</span>
									</div>
								</div>
								<div class="col-xs-4 col-lg-4">
									<div class="input-group">
										<div class="input-group-addon">Assigned To:</div>
										<input type="text" class="form-control" value="${item.artwork.assignedToUserLogin?default("Not Yet Assigned")}" />
										<span class="input-group-btn">
											<button data-assignedto="" class="btn btn-default" type="button"><i class="wb-close"></i></button>
										</span>
									</div>
								</div>
							</div>
							<div class="row">
                                <div class="col-xs-3">
                                    <div class="widget dropzone" data-purpose-id="OIACPRP_INT_FILE">
                                        <div class="overlay small">
                                            <div>Drop proof file here to upload</div>
                                        </div>
                                        <div class="widget-title">
                                            <h4>Internal Files</h4>
                                            <span class="tools fileinput-button">
											<i class="fa fa-upload"> Upload</i>
											<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" class="fileupload3">
										</span>
                                        </div>
                                        <div class="widget-body row proof-files">
                                            <div bns-uploaded_files class="col-xs-12">
												<#if item.content?has_content>
													<#list item.content as content>
														<#if content.contentPurposeEnumId == "OIACPRP_INT_FILE" && content.contentPath?has_content>
															<#assign internalFiles = true />
															<#assign fileType = "photo" />
															<#if content.contentPath?has_content && content.contentPath?ends_with("pdf")>
																<#assign fileType = "pdf" />
															<#elseif content.contentPath?has_content && content.contentPath?ends_with("psd")>
																<#assign fileType = "powerpoint" />
															<#elseif content.contentPath?has_content && content.contentPath?ends_with("docx")>
																<#assign fileType = "word" />
															</#if>
                                                            <div class="row">
                                                                <i class="col-xs-6 fa fa-file-${fileType}-o pdf"> <a href="/admin/control/serveFileForStream?filePath=${content.contentPath}&amp;fileName=${content.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${content.contentName?default("_NA_")}</a></i>
                                                                <div class="col-xs-6 text-right">${content.createdStamp?date}</div>
                                                            </div>
														</#if>
													</#list>
												</#if>
												<#if !internalFiles?exists>
                                                    <div>No internal files found.</div>
												</#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
								<div class="col-xs-3">
									<div class="widget dropzone" data-purpose-id="OIACPRP_FILE">
										<div class="overlay small">
											<div>Drop proof file here to upload</div>
										</div>
										<div class="widget-title">
											<h4>Customer Files <#if orderItem.referenceOrderId?has_content>(Re-Use from: <a href="<@ofbizUrl>/viewOrder</@ofbizUrl>?orderId=${orderItem.referenceOrderId}#item-${Static["com.envelopes.util.EnvUtil"].removeChar("0", orderItem.referenceOrderItemSeqId, true, false, false)}">${orderItem.referenceOrderId} - ${orderItem.referenceOrderItemSeqId}</a>)</#if></h4>
											<span class="tools fileinput-button">
											<i class="fa fa-upload"> Upload</i>
											<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" class="fileupload3">
										</span>
										</div>
										<div class="widget-body row proof-files">
											<div bns-uploaded_files class="col-xs-12">
												<#if artworkSource == 'SCENE7_ART_ONLINE'>
													<#assign ugcFiles = Static["com.envelopes.scene7.Scene7Helper"].getUGCFiles(delegator, item.artwork.scene7DesignId)?if_exists />
													<#assign doogmaFiles = Static["com.envelopes.scene7.Scene7Helper"].getDoogmaFiles(delegator, item.artwork.scene7DesignId)?if_exists />
												</#if>
												<#if item.content?has_content>
													<#if ugcFiles?has_content>
														<#list ugcFiles as ugc>
															<#if ugc.folder?has_content>
																<div class="row">
																	<i class="col-xs-6 fa fa-file-photo-o pdf"> <a href="/admin/control/serveFileForStream?filePath=${ugc.folder}&amp;fileName=${ugc.folder?default("_NA_")?replace("&#x2f;","/")?replace(".*\\/(.*?)$", "$1", "r")}&amp;downLoad=Y">${ugc.folder?default("_NA_")?replace("&#x2f;","/")?replace(".*\\/(.*?)$", "$1", "r")} (Scene7 File)</a></i>
																	<div class="col-xs-6 text-right">${ugc.createdStamp?date}</div>
																</div>
															</#if>
														</#list>
													</#if>
													<#list item.content as content>
														<#if content.contentPurposeEnumId == "OIACPRP_FILE" && content.contentPath?has_content>
															<#assign userFiles = true />
															<#assign fileType = "photo" />
															<#if content.contentPath?has_content && content.contentPath?ends_with("pdf")>
																<#assign fileType = "pdf" />
															<#elseif content.contentPath?has_content && content.contentPath?ends_with("psd")>
																<#assign fileType = "powerpoint" />
															<#elseif content.contentPath?has_content && content.contentPath?ends_with("docx")>
																<#assign fileType = "word" />
															</#if>
															<div class="row">
																<i class="col-xs-6 fa fa-file-${fileType}-o pdf"> <a href="/admin/control/serveFileForStream?filePath=${content.contentPath?if_exists}&amp;fileName=${content.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${content.contentName?default("_NA_")}</a></i>
																<div class="col-xs-6 text-right">${content.createdStamp?date}</div>
															</div>
														</#if>
													</#list>
												</#if>
												<#if doogmaFiles?has_content>
													<#list doogmaFiles as url>
														<#if url?has_content>
                                                            <div class="row">
                                                                <i class="col-xs-6 fa fa-file-photo-o pdf"> <a href="${url?default("_NA_")?replace("&#x2f;","/")}">Image (Doogma File)</a></i>
                                                                <div class="col-xs-6 text-right">${order.orderDate?date}</div>
                                                            </div>
														</#if>
													</#list>
												</#if>
												<#if !userFiles?exists && !ugcFiles?has_content && !doogmaFiles?has_content>
													<div>Customer did not upload any files.</div>
												</#if>
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="widget dropzone" data-purpose-id="OIACPRP_WORKER">
										<div class="overlay small">
											<div>Drop proof file here to upload</div>
										</div>
										<div class="widget-title"><h4>Worker</h4>
										<span class="tools fileinput-button">
											<i class="fa fa-upload"> Upload</i>
											<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" class="fileupload2">
										</span>
										</div>
										<div class="widget-body row proof-files">
											<div bns-uploaded_files class="col-xs-12">
												<#if workerFront?exists && workerFront?has_content>
													<div><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${workerFront.contentPath}&amp;fileName=${workerFront.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${workerFront.contentName?default("_NA_")}</a></i></div>
												</#if>
												<#if workerFrontNoKeyline?exists && workerFrontNoKeyline?has_content>
													<div><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${workerFrontNoKeyline.contentPath}&amp;fileName=${workerFrontNoKeyline.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${workerFrontNoKeyline.contentName?default("_NA_")}</a></i></div>
												</#if>
												<#if workerFrontWhiteInk?exists && workerFrontWhiteInk?has_content>
													<div><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${workerFrontWhiteInk.contentPath}&amp;fileName=${workerFrontWhiteInk.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${workerFrontWhiteInk.contentName?default("_NA_")}</a></i></div>
												</#if>
												<#if workerBack?exists && workerBack?has_content>
													<div><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${workerBack.contentPath}&amp;fileName=${workerBack.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${workerBack.contentName?default("_NA_")}</a></i></div>
												</#if>
												<#if workerBackNoKeyline?exists && workerBackNoKeyline?has_content>
													<div><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${workerBackNoKeyline.contentPath}&amp;fileName=${workerBackNoKeyline.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${workerBackNoKeyline.contentName?default("_NA_")}</a></i></div>
												</#if>
												<#if workerBackWhiteInk?exists && workerBackWhiteInk?has_content>
													<div><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${workerBackWhiteInk.contentPath}&amp;fileName=${workerBackWhiteInk.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${workerBackWhiteInk.contentName?default("_NA_")}</a></i></div>
												</#if>
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-3">
									<div bns-scene7_proofs class="widget dropzone" data-purpose-id="OIACPRP_PDF">
										<div class="overlay small">
											<div>Drop proof file here to upload</div>
										</div>
										<div class="widget-title"><h4>Proof<#if artworkSource == 'SCENE7_ART_ONLINE'> (Scene7)</#if></h4>
										<span class="tools fileinput-button">
											<i class="fa fa-upload"> Upload</i>
											<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" class="fileupload2">
										</span>
										</div>
										<div class="widget-body row proof-files">
											<div bns-texel_pdf style="display: none;" class="col-xs-12"></div>
											<div bns-uploaded_files class="col-xs-12">
												<#if proofPDF?exists && proofPDF?has_content>
													<div bns-display="${proofPDF.contentName?default("_NA_")}"><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${proofPDF.contentPath}&amp;fileName=${proofPDF.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${proofPDF.contentName?default("_NA_")}</a></i></div>
												</#if>
												<#if printFrontPDF?exists && printFrontPDF?has_content>
													<div bns-display="Download Front Print File"><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${printFrontPDF.contentPath}&amp;fileName=${printFrontPDF.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">Download Front Print File</a></i></div>
												</#if>
												<#if printBackPDF?exists && printBackPDF?has_content>
													<div bns-display="Download Back Print File"><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${printBackPDF.contentPath}&amp;fileName=${printBackPDF.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">Download Back Print File</a></i></div>
												</#if>
											</div>
											<div bns-temporary_downloads style="display: none;" class="col-xs-12"></div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-6">
									<div class="widget">
										<div class="widget-title">
											<h4>Front  </h4>
											<span class="tools fileinput-button">
												<i class="fa fa-upload"> Upload</i>
												<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" class="fileupload">
											</span>
										</div>
										<div class="widget-body row">
											<div style="display: table; width: 100%;">
												<div style="display: table-cell; width: auto; vertical-align: top;">
													<div class="dropzone" data-purpose-id="OIACPRP_FRONT">
														<#if frontJPG?has_content>
															<img src="/admin/control/serveFileForStream?filePath=${frontJPG.contentPath}" alt="Front" title="Front" />
														<#else>
															<div class="img-placeholder">No JPG Available</div>
														</#if>
														<div class="overlay">
															<div>Drop front file here<br/>to upload</div>
														</div>
													</div>
												</div>
												<div style="display: table-cell; width: 150px; vertical-align: top;">
													<div class="row">
														<span class="col-xs-6">Colors:</span>
														<div class="col-xs-6 dropdown no-left-padding">
															<select name="colorsFront" class="dropdown-select form-control form-control-sm" data-defaultvalue="<#if item.attribute.colorsFront?has_content>${item.attribute.colorsFront}</#if>">
																<option value="0">0</option>
																<option value="1" <#if item.attribute.colorsFront?has_content && item.attribute.colorsFront == "1">selected</#if>>1</option>
																<option value="2" <#if item.attribute.colorsFront?has_content && item.attribute.colorsFront == "2">selected</#if>>2</option>
																<option value="4" <#if item.attribute.colorsFront?has_content && item.attribute.colorsFront == "4">selected</#if>>4</option>
															</select>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<span class="col-xs-6">White:</span>
														<input class="tgl tgl-ios" name="whiteInkFront" id="${orderItemSeq}whiteInkFront" type="checkbox" <#if item.attribute.whiteInkFront?has_content && item.attribute.whiteInkFront == "true">checked</#if>>
														<label class="tgl-btn col-xs-6 no-left-padding" for="${orderItemSeq}whiteInkFront"></label>
													</div>
													<span>Ink Names:</span>
													<input class="form-control form-control-sm" style="display: block;" type="text" name="frontInkColor1" value="${item.artwork.frontInkColor1?default("")}" />
													<input class="form-control form-control-sm" style="display: block;" type="text" name="frontInkColor2" value="${item.artwork.frontInkColor2?if_exists}" />
													<input class="form-control form-control-sm" style="display: block;" type="text" name="frontInkColor3" value="${item.artwork.frontInkColor3?if_exists}" />
													<input class="form-control form-control-sm" style="display: block;" type="text" name="frontInkColor4" value="${item.artwork.frontInkColor4?if_exists}" />
													<#if item.artwork.scene7DesignId?exists>
													<div class="jqs-proofParent" data-designId="${item.artwork.scene7DesignId?if_exists}" data-designParentId="${item.scene7DesignParentId?if_exists}">
														<button class="btn btn-primary jqs-designnow btn-sm">Edit Design</button>
														<#if item.attribute.addresses?has_content && item.attribute.addresses?number gt 0>
														<button class="btn btn-primary jqs-variabledatagrid m-t-10">Edit Addressing</button>
														</#if>
														<button class="btn btn-danger jqs-saveDesignChanges m-t-10 hidden btn-sm">Save Design</button>
														<div class="row" style="padding-top: 10px;">
															<span class="col-xs-7">Lock Design:</span>
															<input class="tgl tgl-ios" name="lockScene7Design" id="lockScene7Design" type="checkbox" <#if orderItem.lockScene7Design?has_content && orderItem.lockScene7Design == "Y">checked</#if>>
															<label class="tgl-btn col-xs-6 no-left-padding" for="lockScene7Design"></label>
														</div>
													</div>
													</#if>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="widget">
										<div class="widget-title">
											<h4>Back</h4>
											<span class="tools fileinput-button">
												<i class="fa fa-upload"> Upload</i>
													<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" class="fileupload">
											</span>
										</div>
										<div class="widget-body row">
											<div style="display: table; width: 100%;">
												<div style="display: table-cell; width: auto; vertical-align: top;">
													<div class="dropzone" data-purpose-id="OIACPRP_BACK">
														<#if backJPG?has_content>
															<img src="/admin/control/serveFileForStream?filePath=${backJPG.contentPath}" alt="Back" title="Back" />
														<#else>
															<div class="img-placeholder">No JPG Available</div>
														</#if>
														<div class="overlay">
															<div>Drop front file here<br/>to upload</div>
														</div>
													</div>
												</div>
												<div style="display: table-cell; width: 150px; vertical-align: top;">
													<div class="row">
														<span class="col-xs-6">Colors:</span>
														<div class="col-xs-6 dropdown no-left-padding">
														<select name="colorsBack" class="dropdown-select form-control form-control-sm" data-defaultvalue="<#if item.attribute.colorsBack?has_content>${item.attribute.colorsBack}</#if>">
															<option value="0">0</option>
															<option value="1" <#if item.attribute.colorsBack?has_content && item.attribute.colorsBack == "1">selected</#if>>1</option>
															<option value="2" <#if item.attribute.colorsBack?has_content && item.attribute.colorsBack == "2">selected</#if>>2</option>
															<option value="4" <#if item.attribute.colorsBack?has_content && item.attribute.colorsBack == "4">selected</#if>>4</option>
														</select>
														</div>
													</div>
													<div class="row" style="padding-top: 10px;">
														<span class="col-xs-6">White:</span>
														<input class="tgl tgl-ios" name="whiteInkBack" id="${orderItemSeq}whiteInkBack" type="checkbox" <#if item.attribute.whiteInkBack?has_content && item.attribute.whiteInkBack == "true">checked</#if>>
														<label class="tgl-btn col-xs-6" for="${orderItemSeq}whiteInkBack"></label>
													</div>
													<span>Ink Names:</span>
													<input class="form-control form-control-sm" type="text" name="backInkColor1" value="${item.artwork.backInkColor1?default("")}" />
													<input class="form-control form-control-sm" type="text" name="backInkColor2" value="${item.artwork.backInkColor2?if_exists}" />
													<input class="form-control form-control-sm" type="text" name="backInkColor3" value="${item.artwork.backInkColor3?if_exists}" />
													<input class="form-control form-control-sm" type="text" name="backInkColor4" value="${item.artwork.backInkColor4?if_exists}" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row customizing-options">
								<div class="customCol-7">
									<#if item.attribute.addresses?has_content && item.scene7DesignParentId?has_content>
										<#assign addressBookId = Static["com.envelopes.addressbook.AddressBookEvents"].getAddressBookIdByParent(delegator, item.scene7DesignParentId)?if_exists />
									</#if>
									<div bns-total_addresses="${item.attribute.addresses?default("0")}" bns-address_book_id="<#if addressBookId?has_content>${addressBookId}<#else>-1</#if>" class="widget fixed addresses">
										<div class="widget-title" <#if item.attribute.addresses?has_content && item.attribute.addresses != "0">style="background: #FAFF8E;"</#if>>
											<h4>Addressing <#if addressBookId?has_content><a href="<@ofbizUrl>/downloadNewAddressBook</@ofbizUrl>?id=${addressBookId}">[CSV]</a>&nbsp;<a href="<@ofbizUrl>/downloadNewAddressBook</@ofbizUrl>?id=${addressBookId}&format=xlsx">[XLSX]</a></#if></h4>
										</div>
										<div class="widget-body row">
											<input type="text" name="addresses" id="${orderItemSeq}addresses" class=" form-control  form-control-sm" <#if item.attribute.addresses?has_content && item.attribute.addresses != "0">style="background: #FAFF8E;"</#if> value="<#if item.attribute.addresses?has_content>${item.attribute.addresses}<#else>0</#if>">
										</div>
									</div>
								</div>
								<div class="customCol-7">
									<div class="widget fixed folded">
										<div class="widget-title" <#if item.attribute.isFullBleed?has_content && item.attribute.isFullBleed == "true">style="background: #FAFF8E;"</#if>>
											<h4>Full Bleed</h4>
										</div>
										<div class="widget-body row">
											<input class="tgl tgl-ios" name="isFullBleed" id="${orderItemSeq}isFullBleed" type="checkbox" <#if item.attribute.isFullBleed?has_content && item.attribute.isFullBleed == "true">checked</#if>>
											<label class="tgl-btn paddin-left-xxs" for="${orderItemSeq}isFullBleed"></label>
										</div>
									</div>
								</div>
								<div class="customCol-7">
									<div class="widget fixed folded">
										<div class="widget-title" <#if item.attribute.isFolded?has_content && item.attribute.isFolded == "true">style="background: #FAFF8E;"</#if>>
											<h4>Folded</h4>
										</div>
										<div class="widget-body row">
											<input class="tgl tgl-ios" name="isFolded" id="${orderItemSeq}isFolded" type="checkbox" <#if item.attribute.isFolded?has_content && item.attribute.isFolded == "true">checked</#if>>
											<label class="tgl-btn paddin-left-xxs" for="${orderItemSeq}isFolded"></label>
										</div>
									</div>
								</div>
								<div class="customCol-7">
									<div class="widget fixed rush">
										<div class="widget-title"><h4>Color Matched</h4></div>
										<div class="widget-body row">
											<input class="tgl tgl-ios" name="inkMatched" id="${orderItemSeq}inkMatched" type="checkbox" <#if item.artwork.inkMatched?has_content && item.artwork.inkMatched == "Y">checked</#if>>
											<label class="tgl-btn" for="${orderItemSeq}inkMatched"></label>
										</div>
									</div>
								</div>
								<div class="customCol-7">
									<div class="widget fixed cuts">
										<div class="widget-title" <#if item.attribute.cuts?has_content && (item.attribute.cuts == "1" || item.attribute.cuts == "2" || item.attribute.cuts == "4")>style="background: #FAFF8E;"</#if>>
											<h4>Cuts</h4>
										</div>
										<div class="widget-body row">
											<div class="dropdown">
												<select name="cuts" class="dropdown-select form-control  form-control-sm" data-defaultvalue="<#if item.attribute.cuts?has_content>${item.attribute.cuts}<#else>0</#if>">
													<option value="0">0</option>
													<option value="1" <#if item.attribute.cuts?has_content && item.attribute.cuts == "1">selected</#if>>1</option>
													<option value="2" <#if item.attribute.cuts?has_content && item.attribute.cuts == "2">selected</#if>>2</option>
													<option value="4" <#if item.attribute.cuts?has_content && item.attribute.cuts == "4">selected</#if>>4</option>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="customCol-7">
									<div class="widget fixed rush">
										<div class="widget-title"><h4>Rush</h4></div>
										<div class="widget-body row">
											<input class="tgl tgl-ios" name="isRushProduction" id="${orderItemSeq}isRushProduction" type="checkbox" <#if orderItem.isRushProduction?has_content && orderItem.isRushProduction == "Y">checked</#if>>
											<label class="tgl-btn" for="${orderItemSeq}isRushProduction"></label>
										</div>
									</div>
								</div>
								<div class="customCol-7">
									<div class="widget fixed savePrintSample jqs-savePrintSample">
										<div class="widget-title"><h4>Save Print Sample</h4></div>
										<div class="widget-body row">
											<input class="tgl tgl-ios" name="savePrintSample" id="${orderItemSeq}savePrintSample" type="checkbox" <#if orderItem.savePrintSample?has_content && orderItem.savePrintSample == "Y">checked</#if>>
											<label class="tgl-btn" for="${orderItemSeq}savePrintSample"></label>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-3">
									<div class="widget">
										<div class="widget-title">
											<h4>Comments for Pressman</h4>
										</div>
										<div class="widget-body">
											<textarea class="conversation-input  form-control" name="conversation-input-1" placeholder="Comments to Pressman">${item.artwork.itemPressmanComments?if_exists}</textarea>
											<a href="#" class="btn btn-primary btn-sm conversation-send pressManCommentSave">Update</a>
										</div>
									</div>
								</div><div class="col-xs-3">
									<div class="widget">
										<div class="widget-title">
											<h4>Comments for PrePress</h4>
										</div>
										<div class="widget-body">
											<textarea class="conversation-input  form-control" name="conversation-input-1" placeholder="Comments to PrePress">${item.artwork.itemPrepressComments?if_exists}</textarea>
											<a href="#" class="btn btn-primary btn-sm conversation-send prePressCommentSave">Update</a>
										</div>
									</div>
								</div><div class="col-xs-3">
									<div class="widget">
										<div class="widget-title">
											<h4>Comments to Customer</h4>
										</div>
										<div class="widget-body">
											<textarea id="jqs-canned_response_input-${orderItemSeq}" class="conversation-input  form-control" name="conversation-input-2" placeholder="Comments to Customer">${item.artwork.itemCustomerComments?if_exists}</textarea>
											<div id="ctc-button-row-${orderItemSeq}" class="row no-margin m-b-10">
												<div class="col-xs-12">
													<div class="pull-xs-left">
														<a href="#" class="btn btn-primary btn-sm conversation-send customerCommentSave" data-response-id="${orderItemSeq}">Update</a>
													</div>
													<div class="pull-xs-right" style="margin-top: 10px;" data-dropdown-target="dropdown-canned_responses-${orderItemSeq}" data-dropdown-options="click ignore-reverse-dropdown" data-dropdown-alternate-parent="ctc-button-row-${orderItemSeq}">
														<div class="btn btn-primary btn-sm">Add Canned Response</div>
													</div>
												</div>
											</div>
											<div style="width: 600px;" id="dropdown-canned_responses-${orderItemSeq}" class="drop-down canned_responses">
												<div>
													<div>
														<div>
															Need New Art - Low Res
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">The artwork you uploaded is low resolution and probably won't print well. If you can, please upload a color separated, high resolution file. The file it was originally created in works best. (eps or pdf)</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Art is a bit low resolution and could print better quality with a higher resolution file. The file it was originally created in works best. (eps or pdf)</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">We had to adjust your artwork so it fit inside our template guidelines correctly. Please read over carefully and confirm all is correct.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">By approving this proof, you are confirming all content, spelling and placement are correct. All will be printed as seen in proof.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">We matched the color in your file as closely as possible. Please refer to a pantone solid uncoated swatch for an accurate representation of the final printed color. Colors viewed on screen are not accurate as to what will print. If color is critical, please supply a pantone color or a printed sample to be color matched.</p>
														</div>
													</div>
													<div>
														<div>
															Ink Colors
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Your order was placed for X color printing but your artwork has X colors. We have converted your artwork to X color printing. This will affect your price. The new cost for printing X ink colors is $XXX.XX before shipping. By approving this proof you are approving this new cost.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note, ink colors may darken when printed on this color stock.</p>
														</div>
													</div>
													<div>
														<div>
															Converting to 1 Color
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Your order was placed for one color, so your art has been converted to Grayscale. If you would like two color print, please reject the proof and specify in the rejection comments. We will adjust your order and send a new proof with new pricing. By approving the proof, you are approving all art as seen in the proof.</p>
														</div>
													</div>
													<div>
														<div>
															White Ink
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that all our proofs are set up on a white background and the artwork you see in black will print white.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note, we will need an extra day of production for printing white ink on this large of a quantity.</p>
														</div>
													</div>
													<div>
														<div>
															Digital Orders
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that the digital press does not print pantone ink so colors will be approximate.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please be aware that this item is printing digitally while the other item(s) is printing offset so colors may not be an exact match.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please be aware that sometimes when printing exclusive stock on digital quantities, discoloration or fading may occur. This is due to the texture of the paper and the pressure and heat from the press.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">On digital orders we need .25 border from the edge so we cannot print bleeds. Your artwork was adjusted. If you do not like the changes, you can either reject and upload new artwork OR you can up your quantity to 500 and print Offset which does allow bleeds. Your new cost for 500 would be $XX. With your proof approval, you are approving this new cost.</p>
														</div>
													</div>
													<div>
														<div>
															Heavy Ink Fees
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Your artwork is considered to be heavy coverage. This will incur a heavy ink coverage fee of $XX. With your proof approval you are approving this additional fee.</p>
														</div>
													</div>
													<div>
														<div>
															Offset
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that printing large dark areas of ink increases the risk of offset which is a transfer of wet ink onto the back of adjacent envelopes from it not being completely dry.</p>
														</div>
													</div>
													<div>
														<div>
															Seam Lines
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Because our envelopes are constructed prior to printing, seam lines and flap impressions may be visible in your art when printing large coverage.** Any type going over seams may distort slightly.</p>
														</div>
													</div>
													<div>
														<div>
															Full Bleed Warning
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">It is normal for the artwork on the finished envelopes to vary slightly from one piece to another because of movement during the run and could vary up to 1/8". As a result of the intensive bindery processes, die cutting, scoring, folding and gluing (ie. converting), the color wrap &amp; over-bleed may not be even on all sides.</p>
														</div>
													</div>
													<div>
														<div>
															Address
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">There was no address in your file. If you want to add one, please reject the proof and provide us with an address.</p>
														</div>
													</div>
													<div>
														<div>
															Check Payable
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">There is no information stating who to make checks payable to. If you would like to add this information please reject the proof and advise in the comments.</p>
														</div>
													</div>
													<div>
														<div>
															Clasp Warning
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that it is not unusual for there to be a clasp impression on the front of a clasp envelope. The marks are caused by the envelopes rubbing on the metal clasp of the adjacent envelope. The marks are not caused by the manufacturing process but rather during shipment. This is just the nature of the product and can not be avoided and is therefore, not considered a defect.</p>
														</div>
													</div>
													<div>
														<div>
															PDF Screen Effect
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">What you are describing is a screen effect experienced when viewing a PDF file in Adobe Acrobat and has no effect on the final print quality. Zooming in closely on an object or printing the file will give you a better representation of the quality of the final printed product.</p>
														</div>
													</div>
													<div>
														<div>
															Mini
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that these envelopes are not acceptable for mailing. If you need a mailing envelope you would need to change to an A1 size (3.625x5.125), please reject the proof or call if you need it changed.</p>
														</div>
													</div>
													<div>
														<div>
															Layout for Mailing
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">*You may want to check the layout of your envelope with the post office to make sure it's ok for mailing since you have art in the stamp area.</p>
														</div>
													</div>
													<div>
														<div>
															Return/Reply Confusion
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">By approving this proof you are approving you want the address in the middle of the envelope in the REPLY address position.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">By approving this proof you are approving you want the address at the top left corner of the envelope in the RETURN address position.</p>
														</div>
													</div>
													<div>
														<div>
															Tyvek Warning
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note, Tyvek stock has a texture that can easily slip on press. There is a chance the artwork may be shifted during the printing process.</p>
														</div>
													</div>
													<div>
														<div>
															Bleed Line
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please be aware that while our presses are capable of bleeding on this size envelope, a thin line of ink will be transferred onto the back. For this to be avoided, the bleed should be cropped back to have at least an 1/8th of an inch border from the envelope edge.</p>
														</div>
													</div>
													<div>
														<div>
															Remittance Confusion
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please make sure it is a remittance envelope you need and not a regular #9 envelope. A remittance envelope has the large flap (as shown) and you can print on 4 panels, the front and back top and bottom. A #9 regular is just slightly smaller than a regular business #10 envelope. Please advise.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please confirm you want these printed on the #6.25 remittance with the longer flap and not the #6.25 regular envelopes with the shorter flap. Both are the same size closed. Please reject proof and specify in comments if you would like to change the stock.</p>
														</div>
													</div>
													<div>
														<div>
															Paperboard Mailers
														</div>
														<div>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Art should be simple line art with no tight registration.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Art should not contain gradients or screens.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Art should not contain large ink coverage areas or bleeds.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Minimum 1/2 inch border should be left on envelope edges.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">There is an additional fee for printing on both sides of the envelope. With your proof approval, you are approving this new cost/additional fee.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">We do not print metallic inks.</p>
															<p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">There is a 15 day production schedule for paperboard mailers.</p>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div><div class="col-xs-3">
									<div class="widget">
										<div class="widget-title">
											<h4>Comments From Customer</h4>
										</div>
										<div class="widget-body">
											<div class="customerComments">
												<#if orderItem.comments?has_content>
													<pre><span>${orderItem.createdStamp?string.short} - </span>${orderItem.comments?if_exists}</pre>
												</#if>
												<#if item.comments?has_content>
													<#list item.comments as comment>
														<pre><span>${comment.createdStamp?string.short} - </span>${comment.message?if_exists}</pre>
													</#list>
												</#if>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					<#elseif orderItem.artworkSource?has_content && item.artwork?has_content && order.salesChannelEnumId == "FOLD_SALES_CHANNEL">
						<#if item.content?has_content>
							<#list item.content as content>
								<#if content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_FRONT">
									<#assign frontJPG = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_BACK">
									<#assign backJPG = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_PDF">
									<#assign proofPDF = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_WORKER_FRONT">
									<#assign workerFront = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_WORKER_BACK">
									<#assign workerBack = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_SC7_FRNT_PDF">
									<#assign printFrontPDF = content?if_exists />
								<#elseif content.contentPurposeEnumId?has_content && content.contentPurposeEnumId == "OIACPRP_SC7_BACK_PDF">
									<#assign printBackPDF = content?if_exists />
								</#if>
							</#list>
						</#if>
                        <div class="order-actions">
                            <div class="row">
                                <div class="col-xs-4 col-lg-4">
                                    <div class="input-group">
                                        <div class="input-group-addon">Due Date:</div>
                                        <input type="text" class="form-control" data-plugin="datepicker" data-format="yyyy-mm-dd" value="<#if orderItem.dueDate?exists>${orderItem.dueDate?string["yyyy-MM-dd"]}<#else>Not Yet Set</#if>">
                                        <span class="input-group-btn">
											<button data-duedate="" class="btn btn-default" type="button"><i class="wb-check"></i></button>
											<button data-removedate="" class="btn btn-default" type="button"><i class="wb-close"></i></button>
										</span>
                                    </div>
                                </div>
                                <div class="col-xs-4 col-lg-4 offset-lg-4">
                                    <div class="input-group">
                                        <div class="input-group-addon">Assigned To:</div>
                                        <input bns-assignedto type="text" class="form-control" value="${item.artwork.assignedToUserLogin?default("Not Yet Assigned")}" />
                                        <span class="input-group-btn">
											<button data-assignedto="add" class="btn btn-default" type="button"><i class="wb-check"></i></button>
											<button data-assignedto="delete" class="btn btn-default" type="button"><i class="wb-close"></i></button>
										</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3">
                                    <div class="widget dropzone" data-purpose-id="OIACPRP_INT_FILE">
                                        <div class="overlay small">
                                            <div>Drop proof file here to upload</div>
                                        </div>
                                        <div class="widget-title">
                                            <h4>Internal Files</h4>
                                            <span class="tools fileinput-button">
											<i class="fa fa-upload"> Upload</i>
											<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" class="fileupload3">
										</span>
                                        </div>
                                        <div class="widget-body row proof-files">
                                            <div bns-uploaded_files class="col-xs-12">
												<#if item.content?has_content>
													<#list item.content as content>
														<#if content.contentPurposeEnumId == "OIACPRP_INT_FILE" && content.contentPath?has_content>
															<#assign internalFiles = true />
															<#assign fileType = "photo" />
															<#if content.contentPath?has_content && content.contentPath?ends_with("pdf")>
																<#assign fileType = "pdf" />
															<#elseif content.contentPath?has_content && content.contentPath?ends_with("psd")>
																<#assign fileType = "powerpoint" />
															<#elseif content.contentPath?has_content && content.contentPath?ends_with("docx")>
																<#assign fileType = "word" />
															</#if>
                                                            <div class="row">
                                                                <i class="col-xs-6 fa fa-file-${fileType}-o pdf"> <a href="/admin/control/serveFileForStream?filePath=${content.contentPath}&amp;fileName=${content.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${content.contentName?default("_NA_")}</a></i>
                                                                <div class="col-xs-6 text-right">${content.createdStamp?date}</div>
                                                            </div>
														</#if>
													</#list>
												</#if>
												<#if !internalFiles?exists>
                                                    <div>No internal files found.</div>
												</#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="widget dropzone" data-purpose-id="OIACPRP_FILE">
                                        <div class="overlay small">
                                            <div>Drop proof file here to upload</div>
                                        </div>
                                        <div class="widget-title">
                                            <h4>Customer Files <#if orderItem.referenceOrderId?has_content>(Re-Use from: <a href="<@ofbizUrl>/viewOrder</@ofbizUrl>?orderId=${orderItem.referenceOrderId}#item-${Static["com.envelopes.util.EnvUtil"].removeChar("0", orderItem.referenceOrderItemSeqId, true, false, false)}">${orderItem.referenceOrderId} - ${orderItem.referenceOrderItemSeqId}</a>)</#if></h4>
                                            <span class="tools fileinput-button">
											<i class="fa fa-upload"></i> Upload
											<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" class="fileupload3">
										</span>
                                        </div>
                                        <div class="widget-body row proof-files">
                                            <div bns-uploaded_files class="col-xs-12">
												<#assign doogmaFiles = Static["com.envelopes.scene7.Scene7Helper"].getDoogmaFiles(delegator, item.artwork.scene7DesignId)?if_exists />
												<#if artworkSource == 'SCENE7_ART_ONLINE'>
													<#assign ugcFiles = Static["com.envelopes.scene7.Scene7Helper"].getUGCFiles(delegator, item.artwork.scene7DesignId)?if_exists />
												</#if>
												<#if item.content?has_content>
													<#if ugcFiles?has_content>
														<#list ugcFiles as ugc>
															<#if ugc.folder?has_content>
                                                                <div class="row">
                                                                    <div class="col-sm-12 col-md-8">
                                                                        <i class="fa fa-file-photo-o pdf"></i> <a href="/admin/control/serveFileForStream?filePath=${ugc.folder}&amp;fileName=${ugc.folder?default("_NA_")?replace("&#x2f;","/")?replace(".*\\/(.*?)$", "$1", "r")}&amp;downLoad=Y">${ugc.folder?default("_NA_")?replace("&#x2f;","/")?replace(".*\\/(.*?)$", "$1", "r")} (Scene7 File)</a>
                                                                    </div>
                                                                    <div class="col-sm-12 col-md-4 text-right">${ugc.createdStamp?date}</div>
                                                                </div>
															</#if>
														</#list>
													</#if>
													<#list item.content as content>
														<#if content.contentPurposeEnumId == "OIACPRP_FILE">
															<#assign userFiles = true />
															<#assign fileType = "photo" />
															<#if content.contentPath?has_content && content.contentPath?ends_with("pdf")>
																<#assign fileType = "pdf" />
															<#elseif content.contentPath?has_content && content.contentPath?ends_with("psd")>
																<#assign fileType = "powerpoint" />
															<#elseif content.contentPath?has_content && content.contentPath?ends_with("docx")>
																<#assign fileType = "word" />
															</#if>
                                                            <div class="row">
                                                                <div class="col-sm-12 col-md-8 break-word">
                                                                    <i class="fa fa-file-${fileType}-o pdf"></i> <a href="/admin/control/serveFileForStream?filePath=${content.contentPath?if_exists}&amp;fileName=${content.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${content.contentName?default("_NA_")}</a>
                                                                </div>
                                                                <div class="col-sm-12 col-md-4 text-right">${content.createdStamp?date}</div>
                                                            </div>
														</#if>
													</#list>
												</#if>
												<#if doogmaFiles?has_content>
													<#list doogmaFiles as url>
														<#if url?has_content>
                                                            <div class="row">
                                                                <i class="col-xs-6 fa fa-file-photo-o pdf"> <a href="${url?default("_NA_")?replace("&#x2f;","/")}">Image (Doogma File)</a></i>
                                                                <div class="col-xs-6 text-right">${order.orderDate?date}</div>
                                                            </div>
														</#if>
													</#list>
												</#if>
												<#if !userFiles?exists && !ugcFiles?has_content && !doogmaFiles?has_content>
                                                    <div>Customer did not upload any files.</div>
												</#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="widget dropzone" data-purpose-id="OIACPRP_WORKER">
                                        <div class="overlay small">
                                            <div>Drop proof file here to upload</div>
                                        </div>
                                        <div class="widget-title"><h4>Worker</h4>
                                            <span class="tools fileinput-button">
												<i class="fa fa-upload"></i> Upload
												<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" class="fileupload2">
											</span>
                                        </div>
                                        <div class="widget-body row proof-files">
                                            <div bns-uploaded_files class="col-xs-12">
												<#if workerFront?exists && workerFront?has_content>
                                                    <div><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${workerFront.contentPath}&amp;fileName=${workerFront.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${workerFront.contentName?default("_NA_")}</a></i></div>
												</#if>
												<#if workerBack?exists && workerBack?has_content>
                                                    <div><i class="fa fa-file pdf"> <a href="/admin/control/serveFileForStream?filePath=${workerBack.contentPath}&amp;fileName=${workerBack.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${workerBack.contentName?default("_NA_")}</a></i></div>
												</#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="widget dropzone" data-purpose-id="OIACPRP_PDF">
                                        <div class="overlay small">
                                            <div>Drop proof file here to upload</div>
                                        </div>
                                        <div class="widget-title"><h4>Proof<#if artworkSource == 'SCENE7_ART_ONLINE'> (Scene7)</#if></h4>
                                            <span class="tools fileinput-button">
												<i class="fa fa-upload"></i> Upload
												<input type="file" name="files[]" data-url="/admin/control/uploadArtwork" data-ignorestatuschange="1" class="fileupload2">
											</span>
                                        </div>
                                        <div class="widget-body row proof-files">
                                            <div bns-uploaded_files class="col-xs-12">
												<#if proofPDF?exists && proofPDF?has_content>
                                                    <div><i class="fa fa-file pdf"></i> <a href="/admin/control/serveFileForStream?filePath=${proofPDF.contentPath}&amp;fileName=${proofPDF.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${proofPDF.contentName?default("_NA_")}</a></div>
												<#else>
                                                    <div>No Proof Uploaded</div>
												</#if>
												<#if printFrontPDF?exists && printFrontPDF?has_content>
                                                    <div><i class="fa fa-file pdf"></i> <a href="/admin/control/serveFileForStream?filePath=${printFrontPDF.contentPath}&amp;fileName=${printFrontPDF.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y"><!--${printFrontPDF.contentName?default("_NA_")}-->Download Front Print File</a></div>
												</#if>
												<#if printBackPDF?exists && printBackPDF?has_content>
                                                    <div><i class="fa fa-file pdf"></i> <a href="/admin/control/serveFileForStream?filePath=${printBackPDF.contentPath}&amp;fileName=${printBackPDF.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y"><!--${printBackPDF.contentName?default("_NA_")}-->Download Back Print File</a></div>
												</#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="widget fixed addresses m-b-2">
                                        <div class="widget-title">
                                            <h4>Side 1</h4>
                                        </div>
                                        <div class="widget-body">
                                            <div class="row">
                                                <div class="col-md-12">
												<#--
                                                <div class="m-b-1">
                                                    <img src="http://via.placeholder.com/900x500">
                                                </div>
                                                -->
                                                    <div class="row">
                                                        <div class="col-lg-3">
                                                            Heavy Coverage:
                                                            <input id="${orderItemSeq}side1HeavyCoverage" name="side1HeavyCoverage" class="tgl tgl-ios" value="Y" type="checkbox"<#if item.attribute.side1HeavyCoverage?exists && item.attribute.side1HeavyCoverage == "true"> checked</#if> />
                                                            <label for="${orderItemSeq}side1HeavyCoverage" class="tgl-btn"></label>
                                                        </div>
                                                        <div class="col-lg-3">
                                                            Foil:
                                                            <input id="${orderItemSeq}side1Foil" name="side1Foil" class="tgl tgl-ios" type="checkbox"<#if item.attribute.side1Foil?exists && item.attribute.side1Foil == "true"> checked</#if> />
                                                            <label for="${orderItemSeq}side1Foil" class="tgl-btn"></label>
                                                        </div>
                                                        <div class="col-lg-3">
                                                            Embossed:
                                                            <input id="${orderItemSeq}side1Emboss" name="side1Emboss" class="tgl tgl-ios" type="checkbox"<#if item.attribute.side1Emboss?exists && item.attribute.side1Emboss == "true"> checked</#if> />
                                                            <label for="${orderItemSeq}side1Emboss" class="tgl-btn"></label>
                                                        </div>
                                                        <div class="col-lg-3">
                                                            Debossed:
                                                            <input id="${orderItemSeq}side1Deboss" name="side1Deboss" class="tgl tgl-ios" type="checkbox"<#if item.attribute.side1Deboss?exists && item.attribute.side1Deboss == "true"> checked</#if> />
                                                            <label for="${orderItemSeq}side1Deboss" class="tgl-btn"></label>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <div>
                                                                Colors:
                                                                <select name="side1Colors" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.side1Colors?if_exists}">
                                                                    <option value="">None</option>
																	<#list sortedProductAttributes["side1Colors"] as productAttribute>
                                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.side1Colors?exists && item.attribute.side1Colors == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
																	</#list>
                                                                </select>
                                                            </div>
                                                            <div>
                                                                Ink / Color Names:
                                                                <input name="side1InkColor1" value="${item.attribute.side1InkColor1?if_exists}" type="text" class="form-control form-control-sm has-dropdown-options" data-defaultvalue="${item.attribute.side1InkColor1?if_exists}" />
                                                                <ul class="dropdown-menu dropdown-options">
																	<#if sortedProductAttributes["side1InkColor1"]?has_content>
																		<#list sortedProductAttributes["side1InkColor1"] as productAttribute>
                                                                            <li>${productAttribute["attrValue"]}</li>
																		</#list>
																	</#if>
                                                                </ul>
                                                                <input name="side1InkColor2" value="${item.attribute.side1InkColor2?if_exists}" type="text" class="form-control form-control-sm has-dropdown-options" data-defaultvalue="${item.attribute.side1InkColor2?if_exists}" />
                                                                <ul class="dropdown-menu dropdown-options">
																	<#if sortedProductAttributes["side1InkColor2"]?has_content>
																		<#list sortedProductAttributes["side1InkColor2"] as productAttribute>
                                                                            <li>${productAttribute["attrValue"]}</li>
																		</#list>
																	</#if>
                                                                </ul>
                                                                <input name="side1InkColor3" value="${item.attribute.side1InkColor3?if_exists}" type="text" class="form-control form-control-sm has-dropdown-options" data-defaultvalue="${item.attribute.side1InkColor3?if_exists}" />
                                                                <ul class="dropdown-menu dropdown-options">
																	<#if sortedProductAttributes["side1InkColor3"]?has_content>
																		<#list sortedProductAttributes["side1InkColor3"] as productAttribute>
                                                                            <li>${productAttribute["attrValue"]}</li>
																		</#list>
																	</#if>
                                                                </ul>
                                                                <input name="side1InkColor4" value="${item.attribute.side1InkColor4?if_exists}" type="text" class="form-control form-control-sm has-dropdown-options" data-defaultvalue="${item.attribute.side1InkColor4?if_exists}" />
                                                                <ul class="dropdown-menu dropdown-options">
																	<#if sortedProductAttributes["side1InkColor4"]?has_content>
																		<#list sortedProductAttributes["side1InkColor4"] as productAttribute>
                                                                            <li>${productAttribute["attrValue"]}</li>
																		</#list>
																	</#if>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-6">
															Coating:
															<select name="side1Coating" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.side1Coating?if_exists}">
																<option value="">None</option>
																<#list sortedProductAttributes["side1Coating"] as productAttribute>
																	<option value="${productAttribute["attrValue"]}"<#if item.attribute.side1Coating?exists && item.attribute.side1Coating == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
																</#list>
															</select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
								<div class="col-lg-6">
									<div class="widget fixed addresses m-b-2">
										<div class="widget-title">
											<h4>Side 2</h4>
										</div>
										<div class="widget-body">
											<div class="row">
												<div class="col-md-12">
												<#--
												<div class="m-b-1">
													<img src="http://via.placeholder.com/900x500">
												</div>
												-->
													<div class="row">
														<div class="col-lg-3">
															Heavy Coverage:
															<input id="${orderItemSeq}side2HeavyCoverage" name="side2HeavyCoverage" class="tgl tgl-ios" value="Y" type="checkbox"<#if item.attribute.side2HeavyCoverage?exists && item.attribute.side2HeavyCoverage == "true"> checked</#if> />
															<label for="${orderItemSeq}side2HeavyCoverage" class="tgl-btn"></label>
														</div>
														<div class="col-lg-6">
															<div>
																Colors:
																<select name="side2Colors" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.side2Colors?if_exists}">
																	<option value="">None</option>
																	<#list sortedProductAttributes["side2Colors"] as productAttribute>
																		<option value="${productAttribute["attrValue"]}"<#if item.attribute.side2Colors?exists && item.attribute.side2Colors == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
																	</#list>
																</select>
															</div>
															<div>
																Ink / Color Names:
																<input name="side2InkColor1" value="${item.attribute.side2InkColor1?if_exists}" type="text" class="form-control form-control-sm has-dropdown-options" data-defaultvalue="${item.attribute.side2InkColor1?if_exists}" />
																<ul class="dropdown-menu dropdown-options">
																	<#if sortedProductAttributes["side2InkColor1"]?has_content>
																		<#list sortedProductAttributes["side2InkColor1"] as productAttribute>
																			<li>${productAttribute["attrValue"]}</li>
																		</#list>
																	</#if>
																</ul>
																<input name="side2InkColor2" value="${item.attribute.side2InkColor2?if_exists}" type="text" class="form-control form-control-sm has-dropdown-options" data-defaultvalue="${item.attribute.side2InkColor2?if_exists}" />
																<ul class="dropdown-menu dropdown-options">
																	<#if sortedProductAttributes["side2InkColor2"]?has_content>
																		<#list sortedProductAttributes["side2InkColor2"] as productAttribute>
																			<li>${productAttribute["attrValue"]}</li>
																		</#list>
																	</#if>
																</ul>
																<input name="side2InkColor3" value="${item.attribute.side2InkColor3?if_exists}" type="text" class="form-control form-control-sm has-dropdown-options" data-defaultvalue="${item.attribute.side2InkColor3?if_exists}" />
																<ul class="dropdown-menu dropdown-options">
																	<#if sortedProductAttributes["side2InkColor3"]?has_content>
																		<#list sortedProductAttributes["side2InkColor3"] as productAttribute>
																			<li>${productAttribute["attrValue"]}</li>
																		</#list>
																	</#if>
																</ul>
																<input name="side2InkColor4" value="${item.attribute.side2InkColor4?if_exists}" type="text" class="form-control form-control-sm has-dropdown-options" data-defaultvalue="${item.attribute.side2InkColor4?if_exists}" />
																<ul class="dropdown-menu dropdown-options">
																	<#if sortedProductAttributes["side2InkColor4"]?has_content>
																		<#list sortedProductAttributes["side2InkColor4"] as productAttribute>
																			<li>${productAttribute["attrValue"]}</li>
																		</#list>
																	</#if>
																</ul>
															</div>
														</div>
														<div class="col-lg-6">
															Coating:
															<select name="side2Coating" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.side2Coating?if_exists}">
																<option value="">None</option>
																<#list sortedProductAttributes["side2Coating"] as productAttribute>
																	<option value="${productAttribute["attrValue"]}"<#if item.attribute.side2Coating?exists && item.attribute.side2Coating == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
																</#list>
															</select>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
                           		</div>
                            </div>
                            <div class="row customizing-options">
                                <div class="col-sm-12 col-md-6">
                                    <div class="widget fixed addresses">
                                        <div class="widget-title">
                                            <h4>Foil Images</h4>
                                        </div>
										<#assign totalRows = 20 />
										<#list 1..totalRows as i>
                                            <div class="jqs-foilDataRows jqs-dataRows widget-body<#if i gt 1> hidden</#if>">
												<div class="row">
													<div class="col-lg-12 m-b-1">
														Images:
														<select name="foilImages${i}" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute["foilImages" + i]?if_exists}">
															<option value="">None</option>
															<#list sortedProductAttributes["foilImages" + i] as productAttribute>
																<option value="${productAttribute["attrValue"]}"<#if item.attribute["foilImages" + i]?exists && item.attribute["foilImages" + i] == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
															</#list>
														</select>
                                                        <input name="foilImagesOther${i}" value="${item.attribute["foilImagesOther" + i]?if_exists}" type="text" class="form-control form-control-sm<#if !item.attribute["foilImagesOther" + i]?has_content> hidden</#if>" data-defaultvalue="${item.attribute["foilImagesOther" + i]?if_exists}" />
													</div>
                                                </div>
												<div class="row">
													<div class="col-lg-6">
														Foil Color:
														<select name="foilColor${i}" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute["foilColor" + i]?if_exists}">
															<option value="">None</option>
															<#list sortedProductAttributes["foilColor" + i] as productAttribute>
																<option value="${productAttribute["attrValue"]}"<#if item.attribute["foilColor" + i]?exists && item.attribute["foilColor" + i] == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
															</#list>
														</select>
                                                        <input name="foilColorOther${i}" value="${item.attribute["foilColorOther" + i]?if_exists}" type="text" class="form-control form-control-sm<#if !item.attribute["foilColorOther" + i]?has_content> hidden</#if>" data-defaultvalue="${item.attribute["foilColorOther" + i]?if_exists}" />
													</div>
													<div class="col-lg-6">
														Size:
														<select name="foilSize${i}" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute["foilSize" + i]?if_exists}">
															<option value="">None</option>
															<#list sortedProductAttributes["foilSize" + i] as productAttribute>
																<option value="${productAttribute["attrValue"]}"<#if item.attribute["foilSize" + i]?exists && item.attribute["foilSize" + i] == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
															</#list>
														</select>
                                                        <input name="foilSizeOther${i}" value="${item.attribute["foilSizeOther" + i]?if_exists}" type="text" class="form-control form-control-sm<#if !item.attribute["foilSizeOther" + i]?has_content> hidden</#if>" data-defaultvalue="${item.attribute["foilSizeOther" + i]?if_exists}" />
													</div>
                                                </div>
                                            </div>
										</#list>
                                        <div class="widget-body row">
                                            <div class="col-lg-12">
                                                <button class="jqs-addFoilRow btn btn-primary btn-sm" style="display: inline-block;">Add Row</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-md-6">
                                    <div class="widget fixed addresses">
                                        <div class="widget-title">
                                            <h4>Emboss Images</h4>
                                        </div>
										<#assign totalRows = 20 />
										<#list 1..totalRows as i>
                                            <div class="jqs-embossDataRows jqs-dataRows widget-body<#if i gt 1> hidden</#if>">
												<div class="row">
													<div class="col-lg-12 m-b-1">
														Images:
														<select name="embossImages${i}" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute["embossImages" + i]?if_exists}">
															<option value="">None</option>
															<#list sortedProductAttributes["embossImages" + i] as productAttribute>
																<option value="${productAttribute["attrValue"]}"<#if item.attribute["embossImages" + i]?exists && item.attribute["embossImages" + i] == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
															</#list>
														</select>
                                                        <input name="embossImagesOther${i}" value="${item.attribute["embossImagesOther" + i]?if_exists}" type="text" class="form-control form-control-sm<#if !item.attribute["embossImagesOther" + i]?has_content> hidden</#if>" data-defaultvalue="${item.attribute["embossImagesOther" + i]?if_exists}" />
													</div>
												</div>
                                            	<div class="row">
													<div class="col-lg-6">
														Emboss Level:
														<select name="embossLevel${i}" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute["embossLevel" + i]?if_exists}">
															<option value="">None</option>
															<#list sortedProductAttributes["embossLevel" + i] as productAttribute>
																<option value="${productAttribute["attrValue"]}"<#if item.attribute["embossLevel" + i]?exists && item.attribute["embossLevel" + i] == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
															</#list>
														</select>
                                                        <input name="embossLevelOther${i}" value="${item.attribute["embossLevelOther" + i]?if_exists}" type="text" class="form-control form-control-sm<#if !item.attribute["embossLevelOther" + i]?has_content> hidden</#if>" data-defaultvalue="${item.attribute["embossLevelOther" + i]?if_exists}" />
													</div>
													<div class="col-lg-6">
														Size:
														<select name="embossSize${i}" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute["embossSize" + i]?if_exists}">
															<option value="">None</option>
															<#list sortedProductAttributes["embossSize" + i] as productAttribute>
																<option value="${productAttribute["attrValue"]}"<#if item.attribute["embossSize" + i]?exists && item.attribute["embossSize" + i] == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
															</#list>
														</select>
                                                        <input name="embossSizeOther${i}" value="${item.attribute["embossSizeOther" + i]?if_exists}" type="text" class="form-control form-control-sm<#if !item.attribute["embossSizeOther" + i]?has_content> hidden</#if>" data-defaultvalue="${item.attribute["embossSizeOther" + i]?if_exists}" />
													</div>
												</div>
                                            </div>
										</#list>
                                        <div class="widget-body row">
                                            <div class="col-lg-12">
                                                <button class="jqs-addEmbossRow btn btn-primary btn-sm" style="display: inline-block;">Add Row</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <div class="widget fixed addresses">
                                        <div class="widget-title">
                                            <h4>Business Card Slits</h4>
                                        </div>
                                        <div class="widget-body row">
                                            <div class="col-xs-6">
                                                Right Pocket:
                                                <select name="rightPocket" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.rightPocket?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["rightPocket"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.rightPocket?exists && item.attribute.rightPocket == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                                <input name="rightPocketOther" value="${item.attribute.rightPocketOther?if_exists}" type="text" class="form-control form-control-sm<#if !item.attribute.rightPocketOther?has_content> hidden</#if>" data-defaultvalue="${item.attribute.rightPocketOther?if_exists}" />
                                            </div>
                                            <div class="col-xs-6">
                                                Right Pocket Position:
                                                <select name="rightPocketPosition" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.rightPocketPosition?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["rightPocketPosition"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.rightPocketPosition?exists && item.attribute.rightPocketPosition == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
										</div>
										<div class="widget-body row">
                                            <div class="col-xs-6">
                                                Center Pocket:
                                                <select name="centerPocket" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.centerPocket?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["centerPocket"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.centerPocket?exists && item.attribute.centerPocket == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                                <input name="centerPocketOther" value="${item.attribute.centerPocketOther?if_exists}" type="text" class="form-control form-control-sm<#if !item.attribute.centerPocketOther?has_content> hidden</#if>" data-defaultvalue="${item.attribute.centerPocketOther?if_exists}" />
                                            </div>
                                            <div class="col-xs-6">
                                                Center Pocket Position:
                                                <select name="centerPocketPosition" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.centerPocketPosition?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["centerPocketPosition"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.centerPocketPosition?exists && item.attribute.centerPocketPosition == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="widget-body row">
											<div class="col-xs-6">
												Left Pocket:
												<select name="leftPocket" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.leftPocket?if_exists}">
													<option value="">None</option>
													<#list sortedProductAttributes["leftPocket"] as productAttribute>
														<option value="${productAttribute["attrValue"]}"<#if item.attribute.leftPocket?exists && item.attribute.leftPocket == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
												</select>
												<input name="leftPocketOther" value="${item.attribute.leftPocketOther?if_exists}" type="text" class="form-control form-control-sm<#if !item.attribute.leftPocketOther?has_content> hidden</#if>" data-defaultvalue="${item.attribute.leftPocketOther?if_exists}" />
											</div>
											<div class="col-xs-6">
												Left Pocket Position:
												<select name="leftPocketPosition" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.leftPocketPosition?if_exists}">
													<option value="">None</option>
													<#list sortedProductAttributes["leftPocketPosition"] as productAttribute>
														<option value="${productAttribute["attrValue"]}"<#if item.attribute.leftPocketPosition?exists && item.attribute.leftPocketPosition == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
												</select>
											</div>
                                            <script>
                                                $('[name="rightPocket"], [name="leftPocket"], [name="centerPocket"], [name*="foilImages"], [name*="foilSize"], [name*="foilColor"], [name*="embossImages"], [name*="embossSize"], [name*="embossLevel"]').on('change', function() {
                                                    var counter = $(this).attr('name').match(/\d+$/);

                                                    if ($(this).val() == 'Other') {
                                                        $('[name="' + $(this).attr('name').replace(/\d+$/, '') + 'Other' + (counter != null ? counter[0] : '') + '"]').removeClass('hidden');
                                                    } else {
                                                        $('[name="' + $(this).attr('name').replace(/\d+$/, '') + 'Other' + (counter != null ? counter[0] : '') + '"]').removeClass('hidden').addClass('hidden');
                                                    }
                                                });
                                            </script>
										</div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-6">
									<div class="widget fixed addresses">
										<div class="widget-title">
											<h4>Pockets</h4>
										</div>
										<div class="widget-body row">
                                            <div class="col-lg-6">
                                                Right Pocket Style:
                                                <select name="rightPocketStyle" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.rightPocketStyle?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["rightPocketStyle"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.rightPocketStyle?exists && item.attribute.rightPocketStyle == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
                                            <div class="col-lg-6">
                                                Right Pocket Capacity:
                                                <select name="rightPocketCapacity" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.rightPocketCapacity?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["rightPocketCapacity"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.rightPocketCapacity?exists && item.attribute.rightPocketCapacity == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
										</div>
										<div class="widget-body row">
                                            <div class="col-lg-6">
                                                Center Pocket Style:
                                                <select name="centerPocketStyle" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.centerPocketStyle?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["centerPocketStyle"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.centerPocketStyle?exists && item.attribute.centerPocketStyle == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
                                            <div class="col-lg-6">
                                                Center Pocket Capacity:
                                                <select name="centerPocketCapacity" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.centerPocketCapacity?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["centerPocketCapacity"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.centerPocketCapacity?exists && item.attribute.centerPocketCapacity == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="widget-body row">
											<div class="col-lg-6">
												Left Pocket Style:
												<select name="leftPocketStyle" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.leftPocketStyle?if_exists}">
													<option value="">None</option>
													<#list sortedProductAttributes["leftPocketStyle"] as productAttribute>
														<option value="${productAttribute["attrValue"]}"<#if item.attribute.leftPocketStyle?exists && item.attribute.leftPocketStyle == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
												</select>
											</div>
                                            <div class="col-lg-6">
                                                Left Pocket Capacity:
                                                <select name="leftPocketCapacity" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.leftPocketCapacity?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["leftPocketCapacity"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.leftPocketCapacity?exists && item.attribute.leftPocketCapacity == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
										</div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
								<div class="col-md-3">
									<div class="widget fixed addresses">
										<div class="widget-title">
											<h4>Reinforced Edges</h4>
										</div>
										<div class="widget-body row">
											<div class="col-lg-12">
												Reinforced Edges:
												<select name="reinforcedEdgeType" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.reinforcedEdgeType?if_exists}">
													<option value="">None</option>
													<#list sortedProductAttributes["reinforcedEdgeType"] as productAttribute>
														<option value="${productAttribute["attrValue"]}"<#if item.attribute.reinforcedEdgeType?exists && item.attribute.reinforcedEdgeType == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="widget fixed addresses">
										<div class="widget-title">
											<h4>Backbone</h4>
										</div>
										<div class="widget-body row">
											<div class="col-lg-12">
												Backbone:
												<select name="backboneSize" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.backboneSize?if_exists}">
													<option value="">None</option>
													<#list sortedProductAttributes["backboneSize"] as productAttribute>
														<option value="${productAttribute["attrValue"]}"<#if item.attribute.backboneSize?exists && item.attribute.backboneSize == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6">
                                    <div class="widget fixed addresses">
                                        <div class="widget-title">
                                            <h4>Closure</h4>
                                        </div>
                                        <div class="widget-body row">
                                            <div class="col-lg-6">
                                                Type:
                                                <select name="closureType" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.closureType?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["closureType"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.closureType?exists && item.attribute.closureType == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
                                            <div class="col-lg-6">
                                                Color:
                                                <select name="closureColor" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.closureColor?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["closureColor"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.closureColor?exists && item.attribute.closureColor == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="widget fixed addresses">
										<div class="widget-title">
											<h4>Gusset</h4>
										</div>
										<div class="widget-body row">
											<div class="col-lg-12">
												Gusset:
												<select name="gussetType" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.gussetType?if_exists}">
													<option value="">None</option>
													<#list sortedProductAttributes["gussetType"] as productAttribute>
														<option value="${productAttribute["attrValue"]}"<#if item.attribute.gussetType?exists && item.attribute.gussetType == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="widget fixed addresses">
										<div class="widget-title">
											<h4>File Tab</h4>
										</div>
										<div class="widget-body row">
											<div class="col-lg-12">
												File Tab:
												<select name="fileTabType" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.fileTabType?if_exists}">
													<option value="">None</option>
													<#list sortedProductAttributes["fileTabType"] as productAttribute>
														<option value="${productAttribute["attrValue"]}"<#if item.attribute.fileTabType?exists && item.attribute.fileTabType == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
												</select>
											</div>
										</div>
									</div>
								</div>
                                <div class="col-lg-3">
                                    <div class="widget fixed addresses">
                                        <div class="widget-title">
                                            <h4>Spine Attachment</h4>
                                        </div>
                                        <div class="widget-body row">
                                            <div class="col-lg-12">
                                                Color:
                                                <select name="spineColor" class="dropdown-select form-control form-control-sm" data-defaultvalue="${item.attribute.spineColor?if_exists}">
                                                    <option value="">None</option>
													<#list sortedProductAttributes["spineColor"] as productAttribute>
                                                        <option value="${productAttribute["attrValue"]}"<#if item.attribute.spineColor?exists && item.attribute.spineColor == productAttribute["attrValue"]> selected</#if>>${productAttribute["attrValue"]}</option>
													</#list>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-3">
                                    <div class="widget">
                                        <div class="widget-title">
                                            <h4>Quote Comments</h4>
                                        </div>
                                        <div class="widget-body">
                                            <textarea class="conversation-input  form-control" name="conversation-input-0" placeholder="" readonly>${item.quoteComments?default("")}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="widget">
                                        <div class="widget-title">
                                            <h4>Comments for PrePress</h4>
                                        </div>
                                        <div class="widget-body">
                                            <textarea class="conversation-input  form-control" name="conversation-input-1" placeholder="Comments to PrePress">${item.artwork.itemPrepressComments?if_exists}</textarea>
                                            <a href="#" class="btn btn-primary btn-sm conversation-send prePressCommentSave">Update</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="widget">
                                        <div class="widget-title">
                                            <h4>Comments to Customer</h4>
                                        </div>
                                        <div class="widget-body">
                                            <textarea id="jqs-canned_response_input-${orderItemSeq}" class="conversation-input  form-control" name="conversation-input-2" placeholder="Comments to Customer">${item.artwork.itemCustomerComments?if_exists}</textarea>
                                            <div id="ctc-button-row-${orderItemSeq}" class="row no-margin">
                                                <div class="col-xs-12">
                                                    <div class="pull-xs-left">
                                                        <a href="#" class="btn btn-primary btn-sm conversation-send customerCommentSave" data-response-id="${orderItemSeq}">Update</a>
                                                    </div>
                                                    <div class="pull-xs-right" style="margin-top: 10px;" data-dropdown-target="dropdown-canned_responses-${orderItemSeq}" data-dropdown-options="click ignore-reverse-dropdown" data-dropdown-alternate-parent="ctc-button-row-${orderItemSeq}">
                                                        <div class="btn btn-primary btn-sm">Add Canned Response</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div style="width: 600px;" id="dropdown-canned_responses-${orderItemSeq}" class="drop-down canned_responses">
                                                <div>
                                                    <div>
                                                        <div>
                                                            Need New Art - Low Res
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">The artwork you uploaded is low resolution and probably won't print well. If you can, please upload a color separated, high resolution file. The file it was originally created in works best. (eps or pdf)</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Art is a bit low resolution and could print better quality (?) with a higher resolution file. The file it was originally created in works best. (eps or pdf)</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">We had to adjust your artwork so it fit inside our template guidelines correctly. Please read over carefully and confirm all is correct.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">By approving this proof, you are confirming all content, spelling and placement are correct. All will be printed as seen in proof.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">We matched the color in your file as closely as possible. Please refer to a pantone solid uncoated swatch for an accurate representation of the final printed color. Colors viewed on screen are not accurate as to what will print. If color is critical, please supply a pantone color or a printed sample to be color matched.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Ink Colors
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Your order was placed for X color printing but your artwork has X colors. We have converted your artwork to X color printing. This will affect your price. The new cost for printing X ink colors is $XXX.XX before shipping. By approving this proof you are approving this new cost.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note, ink colors may darken when printed on this color stock.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Converting to 1 Color
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Your order was placed for one color, so your art has been converted to Grayscale. If you would like two color print, please reject the proof and specify in the rejection comments. We will adjust your order and send a new proof with new pricing. By approving the proof, you are approving all art as seen in the proof.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            White Ink
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that all our proofs are set up on a white background and the artwork you see in black will print white.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note, we will need an extra day of production for printing white ink on this large of a quantity.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Digital Orders
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that the digital press does not print pantone ink so colors will be approximate.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please be aware that this item is printing digitally while the other item(s) is printing offset so colors may not be an exact match.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please be aware that sometimes when printing exclusive stock on digital quantities, discoloration or fading may occur. This is due to the texture of the paper and the pressure and heat from the press.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">On digital orders we need .25 border from the edge so we cannot print bleeds. Your artwork was adjusted. If you do not like the changes, you can either reject and upload new artwork OR you can up your quantity to 500 and print Offset which does allow bleeds. Your new cost for 500 would be $XX. With your proof approval, you are approving this new cost.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Heavy Ink Fees
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Your artwork is considered to be heavy coverage. This will incur a heavy ink coverage fee of $XX. With your proof approval you are approving this additional fee.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Offset
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that printing large dark areas of ink increases the risk of offset which is a transfer of wet ink onto the back of adjacent envelopes from it not being completely dry.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Seam Lines
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Because our envelopes are constructed prior to printing, seam lines and flap impressions may be visible in your art when printing large coverage.** Any type going over seams may distort slightly</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Full Bleed Warning
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">It is normal for the artwork on the finished envelopes to vary slightly from one piece to another because of movement during the run and could vary up to 1/8". As a result of the intensive bindery processes, die cutting, scoring, folding and gluing (ie. converting), the color wrap &amp; over-bleed may not be even on all sides.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Address
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">There was no address in your file. If you want to add one, please reject the proof and provide us with an address.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Check Payable
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">There is no information stating who to make checks payable to. If you would like to add this information please reject the proof and advise in the comments.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Clasp Warning
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that it is not unusual for there to be a clasp impression on the front of a clasp envelope. The marks are caused by the envelopes rubbing on the metal clasp of the adjacent envelope. The marks are not caused by the manufacturing process but rather during shipment. This is just the nature of the product and can not be avoided and is therefore, not considered a defect.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            PDF Screen Effect
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">What you are describing is a screen effect experienced when viewing a PDF file in Adobe Acrobat and has no effect on the final print quality. Zooming in closely on an object or printing the file will give you a better representation of the quality of the final printed product.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Mini
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note that these envelopes are not acceptable for mailing. If you need a mailing envelope you would need to change to an A1 size (3.625x5.125), please reject the proof or call if you need it changed.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Layout for Mailing
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">*You may want to check the layout of your envelope with the post office to make sure it's ok for mailing since you have art in the stamp area.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Return/Reply Confusion
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">By approving this proof you are approving you want the address in the middle of the envelope in the REPLY address position.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">By approving this proof you are approving you want the address at the top left corner of the envelope in the RETURN address position.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Tyvek Warning
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please note, Tyvek stock has a texture that can easily slip on press. There is a chance the artwork may be shifted during the printing process.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Bleed Line
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please be aware that while our presses are capable of bleeding on this size envelope, a thin line of ink will be transferred onto the back. For this to be avoided, the bleed should be cropped back to have at least an 1/8th of an inch border from the envelope edge.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Remittance Confusion
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please make sure it is a remittance envelope you need and not a regular #9 envelope. A remittance envelope has the large flap (as shown) and you can print on 4 panels, the front and back top and bottom. A #9 regular is just slightly smaller than a regular business #10 envelope. Please advise.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Please confirm you want these printed on the #6.25 remittance with the longer flap and not the #6.25 regular envelopes with the shorter flap. Both are the same size closed. Please reject proof and specify in comments if you would like to change the stock.</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div>
                                                            Paperboard Mailers
                                                        </div>
                                                        <div>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Art should be simple line art with no tight registration.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Art should not contain gradients or screens.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Art should not contain large ink coverage areas or bleeds.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">Minimum 1/2 inch border should be left on envelope edges.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">There is an additional fee for printing on both sides of the envelope. With your proof approval, you are approving this new cost/additional fee.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">We do not print metallic inks.</p>
                                                            <p class="jqs-canned_response response_text" data-response-id="${orderItemSeq}">There is a 15 day production schedule for paperboard mailers.</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="widget">
                                        <div class="widget-title">
                                            <h4>Comments From Customer</h4>
                                        </div>
                                        <div class="widget-body">
                                            <div class="customerComments">
												<#if item.attribute?has_content && item.attribute.pricingRequest?has_content>
                                                    <pre id="priceJSON">${item.attribute.pricingRequest?if_exists}</pre>
												</#if>
												<#if orderItem.comments?has_content>
                                                    <pre><span>${orderItem.createdStamp?string.short} - </span>${orderItem.comments?if_exists}</pre>
												</#if>
												<#if item.comments?has_content>
													<#list item.comments as comment>
                                                        <pre><span>${comment.createdStamp?string.short} - </span>${comment.message?if_exists}</pre>
													</#list>
												</#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
						<#--
                            </div>
                        </div>
                        -->
                        </div>
					</#if>
				</div>
			</div>
		</div>
	</#list>
	<div class="row totals m-0">
		<div class="col-xs-12 col-md-6 col-lg-7"></div>
		<div class="col-xs-12 col-md-6 col-lg-5 right">
			<div class="row sub-totals m-0">

			</div>
		</div>
	</div>
</div>

<script>
	var realShipMethod = '${orderReadHelper.getShippingMethod("00001", false)}';
	var genShipMethod = '${shipping}';
	var partyId = '${person.partyId}';
</script>

<script src="/html/js/util/spinner.js"></script>
<script src="/html/js/addons/barcode/jquery-barcode.min.js"></script>
<script src="/html/js/addons/ui/jquery.ui.widget.js"></script>
<script src="/html/js/addons/moment/moment.min.js"></script>
<script src="/html/js/addons/fileupload/jquery.iframe-transport.js"></script>
<script src="/html/js/addons/fileupload/jquery.fileupload.js"></script>
<script src="/html/js/util/jsPDF.js"></script>
<script src="/html/js/util/jsPDFFonts.js"></script>
<script src="/html/js/util/fabricJS.js"></script>
<script type="text/javascript" src="/html/js/util/magikMike/texel.js"></script>
<script src="/html/js/admin/viewOrder.js?ts=13"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/addons/jquery.minicolors.min.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/lib/envelopes-widget-util.js</@ofbizContentUrl>?ts=3"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.grid.js</@ofbizContentUrl>?ts=2"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.grid.proxy.js</@ofbizContentUrl>?ts=3"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.variabledata.js</@ofbizContentUrl>?ts=3"></script>
<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>

<script>
	braintree.client.create({
		authorization: '${Static["com.bigname.payments.braintree.BraintreeHelper"].getClientToken("envelopes")?if_exists?replace("&#x3d;", "=")}'
	}, function(err, clientInstance) {
		if (err) {
			console.error(err);
			return;
		}

		createHostedFields(clientInstance);
	});

	function createHostedFields(clientInstance) {
		braintree.hostedFields.create({
			client: clientInstance,
			styles: {
				'input': {
					'font-size': '.875rem',
					'padding': '8px',
					'font-family': "'Lato', sans-serif !important", 
					'font-weight': 'bold',
					'color': '#1a345f'
				},
				'.valid': {
					'color': '#8bdda8'
				}
			},
			fields: {
				number: {
					selector: '#card-number',
					placeholder: 'Card Number'
				},
				cvv: {
					selector: '#cvv',
					placeholder: 'Security Code'
				},
				expirationDate: {
					selector: '#expiration-date',
					placeholder: 'Exp. Date (MM/YYYY)'
				},
				postalCode: {
					selector: '#postal-code',
					placeholder: 'Postal Code'
				}
			}
		}, function (err, hostedFieldsInstance) {
			var tokenize = function (event) {
				event.preventDefault();
				
				hostedFieldsInstance.tokenize(function (err, payload) {
					if (err) {
						alert(err.message);
					} else {
						$.ajax({
							type: 'POST',
							url: '/admin/control/preAuth',
							dataType: 'json',
							data: {
								'orderId': $('.order-id').html(),
								'paymentMethodTypeId': "CREDIT_CARD",
								'nonceToken': payload.nonce,
								'webSiteId': "${Static["com.envelopes.util.EnvUtil"].getWebsiteId(order.salesChannelEnumId)}"
							}
						}).done(function(jsonResponse) {
							alert(jsonResponse.responseMessage);
						})
						
						$("#modal-braintree_payment").modal('hide');
					}
				});
			};
			
			$("#modal-braintree_payment").on("getToken", tokenize);
		});
	}
</script>