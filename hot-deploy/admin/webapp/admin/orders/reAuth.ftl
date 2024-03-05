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
<div class="row row-spacer">
	<div class="col-md-4"></div>
	<div class="col-md-4">

	</div>
	<div class="col-md-4"></div>
</div>


<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary panel-line" data-collapsed="0">
			<div class="panel-heading">
				<div class="panel-title">Visa ReAuth List</div>
                <div class="panel-actions">
                    <div class="form-group">
                        <div class="input-group input-group-icon">
                        <form name="reAuthForm" action="<@ofbizUrl>/reAuth</@ofbizUrl>" method="GET">
							<select class="form-control" name="goBack" onchange="this.form.submit()">
								<option value="0">Go Back Extra Days</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
							</select>
						</form>
                        </div>
                    </div>
                </div>
			</div>

			<div class="panel-body">
				<table class="table table-hover">
					<thead>
					<tr>
						<th>Order ID</th>
						<th>Plain/Printed</th>
						<th>Netsuite Link</th>
						<th>Re-Auth Day</th>
					</tr>
					</thead>
					<tbody>
						<#if orderList?has_content>
							<#list orderList?sort_by("statusDatetime") as orders>
								<tr>
									<td><a href="<@ofbizUrl>viewOrder?orderId=${orders.orderId}</@ofbizUrl>" >${orders.orderId}</a></td>
									<td>${orders.plainOrPrinted}</td>
									<td><a target="_blank" href="https://system.na2.netsuite.com/app/common/search/ubersearchresults.nl?quicksearch=T&searchtype=Uber&frame=be&Uber_NAMEtype=KEYWORDSTARTSWITH&Uber_NAME=${orders.orderId}">Netsuite</a></td>
									<td>${orders.statusDatetime?string("EEEE")}</td>
								</tr>
							</#list>
						</#if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary panel-line" data-collapsed="0">
			<div class="panel-heading">
				<div class="panel-title">Visa ReAuth List (14 Days)</div>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
					<tr>
						<th>Order ID</th>
						<th>Plain/Printed</th>
						<th>Netsuite Link</th>
						<th>Re-Auth Day</th>
					</tr>
					</thead>
					<tbody>
					<#if orderList2?has_content>
						<#list orderList2?sort_by("statusDatetime") as orders>
						<tr>
							<td><a href="<@ofbizUrl>orderview?orderId=${orders.orderId}</@ofbizUrl>" >${orders.orderId}</a></td>
							<td>${orders.plainOrPrinted}</td>
							<td><a target="_blank" href="https://system.netsuite.com/app/common/search/ubersearchresults.nl?quicksearch=T&searchtype=Uber&frame=be&Uber_NAMEtype=KEYWORDSTARTSWITH&Uber_NAME=${orders.orderId}">Netsuite</a></td>
							<td>${orders.statusDatetime?string("EEEE")}</td>
						</tr>
						</#list>
					</#if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>