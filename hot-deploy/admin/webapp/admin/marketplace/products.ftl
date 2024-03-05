<div class="example-wrap">
    <div class="nav-tabs-horizontal nav-tabs-inverse" data-plugin="tabs">
        <ul class="nav nav-tabs" role="tablist">
            <li class="nav-item" role="presentation">
                <a class="nav-link active" data-toggle="tab" href="#importedProductsTab" aria-controls="importedProductsTab" role="tab" aria-expanded="true">
                    Imported
                </a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" data-toggle="tab" href="#publishedProductsTab" aria-controls="publishedProductsTab" role="tab" aria-expanded="false">
                    Published
                </a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" data-toggle="tab" href="#pendingProductsTab" aria-controls="pendingProductsTab" role="tab" aria-expanded="false">
                    Pending <#if newProducts.size() != 0><span class="tag tag-pill tag-danger up">${newProducts.size()}</span></#if>
                </a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" data-toggle="tab" href="#heldProductsTab" aria-controls="heldProductsTab" role="tab" aria-expanded="false">
                    Held <#if heldProducts.size() != 0><span class="tag tag-pill tag-danger up">${heldProducts.size()}</span></#if>
                </a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" data-toggle="tab" href="#vendorUploadsTab" aria-controls="vendorUploadsTab" role="tab" aria-expanded="false">
                    Vendor Uploads
                </a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" data-toggle="tab" href="#actionsTab" aria-controls="actionsTab" role="tab" aria-expanded="false">
                    Actions
                </a>
            </li>
        </ul>
        <div class="tab-content p-20">
            <div class="tab-pane active" id="importedProductsTab" role="tabpanel" aria-expanded="true">
                <div class="panel jqs-imported-products" id="importedProducts">
                    <div class="panel-body">
                        <div class="panel-heading">
                            <h4 class="panel-title">${importedProductsCount}</h4>
                            <div class="panel-actions">
                                <button type="button" class="jqs-publish btn btn-dark waves-effect"><i class="icon fa-upload" aria-hidden="true"></i> Publish</button>
                            </div>
                        </div>
                        <div class="example-wrap">
                            <div class="example">
                                <div class="table-responsive">
                                      <table class="table table-hover" data-role="content" data-plugin="selectable" data-row-selectable="true">
                                            <thead class="bg-blue-grey-100">
                                            <tr>
                                                <th>
                                                <span class="checkbox-custom checkbox-primary">
                                                  <input class="selectable-all" data-id="" type="checkbox">
                                                  <label></label>
                                                </span>
                                                </th>
                                                <th>Mirakl SKU</th>
                                                <th>Name</th>
                                                <th>Vendor SKU</th>
                                                <#--<th>Color Name</th>-->
                                                <th>Size</th>
                                                <th>Seller Id</th>
                                                <th>BigName SKU</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <#list importedProducts as product>
                                            <tr>
                                                <td>
                                                <span class="checkbox-custom checkbox-primary">
                                                  <input class="selectable-item" data-id="${product.marketplaceProductId}" type="checkbox">
                                                  <label></label>
                                                </span>
                                                </td>
                                                <td>${product.marketplaceProductId}</td>
                                                <td>${product.productName}</td>
                                                <td>${product.sellerProductId}</td>
                                                <#--<td>${product.colorName}</td>-->
                                                <td>${product.sizeCode}</td>
                                                <td>${product.marketplaceSellerId}</td>
                                                <td>${product.bignameProductId}</td>
                                            </tr>
                                            </#list>
                                            </tbody>
                                        </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane" id="publishedProductsTab" role="tabpanel" aria-expanded="false">
                <div class="panel" id="publishedProducts">
                    <div class="panel-body">
                        <div class="panel-heading">
                            <h4 class="panel-title">${publishedProductsCount}</h4>
                        <#--<div class="panel-actions">
                            <a class="panel-action icon wb-edit" data-toggle="tooltip" data-original-title="edit" data-container="body" title=""></a>
                            <a class="panel-action icon wb-reply" data-toggle="tooltip" data-original-title="send" data-container="body" title=""></a>
                            <a class="panel-action icon wb-trash" data-toggle="tooltip" data-original-title="move to trash" data-container="body" title=""></a>
                            <a class="panel-action icon wb-user" data-toggle="tooltip" data-original-title="uesrs" data-container="body" title=""></a>
                        </div>-->
                        </div>
                        <div class="example-wrap">
                            <div class="example">
                                <div class="table-responsive">
                                    <table class="table table-hover" data-role="content" data-plugin="selectable" data-row-selectable="true">
                                        <thead class="bg-blue-grey-100">
                                        <tr>
                                            <th>
                                            <span class="checkbox-custom checkbox-primary">
                                              <input class="selectable-all" type="checkbox">
                                              <label></label>
                                            </span>
                                            </th>
                                            <th>Mirakl SKU</th>
                                            <th>Name</th>
                                            <th>Vendor SKU</th>
                                            <#--<th>Color Name</th>-->
                                            <th>Size</th>
                                            <th>Seller Id</th>
                                            <th>BigName SKU</th>
                                            <th>Status</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <#list publishedProducts as product>
                                        <tr>
                                            <td>
                                            <span class="checkbox-custom checkbox-primary">
                                              <input class="selectable-item" type="checkbox">
                                              <label></label>
                                            </span>
                                            </td>
                                            <td>${product.marketplaceProductId}</td>
                                            <td>${product.productName}</td>
                                            <td>${product.sellerProductId}</td>
                                        <#--<td>${product.colorName}</td>-->
                                            <td>${product.get("size")}</td>
                                            <td>${product.marketplaceSellerId}</td>
                                            <td>${product.bignameProductId}</td>
                                            <td>ACTIVE</td>
                                        </tr>
                                        </#list>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane" id="pendingProductsTab" role="tabpanel" aria-expanded="false">
                <div class="panel jqs-pending-products" id="pendingProducts">
                    <div class="panel-body">
                        <div class="panel-heading">
                            <h4 class="panel-title">${newProductsCount}</h4>
                        <div class="panel-actions">
                            <button type="button" class="jqs-import btn btn-dark waves-effect"><i class="icon fa-upload" aria-hidden="true"></i> Import</button>
                            <button type="button" class="jqs-hold btn btn-dark waves-effect"><i class="icon fa-upload" aria-hidden="true"></i> Hold</button>
                            <#--<a class="panel-action icon wb-reply" data-toggle="tooltip" data-original-title="send" data-container="body" title=""></a>-->
                        </div>
                        </div>
                        <div class="example-wrap">
                            <div class="example">
                                <div class="table-responsive">
                                    <table class="table table-hover" data-role="content" data-plugin="selectable" data-row-selectable="true">
                                        <thead class="bg-blue-grey-100">
                                        <tr>
                                            <th>
                                                <span class="checkbox-custom checkbox-primary">
                                                  <input class="selectable-all" data-id="" type="checkbox">
                                                  <label></label>
                                                </span>
                                            </th>
                                            <th>Marketplace Product Id</th>
                                            <th>Name</th>
                                            <th>Vendor SKU</th>
                                            <th>Color Name</th>
                                            <th>Size</th>
                                            <th>Seller Id</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#list newProducts as product>
                                        <tr>
                                            <td>
                                                <span class="checkbox-custom checkbox-primary">
                                                  <input class="selectable-item" data-id="${product.marketplaceProductId}" type="checkbox">
                                                  <label></label>
                                                </span>
                                            </td>
                                            <td>${product.marketplaceProductId}</td>
                                            <td>${product.name}</td>
                                            <td>${product.sellerProductId}</td>
                                            <td>${product.colorName}</td>
                                            <td>${product.sizeCode}</td>
                                            <td>${product.marketplaceSellerId}</td>
                                        </tr>
                                        </#list>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane" id="heldProductsTab" role="tabpanel" aria-expanded="false">
                <div class="panel jqs-held-products" id="heldProducts">
                    <div class="panel-body">
                        <div class="panel-heading">
                            <h4 class="panel-title">${heldProductsCount}</h4>
                            <div class="panel-actions">
                                <button type="button" class="jqs-import-held btn btn-dark waves-effect"><i class="icon fa-upload" aria-hidden="true"></i> Import</button>
                            </div>
                        </div>
                        <div class="example-wrap">
                            <div class="example">
                                <div class="table-responsive">
                                    <table class="table table-hover" data-role="content" data-plugin="selectable" data-row-selectable="true">
                                        <thead class="bg-blue-grey-100">
                                        <tr>
                                            <th>
                                                <span class="checkbox-custom checkbox-primary">
                                                  <input class="selectable-all" data-id="" type="checkbox">
                                                  <label></label>
                                                </span>
                                            </th>
                                            <th>Marketplace Product Id</th>
                                            <th>Name</th>
                                            <th>Vendor SKU</th>
                                            <th>Color Name</th>
                                            <th>Size</th>
                                            <th>Seller Id</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#list heldProducts as product>
                                        <tr>
                                            <td>
                                                <span class="checkbox-custom checkbox-primary">
                                                  <input class="selectable-item" data-id="${product.marketplaceProductId}" type="checkbox">
                                                  <label></label>
                                                </span>
                                            </td>
                                            <td>${product.marketplaceProductId}</td>
                                            <td>${product.name}</td>
                                            <td>${product.sellerProductId}</td>
                                            <td>${product.colorName}</td>
                                            <td>${product.sizeCode}</td>
                                            <td>${product.marketplaceSellerId}</td>
                                        </tr>
                                        </#list>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane" id="vendorUploadsTab" role="tabpanel" aria-expanded="false">
                <div class="panel" id="vendorUploads">
                    <div class="panel-body">
                        <div class="panel-heading">
                            <h4 class="panel-title">${uploadedFilesCount}</h4>
                        <#--<div class="panel-actions">
                            <a class="panel-action icon wb-edit" data-toggle="tooltip" data-original-title="edit" data-container="body" title=""></a>
                            <a class="panel-action icon wb-reply" data-toggle="tooltip" data-original-title="send" data-container="body" title=""></a>
                            <a class="panel-action icon wb-trash" data-toggle="tooltip" data-original-title="move to trash" data-container="body" title=""></a>
                            <a class="panel-action icon wb-user" data-toggle="tooltip" data-original-title="uesrs" data-container="body" title=""></a>
                        </div>-->
                        </div>
                        <div class="example-wrap">
                            <div class="example">
                                <div class="table-responsive">
                                    <table class="table table-hover" data-role="content" data-plugin="selectable" data-row-selectable="true">
                                        <thead class="bg-blue-grey-100">
                                        <tr>
                                            <th>File Name</th>
                                            <th>Upload Id</th>
                                            <th>Store Id</th>
                                            <th># of Products</th>
                                            <th>Date Uploaded</th>
                                            <th>Status</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#list uploads as upload>
                                        <tr>
                                            <td>${upload.fileName}</td>
                                            <td>${upload.uploadId}</td>
                                            <td>${upload.sellerId}</td>
                                            <td>${upload.numOfItems?if_exists}</td>
                                            <td>${upload.uploadTime}</td>
                                            <td>${upload.status}</td>
                                        </tr>
                                        </#list>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane" id="actionsTab" role="tabpanel" aria-expanded="false">
                <div class="panel" id="operatorActions">
                    <div class="panel-body">
                        <div class="panel-heading">
                            <#--<h4 class="panel-title">2 Files</h4>-->
                        <#--<div class="panel-actions">
                            <a class="panel-action icon wb-edit" data-toggle="tooltip" data-original-title="edit" data-container="body" title=""></a>
                            <a class="panel-action icon wb-reply" data-toggle="tooltip" data-original-title="send" data-container="body" title=""></a>
                            <a class="panel-action icon wb-trash" data-toggle="tooltip" data-original-title="move to trash" data-container="body" title=""></a>
                            <a class="panel-action icon wb-user" data-toggle="tooltip" data-original-title="uesrs" data-container="body" title=""></a>
                        </div>-->
                        </div>
                        <div class="example-wrap">
                            <div class="example">
                                <div class="table-responsive">
                                    <table class="table table-hover" data-role="content" data-plugin="selectable" data-row-selectable="true">
                                        <thead class="bg-blue-grey-100">
                                        <tr>
                                            <th></th>
                                            <th>Level</th>
                                            <th>Date</th>
                                            <th>Message</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <th><span class="icon fa-info-circle"></span> </th>
                                            <td>Info</td>
                                            <td>02.02.2017 11:25:16 AM</td>
                                            <td>Received product file from BigName Inc</td>
                                        </tr>
                                        <tr>
                                            <th><span class="icon fa-warning"></span> </th>
                                            <td>Warning</td>
                                            <td>02.02.2017 11:25:16 AM</td>
                                            <td>Received product file from BigName Inc</td>
                                        </tr>
                                        <tr>
                                            <th><span class="icon fa-close"></span> </th>
                                            <td>Error</td>
                                            <td>02.02.2017 11:25:16 AM</td>
                                            <td>Received product file from BigName Inc</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/html/js/admin/marketplace/products.js"></script>


