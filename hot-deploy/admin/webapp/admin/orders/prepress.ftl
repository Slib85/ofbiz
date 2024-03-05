<style>
	.orderSchedule {
		margin: 0 0 20px 0;
		width: 100%;
		padding: 5px;
		border: 1px solid #ccc;
	}
	.panel-heading.multiHeading {
		margin: 0px;
		padding: 20px 30px;
	}
		.panel-heading.multiHeading > * {
			text-align: left;
			padding: 0px;
			position: relative;
		}
		.panel-heading.multiHeading > *:last-child {
			text-align: right;
		}
		.showHiddenQueuesButton {
			cursor: pointer;
		}
		.panel-heading.multiHeading .tglButtonContainer {
			position: absolute;
			right: 0px;
		}
	.hideQueue i {
		cursor: pointer;
	}
	.hidden.forceDisplay {
		display: table-row !important;
		visibility: visible !important;
	}
		.hidden.forceDisplay i.fa-eye {
			opacity: .4;
		}

</style>
<div class="row">
	<div bns-queuecontainer class="col-md-<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")>10<#else>5</#if>">
		<div class="panel panel-success panel-line">
			<div class="panel-heading row multiHeading">
				<div class="panel-title col-sm-4">Offset</div>
				<div class="col-sm-8">
					<div class="tglButtonContainer">
						<div class="tglText">Show Hidden Queues:</div>
						<div class="tglButton">
							<input class="tgl tgl-ios" bns-showhiddenqueue name="showHiddenQueueOffset" id="showHiddenQueueOffset" type="checkbox">
							<label class="tgl-btn" for="showHiddenQueueOffset"></label>
						</div>
					</div>
				</div>
			</div>

			<table class="table table-hover">
				<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")>
                	<thead>
						<tr>
							<th>Status</th>
							<#list foldersAssignedToUsers.keySet() as user>
								<#if
									(
										session.getAttribute("userLogin").userLoginId?has_content && foldersAssignedToUsers.get(session.getAttribute("userLogin").userLoginId)?has_content && (
											foldersAssignedToUsers.get(session.getAttribute("userLogin").userLoginId).get("viewableQueues") == "all" || (
												foldersAssignedToUsers.get(session.getAttribute("userLogin").userLoginId).get("viewableQueues") == "self" &&
												user?string == session.getAttribute("userLogin").userLoginId
											)
										) &&
										foldersAssignedToUsers.get(user).get("isSalesRep") && foldersAssignedToUsers.get(user).get("webSite").get("folders")
									) ||
									user?string == "website"
									>
							    	<th>${user}</th>
								</#if>
							</#list>
                            <th></th>
						</tr>
					</thead>
					<tbody>
						<#list offset.keySet() as statusId>
							<#assign statusItem = delegator.findOne("StatusItem", {"statusId" : statusId}, true)?if_exists />
							<#if statusItem?has_content>
								<#assign statusDesc = statusItem.description />
								<#assign userPrepressQueueKey = statusItem.statusId />
							<#else>
								<#assign statusDesc = statusId />
								<#assign userPrepressQueueKey = statusId />
							</#if>


							<tr bns-queuerow<#if prepressQueueFilterList?exists && prepressQueueFilterList.get("offset")?exists && prepressQueueFilterList.get("offset").get(statusId)?exists && prepressQueueFilterList.get("offset").get(statusId) == "Y"> class="hidden"</#if>>
								<td>${statusDesc}</td>
								<#list foldersAssignedToUsers.keySet() as user>
									<#if
										(
											session.getAttribute("userLogin").userLoginId?has_content && foldersAssignedToUsers.get(session.getAttribute("userLogin").userLoginId)?has_content && (
												foldersAssignedToUsers.get(session.getAttribute("userLogin").userLoginId).get("viewableQueues") == "all" || (
													foldersAssignedToUsers.get(session.getAttribute("userLogin").userLoginId).get("viewableQueues") == "self" &&
													user?string == session.getAttribute("userLogin").userLoginId
												)
											) &&
											foldersAssignedToUsers.get(user).get("isSalesRep") && foldersAssignedToUsers.get(user).get("webSite").get("folders")?has_content && foldersAssignedToUsers.get(user).get("webSite").get("folders")
										) ||
										user?string == "website"
									>
										<td>
											<#if userPrepressQueue.get(userPrepressQueueKey)?has_content && userPrepressQueue.get(userPrepressQueueKey).get(user)?has_content>
												<form id="OFFSET_${userPrepressQueueKey}_${user?string?replace("(?:&.*?;|\\.)", "", "r")}" name="OFFSET_N_${userPrepressQueueKey}_${user?string?replace("(?:&.*?;|\\.)", "", "r")}" method="POST" action="<@ofbizUrl>/prepressOrderList</@ofbizUrl>">
													<input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
													<input type="hidden" name="queue" value="OFFSET" />
													<input type="hidden" name="statusId" value="${statusId}" />
													<input type="hidden" name="orderIds" value="<#list userPrepressQueue.get(userPrepressQueueKey).get(user) as order>${order}<#if order_has_next>,</#if></#list>" />
												</form>
												<#assign orderCount = userPrepressQueue.get(userPrepressQueueKey).get(user)?size />
											<#else>
												<#assign orderCount = 0 />
											</#if>
											<a href="#" onclick="$('#OFFSET_${statusId}_${user?string?replace("(?:&.*?;|\\.)", "", "r")}').submit()"><div class="btn <#if orderCount gt 0>btn btn-success<#else>btn-outline btn-primary</#if> btn-sm"><strong>${orderCount}</strong></div></a>
										</td>
									</#if>
								</#list>
								<td class="hideQueue"><i bns-hidequeue data-statusid="${statusId}" data-queuename="offset" data-tohide="<#if prepressQueueFilterList?exists && prepressQueueFilterList.get("offset")?exists && prepressQueueFilterList.get("offset").get(statusId)?exists && prepressQueueFilterList.get("offset").get(statusId) == "Y">N<#else>Y</#if>" class="fa fa-eye"></i></td>
							</tr>
						</#list>
					</tbody>
				<#else>
					<thead>
						<tr>
							<th>Status</th>
							<th>Standard</th>
							<th>Rush</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<#list offset.keySet() as statusId>
							<tr bns-queuerow<#if prepressQueueFilterList?exists && prepressQueueFilterList.get("offset")?exists && prepressQueueFilterList.get("offset").get(statusId)?exists && prepressQueueFilterList.get("offset").get(statusId) == "Y"> class="hidden"</#if>>
								<#assign statusItem = delegator.findOne("StatusItem", {"statusId" : statusId}, true)?if_exists />
								<#assign statusDesc = (statusItem.description)?default("NA") />
								<td><#if statusDesc == "NA">${statusId}<#else>${statusItem.description}</#if></td>
								<td>
									<form id="OFFSET_N_${statusId}" name="OFFSET_N_${statusId}" method="POST" action="<@ofbizUrl>/prepressOrderList</@ofbizUrl>">
										<input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
										<input type="hidden" name="queue" value="OFFSET" />
										<input type="hidden" name="isRush" value="N" />
										<input type="hidden" name="statusId" value="${statusId}" />
										<input type="hidden" name="orderIds" value="<#list offset.get(statusId).get("Standard") as order>${order}<#if order_has_next>,</#if></#list>" />
									</form>
									<a href="#" onclick="$('#OFFSET_N_${statusId}').submit()"><div class="btn <#if offset.get(statusId).get("Standard")?size gt 0 && (requestParameters.webSiteId?exists && requestParameters.webSiteId == "folders")>btn btn-success<#else>btn-outline btn-primary</#if> btn-sm"><strong>${offset.get(statusId).get("Standard")?size}</strong></div></a>
								</td>
								<td>
									<form id="OFFSET_Y_${statusId}" name="OFFSET_Y_${statusId}" method="POST" action="<@ofbizUrl>/prepressOrderList</@ofbizUrl>">
										<input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
										<input type="hidden" name="queue" value="OFFSET" />
										<input type="hidden" name="isRush" value="Y" />
										<input type="hidden" name="statusId" value="${statusId}" />
										<input type="hidden" name="orderIds" value="<#list offset.get(statusId).get("Rush") as order>${order}<#if order_has_next>,</#if></#list>" />
									</form>
									<a href="#" onclick="$('#OFFSET_Y_${statusId}').submit()"><div class="btn <#if offset.get(statusId).get("Rush")?size gt 0 && (requestParameters.webSiteId?exists && requestParameters.webSiteId == "folders")>btn btn-success<#else>btn-outline btn-primary</#if> btn-sm"><strong>${offset.get(statusId).get("Rush")?size}</strong></div></a>
								</td>
								<td class="hideQueue"><i bns-hidequeue data-statusid="${statusId}" data-queuename="offset" data-tohide="<#if prepressQueueFilterList?exists && prepressQueueFilterList.get("offset")?exists && prepressQueueFilterList.get("offset").get(statusId)?exists && prepressQueueFilterList.get("offset").get(statusId) == "Y">N<#else>Y</#if>" class="fa fa-eye"></i></td>
							</tr>
						</#list>
					</tbody>
				</#if>
			</table>
		</div>
	</div>


	<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")>
	<div bns-queuecontainer class="col-md-5">
		<div class="panel panel-success panel-line">
			<div class="panel-heading row multiHeading">
				<div class="panel-title col-sm-4">Digital</div>
				<div class="col-sm-8">
					<div class="tglButtonContainer">
						<div class="tglText">Show Hidden Queues:</div>
						<div class="tglButton">
							<input class="tgl tgl-ios" bns-showhiddenqueue name="showHiddenQueueDigital" id="showHiddenQueueDigital" type="checkbox">
							<label class="tgl-btn" for="showHiddenQueueDigital"></label>
						</div>
					</div>
				</div>
			</div>
			<table class="table table-hover">
				<thead>
				<tr>
					<th>Status</th>
					<th>Standard</th>
					<th>Rush</th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<#list digital.keySet() as statusId>
				<tr bns-queuerow<#if prepressQueueFilterList?exists && prepressQueueFilterList.get("digital")?exists && prepressQueueFilterList.get("digital").get(statusId)?exists && prepressQueueFilterList.get("digital").get(statusId) == "Y"> class="hidden"</#if>>
					<#assign statusItem = delegator.findOne("StatusItem", {"statusId" : statusId}, true)?if_exists />
					<#assign statusDesc = (statusItem.description)?default("NA") />
					<td><#if statusDesc == "NA">${statusId}<#else>${statusItem.description}</#if></td>
					<td>
						<form id="DIGITAL_N_${statusId}" name="DIGITAL_N_${statusId}" method="POST" action="<@ofbizUrl>/prepressOrderList</@ofbizUrl>">
                            <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
							<input type="hidden" name="queue" value="DIGITAL" />
							<input type="hidden" name="isRush" value="N" />
							<input type="hidden" name="statusId" value="${statusId}" />
							<input type="hidden" name="orderIds" value="<#list digital.get(statusId).get("Standard") as order>${order}<#if order_has_next>,</#if></#list>" />
						</form>
						<a href="#" onclick="$('#DIGITAL_N_${statusId}').submit()"><div class="btn <#if digital.get(statusId).get("Standard")?size gt 0 && (requestParameters.webSiteId?exists && requestParameters.webSiteId == "folders")>btn btn-success<#else>btn-outline btn-primary</#if> btn-sm"><strong>${digital.get(statusId).get("Standard")?size}</strong></div></a>
					</td>
					<td>
						<form id="DIGITAL_Y_${statusId}" name="DIGITAL_Y_${statusId}" method="POST" action="<@ofbizUrl>/prepressOrderList</@ofbizUrl>">
                            <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
							<input type="hidden" name="queue" value="DIGITAL" />
							<input type="hidden" name="isRush" value="Y" />
							<input type="hidden" name="statusId" value="${statusId}" />
							<input type="hidden" name="orderIds" value="<#list digital.get(statusId).get("Rush") as order>${order}<#if order_has_next>,</#if></#list>" />
						</form>
						<a href="#" onclick="$('#DIGITAL_Y_${statusId}').submit()"><div class="btn <#if digital.get(statusId).get("Rush")?size gt 0 && (requestParameters.webSiteId?exists && requestParameters.webSiteId == "folders")>btn btn-success<#else>btn-outline btn-primary</#if> btn-sm"><strong>${digital.get(statusId).get("Rush")?size}</strong></div></a>
					</td>
					<td class="hideQueue"><i bns-hidequeue data-statusid="${statusId}" data-queuename="digital" data-tohide="<#if prepressQueueFilterList?exists && prepressQueueFilterList.get("digital")?exists && prepressQueueFilterList.get("digital").get(statusId)?exists && prepressQueueFilterList.get("digital").get(statusId) == "Y">N<#else>Y</#if>" class="fa fa-eye"></i></td>
				</tr>
				</#list>
				</tbody>
			</table>
		</div>
	</div>
	</#if>

	<div class="col-md-2">
		<form id="plateOrderLookup" action="" method="GET">
			<div>
				<input type="text" class="orderSchedule" name="orderSchedule" placeholder="Scan Barcode" />
			</div>
		</form>
		<div class="panel panel-warning panel-line">
			<div class="panel-heading">
				<div class="panel-title">Proof Ready For Review</div>
			</div>
			<table class="table table-hover">
				<thead>
				<tr>
					<th></th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<#list proofReady.keySet() as key>
				<tr>
					<td>${key}</td>
					<td><a href="<@ofbizUrl>/prepressOrderList</@ofbizUrl>?isRush=<#if key == "Rush">Y<#else>N</#if>&statusId=ART_READY_FOR_REVIEW&orderIds=<#list proofReady.get(key) as order>${order}<#if order_has_next>,</#if></#list>"><div class="btn btn-outline btn-primary btn-sm"><strong>${proofReady.get(key)?size}</strong></div></a></td>
				</tr>
				</#list>
				</tbody>
			</table>
		</div>
		<div class="panel panel-warning panel-line">
			<div class="panel-heading">
				<div class="panel-title">Full Status List</div>
			</div>
			<table class="table table-hover">
				<thead>
				<tr>
					<th></th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<#list fullStatus.keySet() as key>
				<#assign statusItem = delegator.findOne("StatusItem", {"statusId" : key}, true)?if_exists />
				<#assign statusDesc = (statusItem.description)?default("NA") />
				<tr>
					<form id="FULL_${key}" name="FULL_${key}" method="POST" action="<@ofbizUrl>/prepressOrderList</@ofbizUrl>">
                        <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
						<input type="hidden" name="statusId" value="${key}" />
						<input type="hidden" name="orderIds" value="<#list fullStatus.get(key) as order>${order}<#if order_has_next>,</#if></#list>" />
					</form>
					<td><#if statusDesc == "NA">${key}<#else>${statusItem.description}</#if></td>
					<td><a href="#" onclick="$('#FULL_${key}').submit()"><div class="btn btn-outline btn-primary btn-sm"><strong>${fullStatus.get(key)?size}</strong></div></a></td>
				</tr>
				</#list>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
<script src="<@ofbizContentUrl>/html/js/admin/plateLookup.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/js/admin/prepress.js</@ofbizContentUrl>"></script>