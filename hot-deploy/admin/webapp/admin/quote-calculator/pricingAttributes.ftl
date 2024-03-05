<div class="row">
    <div class="col-md-12">
        <div class="panel" id="jqs-items-panel">
            <div class="panel-body">
                <div class="panel-heading">
                    <h4 id="item-count" class="panel-title">${itemsCount}</h4>
                    <div class="panel-actions">
                        <a class="panel-action icon fa-plus" data-toggle="modal" data-target="#jqs-add-item"  title="Add Attribute"></a>
                    </div>
                </div>


                <div class="table-responsive">
                    <table id="items-grid" class="table table-hover" data-role="content" data-plugin="selectable" data-row-selectable="true">
                        <thead class="bg-blue-grey-100">
                        <tr>
                            <th>Attribute ID</th>
                            <th>Name</th>
                            <th>Label</th>
                            <th>Type</th>
                            <th>Sequence #</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list items as attribute>
                        <tr id="${attribute.attributeId}">
                            <td>${attribute.attributeId}</td>
                            <td>${attribute.attributeName}</td>
                            <td>${attribute.attributeLabel}</td>
                            <td>${attribute.attributeType}</td>
                            <td>${attribute.sequenceNum}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="jqs-dialogs-body">
    <div class="jqs-add-dialog modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">Add a Pricing Attribute</h4>
        </div>
        <form name="addForm" action="<@ofbizUrl>/qcSavePricingAttribute</@ofbizUrl>" onsubmit="return false">
            <div class="modal-body row">
                <div class="col-xs-12">
                    <div class="form-group has-feedback">
                        <label class="control-label">Attribute ID:</label>
                        <input type="text" class="form-control" name="attributeId">
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="control-label">Name:</label>
                        <input type="text" class="form-control" name="name">
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="control-label">Active:</label>
                        <select class="form-control" name="active">
                            <option value="Y">Yes</option>
                            <option value="N">No</option>
                        </select>
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="control-label">Active From:</label>
                        <input type="text" class="form-control" name="activeFrom">
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="control-label">Active To:</label>
                        <input type="text" class="form-control" name="activeTo">
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
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
                    <div class="form-group has-feedback">
                        <label class="control-label">Style Group ID:</label>
                        <input type="text" readonly aria-readonly="true" class="form-control" name="styleGroupId">
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="control-label">Name:</label>
                        <input type="text" class="form-control" name="name">
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="control-label">Active:</label>
                        <select class="form-control" name="active">
                            <option value="Y">Yes</option>
                            <option value="N">No</option>
                        </select>
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="control-label">Active From:</label>
                        <input type="text" class="form-control" name="activeFrom">
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="control-label">Active To:</label>
                        <input type="text" class="form-control" name="activeTo">
                        <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
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
                    <span class="jqs-style-group-active"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Active From:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-group-active-from"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Active To:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-style-group-active-to"></span>
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
            <button type="button" class="jqs-edit btn btn-primary waves-effect"><i class="icon fa-edit" aria-hidden="true"></i> Edit</button>
        </div>
    </div>
</div>

<script src="/html/js/admin/quote-calculator/qcStyleGroups.js"></script>


