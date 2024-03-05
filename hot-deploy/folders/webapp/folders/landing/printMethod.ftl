<link href="<@ofbizContentUrl>/html/css/folders/landing/printMethod.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<div class="printingMethodBanner margin10">
    <h1>${printMethodInfo.h1?if_exists}</h1>
    <p>${printMethodInfo.bannerText?if_exists}</p>
    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${printMethodInfo.bannerImage?if_exists}?fmt=png-alpha&amp;wid=373</@ofbizScene7Url>" alt="${printMethodInfo.h1?if_exists}">
</div>
<div class="printingMethodContainer">
    <div class="leftContent">
        <a href="<@ofbizUrl>/${printMethodInfo.standardFolderProductLink?if_exists}</@ofbizUrl>">
            <img class="marginTop20" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${printMethodInfo.standardFolderProductImage?if_exists}?fmt=png-alpha&amp;hei=258</@ofbizScene7Url>" alt="${printMethodInfo.standardFolderProductImage?if_exists} Folder">
            <p>9x12 Presentation Folders <i class="fa fa-caret-right"></i></p>
        </a>
        <a href="<@ofbizUrl>/${printMethodInfo.legalProductLink?if_exists}</@ofbizUrl>">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${printMethodInfo.legalProductImage?if_exists}?fmt=png-alpha&amp;hei=278&amp;ts=2</@ofbizScene7Url>" alt="${printMethodInfo.legalProductImage?if_exists} Folder">
            <p>Legal Size Folders <i class="fa fa-caret-right"></i></p>
        </a>
        <a href="<@ofbizUrl>/${printMethodInfo.certificateHolderProductLink?if_exists}</@ofbizUrl>">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${printMethodInfo.certificateHolderProductImage?if_exists}?fmt=png-alpha&amp;hei=278&amp;wid=275</@ofbizScene7Url>" alt="${printMethodInfo.certificateHolderProductImage?if_exists} Folder">
            <p>Certificate Holders <i class="fa fa-caret-right"></i></p>
        </a>
        <#if printMethodInfo.type == "embossing">
        <a href="<@ofbizUrl>/${printMethodInfo.cardHolderProductLink?if_exists}</@ofbizUrl>">
            <img class="embossingImgMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${printMethodInfo.cardHolderProductImage?if_exists}?fmt=png-alpha&amp;hei=248&amp;wid=275</@ofbizScene7Url>" alt="${printMethodInfo.cardHolderProductImage?if_exists} Folder">
            <p>Small Folders <i class="fa fa-caret-right"></i></p>
        </a>
        <#else>
        <a href="<@ofbizUrl>/${printMethodInfo.cardHolderProductLink?if_exists}</@ofbizUrl>">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${printMethodInfo.cardHolderProductImage?if_exists}?fmt=png-alpha&amp;hei=278&amp;wid=275</@ofbizScene7Url>" alt="${printMethodInfo.cardHolderProductImage?if_exists} Folder">
            <p>Small Folders <i class="fa fa-caret-right"></i></p>
        </a>
        </#if>
    </div>
    <div class="rightContent">
        <div class="colorTypeDescription">
            <h4>${printMethodInfo.sidebarCopyHeader?if_exists}</h4>
            <p>${printMethodInfo.sidebarCopy?if_exists}</p>
        </div>
        <#if printMethodInfo.type == "foilStamp">
        <div class="foilColorList marginRight5">
            <h4>Metallic Colors</h4>
            <div class="slideIt-container vertical">
                <div class="slideIt-up">
                    <i class="fa fa-chevron-up"></i>
                </div>
                <div class="slideIt vertical" bns-verticalslideit="true">
                    <div class="">
                        <#list metallicFoilList as foil><div class="materialImage marginBottom20 paddingLeft20 paddingRight20">
                            <img src="/html/img/folders/customerService/foilGuide/${foil.img?if_exists}" alt="${foil.foilId?if_exists}" />
                            <a class="ftc-blue marginTop10 textCenter" href="<@ofbizUrl>/foilGuide</@ofbizUrl>">${foil.foilId?if_exists}</a>
                        </div></#list>
                    </div>
                </div>
                <div class="slideIt-down">
                    <i class="fa fa-chevron-down"></i>
                </div>
            </div>
        </div>
        <div class="foilColorList">
            <h4>Non-Metallic Colors</h4>
            <div class="slideIt-container vertical">
                <div class="slideIt-up">
                    <i class="fa fa-chevron-up"></i>
                </div>
                <div class="slideIt vertical" bns-verticalslideit="true">
                    <div class="">
                        <#list nonMetallicFoilList as nmfoil><div class="materialImage marginBottom20 paddingLeft20 paddingRight20" href="<@ofbizUrl>/foilGuide</@ofbizUrl>">
                            <img src="/html/img/folders/customerService/foilGuide/${nmfoil.img?if_exists}" alt="${nmfoil.foilId?if_exists}" />
                            <div class="ftc-blue marginTop10 textCenter">${nmfoil.foilId?if_exists}</div>
                        </div></#list>
                    </div>
                </div>
                <div class="slideIt-down">
                    <i class="fa fa-chevron-down"></i>
                </div>
            </div>
        </div>
        <#else>
        <div class="printMethodColorList marginTop10">
            <div class="slideIt-container vertical">
                <div class="slideIt-up">
                    <i class="fa fa-chevron-up"></i>
                </div>
                <div class="slideIt vertical" bns-verticalslideit="true">
                    <div class="">
                        <#if printMethodInfo.type == "fourColor">
                            <#list fourColor as fourColorList>
                                <#assign color = fourColorList.getSourceAsMap().get("color")?upper_case?replace(" ", "_")?replace("%", "") />
                                <#assign desc = fourColorList.getSourceAsMap().get("color")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace("X2F", "")?replace(" ", "_") />
                                <#assign weight = fourColorList.getSourceAsMap().get("paperweight")?upper_case?replace(".", "") />
                                <#assign texture = fourColorList.getSourceAsMap().get("papertexture")?if_exists?upper_case?replace("-", "") />
                                <#if fourColorList_index % 2 == 0>
                                    <div>
                                </#if>
                                    <div class="printMethodList">
                                        <img src="https://actionenvelope3.scene7.com/is/image/ActionEnvelope/PC-${desc?default("")}_${weight?default("")}<#if texture?exists && texture != "">_${texture}</#if>?fmt=png-alpha&amp;wid=100" />
                                        
                                        <p>${fourColorList.getSourceAsMap().get("color")?if_exists}</p>
                                        <p>${fourColorList.getSourceAsMap().get("paperweight")?if_exists} ${fourColorList.getSourceAsMap().get("papertexture")?if_exists}</p>
                                    </div>
                                <#if fourColorList_index % 2 == 1>
                                    </div>
                                </#if>
                            </#list>
                            <#if fourColor?size % 2 == 1>
                                </div>
                            </#if>
                        <#else>
                            <#list spotColorEmboss as spotColorEmbossList>
                                <#assign color = spotColorEmbossList.getSourceAsMap().get("color")?upper_case?replace(" ", "_") />
                                <#assign weight = spotColorEmbossList.getSourceAsMap().get("paperweight")?upper_case?replace(".", "") />
                                <#assign texture = spotColorEmbossList.getSourceAsMap().get("papertexture")?if_exists?upper_case?replace("-", "") />

                                <#if spotColorEmbossList_index % 2 == 0>
                                    <div>
                                </#if>
                                    <div class="printMethodList">
                                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/PC-${color}_${weight}<#if texture?exists && texture != ''>_${texture}</#if>?fmt=png-alpha&amp;wid=100</@ofbizScene7Url>">
                                        <p>${spotColorEmbossList.getSourceAsMap().get("color")?if_exists}</p>
                                        <p>${spotColorEmbossList.getSourceAsMap().get("paperweight")?if_exists} ${spotColorEmbossList.getSourceAsMap().get("papertexture")?if_exists}</p>
                                    </div>
                                <#if spotColorEmbossList_index % 2 == 1>
                                    </div>
                                </#if>
                            </#list>
                            <#if spotColorEmboss?size % 2 == 1>
                                </div>
                            </#if>
                        </#if>  
                    </div>
                </div>
                <div class="slideIt-down">
                    <i class="fa fa-chevron-down"></i>
                </div>
            </div>
            <a class="viewAllLink" href="<@ofbizUrl>/paperStocks</@ofbizUrl>">View All Colors</a>
        </div>
        </#if>
    </div>
</div>