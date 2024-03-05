<div class="foldersRow">
    <h5 class="noPaddingTop" style="line-height: 32px;">Quantity &amp; Pricing</h5>
    <div class="foldersButton noMargin padding10 pullRight ftc-blue" bns-emailthisquote data-bnreveal="emailThisQuote" style="background-color: #ffffff; border: 1px solid #f5a83f; font-size: 12px;">Email This Quote <i class="fa fa-envelope marginLeft5" style="font-size: 14px;"></i></div>
</div>
<div>
    <div id="quantityPriceSelection" class="selectListParent sidebarToggle jqs-sidebarToggle selectListSmall quantitySelection" data-sidebar-name="sidebar-quantityList" selection-selectListName="quantitySelection" data-ignorecaret="">
    <#list product.getPrices().keySet() as quantities>
        <#if quantities_index == 0>
            <div class="foldersTabularRow">
                <div><span class="selQty">${quantities?string[",##0"]} Qty</span></div>
                <div class="textRight"><span class="selPrice"><strong class="priceDisplay">$${product.getPrices().get(quantities).price?string[",##0.##"]}</strong></span></div>
            </div>
            <#break>
        </#if>
    </#list>
    </div>
</div>

<div id="sidebar-quantityList" class="sidebarPanel quantityList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Quantity &amp; Pricing</h4>
            <div class="customQuantity row <#if product.hasCustomQty()?c == "false">hidden</#if>">
                <div class="customQuantityRow">
                    <div bns-quantityerror class="qlError hidden">Error</div>
                    <div class="qtyInput">
                        <span class="tablet-desktop-only-inline-block ftc-blue">Custom Quantity:</span> <input bns-textinput class="customQty" type="text" placeholder="Enter Your Qty." value="" name="customQuantity" />
                    </div>
                </div>
            </div>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="selectListContainer jqs-pricelist jqs-scrollable colorTextureBody">
        <div bns-quantitylist class="colorTextureBodyInner" style="padding: 0;">
        <#list product.getPrices().keySet() as quantities>
            <div class="selectListItem selectList qpsListItems" selection-selected="<#if quantities_index == 0>true<#else>false</#if>" bns-selection="" selection-target="quantitySelection" selection-name="quantity" selection-value="${quantities?string[",##0"]}">
                <div class="foldersTabularRow">
                    <div>
                        <span selection-removeonselect="" class="selectCheckbox"></span><span class="selQty">${quantities?string[",##0"]} Qty</span>
                    </div>
                    <div class="textRight">
                        <span class="selPrice"><strong class="priceDisplay">$${product.getPrices().get(quantities).price?string[",##0.##"]}</strong></span>
                    </div>
                </div>
            </div>
        </#list>
        </div>
    </div>
</div>

<div id="emailThisQuote" class="bnRevealContainer">
    <div class="bnRevealHeader fbc-blue">
        <h3>Email This Quote</h3>
        <i class="fa fa-times jqs-bnRevealClose"></i>
    </div>
    <form name="emailThisQuote" class="padding10 foldersRow" data-bigNameValidateForm="emailThisQuote">
        <p class="ftc-blue">Please enter your email address below to receive a copy of this quote.</p>
        <input type="text" name="quoteEmailAddress" placeholder="Enter Email Address" data-bigNameValidate="quoteEmailAddress" data-bigNameValidateType="required email" data-bigNameValidateErrorTitle="Email" />
        <div class="foldersButton buttonGreen marginTop10 noMarginBottom padding10 pullRight" bns-submitemailthisquote data-bigNameValidateSubmit="emailThisQuote" data-bigNameValidateAction="emailThisQuoteSubmission">Submit</div>
    </form>
    <div bns-emailthisquoteemailsubmissiontext class="hidden padding10">
        Your quote has been emailed to <span></span>.
    </div>
</div>