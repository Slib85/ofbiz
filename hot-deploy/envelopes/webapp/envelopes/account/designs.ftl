<link href="<@ofbizContentUrl>/html/css/account/account.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/account/designs.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="content account">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Saved Designs
	</div>
    <div class="container designs padding-xs">
        <div id="design-message" data-alert class="alert-box success radius margin-top-xxs hidden">Design has been successfully deleted.</div>
        <div id="design-error" data-alert class="alert-box alert radius margin-top-xxs hidden">An error occurred while deleting the design.</div>
        <div class="row design-list">
            <#list designs as design>
                <#if design.productType == "product">
                    <#assign editUrl = "/product/~category_id=" + design.categoryId?default("null") + "/~product_id=" + design.sku?default("null") />
                <#else>
                    <#assign mainDesign = Static["com.envelopes.scene7.Scene7Helper"].getMainProjectDesign(delegator, design.projectId) />
                    <#assign editUrl = "/product/~designId=" + mainDesign />
                </#if>
                <#assign prodUrl><@ofbizUrl>${editUrl}</@ofbizUrl>#${design.projectId}</#assign>
                <div id="design-${design.projectId}" class="padding-xxs margin-right-xxs margin-top-xxs left" style="min-width: 200px">
                    <div style="min-height: 100px">
                        <img src="/envelopes/control/scene7Image.png?id=${design.scene7DesignId}&setWidth=100" alt="Your Design" />
                    </div>
                    <div>
					<span class="jqs-name">
						${design.lastUpdatedStamp}
					</span>
                        <div>
                            <span>
                                <a href="javascript:void(0)" class="jqs-delete-design" data-design-id="${design.projectId}">delete</a>
                            </span>
                            <span>
                                <a href="javascript:void(0)" class="jqs-edit-design" data-edit-url="${prodUrl}">edit</a>
                            </span>
                        </div>

                    </div>
                </div>
            </#list>
        </div>
    </div>
</div>
<script>
    var deleteDesignEndPoint = '<@ofbizUrl>/deleteDesign</@ofbizUrl>';
</script>
<script src="<@ofbizContentUrl>/html/js/account/saved-designs.js</@ofbizContentUrl>"></script>