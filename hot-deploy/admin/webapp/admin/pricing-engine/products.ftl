<link rel="stylesheet" href="/html/css/admin/pricing-engine/peProducts.css">
<div class="page-header-override" style="display: none;">
    <h1 class="page-title">${title}</h1>
    <div class="page-header-actions">
        <ol class="breadcrumb">
            <#list breadcrumbs as breadcrumb>
                <#if breadcrumb.getValue()?? && breadcrumb.getValue() != ''>
                    <li class="breadcrumb-item"><a href="${breadcrumb.getValue()}">${breadcrumb.getKey()}</a></li>
                <#else>
                    <li class="breadcrumb-item active">${breadcrumb.getKey()}</li>
                </#if>
            </#list>
        </ol>
    </div>
</div>
<script>
    $('.page-header').html($('.page-header-override').html());
</script>

<#if pageType == 'Products'>
<div class="modal fade" id="jqs-products-upload" role="dialog">
    <div class="modal-dialog">
        <div class="jqs-add-dialog modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add New Product(s)</h4>
            </div>
            <div class="modal-body">
                <h4>Product File Upload</h4>
                <h6 style="color:green; position: relative; top: -29px; left:176px">[Allowed File Types : .xls, .xlsx]</h6>
                <form>
                    <div class="form-group">
                        <div class="input-group input-group-file" data-plugin="inputGroupFile">

                            <input type="text" id="jqs-fileName" class="form-control" readonly="readonly">
                            <span class="input-group-btn">
                                <span class="btn btn-success btn-file">
                                    <i class="icon wb-upload" aria-hidden="true" title="aa"></i>
                                    <input id="fileupload" type="file" name="files[]" data-url="<@ofbizUrl>/peProductUpload</@ofbizUrl>" multiple="">
                                </span>
                            </span>

                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">

            </div>
        </div>
    </div>
</div>
<div class="panel" id="jqs-items-panel">
    <div class="panel-body">
        <div class="add-item text-xs-right" style="margin-bottom: 15px">
            <button type="button" data-toggle="modal" data-target="#jqs-products-upload" class="jqs-add-products btn btn-dark"><i class="icon wb-plus" aria-hidden="true"></i> Add Product(s)</button>
        </div>

        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="items-grid">
                <thead>
                <tr>
                    <th style="width: 100px;">PRODUCT ID</th>
                    <th style="width: 300px;">NAME</th>
                    <th style="width: 100px;">VENDOR_ID</th>
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>PRODUCT ID</th>
                    <th>NAME</th>
                    <th>VENDOR_ID</th>
                </tr>
                </tfoot>
                <tbody>
                <#assign count = 0>
                <#list items as product>
                <tr role="row" id="${product.vendorProductId}"class="<#if (count % 2) == 0>odd<#else>even</#if>">
                    <td><a href="<@ofbizUrl>/peProducts</@ofbizUrl>?productId=${product.vendorProductId}&vendorId=${product.vendorId}">${product.vendorProductId}</a></td>
                    <td>${product.productName}</td>
                    <td>${product.vendorId}</td>
                </tr>
                <#assign count = count+1 />
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
<#else>
    <div id="jqs-product-details" class="panel panel-primary panel-line">
        <div class="panel-heading">
            <h3 class="panel-title">${productId}</h3>
        </div>
        <form name="saveForm" action="<@ofbizUrl>/peSaveProduct</@ofbizUrl>" onsubmit="return false">
            <input type="hidden" name="productId" value="${productId}" />
            <input type="hidden" name="vendorId" value="${vendorId}" />
            <div class="panel-body">
                <div class="form-group">
                    <label class="form-control-label">Product Name:</label>
                    <input type="text" class="form-control" name="productName" value="${product.productName!}">
                    <small class="text-error" data-default-message="Product Name is required and cannot be empty"></small>
                </div>
                <div class="form-group">
                    <label class="form-control-label">Short Name:</label>
                    <input type="text" class="form-control" name="shortName" value="${product.shortName!}">
                    <small class="text-error"></small>
                </div>
                <div class="form-group">
                    <label class="form-control-label">Description:</label>
                    <textarea type="text" class="form-control" name="description">${product.description!}</textarea>
                    <small class="text-error"></small>
                </div>
                <div class="form-group">
                    <label class="form-control-label">Vendor:</label>
                    <select class="form-control" name="vendor">
                        <option value="V_ADMORE">Admore</option>
                    </select>
                    <small class="text-error"></small>
                </div>
            </div>
            <div class="panel-footer text-xs-right">
                <button type="button" class="btn btn-default m-r-1" onclick="window.location.href='/admin/control/peProducts'">Cancel</button>
                <button type="button" class="jqs-save-item btn btn-primary">Save</button>
            </div>
        </form>
    </div>
    <div class="modal fade" id="jqs-pricing-attribute" role="dialog">
        <div class="modal-dialog">
            <div class="jqs-add-dialog modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Pricing Attribute for ${productId}</h4>
                </div>
                <form name="addForm" action="<@ofbizUrl>/peSavePricingAttributeValue</@ofbizUrl>" onsubmit="return false">
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="input-group">
                                    <span class="input-group-addon">Pricing Attribute ID</span>
                                    <select name="attributeId" class="form-control" placeholder="Pricing Attribute">
                                        <option value=""> Select One</option>
                                        <#list pricingAttributes as attribute>
                                            <option value="${attribute.attributeId}">${attribute.attributeId}</option>
                                        </#list>
                                    </select>
                                    <input type="hidden" name="vendorId" value="${vendorId}" />
                                    <input type="hidden" name="productId" value="${productId}" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <#--<#list pricingDetails.get(0)['quantityBreak'] as qty>-->
                            <#list generalQuantityBreak as qty>
                            <div class="jqs-pricing col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                <div class="input-group">
                                    <span class="input-group-addon">${qty}</span>
                                    <input type="text" class="form-control" placeholder="">
                                </div>
                            </div>
                            </#list>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="jqs-save-pricing-attribute btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div id="jqs-product-pricing-details" class="panel panel-primary panel-line">
        <div class="panel-heading">
            <h3 class="panel-title">Pricing Details</h3>
        </div>
        <div class="panel-body">
            <div class="table-responsive" style="font-size: 14px;">
                <div class="add-item text-xs-right" style="margin-bottom: 15px">
                    <button type="button" class="jqs-add-pricing-attribute btn btn-dark"><i class="icon wb-plus" aria-hidden="true"></i> Add Pricing Detail</button>
                </div>
                <div style="overflow: auto">
                <table class="table jqs-pricing-details" data-role="content" data-plugin="selectable" data-row-selectable="true" style="min-width:1280px;">
                    <#if pricingDetails?has_content>
                        <#list pricingDetails as pricingDetail>
                            <#if pricingDetail_index == 0>
                                <thead class="bg-blue-grey-100">
                                <tr>
                                    <th style="max-width: 75px;">Attribute</th>
                                    <#--<#list pricingDetail['quantityBreak'] as qty>-->
                                        <#--<th style="text-align:right; width: 40px">${qty}</th>-->
                                    <#--</#list>-->
                                    <#list generalQuantityBreak as qty>
                                        <th style="text-align:right; width: 40px">${qty}</th>
                                    </#list>
                                    <th style="text-align:right; width: 40px"></th>
                                </tr>
                                </thead>
                            <tbody>
                            </#if>
                        <tr style="font-family: monospace" id="${pricingDetail.attributeId}">
                            <td style="text-align: left;width: 75px;">${pricingDetail.attributeId}</td>
                            <#list pricingDetail['volumePrice'] as price>
                                <td style="text-align:right; width: 40px">${price}</td>
                            </#list>
                            <td class="text-nowrap" style="text-align: center">
                                <button type="button" class="jqs-edit-pricing-attribute btn btn-sm btn-icon btn-flat btn-default" data-toggle="tooltip" data-original-title="Edit">
                                    <i class="icon wb-wrench" aria-hidden="true"></i>
                                </button>
                                <button type="button" class="jqs-delete-pricing-attribute btn btn-sm btn-icon btn-flat btn-default" data-toggle="tooltip" data-original-title="Delete">
                                    <i class="icon wb-close" style="color: red;" aria-hidden="true"></i>
                                </button>
                            </td>
                        </tr>
                            <#if pricingDetail_index == 0>
                            </tbody>
                            </#if>
                        </#list>
                    </#if>
                </table>
                </div>
            </div>
        </div>
    </div>


</#if>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.iframe-transport.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.fileupload.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/admin/pricing-engine/peBase.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/admin/pricing-engine/peProducts.js</@ofbizContentUrl>"></script>


