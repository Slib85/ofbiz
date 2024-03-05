<style>
    .table a {
        text-decoration: none;
    }
    .table {
        font-size: 12px;
    }
    .table td {
        white-space: nowrap;
    }
</style>

<#if product?exists>
<form name="updateVendorProduct" method="POST" action="<@ofbizUrl>/updateVendorProduct</@ofbizUrl>" role="form" class="clearfix form-horizontal form-groups-bordered">
	<input id="productId" type="hidden" name="id" value="${product.productId?if_exists}" />
	<input id="vendorProductId" type="hidden" name="vendorProductId" value="${productVendor.vendorProductId?if_exists}" />
	<input id="partyId" type="hidden" name="partyId" value="${productVendor.vendorPartyId?if_exists}" />
	<div class="panel panel-primary panel-line" data-collapsed="0">
		<!-- panel head -->
		<div class="panel-heading">
			<div class="panel-title">${product.internalName}</div>
		</div>

		<!-- panel body -->
		<div class="panel-body">
			<div class="row">
				<div class="col-md-2 center">
					<img class="img-responsive center-block" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.productId?if_exists}?wid=100&amp;hei=100&amp;fmt=png-alpha" alt="${product.internalName}" />
				</div>
				<div class="col-md-7">
					<div class="row">
						<div class="col-md-6">
							<div>Product Name: ${product.productName?if_exists}</div>
							<div>Width: ${product.productWidth?if_exists}</div>
							<div>Height: ${product.productHeight?if_exists}</div>
							<div>Weight: <input type="text" class="form-control" name="weight" value="${product.productWeight?if_exists}" /></div>
							<div>Box Qty: <input type="text" class="form-control" name="boxQty" value="${product.boxQty?default("Not Available")}" /></div>
							<div>Carton Qty: <input type="text" class="form-control" name="cartonQty" value="${product.cartonQty?default("Not Available")}" /></div>
							<div>Vendor Product ID: <input type="text" class="form-control" name="newVendorProductId" value="${productVendor.vendorProductId?if_exists}" /></div>
                            <div class="checkbox-custom checkbox-primary">
                                <input type="checkbox" id="stockSupplied" name="stockSupplied" value="Y" <#if productVendor.stockSupplied?has_content && productVendor.stockSupplied == "Y">checked</#if> />
                                <label for="stockSupplied">Stock Supplied</label>
                            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="panel panel-success panel-line">
		<!-- panel head -->
		<div class="panel-heading">
			<div class="panel-title">Product Prices</div>
		</div>

        <div class="panel-body">
			<!-- panel body -->
			<table class="table table-bordered responsive table-striped dataTable" id="productTable2">
				<thead>
				<tr>
					<th>Quantity</th>
					<th>Ink Colors Front</th>
					<th>Front Black</th>
					<th>Ink Colors Back</th>
					<th>Back Black</th>
					<th>Price</th>
					<th>Discontinue</th>
					<th>Delete</th>
				</tr>
				</thead>
				<tfoot>
				<tr class="replace-inputs">
					<th>Quantity</th>
					<th>Ink Colors Front</th>
					<th>Front Black</th>
					<th>Ink Colors Back</th>
					<th>Back Black</th>
					<th>Price</th>
					<th>Discontinue</th>
					<th>Delete</th>
				</tr>
				</tfoot>
				<tbody class="priceList">
					<#list prices as price>
					<tr>
						<td>${price.quantity?if_exists}</td>
						<td>${price.colorsFront?if_exists}</td>
						<td>${price.isFrontBlack?if_exists}</td>
						<td>${price.colorsBack?if_exists}</td>
						<td>${price.isBackBlack?if_exists}</td>
						<td>
							<div class="input-group">
								<span class="input-group-addon">$</span>
								<input type="text" name="pp_${price.quantity?if_exists}_${price.colorsFront?if_exists}_${price.colorsBack?if_exists}_${price.isFrontBlack?if_exists}_${price.isBackBlack?if_exists}" class="form-control" placeholder="Price" value="${price.price?if_exists}">
							</div>
						</td>
						<td><input type="checkbox" name="ppd_${price.quantity?if_exists}_${price.colorsFront?if_exists}_${price.colorsBack?if_exists}_${price.isFrontBlack?if_exists}_${price.isBackBlack?if_exists}" value="Y" <#if price.thruDate?has_content>checked</#if> /></td>
						<td><input type="checkbox" name="ppdel_${price.quantity?if_exists}_${price.colorsFront?if_exists}_${price.colorsBack?if_exists}_${price.isFrontBlack?if_exists}_${price.isBackBlack?if_exists}" value="Y" /></td>
					</tr>
					</#list>
				</tbody>
			</table>
			<div class="row">
				<div class="col-md-12">
					<button type="button" class="btn btn-orange btn-icon btn-sm icon-right addPrice" data-toggle="modal" data-target="#addQuantity">
						ADD NEW PRICE
						<i class="entypo-check"></i>
					</button>
					<div class="modal fade" id="addQuantity" role="dialog">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">Add new quantity</h4>
								</div>
								<div class="modal-body row">
									<div class="col-xs-12">
										<div class="form-group has-feedback">
											<div class="envRow">
												<div class="envCol col50">
													Colors Front
													<select id="colorsFront" class="form-control" name="colorsFront">
														<option value="0">0 Color</option>
														<option value="1">1 Color</option>
														<option value="2">2 Colors</option>
														<option value="4">4 Colors</option>
													</select>
												</div>
												<div class="envCol col50">
													Colors Back
													<select id="colorsBack" class="form-control" name="colorsBack">
														<option value="0">0 Color</option>
														<option value="1">1 Color</option>
														<option value="2">2 Colors</option>
														<option value="4">4 Colors</option>
													</select>
												</div>
											</div>
											<div class="envRow margin-top-xxs">
												<div class="envCol col50">
													<input type="checkbox" id="isFrontBlack" name="isFrontBlack" value="Y" /> Is Front Black
												</div>
												<div class="envCol col50">
													<input type="checkbox" id="isBackBlack" name="isBackBlack" value="Y" /> Is Back Black
												</div>
											</div>
											<div class="envRow margin-top-xxs">
												<div class="envCol col50">
													Quantity
													<input type="text" class="form-control" id="quantity" placeholder="Quantity" value="" />
												</div>
												<div class="envCol col50">
													Price
													<input type="text" class="form-control" id="price" placeholder="Price" value="" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
									<button type="button" class="btn btn-primary j-btn-add">Add</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row text-right">
		<div class="col-md-12 center">
			<button type="submit" class="btn btn-green btn-icon btn-lg icon-right">
				SAVE
				<i class="entypo-check"></i>
			</button>
		</div>
	</div>
</form>
<#else>
<br />
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary panel-line" data-collapsed="0">
			<!-- panel head -->
			<div class="panel-heading">
				<div class="panel-title">Product List</div>
			</div>

			<!-- panel body -->
			<div class="panel-body">
				<table class="table table-hover" id="productTable">
                    <thead>
						<tr>
							<th>&nbsp;</th>
							<th>Product ID</th>
							<th>Vendor Product ID</th>
							<th>Vendor</th>
							<th></th>
						</tr>
                    </thead>
					<tfoot>
						<tr class="replace-inputs">
							<th>&nbsp;</th>
							<th>Product ID</th>
							<th>Vendor Product ID</th>
							<th>Vendor</th>
							<th></th>
						</tr>
					</tfoot>
					<tbody>
						<#if vendorProducts?has_content>
							<#list vendorProducts as product>
								<tr>
									<td>
										<a href="<@ofbizUrl>/productVendor</@ofbizUrl>?id=${product.productId?if_exists}&partyId=${product.vendorPartyId?if_exists}">
											<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.productId}?hei=40&amp;fmt=png-alpha" />
										</a>
									</td>
									<td>${product.productId?if_exists}</td>
									<td>${product.vendorProductId?if_exists}</td>
									<td>${product.vendorPartyId?if_exists}</td>
									<td>
										<a href="<@ofbizUrl>/productVendor</@ofbizUrl>?id=${product.productId?if_exists}&partyId=${product.vendorPartyId?if_exists}" class="btn btn-default btn-xs">
											Edit
										</a>
									</td>
								</tr>
							</#list>
						</#if>
					</tbody>
				</table>
				<div class="row">
					<div class="col-md-12">
						<button class="btn btn-orange btn-icon btn-sm icon-right addSKU" data-toggle="modal" data-target="#addSKU">
							ADD NEW PRODUCT
							<i class="entypo-check"></i>
						</button>
						<div class="modal fade" id="addSKU" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="myModalLabel">Add New Product</h4>
									</div>
									<div class="modal-body row">
										<div class="col-xs-12">
											<div class="form-group has-feedback">
												<div class="envRow margin-top-xxs">
													<input type="hidden" id="vendorPartyId" name="vendorPartyId" value="${vendorPartyId?if_exists}" />
													<div class="envCol col50">
														Product ID
														<input type="text" class="form-control" id="productId" name="productId" placeholder="Product ID" value="" />
													</div>
													<div class="envCol col50">
														Vendor Product ID
														<input type="text" class="form-control" id="vendorProductId" name="vendorProductId" placeholder="Vendor Product ID" value="" />
													</div>
                                                    <div class="envCol col50">
                                                        Quantity
                                                        <input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quantity" value="500" />
                                                    </div>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-primary j-btn-add">Add</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</#if>

<script src="<@ofbizContentUrl>/html/js/admin/productVendor.js</@ofbizContentUrl>?ts=2"></script>
