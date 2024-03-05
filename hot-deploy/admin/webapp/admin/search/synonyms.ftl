<link rel="stylesheet" href="/html/themes/global/vendor/toastr/toastr.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/advanced/toastr.css">
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-tagsinput/bootstrap-tagsinput.css">
<style>
    .bootstrap-tagsinput {
        width: 100% !important;
    }
    #synListFixedHeader a {
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
        <form name="createSynonym" id="createSynonym" method="POST" action="<@ofbizUrl>/setSynonym</@ofbizUrl>">
        <input type="hidden" name="id" value="${(synonym.id)?if_exists}" />
        <div class="row row-lg">
            <div id="fromSynonyms" class="col-md-3 col-xs-12">
                <div class="form-group fromSyn">
                    <label class="control-label fromLabel">From</label>
                    <input class="form-control bsTags" data-role="tagsinput" name="fromSynonyms" value="${(synonym.fromSynonyms)?if_exists}" />
                </div>
            </div>
            <div id="toSynonyms" class="col-md-3 col-xs-12">
                <div class="form-group toSyn">
                    <label class="control-label">To</label>
                    <input class="form-control bsTags2" data-role="tagsinput" name="toSynonyms" value="${(synonym.toSynonyms)?if_exists}" />
                </div>
            </div>
            <div class="col-md-2 col-xs-12">
                <div class="form-group">
                    <label class="control-label">Type</label>
                    <select id="synonymTypeId" name="synonymTypeId" class="form-control">
                        <option value="ONE_WAY" <#if (synonym.synonymTypeId)?exists && synonym.synonymTypeId == "ONE_WAY">selected</#if>>One Way</option>
                        <option value="TWO_WAY" <#if (synonym.synonymTypeId)?exists && synonym.synonymTypeId == "TWO_WAY">selected</#if>>Two Way</option>
                    </select>
                </div>
            </div>
            <div class="col-md-2 col-xs-12">
                <div class="form-group">
                    <label class="control-label">WebSite ID</label>
                    <select name="webSiteId" class="form-control">
                        <option value="ae" <#if (synonym.webSiteId)?exists && synonym.webSiteId == "ae">selected</#if>>ActionEnvelope.com</option>
                        <option value="envelopes" <#if (synonym.webSiteId)?exists && synonym.webSiteId == "envelopes">selected</#if>>Envelopes.com</option>
                        <option value="folders" <#if (synonym.webSiteId)?exists && synonym.webSiteId == "folders">selected</#if>>Folders.com</option>
                        <option value="bags" <#if (synonym.webSiteId)?exists && synonym.webSiteId == "bags">selected</#if>>Bags.com</option>
                    </select>
                </div>
            </div>
            <div class="col-md-1 col-xs-12">
                <div class="form-group">
                    <label class="control-label">Enabled</label>
                    <select name="enabled" class="form-control">
                        <option value="Y" <#if (synonym.enabled)?exists && synonym.enabled == "Y">selected</#if>>Enabled</option>
                        <option value="N" <#if (synonym.enabled)?exists && synonym.enabled == "N">selected</#if>>Disabled</option>
                    </select>
                </div>
            </div>
            <div class="col-md-1 col-xs-12">
                <br />
                <button class="btn btn-primary btn-icon btn-lg" type="submit">SAVE</button>
            </div>
        </div>
        </form>
    </div>
</div>

<#if synonyms?has_content>
<div class="panel">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="synListFixedHeader">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>FROM</th>
                    <th>TO</th>
                    <th>TYPE</th>
                    <th>WEBSITE</th>
                    <th>ENABLED</th>
                    <th></th>
                </tr>
                </thead>
                <tfoot>
                <tr class="replace-inputs">
                    <th>ID</th>
                    <th>FROM</th>
                    <th>TO</th>
                    <th>TYPE</th>
                    <th>WEBSITE</th>
                    <th>ENABLED</th>
                    <th></th>
                </tr>
                </tfoot>
                <tbody>
                    <#list synonyms as synonym>
                    <tr>
                        <td>${synonym.id}</td>
                        <td>${synonym.fromSynonyms}</td>
                        <td>${synonym.toSynonyms?if_exists}</td>
                        <td>${synonym.synonymTypeId}</td>
                        <td>${synonym.webSiteId}</td>
                        <td>${synonym.enabled}</td>
                        <td>
                            <a href="<@ofbizUrl>/createSynonym</@ofbizUrl>?id=${synonym.id?if_exists}" class="btn btn-success btn-xs">
                                <i class="icon wb-link" aria-hidden="true"></i>Edit
                            </a>
                            <a data-id=${synonym.id?if_exists} href="<@ofbizUrl>/removeSynonym</@ofbizUrl>?id=${synonym.id?if_exists}" class="removeSynonym btn btn-danger btn-xs">
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

<button class="btn btn-primary btn-icon btn-lg generateSynonymFile" type="submit">RE-PROCESS SYNONYM FILE</button>
</#if>

<script src="/html/themes/global/vendor/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
<script src="/html/themes/global/vendor/toastr/toastr.js"></script>
<script>
    $('#synonymTypeId').on('change', function(e) {
        if($(this).val() == "ONE_WAY") {
            $('#toSynonyms').show();
            $('.fromLabel').text('From');
        } else {
            $('#toSynonyms').hide();
            $('.fromLabel').text('Keywords');
        }
    });

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
    $('input.bsTags2').tagsinput({
        tagClass: 'tag tag-danger'
    });

    $('#createSynonym').on('submit', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/setSynonym',
            data: $('#createSynonym').serialize(),
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully created new synonym', 'Success', toastrOpts);
                window.setTimeout(function(){
                    window.location.href = '/admin/control/createSynonym';
                }, 2000);
            } else {
                toastr.error(data.error, 'Error', toastrOpts);
            }
        });
    });

    $('.removeSynonym').on('click', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/removeSynonym',
            data: { 'id' : $(this).attr('data-id') },
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully removed synonym.', 'Success', toastrOpts);
                window.setTimeout(function(){
                    window.location.href = '/admin/control/createSynonym';
                }, 2000);
            } else {
                toastr.error('Error trying to remove synonym.', 'Error', toastrOpts);
            }
        });
    });

    $('.generateSynonymFile').on('click', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/generateSynonymFile',
            data: {},
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully generated synonym file.', 'Success', toastrOpts);
            } else {
                toastr.error('Error trying to generated synonym file.', 'Error', toastrOpts);
            }
        });
    });

    // initialize datatable
    jQuery(document).ready(function($) {
        var table = $('#synListFixedHeader').DataTable({
            iDisplayLength: 50,
            responsive: true,
            bPaginate: true,
            aaSorting: []
        });
    });
</script>