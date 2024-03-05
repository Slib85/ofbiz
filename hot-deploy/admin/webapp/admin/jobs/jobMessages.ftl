<link rel="stylesheet" href="/html/css/admin/orders/orderList.css" />
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">


<div class="panel">
    <div class="panel-body">
        <h4 style="text-transform: uppercase;">${jobName} - Run Id: ${runId}</h4>
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="jobFixedHeader">
                <thead>
                <tr>
                    <th>MESSAGE</th>
                    <th>LEVEL</th>
                    <th>CREATED TIME</th>

                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">

                    <th>MESSAGE</th>
                    <th>LEVEL</th>
                    <th>CREATED TIME</th>

                </tr>
                </tfoot>
                <tbody>
                <#assign count = 0>
                <#list messages as message>
                <tr class="${message.messageId} <#if (count % 2) == 0>odd<#else>even</#if>">
                    <td>${message['message']}</td>
                    <td>${message['level']}</td>
                    <td>${message['messageTimestamp']}</td>
                </tr>
                    <#assign count = count+1 />
                </#list>
                </tbody>
            </table>
            <#if '${previousRunId}' != '-1'><a href="<@ofbizUrl>/jobMessages?runId=${previousRunId}&jobName=${jobName}&jobId=${jobId}</@ofbizUrl>" class="previous">&laquo; Previous</a><#else></#if>
            <#if '${nextRunId}' != '-1'><a href="<@ofbizUrl>/jobMessages?runId=${nextRunId}&jobName=${jobName}&jobId=${jobId}</@ofbizUrl>" class="next">Next &raquo;</a><#else></#if>
        </div>
    </div>
</div>
<#--
<#macro statusDescription desc export="null" orderId="null">
<button type="button" data-oid="<#if orderId?has_content>${orderId}</#if>" class="exportOrder btn btn-<#if export?has_content>success<#else>default</#if> btn-xs" style="font-size:10px;">
    <i class="site-menu-icon fa-cloud-upload"></i>
</button>
    <#if desc == "ORDER_CREATED">
    <button type="button" class="btn btn-primary btn-icon btn-xs" style="font-size:10px;">
        CREATED
        <i class="fa-check"></i>
    </button>
    <#elseif desc == "ORDER_APPROVED">
    <button type="button" class="btn btn-warning btn-icon btn-xs" style="font-size:10px;">
        APPROVED
        <i class="fa-warning"></i>
    </button>
    <#elseif desc == "ORDER_PENDING">
    <button type="button" class="btn btn-warning btn-icon btn-xs" style="font-size:10px;">
        PENDING
        <i class="fa-warning"></i>
    </button>
    <#elseif desc == "ORDER_SHIPPED">
    <button type="button" class="btn btn-success btn-icon btn-xs" style="font-size:10px;">
        SHIPPED
        <i class="fa-truck"></i>
    </button>
    <#elseif desc == "ORDER_CANCELLED">
    <button type="button" class="btn btn-danger btn-icon btn-xs" style="font-size:10px;">
        CANCELED
        <i class="fa-close"></i>
    </button>
    </#if>
</#macro>-->

<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
<#--<script src="<@ofbizContentUrl>/html/js/admin/orderList.js</@ofbizContentUrl>?ts=1"></script>-->
<script src="<@ofbizContentUrl>/html/js/admin/jobMessages.js</@ofbizContentUrl>?ts=1"></script>