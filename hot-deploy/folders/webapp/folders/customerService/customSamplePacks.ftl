<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/util/selectList.css</@ofbizContentUrl>" />
<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/product/product.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" />
<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/landing/customSamplePack.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" />

<div class="foldersContainer foldersProduct customFoldersProduct">
    <div class="tabletDesktop-inlineBlock marginTop10 marginBottom10">
        <#include "../includes/breadcrumbs.ftl" />
    </div>
    <div class="foldersProductHeader">
        <div id="top" class="foldersTabularRow">
            <div>
                <h1>Custom Samples Pack</h1>
            </div>
            <div class="noPaddingLeft questionsProductHeader">
                <h5>Have Questions?</h5>
                <ul class="noListStyle">
                    <li>
                        <a href="tel:1-888-327-2812"><i class="fa fa-phone"></i> Call 1-888-327-2812</a>
                    </li>
                    <li class="marginLeft10">
                        <p href="" bns-driftopenchat=""><i class="fa fa-comment"></i> Chat</p>
                    </li>
                    <li>
                        <a bns-loadproductstep="getQuoteForm" bns-hidewhenclicked href="#top"><i class="fa fa-calculator"></i> Get a Special Quote</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="foldersProductBody">
        <div class="foldersTabularRow marginTop10 productContent">
            <div class="sampleProductImg">
            	<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foldersCustomSamplePack?fmt=png-alpha&amp;wid=600</@ofbizScene7Url>" alt="Placeholder Folder" />
            </div>
            <div class="productSidebar">
                <form name="quoteRequestForm" action="#" method="POST">
                    <input type="hidden" id="gclid_field" name="gclid_field" value="" />
                    <div data-bigNameValidateForm="sampleRequestContactInfo" class="productSidebarSection">
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
                                <input type="text" name="quoteCompanyName" val="" data-bigNameValidate="quoteCompanyName" />
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
                                <p data-bigNameValidateId="quoteAddress" val="" class="ftc-blue">Address:</p>
                                <input type="text" name="quoteAddress" data-bigNameValidate="quoteAddress" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Address" />
                            </div>
                        </div>
                        <div class="foldersRow">
                            <div class="foldersColumn large12">
                                <p data-bigNameValidateId="quoteCity" val="" class="ftc-blue">City:</p>
                                <input type="text" name="quoteCity" data-bigNameValidate="quoteCity" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="City" />
                            </div>
                        </div>
                        <div class="foldersRow">
                            <div class="foldersColumn large12">
                                <p data-bigNameValidateId="quoteState" class="ftc-blue required-indicator">State:</p>
                                <label data-bigNameValidateParent="quoteState" class="bigNameSelect">
                                    <select name="quoteState" data-bigNameValidate="quoteState" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="State">
                                        <option value="">Select</option>
                                        <#include "../includes/forms/states.ftl" />
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
                        <div class="foldersButton buttonGreen marginTop10 noMarginBottom padding10 fullWidth" data-bigNameValidateSubmit="sampleRequestContactInfo" data-bigNameValidateAction="samplePackRequestSubmit">Request Sample <i class="fa fa-caret-right pullRight"></i></div>
                    </div>
                    <div bns-samplecompleted class="productSidebarSection hidden">
                        <h3 class="sidebarHeader">
                            Thank You
                        </h3>
                        <div class="jqs-quoteThankYou quoteThankYou textCenter">
                            <h5 class="ftc-blue">Your sample request has been submitted! You will receive an email confirmation shortly.</h5>
                            <a href="tel:+18883273606" class="ftc-orange">Any Questions? <strong>Call Us 888-327-3606</strong></a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <#include "../includes/product/productAssets/getQuoteText.ftl" />
    </div>
</div>

<script>
    <#-- GCLID -->
    document.getElementById('gclid_field').value = (typeof localStorage['gclid'] != 'undefined' ? localStorage['gclid'] : '');

    if (typeof BigNameValidate != 'undefined') {
        window['sampleRequestContactInfo'] = new BigNameValidate($('[data-bigNameValidateForm="sampleRequestContactInfo"]'), 'sampleRequestContactInfo');
    }

    function samplePackRequestSubmit() {
        $.ajax({
            type: "POST",
            url: "<@ofbizUrl>/customSamplePackSubmission</@ofbizUrl>",
            data: {
                sku: 'Custom Sample Pack Request',
                firstName: $('input[name="quoteFirstName"]').val(),
                lastName: $('input[name="quoteLastName"]').val(),
                email: $('input[name="quoteEmailAddressWithContact"]').val(),
                phoneNumber: $('input[name="quotePhoneNumber"]').val(),
                companyName: $('input[name="quoteCompanyName"]').val(),
                address: $('input[name="quoteAddress"]').val(),
                city: $('input[name="quoteCity"]').val(),
                state: $('select[name="quoteState"]').val(),
                zipCode: $('input[name="quoteZip"]').val()
            },
            dataType:'json',
            cache: false
        }).done(function(data) {
            if (data.success) {
                GoogleAnalytics.trackEvent('Custom Sample Pack Request', 'Finish', 'Success');
                $('[data-bigNameValidateForm="sampleRequestContactInfo"]').removeClass('hidden').addClass('hidden');
                $('[bns-samplecompleted]').removeClass('hidden');
            }
            else {
                GoogleAnalytics.trackEvent('Sample Request', 'Finish', 'Error');
            }
        });
    }

    // $('[bns-loadsamples]').off('click').on('click', function() {
    //     $('[bns-hideforsample]').addClass('hidden');
    //     $('[bns-hideforquote]').removeClass('hidden');
    //     $('[data-bigNameValidateSubmit="sampleRequestContactInfo"]').attr('data-bigNameValidateAction', 'samplePackRequestSubmit');
    //     $('[selection-name="quoteOutsideColor"][selection-value="1 Color Printing"]').trigger('click');
    //     $('[selection-name="quoteOutsideColor"][selection-value="None"]').addClass('hidden');
    //     $('[selection-name="quoteInsideColor"][selection-value="None"]').trigger('click');
    //     $('[selection-selectlistname="quoteQuantitySelection"]').attr('selected-userselection', 'true');
    //     $('[bns-ordersamples]').removeClass('hidden');
    // });

    // $('[bns-loadquotes]').off('click').on('click', function() {
    //     $('[bns-hideforsample]').removeClass('hidden');
    //     $('[bns-hideforquote]').addClass('hidden');
    //     $('[data-bigNameValidateSubmit="quoteRequestContactInfo"]').attr('data-bigNameValidateAction', 'quoteRequestSubmit');
    //     $('[selection-name="quoteOutsideColor"][selection-value="None"]').removeClass('hidden');
    //     $('[selection-selectlistname="quoteQuantitySelection"]').attr('selected-userselection', 'false');
    //     $('[bns-ordersamples]').addClass('hidden');
    // });

    // if (typeof isColorAllowed == 'function') {
    //     $('#sidebar-quoteColorList .selectListItem').each(function(){
    //         if (typeof $(this).attr('data-hex') != 'undefined') {
    //             $(this).find('.productColorSelection').css('color', '#' + (isColorAllowed('ffffff', $(this).attr('data-hex')) ? 'ffffff' : '000000'));
    //         }
    //     });

    //     if (typeof $('[bns-selectedcolortext]').attr('data-hex') != 'undefined') {
    //         $('[bns-selectedcolortext]').css('color', '#' + (isColorAllowed('ffffff', $('[bns-selectedcolortext]').attr('data-hex')) ? 'ffffff' : '000000'));
    //     }
    // }
</script>

<script src="<@ofbizContentUrl>/html/js/util/selectList.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>