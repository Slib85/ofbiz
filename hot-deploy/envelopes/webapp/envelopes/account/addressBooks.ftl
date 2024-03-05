<link href="/html/css/account/account.css" rel="stylesheet" />
<link href="/html/css/account/addresses.css" rel="stylesheet" />

<div class="content account">
    <div class="content-breadcrumbs">
        <a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Address Books
    </div>
    <div class="container addresses padding-xs">
        <div id="address-message" data-alert class="alert-box success radius margin-top-xxs hidden">Address Book has been successfully created.</div>
        <div id="global-address-error" data-alert class="alert-box alert radius margin-top-xxs hidden">An error occurred while deleting the Address Book.</div>
        <div class="row no-margin padding-left-xxs padding-right-xxs">
            <#--<div class="add-new-address large-3 columns no-padding">
                <div class="button-regular button-cta padding-left-xxs padding-right-xxs" data-reveal-id="add-address">Create New Address Book</div>
            </div>-->
            <div class="columns large-9 no-padding">
            <#--<div class="filter-by">
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
                </div>-->
            </div>
        </div>
        <#--<div id="add-address" class="jqs-parent reveal-modal no-padding" data-reveal>
        ${request.setAttribute("mode", "add")}
            ${screens.render("component://envelopes/widget/AccountScreens.xml#addressBook")}
        </div>

        <div id="edit-address" class="jqs-parent reveal-modal no-padding" data-reveal>
        ${request.setAttribute("mode", "edit")}
            ${screens.render("component://envelopes/widget/AccountScreens.xml#addressBook")}
        </div>-->
        <div id="jqs-address-list" class="row address-list">
        <#list addressBooks as addressBook>
            <div id="addressBook-${addressBook.variableDataGroupId}" class="jqs-address padding-xs margin-right-xxs margin-top-xxs left">
                <div>
                    <span class="jqs-contact-mech-purpose-type"><a href="<@ofbizUrl>/addressBook</@ofbizUrl>?variableDataGroupId=${addressBook.variableDataGroupId}">${addressBook.name}</a></span>
                </div>
                <div>
                    <span class="jqs-first-name">Created: ${addressBook.createdStamp?string.full}</span>
                </div>
                <div>
                    <span class="jqs-company">${addressBook.variableDataCount} Addresses</span>
                </div>
                <#--<div>
					<span>
						<a class="jqs-address-data" href="#" data-reveal-id="edit-address" data-address="">edit</a>
					</span>
					<span class="margin-left-xs">
						<a class="jqs-delete" href="javascript:void(0)" data-contact-mech-id="">delete</a>
					</span>
                </div>-->
            </div>
        </#list>
        </div>
    <#--<#if addresses?size == 0 >
        <div class="jqs-address padding-xs margin-right-xxs margin-top-xxs left">
            <div>
                <span class="jqs-contact-mech-purpose-type"></span>
            </div>
            <div>
                <span class="jqs-first-name"></span>
            </div>
            <div>
                <span class="jqs-company"></span>
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
    </#if>-->
    </div>
</div>
<script>
    var addAddressURL = '<@ofbizUrl>/saveAddress</@ofbizUrl>';
    var removeAddressURL = '<@ofbizUrl>/discAddress</@ofbizUrl>';
</script>
<script type="text/javascript" src="/html/js/account/addresses.js"></script>
