<#assign imgFmtQltyGry = 'fmt=jpeg&amp;qlt=50&amp;bgc=f1f1f1' />
<#assign imgFmtQltyLtGry = 'fmt=jpeg&amp;qlt=50&amp;bgc=f9f9f9' />
<#assign imgFmtQltyLtGry2 = 'fmt=jpeg&amp;qlt=50&amp;bgc=f6f6f6' />
<#assign imgFmtQltyWht = 'fmt=jpeg&amp;qlt=50&amp;bgc=ffffff' />
<#--
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
-->

<style id="designerCSS">
    #designerContainer {
        width: 100% !important;
        height: 100% !important;
        position: fixed !important;
        top: 0px !important;
        left: 0px !important;
        border: 55px solid rgba(0,0,0,.45) !important;
        box-shadow: inset 0 0 0 2px rgba(254,126,3,.75) !important;
        -moz-background-clip: padding !important;
        -webkit-background-clip: padding !important;
        background-clip: padding-box !important;
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

    /* Magik Mike CSS */
    .magikMikeSelect {
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
    .magikMikeSelect[bns-magik_mike_select="magikMike_fontFamily"] {
        width: 125px;
    }
        .magikMikeSelect .selectedColor {
            height: 18px;
            width: 18px;
            left: 0;
            top: 1px;
            position: relative;
        }

    .magikMikeSelectListPopup {
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
        .magikMikeSelectListOption {
            box-sizing: border-box;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
            padding: 5px 10px 5px 15px;
            cursor: pointer;
            white-space: nowrap;
            border: 1px solid transparent;
        }

        .magikMikeSelectListOption:hover {
            border: 1px solid #00a4e4;
        }
        .magikMikeSelectListSelected {
            border: 1px solid #00a4e4;
        }

    .magikMikeRadio {
        background-color: #e8e8e8;
        display: inline-block;
        width: 17px;
        height: 17px;
        position: relative;
        padding: 5px;
        cursor: pointer;
        margin: 0 5px 0 0;
    }
    .magikMikeRadio[data-selected="true"] {
        background-color: #545554;
    }
        .magikMikeRadio [class*="-fontAlignment"] {
            border-top: 2px solid #555;
            position: relative;
            height: 0;
        }
        .magikMikeRadio[data-selected="true"] [class*="-fontAlignment"] {
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
<script src="<@ofbizContentUrl>/html/js/util/jsPDF.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/js/util/jsPDFFonts.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/js/util/fabricJS.js</@ofbizContentUrl>"></script>

<div id="designerContainer" class="hidden">
    <canvas id="magikMike"></canvas>
</div>

<#if product?exists>
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
                    <img property="image" class="jqs-imagehero productImage photo" alt="${product.getName()?if_exists} ${product.getColor()?if_exists}" style="display: block;" src="<#if designId?has_content && hasDefault != "y">//texel.envelopes.com/getBasicImage?id=${designId}<#if templateType?exists>&amp;templateType=${templateType}</#if><#if hex?exists>&amp;hex=${hex}</#if>&amp;hei=413&amp;fmt=png-alpha<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/<#if hasDefault == "y">${defaultImageId}<#else>${product.getId()}</#if>?hei=413&wid=510&amp;${imgFmtQltyGry}</#if>" />
                    <div class="jqs-productVideoContainer envResponsiveIframe hidden" data-ratio="16x9">
                        <iframe id="productVideo" title="Video Player" src="<#if firstVideo?has_content>${firstVideo}<#else>//fast.wistia.net/embed/iframe/sfyk3r1v74</#if>" allowtransparency="true" frameborder="0" scrolling="no" class="wistia_embed" name="wistia_embed" allowfullscreen mozallowfullscreen webkitallowfullscreen oallowfullscreen msallowfullscreen></iframe>
                    </div>
                </div>
                <div bns-load_design class="margin-top-xxs margin-bottom-xxs blueButton hidden tablet-desktop-only">
                    Get Started <i class="fa fa-caret-right"></i>
                </div>
                <a class="tablet-desktop-only uploadLink<#if !designId?has_content> hidden</#if>" href="<@ofbizUrl>/product?product_id=${product.getId()}&amp;showUpload=1</@ofbizUrl>">
                    Have a print-ready file? Upload Artwork <i class="fa fa-caret-right"></i>
                    <div><span class="cmykC"></span><span class="cmykM"></span><span class="cmykY"></span><span class="cmykK margin-right-xs"></span></div>
                </a>
                <div class="jqs-optTestDesignsLeftSide hidden">
                    <div class="margin-top-xxs tablet-desktop-only <#if product.isPrintable()?c == "false">hidden</#if>" style="font-size: 11px; background-color: #ffffff; width: 250px; text-align: left; height: 75px; overflow: hidden; position: relative; margin: auto; border: 1px solid #333333; padding: 5px;">
                        <div class="jqs-optTestDesignsLeftSideContent" style="font-size: 5px;position: absolute;width: 100px;height: 73px;border: 1px solid #e3e3e3;background-color: #${product.getHex()?if_exists};color: #000000;text-align: left;display: inline-block;vertical-align: top;margin: 0px 10px;padding: 25px;transform: rotate(-19deg);right: -22px;top: 15px;">
                            <div style="line-height: 7px; position: relative; top: 1px; left: 15px;">Your Name</div>
                            <div style="line-height: 7px; position: relative; top: -1px; left: 15px;">Happy Street</div>
                            <div style="line-height: 7px; position: relative; top: -3px; left: 15px;">Anywhere, USA</div>
                            <div style="position: absolute; top: 2px; left: 4px; line-height: 7px;">Your Name</div>
                            <div style="position: absolute; top: 7px; left: 4px; line-height: 7px;">Happy Street</div>
                            <div style="position: absolute; top: 12px; left: 4px; line-height: 7px;">Anywhere, USA</div>
                            <i class="fa fa-navicon" style="position: absolute; top: 2px; right: 3px; font-size: 10px;"></i>
                        </div>
                        <div style="font-weight: bold; font-size: 13px; color: #00a4e4;">We'll Print Your Return Address</div>
                        <div><strong>1:</strong> Choose "Add Printing Now"</div>
                        <div><strong>2:</strong> Choose "Design Now"</div>
                    </div>
                </div>
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
            <div class="freeDesign">
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
            </div>
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
                    <div class="optionContentBlock option jqs-colorList colorListContent<#if productSizeAndStyle?has_content && productSizeAndStyle.SORTED_COLORS?has_content && productSizeAndStyle.SORTED_COLORS.keySet()?size == 1> hidden</#if>">
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
                                            <div bns-selection class="jqs-selectList selectList selectListItem <#if entry.getKey() == product.getId()>slSelected</#if>" <#if entry.getKey() == product.getId()>data-selected="true"</#if>data-percent-savings="${variant.savings?default("0")}" data-color-groups="${variant.get("colorGroup")?if_exists?replace("jqs-","")}" data-collection-groups="${variant.get("collection")?default("")?replace("jqs-","")}" data-target="colorSelection" data-new="${variant.get("new")?c}" data-onsale="${variant.get("sale")?c}" data-rating="${variant.get("rating")}" data-product-brand="${variant.get("brand")?default("")}" data-printable="${variant.get("printable")?c}" data-url="<@ofbizUrl>/product/~category_id=${variant.get("style")}/~product_id=${entry.getKey()}</@ofbizUrl>" data-product-id="${entry.getKey()}" data-hasPeelAndPress="${variant.hasPeelAndress?c}" data-has-white-ink="${variant.get("hasWhiteInk")?c}" data-has-sample="${variant.get("hasSample")?c}" data-product-weight="${variant.get("weight")?if_exists}" data-product-name="${variant.get("name")}" data-product-color="${variant.get("color")?if_exists}" data-hex="${variant.get("hex")?replace("#","")?replace("&x23;","")?replace("&35;","")}" data-min-color="${variant.get("minColors")?default(1)}" data-max-color="${variant.get("maxColors")?default(1)}" data-print-desc="${variant.get("printDescription")?default("")}" data-size="${variant.get("size")?if_exists}" data-actualsize="${variant.get("actualSize")?if_exists}" data-category="${variant.get("category")?if_exists}" data-style="${variant.get("style")?if_exists}" data-value="${entry.getKey()}" data-parentproductid="${variant.parentProductId?if_exists}" data-product-type="${variant.get("productTypeId")}" data-has-rush="${variant.get("hasRush")?c}">
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
                                        <div class="selectionDescription jqs-inventoryDom hidden"><i class="fa fa-check productStock"></i> In Stock, Ships <span class="jqs-plainShipTime"><#if product.getLeadTime().leadTimePlain != 0>in ${product.getLeadTime().leadTimePlain} Day<#if product.getLeadTime().leadTimePlain gt 1>s</#if><#else>Today</#if></span></div>
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
                                                    <div class="selectionDescription jqs-inventoryDom hidden"><i class="fa fa-check productStock"></i> In Stock, Ships <span class="jqs-plainShipTime"><#if product.getLeadTime().leadTimePlain != 0>in ${product.getLeadTime().leadTimePlain} Day<#if product.getLeadTime().leadTimePlain gt 1>s</#if><#else>Today</#if></span></div>
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
                                        <div bns-selection class="selectList jqs-selectList<#if designId?has_content> slSelected</#if> tablet-desktop-only" data-key="plainOrPrinted" data-additionalkey="designOrUpload" data-value="printed" data-additionalvalue="design" data-selected="<#if designId?has_content>true<#else>false</#if>" data-target="printingOption">
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
                                        <div bns-customization bns-selection class="selectList jqs-selectList tablet-desktop-only<#if product.hasAddressingAbility()?c == "false"> hidden</#if>" data-key="plainOrPrinted" data-value="printed" data-additionalkey="addressing" data-additionalvalue="true" data-target="printingOption">
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
                                <div class="optionButton addRecipientAddressingButton jqs-addaddresses jqs-selectList" data-target="addressing" data-key="addresses" data-holder data-price data-selected="false" data-value="true" data-total="0" data-reveal-id="startAddressing">
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
                                        <div class="addressingTemplateList jqs-address jqs-scrollable">
                                            <div>
                                                <div class="selected" data-variable-style="[{'fontSize': 16, 'lineHeight': 19, 'textAlign': 'center', 'trackingRight': 0, 'fontFamily': 'Myriad Pro'}]">
                                                    <img src="<@ofbizContentUrl>/html/img/designer/11400.jpg</@ofbizContentUrl>" alt="Myriad Pro" />
                                                </div>
                                                <span>Myriad Pro</span>
                                            </div><div>
                                            <div data-variable-style="[{'fontSize': 16, 'lineHeight': 19, 'textAlign': 'center', 'trackingRight': 0, 'fontFamily': 'Minion Pro'}]">
                                                <img src="<@ofbizContentUrl>/html/img/designer/11401.jpg</@ofbizContentUrl>" alt="Minion Pro" />
                                            </div>
                                            <span>Minion Pro</span>
                                        </div><div>
                                            <div data-variable-style="[{'fontSize': 16, 'lineHeight': 19, 'textAlign': 'center', 'trackingRight': 0, 'fontFamily': 'Poor Richard'}]">
                                                <img src="<@ofbizContentUrl>/html/img/designer/11402.jpg</@ofbizContentUrl>" alt="Poor Richard" />
                                            </div>
                                            <span>Poor Richard</span>
                                        </div><div>
                                            <div data-variable-style="[{'fontSize': 18, 'lineHeight': 24, 'textAlign': 'center', 'trackingRight': 0, 'fontFamily': 'Pinyon Script'}]">
                                                <img src="<@ofbizContentUrl>/html/img/designer/11403.jpg</@ofbizContentUrl>" alt="Pinyon Script" />
                                            </div>
                                            <span>Pinyon Script</span>
                                        </div><div>
                                            <div data-variable-style="[{'fontSize': 18, 'lineHeight': 19, 'textAlign': 'center', 'trackingRight': 0, 'fontFamily': 'Mrs Eaves OT'}]">
                                                <img src="<@ofbizContentUrl>/html/img/designer/11404.jpg</@ofbizContentUrl>" alt="Mrs Eaves OT" />
                                            </div>
                                            <span>Mrs Eaves OT</span>
                                        </div><div>
                                            <div data-variable-style="[{'fontSize': 16, 'lineHeight': 22, 'textAlign': 'center', 'trackingRight': 0, 'fontFamily': 'Trajan Pro'}]">
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
                    <div class="optionContentBlock option hidden" bns-customization>
                        <h5>Production Time: </h5>
                        <div>
                            <div id="productionTime" class="selectList selectListParent sidebarToggle jqs-sidebarToggle productionTime" data-sidebar-name="sidebar-productionTime" selection-selectlistname="productionTime" data-ignorecaret="">
                                <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                    <div class="printingIconContainer"><i class="fa fa-envelope"></i></div>
                                    <div class="selectionHeader">Standard (5 days)</div>
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
                                        <div class="selectionHeader">Standard (5 days)</div>
                                        <i selection-showonselect class="fa fa-caret-right productCaretSidebar"></i>
                                    </div>
                                </div>
                                <div bns-selection class="selectList jqs-selectList<#if product.hasRush()?c == "false"> hidden</#if>" data-price data-target="productionTime" data-key="isRush" data-value="true">
                                    <div class="foldersTabularRow folderDisplayTable printingOptionContent">
                                        <div selection-removeonselect class="paddingLeft10 width40"><span class="selectCheckbox"></span></div>
                                        <div class="printingIconContainer"><i class="fa fa-envelope"></i></div>
                                        <div class="selectionHeader">Rush Production (2 days)</div>
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
                    </div>
                <#else>
                    <div class="discontinuedText">Discontinued</div>
                </#if>
            </div>
            <div class="productTools margin-top-xxs productPageTotal">
                <#--<div bns-originaladdtocart class="button-cta jqs-addToCart addToCart margin-top-xxs hidden">Add to Cart</div>-->
                <div bns-addtocarttesta class="button-cta addToCart jqs-addToCart testA">Add to Cart</div>
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
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 4> hidden</#if>" data-price="" data-target="inkFrontSelection" data-key="colorsFront" data-value="4" data-selected="false"><div>Full Color</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.hasWhiteInk()?c == "false"> hidden</#if>" data-price="" data-target="inkFrontSelection" data-key="colorsFront" data-value="whiteInkFront" data-selected="false"><div>White Ink</div></div>
    </div>
    <div id="dropdown-inkBackSelection" class="drop-down inkBackList selectListDropDown">
        <div bns-selection class="jqs-selectList selectList slSelected" data-price="" data-target="inkBackSelection" data-key="colorsBack" data-value="0" data-selected="true"><div>None</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 1> hidden</#if>" data-price="" data-target="inkBackSelection" data-key="colorsBack" data-value="1" data-selected="false"><div>1 Color</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 2> hidden</#if>" data-price="" data-target="inkBackSelection" data-key="colorsBack" data-value="2" data-selected="false"><div>2 Color</div></div>
        <div bns-selection class="jqs-selectList selectList<#if product.maxPrintColor?exists && product.maxPrintColor lt 4> hidden</#if>" data-price="" data-target="inkBackSelection" data-key="colorsBack" data-value="4" data-selected="false"><div>Full Color</div></div>
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
                                    <div bns-design data-design="${productDesign.get("designTemplateId")}">
                                        <img bns-design_image src="" alt="${productDesign.get("name")}" />
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
                Proofs are ready for review on the next business day. Your print order will ship 5 business days after proof approval.
                <br /><br />
                <strong>Rush Production:</strong>
                Proofs are ready for review on the same business day. Your print order will ship 2 business days after proof approval.
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
<script src="<@ofbizContentUrl>/html/js/product/productSandbox.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
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
                'data-value': productIdKey
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
    productPage.bindTexel();
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
            productPage.updateImage(productPage.getProducts()[0].getProduct().s7Data.scene7Data.designs['0'].scene7url);

            //disable design popup
            productPage.updateDesignButton();
            $('[data-key=colorsFront][data-value=' + ((productPage.returnActiveProduct(true).whiteInkFront) ? 'whiteInkFront' : productPage.returnActiveProduct(true).colorsFront) + ']').trigger('click');
            $('[data-key=colorsBack][data-value=' + ((productPage.returnActiveProduct(true).whiteInkBack) ? 'whiteInkBack' : productPage.returnActiveProduct(true).colorsBack) + ']').trigger('click');
            $($('[data-key=plainOrPrinted][data-value=printed][data-additionalvalue="design"]')[0]).trigger('click.updateSelection', {loadDesigner: true});
            $('[data-key="addresses"][data-value="true"]').parents('[bns-customization]').removeClass('hidden');
            //<#if !designId?has_content>$('[data-key=designOrUpload][data-value=design]').trigger('click');</#if>

            //disable addressing popup
            if(productPage.returnActiveProduct(true).addAddressing && productPage.returnActiveProduct(true).addresses > 0) {
                productPage.updateAddressingButton(productPage.returnActiveProduct(true).addresses);
                $('[data-key="addresses"][data-value="true"]').trigger('click.updateSelection');
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
/*
        if (${showUpload?default('0')} == 1) {
            $('[data-reveal-id="startUpload"]').trigger('click');
            $(window).scrollTop(0);
        }
*/
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

            $('[bns-design][data-design="${productDesign.get("designTemplateId")}"]').find('img[bns-design_image]').attr('src', new MagikMike(null, designData['${productDesign.get("designTemplateId")}'], true).getCanvasDataAsImage(designData['${productDesign.get("designTemplateId")}'].jsonData)[designData['${productDesign.get("designTemplateId")}'].location]);
        </#if>
    </#list>
</script>
<#else>
The product you requested is no longer available.
</#if>


<script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>
<img style="display: none;" src="data:image/png;base64,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" />