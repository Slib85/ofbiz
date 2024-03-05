<#--
NON-WORKING / WORKING SECTIONS

NON-WORKING

- 'In Stock' doesn't check stock
- 'View Inspiration Gallery' link, also don't have the right image for this
- Related Products Section
- Product Specs Image and PDF Download Link
- Share link
- Request a sample doesn't seem to update cart. Right now all possible samples are showing (data-has-sample=true) to make the popup visible. Maybe has something to do with no samples being available when that isn't explicitly set to true.

WORKING

- Images and Quantity/Pricing update when new color texture is selected
- Style description below select dropdowns
- Customer Reviews.
- Style description next to specs (is this supposed to be the same copy?)
- Specs in the righthand table
-->
<#if product?exists || foldersProduct?exists>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.7.1/slick.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/util/selectList.css</@ofbizContentUrl>" />
    <link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/product/blankProduct.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" />

    <#assign imgFmtQltyGry = 'fmt=jpeg&amp;qlt=50&amp;bgc=f1f1f1' />
    <#assign imgFmtQltyLtGry = 'fmt=jpeg&amp;qlt=50&amp;bgc=f9f9f9' />
    <#assign imgFmtQltyLtGry2 = 'fmt=jpeg&amp;qlt=50&amp;bgc=f1f1f1' />
    <#assign imgFmtQltyWht = 'fmt=jpeg&amp;qlt=50&amp;bgc=ffffff' />

    <#if isMobile == 'false'>
        <#assign imgFmtQltyGry = 'fmt=jpeg&amp;qlt=75&amp;bgc=f1f1f1' />
        <#assign imgFmtQltyLtGry = 'fmt=jpeg&amp;qlt=75&amp;bgc=f9f9f9' />
        <#assign imgFmtQltyLtGry2 = 'fmt=jpeg&amp;qlt=75&amp;bgc=f1f1f1' />
        <#assign imgFmtQltyWht = 'fmt=jpeg&amp;qlt=75&amp;bgc=ffffff' />
    </#if>

    <#if product?exists>
        <#assign variantData = Static["com.envelopes.product.ProductHelper"].getProductColors(delegator, product.getProduct()) />
        <#assign variantColors = variantData.get("colors") />
        <#assign filters = variantData.get("filters").get("By Color").get("choices") />
        <#assign collections = variantData.get("filters").get("By Collection").get("choices") />
    </#if>

    <div class="foldersContainer blankProduct">
        <div class="blankProductHeader">
            <div class="foldersRow">
                <div>
                    <h1><#if foldersProduct?has_content && foldersProduct["Title"]?exists>${foldersProduct["Title"]?if_exists}<#else>${product.getName()?if_exists}</#if></h1>
                    <#if product.getReviews()?exists>
                    <p>
                        <span class="starRating-${(product.getRating())?replace(".", "_")}">
                            <#if product.getRating() == '0_0'>
                                <a data-bnReveal="productReview" href="#">Be the first to write a review.</a>
                            <#else>
                                <a href="#reviews">(${product.getReviews().reviews?size}) Reviews</a>
                            </#if>
                        </span> | <span class="inStock">In Stock</span> <span class="inStock">${variantColors?size} Colors and Stocks</span> <span class="inStock">Standard Card Slit Included</span></p>
                    </#if>
                </div>
            </div>
        </div>

        <#assign productAssets = product.getProductAssets("plain", test) />

        <div class="blankProductBody">
            <div class="foldersTabularRow marginTop20 productContent">
                <div class="textCenter">
                    <div class="foldersTabularRow productImages">
                        <div>
                            <div>
                                <div style="width:105px; float:left;">
                                    <div class="productThumbs jqs-prodassets imageSelection">
                                        <div class="productThumb active">
                                            <div style="background-image: url(<#if designId?has_content>//texel.envelopes.com/getBasicImage?id=${designId}<#if templateType?exists>&amp;templateType=${templateType}</#if><#if hex?exists>&amp;hex=${hex}</#if>&amp;wid=95&amp;fmt=png-alpha<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}?wid=95</#if>&amp;fmt=png-alpha);"></div>
                                        </div>
                                        <#assign productAssets = product.getProductAssets("plain", test) />
                                        <#list productAssets as asset>
                                        <div class="productThumb">
                                            <div style="background-image: url(//actionenvelope.scene7.com/is/image/ActionEnvelope/${asset.assetName}?wid=95&amp;fmt=png-alpha);"></div>
                                        </div>
                                        </#list>
                                        <#if productId?exists && (basicAndPremiumMaterialList?has_content || nonBasicAndPremiumMaterialList?has_content)>
                                        <div class="productThumb" data-schematicId="${productId?if_exists}">
                                            <div style="background-image: url(//actionenvelope.scene7.com/is/image/ActionEnvelope/${productId?if_exists}?fmt=png-alpha&amp;ts=20170502);"></div>
                                        </div>
                                        </#if>
                                    </div>
                                </div>
                                <div class="product-images jqs-prodslides">
                                    <div class="product-images-single">
                                        <a>
                                            <img property="image" class="jqs-imagehero product-slide-image" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="<#if designId?has_content>//texel.envelopes.com/getBasicImage?id=${designId}<#if templateType?exists>&amp;templateType=${templateType}</#if><#if hex?exists>&amp;hex=${hex}</#if>&amp;hei=500&amp;wid=622&amp;fmt=png-alpha<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}?hei=500&amp;wid=622&amp;fmt=png-alpha</#if>" />
                                        </a>
                                    </div>
                                    <#list productAssets as asset>
                                    <div class="product-images-single removable">
                                        <a>
                                            <img property="image" class="product-slide-image" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${asset.assetName}?hei=750&amp;fit=stretch,1&amp;fmt=png-alpha" />
                                        </a>
                                    </div>
                                    </#list>
                                <#if productId?exists && (basicAndPremiumMaterialList?has_content || nonBasicAndPremiumMaterialList?has_content)>
                                        <div class="product-images-single removable">
                                            <a>
                                                <img property="image" class="product-slide-image" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${productId?if_exists}?hei=375&amp;fit=stretch,1" alt="${productId?if_exists}"/>
                                            </a>
                                        </div>
                                    </#if>
                                </div>
                            </div>
                            <#--
                            <p class="textRight marginTop20 belowProdIcons">
                                <span class="requestSample" data-bnReveal="orderSamples">Request a Sample</span>
                                <span class="viewInspiration">View Inspiration Gallery</span>
                            </p>
                            -->
                        </div>
                    </div>
                </div>
                <div class="productSidebar">
                    <div>
                        <div class="productSidebarSection">
                            <h5 class="colorTexture"><img src="//cdn.envelopemedia.com/html/img/icon/color_wheel.png" alt="Color Wheel" /> Paper Color &amp; Texture (${variantColors?size} Colors)</h5>
                            <#if product.getColor()?exists>
                                <#assign colorSwatch = product.getColor()?replace("[^\\w\\s]", "", "r")?replace("\\s+", "_", "r") >
                                <#assign colorSwatch = colorSwatch?replace('_w_Gold_Foil', '') >
                                <#assign colorSwatch = colorSwatch?replace('_with_Gold_Foil', '') >
                                <#assign colorSwatch = colorSwatch?replace('_w_Silver_Foil', '') >
                                <#assign colorSwatch = colorSwatch?replace('_with_Silver_Foil', '') >
                            </#if>
                            <div class="selectListParent sidebarToggle colorSelection jqs-scrollToSelected colorTextureSelection" data-dropdown-target="dropdown-colorList" data-selectlistname="colorSelection" data-selectlistaction="updateSelectListSelection" data-hex="${product.getHex()?if_exists}" style="background-image: url(//actionenvelope.scene7.com/is/image/ActionEnvelope/PC-${colorSwatch?if_exists});">
                                <p>
                                    <span class="selectCheckbox"></span>
                                    <strong>${product.getColor()?if_exists}</strong>
                                </p>
                            </div>
                            <div class="foldersRow">
                                <h5 class="marginTop10">Quantity &amp; Pricing</h5>
                                <#--<span class="requestSample pullRight" data-bnReveal="orderSamples">Request a Sample</span>-->
                            </div>
                            <#list product.getPrices().keySet() as quantities>
                            <#if quantities_index == 0>
                                <#assign smallestQuantity = quantities />
                                <#assign lowestPrice = product.getPrices().get(quantities).price />
                                <div id="quantityPriceSelection" class="selectListParent sidebarToggle selectListSmall quantitySelection jqs-quantityTest" data-dropdown-target="dropdown-quantityList" data-selectListName="quantitySelection" data-selectListAction="updateSelectListSelection">
                                    <p>
                                    <span class="jqs-selQty selQty">${quantities?string[",##0"]} Qty
                                        <span class="jqs-originalPriceDisplay originalPriceDisplay margin-left-xxs<#if product.percentSavings()?default("0") == 0> hidden</#if>"><#if product.percentSavings()?default("0") gt 0 && product.getOriginalPrices().get(quantities).price?exists><strike>$${product.getOriginalPrices().get(quantities).price?string[",##0.##"]}</strike></#if></span>
                                    </span>
                                    <span class="selPrice">
                                        <span class="jqs-savingsDisplay savingsDisplay margin-left-xxs<#if product.percentSavings()?default("0") == 0> hidden</#if>"><#if product.percentSavings() gt 0>Save ${product.percentSavings()}%</#if></span>
                                        <#if product.percentSavings()?default("0") == 0><span class="jqs-pricePerUnit pricePerUnitDisplay margin-left-xxs">${(product.getPrices().get(quantities).price?number / quantities?string["##0"]?number)?string.currency} ea.</span></#if>
                                        <strong class="jqs-selPrice priceDisplay">$${product.getPrices().get(quantities).price?string[",##0.##"]}</strong>
                                    </span>
                                    </p>
                                </div>
                                <#break>
                            </#if>
                            </#list>
                            <div class="foldersRow">
                                <div class="foldersButton buttonGreen jqs-addToCart addToCart marginTop10 noMarginBottom padding20 pullRight">Add to Cart</div>
                            </div>

                        </div>

                        <#if productId?exists>
                            <p class="textCenter marginTop5"><a href="<@ofbizUrl>/product/~product_id=${productId?if_exists}</@ofbizUrl>">This item is also available custom printed</a></p>
                        </#if>
                        <div class="marginTop20">
                            <h4>Style Description</h4>
                            <p class="jqs-longdesc">${product.getDescription()?if_exists}</p>
                        </div>
                    </div>
                    <div id="dropdown-colorList" class="drop-down sidebarPanel colorList jqs-sampleReadableList">
                        <div class="colorTextureHeading">
                            <div class="stickyTextureHeading">
                                <h4><i class="fa fa-caret-left"></i>Paper Color &amp; Texture</h4>
                                <div>
                                    <div class="foldersTabularRow">
                                        <div class="colorWheel">
                                            <div><div data-color-group="White" style="background: #FCFCFC;"></div></div>
                                            <div><div data-color-group="Silver" style="background: #c7c7c7;"></div></div>
                                            <div><div data-color-group="Gold" style="background: #FFBF06;"></div></div>
                                            <div><div data-color-group="Red" style="background: #C93B4B;"></div></div>
                                            <div><div data-color-group="Green" style="background: #61CA87;"></div></div>
                                            <div><div data-color-group="Brown" style="background: #614537;"></div></div>
                                            <div><div data-color-group="Blue" style="background: #6AC4E6;"></div></div>
                                            <div><div data-color-group="Black" style="background: #1F2025;"></div></div>
                                            <div><div data-color-group="Natural" style="background: #F2EFDE;"></div></div>
                                            <div><div data-color-group="Orange" style="background: #F46F46;"></div></div>
                                            <div><div data-color-group="Clear" style="background: #E2E2E4;"></div></div>
                                            <div><div data-color-group="Crystal" style="background: #FFFFFF;"></div></div>
                                            <div><div data-color-group="Pink" style="background: #FA6EC5;"></div></div>
                                            <div><div data-color-group="Ivory" style="background: #FFFFFF;"></div></div>
                                            <div><div data-color-group="Purple" style="background: #DB7BE6;"></div></div>
                                            <div><div data-color-group="Teal" style="background: #017276;"></div></div>
                                        </div>
                                    </div>
                                    <small class="colorFilterClear hidden"><a>Clear Filters</a></small>
                                </div>
                            </div>
                            <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
                        </div>
                        <div class="colorTextureBody">
                            <div class="colorTextureBodyInner" style="padding: 0;">
                            <#assign counter = 0 />
                            <#list variantColors as variant>
                                <div class="selectListItem jqs-selectListItem jqs-selectList <#if variant.get("productId") == product.getId()>slSelected</#if>" data-selectListName="colorSelection" data-percent-savings="${variant.percentSavings?default("0")}" data-color-groups="${variant.get("colorGroupClasses")?replace("jqs-","")}" data-collection-groups="${variant.get("collectionClasses")?replace("jqs-","")}" data-target="colorSelection" data-new="${variant.get("new")?c}" data-onsale="${variant.get("sale")?c}" data-rating="${variant.get("rating")}" data-product-brand="${variant.get("brand")?default("")}" data-printable="${variant.get("isPrintable")?c}" data-url="<@ofbizUrl>/product/~category_id=${variant.get("primaryProductCategoryId")?default("null")}/~product_id=${variant.get("productId")}</@ofbizUrl>" data-product-id="${variant.get("productId")}" data-hasPeelAndPress="<#if variant.sealingMethod?exists && variant.sealingMethod?contains("Peel") && variant.sealingMethod?contains("Press")>true<#else>false</#if>" data-has-white-ink="${variant.get("hasWhiteInk")?c}" data-has-sample="true" data-product-weight="${variant.get("weight")?if_exists}" data-product-name="${variant.get("name")}" data-product-color="${variant.get("desc")?if_exists}" data-hex="${variant.get("hex")?replace("#","")?replace("&x23;","")?replace("&35;","")}" data-min-color="${variant.get("minColors")?default(1)}" data-max-color="${variant.get("maxColors")?default(1)}" data-print-desc="${variant.get("printPrice")?default("")}">
                                    <p>
                                    <span class="selectCheckbox"></span>
                                    <strong>${variant.get("desc")?if_exists} <#if variant.get("clearance")?c == "true"><span class="clearanceRibbon">${variant.percentSavings?default("0")}% Off</span></#if><#if variant.get("sale")?c == "true"><span class="saleRibbon">${variant.percentSavings?default("0")}% Off</span></#if><#if variant.get("new")?c == "true"><span class="newRibbon">New</span></#if></strong>
                                    </p>
                                </div>
                            <#assign counter = counter + 1 />
                            </#list>
                            </div>
                        </div>
                    </div>
                    <div id="dropdown-quantityList" class="drop-down sidebarPanel quantityList jqs-scrollable">
                        <div class="colorTextureHeading">
                            <div class="stickyTextureHeading">
                                <h4><i class="fa fa-caret-left"></i>Quantity &amp; Pricing</h4>
                                <div class="customQuantity row">
                                    <div class="customQuantityRow">
                                        <div class="jqs-qlError qlError hidden">Error</div>
                                        <div class="qtyInput">
                                        <span class="tablet-desktop-only-inline-block jqs-quantityType ftc-blue">Custom Quantity:</span> <input class="jqs-number customQty" type="text" placeholder="Enter Your Qty." value="" name="CUSTOM" /><a class="foldersButton buttonGold">Apply</a>
                                        </div>
                                    </div>
                                    <div class="currentQuantity">
                                        <div class="selectListItem jqs-selectListItem jqs-selectList qpsListItems selectList slSelected" data-selectlistname="quantitySelection" data-qty="" data-price="">
                                            <div class="currentSelection">Current Selection:</div>
                                            <div>
                                                <span class="jqs-selQty selQty"></span><span class="selPrice"><span class="jqs-pricePerUnit pricePerUnitDisplay margin-left-xxs"></span><span style="margin-left: 20px;" class="jqs-selPrice priceDisplay margin-left-xxs"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
                        </div>
                        <div class="colorTextureBody">
                            <div class="colorTextureBodyInner" style="padding: 0;">
                                <div class="selectListContainer jqs-pricelist jqs-scrollable">
                                    <#list product.getPrices().keySet() as quantities>
                                    <#if !quantities_has_next>
                                        <#assign largestQuantity = quantities />
                                        <#assign highestPrice = product.getPrices().get(quantities).price />
                                    </#if>
                                    <div class="selectListItem jqs-selectListItem jqs-selectList selectList qpsListItems<#if quantities_index == 0> slSelected</#if>" data-selectListName="quantitySelection" data-qty="${quantities}" data-price="${product.getPrices().get(quantities).price?if_exists}" data-originalPrice="<#if product.percentSavings()?default("0") gt 0 && product.getOriginalPrices().get(quantities).price?exists>${product.getOriginalPrices().get(quantities).price}<#else>${product.getPrices().get(quantities).price}</#if>">
                                        <p>
                                        <span class="selQty">${quantities?string[",##0"]} Qty</span>
                                        <span class="selPrice">
                                            <span class="originalPriceDisplay margin-left-xxs<#if product.percentSavings()?default("0") == 0> hidden</#if>"><#if product.percentSavings()?default("0") gt 0 && product.getOriginalPrices().get(quantities).price?exists><strike>$${product.getOriginalPrices().get(quantities).price?string[",##0.##"]}</strike></#if></span>
                                            <span class="savingsDisplay margin-left-xxs<#if product.percentSavings()?default("0") == 0> hidden</#if>"><#if product.percentSavings()?exists && product.percentSavings() gt 0>Save ${product.percentSavings()}%</#if></span>
                                            <#if product.percentSavings()?default("0") == 0>
                                                <span class="jqs-pricePerUnit pricePerUnitDisplay margin-left-xxs">${(product.getPrices().get(quantities).price?number / quantities?string["##0"]?number)?string.currency} ea.</span>
                                            </#if>
                                            <strong class="priceDisplay">$${product.getPrices().get(quantities).price?string[",##0.##"]}</strong>
                                        </span>
                                        </p>
                                    </div>
                                    </#list>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <#--
            <div class="relatedProducts">
                <h3>Related Products:</h3>
                <div>
                    <div>
                        <div class="related-slider">
                        <#assign relatedSlides = 10>
                            <#list 1..relatedSlides as relatedSlide>
                            <div>
                                <a style="width: 210px;">
                                    <div class="relatedImg padding20">
                                        <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${productId?if_exists}_Premium?wid=180&hei=180&fmt=png-alpha" alt="${productId?if_exists}">
                                    </div>
                                    <h5>9x12 Standard Folder # ${relatedSlide}</h5>
                                    <p><span class="reviewCount">(134) Reviews</span> | <span class="inStock">In Stock</span></p>
                                </a>
                            </div>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
            -->
            <div class="productSpecs marginBottom30">
                <h3>Product Specs:</h3>
                <div class="foldersTabularRow productSpecsRow">
                    <#if productId?exists && (basicAndPremiumMaterialList?has_content || nonBasicAndPremiumMaterialList?has_content)>
                    <div class="jqs-templateInfo" data-templateId="${productId?if_exists}">
                        <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${productId?if_exists}" alt="${productId?if_exists}">
                        <a class="tmplDownload" href="<@ofbizContentUrl>/html/files/folders/templates/${productId?if_exists}.zip</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}">Download PDF Template</a>
                    </div>
                    </#if>
                    <div>
                        <table class="blankProductTable jqs-productfeatures">
                            <tr><td>SKU:</td><td>${product.getId()}</td></tr>

                            <#if product.getFeatures()?has_content>
                                <#list (product.getFeatures()).keySet() as key>
                                    <#if Static["com.envelopes.product.ProductEvents"].FEATURES_TO_SHOW.contains(key)?has_content>
                                        <#assign featureTypeDesc = Static["com.envelopes.product.ProductHelper"].getFeatureDescByType(delegator, key) />
                                        <tr>
                                            <td>
                                            ${featureTypeDesc?if_exists}
                                            </td>
                                            <td>
                                            ${product.getFeatures().get(key)}
                                            </td>
                                        </tr>
                                    </#if>
                                </#list>
                            </#if>
                            <#-- <tr><td>Paper Weight:</td><td>80lb.</td></tr>
                            <tr><td>Size:</td><td>9"x12" - 4" Pockets</td></tr>
                            <tr><td>Number of Panels:</td><td>2</td></tr>
                            <tr><td>Left Pocket:</td><td>Yes</td></tr>
                            <tr><td>Center Pocket:</td><td>No</td></tr>
                            <tr><td>Right Pocket:</td><td>Yes</td></tr>
                            <tr><td>Pocket Options:</td><td>Left Pocket Slits, Right Pocket Slits</td></tr>
                            <tr><td>Special Features:</td><td>Yes</td></tr>
                            <tr><td>Accessories:</td><td>No</td></tr>
                            <tr><td>Acceptable Insert Sizes:</td><td>Yes</td></tr>
                            <tr><td>Minimum Quality:</td><td>250</td></tr>
                            <tr><td>Production Time:</td><td>5 Working Days from Proof Approval till Ship date *</td></tr> -->
                        </table>
                    </div>
                </div>
            </div>

            <div id="reviews" class="product customerReviews marginBottom30">
                <div class="foldersTabularRow">
                    <div>
                        <h3>Customer Reviews:</h3>
                    </div>
                    <div>
                        <strong class="pullRight" data-bnReveal="productReview">Leave a Review</strong>
                    </div>
                </div>

                <div class="jqs-productReviewsContent marginTop10">
                    ${screens.render("component://folders/widget/ProductScreens.xml#reviews2")}
                </div>

            </div>
        </div>
    </div>

    <div id="productReview" class="bnRevealContainer">
        <div class="bnRevealHeader fbc-blue">
            <h3>Leave a Review</h3>
            <i class="fa fa-times jqs-bnRevealClose"></i>
        </div>
        <div class="bnRevealBody">
            <form id="reviewForm" name="reviewForm" method="post" action="" data-bigNameValidateForm="productReview">
                <input type="hidden" name="productId" value="${product.getId()}" />
                <input type="hidden" name="orderId" value="<#if requestParameters.orderId?exists>${requestParameters.orderId}</#if>" />
                <input type="hidden" name="salesChannelEnumId" value="FOLD_SALES_CHANNEL" />
                <div class="jqs-submitReviewResponse hidden ftc-green">Thank You</div>
                <div class="marginTop10">
                    Overall Envelope Rating: <div class="rating-1_0"></div>
                    <input type="hidden" name="productRating" value="5">
                </div>
                <div class="marginTop10">
                    <input type="text" name="nickName" value="" placeholder="Your Name" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Nick Name" />
                </div>
                <div>
                    <input type="text" name="userLoginId" value="" placeholder="Email Address (optional and will not be shared)" />
                </div>
                <div>
                    <label class="bigNameSelect">
                        <select name="describeYourself">
                            <option value="">Industry</option>
                            <option value="Accounting Services">Accounting Services</option>
                            <option value="Banking">Banking</option>
                            <option value="Bride">Bride</option>
                            <option value="Ecommerce">Ecommerce</option>
                            <option value="Education">Education</option>
                            <option value="Event Planner">Event Planner</option>
                            <option value="Florist">Florist</option>
                            <option value="Government">Government</option>
                            <option value="Graphic Designer">Graphic Designer</option>
                            <option value="Groom">Groom</option>
                            <option value="Home Improvement Services">Home Improvement Services</option>
                            <option value="Homemaker">Homemaker</option>
                            <option value="Legal Services">Legal Services</option>
                            <option value="Medical Services">Medical Services</option>
                            <option value="Non-Profit">Non-Profit</option>
                            <option value="Other">Other</option>
                            <option value="Photographer">Photographer</option>
                            <option value="Printer">Printer</option>
                            <option value="Realtor">Realtor</option>
                            <option value="Religious Institution">Religious Institution</option>
                            <option value="Retail Sales">Retail Sales</option>
                            <option value="Stationery Designer">Stationery Designer</option>
                            <option value="Student">Student</option>
                            <option value="Teacher">Teacher</option>
                            <option value="Web Designer">Web Designer</option>
                            <option value="Other">Other</option>
                        </select>
                    </label>
                </div>
                <div>
                    <input type="text" name="productUse" value="" placeholder="What did you use this product for?" />
                </div>
                <div>
                    <textarea name="productReview" value="" placeholder="Your Review" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Your Review"></textarea>
                </div>
                <div>
                    <div>
                        Did the product meet your expectations?
                        <div>
                            <input id="pq-exceeds" type="radio" name="productQuality" value="Exceeds Expectation"> <label for="pq-exceeds">Exceeded</label>
                            <input class="marginLeft20" id="pq-met" type="radio" name="productQuality" value="Met Expectation"> <label for="pq-met">Met</label>
                            <input class="marginLeft20" id="pq-below" type="radio" name="productQuality" value="Below Expectation"> <label for="pq-below">Below</label>
                        </div>
                    </div>
                </div>
                <div class="marginTop10">
                    Would you recommend this product to a friend?
                    <div>
                        <input id="r-yes" type="radio" name="recommend" value="Y"> <label for="r-yes">Yes</label>
                        <input class="marginLeft20" id="r-no" class="padding-left-xxs" type="radio" name="recommend" value="N"> <label for="r-no">No</label>
                    </div>
                </div>
                <div class="foldersButton buttonGold marginTop10" data-bigNameValidateSubmit="productReview" data-bigNameValidateAction="submitProductReview">Submit Review</div>
            </form>
        </div>
    </div>


    <div id="orderSamples" class="bnRevealContainer">
        <div class="bnRevealHeader fbc-blue">
            <h3>Order Samples</h3>
            <i class="fa fa-times jqs-bnRevealClose"></i>
        </div>
        <div class="bnRevealBody">
            <p>Samples are $1 each. Maximum of 5 samples per color. Shipped via USPS.<br />Every $1 spent on samples (up to $5) can be used as a discount on your next order of $20+.<br /><br />Discount code will be sent via email.</p>
            <div class="samplesTableWrap">
            <table class="jqs-samplesTable samplesTable blankProductTable">
            <thead>
            <tr class="samplesHeader jqs-samplesHeader">
                <th></th>
                <th>Color</th>
                <th>Weight</th>
                <th>Quantity</th>
                <th>Price</th>
                <th></th>
            </tr>
            </thead>
            </table>
            </div>
            <div class="samplesPopupBody jqs-samplesPopupBody text-center"></div>
            <div class="row text-center margin-top-xs">
                <a href="<@ofbizUrl>/cart</@ofbizUrl>" class="right margin-right-xs margin-bottom-xs">View Cart</a>
            </div>
        </div>
    </div>


    <script src="<@ofbizContentUrl>/html/js/util/selectList.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
    <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.7.1/slick.min.js"></script>
    <script src="<@ofbizContentUrl>/html/js/folders/product/blankProduct.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>

    <#if product?exists>
    <script type="text/javascript">
        $(document).ready(function() {
            <#if reviewProductId?exists>
                bnRevealLoad('productReview');
            </#if>
        });

        function submitProductReview() {
            $.ajax({
                type: "POST",
                url: '<@ofbizUrl>/addReview</@ofbizUrl>',
                data: $('form[name="reviewForm"]').serialize(),
                dataType:'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    $('.jqs-submitReviewResponse').removeClass('hidden').html('Your review has been submitted!');
                    setTimeout(function() {
                        $('.bnRevealShadowedBackground').trigger('click');
                    }, 3000);
                }
                else {
                    //$(".jqs-response").addClass("form-error").html("There was an error processing your review.");
                }
            });
        }

        <#if product?exists>
        var productComplete = false;
        var productPage = new EnvProductPage();
        productPage.addProduct({
            'productId': '${product.getId()}',
            'color': '${product.getColor()?if_exists}',
            'name': '${product.getName()?if_exists}',
            'hex': '${product.getHex()?if_exists}',
            'printable': ${product.isPrintable()?c},
            'minColor': ${product.getMinPrintColor()?if_exists},
            'maxColor': ${product.getMaxPrintColor()?if_exists},
            'hasWhiteInk': ${product.hasWhiteInk()?c},
            'paperWeight': '${product.getWeight()?if_exists}',
            'brand': '${product.getBrand()?if_exists}',
            'hasVariableDataAbility': ${product.hasVariableDataAbility()?c},
            'hasAddressingAbility': ${product.hasAddressingAbility()?c},
            'parentProductId': '${product.getParentId()?if_exists}',
        'design': <#if designId?exists>'${designId}'<#else>null</#if>,
        'backDesign': <#if product.getBackDesign()?has_content>'${product.getBackDesign().get("scene7TemplateId")}'<#else>null</#if>,
            'colorsFront': ${product.getColorsFront()?if_exists},
            'colorsBack': ${product.getColorsBack()?if_exists},
        'templateType': <#if templateType?exists>'${templateType}'<#else>null</#if>,
        'designMethod': <#if designId?exists>'online'<#else>null</#if>,
            'hasSample': ${product.hasSample()?c},
            'enabled': true,
            'percentSavings': ${product.percentSavings()},
        'addOnProduct': <#if addOnProduct?has_content>'${addOnProduct}'<#else>null</#if>
        });

        productPage.loadSamples();
        productPage.bindClickEvents();
        productPage.returnActiveProduct(false).bindPrices();
        productPage.returnActiveProduct(false).smallestQuantity = ${smallestQuantity?if_exists};
        productPage.returnActiveProduct(false).lowestPrice = ${lowestPrice?if_exists};
        productPage.returnActiveProduct(false).priceData.quantity = ${smallestQuantity?if_exists};
        productPage.returnActiveProduct(false).calculatePrice();

        productComplete = true;
        </#if>
    </script>
    </#if>

    <script type='text/javascript' src="<@ofbizContentUrl>/html/js/addons/background-check/background-check.min.js</@ofbizContentUrl>"></script>
    <script type='text/javascript' src="<@ofbizContentUrl>/html/js/addons/lazy/jquery.lazy.min.js</@ofbizContentUrl>"></script>
<#else>
    <div class="padding20" style="font-size: 26px; font-weight: bold; text-align: center;">This product does not exist!</div>
</#if>