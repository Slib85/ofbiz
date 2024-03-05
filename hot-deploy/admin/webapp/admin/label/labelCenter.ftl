<link rel="stylesheet" type="text/css" href="/html/css/admin/product/productEditor.css">
<div class="row row-spacer ">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <form class="" name="productEditor" action="<@ofbizUrl>/labelCenter</@ofbizUrl>" method="GET">
            <div class="input-group">
                <input type="text" class="form-control" name="id" placeholder="Search for Product" value="${(productId)?if_exists}">
				<span class="input-group-btn">
					<button class="btn btn-success" type="submit">GO</button>
				</span>
            </div>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>
<#if pages gt 1>
<div class="row" style="text-align: center;">
    <ul class="pagination pagination-sm">
        <li class="<#if pageIndex + 1 == 1>disabled</#if>"><a href=""<#if pageIndex + 1 != 1><@ofbizUrl>/labelCenter</@ofbizUrl>?pageIndex=${pageIndex - 1}</#if>"><i class="entypo-left-open-mini"></i></a></li>
        <#list 1..pages as page>
            <li class="<#if pageIndex + 1 == page>active</#if>"><a href="<@ofbizUrl>/labelCenter</@ofbizUrl>?pageIndex=${page - 1}">${page}</a></li>
        </#list>
        <li class="<#if pageIndex + 1 == pages>disabled</#if>"><a href="<#if pageIndex + 1 != pages><@ofbizUrl>/labelCenter</@ofbizUrl>?pageIndex=${pageIndex + 1}</#if>"><i class="entypo-right-open-mini"></i></a></li>
    </ul>

    <div class="clear"></div>
</div>
</#if>
<div class="row" style="text-align: center;">
<#if labels?size gt 0>
    <#list labels as label>
        <div class="jqs-label" data-product-id="${label?replace(".png", "")}" style="width: 420px; display: inline-block; margin-right: 10px">
            <div class="tile-stats tile-primary" style="overflow: visible">
                <img src="/admin/control/serveFileForStream?filePath=/uploads/productLabels/${label}" style="width: 375px"/>
                <div class="row" style="margin: 11px 6px -7px 0px;">
                    <div class="btn btn-red jqs-rebuild" style="float:left;width:100px;">Rebuild</div>
                    <div class="btn btn-green jqs-print" style="float:right;width:100px;">Print</div>

                </div>
                <#--<h3>Prefix and Postfix</h3>
                <p>so far in our blog, and our website.</p>-->
            </div>
        </div>
    </#list>
<#else>
    <p>No labels found for the given SKU.</p>
</#if>
</div>
<script>
    $(function(){


        function bindRebuildEvent() {
            $('.jqs-label .jqs-rebuild').each(function(){
                var labelElement = $(this).closest('.jqs-label');

                $(this).on('click', function() {
                    rebuildEvent(labelElement);
                });
            });
        }

        function bindPrintEvent() {
            $('.jqs-label .jqs-print').each(function(){
                var labelElement = $(this).closest('.jqs-label');

                $(this).on('click', function() {
                    printEvent(labelElement);
                });
            });
        }

        function rebuildEvent(labelElement) {
            $.ajax({
                type: 'POST',
                url: '/admin/control/rebuildLabel',
                dataType: 'json',
                data: {
                    "productId": $(labelElement).data('product-id')
                }
            }).done(function(data) {
                if(data.success) {
                    console.log(data.labelURL);
                    $(labelElement).find('img').attr('src', '/admin/control/serveFileForStream?filePath=/' + data.labelURL);
                } else {
                    if(typeof data.error != 'undefined') {
                        alert(data.error);
                    } else {
                        data("There was an error trying to rebuild the label.");
                    }
                }
            }).error(function(data) {
                //todo error
            });
        }

        function printEvent(labelElement) {
            window.location.href = '/admin/control/printLabel?id=' + $(labelElement).data('product-id');
        }
        bindRebuildEvent();
        bindPrintEvent();
    });


</script>