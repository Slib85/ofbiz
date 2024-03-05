<div class="row">
    <div class="col-md-12">
        <div class="panel" id="importedProducts">
            <div class="panel-body">
                <div class="panel-heading">
                    <h4 id="marketplace-seller-count" class="panel-title">${sellersCount}</h4>
                    <div class="panel-actions">
                        <a class="jqs-sync panel-action icon fa-exchange" data-toggle="tooltip" data-original-title="Sync" data-container="body" title="Sync"></a>
                    </div>
                </div>
                <div class="table-responsive">
                    <table id="marketplace-sellers" class="table table-hover" data-role="content" data-plugin="selectable" data-row-selectable="true">
                        <thead class="bg-blue-grey-100">
                        <tr>
                            <th>Seller Id</th>
                            <th>Name</th>
                            <th>State</th>
                            <th>Status</th>
                            <th>Last Updated</th>
                            <th>Created</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list sellers as seller>
                        <tr>
                            <td>${seller.marketplaceSellerId}</td>
                            <td>${seller.sellerName}</td>
                            <td>OPEN</td>
                            <td>ACTIVE</td>
                            <td>${seller.miraklLastUpdatedDate}</td>
                            <td>${seller.miraklCreatedDate}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/html/js/admin/marketplace/sellers.js"></script>
