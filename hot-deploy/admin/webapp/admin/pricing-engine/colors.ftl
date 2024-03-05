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

<div class="modal fade jqs-color-details" id="jqs-add-item" role="dialog">
    <div class="modal-dialog">
        <div class="jqs-add-dialog modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Create New Color</h4>
            </div>
            <form name="saveForm" action="<@ofbizUrl>/peSaveColorAndStock</@ofbizUrl>" onsubmit="return false">
                <div class="panel-body">
                    <div class="form-group">
                        <label class="form-control-label">Color Code</label>
                        <input type="text" required="required" class="form-control" name="colorCode" value="">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Vendor</label>
                        <select class="form-control" name="vendorId">
                            <option value=""> Select One</option>
                            <#list vendors as vendor>
                                <option value="${vendor.vendorId}">${vendor.vendorName}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Color Group</label>
                        <input type="text" class="form-control" name="colorGroup" value="">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Color Name</label>
                        <input type="text" class="form-control" name="colorName" value="">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Paper Texture</label>
                        <input type="text" class="form-control" name="paperTexture" value="">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Paper Weight</label>
                        <input type="text" class="form-control" name="paperWeight" value="">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Vendor Stock ID</label>
                        <input type="text" class="form-control" name="vendorStockId" value="">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Vendor Stock</label>
                        <input type="text" class="form-control" name="vendorStock" value="">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Pricing Attribute ID</label>
                        <select name="attributeId" class="form-control" placeholder="Pricing Attribute">
                            <option value=""> Select One</option>
                            <#list pricingAttributes as attribute>
                                <option value="${attribute.attributeId}">${attribute.attributeId}</option>
                            </#list>
                        </select>
                    </div>

                </div>
                <div class="panel-footer text-xs-right">
                    <button type="button" class="btn btn-default m-r-1" data-dismiss="modal">Cancel</button>
                    <button type="button" class="jqs-save-item btn btn-primary" data-dialog="true">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="panel" id="jqs-items-panel">
    <div class="panel-body">
        <div class="add-item text-xs-right" style="margin-bottom: 15px">
            <a class="panel-action" data-toggle="modal" data-target="#jqs-add-item"  title="Add Color"><button type="button" class="btn btn-success"><i class="icon wb-plus" aria-hidden="true"></i> Add Color</button></a>
        </div>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="items-grid">
                <thead>
                <tr>
                    <th>COLOR_CODE</th>
                    <th>VENDOR</th>
                    <th>COLOR_GROUP</th>
                    <th>COLOR_NAME</th>
                    <th>PAPER_TEXTURE</th>
                    <th>PAPER_WEIGHT</th>
                    <th>STOCK</th>
                    <th>ATTRIBUTE_ID</th>
                    <th></th>
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>COLOR_CODE</th>
                    <th>VENDOR</th>
                    <th>COLOR_GROUP</th>
                    <th>COLOR_NAME</th>
                    <th>PAPER_TEXTURE</th>
                    <th>PAPER_WEIGHT</th>
                    <th>STOCK</th>
                    <th>ATTRIBUTE_ID</th>
                    <th></th>
                </tr>
                </tfoot>
                <tbody>
                    <#assign count = 0>
                    <#list items as color>
                    <tr role="row" id="${color.colorCode}-${color.vendorId}" class="<#if (count % 2) == 0>odd<#else>even</#if>">
                        <td><a href="<@ofbizUrl>/peColors</@ofbizUrl>?colorCode=${color.colorCode}&vendorId=${color.vendorId}">${color.colorCode}</a></td>
                        <td>${color.vendorId!}</td>
                        <td>${color.colorGroup!}</td>
                        <td>${color.colorName!}</td>
                        <td>${color.paperTexture!}</td>
                        <td>${color.paperWeight!}</td>
                        <td>${color.vendorStock!}</td>
                        <td>${color.pricingAttributeId!}</td>
                        <td class="text-nowrap" style="text-align: center">
                            <button type="button" class="jqs-edit-color btn btn-sm btn-icon btn-flat btn-default" data-toggle="tooltip" data-original-title="Edit">
                                <i class="icon wb-wrench" aria-hidden="true"></i>
                            </button>
                            <button type="button" class="jqs-delete-color btn btn-sm btn-icon btn-flat btn-default" data-toggle="tooltip" data-original-title="Delete">
                                <i class="icon wb-close" style="color: red;" aria-hidden="true"></i>
                            </button>
                        </td>
                    </tr>
                        <#assign count = count+1 />
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="/html/js/admin/pricing-engine/peBase.js"></script>
<script src="/html/js/admin/pricing-engine/peColors.js"></script>