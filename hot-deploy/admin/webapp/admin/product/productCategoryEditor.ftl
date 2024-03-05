<div class="panel-body">
    <div class="row">
        <div class="col-lg-4">
            <div class="form-group">
                <label class="col-lg-3 control-label" style="text-align: right;">Product Category ID</label>
                <div class="col-lg-9">
                    <input name="productCategoryId" type="text" class="form-control" value="">
                </div>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="form-group">
                <label class="col-lg-3 control-label" style="text-align: right;">Primary Parent Category ID</label>
                <div class="col-lg-9">
                    <input name="primaryParentCategoryId" type="text" class="form-control" value="">
                </div>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="form-group">
                <label class="col-lg-3 control-label" style="text-align: right;">Category Name</label>
                <div class="col-lg-9">
                    <input name="categoryName" type="text" class="form-control" value="">
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-4">
            <div class="form-group">
                <label class="col-lg-3 control-label" style="text-align: right;">Description</label>
                <div class="col-lg-9">
                    <input name="description" type="text" class="form-control" value="">
                </div>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="form-group">
                <label class="col-lg-3 control-label" style="text-align: right;">Long Description</label>
                <div class="col-lg-9">
                    <input name="longDescription" type="text" class="form-control" value="">
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="form-group">
            <div class="col-lg-12">
                <input class="pull-right" type="button" name="productCategorySubmit" value="Save Category" />
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        $("[name=\"productCategorySubmit\"]").on("click", function() {
            $.ajax({
                type: "POST",
                url: "/admin/control/addProductCategory",
                data: {
                    "productCategoryId": $("input[name=\"productCategoryId\"]").val(),
                    "primaryParentCategoryId": $("input[name=\"primaryParentCategoryId\"]").val(),
                    "categoryName": $("input[name=\"categoryName\"]").val(),
                    "description": $("input[name=\"description\"]").val(),
                    "longDescription": $("input[name=\"longDescription\"]").val()
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
    });
</script>

<div class="row margin-top-15">
    <div class="col-md-12">
        <div class="panel panel-primary panel-line">
            <!-- panel head -->
            <div class="panel-heading">
                <h3 class="panel-title">Product Categories</h3>
            </div>

            <!-- panel body -->
            <div class="panel-body">
                <table class="table responsive table-striped" id="ruleTable">
                    <thead>
                        <tr>
                            <th>Product Category ID</th>
                            <th>Primary Parent Category ID</th>
                            <th>Category Name</th>
                            <th>Description</th>
                            <th>Long Description</th>
                        </tr>
                    </thead>

                    <tbody>
                        <#if productCategoryList?has_content>
                            <#list productCategoryList as productCategory>
                                <tr>
                                    <td>${productCategory.productCategoryId?if_exists}</td>
                                    <td>${productCategory.primaryParentCategoryId?if_exists}</td>
                                    <td>${productCategory.categoryName?if_exists}</td>
                                    <td>${productCategory.description?if_exists}</td>
                                    <td>${productCategory.longDescription?if_exists}</td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>