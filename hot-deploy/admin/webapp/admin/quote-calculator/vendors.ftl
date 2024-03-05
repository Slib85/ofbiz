<#if pageType?has_content>
    <input type="hidden" name="vendorId" value="${vendorId?if_exists}"/>
    <#if pageType == 'VendorStyleGroups'>
    <div class="page-header-override" style="display: none;">
        <h1 class="page-title">Admore - Style Groups</h1>
        <div class="page-header-actions">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/admin/control/main">Home</a></li>
                <li class="breadcrumb-item"><a href="/admin/control/qcVendors">Vendors</a></li>
                <li class="breadcrumb-item active">Admore</li>
            </ol>
        </div>
    </div>
    <#else>
    <div class="page-header-override" style="display: none;">
        <h1 class="page-title">Admore - Pricing Details</h1>
        <div class="page-header-actions">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/admin/control/main">Home</a></li>
                <li class="breadcrumb-item"><a href="/admin/control/qcVendors">Vendors</a></li>
                <li class="breadcrumb-item"><a href="/admin/control/qcVendors?vendorId=${vendorId}">Admore</a></li>
                <li class="breadcrumb-item active">Pricing Details</li>
            </ol>
        </div>
    </div>
    </#if>

    <script>
        $('.page-header').html($('.page-header-override').html());
    </script>
    <#if pageType == 'VendorStyleGroups'>
        <div class="modal fade" id="jqs-add-style-group-popup" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" class="panel-title">Style Groups</h4>
                        <br/>
                    </div>
                    <div class="modal-body row">
                        <div class="col-xs-5">
                            <div class="form-group">
                                <label class="control-label">Available Groups:</label>
                                <select name="available" class="form-control" style="height: 200px;" size="8">

                                </select>
                            </div>
                        </div>

                        <div class="col-xs-1" style="padding-top: 40px;padding-right: 54px;">
                            <button id="multiselect_rightAll" style="margin-bottom: 10px;visibility: hidden" class="btn btn-icon btn-success"><i class="icon fa-forward"></i></button>
                            <button id="addVendorStyleGroup" style="margin-bottom: 10px" class="btn btn-icon btn-success"><i class="icon fa-plus"></i></button>
                            <button id="removeVendorStyleGroup" style="margin-bottom: 10px" class="btn btn-icon btn-warning"><i class="icon fa-minus"></i></button>
                            <button id="multiselect_leftAll" style="margin-bottom: 10px;visibility: hidden" class="btn btn-icon btn-warning"><i class="icon fa-backward"></i></button>
                        </div>

                        <div class="col-xs-5">
                            <div class="form-group">
                                <label class="control-label">Assigned Groups:</label>
                                <select name="assigned" class="form-control" style="height: 200px;" size="8">

                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="jqs-done-item jqs-add btn btn-primary" onclick="$('#jqs-add-style-group-popup').modal('hide');">Done</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="add-item" style="float:right; margin: 14px">
                <a id="add-style-group-btn" class="panel-action" data-toggle="modal" title="Add Style Group"><button type="button" class="btn btn-success">Add / Remove Style Group</button></a>
            </div>
        </div>
        <#list styleGroups as group>
        <#if vendorPricingDetails??>
            <#assign pricingDetails = vendorPricingDetails[group.styleGroupId] />
        </#if>

        <div class="row">
            <div class="col-md-12">
                <div class="panel-group" aria-multiselectable="true" role="tablist">
                    <div class="panel">
                        <div class="panel-heading" role="tab">
                            <a class="panel-title collapsed" data-toggle="collapse" href="#${group.styleGroupId}" aria-expanded="false" aria-controls="${group.styleGroupId}">
                                <h4 class="" style="font-size: 1.4rem">${group.styleGroupName}</h4>
                            </a>
                        </div>
                        <div class="panel-collapse collapse" id="${group.styleGroupId}" aria-labelledby="${group.styleGroupId}" role="tabpanel" aria-expanded="true">
                            <div class="panel-body" style="padding-top: 0;margin-top: -15px;">
                                <div class="table-responsive" style="font-size: 14px;">
                                    <div class="row">
                                        <div style="float: right;padding: 0 13px 13px;">
                                            <button type="button" class="btn btn-dark" style="padding: 1px 5px 1px 5px;margin-bottom: -10px"><i class="icon wb-upload" aria-hidden="true"></i> Upload</button>
                                            <button type="button" class="btn btn-dark" style="padding: 1px 5px 1px 5px;margin-bottom: -10px"><i class="icon wb-download" aria-hidden="true"></i> Download</button>
                                        </div>

                                    </div>
                                    <table class="table jqs-pricing-details" data-role="content" data-plugin="selectable" data-row-selectable="true">
                                        <#if pricingDetails?has_content>
                                            <#list pricingDetails as pricingDetail>
                                                <#if pricingDetail_index == 0>
                                                    <thead class="bg-blue-grey-100">
                                                        <tr>
                                                            <th>Attribute</th>
                                                            <#list pricingDetail['quantityBreak'] as qty>
                                                                <th>${qty}</th>
                                                            </#list>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                </#if>
                                                        <tr>
                                                            <td style="text-align: left">${pricingDetail.attributeName}</td>
                                                            <#list pricingDetail['volumePrice'] as price>
                                                                <td>${price}</td>
                                                            </#list>
                                                        </tr>
                                                <#if pricingDetail_index == 0>
                                                    </tbody>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </#list>
    <#else>
        <div class="row">
            <div class="col-md-12">
                <div class="panel-group" id="exampleAccordionDefault" aria-multiselectable="true" role="tablist">
                    <div class="panel">
                        <div class="panel-heading" id="exampleHeadingDefaultOne" role="tab">
                            <a class="panel-title" data-toggle="collapse" href="#exampleCollapseDefaultOne" data-parent="#exampleAccordionDefault" aria-expanded="true" aria-controls="exampleCollapseDefaultOne">
                                Collapsible Group Item #1
                            </a>
                        </div>
                        <div class="panel-collapse collapse in" id="exampleCollapseDefaultOne" aria-labelledby="exampleHeadingDefaultOne" role="tabpanel" aria-expanded="true" style="">
                            <div class="panel-body">
                                De moveat laudatur vestra parum doloribus labitur sentire partes, eripuit praesenti
                                congressus ostendit alienae, voluptati ornateque accusamus
                                clamat reperietur convicia albucius, veniat quocirca vivendi
                                aristotele errorem epicurus. Suppetet. Aeternum animadversionis,
                                turbent cn partem porrecta c putamus diceret decore. Vero
                                itaque incursione.
                            </div>
                        </div>
                    </div>
                    <div class="panel">
                        <div class="panel-heading" id="exampleHeadingDefaultTwo" role="tab">
                            <a class="panel-title collapsed" data-toggle="collapse" href="#exampleCollapseDefaultTwo" data-parent="#exampleAccordionDefault" aria-expanded="false" aria-controls="exampleCollapseDefaultTwo">
                                Collapsible Group Item #2
                            </a>
                        </div>
                        <div class="panel-collapse collapse" id="exampleCollapseDefaultTwo" aria-labelledby="exampleHeadingDefaultTwo" role="tabpanel" aria-expanded="false" style="height: 0px;">
                            <div class="panel-body">
                                Praestabiliorem. Pellat excruciant legantur ullum leniter vacare foris voluptate
                                loco ignavi, credo videretur multoque choro fatemur mortis
                                animus adoptionem, bello statuat expediunt naturales frequenter
                                terminari nomine, stabilitas privatio initia paranda contineri
                                abhorreant, percipi dixerit incurreret deorsum imitarentur
                                tenetur antiopam latinam haec.
                            </div>
                        </div>
                    </div>
                    <div class="panel">
                        <div class="panel-heading" id="exampleHeadingDefaultThree" role="tab">
                            <a class="panel-title collapsed" data-toggle="collapse" href="#exampleCollapseDefaultThree" data-parent="#exampleAccordionDefault" aria-expanded="false" aria-controls="exampleCollapseDefaultThree">
                                Collapsible Group Item #3
                            </a>
                        </div>
                        <div class="panel-collapse collapse" id="exampleCollapseDefaultThree" aria-labelledby="exampleHeadingDefaultThree" role="tabpanel" aria-expanded="false">
                            <div class="panel-body">
                                Horum, antiquitate perciperet d conspectum locus obruamus animumque perspici probabis
                                suscipere. Desiderat magnum, contenta poena desiderant
                                concederetur menandri damna disputandum corporum equidem
                                cyrenaicisque. Defuturum ultimum ista ignaviamque iudicant
                                feci incursione, reprimique fruenda utamur tu faciam complexiones
                                eo, habeo ortum iucundo artes.
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#if>
<#else>
    <div class="row">
        <div class="col-md-12">
            <div class="panel" id="jqs-items-panel">
                <div class="panel-body">
                    <div class="panel-heading">
                        <h4 id="item-count" class="panel-title"><span>${itemsCount}</span> Vendor(s)</h4>
                    </div>
                    <div class="table-responsive">
                        <div class="row">
                            <div class="add-item">
                                <a class="panel-action" data-toggle="modal" data-target="#jqs-add-item"  title="Add Vendor"><button type="button" class="btn btn-success"><i class="icon wb-plus" aria-hidden="true"></i> Add Vendor</button></a>
                            </div>
                            <div class="filter">
                                <div class="form-group">
                                    <div class="input-group">
                                        <input id="filterBy" type="text" class="form-control" name="" placeholder="Search by Vendor ID ...">
                                        <span class="input-group-btn">
                                              <button type="submit" class="btn btn-primary"><i class="icon wb-search" aria-hidden="true"></i></button>
                                            </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table id="items-grid" class="table table-hover sortable paginated" data-role="content" data-plugin="selectable" data-row-selectable="true">
                            <thead class="bg-blue-grey-100">
                            <tr>
                                <th>Vendor ID</th>
                                <th>Name</th>
                                <th style="text-align: center">Active Flag</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list items as vendor>
                                <tr id="${vendor.vendorId}" style="display:none">
                                    <td>${vendor.vendorId}</td>
                                    <td>${vendor.vendorName}</td>
                                    <td align="center"><#if vendor.activeFlag?has_content>${vendor.activeFlag}<#else>N</#if></td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="jqs-dialogs-body">
        <div class="jqs-add-dialog modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add a Vendor</h4>
            </div>
            <form name="addForm" action="<@ofbizUrl>/qcSaveVendor</@ofbizUrl>" onsubmit="return false">
                <div class="modal-body row">
                    <div class="col-xs-12">
                        <div class="form-group has-feedback">
                            <label class="control-label">Vendor ID:</label>
                            <input type="text" class="form-control" name="vendorId">
                        <#--<input type="hidden" class="form-control" name="id">-->
                            <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label">Name:</label>
                            <input type="text" class="form-control" name="name">
                            <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label">Active:</label>
                            <select class="form-control" name="activeFlag">
                                <option value="Y" selected>Yes</option>
                                <option value="N">No</option>
                            </select>
                            <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="jqs-save-item jqs-add btn btn-primary">Add Vendor</button>
                </div>
            </form>
        </div>

        <div class="jqs-edit-dialog modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Edit Vendor</h4>
            </div>
            <form name="editForm" action="<@ofbizUrl>/qcSaveVendor</@ofbizUrl>" onsubmit="return false">
                <div class="modal-body row">
                    <div class="col-xs-12">
                        <div class="form-group has-feedback">
                            <label class="control-label">Vendor ID:</label>
                            <input type="text" readonly aria-readonly="true" class="form-control" name="vendorId">
                            <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label">Name:</label>
                            <input type="text" class="form-control" name="name">
                            <span class="glyphicon glyphicon-ok form-control-feedback hidden"></span>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label">Active:</label>
                            <select class="form-control" name="activeFlag">
                                <option value="">Select One</option>
                                <option value="Y">Yes</option>
                                <option value="N">No</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="jqs-save-item jqs-edit btn btn-primary">Edit Vendor</button>
                </div>
            </form>
        </div>

        <div class="jqs-view-dialog modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="jqs-vendor-name modal-title" id="myModalLabel"></h4>
            </div>
            <div>

            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Vendor ID:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-vendor-id"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Name:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-vendor-name"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Active:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-style-group-active-flag"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Created:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-vendor-created"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-sm-6" style="text-align: right">
                        <h5 class="inline-label">Last Updated:</h5>
                    </div>
                    <div class="col-md-8 col-sm-6">
                        <span class="jqs-vendor-updated"></span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row">
                    <div style="float: left"><button type="button" class="jqs-vendor-pricing btn btn-success waves-effect"><i class="icon fa-dollar" aria-hidden="true"></i> Pricing Details</button></div>
                    <div style="float: right"><button type="button" class="jqs-edit btn btn-primary waves-effect"><i class="icon fa-edit" aria-hidden="true"></i> Edit</button></div>
                </div>
            </div>
        </div>
    </div>
</#if>
<script src="/html/js/admin/quote-calculator/qcVendors.js"></script>



