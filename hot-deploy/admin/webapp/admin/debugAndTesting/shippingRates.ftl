<form method="POST" action="<@ofbizUrl>/shippingRates</@ofbizUrl>">
    <div class="panel-body">
        <div class="row">
            <div class="col-lg-3">
                <div class="form-group">
                    <label class="col-sm-4 control-label" style="text-align: right;">Product ID</label>
                    <div class="col-sm-8">
                        <input name="productId" type="text" class="form-control" value="${requestParameters.productId?if_exists}">
                    </div>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label class="col-sm-4 control-label" style="text-align: right;">Quantity</label>
                    <div class="col-sm-8">
                        <input name="productQuantity" type="text" class="form-control" value="${requestParameters.productQuantity?if_exists}">
                    </div>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label class="col-sm-4 control-label" style="text-align: right;">Zip Code</label>
                    <div class="col-sm-8">
                        <input name="zipCode" type="text" class="form-control" value="${requestParameters.zipCode?if_exists}">
                    </div>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <input type="submit" />
                </div>
            </div>
        </div>
    </div>
</form>

<#if shippingMethods?has_content>
    <div class="row margin-top-15">
        <div class="col-md-12">
            <div class="panel panel-primary panel-line">
                <!-- panel head -->
                <div class="panel-heading">
                    <h3 class="panel-title">Shipping Methods</h3>
                </div>

                <!-- panel body -->
                <div class="panel-body">
                    <table class="table responsive table-striped" id="ruleTable">
                        <thead>
                            <tr>
                                <th>Carrier</th>
                                <th>Zone</th>
                                <th>Shipping Method</th>
                                <th>Rate Per Unit</th>
                                <th>Total Cost</th>
                            </tr>
                        </thead>

                        <tbody>
                            <#list shippingMethods as shippingMethod>
                                <tr>
                                    <td>${shippingMethod.get("carrierId")?if_exists}</td>
                                    <td>${shippingMethod.get("zone")?if_exists}</td>
                                    <td>${shippingMethod.get("shippingMethodId")?if_exists}</td>
                                    <td>${shippingMethod.get("ratePerUnit")?default(0)?string.currency}</td>
                                    <td>${(shippingMethod.get("ratePerUnit")?default(0) * cartonQty)?string.currency}</td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</#if>