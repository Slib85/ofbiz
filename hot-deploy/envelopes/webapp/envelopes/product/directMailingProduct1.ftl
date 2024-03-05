<link href="<@ofbizContentUrl>/html/css/landing/directMailingProduct.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/product/product.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />

<span id="jqs-optionsJSON" class="hidden">${optionsJSON!}</span>
<span id="jqs-jobDataJSON" class="hidden">${jobDataJSON!}</span>
<script>var optionsJSON = $('#jqs-optionsJSON').text().trim();</script>
<script>var jobDataJSON = $.parseJSON($('#jqs-jobDataJSON').text().trim());console.log($('#jqs-jobDataJSON').text().trim())</script>
<script>var errorCode = '${errorCode!0}';</script>

<div class="product">
    <div class="margin-right-sm margin-top-xxs margin-left-sm tablet-desktop-only">
        <#include "../includes/breadcrumbs.ftl" />
    </div>
    <div class="productContent">
        <h2 property="name" class="fn jqs-productname">${product.getName()!}</h2>
        <div class="inline-list no-margin">
            <#if product.getReviews()??>
                <div class="jqs-starRating rating-${product.getRating()}"></div>
                <span class="jqs-noreview <#if product.getRating() != '0_0'>hidden</#if>"><a data-reveal-id="leave-a-review" href="#">Be the first to write a review.</a></span><span class="jqs-hasreview <#if product.getRating() == '0_0'>hidden</#if>"><a href="#reviews">(${product.getReviews().reviews?size}) Reviews</a></span>
            </#if>
            <span class="productStock jqs-inventoryDom margin-left-xxs"><i class="fa fa-check"></i> In Stock</span>
            <div class="pullRight width20">
                <div class="headerPrice">
                    <h4>$XXXX.XX</h4>
                    <p>(X,XXXX Quantity + $2.00 each)</p>
                </div>
                <div class="button directMailingHeaderButton">Save &amp; Exit</div>
            </div>
        </div>
        <div class="productContentLeft">
            <div class="productImageContainer">
                <div class="imageDisplay">
                    <i data-reveal-id="enhancedImage" class="fa fa-search-plus"></i>
                    <div id="enhancedImage" class="reveal-modal reveal-modal-limiter enhancedImage" data-reveal>
                        <div class="padding-bottom-xxs popup-title">
                            <h3 class="padding-left-xxs">${product.getName()!}</h3>
                            <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
                        </div>
                        <div class="enhancedImageBody">
                        <#--TODO-->
                            <img class="jqs-enhancedImage" alt="${product.getName()!}" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/43703?hei=600&amp;fmt=jpeg&amp;qlt=75&amp;bgc=f1f1f1" data-src="//actionenvelope.scene7.com/is/image/ActionEnvelope/43703?hei=600&amp;fmt=jpeg&amp;qlt=75&amp;bgc=f1f1f1" />
                        </div>
                    </div>
                <#--TODO-->
                    <img property="image" class="jqs-imagehero productImage photo" alt="${product.getName()!}" style="display: block;" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/43703?hei=413&amp;wid=510&amp;fmt=jpeg&amp;qlt=75&amp;bgc=f1f1f1" />
                </div>
                <div class="margin-top-xs">
                    <h4>Product Specs</h4>
                    <div class="margin-top-xxs jqs-productfeatures">
                        <div class="productSpecsRow">
                            <div class="specsCol1">SKU</div>
                            <div class="specsCol2" property="mpn">${product.getId()}</div>
                        </div>
                        <#if product.getFeatures()?has_content>
                            <#list (product.getFeatures()).keySet() as key>
                                <#if FEATURES_TO_SHOW.contains(key)?has_content>
                                    <#assign featureTypeDesc = Static["com.envelopes.product.ProductHelper"].getFeatureDescByType(delegator, key) />
                                    <div class="productSpecsRow">
                                        <div class="specsCol1">
                                        ${featureTypeDesc!}<#if featureTypeDesc?? && featureTypeDesc == "Paper Weight"> <i class="fa fa-question-circle" data-reveal-id="paperWeightInfo"></i></#if>
                                        </div>
                                        <div class="specsCol2">
                                        ${product.getFeatures().get(key)}<#if featureTypeDesc?? && featureTypeDesc == "Paper Weight"> <i class="fa fa-question-circle" data-reveal-id="paperWeightInfo"></i></#if>
                                        </div>
                                    </div>
                                </#if>
                            </#list>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
        <div class="productContentRight productSidebar">
            <p class="padding-left-xxs stepBack jqs-stepBack invisible"><i class="fa fa-caret-left"></i> Back</p>
            <p class="padding-right-xxs stepNext jqs-stepNext pullRight">Next <i class="fa fa-caret-right"></i></p>
            <div class="productSideBarSection margin-top-xxs" bns-selectproduct>
                <a class="pullRight" href="">Need Help?</a>
                <h4 class="sidebarHeader">Select Product</h4>
                <input type="hidden" name="productId" value="${product.getId()}" />
                <input type="hidden" name="documentClass" value="" />
                <input type="hidden" name="jobId" value="" />
                <input type="hidden" name="documentId" value="" />
                <input type="hidden" name="documentName" value="" />
                <input type="hidden" name="addressListId" value="" />
                <h5>1. Layout Options</h5>
                <div class="selectListParent sidebarToggle jqs-sidebarToggle " selection-name="layoutSelection" data-sidebar-name="sidebar-layoutOptions">
                    <div class="placeholder" data-value="">Select One</div>
                </div>
                <h5>2. Production Time</h5>
                <div class="selectListParent sidebarToggle jqs-sidebarToggle " selection-name="productionTime" data-sidebar-name="sidebar-productionTime">
                    <div class="placeholder" data-value="">Select One</div>
                </div>
                <h5>3. Print Color </h5>
                <div class="selectListParent sidebarToggle jqs-sidebarToggle " selection-name="printColor" data-sidebar-name="sidebar-printColor">
                    <div class="placeholder" data-value="">Select One</div>
                </div>
                <h5>4. Paper Color</h5>
                <div class="selectListParent sidebarToggle jqs-sidebarToggle " selection-name="paperColor" data-sidebar-name="sidebar-paperColor">
                    <div class="placeholder" data-value="">Select One</div>
                </div>
                <h5>5. Print Option</h5>
                <div class="selectListParent sidebarToggle jqs-sidebarToggle " selection-name="printOption" data-sidebar-name="sidebar-printOption">
                    <div class="placeholder" data-value="">Select One</div>
                </div>
                <h5>6. Number of Pages</h5>
                <div class="pageCount">
                    <div class="numberOfPages jqs-numberOfPages" data-value="1" data-max="7">
                        <div class="numberBackground"><i class="fa fa-minus jqs-minus"></i></div>
                        <div class="placeholder">1 Page</div>
                        <div class="numberBackground pullRight"><i class="fa fa-plus jqs-plus"></i></div>
                    </div>
                </div>
                <h5 class="margin-top-xs">Quantity &amp; Pricing:</h5>
                <div class="selectListParent sidebarToggle dmQuantity jqs-sidebarToggle" selection-name="quantity" data-sidebar-name="sidebar-quantity">
                    <div class="placeholder">50 Qty.</div>
                    <div class="quantityPrice">
                        <h4>$XXXX.XX</h4>
                        <p>(X,XXXX Quantity + $2.00 each)</p>
                    </div>
                </div>
            </div>
            <div class="productSideBarSection margin-top-xxs hidden" bns-selectdocument>
                <a class="pullRight" href="">Need Help?</a>
                <h4 class="sidebarHeader">Select Document</h4>
                <h5 class="margin-top-xxs">Upload or select a document.</h5>
                <form id="uploadDocumentFile" method="POST" action="<@ofbizUrl>/uploadDirectMailingDocumentFile</@ofbizUrl>" enctype="multipart/form-data">
                    <input type="file" name="fileUpload" class="jqs-fileupload" style="display: none;" />
                    <div class="directMailingUploadBox text-center filecontainer jqs-filecontainer" style="cursor: pointer;">
                        <div class="dropzone">
                            <i bns-dropzoneplaceholders class="fa fa-upload"></i>
                            <h5 bns-dropzoneplaceholders>Drag and Drop File to Upload</h5>
                            <p bns-dropzoneplaceholders class="margin-bottom-xxs">Select any .docx file</p>
                        </div>
                        <div style="color: #00a4e4;">Click here to browse</div>
                    </div>
                    <div class="uploadedDocument text-center filecontainer" style="display: none;" >
                        <span class="paddingLeft10 relative"><i class="fa fa-file-photo-o" style="color: #00a4e4;"></i></span>
                        <span  class="relative fileUploaded" ></span>
                    </div>
                </form>

                <div class="margin-top-xs loggedInUploadedFiles <#if !isLoggedInUser>hidden</#if>">
                    <div class="uploadHeader">
                        <p class="padding-left-xs" style="width: 180px;">Name</p>
                        <p>Edit History </p>
                    </div>
                    <div class="uploadBody" style="height: 150px; overflow: scroll;">
                    <#list documents as document>
                        <div class="filesAfterUpload margin-top-xxs" >
                            <input type="radio" class="radioButton" value="${document.documentId}" contentPath="${document.contentPath}" data-document-content="${document.contentName}">
                            <p class="directMailingContent" style="width: 180px;">${document.contentName}</p>
                            <p>${document.lastUpdatedStamp}</p>
                        </div>
                    </#list>
                    </div>
                    <a href="">Load More Files</a>
                </div>
                <div class="notLoggedIn text-center <#if isLoggedInUser>hidden</#if>">
                    <p>Looking for an existing document?</p>
                    <a href="#" bns-login>Log In</a>
                </div>
            </div>
            <div class="productSideBarSection margin-top-xxs hidden" bns-addressing>
                <a class="pullRight" href="">Need Help?</a>
                <h4 class="sidebarHeader">Select Address & Envelope</h4>
                <h5>Envelope</h5>
                <div class="selectListParent sidebarToggle jqs-sidebarToggle " selection-name="envelope" data-sidebar-name="sidebar-envelope">
                    <div class="placeholder" data-value="">Select One</div>
                </div>
                <h5>Mail Class</h5>
                <div class="selectListParent sidebarToggle jqs-sidebarToggle" selection-name="mailClass" data-sidebar-name="sidebar-mailClass">
                    <div class="placeholder" data-value="">Select One</div>
                </div>
                <h5 class="margin-top-xxs">Add your return address &amp; Mailing List.</h5>
                <div class="directMailingAddress">
                    <h5>Return Address</h5>
                    <textarea class="jqs-sidebarToggle" name="returnAddress" placeholder="Add Return Address" readonly data-sidebar-name="sidebar-returnAddress"></textarea>
                    <div style="color: #00a4e4;" class="jqs-sidebarToggle" data-sidebar-name="sidebar-returnAddress">Edit Return Address</div>
                </div>
                <div class="directMailingAddress margin-bottom-xxs">
                    <h5>Mailing List</h5>
                    <textarea name="mailingList" placeholder="Add Mailing List" readonly data-sidebar-name="sidebar-mailingList" bns-showAddressingGrid></textarea>
                    <div class="row">
                        <i bns-nextaddress class="pullRight fa fa-chevron-right margin-left-xxs" style="background-color: #949494; padding: 7px; color: #ffffff; cursor: pointer; font-size: 12px;"></i>
                        <i bns-previousaddress class="pullRight fa fa-chevron-left" style="background-color: #949494; padding: 7px; color: #ffffff; cursor: pointer; font-size: 12px;"></i>
                    </div>
                    <div style="color: #00a4e4;" bns-showAddressingGrid data-sidebar-name="sidebar-mailingList">Edit Mailing List</div>
                </div>
                <a class="mailingAddressTemplate" href=""><i class="fa fa-cloud-upload"></i> Download our Mailing Address Template</a>
            </div>
            <div class="productSideBarSection margin-top-xxs hidden" bns-proofproduct>
                <a class="pullRight" href="">Need Help?</a>
                <h4 class="sidebarHeader">Proof Product</h4>
                <h5 class="margin-top-xxs">Click download proof below to confirm that the layout and all information is correct and in its proper place.</h5>
                <div class="directMailingProof">
                    <h5>Return Address</h5>
                    <div class="button downloadProofButton margin-top-xxs">Download Proof</div>
                <#--<a href="">View More Proofs</a>-->
                </div>
                <div class="directMailingProofSubmission margin-bottom-xxs margin-top-xxs">
                    <div class="proofApproval">
                        <input type="checkbox">
                        <p>Yes, I have reviewed the proofs above and approve them.</p>
                    </div>
                    <div class="text-center proofInitials margin-top-xxs">
                        <p>Initials:</p>
                        <input >
                    </div>
                </div>
                <h4 class="sidebarHeader">Product Summary</h4>
                <div class="margin-top-xs directMailingProductSummary">
                    <div class="productSummaryLeft">
                        <h5>Product Details <i class="fa fa-pencil-square-o"></i></h5>
                        <p class="layoutOption"> Address on Separate Page</p>
                        <p class="productionTIme"> Next Day</p>
                        <p class="envelopeName"> #10 Double Window</p>
                        <p class="printColor"> Full Color</p>
                        <p class="paperColor"> Full Color</p>
                        <p class="printOption"> Printing One Side</p>
                    <#--<p class=""> 1 Page</p>-->
                        <p class="mailClass"> First Class</p>
                        <p class="margin-top-xs quantity"><span>50</span> Qty</p>
                    </div>
                    <div class="productSummaryRight">
                        <div>
                            <h5>Document <i class="fa fa-pencil-square-o"></i></h5>
                            <p class="documentName">Document Name</p>
                        </div>
                        <div>
                            <h5>Return Address <i class="fa fa-pencil-square-o"></i></h5>
                            <p class="rtnName"> Address on Separate Page</p>
                            <p class="rtnOrganization"> Acme Co.</p>
                            <p class="rtnAddress1"> 123 Main St.</p>
                            <p class="rtnAddress2"> Washington DC 20002</p>
                            <p class="rtnCity"> Washington DC 20002</p>
                            <p class="rtnState"> Washington DC 20002</p>
                            <p class="rtnZip"> Washington DC 20002</p>
                        </div>
                        <div>
                            <h5>Mailing List <i class="fa fa-pencil-square-o"></i></h5>
                            <p class="mailingListName">Mailing List Name</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="productSideBarSection">
                <div class="displayTable">
                    <h3>Total:</h3>
                    <div class="finalPrice">
                        <h4>$XXXX.XX</h4>
                        <p>(X,XXXX Quantity + $2.00 each)</p>
                    </div>
                </div>
                <div class="width100 margin-top-xxs">
                    <div class="button saveExitButton">Save &amp; Exit</div>
                    <div class="button nextButton">Next</div>
                </div>
            </div>
        </div>
    </div>
    <div id="sidebar-layoutOptions" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Layout Options</h4>
            </div>
        </div>
        <div class="colorTextureBody">
            <div class="colorTextureBodyInner">
                <div class="selectListItem jqs-selectListItem" selection-target="layoutSelection" bns-selection>
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30">
                            <span class="selectCheckbox"></span>
                        </div>
                        <div class="text-bold padding-left-xxs selectElement jqs-selectElement"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="sidebar-productionTime" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Production Time</h4>
            </div>
        </div>
        <div class="colorTextureBody">
            <div class="colorTextureBodyInner">
                <div class="selectListItem jqs-selectListItem" selection-target="productionTime" bns-selection>
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30">
                            <span class="selectCheckbox"></span>
                        </div>
                        <div class="text-bold padding-left-xxs selectElement jqs-selectElement"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="sidebar-envelope" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Envelope Type</h4>
            </div>
        </div>
        <div class="colorTextureBody">
            <div class="colorTextureBodyInner">
                <div class="selectListItem jqs-selectListItem" selection-target="envelope" bns-selection>
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30">
                            <span class="selectCheckbox"></span>
                        </div>
                        <div class="text-bold padding-left-xxs selectElement jqs-selectElement"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="sidebar-printColor" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Print Color</h4>
            </div>
            <div class="colorTextureBody">
                <div class="colorTextureBodyInner">
                    <div class="selectListItem jqs-selectListItem" selection-target="printColor" bns-selection>
                        <div class="displayTable">
                            <div selection-removeonselect class="padding-left-xxs width30">
                                <span class="selectCheckbox"></span>
                            </div>
                            <div class="text-bold padding-left-xxs selectElement jqs-selectElement"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="sidebar-paperColor" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Paper Color</h4>
            </div>
            <div class="colorTextureBody">
                <div class="colorTextureBodyInner">
                    <div class="selectListItem jqs-selectListItem" selection-target="paperColor" bns-selection>
                        <div class="displayTable">
                            <div selection-removeonselect class="padding-left-xxs width30">
                                <span class="selectCheckbox"></span>
                            </div>
                            <div class="text-bold padding-left-xxs selectElement jqs-selectElement"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="sidebar-printOption" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Print Options</h4>
            </div>
            <div class="colorTextureBody">
                <div class="colorTextureBodyInner" >
                    <div class="selectListItem jqs-selectListItem" selection-target="printOption" bns-selection>
                        <div class="displayTable">
                            <div selection-removeonselect class="padding-left-xxs width30">
                                <span class="selectCheckbox"></span>
                            </div>
                            <div class="text-bold padding-left-xxs selectElement jqs-selectElement"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="sidebar-mailClass" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Mail Class</h4>
            </div>
        </div>
        <div class="colorTextureBody">
            <div class="colorTextureBodyInner">
                <div class="selectListItem jqs-selectListItem" selection-target="mailClass" bns-selection>
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30">
                            <span class="selectCheckbox"></span>
                        </div>
                        <div class="text-bold padding-left-xxs selectElement jqs-selectElement"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="sidebar-quantity" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Quantity</h4>
            </div>
        </div>
        <div class="colorTextureBody">
            <div class="colorTextureBodyInner">
                <#assign qtys = [50, 100, 250, 500] />
                <#list qtys as qty>
                <div class="selectListItem" selection-target="quantity" bns-selection selection-selected="true">
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30">
                            <span class="selectCheckbox"></span>
                        </div>
                        <div class="text-bold padding-left-xxs">${qty}</div>
                    </div>
                </div>
                </#list>
            </div>
        </div>
    </div>
    <div id="sidebar-returnAddress" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Return Address</h4>
            </div>
        </div>
        <div class="colorTextureBody">
            <div class="colorTextureBodyInner">
                <textarea class="margin-top-xxs" name="returnAddress" placeholder="Add Return Address" readonly></textarea>
                <div>Name:</div>
                <input bns-returnaddressinput type="text" name="returnAddressName" value="" />
                <div>Organization/Company:</div>
                <input bns-returnaddressinput type="text" name="returnAddressCompany" value="" />
                <div>Address Line 1:</div>
                <input bns-returnaddressinput type="text" name="returnAddressAddress1" value="" />
                <div>Address Line 2:</div>
                <input bns-returnaddressinput type="text" name="returnAddressAddress2" value="" />
                <div>City:</div>
                <input bns-returnaddressinput type="text" name="returnAddressCity" value="" />
                <div>State:</div>
                <input bns-returnaddressinput type="text" name="returnAddressState" value="" />
                <div>Zip Code:</div>
                <input bns-returnaddressinput type="text" name="returnAddressZip" value="" />
                <div>Country:</div>
                <input bns-returnaddressinput type="text" name="returnAddressCountry" value="" />
            </div>
        </div>
    </div>
    <div id="documentModal" class="documentModal">
        <!-- Document Modal content -->
        <div class="modal-content">
            <span style="display: none" class="documentClose">&times;</span>
            <p>Please Wait. Document is Uploading..</p>
            <div id="documentProgressDiv" >
                <div id="documentProgressBar"></div>
            </div>
        </div>
    </div>
</div>


<link href="<@ofbizContentUrl>/html/css/util/slideIt.css</@ofbizContentUrl>?ts=2" rel="stylesheet" />
<script src="<@ofbizContentUrl>/html/js/util/slideIt.js</@ofbizContentUrl>?ts=1"></script>
<link href="<@ofbizContentUrl>/html/addressing/slideout.css</@ofbizContentUrl>?ts=3" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/addons/jquery.minicolors.min.css</@ofbizContentUrl>?ts=2" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/texel/texel.css</@ofbizContentUrl>?ts=4" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/addressing/grid1.min.css</@ofbizContentUrl>?ts=3" rel="stylesheet" />

<script src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.iframe-transport.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.fileupload.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/js/addons/jquery.minicolors.min.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/texel/jquery.env.queue.min.js</@ofbizContentUrl>?ts=3"></script>
<script src="<@ofbizContentUrl>/html/texel/jquery.texel.widget.min.js</@ofbizContentUrl>?ts=9"></script>
<script src="<@ofbizContentUrl>/html/lib/envelopes-widget-util.js</@ofbizContentUrl>?ts=4"></script>
<script src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.grid.js</@ofbizContentUrl>?ts=3"></script>
<script src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.grid.proxy.min.js</@ofbizContentUrl>?ts=4"></script>
<script src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.variabledata.min.js</@ofbizContentUrl>?ts=4"></script>
<script src="<@ofbizContentUrl>/html/js/product/directMailingProduct1.js</@ofbizContentUrl>"></script>
<script src="//fast.wistia.com/assets/external/E-v1.js" async></script>
<script src="<@ofbizContentUrl>/html/js/util/sidebarPanelSlideOut1.js</@ofbizContentUrl>"></script>


<script>


    var jobNumber = 0;
    var jobId = 0;
    var dataGroupId = -1;

    if (errorCode === '501') {
        alert(errorCode);
        $('.jqs-hidden-login-button').trigger('click');

    } else if (errorCode === '500') {

        alert(errorCode);
        window.location = '/' + websiteId + '/control/directMailing';

    } else {
        dataGroupId = jobDataJSON.dataGroupId;
        var productPage = $.getProductPageInstance();
        productPage.setProductOptions(optionsJSON);
        productPage.addProduct({
            'partyId': '${partyId!}',
            'productId': '${product.getId()}',
            'jobId': jobDataJSON.jobId,
            'jobNumber': jobDataJSON.jobNumber,
            'estimatedPrice': typeof jobDataJSON.estimatedPrice !== 'undefined' ? $.parseJSON(jobDataJSON.estimatedPrice) : undefined,
            'price': typeof jobDataJSON.price !== 'undefined' ? $.parseJSON(jobDataJSON.price) : undefined,
            'layout': jobDataJSON.layout,
            'productionTime': jobDataJSON.productionTime,
            'envelope': jobDataJSON.envelope,
            'color': jobDataJSON.color,
            'paperType': jobDataJSON.paperType,
            'printOption': jobDataJSON.printOption,
            'mailClass': jobDataJSON.mailClass,
            'quantity': jobDataJSON.quantity,
            'addressingData':jobDataJSON.addressingData,
            'dataGroupId':jobDataJSON.dataGroupId,
            'addressId': jobDataJSON.addressId,
            'documentId': jobDataJSON.documentId,
            'fileUpload': jobDataJSON.fileUpload,
            'returnAddress': jobDataJSON.returnAddress,
            //'addressListCreatedTimestamp':jobDataJSON.addressListCreatedTimestamp,
            'hasAddressModified':jobDataJSON.hasAddressModified,
            'numberOfPages':'1'
        });
        productPage.initPageActions();



        setTimeout(function(){
            $.createAddressingGrid(dataGroupId);
            $('#env-variabledata-grid').VariableDataGrid('applyEvent');
        }, 2000);
    }



    $('[bns-showAddressingGrid]').on('click', function() {

        $(this).parents('.sidebarPanel').animate({
            'opacity': '0',
            'left': '100%'
        }, 150, 'linear');

        $.showGrid();
    });
</script>