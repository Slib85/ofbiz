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
<div class="panel">
	<div class="panel-body">
		<div class="table-responsive">
			<table class="table table-hover dataTable table-striped" id="promoTable">
				<thead>
					<tr>
						<th>PROMO ID</th>
						<th>PROMO NAME</th>
						<th>PROMO TEXT</th>
						<th>REQUIRE CODE</th>
						<th>CREATED</th>
						<th></th>
					</tr>
				</thead>
				<tfoot>
					<tr class="replace-inputs">
						<th>PROMO ID</th>
						<th>PROMO NAME</th>
						<th>PROMO TEXT</th>
						<th>REQUIRE CODE</th>
						<th>CREATED</th>
					</tr>
				</tfoot>
				<tbody>
					<#assign count = 0>
					<#list promos as promo>
						<tr class="<#if (count % 2) == 0>odd<#else>even</#if>">
							<td><a href="<@ofbizUrl>/editPromo?productPromoId=${promo.productPromoId}</@ofbizUrl>">${promo.productPromoId}</a></td>
							<td>${promo.promoName?if_exists}</td>
							<td>${promo.promoText?if_exists}</td>
							<td>${promo.requireCode?if_exists}</td>
							<td>${promo.createdStamp?string.short}</td>
							<td>
								<a href="#" class="btn btn-default btn-xs">
									Edit
								</a>
							</td>
						</tr>
						<#assign count = count+1 />
					</#list>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script src="<@ofbizContentUrl>/html/js/admin/promoList.js</@ofbizContentUrl>"></script>