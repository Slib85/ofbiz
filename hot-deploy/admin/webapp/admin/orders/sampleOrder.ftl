<link rel="stylesheet" href="/html/themes/global/vendor/jquery-wizard/jquery-wizard.css">
<link rel="stylesheet" href="/html/themes/global/vendor/formvalidation/formValidation.css">
<link rel="stylesheet" href="/html/css/admin/orders/createOrder.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/css/bootstrap-multiselect.css">

<form id="createOrder" method="POST" action="<@ofbizUrl>/addCustomOrder</@ofbizUrl>" class="form-wizard">
	<input type="hidden" name="_SALES_CHANNEL_ENUM_ID" value="FOLD_SALES_CHANNEL" />
    <input type="hidden" name="_WEB_SITE_ID_" value="folders" />

	<div class="panel" id="orderWizard">
		<div class="panel-body">
			<input type="hidden" name="ppsOrder" value="Y" />
			<div class="steps steps-sm row" data-plugin="matchHeight" data-by-row="true" role="tablist">
				<div class="step col-xs-12 current" data-target="#tab-1" role="tab">
					<span class="step-number">1</span>
					<div class="step-desc">
						<span class="step-title">Shipping Address</span>
					</div>
				</div>
				<div class="step col-xs-12" data-target="#tab-2" role="tab">
					<span class="step-number">2</span>
					<div class="step-desc">
						<span class="step-title">Billing Address</span>
					</div>
				</div>
				<div class="step col-xs-12" data-target="#tab-3" role="tab">
					<span class="step-number">3</span>
					<div class="step-desc">
						<span class="step-title">Items</span>
					</div>
				</div>
				<div class="step col-xs-12" data-target="#tab-4" role="tab">
					<span class="step-number">4</span>
					<div class="step-desc">
						<span class="step-title">Shipping Method</span>
					</div>
				</div>
				<div class="step col-xs-12" data-target="#tab-5" role="tab">
					<span class="step-number">5</span>
					<div class="step-desc">
						<span class="step-title">Payment</span>
					</div>
				</div>
			</div>

			<div class="wizard-content">
				<div class="wizard-pane active" id="tab-1" role="tabpanel">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">First Name</label>
								<input type="text" class="form-control" name="shipping_firstName" placeholder="First Name" />
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">Last Name</label>
								<input type="text" class="form-control" name="shipping_lastName" placeholder="Last Name" />
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">Company Name</label>
								<input type="text" class="form-control" name="shipping_companyName" placeholder="Company Name" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Address Line 1</label>
								<input type="text" class="form-control" name="shipping_address1" placeholder="Address Line 1" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Address Line 2</label>
								<input type="text" class="form-control" name="shipping_address2" placeholder="Address Line 2" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">City</label>
								<input type="text" class="form-control" name="shipping_city" placeholder="City" />
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">State</label>
								<select name="shipping_stateProvinceGeoId" class="form-control">
									<option value="">State/Province</option>
									${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">Zip</label>
								<input type="text" class="form-control" name="shipping_postalCode" placeholder="Zip" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Country</label>
								<select name="shipping_countryGeoId" class="form-control">
									${screens.render("component://envelopes/widget/CommonScreens.xml#countries")}
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Phone</label>
								<input type="text" class="form-control" name="shipping_contactNumber" placeholder="Phone" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Email</label>
								<input type="text" class="form-control" name="emailAddress" placeholder="Email" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<div class="radio-custom radio-primary">
									<input type="radio" id="ship_to_business" name="ship_to" value="BUSINESS_LOCATION" checked>
									<label for="ship_to_business">Business Location</label>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<div class="radio-custom radio-primary">
									<input type="radio" id="ship_to_residential" name="ship_to" value="RESIDENTIAL_LOCATION">
									<label for="ship_to_residential">Residential Location</label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="wizard-pane" id="tab-2" role="tabpanel">
					<div class="row">
						<div class="col-md-1">
							<div class="form-group">
								<div class="checkbox-custom checkbox-primary">
									<input type="checkbox" id="billAsShip" />
									<label for="billAsShip">Same as Shipping</label>
								</div>
							</div>
						</div>
						<div class="col-md-1">
							<div class="form-group">
								<div class="checkbox-custom checkbox-primary">
									<input type="checkbox" id="no_price_info" name="no_price_info" />
									<label for="no_price_info">Blind Ship</label>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">First Name</label>
								<input type="text" class="form-control" name="billing_firstName" placeholder="First Name" />
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">Last Text</label>
								<input type="text" class="form-control" name="billing_lastName" placeholder="Last Name" />
							</div>
						</div>
						<div class="col-md-2">
							<div class="form-group">
								<label class="control-label">Company Name</label>
								<input type="text" class="form-control" name="billing_companyName" placeholder="Company Name" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Address Line 1</label>
								<input type="text" class="form-control" name="billing_address1" placeholder="Address Line 1" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Address Line 2</label>
								<input type="text" class="form-control" name="billing_address2" placeholder="Address Line 2" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">City</label>
								<input type="text" class="form-control" name="billing_city" placeholder="City" />
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">State</label>
								<select name="billing_stateProvinceGeoId" class="form-control">
									<option value="">State/Province</option>
									${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label">Zip</label>
								<input type="text" class="form-control" name="billing_postalCode" placeholder="Zip" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Country</label>
								<select name="billing_countryGeoId" class="form-control">
									${screens.render("component://envelopes/widget/CommonScreens.xml#countries")}
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Phone</label>
								<input type="text" class="form-control" name="billing_contactNumber" placeholder="Phone" />
							</div>
						</div>
					</div>
				</div>
				<div class="wizard-pane" id="tab-3" role="tabpanel">
                    <input type="hidden" name="weight" />
                    <input type="hidden" name="colorsFront" value="0" />
                    <input type="hidden" name="colorsBack" value="0" />
					<div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Folder Category</label>
                                <select name="categoryId" class="form-control jqs-productCategoryIdList jqs-listOption">
                                    <option value="all">All Categories</option>
									<#list folderCategoryList as foldersCategory><option value="${foldersCategory.productCategoryId}">${foldersCategory.categoryName}</option></#list>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Folder Style</label>
                                <select name="id" class="form-control jqs-productIdList jqs-listOption">
                                    <option value="">Select a Folder Style</option>
									<#list folderStyleList as foldersStyle><option value="SAMPLE" data-styleId="${foldersStyle.productId}">${foldersStyle.productId} (${foldersStyle.productName?if_exists})</option></#list>
                                    <option value="SAMPLE" data-styleId="SAMPLE-PACK">SAMPLE-PACK (Custom Sample Pack)</option>
								</select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group multiSelectAdjustment">
                                <label class="control-label">Printing Method</label>
                                <select class="selectpicker jqs-listOption" name="printingMethod" multiple>
                                    <option value="1 Color Printing">1 Color Printing</option>
                                    <option value="2 Color Printing">2 Color Printing</option>
                                    <option value="Full Color Printing">Full Color Printing</option>
                                    <option value="Foil Stamped">Foil Stamped</option>
                                    <option value="Embossing">Embossing</option>
                                    <option value="Blind Embossed">Blind Embossed</option>
                                    <option value="Foil and Embossed">Foil and Embossed</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Material</label>
                                <select name="material" class="form-control jqs-listOption" disabled>
                                    <option value="">Select a Material</option>
                                </select>
                            </div>
                        </div>
					</div>
					<div class="row">
                        <div class="col-md-2">
                            <div class="form-group">
                                <label class="control-label">Custom Foil Color #</label>
                                <input type="text" val="" name="customFoilColorNumber" class="form-control jqs-listOption" />
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <label class="control-label">Offset Color Printing</label>
                                <select name="offsetColorPrinting" class="form-control jqs-listOption">
                                    <option value="">None</option>
                                    <option value="1 Color PMS Light Ink Coverage">1 Color PMS Light Ink Coverage</option>
                                    <option value="1 Color PMS Heavy Ink Coverage">1 Color PMS Heavy Ink Coverage</option>
                                    <option value="2 Color PMS Light Ink Coverage">2 Color PMS Light Ink Coverage</option>
                                    <option value="2 Color PMS Heavy Ink Coverage">2 Color PMS Heavy Ink Coverage</option>
                                </select>
                            </div>
                        </div>
						<div class="col-md-2">
							<div class="form-group">
                                <label class="control-label">Offset Printing Coatings</label>
                                <select name="offsetPrintingCoatings" class="form-control jqs-listOption">
                                    <option value="">None</option>
                                    <option value="Flood Gloss">Flood Gloss</option>
                                    <option value="Matte">Matte</option>
                                    <option value="Satin Aqueous">Satin Aqueous</option>
                                    <option value="Soft Touch">Soft Touch</option>
                                    <option value="UV">UV</option>
                                    <option value="Film Lamination">Film Lamination</option>
                                    <option value="Spot Coatings">Spot Coatings</option>
                                </select>
							</div>
						</div>
                        <div class="col-md-3">
                            <div class="form-group multiSelectAdjustment">
                                <label class="control-label">Metallic Foils</label>
                                <select class="selectpicker jqs-listOption" name="metallicFoils" multiple>
                                    <#list metallicFoilList as foil>
										<#if foil.foilId?exists><option value="${foil.foilId}">${foil.foilId}</option></#if>
                                    </#list>
                                </select>
							</div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group multiSelectAdjustment">
                                <label class="control-label">Non-Metallic Foils</label>
                                <select class="selectpicker jqs-listOption" name="nonMetallicFoils" multiple>
									<#list nonMetallicFoilList as foil>
										<#if foil.foilId?exists><option value="${foil.foilId}">${foil.foilId}</option></#if>
									</#list>
                                </select>
                            </div>
                        </div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Description</label>
								<input type="text" class="form-control" name="name" placeholder="Description" readonly />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-5">
							<div class="form-group">
								<label class="control-label">Quantity</label>
								<input type="text" class="form-control" name="quantity" placeholder="Quantity" value="1" />
							</div>
						</div>
						<div class="col-md-5">
							<div class="form-group">
								<label class="control-label">Price</label>
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" class="form-control" name="price" placeholder="Price" value="0" />
									<span class="input-group-btn"><button type="button" class="btn btn-info calcPrice"><i class="wb-refresh"></i></button></span>
								</div>
							</div>
						</div>
						<div class="col-md-2">
							<label class="control-label">&nbsp;</label>
							<div class="input-group">
								<button type="button" class="btn btn-success addToCart">Add to Cart</button>
							</div>
						</div>
					</div>
					<div class="row" style="margin-top: 40px;">
						<table class="table table-bordered responsive productList">
							<thead>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Color</th>
								<th>Quantity</th>
								<th>Price</th>
								<th>Remove</th>
							</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="6">No Items Added</td>
								</tr>
							</tbody>
						</table>
					</div>
                    <div class="row" style="margin-top: 40px;">
                        <div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Order Notes</label>
								<textarea class="form-control" id="textareaDefault" name="orderNote" placeholder="Notes" rows="3"></textarea>
							</div>
						</div>
					</div>
					<#--<div class="row">
						<div class="form-group pull-right">
							<button type="button" class="btn btn-primary btn-orange additem">Add More Item</button>
						</div>
					</div>-->
				</div>
				<div class="wizard-pane" id="tab-4" role="tabpanel">
					<div class="row">
						<button type="button" class="btn btn-info btn-block calcShip">
							Calculate Shipping
							<i class="entypo-flight"></i>
						</button>
					</div>
					<div class="row">
						<div class="form-group shippingOptions">
						</div>
					</div>
				</div>
				<div class="wizard-pane" id="tab-5" role="tabpanel">
					<div class="row">
						<div class="col-md-2">
							<div class="radio-custom radio-primary">
								<input type="radio" id="paymentMethodCreditCard" name="paymentMethodTypeId" value="CREDIT_CARD">
								<label for="paymentMethodCreditCard">Credit Card</label>
							</div>
						</div>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label">Card Number</label>
										<input type="text" class="form-control" name="cardNumber" placeholder="Card Number" />
									</div>
								</div>
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label">Exp Date</label>
										<select class="form-control" name="expMonth">
											<option value="">Exp Month</option>
											<option value="01">01 - January</option>
											<option value="02">02 - February</option>
											<option value="03">03 - March</option>
											<option value="04">04 - April</option>
											<option value="05">05 - May</option>
											<option value="06">06 - June</option>
											<option value="07">07 - July</option>
											<option value="08">08 - August</option>
											<option value="09">09 - September</option>
											<option value="10">10 - October</option>
											<option value="11">11 - November</option>
											<option value="12">12 - December</option>
										</select>
									</div>
								</div>
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label">Exp Year</label>
										<select class="form-control" name="expYear">
											<option value="">Exp Year</option>
											<#list currentYear..currentYear + 10 as i>
												<option value="${i}">${i}</option>
											</#list>
										</select>
									</div>
								</div>
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label">CVV</label>
										<input type="text" class="form-control" name="billToCardSecurityCode" placeholder="Security Code" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-2">
							<div class="radio-custom radio-primary">
								<input type="radio" id="paymentMethodCheck" name="paymentMethodTypeId" value="PERSONAL_CHECK">
								<label for="paymentMethodCheck">Check</label>
							</div>
						</div>
						<div class="col-md-10">
							<div class="form-group">
								<label class="control-label">Check Number</label>
								<input type="text" class="form-control" name="checkNumber" placeholder="Check Number (optional)" />
							</div>
						</div>
					</div>
					<div class="row text-right margin-right-15">
						<button id="submitCreatePromo" type="submit" class="btn btn-success btn-icon btn-lg icon-right ">
							CREATE ORDER
							<i class="entypo-check"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/js/bootstrap-multiselect.min.js"></script>
<script src="<@ofbizContentUrl>/html/js/admin/sampleOrder.js</@ofbizContentUrl>?ts=2"></script>