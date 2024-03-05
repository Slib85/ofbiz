<#if hits?has_content>
    <#list hits.keySet() as parentid>
        <h3 id="${hits.get(parentid).get(0).getSourceAsMap().name?replace(" ", "")}" class="marginTop20 marginBottom20">${hits.get(parentid).get(0).getSourceAsMap().name}<#--<#if hits.get(parentid).get(0).getSourceAsMap().get("size")?exists> - (${hits.get(parentid).get(0).getSourceAsMap().get("size")})</#if>--></h3>
        <div class="padding20 noPaddingTop">
            <div class="slideIt-container">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div bns-categoryslideit class="slideIt textLeft">
                    <div>
                        <#list hits.get(parentid) as hit><#if hit.getSourceAsMap()?exists><div>
                            <a href="${hit.getSourceAsMap().get("url")?if_exists}">
                                <img src="${hit.getSourceAsMap().get("image")?if_exists}?wid=220&hei=200" alt="${hit.getSourceAsMap().get("name")?if_exists}">
                                <h2 class="ftc-blue marginTop5">${hit.getSourceAsMap().get("name")?if_exists}</h2>
                                <h4>SKU: ${hit.getSourceAsMap().get("productid")?if_exists}</h4>
                                <#if hit.getSourceAsMap().get("size")?has_content || hit.getSourceAsMap().get("color")?has_content>
                                <ul class="searchResultsProductInfo">
                                    <#if hit.getSourceAsMap().get("size")?has_content><li>${hit.getSourceAsMap().get("size")}</li></#if>
                                    <#if hit.getSourceAsMap().get("color")?has_content><li>${hit.getSourceAsMap().get("color")}</li></#if>
                                </ul>
                                </#if>
                                <#if hit.getSourceAsMap().get("basequantity")?exists>
                                <div>${hit.getSourceAsMap().get("basequantity")} Minimum Quantity</div>
                                </#if>
                            </a>
                        </div></#if></#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
            <#--
            <#list categoryMap.get(styleKey).keySet() as nameAndSizeKey>
                <div class="paddingLeft20 paddingRight20 paddingTop20 categorySubHeader marginTop20">
                    <h2>${nameAndSizeKey}</h2>
                </div>
                <div class="slideIt-container">
                    <div class="slideIt-left">
                        <i class="fa fa-chevron-left"></i>
                    </div>
                    <div bns-categoryslideit class="slideIt textLeft">
                        <div>
                            <#list categoryMap.get(styleKey).get(nameAndSizeKey).keySet() as productIdKey>
                            <div>
                                <a href="${categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("url")?if_exists}">
                                    <img src="${categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("image")?if_exists}" alt="${categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("name")?if_exists}">
                                    <h2 class="ftc-blue marginTop5">${categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("name")?if_exists}</h2>
                                    <h4>SKU: ${productIdKey?if_exists}</h4>
                                    <#if categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("size")?has_content || categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("color")?has_content>
                                    <ul class="searchResultsProductInfo">
                                        <#if categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("size")?has_content><li>${categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("size")}</li></#if>
                                        <#if categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("color")?has_content><li>${categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("color")}</li></#if>
                                    </ul>
                                    </#if>
                                    <#if categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("basequantity")?exists>
                                    <div>${categoryMap.get(styleKey).get(nameAndSizeKey).get(productIdKey).get("basequantity")} Minimum Quantity</div>
                                    </#if>
                                </a>
                            </div>
                            </#list>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <i class="fa fa-chevron-right"></i>
                    </div>
                </div>
            </#list>
            -->
        </div>
    </#list>
</#if>