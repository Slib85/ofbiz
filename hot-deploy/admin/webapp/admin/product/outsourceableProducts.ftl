<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary panel-line" data-collapsed="0">
            <!-- panel head -->
            <div class="panel-heading">
                <div class="panel-title">Product List</div>
            </div>

            <!-- panel body -->
            <div class="panel-body with-table">
                <table class="table table-bordered responsive table-striped datatable" id="productTable">
                    <thead>
                    <tr>
                        <th>&nbsp;</th>
                        <th>Product ID</th>
                        <th>Name</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tfoot>
                        <tr class="replace-inputs">
                            <th>&nbsp;</th>
                            <th>Product ID</th>
                            <th>Name</th>
                            <th></th>
                        </tr>
                    </tfoot>

                    <tbody>
						<#if products?has_content>
							<#list products as product>
							<#assign productData = Static["com.envelopes.product.ProductHelper"].getProduct(delegator, product.productId)?if_exists />
                            <tr>
                                <td>
                                    <a href="<@ofbizUrl>/productOutsourceRules</@ofbizUrl>?productId=${product.productId?if_exists}">
                                        <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.productId}?hei=40&amp;fmt=png-alpha" />
                                    </a>
                                </td>
                                <td>${product.productId?if_exists}</td>
                                <td>${productData.internalName?if_exists}</td>
                                <td>
                                    <a href="<@ofbizUrl>/productOutsourceRules</@ofbizUrl>?productId=${product.productId?if_exists}" class="btn btn-default btn-sm btn-icon icon-left">
                                        <i class="entypo-pencil"></i>
                                        Edit
                                    </a>
                                </td>
                            </tr>
							</#list>
						</#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="<@ofbizContentUrl>/html/js/admin/outsourceableProducts.js</@ofbizContentUrl>"></script>