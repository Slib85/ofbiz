<div class="popup-border-fade">
	<form data-abide="ajax" id="addressForm" name="addressForm" method="POST">
		<div class="padding-bottom-xxs margin-bottom-xxs popup-title padding-left-xxs">
			<h3><#if mode == 'add'>Add<#else>Edit</#if> an Address</h3>
		</div>
		<div class="padding-xs">
			<div data-alert class="jqs-address-error alert-box alert radius hidden"><i class="fa fa-warning padding-right-xxs"></i>Please correct the errors shown below</div>
			<#if addressType == 'checkout'>
                <div class="row">
                    <div class="jqs-address-type" style="display: none">${addressType}</div>
                    <input type="hidden" name="contactMechId" value="">
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" required="" name="firstName" value="" placeholder="First Name"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>First Name is required.</small>
                    </div>
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" required="" name="lastName" value="" placeholder="Last Name"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>Last Name is required.</small>
                    </div>
                </div>
                <div class="row">
                    <div class="small-12 medium-12 large-12 columns">
                        <input type="text" name="companyName" value="" placeholder="Company (Optional)"/>
                    </div>
                </div>
                <div class="row">
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" required="" name="address1" value="" placeholder="Address Line 1"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>Address Line 1 is required.</small>
                    </div>
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" name="address2" value="" placeholder="Address Line 2 (Optional)"/>
                    </div>
                </div>
                <div class="row">
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" required="" name="city" value="" placeholder="City"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>City is required.</small>
                    </div>
                    <div class="small-12 medium-12 large-3 columns">
                        <label class="envelope-select">
                            <select class="jqs-state jqs-abide" required="" name="stateProvinceGeoId">
                                <option value="">State/Province</option>
                            ${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
                            </select>
                        </label>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>Please select a state.</small>
                    </div>
                    <div class="small-12 medium-12 large-3 columns">
                        <input type="text" name="postalCode" required="" value="" placeholder="Postal Code"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>Postal Code is required.</small>
                    </div>
                </div>
                <div class="row">
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" required="" name="contactNumber" value="" placeholder="Phone"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>Phone is required.</small>
                    </div>
                    <div class="small-12 medium-12 large-2 columns">
                        <span>
                            <input id="CMPT_SB" type="radio" name="contactMechPurposeTypeId" value="SHIPPING_AND_BILLING_LOCATION" checked="true"/>
                            <label class="no-margin" for="CMPT_SB">Shipping and Billing</label>
                        </span>
                    </div>
                    <div class="small-12 medium-12 large-2 columns shipping-radio">
                        <span>
                            <input id="CMPT_S" type="radio" name="contactMechPurposeTypeId" value="SHIPPING_LOCATION"/>
                            <label class="no-margin" for="CMPT_S">Shipping Only</label>
                        </span>
                    </div>
                    <div class="small-12 medium-12 large-2 columns billing-radio">
                        <span>
                            <input id="CMPT_B" type="radio" name="contactMechPurposeTypeId" value="BILLING_LOCATION"/>
                            <label class="no-margin" for="CMPT_B">Billing Only</label>
                        </span>
                    </div>
                </div>
                <div class="row">
                    <div class="small-12 medium-12 large-12 columns defaults">
                        <div class="left margin-right-sm">
                            <span class="jqs-default-shipping">Default Shipping: <input type="checkbox" name="defaultShipping" value="true" /></span>
                        </div>
                        <div class="left">
                            <span class="jqs-default-billing">Default Billing: <input type="checkbox" name="defaultBilling" value="true" /></span>
                        </div>
                    </div>
                </div>
            <#else>
                <div class="row">
                    <div class="jqs-address-type" style="display: none">${addressType}</div>
                    <input type="hidden" name="customerAddressId" value="">
                    <input type="hidden" name="customerAddressGroupId" value="${addressBook.customerAddressGroupId}">
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" required="" name="name" value="" placeholder="Email"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>Email is required.</small>
                    </div>
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" name="name2" value="" placeholder="Name"/>
                    </div>
                </div>
                <div class="row">
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" required="" name="address1" value="" placeholder="Address Line 1"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>Address Line 1 is required.</small>
                    </div>
                    <div class="small-12 medium-12 large-6 columns">
                        <input type="text" name="address2" value="" placeholder="Address Line 2 (Optional)"/>
                    </div>
                </div>
                <div class="row">
                    <div class="small-12 medium-12 large-3 columns">
                        <input type="text" required="" name="city" value="" placeholder="City"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>City is required.</small>
                    </div>
                    <div class="small-12 medium-12 large-3 columns">
                        <input type="text" class="jqs-state jqs-abide" required="" name="state" placeholder="State"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>State is required.</small>
                    </div>
                    <div class="small-12 medium-12 large-3 columns">
                        <input type="text" name="zip" required="" value="" placeholder="Postal Code"/>
                        <small class="error"><i class="fa fa-warning padding-right-xxs"></i>Postal Code is required.</small>
                    </div>
                    <div class="small-12 medium-12 large-3 columns">
                        <input type="text" name="country" value="" placeholder="Country (Optional)"/>
                    </div>
                </div>
            </#if>

			<div class="row">
				<div class="small-12 medium-12 large-6 columns">

					<div class="jqs-submit button-regular button-cta padding-left-xxs padding-right-xxs">Submit</div>
				</div>
			</div>
		</div>
	</form>
	<a class="close-reveal-modal"><i class="fa fa-times"></i></a>
</div>