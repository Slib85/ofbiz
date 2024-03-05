
<div class="panel" id="jqs-items-panel">
    <div class="panel-body">
        <div class="add-item text-xs-right" style="margin-bottom: 15px">
            <a class="panel-action" data-toggle="modal" data-target="#jqs-add-item"  title="Add Style"><button type="button" class="btn btn-success"><i class="icon wb-plus" aria-hidden="true"></i> Add Style</button></a>
        </div>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="items-grid">
                <thead>
                <tr>
                    <th style="width: 100px;">STYLE ID</th>
                    <th style="width: 300px;">NAME</th>
                    <th style="width: 100px;">STYLE GROUP</th>
                    <th style="width: 100px; text-align: center">ACTIVE FLAG</th>
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>STYLE ID</th>
                    <th>NAME</th>
                    <th>STYLE GROUP</th>
                    <th style="text-align: center">ACTIVE FLAG</th>
                </tr>
                </tfoot>
                <tbody style="display: none">
                <#assign count = 0>
                <#list items as style>
                    <tr role="row" id="${style.styleId}"class="<#if (count % 2) == 0>odd<#else>even</#if>">
                        <td><a href="javascript:void(0)">${style.styleId}</a></td>
                        <td>${style.styleName}</td>
                        <td>${style.styleGroupId}</td>
                        <td style="text-align: center"><#if style.activeFlag?has_content>${style.activeFlag}<#else>N</#if></td>
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
            <h4 class="modal-title" id="myModalLabel">Add a Style</h4>
        </div>
        <form name="addForm" action="<@ofbizUrl>/qcSaveStyle</@ofbizUrl>" onsubmit="return false">
            <div class="modal-body row">
                <div class="col-xs-12">
                    <div class="form-group">
                        <label class="form-control-label">Style ID:</label>
                        <input type="text" class="form-control" name="styleId">
                        <small class="text-error" data-default-message="Style ID is required and cannot be empty"></small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Name:</label>
                        <input type="text" class="form-control" name="name">
                        <small class="text-error" data-default-message="Style Name is required and cannot be empty"></small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Short Name:</label>
                        <input type="text" class="form-control" name="shortName">
                        <small class="text-error"></small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Style Group:</label>
                        <select class="form-control" name="styleGroupId" data-validation="required">
                            <option value="">Select Style Group</option>
                        <#list styleGroups as group>
                            <option value="${group.styleGroupId}">${group.styleGroupName}</option>
                        </#list>
                        </select>
                        <small class="text-error" data-default-message="Style Group is required and cannot be empty"></small>
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
                <button type="button" class="jqs-save-item jqs-add btn btn-primary">Add Style</button>
            </div>
        </form>
    </div>

    <div class="jqs-edit-dialog modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">Edit Style</h4>
        </div>
        <form name="editForm" action="<@ofbizUrl>/qcSaveStyle</@ofbizUrl>" onsubmit="return false">
            <div class="modal-body row">
                <div class="col-xs-12">
                    <div class="form-group">
                        <label class="form-control-label">Style ID:</label>
                        <input type="text" readonly aria-readonly="true" class="form-control" name="styleId">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Name:</label>
                        <input type="text" class="form-control" name="name">
                        <small class="text-error" data-default-message="Style Name is required and cannot be empty"></small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Short Name:</label>
                        <input type="text" class="form-control" name="shortName">
                        <small class="text-error"></small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Style Group:</label>
                        <select class="form-control" name="styleGroupId">
                            <option value="">Select Style Group</option>
                        <#list styleGroups as group>
                            <option value="${group.styleGroupId}">${group.styleGroupName}</option>
                        </#list>
                        </select>
                        <small class="text-error" data-default-message="Style Group is required and cannot be empty"></small>
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
                <button type="button" class="jqs-save-item jqs-edit btn btn-primary">Edit Style</button>
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
                    <h5 class="inline-label">Style ID:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-id"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Name:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-name"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Short Name:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-short-name"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Style Group:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-group"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Description:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-description"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Active:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-active-flag"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Created:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-created"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Last Updated:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-updated"></span>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="jqs-edit btn btn-primary waves-effect"><i class="icon fa-edit" aria-hidden="true"></i> Edit</button>
        </div>
    </div>
</div>

<script src="/html/js/admin/quote-calculator/qcStyles.js"></script>


