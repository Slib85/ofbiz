<style>
	.colorSelectionHidden {
		display: none !important;
	}
	.required-indicator:after {
		content: '*';
		color: #ff0000;
		display: inline-block;
		margin-left: 3px;
	}
</style>
<div class="foldersRow">
	<img src="<@ofbizContentUrl>/html/img/folders/product/rapidResponseQuoteBanner.jpg</@ofbizContentUrl>" alt="Rapid Response Quote Form" />
</div>
<div class="textRight ftc-orange marginTop10">Need Help? Call US 800-296-4321</div>
<div class="formContent">
	<div class="foldersRow">
		<div class="foldersColumn small12 medium5 large4 textCenter">
			<h2 class="ftc-blue">${foldersProduct["Title"]?if_exists}</h2>
			<#if productId?exists && productId != "SF-101">
				<img class="marginTop20" data-modified="true" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${productId?if_exists}?wid=250" alt="${productId?if_exists}">
			<#else>
                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderInsideGuide_SF-101?fmt=png-alpha&amp;wid=250" alt="Folders Inside Guide SF-101" />
			</#if>

			<#if foldersProduct["Type"]?exists && foldersProduct["Type"] == "Folder">
                <h2 class="ftc-blue textLeft">Folder Exterior:</h2>
                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderOutsideGuide?fmt=png-alpha&amp;wid=230" alt="Folders Outside Guide" />
			</#if>
		</div>
		<form name="quoteRequestForm" class="foldersColumn small12 medium7 large8" action="#" method="POST">
            <input type="hidden" id="gclid_field" name="gclid_field" value="">
			<div class="jqs-quoteSlide" data-bigNameValidateForm="quoteRequestCustomization" data-quoteSlideNumber="1" data-quoteSlideName="Customization">
				<h2 class="ftc-blue">Customization:</h2>
				<#if basicAndPremiumMaterialList?has_content>
				<div class="foldersRow<#if (productId == "08-96" || productId == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Blind Embossing")))> hidden</#if>">
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="quoteOutsideColor" class="ftc-blue">Side 1 (Exterior Covers &amp; Inner Pockets): <i style="cursor: pointer;" class="fa fa-info-circle pullRight jqs-bignameToolTipTrigger" data-tipTitle="Folders are first printed as flat cardstock and are then converted into a folder. Therefore, the inside pockets are printed on the same side of stock as the front and back covers.<br />Please choose the printing method that best reflects the type of printing that will occur on Side 1 (Front Cover, Back Cover and Inner Pockets). If you have any additional questions regarding the customization of your folder and the creation of a design file, please contact our Pre-Press Team at 888-312-3290."></i></div>
						<label class="bigNameSelect" data-bigNameValidateParent="quoteOutsideColor">
							<select name="quoteOutsideColor" data-bigNameValidate="quoteOutsideColor" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Side 1 (Outside)"<#if (productId == "08-96" || productId == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Blind Embossing")))> disabled</#if>>
								<option value="">Select a Printing Method</option>
								<option value="None">None</option>
								<option value="1 Color Printing">1 Color Printing</option>
								<option value="2 Color Printing">2 Color Printing</option>
                                <option value="Full Color Printing"<#if productId == "08-96"> selected</#if>>Full Color Printing</option>
								<#if !requestParameters.printingType?exists || requestParameters.printingType != "Printed Folders">
								<option value="Foil Stamped"<#if productId == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped"))> selected</#if>>Foil Stamped</option>
								<option value="Embossing">Embossing</option>
								<option value="Blind Embossed"<#if (requestParameters.printingType?exists && (requestParameters.printingType == "Blind Embossing"))> selected</#if>>Blind Embossed</option>
								<option value="Foil and Embossed">Foil and Embossed</option>
								</#if>
							</select>
						</label>
					</div>
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="quoteInsideColor" class="ftc-blue">Side 2 (Inside Panels): <i style="cursor: pointer;" class="fa fa-info-circle pullRight jqs-bignameToolTipTrigger" data-tipTitle="Printing on Side 2 is only for the inside panels of the folders. This <strong>does not</strong> include the inner pockets.<br />Please choose the printing method that best reflects the type of printing that will occur on Side 2 (Inner Panels). If you have any additional questions regarding the customization of your folder and the creation of a design file, please contact our Pre-Press Team at 888-312-3290."></i></div>
						<label class="bigNameSelect" data-bigNameValidateParent="quoteInsideColor">
							<select name="quoteInsideColor" data-bigNameValidate="quoteInsideColor" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Side 2 (Inside)"<#if (productId == "08-96" || productId == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Printed Folders" || requestParameters.printingType == "Blind Embossing")))> disabled</#if>>
								<option value="">Select a Printing Method</option>
								<option value="None"<#if (productId == "08-96" || productId == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Printed Folders" || requestParameters.printingType == "Blind Embossing")))> selected</#if>>None</option>
								<option value="1 Color Printing">1 Color Printing</option>
								<option value="2 Color Printing">2 Color Printing</option>
								<option value="Full Color Printing">Full Color Printing</option>
							</select>
						</label>
					</div>
				</div>
				</#if>
				<div class="foldersRow">
					<div class="foldersColumn large12">
						<div class="ftc-blue" data-bignamevalidateid="quoteColor">Material:</div>
						<div class="selectListParent " data-dropdown-target="dropdown-quoteColorList" data-dropdown-options="click ignore-reverse-dropdown" data-selectListName="quoteColor" data-bigNameValidate="quoteColor" data-bigNameValidateType="selectList" data-bigNameValidateErrorTitle="Material">
							<div>
								<div class="please-select">
									<div class="ftc-blue">Please Select Material</div>
								</div>
							</div>
							<i class="fa fa-caret-down"></i>
						</div>
						<div id="dropdown-quoteColorList" class="drop-down">
							<div class="selectListDropDown jqs-scrollable jqs-quoteColorList">
								<#if basicAndPremiumMaterialList?exists>
								<#list basicAndPremiumMaterialList as material>
								<div class="selectListItem jqs-selectListItem" data-selectListName="quoteColor" data-colorName="${material["description"]?if_exists}" data-type="${material["type"]?if_exists}">
									<div>
										<img class="itemImage" src="${material["img"]?if_exists}?wid=72" alt="${material["description"]?if_exists}"/>
										<div>
											<div class="ftc-blue">${material["description"]?if_exists} <#if material["coating"]?exists>(<#if material["coating"] != "0">Coated ${material["coating"]} Side<#else>Non-Coated</#if>)</#if></div>
											<#--<div class="ftc-green">${material["type"]?if_exists}</div>-->
										</div>
									</div>
								</div>
								</#list>
								</#if>
								<#list nonBasicAndPremiumMaterialList as material>
								<div class="selectListItem jqs-selectListItem" data-selectListName="quoteColor" data-colorName="${material["description"]?if_exists}" data-type="${material["type"]?if_exists}">
									<div>
										<img class="itemImage" src="${material["img"]?if_exists}?wid=72" alt="${material["description"]?if_exists}"/>
										<div>
											<div class="ftc-blue">${material["description"]?if_exists} <#if material["coating"]?exists>(<#if material["coating"] != "0">Coated ${material["coating"]} Side<#else>Non-Coated</#if>)</#if></div>
											<#--<div class="ftc-green">${material["type"]?if_exists}</div>-->
										</div>
									</div>
								</div>
								</#list>
                            </div>
						</div>
					</div>
				</div>
				<h2 class="ftc-blue">Quantities:</h2>
				<div class="foldersRow">
					<#if productId == "08-96" || productId == "08-96-FOIL">
                    <div class="foldersColumn small6 medium4 large2">
                        <input id="quoteQuantity1" name="quoteQuantity" type="checkbox" value="50" />
                        <label for="quoteQuantity1">${50?string[",##0"]}</label>
                    </div>
                    <div class="foldersColumn small6 medium4 large2">
                        <input id="quoteQuantity2" name="quoteQuantity" type="checkbox" value="100" />
                        <label for="quoteQuantity2">${100?string[",##0"]}</label>
                    </div>
					</#if>
					<div class="foldersColumn small6 medium4 large2">
						<input id="quoteQuantity3" name="quoteQuantity" type="checkbox" value="250" />
						<label for="quoteQuantity3">${250?string[",##0"]}</label>
					</div>
					<div class="foldersColumn small6 medium4 large2">
						<input id="quoteQuantity4" name="quoteQuantity" type="checkbox" value="500" />
						<label for="quoteQuantity4">${500?string[",##0"]}</label>
					</div>
					<div class="foldersColumn small6 medium4 large2">
						<input id="quoteQuantity5" name="quoteQuantity" type="checkbox" value="750" />
						<label for="quoteQuantity5">${750?string[",##0"]}</label>
					</div>
					<div class="foldersColumn small6 medium4 large2">
						<input id="quoteQuantity6" name="quoteQuantity" type="checkbox" value="1000" />
						<label for="quoteQuantity6">${1000?string[",##0"]}</label>
					</div>
				</div>
				<div class="foldersRow">
					<div class="foldersColumn small12 medium12 large6">
						<div class="ftc-blue">Custom Quantity:</div>
						<input name="customQuantity" type="text" value="" placeholder="Custom Quantity" />
					</div>
				</div>
				<div class="foldersRow">
					<div class="foldersColumn large12">
						<div data-bigNameValidateId="quoteOtherInfo" class="ftc-blue">Please let us know additional details regarding your custom order:</div>
						<textarea name="quoteOtherInfo" placeholder=""></textarea>
					</div>
				</div>
			</div>
			<div class="jqs-quoteSlide hidden" data-bigNameValidateForm="quoteRequestContactInfo" data-quoteSlideNumber="2" data-quoteSlideName="Contact Information">
				<h2 class="ftc-blue">Contact Information</h2>
				<div class="foldersRow">
					<div class="foldersColumn small12 medium6 large3">
						<div data-bigNameValidateId="quoteFirstName" class="ftc-blue required-indicator">First Name:</div>
						<input type="text" name="quoteFirstName" val="" data-bigNameValidate="quoteFirstName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="First Name" />
					</div>
					<div class="foldersColumn small12 medium6 large3">
						<div data-bigNameValidateId="quoteLastName" class="ftc-blue required-indicator">Last Name:</div>
						<input type="text" name="quoteLastName" val="" data-bigNameValidate="quoteLastName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Last Name" />
					</div>
					<div class="foldersColumn small12 medium12 large6">
						<div data-bigNameValidateId="quoteEmailAddress" class="ftc-blue required-indicator">Email:</div>
						<input type="text" name="quoteEmailAddress" val="" data-bigNameValidate="quoteEmailAddress" data-bigNameValidateType="required email" data-bigNameValidateErrorTitle="Email Address" />
					</div>
				</div>
				<div class="foldersRow">
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="quoteCompanyName" class="ftc-blue required-indicator">Company Name:</div>
						<input type="text" name="quoteCompanyName" val="" data-bigNameValidate="quoteCompanyName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Company Name" />
					</div>
					<div class="foldersColumn small12 large6">
						<div class="ftc-blue">Industry:</div>
						<label class="bigNameSelect" data-bigNameValidateParent="quoteIndustry">
							<select name="quoteIndustry" data-bigNameValidate="quoteIndustry" data-bigNameValidateErrorTitle="Industry">
								<option value="">Select</option>
								<option value="Accountant">Accountant</option>
								<option value="Business">Business</option>
								<option value="Construction">Construction</option>
								<option value="CPA">CPA</option>
								<option value="Design">Design</option>
								<option value="Education">Education</option>
								<option value="Events">Events</option>
								<option value="Financial">Financial</option>
								<option value="Government">Government</option>
								<option value="Insurance">Insurance</option>
								<option value="Law">Law</option>
								<option value="Medical">Medical</option>
								<option value="Nonprofit">Nonprofit</option>
								<option value="Printing">Printing</option>
								<option value="Real Estate">Real Estate</option>
								<option value="Recruiting">Recruiting</option>
								<option value="Religious">Religious</option>
								<option value="Shipping">Shipping</option>
								<option value="Staffing">Staffing</option>
							</select>
						</label>
					</div>
				</div>
                <div class="foldersRow">
                    <div class="foldersColumn small12 medium12 large6">
                        <div data-bigNameValidateId="quotePhoneNumber" class="ftc-blue required-indicator">Phone Number:</div>
                        <input type="text" name="quotePhoneNumber" val="" data-bigNameValidate="quotePhoneNumber" data-bigNameValidateType="required phone" data-bigNameValidateErrorTitle="Phone Number" />
                    </div>
                    <div class="foldersColumn small12 medium12 large6">
                        <div data-bigNameValidateId="quoteAddress" val="" class="ftc-blue">Address:</div>
                        <input type="text" name="quoteAddress" data-bigNameValidate="quoteAddress" data-bigNameValidateErrorTitle="address" />
                    </div>
                </div>
				<div class="foldersRow">
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="quoteCity" val="" class="ftc-blue">City:</div>
						<input type="text" name="quoteCity" data-bigNameValidate="quoteCity" data-bigNameValidateErrorTitle="City" />
					</div>
                    <div class="foldersColumn small12 large6">
                        <div data-bigNameValidateId="quoteState" class="ftc-blue required-indicator">State:</div>
                        <label data-bigNameValidateParent="quoteState" class="bigNameSelect">
                            <select name="quoteState" data-bigNameValidate="quoteState" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="State">
                            <option value="">Select</option>
							<#include "../forms/states.ftl" />
                            </select>
                        </label>
                    </div>
				</div>
				<div class="foldersRow">
                    <div class="foldersColumn small12 large6">
                        <div data-bigNameValidateId="quoteZip" val="" class="ftc-blue required-indicator">Zip Code:</div>
                        <input type="text" name="quoteZip" data-bigNameValidate="quoteZip" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Zip Code" />
                    </div>
                    <div class="foldersColumn small12 large6">
                        <div class="ftc-blue">Country:</div>
                        <label class="bigNameSelect" data-bigNameValidateParent="quoteCountry">
                            <select name="quoteCountry" data-bigNameValidate="quoteCountry" data-bigNameValidateErrorTitle="Country">
                                <option value="">Select</option>
                                <option value="USA">United States</option>
                                <option value="CAN">Canada</option>
                            </select>
                        </label>
                    </div>
				</div>
			</div>
			<div class="jqs-quoteThankYou quoteThankYou hidden textCenter">
				<h2 class="ftc-blue">Thank You - Your quote request has been submitted! You will receive an email confirmation shortly.</h2>
				<h3>Your Quote ID is:<br><span id="quoteId"></span></h3>
				<a href="tel:1-800-296-4321" class="ftc-orange">Any Questions? <strong>Call Us 800-296-4321</strong></a>
			</div>
			<div class="foldersRow marginTop20">
				<div class="foldersColumn large12">
					<div class="foldersButton buttonGreen jqs-quoteBack pullLeft hidden"></div>
					<div class="foldersButton buttonGold jqs-quoteForward pullRight" data-bigNameValidateSubmit="quoteRequestCustomization" data-bigNameValidateAction="quoteRequestSlideSubmit">Continue <i class="fa fa-caret-right"></i></div>
					<div class="foldersButton buttonGold jqs-submitQuote pullRight hidden" data-bigNameValidateSubmit="quoteRequestContactInfo" data-bigNameValidateAction="quoteRequestSubmit">Submit Quote Request</div>
				</div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	var quoteRequestSlideNumber = 1;
	var quantities = '';

	function quoteForward() {
		if ($('.jqs-quoteSlide').length != quoteRequestSlideNumber) {
			var quoteSlideElement = $('.jqs-quoteSlide[data-quoteSlideNumber=' + quoteRequestSlideNumber + ']');
			quoteSlideElement.addClass('hidden');

			quoteRequestSlideNumber++;
			quoteSlideElement = $('.jqs-quoteSlide[data-quoteSlideNumber=' + quoteRequestSlideNumber + ']');

			$('.jqs-quoteBack').removeClass('hidden').html('<i class="fa fa-caret-left"></i> Back');
			quoteSlideElement.removeClass('hidden');

			var formName = quoteSlideElement.attr('data-bigNameValidateForm');
			if (typeof formName !== 'undefined' && formName != '') {
				$('.jqs-quoteForward').attr('data-bigNameValidateSubmit', formName);
				window[formName] = new BigNameValidate(quoteSlideElement, formName);
			}

			// Update Review Section with selected values
			if (quoteSlideElement.attr('data-quoteSlideName') == 'Contact Information') {
				$('.jqs-quoteForward').addClass('hidden');
				$('.jqs-submitQuote').removeClass('hidden');
			}
		}
	}

	$('.jqs-quoteBack').on('click', function() {
		if (quoteRequestSlideNumber > 1) {
			var quoteSlideElement = $('.jqs-quoteSlide[data-quoteSlideNumber=' + quoteRequestSlideNumber + ']');
			quoteSlideElement.addClass('hidden');

			quoteRequestSlideNumber--;
			quoteSlideElement = $('.jqs-quoteSlide[data-quoteSlideNumber=' + quoteRequestSlideNumber + ']');

			if (quoteRequestSlideNumber >= 2) {
				$('.jqs-quoteBack').removeClass('hidden').html('<i class="fa fa-caret-left"></i> Edit ' + quoteSlideElement.attr('data-quoteSlideName'));
			}
			else {
				$(this).addClass('hidden');
			}

			quoteSlideElement.removeClass('hidden');

			var formName = quoteSlideElement.attr('data-bigNameValidateForm');
			if (typeof formName !== 'undefined' && formName != '') {
				$('.jqs-quoteForward').attr('data-bigNameValidateSubmit', formName);
				window[formName] = new BigNameValidate(quoteSlideElement, formName);
			}

			if (quoteSlideElement.attr('data-quoteSlideName') != 'Contact Information') {
				$('.jqs-quoteForward').removeClass('hidden');
				$('.jqs-submitQuote').removeClass('hidden').addClass('hidden');
			}
		}
	});

	function quoteRequestSlideSubmit() {
		quoteForward();
	}

	function quoteRequestSubmit() {
        var quantities = [];

        $('input[name="quoteQuantity"]').each(function() {
            if ($(this).is(':checked')) {
                quantities.push($(this).val());
            }
        });

        if ($('input[name="customQuantity"]').val().replace(/[^0-9\,\.]/, '') != '') {
            quantities.push($('input[name="customQuantity"]').val().replace(/[^0-9\,\.]/, ''));
        }

		$.ajax({
			type: "POST",
			url: "<@ofbizUrl>/quoteRequestSubmission</@ofbizUrl>",
			data: {
				productStyle: '${productId?if_exists}',
                quoteType: $('input[name="quoteType"]').val(),
                webSiteId: 'folders',
				paperColor: $('.jqs-quoteColorList .slSelected').attr('data-colorName'),
				productQuantity: quantities.join(', '),
				outsideColor: $('select[name="quoteOutsideColor"]').val(),
				insideColor: $('select[name="quoteInsideColor"]').val(),
				additonalInfo: $('textarea[name="quoteOtherInfo"]').val(),
				firstName: $('input[name="quoteFirstName"]').val(),
				lastName: $('input[name="quoteLastName"]').val(),
				email: $('input[name="quoteEmailAddress"]').val(),
                countryGeoId: $('select[name="quoteCountry"]').val(),
				phone: $('input[name="quotePhoneNumber"]').val(),
                companyName: $('input[name="quoteCompanyName"]').val(),
				industry: $('select[name="quoteIndustry"]').val(),
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
			}
			else {
                GoogleAnalytics.trackEvent('Quote Request', 'Finish', 'Error');
			}
		});

		$('.jqs-quoteSlide').addClass('hidden');
		$('.jqs-quoteThankYou').removeClass('hidden');
		$('.jqs-submitQuote').addClass('hidden');
		$('.jqs-quoteBack').addClass('hidden');
	}
	
	$('[name="quoteInsideColor"], [name="quoteOutsideColor"]').on('change', function() {
		if ($(this).val() == 'Full Color Printing') {
			var fourColorPrintingList = ['Vellum Vanilla 80lb.','Fiber Natural 80lb.','Linen Natural 100lb.','Smooth Eco Ivory 80lb.','Felt Pumice 80lb.','Semi-Gloss White 12 PT','Semi-Gloss White 14 PT','Semi-Gloss White 16 PT','Semi-Gloss White 18 PT','Smooth White 80lb.','Smooth White 100lb.','Gloss White 120lb.','Dull White 130lb.','Cast Coated White 12 PT','Marble Crush White 10 PT','Bright White 80lb. Felt','Fiber White 80lb.','Felt Warm White 80lb. ','Linen White 100lb. ','Hopsack White 90lb.','Cordwain White 90lb. ','Smooth Eco White 80lb.','White Eggshell 80lb'];
			$('#dropdown-quoteColorList > div > *').each(function() {
				if ($.inArray($(this).attr('data-colorname'), fourColorPrintingList) >= 0) {
					$(this).removeClass('colorSelectionHidden');
				}
				else {
					$(this).removeClass('colorSelectionHidden').addClass('colorSelectionHidden');
				}
			});
		}
		else {
			if ($('[name="quoteInsideColor"]').val() != 'Full Color Printing' && $('[name="quoteOutsideColor"]').val() != 'Full Color Printing') {
				$('#dropdown-quoteColorList > div > *').removeClass('colorSelectionHidden');
			}
		}
		var selection = $('.jqs-quoteColorList').find('.slSelected');
		if (selection.length > 0 && ($('[name="quoteInsideColor"]').val() == 'Full Color Printing' || $('[name="quoteOutsideColor"]').val() == 'Full Color Printing')) {
			if(selection.hasClass('colorSelectionHidden') || selection.hasClass('hidden')){
				$($('.jqs-quoteColorList .jqs-selectListItem').not($('.hidden, .colorSelectionHidden'))[0]).trigger('click');
				$('.selectListParent[data-selectlistname=quoteColor]').trigger('click');
			}
		}
	});

	<#-- GCLID -->
    window.onload = function() {
        document.getElementById('gclid_field').value = (typeof localStorage['gclid'] != 'undefined' ? localStorage['gclid'] : '');
    }

    $('.jqs-bignameToolTipTrigger').on('mouseenter', function() {
        $('body').append(
            $('<div />').attr('style', 'max-width: 400px; z-index: 99999; border: 1px solid #e3e3e3; background-color: #ffffff; padding: 10px; position: absolute; left: -9999px; top: 0px;').addClass('jqs-bignameToolTip').html($(this).attr('data-tipTitle')).append(
                $('<div />').attr('style', 'padding: 10px; position: absolute; bottom: -11px; transform: rotate(45deg); border-bottom: 1px solid #e3e3e3; border-right: 1px solid #e3e3e3; background-color: #ffffff;').addClass('jqs-bignameToolTipArrow')
			)
		);

		$('.jqs-bignameToolTip').css({
			'top': (parseInt($(this).offset().top) - getFullHeight($('.jqs-bignameToolTip')) - 20) + 'px',
			'left': (parseInt($(this).offset().left) - (getFullWidth($('.jqs-bignameToolTip')) / 2) + (getFullWidth($(this)) / 2)) + 'px'
		});

		$('.jqs-bignameToolTipArrow').css({
			'left': parseInt((getFullWidth($('.jqs-bignameToolTip')) / 2) -  (getFullWidth($('.jqs-bignameToolTipArrow')) / 2)) + 'px'
		})
	}).on('mouseleave', function() {
	    $('.jqs-bignameToolTip').remove();
	});
</script>

<!--START DNB VIFF JS-->
<script type="text/javascript" src="//cdn-0.d41.co/tags/ff-2.min.js" charset="utf-8"></script>

<script type="text/javascript">
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
</script>
<!-- END DNB VIFF JS-->