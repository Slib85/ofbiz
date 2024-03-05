<link rel="stylesheet" type="text/css" href="/html/css/admin/product/productEditor.css">
<#if "image" == "image">&nbsp;</#if>

<#if product?exists>
	<div class="panel panel-primary panel-line" data-collapsed="0">
		<!-- panel head -->
		<div class="panel-heading">
			<h3 class="panel-title">${product.internalName?if_exists}</h3>
			<div class="panel-actions">
                <form class="" name="productEditor" action="<@ofbizUrl>/productEditor</@ofbizUrl>" method="GET">
                    <div class="input-group-sm">
                        <button type="submit" class="input-search-btn">
                            <i class="icon wb-search" aria-hidden="true"></i>
                        </button>
                        <input type="text" class="form-control" name="id" placeholder="Search for Product" value="${(product.productId)?if_exists}">
                    </div>
                </form>
			</div>
		</div>
		<!-- panel body -->
        <form name="productUpdate" method="POST" action="<@ofbizUrl>/updateProduct</@ofbizUrl>" role="form" class="clearfix form-horizontal form-groups-bordered">
		<input id="productId" type="hidden" name="id" value="${product.productId}" />
		<div class="panel-body">
			<div class="row">
				<div class="col-md-2 center">
					<img class="img-responsive center-block" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.productId?if_exists}?wid=200&amp;hei=200&amp;fmt=png-alpha" alt="${product.internalName?if_exists}" />
				</div>
				<div class="col-md-7">
					<div class="row m-t-10">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Product Name</label>
								<div class="col-sm-9">
									<input name="productName" type="text" class="form-control" placeholder="Product Name" value="${product.productName?if_exists}">
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Tag Line</label>
								<div class="col-sm-9">
									<input name="tagLine" type="text" class="form-control" placeholder="Tag Line" value="${product.tagLine?if_exists}">
								</div>
							</div>
						</div>
					</div>
					<div class="row m-t-10">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Product Description</label>
								<div class="col-sm-9">
									<textarea name="longDescription" class="form-control" placeholder="Product Description">${product.longDescription?if_exists}</textarea>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Color Description</label>
								<div class="col-sm-9">
									<textarea name="colorDescription" class="form-control" placeholder="Color Description">${product.colorDescription?if_exists}</textarea>
								</div>
							</div>
						</div>
					</div>
                    <div class="row m-t-10">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Meta Title</label>
                                <div class="col-sm-9">
                                    <textarea name="metaTitle" class="form-control" placeholder="Meta Title">${product.metaTitle?if_exists}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Meta Description</label>
                                <div class="col-sm-9">
                                    <textarea name="metaDescription" class="form-control" placeholder="Meta Description">${product.metaDescription?if_exists}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
					<div class="row m-t-10">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Box Qty</label>
								<div class="col-sm-9">
									<input name="boxQty" type="text" class="form-control" placeholder="Box Quantity" value="${product.boxQty?if_exists}" />
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Carton Qty</label>
								<div class="col-sm-9">
									<input name="cartonQty" type="text" class="form-control" placeholder="Carton Quantity" value="${product.cartonQty?if_exists}" />
								</div>
							</div>
						</div>
					</div>
					<div class="row m-t-10">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Bin Location</label>
								<div class="col-sm-9">
									<input name="binLocation" type="text" class="form-control" placeholder="Bin Location" value="${product.binLocation?if_exists}">
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Weight Per Each</label>
								<div class="col-sm-9">
									<input name="weight" type="text" class="form-control" placeholder="Weight Per Each" value="${product.productWeight?if_exists}">
								</div>
							</div>
						</div>
					</div>
					<div class="row m-t-10">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Product Type</label>
								<div class="col-sm-9">
									<select name="productTypeId" class="form-control" >
										<option value="ENVELOPE" <#if product.productTypeId?exists && product.productTypeId == "ENVELOPE">selected</#if>>ENVELOPE</option>
										<option value="BAG" <#if product.productTypeId?exists && product.productTypeId == "BAG">selected</#if>>BAG</option>
										<option value="BOX" <#if product.productTypeId?exists && product.productTypeId == "BOX">selected</#if>>BOX</option>
										<option value="FOLDER" <#if product.productTypeId?exists && product.productTypeId == "FOLDER">selected</#if>>FOLDER</option>
										<option value="LINERS" <#if product.productTypeId?exists && product.productTypeId == "LINERS">selected</#if>>LINERS</option>
										<option value="MAILER" <#if product.productTypeId?exists && product.productTypeId == "MAILER">selected</#if>>MAILER</option>
										<option value="PAPER" <#if product.productTypeId?exists && product.productTypeId == "PAPER">selected</#if>>PAPER</option>
										<option value="PEN" <#if product.productTypeId?exists && product.productTypeId == "PEN">selected</#if>>PEN</option>
										<option value="STAMP" <#if product.productTypeId?exists && product.productTypeId == "STAMP">selected</#if>>STAMP</option>
										<option value="TUBE" <#if product.productTypeId?exists && product.productTypeId == "TUBE">selected</#if>>TUBE</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Parent Product Id</label>
								<div class="col-sm-9">
									<input name="parentProductId" type="text" class="form-control" placeholder="Parent Product Id" value="${product.parentProductId?if_exists}">
								</div>
							</div>
						</div>
					</div>
					<div class="row m-t-10">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Product Height</label>
								<div class="col-sm-9">
									<input name="productHeight" type="text" class="form-control" placeholder="Product Height (ex: 5.5)" value="${product.productHeight?if_exists}">
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Product Width</label>
								<div class="col-sm-9">
									<input name="productWidth" type="text" class="form-control" placeholder="Product Width (ex: 2.5)" value="${product.productWidth?if_exists}">
								</div>
							</div>
						</div>
					</div>
					<div class="row m-t-10">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Product Category Id</label>
								<div class="col-sm-9">
									<input name="primaryProductCategoryId" type="text" class="form-control" placeholder="Category (ex: SQUARE_FLAP)" value="${product.primaryProductCategoryId?if_exists}">
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">Sale/Clearance</label>
								<div class="col-sm-9">
									<select name="percentSavings" class="form-control" >
										<option value="" selected></option>
										<option value="NONE">Default</option>
										<#list 2..15 as x>
										<option value="${x * 5}">${x * 5}% OFF</option>
										</#list>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-3 center">
                    <div class="row">
                        <h3 class="panel-title noPaddingBottom">General Options</h3>
                    </div>
					<div class="row">
						<div class="col-md-12">
                            <div class="row m-t-10">
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="hasRushProductionCheckbox" name="hasRushProduction" value="Y" <#if product.hasRushProduction?has_content && product.hasRushProduction == "Y">checked</#if> />
                                        <label for="hasRushProductionCheckbox">Rushable</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="isPrintableCheckbox" name="isPrintable" value="Y" <#if product.isPrintable?has_content && product.isPrintable == "Y">checked</#if> />
                                        <label for="isPrintableCheckbox">Printable</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row m-t-10">
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="hasWhiteInkCheckbox" name="hasWhiteInk" value="Y" <#if !product.hasWhiteInk?has_content || product.hasWhiteInk == "Y">checked</#if> />
                                        <label for="hasWhiteInkCheckbox">White Ink</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="hasSampleCheckbox" name="hasSample" value="Y" <#if !product.hasSample?has_content || product.hasSample == "Y">checked</#if> />
                                        <label for="hasSampleCheckbox">Has Sample</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row m-t-10">
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="hasFoldCheckbox" name="hasFold" value="Y" <#if product.hasFold?has_content && product.hasFold != "N">checked</#if> />
                                        <label for="hasFoldCheckbox">Has Fold</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="hasCutCheckbox" name="hasCut" value="Y" <#if product.hasCut?has_content && product.hasCut != "N">checked</#if> />
                                        <label for="hasCutCheckbox">Has Cut</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row m-t-10">
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="hasVariableDataCheckbox" name="hasVariableData" value="Y" <#if !product.hasVariableData?has_content || product.hasVariableData == "Y">checked</#if> />
                                        <label for="hasVariableDataCheckbox">Has Variable Data</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="hasCustomQtyCheckbox" name="hasCustomQty" value="N" <#if product.hasCustomQty?has_content && product.hasCustomQty == "N">checked</#if> />
                                        <label for="hasCustomQtyCheckbox">Disable Custom Qty</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row m-t-10">
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="discontinuedCheckbox" name="discontinued" value="Y" <#if product.salesDiscontinuationDate?has_content>checked</#if> />
                                        <label for="discontinuedCheckbox">Discontinued</label>
                                    </div>
                                </div>
								<div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="showOutOfStockRecommendationsCheckbox" name="showOutOfStockRecommendations" value="Y" <#if product.showOutOfStockRecommendations?has_content && product.showOutOfStockRecommendations == "Y">checked</#if> />
                                        <label for="showOutOfStockRecommendationsCheckbox">Out Of Stock Recommendations</label>
                                    </div>
                                </div>
                            </div>
						</div>
					</div>
					<div class="row">
						<h3 class="panel-title noPaddingBottom">Allowed Websites</h3>
					</div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row m-t-10">
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="envelopesWebsiteCheckbox" name="envelopesWebsite" value="Y" <#if productWebsite?has_content && productWebsite.envelopes?has_content && productWebsite.envelopes == "Y">checked</#if> />
                                        <label for="envelopesWebsiteCheckbox">Envelopes</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="checkbox-custom checkbox-primary">
                                        <input type="checkbox" id="foldersWebsiteCheckbox" name="foldersWebsite" value="Y" <#if productWebsite?has_content && productWebsite.folders?has_content && productWebsite.folders == "Y">checked</#if> />
                                        <label for="foldersWebsiteCheckbox">Folders</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <h3 class="panel-title noPaddingBottom">Comments</h3>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <div class="col-sm-9">
                                    <textarea name="comments" class="form-control" placeholder="Comments" style="width: 350px; height: 90px;">${product.comments?if_exists}</textarea>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <span style="font-size: 10px; font-style: italic;">Last Update By: ${product.lastModifiedByUserLogin?if_exists}</span>
                        </div>
                    </div>
				</div>
			</div>

			<div class="row jqs-assets assets">
				<input type="hidden" name="totalAssetCount" value="0" />
				<input type="hidden" name="removedAssets" value="" />
				<div class="col-md-12"><hr></div>
				<div class="col-md-2"></div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="col-sm-12">Additional Product Assets</label>
						<div class="col-sm-12 jqs-assetList assetList">
							<#assign assetIndex = 0 />
							<#list productBasicAssets as basicAsset>
								<div class="row assetRow product-asset-row jqs-asset_row no-margin">
									<div class="col-md-12">
										<input type="hidden" class="asset-id" name="asset${assetIndex}-id" value="${basicAsset.assetId}" />
										<label class="control-label text-xs">Asset Type</label>
										<select name="asset${assetIndex}-type">
											<option value="image" <#if basicAsset.assetType == "image">selected</#if>>Image</option>
											<option value="video" <#if basicAsset.assetType == "video">selected</#if>>Video</option>
										</select>
									</div>
									<div class="col-md-12">
										<label class="control-label">Asset Name</label>
										<input class="asset-name" type="text" name="asset${assetIndex}-name" placeholder="Asset Name" value="${basicAsset.assetName}"/>
									</div>
									<div class="col-xs-12">
										<label class="control-label">Asset Thumbnail</label>
										<input type="text" name="asset${assetIndex}-thumbnail" placeholder="Asset Thumbnail" value="${basicAsset.assetThumbnail?if_exists}" />
									</div>
                                    <div class="col-xs-9">
                                        <label class="control-label">Asset Description</label>
                                        <input type="text" name="asset${assetIndex}-description" placeholder="Asset Description" value="${basicAsset.assetDescription?if_exists}" />
                                    </div>
									<div class="col-xs-3 text-xs-right">
										<label class="control-label">Default?</label>
										<input class="jqs-assetDefault" type="checkbox" name="asset${assetIndex}-default" value="Y" <#if basicAsset.assetDefault?exists && basicAsset.assetDefault == "Y">checked</#if> />
									</div>
									<i class="fa fa-times jqs-remove_asset"></i>
									<i class="fa fa-arrows jqs-moveAsset"></i>
								</div>
								<#assign assetIndex = assetIndex + 1 />
							</#list>
						</div>
						<div class="btn btn-green jqs-add_asset">Add New Product Asset</div>
					</div>
				</div>
				<div class="col-md-4 jqs-design-assets assets">
					<div class="form-group">
						<label class="col-sm-12">Additional Design Assets</label>
						<input type="hidden" name="totalDesignAssetCount" value="0" />
						<input type="hidden" name="removedDesignAssets" value="" />
						<div class="col-sm-12 jqs-design-idList assetList">
						<#assign currentDesignId = '' />
						<#list productPrintedAssets as printedAsset>
						<#if currentDesignId != printedAsset.designId?if_exists>
							<div class="row assetRow jqs-design-id_row no-margin">
								<div class="col-md-12 design-id-row">
									<label class="control-label">Design ID</label>
									<input type="text" class="design-id-field jqs-design-id-field" placeholder="Design ID" value="${printedAsset.designId?if_exists}">
									<div class="jqs-design-assetList">
									</#if>
									<div class="row design-asset-row">
									<input type="hidden" class="asset-id" name="asset${assetIndex}-id" value="${printedAsset.assetId}">
									<input type="hidden" name="asset${assetIndex}-type" value="printed">
									<input class="linked-design-id" type="hidden" name="asset${assetIndex}-designId" value="${printedAsset.designId?if_exists}">
									<div class="col-md-12">
										<label class="control-label">Asset Name</label>
										<input class="asset-name" type="text" name="asset${assetIndex}-name" placeholder="Asset Name" value="${printedAsset.assetName?if_exists}">
									</div>
									<div class="col-xs-12">
										<label class="control-label">Asset Thumbnail</label>
										<input class="asset-thumb" type="text" name="asset${assetIndex}-thumbnail" placeholder="Asset Thumbnail" value="${printedAsset.assetThumbnail?if_exists}" type="text">
									</div>
									<div class="col-xs-9">
										<label class="control-label">Asset Description</label>
										<input class="asset-description" type="text" name="asset${assetIndex}-description" placeholder="Asset Description" value="${printedAsset.assetDescription?if_exists}" type="text">
									</div>
									<div class="col-xs-3 text-xs-right">
										<label class="control-label">Default?</label>
										<input class="jqs-design-assetDefault" type="checkbox" name="asset${assetIndex}-default" value="Y" <#if printedAsset.assetDefault?exists && printedAsset.assetDefault == "Y">checked</#if> />
									</div>
										<i class="fa fa-times jqs-remove_design-asset"></i>
										<i class="fa fa-arrows jqs-moveDesignAsset"></i>
									</div>
									<#assign currentDesignId = printedAsset.designId />
									<#if !printedAsset?has_next>
										</div>
										</div>
										<div class="col-md-12"><a class="jqs-add_design-asset add_design-asset">Add Asset to this ID</a></div>
										<i class="fa fa-times jqs-remove_design-id"></i><i class="fa fa-arrows jqs-moveDesignId"></i>
										</div>
									<#else>
										<#if currentDesignId != productPrintedAssets[printedAsset_index + 1].designId>
											</div>
											</div>
											<div class="col-md-12"><a class="jqs-add_design-asset add_design-asset">Add Asset to this ID</a></div>
											<i class="fa fa-times jqs-remove_design-id"></i><i class="fa fa-arrows jqs-moveDesignId"></i>
											</div>
										</#if>
									</#if>
									<#assign assetIndex = assetIndex + 1 />
								</#list>
							</div>
						<div class="btn btn-green jqs-add_design-id">Add New Design Asset</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="panel panel-primary panel-line" data-collapsed="0">
		<!-- panel head -->
		<div class="panel-heading">
			<div class="panel-title">Product Features</div>
		</div>

		<!-- panel body -->
		<div class="panel-body">
			<#list productFeatureAndOptions.keySet() as key>
				<div class="col-md-6">
					<div class="form-group">
						<label class="col-sm-4 control-label">${Static["com.envelopes.product.ProductHelper"].getFeatureDescByType(delegator, key)} <br /><div class="btn-link" style="display: inline-block; font-size: 10px; color: #005c98;" data-toggle="modal" data-target="#addpf_${key}">Create New</div></label>
						<div class="col-sm-8">
							<select name="pf_${key}" class="form-control" data-allow-clear="true" data-placeholder="${Static["com.envelopes.product.ProductHelper"].getFeatureDescByType(delegator, key)}">
								<option></option>
								<#list (productFeatureAndOptions.get(key)).keySet() as key2>
									<#if (productFeatureAndOptions.get(key)).get(key2)?has_content>
									<option value="${key2}" <#if appliedFeatureIds?seq_contains(key2)>selected</#if>>${(productFeatureAndOptions.get(key)).get(key2)}</option>
									</#if>
								</#list>
							</select>
							<div class="modal fade" id="addpf_${key}" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="myModalLabel">Add a new ${key} feature</h4>
										</div>
										<div class="modal-body row">
											<div class="col-xs-2"></div>
											<div class="col-xs-8">
												<div class="form-group has-feedback">
													<label class="control-label">Description:</label>
													<input type="text" class="form-control" data-fId="${key}" value="" />
													<span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
												</div>
											</div>
											<div class="col-xs-2"></div>
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
			</#list>
		</div>
	</div>
	<div class="panel panel-primary panel-line">
		<!-- panel head -->
		<div class="panel-heading">
			<div class="panel-title">Product Prices</div>
		</div>

		<!-- panel body -->
        <div class="panel-body">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Quantity</th>
						<th># of Ink Colors</th>
						<th>Price</th>
						<th>Cost</th>
						<th>Print Cost</th>
						<th>Manufacturing Cost</th>
						<th>UPC</th>
                        <th>Discontinue</th>
                        <th>Delete</th>
					</tr>
				</thead>
				<tbody class="priceList">
				<#list prices as price>
					<tr>
						<td>${price.quantity?if_exists}</td>
						<td><#if price.colors?exists && price.colors == 0>Plain<#else>${price.colors?if_exists} Color</#if><#if price.originalPrice?has_content><br />($${price.originalPrice}) </#if></td>
						<td>
							<div class="input-group">
								<span class="input-group-addon">$</span>
								<input type="text" name="pp_${price.quantity?if_exists}_${price.colors?if_exists}" class="form-control" placeholder="Price" value="${price.price?if_exists}">
							</div>
						</td>
						<td>
							<div class="input-group">
								<span class="input-group-addon">$</span>
								<input type="text" name="pc_${price.quantity?if_exists}_${price.colors?if_exists}" class="form-control" placeholder="Cost Per Each" value="${price.cost?if_exists}">
							</div>
						</td>
						<td>
							<div class="input-group">
								<span class="input-group-addon">$</span>
								<input type="text" name="ppc_${price.quantity?if_exists}_${price.colors?if_exists}" class="form-control" placeholder="Print Cost Per Each" value="${price.printCost?if_exists}">
							</div>
						</td>
						<td>
							<div class="input-group">
								<span class="input-group-addon">$</span>
								<input type="text" name="pmc_${price.quantity?if_exists}_${price.colors?if_exists}" class="form-control" placeholder="Manufacturing Cost Per Each" value="${price.manufacturingCost?if_exists}">
							</div>
						</td>
						<td>
							<div class="input-group">
								<span class="input-group-addon">UPC</span>
								<input type="text" name="upc_${price.quantity?if_exists}_${price.colors?if_exists}" class="form-control" placeholder="UPC" value="${price.upc?if_exists}">
							</div>
						</td>
						<td>
							<div class="checkbox-custom checkbox-primary">
								<input type="checkbox" id="ssdChecked" name="sdd_${price.quantity?if_exists}_${price.colors?if_exists}" value="Y" <#if price.thruDate?has_content>checked</#if> />
								<label for="ssdChecked"></label>
							</div>
						</td>
                        <td>
                            <div class="checkbox-custom checkbox-primary">
                                <div bns-removeproductprice data-productid="${product.productId?if_exists}" data-price="${price.price?if_exists}" data-quantity="${price.quantity?if_exists}" data-colors="${price.colors?if_exists}" class="btn btn-primary btn-sm">DELETE</div>
                            </div>
                        </td>
					</tr>
				</#list>
				</tbody>
			</table>
			<div class="row">
				<div class="col-md-12">
					<div class="btn btn-outline btn-orange btn-icon btn-sm icon-right addPrice" data-toggle="modal" data-target="#addQuantity">
						ADD NEW PRICE
						<i class="entypo-check"></i>
					</div>
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
											<input type="text" class="form-control" id="quantity" placeholder="Quantity" value="" />
											<select id="colors" class="form-control" name="colors"><option value="0">Ink Option</option><option value="0">Plain</option><option value="1">1 Color</option><option value="2">2 Color</option><option value="4">4 Color</option></select>
											<input type="text" class="form-control" id="price" placeholder="Price" value="" />
											<input type="text" class="form-control" id="upc" placeholder="UPC" value="" />
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

	<div class="panel panel-primary panel-line" data-collapsed="0">
		<!-- panel head -->
		<div class="panel-heading">
			<div class="panel-title">Product Lead Time</div>
		</div>

		<!-- panel body -->
		<div class="panel-body">
			<div class="row">
				<div class="col-md-4 center">
					<div class="form-group">
						<label class="col-sm-3 control-label">Plain Lead Time</label>
						<div class="col-sm-9">
							<input name="leadTimePlain" type="text" class="form-control" placeholder="Plain Lead Time" value="${product.leadTimePlain?default("0")}">
						</div>
					</div>
				</div>
				<div class="col-md-4 center">
					<div class="form-group">
						<label class="col-sm-3 control-label">Standard Lead Time</label>
						<div class="col-sm-9">
							<input name="leadTimeStandardPrinted" type="text" class="form-control" placeholder="Standard Lead Time" value="${product.leadTimeStandardPrinted?default("3")}">
						</div>
					</div>
				</div>
				<div class="col-md-4 center">
					<div class="form-group">
						<label class="col-sm-3 control-label">Rush Lead Time</label>
						<div class="col-sm-9">
							<input name="leadTimeRushPrinted" type="text" class="form-control" placeholder="Rush Lead Time" value="${product.leadTimeRushPrinted?default("2")}">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

    <div class="row pull-xs-right">
        <button type="submit" class="btn btn-primary btn-sm"><i class="icon wb-check" aria-hidden="true"></i> SAVE</button>
    </div>
</form>
<#else>
<br />
<div class="row">
	<div class="col-md-12">
        <div class="panel">
			<div class="panel-heading">
                <div class="panel-title">Products</div>
                <div class="panel-actions">
                    <form class="" name="productEditor" action="<@ofbizUrl>/productEditor</@ofbizUrl>" method="GET">
                        <div class="input-group-sm">
                            <button type="submit" class="input-search-btn">
                                <i class="icon wb-search" aria-hidden="true"></i>
                            </button>
                            <input type="text" class="form-control" name="id" placeholder="Search for Product" value="${(product.productId)?if_exists}">
                        </div>
                    </form>
                </div>
			</div>
            <div class="panel-body">
                <table class="table table-hover dataTable table-striped" id="productTable">
					<thead>
						<tr>
							<th>&nbsp;</th>
							<th>Product ID</th>
							<th>Name</th>
							<th>Color</th>
							<th>Weight</th>
							<th>Collection</th>
							<th></th>
						</tr>
					</thead>

					<tbody>
						<#if activeProducts?has_content>
							<#list activeProducts as product>
								<tr>
									<td>
										<a href="<@ofbizUrl>/productEditor</@ofbizUrl>?id=${product.variantProductId?if_exists}">
											<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.variantProductId}?hei=40&amp;fmt=png-alpha" />
										</a>
									</td>
									<td>${product.variantProductId?if_exists}</td>
									<td>${product.productName?if_exists}</td>
									<td>${product.colorDescription?if_exists}</td>
									<td>${product.paperWeightDescription?if_exists}</td>
									<td>${product.collectionDescription?if_exists}</td>
									<td>
										<a href="<@ofbizUrl>/productEditor</@ofbizUrl>?id=${product.variantProductId?if_exists}" class="btn btn-outline btn-default btn-xs">
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
													<div class="envCol col50">
														Product ID
														<input type="text" class="form-control" id="productId" name="productId" placeholder="Product ID" value="" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-default j-btn-add">Add</button>
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

<script src="<@ofbizContentUrl>/html/js/admin/productEditor.js</@ofbizContentUrl>?ts=1"></script>
