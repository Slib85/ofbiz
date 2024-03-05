<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary panel-line" data-collapsed="0">
			<!-- panel head -->
			<div class="panel-heading">
				<div class="panel-title">Persistent Cart List</div>
                <div class="panel-actions">
                    <form class="" name="productEditor" action="<@ofbizUrl>/persistentCartAssist</@ofbizUrl>" method="GET">
                    <div class="input-group-sm">
                        <button type="submit" class="input-search-btn">
                            <i class="icon wb-search" aria-hidden="true"></i>
                        </button>
                        <input type="text" class="form-control" name="id" placeholder="Search for Cart" value="${requestParameters.id?if_exists}">
                    </div>
                    </form>
                </div>
			</div>

			<!-- panel body -->
			<div class="panel-body">
				<table class="table table-bordered responsive table-striped datatable" id="productTable">
					<thead>
					<tr>
						<th>ID</th>
						<th>Content</th>
						<th>Locked</th>
						<th></th>
					</tr>
					</thead>

					<tbody>
					<#if pCarts?has_content>
						<#list pCarts as carts>
						<#assign data = Static["com.envelopes.cart.PersistentCart"].getDataAsObject(delegator, carts.id) />
						<tr>
							<td>${carts.id?if_exists}</td>
							<td>
								<#if data?has_content>
									<#list data as item>
										<div style="float: left; width: 120px;">
											<img style="display: block;" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${item.productId}?wid=50&amp;hei=50&amp;fmt=png-alpha" />
											<span style="display: block;" >SKU: ${item.productId}</span>
											<span style="display: block;" >QTY: ${item.quantity}</span>
										</div>
									</#list>
								</#if>
							</td>
							<td>${carts.locked?if_exists}</td>
							<td style="width: 200px;">
								<a href="//www.envelopes.com/envelopes/control/loadCart?id=${carts.id?if_exists}&amp;isAgent=true&amp;agentUserLoginId=${session.getAttribute("userLogin").userLoginId}" target="cartassist"><button type="button" class="btn btn-primary j-btn-add">Assist</button></a>
								<#if carts.id?exists>
									<button jqs-delete_cart="${carts.id}" style="float: right;" type="button" class="btn btn-danger">Delete</button>
								</#if>
							</td>
						</tr>
						</#list>
					</#if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<script>
	$("[jqs-delete_cart]").on("click", function() {
		var element = $(this);
		var cartId = element.attr("jqs-delete_cart");
		var c = confirm("Are you sure you wish to delete cart ID: " + cartId);

		if (c == true) {
			$.ajax({
				type: "POST",
				url: "/admin/control/deletePersistentCart",
				dataType: "json",
				data: {
					"cartId": cartId
				}
			}).done(function(response) {
				if(response.success) {
					element.closest("tr").remove();
				} else {
					alert(response.responseMessage);
				}
			})
		}
	});
</script>