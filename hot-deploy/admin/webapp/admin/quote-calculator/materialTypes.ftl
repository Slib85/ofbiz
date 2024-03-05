
<div class="panel" id="jqs-items-panel">
    <div class="panel-body">
        <div class="add-item text-xs-right" style="margin-bottom: 15px">
            <a class="panel-action" data-toggle="modal" data-target="#jqs-add-item"  title="Add Material Type"><button type="button" class="btn btn-success"><i class="icon wb-plus" aria-hidden="true"></i> Add Material Type</button></a>
        </div>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="items-grid">
                <thead>
                <tr>
                    <th style="width: 100px;">MATERIAL TYPE ID</th>
                    <th style="width: 300px;">NAME</th>
                    <th style="width: 100px; text-align: center">ACTIVE FLAG</th>
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>MATERIAL TYPE ID</th>
                    <th>NAME</th>
                    <th style="text-align: center">ACTIVE FLAG</th>
                </tr>
                </tfoot>
                <tbody style="display: none">
                <#assign count = 0>
                <#list items as materialType>
                <tr role="row" id="${materialType.materialTypeId}"class="<#if (count % 2) == 0>odd<#else>even</#if>">
                    <td><a href="javascript:void(0)">${materialType.materialTypeId}</a></td>
                    <td>${materialType.materialTypeName}</td>
                    <td style="text-align: center"><#if materialType.activeFlag?has_content>${materialType.activeFlag}<#else>N</#if></td>
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
            <h4 class="modal-title" id="myModalLabel">Add a Material Type</h4>
        </div>
        <form name="addForm" action="<@ofbizUrl>/qcSaveMaterialType</@ofbizUrl>" onsubmit="return false">
            <div class="modal-body row">
                <div class="col-xs-12">
                    <div class="form-group">
                        <label class="form-control-label">Material Type ID:</label>
                        <input type="text" class="form-control" name="materialTypeId">
                        <small class="text-error" data-default-message="Material Type ID is required and cannot be empty">Material Type ID is required and cannot be empty</small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Name:</label>
                        <input type="text" class="form-control" name="name">
                        <small class="text-error" data-default-message="Material Type Name is required and cannot be empty">Material Type Name is required and cannot be empty</small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Description:</label>
                        <textarea type="text" class="form-control" name="description"></textarea>
                        <small class="text-error"></small>
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
                <button type="button" class="jqs-save-item jqs-add btn btn-primary">Add Material Type</button>
            </div>
        </form>
    </div>

    <div class="jqs-edit-dialog modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">Edit Material Type</h4>
        </div>
        <form name="editForm" action="<@ofbizUrl>/qcSaveMaterialType</@ofbizUrl>" onsubmit="return false">
            <div class="modal-body row">
                <div class="col-xs-12">
                    <div class="form-group has-feedback">
                        <label class="form-control-label">Material Type ID:</label>
                        <input type="text" readonly aria-readonly="true" class="form-control" name="materialTypeId">
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Name:</label>
                        <input type="text" class="form-control" name="name">
                        <small class="text-error" data-default-message="Material Type Name is required and cannot be empty">Material Type Name is required and cannot be empty</small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Description:</label>
                        <textarea type="text" class="form-control" name="description"></textarea>
                        <small class="text-error"></small>
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
                <button type="button" class="jqs-save-item jqs-edit btn btn-primary">Edit Material Type</button>
            </div>
        </form>
    </div>

    <div class="jqs-view-dialog modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel"></h4>
        </div>
        <div>

        </div>
        <div class="modal-body">
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Material Type ID:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-material-type-id"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Name:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-material-type-name"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Description:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-material-type-description"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Active:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-material-type-active-flag"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Created:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-material-type-created"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Last Updated:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-material-type-updated"></span>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="jqs-edit btn btn-primary waves-effect"><i class="icon fa-edit" aria-hidden="true"></i> Edit</button>
        </div>
    </div>
</div>

<script src="/html/js/admin/quote-calculator/qcMaterialTypes.js"></script>


