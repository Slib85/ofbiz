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

<#if pageType == 'Attributes'>
    <div class="modal fade jqs-pricing-attribute-details" id="jqs-add-item" role="dialog">
        <div class="modal-dialog">
            <div class="jqs-add-dialog modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Create New Pricing Attribute</h4>
                </div>
                <form name="saveForm" action="<@ofbizUrl>/peSaveAttribute</@ofbizUrl>" onsubmit="return false">
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="form-control-label">Pricing Attribute ID:</label>
                            <input type="text" required="required" class="form-control" name="attributeId" value="">
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="form-control-label">Pricing Attribute Name:</label>
                            <input type="text" class="form-control" name="attributeName" value="">
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="form-control-label">Pricing Attribute Label:</label>
                            <input type="text" class="form-control" name="attributeLabel" value="">
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="form-control-label">Pricing Attribute Description:</label>
                            <textarea class="form-control" name="attributeDescription"></textarea>
                        </div>
                    </div>
                    <div class="panel-footer text-xs-right">
                        <button type="button" class="btn btn-default m-r-1" onclick="window.location.href='/admin/control/pePricingAttributes'">Cancel</button>
                        <button type="button" class="jqs-save-item btn btn-primary" data-dialog="true">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="panel" id="jqs-items-panel">
        <div class="panel-body">
            <div class="add-item text-xs-right" style="margin-bottom: 15px">
                <a class="panel-action" data-toggle="modal" data-target="#jqs-add-item"  title="Add Pricing Attribute"><button type="button" class="btn btn-success"><i class="icon wb-plus" aria-hidden="true"></i> Add Pricing Attribute</button></a>
            </div>
            <div class="table-responsive">
                <table class="table table-hover dataTable table-striped" id="items-grid">
                    <thead>
                    <tr>
                        <th style="width: 100px;">ATTRIBUTE ID</th>
                        <th style="width: 300px;">NAME</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr class="replace-inputs">
                        <th>ATTRIBUTE ID</th>
                        <th>NAME</th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <#assign count = 0>
                    <#list items as attribute>
                    <tr role="row" id="${attribute.attributeId}"class="<#if (count % 2) == 0>odd<#else>even</#if>">
                        <td><a href="<@ofbizUrl>/pePricingAttributes</@ofbizUrl>?attributeId=${attribute.attributeId}">${attribute.attributeId}</a></td>
                        <td>${attribute.attributeName!}</td>
                    </tr>
                        <#assign count = count+1 />
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<#else>
<div class="panel panel-primary panel-line jqs-pricing-attribute-details">
    <div class="panel-heading">
        <h3 class="panel-title">${attributeId}</h3>
    </div>
    <form name="saveForm" action="<@ofbizUrl>/peSaveAttribute</@ofbizUrl>" onsubmit="return false">
        <input type="hidden" name="attributeId" value="${attributeId}" />
        <div class="panel-body">
            <div class="form-group">
                <label class="form-control-label">Pricing Attribute Name:</label>
                <input type="text" class="form-control" name="attributeName" value="${attribute.attributeName!}">
            </div>
        </div>
        <div class="panel-body">
            <div class="form-group">
                <label class="form-control-label">Pricing Attribute Label:</label>
                <input type="text" class="form-control" name="attributeLabel" value="${attribute.attributeName!}">
            </div>
        </div>
        <div class="panel-body">
            <div class="form-group">
                <label class="form-control-label">Pricing Attribute Description:</label>
                <textarea class="form-control" name="attributeDescription">${attribute.attributeDescription!}</textarea>
            </div>
        </div>
        <div class="panel-footer text-xs-right">
            <button type="button" class="btn btn-default m-r-1" onclick="window.location.href='/admin/control/pePricingAttributes'">Cancel</button>
            <button type="button" class="jqs-save-item btn btn-primary">Save</button>
        </div>
    </form>
</div>
</#if>
<script src="/html/js/admin/pricing-engine/peBase.js"></script>
<script src="/html/js/admin/pricing-engine/pePricingAttributes.js"></script>