<div class="panel-body">
    <div class="row">
        <div class="col-lg-6">
            <div class="form-group">
                <label class="col-lg-3 control-label" style="text-align: right;">Email Address</label>
                <div class="col-lg-9">
                    <input name="emailAddress" type="text" class="form-control" value="">
                </div>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="form-group">
                <label class="col-lg-3 control-label" style="text-align: right;">Netsuite ID</label>
                <div class="col-lg-9">
                    <input name="netsuiteId" type="text" class="form-control" value="">
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="form-group">
            <div class="col-lg-12">
                <input class="pull-right" type="button" name="saveWorkOrderEmployee" value="Save Work Order Employee" />
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        $("[name=\"saveWorkOrderEmployee\"]").on("click", function() {
            $.ajax({
                type: "POST",
                url: "/admin/control/addWorkOrderEmployee",
                data: {
                    "emailAddress": $("input[name=\"emailAddress\"]").val(),
                    "netsuiteId": $("input[name=\"netsuiteId\"]").val()
                }
            }).done(function(response) {
                response = JSON.parse(response);
                
                if(response.success) {
                    location.reload();
                } else {
                    if (typeof response.error !== "undefined") {
                        alert(response.error);
                    }
                }
            });
        });

        $("[jqs-remove]").on("click", function() {
            if (confirm("Are you sure you wish to remove " + $(this).attr("jqs-remove") + "?")) {
                $.ajax({
                    type: "POST",
                    url: "/admin/control/removeWorkOrderEmployee",
                    data: {
                        "emailAddress": $(this).attr("jqs-remove")
                    }
                }).done(function(response) {
                    response = JSON.parse(response);

                    if(response.success) {
                        location.reload();
                    } else {
                        if (typeof response.error !== "undefined") {
                            alert(response.error);
                        }
                    }
                });
            }
        });
    });
</script>

<div class="row margin-top-15">
    <div class="col-md-12">
        <div class="panel panel-primary panel-line">
            <!-- panel head -->
            <div class="panel-heading">
                <h3 class="panel-title">Work Order Employees</h3>
            </div>

            <!-- panel body -->
            <div class="panel-body">
                <table class="table responsive table-striped" id="ruleTable">
                    <thead>
                        <tr>
                            <th>Email Address</th>
                            <th>Netsuite ID</th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                        <#if workOrderEmployeeList?has_content>
                            <#list workOrderEmployeeList as workOrderEmployee>
                                <tr>
                                    <td>${workOrderEmployee.emailAddress?if_exists}</td>
                                    <td>${workOrderEmployee.netsuiteId?if_exists}</td>
                                    <td><i class="fa fa-trash" jqs-remove="${workOrderEmployee.emailAddress?if_exists}" style="cursor: pointer; padding: 5px; float: right;"></i></td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>