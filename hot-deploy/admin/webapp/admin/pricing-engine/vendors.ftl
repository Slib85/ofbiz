
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

<#if pageType == 'Vendors'>
    <div class="panel" id="jqs-items-panel">
    <div class="panel-body">
        <div class="add-item text-xs-right" style="margin-bottom: 15px">
            <#--<a class="panel-action" data-toggle="modal" data-target="#jqs-add-item"  title="Add Vendor"><button type="button" class="btn btn-success"><i class="icon wb-plus" aria-hidden="true"></i> Add Vendor</button></a>-->
        </div>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="items-grid">
                <thead>
                <tr>
                    <th style="width: 100px;">VENDOR ID</th>
                    <th style="width: 300px;">NAME</th>
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>VENDOR ID</th>
                    <th>NAME</th>
                </tr>
                </tfoot>
                <tbody>
                    <#assign count = 0>
                    <#list items as vendor>
                    <tr role="row" id="${vendor.vendorId}" class="<#if (count % 2) == 0>odd<#else>even</#if>">
                        <td><a href="<@ofbizUrl>/peVendors</@ofbizUrl>?vendorId=${vendor.vendorId}">${vendor.vendorId}</a></td>
                        <td>${vendor.vendorName!}</td>
                    </tr>
                        <#assign count = count+1 />
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
<#else>
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
    <div id="jqs-vendor-details" class="panel panel-primary panel-line">
        <div class="panel-heading">
            <h3 class="panel-title">${vendorId}</h3>
        </div>
        <form name="saveForm" action="<@ofbizUrl>/peSaveVendor</@ofbizUrl>" onsubmit="return false">
            <input type="hidden" name="vendorId" value="${vendorId}" />
            <div class="panel-body">
                <div class="form-group">
                    <label class="form-control-label">Vendor Name:</label>
                    <input type="text" class="form-control" name="vendorName" value="${vendor.vendorName!}">
                    <small class="text-error" data-default-message="Vendor Name is required and cannot be empty"></small>
                </div>
            </div>
            <div class="panel-footer text-xs-right">
                <button type="button" class="btn btn-default m-r-1" onclick="window.location.href='/admin/control/peVendors'">Cancel</button>
                <button type="button" class="jqs-save-item btn btn-primary">Save</button>
            </div>
        </form>
    </div>
    <div class="panel panel-primary panel-line">
    <div class="panel-heading">
        <h3 class="panel-title">${vendor.vendorName!} Products</h3>
    </div>
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
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>PRODUCT ID</th>
                    <th>NAME</th>
                </tr>
                </tfoot>
                <tbody>
                    <#assign count = 0>
                    <#list vendorProducts as product>
                    <tr role="row" id="${product.vendorProductId}"class="<#if (count % 2) == 0>odd<#else>even</#if>">
                        <td><a href="<@ofbizUrl>/peProducts</@ofbizUrl>?productId=${product.vendorProductId}&vendorId=${product.vendorId}">${product.vendorProductId}</a></td>
                        <td>${product.productName}</td>
                    </tr>
                        <#assign count = count+1 />
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</#if>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.iframe-transport.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.fileupload.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/admin/pricing-engine/peBase.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/admin/pricing-engine/peVendors.js</@ofbizContentUrl>"></script>