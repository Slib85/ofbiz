<div bns-folderdesigncontent>
    <div class="productSidebarSection">
        <h3 class="sidebarHeader">
            Folder Design
            <span bns-loadPreviousProductStep class="pullRight hidden">Go Back</span>
        </h3>

        <#include "../../includes/product/productAssets/designMethod.ftl" />
    </div>
    <div class="productSidebarSection addToCartStep">
        <#include "../../includes/product/productAssets/quantityAndPricing.ftl" />

        <div class="foldersButton buttonGreen marginTop10 noMarginBottom padding10 fullWidth" bns-addtocart>Add To Cart <i class="fa fa-caret-right pullRight"></i></div>
    </div>
    <div class="productSidebarSection textBold">
        <div class="foldersTabularRow">
            <span>Need More Options?</span>
            <div bns-getquote="contactOnly" class="foldersButton buttonOrange noMargin alternateGetQuote">Get a Quote <i class="fa fa-caret-right"></i></div>
        </div>
    </div>
</div>
<div bns-folderdesignquoterequestcontent class="hidden">
    <div data-bigNameValidateForm="quoteRequestContactInfo" class="productSidebarSection">
        <h3 class="sidebarHeader">
            Contact Info
            <span bns-loadpreviousquotestep class="pullRight">Go Back</span>
        </h3>
        <div class="foldersRow">
            <div class="foldersColumn large6">
                <p data-bigNameValidateId="quoteFirstName" class="ftc-blue required-indicator">First Name:</p>
                <input type="text" name="quoteFirstName" val="" data-bigNameValidate="quoteFirstName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="First Name" />
            </div>
            <div class="foldersColumn large6">
                <p data-bigNameValidateId="quoteLastName" class="ftc-blue required-indicator">Last Name:</p>
                <input type="text" name="quoteLastName" val="" data-bigNameValidate="quoteLastName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Last Name" />
            </div>
        </div>
        <div class="foldersRow">
            <div class="foldersColumn large12">
                <p data-bigNameValidateId="quoteEmailAddressWithContact" class="ftc-blue required-indicator">Email:</p>
                <input type="text" name="quoteEmailAddressWithContact" val="" data-bigNameValidate="quoteEmailAddressWithContact" data-bigNameValidateType="required email" data-bigNameValidateErrorTitle="Email Address" />
            </div>
        </div>
        <div class="foldersRow">
            <div class="foldersColumn large12">
                <p data-bigNameValidateId="quoteCompanyName" class="ftc-blue required-indicator">Company Name:</p>
                <input type="text" name="quoteCompanyName" val="" data-bigNameValidate="quoteCompanyName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Company Name" />
            </div>
        </div>
        <div class="foldersRow">
            <div class="foldersColumn large12">
                <p data-bigNameValidateId="quotePhoneNumber" class="ftc-blue required-indicator">Phone Number:</p>
                <input type="text" name="quotePhoneNumber" val="" data-bigNameValidate="quotePhoneNumber" data-bigNameValidateType="required phone" data-bigNameValidateErrorTitle="Phone Number" />
            </div>
        </div>
        <div class="foldersRow">
            <div class="foldersColumn large12">
                <p data-bigNameValidateId="quoteState" class="ftc-blue required-indicator">State:</p>
                <label data-bigNameValidateParent="quoteState" class="bigNameSelect">
                    <select name="quoteState" data-bigNameValidate="quoteState" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="State">
                        <option value="">Select</option>
                        <#include "../../includes/forms/states.ftl" />
                    </select>
                </label>
            </div>
        </div>
        <div class="foldersRow">
            <div class="foldersColumn large12">
                <p data-bigNameValidateId="quoteZip" val="" class="ftc-blue required-indicator">Zip Code:</p>
                <input type="text" name="quoteZip" data-bigNameValidate="quoteZip" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Zip Code" />
            </div>
        </div>
        <div class="foldersButton buttonGreen marginTop10 noMarginBottom padding10 fullWidth" data-bigNameValidateSubmit="quoteRequestContactInfo" data-bigNameValidateAction="emailThisQuoteSubmission">Submit Quote <i class="fa fa-caret-right pullRight"></i></div>
    </div>
</div>
<div bns-quotecompleted class="productSidebarSection hidden">
    <h3 class="sidebarHeader">
        Thank You
        <#if !materialList?exists><span bns-loadPreviousProductStep class="pullRight hidden">Go Back</span></#if>
    </h3>
    <div class="jqs-quoteThankYou quoteThankYou textCenter">
        <h5 class="ftc-blue">Your quote request has been submitted! You will receive an email confirmation shortly.</h5>
        <h3>Your Quote ID is:<br><span id="quoteId"></span></h3>
        <a href="tel:+18883273606" class="ftc-orange">Any Questions? <strong>Call Us 888-327-3606</strong></a>
    </div>
</div>

<script>
    if (typeof BigNameValidate != 'undefined') {
        window['quoteRequestContactInfo'] = new BigNameValidate($('[data-bigNameValidateForm="quoteRequestContactInfo"]'), 'quoteRequestContactInfo');
    }
    
    $('[bns-getquote="contactOnly"]').on('click', function() {
        $('[bns-folderdesigncontent]').addClass('hidden');
        $('[bns-folderdesignquoterequestcontent]').removeClass('hidden');
    });

    $('[bns-loadpreviousquotestep]').on('click', function() {
        $('[bns-folderdesigncontent]').removeClass('hidden');
        $('[bns-folderdesignquoterequestcontent]').addClass('hidden');
    });
</script>
