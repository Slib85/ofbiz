<link rel="stylesheet" href="/html/css/admin/orders/orderList.css" />
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">

<div class="panel">
    <div class="panel-body">
        <#--<h2>Accepted Orders</h2>-->
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="orderListFixedHeader">
                <thead>
                <tr>
                    <th>JOB NAME</th>
                    <th>JOB START TIME</th>
                    <th>JOB END TIME</th>
                    <th>JOB STATUS</th>
                </tr>
                </thead>


                <tbody>
                <#assign count = 0>
                <#list jobs as job>
                <tr >

                    <td><a href="<@ofbizUrl>/jobMessages?runId=${job['runId']}&jobName=${job['jobName']}&jobId=${job['jobId']}</@ofbizUrl>">${job['jobName']}</a></td>
                    <td>${job['jobStartTime']}</td>
                    <td>${job['jobEndTime']}</td>
                    <td>${job['jobStatus']}</td>

                </tr>
                <#-- <#assign count = count+1 />-->
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>


<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
<script src="<@ofbizContentUrl>/html/js/admin/jobMessages.js</@ofbizContentUrl>?ts=1"></script>