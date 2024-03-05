<link href="<@ofbizContentUrl>/html/css/landing/directMailingProduct.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/product/product.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />

<span id="jqs-optionsJSON" class="hidden">${optionsJSON!}</span>
<span id="jqs-jobDataJSON" class="hidden">${jobDataJSON!}</span>
<script>
    var optionsJSON = $('#jqs-optionsJSON').text().trim();
    var jobDataJSON = $.parseJSON($('#jqs-jobDataJSON').text().trim());
    //console.log($('#jqs-jobDataJSON').text().trim());
    var errorCode = '${errorCode!0}';
</script>

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
            <div class="textRight">
                <!-- <div class="headerPrice">
                    <h4>$XXXX.XX</h4>
                    <p>(X,XXXX Quantity + $2.00 each)</p>
                </div> -->
                <div class="headerRightContent">
                    <h5><i class=" fa fa-check"></i> Orders placed by 8pm EST, mail out the Next Business Day</h5>
                    <h5><i class="fa fa-check"></i> Pricing includes postage</h5>
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

                            <img class="jqs-enhancedImage" alt="${product.getName()!}" src="<#if product.getId() == 'C2M-L85X11'>/html/img/product/C2M-L85X11_address_on_separate_page.png<#elseif product.getId() == 'C2M-N425X55'>/html/img/product/C2M-N425X55_single_sided.png<#elseif product.getId() == 'C2M-FN425X55'>/html/img/product/C2M-FN425X55_folded.png</#if>" />
                        </div>
                    </div>

                    <img property="image" class="jqs-imagehero productImage photo" alt="${product.getName()!}" style="display: block;" src="<#if product.getId() == 'C2M-L85X11'>/html/img/product/C2M-L85X11_address_on_separate_page.png<#elseif product.getId() == 'C2M-N425X55'>/html/img/product/C2M-N425X55_single_sided.png<#elseif product.getId() == 'C2M-FN425X55'>/html/img/product/C2M-FN425X55_folded.png</#if>" />
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
                                    <div class="specsCol2 <#if featureTypeDesc?? && featureTypeDesc == "Size">jqs-dmSize</#if>">${product.getFeatures().get(key)}<#if featureTypeDesc?? && featureTypeDesc == "Paper Weight"> <i class="fa fa-question-circle" data-reveal-id="paperWeightInfo"></i></#if></div>
                                </div>
                            </#if>
                        </#list>
                    </#if>
                    </div>
                    <#if product.getId() == "C2M-L85X11">
                    <div class="downloadTemplate">
                        <a bns-updateTemplateLink href="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-letterSeparate.zip&amp;downLoad=Y"><i class="fa fa-cloud-download"></i>Download Product Templates (.zip)</a>
                    </div>
                    <#elseif product.getId() == "C2M-N425X55">
                    <div class="downloadTemplate">
                        <a href="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-notecardSingle.zip&amp;downLoad=Y"><i class="fa fa-cloud-download"></i>Download Product Templates (.zip)</a>
                    </div>
                    <#else>
                    <div class="downloadTemplate">
                        <a href="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-notecardDouble.zip&amp;downLoad=Y"><i class="fa fa-cloud-download"></i>Download Product Templates (.zip)</a>
                    </div>
                    </#if>
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
                <h5 class="margin-top-xs">Quantity &amp; Pricing*: <span style="color: #009cdd; font-size: 14px; display: block;">*Includes Postage, Printing and Mailing Services</span></h5>
                <div class="selectListParent sidebarToggle dmQuantity jqs-sidebarToggle" selection-name="quantity" data-sidebar-name="sidebar-quantity">
                    <div>
                        <div class="placeholder">50 Qty.</div>
                        <div class="quantityPrice">
                            <h4>$XXXX.XX</h4>
                            <p>(X,XXXX Quantity + $2.00 each)</p>
                        </div>
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
                <#if product.getId() == "C2M-L85X11">
                    <div class="downloadTemplate">
                        <a bns-updateTemplateLink href="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-letterSeparate.zip&amp;downLoad=Y"><i class="fa fa-cloud-download"></i>Download Product Templates (.zip)</a>
                    </div>
                    <#elseif product.getId() == "C2M-N425X55">
                    <div class="downloadTemplate">
                        <a href="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-notecardSingle.zip&amp;downLoad=Y"><i class="fa fa-cloud-download"></i>Download Product Templates (.zip)</a>
                    </div>
                    <#else>
                    <div class="downloadTemplate">
                        <a href="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-notecardDouble.zip&amp;downLoad=Y"><i class="fa fa-cloud-download"></i>Download Product Templates (.zip)</a>
                    </div>
                </#if>
                <div class="jqs-logged-in margin-top-xs loggedInUploadedFiles <#if !showSavedDocuments>hidden</#if>">
                    <div class="uploadHeader">
                        <p class="padding-left-xs" style="width: 180px;">Name</p>
                        <p>Edit History </p>
                    </div>
                    <div class="jqs-upload-body uploadBody" style="height: 150px; overflow: scroll;">
                        <div class="jqs-saved-file-template filesAfterUpload margin-top-xxs hidden" >
                            <input type="radio" class="radioButton" value="" contentPath="">
                            <p class="jqs-content-name directMailingContent" style="width: 180px;"></p>
                            <p class="jqs-updated-time"></p>
                        </div>
                    <#list documents as document>
                        <div class="filesAfterUpload margin-top-xxs" >
                            <input type="radio" class="radioButton" value="${document.documentId}" contentPath="${document.contentPath}" data-document-content="${document.contentName}">
                            <p class="directMailingContent" style="width: 180px;">${document.contentName}</p>
                            <p>${document.lastUpdatedStamp}</p>
                        </div>
                    </#list>
                    </div>
                    <#--<a href="">Load More Files</a>-->
                </div>
                <div class="jqs-not-logged-in notLoggedIn text-center <#if showSavedDocuments>hidden</#if>">
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
                    <textarea class="jqs-sidebarToggle" name="returnAddress" placeholder="Click to Enter" readonly data-sidebar-name="sidebar-returnAddress"></textarea>
                    <div style="color: #00a4e4;" class="jqs-sidebarToggle" data-sidebar-name="sidebar-returnAddress">Edit Return Address</div>
                </div>
                <div class="directMailingAddress margin-bottom-xxs">
                    <h5>Mailing List</h5>
                    <textarea name="mailingList" placeholder="Click to upload Mailing List" readonly data-sidebar-name="sidebar-mailingList" bns-showAddressingGrid></textarea>
                    <div class="row">
                        <i bns-nextaddress class="pullRight fa fa-chevron-right margin-left-xxs" style="background-color: #949494; padding: 7px; color: #ffffff; cursor: pointer; font-size: 12px;"></i>
                        <i bns-previousaddress class="pullRight fa fa-chevron-left" style="background-color: #949494; padding: 7px; color: #ffffff; cursor: pointer; font-size: 12px;"></i>
                    </div>
                    <div style="color: #00a4e4;" bns-showAddressingGrid data-sidebar-name="sidebar-mailingList">Edit Mailing List</div>
                </div>
                <a target="_blank" class="mailingAddressTemplate" href="<@ofbizContentUrl>/html/files/addressingTemplate.csv</@ofbizContentUrl>"><i class="fa fa-cloud-upload"></i> Download our Mailing Address Template</a>
            </div>
            <div class="productSideBarSection margin-top-xxs hidden" bns-proofproduct>
                <a class="pullRight" href="">Need Help?</a>
                <h4 class="sidebarHeader">Proof Product</h4>
                <h5 class="margin-top-xxs">Click download proof below to confirm that the layout and all information is correct and in its proper place.</h5>
                <div class="directMailingProof">
                    <h5>Return Address</h5>
                    <div class="button downloadProofButton margin-top-xxs" id="">Download Proof</div>
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
                            <p class="documentName"></p>
                            <p hidden class="documentPath"></p>
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
            <div bns-pricinginfo class="productSideBarSection">
                <div class="displayTable">
                    <h3>Total:</h3>
                    <div class="finalPrice">
                        <h4>$XXXX.XX</h4>
                        <p>(X,XXXX Quantity + $2.00 each)</p>
                    </div>
                </div>
                <div class="width100Percent margin-top-xxs">
                    <div class="button saveExitButton">Save &amp; Exit</div>
                    <div class="button nextButton">Next</div>
                </div>
            </div>
            <div data-bigNameValidateForm="quoteRequestDirectMailing" class="productSideBarSection margin-top-xxs hidden" bns-dmgetquote>
                <a class="pullRight" href="">Need Help?</a>
                <h4 class="sidebarHeader">Get a Quote</h4>
                <div class="row">
                    <p data-bigNameValidateId="quoteFirstName" class="ftc-blue required-indicator">First Name:</p>
                    <input type="text" name="quoteFirstName" val="" data-bigNameValidate="quoteFirstName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="First Name" />
                </div>
                <div class="row">
                    <p data-bigNameValidateId="quoteLastName" class="ftc-blue required-indicator">Last Name:</p>
                    <input type="text" name="quoteLastName" val="" data-bigNameValidate="quoteLastName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Last Name" />
                </div>
                <div class="row">
                    <p data-bigNameValidateId="quoteEmailAddress" class="ftc-blue required-indicator">Email:</p>
                    <input type="text" name="quoteEmailAddress" val="" data-bigNameValidate="quoteEmailAddress" data-bigNameValidateType="required email" data-bigNameValidateErrorTitle="Email Address" />
                </div>
                <div class="row">
                    <p data-bigNameValidateId="quoteCompanyName" class="ftc-blue required-indicator">Company Name:</p>
                    <input type="text" name="quoteCompanyName" val="" data-bigNameValidate="quoteCompanyName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Company Name" />
                </div>
                <div class="row">
                    <p data-bigNameValidateId="quotePhoneNumber" class="ftc-blue required-indicator">Phone Number:</p>
                    <input type="text" name="quotePhoneNumber" val="" data-bigNameValidate="quotePhoneNumber" data-bigNameValidateType="required phone" data-bigNameValidateErrorTitle="Phone Number" />
                </div>
                <div class-"row">
                    <p>Comments:</p>
                    <textarea name="dmComments"></textarea>
                </div>
                <p class="ftc-blue">Quantity:</p>
                <div class="selectListParent sidebarToggle jqs-sidebarToggle jqs-scrollToSelected" data-sidebar-name="sidebar-quoteQuantity" selection-name="quoteQuantity" name="quantity" >
                    <div>Select Quantity</div>
                </div>
                <div class="button dmButton margin-top-xxs no-margin-bottom padding-xxs" data-bigNameValidateSubmit="quoteRequestDirectMailing" data-bigNameValidateAction="quoteRequestDirectMailingAction">Submit Quote <i class="fa fa-caret-right"></i></div>
            </div>
            <div bns-quotecompleted class="productSideBarSection hidden">
                <h3 class="sidebarHeader">
                    Thank You
                    <#if !materialList?exists><span bns-loadPreviousProductStep class="pullRight hidden">Go Back</span></#if>
                </h3>
                <div class="jqs-quoteThankYou quoteThankYou text-center">
                    <h5 class="ftc-blue">Your quote request has been submitted! You will receive an email confirmation shortly.</h5>
                    <h3>Your Quote ID is:<br><span id="quoteId"></span></h3>
                    <a href="tel:+18883273606" class="ftc-orange">Any Questions? <strong>Call Us 888-327-3606</strong></a>
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
    <div id="sidebar-paperColor" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Paper Color</h4>
            </div>
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
    <div id="sidebar-printOption" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Print Options</h4>
            </div>
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
    <div id="sidebar-quoteQuantity" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Quantity &amp; Pricing</h4>
            </div>
        </div>
        <div class="colorTextureBody">
            <div class="colorTextureBodyInner" >
                <div class="selectListItem jqs-selectListItem" bns-selection selection-target="quoteQuantity" selection-name="quoteQuantity" selection-value="50">
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30"><span class="selectCheckbox"></span></div>
                        <div class="padding-left-xxs selectElement jqs-selectElement">50 Qty</div>
                    </div>
                </div>
                <div class="selectListItem jqs-selectListItem" bns-selection selection-target="quoteQuantity" selection-name="quoteQuantity" selection-value="100">
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30"><span class="selectCheckbox"></span></div>
                        <div class="padding-left-xxs selectElement jqs-selectElement">100 Qty</div>
                    </div>
                </div>
                <div class="selectListItem jqs-selectListItem" bns-selection selection-target="quoteQuantity" selection-name="quoteQuantity" selection-value="250">
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30"><span class="selectCheckbox"></span></div>
                        <div class="padding-left-xxs selectElement jqs-selectElement">250 Qty</div>
                    </div>
                </div>
                <div class="selectListItem jqs-selectListItem" bns-selection selection-target="quoteQuantity" selection-name="quoteQuantity" selection-value="500">
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30"><span class="selectCheckbox"></span></div>
                        <div class="padding-left-xxs selectElement jqs-selectElement">500 Qty</div>
                    </div>
                </div>
                <div class="selectListItem jqs-selectListItem" bns-selection selection-target="quoteQuantity" selection-name="quoteQuantity" selection-value="750">
                    <div class="displayTable">
                        <div data-removeonselect class="padding-left-xxs width30"><span class="selectCheckbox"></span></div>
                        <div class="padding-left-xxs selectElement jqs-selectElement">1,000 Qty</div>
                    </div>
                </div>
                <div class="selectListItem jqs-selectListItem" bns-selection selection-target="quoteQuantity" selection-name="quoteQuantity" selection-value="1000">
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30"><span class="selectCheckbox"></span></div>
                        <div class="padding-left-xxs selectElement jqs-selectElement">2,500 Qty</div>
                    </div>
                </div>
                <div class="selectListItem jqs-selectListItem" bns-selection selection-target="quoteQuantity" selection-name="quoteQuantity" selection-value="1000">
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30"><span class="selectCheckbox"></span></div>
                        <div class="padding-left-xxs selectElement jqs-selectElement">5,000 Qty</div>
                    </div>
                </div>
                <div class="selectListItem jqs-selectListItem" bns-selection selection-target="quoteQuantity" selection-name="quoteQuantity" selection-value="1000">
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30"><span class="selectCheckbox"></span></div>
                        <div class="padding-left-xxs selectElement jqs-selectElement">10,000 Qty</div>
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
            <!-- <#assign qtys = [50, 100, 250, 500, 1000, 2500, 5000, 10000] />
            <#list qtys as qty>
                <div class="selectListItem" selection-target="quantity" bns-selection selection-selected="true">
                    <div class="displayTable">
                        <div selection-removeonselect class="padding-left-xxs width30">
                            <span class="selectCheckbox"></span>
                        </div>
                        <div class="text-bold padding-left-xxs">${qty?string[",###"]}</div>
                    </div>
                </div>
            </#list> -->
            <div bns-priceList></div>
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
                <textarea class="margin-top-xxs" name="returnAddress" placeholder="Preview" readonly></textarea>
                <p class="returnAddressInstructions">Enter Your Address Details in the fields below</p>
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
                <#--<div>Country:</div>
                <input bns-returnaddressinput type="text" name="returnAddressCountry" value="" />-->
                <div class="button returnAddressButton">Apply to Mailing</div>
            </div>
        </div>
    </div>
    <!-- Modals -->
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
    <div id="paperWeightInfo" class="reveal-modal reveal-modal-limiter infoPopup paperWeightGuide" data-reveal>
        <div>
            <div class="padding-bottom-xxs popup-title">
                <h3 class="padding-left-xxs">Paper Weight Guide</h3>
                <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
            </div>
            <div class="infoPopupBody padding-xs">
                <p>
                    Paperweight is measured in lbs. (pounds) and refers to the thickness and sturdiness of a sheet of paper.
                    The higher the number, the thicker the paper or cardstock.  Use the "Thickness" column on the far right of this chart to compare the thickness of different paperweights.
                    <br />
                    **NOTE** Paperweight does not refer to the actual weight of a single piece of paper.
                </p>

                <table class="margin-top-xxs">
                    <thead>
                    <tr>
                        <th>Paper &amp; Envelopes<br />(Text)</th>
                        <th>Cardstock &amp; Notecards<br />(Cover)</th>
                        <th>Metric<br />(grams/sq meter)</th>
                        <th>Thickness<br />(mm)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>20lb./50lbs.</td>
                        <td>-</td>
                        <td>74 gsm</td>
                        <td>0.097</td>
                    </tr>
                    <tr>
                        <td>24lb./60lbs.</td>
                        <td>-</td>
                        <td>89gsm</td>
                        <td>0.12</td>
                    </tr>
                    <tr>
                        <td>28lb/70lbs.</td>
                        <td>-</td>
                        <td>104 gsm</td>
                        <td>0.147</td>
                    </tr>
                    <tr>
                        <td>73lbs.</td>
                        <td>-</td>
                        <td>109gsm</td>
                        <td>0.152</td>
                    </tr>
                    <tr>
                        <td>80lbs.</td>
                        <td>-</td>
                        <td>120 gsm</td>
                        <td>0.155</td>
                    </tr>
                    <tr>
                        <td>90lbs.</td>
                        <td>-</td>
                        <td>133 gsm</td>
                        <td>0.157</td>
                    </tr>
                    <tr>
                        <td>100lbs.</td>
                        <td>-</td>
                        <td>148 gsm</td>
                        <td>0.183</td>
                    </tr>
                    <tr>
                        <td>110lbs.</td>
                        <td>60lbs.</td>
                        <td>163 gsm</td>
                        <td>0.188</td>
                    </tr>
                    <tr>
                        <td>137lbs.</td>
                        <td>75lbs.</td>
                        <td>178 gsm</td>
                        <td>0.229</td>
                    </tr>
                    <tr>
                        <td>-</td>
                        <td>80lbs.</td>
                        <td>216 gsm</td>
                        <td>0.234</td>
                    </tr>
                    <tr>
                        <td>-</td>
                        <td>90lbs.</td>
                        <td>244 gsm</td>
                        <td>0.241</td>
                    </tr>
                    <tr>
                        <td>-</td>
                        <td>93lbs.</td>
                        <td>252 gsm</td>
                        <td>0.25</td>
                    </tr>
                    <tr>
                        <td>-</td>
                        <td>100lbs.</td>
                        <td>271 gsm</td>
                        <td>0.289</td>
                    </tr>
                    <tr>
                        <td>-</td>
                        <td>105lbs.</td>
                        <td>286 gsm</td>
                        <td>0.33</td>
                    </tr>
                    <tr>
                        <td>-</td>
                        <td>120lbs.</td>
                        <td>312 gsm</td>
                        <td>0.38</td>
                    </tr>
                    <tr>
                        <td>-</td>
                        <td>146lbs.</td>
                        <td>385 gsm</td>
                        <td>0.445</td>
                    </tr>
                    </tbody>
                </table>
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
<script src="<@ofbizContentUrl>/html/js/product/directMailingProduct.js</@ofbizContentUrl>"></script>
<script src="//fast.wistia.com/assets/external/E-v1.js" async></script>
<script src="<@ofbizContentUrl>/html/js/util/sidebarPanelSlideOut.js</@ofbizContentUrl>"></script>


<script>
    <#if requestParameters.get_quote?has_content && requestParameters.get_quote == "true">
        $('[bns-selectproduct]').addClass('hidden');
        $('.jqs-stepNext').addClass('hidden');
        $('[bns-pricinginfo]').addClass('hidden');
        $('[bns-dmgetquote]').removeClass('hidden');
    </#if>

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
            'estimatedPrice': typeof jobDataJSON.estimatedPrice !== 'undefined' && jobDataJSON.estimatedPrice != '' ? $.parseJSON(jobDataJSON.estimatedPrice) : undefined,
            'price': typeof jobDataJSON.price !== 'undefined' && jobDataJSON.price != '' ? $.parseJSON(jobDataJSON.price) : undefined,
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
            'documentName': jobDataJSON.contentName,
            'documentPath': jobDataJSON.contentPath,
            'numberOfPages': '1'
        });
        $('.downloadProofButton').attr('id', jobDataJSON.proof);
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

     function quoteRequestDirectMailingAction() {
        $.ajax({
            type: "POST",
            url: '<@ofbizUrl>/add-custom-order</@ofbizUrl>',
            data: {
                firstName: $('input[name="quoteFirstName"]').val(),
                lastName: $('input[name="quoteLastName"]').val(),
                userEmail: $('input[name="quoteEmailAddress"]').val(),
                phone: $('input[name="quotePhoneNumber"]').val(),
                companyName: $('input[name="quoteCompanyName"]').val(),
                company: $('input[name="quoteCompanyName"]').val(),
                quantity: $('.sidebarToggle[name=quantity]').attr('bns-dmqty'),
                sku: $('.jqs-productfeatures .productSpecsRow:first-child .specsCol2').html(),
                comment: $('textarea[name="dmComments"]').val(),
                paperType: 'Direct Mail',
                standardSize: $('.jqs-dmSize').html(),
                webSiteId: 'envelopes',
                assignedTo: 'whitney@bigname.com',
                emailToCustomer: 'quoteAssignment'
            },
            dataType:'json',
            cache: false
        }).done(function(data) {
            if (data.success) {
                GoogleAnalytics.trackEvent('Quote Request', 'Finish', data.quoteId);
                $('#quoteId').html(data.quoteId);

                $('[data-bigNameValidateForm="quoteRequestDirectMailing"]').removeClass('hidden').addClass('hidden');
                $('[bns-quotecompleted]').removeClass('hidden');
            }
            else {
                GoogleAnalytics.trackEvent('Quote Request', 'Finish', 'Error');
            }
        });

        //clicks on this will trigger tracking of quote start
        // $('[name="quoteRequestForm"] select,[name="quoteRequestForm"] textarea').on('change', function(e) {
        //     GoogleAnalytics.trackEvent('Quote Request', 'CUSTOM', 'Start');
        // });
    }
</script>