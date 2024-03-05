<link href="<@ofbizContentUrl>/html/css/account/account.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/account/addresses.css</@ofbizContentUrl>" rel="stylesheet" />
<div class="content account">
    <div class="content-breadcrumbs">
        <a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > <a href="<@ofbizUrl>/addressBooks</@ofbizUrl>">Address Books</a> > ${addressBook.name}
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
            ${screens.render("component://envelopes/widget/AccountScreens.xml#addressingAddress")}
        </div>

        <div id="edit-address" class="jqs-parent reveal-modal no-padding" data-reveal>
        ${request.setAttribute("mode", "edit")}
            ${screens.render("component://envelopes/widget/AccountScreens.xml#addressingAddress")}
        </div>
        <div id="jqs-address-list" class="row address-list">
        <#list addressBook.addresses as address>

            <div id="address-${address.customerAddressId}" class="jqs-address padding-xs margin-right-xxs margin-top-xxs left">
                <div>
                    <span class="jqs-name">${address.name?default('')}</span><br/><span class="jqs-name2">${address.name2?default('')}</span>
                </div>
                <div>
                    <span class="jqs-address1">${address.address1?default('')}</span>
                </div>
                <div>
                    <span class="jqs-address2">${address.address2?default('')}</span>
                </div>
                <div>
                    <span class="jqs-city">${address.city?default('')}</span>, <span class="jqs-state">${address.state?default('')}</span> <span class="jqs-zip">${address.zip?default('')}</span>
                </div>
                <div>
                    <span class="jqs-country">${address.country?default('')}</span>
                </div>
                <div>
					<span>
						<a class="jqs-address-data" href="#" data-reveal-id="edit-address" data-address="${address.jsonData}">edit</a>
					</span>
					<span class="margin-left-xs">
						<a class="jqs-delete" href="javascript:void(0)" data-contact-mech-id="${address.customerAddressId}">delete</a>
					</span>
                </div>
            </div>
        </#list>
        </div>

    </div>
</div>
<script>
    var addAddressURL = '<@ofbizUrl>/saveAddressingAddress</@ofbizUrl>';
    var removeAddressURL = '<@ofbizUrl>/deleteAddressingAddress</@ofbizUrl>';
</script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/addresses.js</@ofbizContentUrl>"></script>
