<#assign printMethod = requestParameters.print_method?default("SPOT") />
<#list pocketStyles.keySet() as featureId>
    <#if pocketStyles.get(featureId).get("desc")?upper_case == requestParameters.pocketDescription?default("STANDARD")>
        <#assign featureIdForCardSlits = featureId />
        <#break />
    </#if>
</#list>
<div>
    <div class="productSidebarSection">
        <h3 class="sidebarHeader">
            Build Your Folder
            <span bns-loadPreviousProductStep class="pullRight hidden">Go Back</span>
        </h3>
        <div>
            <#include "../../includes/product/productAssets/printMethod.ftl" />
        </div>
        <div>
            <#include "../../includes/product/productAssets/paperColors.ftl" />
        </div>
        <div bns-hideCoatingsContent="<#if printMethod == "FOIL">true</#if>">
            <#include "../../includes/product/productAssets/coatings.ftl" />
        </div>
        <div bns-hideFoilColorsContent="<#if printMethod != "FOIL">true</#if>">
            <#include "../../includes/product/productAssets/foilColors.ftl" />
        </div>
    </div>

    <div class="foldersTabularRow pocketsCornersSlits">
        <div bns-styleParent="pockets">
            <#include "../../includes/product/productAssets/pockets.ftl" />
        </div>
        <#if featureIdForCardSlits?exists && pocketStyles.get(featureIdForCardSlits).get("assocs").get("CARD_SLITS")?exists>
        <div bns-styleParent="cardSlits">
            <#include "../../includes/product/productAssets/cardSlits.ftl" />
        </div>
        </#if>
    </div>

    <div class="additionalFolderOptions">
        <#include "../../includes/product/productAssets/attachments.ftl" />
    </div>

    <div class="productSidebarSection">
        <#include "../../includes/product/productAssets/quantityAndPricing.ftl" />
        <div class="foldersButton buttonGreen marginTop10 noMarginBottom padding10 fullWidth" bns-loadProductStep="folderDesign">Next: Customize <i class="fa fa-caret-right pullRight"></i></div>
    </div>
</div>