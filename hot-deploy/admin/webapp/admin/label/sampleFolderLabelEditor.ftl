<style>

    .code {
        background-color:#EEEEEE;
        font-family:Consolas,Monaco,"Lucida Console","Liberation Mono","DejaVu Sans Mono","Bitstream Vera Sans Mono","Courier New";
    }
    .dialog-close-btn {
        position: absolute;
        top:17px;
        right:17px;
        cursor: pointer;
    }
    .job-data-panel, .label-data-panel {
        padding:20px;
    }

    .sample-label {
        height:375px;
        width:500px;
        padding: 120px 30px 30px 30px;
        background-color: #ffffff;
        margin:10px;
        float:left;
        font-family: Helvetica, Arial, sans-serif;
        font-weight:bold;
        font-size:15px;
    }
    .sample-label .job-num, .sample-label .style {
        font-size: 16px;
    }
    .sample-label .name {
        font-size:20px;
        height:50px;
        overflow: hidden;
        line-height:24px;
        margin-top:5px;
    }

    .sample-label .sub-item {
        margin-top: -5px;
    }

    .fl {
        float:left;
    }
    .fr {
        float:right;
    }

</style>

<div class="modal fade" id="jqs-label-data" role="dialog">
    <div class="modal-dialog jqs-label-data-modal" style="max-width: 750px">
        <div class="panel">
            <div class="panel-body container-fluid">
                <i class="dialog-close-btn icon wb-close pointer" aria-hidden="true" onclick="$('#jqs-label-data').modal('hide');"></i>
                <div class="row">
                    <div class="col-xl-6"><h3>Job# ${(jobNumber)?if_exists}</h3></div>
                    <div class="col-xl-6 text-xs-right"></div>
                </div>

                <div style="border-top:1px solid #e4eaec; padding-top: 50px;">
                    <form class="form-horizontal jqs-label-data-form">
                        <input type="hidden" name="jobNumber" value="${(jobNumber)?if_exists}" />
                        <input type="hidden" name="idx" value="-1" />
                        <div class="form-group row form-material row">
                            <label class="col-xs-12 col-md-3 form-control-label">Style: </label>
                            <div class="col-md-9 col-xs-12" style="top: -7px;">
                                <input type="text" class="form-control" name="style" placeholder="Style" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group row form-material row">
                            <label class="col-xs-12 col-md-3 form-control-label">Name: </label>
                            <div class="col-md-9 col-xs-12" style="top: -7px;">
                                <input type="text" class="form-control" name="name" placeholder="Name" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group row form-material row">
                            <label class="col-xs-12 col-md-3 form-control-label">Stock: </label>
                            <div class="col-md-9 col-xs-12" style="top: -7px;">
                                <input type="text" class="form-control" name="stock" placeholder="Stock" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group row form-material row">
                            <label class="col-xs-12 col-md-3 form-control-label">Print Method 1: </label>
                            <div class="col-md-9 col-xs-12" style="top: -7px;">
                                <input type="text" class="form-control" name="printMethod1" placeholder="Print Method 1" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group row form-material row">
                            <label class="col-xs-12 col-md-3 form-control-label">Print Method 2: </label>
                            <div class="col-md-9 col-xs-12" style="top: -7px;">
                                <input type="text" class="form-control" name="printMethod2" placeholder="Print Method 2" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group row form-material row">
                            <label class="col-xs-12 col-md-3 form-control-label">Print Method 3: </label>
                            <div class="col-md-9 col-xs-12" style="top: -7px;">
                                <input type="text" class="form-control" name="printMethod3" placeholder="Print Method 3" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group row form-material row">
                            <label class="col-xs-12 col-md-3 form-control-label">Coatings: </label>
                            <div class="col-md-9 col-xs-12" style="top: -7px;">
                                <textarea class="form-control" placeholder="Coatings" name="coatings" rows="3"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <br/>
                <div class="text-xs-right">
                    <div class="form-group row form-material row">
                        <div class="col-xs-12 col-md-9 offset-md-3">
                            <button type="button" class="btn btn-primary waves-effect jqs-label-data-submit">Submit </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row row-spacer ">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <form class="" name="productEditor" action="<@ofbizUrl>/sampleFolderLabelEditor</@ofbizUrl>" method="GET">
            <div class="input-group">
                <input type="text" class="form-control" name="id" placeholder="Search for Job #" value="${(jobNumber)?if_exists}">
                <span class="input-group-btn">
					<button class="btn btn-success" type="submit">GO</button>
				</span>
            </div>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>

<#if hasResult>
<div style="padding: 10px;">
    <div class="panel" id="jobDataOriginal">
        <div class="panel-body">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <div class="form-group">
                            <label class="control-label">Job Data (Original)</label>
                            <div class="code" style="border: 1px solid #e4eaec;min-height: 64px;padding: 10px;">
                                ${result.jobDataString}
                            </div>
                            <textarea name="jobDataOriginal" style="display: none">${result.jobDataString}</textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#--<div style="padding: 10px;">
    <div class="panel" id="jobDataOverride">
        <div class="panel-body">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <div class="form-group">
                            <label class="control-label">Job Data (Override)</label>
                            <textarea id="xyz" class="form-control code" style="resize: none;overflow: hidden" name="jobDataOverride" spellcheck="false" rows="4">${result.jobDataOverrideString?if_exists}</textarea>
                            <script>
                                $('textarea[name="jobDataOverride"]').get(0).style.height = $('textarea[name="jobDataOverride"]').get(0).scrollHeight + 'px';
                            </script>
                        </div>
                    </div>
                </div>
            </div>
            <div class="text-xs-right">
                <div class="form-group row form-material row">
                    <div class="col-xs-12 col-md-9 offset-md-3">
                        <button type="button" class="btn btn-primary waves-effect jqs-job-data-override">Submit </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>-->
<div style="padding: 10px;">
    <div class="panel" id="labelData">
        <div class="panel-body">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <div class="form-group">
                            <label class="control-label">Label Data</label>
                            <div class="code" style="border: 1px solid #e4eaec;min-height: 64px;padding: 10px;">
                            ${result.labelsDataString?if_exists}
                            </div>
                            <textarea name="labelsData" style="display: none">${result.labelsDataString?if_exists}</textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<span id="jqs-labels-data" style="display:none">${result.labelsDataString!}</span>
<div>
    <script>
        var labelsDataJSON = $('#jqs-labels-data').text() !== '' ? JSON.parse($('#jqs-labels-data').text()) : undefined;
    </script>
    <#list result.labelsData["LABELS_DATA"] as labelData>

        <div class="sample-label jqs-label" data-idx="${labelData_index}" onclick="$('#jqs-label-data').modal('show');">
            <div class="row">
                <div class="job-num fl">
                    Job# <span>${labelData.JOB_NUMBER?if_exists}</span>
                </div>
                <div class="style fr">
                    Style: <span>${labelData.STYLE?if_exists}</span>
                </div>
            </div>
            <div class="row name">
            ${labelData.NAME?if_exists}
            </div>
            <div class="row">
                <div class="stock">Paper: <span>${labelData.STOCK?if_exists}</span></div>
            </div>
            <div class="row">
                <div class="fl">Print Method: </div>
                <div class="fl">
                    <#list labelData.PRINT_METHOD_1?split(",") as printMethod>
                        <div <#if printMethod_index gt 0>class="sub-item"</#if>>${printMethod}</div>
                    </#list>
                    <#if labelData.PRINT_METHOD_2?has_content>
                        <div <#if labelData.PRINT_METHOD_1?has_content>class="sub-item"</#if>>${labelData.PRINT_METHOD_2?if_exists}</div>
                    </#if>
                    <#if labelData.PRINT_METHOD_3?has_content>
                        <div <#if labelData.PRINT_METHOD_1?has_content || labelData.PRINT_METHOD_2?has_content>class="sub-item"</#if>>${labelData.PRINT_METHOD_3?if_exists}</div>
                    </#if>
                </div>
            </div>
            <#if labelData.COATINGS?has_content>
                <div class="row">
                    <div class="fl">Coating: </div>
                    <div class="fl">
                        <#list labelData.COATINGS?split(",") as coating>
                            <div <#if coating_index gt 0>class="sub-item"</#if>>${coating}</div>
                        </#list>
                    </div>
                </div>
            </#if>
        </div>
    </#list>
    <div style="clear: both"></div>
</div>
</#if>
<script>
    var jobNumber = '${jobNumber?if_exists}';
    (function($) {
        $.extend({
            bindSubmitJobDataOverrideEvent: function() {
                $('.jqs-job-data-override').on('click', function() {
                    var jobDataOriginal = $('textarea[name="jobDataOriginal"]').get(0).value;
                    var jobDataOverride = $('textarea[name="jobDataOverride"]').get(0).value;
                    if(jobNumber !== '' && jobDataOriginal !== '' && jobDataOverride !== '') {
                        $.ajax({
                            type: 'POST',
                            data: {
                                'jobNumber' : jobNumber,
                                'jobDataOriginal' : jobDataOriginal,
                                'jobDataOverride' : jobDataOverride
                            },
                            async: false,
                            dataType: 'json',
                            url: '/admin/control/overrideFolderJobData'
                        }).done(function (data) {
                            if(data.success === true) {
                                window.location.reload();
                            } else {
                                alert('An error occurred while overriding the job data');
                            }
                        });
                    }
                });
            },
            bindEditLabelEvent: function() {
                $('.jqs-label').on('click', function() {
                    var el = $(this);
                    var idx = $(el).data("idx");
                    $('input[name="idx"]', '.jqs-label-data-modal').val(idx);
                    if(typeof labelsDataJSON !== 'undefined') {
                        var labelData = labelsDataJSON["LABELS_DATA"][idx];
                        $('input[name="style"]', '.jqs-label-data-modal').val(labelData.STYLE);
                        $('input[name="name"]', '.jqs-label-data-modal').val(labelData.NAME);
                        $('input[name="stock"]', '.jqs-label-data-modal').val(labelData.STOCK);
                        $('input[name="printMethod1"]', '.jqs-label-data-modal').val(labelData.PRINT_METHOD_1);
                        $('input[name="printMethod2"]', '.jqs-label-data-modal').val(labelData.PRINT_METHOD_2);
                        $('input[name="printMethod3"]', '.jqs-label-data-modal').val(labelData.PRINT_METHOD_3);
                        $('textarea[name="coatings"]', '.jqs-label-data-modal').val(labelData.COATINGS);
                    }
                });
            },
            bindUpdateNameEvent: function() {
                $('input[name="style"]', '.jqs-label-data-modal').on('blur', function() {
                    if($(this).val !== '') {
                        $.ajax({
                            type: 'GET',
                            data: {
                                'styleId' : $(this).val()
                            },
                            async: false,
                            dataType: 'json',
                            url: '/admin/control/getFolderName'
                        }).done(function (data) {
                            if(data.success === true && data.name !== '') {
                                $('input[name="name"]', '.jqs-label-data-modal').val(data.name);
                            }
                        });
                    }
                });
            },
            bindSubmitLabelDataEvent: function() {
                $('.jqs-label-data-submit').on('click', function() {
                    var labelData = $('.jqs-label-data-form').find('input,select,textarea').serializeObject();
                    $.ajax({
                        type: 'POST',
                        data: labelData,
                        async: false,
                        dataType: 'json',
                        url: '/admin/control/overrideFolderLabelData'
                    }).done(function (data) {
                        if(data.success === true) {
                            window.location.reload();
                        } else {
                            alert('An error occurred while overriding the label data');
                        }
                    });
                });
            },
            clearLabelData: function() {
                $('input[name="style"]', '.jqs-label-data-modal').val('');
                $('input[name="name"]', '.jqs-label-data-modal').val('');
                $('input[name="stock"]', '.jqs-label-data-modal').val('');
                $('input[name="printMethod1"]', '.jqs-label-data-modal').val('');
                $('input[name="printMethod2"]', '.jqs-label-data-modal').val('');
                $('input[name="printMethod3"]', '.jqs-label-data-modal').val('');
                $('textarea[name="coatings"]', '.jqs-label-data-modal').val('');
            }
        });
        $.bindSubmitJobDataOverrideEvent();
        $.bindEditLabelEvent();
        $.bindUpdateNameEvent();
        $.bindSubmitLabelDataEvent();
    })(jQuery);
</script>


