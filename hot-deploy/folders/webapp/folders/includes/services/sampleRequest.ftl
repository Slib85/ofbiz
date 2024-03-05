<style>
	.itemImage {
		border: 1px solid #000000;
	}
	.sampleList > div {
		border: 1px solid #dadada;
	}
		.sampleList > div > div {
			padding: 4px;
		}
		.sampleList .sampleListImage {
			width: 43px;
		}
			.sampleList .sampleListImage img {
				display: block;
				border: 1px solid #000000;
			}
		.sampleList > * > *:last-child {
			width: 20px;
		}
		.sampleList .sampleListInfo {
			width: auto;
			text-align: left;
		}
		.sampleList i {
			cursor: pointer;
			font-size: 20px;
		}
	.sampleInactive {
		opacity: .35;
		position: relative;
	}
</style>

<#assign sampleLimit = 3 />

<div class="textRight ftc-orange">Need Help? Call US 800-296-4321</div>
<div class="formContent requestSample" data-bigNameValidateForm="sampleRequest">
	<div class="foldersRow">
		<div class="foldersColumn small12 medium5 large4 textCenter">
			<h2 class="ftc-blue">${foldersProduct["Title"]?if_exists}</h2>
			<img class="marginTop20" data-modified="true" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${productId?if_exists}" alt="${productId?if_exists}">
			<h2 class="ftc-blue">Requested Print Samples:</h2>
			<div class="jqs-sampleList sampleList">
				<#list 1..sampleLimit as i>
				<div class="foldersTabularRow jqs-requestedSample<#if i gt 1> marginTop5 hidden</#if>">
					<div class="sampleListImage">
						<img class="jqs-imageInfo" src="" alt=""/>
					</div>
					<div class="sampleListInfo">
						<div class="foldersRow jqs-printingInfo"></div>
						<div class="foldersRow jqs-colorInfo"></div>
					</div>
					<div>
						<i class="fa fa-times ftc-orange jqs-disable"></i>
					</div>
				</div>
				</#list>
			</div>
		</div>
		<div class="foldersColumn small12 medium7 large8">
			<div class="jqs-samplesContainer" data-bigNameValidateForm="sampleInfoForm">
				<#list 1..sampleLimit as i>
				<div class="jqs-sample<#if i gt 1> hidden</#if>">
					<h2 class="ftc-blue">Print Sample ${i} Options</h2>
					<#if basicAndPremiumMaterialList?has_content>
					<div data-bigNameValidateId="printingMethod${i}" class="ftc-blue marginTop20<#if (productId == "08-96" || productId == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Blind Embossing")))> hidden</#if>">Printing Method:</div>
					<label class="bigNameSelect<#if (productId == "08-96" || productId == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Blind Embossing")))> hidden</#if>" data-bigNameValidateParent="printingMethod${i}">
						<select name="printingMethod${i}" data-bigNameValidate="printingMethod${i}" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Printing Method"<#if i gt 1> data-bigNameValidateInactive</#if><#if (productId == "08-96" || productId == "08-96-FOIL" || (requestParameters.printingType?exists && (requestParameters.printingType == "Foil Stamped" || requestParameters.printingType == "Blind Embossing")))> disabled</#if>>
							<option value="">Select a Printing Method</option>
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
					</#if>
					<div class="ftc-blue marginTop20" data-bignamevalidateid="sampleColor${i}">Material:</div>
					<div class="selectListParent" data-dropdown-target="dropdown-sampleColorList${i}" data-dropdown-options="click ignore-reverse-dropdown" data-selectListName="sampleColor${i}" data-selectListAction="updateRequestedSamples" data-bigNameValidate="sampleColor${i}" data-bigNameValidateType="selectList" data-bigNameValidateErrorTitle="Material"<#if i gt 1> data-bigNameValidateInactive</#if>>
						<div>
							<div class="please-select">
								<div class="ftc-blue">Please Select Material</div>
							</div>
						</div>
						<i class="fa fa-caret-down"></i>
					</div>
					<div id="dropdown-sampleColorList${i}" class="drop-down">
						<div class="selectListDropDown jqs-scrollable jqs-sampleColorList">
							<#assign listCount = 0 />
							<#if basicAndPremiumMaterialList?exists>
							<#list basicAndPremiumMaterialList as material>
							<div class="selectListItem jqs-selectListItem" data-selectListName="sampleColor${i}" data-colorName="${material["description"]?if_exists}" data-imgSrc="${material["img"]?if_exists}">
								<div>
									<img class="itemImage" src="${material["img"]?if_exists}?wid=72" alt="${material["description"]?if_exists}"/>
									<div>
										<div class="ftc-blue">${material["description"]?if_exists} <#if material["coating"]?exists>(<#if material["coating"] != "0">Coated ${material["coating"]} Side<#else>Non-Coated</#if>)</#if></div>
										<#--<div class="ftc-green">${material["type"]?if_exists}</div>-->
									</div>
								</div>
							</div>
							<#assign listCount = listCount + 1 />
							</#list>
							</#if>
							<#list nonBasicAndPremiumMaterialList as material>
							<div class="selectListItem jqs-selectListItem" data-selectListName="sampleColor${i}" data-colorName="${material["description"]?if_exists}" data-imgSrc="${material["img"]?if_exists}?ts=${pageTimestamp?default("65535")}">
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
				</#list>
			</div>
			<div class="jqs-sampleContactInformation hidden">
				<h2 class="ftc-blue">Contact Information</h2>
				<div class="foldersRow">
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="firstName" class="ftc-blue">First Name:</div>
						<input type="text" name="firstName" val="" data-bigNameValidate="firstName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="First Name" />
					</div>
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="lastName" class="ftc-blue">Last Name:</div>
						<input type="text" name="lastName" val="" data-bigNameValidate="lastName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Last Name" />
					</div>
				</div>
				<div class="foldersRow">
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="emailAddress" class="ftc-blue">Email Address:</div>
						<input type="text" name="emailAddress" val="" data-bigNameValidate="emailAddress" data-bigNameValidateType="required email" data-bigNameValidateErrorTitle="Email Address" />
					</div>
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="phoneNumber" class="ftc-blue">Phone Number:</div>
						<input type="text" name="phoneNumber" val="" data-bigNameValidate="phoneNumber" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Phone Number" />
					</div>
				</div>
				<div class="foldersRow">
					<div class="foldersColumn small12 large6">
						<div class="ftc-blue">Company Name:</div>
						<input type="text" name="companyName" val="" />
					</div>
				</div>
				<div class="foldersRow">
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="address" val="" class="ftc-blue">Address:</div>
						<input type="text" name="address" data-bigNameValidate="address" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="address" />
					</div>
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="city" val="" class="ftc-blue">City:</div>
						<input type="text" name="city" data-bigNameValidate="city" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="City" />
					</div>
				</div>
				<div class="foldersRow">
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="state" class="ftc-blue">State:</div>
						<label class="bigNameSelect" data-bigNameValidateParent="state">
							<select name="state" data-bigNameValidate="state" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="State">
								<#include "../forms/states.ftl" />
							</select>
						</label>
					</div>
					<div class="foldersColumn small12 large6">
						<div data-bigNameValidateId="zipCode" val="" class="ftc-blue">Zip Code:</div>
						<input type="text" name="zipCode" data-bigNameValidate="zipCode" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Zip Code" />
					</div>
				</div>
			</div>
			<div class="jqs-samplesThankYou hidden">
				<h2 class="ftc-blue">Thank You - Your print sample request has been submitted!</h2>
				<a href="tel:1-800-296-4321" class="ftc-orange">Any Questions? <strong>Call Us 800-296-4321</strong></a>
			</div>
			<div class="foldersRow marginTop20">
				<div class="foldersButton buttonGreen jqs-addSample pullLeft">Add Another Sample</div>
				<div class="foldersButton buttonGold jqs-continueSample pullRight" data-bigNameValidateSubmit="sampleInfoForm" data-bigNameValidateAction="sampleInfoSubmit">Continue <i class="fa fa-caret-right"></i></div>
				<div class="foldersButton buttonGreen jqs-editSample pullLeft hidden"><i class="fa fa-caret-left"></i> Edit Samples</div>
				<div class="foldersButton buttonGold jqs-submitSample pullRight hidden" data-bigNameValidateSubmit="sampleRequest" data-bigNameValidateAction="sampleRequestSubmit">Submit <i class="fa fa-caret-right"></i></div>
				<div class="foldersButton buttonGold jqs-closeSample jqs-bnRevealClose pullRight hidden">Close</div>
			</div>
		</div>
	</div>
</div>

<script>

	var sampleLimit = ${sampleLimit?default(1)};

	function updateRequestedSamples() {
		var totalSamples = $('.jqs-sample').length;

		for (var i = 0; i < totalSamples; i++) {
			var sampleElement = $('.jqs-sample:eq(' + i + ')');
			var requestedSampleElement = $('.jqs-requestedSample:eq(' + i + ')');
			if (!sampleElement.hasClass('hidden')) {
				requestedSampleElement.find('.jqs-imageInfo').attr('src', $('.slSelected[data-selectListName="sampleColor' + (i + 1) + '"]').attr('data-imgSrc'));
				requestedSampleElement.find('.jqs-printingInfo').html('<strong>Printing:</strong> ' + ($('[name="printingMethod' + (i + 1) + '"]').val() != '' ? $('[name="printingMethod' + (i + 1) + '"]').val() : 'None'));
				requestedSampleElement.find('.jqs-colorInfo').html('<strong>Color:</strong> ' + $('.slSelected[data-selectListName="sampleColor' + (i + 1) + '"]').attr('data-colorName'));
				requestedSampleElement.removeClass('hidden');
			}
		}
	}

	$('[name*="printingMethod"]').on('change', function() {
		updateRequestedSamples();
	});

	$('.jqs-requestedSample .jqs-disable').on('click', function() {
		var sampleElement = $('.jqs-sample:eq(' + $(this).closest('.jqs-requestedSample').index() + ')');

		if ($(this).hasClass('fa-times')) {
			$(this).closest('.jqs-requestedSample').find('.sampleListImage').addClass('sampleInactive');
			$(this).closest('.jqs-requestedSample').find('.sampleListInfo').addClass('sampleInactive');
			$(this).removeClass('fa-times').removeClass('ftc-orange').addClass('fa-plus ftc-green');

			sampleElement.addClass('sampleInactive').append(
				$('<div />').addClass('jqs-inactiveOverlay').css({
					'position': 'absolute',
					'top': '0px',
					'left': '0px',
					'height': sampleElement.outerHeight(),
					'width': sampleElement.outerWidth(),
					'background': 'transparent'
				})
			);

			sampleElement.find('[data-bigNameValidate]').attr('data-bigNameValidateInactive', '');
		}
		else {
			$(this).closest('.jqs-requestedSample').find('.sampleListImage').removeClass('sampleInactive');
			$(this).closest('.jqs-requestedSample').find('.sampleListInfo').removeClass('sampleInactive');
			$(this).removeClass('fa-plus').removeClass('ftc-green').addClass('fa-times ftc-orange');
			sampleElement.removeClass('sampleInactive').find('.jqs-inactiveOverlay').remove();
			sampleElement.find('[data-bigNameValidate]').removeAttr('data-bigNameValidateInactive');
		}
	});

	$('.jqs-addSample').on('click', function() {
		var activeSamples = sampleLimit - parseInt($('.jqs-sample.hidden').length);

		if (activeSamples != sampleLimit) {
			$('.jqs-sample:eq(' + activeSamples + ')').removeClass('hidden').find('[data-bigNameValidate]').removeAttr('data-bigNameValidateInactive');
			$('.jqs-requestedSample:eq(' + activeSamples + ')').removeClass('hidden');
			updateRequestedSamples();
			activeSamples++;
		}

		if (activeSamples == sampleLimit) {
			$(this).remove();
		}
	});

	$('.jqs-editSample').on('click', function() {
		$('.jqs-sampleList .jqs-disable').removeClass('hidden');
		$('.jqs-continueSample').removeClass('hidden');
		$('.jqs-addSample').removeClass('hidden');
		$('.jqs-samplesContainer').removeClass('hidden');
		$('.jqs-editSample').addClass('hidden');
		$('.jqs-submitSample').addClass('hidden');
		$('.jqs-sampleContactInformation').addClass('hidden');
	});

	function sampleInfoSubmit() {
		$('.jqs-sampleList .jqs-disable').addClass('hidden');
		$('.jqs-continueSample').addClass('hidden');
		$('.jqs-addSample').addClass('hidden');
		$('.jqs-samplesContainer').addClass('hidden');
		$('.jqs-editSample').removeClass('hidden');
		$('.jqs-submitSample').removeClass('hidden');
		$('.jqs-sampleContactInformation').removeClass('hidden');
	}

	function sampleRequestSubmit() {
		$.ajax({
			type: "POST",
			url: "<@ofbizUrl>/sampleRequestSubmission</@ofbizUrl>",
			data: {
				sku: '${productId?if_exists}',
				printingMethod1: (!$('.jqs-sample:eq(0)').hasClass('hidden') && !$('.jqs-sample:eq(0)').hasClass('sampleInactive') ? $('select[name="printingMethod1"]').val() : 'None'),
				printingMethod2: (!$('.jqs-sample:eq(1)').hasClass('hidden') && !$('.jqs-sample:eq(1)').hasClass('sampleInactive') ? $('select[name="printingMethod2"]').val() : 'None'),
				printingMethod3: (!$('.jqs-sample:eq(2)').hasClass('hidden') && !$('.jqs-sample:eq(2)').hasClass('sampleInactive') ? $('select[name="printingMethod3"]').val() : 'None'),
				colorName1: (!$('.jqs-sample:eq(0)').hasClass('hidden') && !$('.jqs-sample:eq(0)').hasClass('sampleInactive') ? $('.slSelected[data-selectListName="sampleColor1"]').attr('data-colorName') : 'None'),
				colorName2: (!$('.jqs-sample:eq(1)').hasClass('hidden') && !$('.jqs-sample:eq(1)').hasClass('sampleInactive') ? $('.slSelected[data-selectListName="sampleColor2"]').attr('data-colorName') : 'None'),
				colorName3: (!$('.jqs-sample:eq(2)').hasClass('hidden') && !$('.jqs-sample:eq(2)').hasClass('sampleInactive') ? $('.slSelected[data-selectListName="sampleColor3"]').attr('data-colorName') : 'None'),
				firstName: $('input[name="firstName"]').val(),
				lastName: $('input[name="lastName"]').val(),
				email: $('input[name="emailAddress"]').val(),
				phoneNumber: $('input[name="phoneNumber"]').val(),
				companyName: $('input[name="companyName"]').val(),
				address: $('input[name="address"]').val(),
				city: $('input[name="city"]').val(),
				state: $('select[name="state"]').val(),
				zipCode: $('input[name="zipCode"]').val()
			},
			dataType:'json',
			cache: false
		}).done(function(data) {
            if (data.success) {
                GoogleAnalytics.trackEvent('Sample Request', 'Finish', 'Success');
            }
            else {
                GoogleAnalytics.trackEvent('Sample Request', 'Finish', 'Error');
            }
		});

		$('.jqs-editSample').addClass('hidden');
		$('.jqs-submitSample').addClass('hidden');
		$('.jqs-closeSample').removeClass('hidden');
		$('.jqs-sampleContactInformation').addClass('hidden');
		$('.jqs-samplesThankYou').removeClass('hidden');

	}

	// Limit material options for full color printing
	$('.requestSample').on('change', 'select[data-bignamevalidate]', function() {
		var sampleNum = $(this).attr('name').match(/\d+$/);
		var colorList = '#dropdown-sampleColorList' + sampleNum;
		if ($(this).val() == 'Full Color Printing') {
			var fourColorPrintingList = ['Vellum Vanilla 80lb.','Fiber Natural 80lb.','Linen Natural 100lb.','Smooth Eco Ivory 80lb.','Felt Pumice 80lb.','Semi-Gloss White 12 PT','Semi-Gloss White 14 PT','Semi-Gloss White 16 PT','Semi-Gloss White 18 PT','Smooth White 80lb.','Smooth White 100lb.','Gloss White 120lb.','Dull White 130lb.','Cast Coated White 12 PT','Marble Crush White 10 PT','Bright White 80lb. Felt','Fiber White 80lb.','Felt Warm White 80lb. ','Linen White 100lb. ','Hopsack White 90lb.','Cordwain White 90lb. ','Smooth Eco White 80lb.','White Eggshell 80lb'];
			$(colorList + " > div > *").each(function() {
				if ($.inArray($(this).attr('data-colorname'), fourColorPrintingList) >= 0) {
					$(this).removeClass('colorSelectionHidden');
				}
				else {
					$(this).removeClass('colorSelectionHidden').addClass('colorSelectionHidden');
				}	
			});
		} else {
			$(colorList + " > div > *").removeClass('colorSelectionHidden');
		}
		var selection = $(colorList).find('.slSelected');
		if (selection.length > 0 && ($(this).val() == 'Full Color Printing') ) {
			if(selection.hasClass('colorSelectionHidden') || selection.hasClass('hidden')){
				$(colorList + ' .jqs-selectListItem').not('.hidden, .colorSelectionHidden').eq(0).trigger('click');
				$('.selectListParent[data-selectlistname=sampleColor' + sampleNum + ']').trigger('click');
			}
		}
	});

	updateRequestedSamples();
</script>
