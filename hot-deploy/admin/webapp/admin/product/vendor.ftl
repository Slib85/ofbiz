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

<div class="panel panel-primary panel-line" data-collapsed="0">
    <!-- panel head -->
    <div class="panel-heading">
        <div class="panel-title">Add/Edit Vendor</div>
    </div>

    <!-- panel body -->
    <div class="panel-body">
		<form id="addUpdateVendor" method="POST" action="<@ofbizUrl>/addUpdateVendor</@ofbizUrl>" class="form-wizard" enctype="application/x-www-form-urlencoded">
			<input type="hidden" name="vendorTypeId" value="V_OUTSOURCE" />
			<div class="row">
				<div class="col-md-10">
					<div class="row">
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">ID</label>
								<input class="form-control" id="partyId" name="partyId" placeholder="ID" value="${(vendor.partyId)?if_exists}" <#if (vendor.partyId)?has_content>readonly</#if> />
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">Secret</label>
								<input class="form-control" name="sharedSecret" placeholder="Secret" value="${(vendor.sharedSecret)?if_exists}" />
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">Endpoint</label>
								<input class="form-control" name="cxmlEndpoint" placeholder="CXML Endpoint" value="${(vendor.cxmlEndpoint)?if_exists}" />
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">Email</label>
								<input class="form-control" name="email" placeholder="Email" value="${(vendor.email)?if_exists}" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">DUNS #</label>
								<input class="form-control" name="duns" placeholder="DUNS #" value="${(vendor.duns)?if_exists}" />
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">Netsuite ID</label>
								<input class="form-control" name="netsuiteId" placeholder="Netsuite ID" value="${(vendor.netsuiteId)?if_exists}" />
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">Black Ink Rule</label>
								<select name="hasBlackInkRule" class="form-control" data-allow-clear="true" data-placeholder="Black Ink Rule">
									<option value="N">No</option>
									<option value="Y" <#if (vendor.hasBlackInkRule)?exists && vendor.hasBlackInkRule == "Y">selected</#if>>Yes</option>
								</select>
							</div>
						</div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">UPS Account #</label>
                                <input class="form-control" name="upsAccount" placeholder="UPS Account #" value="${(vendor.upsAccount)?if_exists}" />
                            </div>
                        </div>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<label class="col-sm-12 control-label">Supported States</label>
						<select multiple="multiple" name="states" class="form-control" >
						${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
						</select>
					</div>
				</div>
			</div>
			<div class="row pull-xs-right margin-right-15">
				<button id="submitVendor" type="submit" class="btn btn-success"><i class="icon wb-check" aria-hidden="true"></i> I SAVE</button>
			</div>
		</form>
	</div>
</div>

<div class="row margin-top-15">
	<div class="col-md-12">
		<div class="panel panel-primary panel-line" data-collapsed="0">
			<!-- panel head -->
			<div class="panel-heading">
				<div class="panel-title">Vendor List</div>
			</div>

			<!-- panel body -->
			<div class="panel-body">
				<table class="table table-hover" id="productTable">
					<thead>
					<tr>
						<th>Vendor ID</th>
						<th>Secret</th>
						<th>Endpoint</th>
						<th>Email</th>
						<th>DUNS</th>
						<th>Black Ink Rule</th>
						<th></th>
					</tr>
					</thead>

					<tbody>
					<#if vendors?has_content>
						<#list vendors as vendor>
						<tr>
							<td>${vendor.partyId?if_exists}</td>
							<td>${vendor.sharedSecret?if_exists}</td>
							<td>${vendor.cxmlEndpoint?if_exists}</td>
							<td>${vendor.email?if_exists}</td>
							<td>${vendor.duns?if_exists}</td>
							<td>${vendor.hasBlackInkRule?if_exists}</td>
							<td>
								<a href="<@ofbizUrl>/vendors</@ofbizUrl>?id=${vendor.partyId?if_exists}" class="btn btn-default btn-xs">
									Edit
								</a>
								<a href="<@ofbizUrl>/productVendor</@ofbizUrl>?partyId=${vendor.partyId?if_exists}" class="btn btn-success btn-xs">
									Products
								</a>
								<a href="<@ofbizUrl>/vendorOrderList</@ofbizUrl>?partyId=${vendor.partyId?if_exists}" class="btn btn-warning btn-xs">
									Orders
								</a>
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
	jQuery(document).ready(function($) {
		$('#addUpdateVendor').on('submit', function(e) {
			e.preventDefault();

			//ajax call
			$.ajax({
				type: 'POST',
				url: '/admin/control/addUpdateVendor',
				dataType: 'json',
				data: $(this).serialize()
			}).done(function( response ) {
				if(response.success) {
					window.location = '/admin/control/vendors?id=' + $('#partyId').val().toUpperCase();
				} else {
					if(typeof response.error != 'undefined') {
						alert(response.error);
					} else {
						alert("There was an error trying to add a new quantity.");
					}
				}
			}).error(function( response ) {
				//todo error
			});
		});
	});
</script>