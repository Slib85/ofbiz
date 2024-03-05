<div class="row">
    <div class="col-md-6">
        <div class="panel">
            <div class="panel-body">
                <ul class="list-group list-group-bordered">
                    <li class="list-group-item active">
                        <div class="row">
                            <div class="col-md-9">Bigname Categories</div>
                            <div class="col-md-3">
                                <div class="pull-xs-right">
                                    <a class="jqs-export panel-action icon fa-exchange" data-toggle="tooltip"
                                       data-original-title="Upload Bigname Hierarchy to Marketplace" data-container="body" title=""></a>
                                </div>
                            </div>
                        </div>
                    </li>
                <#list categories as category>
                    <li class="list-group-item">
                        <div class="row">
                            <div class="col-md-10"><span class="<#if (category.level)?exists && category.level == 2>m-l-20<#elseif (category.level)?exists && category.level == 3>m-l-40</#if>"><span class="icon fa-folder"></span>${category.categoryName}</span><span class="tag tag-round tag-dark m-l-5 m-r-5">${category.productCategoryId}</span></div>
                            <div class="col-md-2">
                            <#--<div class="pull-xs-right">
                                        <a class="panel-action icon wb-reply" data-toggle="tooltip"
                                           data-original-title="send-to-marketplace" data-container="body" title=""></a>
                                    </div>-->
                            </div>
                        </div>
                    </li>
                </#list>
                </ul>
            </div>
        </div>
    </div>

    <div class="col-md-6">
        <div class="panel">
            <div class="panel-body">
                <ul class="jqs-categories list-group list-group-bordered">
                    <li class="list-group-item active">
                        <div class="row">
                            <div class="col-md-9">Marketplace Categories</div>
                            <div class="col-md-3">
                                <div class="pull-xs-right">
                                    <a class="jqs-sync panel-action icon fa-refresh" data-toggle="tooltip"
                                       data-original-title="Refresh Marketplace Hierarchy" data-container="body" title=""></a>
                                </div>
                            </div>
                        </div>
                    </li>
                <#list miraklCategories as category>
                    <li class="jqs-category list-group-item">
                        <div class="row">
                            <div class="col-md-10"><span class="<#if (category.level)?exists && category.level == 2>m-l-20<#elseif (category.level)?exists && category.level == 3>m-l-40</#if>"><span class="icon fa-folder"></span>${category.categoryLabel}</span><span class="tag tag-round tag-dark m-l-5 m-r-5">${category.categoryId}</span></div>
                            <div class="col-md-2">
                            <#--<div class="pull-xs-right">
                                        <a class="panel-action icon wb-reply" data-toggle="tooltip"
                                           data-original-title="send-to-marketplace" data-container="body" title=""></a>
                                    </div>-->
                            </div>
                        </div>
                    </li>
                </#list>

                </ul>
            </div>
        </div>
    </div>
</div>

<script src="/html/js/admin/marketplace/categories.js"></script>
