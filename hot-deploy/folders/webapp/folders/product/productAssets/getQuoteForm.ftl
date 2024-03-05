<#assign SHOW_INT_SHIP_METHODS = false />
<#if basicAndPremiumMaterialList?has_content>
    <#assign bpMaterialList = basicAndPremiumMaterialList />
    <#assign materialList = nonBasicAndPremiumMaterialList />
<#else>
    <#assign materialList = quoteFormMaterialList />
</#if>

<form name="quoteRequestForm" action="#" method="POST">
    <input type="hidden" id="gclid_field" name="gclid_field" value="" />
    <div data-bigNameValidateForm="quoteRequestCustomization" class="productSidebarSection">
        <h3 bns-hideforsample class="sidebarHeader quoteSidebarHeader fbc-green padding10">
            Quote Builder
            <#if !basicAndPremiumMaterialList?has_content><span bns-loadPreviousProductStep class="pullRight hidden">Go Back</span></#if>
        </h3>

        <h3 bns-hideforquote class="sidebarHeader hidden">
            Order Samples
            <span bns-loadquotes class="pullRight">Get A Quote</span>
        </h3>

        <#if !materialList?has_content && !bpMaterialList?has_content>
            <#include "../../includes/product/productAssets/paperColors.ftl" />
        </#if>

        <div<#if (product.getId() == "08-96" || product.getId() == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Blind Embossing")))> class="hidden"</#if>>
            <#include "../../includes/product/productAssets/quoteColors.ftl" />
        </div>
        
        <#if (product.getId() == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Blind Embossing")))>
        <div>
            <#include "../../includes/product/productAssets/foilColors.ftl" />
        </div>
        </#if>
         
        <#if materialList?has_content || bpMaterialList?has_content>
            <div>
                <h5 bns-ordersamples class="ftc-blue hidden">Material:</h5>
                <div bns-ordersamples class="selectListParent sidebarToggle jqs-sidebarToggle jqs-scrollToSelected colorTextureSelection backgroundGray hidden" data-selectListName="quoteColor" data-sidebar-name="sidebar-quoteColorList" selection-selectlistname="quoteColorSelection" name="quoteMaterial">
                    <div>
                        <div class="please-select">
                            <div class="ftc-blue">Please Select Material</div>
                        </div>
                    </div>
                    <!-- <i class="fa fa-caret-down"></i> -->
                </div>
                <h5 bns-hideforsample class="ftc-blue" data-bigNameValidateId="quoteMaterial">Material:</h5>
                <div bns-hideforsample class="selectListParent sidebarToggle jqs-sidebarToggle jqs-scrollToSelected colorTextureSelection backgroundGray" data-selectListName="quoteColor" data-sidebar-name="sidebar-quoteColorList" selection-selectlistname="quoteColorSelection" name="quoteMaterial" data-bigNameValidate="quoteMaterial" data-bigNameValidateType="quoteRequestCustomization" data-bigNameValidateErrorTitle="Material">
                    <div>
                        <div class="please-select">
                            <div class="ftc-blue">Please Select Material</div>
                        </div>
                    </div>
                    <!-- <i class="fa fa-caret-down"></i> -->
                </div>
                <div id="sidebar-quoteColorList" class="sidebarPanel colorlist jqs-sampleReadablelist jqs-scrollable">
                    <div class="colorTextureHeading">
                        <div class="stickyTextureHeading">
                            <h4><i class="fa fa-times"></i>Material Color</h4>
                        </div>
                        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
                    </div>
                    <div class="jqs-scrollable jqs-quoteColorList colorTextureBody">
                        <div class="colorTextureBodyInner">
                            <#if bpMaterialList?exists>
                                <#list bpMaterialList as material>
                                    <div bns-selection class="selectListItem" data-selectListName="quoteColor" selection-target="quoteColorSelection" selection-name="materialDescription" selection-value="${material["description"]?if_exists}<#if material["coating"]?exists> C${material["coating"]}S</#if>" data-hex="${material["colorHexCode"]?if_exists}">
                                        <img class="productBackgroundColor" src="${material["img"]?if_exists}?wid=72" alt="${material["description"]?if_exists}"/>
                                        <div class="foldersTabularRow folderDisplayTable productColorSelection">
                                            <div selection-removeonselect class="paddingLeft10 width30">
                                                <span class="selectCheckbox"></span>
                                            </div>
                                            <div bns-colorselecttext class="textBold paddingLeft10">${material["description"]?if_exists} <#if material["coating"]?exists>(<#if material["coating"] != "0">Coated ${material["coating"]} Side<#else>Non-Coated</#if>)</#if></div>
                                        </div>
                                    </div>
                                </#list>
                            </#if>
                            <#list materialList as material>
                                <div bns-selection class="selectListItem" data-selectListName="quoteColor" selection-target="quoteColorSelection" selection-name="materialDescription" selection-value="${material["description"]?if_exists}<#if material["coating"]?exists> C${material["coating"]}S</#if>" data-hex="${material["colorHexCode"]?if_exists}">
                                    <div>
                                        <img class="productBackgroundColor" src="${material["img"]?if_exists}?wid=72" alt="${material["description"]?if_exists}"/>
                                        <div class="foldersTabularRow folderDisplayTable productColorSelection">
                                            <div selection-removeonselect class="paddingLeft10 width30">
                                                <span class="selectCheckbox"></span>
                                            </div>
                                            <div bns-colorselecttext class="textBold paddingLeft10">${material["description"]?if_exists} <#if material["coating"]?exists>(<#if material["coating"] != "0">Coated ${material["coating"]} Side<#else>Non-Coated</#if>)</#if></div>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
        </#if>

        <#if materialList?has_content>
            <div class="foldersTabularRow pocketsCornersSlits">
                <div bns-styleParent="pockets">
                    <#include "../../includes/product/productAssets/pockets.ftl" />
                </div>
            </div>
        </#if>

        <h5 bns-hideforsample>Additonal Details:</h5>
        <textarea bns-hideforsample bns-textinput name="additionalQuoteDetails"></textarea>

        <div bns-hideforsample>
            <#include "../../includes/product/productAssets/quoteQuantity.ftl" />
        </div>

        <div class="foldersButton buttonGreen marginTop10 noMarginBottom padding10 fullWidth" data-bigNameValidateSubmit="quoteRequestCustomization" data-bigNameValidateAction="quoteRequestCustomizationSubmission">Next <i class="fa fa-caret-right pullRight"></i></div>
    </div>
    <div data-bigNameValidateForm="quoteRequestContactInfo" class="productSidebarSection hidden">
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
                <p data-bigNameValidateId="quoteAddress" val="" class="ftc-blue">Address:</p>
                <input type="text" name="quoteAddress" data-bigNameValidate="quoteAddress" data-bigNameValidateErrorTitle="address" />
            </div>
        </div>
        <div class="foldersRow">
            <div class="foldersColumn large12">
                <p data-bigNameValidateId="quoteCity" val="" class="ftc-blue">City:</p>
                <input type="text" name="quoteCity" data-bigNameValidate="quoteCity" data-bigNameValidateErrorTitle="City" />
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
        <div bns-hideforsample class="foldersButton buttonGreen marginTop10 noMarginBottom padding10 fullWidth" data-bigNameValidateSubmit="quoteRequestContactInfo" data-bigNameValidateAction="quoteRequestSubmit">Submit Quote <i class="fa fa-caret-right pullRight"></i></div>
        <div bns-hideforquote class="foldersButton buttonGreen marginTop10 noMarginBottom padding10 fullWidth hidden" data-bigNameValidateSubmit="quoteRequestContactInfo" data-bigNameValidateAction="quoteRequestSubmit">Submit Sample <i class="fa fa-caret-right pullRight"></i></div>
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

    <div bns-samplecompleted class="productSidebarSection hidden">
        <h3 class="sidebarHeader">
            Thank You
        <#if !materialList?exists><span bns-loadPreviousProductStep class="pullRight hidden">Go Back</span></#if>
        </h3>
        <div class="jqs-quoteThankYou quoteThankYou textCenter">
            <h5 class="ftc-blue">Your sample request has been submitted! You will receive an email confirmation shortly.</h5>
            <a href="tel:+18883273606" class="ftc-orange">Any Questions? <strong>Call Us 888-327-3606</strong></a>
        </div>
    </div>
</form>


<script>
    <#-- GCLID -->
    document.getElementById('gclid_field').value = (typeof localStorage['gclid'] != 'undefined' ? localStorage['gclid'] : '');

    if (typeof BigNameValidate != 'undefined') {
        window['quoteRequestCustomization'] = new BigNameValidate($('[data-bigNameValidateForm="quoteRequestCustomization"]'), 'quoteRequestCustomization');
        window['quoteRequestContactInfo'] = new BigNameValidate($('[data-bigNameValidateForm="quoteRequestContactInfo"]'), 'quoteRequestContactInfo');
    }

    function quoteRequestCustomizationSubmission() {
        $('[data-bigNameValidateForm="quoteRequestContactInfo"]').removeClass('hidden');
        $('[data-bigNameValidateForm="quoteRequestCustomization"]').addClass('hidden');
    }

    $('[bns-loadpreviousquotestep]').on('click', function() {
        $('[data-bigNameValidateForm="quoteRequestCustomization"]').removeClass('hidden');
        $('[data-bigNameValidateForm="quoteRequestContactInfo"]').addClass('hidden');
    });

    function quoteRequestSubmit() {
        var quantities = [];

        if ($('[name=customQuantity]').val() != '') {
            quantities.push($('[name=customQuantity]').val().replace(/[^0-9]+/, ''));
        }

        quantities.push(productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.quoteQuantitySelection);
        var sku = <#if nonBasicAndPremiumMaterialList?exists>'${requestParameters.product_id}'<#else>productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.vendorProductId</#if>;
        $.ajax({
            type: "POST",
            url: "<@ofbizUrl>/quoteRequestSubmission</@ofbizUrl>",
            data: {
                productStyle: sku,
                webSiteId: 'folders',
                paperColor: productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.materialDescription,
                productQuantity: quantities.join(', '),
                outsideColor: productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.quoteOutsideColor,
                insideColor: productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.quoteInsideColor,
                foilColor: ($('[selection-name="foilColor"]').length > 0 ? $('[selection-name="foilColor"]').attr('selection-value') : 'None'),
                additonalInfo: $('textarea[name="additionalQuoteDetails"]').val(),
                firstName: $('input[name="quoteFirstName"]').val(),
                lastName: $('input[name="quoteLastName"]').val(),
                email: $('input[name="quoteEmailAddressWithContact"]').val(),
                phone: $('input[name="quotePhoneNumber"]').val(),
                companyName: $('input[name="quoteCompanyName"]').val(),
                address1: $('input[name="quoteAddress"]').val(),
                city: $('input[name="quoteCity"]').val(),
                stateProvinceGeoId: $('select[name="quoteState"]').val(),
                postalCode: $('input[name="quoteZip"]').val(),
                gclid: $('input[name="gclid_field"]').val()
            },
            dataType:'json',
            cache: false
        }).done(function(data) {
            if (data.success) {
                GoogleAnalytics.trackEvent('Quote Request', 'Finish', data.quoteId);
                $('#quoteId').html(data.quoteId);

                $('[data-bigNameValidateForm="quoteRequestCustomization"]').removeClass('hidden').addClass('hidden');
                $('[data-bigNameValidateForm="quoteRequestContactInfo"]').removeClass('hidden').addClass('hidden');
                $('[bns-quotecompleted]').removeClass('hidden');
                history.replaceState(null, 'Quote Confirmation', '/QuoteConf/~quoteId=' + data.quoteId);
                dataLayer.push({
                    'sku': sku,
                    'quoteId': data.quoteId,
                    'event': 'quotecreated'
                });
            }
            else {
                GoogleAnalytics.trackEvent('Quote Request', 'Finish', 'Error');
            }
        });

        //clicks on this will trigger tracking of quote start
        $('[name="quoteRequestForm"] select,[name="quoteRequestForm"] textarea').on('change', function(e) {
            GoogleAnalytics.trackEvent('Quote Request', 'CUSTOM', 'Start');
        });
    }

    function sampleRequestSubmit() {
        $.ajax({
            type: "POST",
            url: "<@ofbizUrl>/sampleRequestSubmission</@ofbizUrl>",
            data: {
                sku: '${product.getId()?if_exists}',
                printingMethod1: productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.quoteOutsideColor,
                printingMethod2: 'None',
                printingMethod3: 'None',
                colorName1: productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.materialDescription,
                colorName2: 'None',
                colorName3: 'None',
                foilColor: ($('[selection-name="foilColor"][selection-selected="true"]').length > 0 ? $('[selection-name="foilColor"][selection-selected="true"]').attr('selection-value') : 'None'),
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
                GoogleAnalytics.trackEvent('Sample Request', 'Finish', 'Success');
                $('[data-bigNameValidateForm="quoteRequestCustomization"]').removeClass('hidden').addClass('hidden');
                $('[data-bigNameValidateForm="quoteRequestContactInfo"]').removeClass('hidden').addClass('hidden');
                $('[bns-samplecompleted]').removeClass('hidden');
            }
            else {
                GoogleAnalytics.trackEvent('Sample Request', 'Finish', 'Error');
            }
        });
    }

    $('[bns-loadsamples]').off('click').on('click', function() {
        $('[bns-hideforsample]').addClass('hidden').attr('data-bignamevalidateactive', 'false');
        $('[bns-hideforquote]').removeClass('hidden').attr('data-bignamevalidateactive', 'true');
        $('[data-bigNameValidateSubmit="quoteRequestContactInfo"]').attr('data-bigNameValidateAction', 'sampleRequestSubmit');
        $('[selection-name="quoteOutsideColor"][selection-value="1 Color Printing"]').trigger('click');
        $('[selection-name="quoteOutsideColor"][selection-value="None"]').addClass('hidden');
        $('[selection-name="quoteInsideColor"][selection-value="None"]').trigger('click');
        $('[selection-selectlistname="quoteQuantitySelection"]').attr('selected-userselection', 'true');
        $('[bns-ordersamples]').removeClass('hidden');
        if (typeof productPage != "undefined") {
            productPage.getActiveProductAttributes().orderSample = 'true';
            productPage.updateSelection($(this));
        }
    });

    <#if requestParameters.order_samples?has_content && requestParameters.order_samples == "true">$($('[bns-loadsamples]')[0]).trigger('click');</#if>

    $('[bns-loadquotes]').off('click').on('click', function() {
        $('[bns-hideforsample]').removeClass('hidden').attr('data-bignamevalidateactive', 'true');
        $('[bns-hideforquote]').addClass('hidden').attr('data-bignamevalidateactive', 'false');
        $('[data-bigNameValidateSubmit="quoteRequestContactInfo"]').attr('data-bigNameValidateAction', 'quoteRequestSubmit');
        $('[selection-name="quoteOutsideColor"][selection-value="None"]').removeClass('hidden');
        $('[selection-selectlistname="quoteQuantitySelection"]').attr('selected-userselection', 'false');
        $('[bns-ordersamples]').addClass('hidden');
    });

    if (typeof isColorAllowed == 'function') {
        $('#sidebar-quoteColorList .selectListItem').each(function(){
            if (typeof $(this).attr('data-hex') != 'undefined') {
                $(this).find('.productColorSelection').css('color', '#' + (isColorAllowed('ffffff', $(this).attr('data-hex')) ? 'ffffff' : '000000'));
            }
        });

        if (typeof $('[bns-selectedcolortext]').attr('data-hex') != 'undefined') {
            $('[bns-selectedcolortext]').css('color', '#' + (isColorAllowed('ffffff', $('[bns-selectedcolortext]').attr('data-hex')) ? 'ffffff' : '000000'));
        }
    }
</script>


<!--START DNB VIFF JS-->
<script type="text/javascript" src="//cdn-0.d41.co/tags/ff-2.min.js" charset="utf-8"></script>
<script type="text/javascript">
    var maxTries = 3;
    var currentTry = 1;
    
    var loadDNB = window.setInterval(function() {
        if (typeof Fill != 'undefined') {
            if (window.ActiveXObject) {
                window.ActiveXObject = null;
            }
            var dpa = new Fill.LeadFormApp({
                visitorIntelligenceApiKey: "vid0264",
                defaultCompanyCountry: "US",
                leadFormName: 'quoteRequestForm',
                firstNameFieldName: 'quoteFirstName',
                lastNameFieldName: 'quoteLastName',
                contactEmailSearchFieldName: 'quoteEmailAddress',
                companyNameSearchFieldName: 'quoteCompanyName',
                companyNameFieldName: 'quoteCompanyName',
                phoneFieldName: 'quotePhoneNumber',
                address1FieldName: 'quoteAddress',
                cityFieldName: 'quoteCity',
                stateFieldName: 'quoteState',
                postalFieldName: 'quoteZip',
                companyCountrySearchFieldName: 'quoteCountry',
                countryFieldName: 'quoteCountry',
                useLIDropdowns: true,
                initialFocusFieldName: ''
            });

            dpa.attach();

            clearTimeout(loadDNB);
        }

        if (currentTry == 3) {
            clearTimeout(loadDNB);
        }
        
        currentTry++;
    }, 1500);
</script>
<!-- END DNB VIFF JS-->

<script src="<@ofbizContentUrl>/html/js/util/selectList.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>