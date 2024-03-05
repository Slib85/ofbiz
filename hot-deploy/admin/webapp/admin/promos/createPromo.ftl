<link rel="stylesheet" href="/html/themes/global/vendor/jquery-wizard/jquery-wizard.css">
<link rel="stylesheet" href="/html/themes/global/vendor/formvalidation/formValidation.css">
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">

<style>
	.Rule .panel {
		border: 1px solid #f2a654;
	}
</style>

<form id="createPromo" method="POST" action="<@ofbizUrl>/createPromo</@ofbizUrl>" class="form-wizard">
    <div class="panel" id="orderWizard">
        <div id="main-panel" class="panel-body">
            <div class="steps steps-sm row" data-plugin="matchHeight" data-by-row="true" role="tablist">
                <div class="step col-xs-12 col-lg-3 current" data-target="#tab-1" role="tab">
                    <span class="step-number">1</span>
                    <div class="step-desc">
                        <span class="step-title">Coupon Description</span>
                    </div>
                </div>
                <div class="step col-xs-12 col-lg-3" data-target="#tab-2" role="tab">
                    <span class="step-number">2</span>
                    <div class="step-desc">
                        <span class="step-title">Rules & Conditions</span>
                    </div>
                </div>
                <div class="step col-xs-12 col-lg-3" data-target="#tab-3" role="tab">
                    <span class="step-number">3</span>
                    <div class="step-desc">
                        <span class="step-title">Valid Thru</span>
                    </div>
                </div>
                <div class="step col-xs-12 col-lg-3" data-target="#tab-4" role="tab">
                    <span class="step-number">4</span>
                    <div class="step-desc">
                        <span class="step-title">Coupon Code</span>
                    </div>
                </div>
            </div>
            <div class="wizard-content">
                <div class="wizard-pane active" id="tab-1" role="tabpanel">
					<#-- <input type="hidden" name="productStoreId" value="10000" /> -->
					<input type="hidden" name="userEntered" value="Y" />
					<input type="hidden" name="useLimitPerOrder" value="1" />
					<input type="hidden" name="useLimitPerCustomer" value="" />
					<input type="hidden" name="useLimitPerPromotion" value="" />
					<input type="hidden" name="billbackFactor" value="" />
					<input type="hidden" name="overrideOrgPartyId" value="" />
					<input type="hidden" name="netsuiteId" value="" />
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Promo Name</label>
								<input class="form-control" name="promoName" placeholder="Name of Promotion" />
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Promo Text</label>
								<input class="form-control" name="promoText" placeholder="Description of Promotion" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Show To Customer</label>
								<select name="showToCustomer" class="form-control" data-allow-clear="true" data-placeholder="Show To Customer">
									<option value="Y" selected>Yes</option>
									<option value="N">No</option>
								</select>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Require Code</label>
								<select name="requireCode" class="form-control" data-allow-clear="true" data-placeholder="Require Code">
									<option value="Y" selected>Yes</option>
									<option value="N">No</option>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">Sample Promo</label>
								<select name="isSample" class="form-control" data-allow-clear="true" data-placeholder="Sample Promo">
									<option value="Y">Yes</option>
									<option value="N" selected>No</option>
								</select>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">Can Stack With Other Promos</label>
								<select name="isStackable" class="form-control" data-allow-clear="true" data-placeholder="Allow Stacking">
									<option value="Y">Yes</option>
									<option value="N" selected>No</option>
								</select>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">Can Be Used By Trade</label>
								<select name="canTradeUse" class="form-control" data-allow-clear="true" data-placeholder="Can Trade Use">
									<option value="Y">Yes</option>
									<option value="N" selected>No</option>
								</select>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">Show on Coupon Page</label>
								<select name="showOnSite" class="form-control" data-allow-clear="true" data-placeholder="Show on Site">
									<option value="Y">Yes</option>
									<option value="N" selected>No</option>
								</select>
							</div>
						</div>
					</div>
				</div>
                <div class="wizard-pane" id="tab-2" role="tabpanel">
					<div class="row Rule">
						<div class="col-md-12">
							<div class="panel panel-warning panel-line" data-collapsed="0">
								<div class="panel-heading">
									<div class="panel-title Rule">Rule #1</div>
								</div>
								<!-- panel body -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label">Rule Name</label>
												<input class="form-control" name="ruleName" placeholder="Name of Rule" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 Condition">
											<div class="panel panel-success" data-collapsed="0">
												<div class="panel-heading">
													<div class="panel-title Condition">Condition #1</div>
													<div class="panel-options">
														<a href="#" data-rel="collapse"><i class="entypo-down-open"></i></a>
														<a href="#" data-rel="close"><i class="entypo-cancel"></i></a>
													</div>
												</div>
												<div class="panel-body">
													<div class="col-md-3">
														<div class="form-group">
															<label class="control-label">Condition Type</label>
															<select name="inputParamEnumId" class="form-control input-lg" data-placeholder="Condition Type">
																<#list inputParamEnums as inputParamEnum>
																	<option value="${(inputParamEnum.enumId)!}">${(inputParamEnum.get("description",locale))!}</option>
																</#list>
															</select>
														</div>
													</div>
													<div class="col-md-3">
														<div class="form-group">
															<label class="control-label">Operator</label>
															<select name="operatorEnumId" class="form-control input-lg" data-placeholder="Operator">
																<#list condOperEnums as condOperEnum>
																	<option value="${(condOperEnum.enumId)!}">${(condOperEnum.get("description",locale))!}</option>
																</#list>
															</select>
														</div>
													</div>
													<div class="col-md-3">
														<div class="form-group">
															<label class="control-label">Value</label>
															<input class="form-control input-lg" name="condValue" placeholder="Value" />
														</div>
													</div>
													<div class="col-md-3">
														<div class="form-group">
															<label class="control-label">Other</label>
															<input class="form-control input-lg" name="otherValue" placeholder="Other" />
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="form-group pull-right clearfix margin-right-15">
											<button type="button" class="btn btn-primary btn-success addcondition">Add More Conditions</button>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 Action">
											<div class="panel panel-info" data-collapsed="0">
												<div class="panel-heading">
													<div class="panel-title Action">Action #1</div>
												</div>
												<div class="panel-body">
													<div class="col-md-3">
														<div class="form-group">
															<label class="control-label">Action Type</label>
															<select name="productPromoActionEnumId" class="form-control input-lg" data-placeholder="Action Type">
																<#list productPromoActionEnums as productPromoActionEnum>
																	<option value="${(productPromoActionEnum.enumId)!}">${(productPromoActionEnum.get("description",locale))!}</option>
																</#list>
															</select>
														</div>
													</div>
													<div class="col-md-3">
														<div class="form-group">
															<label class="control-label">Quantity</label>
															<input class="form-control input-lg" name="quantity" placeholder="Quantity" />
														</div>
													</div>
													<div class="col-md-2">
														<div class="form-group">
															<label class="control-label">Amount</label>
															<input class="form-control input-lg" name="amount" placeholder="Amount" />
														</div>
													</div>
													<div class="col-md-2">
														<div class="form-group">
															<label class="control-label">SKU</label>
															<input class="form-control input-lg" name="productId" placeholder="SKU" />
														</div>
													</div>
													<div class="col-md-2">
														<div class="form-group">
															<label class="control-label">Enable Clearance</label>
															<select name="allowClearance" class="form-control" data-allow-clear="true" data-placeholder="Allow on Clearance">
																<option value="Y">Yes</option>
																<option value="N" selected>No</option>
															</select>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="form-group pull-right clearfix margin-right-15">
											<button type="button" class="btn btn-primary btn-info addaction">Add More Actions</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group pull-right clearfix margin-right-15">
							<button type="button" class="btn btn-primary btn-warning addrule">Add More Rules</button>
						</div>
					</div>
				</div>
                <div class="wizard-pane" id="tab-3" role="tabpanel">
					<div class="row">
						<div class="col-md-12">
							<div class="panel panel-primary panel-line" data-collapsed="0">
								<div class="panel-heading">
									<div class="panel-title">Select Date Range for Promotion</div>
								</div>
								<!-- panel body -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-4">
											<label class="col-md-3 control-label">Start Date</label>
											<div class="col-md-9">
												<div class="input-group">
													<input type="text" name="fromDate" class="form-control" data-plugin="datepicker" data-format="yyyy-mm-dd">
                                                    <span class="input-group-addon"><i class="icon wb-calendar" aria-hidden="true"></i></span>
												</div>
											</div>
										</div>
										<div class="col-md-4">
											<label class="col-md-3 control-label">End Date</label>
											<div class="col-md-9">
												<div class="input-group">
													<input type="text" name="thruDate" class="form-control" data-plugin="datepicker" data-format="yyyy-mm-dd">
													<span class="input-group-addon"><i class="icon wb-calendar" aria-hidden="true"></i></span>
												</div>
											</div>
										</div>
                                        <div class="col-md-4">
                                            <label class="col-md-3 control-label">Web Site IDs</label>
                                            <div class="col-md-9">
                                                <div class="input-group">
                                                    <input type="text" name="webSiteId" class="form-control" placeholder="Web Site Ids" />
                                                    <span class="input-group-addon"><i class="icon wb-calendar" aria-hidden="true"></i></span>
                                                </div>
                                            </div>
                                        </div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
                <div class="wizard-pane" id="tab-4" role="tabpanel">
					<div class="row promoCode">
						<div class="col-md-12">
							<div class="panel panel-primary panel-line" data-collapsed="0">
								<div class="panel-heading">
									<div class="panel-title promoCode">Add Coupon Code</div>
								</div>
								<!-- panel body -->
								<div class="panel-body">
									<div class="row coupon">
										<input type="hidden" name="userEntered" value="Y" />
										<input type="hidden" name="requireEmailOrParty" value="N" />
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label">Coupon Code</label>
												<input class="form-control" name="productPromoCodeId" placeholder="Coupon Code" />
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label">Use Limit Per Code</label>
												<input class="form-control" name="useLimitPerCode" placeholder="Use Limit Per Code" />
											</div>
										</div>
										<div class="col-md-3">
											<label class="control-label">Start Date</label>
											<div class="input-group">
												<input type="text" name="fromDate" class="form-control" data-plugin="datepicker" data-format="yyyy-mm-dd">
                                                <span class="input-group-addon"><i class="icon wb-calendar" aria-hidden="true"></i></span>
											</div>
										</div>
										<div class="col-md-3">
											<label class="control-label">End Date</label>
											<div class="input-group">
												<input type="text" name="thruDate" class="form-control" data-plugin="datepicker" data-format="yyyy-mm-dd">
                                                <span class="input-group-addon"><i class="icon wb-calendar" aria-hidden="true"></i></span>
											</div>
										</div>
									</div>
									<div class="form-group pull-right clearfix">
										<button type="button" class="btn btn-primary btn-orange addcoupon">Add More Coupons</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
<script src="<@ofbizContentUrl>/html/js/admin/promos.js</@ofbizContentUrl>?ts=1"></script>