<style type="text/css">
	.plateComment {
		height: 275px !important;
	}
</style>

<#list plate as orderItem>
	<#if orderItem_index == 0>
		<#assign pressId = orderItem.pressNum?if_exists />
		<#assign plateId = orderItem.plateNumber?if_exists?upper_case />
		<#assign statusId = orderItem.statusId?if_exists />
		<#assign dueDate = orderItem.dueDateYMD?if_exists />
		<#assign comment = orderItem.comment?if_exists />
	</#if>
</#list>

<div class="row row-spacer">
	<div class="col-md-6 col-sm-6 col-xs-4"><h2>${plateId?if_exists} (<a style="color: blue;text-decoration: underline" href="<@ofbizUrl>/plateReceipt</@ofbizUrl>?plateId=${plateId?if_exists}">Print Plate</a>)</h2></div>
</div>

<#assign printPressList = delegator.findAll("PrintPress", true) />
<form id="createOrUpdatePlate" name="editPlate" action="<@ofbizUrl>/createOrUpdatePlate</@ofbizUrl>" method="GET">
<input type="hidden" name="plateId" value="${plateId?if_exists}" />
<input type="hidden" name="printPlateId" value="${plateId?if_exists}" />
<input type="hidden" name="responseName" value="plateEdit" />
<input type="hidden" name="saveResponse" value="true"/>
<div class="panel panel-info" data-collapsed="0">
	<div class="panel-heading">
		<div class="panel-title Rule">Plate Options</div>
		<div class="panel-options">
			<a href="#" data-rel="collapse"><i class="entypo-down-open"></i></a>
			<a href="#" data-rel="close"><i class="entypo-cancel"></i></a>
		</div>
	</div>
	<!-- panel body -->
	<div class="panel-body">
		<div class="row">
			<div class="col-md-3">
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label">Due Date: </label>
							<input type="text" name="dueDate" value="${dueDate?if_exists}" class="form-control datepicker" data-format="yyyy-mm-dd">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label">Press: </label>
							<select name="printPressId" class="select2" data-allow-clear="true" data-placeholder="Press ID">
							<#if printPressList?has_content>
								<#list printPressList as press>
									<option value="${press.printPressId}" <#if pressId?exists && pressId == press.printPressId>selected</#if>>${press.description}</option>
								</#list>
							</#if>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label">Print Schedule: </label>
							<select name="schedulePrintPressId" class="select2" data-allow-clear="true" data-placeholder="Press ID">
							<#if printPressList?has_content>
								<#list printPressList as press>
									<option value="${press.printPressId}" <#if pressId?exists && pressId == press.printPressId>selected</#if>>${press.description}</option>
								</#list>
							</#if>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label">Status: </label>
							<select name="statusId" class="select2" data-allow-clear="true" data-placeholder="Press ID">
							<#assign plateStatusList = delegator.findByAnd("StatusItem", Static["org.apache.ofbiz.base.util.UtilMisc"].toMap("statusTypeId", "AE_PLATE_STTS"), null, true) />
							<#if plateStatusList?has_content>
								<#list plateStatusList as plateStatus>
									<option value="${plateStatus.statusId}" <#if statusId?exists && statusId == plateStatus.statusId>selected</#if>>${plateStatus.description}</option>
								</#list>
							</#if>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<a class="btn btn-info" id="submitPlate" href="<@ofbizUrl>/createOrUpdatePlate</@ofbizUrl>">Save</a>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-9">
				<div class="form-group">
					<label class="control-label">Comments/Notes: </label>
					<textarea class="form-control plateComment" name="comment" placeHolder="Comments">${comment?if_exists}</textarea>
				</div>
			</div>
		</div>
	</div>
</div>
</form>

<#list plate as orderItem>
<#assign orderItemSeq = Static["com.envelopes.util.EnvUtil"].removeChar("0", orderItem.orderItemSeqId, true, false, false) />
<form id="createOrUpdatePlateItem_${orderItem.name}" name="editPlate" action="<@ofbizUrl>/createOrUpdatePlateItem</@ofbizUrl>" method="GET">
<input type="hidden" name="plateId" value="${plateId?if_exists}" />
<input type="hidden" name="responseName" value="plateEdit" />
<input type="hidden" name="saveResponse" value="true"/>
<input type="hidden" name="orderId" value="${orderItem.orderNumber}" />
<input type="hidden" name="orderItemSeqId" value="${orderItem.orderItemSeqId}" />
	<div class="panel panel-primary" data-collapsed="0">
		<div class="panel-heading">
			<div class="panel-title Rule">
                <a style="font-weight:bold;color:#0000ff;text-decoration:underline" href="<@ofbizUrl>/viewOrder?orderId=${orderItem.orderNumber}</@ofbizUrl>#item-${orderItemSeq}">${orderItem.orderNumber} #${orderItem.orderItemSeqId}</a> <br /> SKU: ${orderItem.sku} (${orderItem.item} - Quantity: ${orderItem.quantity})
			</div>
			<div class="panel-options">
				<a href="#" data-rel="collapse"><i class="entypo-down-open"></i></a>
				<a href="#" data-rel="close"><i class="entypo-cancel"></i></a>
			</div>
		</div>
		<!-- panel body -->
		<div class="panel-body">
			<div class="row">
				<div class="col-md-8">
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label">Due Date: </label>
								<input type="text" name="dueDate" value="${(orderItem.itemDueDateYMD)?if_exists}" class="form-control datepicker" data-format="yyyy-mm-dd">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<a class="btn btn-info" id="submitPlate" href="#" onclick="$('#createOrUpdatePlateItem_${orderItem.name}').submit();">Save</a>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					Ink Color(s): ${orderItem.inkList}<br />
					Comments: ${orderItem.prepressComments?if_exists}<br /><br />
					<#assign proof = delegator.findByAnd("OrderItemContent", Static["org.apache.ofbiz.base.util.UtilMisc"].toMap("contentPurposeEnumId", "OIACPRP_PDF", "orderId", orderItem.orderNumber, "orderItemSeqId", orderItem.orderItemSeqId), null, false) />
					<#if proof?has_content><a class="btn btn-info" href="<@ofbizUrl>/serveFileForStream</@ofbizUrl>?filePath=${proof.contentPath?if_exists}&amp;fileName=${proof.contentName?if_exists}&amp;downLoad=Y">Download Proof</a></#if>
				</div>
			</div>
		</div>
	</div>
</form>
</#list>

<script src="/html/js/admin/plateEdit.js"></script>