<link rel="stylesheet" href="/html/css/admin/pricing-engine/pricingEngineDashboard.css">
<form id="vendorChangeForm" method="GET" action="<@ofbizContentUrl>pricingEngineDashboard</@ofbizContentUrl>">
    <input type="hidden" name="vendorId" value="V_VENDOR_ID"/>
</form>
<form id="createQuote" method="POST" action="<@ofbizUrl>/createQuote</@ofbizUrl>" class="form-wizard">
    <input type="hidden" name="quoteRequestId" value="${(requestParameters.quoteRequestId)?if_exists}"/>
    <input type="hidden" name="vendorId" value="V_ADMORE"/>
    <div class="panel" id="quoteWizard">
        <div id="main-panel" class="panel-body">
            <div class="wizard-content">
                <div class="wizard-pane active jqs-CALCULATOR" id="tab-1" role="tabpanel">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label">VENDOR</label>
                                <select name="VENDOR" id="VENDOR" class="form-control jqs-vendor-dropdown" data-allow-clear="true" data-placeholder="SKU">
                                    <option>Select Vendor</option>
                                <#list vendors as vendor>
                                    <option value="${vendor.vendorId}">${vendor.vendorName}</option>
                                </#list>
                                </select>
                                <script>
                                    $('select[name="VENDOR"]').val('${vendorId}');
                                </script>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label">SKU</label>
                                <select name="SKU" class="form-control" data-allow-clear="true" data-placeholder="SKU">
                                    <option value="">Select SKU</option>
                                <#list vendorProducts as skudata>
                                    <option value=${skudata.get("vendorProductId")}>${skudata.get("productName")}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label">Color Group</label>
                                <select name="COLOR_GROUP" id="COLOR_GROUP" class="form-control jqs-color-dropdown"
                                        data-allow-clear="true" data-placeholder="Color Group">
                                    <option value="">Select Color Group</option>
                                <#list colorHierarchy.colorGroups as colorGroup>
                                    <option value="${colorGroup.value}">${colorGroup.text}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label">Color Name</label>
                                <select name="COLOR_NAME" class="form-control jqs-color-dropdown"
                                        data-allow-clear="true" data-placeholder="Color Name">
                                    <option value="">Select Color Name</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label">Paper Texture</label>
                                <select name="PAPER_TEXTURE" class="form-control jqs-color-dropdown"
                                        data-allow-clear="true" data-placeholder="Paper Texture">
                                    <option value="">Select Paper Texture</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label">Paper Weight</label>
                                <select name="PAPER_WEIGHT" class="form-control" data-allow-clear="true"
                                        data-placeholder="Paper Weight">
                                    <option value="">Select Paper Weight</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Custom Qty</label>
                                <input class="form-control" name="CUSTOM_QUANTITY" value="" placeholder="Custom Qty">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Primary Qty</label>
                                <input class="form-control" name="QUANTITIES" value="" placeholder="Primary Qty">
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <div class="form-group">
                                    <label class="control-label">Additional Qty</label>
                                    <input class="form-control" name="QUANTITIES" value="" placeholder="Additional Qty">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <label class="control-label">Additional Qty</label>
                                <input class="form-control" name="QUANTITIES" value="" placeholder="Additional Qty">
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <label class="control-label">Additional Qty</label>
                                <input class="form-control" name="QUANTITIES" value="" placeholder="Additional Qty">
                            </div>
                        </div>
                    </div>

                    <div class="row jqs-OFFSET">
                        <div class="col-md-12">
                            <div class="jqs-offset panel panel-bordered panel-dark is-collapse"
                                 style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="offset">Offset Printing</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                           aria-controls="offsetPrintingBody" aria-expanded="true"
                                           data-toggle="panel-collapse" href="#offsetPrintingBody"
                                           aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-6 jqs-SIDE1">
                                            <div class="panel panel-bordered panel-dark"
                                                 style="border:1px solid #dadada">
                                                <div class="panel-heading"
                                                     style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title"
                                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">
                                                        Side One</h3>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Print Method</label>
                                                                <select name="PRINT_METHOD" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Print Method">
                                                                    <option value="PMS">PMS Offset</option>
                                                                    <option value="FOUR_COLOR">4 CP Offset</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Number of Inks</label>
                                                                <select name="NUMBER_OF_INKS" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Number of Inks">
                                                                    <option value="1">1</option>
                                                                    <option value="2">2</option>
                                                                    <option value="3">3</option>
                                                                    <option value="4">4</option>
                                                                    <option value="5">5</option>
                                                                    <option value="6">6</option>
                                                                    <option value="7">7</option>
                                                                    <option value="8">8</option>
                                                                    <option value="9">9</option>
                                                                    <option value="10">10</option>
                                                                    <option value="11">11</option>
                                                                    <option value="12">12</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Heavy Coverage</label>
                                                                <select name="HEAVY_COVERAGE" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Heavy Coverage">
                                                                    <option value="N">No</option>
                                                                    <option value="Y">Yes</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Color Washes</label>
                                                                <select name="COLOR_WASHES" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Color Washes">
                                                                    <option value="0">0</option>
                                                                    <option value="1">1</option>
                                                                    <option value="2">2</option>
                                                                    <option value="3">3</option>
                                                                    <option value="4">4</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Plate Changes</label>
                                                                <select name="PLATE_CHANGES" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Plate Changes">
                                                                    <option value="0">0</option>
                                                                    <option value="1">1</option>
                                                                    <option value="2">2</option>
                                                                    <option value="3">3</option>
                                                                    <option value="4">4</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6 jqs-SIDE2">
                                            <div class="jqs-side2 panel panel-bordered panel-dark is-collapse"
                                                 style="border:1px solid #dadada">
                                                <div class="panel-heading"
                                                     style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="offsetside2"
                                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">
                                                        Side Two</h3>
                                                    <div class="panel-actions panel-actions-keep"
                                                         style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                                           aria-controls="offsetPrintingSide2Body" aria-expanded="true"
                                                           data-toggle="panel-collapse" href="#offsetPrintingSide2Body"
                                                           aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Print Method</label>
                                                                <select name="PRINT_METHOD" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Print Method">
                                                                    <option value="PMS">PMS Offset</option>
                                                                    <option value="FOUR_COLOR">4 CP Offset</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Number of Inks</label>
                                                                <select name="NUMBER_OF_INKS" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Number of Inks">
                                                                    <option value="1">1</option>
                                                                    <option value="2">2</option>
                                                                    <option value="3">3</option>
                                                                    <option value="4">4</option>
                                                                    <option value="5">5</option>
                                                                    <option value="6">6</option>
                                                                    <option value="7">7</option>
                                                                    <option value="8">8</option>
                                                                    <option value="9">9</option>
                                                                    <option value="10">10</option>
                                                                    <option value="11">11</option>
                                                                    <option value="12">12</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Heavy Coverage</label>
                                                                <select name="HEAVY_COVERAGE" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Heavy Coverage">
                                                                    <option value="N">No</option>
                                                                    <option value="Y">Yes</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Color Washes</label>
                                                                <select name="COLOR_WASHES" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Color Washes">
                                                                    <option value="0">0</option>
                                                                    <option value="1">1</option>
                                                                    <option value="2">2</option>
                                                                    <option value="3">3</option>
                                                                    <option value="4">4</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Plate Changes</label>
                                                                <select name="PLATE_CHANGES" class="form-control"
                                                                        data-allow-clear="true"
                                                                        data-placeholder="Plate Changes">
                                                                    <option value="0">0</option>
                                                                    <option value="1">1</option>
                                                                    <option value="2">2</option>
                                                                    <option value="3">3</option>
                                                                    <option value="4">4</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row jqs-FOIL-STAMP">
                        <div class="col-md-12">
                            <div class="jqs-foil-stamp panel panel-bordered panel-dark is-collapse"
                                 style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="foilstamp">Foil Stamping</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                           aria-controls="foilStampingBody" aria-expanded="true"
                                           data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="jqs-runs">
                                        <blockquote class="jqs-run blockquote custom-blockquote blockquote-success">
                                            <div class="run-label"
                                                 style="width:0;word-wrap:break-word;margin-left: -26px;font-size: 0.8em;position: absolute">
                                                RUN1
                                            </div>
                                            <div class="jqs-remove-addl-run text-xs-right">
                                                <i class="icon wb-close pointer jqs-remove-addl-run"
                                                   style="display: none;" aria-hidden="true"></i>
                                            </div>

                                            <div class="row jqs-images">
                                                <div class="jqs-image">
                                                    <div class="img-label input-group input-group-file">
                                                                <span class="input-group-btn">
                                                                  <span class="btn btn-primary">IMAGE #1</span>
                                                                </span>
                                                        <select name="IMAGES" class="form-control">
                                                            <option value="15">Up to 15 Sq. In.</option>
                                                            <option value="36">Up to 36 Sq. In.</option>
                                                            <option value="40">Up to 40 Sq. In.</option>
                                                            <option value="40+">Over 40 Sq. In.</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div style="height:30px">
                                                <div class="form-group pull-right clearfix margin-right-15">
                                                    <button type="button" style="position: relative; top:-2px"
                                                            class="jqs-add-image btn btn-primary addaction">Add Image
                                                    </button>
                                                </div>
                                            </div>
                                        </blockquote>
                                    </div>
                                    <div class="form-group pull-right clearfix margin-right-15">
                                        <button type="button" style="position: relative; top:10px;"
                                                class="jqs-add-run btn btn-success addaction">Add Run
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="row jqs-EMBOSS">
                        <div class="col-md-12">
                            <div class="jqs-emboss panel panel-bordered panel-dark is-collapse"
                                 style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="emboss">Embossing</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                           aria-controls="foilStampingBody" aria-expanded="true"
                                           data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-6 jqs-SIDE1">
                                            <div class="panel panel-bordered panel-dark"
                                                 style="border:1px solid #dadada">
                                                <div class="panel-heading"
                                                     style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title"
                                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">
                                                        Side One</h3>
                                                    <div class="panel-actions panel-actions-keep"
                                                         style="padding-right:0">
                                                    <#--<a class="jqs-panel-toggle panel-action icon wb-close" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>-->
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="jqs-runs">
                                                        <blockquote
                                                                class="jqs-run blockquote custom-blockquote blockquote-success">
                                                            <div class="run-label"
                                                                 style="width:0;word-wrap:break-word;margin-left: -26px;font-size: 0.8em;position: absolute">
                                                                RUN1
                                                            </div>
                                                            <div class="jqs-remove-addl-run text-xs-right">
                                                                <i class="icon wb-close pointer jqs-remove-addl-run"
                                                                   style="display: none;" aria-hidden="true"></i>
                                                            </div>
                                                            <div class="row jqs-images">
                                                                <div class="jqs-image">
                                                                    <div class="img-label input-group input-group-file">
                                                                            <span class="input-group-btn">
                                                                              <span class="btn btn-primary">IMAGE #1</span>
                                                                            </span>
                                                                        <select name="IMAGES" class="form-control">
                                                                            <option value="15">Up to 15 Sq. In.</option>
                                                                            <option value="36">Up to 36 Sq. In.</option>
                                                                            <option value="40">Up to 40 Sq. In.</option>
                                                                            <option value="40+">Over 40 Sq. In.</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div style="height:30px">
                                                                <div class="form-group pull-right clearfix margin-right-15">
                                                                    <button type="button"
                                                                            style="position: relative; top:-2px"
                                                                            class="jqs-add-image btn btn-primary addaction">
                                                                        Add Image
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </blockquote>
                                                    </div>
                                                    <div class="form-group pull-right clearfix margin-right-15">
                                                        <button type="button" style="position: relative; top:10px;"
                                                                class="jqs-add-run btn btn-success addaction">Add Run
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6 jqs-SIDE2">
                                            <div class="jqs-side2 panel panel-bordered panel-dark is-collapse"
                                                 style="border:1px solid #dadada">
                                                <div class="panel-heading"
                                                     style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="embossside2"
                                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">
                                                        Side Two</h3>
                                                    <div class="panel-actions panel-actions-keep"
                                                         style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                                           aria-controls="foilStampingBody" aria-expanded="true"
                                                           data-toggle="panel-collapse" href="#foilStampingBody"
                                                           aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="jqs-runs">
                                                        <blockquote
                                                                class="jqs-run blockquote custom-blockquote blockquote-success">
                                                            <div class="run-label"
                                                                 style="width:0;word-wrap:break-word;margin-left: -26px;font-size: 0.8em;position: absolute">
                                                                RUN1
                                                            </div>
                                                            <div class="jqs-remove-addl-run text-xs-right">
                                                                <i class="icon wb-close pointer jqs-remove-addl-run"
                                                                   style="display: none;" aria-hidden="true"></i>
                                                            </div>
                                                            <div class="row jqs-images">
                                                                <div class="jqs-image">
                                                                    <div class="img-label input-group input-group-file">
                                                                            <span class="input-group-btn">
                                                                              <span class="btn btn-primary">IMAGE #1</span>
                                                                            </span>
                                                                        <select name="IMAGES" class="form-control">
                                                                            <option value="15">Up to 15 Sq. In.</option>
                                                                            <option value="36">Up to 36 Sq. In.</option>
                                                                            <option value="40">Up to 40 Sq. In.</option>
                                                                            <option value="40+">Over 40 Sq. In.</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div style="height:30px">
                                                                <div class="form-group pull-right clearfix margin-right-15">
                                                                    <button type="button"
                                                                            style="position: relative; top:-2px"
                                                                            class="jqs-add-image btn btn-primary addaction">
                                                                        Add Image
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </blockquote>
                                                    </div>
                                                    <div class="form-group pull-right clearfix margin-right-15">
                                                        <button type="button" style="position: relative; top:10px;"
                                                                class="jqs-add-run btn btn-success addaction">Add Run
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row jqs-ADDON">
                        <div class="col-md-12 jqs-regular">
                            <div class="jqs-addon panel panel-bordered panel-dark is-collapse"
                                 style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="addon">Addons</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                           aria-controls="foilStampingBody" aria-expanded="true"
                                           data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-6  jqs-ADDON-COATING-SIDE1">
                                            <div class="jqs-addon-coating-side1 panel panel-bordered panel-dark is-collapse"
                                                 style="border:1px solid #dadada">
                                                <div class="panel-heading"
                                                     style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="addoncoatingside1"
                                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">
                                                        Coating - Side One</h3>
                                                    <div class="panel-actions panel-actions-keep"
                                                         style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                                           aria-controls="foilStampingBody" aria-expanded="true"
                                                           data-toggle="panel-collapse" href="#foilStampingBody"
                                                           aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="AQUEOUS_CHOICE"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Choice</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="LAMINATION_GLOSS"/><label
                                                                    style="padding-left: 20px;">Film Lamination -
                                                                Gloss</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="AQUEOUS_GLOSS"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Gloss</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="LAMINATION_MATTE"/><label
                                                                    style="padding-left: 20px;">Film Lamination -
                                                                Matte</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="AQUEOUS_MATTE"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Matte</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="LAMINATION_SOFT_TOUCH"/><label
                                                                    style="padding-left: 20px;">Film Lamination - Soft
                                                                Touch</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="AQUEOUS_SATIN"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Satin</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="SPOT_LAMINATION"/><label
                                                                    style="padding-left: 20px;">Spot Lamination</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="AQUEOUS_SOFT_TOUCH"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Soft</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="SPOT_UV"/><label
                                                                    style="padding-left: 20px;">Spot U/V</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="UV_COATING_GLOSS"/><label
                                                                    style="padding-left: 20px;">U/V Flood Coating -
                                                                Gloss</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_1"
                                                                       value="SPOT_STRIKE_MATTE"/><label
                                                                    style="padding-left: 20px;">Spot Strike-thru -
                                                                Matte</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6  jqs-ADDON-COATING-SIDE2">
                                            <div class="jqs-addon-coating-side2 panel panel-bordered panel-dark is-collapse"
                                                 style="border:1px solid #dadada">
                                                <div class="panel-heading"
                                                     style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="addoncoatingside2"
                                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">
                                                        Coating - Side Two</h3>
                                                    <div class="panel-actions panel-actions-keep"
                                                         style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                                           aria-controls="foilStampingBody" aria-expanded="true"
                                                           data-toggle="panel-collapse" href="#foilStampingBody"
                                                           aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="AQUEOUS_CHOICE"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Choice</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="LAMINATION_GLOSS"/><label
                                                                    style="padding-left: 20px;">Film Lamination -
                                                                Gloss</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="AQUEOUS_GLOSS"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Gloss</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="LAMINATION_MATTE"/><label
                                                                    style="padding-left: 20px;">Film Lamination -
                                                                Matte</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="AQUEOUS_MATTE"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Matte</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="LAMINATION_SOFT_TOUCH"/><label
                                                                    style="padding-left: 20px;">Film Lamination - Soft
                                                                Touch</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="AQUEOUS_SATIN"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Satin</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="SPOT_LAMINATION"/><label
                                                                    style="padding-left: 20px;">Spot Lamination</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="AQUEOUS_SOFT_TOUCH"/><label
                                                                    style="padding-left: 20px;">Aqueous Coating -
                                                                Soft</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="SPOT_UV"/><label
                                                                    style="padding-left: 20px;">Spot U/V</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="UV_COATING_GLOSS"/><label
                                                                    style="padding-left: 20px;">U/V Flood Coating -
                                                                Gloss</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="COATING_SIDE_2"
                                                                       value="SPOT_STRIKE_MATTE"/><label
                                                                    style="padding-left: 20px;">Spot Strike-thru -
                                                                Matte</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6  jqs-ADDON-ATTACHMENT-SIDE1">
                                            <div class="jqs-addon-attachment-side1 panel panel-bordered panel-dark is-collapse"
                                                 style="border:1px solid #dadada">
                                                <div class="panel-heading"
                                                     style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="addonattachmentside1"
                                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">
                                                        Attachments</h3>
                                                    <div class="panel-actions panel-actions-keep"
                                                         style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                                           aria-controls="foilStampingBody" aria-expanded="true"
                                                           data-toggle="panel-collapse" href="#foilStampingBody"
                                                           aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="ATTACHMENT_SIDE_1"
                                                                       value="VELCRO"/><label
                                                                    style="padding-left: 20px;">Velcro</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="ATTACHMENT_SIDE_1"
                                                                       value="BRASS_RINGS"/><label
                                                                    style="padding-left: 20px;">Brass Rings</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="ATTACHMENT_SIDE_1"
                                                                       value="TANG_STRIP"/><label
                                                                    style="padding-left: 20px;">Tang Strip</label>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6  jqs-ADDON-MISC-SIDE1">
                                            <div class="jqs-addon-misc-side1 panel panel-bordered panel-dark is-collapse"
                                                 style="border:1px solid #dadada">
                                                <div class="panel-heading"
                                                     style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="addonmiscside1"
                                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">
                                                        Other Addons</h3>
                                                    <div class="panel-actions panel-actions-keep"
                                                         style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus"
                                                           aria-controls="foilStampingBody" aria-expanded="true"
                                                           data-toggle="panel-collapse" href="#foilStampingBody"
                                                           aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="ADDON_SIDE_1"
                                                                       value="MISC1"/><label
                                                                    style="padding-left: 20px;">Miscellaneous Addon
                                                                1</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="checkbox" name="ADDON_SIDE_1"
                                                                       value="MISC2"/><label
                                                                    style="padding-left: 20px;">Miscellaneous Addon
                                                                2</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 jqs-REQUEST">
                            <div class="panel panel-bordered panel-dark" style="border:1px solid #dadada">
                                <div class="panel-heading" style="border-color: #4e97d9; background-color: #4e97d9">
                                    <h3 class="panel-title"
                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">Pricing
                                        Request</h3>
                                </div>
                                <div class="panel-body container">

                                </div>
                            </div>
                        </div>
                        <div class="col-md-12 jqs-RESPONSE">
                            <div class="jqs-side2 panel panel-bordered panel-dark" style="border:1px solid #dadada">
                                <div class="panel-heading" style="border-color: #4e97d9; background-color: #4e97d9">
                                    <h3 class="panel-title" data-panelname="offsetside2"
                                        style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">Pricing
                                        Response</h3>
                                </div>
                                <div class="panel-body container">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

<span id="jsq-color-hierarchy-response" class="hidden">${colorJSON!}</span>
<script>var colorJSON = $('#jsq-color-hierarchy-response').text();</script>

<script src="<@ofbizContentUrl>/html/js/admin/pricing-engine/pricingEngineDashboard.js</@ofbizContentUrl>"></script>