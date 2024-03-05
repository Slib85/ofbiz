<link rel="stylesheet" href="/html/themes/global/vendor/toastr/toastr.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/advanced/toastr.css">
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-tagsinput/bootstrap-tagsinput.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/structure/ribbon.css">
<link rel="stylesheet" href="/html/themes/global/vendor/select2/select2.css">
<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/admin/merchandise.css</@ofbizContentUrl>" type="text/css" />

<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">Search</h3>
        <div class="panel-actions panel-actions-keep">
            <button class="btn btn-primary" data-target="#addSku" data-toggle="modal" type="button">Insert SKU</button>
        </div>
    </div>
    <div class="panel-body">
        <div class="row row-lg">
            <div class="col-md-3 col-xs-12">
                <form name="createTunedResult" id="createTunedResult" method="POST" action="<@ofbizUrl>/setTune</@ofbizUrl>">
                    <input type="hidden" name="id" value="${(tunedResult.id)?if_exists}" />
                    <input type="hidden" name="promotedProductList" value="${(tunedResult.promotedProductList)?if_exists}" />
                    <input type="hidden" name="demotedProductList" value="${(tunedResult.demotedProductList)?if_exists}" />
                    <input type="hidden" name="removedProductList" value="${(tunedResult.removedProductList)?if_exists}" />
                    <div class="row row-lg">
                        <div class="col-md-12 col-xs-12">
                            <div class="form-group">
                                <label class="control-label">Name</label>
                                <input class="form-control" name="tuneName" value="${(tunedResult.tuneName)?if_exists}" />
                            </div>
                        </div>
                    </div>
                    <div class="row row-lg">
                        <div class="col-md-12 col-xs-12">
                            <div class="form-group">
                                <label class="control-label">WebSite ID</label>
                                <select name="webSiteId" class="form-control">
                                    <option value="ae" <#if (tunedResult.webSiteId)?exists && tunedResult.webSiteId == "ae">selected</#if>>ActionEnvelope.com</option>
                                    <option value="envelopes" <#if (tunedResult.webSiteId)?exists && tunedResult.webSiteId == "envelopes">selected</#if>>Envelopes.com</option>
                                    <option value="folders" <#if (tunedResult.webSiteId)?exists && tunedResult.webSiteId == "folders">selected</#if>>Folders.com</option>
                                    <option value="bags" <#if (tunedResult.webSiteId)?exists && tunedResult.webSiteId == "bags">selected</#if>>Bags.com</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row row-lg">
                        <div class="col-md-6 col-xs-12">
                            <div class="form-group">
                                <label class="control-label">Page Type</label>
                                <select id="pageTypeId" name="pageTypeId" class="form-control">
                                    <option value="ANY_PAGE" <#if (tunedResult.pageTypeId)?exists && tunedResult.pageTypeId == "ANY_PAGE">selected</#if>>Any Page</option>
                                    <option value="SEARCH_PAGE" <#if (tunedResult.pageTypeId)?exists && tunedResult.pageTypeId == "SEARCH_PAGE">selected</#if>>Search Page</option>
                                    <option value="CATEGORY_PAGE" <#if (tunedResult.pageTypeId)?exists && tunedResult.pageTypeId == "CATEGORY_PAGE">selected</#if>>Category Page</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6 col-xs-12">
                            <div class="form-group">
                                <label class="control-label">Operator</label>
                                <select id="conditionOperator" name="conditionOperator" class="form-control">
                                    <option value="AND" <#if (tunedResult.conditionOperator)?exists && tunedResult.conditionOperator == "AND">selected</#if>>All</option>
                                    <option value="OR" <#if (tunedResult.conditionOperator)?exists && tunedResult.conditionOperator == "OR">selected</#if>>Any</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row row-lg">
                        <div class="col-md-6 col-xs-12">
                            <div class="form-group">
                                <label class="control-label fromLabel">Is Phrase</label>
                                <input class="form-control bsTags" data-role="tagsinput" name="isQuery" value="${(tunedResult.isQuery)?if_exists}" />
                            </div>
                        </div>
                        <div class="col-md-6 col-xs-12">
                            <div class="form-group">
                                <label class="control-label fromLabel">Contains Phrase</label>
                                <input class="form-control bsTags" data-role="tagsinput" name="containsQuery" value="${(tunedResult.containsQuery)?if_exists}" />
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
                                <div class="col-md-6 col-xs-12">
                                    <div class="form-group">
                                        <label class="control-label">${Static["com.bigname.search.elasticsearch.SearchField"].getFilterDescription(field.facetTypeId, (tunedResult.webSiteId)?default("envelopes"))}</label>
                                        <select class="form-control jqs-filterId" data-plugin="select2" name="${field.facetTypeId}" multiple>
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
                        <div class="col-md-6 col-xs-12">
                            <div class="form-group">
                                <label class="control-label">Enabled</label>
                                <select class="form-control" name="enabled">
                                    <option value="Y" <#if (tunedResult.enabled)?exists && tunedResult.enabled == "Y">selected</#if>>Enabled</option>
                                    <option value="N" <#if (tunedResult.enabled)?exists && tunedResult.enabled == "N">selected</#if>>Disabled</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="row row-lg">
                    <div class="col-md-1 col-xs-12">
                        <button class="btn btn-primary btn-icon btn-lg getResults">GET RESULTS</button>
                    </div>
                </div>
            </div>
            <div class="col-md-9 col-xs-12">
                Results
                <div class="row resultSet"></div>
            </div>
        </div>
        <div class="row row-lg">
            <div class="col-md-12 col-xs-12">
                <button class="btn btn-primary btn-icon btn-lg pull-right saveTune">SAVE</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade example-modal-sm" id="addSku" aria-hidden="false" aria-labelledby="addSkuLabel" role="dialog" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <form name="addSku" method="GET" action="/addSku" class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="addSkuLabel">Add Sku</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12 col-xs-12 form-group">
                        <input type="text" class="form-control" name="productId" placeholder="Enter SKU">
                    </div>
                    <div class="col-md-12 col-xs-12 pull-xs-right">
                        <button class="btn btn-primary btn-outline" type="submit">Add</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- End Modal -->

<#if tunedResults?has_content>
<div class="panel">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-hover dataTable table-striped" id="redListFixedHeader">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
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
                    <th>TYPE</th>
                    <th>IS PHRASE</th>
                    <th>CONTAINS PHRASE</th>
                    <th>WEBSITE ID</th>
                    <th>ENABLED</th>
                    <th></th>
                </tr>
                </tfoot>
                <tbody>
                    <#list tunedResults as tunedResult>
                    <tr>
                        <td>${tunedResult.id}</td>
                        <td>${tunedResult.tuneName?if_exists}</td>
                        <td>${tunedResult.pageTypeId}</td>
                        <td>${tunedResult.isQuery?if_exists}</td>
                        <td>${tunedResult.containsQuery?if_exists}</td>
                        <td>${tunedResult.webSiteId}</td>
                        <td>${tunedResult.enabled}</td>
                        <td>
                            <a href="<@ofbizUrl>/createTunedResult</@ofbizUrl>?id=${tunedResult.id?if_exists}" class="btn btn-success btn-xs">
                                <i class="icon wb-link" aria-hidden="true"></i>Edit
                            </a>
                            <a data-id=${tunedResult.id?if_exists} href="<@ofbizUrl>/removeTune</@ofbizUrl>?id=${tunedResult.id?if_exists}" class="removeTune btn btn-danger btn-xs">
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

    $(function() {
        $('.resultSet').sortable({containment: 'parent'});
        if($('input[name=id]').val() != '') {
            $('.getResults').trigger('click');
        }
    });

    $('input.bsTags').tagsinput({
        tagClass: 'tag tag-success'
    });

    $('.saveTune').on('click', function(e) {
        e.preventDefault();
        $('#createTunedResult').submit();
    });
    $('#createTunedResult').on('submit', function(e) {
        e.preventDefault();

        //get promoted products
        var promotedArray = [];
        $('.resultSet').find('[action="promotion"]').each(function(i) {
            if($(this).attr('sequence') != '') {
                promotedArray[parseInt($(this).attr('sequence'))] = $(this).attr('id');
            }
        });
        $('[name="promotedProductList"]').val(JSON.stringify(promotedArray));

        var demotedArray = [];
        $('.resultSet').find('[action="demotion"]').each(function(i) {
            demotedArray.push($(this).attr('id'));
        });
        $('[name="demotedProductList"]').val(JSON.stringify(demotedArray));

        var removedArray = [];
        $('.resultSet').find('[action="removed"]').each(function(i) {
            removedArray.push($(this).attr('id'));
        });
        $('[name="removedProductList"]').val(JSON.stringify(removedArray));

        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/setTune',
            data: $('#createTunedResult').serialize(),
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully created new tune', 'Success', toastrOpts);
                window.setTimeout(function(){
                    window.location.href = '/admin/control/createTunedResult';
                }, 2000);
            } else {
                toastr.error(data.error, 'Error', toastrOpts);
            }
        });
    });

    $('.removeTune').on('click', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/removeTune',
            data: { 'id' : $(this).attr('data-id') },
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully removed tune.', 'Success', toastrOpts);
                window.setTimeout(function(){
                    window.location.href = '/admin/control/createRedirect';
                }, 2000);
            } else {
                toastr.error('Error trying to remove tune.', 'Error', toastrOpts);
            }
        });
    });

    function resetSequences() {
        $('.resultSet').find('[sequence]').each(function (i) {
            if ($(this).attr('sequence') != '') {
                $(this).attr('sequence', $(this).index());
            }
        });
    }

    function initializeSortable(refresh) {
        bindSortingClickHandles();

        if(refresh) {
            $('.resultSet').sortable('refresh');
        } else {
            $('.resultSet').sortable('destroy');
            $('.resultSet').sortable({
                containment: 'parent',
                change: function (event, ui) {
                    ui.item.removeAttr('action');
                    ui.item.find('.ribbon').remove();
                },
                update: function (event, ui) {
                    if (typeof ui.item.attr('action') == 'undefined') {
                        ui.item.attr('action', 'promotion');
                    }

                    ui.item.find('.ribbon').remove();
                    if (ui.item.attr('action') == 'promotion') {
                        ui.item.attr('sequence', ui.item.index());
                        ui.item.find('.example').prepend(
                                $('<div/>').addClass('ribbon ribbon-vertical ribbon-success').append(
                                        $('<span/>').addClass('ribbon-inner').append(
                                                $('<i/>').addClass('icon wb-thumb-up').attr('aria-hidden', 'true')
                                        )
                                )
                        );
                    } else if (ui.item.attr('action') == 'demotion') {
                        ui.item.attr('sequence', '');
                        ui.item.find('.example').prepend(
                                $('<div/>').addClass('ribbon ribbon-vertical ribbon-warning').append(
                                        $('<span/>').addClass('ribbon-inner').append(
                                                $('<i/>').addClass('icon wb-thumb-down').attr('aria-hidden', 'true')
                                        )
                                )
                        );
                    } else if (ui.item.attr('action') == 'removed') {
                        ui.item.attr('sequence', '');
                        ui.item.find('.example').prepend(
                                $('<div/>').addClass('ribbon ribbon-vertical ribbon-danger').append(
                                        $('<span/>').addClass('ribbon-inner').append(
                                                $('<i/>').addClass('icon wb-close').attr('aria-hidden', 'true')
                                        )
                                )
                        );
                    }

                    //reset all other sequences
                    resetSequences();
                }
            });

            //loop through existing tuned data and apply it
            var promotedProductList = ($('input[name=promotedProductList]').val() != '') ? JSON.parse($('input[name=promotedProductList]').val()) : [];
            var demotedProductList = ($('input[name=demotedProductList]').val() != '') ? JSON.parse($('input[name=demotedProductList]').val()) : [];
            var removedProductList = ($('input[name=removedProductList]').val() != '') ? JSON.parse($('input[name=removedProductList]').val()) : [];

            if(promotedProductList.length > 0) {
                $.each(promotedProductList, function(index, id) {
                    if(id == null) {
                        return;
                    }
                    var el = $('.resultSet .itemContainer[id="' + id + '"]');
                    if(el.length > 0) {
                        $('.resultSet .itemContainer:nth-child(' + (index+1) + ')').before(el);

                        el.attr('action', 'promotion');
                        $('.resultSet').sortable('option', 'update')(null, {
                            item: el
                        });
                    } else {
                        addSku(id, index);
                    }

                    //make new item sortable
                    initializeSortable(true);

                    //reset all other sequences
                    resetSequences();
                });
            }

            if(demotedProductList.length > 0) {
                $.each(demotedProductList, function(index, id) {
                    $('.resultSet .itemContainer[id="' + id + '"]').find('.quickDemote').trigger('click');
                });
            }

            if(removedProductList.length > 0) {
                $.each(removedProductList, function(index, id) {
                    $('.resultSet .itemContainer[id="' + id + '"]').find('.quickRemove').trigger('click');
                });
            }
        }
    }

    function bindSortingClickHandles() {
        $('.quickPromote').off('click').on('click', function (e) {
            $(this).closest('.itemContainer').attr('action', 'promotion');
            $('.resultSet').prepend($(this).closest('.itemContainer'));
            $('.resultSet').sortable('option', 'update')(null, {
                item: $(this).closest('.itemContainer')
            });
        });
        $('.quickDemote').off('click').on('click', function (e) {
            $(this).closest('.itemContainer').attr('action', 'demotion');
            $('.resultSet').append($(this).closest('.itemContainer'));
            $('.resultSet').sortable('option', 'update')(null, {
                item: $(this).closest('.itemContainer')
            });
        });
        $('.quickRemove').off('click').on('click', function (e) {
            var isManual = $(this).closest('.itemContainer').attr('manual');
            if(typeof isManual != 'undefined' && isManual == 'true') {
                $(this).closest('.itemContainer').remove();
            } else {
                $(this).closest('.itemContainer').attr('action', 'removed');
                $('.resultSet').append($(this).closest('.itemContainer'));
                $('.resultSet').sortable('option', 'update')(null, {
                    item: $(this).closest('.itemContainer')
                });
            }
        });
    }

    function addSku(sku, index) {
        $('.resultSet .itemContainer:nth-child(' + (index+1) + ')').before(
            $('<div/>').addClass('itemContainer col-xl-2 col-md-4 col-xs-12').attr({'id': sku, 'sequence': index}).append(
                $('<div/>').addClass('resultProducts example textCenter').append(
                    $('<img/>').css({'max-height':'100px','max-width':'100px'}).attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + sku.toUpperCase() + '?wid=700&hei=440&fmt=png-alpha')
                ).append(
                    $('<p/>').addClass('resultsProductInfo').html(sku)
                ).append(
                    $('<p/>').addClass('resultsProductSubInfo').html('')
                ).append(
                    $('<p/>').addClass('resultsProductSubInfo').html('')
                ).append(
                    $('<p/>').addClass('resultsProductSubInfo').html('')
                ).append(
                    $('<span/>').addClass('quickPromote').append(
                        $('<i/>').addClass('icon wb-plus').attr('aria-hidden', 'true')
                    )
                ).append(
                    $('<span/>').addClass('quickRemove').append(
                        $('<i/>').addClass('icon wb-close').attr('aria-hidden', 'true')
                    )
                ).prepend(
                    $('<div/>').addClass('ribbon ribbon-vertical ribbon-success').append(
                        $('<span/>').addClass('ribbon-inner').append(
                            $('<i/>').addClass('icon wb-thumb-up').attr('aria-hidden', 'true')
                        )
                    )
                )
            )
        );

        var el = $('.resultSet .itemContainer[id="' + sku + '"]');
        el.attr({'action':'promotion','manual':'true'});
        $('.resultSet').sortable('option', 'update')(null, {
            item: el
        });
    }

    $('[name="addSku"]').on('submit', function(e) {
        e.preventDefault();

        if($('[name="productId"]').val() != '') {
            var skus = $('[name="productId"]').val().split(',');
            for (var i = 0; i < skus.length; i++) {
                addSku(skus[i], 0);

                //make new item sortable
                initializeSortable(true);

                //reset all other sequences
                resetSequences();
            }
        }

        $('#addSku').modal('hide');
    });

    function serializeSearchParams() {
        var filterString = '';

        $('.jqs-filterId option:selected').each(function() {
            filterString += (filterString == '' ? '' : ' ') + $(this).parent().attr('name') + ':' + $(this).val();
        });

        var query = '';
        if($('[name="isQuery"]').val().trim() != '') {
            var array = $('[name="isQuery"]').val().trim().split(',')
            query = array[0];
        } else if($('[name="containsQuery"]').val().trim() != '') {
            var array = $('[name="containsQuery"]').val().trim().split(',')
            query = array[0];
        }

        return 'bypassTuneCheck=Y&webSiteId=' + $('[name="webSiteId"]').val() + ((query != '') ? '&w=' + encodeURIComponent(query) : '') + '&af=' + filterString + '&cnt=1000';
    }

    $('.getResults').on('click', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'GET',
            timeout: 5000,
            url: '/admin/control/getSearchResult?' + serializeSearchParams(),
            data: {},
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success && data.hasOwnProperty('response') && data.response.hasOwnProperty('hits') && data.response.hits.hits.length > 0) {
                $('.resultSet').empty();
                for(var i = 0; i < data.response.hits.hits.length; i++) {
                    $('.resultSet').append(
                        $('<div/>').addClass('itemContainer col-xl-2 col-md-4 col-xs-12').attr({'id':data.response.hits.hits[i]._source.productid, 'sequence':''}).append(
                            $('<div/>').addClass('resultProducts example textCenter').append(
                                $('<img/>').css({'max-height':'100px','max-width':'100px'}).attr('src', data.response.hits.hits[i]._source.image)
                            ).append(
                                $('<p/>').addClass('resultsProductInfo').html(data.response.hits.hits[i]._source.name)
                            ).append(
                                $('<p/>').addClass('resultsProductSubInfo').html(data.response.hits.hits[i]._source.color)
                            ).append(
                                $('<p/>').addClass('resultsProductSubInfo').html(data.response.hits.hits[i]._source.size)
                            ).append(
                                $('<p/>').addClass('resultsProductSubInfo').html(data.response.hits.hits[i]._source.productid)
                            ).append(
                                $('<span/>').addClass('quickPromote').append(
                                    $('<i/>').addClass('icon wb-plus').attr('aria-hidden', 'true')
                                )
                            ).append(
                                $('<span/>').addClass('quickDemote').append(
                                    $('<i/>').addClass('icon wb-minus').attr('aria-hidden', 'true')
                                )
                            ).append(
                                $('<span/>').addClass('quickRemove').append(
                                    $('<i/>').addClass('icon wb-close').attr('aria-hidden', 'true')
                                )
                            )
                        )
                    );
                }

                initializeSortable(false);
            } else {
                //no search results
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