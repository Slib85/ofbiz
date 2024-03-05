<#assign imageTimestamp = "20180815" />
<#assign imgFmtQltyGry = 'fmt=jpeg&amp;qlt=50&amp;bgc=f1f1f1' />
<#assign imgFmtQltyLtGry = 'fmt=jpeg&amp;qlt=50&amp;bgc=f9f9f9' />
<#assign imgFmtQltyLtGry2 = 'fmt=jpeg&amp;qlt=50&amp;bgc=f1f1f1' />
<#assign imgFmtQltyWht = 'fmt=jpeg&amp;qlt=50&amp;bgc=ffffff' />

<#if product?exists>
    <#assign variantData = Static["com.envelopes.product.ProductHelper"].getProductColors(delegator, product.getProduct()) />
    <#assign variantColors = variantData.get("colors") />
    <#assign filters = variantData.get("filters").get("By Color").get("choices") />
    <#assign collections = variantData.get("filters").get("By Collection").get("choices") />


    <#assign pocketType = requestParameters.pocket_type?default("STANDARD") />
    <#assign printMethod = requestParameters.print_method?default("foilStamping") />

    <#if nonBasicAndPremiumMaterialList?exists>
        <#assign productStep = "getQuoteForm" />
        <#assign printMethod = "" />
    <#else>
        <#if product.isPrintable(false)?c == "true">
            <#assign productStep = "printing" />
        <#else>
            <#assign productStep = "plain" />

            <#list product.getPrices().keySet() as quantities>
                <#if quantities_index == 0>
                    <#assign defaultQuantity = quantities />
                    <#assign defaultPrice = product.getPrices().get(quantities).price />
                    <#break>
                </#if>
            </#list>
        </#if>
    </#if>

    <#if requestParameters.print_method?has_content>
        <#if (product.hasImprintMethod("FOUR_COLOR")?c == 'true' && requestParameters.print_method == "FOUR_COLOR") || (product.hasImprintMethod("SPOT")?c == 'true' && (requestParameters.print_method == "SPOT" || requestParameters.print_method == "PMS"))>
            <#assign productStep = 'printing' />
        <#elseif product.hasImprintMethod("FOIL")?c == 'true' && (requestParameters.print_method == "FOIL" || requestParameters.print_method == "foilStamping")>
            <#assign productStep = 'printing' />
        <#elseif product.hasImprintMethod("EMBOSS")?c == 'true' && (requestParameters.print_method == "EMBOSS" || requestParameters.print_method == "embossing")>
            <#assign productStep = 'printing' />
        <#elseif requestParameters.print_method == "getQuoteForm">
            <#assign productStep = "getQuoteForm" />
        </#if>
    </#if>
    <link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/util/selectList.css</@ofbizContentUrl>" />
    <link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/product/product.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" />
    <div class="foldersContainer foldersProduct customFoldersProduct">
        <div class="tabletDesktop-inlineBlock">
            <#include "../includes/breadcrumbs.ftl" />
        </div>
        <div class="foldersProductHeader">
            <div id="top" class="foldersTabularRow">
                <div class="noPaddingTop">
                    <h1>${product.getName()?if_exists}</h1>
                    <div class="productColor" bns-headercolorname>
                        ${product.getColor()?if_exists}
                        ${product.getPaperWeight()?if_exists}
                        <#if product.isNew()?has_content && product.isNew()?c == "true" && (!product.onSale()?has_content || product.onSale()?c == "false") && (!product.onClearance()?has_content || product.onClearance()?c == "false")><span class="newArrivalRibbon">NEW!</span></#if>
                    </div>
                    <#if product.getTagLine()?has_content><#if product.getTagLine() == "Ships in 1 Business Day"><i class="fa fa-info-circle marginRight5" data-bnreveal="ships1BusinessDay"></i><#elseif product.getTagLine() == "Ships in 4 Business Days"><i class="fa fa-info-circle marginRight5" data-bnreveal="ships4BusinessDay"></i></#if><h2>${product.getTagLine()}</h2></#if>
                    <div id="ships1BusinessDay" class="bnRevealContainer">
                        <div class="bnRevealHeader fbc-blue">
                            <h3>Ships In 1 Business Day:</h3>
                            <i class="fa fa-times jqs-bnRevealClose"></i>
                        </div>
                        <div class="bnRevealBody uploadContainer">
                            Please note, your order will be printed and Shipped 1 Business Day After you have approved your proof. Your order must be submitted by 12PM EST to qualify. 
                        </div>
                    </div>
                    <div id="ships4BusinessDay" class="bnRevealContainer">
                        <div class="bnRevealHeader fbc-blue">
                            <h3>Ships In 4 Business Days:</h3>
                            <i class="fa fa-times jqs-bnRevealClose"></i>
                        </div>
                        <div class="bnRevealBody uploadContainer">
                            Please note, your order will be printed and Shipped 4 Business Days After you have approved your proof. Your order must be submitted by 12PM EST to qualify.
                        </div>
                    </div>
                    <#if product.getReviews()?exists && productStep != "getQuoteForm">
                    <p>
                        <span bns-starrating class="starRating-${(product.getRating())?replace(".", "_")}<#if productStep == 'printing'> hidden</#if>">
                            <#if product.getRating() == '0_0'>
                                <a data-bnReveal="productReview" href="#">Be the first to write a review.</a>
                            <#else>
                                <a href="#reviews">(${product.getReviews().reviews?size}) Reviews</a>
                            </#if>
                        </span>
                        <#--  | <span class="inStock">In Stock</span> <span class="inStock">${variantColors?size} Colors and Stocks</span> <span class="inStock">Standard Card Slit Included</span> -->
                    </p>
                    </#if>
                </div>
                <div class="noPaddingLeft questionsProductHeader">
                    <h5>Have Questions?</h5>
                    <ul class="noListStyle">
                        <li>
                            <a href="tel:1-888-327-2812"><i class="fa fa-phone"></i> Call 1-888-327-2812</a>
                        </li>
                        <li class="marginLeft10">
                            <p href="" bns-driftopenchat=""><i class="fa fa-comment"></i> Chat</p>
                        </li>
                        <#if productStep != "plain">
                            <li>
                                <a bns-loadproductstep="getQuoteForm" bns-hidewhenclicked href="#top"><i class="fa fa-calculator"></i> Get a Special Quote</a>
                            </li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>

        <div class="foldersProductBody">
            <div class="foldersTabularRow marginTop10 productContent">
                <div class="foldersTabularRow width100" bns-hiddenproductinfo>
                    <div class="sideThumbnail textCenter">
                        <div bns-assetlist>
                            <#if productStep == 'plain' || productStep == "getQuoteForm">
                                <#assign productAssets = product.getProductAssets() />
                                <div<#if productStep == "getQuoteForm"> bns-adjustablequoteimage</#if> bns-selection selection-autoupdate="false" selection-value="${product.getId()}<#if productStep == "getQuoteForm">_LS</#if>" selection-name="assetName" class="textCenter marginBottom10 cursorPointer<#if productStep == "getQuoteForm"> hidden</#if>">
                                    <img class="padding10" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}<#if productStep == "getQuoteForm">_LS</#if>?ts=${imageTimestamp}&amp;fmt=png-alpha&amp;wid=100" />
                                </div>
                                <#if productStep == "getQuoteForm">
                                    <div bns-selection selection-autoupdate="false" selection-value="${product.getId()}" selection-name="assetName" class="textCenter marginBottom10 cursorPointer">
                                        <img class="padding10" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}?ts=${imageTimestamp}&amp;fmt=png-alpha&amp;wid=100" />
                                    </div>
                                </#if>
                                <#list productAssets as asset>
                                <div bns-selection selection-autoupdate="false" selection-value="${asset.assetName}" selection-name="assetName" class="textCenter marginBottom10 cursorPointer">
                                    <img class="padding10" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${asset.assetName}?ts=${imageTimestamp}&amp;fmt=png-alpha&amp;wid=100" />
                                </div>
                                </#list>
                            <#else>
                                <div bns-selection selection-autoupdate="false" selection-value="${product.getId()}_${pocketType?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r")}_FRONT" selection-name="assetName" class="textCenter marginBottom10 cursorPointer">
                                    <img data-doogma data-doogma-key="productview" data-doogma-value="front" property="image" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}_${pocketType?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r")}_FRONT?ts=${imageTimestamp}&amp;fmt=png-alpha&amp;wid=100" />
                                    <p>Front</p>
                                </div>
                                <div bns-selection selection-autoupdate="false" selection-value="${product.getId()}_${pocketType?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r")}" selection-name="assetName" class="textCenter marginBottom10 cursorPointer">
                                    <img data-doogma data-doogma-key="productview" data-doogma-value="perspective" property="image" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}_${pocketType?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r")}?ts=${imageTimestamp}&amp;fmt=png-alpha&amp;wid=100" />
                                    <p>Perspective</p>
                                </div>
                                <div bns-productassetinside bns-selection selection-autoupdate="false" selection-value="${product.getId()}_${pocketType?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r")}_OPEN" selection-name="assetName" class="textCenter marginBottom10 cursorPointer">
                                    <img data-doogma data-doogma-key="productview" data-doogma-value="inside" property="image" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}_${pocketType?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r")}_OPEN?ts=${imageTimestamp}&amp;fmt=png-alpha&amp;wid=100" />
                                    <p>Open</p>
                                </div>
                                <div bns-selection selection-autoupdate="false" selection-value="${product.getId()}_${pocketType?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r")}_BACK" selection-name="assetName" class="textCenter marginBottom10 cursorPointer">
                                    <img data-doogma data-doogma-key="productview" data-doogma-value="back" property="image" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}_${pocketType?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r")}_BACK?ts=${imageTimestamp}&amp;fmt=png-alpha&amp;wid=100" />
                                    <p>Back</p>
                                </div>
                            </#if>
                        </div>
                        <a target="_blank" href="<@ofbizContentUrl>/html/files/folders/foldersCheckList.pdf</@ofbizContentUrl>">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foldersCustomCheckList?ts=${imageTimestamp}&amp;fmt=png-alpha&amp;wid=100</@ofbizScene7Url>" />
                            <p>Download Artwork Checklist</p>
                        </a>
                    </div>
                </div>
                <div bns-doogmacontainer<#if productStep != 'foilStamping'> class="hidden"</#if>>
                    <!-- BEGIN DOOGMA DESIGN -->
                    <div class="doogmaplugin" data-uid="${product.getParentId()?default("")?lower_case}_folders"></div>
                    <div class="doogma-saved-design-link hidden"><button data-addtocartbutton></button></div>
                    <!-- END DOOGMA DESIGN -->
                </div>
                <div class="textCenter<#if productStep == 'foilStamping'> hidden</#if> positionElement" bns-heroimagecontainer>
                    <i data-bnreveal="enhancedImage" class="fa fa-search-plus"></i>
                    <div class="foldersTabularRow productImages">
                        <div class="product-images jqs-prodslides noMargin slider">
                            <div class="product-images-single slide">
                                <a>
                                    <img style="height: initial; max-height: initial;" data-key="1" property="image" bns-heroimage class="product-slide-image" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}<#if productStep != 'plain' && !nonBasicAndPremiumMaterialList?exists>_STANDARD</#if>?ts=${imageTimestamp}&fmt=png-alpha&wid=600&hei=600" />
                                </a>
                            </div>
                            <#if productAssets?has_content>
                                <#list productAssets as asset>
                                <div class="product-images-single removable slide">
                                    <a>
                                        <img data-key="2" property="image" class="product-slide-image" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${asset.assetName}?hei=750&amp;fit=stretch,1&amp;fmt=png-alpha" />
                                    </a>
                                </div>
                                </#list>
                            </#if>
                            <#if productId?exists>
                            <div class="product-images-single removable slide">
                                <a>
                                    <img data-key="3" property="image" class="product-slide-image" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${productId?if_exists}?hei=375&amp;fit=stretch,1" alt="${productId?if_exists}"/>
                                </a>
                            </div>
                            </#if>
                        </div>
                        <div id="doogma" class="hidden textCenter">
                            <img style="width: 100%; height: auto;" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/doogma?wid=700" alt="Doogma">
                        </div>
                    </div>
                </div>
                <div class="productSidebar" bns-productStep="${productStep}">

                    <#if product.isActive()?c == "true">
                        ${screens.render("component://folders/widget/ProductScreens.xml#" + productStep)}
                     <#else>
                         <div class=" discontinuedContainer">
                             <div class="discontinuedText">Discontinued</div>
                         </div>
                    </#if>
                </div>
            </div>
            <#if productStep != "plain">
            <#include "../includes/product/productAssets/getQuoteText.ftl" />
            </#if>
            <div class="productSpecs marginBottom30 jqs-prodSpecsContainer" bns-hiddenproductinfo>
                <div class="productDetailButtons <#if !productSlitInfoList?has_content>hidden</#if>"><div class="productSpecsButton padding10">Product Specs</div><div class="slitOptionsButton padding10">Pocket Slit Options</div></div>
                <div class="foldersRow productSpecsRow backgroundWhite padding10">
                    <#--<#if !product.getDescription()?has_content>-->
                    <div class="foldersColumn small12 medium12 large<#if productStep != "plain">4<#else>6</#if>" bns-longdesc property="description">
                        <h3 class="marginBottom5">Product Specs:</h3>
                        ${product.getDescription()?if_exists}
                    </div>
                    <#--</#if>-->
                    <#if productStep != "plain">
                    <div class="foldersColumn small12 medium4 large4 textCenter">
                        <img bns-templateimage src="//actionenvelope.scene7.com/is/image/ActionEnvelope/<#if productStep == 'plain' || nonBasicAndPremiumMaterialList?exists>${requestParameters.product_id?if_exists}<#else>SF-101</#if>?ts=${imageTimestamp}" alt="<#if productStep == 'plain' || nonBasicAndPremiumMaterialList?exists>${requestParameters.product_id?if_exists}<#else>SF-101</#if>">
                        <a bns-templatedownload class="tmplDownload" href="<@ofbizContentUrl>/html/files/folders/templates/<#if productStep == 'plain' || nonBasicAndPremiumMaterialList?exists>${requestParameters.product_id?if_exists}<#else>SF-101</#if>.zip</@ofbizContentUrl>">Download PDF Template</a>
                    </div>
                    </#if>
                    <div class="foldersColumn small12 medium4 large<#if productStep != "plain">4<#else>6</#if>">
                        <table class="foldersProductTable jqs-productfeatures" bns-productfeatures>
                            <tr><td>SKU</td><td>${product.getId()}</td></tr>

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
                            <#if pocketStyles?has_content>
                                <#list pocketStyles.keySet() as featureId>
                                    <#if pocketStyles.get(featureId).get("desc")?upper_case == requestParameters.pocketDescription?default("STANDARD")>
                                        <#assign featureIdForCardSlits = featureId />
                                        <#break />
                                    </#if>
                                </#list>
                                <#list pocketStyles.get(featureIdForCardSlits).get("assocs").keySet() as key>
                                    <#if Static["com.envelopes.product.ProductEvents"].FEATURES_TO_SHOW.contains(key)?has_content>
                                        <#assign featureTypeDesc = Static["com.envelopes.product.ProductHelper"].getFeatureDescByType(delegator, key) />
                                        <tr>
                                            <td>
                                            ${featureTypeDesc?if_exists}
                                            </td>
                                            <td>
                                            ${pocketStyles.get(featureIdForCardSlits).get("assocs").get(key).get(0).get("desc")}
                                            </td>
                                        </tr>
                                    </#if>
                                </#list>
                            </#if>
                        </table>
                    </div>
                </div>

                <#if productSlitInfoList?has_content>
                <div class="slitOptions padding20 hidden">
                    <div>
                        <#assign pocketName = "" />
                        <#list productSlitInfoList as productSlitInfo>
                            <#if pocketName != productSlitInfo.pocket>
                                <#assign pocketName = productSlitInfo.pocket />
                                <#if pocketName != ""></div></#if>
                                <h3 class="ftc-blue">${pocketName}</h3>
                            <div class="foldersFlexRow noFlexCountMobile marginTop10 marginBottom10">
                            </#if>

                            <#assign leftCenterRight = "" />
                            <#if productSlitInfo.hasLeft?exists && productSlitInfo.hasLeft == "Y">
                                <#assign leftCenterRight = leftCenterRight + "Left, " />
                            </#if>
                            <#if productSlitInfo.hasCenter?exists && productSlitInfo.hasCenter == "Y">
                                <#assign leftCenterRight = leftCenterRight + "Center, " />
                            </#if>
                            <#if productSlitInfo.hasRight?exists && productSlitInfo.hasRight == "Y">
                                <#assign leftCenterRight = leftCenterRight + "Right," />
                            </#if>
                            <#if productSlitInfo.hasRight?exists && productSlitInfo.hasTop == "Y">
                                <#assign leftCenterRight = leftCenterRight + "Top," />
                            </#if>
                            <#if productSlitInfo.hasRight?exists && productSlitInfo.hasBottom == "Y">
                                <#assign leftCenterRight = leftCenterRight + "Bottom" />
                            </#if>
                            <div class="textCenter marginRight20 marginBottom10 width30Percent textLeft">
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${productSlitInfo.template?if_exists}" alt="${productSlitInfo.template?if_exists}">
                                <h3 class="ftc-blue marginTop20 marginBottom5 slitType">${productSlitInfo.type?if_exists}</h3>
                                <div class="">${leftCenterRight?replace('\\, $', '', 'r')}</div>
                                <div class="">${productSlitInfo.description?if_exists}</div>
                                <div>${productSlitInfo.template?if_exists}</div>
                            </div>
                        </#list>
                    </div>
                </div>
                </#if>
            </div>
            <#if productStep != "getQuoteForm">
            <div id="reviews" class="product customerReviews marginBottom30" bns-hiddenProductInfo>
                <div class="foldersTabularRow">
                    <div>
                        <h3>Customer Reviews:</h3>
                    </div>
                    <div>
                        <strong class="pullRight" data-bnReveal="productReview">Leave a Review</strong>
                    </div>
                </div>
                <div class="jqs-productReviewsContent marginTop10">
                    ${screens.render("component://folders/widget/ProductScreens.xml#reviews")}
                </div>
            </div>
            </#if>
        </div>
        <div id="preOrderPromise" class="bnRevealContainer">
            <div class="popup-border-fade">
                <div class="bnRevealHeader fbc-blue">
                    <h3>Peace Of Mind Promise</h3>
                    <i class="fa fa-times jqs-bnRevealClose"></i>
                </div>
                <div class="bnRevealBody preOrderPromise">
                    <div class="preOrderHeader textCenter">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/peaceOfMindPromise?fmt=png-alpha&amp;wid=760</@ofbizScene7Url>" alt="Pre Order Promise"/>
                    </div>
                    <div class="preOrderContainer textCenter">
                        <div class="preOrderBackground">
                            <h2>Expert Service &amp; Attention to Detail:</h2>
                            <p>Everything is inspected with the utmost care from step 1! Before your order goes into production, you'll receive a <span>Free Digital Proof</span> and <span>Artwork Assistance</span> from our Expert Folder Fanatics! <span>Your proof approval is key!</span></p>
                        </div>
                        <div class="marginBottom20">
                            <div class="preOrderBackground width33">
                                <h2>Peace of Mind:</h2>
                                <p><span>Your satisfaction is our only concern!</span> We promise your order will be delivered exactly the way you expect it to, or better!</p>
                            </div>
                            <div class="preOrderBackground width33">
                                <h2>Deadline Dedication:</h2>
                                <p>We are dedicated to meeting your deadline, <span>we guarantee on-time shipment on every order!</span></p>
                            </div>
                            <div class="preOrderBackground width33">
                                <h2>Free Samples:</h2>
                                <p><span>Fast and Free Samples</span> shipped to your door to make decision making easier!</p>
                            </div>
                        </div>
                        <div class="contactInfo">
                            <h2>Have Questions or Need Help Placing Your Order?</h2>
                            <div>
                                <a href="tel:1-888-327-2812">
                                    <i class="fa fa-phone-square"></i>
                                    Call 888-327-2812
                                </a>
                            </div>
                            <div>
                                <a href="http://support.folders.com/customer/portal/emails/new" target="_blank">
                                    <i class="fa fa-envelope"></i>
                                    Email: support@folders.com
                                </a>
                            </div>
                            <div>
                                <a href="#" bns-driftOpenChat>
                                    <i class="fa fa-user-circle"></i>
                                    Chat Live with Support
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="startUpload" class="bnRevealContainer">
            <div class="bnRevealHeader fbc-blue">
                <h3>File Upload:</h3>
                <i class="fa fa-times jqs-bnRevealClose"></i>
            </div>
            <div class="bnRevealBody uploadContainer">
                <div class="padding20">
                    <form id="uploadScene7Files" method="POST" action="<@ofbizUrl>/uploadScene7Files</@ofbizUrl>" enctype="multipart/form-data">
                        <input type="file" name="fileUpload" class="jqs-fileupload" multiple />
                        <div class="fileContainer jqs-filecontainer">
                            <div class="dropzone placeholder"></div>
                        </div>
                    </form>
                    <div class="textCenter marginTop10">
                        <span>Accepted File Types:</span>
                        <img src="/html/img/product/popups/allowedFileTypes.png" alt="accepted file types" />
                    </div>
                </div>
                <div class="pullLeft marginLeft5 paddingLeft20 paddingRight20 marginBottom10 reviewProofText">You'll receive an emailed proof within 24 hours. Once you approve your proof, your order will enter print production. If you do not review your proof within 5 business days, your order will automatically enter print production.</div>
                <div class="foldersRow">
                    <div class="foldersColumn large8 paddingLeft30">
                        <a href="<@ofbizUrl>/printing-and-prepress-help</@ofbizUrl>">Click Here to read our Artwork Specs</a>
                    </div>
                    <div bns-hiddenuploadbutton class="foldersColumn large4 paddingRight30 hidden">
                        <div class="foldersButton buttonGreen jqs-bnRevealClose pullRight uploadButton">Upload</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="enhancedImage" class="bnRevealContainer">
        <div class="bnRevealHeader fbc-blue">
            <h3>${product.getName()?if_exists}</h3>
            <i class="fa fa-times jqs-bnRevealClose"></i>
        </div>
        <div class="bnRevealBody enhancedImageBody">
            <img class="jqs-enhancedImage jqs-defer-img" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}<#if productStep != 'plain' && !nonBasicAndPremiumMaterialList?exists>_STANDARD</#if>?ts=${imageTimestamp}&fmt=png-alpha&wid=700&hei=710" />
        </div>
    </div>
    <script>
        $('.jqs-premiumQuantity').on('change', function() {
            $('.jqs-premiumPrice').html($(this).val() + ' each');
        }).trigger('change');

        $('.jqs-requestQuote').on('click', function() {
            var counter = 0;
            while (!$('.jqs-quoteBack').hasClass('hidden') && counter <= 99) {
                $('.jqs-quoteBack').trigger('click');
                counter++;
            }
        });

        $('.jqs-requestQuote').on('click', function() {
            GoogleAnalytics.trackEvent('Quote Request', 'CUSTOM', 'Start');
        });
    </script>

    <script src="<@ofbizContentUrl>/html/js/addons/jquery-ui-1.11.1.min.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
    <script src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.iframe-transport.js</@ofbizContentUrl>"></script>
    <script src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.fileupload.js</@ofbizContentUrl>"></script>
    <script src="<@ofbizContentUrl>/html/js/addons/jquery.scrollTo-min.js</@ofbizContentUrl>"></script>
    <script src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.iframe-transport.js</@ofbizContentUrl>"></script>
    <script src="<@ofbizContentUrl>/html/js/folders/product/product.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>

    <script type="text/javascript">
        var productPage = new ProductPage();
        var recentlyViewedCounter = 0;

        if(window.location.hash.substr(1) != '' && window.location.hash.substr(1).match(/^\d/) != null) {
            var projectId = window.location.hash.substr(1);

            $.ajax({
                type: 'POST',
                url: '/' + websiteId + '/control/loadProject',
                data: { 'id' : projectId },
                async: false,
                dataType: 'json',
                success: function(data) {
                    if(data.success && typeof data.settings !== 'undefined') {
                        var myProduct = data.settings.product[0];
                        myProduct.projectId = window.location.hash.substr(1);
                        productPage.addProduct(data.settings.product[0]);
                    }
                }
            });
        } else {
            productPage.addProduct({
                'selectionData': {
                    'productId': '${product.getId()}',
                    'attachments': '',
                    'foilColor': 'Gold Metallic',
                    <#if productStep != "plain" && productStep != "getQuoteForm">'pocket': '${pocketType?replace('_', ' ')}',</#if>
                    'cardSlits': 'Horizontal 2-Corner (Top Left, Bottom Right)',
                    <#if productStep != "plain" && productStep != "getQuotleForm">'printMethod': productPage.getAlternatePrintMethodName('${printMethod}', true),</#if>
                    'designMethod': 'onlineDesigner',
                    <#if product.getId() == "08-96">'quoteOutsideColor': 'Full Color Printing',</#if>
                    <#if product.getId() == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped"))>'quoteOutsideColor': 'Foil Stamped',</#if>
                    <#if (requestParameters.printingType?exists && (requestParameters.printingType == "Blind Embossing"))>'quoteOutsideColor': 'Blind Embossed',</#if>
                    <#if (product.getId() == "08-96" || product.getId() == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Printed Folders" || requestParameters.printingType == "Blind Embossing")))>'quoteInsideColor': 'None',</#if>
                    'assetName': '${product.getId()}<#if productStep != 'plain' && !nonBasicAndPremiumMaterialList?exists>_STANDARD</#if>',
                    'quantity': ${defaultQuantity?default("undefined")},
                    'spotColor': 1,
                    'designerValues': {},
                    'editLayout': 'Top Center 15sq',
                    'coatingSide1': 'None',
                    'coatingSide2': 'None',
                    'numberOfCoatings': 0,
                    'productUrl': '${request.getRequestURL()?default('')}'
                },
                'productName': '${product.getName()?if_exists}',
                'parentProductId': '${product.getParentId()?if_exists}',
                'paperWeight': '100lb.',
                'paperTexture': 'Linen',
                'colorDesc': 'Bright White',
                'colorGroup': 'White',
                'imageSize': '15',
                'mode': 'regular',
                'startedOnCustom': <#if nonBasicAndPremiumMaterialList?exists || productStep == "plain">false<#else>true</#if>,
                'initialProductStep': '${productStep?if_exists}',
                'projectId': null,
                'scene7DesignId': null,
                'enabled': true,
                'userSelectedTemplateType': false,
                'pricingRequest': null,
                'categoryId': '${requestParameters.category_id?if_exists}',
                'stepName': '${productStep}',
                'hasSample': ${product.hasSample()?c},
                'minimumQuantity': null,
                'minimumPrice': <#if defaultPrice?exists>'${defaultPrice}'<#else>null</#if>,
                'rating': '${product.getRating()}',
                'type': '${product.getProductType()?if_exists}',
                'percentSavings': null,
                'orderSample': '',
                'hasCustomQty': '${product.hasCustomQty()?c}'
            });
            recentlyViewedCounter++;
        }

        productPage.setInitialDoogmaValues();
        productPage.stepList.push('${productStep}');
        productPage.updateMode('${productStep}'); 
        
        productPage.getActiveProduct().setProductAttributes({
            printedDiscount: ${printedDiscount?default("0")}
        });


        <#if nonBasicAndPremiumMaterialList?exists>
            function checkImage(imageSrc, good, bad) {
                var img = new Image();
                img.onload = good;
                img.onerror = bad;
                img.src = imageSrc;
            }

            checkImage('//actionenvelope.scene7.com/is/image/ActionEnvelope/${requestParameters.product_id?if_exists}_LS?ts=${imageTimestamp}', function() {
                $('[bns-heroimage]').attr('src', '//actionenvelope.scene7.com/is/image/ActionEnvelope/${requestParameters.product_id?if_exists}_LS?ts=${imageTimestamp}&fmt=png-alpha&wid=600&hei=600');
                $('[bns-adjustablequoteimage]').removeClass('hidden');
                productPage.getProducts()[productPage.activeProductIndex].setProductAttributes({'selectionData': {'assetName': '${requestParameters.product_id?if_exists}_LS'}});
            }, '');
        </#if>

        function submitProductReview() {
            $.ajax({
                type: "POST",
                url: '<@ofbizUrl>/addReview</@ofbizUrl>',
                data: $('form[name="reviewForm"]').serialize(),
                dataType:'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    $('#reviewForm').addClass('hidden');
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

        $(document).ready(function() {
            var pixels_per_star = 22;
            var current_rating = 5;

            $("#reviewForm [class^='rating']").on("mousemove", function(e) {
                rating = Math.ceil((e.pageX - $(this).offset().left) / pixels_per_star);
                $(this).removeClass().addClass("rating-" + rating + "_0");
            }).on("mouseleave", function() {
                $(this).removeClass().addClass("rating-" + current_rating + "_0");
            }).on("click", function(e) {
                current_rating = Math.ceil((e.pageX - $(this).offset().left) / pixels_per_star)
                $("[name='productRating']").val(current_rating);
            });
        });
    </script>
<#else>
    The product you requested is no longer available.
</#if>