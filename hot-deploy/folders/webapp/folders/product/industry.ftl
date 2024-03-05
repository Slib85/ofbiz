<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/industry/industry.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" type="text/css" />
<div class="foldersContainer foldersContainerLimiter industry noPadding">
    <div class="foldersHeaderBanner foldersRow textRight">
        <div class="textLeft padding20">
           <h1>${industryInfo.h1?if_exists}</h1>
            <p class="tabletDesktop-inlineBlock marginTop5">
                ${industryInfo.bannerTextDesktop?if_exists}
            </p>
            <div class="foldersTabularRow freeShippingHeader marginTop20">
                <i class="fa fa-truck"></i><p> Free Shipping Available on all Custom Orders!</p>
            </div>
        </div>
        <img class="tabletDesktop-inlineBlock" src="<@ofbizContentUrl>/html/img/folders/product/industry/desktop/${industryInfo.imageName?if_exists}.jpg</@ofbizContentUrl>" alt="${industryInfo.h1?if_exists}" />
        <img class="mobile-inlineBlock" src="<@ofbizContentUrl>/html/img/folders/product/industry/mobile/${industryInfo.imageName?if_exists}.jpg</@ofbizContentUrl>" alt="${industryInfo.h1?if_exists}" />
    </div>
    <div class="mobile-block textCenter freeShippingHeaderMobile">
        <p><i class="fa fa-truck"></i> Free Shipping Available on all Custom Orders!</p>
    </div>
    <div class="padding20">
        <h2>
            Custom Folder Top Sellers
        </h2>
        <div class="folderList singleProductLine foldersFlexRow flexCenter marginTop10">
            <a href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C</@ofbizUrl>" class="marginTop10 marginLeft5 marginRight5 padding10">
                <div class="topSellers">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/SF_101_Up_8?fmt=png-alpha&amp;wid=60&amp;hei=90</@ofbizScene7Url>" alt="Placeholder Folder" />
                    <p>9x12 Standard</p>
                    <p>Two Pocket Folder</p>
                    <p class="printStyle">Foil Stamped</p>
                    <p class="fs">Free Shipping</p>
                    <div class=" customButton foldersButton">Customize</div>
                </div>
            </a>
            <a href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C</@ofbizUrl>" class="marginTop10 marginLeft5 marginRight5 padding10">
                <div class="topSellers">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/LF-1401-A-STANDING?fmt=png-alpha&amp;wid=60&amp;hei=90</@ofbizScene7Url>" alt="Placeholder Folder" />
                    <p>9x12 Standard</p>
                    <p>Two Pocket Folder</p>
                    <p class="printStyle">Full Color Printing</p>
                    <p class="fs">Free Shipping</p>
                    <div class=" customButton foldersButton">Customize</div>
                </div>
            </a>
            <a href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C</@ofbizUrl>" class="marginTop10 marginLeft5 marginRight5 padding10">
                <div class="topSellers">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/FIN_SPOT_1?fmt=png-alpha&amp;wid=60&amp;hei=90</@ofbizScene7Url>" alt="Placeholder Folder" />
                    <p>9x12 Standard</p>
                    <p>Two Pocket Folder</p>
                    <p class="printStyle">Spot Color Printing</p>
                    <p class="fs">Free Shipping</p>
                    <div class=" customButton foldersButton">Customize</div>
                </div>
            </a>
            <a href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C</@ofbizUrl>" class="marginTop10 marginLeft5 marginRight5 padding10">
                <div class="topSellers">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/912-533-C_EMB_STANDARD?fmt=png-alpha&amp;wid=60&amp;hei=90</@ofbizScene7Url>" alt="Placeholder Folder" />
                    <p>9x12 Standard</p>
                    <p>Two Pocket Folder</p>
                    <p class="printStyle">Blind Embossing</p>
                    <p class="fs">Free Shipping</p>
                    <div class=" customButton foldersButton">Customize</div>
                </div>
            </a>
        </div>

        <h2 class="marginTop20">
            ${industryInfo.customerExamplesHeader?if_exists}
        </h2>
        <p>
            ${industryInfo.customerExamplesText?if_exists}
        </p>
        <div class="folderList foldersFlexRow flexCenter marginTop10 marginBottom10 topSellerBackground">
            <#if industryInfo.productList?has_content >
                <#list industryInfo.productList as product>
                    <a href="<@ofbizUrl>/${product.link}</@ofbizUrl>" class="marginTop10 marginLeft5 marginRight5 paddingTop10 paddingBottom10 paddingLeft20 paddingRight20">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.id}?fmt=png-alpha&amp;wid=180&amp;hei=185</@ofbizScene7Url>" alt="${product.id}"/>
                            <div class="textBold folderName">
                                <#if product_index == 0>
                                    <div class="customButton foldersButton">Foil Stamped</div>
                                <#elseif product_index == 1>
                                    <div class="customButton foldersButton">Full Color Printing</div>
                                <#elseif product_index == 2>
                                    <div class="customButton foldersButton">Spot Color Printing</div>
                                <#elseif product_index == 3>
                                    <div class="customButton foldersButton">Embossed</div>
                                <#else>
                                    <div class="customButton foldersButton">Other Custom Folders</div>
                                </#if>
                            </div> 
                        </div>
                    </a> 
                </#list>
            </#if>
        </div>
        <#--
            <div class="textCenter marginTop20 paddingBottom20 otherIndustryReviews">
                <h3 class="padding10 fbc-blue">
                   What Are Other Industry People Saying about Folders.com?
                </h3>
                <span class="rating-5 marginTop10"></span>
                <p class="reviewText ftc-darkGray marginTop10">
                    "I will use these envelopes to go with a hand crafted greeting card created for a state historic site.
                    The envelope is of excellent quality and complements my card nicely. I also greatly appreciated the
                    wonderful customer service I received. Even though I'm not purchasing large quantities, I received large
                    assistance to resolve a minor problem I had in ordering. Thank you!"
                </p>
                <div class="reviewText ftc-darkGray marginTop10 textBold">By Brian Converse - <span class="ftc-lightBlue"><i class="fa fa-check"></i> Verified Buyer</span></div>
                <div class="reviewText ftc-darkGray">(Industry: Other) on Sep 29, 2016</div>
            </div>
        -->
         <div class="customerReview">
            <span class="rating-5"></span>
            <h2>${industryInfo.customerReview?if_exists}</h2>
            <div class="customerImage">
                <p>${industryInfo.customerReviewName?if_exists}</p>
                <p>${industryInfo.customerReviewCompany?if_exists}</p>
            </div>
        </div>
        <h2 class="marginTop20">
            ${industryInfo.customProductHeader?if_exists}
        </h2>

        <p>
            ${industryInfo.browseMoreCustomCopy?if_exists}
        </p>
       
        <div class="folderList foldersFlexRow flexCenter marginTop10">
           
            <#if browseMoreCustomList?has_content>
                <#list browseMoreCustomList as custom>
                    <a href="<@ofbizUrl>/${custom.link}</@ofbizUrl>" class="marginTop10 marginLeft5 marginRight5 paddingTop10 paddingBottom10 paddingLeft20 paddingRight20">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${custom.img?if_exists}</@ofbizScene7Url>" alt="${industryInfo.browseMoreCustom?if_exists}"/>
                            <div class="textBold folderName">
                               ${custom.text?if_exists}
                            </div> 
                        </div>
                    </a>
                </#list>
            </#if>  
        </div>

        <h2 class="marginTop20">
            ${industryInfo.blankProductHeader?if_exists}
        </h2>
        <p>
            ${industryInfo.browseBlankCopy?if_exists}
        </p>
        <div class="folderList foldersFlexRow flexCenter marginTop10">
           
            <#if browseMoreBlankList?has_content>
                <#list browseMoreBlankList as blank>
                    <a href="<@ofbizUrl>/${blank.link}</@ofbizUrl>" class="marginTop10 marginLeft5 marginRight5 paddingTop10 paddingBottom10 paddingLeft20 paddingRight20">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${blank.img?if_exists}</@ofbizScene7Url>" alt="${industryInfo.browseMoreCustom?if_exists}"/>
                            <div class="textBold folderName">
                               ${blank.text?if_exists}
                            </div> 
                        </div>
                    </a>
                </#list>
            </#if>
        </div>
    </div>
</div>
<#if seoH3?has_content>
    <div class="seoTextContainer">
        <h3 class="searchSEOTitle">${seoH3?if_exists}</h3>
        <#if footerDesc?has_content><p class="searchSEOText">${footerDesc?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}</p></#if>
    </div>
</#if>