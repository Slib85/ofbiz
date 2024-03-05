<#assign imgFmtQltyGry = 'fmt=jpeg&amp;qlt=75&amp;bgc=f1f1f1' />
<#assign imgFmtQltyLtGry = 'fmt=jpeg&amp;qlt=75&amp;bgc=f9f9f9' />
<#assign imgFmtQltyLtGry2 = 'fmt=jpeg&amp;qlt=75&amp;bgc=f6f6f6' />
<#assign imgFmtQltyWht = 'fmt=jpeg&amp;qlt=75&amp;bgc=ffffff' />

<style id="designerCSS">
    #designerContainer {
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0px;
        left: 0px;
        border: 55px solid rgba(0,0,0,.45);
        box-shadow: inset 0 0 0 2px rgba(254,126,3,.75);
        -moz-background-clip: padding;
        -webkit-background-clip: padding;
        background-clip: padding-box;
        z-index: 50;
        background-color: #ffffff;
    }

    .canvas-container {
        position: relative !important;
        top: 10px !important;
        left: 50% !important;
        opacity: 0 !important;
        -webkit-box-shadow: rgba(128, 128, 128, 0.6) 0 0 5px 0 !important;
        -moz-box-shadow: rgba(128, 128, 128, 0.6) 0 0 5px 0 !important;
        box-shadow: rgba(128, 128, 128, 0.6) 0 0 5px 0 !important;
    }

    .canvas-container canvas {
        width: 100% !important;
        height: 100% !important;
    }

    .designerHidden {
        position: absolute !important;
        visibility: none !important;
        left: -99999px !important;
    }

    /* Texel CSS */
    .texelSelect {
        height: 14px;
        font-size: 13px;
        padding: 4px 31px 9px 5px;
        background: url(/html/texel/img/dropDownArrow.png) no-repeat right 5px center,#e8e8e8;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        cursor: pointer;
        display: inline-block;
        margin-right: 5px;
    }
    .texelSelect[bns-texel_select="texel_fontFamily"] {
        width: 125px;
    }
        .texelSelect .selectedColor {
            height: 18px;
            width: 18px;
            left: 0;
            top: 1px;
            position: relative;
        }

    .texelSelectListPopup {
        display: none;
        overflow-y: auto;
        max-height: 300px;
        position: absolute;
        background-color: #fff;
        z-index: 9999;
        border: 1px solid #ccc;
        min-width: 185px;
        margin: 0px;
    }
        .texelSelectListOption {
            box-sizing: border-box;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
            padding: 5px 10px 5px 15px;
            cursor: pointer;
            white-space: nowrap;
            border: 1px solid transparent;
        }

        .texelSelectListOption:hover {
            border: 1px solid #00a4e4;
        }
        .texelSelectListSelected {
            border: 1px solid #00a4e4;
        }

    .texelRadio {
        background-color: #e8e8e8;
        display: inline-block;
        width: 17px;
        height: 17px;
        position: relative;
        padding: 5px;
        cursor: pointer;
        margin: 0 5px 0 0;
    }
    .texelRadio[data-selected="true"] {
        background-color: #545554;
    }
        .texelRadio [class*="-fontAlignment"] {
            border-top: 2px solid #555;
            position: relative;
            height: 0;
        }
        .texelRadio[data-selected="true"] [class*="-fontAlignment"] {
            border-top: 2px solid #fff;
        }
    .canvas-container .addressRotator {
        position: absolute;
        width: 120px;
    }
    .canvas-container .addPrev,
    .canvas-container .addNext {
        display: inline-block;
        border: 1px solid #000000;
        color: #000000;
        padding: 1px;
        cursor: pointer;
    }
    .canvas-container .addNext {
        margin-left: 5px;
    }
</style>



<#if product?exists && (!designActive?has_content || designActive)>
<script>
    metaTitleSuffix = "${metaTitleSuffix?default("")}";
</script>
<link href="<@ofbizContentUrl>/html/css/util/fonts.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/product/sideBarPanelNEW.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/product/productNEW.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<div vocab="https://schema.org/" typeof="Product" class="container product hproduct">
    <div class="margin-right-sm margin-top-xxs margin-left-sm tablet-desktop-only">
        <#include "../includes/breadcrumbs.ftl" />
    </div>
    <div class="productContent">
        <div class="productContentLeft">
            <h1 property="name" class="fn jqs-productname"><#if designName?has_content>${designName?if_exists}<#else>${product.getName()?if_exists}</#if></h1>
            <div>
                <span class="productBrand jqs-brand jqs-LUXPaper<#if !product.getBrand()?has_content || (product.getBrand()?has_content && !product.getBrand()?starts_with("LUXPaper"))> hidden</#if>"><img src="<@ofbizContentUrl>/html/img/logo/luxPaperLogo.png</@ofbizContentUrl>" alt="LUXPaper" /> &#8212;</span>
                <span class="productBrand jqs-brand jqs-Neenah<#if !product.getBrand()?has_content || (product.getBrand()?has_content && !product.getBrand()?starts_with("Neenah"))> hidden</#if>"><img src="<@ofbizContentUrl>/html/img/logo/neenah_logo.png</@ofbizContentUrl>" alt="Neenah" /> &#8212;</span>
                <span class="productBrand jqs-brand jqs-Fredrigoni<#if !product.getBrand()?has_content || (product.getBrand()?has_content && !product.getBrand()?starts_with("Fredrigoni"))> hidden</#if>"><img src="<@ofbizContentUrl>/html/img/logo/fredrigoni_logo.png</@ofbizContentUrl>" alt="Fredrigoni" /> &#8212;</span>
                <span class="productColor jqs-colorname">${product.getColor()?if_exists}</span>

                <span class="getItQuick jqs-getItQuick hidden" data-tooltip aria-haspopup="true" class="has-tip" title="Order today, receive tomorrow! This item will be delivered the next business day if item is not printed and ordered by 5PM EST. Orders placed Friday after 5PM EST through Sunday are delivered on Tuesday."><i class="fa fa-bolt"></i> In Stock, Get it Tomorrow</span>
                <span class="productStock jqs-inventoryDom margin-left-xxs hidden"><i class="fa fa-check"></i> In Stock</span>
                <#if product.isNew()?has_content && product.isNew()?c == "true" && (!product.onSale()?has_content || product.onSale()?c == "false") && (!product.onClearance()?has_content || product.onClearance()?c == "false")><span class="jqs-new newRibbon">NEW!</span></#if>
            </div>
            <#if product.getReviews()?exists>
                <ul class="inline-list no-margin">
                    <li class="marginTop5 no-margin">
                        <div class="jqs-starRating rating-${product.getRating()}"></div>
                    </li>
                    <li class="marginTop5 no-margin">
                        <span class="jqs-noreview <#if product.getRating() != '0_0'>hidden</#if>"><a data-reveal-id="leave-a-review" href="#">Be the first to write a review.</a></span><span class="jqs-hasreview <#if product.getRating() == '0_0'>hidden</#if>"><a href="#reviews">(${product.getReviews().reviews?size}) Reviews</a></span>
                    </li>
                </ul>
            </#if>
            <div class="productImageContainer">
                <#if designId?has_content>
                    <#assign productAssets = product.getProductAssets("printed", designId) />
                <#else>
                    <#assign productAssets = product.getProductAssets("plain", null) />
                </#if>
                <div class="imageDisplay">
                    <#if product.getId()?if_exists == "SWATCHBOOK" || product.getId()?if_exists == "LUX-SWATCHBOOK">
                        <img class="swatchbookBurst" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/ships_free_burst?wid=149&amp;fmt=png-alpha" alt="Swatchbook Burst" />
                    </#if>
                    <#assign hasDefault = "n" />
                    <#assign defaultImageId = "" />
                    <#assign hasPrinted = "n" />
                    <#list productAssets as asset>
                        <#if asset.assetType == "printed"><#assign hasPrinted = "y" /></#if>
                        <#if (asset.assetDefault?exists && asset.assetDefault == "Y") || (asset.assetDefault?exists && asset.assetDefault == "Y" && designId?has_content && asset.assetType == "printed" && designId == asset.designId)>
                            <#assign hasDefault = "y" />
                            <#assign defaultImageId = asset.assetName />
                        </#if>
                    </#list>
                    <#if (productAssets?size gt 0 && !designId?has_content) || (designId?has_content && hasPrinted == "y")>
                        <div class="jqs-prodassets imageSelection">
                            <div data-selected="<#if hasDefault == "y">n<#else>y</#if>" data-type="image" data-src="<#if designId?has_content>//texel.envelopes.com/getBasicImage?id=${designId}<#if templateType?exists>&amp;templateType=${templateType}</#if><#if hex?exists>&amp;hex=${hex}</#if>&amp;hei=413&amp;fmt=png-alpha<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}?hei=413&amp;wid=510&amp;${imgFmtQltyGry}</#if>">
                                <img src="<#if designId?has_content>//texel.envelopes.com/getBasicImage?id=${designId}<#if templateType?exists>&amp;templateType=${templateType}</#if><#if hex?exists>&amp;hex=${hex}</#if>&amp;hei=48&amp;fmt=png-alpha<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}?hei=48&amp;${imgFmtQltyGry}</#if>" alt="<#if designName?has_content>${designName?if_exists}<#else>${product.getName()?if_exists}</#if>" />
                            </div>
                            <#list productAssets as asset>
                                <#if (asset.assetType == "printed" && designId?has_content && designId == asset.designId) || (asset.assetType != "printed" && !designId?has_content)>
                                    <div data-selected="<#if hasDefault == "y" && asset.assetDefault?exists && asset.assetDefault == "Y">y<#else>n</#if>" data-type="${asset.assetType}" data-src="<#if asset.assetType == "video"><#if !firstVideo?exists><#assign firstVideo = "//fast.wistia.net/embed/iframe/${asset.assetName}" /></#if>//fast.wistia.net/embed/iframe/${asset.assetName}<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${asset.assetName}?hei=413&amp;wid=510&amp;fmt=jpeg&amp;qlt=70&amp;bgc=f1f1f1</#if>">
                                        <img src="<#if asset.assetType == "video">//embed-ssl.wistia.com/deliveries/${asset.assetThumbnail?if_exists}.jpg?image_crop_resized=48x36<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${asset.assetName}?hei=48&amp;fmt=jpeg&amp;qlt=85&amp;bgc=f1f1f1</#if>" alt="<#if designName?has_content>${designName?if_exists}<#else>${product.getName()?if_exists}</#if>" />
                                        <#if asset.assetType == "video"><i class="fa fa-youtube-play"></i></#if>
                                    </div>
                                </#if>
                            </#list>
                        </div>
                    </#if>
                    <i data-reveal-id="enhancedImage" class="fa fa-search-plus"></i>
                    <div id="enhancedImage" class="reveal-modal reveal-modal-limiter enhancedImage" data-reveal>
                        <div class="padding-bottom-xxs popup-title">
                            <h3 class="padding-left-xxs">${product.getName()?if_exists}</h3>
                            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
                        </div>
                        <div class="enhancedImageBody">
                            <img class="jqs-enhancedImage jqs-defer-img" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" src="data:image/png;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=" data-src="<#if designId?has_content>//texel.envelopes.com/getBasicImage?id=${designId}<#if templateType?exists>&amp;templateType=${templateType}</#if><#if hex?exists>&amp;hex=${hex}</#if>&amp;hei=600&amp;fmt=png-alpha<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/<#if hasDefault == "y">${defaultImageId}<#else>${product.getId()}</#if>?hei=600&amp;${imgFmtQltyGry}</#if>" />
                        </div>
                    </div>
                    <img property="image" class="jqs-imagehero productImage photo" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" style="display: block;" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/<#if hasDefault == "y">${defaultImageId}<#else>${product.getId()}</#if>?hei=413&wid=510&amp;${imgFmtQltyGry}" />
                    <div class="jqs-productVideoContainer envResponsiveIframe hidden" data-ratio="16x9">
                        <iframe id="productVideo" title="Video Player" src="<#if firstVideo?has_content>${firstVideo}<#else>//fast.wistia.net/embed/iframe/sfyk3r1v74</#if>" allowtransparency="true" frameborder="0" scrolling="no" class="wistia_embed" name="wistia_embed" allowfullscreen mozallowfullscreen webkitallowfullscreen oallowfullscreen msallowfullscreen></iframe>
                    </div>
                </div>
                <div bns-load_design class="margin-top-xxs margin-bottom-xxs blueButton<#if !designId?has_content> hidden</#if> tablet-desktop-only">
                    <#if designId?has_content>Edit Your Design<#else>Get Started</#if> <i class="fa fa-caret-right"></i>
                </div>
                <a class="tablet-desktop-only uploadLink<#if !designId?has_content> hidden</#if>" href="<@ofbizUrl>/product?product_id=${product.getId()}&amp;showUpload=1</@ofbizUrl>">
                    Have a print-ready file? Upload Artwork <i class="fa fa-caret-right"></i>
                    <div><span class="cmykC"></span><span class="cmykM"></span><span class="cmykY"></span><span class="cmykK margin-right-xs"></span></div>
                </a>
                <#if product.isPrintable()?c == "true">
                    <div class="text-center notPrintableOnMobileText mobile-only"><span class="cmykC"></span><span class="cmykM"></span><span class="cmykY"></span><span class="cmykK margin-right-xxs"></span><#if designId?has_content>Customize<#else>Add printing</#if> using a non-mobile device.</div>
                </#if>
            </div>
            <div class="addToCartRow jqs-addToCartRow hidden">
                <div class="uploadedFiles hidden jqs-uploadedfiles no-margin"></div>
                <div class="button-cta button-regular jqs-addToCart addToCart">Add to Cart</div>
            </div>
            <div class="margin-top-xs jqs-productSpecs">
                <h4>Product Specs</h4>
                <div class="margin-top-xxs jqs-productfeatures">
                    <div class="productSpecsRow">
                        <div class="specsCol1">SKU</div>
                        <div class="specsCol2" data-productSku property="mpn">${product.getId()}</div>
                    </div>
                    <#if product.getFeatures()?has_content>
                        <#list (product.getFeatures()).keySet() as key>
                            <#if FEATURES_TO_SHOW.contains(key)?has_content>
                                <#assign featureTypeDesc = Static["com.envelopes.product.ProductHelper"].getFeatureDescByType(delegator, key) />
                                <div class="productSpecsRow">
                                    <div class="specsCol1">
                                    ${featureTypeDesc?if_exists}<#if featureTypeDesc?exists && featureTypeDesc == "Paper Weight"> <i class="fa fa-question-circle" data-reveal-id="paperWeightInfo"></i></#if>
                                    </div>
                                    <div class="specsCol2">
                                    ${product.getFeatures().get(key)}<#if featureTypeDesc?exists && featureTypeDesc == "Paper Weight"> <i class="fa fa-question-circle" data-reveal-id="paperWeightInfo"></i></#if>
                                    </div>
                                </div>
                            </#if>
                        </#list>
                    </#if>
                </div>
            </div>
            <#if product.isPrintable()?c == "true">
                <div class="margin-top-xs tablet-desktop-only row jqs-productSpecs">
                    <h4 class="margin-bottom-xxs">Product Templates</h4>
                    <#assign frontTemplate = Static["com.envelopes.http.FileHelper"].doesFileExist("/hot-deploy/html/webapp/html/files/templates/", product.getId()?if_exists + "_FRONT.pdf")?if_exists />
                    <#if !frontTemplate?has_content>
                        <#assign frontTemplate = Static["com.envelopes.http.FileHelper"].doesFileExist("/hot-deploy/html/webapp/html/files/templates/", product.getParentId()?if_exists + "_FRONT.pdf")?if_exists />
                    </#if>
                    <#if frontTemplate?has_content>
                        <div class="left padding-right-xs text-left productTemplateDownloadContainer">
                            <img class="left" alt="Acrobat Logo" src="/html/img/common/fileformat/acrobat.png" />
                            <a href="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=${frontTemplate}&amp;downLoad=Y">Download Front Template PDF</a>
                        </div>
                    </#if>

                    <#assign backTemplate = Static["com.envelopes.http.FileHelper"].doesFileExist("/hot-deploy/html/webapp/html/files/templates/", product.getId()?if_exists + "_BACK.pdf")?if_exists />
                    <#if !backTemplate?has_content>
                        <#assign backTemplate = Static["com.envelopes.http.FileHelper"].doesFileExist("/hot-deploy/html/webapp/html/files/templates/", product.getParentId()?if_exists + "_BACK.pdf")?if_exists />
                    </#if>
                    <#if backTemplate?has_content>
                        <div class="left text-left productTemplateDownloadContainer">
                            <img class="left" alt="Acrobat Logo" src="/html/img/common/fileformat/acrobat.png" />
                            <a href="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=${backTemplate}&amp;downLoad=Y">Download Back Template PDF</a>
                        </div>
                    </#if>
                </div>
            </#if>
        </div>
        <div class="productContentRight">
            <#--  <div class="freeDesign">
                <h4 data-reveal-id="freeDesignReview"><i class="fa fa-file-image-o"></i> Free Design Review + Proof!</h4>
                <div id="freeDesignReview" class="reveal-modal reveal-modal-limiter" data-reveal>
                    <div class="padding-bottom-xs">
                        <div class="padding-bottom-xxs popup-title">
                            <h3 class="padding-left-xxs">Free Design Review + Proof</h3>
                        </div>
                        <h5 class="padding-top-xxs padding-left-xs padding-right-xs padding-bottom-xxs">Free Design Review + Proof</h5>
                        <div class="padding-left-xs padding-right-xs">
                            After checkout, you'll receive a <span>FREE</span> emailed proof within 24 hours. Once you approve your proof, your order will enter print production.<br />
                            For all orders placed using the "Online Design" tool, your products will be printed as they appear on your screen. If our design team has questions, or makes any adjustments, you will receive a digital proof to approve.
                        </div>
                        <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
                    </div>
                </div>
            </div>  -->
            <#if productSizeAndStyle?exists && productSizeAndStyle.SORTED_SIZE_LIST.size() gt 1 && !designId?has_content>
            <div class="productPageSizeStyle desktop-only">
                <div class="productPageSize">
                    <h5>Size</h5>
                    <div id="sizeSelection" class="productPageSizeBox selectList selectListParent sidebarToggle jqs-sidebarToggle" data-sidebar-name="sidebar-sizeList" selection-selectlistname="sizeSelection" bns-resizetext>
                        <div class="foldersTabularRow folderDisplayTable">
                            <div class="sizeName" bns-adjustfontsize style="opacity: 0;">${product.getSize()?if_exists}<#if product.getSize()?if_exists != product.getActualSize()?if_exists><br/><span class="actualSizeName"> (${product.getActualSize()?if_exists})</#if></span></div>
                            <i class="fa fa-caret-right productCaretSidebar"></i>
                        </div>
                    </div>

                    <div id="sidebar-sizeList" class="sidebarPanel jqs-scrollable">
                        <div class="colorTextureHeading">
                            <div class="stickyTextureHeading">
                                <h4><i class="fa fa-caret-left"></i>Sizes</h4>
                            </div>
                        </div>
                        <div class="colorTextureBody">
                            <div bns-sizeListOptionContainer class="colorTextureBodyInner jqs-scrollable selectListContainer" style="padding: 0;"></div>
                        </div>
                    </div>
                </div>
                <div class="productPageStyle">
                    <h5>Style</h5>
                    <div id="styleSelection" class="productPageStyleBox selectList selectListParent sidebarToggle jqs-sidebarToggle" data-sidebar-name="sidebar-styleList" selection-selectlistname="styleSelection">
                        <div class="foldersTabularRow folderDisplayTable">
                            <div class="styleImage"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/designTemplate-${product.getCategoryId()?lower_case}?hei=100&wid=100&fmt=png-alpha</@ofbizScene7Url>" alt="${product.getCategoryId()?lower_case}"></div>
                            <div class="styleValue" bns-adjustfontsize>${product.getCategoryName()}</div>
                            <i class="fa fa-caret-right productCaretSidebar"></i>
                        </div>
                    </div>

                    <div id="sidebar-styleList" class="sidebarPanel jqs-scrollable">
                        <div class="colorTextureHeading">
                            <div class="stickyTextureHeading">
                                <h4><i class="fa fa-caret-left"></i>Styles</h4>
                            </div>
                        </div>
                        <div class="colorTextureBody">
                            <div bns-styleListOptionContainer class="colorTextureBodyInner jqs-scrollable selectListContainer" style="padding: 0;"></div>
                        </div>
                    </div>
                </div>
            </div>
            </#if>
            <div class="<#if product.isActive()?c == "false"> discontinuedContainer</#if>">
                <#if product.isActive()?c == "true">
                    <div class="optionContentBlock option jqs-colorList colorListContent<#if (productSizeAndStyle?has_content && !productSizeAndStyle.SORTED_COLORS?has_content) || (productSizeAndStyle?has_content && productSizeAndStyle.SORTED_COLORS?has_content && productSizeAndStyle.SORTED_COLORS.keySet()?size == 1)> hidden</#if>">
                        <h5><img class="colorWheel" src="<@ofbizContentUrl>/html/img/icon/color_wheel.png</@ofbizContentUrl>" alt="Color Wheel" />Paper Color &amp; Texture <span class="jqs-colorCount">(<#if productSizeAndStyle?exists && productSizeAndStyle.SORTED_COLORS?exists>${productSizeAndStyle.SORTED_COLORS.size()}</#if> colors available):</span></h5>
                        <div>
                            <div bns-selectedcolortext id="colorSelection" class="selectList selectListParent sidebarToggle jqs-sidebarToggle colorSelection colorTextureSelection" data-sidebar-name="sidebar-colorList" selection-selectlistname="colorSelection" data-hex="${product.getHex()?if_exists}" data-ignorecaret="">
                                <div class="foldersTabularRow folderDisplayTable productColorSelection" style="height: 55px; background: url('<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getColor()?if_exists?replace("&.*?;", "", "r")?replace("[^a-zA-Z0-9_]", "", "r")?lower_case}</@ofbizScene7Url>?wid=1100&hei=92&ts=1') 100% repeat; background-color: #${product.getHex()?default("ffffff")}; background-position: right;">
                                    <div bns-colorselecttext class="textBold paddingLeft10">
                                        <div class="colorName <#if product.getBrand()?exists && product.getBrand()?matches("^.*?LUXPaper.*?$")?c == "true" && product.getCategoryId()?exists && product.getCategoryId() == "LINED">width250</#if>">
                                            <#if product.getBrand()?exists && product.getBrand()?matches("^.*?LUXPaper.*?$")?c == "true"><img src="<@ofbizContentUrl>/html/img/logo/luxPaperLogo.png</@ofbizContentUrl>" alt="LUXPaper" /></#if>
                                            ${product.getColor()?if_exists?replace(product.getWeight()?default(""), "")} ${product.getWeight()?if_exists}
                                        </div>
                                        <div class="colorPeelAndPress">
                                            <#if product.hasPeelAndPress()?c == "true"><span class="margin-right-xxs">w/ Peel &amp; Press&trade;</span></#if>
                                            <#if product.onClearance()?has_content && product.onClearance()?c == "true"><span class="clearanceRibbon">${product.percentSavings()?default("0")}% Off</span></#if>
                                            <#if product.onSale()?has_content && product.onSale()?c == "true"><span class="saleRibbon">${product.percentSavings()?default("0")}% Off</span></#if>
                                            <#if product.isNew()?has_content && product.isNew()?c == "true" && (!product.onSale()?has_content || product.onSale()?c == "false") && (!product.onClearance()?has_content || product.onClearance()?c == "false")><span class="newRibbon">NEW!</span></#if>
                                        </div>
                                    </div>
                                    <div><i class="fa fa-caret-right productCaretSidebar"></i></div>
                                </div>
                            </div>
                            <div id="sidebar-colorList" class="sidebarPanel colorList jqs-scrollable">
                                <div class="colorTextureHeading">
                                    <div class="stickyTextureHeading">
                                        <h4><i class="fa fa-caret-left"></i>Paper Color</h4>
                                    </div>
                                    <div class="productFilterHeader">
                                        <div>
                                            <div>Filter By:</div>
                                            <div class="jqs-filterOption filterOption filterSelected" data-filter-to-show="colorFilter">Color</div>
                                            <div class="jqs-filterOption filterOption" data-filter-to-show="collectionFilter">Collection</div>
                                            <div class="jqs-filterOption filterOption" data-filter-to-show="saleFilter">On Sale</div>
                                        </div>
                                        <div class="productFilter jqs-productFilter">
                                            <div class="productFilterBody">
                                                <div class="slideIt-container padding-xxs colorFilterList jqs-colorFilter">
                                                    <div class="slideIt-left">
                                                        <i class="fa fa-chevron-left"></i>
                                                    </div>
                                                    <div class="slideIt text-left colorFilterSlideIt">
                                                        <div bns-colorfilterlist></div>
                                                    </div>
                                                    <div class="slideIt-right">
                                                        <i class="fa fa-chevron-right"></i>
                                                    </div>
                                                </div>
                                                <div class="slideIt-container padding-xxs collectionFilterList jqs-collectionFilter hidden">
                                                    <div class="slideIt-left">
                                                        <i class="fa fa-chevron-left"></i>
                                                    </div>
                                                    <div class="slideIt text-left productImageSlideIt">
                                                        <div bns-collectionfilterlist></div>
                                                    </div>
                                                    <div class="slideIt-right">
                                                        <i class="fa fa-chevron-right"></i>
                                                    </div>
                                                </div>
                                                <div class="asdf jqs-clearProductFilter hidden clearFilter">Clear Filter</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="colorTextureBody">
                                    <div bns-colorListOptionContainer class="colorTextureBodyInner jqs-sampleReadableList selectListContainer" style="padding: 0;">
                                    <#assign counter = 0 />
                                    <#if productSizeAndStyle?has_content && productSizeAndStyle.SORTED_COLORS?has_content>
                                        <#list productSizeAndStyle.SORTED_COLORS.entrySet() as entry>
                                            <#assign variant = entry.getValue() />
                                            <#if variant.get("size")?exists && product.getActualSize() == variant.get("actualSize") && variant.get("style")?exists && product.getCategoryId() == variant.get("style")>
                                            <div bns-selection class="jqs-selectList selectList selectListItem <#if entry.getKey() == product.getId()>slSelected</#if>" <#if entry.getKey() == product.getId()>data-selected="true"</#if>data-percent-savings="${variant.savings?default("0")}" data-color-groups="${variant.get("colorGroup")?if_exists?replace("jqs-","")}" data-collection-groups="${variant.get("collection")?default("")?replace("jqs-","")}" data-target="colorSelection" data-new="${variant.get("new")?c}" data-onsale="${variant.get("sale")?c}" data-rating="${variant.get("rating")}" data-product-brand="${variant.get("brand")?default("")}" data-printable="${variant.get("printable")?c}" data-url="<@ofbizUrl>/product/~category_id=${variant.get("style")}/~product_id=${entry.getKey()}</@ofbizUrl>" data-product-id="${entry.getKey()}" data-hasPeelAndPress="${variant.hasPeelAndress?c}" data-has-white-ink="${variant.get("hasWhiteInk")?c}" data-has-sample="${variant.get("hasSample")?c}" data-product-weight="${variant.get("weight")?if_exists}" data-product-name="${variant.get("name")}" data-product-color="${variant.get("color")?if_exists}" data-hex="${variant.get("hex")?replace("#","")?replace("&x23;","")?replace("&35;","")}" data-min-color="${variant.get("minColors")?default(1)}" data-max-color="${variant.get("maxColors")?default(1)}" data-print-desc="${variant.get("printDescription")?default("")}" data-size="${variant.get("size")?if_exists}" data-actualsize="${variant.get("actualSize")?if_exists}" data-category="${variant.get("category")?if_exists}" data-style="${variant.get("style")?if_exists}" data-value="${entry.getKey()}" data-parentproductid="${variant.parentProductId?if_exists}" data-product-type="${variant.get("productTypeId")}" data-has-rush="${variant.get("hasRush")?c}" data-meta-title="${variant.get("metaTitle")?if_exists}">
                                                <div class="foldersTabularRow folderDisplayTable productColorSelection" style="background: url('<@ofbizScene7Url>/is/image/ActionEnvelope/${variant.get("color")?if_exists?replace("&.*?;", "", "r")?replace("[^a-zA-Z0-9_]", "", "r")?lower_case}</@ofbizScene7Url>?fmt=jpeg&qlt=50&wid=1100&hei=92&ts=1') 100% repeat; background-color: ${variant.get("hex")}; background-position: right;">
                                                    <div selection-removeonselect class="paddingLeft10 width30"><span class="selectCheckbox"></span></div>
                                                    <div bns-colorselecttext class="textBold paddingLeft10">
                                                        <div class="colorName <#if variant.get("brand")?exists && variant.get("brand")?matches("^.*?LUXPaper.*?$")?c == "true" && variant.get("style")?exists && variant.get("style") == "LINED">width250</#if>">
                                                            <#if variant.get("brand")?exists && variant.get("brand")?matches("^.*?LUXPaper.*?$")?c == "true"><img src="<@ofbizContentUrl>/html/img/logo/luxPaperLogo.png</@ofbizContentUrl>" alt="LUXPaper" /></#if>
                                                            ${variant.get("color").replace(variant.weight?default(""), "")?if_exists} ${variant.weight?if_exists}
                                                        </div>
                                                        <div class="colorPeelAndPress">
                                                            <#if variant.hasPeelAndress?c == "true"><span class="margin-right-xxs">w/ Peel &amp; Press&trade;</span></#if>
                                                            <#if variant.get("clearance")?c == "true"><span class="clearanceRibbon">${variant.get("savings")?default("0")}% Off</span></#if>
                                                            <#if variant.get("sale")?c == "true"><span class="saleRibbon">${variant.get("savings")?default("0")}% Off</span></#if>
                                                            <#if variant.get("new")?c == "true" && variant.get("sale")?c == "false" && variant.get("clearance")?c == "false"><span class="newRibbon">NEW!</span></#if>
                                                        </div>
                                                    </div>
                                                    <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                                </div>
                                            </div>
                                            <#assign counter = counter + 1 />
                                            </#if>
                                        </#list>
                                    </#if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="optionContentBlock option plainOrPrinted <#if product.isPrintable()?c != "true" || designId?has_content>hidden</#if>">
                        <h5 style="display:inline-block;">Choose Printing Option:</h5>
                        <div>
                            <div id="printingOption" class="selectList selectListParent sidebarToggle jqs-sidebarToggle printingOption" data-sidebar-name="sidebar-printingOption" selection-selectlistname="printingOption" data-ignorecaret="">
                                <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                    <div class="printingIconContainer"><i class="fa fa-envelope"></i></div>
                                    <div class="row">
                                        <div class="selectionHeader">Plain</div>
                                        <div class="selectionDescription jqs-inventoryDom hidden" style="font-size: 13px;"><i class="fa fa-check productStock" style="font-size: 10px;"></i> Ships today if placed by 4PM EST</div>
                                        <div class="getItQuick jqs-getItQuick hidden" data-tooltip aria-haspopup="true" class="has-tip" title="Order today, receive tomorrow! This item will be delivered the next business day if item is not printed and ordered by 5PM EST. Orders placed Friday after 5PM EST through Sunday are delivered on Tuesday."><i class="fa fa-bolt"></i> In Stock, Get it Tomorrow</div>
                                    </div>
                                    <div selection-showonselect >
                                        <div class="addPrintingQuickLink">Add Printing</div>
                                    </div>
                                    <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                </div>
                            </div>
                            <div id="sidebar-printingOption" class="sidebarPanel printingOption jqs-scrollable">
                                <div class="colorTextureHeading">
                                    <div class="stickyTextureHeading">
                                        <h4><i class="fa fa-caret-left"></i>Choose a Printing Option</h4>
                                    </div>
                                </div>
                                <div class="colorTextureBody">
                                    <div class="colorTextureBodyInner" style="padding: 0;">
                                        <div bns-selection class="selectList jqs-selectList<#if !designId?has_content> slSelected</#if>" data-key="plainOrPrinted" data-value="plain" data-selected="<#if !designId?has_content>true<#else>false</#if>" data-target="printingOption">
                                            <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                                <div selection-removeonselect class="paddingLeft10 width40"><span class="selectCheckbox"></span></div>
                                                <div class="printingIconContainer"><i class="fa fa-envelope"></i></div>
                                                <div class="row">
                                                    <div class="selectionHeader">Plain</div>
                                                    <div class="selectionDescription jqs-inventoryDom hidden" style="font-size: 13px;"><i class="fa fa-check productStock" style="font-size: 10px;"></i> Ships today if placed by 4PM EST</div>
                                                    <div class="getItQuick jqs-getItQuick hidden" data-tooltip aria-haspopup="true" class="has-tip" title="Order today, receive tomorrow! This item will be delivered the next business day if item is not printed and ordered by 5PM EST. Orders placed Friday after 5PM EST through Sunday are delivered on Tuesday."><i class="fa fa-bolt"></i> In Stock, Get it Tomorrow</div>
                                                </div>
                                                <div selection-showonselect >
                                                    <div class="addPrintingQuickLink">Add Printing</div>
                                                </div>
                                                <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                            </div>
                                        </div>
                                        <div bns-selection class="selectList jqs-selectList" data-key="plainOrPrinted" data-additionalkey="designOrUpload" data-value="printed" data-additionalvalue="upload" data-selected="false" data-target="printingOption">
                                            <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                                <div selection-removeonselect class="paddingLeft10 width40"><span class="selectCheckbox"></span></div>
                                                <div class="printingIconContainer"><i class="fa fa-cloud-upload"></i></div>
                                                <div class="row">
                                                    <div class="selectionHeader">Use a Complete Design</div>
                                                    <div class="allowedFileTypes">
                                                        <div class="pdf">pdf</div>
                                                        <div class="eps">eps</div>
                                                        <div class="psd">psd</div>
                                                        <div class="png">png</div>
                                                        <div class="jpg">jpg</div>
                                                        <div class="ai">ai</div>
                                                        <div class="tiff">tiff</div>
                                                    </div>
                                                </div>
                                                <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                            </div>
                                        </div>
                                        <div bns-selection class="selectList jqs-selectList<#if designId?has_content> slSelected</#if> tablet-desktop-only<#if !product.getDesigns()?has_content> hidden</#if>" data-key="plainOrPrinted" data-additionalkey="designOrUpload" data-value="printed" data-additionalvalue="design" data-selected="<#if designId?has_content>true<#else>false</#if>" data-target="printingOption">
                                            <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                                <div selection-removeonselect class="paddingLeft10 width40"><span class="selectCheckbox"></span></div>
                                                <div class="printingIconContainer"><i class="fa fa-pencil-square-o"></i></div>
                                                <div class="row">
                                                    <div class="selectionHeader jqs-optTestDesignsRightSideHide">Design Now</div>
                                                    <#if product.isMailable()?c == 'true' && (product.getProductType()?has_content && product.getProductType() == "ENVELOPE")><div class="selectionDescription"><i class="fa fa-circle"></i> Add a Return/Reply Address</div></#if>
                                                    <div class="selectionDescription"><i class="fa fa-circle"></i> Add a Logo, Browse Designs or Start from Scratch</div>
                                                    <div class="columns small-5 no-padding jqs-optTestDesignsRightSide hidden">
                                                        <div class="selectionHeader padding-left-xs">Design<br />Now</div>
                                                    </div>
                                                    <div class="columns small-2 no-padding jqs-optTestDesignsRightSide hidden" style="position: relative; top: 8px; font-size: 35px; font-weight: 200; color: #b5b5b5;">
                                                        |
                                                    </div>
                                                    <div class="columns small-5 no-padding jqs-optTestDesignsRightSide hidden">
                                                        <div class="selectionHeader">Add Return<br />Address</div>
                                                    </div>
                                                </div>
                                                <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                            </div>
                                        </div>
                                        <div bns-selection class="selectList jqs-selectList tablet-desktop-only" data-key="plainOrPrinted" data-target="printingOption" data-additionalkey="reuseDesign" data-additionalvalue="upload" data-reveal-id="startReuse" data-value="printed">
                                            <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                                <div selection-removeonselect class="paddingLeft10 width40"><span class="selectCheckbox"></span></div>
                                                <div class="printingIconContainer"><i class="fa fa-repeat"></i></div>
                                                <div class="row">
                                                    <div class="selectionHeader">Re-use a Previous Design</div>
                                                    <div class="selectionDescription"> Re-use a design from a previous order</div>
                                                </div>
                                                <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                            </div>
                                        </div>
                                        <div bns-selection bns-addreturnaddress class="selectList jqs-selectList tablet-desktop-only" data-key="plainOrPrinted" data-additionalkey="designOrUpload" data-value="printed" data-additionalvalue="design" data-target="printingOption">
                                            <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                                <div selection-removeonselect class="paddingLeft10 width40"><span class="selectCheckbox"></span></div>
                                                <div class="printingIconContainer"><i class="fa fa-align-left"></i></div>
                                                <div class="row">
                                                    <div class="selectionHeader">Add Return Address / Logo</div>
                                                    <div class="selectionDescription"> Add a simple return address to your envelopes</div>
                                                </div>
                                                <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                            </div>
                                        </div>
                                        <div bns-customization bns-selection class="selectList jqs-selectList tablet-desktop-only<#if product.hasAddressingAbility()?c == "false"> hidden</#if><#if !product.getDesigns()?has_content> hidden</#if>" data-key="plainOrPrinted" data-value="printed" data-additionalkey="addressing" data-additionalvalue="true" data-target="printingOption">
                                            <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                                <div selection-removeonselect class="paddingLeft10 width40"><span class="selectCheckbox"></span></div>
                                                <div class="printingIconContainer"><i class="fa fa-address-card-o"></i></div>
                                                <div class="row">
                                                    <div class="selectionHeader">Add Recipient Addressing</div>
                                                    <div class="selectionDescription">Upload or enter a list of recipients</div>
                                                </div>
                                                <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div bns-load_design class="margin-top-xxs margin-bottom-xxs blueButton hidden">
                            Get Started <i class="fa fa-caret-right"></i>
                        </div>
                        <div bns-uploadedfilesheader class="hidden uploadedFilesHeader" data-reveal-id="startUpload"><i class="fa fa-upload"></i> Upload a New File <i class="fa fa-caret-right"></i></div>
                        <div class="uploadedFiles hidden jqs-uploadedfiles no-margin"></div>
                        <div class="uploadedFiles hidden jqs-reusedfiles no-margin"></div>
                        <div class="option hidden tablet-desktop-only" bns-customization>
                            <div>
                                <div class="optionButton addRecipientAddressingButton jqs-addaddresses jqs-selectList" data-target="addressing" data-key="addresses" data-holder data-price data-selected="false" data-value="true" data-total="0" onclick="productPage.showGrid('manual', 0)">
                                    <img class="recipientAddressingBar" src="<@ofbizScene7Url>/is/image/ActionEnvelope/Recipient-Addressing-Button?fmt=jpeg&amp;qlt=90</@ofbizScene7Url>" alt="Add Recipient Addressing" />
                                    <div bns-recipientaddresscount class="recipientAddressCount hidden"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div id="startAddressing" class="reveal-modal addressingTemplateContainer jqs-addressingTemplateContainer" data-reveal>
                        <div>
                            <div class="padding-bottom-xxs popup-title">
                                <h3 class="padding-left-xxs">Choose an Addressing Style:</h3>
                                <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
                            </div>
                            <div class="row addressingTemplateBody">
                                <div class="addressingTemplateHelp jqs-addressingTemplateHelp">
                                    <div class="row addressingTemplateHelpHeader jqs-addressingTemplateHelpHeader">
                                        <div>Addressing Help:</div>
                                    </div>
                                    <div class="jqs-addressingTemplateHelpContainer addressingTemplateHelpContainer row">
                                        <div>
                                            <span>To Get Started: (This tool is used to add a different address to every envelope)</span>
                                            <ol>
                                                <li>Choose which style you would like to start with.</li>
                                                <li>Choose to either: upload your file, manually enter your addresses, or re-use a previous file.</li>
                                                <li>Click the orange next button in the right hand corner to move on.</li>
                                            </ol>
                                        </div>
                                    </div>
                                    <div class="button button-cta downloadTemplate"><a target="_blank" href="<@ofbizContentUrl>/html/files/addressingTemplate.csv</@ofbizContentUrl>">Download our template here</a></div>
                                    <div class="recipientAddressingPriceGuide">
                                        <div class="priceGuideHeader">
                                            Recipient Addressing Price guide
                                        </div>
                                        <div class="priceGuideContent">
                                            <div>1-99:</div>
                                            <div>$1.15 Per Address</div>
                                        </div>
                                        <div class="priceGuideContent">
                                            <div>100-199:</div>
                                            <div>$0.87 Per Address</div>
                                        </div>
                                        <div class="priceGuideContent">
                                            <div>200-299:</div>
                                            <div>$0.75 Per Address</div>
                                        </div>
                                        <div class="priceGuideContent">
                                            <div>300-499:</div>
                                            <div>$0.55 Per Address</div>
                                        </div>
                                        <div class="priceGuideContent">
                                            <div>500-999:</div>
                                            <div>$0.48 Per Address</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="addressingTemplateContent">
                                    <img class="recipientAddressingBar" alt="Recipient Addressing Pop Up Bar" src="<@ofbizContentUrl>/html/img/product/recipientAddressingPopupBar.png</@ofbizContentUrl>" />
                                    <div class="addressingTemplateListContainer">
                                        <div bns-address_font_list class="addressingTemplateList jqs-address jqs-scrollable">
                                            <div>
                                                <div class="selected" data-variable-style='{"fontSize": 16, "lineHeight": 1.1875, "textAlign": "center", "fontFamily": "Myriad Pro"}'>
                                                    <img src="<@ofbizContentUrl>/html/img/designer/11400.jpg</@ofbizContentUrl>" alt="Myriad Pro" />
                                                </div>
                                                <span>Myriad Pro</span>
                                            </div><div>
                                                <div data-variable-style='{"fontSize": 16, "lineHeight": 1.1875, "textAlign": "center", "fontFamily": "Minion Pro"}'>
                                                    <img src="<@ofbizContentUrl>/html/img/designer/11401.jpg</@ofbizContentUrl>" alt="Minion Pro" />
                                                </div>
                                                <span>Minion Pro</span>
                                            </div><div>
                                                <div data-variable-style='{"fontSize": 16, "lineHeight": 1.1875, "textAlign": "center", "fontFamily": "Poor Richard"}'>
                                                    <img src="<@ofbizContentUrl>/html/img/designer/11402.jpg</@ofbizContentUrl>" alt="Poor Richard" />
                                                </div>
                                                <span>Poor Richard</span>
                                            </div><div>
                                                <div data-variable-style='{"fontSize": 16, "lineHeight": 1.1875, "textAlign": "center", "fontFamily": "Mrs Eaves OT"}'>
                                                    <img src="<@ofbizContentUrl>/html/img/designer/11404.jpg</@ofbizContentUrl>" alt="Mrs Eaves OT" />
                                                </div>
                                                <span>Mrs Eaves OT</span>
                                            </div><div>
                                                <div data-variable-style='{"fontSize": 16, "lineHeight": 1.1875, "textAlign": "center", "fontFamily": "Trajan Pro"}'>
                                                    <img src="<@ofbizContentUrl>/html/img/designer/11405.jpg</@ofbizContentUrl>" alt="Trajan Pro" />
                                                </div>
                                                <span>Trajan Pro</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="padding-left-xs padding-right-xs padding-bottom-xs">
                                        <h4>Choose data Method:</h4>
                                        <div class="jqs-addressOptions addressingOptions envRow margin-top-xxs">
                                            <div class="envCol-3 selected selectionButton" data-addressing-option="upload">
                                                Upload Data File
                                            </div>
                                            <div class="padding-left-xs"></div>
                                            <div class="envCol-3 selectionButton" data-addressing-option="manual">
                                                Manually Enter Addresses
                                            </div>
                                            <div class="padding-left-xs"></div>
                                            <div class="envCol-3 selectionButton" data-addressing-option="existing">
                                                Choose Existing List
                                            </div>
                                            <div class="padding-left-xs"></div>
                                            <div class="button button-cta right jqs-calltexel jqs-variabledatagrid variableDataGridButton">
                                                Next <i class="fa fa-caret-right"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="optionContentBlock option hidden" bns-customization bns-rush_production>
                        <h5>Production Time: </h5>
                        <div>
                            <div id="productionTime" class="selectList selectListParent sidebarToggle jqs-sidebarToggle productionTime" data-sidebar-name="sidebar-productionTime" selection-selectlistname="productionTime" data-ignorecaret="">
                                <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                    <div class="printingIconContainer"><i class="fa fa-envelope"></i></div>
                                    <div class="selectionHeader">Standard (${product.getLeadTime().leadTimeStandardPrinted?default("3")} days)</div>
                                    <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="sidebar-productionTime" class="sidebarPanel colorList jqs-scrollable">
                        <div class="colorTextureHeading">
                            <div class="stickyTextureHeading">
                                <h4><i class="fa fa-caret-left"></i>Production Time</h4>
                            </div>
                        </div>
                        <div class="colorTextureBody">
                            <div class="colorTextureBodyInner" style="padding: 0;">
                                <div bns-selection class="selectList jqs-selectList slSelected" data-price data-target="productionTime" data-key="isRush" data-value="false">
                                    <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                        <div selection-removeonselect class="paddingLeft10 width40"><span class="selectCheckbox"></span></div>
                                        <div class="printingIconContainer"><i class="fa fa-envelope"></i></div>
                                        <div class="selectionHeader">Standard (${product.getLeadTime().leadTimeStandardPrinted?if_exists} days)</div>
                                        <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                    </div>
                                </div>
                                <div bns-selection class="selectList jqs-selectList<#if product.hasRush()?c == "false"> hidden</#if>" data-price data-target="productionTime" data-key="isRush" data-value="true">
                                    <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                        <div selection-removeonselect class="paddingLeft10 width40"><span class="selectCheckbox"></span></div>
                                        <div class="printingIconContainer"><i class="fa fa-envelope"></i></div>
                                        <div class="selectionHeader">Rush Production (${product.getLeadTime().leadTimeRushPrinted?if_exists} days)</div>
                                        <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="optionContentBlock option jqs-quantityPriceSelection no-margin">
                        <h5>Quantity &amp; Price: <i class="env-tooltip fa fa-question-circle" data-reveal-id="quantityAndPrice"></i></h5>
                        <span class="orderSamplesQuickLink jqs-orderSamplesQuickLink<#if product.hasSample()?string("true", "false") == "false"> hidden</#if>" data-reveal-id="orderSamples">Order Samples</span>
                        <div>
                            <#list product.getPrices().keySet() as quantities>
                                <#if quantities_index == 0>
                                    <#assign smallestQuantity = quantities />
                                    <#assign lowestPrice = product.getPrices().get(quantities).price />
                                    <div id="quantityPriceSelection" class="qpsListItems selectList selectListParent quantityPriceSelection sidebarToggle jqs-sidebarToggle jqs-quantityTest" data-sidebar-name="sidebar-quantityList" selection-selectlistname="quantityList">
                                        <div class="width95 quantityDisplay">${quantities?string[",##0"]} Qty.</div>
                                        <div class="priceDisplay">$${product.getPrices().get(quantities).price?string[",##0.##"]}</div>
                                        <#if product.percentSavings()?default("0") == 0><div class="jqs-pricePerUnit pricePerUnitDisplay">${(product.getPrices().get(quantities).price?number / quantities?string["##0"]?number)?string.currency}/each</div></#if>
                                        <div class="jqs-subfreeshipnote freeshipnote <#if product.getPrices().get(quantities).price lt globalContext.freeShippingAmount?default("250")>hidden</#if>"> + <i class="fa fa-truck"></i> Ships FREE w/code</div>
                                        <div><i class="fa fa-caret-right productCaretSidebar"></i></div>
                                    </div>
                                    <#break>
                                </#if>
                            </#list>
                            <div id="sidebar-quantityList" class="sidebarPanel quantityList jqs-scrollable">
                                <div class="jqs-qlError hidden qlError">
                                    Error
                                </div>
                                <div class="colorTextureHeading">
                                    <div class="stickyTextureHeading">
                                        <h4><i class="fa fa-caret-left"></i>Quantity &amp; Pricing</h4>
                                    </div>
                                </div>
                                <div class="colorTextureBody">
                                    <div class="colorTextureBodyInner jqs-scrollable" style="padding: 0;">
                                        <div class="jqs-pricelist">
                                            <#list product.getPrices().keySet() as quantities>
                                                <#if !quantities_has_next>
                                                    <#assign largestQuantity = quantities />
                                                    <#assign highestPrice = product.getPrices().get(quantities).price />
                                                </#if>

                                                <div bns-selection class="qpsListItems no-padding jqs-selectList selectList <#if quantities_index == 0> slSelected</#if>" data-target="quantityPriceSelection" data-qty="${quantities}" data-price="${product.getPrices().get(quantities).price}" data-originalPrice="<#if product.percentSavings()?default("0") gt 0 && product.getOriginalPrices().get(quantities).price?exists>${product.getOriginalPrices().get(quantities).price}<#else>${product.getPrices().get(quantities).price}</#if>">
                                                    <div selection-removeonselect class="width30"><span class="selectCheckbox"></span></div>
                                                    <div class="width95 quantityDisplay">${quantities?string[",##0"]} Qty.</div>
                                                    <div selection-removeonselect class="originalPriceDisplay<#if product.percentSavings()?default("0") == 0> hidden</#if>"><#if product.percentSavings()?default("0") gt 0 && product.getOriginalPrices().get(quantities).price?exists><strike>$${product.getOriginalPrices().get(quantities).price?string[",##0.##"]}</strike></#if></div>
                                                    <div class="priceDisplay">$${product.getPrices().get(quantities).price?string[",##0.##"]}</div>
                                                    <div selection-removeonselect class="savingsDisplay<#if product.percentSavings()?default("0") == 0> hidden</#if>"><#if product.percentSavings()?default("0") gt 0>Save ${product.percentSavings()?default("0")}%</#if></div>
                                                    <#if product.percentSavings()?default("0") == 0><div class="jqs-pricePerUnit pricePerUnitDisplay">${(product.getPrices().get(quantities).price?number / quantities?string["##0"]?number)?string.currency}/each</div></#if>
                                                    <div class="jqs-subfreeshipnote freeshipnote <#if product.getPrices().get(quantities).price lt globalContext.freeShippingAmount?default("250")>hidden</#if>"> + <i class="fa fa-truck"></i> Ships FREE w/code</div>
                                                    <div selection-showonselect><i class="fa fa-caret-right productCaretSidebar"></i></div>
                                                </div>
                                            </#list>
                                        </div>
                                        <div class="customQuantity row <#if product.hasCustomQty()?c == "false">hidden</#if>">
                                            <div class="left">
                                                <span class="tablet-desktop-only-inline-block jqs-quantityType customQuantityText">Custom Quantity:</span> <input class="jqs-number customQty" type="text" placeholder="Enter Your Qty." value="" name="CUSTOM" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div data-reveal-id="emailThisQuote" class="aStyle right">
                                Email This Quote
                            </div>
                        </div>
                    </div>
                <#else>
                    <div class="discontinuedText">Discontinued</div>
                </#if>
            </div>
            <div class="productTools margin-top-xxs productPageTotal row">
                <div bns-addtocarttesta class="button-cta addToCart jqs-addToCart testA noMargin">Add to Cart</div>
                <div class="hidden" data-dropdown-target="dropdown-addtocartTestA" data-dropdown-options="click reverse-horizontal ignore-reverse-dropdown shadowed(myBody)" data-dropdown-alternate-parent="cartContainer" style=""></div>
            </div>
            <div class="jqs-longdescParent <#if !product.getDescription()?has_content>hidden</#if> margin-top-xs productTools">
                <h4>Style Description</h4>
                <div class="jqs-longdesc longdesc" property="description">
                ${product.getDescription()?if_exists}
                </div>
            </div>
            <div class="jqs-colordescParent <#if !product.getColorDescription()?has_content>hidden</#if> margin-top-xs productTools">
                <h4>Color Description</h4>
                <div class="jqs-colordesc colordesc">
                ${product.getColorDescription()?if_exists}
                </div>
            </div>
        </div>
    </div>

    <div id="emailThisQuote" class="reveal-modal reveal-modal-limiter samplesPopup" data-reveal>
        <div>
            <div class="padding-bottom-xxs popup-title">
                <h3 class="padding-left-xxs">Email Quote</h3>
                <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
            </div>
            <div id="quoteForm" data-bigNameValidateForm="quoteForm" bns-email_this_quote="default">
                <div class="padding-xxs">
                    <div data-bignamevalidateid="emailAddress">To:</div>
                    <input type="text" name="quoteToEmailAddress" placeholder="Email Address" data-bigNameValidate="emailAddress" data-bigNameValidateType="required email" data-bignamevalidateerrortitle="Email Address" />
                    <div>From:</div>
                    <input type="text" name="quoteFromEmailAddress" placeholder="Email Address" />
                    <div>Message:</div>
                    <textarea name="quoteMessage"></textarea>
                </div>
                <div class="row">
                    <div bns-quote_submit_button data-bigNameValidateSubmit="quoteForm" data-bigNameValidateAction="submitQuote" class="button button-cta right margin-xxs">Submit Quote</div>
                </div>
            </div>
            <div class="hidden" bns-email_this_quote="success">
                <div class="padding-xxs">
                    <p>
                        Your quote has been sent successfully.
                    </p>
                </div>
            </div>
        </div>
    </div>

    <!-- CROSS SELL / RECOMMENDATIONS -->
    <!--<#if crossSells?has_content && crossSells.size() gt 0>
        <div class="jqs-cross-sells matchingProducts margin-top-xs">
            <h4>Recommended Products</h4>
            <div class="slideIt-container padding-xxs margin-top-xxs">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt text-left productImageSlideIt">
                    <div>
                        <#list crossSells as crossSell><div class="miniProductDisplay">
                            <a data-sku="${crossSell.getId()?if_exists}" href="<@ofbizUrl>${crossSell.getUrl(false)?default("")?replace("&#x2f;", "/")?replace("&#x7e;", "~")?replace("&#x3d;", "=")}</@ofbizUrl>">
                                <img alt="${crossSell.getName()?if_exists} ${crossSell.getColor()?if_exists}" class="jqs-defer-img" src="data:image/png;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=" data-src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${crossSell.getId()}?wid=125&amp;hei=100&amp;${imgFmtQltyLtGry}" />
                                <div class="mpd-itemName"><p>${crossSell.getName()?if_exists}</p></div>
                                <p class="mpd-itemSku">${crossSell.getId()?if_exists}</p>
                                <p class="mpd-itemColor">${crossSell.getColor()?if_exists}</p>
                                <div class="mpd-row">
                                    <div class="rating-${crossSell.getRating()?if_exists}"></div>
                                    <p class="mpd-itemPrice">From ${crossSell.getPlainPriceDescription()?if_exists}</p>
                                </div>
                            </a>
                        </div></#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
        </div>
    </#if>-->

    <div id="reviews" class="customer-reviews margin-top-xxs">
    ${screens.render("component://envelopes/widget/ProductScreens.xml#reviews")}
    </div>
    <div class="hidden">
        <span property="brand" class="brand">Envelopes.com</span>
        <span property="offers" typeof="AggregateOffer">
            <span property="lowPrice" class="price"><#if lowestPrice?exists>${lowestPrice?string["0.##"]}</#if></span>
            <span property="highPrice"><#if highestPrice?exists>${highestPrice?string["0.##"]}</#if></span>
            <meta property="priceCurrency" content="USD" />
            <link property="availability" href="https://schema.org/InStock"/>
            <link property="itemCondition" href="https://schema.org/NewCondition"/>
        </span>
    </div>
</div>

<div id="orderSamples" class="reveal-modal reveal-modal-limiter samplesPopup" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Order Samples</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="padding-xxs">
            Samples are $1 each. Maximum of 5 samples per color. Shipped via USPS.<br />
            Every $1 spent on samples (up to $5) can be used as
            a discount on your next order of $20+.
            <br />
            Discount code will be sent via email.
        </div>
        <div class="samplesHeader">
            <div class="samplesCol1">Color</div>
            <div class="samplesCol2">Weight</div>
            <div class="samplesCol3">Quantity</div>
            <div class="samplesCol4">Price</div>
            <div class="samplesCol5"></div>
        </div>
        <div class="samplesPopupBody"></div>
        <div class="samplesPopupBody jqs-samplesPopupBody text-center"></div>
        <div class="row text-center margin-top-xs">
            <a href="<@ofbizUrl>/cart</@ofbizUrl>" class="right margin-right-xs margin-bottom-xs">View Cart</a>
        </div>
    </div>
</div>
<div id="quantityAndPrice" class="reveal-modal reveal-modal-limiter infoPopup" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Quantity &amp; Price - Information</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="infoPopupBody">
            <div>Need to know more about Quantity &amp; Price?</div>
            <p>
                <strong>Custom Quantity:</strong><br />
                Don't see the exact quantity you are looking for listed here? Type the custom quantity you'd like to order into the
                "Custom Quantity" box at the top of the standard quantity options. Your custom quantity must be in increments of 50.
                <br /><br />
                <strong>Order a Sample:</strong><br />
                Would you like to order a sample? Open the "Quantity and Price" menu, and click the "Order Samples" checkbox at the
                top of the menu. You cannot order printed samples, just plain samples. Type the amount of samples you would like to
                order into the "Custom Quantity" box, then click "Add to Cart"
            </p>
        </div>
        <div class="row text-center margin-top-xs">
            <div class="close-reveal-modal button button-cta padding-xxs">Close This Window</div>
        </div>
    </div>
</div>
<div id="paperWeightInfo" class="reveal-modal reveal-modal-limiter infoPopup paperWeightGuide" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Paper Weight Guide</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="infoPopupBody padding-xs">
            <p>
                Paperweight is measured in lbs. (pounds) and refers to the thickness and sturdiness of a sheet of paper.
                The higher the number, the thicker the paper or cardstock.  Use the "Thickness" column on the far right of this chart to compare the thickness of different paperweights.
                <br />
                **NOTE** Paperweight does not refer to the actual weight of a single piece of paper.
            </p>

            <table class="margin-top-xxs">
                <thead>
                <tr>
                    <th>Paper &amp; Envelopes<br />(Text)</th>
                    <th>Cardstock &amp; Notecards<br />(Cover)</th>
                    <th>Metric<br />(grams/sq meter)</th>
                    <th>Thickness<br />(mm)</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>20lb./50lbs.</td>
                    <td>-</td>
                    <td>74 gsm</td>
                    <td>0.097</td>
                </tr>
                <tr>
                    <td>24lb./60lbs.</td>
                    <td>-</td>
                    <td>89gsm</td>
                    <td>0.12</td>
                </tr>
                <tr>
                    <td>28lb/70lbs.</td>
                    <td>-</td>
                    <td>104 gsm</td>
                    <td>0.147</td>
                </tr>
                <tr>
                    <td>73lbs.</td>
                    <td>-</td>
                    <td>109gsm</td>
                    <td>0.152</td>
                </tr>
                <tr>
                    <td>80lbs.</td>
                    <td>-</td>
                    <td>120 gsm</td>
                    <td>0.155</td>
                </tr>
                <tr>
                    <td>90lbs.</td>
                    <td>-</td>
                    <td>133 gsm</td>
                    <td>0.157</td>
                </tr>
                <tr>
                    <td>100lbs.</td>
                    <td>-</td>
                    <td>148 gsm</td>
                    <td>0.183</td>
                </tr>
                <tr>
                    <td>110lbs.</td>
                    <td>60lbs.</td>
                    <td>163 gsm</td>
                    <td>0.188</td>
                </tr>
                <tr>
                    <td>137lbs.</td>
                    <td>75lbs.</td>
                    <td>178 gsm</td>
                    <td>0.229</td>
                </tr>
                <tr>
                    <td>-</td>
                    <td>80lbs.</td>
                    <td>216 gsm</td>
                    <td>0.234</td>
                </tr>
                <tr>
                    <td>-</td>
                    <td>90lbs.</td>
                    <td>244 gsm</td>
                    <td>0.241</td>
                </tr>
                <tr>
                    <td>-</td>
                    <td>93lbs.</td>
                    <td>252 gsm</td>
                    <td>0.25</td>
                </tr>
                <tr>
                    <td>-</td>
                    <td>100lbs.</td>
                    <td>271 gsm</td>
                    <td>0.289</td>
                </tr>
                <tr>
                    <td>-</td>
                    <td>105lbs.</td>
                    <td>286 gsm</td>
                    <td>0.33</td>
                </tr>
                <tr>
                    <td>-</td>
                    <td>120lbs.</td>
                    <td>312 gsm</td>
                    <td>0.38</td>
                </tr>
                <tr>
                    <td>-</td>
                    <td>146lbs.</td>
                    <td>385 gsm</td>
                    <td>0.445</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div id="startUpload" class="reveal-modal reveal-modal-limiter uploadContainer" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Use a Complete Design</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="padding-top-xxs padding-bottom-xxs padding-left-xs padding-right-xs">
            <div class="padding-top-xxs padding-bottom-xxs padding-left-xs padding-right-xs uploadBackgroundColor">
                <p class="blackBold margin-top-xxs">Upload Your Art</p>
                <form id="uploadScene7Files" method="POST" action="<@ofbizUrl>/uploadScene7Files</@ofbizUrl>" enctype="multipart/form-data">
                    <input type="file" name="fileUpload" class="jqs-fileupload" multiple />
                    <div class="fileContainer jqs-filecontainer">
                        <div class="dropzone placeholder">
                            <span bns-removeonupload><i class="fa fa-upload"></i> Upload or Drag Files Here</span>
                            <div bns-removeonupload>
                                <span class="blackBold">Accepted File Types:</span>
                                <img src="/html/img/product/popups/allowedFileTypes.png" alt="accepted file types" />
                            </div>
                        </div>
                    </div>
                </form>
                <div class="sendFilesLater">
                    <input type="checkbox">
                    <p>I'll send my file later. Email files to <span>prepress@envelopes.com</span></p>
                </div>
                <div>
                    <p class="blackBold margin-top-xxs">Comments:</p>
                    <textarea class="jqs-itemcomments"></textarea>
                </div>  
                <div class="optionRow">
                    <div>
                        <p class="blackBold margin-top-xxs">Colors on Front</p>
                        <div id="inkFrontSelection" class="selectList selectListParent qpsListItems marginTop" data-dropdown-target="dropdown-inkFrontSelection" data-dropdown-options="click ignore-reverse-dropdown shadowed(inkFrontSelection)">
                            <div class="foldersTabularRow folderDisplayTable productColorSelection">
                                <div class="no-padding">1 Color</div>
                                <div class="no-padding"><i class="fa fa-caret-right productCaretSidebar"></i></div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <p class="blackBold margin-top-xxs">Colors on Back</p>
                        <div id="inkBackSelection" class="selectList selectListParent qpsListItems" data-dropdown-target="dropdown-inkBackSelection" data-dropdown-options="click ignore-reverse-dropdown shadowed(inkBackSelection)">
                            <div class="foldersTabularRow folderDisplayTable productColorSelection">
                                <div class="no-padding">None</div>
                                <div class="no-padding"><i class="fa fa-caret-right productCaretSidebar"></i></div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <p class="blackBold margin-top-xxs">Quantity &amp; Price: <i class="env-tooltip fa fa-question-circle" data-reveal-id="quantityAndPrice"></i></p>
                        <div>
                            <div bns-sidebarid="quantityPriceSelection" class="qpsListItems selectList selectListParent quantityPriceSelection sidebarToggle jqs-sidebarToggle jqs-quantityTest" data-sidebar-name="sidebar-quantityList" selection-selectlistname="quantityList">
                                <div class="uploadQuantityDisplay"></div>
                                <div class="uploadPriceDisplay"></div>
                                <div><i class="fa fa-caret-right productCaretSidebar"></i></div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div class="button button-cta padding-xxs jqs-closemodal">Next <i class="fa fa-caret-right"></i></div>
                    </div>
                </div>
                <div class="uploadNote margin-top-xs"><span>*Please Note:</span> Metallic ink can only be printed on quantities over 500</div>
            </div>
        </div>
        <div class="row">
            <div class="left margin-left-xxs margin-right-xxs padding-left-xxs padding-right-xxs margin-bottom-xxs reviewProofText">
                <h5>Important Information: (Please read)</h5>
                <p class="margin-top-xxs">1.) Proofs are ready for review within 24 hours of order placement or sooner. Once you approve your proof, your order will enter print production.</p>
                <p class="margin-top-xxs margin-bottom-xxs">2.) The Production Time Clock starts at Date and Time of Approval. <span>Before adding to cart, choose the production time that best suits your needs. (Standard or RUSH)</span></p>
                <p><span>Please Note: </span>Proof Approvals after 5pm EST are considered next business day. If you do not approve your proof within 5 business days, your order will automatically enter production.</p>
            </div>
        </div>
    </div>
    <div id="dropdown-inkFrontSelection" class="drop-down inkFrontList selectListDropDown">
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 1> slSelected</#if>" data-price="" data-target="inkFrontSelection" data-key="colorsFront" data-value="0" data-selected="false"><div>None</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 1> hidden<#else> slSelected</#if>" data-price="" data-target="inkFrontSelection" data-key="colorsFront" data-value="1" data-selected="true"><div>1 Color</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 2> hidden</#if>" data-price="" data-target="inkFrontSelection" data-key="colorsFront" data-value="2" data-selected="false"><div>2 Color</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 4> hidden</#if>" data-price="" data-target="inkFrontSelection" data-key="colorsFront" data-value="4" data-selected="false"><div>3-4 Color</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.hasWhiteInk()?c == "false"> hidden</#if>" data-price="" data-target="inkFrontSelection" data-key="colorsFront" data-value="whiteInkFront" data-selected="false"><div>White Ink</div></div>
    </div>
    <div id="dropdown-inkBackSelection" class="drop-down inkBackList selectListDropDown">
        <div bns-selection class="jqs-selectList selectList slSelected" data-price="" data-target="inkBackSelection" data-key="colorsBack" data-value="0" data-selected="true"><div>None</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 1> hidden</#if>" data-price="" data-target="inkBackSelection" data-key="colorsBack" data-value="1" data-selected="false"><div>1 Color</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 2> hidden</#if>" data-price="" data-target="inkBackSelection" data-key="colorsBack" data-value="2" data-selected="false"><div>2 Color</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 4> hidden</#if>" data-price="" data-target="inkBackSelection" data-key="colorsBack" data-value="4" data-selected="false"><div>3-4 Color</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.hasWhiteInk()?c == "false"> hidden</#if>" data-price="" data-target="inkBackSelection" data-key="colorsBack" data-value="whiteInkBack" data-selected="false"><div>White Ink</div></div>
    </div>
</div>
<div id="startDesign" class="reveal-modal designTemplateContainer jqs-designTemplateContainer tablet-desktop-only" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Choose a Design Template</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="row designTemplateBody">
            <div class="designTemplateHelp jqs-designTemplateHelp">
                <div class="row designTemplateHelpHeader jqs-designTemplateHelpHeader">
                    <div>Design Template Help:</div>
                </div>
                <div class="jqs-designTemplateHelpContainer designTemplateHelpContainer row">
                    <div>
                        <span>To Get Started:</span>
                        <ol>
                            <li>Choose the template you would like to customize.</li>
                            <li>Click the orange "Customize" button in the bottom right corner.</li>
                            <li>Use our online designer to customize your product.</li>
                        </ol>
                    </div>
                    <div>
                        <div style="color: #000000; font-size: 16px; font-weight: bold;">Need Recipient Addressing? <span style="font-weight: 400; font-size: 14px;">(different address on each envelope)</span>:</div>
                        You can utilize that service from the product page by choosing "recipient addressing". This can be done before or after you
                        customize one of these templates, or as a stand-alone service.
                    </div>
                </div>
            </div>
            <div>
                <div class="designNote"><span style="color: #ff0000;">*Please Note:</span> Metallic ink can only be printed on quantities over 500</div>
                <div class="designTemplateListContainer">
                    <div bns-design_template_list class="designTemplateList jqs-scrollable">
                        <#list product.getDesigns() as productDesign>
                            <#if productDesign.get("active") == "Y">
                                <div>
                                    <div bns-design data-design="${productDesign.get("designTemplateId")}" data-design_type="${productDesign.get("name")}">
                                        <img bns-design_image src="<@ofbizContentUrl>/html/img/designs/thumbnails/${productDesign.get("designTemplateId")}_${productDesign.get("location")}.png" alt="${productDesign.get("name")}</@ofbizContentUrl>" style="background-color: #${product.getHex()?default("#FFFFFF")};" />
                                    </div>
                                    <p style="text-transform: uppercase;">${productDesign.get("name")}</p>
                                </div>
                            </#if>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="startReuse" class="reveal-modal reuseContainer" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Choose Previous Artwork Files:</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="padding-xs jqs-reusehistory reuseHistory jqs-scrollable">
            <div class="not-logged-in">
                You are not logged in, please <span>click here</span> to login and view your file history.
            </div>
        </div>
        <div class="row padding-right-xs">
            <div class="button button-cta right padding-xxs jqs-closemodal">Save &amp; Close <i class="fa fa-caret-right"></i></div>
        </div>
    </div>
</div>
<div id="optEnvAction" class="reveal-modal reveal-modal-limiter tablet-desktop-only" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">You did not Upload Your File!</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="row text-center margin-top-xs">
            <div class="button button-cta padding-xxs jqs-addToCart" data-ignore="true">I only want Recipient Addressing</div>
        </div>
        <div class="row text-center margin-top-xs">
            <div class="close-reveal-modal button button-cta padding-xxs">I want to upload a file</div>
        </div>
    </div>
</div>
<div id="plainOrPrinted" class="reveal-modal reveal-modal-limiter infoPopup tablet-desktop-only" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Plain or Printed - Information</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="infoPopupBody">
            <div>Would you like your item Plain or Printed?</div>
            <p>
                Nearly every item is available plain, or with custom printing.
                <br /><br />
                <strong>PLAIN:</strong><br />
                Choose "Plain" if you would like this item without any printing, in other words, blank.
                <br /><br />
                <strong>ADD PRINTING:</strong><br />
                Choose the "Add Printing Now" option to design your item online, to upload files that you've already created, to reorder a printed
                item that you've purchased in the past and/or to add recipient addressing to your envelopes. (Recipient Addressing means
                pre-addressed envelopes)
            </p>
        </div>
        <div class="row text-center margin-top-xs">
            <div class="close-reveal-modal button button-cta padding-xxs">Close This Window</div>
        </div>
    </div>
</div>
<div id="printingOptions" class="reveal-modal reveal-modal-limiter infoPopup tablet-desktop-only" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Printing Options - Information</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="infoPopupBody">
            <div>Need to know more about Printing Options?</div>
            <p>
                Printing and Customization made easy! Ready to customize your item? Choose one of the following options:
                <br /><br />
                <strong>Design Now:</strong>
                Use our easy-to-use online designer. Choose one of our ready-made templates, or start from scratch!
                <br /><br />
                <strong>Use a Complete Design:</strong>
                Choose this option if your print files are ready to go. Uploading your own artwork allows for full creative
                control over your project and comes with a Free Digital Proof to ensure your satisfaction. If you've already
                used our printing services and would like to place a re-order, simply log into your account and re-order with
                the same artwork. You can also choose to make change to a past order!
            </p>
        </div>
        <div class="row text-center margin-top-xs">
            <div class="close-reveal-modal button button-cta padding-xxs">Close This Window</div>
        </div>
    </div>
</div>
<div id="recipientAddressing" class="reveal-modal reveal-modal-limiter infoPopup tablet-desktop-only" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Recipient Addressing, Explained:</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="infoPopupBody">
            <div>Need to know more about Recipient Addressing?</div>
            <p>
                Take the hassle out of addressing your own envelopes! Select Recipient Addressing, choose the way you'd like your addresses to be
                displayed, and simply upload or create your address list. You'll be able to preview each and every address! You can edit fonts,
                sizes, spacing, placement, colors and so much more.
            </p>
            <div class="price-guide text-center margin-top-xxs">
                <div class="padding-xxs">
                    <div>Recipient Addressing Price Guide</div>
                </div>
                <div>
                    <div class="guide-info text-center">
                        <span class="addressing-amount">1-99:</span>
                        <span class="price-per-address">$1.15 per address</span>
                    </div>
                    <div class="guide-info text-center">
                        <span class="addressing-amount">100-199:</span>
                        <span class="price-per-address">$0.87 per address</span>
                    </div>
                    <div class="guide-info text-center">
                        <span class="addressing-amount">200-299:</span>
                        <span class="price-per-address">$0.75 per address</span>
                    </div>
                    <div class="guide-info text-center">
                        <span class="addressing-amount">300-499:</span>
                        <span class="price-per-address">$0.55 per address</span>
                    </div>
                    <div class="guide-info text-center">
                        <span class="addressing-amount">500-999:</span>
                        <span class="price-per-address">$0.48 per address</span>
                    </div>
                    <div class="guide-info text-center">
                        <span class="addressing-amount">Over 1,000:</span>
                        <span class="price-per-address">$0.38 per address</span>
                    </div>
                </div>
            </div>
            <iframe src="//fast.wistia.net/embed/iframe/j5j6iqnbvu" allowtransparency="true" frameborder="0" scrolling="no" class="wistia_embed" name="wistia_embed" allowfullscreen mozallowfullscreen webkitallowfullscreen oallowfullscreen msallowfullscreen width="640" height="360"></iframe>
        </div>
        <div class="row text-center margin-top-xs">
            <div class="close-reveal-modal button button-cta padding-xxs">Close This Window</div>
        </div>
    </div>
</div>
<div id="productionTime" class="reveal-modal reveal-modal-limiter infoPopup tablet-desktop-only" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Production Time - Information</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="infoPopupBody">
            <div>Need to know more about Production Time?</div>
            <p>
                <strong>Standard Production:</strong>
                Proofs are ready for review on the next business day. Your print order will ship ${product.getLeadTime().leadTimeStandardPrinted?if_exists} business days after proof approval.
                <br /><br />
                <strong>Rush Production:</strong>
                Proofs are ready for review on the same business day. Your print order will ship ${product.getLeadTime().leadTimeRushPrinted?if_exists} business days after proof approval.
            </p>
        </div>
        <div class="row text-center margin-top-xs">
            <div class="close-reveal-modal button button-cta padding-xxs">Close This Window</div>
        </div>
    </div>
</div>
<div id="colorsBackFront" class="reveal-modal reveal-modal-limiter infoPopup tablet-desktop-only" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Colors on Front/Back - Information</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="infoPopupBody">
            <div>Printing Options Explained</div>
            <p>
                <strong>One Color Printing:</strong><br />
                One color printing means that your design contains only one ink color. Gray scale designs,
                contain a range of grays, and is also considered one color printing
                <br /><br />
                <strong>Two-Color Printing:</strong><br />
                Two color printing means that your design contains two different ink colors.
                <br /><br />
                <strong>Full-Color Printing:</strong><br />
                All print designs with more than two colors require an ink process called full color printing,
                which is also known as CMYK. CMYK stands for Cyan, Magenta, Yellow and Key (key means black).
                These four ink colors can be combined to print an unlimited number of colors. Remember, if
                your design contains more than two colors, select full color.
                <br /><br />
                <strong>White Ink Printing:</strong><br />
                White ink is a special, unique ink that prints best on darker colored papers and stocks.
                White ink can be printed with CMYK printing if the design contains white and additional color elements.
            </p>
            <iframe src="//fast.wistia.net/embed/iframe/89hcdokv1z" allowtransparency="true" frameborder="0" scrolling="no" class="wistia_embed" name="wistia_embed" allowfullscreen mozallowfullscreen webkitallowfullscreen oallowfullscreen msallowfullscreen width="640" height="360"></iframe>
        </div>
        <div class="row text-center margin-top-xs">
            <div class="close-reveal-modal button button-cta padding-xxs">Close This Window</div>
        </div>
    </div>
</div>
<div id="uploadImage" class="reveal-modal reveal-modal-limiter uploadContainer" data-reveal>
    <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Upload an Image</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="padding-top-xxs padding-bottom-xxs padding-left-xs padding-right-xs">
            <div>Upload Your Own Images:</div>
            <div>Supported Files: AI, EPS, BMP, JPEG, PNG, GIF, TIFF</div>
            <div>20MB File Size Maximum.</div>
            <form id="uploadDesignerImage" method="POST" action="about:blank" enctype="multipart/form-data" target="uploadTarget">
                <input type="file" name="uploadImage" bns-designuploadimage/>
                <iframe src="about:blank" id="uploadTarget" name="uploadTarget" src="#" style="width:0;height:0;border:0px solid #fff;"></iframe>
                <div class="button button-cta designUploadImageButton">Upload Image</div>
            </form>
        </div>
    </div>
    <#--  <div>
        <div class="padding-bottom-xxs popup-title">
            <h3 class="padding-left-xxs">Use a Complete Design</h3>
            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
        </div>
        <div class="padding-top-xxs padding-bottom-xxs padding-left-xs padding-right-xs">
            <div class="padding-top-xxs padding-bottom-xxs padding-left-xs padding-right-xs uploadBackgroundColor">
                <p class="blackBold margin-top-xxs">Upload Your Art</p>
                <form id="uploadDesignerImage" method="POST" action="about:blank" enctype="multipart/form-data" target="uploadTarget">
                    <input type="file" name="uploadImage" />
                    <iframe src="about:blank" id="uploadTarget" name="uploadTarget" src="#" style="width:0;height:0;border:0px solid #fff;"></iframe>
                </form>
            </div>
        </div>
    </div>      -->
</div>
<div id="pp_inline_div" style="width:1200px; height: 0; margin: 0 auto; position: relative; overflow: hidden;"></div>

<!-- Add to Cart Test A - Pop Up Box -->
<div id="dropdown-addtocartTestA" class="drop-down addToCartTestA" style="right: 20px;">
    <div class="popup-border-fade">
        <div class="paddingTestA">
            <h4>This item has been added to your cart</h4>
            <div class="addToCartProductInfoTestA"></div>
        </div>
        <div class="addToCartPriceInfoTestA"></div>
        <div bns-certonaloadlist class="paddingTestA">
            <div class="addToCartContinueShoppingA">Continue Shopping</div>
            <a href="<@ofbizUrl>/cart</@ofbizUrl>"><div class="addToCartCheckoutA">Checkout</div></a>
        </div>
    </div>
</div>

<div id="dropdown-quantityList2" class="drop-down selectListDropDown quantityList"></div>

<link href="<@ofbizContentUrl>/html/addressing/slideout.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/addons/jquery.minicolors.min.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/texel/texel.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/addressing/grid1.min.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />

<script src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.iframe-transport.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.fileupload.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/js/addons/jquery.minicolors.min.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/texel/jquery.env.queue.min.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script src="<@ofbizContentUrl>/html/texel/jquery.texel.widget.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script src="<@ofbizContentUrl>/html/lib/envelopes-widget-util.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.grid.min.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.grid.proxy.min.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.variabledata.min.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script src="<@ofbizContentUrl>/html/js/texel/texel_739_product.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script src="<@ofbizContentUrl>/html/js/product/productEvents.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script src="//fast.wistia.com/assets/external/E-v1.js" async></script>
<script>
    var insertIntoArrayIndex = function(array, index, value, hard) {
        if (typeof array != 'undefined' && typeof index != 'undefined' && typeof value != 'undefined') {
            if ((typeof hard == 'boolean' && hard) || (typeof hard == 'string' && hard == "true")) {
                for (var x = 0; x <= index; x++) {
                    if (x == index) {
                        if (typeof array[x] != 'undefined') {
                            if (array[x] == null) {
                                array[x] = [];
                            }
                            array[x].push(value);
                        } else {
                            array.push(value);
                        }

                        break;
                    } else {
                        if (typeof array[x] == 'undefined') {
                            array.push(null);
                        }
                    }
                }
            } else {
                array.splice(index, 0, value);
            }
        }

        return array;
    }

    cleanEncodedCharacters = function(text) {
        if (typeof text == 'undefined') { return undefined; }

        return text.replace(/&#x23;/g, '#').replace(/&#x2f;/g, '/');
    }

    sizeAndStyleData = undefined;

    function adjustFontSize(element, fontSize, heightAllowed, widthAllowed) {
        var minimumFontSizeAllowed = 12;

        element.css('font-size', fontSize);

        if (typeof heightAllowed != 'undefined' && heightAllowed != null) {
            heightAllowed = heightAllowed * 1.25; // This is to give some extra spacing incase other browsers might be off a pixel or two.  Exact numbers could be dangerous in css.

            element.each(function() {
                while (minimumFontSizeAllowed < fontSize && getFullHeight($(this)) > heightAllowed) {
                    fontSize--;
                    $(this).css('font-size', fontSize);
                }
            });
        } else if (typeof widthAllowed != 'undefined' && widthAllowed != null) {
            var elementDisplay = element.css('display');
            element.css({
                'display': 'block',
                'white-space': 'nowrap'
            });
            element.each(function() {
                while (minimumFontSizeAllowed < fontSize && getFullWidth($(this)) > widthAllowed) {
                    fontSize--;
                    $(this).css('font-size', fontSize);
                    $(this).find('.actualSizeName').css('font-size', (fontSize - 10 < 10 ? 10 : fontSize - 10));
                } 
            });
            element.css({
                'display': elementDisplay,
                'white-space': 'initial'
            });
        }
        
        element.css({
            'font-size': fontSize,
            'opacity': 1
        }).find('.actualSizeName').css({
            'font-size': (fontSize - 10 < 10 ? 10 : fontSize - 10)
        });
    }

    function getSelectionWidth() {
        if(window.outerWidth >= 1024) {
            return 135;
        } else if (window.outerWidth >= 768) {
            return 655;
        } else {
            return 310;
        }
    }
    
    adjustFontSize($('#sizeSelection [bns-adjustfontsize]'), 30, null, getSelectionWidth());
    adjustFontSize($('#styleSelection [bns-adjustfontsize]'), 15, null, getSelectionWidth());

    function getSizeAndStyleData(categoryId) {
        if(categoryId == null) {
            categoryId = typeof $('#sidebar-styleList [data-selected="true"]').attr('data-value') != 'undefined' ? $('#sidebar-styleList [data-selected="true"]').attr('data-value') : '${product.getCategoryId()?default("")}'
        }

        $.ajax({
            type: "GET",
            url: '/' + websiteId + '/control/getProductSizeAndStyle',
            dataType: 'json',
            data: {
                'productTypeId': '${product.getProductType()?default("")}',
                'productCategoryId': categoryId
            },
            cache: true,
            async: false
        }).done(function(response) { 
            if (response.success) {
                sizeAndStyleData = response.data;
            }
        });
    }

    // Customization Options
    $('[data-key="plainOrPrinted"][data-value="printed"]').on('click', function() {
        $('[bns-customization]').removeClass('hidden');
    });

    $('[data-key="plainOrPrinted"][data-value="plain"]').on('click', function() {
        $('[bns-customization]').addClass('hidden');
    });

    $(document).mousedown(function(e){
        var container = $('.sidebarPanel');

        if ($(e.target).parents('.sidebarPanel').length == 0 && $(e.target).parents('.sidebarToggle').length == 0 && !$(e.target).hasClass('sidebarPanel') && !$(e.target).hasClass('sidebarToggle')) {
            container.animate({
                'opacity': '0',
                'right': '-100%'
            }, 150, 'linear', function() {
                container.css('display', 'none');
            });
        }
    });

    initSideBar = function() {
        productPage.bindUpdateSelection();

        $('.sidebarPanel').each(function(){
            var hideAtEnd = false;
            if ($(this).css('display') == 'none') {
                $(this).css('display', 'block');
                hideAtEnd = true;
            }   
            
            var headerHeight = $(this).find('.colorTextureHeading').outerHeight();
            var windowHeight = $(window).height();
            var scrollHeight = windowHeight - headerHeight - 15;

            var innerBody = $(this).find('.colorTextureBodyInner');
            innerBody.css('height', scrollHeight + 'px');
            if (hideAtEnd) {
                $(this).css('display', 'none');
            }
        });

        closeSideBar = function(element) {
            element.parents('.sidebarPanel').animate({
                'opacity': '0',
                'right': '-100%'
            }, 150, 'linear', function() {
                element.parents('.sidebarPanel').css('display', 'none');
            });
        }

        $('.sidebarPanel h4 .fa, [bns-selection]').off('click.closeSidebar').on('click.closeSidebar', function(){
            closeSideBar($(this));
        });

        $('.sidebarPanel').off('click.stopProp').on('click.stopProp', '.jqs-directionalButtonSelect, .jqs-hiddenDropdown', function(e) {
            e.stopPropagation();
        });
        
        $('.jqs-sidebarToggle').off('click.sidebarToggle').on('click.sidebarToggle', function() {
            var sidebar = $(this).attr('data-sidebar-name');

            $('#' + sidebar).css({
                'width': ($(window).width() - $('.productContentRight').offset().left) + 'px',
                'display': 'block'
            });
            
            if (typeof $('[data-sidebar-name="' + sidebar + '"]').attr('bns-resizetext') != 'undefined') {
                if (window.outerWidth <= 425) {
                    adjustFontSize($('#' + sidebar).find('[bns-adjustfontsize]'), 28, 65, null);
                } else {
                    adjustFontSize($('#' + sidebar).find('[bns-adjustfontsize]'), 28, 40, null);
                }
                
            }

            $('#' + sidebar).animate({
                'opacity': '1',
                'right': '0px'
            }, 150, 'linear');

            $('.colorTextureBodyInner').scrollTo('[selection-selected]');

            scrollToSelected($(this));
            $('#' + $(this).attr('data-sidebar-name') + ' .slideIt').each(function() {
                slideIt_init($(this));
            });
        });



    }

    buildColorList = function(selectColorProductId, ignoreAction) {
        styleValue = $('#sidebar-styleList [bns-selection][data-selected="true"]').attr('data-value');
        sizeValue = $('#sidebar-sizeList [bns-selection][data-selected="true"]').attr('data-value');
        selectedColor = $('#sidebar-colorList [bns-selection][data-selected="true"]').attr('data-product-color');
        ignoreAction = (typeof ignoreAction != 'undefined' ? ignoreAction : false);
        var colorCounter = 0;

        $('[bns-colorListOptionContainer]').empty();

        var colorListElements = [];
        var colorListElementsNoSequenceNum = [];
        for (var productIdKey in sizeAndStyleData["BY_SIZE"][sizeValue][styleValue]) {
            var productData = sizeAndStyleData["BY_SIZE"][sizeValue][styleValue][productIdKey];
            if (typeof selectColorProductId == 'undefined' && typeof productData.color != 'undefined' && selectedColor == productData.color) {
                selectColorProductId = productIdKey;
            }

            var colorElement = $('<div />').attr({
                'bns-selection': '',
                'class': 'jqs-selectList selectList selectListItem',
                'data-percent-savings': (typeof productData.savings != 'undefined' && productData.savings != null ? productData.savings : 0),
                'data-color-groups': (typeof productData.colorGroup != 'undefined' && productData.colorGroup != null ? productData.colorGroup : ''),
                'data-collection-groups': (typeof productData.collection != 'undefined' && productData.collection != null ? productData.collection : ''),
                'data-target': 'colorSelection',
                'data-new': (typeof productData.new != 'undefined' && productData.new != null ? productData.new : 'false'),
                'data-onsale': (typeof productData.sale != 'undefined' && productData.sale != null ? productData.sale : 'false'),
                'data-rating': (typeof productData.rating != 'undefined' && productData.rating != null ? productData.rating : '0_0'),
                'data-product-brand': (typeof productData.brand != 'undefined' && productData.brand != null ? productData.brand : ''),
                'data-printable': (typeof productData.printable != 'undefined' && productData.printable != null ? productData.printable : 'false'),
                'data-url': '/envelopes/control/product/~category_id=' + styleValue + '/~product_id=' + productIdKey,
                'data-product-id': productIdKey,
                'data-hasPeelAndPress': (typeof productData.hasPeelAndress != 'undefined' && productData.hasPeelAndress != null ? productData.hasPeelAndress : 'false'),
                'data-has-white-ink': (typeof productData.hasWhiteInk != 'undefined' && productData.hasWhiteInk != null ? productData.hasWhiteInk : 'false'),
                'data-has-sample': (typeof productData.hasSample != 'undefined' && productData.hasSample != null ? productData.hasSample : 'false'),
                'data-has-rush': (typeof productData.hasRush != 'undefined' && productData.hasRush != null ? productData.hasRush : 'false'),
                'data-product-weight': (typeof productData.weight != 'undefined' && productData.weight != null ? productData.weight : ''),
                'data-product-name': (typeof productData.name != 'undefined' && productData.name != null ? productData.name : ''),
                'data-product-color': (typeof productData.color != 'undefined' && productData.color != null ? productData.color : ''),
                'data-hex': (typeof productData.hex != 'undefined' && productData.hex != null ? productData.hex : '#ffffff'),
                'data-min-color': (typeof productData.minColors != 'undefined' && productData.minColors != null ? productData.minColors : ''),
                'data-max-color': (typeof productData.maxColors != 'undefined' && productData.maxColors != null ? productData.maxColors : ''),
                'data-parentproductid': (typeof productData.parentProductId != 'undefined' && productData.parentProductId != null ? productData.parentProductId : ''),
                'data-print-desc': (typeof productData.printDescription != 'undefined' && productData.printDescription != null ? productData.printDescription : ''),
                'data-size': (typeof productData.size != 'undefined' && productData.size != null ? productData.size : ''),
                'data-actualsize': (typeof productData.size != 'undefined' && productData.actualSize != null ? productData.actualSize : ''),
                'data-category': (typeof productData.category != 'undefined' && productData.category != null ? productData.category : ''),
                'data-style': (typeof productData.style != 'undefined' && productData.style != null ? productData.style : ''),
                'data-value': productIdKey,
                'data-meta-title': (typeof productData.metaTitle != 'undefined' && productData.metaTitle != null ? productData.metaTitle : "")
            }).append(
                $('<div />').addClass('foldersTabularRow folderDisplayTable productColorSelection').css({
                    'background': 'url("https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + (typeof productData.color != 'undefined' && productData.color != null ? (productData.color).replace(/[^a-zA-Z0-9_]/g, '').toLowerCase() : '') + '?fmt=jpeg&qlt=50&wid=1100&hei=100&ts=1") 100% repeat',
                    'background-color': '#' + (typeof productData.hex != 'undefined' && productData.hex != null ? productData.hex : 'ffffff'),
                    'background-position': 'right'
                }).append(
                    $('<div />').attr('selection-removeonselect', '').addClass('paddingLeft10 width30').append(
                        $('<span />').addClass('selectCheckbox')
                    )
                ).append(
                    $('<div />').attr('bns-colorselecttext', '').addClass('textBold paddingLeft10').append(
                        $('<div />').addClass((typeof productData.brand != 'undefined' && productData.brand != null && (productData.brand).match(/^.*?LUXPaper.*?$/) != null && typeof productData.style != 'undefined' && productData.style != null && productData.style == "LINED" ? 'colorName width250' : 'colorName')).html(
                            (typeof productData.color != 'undefined' && productData.color != null ? (typeof productData.weight != 'undefined' && productData.weight != null ? productData.color.replace(productData.weight, '') : productData.color) : '') + 
                            (typeof productData.weight != 'undefined' && productData.weight != null ? '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + productData.weight : '')
                        ).prepend(
                            $('<img />').attr({
                                'src': '/html/img/logo/luxPaperLogo.png',
                                'alt': 'LUXPaper'
                            }).addClass((typeof productData.brand != 'undefined' && productData.brand != null && (productData.brand).match(/^.*?LUXPaper.*?$/) != null ? '' : 'hidden'))
                        )
                    ).append(
                        $('<div />').addClass('colorPeelAndPress' + (typeof productData.hasPeelAndress != 'undefined' && productData.hasPeelAndress != null && productData.hasPeelAndress ? '' : ' hidden')).html('w/ Peel &amp; Press&trade;')
                    )
                ).append($('<i />').addClass('fa fa-caret-right productCaretSidebar').attr('selection-showonselect', ''))
            );

            if (typeof productData.sequenceNum != 'undefined' && productData.sequenceNum != null) {
                colorListElements = insertIntoArrayIndex(colorListElements, parseInt(productData.sequenceNum), colorElement, true);
            } else {
                colorListElementsNoSequenceNum.push(colorElement);
            }
            
            colorCounter++;
        }

        for (var x = 0; x < colorListElements.length; x++) {
            if (colorListElements[x] != null) {
                for (var y = 0; y < colorListElements[x].length; y++) {
                    $('[bns-colorListOptionContainer]').append(colorListElements[x][y]);
                }
            }
        }
        
        for (var x = 0; x < colorListElementsNoSequenceNum.length; x++) {
            if (colorListElementsNoSequenceNum[x] != null) {
                $('[bns-colorListOptionContainer]').append(colorListElementsNoSequenceNum[x]);
            }
        }

        $('.jqs-colorList').removeClass('hidden');

        if ($('[bns-colorListOptionContainer] > *').length <= 1) {
            $('.jqs-colorList').addClass('hidden');    
        }

        $('.jqs-colorCount').html('(' + colorCounter + ' color' + (colorCounter != 1 ? 's' : '') + ' available)');
        
        initSideBar();

        if (!ignoreAction) {
            if ($('#sidebar-colorList [data-selected="true"]').length == 0 && $('#sidebar-colorList [bns-colorListOptionContainer] [bns-selection][data-product-id="' + selectColorProductId + '"]').length > 0) {
                $('#sidebar-colorList [bns-colorListOptionContainer] [bns-selection][data-product-id="' + selectColorProductId + '"]').trigger('click.updateSelection', {googleAnalytic: true});
            } else {
                $('#sidebar-colorList [bns-colorListOptionContainer] [bns-selection]:first-child').trigger('click.updateSelection', {googleAnalytic: true});
            }
        }

        sidebarFontColorCheck();
        productPage.updateColorFilter();
    }

    buildStyleList = function(currentSelectedSize, currentSelectedStyle, ignoreAction) {
        function sortObj(obj, order, byKey) {
            var key,
                tempArray = [],
                i,
                tempObj = {};

            for (key in obj) {
                tempArray.push((byKey == 'undefined' || byKey ? '' : obj[key] + '|~|') + key);
            }

            tempArray.sort(
                function(a, b) {
                    return a.toLowerCase().localeCompare(b.toLowerCase());
                }
            );

            function updateArray(i) {
                if (byKey == 'undefined' || byKey) {
                    tempObj[tempArray[i]] = obj[tempArray[i]];
                } else {
                    var splitValues = tempArray[i].split('|~|');
                    tempObj[splitValues[1]] = splitValues[0];
                }
            }

            if(order === 'desc') {
                for (i = tempArray.length - 1; i >= 0; i--) {
                    updateArray(i);
                }
            } else {
                for (i = 0; i < tempArray.length; i++) {
                    updateArray(i);
                }
            }

            return tempObj;
        }

        if (typeof currentSelectedSize == 'undefined') {
            currentSelectedSize = $('#sidebar-sizeList [data-selected="true"]').attr('data-value');
        }

        if (typeof currentSelectedStyle == 'undefined') {
            currentSelectedStyle = $('#sidebar-styleList [data-selected="true"]').attr('data-value');
        }

        ignoreAction = (typeof ignoreAction != 'undefined' ? ignoreAction : false);
        currentSelectedSize = cleanEncodedCharacters(currentSelectedSize);
        currentSelectedStyle = cleanEncodedCharacters(currentSelectedStyle);

        $('[bns-styleListOptionContainer]').empty();

        for (var styleKey in sortObj(sizeAndStyleData.STYLE_LIST, 'asc', false)) {
            $('[bns-styleListOptionContainer]').append(
                $('<div />').attr({
                    'bns-selection': '', 
                    'data-target': 'styleSelection', 
                    'data-value': styleKey,
                    'data-selected': (currentSelectedStyle == styleKey ? 'true' : 'false')
                }).addClass('jqs-selectList selectList' + (!ignoreAction && currentSelectedStyle == styleKey ? ' slSelected' : '') + (typeof sizeAndStyleData['BY_SIZE'][currentSelectedSize][styleKey] == 'undefined' ? ' hidden' : '')).append(
                    $('<div />').addClass('foldersTabularRow foldersDisplayTable').append(
                        $('<div />').attr('selection-removeonselect', '').addClass('width30').append(
                            $('<span />').addClass('selectCheckbox')
                        )
                    ).append(
                        $('<div />').addClass('styleImage').append(
                            $('<img />').attr('src', '//actionenvelope.scene7.com/is/image/ActionEnvelope/designTemplate-' + styleKey.toLowerCase() + '?hei=100&wid=100&fmt=png-alpha&ts=1')
                        )
                    ).append(
                        $('<div />').addClass('styleValue').attr('bns-adjustfontsize', '').html(sizeAndStyleData["STYLE_LIST"][styleKey])
                    ).append(
                        $('<i />').addClass('fa fa-caret-right productCaretSidebar').attr('selection-showonselect', '')
                    )
                )
            );
        }

        scrollToSelected($('#styleSelection'));
        createOrderSamplesEvent();
        initSideBar();
    }

    buildSizeList = function(currentSelectedSize, currentSelectedStyle, ignoreAction) {
        if (typeof currentSelectedSize == 'undefined') {
            currentSelectedSize = $('#sidebar-sizeList [data-selected="true"]').attr('data-value');
        }

        if (typeof currentSelectedStyle == 'undefined') {
            currentSelectedStyle = $('#sidebar-styleList [data-selected="true"]').attr('data-value');
        }
        
        ignoreAction = (typeof ignoreAction != 'undefined' ? ignoreAction : false);
        currentSelectedSize = cleanEncodedCharacters(currentSelectedSize);
        currentSelectedStyle = cleanEncodedCharacters(currentSelectedStyle);
        
        $('[bns-sizeListOptionContainer]').empty();

        var sizeListElements = [];
        
        for (var sizeKey in sizeAndStyleData["BY_STYLE"][currentSelectedStyle]) {
            var sizeElement;
            var actualSize = null;
            var totalColors = 0;
            var sizeSortOrder = sizeAndStyleData["SORTED_SIZE_LIST"].indexOf(sizeKey);
            
            for (var productIdKey in sizeAndStyleData["BY_STYLE"][currentSelectedStyle][sizeKey]) {
                if (actualSize == null) {
                    actualSize = sizeAndStyleData["BY_STYLE"][currentSelectedStyle][sizeKey][productIdKey].actualSize;
                }
                totalColors++;
            }
            
            var re = new RegExp(actualSize, 'g');

            var displayableSizeKeyList = sizeKey.match(/(?:^|\s)\d+\/\d+(?:\s|$)/g);
            var displayableSizeKey = getDisplayableSize(displayableSizeKeyList, sizeKey);
            var displayableActualSizeList = actualSize.match(/(?:^|\s)\d+\/\d+(?:\s|$)/g);
            var displayableActualSize = getDisplayableSize(displayableActualSizeList, actualSize);

            function getDisplayableSize(displayableSizeList, displayableSize) {
                if (displayableSize.match(/(?:^|\s)\d+\/\d+(?:\s|$)/g) != null) {
                    for (var x = 0; x < displayableSizeList.length; x++) {
                        var splitSize = (displayableSizeList[x].trim()).match(/^(.*?)\/(.*?)$/);
                        if (splitSize.length != null) {
                            displayableSize = displayableSize.replace(displayableSizeList[x].trim(), '<span class="fractionContainer"><span class="fractionNumber">' + splitSize[1] + '</span>/<span class="fractionNumber">' + splitSize[2] + '</span></span>');
                        }
                    }
                }

                return displayableSize;
            }

            var sizeElement = $('<div />').attr({
                'bns-selection': '',
                'data-target': 'sizeSelection', 
                'data-value': sizeKey, 
                'data-selected': (currentSelectedSize == sizeKey ? 'true' : 'false')
            }).addClass('jqs-selectList selectList' + (!ignoreAction && currentSelectedSize == sizeKey ? ' slSelected' : '')).append(
                $('<div />').addClass('foldersTabularRow foldersDisplayTable').append(
                    $('<div />').attr('selection-removeonselect', '').addClass('width30').append(
                        $('<span />').addClass('selectCheckbox')
                    )
                ).append(
                    $('<div />').addClass('sizeName').attr('bns-adjustfontsize','').append(
                        displayableSizeKey + (sizeKey.match(re) == null ? '<span class="actualSizeName">&nbsp;&nbsp;(' + displayableActualSize + ')</span>' : '')
                    )
                ).append(
                    $('<div />').addClass('text-right').attr('selection-removeonselect', '').html(totalColors + ' Color' + (totalColors > 1 ? 's' : '') + ' Available')
                )
            ).append(
                $('<i />').addClass('fa fa-caret-right productCaretSidebar').attr('selection-showonselect', '')
            );

            if (sizeSortOrder >= 0) {
                sizeListElements = insertIntoArrayIndex(sizeListElements, sizeSortOrder, sizeElement, true);
            }
        }

        for (var x = 0; x < sizeListElements.length; x++) {
            if (sizeListElements[x] != null) {
                $('[bns-sizeListOptionContainer]').append(sizeListElements[x]);
            }
        }

        if (window.outerWidth <= 425 ) {
            $('#sidebar-sizeList .colorTextureBodyInner [bns-selection]').each(function() {
                if($(this).find('.sizeName').children('.actualSizeName').length > 0 && $(this).find('.sizeName').height() < 56) {
                    $('<br />').insertBefore($(this).find('span.actualSizeName'));
                }
            });
        }

        scrollToSelected($('#sizeSelection'));
        createOrderSamplesEvent();
        initSideBar();
    }

    sidebarFontColorCheck = function() {
        if (typeof isColorAllowed == 'function') {
            $('#colorSelection.selectListParent').find('.productColorSelection').css('color', '#' + (isColorAllowed('ffffff', $('[data-target="colorSelection"].slSelected').attr('data-hex')) ? 'ffffff' : '000000'));
            $('#colorSelection.selectListParent').find('.colorName img').attr('src', '/html/img/logo/luxPaperLogo' + (isColorAllowed('ffffff', $('[data-target="colorSelection"].slSelected').attr('data-hex')) ? '_white' : '') + '.png');

            $('#sidebar-colorList .selectListItem').each(function(){
                $(this).find('.productColorSelection').css('color', '#' + (isColorAllowed('ffffff', $(this).attr('data-hex')) ? 'ffffff' : '000000'));
                $(this).find('.colorName img').attr('src', '/html/img/logo/luxPaperLogo' + (isColorAllowed('ffffff', $(this).attr('data-hex')) ? '_white' : '') + '.png');
            });
        }
    }
    
    sidebarFontColorCheck();

    $('#sizeSelection, #styleSelection').off('click.buildSizeList').on('click.buildSizeList', function() {
        // Initial Load for Size and Style List
        if ($('[bns-sizeListOptionContainer] > *').length == 0) {
            getSizeAndStyleData(null);
            buildSizeList('${product.getSize()?if_exists}', '${product.getCategoryId()?if_exists}')
            buildStyleList('${product.getSize()?if_exists}', '${product.getCategoryId()?if_exists}')
            $(this).off('click.buildSizeList');
        }
    });
</script>

<script>
    showTemplatesInDesigner = !(window.location.search.substring(1).match('designId=') != null);

    var productComplete = false;
    var startedAsDesign = <#if designId?exists>true<#else>false</#if>; //initialize new product page and add sku
    var productPage = new EnvProductPage();
    productPage.addProduct({
        'productId': '${product.getId()}',
        'color': '${product.getColor()?if_exists}',
        'name': '${product.getName()?if_exists}',
        'size': '${product.getSize()?if_exists}',
        'actualSize': '${product.getActualSize()?if_exists}',
        'category': '${product.getParentCategoryId()?if_exists}',
        'style': '${product.getCategoryId()?if_exists}',
        'productTypeId': '${product.getProductType()?if_exists}',
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
        'addOnProduct': <#if addOnProduct?has_content>'${addOnProduct}'<#else>null</#if>,
        'productPriceData': JSON.parse('${product.getPrices()}'.replace(/([0-9A-Za-z\.]+)\=\{/g, '"$1":{').replace(/([0-9A-Za-z\.]+)\=([0-9A-Za-z\.]+)/g, '"$1":"$2"')),
        'rating': '${product.getRating()}'
    });

    productPage.adjustProductNameSize();
    productPage.loadSamples();
    //productPage.getDueDates();
    productPage.bindClickEvents();
    productPage.returnActiveProduct(false).bindAssetImageSelection();
    productPage.returnActiveProduct(false).bindPrices();
    productPage.returnActiveProduct(false).smallestQuantity = ${smallestQuantity?if_exists};
    productPage.returnActiveProduct(false).lowestPrice = ${lowestPrice?if_exists};
    productPage.returnActiveProduct(false).priceData.quantity = ${smallestQuantity?if_exists};
    productPage.returnActiveProduct(false).storeRecentlyViewed();
    productPage.popState();
    productPage.updateColorFilter();
    

    $(document).ready(function() {
        if(window.location.hash.substr(1) != '' && window.location.hash.substr(1).match(/^\d/) != null) {
            $('.plainOrPrinted [data-key="plainOrPrinted"][data-value="printed"]').removeAttr('data-reveal-id').removeClass('jqs-designnow');
            $('.jqs-designOnlineOptions .jqs-designnow').removeAttr('data-reveal-id');
            $('.jqs-designnow').removeAttr('data-reveal-id');
            showTemplatesInDesigner = false;

            productPage.projectId = window.location.hash.substr(1);
            productPage.loadProject(productPage.projectId, true, null);

            $('[data-key=colorsFront][data-value=' + ((productPage.returnActiveProduct(true).whiteInkFront) ? 'whiteInkFront' : productPage.returnActiveProduct(true).colorsFront) + ']').trigger('click');
            $('[data-key=colorsBack][data-value=' + ((productPage.returnActiveProduct(true).whiteInkBack) ? 'whiteInkBack' : productPage.returnActiveProduct(true).colorsBack) + ']').trigger('click');
            $($('[data-key=plainOrPrinted][data-value=printed][data-additionalvalue="design"]')[0]).trigger('click.updateSelection', {loadDesigner: true});
            $('[data-key="addresses"][data-value="true"]').parents('[bns-customization]').removeClass('hidden');

            if(productPage.returnActiveProduct(true).addAddressing && productPage.returnActiveProduct(true).addresses > 0) {
                var addressingDataGroupId = productPage.getProducts()[0].getProduct().dataGroupId;

                productPage.updateAddressingButton(productPage.returnActiveProduct(true).addresses);
                $('[data-target="addressing"][data-key="addresses"]').attr('data-selected', 'true');
                productPage.createAddressingGrid((typeof addressingDataGroupId != 'undefined' ? addressingDataGroupId : -1), JSON.parse($('[bns-address_font_list]').find('.selected').attr('data-variable-style')));
            }
        }
        //certonaListLoadTop();
    });

    if(getUrlParameters('showPrint', '', false) == 'Y') {
        $('[data-key=plainOrPrinted][data-value=printed]').trigger('click');
    }

    productComplete = true;

    $('.jqs-pauseVideoOnClose').each(function() {
        var iframe = $(this);
        $(document).on('closed.fndtn.reveal', '#' + $(iframe).closest('[data-reveal]').attr('id'), function () {
            $(iframe)[0].contentWindow.postMessage('{"event":"command","func":"pauseVideo","args":""}', '*');
        });
    });

    $('.jqs-quantityTest > *').each(function() {
        $(this).clone(true).appendTo('#quantityPriceSelection2');
    });
    $('#dropdown-quantityList > *').each(function() {
        $(this).clone(true).appendTo('#dropdown-quantityList2');
    });

    $(function() {        
        productPage.returnActiveProduct(false).getProductInventory();
        productPage.returnActiveProduct(false).sendSLIClick();
        productPage.returnActiveProduct(false).calculatePrice(null);
        
        if (${showUpload?default('0')} == 1) {
            $('[bns-selection][data-additionalkey="designOrUpload"][data-additionalvalue="upload"]').trigger('click');
        }

        if (typeof getUrlParameters('orderId', '', false) != 'undefined' && getUrlParameters('orderId', '', false) && getUrlParameters('orderId', '', false).match(/(?:ENV|AE)\d+/) != null) {
            $('[data-reveal-id="leave-a-review"]').trigger('click');
        }
    });

    $('.addToCartContinueShoppingA').on('click', function() {
        hideAllDropDown(0, true);
    });
    
    initSideBar();

    $(document).ready(function() {
        $(document).on('opened.fndtn.reveal', '[data-reveal]', function () {
            $(window).scrollTop(0);
        });
    });
/*
    $('[bns-addaddressing]').on('click', function() {
        $($('[data-key="plainOrPrinted"][data-value="printed"][data-additionalvalue="design"]')[0]).trigger('click.updateSelection', {loadDesigner: true});
        closeSideBar($(this));
        $('[data-key="addresses"][data-value="true"][data-target="addressing"]').trigger('click');
        $('[bns-customization]').removeClass('hidden');
    });
*/
</script>
<script>
    var designData = {};
    var locations = ['front', 'back'];

    <#list product.getDesigns() as productDesign>
        <#if productDesign.get("active") == "Y">
            designData['${productDesign.get("designTemplateId")}'] = {
                'designTemplateId': '${productDesign.get("designTemplateId")}',
                'backgroundColor': productPage.returnActiveProduct(true).hex,
                'location': '${productDesign.get("location")}',
                'name': '${productDesign.get("name")}',
                'width': '${productDesign.get("width")}',
                'height': '${productDesign.get("height")}',
                'jsonData': JSON.parse('${productDesign.get("jsonData")?replace("&quot;", '"')?replace("&#x5b;", "[")?replace("&#x7b;", "{")?replace("&#x3d;", "=")?replace("&#x3a;", ":")?replace("&#x5d;", "]")?replace("&#x7d;", "}")?replace("&gt;", ">")?replace("&lt;", "<")?replace("&#x2f;", "/")?replace("&#x3f;", "?")?replace("&#x23;", "#")?replace("&#x5c;", "\\\\")}')
            };

            <#if designId?has_content && productDesign.get("designTemplateId") == designId>
                productPage.loadNewTexel(designData['${productDesign.get("designTemplateId")}'], false, false);
                productPage.returnActiveProduct(false).calculatePrice(null);
                $('[bns-rush_production]').removeClass('hidden');
            </#if>
        </#if>
    </#list>
</script>
<#else>
The product you requested is no longer available.
</#if>


<script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>