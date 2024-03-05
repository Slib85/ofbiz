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
<link href="<@ofbizContentUrl>/html/css/checkout/order-summary.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/checkout/checkout.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />

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
    <!-- <div id="checkout-message" data-alert class="alert-box success radius hidden">${checkoutMessage?default('')}</div>
    <div id="checkout-error" data-alert class="alert-box alert radius" style="display:none">${checkoutError?default('')}</div> 
    <script>
        if($('#checkout-message').text() != '') {
            $('#checkout-message').removeClass('hidden').show();
        }

        if($('#checkout-error').text() != '') {
            $('#checkout-error').removeClass('hidden').show();
        }
    </script> -->
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
                            <div style="position: absolute; top:40px; right:20px;"><a href="javascript:$.showForgotPassword()" data-reveal-id="secure-layer">Forgot Password?</a></div>
                        </div>
                        <div class="small-12 medium-3 large-2 columns sign_in_button_container">
                            <input name="responseName" type="hidden" value="checkout"/>
                            <input type="hidden" name="saveResponse" value="true"/>
                            <div class="button-regular button-cta" onclick="$('#loginForm').submit();">Sign In</div>
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
                                    <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="checkoutEmailAddress">
                                        <input type="hidden" name="oldEmailAddress" value="<#if request.getSession().getAttribute("userLogin")?exists>${request.getSession().getAttribute("userLogin").userLoginId?default('')}</#if>" />
                                        <input class="jqs-email" name="checkoutEmailAddress" data-bigNameValidate="checkoutEmailAddress" data-bigNameValidateType="required" data-bignamevalidateerrortitle="Email Address" type="email" value="<#if parameterMap.emailAddress?exists>${parameterMap.emailAddress?default('')}<#elseif request.getSession().getAttribute("userLogin")?exists>${request.getSession().getAttribute("userLogin").userLoginId?default('')}</#if>" placeholder="Email Address" />
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
                                    <div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="shippingFirstName">
                                        <input class="jqs-first-name" name="shippingFirstName" type="text" placeholder="First Name" data-bignamevalidate="shippingFirstName" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="First Name"/>
                                    </div>
                                    <div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="shippingLastName">
                                        <input class="jqs-last-name" name="shippingLastName" type="text" placeholder="Last Name" data-bignamevalidate="shippingLastName" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Last Name"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="shippingCompanyName">
                                        <input class="jqs-company-name" name="shippingCompanyName" type="text" placeholder="Company (Optional)" data-bignamevalidate="shippingCompanyName"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="shippingAddress1">
                                        <input class="jqs-address1" name="shippingAddress1" type="text" placeholder="Address Line 1" data-bignamevalidate="shippingAddress1" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Address"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="shippingAddress2">
                                        <input class="jqs-address2" name="shippingAddress2" type="text" placeholder="Address Line 2 (Optional)" data-bignamevalidate="shippingAddress2"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="shippingCity">
                                        <input class="jqs-city" name="shippingCity"type="text" placeholder="City" data-bignamevalidate="shippingCity" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="City"/>
                                    </div>
                                    <div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="shippingState">
                                        <label class="bigNameSelect" data-bigNameValidateParent="shippingState">
                                            <select class="jqs-state" name="shippingState" data-type="shipping" data-bignamevalidate="shippingState" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="State">
                                                <option value="">State/Province</option>
											${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
                                            </select>
                                        </label>
                                        <script>
                                            $('[name=checkoutState]').val('${parameterMap.shipping_stateProvinceGeoId?default('')}');
                                        </script>
                                    </div>
                                    <div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="shippingZip">
                                        <input id="shipping-postal-code" class="jqs-postal-code" name="shippingZip" data-type="shipping"type="text" value="${parameterMap.shipping_postalCode?default('')}" placeholder="Zip" data-bignamevalidate="shippingZip" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Zip"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="shippingCountry">
                                        <label class="bigNameSelect" data-bigNameValidateParent="shippingCountry">
                                            <select class="jqs-country jqs-shipping-country" name="shippingCountry" data-bignamevalidate="shippingCountry" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Country">
                                                <option value="">Country</option>
											${screens.render("component://envelopes/widget/CommonScreens.xml#countries")}
                                            </select>
                                        </label>
                                        <script>
                                            $('[name=checkoutCountry]').val('${parameterMap.shipping_countryGeoId?default('USA')}');
                                        </script>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="shippingPhone">
                                        <input class="jqs-phone" name="shippingPhone" type="text" value="${parameterMap.shipping_contactNumber?default('')}" placeholder="Phone" data-bignamevalidate="shippingPhone" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Phone"/>
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
                    <div class="padding-left-xs shop-with-confidence jqs-opt2">
                        <div>
                            <div class="padding-xs">
                                <div class="swc-header">
                                    <div>
                                        <i class="fa fa-lock"></i>
                                    </div>
                                    <div>
                                        <h4 class="margin-left-xxs">Shop With Confidence</h4>
                                    </div>
                                </div>
                                <div class="margin-top-xs">
                                    <span>Privacy Policy</span>
                                    <p class="no-margin">
                                        We will never share or sell any of your personal information, including your e-mail address.
                                    </p>
                                </div>
                                <div class="margin-top-xs">
                                    <span>Security Guarantee</span>
                                    <p class="no-margin">
                                        We guarantee that every transaction you make at Envelopes.com will be safe.
                                    </p>
                                </div>
                                <div class="margin-top-xs">
                                    <span>Shipping Policy</span>
                                    <p class="no-margin">
                                        95% of orders ship the same or next business day.
                                    </p>
                                </div>
                                <div class="margin-top-xs">
                                    <span>Returns</span>
                                    <p class="no-margin">
                                        We aren't happy unless you are 100% satisfied. We accept returns of plain products if returned within 14 days of receiving the shipment.
                                    </p>
                                </div>
                            </div>
                        </div>
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
                                <div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="billingFirstName">
                                    <input class="jqs-first-name" name="billingFirstName" type="text" placeholder="First Name" data-bignamevalidate="billingFirstName" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="First Name"/>
                                </div>
                                <div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="billingLastName">
                                    <input class="jqs-last-name" name="billingLastName" type="text" placeholder="Last Name" data-bignamevalidate="billingLastName" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Last Name"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billingCompanyName">
                                    <input class="jqs-company-name" name="billing_companyName" type="text" placeholder="Company (Optional)" data-bignamevalidate="billingCompanyName"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billingAddress1">
                                    <input class="jqs-address1" name="billingAddress1" type="text" placeholder="Address Line 1" data-bignamevalidate="billingAddress1" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Address"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billingAddress2">
                                    <input class="jqs-address2" name="billingAddress2" type="text" placeholder="Address Line 2 (Optional)" data-bignamevalidate="billingAddress2"/>
                                </div>
                            </div>
						<#--<div class="row">
                            <div class="small-12 medium-12 large-12 columns">
                                <input class="jqs-address3" name="billing_address3" type="text" value="${parameterMap.billing_address3?default('')}" placeholder="Address Line 3 (Optional)" />
                            </div>
                        </div>-->
                            <div class="row">
                                <div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="billingCity">
                                    <input class="jqs-city" name="billingCity" type="text" placeholder="City" data-bignamevalidate="billingCity" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="City"/>
                                </div>
                                <div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="billingState">
                                    <label class="bigNameSelect" data-bigNameValidateParent="billingState">
                                        <select class="jqs-state" name="billingState" data-type="billing" data-bignamevalidate="billingState" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="State">
                                            <option value="">State/Province</option>
										${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
                                        </select>
                                    </label>
                                    <script>
                                        $('[name=billingState]').val('${parameterMap.billing_stateProvinceGeoId?default('')}');
                                    </script>
                                </div>
                                <div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="billingZip">
                                    <input class="jqs-postal-code" name="billingZip" data-type="billing" type="text" value="${parameterMap.billing_postalCode?default('')}" placeholder="Zip" data-bignamevalidate="billingZip" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Zip"/>
                                </div>
                            </div>
						<#--<div class="row">
							<div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billingEmail">
								<input class="jqs-email" name="billingEmail" type="email" value="" placeholder="Email" data-bignamevalidate="billingEmail" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Email"/>
							</div>
						</div>-->
                            <div class="row">
                                <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billingCountry">
                                    <label class="bigNameSelect" data-bigNameValidateParent="billingCountry">
                                        <select class="jqs-country" required="" name="billingCountry" data-bignamevalidate="billingCountry" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Country">
                                            <option value="">Country</option>
										${screens.render("component://envelopes/widget/CommonScreens.xml#countries")}
                                        </select>
                                    </label>
                                    <script>
                                        $('[name=billingCountry]').val('${parameterMap.billing_countryGeoId?default('USA')}');
                                    </script>
                                </div>
                            </div>
                            <div class="row">
                                <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billingPhone">
                                    <input class="jqs-phone" name="billingPhone" type="text" placeholder="Phone" data-bignamevalidate="billingPhone" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Phone"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="small-12 medium-12 large-12 columns" data-bignamevalidateid="billingOrderId">
                                <input class="jqs-poid" name="billingOrderId" type="text" value="${parameterMap.correspondingPoId?default('')}" maxlength="30" placeholder="Purchase Order ID (Optional)" data-bignamevalidate="billingOrderId"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="small-12 medium-12 large-12 columns">
                                <input class="jqs-resellerid jqs-abide" name="resellerId" type="text" value="${parameterMap.resellerId?default('')}" maxlength="20" placeholder="Tax ID (Optional)" autocomplete="off" />
                                <p style="font-size:12px;">
                                    <span style="font-weight: bold; color: #00a4e4">TAX NOTE:</span> If you are tax-exempt, please checkout and send your tax-certificate to
                                    <a href="mailto:tax@folders.com">tax@folders.com</a>.
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
                            <div class="padding-left-xxs horizontal-input">
                                <div>
                                    <input id="pay_by_cc" name="paymentMethodTypeId" type="radio" value="CREDIT_CARD" <#if parameterMap.paymentMethodTypeId?default('CREDIT_CARD') == "CREDIT_CARD">checked="checked"</#if>/>
                                </div>
                                <div>
									<span class="margin-left-xxs">
										<label for="pay_by_cc">Pay by credit card<label>
									</span>
                                    <div class="accepted-cards">
                                        <img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/card-1.jpg</@ofbizContentUrl>" alt="Amex" />
                                        <img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/card-2.jpg</@ofbizContentUrl>" alt="Discover" />
                                        <img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/card-3.jpg</@ofbizContentUrl>" alt="Visa" />
                                        <img class="margin-left-xxs" src="<@ofbizContentUrl>/html/img/temp/card-4.jpg</@ofbizContentUrl>" alt="Mastercard" />
                                    </div>
                                </div>
                            </div>
                            <div id="jqs-pay-by-card" class="row pay-by-card">
                                <div class="col-xs-12 margin-top-xxs">
                                    <div class="row margin-left-xxs margin-right-xxs">
                                        <div class="small-12 medium-12 large-12 columns ccValMessage" style="display: none;">
                                            <span class="alert-box alert radius">Please enter a valid credit card.</span>
                                        </div>
                                        <div class="small-12 medium-6 large-6 columns" data-bignamevalidateid="checkoutCardNumber">
                                            <input class="jqs-card-number inspectletIgnore" name="cardNumber" type="text" required="" value="${parameterMap.cardNumber?default('')}" placeholder="Card Number" data-bignamevalidate="checkoutCardNumber" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Card Number"/>
                                        </div>
                                        <div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="checkoutExpMonth">
                                            <label class="bigNameSelect" data-bigNameValidateParent="checkoutExpMonth">
                                                <select name="checkoutExpMonth" required="" data-bignamevalidate="checkoutExpMonth" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Expiration Month">
                                                    <option value="">Exp Month</option>
                                                    <option value="01">01 - January</option>
                                                    <option value="02">02 - February</option>
                                                    <option value="03">03 - March</option>
                                                    <option value="04">04 - April</option>
                                                    <option value="05">05 - May</option>
                                                    <option value="06">06 - June</option>
                                                    <option value="07">07 - July</option>
                                                    <option value="08">08 - August</option>
                                                    <option value="09">09 - September</option>
                                                    <option value="10">10 - October</option>
                                                    <option value="11">11 - November</option>
                                                    <option value="12">12 - December</option>
                                                </select>
                                            </label>
                                            <script>
                                                $('[name=checkoutExpMonth]').val('${parameterMap.expMonth?default('')}');
                                            </script>
                                        </div>
                                        <div class="small-12 medium-3 large-3 columns" data-bignamevalidateid="checkoutExpYear">
                                            <label class="bigNameSelect" data-bigNameValidateParent="checkoutExpYear">
                                                <select name="checkoutExpYear" required=""  data-bignamevalidate="checkoutExpYear" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Expiration Year">
                                                    <option value="">Exp Year</option>
        											<#list currentYear..currentYear + 10 as i>
                                                        <option value="${i}">${i}</option>
        											</#list>
                                                </select>
                                            </label>
                                            <script>
                                                $('[name=checkoutExpYear]').val('${parameterMap.expYear?default('')}');
                                            </script>
                                        </div>
                                    </div>
                                    <div class="row margin-left-xxs margin-right-xxs">
                                        <div class="small-12 medium-12 large-12 columns security-code" data-bignamevalidateid="checkoutSecurityCode">
                                            <input type="text" name="checkoutSecurityCode" required="" value="" placeholder="Security Code" data-bignamevalidate="checkoutSecurityCode" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Security Code"/>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" name="cardType" value="<#if parameterMap.cardType?has_content>${parameterMap.cardType?default("")}<#else>CCT_VISA</#if>" />
                            </div>
                            <div class="padding-left-xxs horizontal-input">
                                <div>
                                    <input id="pay_by_check" name="paymentMethodTypeId" type="radio" value="PERSONAL_CHECK" <#if parameterMap.paymentMethodTypeId?default('') == "PERSONAL_CHECK">checked="checked"</#if>/>
                                </div>
                                <div>
									<span class="margin-left-xxs">
										<label for="pay_by_check">Pay by check<label>
									</span>
                                </div>
                            </div>
                            <div id="jqs-pay-by-check" class="row pay-by-check">
                                <div class="row">
                                    <div class="small-12 medium-6 large-6 columns no-padding padding-left-sm margin-top-xxs" data-bignamevalidateid="checkoutCheckNumber">
                                        <input name="checkNumber" type="text" value="" placeholder="Check Number (optional)" data-bignamevalidate="checkoutCheckNumber"/>
                                    </div>
                                </div>
                                <div class="row margin-bottom-xs padding-left-sm">
									<span class="instructions">
										<span>Instructions:</span>
										Your order will be held until we receive and deposit your check. Please make checks payable to Envelopes.com and mail your payment to:
									</span>
                                    <span class="margin-top-xs">Folders.com</span>
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
                            <div class="padding-left-xxs horizontal-input">
                                <div>
                                    <input id="pay_by_amazon" name="paymentMethodTypeId" type="radio" value="EXT_AMAZON" <#if parameterMap.paymentMethodTypeId?default('') == "EXT_AMAZON">checked="checked"</#if>/>
                                </div>
                                <div>
									<span class="margin-left-xxs">
										<label for="pay_by_amazon">Pay by Amazon<label>
									</span>
                                </div>
                            </div>
                            <div id="jqs-pay-by-amazon" class="row pay-by-amazon">
                                <input type="hidden" name="externalId" class="jqs-abide jqs-amazon-external-id" data-abide-validator="validateAmazonPayment" required="" value="" />
                                <small class="error margin-top-xxs">Please click the below 'Pay with Amazon' button to login to your Amazon account.</small>
                                <iframe id="amazonIframe" scrolling="no" src="<@ofbizUrl>/payByAmazonButton</@ofbizUrl>"></iframe>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row margin-top-sm place-order">
                    <div id="jqs-place-order" class="button-regular button-cta left" data-bigNameValidateSubmit="checkoutForm" data-bigNameValidateAction="checkoutFormSubmit">Place Order</div>
                    <div id="jqs-iparcel" class="button-regular button-cta left hidden">Next</div>
                </div>
                <div class="row checkoutDisclaimer">
                    <p>*By placing your order, you agree to Folders.com's <a href="<@ofbizUrl>/privacy</@ofbizUrl>" target="_blank">Privacy Policy</a> and <a href="<@ofbizUrl>/terms</@ofbizUrl>" target="_blank">Terms &amp; Conditions</a>.</p>
                </div>
                <div class="row margin-top-sm">
				<#--<span class="left">By placing an order, you are agreeing to the <a href="#">terms &amp; conditions</a>.-->
                </div>
            </div>
        </div>
        <input type="hidden" name="productStoreId" value="10000" />
        <input type="hidden" name="userLoginId" value="${userLoginId?default('')}" />
        <input type="hidden" name="partyId" value="${partyId?default('')}" />
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
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/folders/checkout/jquery.envelopes.checkout.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/checkout/checkout.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
	<#if hasLoginError?default('false') == 'true'>
    <script>
        $.toggleLoginPanel();
    </script>
	</#if>
</#if>


 
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/util/bigNameValidation.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script>
    function checkoutFormSubmit() {
        $('#checkoutForm').submit();
    }
</script>
