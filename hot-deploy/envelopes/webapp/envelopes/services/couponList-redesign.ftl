<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/services/couponList-redesign.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<div class="couponList content-body">
    <div>
        <div class="section signUp round-btn glow">
            <div class="bnc_ribbon"><span>POPULAR</span></div>
            <div onclick="_ltk.Popup.openManualByName('Popup2021-Envelopes Holiday-Manual');">
                <div class="name discountProgram emailSignUp">
                    <span>Subscribe &amp; Save $10<br /></span>
                    Get special offers, exclusive new arrivals &amp; more when you sign up for our mailing list
                </div>
                <div class="discount margin-bottom-xxs subscribeHeight">
                    * Discount on orders of $50 or more and applied before shipping and applicable taxes
                </div>
                <div class="button button-cta padding-left-xs padding-right-xs no-margin margin-top-xxs round-btn navyblue-bckgrd">Sign Up <i class="fa fa-caret-right"></i></div>
            </div>
        </div>
    </div>
    <div>
        <div class="section customOrange round-btn glow">
            <div class="bnc_ribbon"><span>TRENDING</span></div>
            <a href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>">
                <div class="name">
                    <span>Up To<br />75% Off</span>
                    All Clearance
                </div>
                <div class="discount">
                    Prices As Marked<br /><br /><br />
                </div>
                <div class="code margin-top-xxs">
                    No Code Required
                </div>
                <div class="button button-cta padding-left-xs padding-right-xs no-margin margin-top-xxs  round-btn">Shop Now <i class="fa fa-caret-right"></i></div>
            </a>
        </div>
    </div>
    <div>
        <div class="section round-btn glow">
            <div class="bnc_ribbon"><span>TRENDING</span></div>
            <a id="applyCoupon" href="<@ofbizUrl>/applyPromo?productPromoCodeId=FREE${globalContext.freeShippingAmount?default("299")}&amp;saveResponse=true</@ofbizUrl>">
                <div class="name freeShipping margin-top-xxs">
                    <span>Free</span> Shipping
                    <p>On All Orders of $${globalContext.freeShippingAmount?default("299")} or more</p>
                </div>
                <div class="discount">
                    Discount Applied Before<br />
                    Shipping and Applicable Taxes.
                </div>
                <div class="code">
                    Use Code <span>FREE${globalContext.freeShippingAmount?default("299")}</span>
                </div>
                <div class="button button-cta padding-left-xs padding-right-xs no-margin margin-top-xxs round-btn navyblue-bckgrd">Apply Coupon <i class="fa fa-caret-right"></i></div>
            </a>
        </div>
    </div>
    <#assign usedPromo = "" />
    <#list couponCodes as couponCode>
        <#if usedPromo != couponCode.productPromoId>
            <div>
                <div class="section round-btn glow">
                    <div class="bnc_ribbon"><span>LIMITED TIME!</span></div>
                    <a id="applyCoupon" <#if couponCode.expired?exists && couponCode.expired == "Y">href="#" onclick="return false;" style="cursor: default;"<#else>href="<@ofbizUrl>/applyPromo</@ofbizUrl>?productPromoCodeId=${couponCode.productPromoCodeId?if_exists}&amp;saveResponse=true"</#if>>
                        <div class="name">
                            <#if couponCode.productPromoCodeId?exists && couponCode.productPromoCodeId == "BF2018"><span style="font-size: 24px; line-height: 26px;">Black Friday Deal</span></#if>
                            <#if couponCode.productPromoCodeId?exists && couponCode.productPromoCodeId == "CYBER2018"><span style="font-size: 24px; line-height: 26px;">Today Only!<br />Cyber Monday</span></#if>
                            <#if couponCode.productPromoCodeId?exists && couponCode.productPromoCodeId == "CYBERX18"><span style="font-size: 24px; line-height: 26px;">Cyber Week Extended</span></#if>
                            <#if couponCode.productPromoCodeId?exists && couponCode.productPromoCodeId == "CYBER10"><span style="font-size: 24px; line-height: 26px;">Cyber Deal</span></#if>
                            ${couponCode.promoText?if_exists?replace("(^(?:\\&.*?\\;|\\$|)[\\d\\.]+(?:\\&.*?\\;|\\%|)\\soff)", "<span>$1</span>", "ri")}
                            <#if couponCode.operatorEnumId?exists && couponCode.operatorEnumId == "PPC_GTE">
                                Orders Over $${couponCode.condValue?default(0)?number?string}
                            <#elseif couponCode.operatorEnumId?exists && couponCode.operatorEnumId == "PPC_GT">
                                <#if couponCode.condValue?default(0)?number?string == "0">
                                <#--On Any Order-->
                                <#else>
                                    Orders Over $${couponCode.condValue?default(0)?number?string}
                                </#if>
                            <#elseif couponCode.operatorEnumId?exists && couponCode.operatorEnumId == "PPC_LTE">
                                Orders Under $${couponCode.condValue?default(0)?number?string}
                            <#elseif couponCode.operatorEnumId?exists && couponCode.operatorEnumId == "PPC_LT">
                                Orders Under $${couponCode.condValue?default(0)?number?string}
                            </#if>
                        </div>
                        <div class="discount">
                            Discount Applied Before<br />
                            Shipping and Applicable Taxes.<br /><br />
                        </div>
                        <div class="code margin-top-xxs">
                            Use Code <span>${couponCode.productPromoCodeId?if_exists}</span><#if couponCode.expired?exists && couponCode.expired == "Y"><br />(expired)</#if>
                        </div>
                        <#if couponCode.expired?exists && couponCode.expired == "Y"><#else>
                            <div class="button button-cta padding-left-xs padding-right-xs no-margin margin-top-xxs round-btn navyblue-bckgrd">Apply Coupon <i class="fa fa-caret-right"></i></div>
                        </#if>
                    </a>
                </div>
            </div>
        </#if>
        <#assign usedPromo = couponCode.productPromoId />
    </#list>
    <div>
        <div class="section envCoupBlock round-btn glow">
            <a href="<@ofbizUrl>/new-arrivals</@ofbizUrl>">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-arrivals-deal-block-banner?fmt=png-alpha</@ofbizScene7Url>" alt="">
            </a>
        </div>
    </div>
    <div>
        <div class="section round-btn glow">
            <div class="bnc_ribbon"><span>PREMIUM</span></div>
            <a href="<@ofbizUrl>/tradePro</@ofbizUrl>">
                <div class="name discountProgram">
                    <span>Trade Discount Program<br /></span>
                    Save 10% On Every Order <br />
                    and Up to 25% w/ Loyalty Points
                </div>
                <div class="discount">
                    * Discount applied upon approval of application ( 1 business day turnaround )<br /><br /><br />
                </div>
                <div class="code margin-top-xxs">
                    No Code Required
                </div>
                <div class="button button-cta padding-left-xs padding-right-xs no-margin margin-top-xxs round-btn">Apply Now <i class="fa fa-caret-right"></i></div>
            </a>
        </div>
    </div>
    <div>
        <div class="section foldersCoupon round-btn glow">
            <div class="bnc_ribbon"><span>FLASH SALE!</span></div>
            <a href="https://www.folders.com" target="_blank">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foldersLogoCoupons?fmt=png-alpha&amp;wid=117&amp;ts=2</@ofbizScene7Url>" alt="Folders.com Logo">
                <div class="foldersHeadline">
                    <span>$20 OFF + FREE SHIPPING<br /></span>
                    All Custom Folders.com Orders
                </div>
                <div class="discount margin-top-xxs">
                    * Discount Only Available on Folders.com and is Applied Before Shipping and Applicable Taxes<br /><br /><br />
                </div>
                <div class="code margin-top-xxs">
                    USE CODE: CUSTOM20
                </div>
                <div class="button foldersButton padding-left-xs padding-right-xs no-margin margin-top-xxs round-btn">Shop Folders.com <i class="fa fa-caret-right"></i></div>
            </a>
        </div>
    </div>
</div>