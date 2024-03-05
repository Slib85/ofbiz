<link rel="stylesheet" href="/html/css/admin/quote-calculator/calculator.css">
<#if requestParameters.quoteRequestId?has_content>
<div class="row" style="padding: 0 15px 15px 0;">
    <a href="<@ofbizUrl>/foldersQuoteView</@ofbizUrl>?quoteId=${(requestParameters.quoteRequestId)?if_exists}" class="pull-right tag tag-primary tag-lg">View Quote Request</a>
</div>
</#if>
<div class="modal fade" id="jqs-quote" role="dialog">
    <div class="modal-dialog jqs-quote-modal">
        <div class="panel">
            <div class="panel-body container-fluid">
                <div class="row">
                    <div class="col-lg-4 col-xs-12">
                        <div>
                            <div>Folders.com</div>
                            <div>105 Maxess Rd.</div>
                            <div>Suite S215</div>
                            <div>Melville, NY 11747</div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-xs-12">
                        <img style="height:75px;padding-bottom: 10px;padding-left:75px" class="m-r-10" src="//cdn.envelopemedia.com/html/img/logo/foldersNavy.png" alt="...">
                    </div>
                    <div class="col-xs-12 col-lg-4 text-xs-right">
                        <div>
                            <div>Toll-Free: 1 (800) 296-4321</div>
                            <div>service@folders.com</div>
                            <div>fax: 540-707-1793</div>
                        </div>
                    </div>
                </div>
                <h2 style="text-align: right">Quotation & Order Form</h2>
                <div class="row">
                    <div class="col-lg-6 col-xs-12">
                        <div>Quote Prepared For:</div>
                        <div class="jqs-prepared-for">
                            <div>Blacwood Systems Inc</div>
                            <div>178 Vincent Dr</div>
                            <div>East Meadow, NY 11554</div>
                            <div>Email: manu@blacwood.com</div>
                            <div>Phone: 631-746-5804</div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-lg-6 text-xs-right">
                        <div>
                            <div>Quote #: <span class="jqs-quote-num">FQT-xxxxxxx</span></div>
                            <div>Date: <span class="jqs-quote-created-date">06/27/2017</span></div>
                            <div>Prepared By: <span class="jqs-quote-created-by">Lee Wells</span></div>
                        </div>
                    </div>
                </div>
                <br/>
                <br/>

                <div class="page-invoice-table table-responsive">
                    <table  style="font-size: 12px" class="table table-hover text-xs-right">
                        <thead class="jqs-qty-breaks">
                        </thead>
                        <tbody class="jqs-quote-details">
                        <tr><td colspan="8"></td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="row">
                    <div class="jqs-quote-summary" style="padding:20px">

                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <button class="btn btn-default" onclick="$.showPriceBreakdown(this)">
                            <span>Details</span>
                        </button>
                    </div>
                    <div class="col-md-8 text-xs-right">
                        <button type="button" class="btn btn-primary jqs-generate-quote">
                            <span>Generate</span>
                        </button>
                        <button type="button" class="btn btn-primary jqs-generate-similar hidden">
                            <span>Generate Similar</span>
                        </button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="jqs-price-breakdown" role="dialog">
    <div class="modal-dialog jqs-quote-modal">
        <div class="panel">
            <div class="panel-body container-fluid">
                <h3>Quote Details</h3>
                <br/>
                <br/>
                <div class="page-invoice-table table-responsive">
                    <table  style="font-size: 12px" class="table table-hover text-xs-right">
                        <thead class="jqs-qty-breaks">
                        </thead>
                        <tbody class="jqs-pricing-details">
                        <tr><td colspan="8"></td></tr>
                        </tbody>
                    </table>
                </div>
                <br/>
                <div class="text-xs-right">
                    <button class="btn btn-animate btn-animate-side btn-primary" onclick="$.closePriceBreakdown(this)">
                        <span>Close</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade modal-success" id="messageDialog" role="dialog"">
<div class="modal-dialog modal-center">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="jqs-dialog-header modal-title">QUOTE CALCULATOR</h4>
        </div>
        <div class="modal-body">
            <p class="jqs-dialog-body"></p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
        </div>
    </div>
</div>
</div>
<form id="createQuote" method="POST" action="<@ofbizUrl>/createQuote</@ofbizUrl>" class="form-wizard">
    <input type="hidden" name="quoteRequestId" value="${(requestParameters.quoteRequestId)?if_exists}" />
    <div class="panel" id="quoteWizard">
        <div id="main-panel" class="panel-body">
            <div class="wizard-content">
                <div class="wizard-pane active jqs-CALCULATOR" id="tab-1" role="tabpanel">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="control-label">Folder Style</label><a class="jqs-regular" style="float: right;font-size: 11px;display: none" href="#">Regular</a>
                                <select name="STYLE" class="form-control" data-allow-clear="true" data-placeholder="Folder Style">
                                    <option value="">Select Folder Style</option>
                                    <option value="custom">Custom Folder Style</option>
                                    <#list styles as style>
                                        <option value="${style.styleId}">${style.styleId} - ${style.styleDescription}</option>
                                    </#list>
                                </select>
                                <input class="form-control jqs-hidden jqs-custom" style="display: none" name="CUSTOM_STYLE" value="" placeholder="Style">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label">Style Category</label><a class="jqs-regular" style="float: right;font-size: 11px;display: none" href="#">Regular</a>
                                <select name="STYLE_GROUP" class="form-control" data-allow-clear="true" data-placeholder="Style Category">
                                    <option value="">Select Style Category</option>
                                    <option value="custom">Custom Style Category</option>
                                <#list styleGroups as group>
                                    <option value="${group.styleGroupId}">${group.styleGroupName}</option>
                                </#list>
                                </select>
                                <input class="form-control jqs-hidden jqs-custom" style="display: none" name="CUSTOM_STYLE_GROUP" value="" placeholder="Style Category">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label">Vendor</label><a class="jqs-regular" style="float: right;font-size: 11px;display: none" href="#">Regular</a>
                                <select name="VENDOR" class="form-control" data-allow-clear="true" data-placeholder="Vendor">
                                    <option value="">Select Vendor</option>
                                    <option value="custom">Custom Vendor</option>
                                </select>
                                <input class="form-control jqs-hidden jqs-custom" style="display: none" name="CUSTOM_VENDOR" value="" placeholder="Vendor">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label">Stock Type</label><a class="jqs-regular" style="float: right;font-size: 11px;display: none" href="#">Regular</a>
                                <select name="STOCK_TYPE" class="form-control" data-allow-clear="true" data-placeholder="Stock Type">
                                    <option value="">Select Stock Type</option>
                                    <option value="custom">Custom Stock Type</option>
                                <#list stockTypes as stockType>
                                    <option value="${stockType.stockTypeId}">${stockType.stockTypeName}</option>
                                </#list>
                                </select>
                                <input class="form-control jqs-hidden jqs-custom" style="display: none" name="CUSTOM_STOCK_TYPE" value="" placeholder="Stock Type">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label">Stock</label><a class="jqs-regular" style="float: right;font-size: 11px;display: none" href="#">Regular</a>
                                <select name="STOCK" class="form-control" data-allow-clear="true" data-placeholder="Stock">
                                    <option value="">Select Stock</option>
                                    <option value="custom">Custom Stock</option>
                                <#list stocks as stock>
                                    <option value="${stock.stockId}">${stock.stockName}</option>
                                </#list>
                                </select>
                                <input class="form-control jqs-hidden jqs-custom" style="display: none" name="CUSTOM_STOCK" value="" placeholder="Stock">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Primary Quantity</label>
                                <input class="form-control" name="QUANTITIES" value="250" placeholder="Primary Quantity">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <div class="form-group">
                                    <label class="control-label">Additional Quantity</label>
                                    <input class="form-control" name="QUANTITIES" value="500" placeholder="Additional Quantity">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Additional Quantity</label>
                                <input class="form-control" name="QUANTITIES" value="1000" placeholder="Additional Quantity">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Additional Quantity</label>
                                <input class="form-control" name="QUANTITIES" value="2500" placeholder="Additional Quantity">
                            </div>
                        </div>
                    </div>
                    <div class="row jqs-CUSTOMER-ADDRESS">
                        <div class="col-md-12">
                            <div class="jqs-customer-address panel panel-bordered panel-dark is-collapse" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="address">Customer Address</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="control-label">Company Name</label>
                                                    <input class="form-control" name="companyName" value="${(quoteRequest.companyName)?if_exists}" placeholder="Company Name">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label">First Name</label>
                                                <input class="form-control" name="firstName" value="${(quoteRequest.firstName)?if_exists}" placeholder="First Name">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label">Last Name</label>
                                                <input class="form-control" name="lastName" value="${(quoteRequest.lastName)?if_exists}" placeholder="Last Name">
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="control-label">Address Line 1</label>
                                                <input class="form-control" name="address1" value="${(quoteRequest.address1)?if_exists}" placeholder="Address Line 1">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="control-label">Address Line 2</label>
                                                    <input class="form-control" name="address2" value="${(quoteRequest.address2)?if_exists}" placeholder="Address Line 2">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="control-label">City</label>
                                                <input class="form-control" name="city" value="${(quoteRequest.city)?if_exists}" placeholder="City">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="control-label">State</label>
                                                    <select class="form-control" name="stateProvinceGeoId">
                                                        <option value="">Select One</option>
                                                    ${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="control-label">ZIP Code *</label>
                                                <input class="form-control" name="postalCode" value="${(quoteRequest.postalCode)?if_exists}" placeholder="ZIP Code">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="control-label">Country</label>
                                                    <select class="form-control" name="countryGeoId" placeholder="Country">
                                                    ${screens.render("component://envelopes/widget/CommonScreens.xml#countries")}
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="control-label">E-Mail Address *</label>
                                                <input class="form-control" name="userEmail" value="${(quoteRequest.userEmail)?if_exists}" placeholder="E-Mail Address">
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="control-label">Phone Number *</label>
                                                    <input class="form-control" name="phone" value="${(quoteRequest.phone)?if_exists}" placeholder="Phone Number">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="control-label">Call ID Number</label>
                                                    <input class="form-control" name="additionalPhone" value="${(quoteRequest.additionalPhone)?if_exists}" placeholder="Additional Phone Number">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row jqs-OFFSET">
                        <div class="col-md-12">
                            <div class="jqs-offset panel panel-bordered panel-dark is-collapse" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="offset">Offset Printing</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="offsetPrintingBody" aria-expanded="true" data-toggle="panel-collapse" href="#offsetPrintingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-6 jqs-SIDE1">
                                            <div class="panel panel-bordered panel-dark" style="border:1px solid #dadada">
                                                <div class="panel-heading" style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">Side One</h3>
                                                    <#--<div class="panel-actions panel-actions-keep" style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-close" aria-controls="offsetPrintingSide1Body" aria-expanded="true" data-toggle="panel-collapse" href="#offsetPrintingSide1Body" aria-hidden="true"></a>
                                                    </div>-->
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Print Method</label>
                                                                <select name="PRINT_METHOD" class="form-control" data-allow-clear="true" data-placeholder="Print Method">
                                                                    <option value="PMS_OFFSET">PMS Offset</option>
                                                                    <option value="FOUR_CP">4 CP Offset</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Number of Inks</label>
                                                                <select name="NUMBER_OF_INKS" class="form-control" data-allow-clear="true" data-placeholder="Number of Inks">
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
                                                                <select name="HEAVY_COVERAGE" class="form-control" data-allow-clear="true" data-placeholder="Heavy Coverage">
                                                                    <option value="N">No</option>
                                                                    <option value="Y">Yes</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Color Washes</label>
                                                                <select name="COLOR_WASHES" class="form-control" data-allow-clear="true" data-placeholder="Color Washes">
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
                                                                <select name="PLATE_CHANGES" class="form-control" data-allow-clear="true" data-placeholder="Plate Changes">
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
                                            <div class="jqs-side2 panel panel-bordered panel-dark is-collapse" style="border:1px solid #dadada">
                                                <div class="panel-heading" style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="offsetside2" style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">Side Two</h3>
                                                    <div class="panel-actions panel-actions-keep" style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="offsetPrintingSide2Body" aria-expanded="true" data-toggle="panel-collapse" href="#offsetPrintingSide2Body" aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Print Method</label>
                                                                <select name="PRINT_METHOD" class="form-control" data-allow-clear="true" data-placeholder="Print Method">
                                                                    <option value="PMS_OFFSET">PMS Offset</option>
                                                                    <option value="FOUR_CP">4 CP Offset</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Number of Inks</label>
                                                                <select name="NUMBER_OF_INKS" class="form-control" data-allow-clear="true" data-placeholder="Number of Inks">
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
                                                                <select name="HEAVY_COVERAGE" class="form-control" data-allow-clear="true" data-placeholder="Heavy Coverage">
                                                                    <option value="N">No</option>
                                                                    <option value="Y">Yes</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-12">
                                                            <div class="form-group">
                                                                <label class="control-label">Color Washes</label>
                                                                <select name="COLOR_WASHES" class="form-control" data-allow-clear="true" data-placeholder="Color Washes">
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
                                                                <select name="PLATE_CHANGES" class="form-control" data-allow-clear="true" data-placeholder="Plate Changes">
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
                            <div class="jqs-foil-stamp panel panel-bordered panel-dark is-collapse" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="foilstamp">Foil Stamping</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="jqs-runs">
                                        <blockquote class="jqs-run blockquote custom-blockquote blockquote-success">
                                            <div class="run-label" style="width:0;word-wrap:break-word;margin-left: -26px;font-size: 0.8em;position: absolute">RUN1</div>
                                            <div class="jqs-remove-addl-run text-xs-right">
                                                <i class="icon wb-close pointer jqs-remove-addl-run" style="display: none;" aria-hidden="true"></i>
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
                                                    <button type="button" style="position: relative; top:-2px" class="jqs-add-image btn btn-primary addaction">Add Image</button>
                                                </div>
                                            </div>
                                        </blockquote>
                                    </div>
                                    <div class="form-group pull-right clearfix margin-right-15">
                                        <button type="button" style="position: relative; top:10px;" class="jqs-add-run btn btn-success addaction">Add Run</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="row jqs-EMBOSS">
                        <div class="col-md-12">
                            <div class="jqs-emboss panel panel-bordered panel-dark is-collapse" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="emboss">Embossing</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-6 jqs-SIDE1">
                                            <div class="panel panel-bordered panel-dark" style="border:1px solid #dadada">
                                                <div class="panel-heading" style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">Side One</h3>
                                                    <div class="panel-actions panel-actions-keep" style="padding-right:0">
                                                        <#--<a class="jqs-panel-toggle panel-action icon wb-close" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>-->
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="jqs-runs">
                                                        <blockquote class="jqs-run blockquote custom-blockquote blockquote-success">
                                                            <div class="run-label" style="width:0;word-wrap:break-word;margin-left: -26px;font-size: 0.8em;position: absolute">RUN1</div>
                                                            <div class="jqs-remove-addl-run text-xs-right">
                                                                <i class="icon wb-close pointer jqs-remove-addl-run" style="display: none;" aria-hidden="true"></i>
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
                                                                    <button type="button" style="position: relative; top:-2px" class="jqs-add-image btn btn-primary addaction">Add Image</button>
                                                                </div>
                                                            </div>
                                                        </blockquote>
                                                    </div>
                                                    <div class="form-group pull-right clearfix margin-right-15">
                                                        <button type="button" style="position: relative; top:10px;" class="jqs-add-run btn btn-success addaction">Add Run</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6 jqs-SIDE2">
                                            <div class="jqs-side2 panel panel-bordered panel-dark is-collapse" style="border:1px solid #dadada">
                                                <div class="panel-heading" style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="embossside2" style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">Side Two</h3>
                                                    <div class="panel-actions panel-actions-keep" style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="jqs-runs">
                                                        <blockquote class="jqs-run blockquote custom-blockquote blockquote-success">
                                                            <div class="run-label" style="width:0;word-wrap:break-word;margin-left: -26px;font-size: 0.8em;position: absolute">RUN1</div>
                                                            <div class="jqs-remove-addl-run text-xs-right">
                                                                <i class="icon wb-close pointer jqs-remove-addl-run" style="display: none;" aria-hidden="true"></i>
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
                                                                    <button type="button" style="position: relative; top:-2px" class="jqs-add-image btn btn-primary addaction">Add Image</button>
                                                                </div>
                                                            </div>
                                                        </blockquote>
                                                    </div>
                                                    <div class="form-group pull-right clearfix margin-right-15">
                                                        <button type="button" style="position: relative; top:10px;" class="jqs-add-run btn btn-success addaction">Add Run</button>
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
                            <div class="jqs-addon panel panel-bordered panel-dark is-collapse" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="addon">Coatings</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-6  jqs-ADDON-COATING-SIDE1">
                                            <div class="jqs-addon-coating-side1 panel panel-bordered panel-dark is-collapse" style="border:1px solid #dadada">
                                                <div class="panel-heading" style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="addoncoatingside1" style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">Coating - Side One</h3>
                                                    <div class="panel-actions panel-actions-keep" style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="AQUEOUS_CHOICE"/><label style="padding-left: 20px;">Aqueous Coating - Choice</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="LAMINATION_GLOSS"/><label style="padding-left: 20px;">Film Lamination - Gloss</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="AQUEOUS_GLOSS"/><label style="padding-left: 20px;">Aqueous Coating - Gloss</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="LAMINATION_MATTE"/><label style="padding-left: 20px;">Film Lamination - Matte</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="AQUEOUS_MATTE"/><label style="padding-left: 20px;">Aqueous Coating - Matte</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="LAMINATION_SOFT_TOUCH"/><label style="padding-left: 20px;">Film Lamination - Soft Touch</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="AQUEOUS_SATIN"/><label style="padding-left: 20px;">Aqueous Coating - Satin</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="SPOT_LAMINATION"/><label style="padding-left: 20px;">Spot Lamination</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="AQUEOUS_SOFT_TOUCH"/><label style="padding-left: 20px;">Aqueous Coating - Soft</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="SPOT_UV"/><label style="padding-left: 20px;">Spot U/V</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="UV_COATING_GLOSS"/><label style="padding-left: 20px;">U/V Flood Coating - Gloss</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_1" value="SPOT_STRIKE_MATTE"/><label style="padding-left: 20px;">Spot Strike-thru - Matte</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6  jqs-ADDON-COATING-SIDE2">
                                            <div class="jqs-addon-coating-side2 panel panel-bordered panel-dark is-collapse" style="border:1px solid #dadada">
                                                <div class="panel-heading" style="border-color: #4e97d9; background-color: #4e97d9">
                                                    <h3 class="panel-title" data-panelname="addoncoatingside2" style="font-size: 1.1rem;font-weight: 100; padding:10px 20px">Coating - Side Two</h3>
                                                    <div class="panel-actions panel-actions-keep" style="padding-right:0">
                                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="AQUEOUS_CHOICE"/><label style="padding-left: 20px;">Aqueous Coating - Choice</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="LAMINATION_GLOSS"/><label style="padding-left: 20px;">Film Lamination - Gloss</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="AQUEOUS_GLOSS"/><label style="padding-left: 20px;">Aqueous Coating - Gloss</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="LAMINATION_MATTE"/><label style="padding-left: 20px;">Film Lamination - Matte</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="AQUEOUS_MATTE"/><label style="padding-left: 20px;">Aqueous Coating - Matte</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="LAMINATION_SOFT_TOUCH"/><label style="padding-left: 20px;">Film Lamination - Soft Touch</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="AQUEOUS_SATIN"/><label style="padding-left: 20px;">Aqueous Coating - Satin</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="SPOT_LAMINATION"/><label style="padding-left: 20px;">Spot Lamination</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="AQUEOUS_SOFT_TOUCH"/><label style="padding-left: 20px;">Aqueous Coating - Soft</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="SPOT_UV"/><label style="padding-left: 20px;">Spot U/V</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="UV_COATING_GLOSS"/><label style="padding-left: 20px;">U/V Flood Coating - Gloss</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <div style="white-space: nowrap">
                                                                <input type="radio" name="COATING_SIDE_2" value="SPOT_STRIKE_MATTE"/><label style="padding-left: 20px;">Spot Strike-thru - Matte</label>
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
                        <div class="col-md-12 jqs-hidden jqs-custom" style="display: none;">
                            <div class="jqs-custom-addons panel panel-bordered panel-dark is-collapse" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="addon">Coatings</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="jqs-addons">
                                        <div class="row jqs-addon" style="padding: 10px 0;">
                                            <div class="col-md-10">
                                                <a class="jqs-regular" style="float: right;font-size: 11px;display: none" href="javascript:void(0)">Regular</a>
                                                <select name="ADDON" class="form-control" data-allow-clear="true" data-placeholder="Add-Ons">
                                                    <option value="">Select One</option>
                                                    <option value="custom">Custom Add-on</option>
                                                    <option value="AQUEOUS_CHOICE">Aqueous Coating - Choice</option>
                                                    <option value="AQUEOUS_GLOSS">Aqueous Coating - Gloss</option>
                                                    <option value="AQUEOUS_MATTE">Aqueous Coating - Matte</option>
                                                    <option value="AQUEOUS_SATIN">Aqueous Coating - Satin</option>
                                                    <option value="AQUEOUS_SOFT_TOUCH">Aqueous Coating - Soft Touch</option>
                                                    <option value="LAMINATION_GLOSS">Film Lamination - Gloss</option>
                                                    <option value="LAMINATION_MATTE">Film Lamination - Matte</option>
                                                    <option value="LAMINATION_SOFT_TOUCH">Film Lamination - Soft Touch</option>
                                                    <option value="SPOT_LAMINATION">Spot Lamination</option>
                                                    <option value="SPOT_UV">Spot U/V</option>
                                                    <option value="SPOT_STRIKE_MATTE">Spot Strike-thru - Matte</option>
                                                    <option value="UV_COATING_GLOSS">U/V Flood Coating - Gloss</option>
                                                </select>
                                                <input class="form-control jqs-hidden jqs-custom" style="display: none" name="CUSTOM_ADDON" value="" placeholder="Custon Addon">
                                            </div>
                                            <div class="col-md-1 text-xs-center" style="padding-top: 10px">
                                                <i class="icon wb-close pointer jqs-remove-addl-custom-addon" style="display: none;" aria-hidden="true"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group pull-right clearfix margin-right-15">
                                        <button type="button" style="position: relative; top:10px" class="btn btn-primary btn-info jqs-add-addl-custom-addon">Add Additional Addons</button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="jqs-VENDOR-COST row jqs-hidden" style="display: none">
                        <div class="col-md-12">
                            <div class="jqs-custom-vendor-cost panel panel-bordered panel-dark" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="markups">Vendor Cost</h3>
                                    <#--<div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>-->
                                </div>
                                <div class="panel-body">
                                    <div class="row jqs-vendor-cost">
                                        <div class="col-md-3 jqs-qty-break-1">
                                            <div class="jqs-lbl-text">
                                                <div class="ctl-label input-group input-group-file">
                                                        <span class="input-group-btn">
                                                          <span class="btn btn-primary text-xs-right">250</span>
                                                        </span>
                                                    <input class="form-control text-xs-right" name="qtyBreak1" value="0.00">
                                                </div>
                                            </div>

                                        </div>
                                        <div class="col-md-3 jqs-qty-break-2">
                                            <div class="jqs-lbl-text">
                                                <div class="ctl-label input-group input-group-file">
                                                        <span class="input-group-btn">
                                                          <span class="btn btn-primary text-xs-right">500</span>
                                                        </span>
                                                    <input class="form-control text-xs-right" name="qtyBreak2" value="0.00">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3 jqs-qty-break-3">
                                            <div class="jqs-lbl-text">
                                                <div class="ctl-label input-group input-group-file">
                                                        <span class="input-group-btn">
                                                          <span class="btn btn-primary text-xs-right">1,000</span>
                                                        </span>
                                                    <input class="form-control text-xs-right" name="qtyBreak3" value="0.00">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3 jqs-qty-break-4">
                                            <div class="jqs-lbl-text">
                                                <div class="ctl-label input-group input-group-file">
                                                        <span class="input-group-btn">
                                                          <span class="btn btn-primary text-xs-right">2,500</span>
                                                        </span>
                                                    <input class="form-control text-xs-right" name="qtyBreak4" value="0.00">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="jqs-MARKUP row">
                        <div class="col-md-12">
                            <div class="jqs-markups-and-discounts panel panel-bordered panel-dark is-collapse" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="markups">Markups & Discounts</h3>
                                    <div class="panel-actions panel-actions-keep">
                                        <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="jqs-markup-discounts">
                                        <div class="row jqs-markup-discount">
                                            <div class="col-md-3">
                                                <select name="MARKUP_DISCOUNT_TYPE" class="form-control" data-allow-clear="true" data-placeholder="Markup/Discount Type">
                                                    <option value="">Select One</option>
                                                    <option value="artChargeHours">Art Charge (Estimated Hours)</option>
                                                    <option value="markupPercentage">Custom Sales Markup (Percent)</option>
                                                    <option value="markupDollar">Custom Sales Markup (Dollar)</option>
                                                    <option value="discountPercentage">Custom Sales Discount (Percent)</option>
                                                    <option value="discountDollar">Custom Sales Discount (Dollar)</option>
                                                </select>
                                            </div>
                                            <div class="col-md-2 jqs-qty-break-1">
                                                <div class="jqs-lbl-text">
                                                    <div class="ctl-label input-group input-group-file">
                                                        <span class="input-group-btn">
                                                          <span class="btn btn-primary text-xs-right">250</span>
                                                        </span>
                                                        <input class="form-control text-xs-right" name="qtyBreak1" value="0.00" placeholder="Primary Quantity">
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="col-md-2 jqs-qty-break-2">
                                                <div class="jqs-lbl-text">
                                                    <div class="ctl-label input-group input-group-file">
                                                        <span class="input-group-btn">
                                                          <span class="btn btn-primary text-xs-right">500</span>
                                                        </span>
                                                        <input class="form-control text-xs-right" name="qtyBreak2" value="0.00" placeholder="Primary Quantity">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-2 jqs-qty-break-3">
                                                <div class="jqs-lbl-text">
                                                    <div class="ctl-label input-group input-group-file">
                                                        <span class="input-group-btn">
                                                          <span class="btn btn-primary text-xs-right">1,000</span>
                                                        </span>
                                                        <input class="form-control text-xs-right" name="qtyBreak3" value="0.00" placeholder="Primary Quantity">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-2 jqs-qty-break-4">
                                                <div class="jqs-lbl-text">
                                                    <div class="ctl-label input-group input-group-file">
                                                        <span class="input-group-btn">
                                                          <span class="btn btn-primary text-xs-right">2,500</span>
                                                        </span>
                                                        <input class="form-control text-xs-right" name="qtyBreak4" value="0.00" placeholder="Primary Quantity">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-1 text-xs-center" style="padding-top: 10px">
                                                <i class="icon wb-close pointer jqs-remove-addl-markup-discount" style="display: none;" aria-hidden="true"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group pull-right clearfix margin-right-15">
                                        <button type="button" style="position: relative; top:10px" class="btn btn-primary btn-info jqs-add-addl-markup-discount">Add Additional Markup/Discount</button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row jqs-QUOTE-COMMENTS">
                        <div class="col-md-12">
                            <div class="jqs-quote-comments panel panel-bordered panel-dark" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="comments">Comments</h3>
                                <#--<div class="panel-actions panel-actions-keep">
                                    <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                </div>-->
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="control-label">Quote Comments</label>
                                                    <textarea class="form-control" name="quoteComments"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row jqs-QUOTE-PRODUCTION">
                        <div class="col-md-12">
                            <div class="jqs-quote-production panel panel-bordered panel-dark" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="production">Production Time</h3>
                                <#--<div class="panel-actions panel-actions-keep">
                                    <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                </div>-->
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="control-label">Production Time</label>
                                                    <textarea class="form-control" name="quoteProduction" maxlength="255"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row jqs-QUOTE-INTERNALCOMMENT">
                        <div class="col-md-12">
                            <div class="jqs-quote-production panel panel-bordered panel-dark" style="border:1px solid #526069">
                                <div class="panel-heading">
                                    <h3 class="panel-title" data-panelname="internalComment">Internal Comments</h3>
                                <#--<div class="panel-actions panel-actions-keep">
                                    <a class="jqs-panel-toggle panel-action icon wb-plus" aria-controls="foilStampingBody" aria-expanded="true" data-toggle="panel-collapse" href="#foilStampingBody" aria-hidden="true"></a>
                                </div>-->
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="control-label">Internal Comments</label>
                                                    <textarea class="form-control" name="quoteInternalComments"></textarea>
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
    </div>
</form>

<div class="row" style="position: fixed; height:98px; bottom:-4px;width:91%;padding-right:25px;background-color: #e6e6e6;z-index: 1000">
    <a class="jqs-show-quote panel-action" style="cursor: pointer; " data-toggle="modal"  data-target="#"  title="Show Quote">
    <div class="col-xl-3 col-md-3 col-xs-12">
        <div class="card card-inverse card-primary jqs-summary-1" style="border-left:40px solid #76838f">
            <div class="card-block text-xs-right" style="height: 110px;">
                <div class="jqs-qty-tile">250</div>
                <div class="card-text"><span class="jqs-total-tile">$ 0.00</span><span class="jqs-each"></span></div>
            </div>
        </div>
    </div>
    <div class="col-xl-3 col-md-3 col-xs-12">
        <div class="card card-inverse card-primary jqs-summary-2" style="border-left:40px solid #76838f">
            <div class="card-block text-xs-right" style="height: 110px;">
                <div class="jqs-qty-tile">500</div>
                <div class="card-text"><span class="jqs-total-tile">$ 0.00</span><span class="jqs-each"></span></div>
            </div>
        </div>
    </div>
    <div class="col-xl-3 col-md-3 col-xs-12">
        <div class="card card-inverse card-primary jqs-summary-3" style="border-left:40px solid #76838f">
            <div class="card-block text-xs-right" style="height: 110px;">
                <div class="jqs-qty-tile">1000</div>
                <div class="card-text"><span class="jqs-total-tile">$ 0.00</span><span class="jqs-each"></span></div>
            </div>
        </div>
    </div>
    <div class="col-xl-3 col-md-3 col-xs-12">
        <div class="card card-inverse card-primary jqs-summary-4" style="border-left:40px solid #76838f">
            <div class="card-block text-xs-right" style="height: 110px;">
                <div class="jqs-qty-tile">2500</div>
                <div class="card-text"><span class="jqs-total-tile">$ 0.00</span><span class="jqs-each"></span></div>
            </div>
        </div>
    </div>
    </a>
</div>
<#--###################################################################-->
<#--###################################################################-->
<#--####Hack to avoid freemarker encoding of javascript variables######-->
<#--###################################################################-->
<#--###################################################################-->
<span id="jqs-pricing-request" class="hidden">${pricingRequest!}</span>
<script>var quoteId = '${quoteId!}'; var comment = "${comment?default("")}"; var internalComment = "${internalComment?default("")}"; var production = "${production?default("")}"; var pricingRequest = $('#jqs-pricing-request').text();</script>
<span id="jqs-pricing-response" class="hidden">${pricingResponse!}</span>
<script>var pricingResponse = $('#jqs-pricing-response').text();</script>
<#--###################################################################-->
<#--###################################################################-->
<#--###################################################################-->
<#--###################################################################-->
<script src="<@ofbizContentUrl>/html/js/admin/quote-calculator/calculator.js</@ofbizContentUrl>?ts=8"></script>

<script>
    var session = {
        'VENDOR' : 'Admore',
        'STYLE_GROUP' : 'LARGE',
        'STYLE' : '0806',
        'MATERIAL_TYPE' : 'PAPER',
        'STOCK_TYPE' : 'GROUP_A',
        'STOCK' : '#100 Smooth White',
        'QUANTITIES' : [1000, 2500, 5000, 10000],
        'PRINT_OPTIONS' : [
            {
                'PRINT_OPTION_NAME' : 'OFFSET',
                'SIDES' : [
                    {
                        'PRINT_METHOD' : 'PMS_OFFSET',
                        'NUMBER_OF_INKS' : 12,
                        'HEAVY_COVERAGE' : 'Y',
                        'COLOR_WASHES' : 0,
                        'PLATE_CHANGES' : 0
                    },
                    {
                        'PRINT_METHOD' : 'FOUR_CP',
                        'NUMBER_OF_INKS' : 3,
                        'HEAVY_COVERAGE' : 'Y',
                        'COLOR_WASHES' : 0,
                        'PLATE_CHANGES' : 0
                    }
                ]
            },
            {
                'PRINT_OPTION_NAME' : 'FOIL_STAMPING',
                'RUNS' : [
                    {
                        'IMAGES' : ['15', '36', '40', '40+', '15', '15', '15', '15', '15', '15']
                    },
                    {
                        'IMAGES' : ['15', '36', '40', '40+', '15', '15', '15', '15', '15', '15']
                    },
                    {
                        'IMAGES' : ['15', '36', '40', '40+', '15', '15', '15', '15', '15', '15']
                    },
                    {
                        'IMAGES' : ['15', '36', '40', '40+', '15', '15', '15', '15', '15', '15']
                    },
                    {
                        'IMAGES' : ['15', '36', '40', '40+', '15', '15', '15', '15', '15', '15']
                    }
                ]
            },
            {
                'PRINT_OPTION_NAME' : 'EMBOSSING',
                'SIDES' : [
                    {
                        'RUNS' : [
                            {
                                'IMAGES' : ['15', '36', '40', '40+', '15', '15', '15', '15', '15', '15']
                            },
                            {
                                'IMAGES' : ['15', '36', '40', '40+', '15', '15', '15', '15', '15', '15']
                            }
                        ]
                    },
                    {
                        'RUNS' : [
                            {
                                'IMAGES' : ['15', '36', '40', '40+', '15', '15', '15', '15', '15', '15']
                            },
                            {
                                'IMAGES' : ['15', '36', '40', '40+', '15', '15', '15', '15', '15', '15']
                            }
                        ]
                    }
                ]

            }
        ],
        'ADDONS' : [
            {
                'ADDON_NAME': 'COATING',
                'SIDES': ['AQUEOUS_CHOICE', 'AQUEOUS_MATTE']
            }
        ],
        'MARKUPS_AND_DISCOUNTS' : [
            {
                'MARKUP_DISCOUNT_TYPE' : '',
                'MARKUP_DISCOUNT_VALUE' : []
            },
            {
                'MARKUP_DISCOUNT_TYPE' : '',
                'MARKUP_DISCOUNT_VALUE' : []
            },
            {
                'MARKUP_DISCOUNT_TYPE' : '',
                'MARKUP_DISCOUNT_VALUE' : []
            },
            {
                'MARKUP_DISCOUNT_TYPE' : '',
                'MARKUP_DISCOUNT_VALUE' : []
            },
            {
                'MARKUP_DISCOUNT_TYPE' : '',
                'MARKUP_DISCOUNT_VALUE' : []
            }
        ]
    };
</script>