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

<#assign emptyCart = request.getAttribute("emptyCart")?c />
<#if emptyCart == 'true'>
	<script>
	   window.location.href = '<@ofbizUrl>/cart</@ofbizUrl>';
	</script>
<#else>
<script>
    var freeOrder = '${freeOrder?string("true", "false")}' == 'true';
</script>
<#assign isSignedInUser = request.getAttribute("signedInUser")?c />
<link href="<@ofbizContentUrl>/html/css/checkout/order-summary-redesign.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/checkout/checkout-redesign.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />

<#if isSignedInUser == 'true'>
<script>
	$.checkStatus();
	<#if request.getSession().getAttribute("userLogin")?exists>
		ga('set', '&uid', '${request.getSession().getAttribute("userLogin").userLoginId?default('')?replace('&#x40;','@')}'); // Set the user ID using signed-in user_id.
		GoogleAnalytics.trackEvent('Log In', 'Logged In', 'Success');
	</#if>
</script>
</#if>

<div class="content container checkout">
	<div id="checkout-message" data-alert class="alert-box success radius hidden">${checkoutMessage?default('')}</div>
	<div id="checkout-error" data-alert class="alert-box alert radius" style="display:none">${checkoutError?default('')}</div>
	<script>
		if($('#checkout-message').text() != '') {
			$('#checkout-message').removeClass('hidden').show();
		}

		if($('#checkout-error').text() != '') {
			$('#checkout-error').removeClass('hidden').show();
		}
	</script>
	<#if isSignedInUser == 'false'>
		<div class="padding-xs log-in optional generic-container jqs-tracking" data-tracking-seq="2" data-tracking-name="Login">
			<div class="row collapse">
				<div class="small-10 medium-10 large-10 columns no-padding">
					<h3>Already Have an Account? <span class="orangeText">Click here to Login!</span></h3>
				</div>
				<div class="small-2 medium-2 large-2 columns text-right">
					<i class="fa fa-caret-down"></i>
				</div>
			</div>
			<div class="optional-content">
				<form id="loginForm" name="loginForm" action="<@ofbizUrl>/login-action</@ofbizUrl>" method="post">
					<input type="hidden" id="coToken" name="token" value="" />
					<div class="row margin-top-xs margin-left-xxs">
						<div class="small-12 medium-3 large-4 columns">
							<input name="USERNAME" type="text" value="${userName?default('')}" placeholder="E-mail Address" />
						</div>
						<div class="small-12 medium-3 large-4 columns">
							<input name="PASSWORD" type="password" value="" placeholder="Password" />
                            <div style="position: absolute; top:50px; right:20px;"><a href="javascript:$.showForgotPassword()" data-reveal-id="secure-layer">Forgot Password?</a></div>
						</div>
						<div class="small-12 medium-3 large-2 columns sign_in_button_container">
							<input name="responseName" type="hidden" value="checkout"/>
                            <input type="hidden" name="saveResponse" value="true"/>
							<div class="button-regular button-cta round-btn" onclick="$('#loginForm').submit();">Sign In</div>
						</div>
						<div class="small-12 medium-3 large-2 columns">
							<a class="checkoutJE janrainEngage">Or login using <img src="<@ofbizContentUrl>/html/img/btn/social_login_button.png</@ofbizContentUrl>" alt="Social Login" /></a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</#if>

	<form id="checkoutForm" name="checkoutForm" data-bigNameValidateForm="checkoutForm" method="post" action="<@ofbizUrl>/placeOrder</@ofbizUrl>">
		<div class="jqs-opt4">
			<div>
				<div class="margin-top-xs double-container">

					<div>
						<div class="padding-xs address-input generic-container jqs-tracking" data-tracking-seq="3" data-tracking-name="Email Address">
							<div>
								<div class="row margin-bottom-xs">
									<h3>1. Enter Your Email Address for Order Confirmation</h3>
								</div>
								<div class="row">
									<div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="emailAddress">
										<input type="hidden" name="oldEmailAddress" value="<#if request.getSession().getAttribute("userLogin")?exists>${request.getSession().getAttribute("userLogin").userLoginId?default('')}</#if>" />
										<input class="jqs-email" name="emailAddress" data-bigNameValidate="emailAddress" data-bigNameValidateType="required email" data-bignamevalidateerrortitle="Email Address" type="email" value="<#if parameterMap.emailAddress?exists>${parameterMap.emailAddress?default('')}<#elseif request.getSession().getAttribute("userLogin")?exists>${request.getSession().getAttribute("userLogin").userLoginId?default('')}</#if>" placeholder="Email Address" />
									</div>
								</div>
							</div>
						</div>
						<div class="padding-xs margin-top-xs address-input generic-container coShippingAddress jqs-tracking" data-tracking-seq="4" data-tracking-name="Shipping Address">
							<div class="jqs-shipping">
								<div class="row margin-bottom-xs">
									<h3>2. Enter Your Shipping Address</h3>
								</div>
								<div class="row">
									<#if (isSignedInUser == 'true' && shippingAddresses?has_content && shippingAddresses?size > 0)>
									<div class="small-12 medium-6 large-6 columns">
										<div class="envelope-select">
											<select id="saved-shipping-address" name="saved_shipping_address" class="jqs-filter-size">
												<option value="">Select a saved address</option>
												<#list shippingAddresses.entrySet() as entry>
													<#assign isSelected = (entry.key ==  parameterMap.saved_shipping_address?default(''))?string('selected ', '')/>
													<option ${isSelected} value="${entry.key}" data-address="${entry.value.jsonData}">${entry.value.firstName} ${entry.value.lastName} - ${entry.value.address1}</option>
												</#list>
											</select>
										</div>
									</div>
									</#if>
									<div class="small-12 <#if (isSignedInUser == 'true' && shippingAddresses?has_content && shippingAddresses?size > 0)>medium-6 large-6<#else>medium-12 large-12</#if> columns">
										<div class="row ship-to">
											<div class="small-6 medium-6 large-6 columns">
												<div class="padding-left-xxs horizontal-input">
													<div>
														<input id="ship_to_business" class="jqs-address-type" name="ship_to" type="radio" value="BUSINESS_LOCATION" <#if parameterMap.ship_to?default('BUSINESS_LOCATION') == "BUSINESS_LOCATION">checked="checked"</#if> />
													</div>
													<div>
														<span class="margin-left-xxs">
															<label for="ship_to_business">Business</label>
														</span>
													</div>
												</div>
											</div>
											<div class="small-6 medium-6 large-6 columns">
												<div class="padding-left-xxs horizontal-input">
													<div>
														<input id="ship_to_residential" class="jqs-address-type" name="ship_to" type="radio" value="RESIDENTIAL_LOCATION" <#if parameterMap.ship_to?default('BUSINESS_LOCATION') == "RESIDENTIAL_LOCATION">checked="checked"</#if>/>
													</div>
													<div>
														<span class="margin-left-xxs">
															<label for="ship_to_residential">Residential</label>
														</span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="shipping_firstName">
										<input class="jqs-first-name" name="shipping_firstName" type="text" value="${parameterMap.shipping_firstName?default('')}" placeholder="First Name" data-bignamevalidate="shipping_firstName" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="First Name" />
									</div>
									<div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="shipping_lastName">
										<input class="jqs-last-name" name="shipping_lastName" type="text" value="${parameterMap.shipping_lastName?default('')}" placeholder="Last Name" data-bignamevalidate="shipping_lastName" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Last Name" />
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-12 large-12 columns">
										<input class="jqs-company-name" name="shipping_companyName" type="text" value="${parameterMap.shipping_companyName?default('')}" placeholder="Company (Optional)" />
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="shipping_address1">
										<input id="jqs-shipping-autocomplete" class="jqs-address1" name="shipping_address1" type="text" value="${parameterMap.shipping_address1?default('')}" placeholder="Address Line 1" data-bignamevalidate="shipping_address1" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Address" />
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-12 large-12 columns">
										<input class="jqs-address2" name="shipping_address2" type="text" value="${parameterMap.shipping_address2?default('')}" placeholder="Address Line 2 (Optional)" />
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="shipping_city">
										<input class="jqs-city" name="shipping_city" type="text" value="${parameterMap.shipping_city?default('')}" placeholder="City" data-bignamevalidate="shipping_city" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="City" />
									</div>
									<div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="shipping_stateProvinceGeoId">
										<label class="envelope-select" data-bigNameValidateParent="shipping_stateProvinceGeoId">
											<select class="jqs-state" name="shipping_stateProvinceGeoId" data-type="shipping" data-bignamevalidate="shipping_stateProvinceGeoId" data-bignamevalidatetype="required state" data-bignamevalidateerrortitle="State">
												<option value="">State/Province</option>
												${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
											</select>
										</label>
										<script>
											$('[name=shipping_stateProvinceGeoId]').val('${parameterMap.shipping_stateProvinceGeoId?default('')}');
										</script>
									</div>
									<div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="shipping_postalCode">
										<input id="shipping-postal-code" class="jqs-postal-code" name="shipping_postalCode" data-type="shipping" type="text" value="${parameterMap.shipping_postalCode?default('')}" placeholder="Zip" data-bignamevalidate="shipping_postalCode" data-bignamevalidatetype="required postalCode" data-bignamevalidateerrortitle="Zip" />
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="shipping_countryGeoId">
										<label class="envelope-select" data-bigNameValidateParent="shipping_countryGeoId">
											<select class="jqs-country jqs-shipping-country" name="shipping_countryGeoId" data-bignamevalidate="shipping_countryGeoId" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Country">
												<option value="">Country</option>
											${screens.render("component://envelopes/widget/CommonScreens.xml#countries")}
											</select>
										</label>
										<script>
											$('[name=shipping_countryGeoId]').val('${parameterMap.shipping_countryGeoId?default('USA')}');
										</script>
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="shipping_contactNumber">
										<input class="jqs-phone" name="shipping_contactNumber" type="text" value="${parameterMap.shipping_contactNumber?default('')}" placeholder="Phone" data-bignamevalidate="shipping_contactNumber" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Phone" />
									</div>
								</div>
								<div class="row margin-left-xxs">
									<div class="padding-left-xxs horizontal-input">
										<div>
											<input id="no_price_info" class="jqs-blind-ship" name="no_price_info" value="Y" type="checkbox" <#if parameterMap.no_price_info?default('') == "Y">checked="checked"</#if>/>
										</div>
										<div>
											<span class="margin-left-xxs"><label for="no_price_info">Blind shipment.</label></span>
										</div>
									</div>
								</div>
								<input type="hidden" name="shipping_countryCode" value="${parameterMap.shipping_countryCode?default('')}" />
								<input type="hidden" name="shipping_areaCode" value="${parameterMap.shipping_areaCode?default('')}" />
							</div>
						</div>
					</div>

					<#--Shop with Confidence Section-->
					<div class="shop-with-confidence section-container text-size-md jqs-opt2">
						<div class="padding-bottom-xxs caption h5b bns-center">
							<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/lock-icon?fmt=png-alpha&amp;wid=14&amp;ts=3</@ofbizScene7Url>" /></i><span class="bns-style1">Shop With Confidence</span>  
						</div>
						<ul class="no-margin">
							<li>
								<div class="padding-top-xs blue bns-style2"><span class="margin-right-xxs blue">Privacy Policy</span><div class="arrow-ui thin-right"></div></div>
								<span>We will never share or sell any of your personal information, including your e-mail address.</span>
							</li>
							<li>
								<a href="http://support.envelopes.com/customer/portal/articles/828485-shipping-policy" title="Shipping Policy">
									<div class="padding-top-xs blue bns-style2"><span class="margin-right-xxs blue">Shipping Policy</span><div class="arrow-ui thin-right"></div></div>
								</a>
								<span>95% of plain orders ship the same or next business day.</span>
							</li>
							<li>
								<div class="padding-top-xs blue bns-style2"><span class="margin-right-xxs blue">Security Guarantee</span><div class="arrow-ui thin-right"></div></div>
								<span>We guarantee that every transaction you make at Envelopes.com will be safe.</span>
							</li>
							<li>
								<a href="http://support.envelopes.com/customer/portal/articles/828488-returns-" title="Returns">
									<div class="padding-top-xs blue bns-style2"><span class="margin-right-xxs blue">Returns</span><div class="arrow-ui thin-right"></div></div>
								</a>
								<span>We aren't happy unless you are 100% satisfied. We accept returns of plain products if returned within 14 days of receiving the shipment.</span>
							</li>
						</ul>
						<hr />
						<div class="caption h5b bns-center padding-bottom-xs">
							</i><span class="bns-style1">Need Help?</span>
						</div>
						<ul class="need-help no-margin no-padding">
							<li>
								<div class="uibox tabular_row">
									<div>
										<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/mail-icon-1?fmt=png-alpha&amp;wid=24</@ofbizScene7Url>" />
										<span class="blue bns-style3"><a href="mailto:service@envelopes.com">Email Us</a></span>
									</div>
								</div>
							</li>
							<li>
								<div class="uibox tabular_row">
									<div>
										<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/phone-icon-1?fmt=png-alpha&amp;wid=24</@ofbizScene7Url>" />
										<span class="blue bns-style3"><a href="tel:1-877-683-5673">877.683.5673</a></span>
									</div>
								</div>
							</li>
							<li>
								<div class="uibox tabular_row">
									<div>
										<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/chat-icon-1?fmt=png-alpha&amp;wid=24</@ofbizScene7Url>" />
										<span class="blue bns-style3"><a href="javascript:void(0);" onclick="olark('api.box.expand')">Live Chat</a></span>
									</div>
								</div>
							</li>
						</ul>
					</div>

				</div>

				<div class="padding-xs margin-top-xs address-input generic-container coBillingAddress jqs-tracking jqs-opt1" data-tracking-seq="5" data-tracking-name="Billing Address">
					<div class="jqs-billing">
						<div class="row">
							<h3>3. Select a Billing Address</h3>
						</div>
						<div class="row margin-left-xxs margin-top-xs">
							<div class="padding-left-xxs horizontal-input">
								<div>
									<input id="bill_to_shipping" name="bill_to_shipping" type="checkbox" value="shipping" <#if parameterMap.bill_to_shipping?default('') == "shipping">checked="checked"</#if>/>
								</div>
								<div>
									<span class="margin-left-xxs"><label for="bill_to_shipping">Bill to shipping address</label></span>
								</div>
							</div>
						</div>
						<div class="billing-content">
							<#if (isSignedInUser == 'true' && billingAddresses?has_content && billingAddresses?size > 0)>
							<div class="row">
								<div class="small-12 medium-6 large-6 columns">
									<div class="envelope-select">
										<select id="saved-billing-address" name="saved_billing_address">
											<option value="">Select a saved address</option>
											<#list billingAddresses.entrySet() as entry>
												<#assign isSelected = (entry.key ==  parameterMap.saved_billing_address?default(''))?string('selected ', '')/>
												<option ${isSelected} value="${entry.key}" data-address="${entry.value.jsonData}">${entry.value.firstName} ${entry.value.lastName} - ${entry.value.address1}</option>
											</#list>
										</select>
									</div>
								</div>
							</div>
							</#if>
							<div class="row">
								<div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="billing_firstName">
									<input class="jqs-first-name" name="billing_firstName" type="text" value="${parameterMap.billing_firstName?default('')}" placeholder="First Name" data-bignamevalidate="billing_firstName" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="First Name" />
								</div>
								<div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="billing_lastName">
									<input class="jqs-last-name" name="billing_lastName" type="text" value="${parameterMap.billing_lastName?default('')}" placeholder="Last Name" data-bignamevalidate="billing_lastName" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Last Name" />
								</div>
							</div>
							<div class="row">
								<div class="small-12 medium-12 large-12 columns">
									<input class="jqs-company-name" name="billing_companyName" type="text" value="${parameterMap.billing_companyName?default('')}" placeholder="Company (Optional)" />
								</div>
							</div>
							<div class="row">
								<div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billing_address1">
									<input id="jqs-billing-autocomplete" class="jqs-address1" name="billing_address1" type="text" value="${parameterMap.billing_address1?default('')}" placeholder="Address Line 1" data-bignamevalidate="billing_address1" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Address" />
								</div>
							</div>
							<div class="row">
								<div class="small-12 medium-12 large-12 columns">
									<input class="jqs-address2" name="billing_address2" type="text" value="${parameterMap.billing_address2?default('')}" placeholder="Address Line 2 (Optional)" />
								</div>
							</div>
							<#--<div class="row">
								<div class="small-12 medium-12 large-12 columns">
									<input class="jqs-address3" name="billing_address3" type="text" value="${parameterMap.billing_address3?default('')}" placeholder="Address Line 3 (Optional)" />
								</div>
							</div>-->
							<div class="row">
								<div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="billing_city">
									<input class="jqs-city" name="billing_city" type="text" value="${parameterMap.billing_city?default('')}" placeholder="City" data-bignamevalidate="billing_city" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="City" />
								</div>
								<div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="billing_stateProvinceGeoId">
									<label class="envelope-select" data-bigNameValidateParent="billing_stateProvinceGeoId">
										<select class="jqs-state" name="billing_stateProvinceGeoId" data-type="billing" data-abide-validator="validateStateCode" data-bignamevalidate="billing_stateProvinceGeoId" data-bignamevalidatetype="required state" data-bignamevalidateerrortitle="State">
											<option value="">State/Province</option>
											${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
										</select>
									</label>
									<script>
										$('[name=billing_stateProvinceGeoId]').val('${parameterMap.billing_stateProvinceGeoId?default('')}');
									</script>
								</div>
								<div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="billing_postalCode">
									<input class="jqs-postal-code" name="billing_postalCode" data-type="billing" data-abide-validator="validatePostalCode" type="text" value="${parameterMap.billing_postalCode?default('')}" placeholder="Zip" data-bignamevalidate="billing_postalCode" data-bignamevalidatetype="required postalCode" data-bignamevalidateerrortitle="Zip" />
								</div>
							</div>
						<#--<div class="row">
							<div class="small-12 medium-12 large-12 columns">
								<input class="jqs-email" name="billing_email" type="email" value="" placeholder="Email" />
								<small class="error">Email is required.</small>
							</div>
						</div>-->
							<div class="row">
								<div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billing_countryGeoId">
									<label class="envelope-select" data-bigNameValidateParent="billing_countryGeoId">
										<select class="jqs-country" name="billing_countryGeoId" data-bignamevalidate="billing_countryGeoId" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Country">
											<option value="">Country</option>
										${screens.render("component://envelopes/widget/CommonScreens.xml#countries")}
										</select>
									</label>
									<script>
										$('[name=billing_countryGeoId]').val('${parameterMap.billing_countryGeoId?default('USA')}');
									</script>
								</div>
							</div>
							<div class="row">
								<div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billing_contactNumber">
									<input class="jqs-phone" name="billing_contactNumber" type="text" value="${parameterMap.billing_contactNumber?default('')}" placeholder="Phone" data-bignamevalidate="billing_contactNumber" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Phone" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="small-12 medium-12 large-12 columns">
								<input class="jqs-poid" name="correspondingPoId" type="text" value="${parameterMap.correspondingPoId?default('')}" maxlength="30" placeholder="Purchase Order ID (Optional)" />
							</div>
						</div>
                        <div class="row">
                            <div class="small-12 medium-12 large-12 columns">
                                <input class="jqs-resellerid margin-bottom-xxs" name="resellerId" type="text" value="${parameterMap.resellerId?default('')}" maxlength="20" placeholder="Tax ID (Optional)" autocomplete="off" />
                                <p style="font-size:12px;">
									<span style="font-weight: bold; color: #00a4e4">TAX NOTE:</span> If you are tax-exempt, please checkout and send your tax-certificate to
									<a href="mailto:tax@envelopes.com">tax@envelopes.com</a>.
									Once we have received your certificate and verify tax exemption, we will remove the tax from your order and mark
									your account tax-exempt.<br />
                                    <strong>Applicable to the following states only:</strong><br />
                                    ${Static["com.envelopes.util.EnvConstantsUtil"].TAXABLE_STATES?if_exists}
								</p>
                            </div>
                        </div>
						<input type="hidden" name="billing_countryCode" value="${parameterMap.billing_countryCode?default('')}" />
						<input type="hidden" name="billing_areaCode" value="${parameterMap.billing_areaCode?default('')}" />
					</div>
				</div>

				<div id="calcShipping" class="jqs-shipping-options padding-xs margin-top-xs shipping-options generic-container coShippingOptions jqs-tracking" data-tracking-seq="6" data-tracking-name="Shipping Options">
					<div>
						<div class="row">
							<h3>4. Shipping Options</h3>
						</div>
						<div id="jqs-shipping-options">
							${screens.render("component://envelopes/widget/CheckoutScreens.xml#checkout-shipping-options")}
						</div>
						<div class="row">
							<div class="small-12 medium-12 large-12 columns">
								<textarea name="orderNote" type="textarea" value="" placeholder="Shipping/Order Comments (optional)"></textarea>
							</div>
						</div>
						<script>
							$('[name=orderNote]').val('${parameterMap.orderNote?default('')}');
						</script>
					</div>
				</div>
				<div class="padding-xs margin-top-xs order-summary generic-container coOrderSummary jqs-tracking jqs-opt5" data-tracking-seq="7" data-tracking-name="Order Summary">
					<div class="row">
						<h3>Order Summary</h3>
						<div class="coupon-response margin-top-xxs alert-box hidden"></div>
					</div>
					<div id="jqs-order-summary">
					${screens.render("component://envelopes/widget/CheckoutScreens.xml#checkout-items")}
					</div>
				</div>
				<div class="jqs-payment-options padding-xs margin-top-xs payment-options generic-container coPaymentOptions jqs-tracking" data-tracking-seq="8" data-tracking-name="Payment Options">
					<div>
						<div class="row">
							<h3>5. Payment Options</h3>
						</div>
						<div class="row padding-left-xs margin-top-xxs">
							<div class="padding-left-xxs horizontal-input margin-top-xs">
								<div>
									<input id="pay_by_cc" name="paymentMethodTypeId" type="radio" value="CREDIT_CARD" <#if parameterMap.paymentMethodTypeId?default('CREDIT_CARD') == "CREDIT_CARD">checked="checked"</#if>/>
								</div>
								<div>
									<span class="margin-left-xxs">
										<label for="pay_by_cc">Pay by Credit Card<label>
									</span>
									<div class="accepted-cards">
										<img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/amex-icon.svg</@ofbizContentUrl>" alt="Amex" />
										<img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/discover-icon.svg</@ofbizContentUrl>" alt="Discover" />
										<img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/visa-icon.svg</@ofbizContentUrl>" alt="Visa" />
										<img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/mastercard-icon.svg</@ofbizContentUrl>" alt="Mastercard" />
									</div>
								</div>
							</div>
							<div id="jqs-pay-by-card" class="row pay-by-card">
								<div class="col-xs-12 margin-top-xxs">
									<div class="row margin-left-xxs margin-right-xxs">
										<div class="small-12 medium-12 large-12 columns ccValMessage" style="display: none;">
											<span class="alert-box alert radius">Please enter a valid credit card.</span>
										</div>
										<div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="cardNumber">
											<div id="card-number" class="hosted-field"></div>
										</div>
										<div class="small-12 medium-6 large-6 columns">
											<div id="expiration-date" class="hosted-field"></div>
										</div>
										<div class="small-12 medium-6 large-6 columns">
											<div id="cvv" class="hosted-field"></div>
										</div>
										<div class="small-12 medium-6 large-6 columns">
											<div id="postal-code" class="hosted-field"></div>
										</div>
									</div>
								</div>
								<input type="hidden" name="cardType" value="BRAINTREE_OFFLINE" />
							</div>
							<div class="padding-left-xxs horizontal-input margin-top-xs">
								<div>
									<input id="pay_by_check" name="paymentMethodTypeId" type="radio" value="PERSONAL_CHECK" <#if parameterMap.paymentMethodTypeId?default('') == "PERSONAL_CHECK">checked="checked"</#if>/>
								</div>
								<div>
									<span class="margin-left-xxs">
										<label for="pay_by_check">Pay by Check<label>
									</span>
									<div class="payment-type-icon">
										<img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/personal-check-icon.svg</@ofbizContentUrl>" alt="Pay By Check" />
									</div>
								</div>
							</div>
							<div id="jqs-pay-by-check" class="row pay-by-check">
								<div class="row">
									<div class="small-12 medium-6 large-6 columns no-padding padding-left-sm">
										<input name="checkNumber" type="text" value="" placeholder="Check Number (optional)" />
									</div>
								</div>
								<div class="row margin-bottom-xs padding-left-sm">
									<span class="instructions">
										<span>Instructions:</span>
										Your order will be held until we receive and deposit your check. Please make checks payable to Envelopes.com and mail your payment to:
									</span>
									<span class="margin-top-xs">Envelopes.com</span>
									<span>105 Maxess Rd.</span>
									<span>Suite S215</span>
									<span>Melville, NY 11747</span>
									<span>Attn: Check Order</span>
								</div>
							</div>
							<#if isNet30?has_content && isNet30?c == "true">
                            <div class="padding-left-xxs horizontal-input">
                                <div>
                                    <input id="pay_by_net30" name="paymentMethodTypeId" type="radio" value="EXT_NET30" <#if parameterMap.paymentMethodTypeId?default('') == "EXT_NET30">checked="checked"</#if>/>
                                </div>
                                <div>
									<span class="margin-left-xxs">
										<label for="pay_by_net30">Terms: Net 30<label>
									</span>
                                </div>
                            </div>
                            <div id="jqs-pay-by-net30" class="row pay-by-net30">
                                <div class="row margin-bottom-xs padding-left-sm">
									<span class="instructions">
										<span>Instructions:</span>
										Payment expected in 30 days.
									</span>
                                </div>
                            </div>
							</#if>
							<#-- ######################################################### -->
							<#-- ######################################################### -->
							<#-- ######################################################### -->
							<#-- AMAZON SETTINGS WILL EXPIRE 3 HOURS AFTER THIS PAGE LOADS -->
							<#-- ######################################################### -->
							<#-- ######################################################### -->
							<#-- ######################################################### -->
							<div class="padding-left-xxs horizontal-input margin-top-xs">
								<div>
									<input id="pay_by_amazon" name="paymentMethodTypeId" type="radio" value="EXT_AMAZON" <#if parameterMap.paymentMethodTypeId?default('') == "EXT_AMAZON">checked="checked"</#if>/>
								</div>
								<div>
									<span class="margin-left-xxs">
										<label for="pay_by_amazon">Pay by Amazon<label>
									</span>
									<div class="payment-type-icon">
										<img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/amazon-pay-icon.svg</@ofbizContentUrl>" alt="Amazon Pay" />
									</div>
								</div>
							</div>
							<div id="jqs-pay-by-amazon" class="row pay-by-amazon">
                                <input type="hidden" name="externalId" class="jqs-external-id" value="" data-bignamevalidate="externalId" data-bignamevalidatetype="required" data-bignamevalidateerrormessage="Please click the below 'Pay with Amazon' button to login to your Amazon account." />
								<iframe id="amazonIframe" scrolling="no" src="<@ofbizUrl>/payByAmazonButton</@ofbizUrl>"></iframe>
							</div>
                            <div class="padding-left-xxs horizontal-input margin-top-xs <#if paypalEnabled?c == "false"> hidden</#if>">
								<div>
                                    <input id="pay_by_paypal" name="paymentMethodTypeId" type="radio" value="EXT_PAYPAL_CHECKOUT" <#if parameterMap.paymentMethodTypeId?default('') == "EXT_PAYPAL_CHECKOUT">checked="checked"</#if>/>
                                </div>
                                <div>
									<span class="margin-left-xxs">
										<label for="pay_by_paypal">Pay by PayPal<label>
									</span>
									<div class="payment-type-icon">
										<img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/paypal-icon.svg</@ofbizContentUrl>" alt="PayPal" />
									</div>
                                </div>
                            </div>
                            <div id="paypal-button" class="row pay-by-paypal<#if paypalEnabled?c == "false"> hidden</#if>"></div>
						</div>
					</div>
				</div>
				<div class="row margin-top-sm place-order">
					<div id="g-recaptcha-error"></div>
					<div id="jqs-place-order" class="button-regular button-cta left" data-bigNameValidateSubmit="checkoutForm" data-bigNameValidateAction="checkoutFormSubmit">Place Order</div>
					<button bns-recaptcha_button class="g-recaptcha hidden" 
						data-sitekey="6Ld4mFIaAAAAACiExsFdotUuHCQGU8WyWMCS6yoY" 
						data-callback='onCapchaSuccess' 
						data-action='submit'>Submit</button>
					<div id="jqs-iparcel" class="button-regular button-cta left hidden">Next</div>
				</div>
				<div class="row checkoutDisclaimer">
                    <p>*By placing your order, you agree to Envelopes.com's <a href="<@ofbizUrl>/privacy</@ofbizUrl>" target="_blank">Privacy Policy</a> and <a href="http://support.envelopes.com/customer/en/portal/articles/828493-terms-conditions?b_id=17235" target="_blank">Terms &amp; Conditions</a>.</p>
                </div>
				<#--<div class="row margin-top-sm">
					<span class="left">By placing an order, you are agreeing to the <a href="#">terms &amp; conditions</a>.
				</div>-->
			</div>
			<div class="jqs-opt3 hidden" style="width: 270px; padding-left: 20px; padding-top: 20px;">
				<div id="jqs-vertical-order-summary" style="border: 1px solid #dee0e3; width: 250px; background-color: #ffffff;">
					${screens.render("component://envelopes/widget/CheckoutScreens.xml#vertical-checkout-items")}
				</div>
			</div>
		</div>
		<input type="hidden" name="productStoreId" value="10000" />
		<input type="hidden" name="userLoginId" value="${userLoginId?default('')}" />
		<input type="hidden" name="partyId" value="${partyId?default('')}" />
		<input type="hidden" name="NONCE_TOKEN" value="" />
	</form>
</div>

<#---- BEGIN FB COUPON ----->
<div id="fb-root"></div>
<#if (orderInfo.grandTotal)?exists && orderInfo.grandTotal gte 20>
<div class="likeUsAndSave jqs-likeUsAndSave">
	<div>
		Like Us On Facebook
		<div>and Save $1</div>
		<i class="fa fa-times"></i>
	</div>
	<div class="fb-like" data-href="https://www.facebook.com/Envelopes" data-layout="box_count" data-action="like" data-show-faces="false" data-share="false" data-send="true"></div>
</div>
</#if>
<#---- END FB COUPON ----->

<#-- Need the script tag here... must be done before other scripts below load AND after the html exists.-->
<script>
	if (typeof optTest != 'undefined' && optTest) {
		$('.jqs-opt2').appendTo('.jqs-opt1');

		$('.jqs-opt1').removeClass('generic-container padding-xs').addClass('double-container');

		$('.jqs-billing').css({
			'background-color': '#ffffff',
			'padding': '20px'
		}).addClass('generic-container');

		$('.jqs-opt3').removeClass('hidden');
		$('.jqs-opt4').addClass('double-container');
		$('.jqs-opt5').remove();
	}
</script>

<script src="https://www.google.com/recaptcha/api.js"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/checkout/jquery.envelopes.checkout.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/checkout/checkout.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
	<#if hasLoginError?default('false') == 'true'>
	<script>
		$.toggleLoginPanel();
	</script>
	</#if>
</#if>
<script>
    var placeSearch, shipAutocomplete, billAutocomplete;
    var componentForm = {
        street_number: {type: 'short_name', id: 'address1'},
        route: {type: 'long_name', id: 'address1'},
        locality: {type: 'long_name', id: 'city'},
        administrative_area_level_1: {type: 'short_name', id: 'stateProvinceGeoId'},
        country: {type: 'short_name', id: 'countryGeoId'},
        postal_code: {type: 'short_name', id: 'postalCode'}
    };

    function initAddressAutocomplete() {
        shipAutocomplete = new google.maps.places.Autocomplete((document.getElementById('jqs-shipping-autocomplete')), {types: ['geocode']});
        shipAutocomplete.addListener('place_changed', fillInShipAddress);

        billAutocomplete = new google.maps.places.Autocomplete((document.getElementById('jqs-billing-autocomplete')), {types: ['geocode']});
        billAutocomplete.addListener('place_changed', fillInBillAddress);
    }

    function fillInShipAddress() {
        return fillInAddress('.jqs-shipping');
	}
    function fillInBillAddress() {
        return fillInAddress('.jqs-billing');
    }
	function fillInAddress(scope) {
        var place = (scope == '.jqs-shipping') ? shipAutocomplete.getPlace() : billAutocomplete.getPlace();

        if (place == undefined || (place != undefined && typeof place.address_components == 'undefined')) {
            return false;
        }
        globalResetAddress(scope, true);

        var addressData = {};
        var addressStreetNumber = null;
        var addressRoute = null;
        for (var i = 0; i < place.address_components.length; i++) {
            var addressType = place.address_components[i].types[0];
            if (componentForm[addressType]) {
                var val = place.address_components[i][componentForm[addressType].type];
                if (addressType == 'street_number') {
                    addressStreetNumber = val;
                } else if (addressType == 'route') {
                    addressRoute = val;
                } else {
                    addressData[componentForm[addressType].id] = val;
                }
            }
        }

        var streetAddress = null;
        if (addressStreetNumber != null) {
            streetAddress = addressStreetNumber;
        }
        if (addressRoute != null) {
            streetAddress = (streetAddress == null) ? addressRoute : streetAddress + ' ' + addressRoute;
        }
        addressData.address1 = streetAddress;
        globalPopulateAddress(addressData, scope, true);
    }

	function checkoutFormSubmit() {
		$("[bns-recaptcha_button]").trigger("click");
    }

	function onCapchaSuccess() {
		GoogleAnalytics.trackEvent('Checkout', 'Payment Method', $('[name="paymentMethodTypeId"]:checked').val());
		
		if ($('#bill_to_shipping').is(':checked') && typeof $.billToShippingAddress == 'function') {
			$.billToShippingAddress();
		}

		if ($('[name="paymentMethodTypeId"]:checked').val() == "CREDIT_CARD") {
			$('#checkoutForm').trigger("getToken");
		} else if ($('[name="paymentMethodTypeId"]:checked').val() == "EXT_PAYPAL_CHECKOUT" && $("[name='NONCE_TOKEN']").val() == "") {
			$(".jqs-payment-options").prepend(
				$("<div />").attr("bns-validationerrorheader", "").html("Cannot tokenize paypal payment.  Please click Paypal Button and submit payment.")
			);
		} else {
        	$('#checkoutForm').submit();
		}
	}
</script>
<script>
$('form input:not([type="submit"])').keydown(function(e) {
    if (e.keyCode == 13) {
        var inputs = $(this).parents("form").eq(0).find(":input");
        if (inputs[inputs.index(this) + 1] != null) {                    
            inputs[inputs.index(this) + 1].focus();
        }
        e.preventDefault();
        return false;
    }
});
</script>
<script>
	var form = document.querySelector('#checkoutForm');

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
					if (typeof freeOrder === "boolean" && freeOrder) {
						$('#checkoutForm').submit();
					} else if (err) {
						$(".jqs-payment-options").prepend(
							$("<div />").attr("bns-validationerrorheader", "").html(err.message)
						);
					} else {
						$("[name='NONCE_TOKEN']").val(payload.nonce);
						$('#checkoutForm').submit();
					}
				});
			};
			
			$(form).on("getToken", tokenize);
		});
	}

	// Create a PayPal Checkout component
	// Create a client.
	braintree.client.create({
		authorization: '${Static["com.bigname.payments.braintree.BraintreeHelper"].getClientToken("envelopes")?if_exists?replace("&#x3d;", "=")}'
	}, function (clientErr, clientInstance) {
		// Stop if there was a problem creating the client.
		// This could happen if there is a network error or if the authorization
		// is invalid.
		if (clientErr) {
			console.error('Error creating client:', clientErr);
			return;
		}

		// Create a PayPal Checkout component.
		braintree.paypalCheckout.create({
			client: clientInstance
		}, function (paypalCheckoutErr, paypalCheckoutInstance) {
			paypalCheckoutInstance.loadPayPalSDK({
				currency: 'USD',
				intent: 'authorize'
			}, function () {
				paypal.Buttons({
					fundingSource: paypal.FUNDING.PAYPAL,

					createOrder: function () {
						return paypalCheckoutInstance.createPayment({
							flow: 'checkout',
							amount: $('.total-cost').html().replace(/[^\d.-]/g, ''),
							currency: 'USD',
							intent: 'authorize',
							requestBillingAgreement: true,
							billingAgreementDetails: {
								description: 'Description of the billng agreement to display to the customer'
							}
						});
					},

					onApprove: function (data, actions) {
						return paypalCheckoutInstance.tokenizePayment(data, function (err, payload) {
							$("[name='NONCE_TOKEN']").val(payload.nonce);
						});
					},

					onCancel: function (data) {
						console.log('PayPal payment cancelled', JSON.stringify(data, 0, 2));
					},

					onError: function (err) {
						console.error('PayPal error', err);
					}
				}).render('#paypal-button').then(function () {
					// The PayPal button will be rendered in an html element with the ID
					// `paypal-button`. This function will be called when the PayPal button
					// is set up and ready to be used
				});
			});
		});
	});
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCk6UIFc_ui0c8IzBKQBmMqknIGgSr8xQY&libraries=places&callback=initAddressAutocomplete" async defer></script>