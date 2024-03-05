<#if product?exists>
    <#assign variantData = Static["com.envelopes.product.ProductHelper"].getProductColors(delegator, dispatcher, product.getProduct(), "folders") />
    <#assign variantColors = variantData.get("colors") />
</#if>
<#if product.getColor()?exists>
    <#assign colorSwatch = product.getColor()?replace(' ', '_') />
</#if>

<#assign productId = requestParameters.product_id?default("912-514-C") />

<#assign selected_desc = "" />
<#assign selected_weight = "" />
<#assign selected_texture = "" />

<#list variantColors as variant>
    <#if variant.productId == productId>
        <#assign plainDesc = variant.desc?replace("[^\\w\\s]", "", "r")?replace("\\s+", "_", "r") />
        <#assign selected_desc = variant.desc?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace(" ", "_") />
        <#assign selected_weight = variant.weight?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace(" ", "_") />
        <#assign selected_texture = variant.texture?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace(" ", "_") />
    </#if>
</#list>

<h5 class="colorTexture">Paper Color &amp; Texture (${variantColors?size} Colors)</h5>
<div bns-selectedcolortext class="selectListParent sidebarToggle jqs-sidebarToggle colorSelection jqs-scrollToSelected colorTextureSelection noPaddingTop noPaddingBottom noPaddingLeft" data-sidebar-name="sidebar-colorList" selection-selectlistname="colorSelection" data-hex="${product.getHex()?if_exists}" data-ignorecaret="" >
    <img class="productBackgroundColor" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/PC-<#if selected_desc != "">${selected_desc}</#if><#if selected_weight != "">_${selected_weight}</#if><#if selected_texture != "">_${selected_texture}</#if>" alt="${product.getColor()?if_exists} ${product.getPaperWeight()?if_exists} ${product.getTexture()?if_exists}"/>
    <div class="foldersTabularRow folderDisplayTable productColorSelection">

        <div bns-colorselectiontext class="textBold paddingLeft10">${product.getColor()?if_exists} ${product.getPaperWeight()?if_exists} ${product.getTexture()?if_exists}</div>
    </div>
</div>

<div id="sidebar-colorList" class="sidebarPanel colorList jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Paper Color &amp; Texture</h4>
            <#--
            <div>
                <div class="foldersTabularRow">
                    <div class="slideIt-container padding10 colorFilterList jqs-colorFilter marginTop10 marginBottom10" style="height: 29px;">
                        <div class="slideIt-left">
                            <i class="fa fa-chevron-left"></i>
                        </div>
                        <div class="slideIt text-left colorFilterSlideIt">
                            <div>
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
                        <div class="slideIt-right">
                            <i class="fa fa-chevron-right"></i>
                        </div>
                    </div>
                </div>
                <small class="colorFilterClear hidden"><a>Clear Filters</a></small>
            </div>
            -->
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="colorTextureBody ">
        <div class="colorTextureBodyInner" style="padding: 0;">
        <#assign counter = 0 />
        <#list variantColors as variant>
            <#assign desc = variant.desc?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace("X2F", "")?replace(" ", "_") />
            <#assign weight = variant.weight?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace("X2F", "")?replace(" ", "_") />
            <#assign texture = variant.texture?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace("X2F", "")?replace(" ", "_") />

            <#assign doogmaDesc = variant.desc?default("")?lower_case?replace("[^0-9a-z]", "", "r")?replace("x\\d{2}", "", "r") />
            <#assign doogmaWeight = variant.weight?default("")?lower_case?replace("[^0-9a-z]", "", "r")?replace("x\\d{2}", "", "r") />
            <#assign doogmaTexture = variant.texture?default("")?lower_case?replace("[^0-9a-z]", "", "r")?replace("x\\d{2}", "", "r") />
            <div imprintmethods="<#list variant.imprintMethods as imprintMethod><#if imprintMethod_index != 0>|</#if>${imprintMethod.idCode}</#list>" data-doogma data-doogma-key="papercolorandtexture" data-doogma-value="${doogmaDesc}${doogmaWeight}${doogmaTexture}" class="noPaddingLeft noPaddingTop noPaddingBottom selectListItem" bns-selection selection-target="colorSelection" selection-name="productId" selection-value="${variant.get("productId")}" data-weight="${variant.get("weight")?if_exists}" data-texture="${variant.get("texture")?if_exists}" data-group="${variant.get("group")?if_exists}" data-percent-savings="${variant.percentSavings?default("0")}" data-color-groups="${variant.get("colorGroupClasses")?replace("jqs-","")}" data-collection-groups="${variant.get("collectionClasses")?replace("jqs-","")}" data-new="${variant.get("new")?c}" data-onsale="${variant.get("sale")?c}" data-rating="${variant.get("rating")}" data-product-brand="${variant.get("brand")?default("")}" data-printable="${variant.get("isPrintable")?c}" data-url="<@ofbizUrl>/product/~category_id=${variant.get("primaryProductCategoryId")?default("null")}/~product_id=${variant.get("productId")}</@ofbizUrl>" data-product-id="${variant.get("productId")}" data-hasPeelAndPress="<#if variant.sealingMethod?exists && variant.sealingMethod?contains("Peel") && variant.sealingMethod?contains("Press")>true<#else>false</#if>" data-has-white-ink="${variant.get("hasWhiteInk")?c}" data-has-sample="<#if variant.hasSample == true>true<#else>false</#if>" data-product-weight="${variant.get("weight")?if_exists}" data-product-name="${variant.get("name")?html}" data-product-color="${variant.get("desc")!}" data-hex="${variant.get("hex")?replace("#","")?replace("&x23;","")?replace("&35;","")}" data-min-color="${variant.get("minColors")?default(1)}" data-max-color="${variant.get("maxColors")?default(1)}" data-print-desc="${variant.get("printPrice")?default("")}" data-coating="${variant.get("coating")?default("")}" data-url="<@ofbizUrl>/product/~category_id=${variant.get("primaryProductCategoryId")?default("null")}/~product_id=${variant.get("productId")}</@ofbizUrl>" data-hasCustomQty="${variant.get("hasCustomQty")?c}">
                <img class="productBackgroundColor" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/PC-<#if desc != "">${desc}</#if><#if weight != "">_${weight}</#if><#if texture != "">_${texture}</#if>" alt="${variant.get("desc")?if_exists} ${variant.weight?if_exists} ${variant.texture?if_exists}"/>
                <div class="foldersTabularRow folderDisplayTable productColorSelection">
                    <div selection-removeonselect class="paddingLeft10 width30"><span class="selectCheckbox"></span></div>
                    <div bns-colorselecttext class="textBold paddingLeft10">
                        <#if variant.get("brand")?exists && variant.get("brand")?matches("^.*?LUXPaper.*?$")?c == "true"><img src="<@ofbizContentUrl>/html/img/logo/luxPaperLogo.png</@ofbizContentUrl>" alt="LUXPaper" /><span>&trade;</span><br /></#if>
                        ${variant.get("desc")?if_exists} ${variant.weight?if_exists} ${variant.texture?if_exists}
                    </div>
                    <#if variant.get("new")?c == "true" && variant.get("sale")?c == "false" && variant.get("clearance")?c == "false">
                        <div class="newArrivalContainer">
                            <div>NEW!</div>
                        </div>
                    </#if>

                    <#if variant.clearance?if_exists && variant.clearance?c == 'true'>
                        <div class="clearanceContainer">
                            <div class="percentOff">${variant.percentSavings}% Off</div>
                        </div>
                    </#if>
                </div>
            </div>
            <#assign counter = counter + 1 />
        </#list>
        </div>
    </div>
</div>

<script>
    if (typeof createScrollableLock == 'function') { createScrollableLock($('#sidebar-colorList')); }

    if (typeof isColorAllowed == 'function') {
        $('#sidebar-colorList .selectListItem').each(function(){
            
            $(this).find('.productColorSelection').css('color', '#' + (isColorAllowed('ffffff', $(this).attr('data-hex')) ? 'ffffff' : '000000'));
            $(this).find('[bns-colorselecttext] img').attr('src', '/html/img/logo/luxPaperLogoN' + (isColorAllowed('ffffff', $(this).attr('data-hex')) ? '_white' : '') + '.png');
        });

        $('[bns-selectedcolortext]').css('color', '#' + (isColorAllowed('ffffff', $('[bns-selectedcolortext]').attr('data-hex')) ? 'ffffff' : '000000'));
        if ($('[bns-selectedcolortext] [bns-colorselecttext]').find('img').length > 0){
            $('[bns-selectedcolortext] [bns-colorselecttext]').find('img').attr('src', '/html/img/logo/luxPaperLogoN' + (isColorAllowed('ffffff', $('[data-target="colorSelection"].slSelected').attr('data-hex')) ? '_white' : '') + '.png');
        } 
    }
</script>