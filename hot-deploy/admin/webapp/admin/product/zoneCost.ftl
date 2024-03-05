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

<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary panel-line" data-collapsed="0">
			<!-- panel head -->
			<!-- panel body -->
			<div class="panel-body">
				<table class="table table-bordered responsive datatable" id="rateTable">
					<thead>
					<tr>
						<th>Shipment Method</th>
						<th>Zone</th>
						<th>Weight</th>
						<th>Average Cost</th>
						<th></th>
					</tr>
					</thead>
					<tfoot>
                    <tr class="replace-inputs">
                        <th>Shipment Method</th>
                        <th>Zone</th>
                        <th>Weight</th>
                        <th>Average Cost</th>
                        <th></th>
                    </tr>
					</tfoot>

					<tbody>
					<#if zones?has_content>
						<#list zones as zone>
						<tr>
							<td class="shipmentMethodTypeId">${zone.shipmentMethodTypeId?if_exists}</td>
							<td class="zone">${zone.zone?if_exists}</td>
							<td class="weight">${zone.weight?if_exists}</td>
							<td class="averageCost">${zone.averageCost?if_exists}</td>
							<td>
								<a href="#" class="btn btn-default btn-sm btn-icon icon-left editZone">
									<i class="entypo-pencil"></i>
									Edit
								</a>
							</td>
						</tr>
						</#list>
					</#if>
					</tbody>
				</table>
				<div class="row">
					<div class="col-md-12">
						<button class="btn btn-orange btn-icon btn-sm icon-right addZone" data-toggle="modal" data-target="#addZone">
							ADD NEW ZONE
							<i class="entypo-check"></i>
						</button>
						<div class="modal fade" id="addZone" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="myModalLabel">Add New Zone</h4>
									</div>
									<div class="modal-body row">
										<div class="col-xs-12">
											<div class="form-group has-feedback">
												<div class="envRow">
													<div class="envCol col50">
														Shipment Method Type
														<select id="shipmentMethodTypeId" class="form-control" name="shipmentMethodTypeId">
															<option value="GROUND">Ground</option>
															<option value="NEXT_DAY_AIR">Next Day Air</option>
															<option value="NEXT_DAY_SAVER">Next Day Air Saver</option>
															<option value="SECOND_DAY_AIR">Second Day Air</option>
															<option value="SECOND_DAY_AIR_AM">Second Day Air AM</option>
															<option value="THREE_DAY_SELECT">3 Day Select</option>
															<option value="WORLDWIDE_EXPR">Worldwide Express</option>
															<option value="WORLDWIDE_EXPTD">Worldwide Expedited</option>
														</select>
													</div>
													<div class="envCol col50">
														ZONE
														<input type="text" class="form-control" id="zone" name="zone" placeholder="Zone" value="" />
													</div>
												</div>
												<div class="envRow margin-top-xxs">
													<div class="envCol col50">
														WEIGHT
														<input type="text" class="form-control" id="weight" name="weight" placeholder="Weight" value="" />
													</div>
													<div class="envCol col50">
														COST
														<input type="text" class="form-control" id="averageCost" name="averageCost" placeholder="Cost" value="" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-primary j-btn-add">Add</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	jQuery(document).ready(function($) {
        var offsetTop = 0;

        if ($('.site-navbar').length > 0) {
            offsetTop = $('.site-navbar').eq(0).innerHeight();
        }

        var table = $("#rateTable").DataTable({
            iDisplayLength: 50,
            responsive: true,
            fixedHeader: {
                header: true,
                headerOffset: offsetTop
            },
            bPaginate: true,
            aaSorting: [],
            initComplete: function() {
                $('#productTable .replace-inputs > th').each(function(index) {
                    var title = $(this).text();
                    $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
                });

                $("#productTable .replace-inputs input").on('keyup change', function () {
                    table
                            .column($(this).parent().index() + ':visible' )
                            .search(this.value)
                            .draw();
                });
            }
        });
	});

	$('.editZone').on('click', function(e) {
		var self = $(this);
		var shipmentMethodTypeId = self.closest('td').siblings('.shipmentMethodTypeId').html();
		var zone = self.closest('td').siblings('.zone').html();
		var weight = self.closest('td').siblings('.weight').html();
		var averageCost = self.closest('td').siblings('.averageCost').html();

		$('#addZone').find('#shipmentMethodTypeId').val(shipmentMethodTypeId);
		$('#addZone').find('#zone').val(zone);
		$('#addZone').find('#weight').val(weight);
		$('#addZone').find('#averageCost').val(averageCost);

		$('#addZone').modal({show: true});
	});

	$('#addZone .j-btn-add').on('click', function(e) {
		var self = this;
		var modal = $(this).closest('.modal');
		var shipmentMethodTypeId = modal.find('#shipmentMethodTypeId').val();
		var zone = modal.find('#zone').val();
		var weight = modal.find('#weight').val();
		var averageCost = modal.find('#averageCost').val();

		var zoneData = {
			"shipmentMethodTypeId": shipmentMethodTypeId,
			"zone": zone,
			"weight": weight,
			"averageCost": averageCost
		};

		//ajax call
		$.ajax({
			type: 'POST',
			url: '/admin/control/addZone',
			dataType: 'json',
			data: zoneData
		}).done(function( response ) {
			if(response.success) {
				window.location = '/admin/control/outsourceZones';
			} else {
				if(typeof response.error != 'undefined') {
					alert(response.error);
				} else {
					alert("There was an error trying to add a new zone.");
				}
			}
		}).error(function( response ) {
			//todo error
		});
	});
</script>