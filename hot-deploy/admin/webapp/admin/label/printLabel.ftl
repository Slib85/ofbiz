<link rel="stylesheet" type="text/css" href="/html/css/admin/product/productEditor.css">
<div class="row row-spacer">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <form class="" name="productEditor" action="<@ofbizUrl>/printLabel</@ofbizUrl>" method="GET">
            <div class="input-group">
                <input type="text" class="form-control" name="id" placeholder="Search for Product" value="${(productIdWithQty)?if_exists}">
				<span class="input-group-btn">
					<button class="btn btn-success" type="submit">GO</button>
				</span>
            </div>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>
<#if productId?exists>
<iframe src="/admin/control/serveFileForStream?filePath=${labelURL}" scrolling="yes" width="600" height="400" style="position: absolute; top: -1000px;visibility:hidden">
    <p>Your browser does not support iframes</p>
</iframe>
<div class="row">
    <div class="jqs-label" data-product-id="${labelURL?replace(".pdf", "")}" style="width: 670px; margin: auto; margin-top: 20px;">
        <div class="tile-stats tile-primary" style="overflow: visible">
            <img src="/admin/control/serveFileForStream?filePath=${labelURL?replace(".pdf", ".png")}" style="width: 625px"/>
            <div class="row" style="margin: 11px 6px -7px 0px;">
                <div class="btn btn-green jqs-print" style="float:right;width:100px;" onclick="$('iframe')[0].contentWindow.print()">Print</div>
            </div>
        </div>
    </div>
</div>

<#else>
    <p>${printLabelError}</p>
</#if>