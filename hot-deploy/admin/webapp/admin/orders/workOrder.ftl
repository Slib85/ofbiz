<style>
	.example-wrap {
		margin-bottom: 0px;
	}
    .example {
		margin-top: 10px;
        margin-bottom: 0px;
    }
</style>

<div class="col-lg-12 col-xs-12">
    <!-- Example Heading Icon -->
    <div class="panel">
        <div class="panel-heading">
            <h3 class="panel-title"><i class="icon wb-briefcase" aria-hidden="true"></i>Work Order</h3>
            <div class="panel-actions">
                <form class="lookup" name="viewWorkOrder" action="<@ofbizUrl>/workOrder</@ofbizUrl>" method="GET">
                    <div class="input-group-sm">
                        <button type="submit" class="input-search-btn">
                            <i class="icon wb-search" aria-hidden="true"></i>
                        </button>
                        <input type="text" class="form-control" name="id" placeholder="Work Order Lookup" value="">
                    </div>
                </form>
            </div>
        </div>
        <div class="panel-body">
			<#--
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">WO#</span>
                    <input type="text" name="workOrerId" class="form-control" placeholder="" value="">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">WO#</span>
                    <input type="text" name="workOrerId" class="form-control" placeholder="" value="">
                </div>
            </div>-->
			<#if workOrder?has_content && workOrder.success?has_content && workOrder.success?c == "true">
			<div class="example table-responsive">
				<form name="workOrder" action="<@ofbizUrl>/workOrder</@ofbizUrl>" method="POST">
					<input type="hidden" name="id" value="${workOrder.id?default("_NA_")}">
                    <input type="hidden" name="internalId" value="${workOrder.internalId?default("_NA_")}">
					<input type="hidden" name="update" value="Y" />
                    <input type="hidden" name="qtyToPull" value="${workOrder.qtyToPull}" disabled />
					<div class="row">
						<div class="col-md-6 col-xs-12">
							<table class="table table-bordered">
								<tbody>
									<tr class="">
										<td>WO#</td>
										<td>${workOrder.id?default("_NA_")} (${workOrder.status?default("No Status Available")})</td>
									</tr>
									<tr class="">
										<td>Date</td>
										<td>${workOrder.date?default("_NA_")}</td>
									</tr>
									<tr class="">
										<td>Assembly</td>
										<td>${workOrder.sku?if_exists} (${workOrder.assembly?default("_NA_")})</td>
									</tr>
                                    <tr class="">
                                        <td>Sealing Method</td>
                                        <td>${workOrder.sealingMethod?default("_NA_")}</td>
                                    </tr>
                                    <tr class="">
                                        <td>Quantity</td>
                                        <td>${workOrder.quantity?default("_NA_")}</td>
                                    </tr>
                                    <tr class="">
                                        <td>Memo</td>
                                        <td>${workOrder.memo?default("_NA_")}</td>
                                    </tr>
								</tbody>
							</table>
						</div>
						<div class="col-md-6 col-xs-12">
                            <div class="example-wrap">
                                <h4 class="example-title">Pull</h4>
                                <div class="example">
									<div class="row">
										<div class="form-group col-xs-12 col-md-12">
											<input type="number" class="form-control form-control-sm" name="pulledQty" placeholder="Quantity Pulled" autocomplete="off" value="<#if workOrder.pulledQty?has_content>${workOrder.pulledQty}<#else>${workOrder.qtyToPull}</#if>" <#if workOrder.pulledQty?has_content>disabled</#if> />
										</div>
									</div>
                                    <div class="row">
                                        <div class="form-group col-xs-12 col-md-12">
                                            <select class="form-control form-control-sm" name="pulledIssues" disabled>
                                                <option value="">Reason</option>
                                                <#list issues.entrySet() as entry>
                                                    <option value="${entry.key}" <#if workOrder.pulledIssues?has_content && entry.key == workOrder.pulledIssues>selected</#if>>${entry.value}</option>
                                                </#list>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-xs-12 col-md-12">
                                            <select style="height: calc(${staff?size} * 16px + 25px)" class="form-control form-control-sm" name="pulledBy" multiple <#if workOrder.pulledBy?has_content>disabled</#if>>
                                                <option value="">Pulled By</option>
                                                <#list staff as workOrderEmployee>
                                                    <#if workOrderEmployee.get("netsuiteId")?exists>
                                                        <#assign pulledBy = workOrder.pulledBy?default("")?split(",") />
                                                        <option value="${workOrderEmployee.get("netsuiteId")}" <#if pulledBy?has_content && pulledBy?seq_contains(workOrderEmployee.get("netsuiteId"))>selected</#if>>${workOrderEmployee.get("emailAddress")}</option>
                                                    </#if>
                                                </#list>
											</select>
                                        </div>
									</div>
                                </div>
                            </div>
                            <div class="example-wrap">
                                <h4 class="example-title">Cut</h4>
                                <div class="example">
                                    <#--<div class="row">
                                        <div class="form-group col-xs-12 col-md-12">
                                            <input type="number" class="form-control form-control-sm" name="cutQty" placeholder="Quantity Cut" autocomplete="off" <#if workOrder.cutQty?has_content>disabled</#if> />
                                        </div>
									</div>-->
                                    <div class="row">
                                        <div class="form-group col-xs-12 col-md-12">
                                            <select class="form-control form-control-sm" name="cutBy" <#if workOrder.cutBy?has_content>disabled</#if>>
												<option value="">Cut By</option>
                                                <#list staff as workOrderEmployee>
                                                    <#if workOrderEmployee.get("netsuiteId")?exists>
                                                        <#assign cutBy = workOrder.cutBy?default("")?split(",") />
                                                        <option value="${workOrderEmployee.get("netsuiteId")}" <#if cutBy?has_content && cutBy?seq_contains(workOrderEmployee.get("netsuiteId"))>selected</#if>>${workOrderEmployee.get("emailAddress")}</option>
                                                    </#if>
                                                </#list>
											</select>
                                        </div>
                                    </div>
                                </div>
                            </div>
						</div>
					</div>
                    <div>
                        <button type="submit" class="btn btn-block btn-success">Save</button>
                    </div>
                </form>
			</div>
			</#if>
        </div>
    </div>
</div>

<script>
    $(function() {
        var maxPull = parseInt($('[name="qtyToPull"]').val());
        $('[name="pulledQty"]').on('keyup', function(e) {
            if(parseInt($(this).val()) != maxPull) {
                $('[name="pulledIssues"]').prop('disabled', false);
            } else {
                $('[name="pulledIssues"]').prop('disabled', true);
            }
        });

        $('form[name="workOrder"]').on('submit', function(e) {
            if($('[name="pulledQty"]').val() == '') {
                alert('Please enter a value for pulled quantity.');
                e.preventDefault();
            } else if(parseInt($('[name="pulledQty"]').val()) != maxPull && $('[name="pulledIssues"]').val() == '') {
                alert('Please enter a reason for pulling less stock.');
                e.preventDefault();
            }
        });
    });
</script>