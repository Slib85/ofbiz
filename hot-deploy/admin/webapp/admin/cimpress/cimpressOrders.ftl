<link rel="stylesheet" href="/html/css/admin/orders/orderList.css" />
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">

<div class="row">
    <div class="col-xl-3 col-md-6 col-xs-12 info-panel">
        <div class="card card-shadow">
            <div class="card-block bg-white p-20">
                <button type="button" class="btn btn-floating btn-sm btn-warning">
                    <i class="icon wb-eye"></i>
                </button>
                <span class="m-l-15 font-weight-400">OPEN ORDERS</span>
                <div class="content-text text-xs-center m-b-0">
                    <span class="font-size-40 font-weight-100">${openOrders?size}</span>
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
                <span class="m-l-15 font-weight-400">CANCELLED ORDERS</span>
                <div class="content-text text-xs-center m-b-0">
                    <span class="font-size-40 font-weight-100">${cancelledOrders?size}</span>
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
                <span class="m-l-15 font-weight-400">CHANGED ORDERS</span>
                <div class="content-text text-xs-center m-b-0">
                    <span class="font-size-40 font-weight-100">${changedOrders?size}</span>
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
                <span class="m-l-15 font-weight-400">PENDING ORDERS</span>
                <div class="content-text text-xs-center m-b-0">
                    <span class="font-size-40 font-weight-100">${pendingOrders?size}</span>
                </div>
            </div>
        </div>
    </div>

</div>

<div class="panel">
    <div class="panel-body">
        <h2>Open Orders</h2>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="openOrderListFixedHeader">
                <thead>
                <tr>
                    <th>BIGNAME ORDER ID</th>
                    <th>CIMPRESS ORDER ID</th>
                    <th>CIMPRESS ITEM ID</th>
                    <th>CIMPRESS STATUS</th>

                </tr>
                </thead>
                <tbody>
                <#assign count = 0>
                <#list openOrders as order>
                <tr class="${order.bignameOrderId} <#if (count % 2) == 0>odd<#else>even</#if>">
                    <td><a href="<@ofbizUrl>/viewOrder?orderId=${order.bignameOrderId}</@ofbizUrl>">${order.bignameOrderId}</a></td>
                    <td>${order.cimpressOrderId}</td>
                    <td>${order.cimpressItemIds}</td>
                    <td>${order.cimpressStatus}</td>
                </tr>
                    <#assign count = count+1 />
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>

<#if cancelledOrders?size gt 0>
<div class="panel">
    <div class="panel-body">
        <h2>Cancelled Orders</h2>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="cancelledOrderListFixedHeader">
                <thead>
                <tr>
                    <th>BIGNAME ORDER ID</th>
                    <th>CIMPRESS ORDER ID</th>
                    <th>CIMPRESS ITEM ID</th>
                    <th>CIMPRESS STATUS</th>

                </tr>
                </thead>

                <tbody>
                <#assign count = 0>
                <#list cancelledOrders as order>
                <tr class="${order.bignameOrderId} <#if (count % 2) == 0>odd<#else>even</#if>">
                    <td><a href="<@ofbizUrl>/viewOrder?orderId=${order.bignameOrderId}</@ofbizUrl>">${order.bignameOrderId}</a></td>
                    <td>${order.cimpressOrderId}</td>
                    <td>${order.cimpressItemIds}</td>
                    <td>${order.cimpressStatus}</td>
                </tr>
                    <#assign count = count+1 />
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</#if>

<#if changedOrders?size gt 0>
<div class="panel">
    <div class="panel-body">
        <h2>Changed Orders</h2>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="changedOrderListFixedHeader">
                <thead>
                <tr>
                    <th>BIGNAME ORDER ID</th>
                    <th>CIMPRESS ORDER ID</th>
                    <th>CIMPRESS ITEM ID</th>
                    <th>CIMPRESS STATUS</th>
                    <th>ADDRESS CHANGED</th>
                    <th>SHIPPING METHOD CHANGED</th>

                </tr>
                </thead>
                <tbody>
                <#assign count = 0>
                <#list changedOrders as order>
                <tr class="${order.bignameOrderId} <#if (count % 2) == 0>odd<#else>even</#if>">
                    <td><a href="<@ofbizUrl>/viewOrder?orderId=${order.bignameOrderId}</@ofbizUrl>">${order.bignameOrderId}</a></td>
                    <td>${order.cimpressOrderId}</td>
                    <td>${order.cimpressItemIds}</td>
                    <td>${order.cimpressStatus}</td>
                    <td>${order.addressChanged}</td>
                    <td>${order.shippingMethodChanged}</td>
                </tr>
                    <#assign count = count+1 />
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</#if>
<#if pendingOrders?size gt 0>
<div class="panel">
    <div class="panel-body">
        <h2>Pending Orders</h2>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="pendingOrderListFixedHeader">
                <thead>
                <tr>
                    <th>BIGNAME ORDER ID</th>
                    <th>CIMPRESS ORDER ID</th>
                    <th>CIMPRESS ITEM ID</th>
                    <th>CIMPRESS STATUS</th>
                    <th>SURE POST</th>
                    <th>PRICE MISMATCH</th>
                    <th>RESTRICTED ADDRESS</th>

                </tr>
                </thead>

                <tbody>
                <#assign count = 0>
                <#list pendingOrders as order>
                <tr class="${order.bignameOrderId} <#if (count % 2) == 0>odd<#else>even</#if>">
                    <td><a href="<@ofbizUrl>/viewOrder?orderId=${order.bignameOrderId}</@ofbizUrl>">${order.bignameOrderId}</a></td>
                    <td>${order.cimpressOrderId}</td>
                    <td>${order.cimpressItemIds}</td>
                    <td>${order.cimpressStatus}</td>
                    <td>${order.surePost!}</td>
                    <td>${order.priceMismatch!}</td>
                    <td>${order.restrictedAddress!}</td>
                </tr>
                    <#assign count = count+1 />
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</#if>
<script src="<@ofbizContentUrl>/html/js/admin/cimpressOrders.js</@ofbizContentUrl>?ts=1"></script>
