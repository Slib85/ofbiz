<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">

<h4 class="example-title">Date Range</h4>
<div class="example">
    <form name="orderSearch" method="GET" action="<@ofbizUrl>/pressMan</@ofbizUrl>">
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

<div class="panel">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-bordered datatable" id="orderTable">
				<thead>
				<tr>
					<th>OFFSET</th>
					<th>QUANTITY</th>
					<th># OF JOBS</th>
					<th>INK COLORS</th>
					<th>AVERAGE TIME/DAY</th>
				</tr>
				</thead>
				<tbody>
				<#assign totalQty = 0 />
				<#assign totalOrderItems  = 0 />
				<#assign totalInkColors = 0 />
				<#list stats as pressMan>
				<#if !pressMan.pressMan?starts_with("justin") && !pressMan.pressMan?starts_with("satesh") && !pressMan.pressMan?starts_with("shawn") && !pressMan.pressMan?starts_with("rj") && !pressMan.pressMan?starts_with("dan") && !pressMan.pressMan?starts_with("cirilo")>
				<tr class="<#if (pressMan_index % 2) == 0>odd<#else>even</#if>">
					<#assign totalQty = totalQty + pressMan.quantity />
					<#assign totalOrderItems = totalOrderItems + pressMan.orderItems />
					<#assign totalInkColors = totalInkColors + pressMan.inkColors />
					<td>${pressMan.pressMan}</td>
					<td>${pressMan.quantity}</td>
					<td>${pressMan.orderItems}</td>
					<td>${pressMan.inkColors}</td>
					<td>${pressMan.totalTime}</td>
				</tr>
				</#if>
				</#list>
				<tr>
					<td>Total</td>
					<td>${totalQty}</td>
					<td>${totalOrderItems}</td>
					<td>${totalInkColors}</td>
					<td></td>
				</tr>
				</tbody>
			</table>
        </div>
    </div>
</div>

<div class="panel">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-bordered datatable" id="orderTable2">
				<thead>
				<tr>
					<th>DIGITAL</th>
					<th>QUANTITY</th>
					<th># OF JOBS</th>
					<th>INK COLORS</th>
					<th>AVERAGE TIME/DAY</th>
				</tr>
				</thead>
				<tbody>
				<#assign totalQty = 0 />
				<#assign totalOrderItems  = 0 />
				<#assign totalInkColors = 0 />
				<#list stats as pressMan>
				<#if pressMan.pressMan?starts_with("justin") || pressMan.pressMan?starts_with("satesh") ||  pressMan.pressMan?starts_with("shawn") || pressMan.pressMan?starts_with("rj") || pressMan.pressMan?starts_with("dan") || pressMan.pressMan?starts_with("cirilo")>
				<tr class="<#if (pressMan_index % 2) == 0>odd<#else>even</#if>">
					<#assign totalQty = totalQty + pressMan.quantity />
					<#assign totalOrderItems = totalOrderItems + pressMan.orderItems />
					<#assign totalInkColors = totalInkColors + pressMan.inkColors />
					<td>${pressMan.pressMan}</td>
					<td>${pressMan.quantity}</td>
					<td>${pressMan.orderItems}</td>
					<td>${pressMan.inkColors}</td>
					<td>${pressMan.totalTime}</td>
				</tr>
				</#if>
				</#list>
				<tr>
					<td>Total</td>
					<td>${totalQty}</td>
					<td>${totalOrderItems}</td>
					<td>${totalInkColors}</td>
					<td></td>
				</tr>
				</tbody>
			</table>
        </div>
    </div>
</div>

<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
<script src="<@ofbizContentUrl>/html/js/admin/pressMan.js</@ofbizContentUrl>"></script>