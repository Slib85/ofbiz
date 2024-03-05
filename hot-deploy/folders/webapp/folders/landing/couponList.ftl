<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/services/couponList.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/folders/product/couponList.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<div class="couponList content-body foldersCouponList">
    <#if requestParameters.coupon_page?has_content && requestParameters.coupon_page == "new">
        <div>
            <div class="section">
                <a href="<@ofbizUrl>/applyPromo?productPromoCodeId=MARCH10&amp;saveResponse=true</@ofbizUrl>">
                    <div class="name freeShipping margin-top-xxs march10">
                        <span>10% OFF</span>
                        <p>Your First Order</p>
                    </div>
                    <div class="discount marginBottom10">
                         Offer expires April 11, 2019.<br />
                         Cannot be combined with other offers.
                    </div>
                    <div class="code">
                        Use Code <span>MARCH10</span>
                    </div>
                    <div class="foldersButton paddingLeft10 paddingRight10 noMargin marginTop10 foldersCouponButton">Apply Coupon <i class="fa fa-caret-right"></i></div>
                </a>
            </div>
        </div>
    <#else>
        <div>
            <div class="section signUp" onclick="_ltk.Popup.openManualByName('Popup2021-Folders-Manual');">
                <div>
                    <div class="name signUpText margin-top-xxs">
                        Subscribe &amp; Save $10<br/>
                        <p>Sign up for our mailing list to get Special Offers and New Arrivals</p>
                    </div>
                    <div class="discount">
                         * Discount on orders of $50 or more and applied before shipping and applicable taxes
                    </div>
                    <div class="foldersButton paddingLeft10 paddingRight10 noMargin marginTop10 foldersCouponButton">Sign Up <i class="fa fa-caret-right"></i></div>
                </div>
            </div>
        </div>
        <div>
            <div class="section folCoupBlock">
                <a href="<@ofbizUrl>/search?af=new:y&w=*&sort=createdstamp+desc</@ofbizUrl>">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-new-arrivals-deal-block-banner2?fmt=png-alpha</@ofbizScene7Url>" alt="">
                </a>
            </div>
        </div>
        <div>
            <div class="section">
                <a href="<@ofbizUrl>/applyPromo?productPromoCodeId=FREE${globalContext.freeShippingAmount?default("250")}&amp;saveResponse=true</@ofbizUrl>">
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
                    <div class="foldersButton paddingLeft10 paddingRight10 noMargin marginTop10 foldersCouponButton">Apply Coupon <i class="fa fa-caret-right"></i></div>
                </a>
            </div>
        </div>
        <#--  <div>
            <div class="section signUp">
                <a href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>">
                    <div class="name marchSale" data-bnreveal="marchSale2019">
                        <strong>20% OFF</strong><br/> 
                        <p>All Custom Orders</p>
                    </div>
                    <div class="discount">
                        Prices as Marked
                    </div>
                    <div class="foldersButton paddingLeft10 paddingRight10 noMargin marginTop10 foldersCouponButton">Shop Now <i class="fa fa-caret-right"></i></div>
                </a>
            </div>
        </div>  -->
        <#--<div>
            <div class="section signUp">
                <a href="<@ofbizUrl>/blankCategory?category_id=9x12_FOLDERS</@ofbizUrl>">
                    <div class="name marchSale" data-bnreveal="marchSale2019">
                        <strong>10% OFF</strong><br/> 
                        <p>All Blank Products</p>
                    </div>
                    <div class="discount">
                        Prices as Marked
                    </div>
                    <div class="foldersButton paddingLeft10 paddingRight10 noMargin marginTop10 foldersCouponButton">Shop Now <i class="fa fa-caret-right"></i></div>
                </a>
            </div>
        </div>-->
        <#assign usedPromo = "" />
        <#list couponCodes as couponCode>
        <#if usedPromo != couponCode.productPromoId>
        <div>
            <div class="section">
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
                        <div class="foldersButton paddingLeft10 paddingRight10 noMargin marginTop10 foldersCouponButton">Apply Coupon <i class="fa fa-caret-right"></i></div>
                    </#if>
                </a>
            </div>
        </div>
        </#if>
        <#assign usedPromo = couponCode.productPromoId />
        </#list>
        <div>
            <div class="section">
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
                    <div class="foldersButton paddingLeft10 paddingRight10 noMargin marginTop10 foldersCouponButton">Shop Now <i class="fa fa-caret-right"></i></div>
                </a>
            </div>
        </div>
    </#if>    
</div>