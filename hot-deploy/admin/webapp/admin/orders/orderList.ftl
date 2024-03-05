<link rel="stylesheet" href="/html/css/admin/orders/orderList.css" />
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">
<div class="row">
	<div class="col-xl-3 col-md-6 col-xs-12 info-panel">
		<div class="card card-shadow">
			<div class="card-block bg-white p-20">
				<button type="button" class="btn btn-floating btn-sm btn-warning">
					<i class="icon wb-eye"></i>
				</button>
				<span class="m-l-15 font-weight-400">FAILED SETTLEMENTS</span>
				<div class="content-text text-xs-center m-b-0">
					<span class="font-size-40 font-weight-100">${orderQueue.nonCapturedOrders?size}</span>
					<p class="blue-grey-400 font-weight-100 m-0"><#if orderQueue.nonCapturedOrders?has_content><a href="<@ofbizUrl>/orderList</@ofbizUrl>?orderId=<#list orderQueue.nonCapturedOrders as orders>${orders}<#if orders_has_next>,</#if></#list>">View List</a><#else>No Orders</#if></p>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xl-3 col-md-6 col-xs-12 info-panel">
		<div class="card card-shadow">
			<div class="card-block bg-white p-20">
				<button type="button" class="btn btn-floating btn-sm btn-danger">
					<i class="icon fa-cloud-upload"></i>
				</button>
				<span class="m-l-15 font-weight-400">ORDERS WAITING FOR EXPORT</span>
				<div class="content-text text-xs-center m-b-0">
					<span class="font-size-40 font-weight-100">${orderQueue.ordersToExport?size} / ${orderQueue.internationalOrders?size}</span>
					<p class="blue-grey-400 font-weight-100 m-0"><#if orderQueue.ordersToExport?has_content><a href="<@ofbizUrl>/orderList</@ofbizUrl>?orderId=<#list orderQueue.ordersToExport as orders>${orders}<#if orders_has_next>,</#if></#list>">View Exportable List</a><#else>No Orders</#if> / <#if orderQueue.internationalOrders?has_content><a href="<@ofbizUrl>/orderList</@ofbizUrl>?orderId=<#list orderQueue.internationalOrders as orders>${orders}<#if orders_has_next>,</#if></#list>">View International List</a><#else>No Orders</#if></p>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xl-3 col-md-6 col-xs-12 info-panel">
		<div class="card card-shadow">
			<div class="card-block bg-white p-20">
				<button type="button" class="btn btn-floating btn-sm btn-success">
					<i class="icon wb-payment"></i>
				</button>
				<span class="m-l-15 font-weight-400">DECLINED & PENDING ORDERS</span>
				<div class="content-text text-xs-center m-b-0">
					<span class="font-size-40 font-weight-100">${orderQueue.declinedOrders?size} / ${orderQueue.pendingOrders?size}</span>
					<p class="blue-grey-400 font-weight-100 m-0"><#if orderQueue.declinedOrders?has_content><a href="<@ofbizUrl>/orderList</@ofbizUrl>?orderId=<#list orderQueue.declinedOrders as orders>${orders}<#if orders_has_next>,</#if></#list>">View Declined List</a><#else>No Orders</#if> / <#if orderQueue.pendingOrders?has_content><a href="<@ofbizUrl>/orderList</@ofbizUrl>?orderId=<#list orderQueue.pendingOrders as orders>${orders}<#if orders_has_next>,</#if></#list>">View Pending List</a><#else>No Orders</#if></p>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xl-3 col-md-6 col-xs-12 info-panel">
		<div class="card card-shadow">
			<div class="card-block bg-white p-20">
				<button type="button" class="btn btn-floating btn-sm btn-primary">
					<i class="icon fa-money"></i>
				</button>
				<span class="m-l-15 font-weight-400">CHECK ORDERS</span>
				<div class="content-text text-xs-center m-b-0">
					<span class="font-size-40 font-weight-100">${orderQueue.checkOrders?size}</span>
					<p class="blue-grey-400 font-weight-100 m-0"><#if orderQueue.checkOrders?has_content><a href="<@ofbizUrl>/orderList</@ofbizUrl>?orderId=<#list orderQueue.checkOrders as orders>${orders}<#if orders_has_next>,</#if></#list>">View List</a><#else>No Orders</#if></p>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="panel panel-primary panel-line">
    <div class="panel-body">
		<form name="orderSearchWebSiteId" method="GET" action="<@ofbizUrl>/orderList</@ofbizUrl>">
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label">Search Term</label>
						<select name="webSiteId" class="form-control" data-allow-clear="true" data-placeholder="Website ID">
							<option value="">Website ID</option>
							<option value="ae" <#if requestParameters.webSiteId?has_content && requestParameters.webSiteId == "ae">selected</#if>>ActionEnvelope.com</option>
							<option value="envelopes" <#if requestParameters.webSiteId?has_content && requestParameters.webSiteId == "envelopes">selected</#if>>Envelopes.com</option>
							<option value="folders" <#if requestParameters.webSiteId?has_content && requestParameters.webSiteId == "folders">selected</#if>>Folders.com</option>
							<option value="bags" <#if requestParameters.webSiteId?has_content && requestParameters.webSiteId == "bags">selected</#if>>Bags.com</option>
						</select>
					</div>
				</div>
			</div>
		</form>
		<form name="orderSearchDateTime" method="GET" action="<@ofbizUrl>/orderList</@ofbizUrl>">
			<input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?default('')}" />
			<div class="input-daterange" data-plugin="datepicker">
				<div class="input-group">
					<span class="input-group-addon"><i class="icon wb-calendar" aria-hidden="true"></i></span>
					<input type="text" class="form-control" name="start" value="<#if requestParameters.start?has_content>${requestParameters.start}<#else>${nowTimestamp?string("yyyy-MM-dd")}</#if>"/>
				</div>
				<div class="input-group">
					<span class="input-group-addon">to</span>
					<input type="text" class="form-control" name="end" value="<#if requestParameters.end?has_content>${requestParameters.end}<#else>${nowTimestamp?string("yyyy-MM-dd")}</#if>" />
					<span class="input-group-btn">
						<button type="submit" class="btn btn-primary"><i class="icon wb-search" aria-hidden="true"></i></button>
					</span>
				</div>
			</div>
		</form>
	</div>
</div>

<div class="panel">
	<div class="panel-body">
		<div class="table-responsive">
			<table class="table table-hover dataTable table-striped" id="orderListFixedHeader">
				<thead>
					<tr>
						<th>ORDER ID</th>
						<th>STATUS</th>
                        <th>COMPANY NAME</th>
						<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")><th>PRIORITY</th></#if>
						<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")><th>SALES REP</th></#if>
						<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")><th>REORDER</th></#if>
						<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")><th>QUOTE ID</th></#if>
                        <th>PRINTED</th>
						<th><#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")>DOOGMA<#else>SCENE7</#if></th>
						<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")><th>ADDRESSING</th></#if>
						<#--<th>SHIPPING METHOD</th>-->
						<th>NAME</th>
						<th>ITEMS</th>
						<th>TOTAL</th>
						<th>DATE</th>
						<th>EMAIL</th>
						<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")>
						<th>PAYMENT STATUS</th>
						<th>PRODUCT ID</th>
						</#if>
					</tr>
				</thead>
				<tfoot>
					<tr class="replace-inputs">
						<th>ORDER ID</th>
						<th>STATUS</th>
                        <th>COMPANY NAME</th>
						<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")><th>PRIORITY</th></#if>
						<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")><th>SALES REP</th></#if>
						<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")><th>REORDER</th></#if>
						<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")><th>QUOTE ID</th></#if>
						<th>PRINTED</th>
						<th><#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")>DOOGMA<#else>SCENE7</#if></th>
						<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")><th>ADDRESSING</th></#if>
						<#--<th>SHIPPING METHOD</th>-->
						<th>NAME</th>
						<th>ITEMS</th>
						<th>TOTAL</th>
						<th>DATE</th>
                        <th>EMAIL</th>
						<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")>
						<th>PAYMENT STATUS</th>
						<th>PRODUCT ID</th>
						</#if>
					</tr>
				</tfoot>
				<tbody>
					<#assign count = 0>
					<#list orderList as order>
						<tr class="${order.orderId} <#if (count % 2) == 0>odd<#else>even</#if>">
							<td><a href="<@ofbizUrl>/viewOrder?orderId=${order.orderId}</@ofbizUrl>">${order.orderId}</a></td>
							<td><@statusDescription desc=order.statusId export=order.exportedDate?default("") orderId=order.orderId /></td>
							<td>${order.billingCompany?if_exists}</td>
							<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")><td>${order.priority?default('Standard')}</td></#if>
							<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")><td>${order.salesRep?default('')}</td></#if>
							<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")><td><span class="tag<#if order.isReorder?has_content && order.isReorder?c == "true"> tag-success">YES<#else> tag-default">NO</#if></span></td></#if>
							<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")><td>${order.quoteId?default('')}</td></#if>
							<td><#if order.isPrinted?c == "true">Printed<#else>Plain</#if></td>
							<td><span class="tag<#if order.isScene7?has_content && order.isScene7?c == "true"> tag-success">YES<#else> tag-default">NO</#if></span></td>
							<#if requestParameters.webSiteId?has_content && !requestParameters.webSiteId?contains("folders")><td><span class="tag<#if order.addressing?has_content && order.addressing?c == "true"> tag-success">YES<#else> tag-default">NO</#if></span></td></#if>
							<#--<td>${order.shippingMethod}</td>-->
							<td>${order.billingName}</td>
							<td>${order.numberOfItems}</td>
							<td><@ofbizCurrency amount=order.orderTotal isoCode=currencyUomId/></td>
							<td>${order.orderDate?string.short}</td>
							<td>${order.email}</td>
							<#if requestParameters.webSiteId?has_content && requestParameters.webSiteId?contains("folders")>
							<td><#if order.orderId?matches("^F\\d+", "gi")?c == "true">${order.paymentStatusDescription?if_exists}<#else>N/A</#if></td>
							<td><#if order.orderId?matches("^F\\d+", "gi")?c == "true">${order.productId?if_exists}<#else>N/A</#if></td>
							</#if>
						</tr>
						<#assign count = count+1 />
					</#list>
				</tbody>
			</table>
		</div>
	</div>
</div>

<#macro statusDescription desc export="null" orderId="null">
	<button type="button" data-oid="<#if orderId?has_content>${orderId}</#if>" class="exportOrder btn btn-<#if export?has_content>success<#else>default</#if> btn-xs" style="font-size:10px;">
		<i class="site-menu-icon fa-cloud-upload"></i>
	</button>
	<#if desc == "ORDER_CREATED">
		<button type="button" class="btn btn-primary btn-icon btn-xs" style="font-size:10px;">
			CREATED
			<i class="fa-check"></i>
		</button>
	<#elseif desc == "ORDER_APPROVED">
		<button type="button" class="btn btn-warning btn-icon btn-xs" style="font-size:10px;">
			APPROVED
			<i class="fa-warning"></i>
		</button>
	<#elseif desc == "ORDER_PENDING">
		<button type="button" class="btn btn-warning btn-icon btn-xs" style="font-size:10px;">
			PENDING
			<i class="fa-warning"></i>
		</button>
	<#elseif desc == "ORDER_PARTIALLY_FULFILLED">
		<button type="button" class="btn btn-success btn-icon btn-xs" style="font-size:10px;">
			PARTIALLY FULFILLED
			<i class="fa-truck"></i>
		</button>
	<#elseif desc == "ORDER_SHIPPED">
		<button type="button" class="btn btn-success btn-icon btn-xs" style="font-size:10px;">
			SHIPPED
			<i class="fa-truck"></i>
		</button>
	<#elseif desc == "ORDER_CANCELLED">
		<button type="button" class="btn btn-danger btn-icon btn-xs" style="font-size:10px;">
			CANCELED
			<i class="fa-close"></i>
		</button>
	</#if>
</#macro>

<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
<script src="<@ofbizContentUrl>/html/js/admin/orderList.js</@ofbizContentUrl>?ts=1"></script>