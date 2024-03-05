<style>
	.table {
        font-size: 12px;
	}
	.table td {
		white-space: nowrap;
	}
    #orderListFixedHeader a {
        text-decoration: none;
    }
	.panel {
		overflow: auto;
	}
</style>

<div class="panel">
    <div class="panel-body">
        <div class="table">
            <table class="table table-hover table-striped" id="orderListFixedHeader">
                <thead>
					<tr>
						<th></th>
						<th>ORDER ID</th>
						<th>ORDER ITEM</th>
						<th>STATUS</th>
						<th>SIZE</th>
						<th>RUSH</th>
						<th>QUANTITY</th>
						<th>INK FRONT</th>
						<th>INK BACK</th>
                        <th>DUE DATE</th>
						<th>SCENE7</th>
						<th>UPLOAD</th>
						<th>RE-USE</th>
						<th>PRINTED</th>
						<th>PARTS</th>
						<th>CIMPRESS ORDER</th>
						<th>NETSUITE</th>
						<th>OUTSOURCEABLE</th>
						<th>WORKER DATE</th>
						<th>TRACKING</th>
						<th>SHIP STATE</th>
						<th>ORDER DATE</th>
                        <th>APPROVAL DATE</th>
						<th>ASSIGNED TO</th>
					</tr>
                </thead>
                <tfoot>
					<tr class="replace-inputs">
						<th class="jqs-inputIgnore"></th>
						<th>ORDER ID</th>
						<th>ORDER ITEM</th>
						<th>STATUS</th>
						<th>SIZE</th>
						<th>RUSH</th>
						<th>QUANTITY</th>
						<th>INK FRONT</th>
						<th>INK BACK</th>
                        <th>DUE DATE</th>
						<th>SCENE7</th>
						<th>UPLOAD</th>
						<th>RE-USE</th>
						<th>PRINTED</th>
						<th>PARTS</th>
						<th>CIMPRESS ORDER</th>
						<th>NETSUITE</th>
						<th>OUTSOURCEABLE</th>
						<th>WORKER DATE</th>
						<th>TRACKING</th>
						<th>SHIP STATE</th>
						<th>ORDER DATE</th>
                        <th>APPROVAL DATE</th>
						<th>ASSIGNED TO</th>
					</tr>
                </tfoot>
			<#assign orderAndSeq = "" />
			<#list orderList?reverse as order>
				<#if orderAndSeq?has_content>
					<#assign orderAndSeq = orderAndSeq + "," + order.orderId + "_" + Static["com.envelopes.util.EnvUtil"].removeChar("0", order.orderItemSeqId, true, false, false) />
				<#else>
					<#assign orderAndSeq = order.orderId + "_" + Static["com.envelopes.util.EnvUtil"].removeChar("0", order.orderItemSeqId, true, false, false) />
				</#if>
			</#list>
			<#assign count = 0>
			<#list orderList as order>
				<#assign orderItemSeq = Static["com.envelopes.util.EnvUtil"].removeChar("0", order.orderItemSeqId, true, false, false) />
                <tr class="<#if (count % 2) == 0>odd<#else>even</#if>" id="${order.orderId}_${order.orderItemSeqId}">
					<#assign statusItem = delegator.findOne("StatusItem", {"statusId" : order.statusId}, true)?if_exists />
                    <td><input name="updateStatus" type="checkbox" value="${order.orderId}|${order.orderItemSeqId}|${order.statusId}" /></td>
                    <td>
                        <form id="${order.orderId}${order.orderItemSeqId}" name="${order.orderId}${order.orderItemSeqId}" method="POST" action="<@ofbizUrl>/viewOrder?orderId=${order.orderId}&seqNum=${orderItemSeq}#item-${orderItemSeq}</@ofbizUrl>">
                            <input type="hidden" name="orderIds" value="${orderAndSeq?default("")}" />
                        </form>
                        <a class="jqs-prepressmidclick" onclick="return false;" href="<@ofbizUrl>/viewOrder?orderId=${order.orderId}&seqNum=${orderItemSeq}#item-${orderItemSeq}</@ofbizUrl>">${order.orderId}</a>
                    </td>
                    <td>${order.orderItemSeqId}</td>
                    <td>${statusItem.description}</td>
                    <td>${order.itemDescription?if_exists}</td>
                    <td><span class="tag<#if order.isRushProduction?has_content && order.isRushProduction == "Y"> tag-success">YES<#else> tag-default">NO</#if></span></td>
                    <td>${order.quantity?default(0)}</td>
                    <td>${order.colorsFront?default(0)}</td>
                    <td>${order.colorsBack?default(0)}</td>
                    <td><#if order.dueDate?has_content>${order.dueDate?string["MM/dd/yyyy"]}<#else></#if></td>
                    <td><span class="tag<#if order.SCENE7?has_content> tag-success">YES<#else> tag-default">NO</#if></span></td>
                    <td><span class="tag<#if order.artworkSource?has_content && order.artworkSource == "ART_UPLOADED"> tag-success">YES<#else> tag-default">NO</#if></span></td>
                    <td><span class="tag<#if order.artworkSource?has_content && order.artworkSource == "ART_REUSED"> tag-success">YES<#else> tag-default">NO</#if></span></td>
                    <td><span class="tag<#if order.totalPrintedItemsInOrder?default(0) == order.totalItemsInOrder?default(0)> tag-success<#else> tag-warning</#if>">${order.totalPrintedItemsInOrder?default(0)} / ${order.totalItemsInOrder?default(0)}</span></td>
                    <td><span class="tag<#if order.totalProofApprovedItemsInOrder?default(0) == order.totalItemsInOrder?default(0)> tag-success<#else> tag-danger</#if>">${order.totalProofApprovedItemsInOrder?default(0)} / ${order.totalItemsInOrder?default(0)}</span></td>
                    <td><span class="tag<#if order.cimpressOrder?has_content && order.cimpressOrder == "Yes"> tag-success">YES<#else> tag-default">NO</#if></span></td>
                    <td><span class="tag<#if order.pendingChange?has_content && order.pendingChange == "Y"> tag-success">YES<#else> tag-default">NO</#if></span></td>
                    <td><span class="tag<#if order.outsourceable?has_content && order.outsourceable == "Y"> tag-success">YES<#else> tag-default">NO</#if></span></td>
					<td><#if order.workerDate?has_content>${order.workerDate?string["MM/dd/yyyy hh:mm a"]}</#if></td>
                    <td>${order.trackingNumber?default("")}</td>
                    <td>${order.shippingState?if_exists}</td>
                    <td><#if order.orderDate?has_content>${order.orderDate?string["MM/dd/yyyy hh:mm a"]}</#if></td>
                    <td><#if order.approvalDate?has_content>${order.approvalDate?string["MM/dd/yyyy hh:mm a"]}</#if></td>
                    <td>${order.assignedToUserLogin?default("")}</td>
                </tr>
				<#assign count = count+1 />
			</#list>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="row">
	<form name="bulkStatusUpdate" class="form-inline">
		<div class="form-group">
			<select name="statusList" class="form-control" placeholder="Select Status">
				<option></option>
				<option value="ITEM_CREATED">Created</option>
				<option value="ITEM_APPROVED">Approved</option>
				<option value="ITEM_CANCELLED">Cancelled</option>
				<option value="ITEM_BACKORDERED">Backordered</option>
				<option value="ITEM_READY_FOR_SHIP">Ready to Ship</option>
			<#assign printStatuses = delegator.findByAnd("StatusItem", Static["org.apache.ofbiz.base.util.UtilMisc"].toMap("statusTypeId", "AE_ARTWORK_STTS"), Static["org.apache.ofbiz.base.util.UtilMisc"].toList("sequenceId ASC"), true) />
			<#list printStatuses as printStatus>
				<option value="${printStatus.statusId}">${printStatus.description?if_exists}</option>
			</#list>
			</select>
		</div>
        <div class="form-group">
            <button type="submit" class="btn btn-success">Go</button>
        </div>
	</form>
</div>

<script src="<@ofbizContentUrl>/html/js/admin/prepressOrderList.js</@ofbizContentUrl>?ts=6"></script>