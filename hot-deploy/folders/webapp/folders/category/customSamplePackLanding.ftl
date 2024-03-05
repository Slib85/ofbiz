<link href="<@ofbizContentUrl>/html/css/folders/search/searchResults.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/folders/category/category.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/product/customSamplePack.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" />

<div class="customSamplesLanding foldersNewLimiter paddingTop20 paddingBottom20">
	<div class="customSamplesHeader">
        <div class="customSamplesHeaderText">
            <h3>Order Custom Samples for Your Project Today!</h3>
            <p>Samples will ship out the same day if ordered before 3PM EST (M-F)</p>
            <p>&#42;Customers may only order up to 5 custom samples.</p>
        </div>
    	<#--  <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/customSamplePacksLandingBanner111618?fmt=png-alpha&amp;wid=469&amp;ts=2</@ofbizScene7Url>" alt="Custom Sample Packs Header" />  -->
        <div class="customSampleHeaderInfo">
            <div class="csh_wrap">
                <div class="csh_info_title">Have questions? Contact us!</div>
                <div class="csh_info_phone">
                    <i class="fa fa-phone-square"></i>
                    <p><span>Call Our Folder Experts: <a class="csh_green" href="tel:888-327-2812">888.327.2812</a></span> <span class="csh_green">Mon-Fri, 8 AM to 6 PM EST</span></p>
                </div>
                <div class="csh_info_email">
                    <i class="fa fa-envelope"></i>
                    <p><span>Email us details about your project</span> <span>at <a class="csh_green" href="mailto:service@folders.com">service@folders.com</a></span></p>
                </div>
            </div>
        </div>
	</div>
	<div class="noPadding">
        <div class="banner3 marginTop20 padding10  marginBottom20">
            <a href="<@ofbizUrl>/customSamplePacks</@ofbizUrl>">Custom Samples Pack</a>
            <span>|</span>
            <a href="#presentationFolders">9x12 Folders</a>
            <span>|</span>
            <a href="#quickShip">Quick Ship Folders</a>
            <span>|</span>
            <a href="#specialty">Specialty Folders</a>
            <span>|</span>
            <a href="#legal">Legal Folders</a>
            <span>|</span>
            <a href="#reinforced">Reinforced Folders</a>
            <span>|</span>
            <a href="#small">Small Folders</a>
            <span>|</span>
            <a href="#extraCapacity">Extra Capcity Folders</a>
            <span>|</span>
            <a href="#certificateHolders">Certificate Holders</a>      
        </div>
	    <div class="">
	    	<h4 id="customSamplePack">Custom Samples Pack</h4>
            <div class="customSamplesPackBanner marginTop20 textCenter">
    	    	<div class="customSamplesPackBannerText">
    	    		<h5>Custom Samples Pack</h5>
    	    		<p>This hand picked selection of custom printed folders will give you some hands-on insight to folders and products with Foil Stamping, Embossing, Full-Color Printing, custom pockets and more! Experience the wide range of customization options that Folders.com has to offer.</p>
                    <a href="<@ofbizUrl>/customSamplePacks</@ofbizUrl>" class="foldersButton sampleButton">Order Sample Pack <i class="fa fa-caret-right"></i></a>
    	    	</div>
    	    	<a href="<@ofbizUrl>/customSamplePacks</@ofbizUrl>">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/customSamplePackLandingBanner2?fmt=png-alpha&amp;wid=667&amp;ts=2</@ofbizScene7Url>" alt="Custom Sample Packs Header" />
                </a>
            </div>
	    </div>
	    <div class="productSection">
	    	<h4 id="presentationFolders" class="marginBottom30">9x12 Presentation Folders</h4>
            <div class="slideIt-container">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt textLeft">
                    <div>
                        <#list presentationFolders as folder>
                        <div>
                            <a href="<@ofbizUrl>/product/~category_id=/~product_id=${folder.getSourceAsMap().productid}/~print_method=/~order_samples=true</@ofbizUrl>" class="productInfoContainer">
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${folder.getSourceAsMap().productid?if_exists}?hei=170&amp;wid=190" alt="${folder.getSourceAsMap().name}" />
                                <div class="nameSkuText">
                                    <h4 bns-productName class="productName">${folder.getSourceAsMap().name}</h4>
                                    <p class="productSKU">SKU: ${folder.getSourceAsMap().productid}</p>
                                </div>
                                <#if folder.getSourceAsMap().get("size")?has_content>
                                <ul class="btsProductInfo">
                                    <li>${folder.getSourceAsMap().get("size")}</li>
                                    <#if folder.getSourceAsMap().get("color")?exists><li>${folder.getSourceAsMap().get("color")} ${folder.getSourceAsMap().get("paperweight")?if_exists} ${folder.getSourceAsMap().get("papertexture")?if_exists}</li></#if>
                                    <#if folder.getSourceAsMap().get("pocketspec")?exists><li>${folder.getSourceAsMap().get("pocketspec")}</li></#if>
                                    <#if folder.getSourceAsMap().get("tabsize")?exists><li>${folder.getSourceAsMap().get("tabsize")}</li></#if>
                                    <#if folder.getSourceAsMap().get("spinesize")?exists><li>${folder.getSourceAsMap().get("spinesize")}</li></#if>
                                </ul>
                                </#if>
                                <#if folder.getSourceAsMap().get("totalcolors")?exists><p class="ftc-blue marginBottom10">Available in ${folder.getSourceAsMap().get("totalcolors")} Color<#if folder.getSourceAsMap().get("totalcolors")?number gt 1>s</#if></p></#if>
                                <#if folder.getSourceAsMap().get("avgrating")?exists><div class="ratings rating-${(folder.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div></#if>
                                <#if folder.getSourceAsMap().get("baseprice")?exists && folder.getSourceAsMap().get("customizable") != 'Y'><div class="ftc-blue price">From ${folder.getSourceAsMap().get("baseprice")}</div></#if>
                                <div class="foldersButton sampleButton">Order Sample <i class="fa fa-caret-right"></i></div>
                            </a>
                        </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
            <h4 id="quickShip" class="marginBottom30">Quick Ship Folders</h4>
            <div class="slideIt-container">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt textLeft">
                    <div>
                        <#list quickShip as quickShipFolders>
                        <div>
                            <a class="productInfoContainer" href="<@ofbizUrl>/product/~category_id=/~product_id=${quickShipFolders.getSourceAsMap().productid}/~print_method=/~order_samples=true</@ofbizUrl>">
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${quickShipFolders.getSourceAsMap().productid?if_exists}?hei=170&amp;wid=190" alt="${quickShipFolders.getSourceAsMap().name}" />
                                <div class="nameSkuText">
                                    <h4 bns-productName class="productName">${quickShipFolders.getSourceAsMap().name}</h4>
                                    <p class="productSKU">SKU: ${quickShipFolders.getSourceAsMap().productid}</p>
                                </div>
                                <#if quickShipFolders.getSourceAsMap().get("size")?has_content>
                                <ul class="btsProductInfo">
                                    <li>${quickShipFolders.getSourceAsMap().get("size")}</li>
                                    <#if quickShipFolders.getSourceAsMap().get("color")?exists><li>${quickShipFolders.getSourceAsMap().get("color")} ${quickShipFolders.getSourceAsMap().get("paperweight")?if_exists} ${quickShipFolders.getSourceAsMap().get("papertexture")?if_exists}</li></#if>
                                    <#if quickShipFolders.getSourceAsMap().get("pocketspec")?exists><li>${quickShipFolders.getSourceAsMap().get("pocketspec")}</li></#if>
                                    <#if quickShipFolders.getSourceAsMap().get("tabsize")?exists><li>${quickShipFolders.getSourceAsMap().get("tabsize")}</li></#if>
                                    <#if quickShipFolders.getSourceAsMap().get("spinesize")?exists><li>${quickShipFolders.getSourceAsMap().get("spinesize")}</li></#if>
                                </ul>
                                </#if>
                                <#if quickShipFolders.getSourceAsMap().get("totalcolors")?exists><p class="ftc-blue marginBottom10">Available in ${quickShipFolders.getSourceAsMap().get("totalcolors")} Color<#if quickShipFolders.getSourceAsMap().get("totalcolors")?number gt 1>s</#if></p></#if>
                                    <#if quickShipFolders.getSourceAsMap().get("avgrating")?exists><div class="ratings rating-${(quickShipFolders.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div></#if>
                                    <#if quickShipFolders.getSourceAsMap().get("baseprice")?exists && quickShipFolders.getSourceAsMap().get("customizable") != 'Y'><div class="ftc-blue price">From ${quickShipFolders.getSourceAsMap().get("baseprice")}</div></#if>
                                <div class="foldersButton sampleButton">Order Sample <i class="fa fa-caret-right"></i></div>
                            </a>
                        </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
            <h4 id="specialty" class="marginBottom30">Specialty Folders</h4>
            <div class="slideIt-container">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt textLeft">
                    <div>
                        <#list specialty as specialtyFolders>
                        <div>
                            <a href="<@ofbizUrl>/product/~category_id=/~product_id=${specialtyFolders.getSourceAsMap().productid}/~print_method=/~order_samples=true</@ofbizUrl>" class="productInfoContainer">
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${specialtyFolders.getSourceAsMap().productid?if_exists}?hei=170&amp;wid=190" alt="${specialtyFolders.getSourceAsMap().name}" />
                                <div class="nameSkuText">
                                    <h4 bns-productName class="productName">${specialtyFolders.getSourceAsMap().name}</h4>
                                    <p class="productSKU">SKU: ${specialtyFolders.getSourceAsMap().productid}</p>
                                </div>
                                <#if specialtyFolders.getSourceAsMap().get("size")?has_content>
                                <ul class="btsProductInfo">
                                    <li>${specialtyFolders.getSourceAsMap().get("size")}</li>
                                    <#if specialtyFolders.getSourceAsMap().get("color")?exists><li>${specialtyFolders.getSourceAsMap().get("color")} ${specialtyFolders.getSourceAsMap().get("paperweight")?if_exists} ${specialtyFolders.getSourceAsMap().get("papertexture")?if_exists}</li></#if>
                                    <#if specialtyFolders.getSourceAsMap().get("pocketspec")?exists><li>${specialtyFolders.getSourceAsMap().get("pocketspec")}</li></#if>
                                    <#if specialtyFolders.getSourceAsMap().get("tabsize")?exists><li>${specialtyFolders.getSourceAsMap().get("tabsize")}</li></#if>
                                    <#if specialtyFolders.getSourceAsMap().get("spinesize")?exists><li>${specialtyFolders.getSourceAsMap().get("spinesize")}</li></#if>
                                </ul>
                                </#if>
                                <#if specialtyFolders.getSourceAsMap().get("totalcolors")?exists><p class="ftc-blue marginBottom10">Available in ${specialtyFolders.getSourceAsMap().get("totalcolors")} Color<#if specialtyFolders.getSourceAsMap().get("totalcolors")?number gt 1>s</#if></p></#if>
                                    <#if specialtyFolders.getSourceAsMap().get("avgrating")?exists><div class="ratings rating-${(specialtyFolders.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div></#if>
                                    <#if specialtyFolders.getSourceAsMap().get("baseprice")?exists && specialtyFolders.getSourceAsMap().get("customizable") != 'Y'><div class="ftc-blue price">From ${specialtyFolders.getSourceAsMap().get("baseprice")}</div></#if>
                                <div class="foldersButton sampleButton">Order Sample <i class="fa fa-caret-right"></i></div>
                            </a>
                        </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
            <h4 id="legal" class="marginBottom30">Legal Size Presentation Folders</h4>
            <div class="slideIt-container">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt textLeft">
                    <div>
                        <#list legal as legalFolders>
                        <div>
                            <a href="<@ofbizUrl>/product/~category_id=/~product_id=${legalFolders.getSourceAsMap().productid}/~print_method=/~order_samples=true</@ofbizUrl>" class="productInfoContainer">
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${legalFolders.getSourceAsMap().productid?if_exists}?hei=170&amp;wid=190" alt="${legalFolders.getSourceAsMap().name}" />
                                <div class="nameSkuText">
                                    <h4 bns-productName class="productName">${legalFolders.getSourceAsMap().name}</h4>
                                    <p class="productSKU">SKU: ${legalFolders.getSourceAsMap().productid}</p>
                                </div>
                                <#if legalFolders.getSourceAsMap().get("size")?has_content>
                                <ul class="btsProductInfo">
                                    <li>${legalFolders.getSourceAsMap().get("size")}</li>
                                    <#if legalFolders.getSourceAsMap().get("color")?exists><li>${legalFolders.getSourceAsMap().get("color")} ${legalFolders.getSourceAsMap().get("paperweight")?if_exists} ${legalFolders.getSourceAsMap().get("papertexture")?if_exists}</li></#if>
                                    <#if legalFolders.getSourceAsMap().get("pocketspec")?exists><li>${legalFolders.getSourceAsMap().get("pocketspec")}</li></#if>
                                    <#if legalFolders.getSourceAsMap().get("tabsize")?exists><li>${legalFolders.getSourceAsMap().get("tabsize")}</li></#if>
                                    <#if legalFolders.getSourceAsMap().get("spinesize")?exists><li>${legalFolders.getSourceAsMap().get("spinesize")}</li></#if>
                                </ul>
                                </#if>
                                <#if legalFolders.getSourceAsMap().get("totalcolors")?exists><p class="ftc-blue marginBottom10">Available in ${legalFolders.getSourceAsMap().get("totalcolors")} Color<#if legalFolders.getSourceAsMap().get("totalcolors")?number gt 1>s</#if></p></#if>
                                    <#if legalFolders.getSourceAsMap().get("avgrating")?exists><div class="ratings rating-${(legalFolders.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div></#if>
                                    <#if legalFolders.getSourceAsMap().get("baseprice")?exists && legalFolders.getSourceAsMap().get("customizable") != 'Y'><div class="ftc-blue price">From ${legalFolders.getSourceAsMap().get("baseprice")}</div></#if>
                                <div class="foldersButton sampleButton">Order Sample <i class="fa fa-caret-right"></i></div>
                            </a>
                        </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
            <h4 id="reinforced" class="marginBottom30">Reinforced Folders</h4>
            <div class="slideIt-container">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt textLeft">
                    <div>
                        <#list reinforced as reinforcedFolders>
                        <div>
                            <a href="<@ofbizUrl>/product/~category_id=/~product_id=${reinforcedFolders.getSourceAsMap().productid}/~print_method=/~order_samples=true</@ofbizUrl>" class="productInfoContainer">
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${reinforcedFolders.getSourceAsMap().productid?if_exists}?hei=170&amp;wid=190" alt="${reinforcedFolders.getSourceAsMap().name}" />
                                <div class="nameSkuText">
                                    <h4 bns-productName class="productName">${reinforcedFolders.getSourceAsMap().name}</h4>
                                    <p class="productSKU">SKU: ${reinforcedFolders.getSourceAsMap().productid}</p>
                                </div>
                                <#if reinforcedFolders.getSourceAsMap().get("size")?has_content>
                                <ul class="btsProductInfo">
                                    <li>${reinforcedFolders.getSourceAsMap().get("size")}</li>
                                    <#if reinforcedFolders.getSourceAsMap().get("color")?exists><li>${reinforcedFolders.getSourceAsMap().get("color")} ${reinforcedFolders.getSourceAsMap().get("paperweight")?if_exists} ${reinforcedFolders.getSourceAsMap().get("papertexture")?if_exists}</li></#if>
                                    <#if reinforcedFolders.getSourceAsMap().get("pocketspec")?exists><li>${reinforcedFolders.getSourceAsMap().get("pocketspec")}</li></#if>
                                    <#if reinforcedFolders.getSourceAsMap().get("tabsize")?exists><li>${reinforcedFolders.getSourceAsMap().get("tabsize")}</li></#if>
                                    <#if reinforcedFolders.getSourceAsMap().get("spinesize")?exists><li>${reinforcedFolders.getSourceAsMap().get("spinesize")}</li></#if>
                                </ul>
                                </#if>
                                <#if reinforcedFolders.getSourceAsMap().get("totalcolors")?exists><p class="ftc-blue marginBottom10">Available in ${reinforcedFolders.getSourceAsMap().get("totalcolors")} Color<#if reinforcedFolders.getSourceAsMap().get("totalcolors")?number gt 1>s</#if></p></#if>
                                    <#if reinforcedFolders.getSourceAsMap().get("avgrating")?exists><div class="ratings rating-${(reinforcedFolders.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div></#if>
                                    <#if reinforcedFolders.getSourceAsMap().get("baseprice")?exists && reinforcedFolders.getSourceAsMap().get("customizable") != 'Y'><div class="ftc-blue price">From ${reinforcedFolders.getSourceAsMap().get("baseprice")}</div></#if>
                                <div class="foldersButton sampleButton">Order Sample <i class="fa fa-caret-right"></i></div>
                            </a>
                        </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
            <h4 id="small" class="marginBottom30">Small Folders</h4>
            <div class="slideIt-container">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt textLeft">
                    <div>
                        <#list smallFolders as smallFolder>
                        <div>
                            <a href="<@ofbizUrl>/product/~category_id=/~product_id=${smallFolder.getSourceAsMap().productid}/~print_method=/~order_samples=true</@ofbizUrl>" class="productInfoContainer">
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${smallFolder.getSourceAsMap().productid?if_exists}?hei=170&amp;wid=190" alt="${smallFolder.getSourceAsMap().name}" />
                                <div class="nameSkuText">
                                    <h4 bns-productName class="productName">${smallFolder.getSourceAsMap().name}</h4>
                                    <p class="productSKU">SKU: ${smallFolder.getSourceAsMap().productid}</p>
                                </div>
                                <#if smallFolder.getSourceAsMap().get("size")?has_content>
                                <ul class="btsProductInfo">
                                    <li>${smallFolder.getSourceAsMap().get("size")}</li>
                                    <#if smallFolder.getSourceAsMap().get("color")?exists><li>${smallFolder.getSourceAsMap().get("color")} ${smallFolder.getSourceAsMap().get("paperweight")?if_exists} ${smallFolder.getSourceAsMap().get("papertexture")?if_exists}</li></#if>
                                    <#if smallFolder.getSourceAsMap().get("pocketspec")?exists><li>${smallFolder.getSourceAsMap().get("pocketspec")}</li></#if>
                                    <#if smallFolder.getSourceAsMap().get("tabsize")?exists><li>${smallFolder.getSourceAsMap().get("tabsize")}</li></#if>
                                    <#if smallFolder.getSourceAsMap().get("spinesize")?exists><li>${smallFolder.getSourceAsMap().get("spinesize")}</li></#if>
                                </ul>
                                </#if>
                                <#if smallFolder.getSourceAsMap().get("totalcolors")?exists><p class="ftc-blue marginBottom10">Available in ${smallFolder.getSourceAsMap().get("totalcolors")} Color<#if smallFolder.getSourceAsMap().get("totalcolors")?number gt 1>s</#if></p></#if>
                                    <#if smallFolder.getSourceAsMap().get("avgrating")?exists><div class="ratings rating-${(smallFolder.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div></#if>
                                    <#if smallFolder.getSourceAsMap().get("baseprice")?exists && smallFolder.getSourceAsMap().get("customizable") != 'Y'><div class="ftc-blue price">From ${smallFolder.getSourceAsMap().get("baseprice")}</div></#if>
                                <div class="foldersButton sampleButton">Order Sample <i class="fa fa-caret-right"></i></div>
                            </a>
                        </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
            <h4 id="extraCapacity" class="marginBottom30">Extra Capacity Folders</h4>
            <div class="slideIt-container">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt textLeft">
                    <div>
                        <#list extraCapacity as extraCapacityFolders>
                        <div>
                            <a href="<@ofbizUrl>/product/~category_id=/~product_id=${extraCapacityFolders.getSourceAsMap().productid}/~print_method=/~order_samples=true</@ofbizUrl>" class="productInfoContainer">
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${extraCapacityFolders.getSourceAsMap().productid?if_exists}?hei=170&amp;wid=190" alt="${extraCapacityFolders.getSourceAsMap().name}" />
                                <div class="nameSkuText">
                                    <h4 bns-productName class="productName">${extraCapacityFolders.getSourceAsMap().name}</h4>
                                    <p class="productSKU">SKU: ${extraCapacityFolders.getSourceAsMap().productid}</p>
                                </div>
                                <#if extraCapacityFolders.getSourceAsMap().get("size")?has_content>
                                <ul class="btsProductInfo">
                                    <li>${extraCapacityFolders.getSourceAsMap().get("size")}</li>
                                    <#if extraCapacityFolders.getSourceAsMap().get("color")?exists><li>${extraCapacityFolders.getSourceAsMap().get("color")} ${extraCapacityFolders.getSourceAsMap().get("paperweight")?if_exists} ${extraCapacityFolders.getSourceAsMap().get("papertexture")?if_exists}</li></#if>
                                    <#if extraCapacityFolders.getSourceAsMap().get("pocketspec")?exists><li>${extraCapacityFolders.getSourceAsMap().get("pocketspec")}</li></#if>
                                    <#if extraCapacityFolders.getSourceAsMap().get("tabsize")?exists><li>${extraCapacityFolders.getSourceAsMap().get("tabsize")}</li></#if>
                                    <#if extraCapacityFolders.getSourceAsMap().get("spinesize")?exists><li>${extraCapacityFolders.getSourceAsMap().get("spinesize")}</li></#if>
                                </ul>
                                </#if>
                                <#if extraCapacityFolders.getSourceAsMap().get("totalcolors")?exists><p class="ftc-blue marginBottom10">Available in ${extraCapacityFolders.getSourceAsMap().get("totalcolors")} Color<#if extraCapacityFolders.getSourceAsMap().get("totalcolors")?number gt 1>s</#if></p></#if>
                                    <#if extraCapacityFolders.getSourceAsMap().get("avgrating")?exists><div class="ratings rating-${(extraCapacityFolders.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div></#if>
                                    <#if extraCapacityFolders.getSourceAsMap().get("baseprice")?exists && extraCapacityFolders.getSourceAsMap().get("customizable") != 'Y'><div class="ftc-blue price">From ${extraCapacityFolders.getSourceAsMap().get("baseprice")}</div></#if>
                                <div class="foldersButton sampleButton">Order Sample <i class="fa fa-caret-right"></i></div>
                            </a>
                        </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
            <h4 id="certificateHolders" class="marginBottom30">Certificate Holders</h4>
            <div class="slideIt-container">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt textLeft">
                    <div>
                        <#list certificateHolders as certificateHolder>
                        <div>
                            <a href="<@ofbizUrl>/product/~category_id=/~product_id=${certificateHolder.getSourceAsMap().productid}/~print_method=/~order_samples=true</@ofbizUrl>" class="productInfoContainer">
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${certificateHolder.getSourceAsMap().productid?if_exists}?hei=170&amp;wid=190" alt="${certificateHolder.getSourceAsMap().name}" />
                                <div class="nameSkuText">
                                    <h4 bns-productName class="productName">${certificateHolder.getSourceAsMap().name}</h4>
                                    <p class="productSKU">SKU: ${certificateHolder.getSourceAsMap().productid}</p>
                                </div>
                                <#if certificateHolder.getSourceAsMap().get("size")?has_content>
                                <ul class="btsProductInfo">
                                    <li>${certificateHolder.getSourceAsMap().get("size")}</li>
                                    <#if certificateHolder.getSourceAsMap().get("color")?exists><li>${certificateHolder.getSourceAsMap().get("color")} ${certificateHolder.getSourceAsMap().get("paperweight")?if_exists} ${certificateHolder.getSourceAsMap().get("papertexture")?if_exists}</li></#if>
                                    <#if certificateHolder.getSourceAsMap().get("pocketspec")?exists><li>${certificateHolder.getSourceAsMap().get("pocketspec")}</li></#if>
                                    <#if certificateHolder.getSourceAsMap().get("tabsize")?exists><li>${certificateHolder.getSourceAsMap().get("tabsize")}</li></#if>
                                    <#if certificateHolder.getSourceAsMap().get("spinesize")?exists><li>${certificateHolder.getSourceAsMap().get("spinesize")}</li></#if>
                                </ul>
                                </#if>
                                <#if certificateHolder.getSourceAsMap().get("totalcolors")?exists><p class="ftc-blue marginBottom10">Available in ${certificateHolder.getSourceAsMap().get("totalcolors")} Color<#if certificateHolder.getSourceAsMap().get("totalcolors")?number gt 1>s</#if></p></#if>
                                    <#if certificateHolder.getSourceAsMap().get("avgrating")?exists><div class="ratings rating-${(certificateHolder.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div></#if>
                                <div class="foldersButton sampleButton">Order Sample <i class="fa fa-caret-right"></i></div>
                            </a>
                        </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
	    </div>
	</div>	
</div>

<script>
    $(document).ready(function() {
        $('[bns-productName]').each(function(i){
            var counter = 0;
            var maxHeight = 42;

            while (getFullHeight($(this)) > maxHeight && parseInt($(this).css('font-size')) > 12) {
                counter++;
                $(this).css('font-size', (parseInt($(this).css('font-size')) - 1) + 'px');
                if(counter > 100) {
                    break;
                }
            }
            if (getFullHeight($(this)) > maxHeight && parseInt($(this).css('font-size')) == 12) {
                $(this).css({
                    'font-size': '15px',
                    'overflow': 'hidden',
                    'text-overflow': 'ellipsis',
                    'display': '-webkit-box',
                    '-webkit-box-orient': 'vertical',
                    '-webkit-line-clamp': '2',
                    'height': '40px'
                });
            }
            
        });
    });
    
</script>