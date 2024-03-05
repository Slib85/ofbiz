<link rel="stylesheet" href="/html/themes/global/vendor/toastr/toastr.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/advanced/toastr.css">
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-tagsinput/bootstrap-tagsinput.css">
<link rel="stylesheet" href="/html/themes/global/vendor/select2/select2.css">
<style>
    .bootstrap-tagsinput {
        width: 100% !important;
    }
    #redListFixedHeader a {
        text-decoration: none;
    }
    .table {
        font-size: 12px;
    }
    .table td {
        white-space: nowrap;
    }
</style>

<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title"></h3>
    </div>
    <div class="panel-body">
        <form name="createBanner" id="createBanner" method="POST" action="<@ofbizUrl>/setBanner</@ofbizUrl>">
            <input type="hidden" name="id" value="${(banner.id)?if_exists}" />
            <div class="row row-lg">
                <div class="col-md-10 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Name</label>
                        <input class="form-control" name="bannerName" value="${(banner.bannerName)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">WebSite ID</label>
                        <select name="webSiteId" class="form-control">
                            <option value="ae" <#if (banner.webSiteId)?exists && banner.webSiteId == "ae">selected</#if>>ActionEnvelope.com</option>
                            <option value="envelopes" <#if (banner.webSiteId)?exists && banner.webSiteId == "envelopes">selected</#if>>Envelopes.com</option>
                            <option value="folders" <#if (banner.webSiteId)?exists && banner.webSiteId == "folders">selected</#if>>Folders.com</option>
                            <option value="bags" <#if (banner.webSiteId)?exists && banner.webSiteId == "bags">selected</#if>>Bags.com</option>
                        </select>
                    </div>
                </div>
            </div>
            <#--
            <div class="row row-lg">
                <div class="col-md-4 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Background URL</label>
                        <input class="form-control" name="bannerBackground" value="${(banner.bannerBackground)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-4 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Left URL</label>
                        <input class="form-control" name="bannerLeft" value="${(banner.bannerLeft)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-4 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Right URL</label>
                        <input class="form-control" name="bannerRight" value="${(banner.bannerRight)?if_exists}" />
                    </div>
                </div>
            </div>
            -->
            <div class="row row-lg">
                <div class="col-md-3 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Banner Image Name</label>
                        <input class="form-control" name="banner" value="${(banner.banner)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-3 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Page Type</label>
                        <select id="pageTypeId" name="pageTypeId" class="form-control">
                            <option value="ANY_PAGE" <#if (banner.pageTypeId)?exists && banner.pageTypeId == "ANY_PAGE">selected</#if>>Any Page</option>
                            <option value="SEARCH_PAGE" <#if (banner.pageTypeId)?exists && banner.pageTypeId == "SEARCH_PAGE">selected</#if>>Search Page</option>
                            <option value="CATEGORY_PAGE" <#if (banner.pageTypeId)?exists && banner.pageTypeId == "CATEGORY_PAGE">selected</#if>>Category Page</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Operator</label>
                        <select id="conditionOperator" name="conditionOperator" class="form-control">
                            <option value="AND" <#if (banner.conditionOperator)?exists && banner.conditionOperator == "AND">selected</#if>>All</option>
                            <option value="OR" <#if (banner.conditionOperator)?exists && banner.conditionOperator == "OR">selected</#if>>Any</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Position</label>
                        <select id="positionTypeId" name="positionTypeId" class="form-control">
                            <option value="POSITION_TOP" <#if (banner.positionTypeId)?exists && banner.positionTypeId == "POSITION_TOP">selected</#if>>Top</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row row-lg">
                <div class="col-md-6 col-xs-12">
                    <div class="form-group">
                        <label class="control-label fromLabel">Is Phrase</label>
                        <input class="form-control bsTags" data-role="tagsinput" name="isQuery" value="${(banner.isQuery)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-6 col-xs-12">
                    <div class="form-group">
                        <label class="control-label fromLabel">Contains Phrase</label>
                        <input class="form-control bsTags" data-role="tagsinput" name="containsQuery" value="${(banner.containsQuery)?if_exists}" />
                    </div>
                </div>
            </div>
            <div class="row row-lg">
                <#assign fieldType = "" />
                <#list fields as field>
                    <#if fieldType != "" && fieldType != field.facetTypeId>
                            </select>
                        </div>
                    </div>
                    </#if>
                    <#if fieldType == "" || fieldType != field.facetTypeId>
                    <div class="col-md-2 col-xs-12">
                        <div class="form-group">
                            <label class="control-label">${Static["com.bigname.search.elasticsearch.SearchField"].getFilterDescription(field.facetTypeId, (banner.webSiteId)?default("envelopes"))}</label>
                            <select class="form-control" data-plugin="select2" name="${field.facetTypeId}" multiple>
                    </#if>
                                <#assign dataValue = "" />
                                <#assign isList = "false" />
                                <#if data?exists && data[field.facetTypeId]?exists>
                                    <#assign dataValue = data[field.facetTypeId] />
                                    <#if dataValue?is_enumerable>
                                        <#assign isList = "true" />
                                    </#if>
                                </#if>
                                <option value="${field.facetId}" <#if data?exists && ((isList == "true" && dataValue.contains(field.facetId)) || (isList == "false" && field.facetId == dataValue))>selected</#if>>${field.facetName}</option>
                    <#assign fieldType = field.facetTypeId />
                    <#if fields?has_content && !field_has_next>
                            </select>
                        </div>
                    </div>
                    </#if>
                </#list>
                <div class="col-md-2 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Enabled</label>
                        <select class="form-control" name="enabled">
                            <option value="Y" <#if (banner.enabled)?exists && banner.enabled == "Y">selected</#if>>Enabled</option>
                            <option value="N" <#if (banner.enabled)?exists && banner.enabled == "N">selected</#if>>Disabled</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row row-lg">
                <div class="col-md-1 col-xs-12">
                    <button class="btn btn-primary btn-icon btn-lg" type="submit">SAVE</button>
                </div>
            </div>
        </form>
    </div>
</div>

<#if banners?has_content>
<div class="panel">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="redListFixedHeader">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>IMAGE</th>
                    <th>TYPE</th>
                    <th>IS PHRASE</th>
                    <th>CONTAINS PHRASE</th>
                    <th>WEBSITE ID</th>
                    <th>ENABLED</th>
                    <th></th>
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>ID</th>
                    <th>NAME</th>
                    <th>IMAGE</th>
                    <th>TYPE</th>
                    <th>IS PHRASE</th>
                    <th>CONTAINS PHRASE</th>
                    <th>WEBSITE ID</th>
                    <th>ENABLED</th>
                    <th></th>
                </tr>
                </tfoot>
                <tbody>
                    <#list banners as banner>
                    <tr>
                        <td>${banner.id}</td>
                        <td>${banner.bannerName}</td>
                        <td>${banner.banner?if_exists}</td>
                        <td>${banner.pageTypeId}</td>
                        <td>${banner.isQuery?if_exists}</td>
                        <td>${banner.containsQuery?if_exists}</td>
                        <td>${banner.webSiteId}</td>
                        <td>${banner.enabled}</td>
                        <td>
                            <a href="<@ofbizUrl>/createBanner</@ofbizUrl>?id=${banner.id?if_exists}" class="btn btn-success btn-xs">
                                <i class="icon wb-link" aria-hidden="true"></i>Edit
                            </a>
                            <a data-id=${banner.id?if_exists} href="<@ofbizUrl>/removeBanner</@ofbizUrl>?id=${banner.id?if_exists}" class="removeBanner btn btn-danger btn-xs">
                                <i class="icon wb-link" aria-hidden="true"></i>Delete
                            </a>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</#if>

<script src="/html/themes/global/vendor/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
<script src="/html/themes/global/vendor/toastr/toastr.js"></script>
<script src="/html/themes/global/vendor/select2/select2.full.min.js"></script>
<script src="/html/themes/global/js/Plugin/select2.js"></script>
<script>
    var toastrOpts = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

    $('input.bsTags').tagsinput({
        tagClass: 'tag tag-success'
    });

    $('#createBanner').on('submit', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/setBanner',
            data: $('#createBanner').serialize(),
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully created new banner', 'Success', toastrOpts);
                window.setTimeout(function(){
                    window.location.href = '/admin/control/createBanner';
                }, 2000);
            } else {
                toastr.error('Error trying to create banner.', 'Error', toastrOpts);
            }
        });
    });

    $('.removeBanner').on('click', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/removeBanner',
            data: { 'id' : $(this).attr('data-id') },
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully removed banner.', 'Success', toastrOpts);
                window.setTimeout(function(){
                    window.location.href = '/admin/control/createBanner';
                }, 2000);
            } else {
                toastr.error('Error trying to remove banner.', 'Error', toastrOpts);
            }
        });
    });

    // initialize datatable
    jQuery(document).ready(function($) {
        var table = $('#redListFixedHeader').DataTable({
            iDisplayLength: 50,
            responsive: true,
            bPaginate: true,
            aaSorting: []
        });
    });
</script>