<#if pageType?has_content>
    <input type="hidden" name="styleGroupId" value="${styleGroupId?if_exists}"/>
    <div class="page-header-override" style="display: none;">
        <h1 class="page-title">${styleGroup.styleGroupName} - Pricing Attributes</h1>
        <div class="page-header-actions">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/admin/control/main">Home</a></li>
                <li class="breadcrumb-item"><a href="/admin/control/qcStyleGroups">Style Groups</a></li>
                <li class="breadcrumb-item active">${styleGroup.styleGroupName}</li>
            </ol>
        </div>
    </div>
    <script>
        $('.page-header').html($('.page-header-override').html());
    </script>

    <div class="modal fade" id="jqs-add-pricing-attribute-popup" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" class="panel-title">Pricing Attributes</h4>
                    <br/>
                </div>
                <div class="modal-body row">
                    <div class="col-xs-5">
                        <div class="form-group">
                            <label class="control-label">Available Attributes:</label>
                            <select name="available" class="form-control" style="height: 200px;" size="8">

                            </select>
                        </div>
                    </div>

                    <div class="col-xs-1" style="padding-top: 40px;padding-right: 54px;">
                        <button id="addPricingAttributes" style="margin-bottom: 10px;visibility: hidden" class="btn btn-icon btn-success"><i class="icon fa-forward"></i></button>
                        <button id="addPricingAttribute" style="margin-bottom: 10px" class="btn btn-icon btn-success"><i class="icon fa-plus"></i></button>
                        <button id="removePricingAttribute" style="margin-bottom: 10px" class="btn btn-icon btn-warning"><i class="icon fa-minus"></i></button>
                        <button id="removePricingAttributes" style="margin-bottom: 10px;visibility: hidden" class="btn btn-icon btn-warning"><i class="icon fa-backward"></i></button>
                    </div>

                    <div class="col-xs-5">
                        <div class="form-group">
                            <label class="control-label">Assigned Attributes:</label>
                            <select name="assigned" class="form-control" style="height: 200px;" size="8">

                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="jqs-done-item btn btn-primary" onclick="$('#jqs-add-pricing-attribute-popup').modal('hide');">Done</button>
                </div>
            </div>
        </div>
    </div>


    <div class="panel" id="jqs-items-panel">
        <div class="panel-body">
            <div class="add-item text-xs-right" style="margin-bottom: 15px">
                <a id="add-pricing-attribute-btn" class="panel-action" data-toggle="modal" title="Add / Remove Attribute"><button type="button" class="btn btn-success">Add / Remove Attribute</button></a>
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
                    <tbody style="display: none">
                        <#assign count = 0>
                        <#list pricingAttributes as pricingAttribute>
                        <tr role="row" id="${pricingAttribute.attributeId}"class="<#if (count % 2) == 0>odd<#else>even</#if>">
                            <td>${pricingAttribute.attributeId}</td>
                            <td>${pricingAttribute.attributeLabel}</td>
                        </tr>
                        </#list>
                        <#assign count = count+1 />
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<#else>
<div class="panel" id="jqs-items-panel">
    <div class="panel-body">
        <div class="add-item text-xs-right" style="margin-bottom: 15px">
            <a class="panel-action" data-toggle="modal" data-target="#jqs-add-item"  title="Add Style Group"><button type="button" class="btn btn-success"><i class="icon wb-plus" aria-hidden="true"></i> Add Style Group</button></a>
        </div>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="items-grid">
                <thead>
                <tr>
                    <th style="width: 100px;">GROUP ID</th>
                    <th style="width: 300px;">NAME</th>
                    <th style="width: 100px; text-align: center">ACTIVE FLAG</th>
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>GROUP ID</th>
                    <th>NAME</th>
                    <th style="text-align: center">ACTIVE FLAG</th>
                </tr>
                </tfoot>
                <tbody style="display: none">
                    <#assign count = 0>
                    <#list items as group>
                    <tr role="row" id="${group.styleGroupId}"class="<#if (count % 2) == 0>odd<#else>even</#if>">
                        <td><a href="javascript:void(0)">${group.styleGroupId}</a></td>
                        <td>${group.styleGroupName}</td>
                        <td style="text-align: center"><#if group.activeFlag?has_content>${group.activeFlag}<#else>N</#if></td>
                    </tr>
                    </#list>
                    <#assign count = count+1 />
                </tbody>
            </table>
        </div>
    </div>
</div>
    <div class="jqs-dialogs-body">
        <div class="jqs-add-dialog modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add a Style Group</h4>
            </div>
            <form name="addForm" action="<@ofbizUrl>/qcSaveStyleGroup</@ofbizUrl>" onsubmit="return false">
                <div class="modal-body row">
                    <div class="col-xs-12">
                        <div class="form-group">
                            <label class="form-control-label">Style Group ID:</label>
                            <input type="text" class="form-control" name="styleGroupId">
                            <small class="text-error" data-default-message="Style Group ID is required and cannot be empty">Style Group ID is required and cannot be empty</small>
                        </div>
                        <div class="form-group">
                            <label class="form-control-label">Name:</label>
                            <input type="text" class="form-control" name="name">
                            <small class="text-error" data-default-message="Style Group Name is required and cannot be empty">Style Group Name is required and cannot be empty</small>
                        </div>
                        <div class="form-group">
                            <label class="form-control-label">Active:</label>
                            <select class="form-control" name="activeFlag">
                                <option value="Y" selected>Yes</option>
                                <option value="N">No</option>
                            </select>
                            <small class="text-error"></small>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="jqs-save-item jqs-add btn btn-primary">Add Style Group</button>
                </div>
            </form>
        </div>

        <div class="jqs-edit-dialog modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Edit Style Group</h4>
            </div>
            <form name="editForm" action="<@ofbizUrl>/qcSaveStyleGroup</@ofbizUrl>" onsubmit="return false">
                <div class="modal-body row">
                    <div class="col-xs-12">
                        <div class="form-group">
                            <label class="form-control-label">Style Group ID:</label>
                            <input type="text" readonly aria-readonly="true" class="form-control" name="styleGroupId">
                        </div>
                        <div class="form-group">
                            <label class="form-control-label">Name:</label>
                            <input type="text" class="form-control" name="name">
                            <small class="text-error" data-default-message="Style Group Name is required and cannot be empty">Style Group Name is required and cannot be empty</small>
                        </div>
                        <div class="form-group">
                            <label class="form-control-label">Active:</label>
                            <select class="form-control" name="activeFlag">
                                <option value="Y" selected>Yes</option>
                                <option value="N">No</option>
                            </select>
                            <small class="text-error"></small>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="jqs-save-item jqs-edit btn btn-primary">Edit Style Group</button>
                </div>
            </form>
        </div>

        <div class="jqs-view-dialog modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="jqs-style-group-name modal-title" id="myModalLabel"></h4>
            </div>
            <div>

            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Style Group ID:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-style-group-id"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Name:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-style-group-name"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Active:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-style-group-active-flag"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Created:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-style-group-created"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Last Updated:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-style-group-updated"></span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <#--<div style="float: left"><button type="button" class="jqs-pricing-attributes btn btn-success waves-effect"><i class="icon fa-dollar" aria-hidden="true"></i> Pricing Attributes</button></div>-->
                <button type="button" class="jqs-edit btn btn-primary waves-effect"><i class="icon fa-edit" aria-hidden="true"></i> Edit</button>
            </div>
        </div>
    </div>
</#if>
<script src="/html/js/admin/quote-calculator/qcStyleGroups.js"></script>

