
<div class="panel" id="jqs-items-panel">
    <div class="panel-body">
        <div class="add-item text-xs-right" style="margin-bottom: 15px">
            <a class="panel-action" data-toggle="modal" data-target="#jqs-add-item"  title="Add Stock"><button type="button" class="btn btn-success"><i class="icon wb-plus" aria-hidden="true"></i> Add Stock</button></a>
        </div>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="items-grid">
                <thead>
                <tr>
                    <th style="width: 100px;">STOCK ID</th>
                    <th style="width: 300px;">NAME</th>
                    <th style="width: 100px;">STOCK TYPE</th>
                    <th style="width: 100px; text-align: center">ACTIVE FLAG</th>
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>STOCK ID</th>
                    <th>NAME</th>
                    <th>STOCK TYPE</th>
                    <th style="text-align: center">ACTIVE FLAG</th>
                </tr>
                </tfoot>
                <tbody style="display: none">
                <#assign count = 0>
                <#list items as stock>
                <tr role="row" id="${stock.stockId}"class="<#if (count % 2) == 0>odd<#else>even</#if>">
                    <td><a href="javascript:void(0)">${stock.stockId}</a></td>
                    <td>${stock.stockName}</td>
                    <td>${stock.stockTypeId}</td>
                    <td style="text-align: center"><#if stock.activeFlag?has_content>${stock.activeFlag}<#else>N</#if></td>
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
            <h4 class="modal-title" id="myModalLabel">Add a Stock</h4>
        </div>
        <form name="addForm" action="<@ofbizUrl>/qcSaveStock</@ofbizUrl>" onsubmit="return false">
            <div class="modal-body row">
                <div class="col-xs-12">
                    <div class="form-group">
                        <label class="form-control-label">Stock ID:</label>
                        <input type="text" class="form-control" name="stockId">
                        <small class="text-error" data-default-message="Stock ID is required and cannot be empty"></small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Name:</label>
                        <input type="text" class="form-control" name="name">
                        <small class="text-error" data-default-message="Stock Name is required and cannot be empty"></small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Stock Type:</label>
                        <select class="form-control" name="stockTypeId">
                            <option value="">Select Stock Type</option>
                        <#list stockTypes as type>
                            <option value="${type.stockTypeId}">${type.stockTypeName}</option>
                        </#list>
                        </select>
                        <small class="text-error" data-default-message="Stock Type is required and cannot be empty"></small>
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
                <button type="button" class="jqs-save-item jqs-add btn btn-primary">Add Stock</button>
            </div>
        </form>
    </div>

    <div class="jqs-edit-dialog modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">Edit Stock</h4>
        </div>
        <form name="editForm" action="<@ofbizUrl>/qcSaveStock</@ofbizUrl>" onsubmit="return false">
            <div class="modal-body row">
                <div class="col-xs-12">
                    <div class="form-group">
                        <label class="form-control-label">Stock ID:</label>
                        <input type="text" readonly aria-readonly="true" class="form-control" name="stockId">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Name:</label>
                        <input type="text" class="form-control" name="name">
                        <small class="text-error" data-default-message="Stock Name is required and cannot be empty"></small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">Stock Type:</label>
                        <select class="form-control" name="stockTypeId">
                            <option value="">Select Stock Type</option>
                        <#list stockTypes as type>
                            <option value="${type.stockTypeId}">${type.stockTypeName}</option>
                        </#list>
                        </select>
                        <small class="text-error" data-default-message="Stock Type is required and cannot be empty"></small>
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
                <button type="button" class="jqs-save-item jqs-edit btn btn-primary">Edit Stock</button>
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
                    <h5 class="inline-label">Stock ID:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-stock-id"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Name:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-stock-name"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Stock Type:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-stock-type"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Description:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-stock-description"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Active:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-stock-active-flag"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Created:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-stock-created"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6" style="text-align: right">
                    <h5 class="inline-label">Last Updated:</h5>
                </div>
                <div class="col-md-8 col-sm-6">
                    <span class="jqs-stock-updated"></span>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="jqs-edit btn btn-primary waves-effect"><i class="icon fa-edit" aria-hidden="true"></i> Edit</button>
        </div>
    </div>
</div>

<script src="/html/js/admin/quote-calculator/qcStocks.js"></script>


