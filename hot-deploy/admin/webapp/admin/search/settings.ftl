<link rel="stylesheet" href="/html/themes/global/vendor/toastr/toastr.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/advanced/toastr.css">
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-tagsinput/bootstrap-tagsinput.css">
<link rel="stylesheet" href="/html/themes/global/vendor/ionrangeslider/ionrangeslider.min.css">
<style>
    .bootstrap-tagsinput {
        width: 100% !important;
    }
    .table {
        font-size: 12px;
    }
    .table td {
        white-space: nowrap;
    }
</style>

<div class="row">
    <div class="col-md-12">
        <div class="panel">
            <div class="panel-heading">
                <h3 class="panel-title">Settings for ${params.webSiteId}</h3>
                <div class="panel-actions">
                    <div class="input-group-sm">
                        <select id="setWebSiteId" name="webSiteId" class="form-control">
                            <option value="ae" <#if (params.webSiteId)?exists && params.webSiteId == "ae">selected</#if>>ActionEnvelope.com</option>
                            <option value="envelopes" <#if (params.webSiteId)?exists && params.webSiteId == "envelopes">selected</#if>>Envelopes.com</option>
                            <option value="folders" <#if (params.webSiteId)?exists && params.webSiteId == "folders">selected</#if>>Folders.com</option>
                            <option value="bags" <#if (params.webSiteId)?exists && params.webSiteId == "bags">selected</#if>>Bags.com</option>
                        </select>
                    </div>
                </div>
            </div>
            <form name="searchSettings" id="searchSettings" method="POST" action="<@ofbizUrl>/searchSettings</@ofbizUrl>">
            <input type="hidden" name="webSiteId" value="${params.webSiteId}" />
            <div class="panel-body">
                <div class="row row-lg">
                    <div class="col-lg-12 col-xs-12">
                        <div class="example table-responsive">
                            <table class="table table-hover table-striped">
                                <thead>
                                    <tr>
                                        <th style="width: 5%"></th>
                                        <th style="width: 10%">Facet</th>
                                        <th style="width: 5%">ID</th>
                                        <th style="width: 40%">Name</th>
                                        <th style="width: 40%;">Weight</th>
                                    </tr>
                                </thead>
                                <tbody class="resultSet" data-plugin="sortable">
                                <#list fields as field>
                                    <#if field.fieldId?has_content>
                                    <tr class="fieldContainer" id="${field.fieldId}_tr" data-sequence="${(field.sequenceNum)?if_exists}">
                                        <input type="hidden" class="form-control" name="fieldName" value="${(field.fieldName)?if_exists}" />
                                        <input type="hidden" class="form-control" name="sequenceNum" value="${(field.sequenceNum)?if_exists}" />
                                        <td class="handle">
                                            <i class="icon wb-move" aria-hidden="true"></i>
                                        </td>
                                        <td>
                                            <div class="checkbox-custom checkbox-primary">
                                                <input type="checkbox" id="${field.fieldId}_enable" name="enable" value="Y" <#if field.sequenceNum?has_content && field.sequenceNum gte 0>checked</#if> />
                                                <label for="${field.fieldId}_enable">Enable Facet</label>
                                            </div>
                                            <div class="checkbox-custom checkbox-primary">
                                                <input type="checkbox" id="${field.fieldId}_searchable" name="searchable" value="Y" <#if field.searchable?has_content && field.searchable == "Y">checked</#if> />
                                                <label for="${field.fieldId}_searchable">Searchable</label>
                                            </div>
                                        </td>
                                        <td>${(field.fieldName)?if_exists}</td>
                                        <td>
                                            <input class="form-control form-control-sm" name="description" value="${(field.description)?if_exists}" />
                                        </td>
                                        <td>
                                            <input type="text" id="${field.fieldId}_boostFactor" name="boostFactor" data-plugin="ionRangeSlider" data-grid=true data-step=.5 data-min=0 data-max=10 data-from=${(field.boostFactor)?default(1)} />
                                        </td>
                                    </tr>
                                    </#if>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row row-lg">
                        <div class="col-md-12 col-xs-12">
                            <button class="btn btn-primary btn-icon btn-lg pull-right saveSettings">SAVE</button>
                        </div>
                    </div>
                </div>
            </div>
            </form>
        </div>
    </div>
</div>

<script src="/html/themes//global/vendor/ionrangeslider/ion.rangeSlider.min.js"></script>
<script src="/html/themes/global/vendor/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
<script src="/html/themes/global/vendor/toastr/toastr.js"></script>
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

    $('#setWebSiteId').on('change', function(e) {
        window.location.href = '/admin/control/searchSettings?webSiteId=' + $(this).val();
    });

    $('#searchSettings').on('submit', function(e) {
        e.preventDefault();

        var data = [];
        $.each($('.fieldContainer'), function(index, element) {
            var dataAttr = {};
            dataAttr.fieldName = $(this).find('input[name=fieldName]').val();
            dataAttr.sequenceNum = $(this).find('input[name=sequenceNum]').val();
            dataAttr.searchable = $(this).find('input[name=searchable]').prop('checked') ? 'Y' : 'N';
            dataAttr.description = $(this).find('input[name=description]').val();
            dataAttr.boostFactor = $(this).find('input[name=boostFactor]').val();
            data.push(dataAttr);
        });

        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/saveSettings',
            data: { 'webSiteId' : $('#searchSettings input[name="webSiteId"]').val(), 'data' : JSON.stringify(data) },
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully updated settings.', 'Success', toastrOpts);
                window.setTimeout(function(){
                    window.location.href = '/admin/control/searchSettings';
                }, 2000);
            } else {
                toastr.error('Error trying to update settings.', 'Error', toastrOpts);
            }
        });
    });

    function updateSequence() {
        //reset all to -1 first that are not enabled
        $('input[name=enable]').each(function(index, element) {
            if(!$(this).prop('checked')) {
                $(this).closest('.fieldContainer').attr('data-sequence', '-1');
                $(this).closest('.fieldContainer').find('input[name=sequenceNum]').val('-1');
            }
        });

        //update to correct sequences for enabled facets
        $('input[name=enable]:checked').each(function (index, element) {
            $(this).closest('.fieldContainer').attr('data-sequence', index);
            $(this).closest('.fieldContainer').find('input[name=sequenceNum]').val(index);
        });
    }

    $('input[name=enable]').on('click', function(e) {
        updateSequence();
    });

    $('.resultSet').sortable({
        containment: 'parent',
        handle: '.handle',
        change: function (event, ui) {
            //
        },
        update: function (event, ui) {
            updateSequence();
        }
    });
</script>