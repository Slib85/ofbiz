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
        <form name="createRedirect" id="createRedirect" method="POST" action="<@ofbizUrl>/setRedirect</@ofbizUrl>">
            <input type="hidden" name="id" value="${(redirect.id)?if_exists}" />
            <div class="row row-lg">
                <div class="col-md-10 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Name</label>
                        <input class="form-control" name="redirectName" value="${(redirect.redirectName)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">WebSite ID</label>
                        <select name="webSiteId" class="form-control">
                            <option value="ae" <#if (redirect.webSiteId)?exists && redirect.webSiteId == "ae">selected</#if>>ActionEnvelope.com</option>
                            <option value="envelopes" <#if (redirect.webSiteId)?exists && redirect.webSiteId == "envelopes">selected</#if>>Envelopes.com</option>
                            <option value="folders" <#if (redirect.webSiteId)?exists && redirect.webSiteId == "folders">selected</#if>>Folders.com</option>
                            <option value="bags" <#if (redirect.webSiteId)?exists && redirect.webSiteId == "bags">selected</#if>>Bags.com</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row row-lg">
                <div class="col-md-8 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">URL</label>
                        <input class="form-control" name="redirectUrl" value="${(redirect.redirectUrl)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Page Type</label>
                        <select id="pageTypeId" name="pageTypeId" class="form-control">
                            <option value="ANY_PAGE" <#if (redirect.pageTypeId)?exists && redirect.pageTypeId == "ANY_PAGE">selected</#if>>Any Page</option>
                            <option value="SEARCH_PAGE" <#if (redirect.pageTypeId)?exists && redirect.pageTypeId == "SEARCH_PAGE">selected</#if>>Search Page</option>
                            <option value="CATEGORY_PAGE" <#if (redirect.pageTypeId)?exists && redirect.pageTypeId == "CATEGORY_PAGE">selected</#if>>Category Page</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-2 col-xs-12">
                    <div class="form-group">
                        <label class="control-label">Operator</label>
                        <select id="conditionOperator" name="conditionOperator" class="form-control">
                            <option value="AND" <#if (redirect.conditionOperator)?exists && redirect.conditionOperator == "AND">selected</#if>>All</option>
                            <option value="OR" <#if (redirect.conditionOperator)?exists && redirect.conditionOperator == "OR">selected</#if>>Any</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row row-lg">
                <div class="col-md-6 col-xs-12">
                    <div class="form-group">
                        <label class="control-label fromLabel">Is Phrase</label>
                        <input class="form-control bsTags" data-role="tagsinput" name="isQuery" value="${(redirect.isQuery)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-6 col-xs-12">
                    <div class="form-group">
                        <label class="control-label fromLabel">Contains Phrase</label>
                        <input class="form-control bsTags" data-role="tagsinput" name="containsQuery" value="${(redirect.containsQuery)?if_exists}" />
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
                            <label class="control-label">${Static["com.bigname.search.elasticsearch.SearchField"].getFilterDescription(field.facetTypeId, (redirect.webSiteId)?default("envelopes"))}</label>
                            <select class="form-control" multiple data-plugin="select2" name="${field.facetTypeId}">
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
                            <option value="Y" <#if (redirect.enabled)?exists && redirect.enabled == "Y">selected</#if>>Enabled</option>
                            <option value="N" <#if (redirect.enabled)?exists && redirect.enabled == "N">selected</#if>>Disabled</option>
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

<#if redirects?has_content>
<div class="panel">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="redListFixedHeader">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>URL</th>
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
                    <th>URL</th>
                    <th>TYPE</th>
                    <th>IS PHRASE</th>
                    <th>CONTAINS PHRASE</th>
                    <th>WEBSITE ID</th>
                    <th>ENABLED</th>
                    <th></th>
                </tr>
                </tfoot>
                <tbody>
                    <#list redirects as redirect>
                    <tr>
                        <td>${redirect.id}</td>
                        <td>${redirect.redirectName}</td>
                        <td>${redirect.redirectUrl?if_exists}</td>
                        <td>${redirect.pageTypeId}</td>
                        <td>${redirect.isQuery?if_exists}</td>
                        <td>${redirect.containsQuery?if_exists}</td>
                        <td>${redirect.webSiteId}</td>
                        <td>${redirect.enabled}</td>
                        <td>
                            <a href="<@ofbizUrl>/createRedirect</@ofbizUrl>?id=${redirect.id?if_exists}" class="btn btn-success btn-xs">
                                <i class="icon wb-link" aria-hidden="true"></i>Edit
                            </a>
                            <a data-id=${redirect.id?if_exists} href="<@ofbizUrl>/removeRedirect</@ofbizUrl>?id=${redirect.id?if_exists}" class="removeRedirect btn btn-danger btn-xs">
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

    $('#createRedirect').on('submit', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/setRedirect',
            data: $('#createRedirect').serialize(),
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully created new redirect', 'Success', toastrOpts);
                window.setTimeout(function(){
                    window.location.href = '/admin/control/createRedirect';
                }, 2000);
            } else {
                toastr.error(data.error, 'Error', toastrOpts);
            }
        });
    });

    $('.removeRedirect').on('click', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/removeRedirect',
            data: { 'id' : $(this).attr('data-id') },
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully removed redirect.', 'Success', toastrOpts);
                window.setTimeout(function(){
                    window.location.href = '/admin/control/createRedirect';
                }, 2000);
            } else {
                toastr.error('Error trying to remove redirect.', 'Error', toastrOpts);
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