<link href="<@ofbizContentUrl>/html/css/account/account.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/account/addresses.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="content account">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Billing &amp; Shipping
	</div>
    <div class="container addresses padding-xs">
        <div id="address-message" data-alert class="alert-box success radius margin-top-xxs hidden">Address has been successfully added to the address book.</div>
        <div id="global-address-error" data-alert class="alert-box alert radius margin-top-xxs hidden">An error occurred while deleting the address.</div>
        <div class="row no-margin padding-left-xxs padding-right-xxs">
			<div class="add-new-address large-3 columns no-padding">
				<div class="button-regular button-cta padding-left-xxs padding-right-xxs" data-reveal-id="add-address">Add New Address</div>
			</div>
			<div class="columns large-9 no-padding">
				<div class="filter-by">
					<span class="text-right padding-right-xxs">Display:</span>
					<div class="envelope-select no-margin">
						<select class="jqs-letterFilter no-margin" required="" name="letterFilter">
							<option value="all">All</option>
							<option value="a">A</option>
							<option value="b">B</option>
							<option value="c">C</option>
							<option value="d">D</option>
							<option value="e">E</option>
							<option value="f">F</option>
							<option value="g">G</option>
							<option value="h">H</option>
							<option value="i">I</option>
							<option value="j">J</option>
							<option value="k">K</option>
							<option value="l">L</option>
							<option value="m">M</option>
							<option value="n">N</option>
							<option value="o">O</option>
							<option value="p">P</option>
							<option value="q">Q</option>
							<option value="r">R</option>
							<option value="s">S</option>
							<option value="t">T</option>
							<option value="u">U</option>
							<option value="v">V</option>
							<option value="w">W</option>
							<option value="x">X</option>
							<option value="y">Y</option>
							<option value="z">Z</option>
						</select>
					</div>
				</div>
			</div>
		</div>
        <div id="add-address" class="jqs-parent reveal-modal no-padding" data-reveal>
        ${request.setAttribute("mode", "add")}
            ${screens.render("component://envelopes/widget/AccountScreens.xml#address")}
        </div>

        <div id="edit-address" class="jqs-parent reveal-modal no-padding" data-reveal>
        ${request.setAttribute("mode", "edit")}
            ${screens.render("component://envelopes/widget/AccountScreens.xml#address")}
        </div>
        <div id="jqs-address-list" class="row address-list">
        <#list addresses.entrySet() as entry>
            <#assign address = entry.value/>

            <div id="address-${entry.key}" class="jqs-address padding-xs margin-right-xxs margin-top-xxs left">
                <div>
                    <span class="jqs-contact-mech-purpose-type"><#if address.contactMechPurposeTypeId?default('') == 'SHIPPING_LOCATION'>Shipping Address<#elseif address.contactMechPurposeTypeId?default('') == 'BILLING_LOCATION'>Billing Address<#elseif address.contactMechPurposeTypeId?default('') == 'SHIPPING_AND_BILLING_LOCATION'>Shipping &amp; Billing Address</#if></span>
                </div>
                <div>
                    <span class="defaults jqs-defaults ${address.defaults?default('')}"></span>
                </div>
                <div>
                    <span class="jqs-first-name">${address.firstName?default('')}</span> <span class="jqs-last-name">${address.lastName?default('')}</span>
                </div>
                <div>
                    <span class="jqs-company">${address.companyName?default('')}</span>
                </div>
                <div>
                    <span class="jqs-address1">${address.address1?default('')}</span>
                </div>
                <div>
                    <span class="jqs-address2">${address.address2?default('')}</span>
                </div>
                <div>
                    <span class="jqs-city">${address.city?default('')}</span>, <span class="jqs-state">${address.stateProvinceGeoId?default('')}</span> <span class="jqs-postal-code">${address.postalCode?default('')}</span>
                </div>
                <div>
                    <span class="jqs-country">${address.countryGeoId?default('')}</span>
                </div>
                <div>
                    <span class="jqs-phone">${address.contactNumber?default('')}</span>
                </div>
                <div>
					<span>
						<a class="jqs-address-data" href="#" data-reveal-id="edit-address" data-address="${address.jsonData}">edit</a>
					</span>
					<span class="margin-left-xs">
						<a class="jqs-delete" href="javascript:void(0)" data-contact-mech-id="${address.contactMechId}">delete</a>
					</span>
                </div>
            </div>
        </#list>
        </div>
    <#if addresses?size == 0 >
        <div class="jqs-address padding-xs margin-right-xxs margin-top-xxs left">
            <div>
                <span class="jqs-contact-mech-purpose-type"></span>
            </div>
            <div>
                <span class="defaults jqs-defaults"><#--(default shipping and billing)--></span>
            </div>
            <div>
                <span class="jqs-first-name"></span> <span class="jqs-last-name"></span>
            </div>
            <div>
                <span class="jqs-company"></span>
            </div>
            <div>
                <span class="jqs-address1"></span>
            </div>
            <div>
                <span class="jqs-address2"></span>
            </div>
            <div>
                <span class="jqs-city"></span>, <span class="jqs-state"></span> <span class="jqs-postal-code"></span>
            </div>
            <div>
                <span class="jqs-country"></span>
            </div>
            <div>
                <span class="jqs-phone"></span>
            </div>
            <div>
				<span>
					<a class="jqs-address-data" href="#" data-reveal-id="edit-address">edit</a>
				</span>
				<span class="margin-left-xs">
					<a href="#">delete</a>
				</span>
            </div>
        </div>
    </#if>
    </div>
</div>
<script>
    var addAddressURL = '<@ofbizUrl>/saveAddress</@ofbizUrl>';
    var removeAddressURL = '<@ofbizUrl>/discAddress</@ofbizUrl>';
</script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/addresses.js</@ofbizContentUrl>"></script>
