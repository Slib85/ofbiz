<form id="productOutsourceRule" method="POST" action="<@ofbizUrl>/addUpdateProductOutsource</@ofbizUrl>" class="form-wizard" enctype="application/x-www-form-urlencoded">
    <input type="hidden" id="productId" name="productId" value="${requestParameters.productId?if_exists}" />
    <div class="panel panel-primary panel-line" data-collapsed="0">
        <!-- panel head -->
        <div class="panel-heading">
            <div class="panel-title">Outsource Rules for ${requestParameters.productId?if_exists}</div>
        </div>

        <!-- panel body -->
        <div class="panel-body">
            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <div class="checkbox">
                            <label><input type="checkbox" name="singleItemOrder" value="Y" <#if rules?has_content && rules.singleItemOrder?has_content && rules.singleItemOrder == "Y">checked</#if> />Single Item Orders</label>
                        </div>
                        <div class="checkbox">
                            <label><input type="checkbox" name="stockSupplied" value="Y" <#if rules?has_content && rules.stockSupplied?has_content && rules.stockSupplied == "Y">checked</#if> />Stock Supplied</label>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Number of Ink Colors</label>
                        <div class="checkbox">
                            <label><input type="checkbox" name="inkColors" value="1" <#if rules?has_content && rules.inkColors?has_content && rules.inkColors?seq_contains("1")>checked</#if> />1 Color</label>
                        </div>
                        <div class="checkbox">
                            <label><input type="checkbox" name="inkColors" value="2" <#if rules?has_content && rules.inkColors?has_content && rules.inkColors?seq_contains("2")>checked</#if> />2 Color</label>
                        </div>
                        <div class="checkbox">
                            <label><input type="checkbox" name="inkColors" value="4" <#if rules?has_content && rules.inkColors?has_content && rules.inkColors?seq_contains("4")>checked</#if> />4 Color</label>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Production Time</label>
                        <div class="checkbox">
                            <label><input type="checkbox" name="productionTime" value="Standard" <#if rules?has_content && rules.productionTime?has_content && rules.productionTime?seq_contains("Standard")>checked</#if> />Standard</label>
                        </div>
                        <div class="checkbox">
                            <label><input type="checkbox" name="productionTime" value="Rush" <#if rules?has_content && rules.productionTime?has_content && rules.productionTime?seq_contains("Rush")>checked</#if> />Rush</label>
                        </div>
                    </div>
                </div>
                <#--
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Vendors</label>
                        <#assign vendors = Static["com.envelopes.cxml.CXMLHelper"].getVendors(delegator, requestParameters.productId?if_exists)?if_exists />
                        <select multiple="multiple" name="vendor" class="form-control" >
                        <#list vendors as vendor>
                            <option value="${vendor.vendorPartyId}">${vendor.vendorPartyId}</option>
                        </#list>
                        </select>
                    </div>
                </div>
                -->
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="col-sm-12 control-label">Profit Range</label>
                        <input type="text" class="form-control" name="profitMargin" data-plugin="TouchSpin" data-min="0" data-step="1" data-decimals="0" value="<#if rules?has_content && rules.profitMargin?has_content>${rules.profitMargin}<#else>10</#if>" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Shipping</label>
                        <#assign shippingOptions = delegator.findByAnd("ProductStoreShipmentMethView", Static["org.apache.ofbiz.base.util.UtilMisc"].toMap("productStoreId", "10000"), Static["org.apache.ofbiz.base.util.UtilMisc"].toList("sequenceNumber"), true) />
                        <select multiple="multiple" name="shipping" class="form-control" >
                        <#list shippingOptions as shipping>
                            <option value="${shipping.description}" <#if rules?has_content && rules.shipping?has_content && rules.shipping?seq_contains("${shipping.description}")>selected</#if>>${shipping.description}</option>
                        </#list>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row pull-xs-right">
            <button id="submitProductOutsource" type="submit" class="btn btn-success"><i class="icon wb-check" aria-hidden="true"></i> I SAVE</button>
        </div>
    </div>
</form>

<script src="/html/themes/global/vendor/bootstrap-touchspin/bootstrap-touchspin.min.js"></script>
<script>
    jQuery(document).ready(function($) {
        $('#productOutsourceRule').on('submit', function(e) {
            e.preventDefault();

            //ajax call
            $.ajax({
                type: 'POST',
                url: '/admin/control/addUpdateProductOutsource',
                dataType: 'json',
                data: $(this).serialize()
            }).done(function( response ) {
                if(response.success) {
                    window.location = '/admin/control/productOutsourceRules?productId=${requestParameters.productId?if_exists}';
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