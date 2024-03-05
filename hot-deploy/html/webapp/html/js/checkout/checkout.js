var pageType = 'checkout';

function setAmazonOrderReferenceDetails(amazonOrderReferenceId) {
	if(typeof amazonOrderReferenceId !== 'undefined' && amazonOrderReferenceId !== '') {
		$('#checkoutForm input[name=externalId]').val(amazonOrderReferenceId);
	}
}
function setPayPalOrderReferenceDetails(paypalOrderReferenceId) {
    if(typeof paypalOrderReferenceId !== 'undefined' && paypalOrderReferenceId !== '') {
        $('#checkoutForm input[name=externalIdPP]').val(paypalOrderReferenceId);
    }
}
function validatePayPalAmount() {
    $('#paypalNotice').attr('data-grand-total', $('.total-cost').html().replace(/[^\d.-]/g, '')); //set new grand total

    //if price changes and paypal has been authorized, alert user
    var authedAmount = $('#paypalNotice').attr('data-authed-amount');
    var grandTotal = $('#paypalNotice').attr('data-grand-total');
    if(authedAmount != '' && parseFloat(authedAmount) < parseFloat(grandTotal) && $('#checkoutForm input[name=externalIdPP]').val() != '') {
        $('#paypalIframe').attr('src', $('#paypalIframe').attr('src')); //refresh iframe with new paypal price
        $('#checkoutForm input[name=externalIdPP]').val(''); //reset paypal nonce value
        $('#paypalNotice').html('Error, your PayPal account was authorized for less then $' + grandTotal + '. Please reauthorize your account by clicking the \'PayPal Checkout\' button again.');
    } else if($('#checkoutForm input[name=externalIdPP]').val() == '') {
        $('#paypalIframe').attr('src', $('#paypalIframe').attr('src')); //refresh iframe with new paypal price
	}
}

//below variables are used for google address autocomplete
var globalPopulateAddress = null;
var globalResetAddress = null;

(function($) {
	var populateAddress = function(data, scope, partialReset) {
		if(data === undefined) {
			resetAddress(scope);
		} else {
			$('.jqs-address-type[value=' + data.businessOrResidence + ']', scope).prop('checked', 'checked');
            if(typeof partialReset === 'undefined' || (typeof partialReset !== 'undefined' && !partialReset)) {
//                $('.jqs-email'/*, scope*/).val(data.infoString);
				$('.jqs-first-name', scope).val(data.firstName);
				$('.jqs-last-name', scope).val(data.lastName);
				$('.jqs-company-name', scope).val(data.companyName);
            }
			$('.jqs-address1', scope).val(data.address1);
			$('.jqs-address2', scope).val(data.address2);
//        $('.jqs-address3', scope).val(data.address3);
			$('.jqs-city', scope).val(data.city);
			$('.jqs-state', scope).val(data.stateProvinceGeoId);
			$('.jqs-postal-code', scope).val(data.postalCode);
			if($('.jqs-country option[value=' + data.countryGeoId + ']', scope).length > 0) {
                $('.jqs-country', scope).val(data.countryGeoId);
			}
			if(scope == '.jqs-shipping' && data.postalCode != '') {
				$.getShippingRates($('.jqs-shipping').find('input,select').serializeObject(), pageType);
			}
			$('.jqs-phone', scope).val(data.contactNumber);
			$('.jqs-blind-ship', scope).prop('checked', data.isBlindShipment == 'Y');
			resetValidationErrors(scope);
		}
	};

    globalPopulateAddress = populateAddress;

	var resetValidationErrors = function(scope) {
		$('.jqs-abide', scope).closest('div').removeClass('error');
	};

	var resetAddress = function(scope, partialReset) {
        $('.jqs-address-type[value=BUSINESS_LOCATION]', scope).prop('checked', 'checked');
		if(typeof partialReset === 'undefined' || (typeof partialReset !== 'undefined' && !partialReset)) {
//            $('.jqs-email', scope).val('');
            $('.jqs-first-name', scope).val('');
            $('.jqs-last-name', scope).val('');
		}
		$('.jqs-company-name', scope).val('');
		$('.jqs-address1', scope).val('');
		$('.jqs-address2', scope).val('');
//        $('.jqs-address3', scope).val('');
		$('.jqs-city', scope).val('');
		$('.jqs-state', scope).val('');
		$('.jqs-postal-code', scope).val('');
		$('.jqs-phone', scope).val('');
		$('.jqs-blind-ship', scope).prop('checked', '');
	};

    globalResetAddress = resetAddress;

	$.extend({
		populateShippingAddress: function(addressData) {
			populateAddress(addressData, '.jqs-shipping');
			if($("[name=bill_to_shipping]").is(':checked')) {
				$.billToShippingAddress();
			}
		},

		populateBillingAddress: function(addressData) {
			populateAddress(addressData, '.jqs-billing');
		},

		sendCartDataToBronto: function(emailAddress) {
			if(validateEmailAddress(emailAddress)) {
				$.ajax({
					type: 'POST',
					url: '/' + websiteId + '/control/sendCartDataToBronto',
					data: 'emailAddress=' + emailAddress + '&emailSource=checkout&isCheckOut=yes',
					dataType: 'json',
					cache: false
				}).done(function(data) {
					if(data.success) {
						//successfully sent cart data to bronto
					} else {
						//failed to send cart data to bronto
					}
				}).fail(function(data) {
					//failed to send cart data to bronto
				});
			}
		},

		billToShippingAddress: function() {
			resetAddress('.jqs-billing');
			resetValidationErrors('.jqs-billing');
//			$('.jqs-email', '.jqs-billing').val($('.jqs-email', '.jqs-shipping').val());
			$('.jqs-first-name', '.jqs-billing').val($('.jqs-first-name', '.jqs-shipping').val());
			$('.jqs-last-name', '.jqs-billing').val($('.jqs-last-name', '.jqs-shipping').val());
			$('.jqs-company-name', '.jqs-billing').val($('.jqs-company-name', '.jqs-shipping').val());
			$('.jqs-address1', '.jqs-billing').val($('.jqs-address1', '.jqs-shipping').val());
			$('.jqs-address2', '.jqs-billing').val($('.jqs-address2', '.jqs-shipping').val());
//        $('.jqs-address3', '.jqs-billing').val($('.jqs-address3', '.jqs-shipping').val());
			$('.jqs-city', '.jqs-billing').val($('.jqs-city', '.jqs-shipping').val());
			$('.jqs-state', '.jqs-billing').val($('.jqs-state', '.jqs-shipping').val());
			$('.jqs-postal-code', '.jqs-billing').val($('.jqs-postal-code', '.jqs-shipping').val());
			$('.jqs-country', '.jqs-billing').val($('.jqs-country', '.jqs-shipping').val());
			$('.jqs-phone', '.jqs-billing').val($('.jqs-phone', '.jqs-shipping').val());
		},

		bindToggleLoginPanelClickEvent: function() {
			$(".collapse").on("click", function() {
				$.toggleLoginPanel();
			});
		},

		bindOnblurEventToEmailAddress: function() {
			/*$('input[name=emailAddress]').on('blur', function() {
				$.sendCartDataToBronto($(this).val());
			});*/
		},

		toggleLoginPanel: function() {
			$(".log-in .optional-content").toggle();

			$(".log-in").find("i").hasClass("fa-caret-down") ?
				$(".log-in").find("i").removeClass("fa-caret-down").addClass("fa-caret-up") :
				$(".log-in").find("i").removeClass("fa-caret-up").addClass("fa-caret-down");
		},

		bindSavedBillingAddressChangeEvent: function() {
			$('#saved-billing-address').on('change', function() {
				$.populateBillingAddress($('option:selected', $(this)).data('address'));
			});
		},

		bindSavedShippingAddressChangeEvent: function() {
			$('#saved-shipping-address').on('change', function() {
				$.populateShippingAddress($('option:selected', $(this)).data('address'));
			});
		},

		bindShippingCountryChangedEvent: function() {
			var previousCountryValue = '';
			setInterval(function() {
				var currentCountryValue = $('.jqs-shipping-country').val();
				if(previousCountryValue != currentCountryValue) {
					previousCountryValue = currentCountryValue;
					if(currentCountryValue == '' || currentCountryValue == 'USA' || currentCountryValue == 'CAN') {
						$(".jqs-payment-options").removeClass('hidden');
						$(".jqs-shipping-options").removeClass('hidden');
						$("#jqs-iparcel").removeClass('hidden').addClass('hidden');
						$("#jqs-place-order").removeClass('hidden');
						if(!freeOrder) {
							$('#pay_by_cc').trigger('click');
						}
					} else {
						$(".jqs-payment-options").removeClass('hidden').addClass('hidden');
						$(".jqs-shipping-options").removeClass('hidden').addClass('hidden');
						$("#jqs-iparcel").removeClass('hidden');
						$("#jqs-place-order").removeClass('hidden').addClass('hidden');
						$.toggleAbideValidation('#jqs-pay-by-card', true);
						$.toggleAbideValidation('#jqs-pay-by-amazon', true);
                        $.toggleAbideValidation('#jqs-pay-by-paypal', true);
						$.toggleAbideValidation('#jqs-pay-by-check', true);
						$.toggleAbideValidation('#jqs-pay-by-net30', true);
					}
				}
			}, 100);

		},

		bindShippingPostalCodeChangeEvent: function() {
			$('input[name="shipping_postalCode"], input[name="shipping_address1"], select[name="shipping_stateProvinceGeoId"], select[name="shipping_countryGeoId"], input[name="ship_to"]').off('change.getShippingRates').on('change.getShippingRates', function(e) {
                if($(this).attr('name') == 'shipping_stateProvinceGeoId') {
                    $.countryGeoIdValidation(null, 'select[name="shipping_stateProvinceGeoId"]');
                }
                waitForFinalEvent(function() {
                    $.getShippingRates($('.jqs-shipping').find('input,select').serializeObject(), pageType);
                }, 1000, 'calculateShipping');
			});
		},

		bindCustomAbideValidationEvent: function() {
			$('.jqs-abide').each(function() {
				var validationMessageElement = $(this).next();
				var defaultValidationMessage = validationMessageElement.html();
				var invalidMessage = 'Valid ' + defaultValidationMessage;
				$(this).on('blur', function(e) {
					if($(this).val() != '') {
						validationMessageElement.html(invalidMessage);
					} else {
						validationMessageElement.html(defaultValidationMessage);
					}
				}).on('focus', function(e) {
					$(this).parent().removeClass('error');
				});
			});
		},

		bindSubmitEvent: function() {
			$('#checkout-error').hide();
			$('#checkout-message').hide();

			$('[data-abide]').on('submit', function() {
				if($("[name=bill_to_shipping]").is(':checked')) {
					$.billToShippingAddress();
				}
				$('.jqs-custom-abide').each(function() {
					var validationMessageElement = $(this).next();
					var defaultValidationMessage = validationMessageElement.text();
					var invalidMessage = 'Valid ' + defaultValidationMessage.substring(0, 1).toLowerCase() + defaultValidationMessage.substring(1);
					if($(this).val() != '') {
						validationMessageElement.html(invalidMessage);
					} else {
						validationMessageElement.html(defaultValidationMessage);
					}
				});
				$('#ship_to_business', this).focus();
			});

			$('#checkoutForm')
				.on('invalid.fndtn.abide', function () {
					$('#checkout-error').text('Please correct the errors shown below.').show();
				})
				.on('valid.fndtn.abide', function () {
					$('#checkout-error').hide();
					$("body").spinner(true, false, 250, 6, undefined, 'Your order is being processed...');
				});
		},

		countryGeoIdValidation: function(postalCodeElement, stateElement) {
			var type = undefined;
			var postalCode = '';
			if(postalCodeElement != null) {
				postalCode = $(postalCodeElement).val();
				type = $(postalCodeElement).data('type');
			} else if(stateElement != null) {
				type = $(stateElement).data('type');
			}
			var stateType = type == 'shipping' || type == 'billing' ? $('[name=' + type +'_stateProvinceGeoId] option:selected').data('type') : undefined;
			if(stateType == 'AUS') {
				$('[name=' + type + '_countryGeoId]').val('AUS');
				return true;
			} else if(stateType == 'BEL') {
				$('[name=' + type +'_countryGeoId]').val('BEL');
				return true;
			} else if(stateType == 'DEU') {
				$('[name=' + type +'_countryGeoId]').val('DEU');
				return true;
			} else if(stateType == 'FRA') {
				$('[name=' + type +'_countryGeoId]').val('FRA');
				return true;
			} else if(stateType == 'HUN') {
				$('[name=' + type +'_countryGeoId]').val('HUN');
				return true;
			} else if(stateType == 'IND') {
				$('[name=' + type +'_countryGeoId]').val('IND');
				return true;
			} else if(stateType == 'IRL') {
				$('[name=' + type +'_countryGeoId]').val('IRL');
				return true;
            } else if(stateType == 'ITA') {
                $('[name=' + type +'_countryGeoId]').val('ITA');
                return true;
			} else if(stateType == 'FIN') {
                $('[name=' + type +'_countryGeoId]').val('FIN');
                return true;
			} else if(stateType == 'MEX') {
				$('[name=' + type +'_countryGeoId]').val('MEX');
				return true;
			} else if(stateType == 'NLD') {
				$('[name=' + type +'_countryGeoId]').val('NLD');
				return true;
			} else if(stateType == 'POL') {
				$('[name=' + type +'_countryGeoId]').val('POL');
				return true;
			} else if(stateType == 'PRT') {
				$('[name=' + type +'_countryGeoId]').val('PRT');
				return true;
			} else if(stateType == 'ESP') {
				$('[name=' + type +'_countryGeoId]').val('ESP');
				return true;
			} else if(stateType == 'CHE') {
				$('[name=' + type +'_countryGeoId]').val('CHE');
				return true;
			} else if(stateType == 'GBR') {
				$('[name=' + type +'_countryGeoId]').val('GBR');
				return true;
			} else if(stateType == 'SWE') {
				$('[name=' + type +'_countryGeoId]').val('SWE');
				return true;
			} else if(stateType == 'DNK') {
				$('[name=' + type +'_countryGeoId]').val('DNK');
				return true;
			} else if(stateType == 'NZL') {
				$('[name=' + type +'_countryGeoId]').val('NZL');
				return true;
			} else if((postalCode != '' && $.isCanadianPostalCode(postalCode) && (stateType == undefined || stateType == 'CAN')) || stateType == 'CAN') {
				$('[name=' + type +'_countryGeoId]').val('CAN');
				return true;
			} else if((postalCode != '' && $.isUSPostalCode(postalCode) && (stateType == undefined || stateType == 'US' || stateType == 'US-OTHERS')) || (stateType == 'US' || stateType == 'US-OTHERS')) {
				$('[name=' + type +'_countryGeoId]').val('USA');
				return true;
			}

			if(postalCodeElement != null) {
				return false;
			} else if(stateElement != null) {
				return true;
			}

			return false;
		},

		amazonPaymentValidation: function(externalId) {
			return externalId != '';
		},

        paypalPaymentValidation: function(externalId) {
            return externalId != '';
        },

		bindCCNumberBlurEvent: function() {
			$('[name=cardNumber]').on('blur', function() {
                $('[name=cardType]').val(getCCTypeDetailed($(this).val()));
			});
		},

		bindBillToShippingClickEvent: function() {
			$("[name=bill_to_shipping]").on("click", function() {
				if ($(this).is(":checked")) {
					$(".billing-content").css("display", "none");
					$.billToShippingAddress();
					$.toggleAbideValidation('.jqs-billing', true);
				} else {
					$.billToShippingAddress();
					$(".billing-content").css("display", "block");
					$.toggleAbideValidation('.jqs-billing');
				}
			});
		},

		bindPayByClickEvent: function() {
			$("[name=paymentMethodTypeId]").on("click", function() {
				if ($(this).val() == "PERSONAL_CHECK") {
					$(".pay-by-card").css("display", "none");
					$(".pay-by-check").css("display", "block");
					$(".pay-by-amazon").css("display", "none");
					$(".pay-by-net30").css("display", "none");
                    $(".pay-by-paypal").css("display", "none");
                    $.toggleAbideValidation('#jqs-pay-by-check');
                    $.toggleAbideValidation('#jqs-pay-by-card', true);
                    $.toggleAbideValidation('#jqs-pay-by-paypal', true)
					$.toggleAbideValidation('#jqs-pay-by-amazon', true);
					$.toggleAbideValidation('#jqs-pay-by-net30', true);
				} else if ($(this).val() == "EXT_NET30") {
					$(".pay-by-card").css("display", "none");
					$(".pay-by-check").css("display", "none");
					$(".pay-by-amazon").css("display", "none");
					$(".pay-by-net30").css("display", "block");
                    $(".pay-by-paypal").css("display", "none");
					$.toggleAbideValidation('#jqs-pay-by-net30');
                    $.toggleAbideValidation('#jqs-pay-by-paypal', true);
					$.toggleAbideValidation('#jqs-pay-by-amazon', true);
					$.toggleAbideValidation('#jqs-pay-by-card', true);
					$.toggleAbideValidation('#jqs-pay-by-check', true);
				} else if ($(this).val() == "EXT_AMAZON") {
					$(".pay-by-card").css("display", "none");
					$(".pay-by-check").css("display", "none");
					$(".pay-by-amazon").css("display", "block");
					$(".pay-by-net30").css("display", "none");
                    $(".pay-by-paypal").css("display", "none");
					$.toggleAbideValidation('#jqs-pay-by-amazon');
                    $.toggleAbideValidation('#jqs-pay-by-paypal', true)
					$.toggleAbideValidation('#jqs-pay-by-card', true);
					$.toggleAbideValidation('#jqs-pay-by-check', true);
					$.toggleAbideValidation('#jqs-pay-by-net30', true);
                } else if ($(this).val() == "EXT_PAYPAL_CHECKOUT") {
                    $(".pay-by-card").css("display", "none");
                    $(".pay-by-check").css("display", "none");
                    $(".pay-by-amazon").css("display", "none");
                    $(".pay-by-net30").css("display", "none");
                    $(".pay-by-paypal").css("display", "block");
                    $.toggleAbideValidation('#jqs-pay-by-paypal');
                    $.toggleAbideValidation('#jqs-pay-by-amazon', true);
                    $.toggleAbideValidation('#jqs-pay-by-card', true);
                    $.toggleAbideValidation('#jqs-pay-by-check', true);
                    $.toggleAbideValidation('#jqs-pay-by-net30', true);
				} else {
					$(".pay-by-card").css("display", "block");
					$(".pay-by-amazon").css("display", "none");
					$(".pay-by-check").css("display", "none");
					$(".pay-by-net30").css("display", "none");
                    $(".pay-by-paypal").css("display", "none");
					$.toggleAbideValidation('#jqs-pay-by-card');
					$.toggleAbideValidation('#jqs-pay-by-check', true);
                    $.toggleAbideValidation('#jqs-pay-by-paypal', true)
					$.toggleAbideValidation('#jqs-pay-by-amazon', true);
					$.toggleAbideValidation('#jqs-pay-by-net30', true);
				}
			});
		},

		bindVolatileEvents: function() {
			$.bindApplyCouponButtonClickEvent();
		},

		bindAllEvents: function() {
			$.bindToggleLoginPanelClickEvent();
			$.bindOnblurEventToEmailAddress();
			$.bindSavedShippingAddressChangeEvent();
			$.bindShippingCountryChangedEvent();
			$.bindShippingPostalCodeChangeEvent();
			$.bindSavedBillingAddressChangeEvent();
			$.bindBillToShippingClickEvent();
			$.bindShippingMethodClickEvent();
			$.bindPayByClickEvent();
			$.bindCCNumberBlurEvent();
			$.bindCustomAbideValidationEvent();
			$.bindSubmitEvent();
			$.bindVolatileEvents();
			$.toggleAbideValidation('#jqs-pay-by-amazon', true);
		},

		triggerAllLoadEvents: function() {
			$("[name=bill_to_shipping]").attr('checked', false);
			if($('#checkout-message').text() != '') {
				$('#checkout-message').removeClass('hidden').show();
			}

			if($('#checkout-error').text() != '') {
				$('#checkout-error').removeClass('hidden').show();
			}

			if(freeOrder) {
				$.toggleAbideValidation('#jqs-pay-by-card', true);
				$.toggleAbideValidation('#jqs-pay-by-check', true);
				$.toggleAbideValidation('#jqs-pay-by-amazon', true);
				$.toggleAbideValidation('#jqs-pay-by-paypal', true);
                $.toggleAbideValidation('#jqs-pay-by-net30', true);
				$('div.payment-options').hide();
			}
		}
	});
})(jQuery);

$(document).ready(function() {
	$.bindAllEvents();
	$.triggerAllLoadEvents();

	$("#pay_by_cc").prop("checked", true);

	/*
	if (navigator.appVersion.indexOf('MSIE 8.') == -1) {
		$(document).foundation({
			abide: {
				live_validate : false,
				focus_on_invalid : false,
				validators: {
					validatePostalCode: function (el, required, parent) {
						return $.countryGeoIdValidation(el, null);
					},
					validateStateCode: function (el, required, parent) {
						return $.countryGeoIdValidation(null, el);
					},
					validateCardNumber: function (el, required, parent) {
						return $.getCCTypeDetailed(el.value, true) != '';
					},
					validateAmazonPayment: function(el, required, parent) {
						return $.amazonPaymentValidation(el.value);
					},
					validatePayPalPayment: function(el, required, parent) {
						return $.paypalPaymentValidation(el.value);
					}
				}
			}
		});
	}
	*/

	$('.jqs-tracking input, .jqs-tracking select, .jqs-tracking textarea').on('click.coTrack focus.coTrack', function() {
		var stepSeq = $(this).closest('.jqs-tracking').data('tracking-seq');
		var stepName = $(this).closest('.jqs-tracking').data('tracking-name');

		// Only allow one time tracking.  Any further clicks must be ignored to prevent spam.
		$('[data-tracking-name="' + stepName + '"]').find('input').each(function() {
			$(this).off('click.coTrack focus.coTrack');
		}).end().find('select').each(function() {
			$(this).off('click.coTrack focus.coTrack');
		}).end().find('textarea').each(function() {
			$(this).off('click.coTrack focus.coTrack');
		});

		// Do Google Tracking for click here
		ga('ec:setAction','checkout', {
			'step': stepSeq,
			'option': stepName
		});
		ga('send', 'event', 'UX', 'click', 'checkout');
	});

	$('#jqs-iparcel').on('click', function() {
		$("body").spinner(true, false, 250, 6, undefined, 'Your order is being processed...');
		$.ajax({
			type: 'POST',
			url: '/' + websiteId + '/control/iparcelCheckoutRequest',
			data: $('#checkoutForm').serialize(),
			dataType: 'json',
			cache: false
		}).done(function(data) {
			if(data.success) {
				window.location.href = data.url;
			} else {
				alert('There was an error processing your order.');
			}
		}).fail(function(data) {
			//failed
		});
	});

	$('[name="shipping_stateProvinceGeoId"]').on('change.specialMessage', function() {
		if ($(this).val() == 'AL') {
            var websiteName = 'Envelopes.com';

			if (typeof websiteId != 'undefined') {
				if (websiteId == 'ae' || websiteId == 'envelopes') {
					websiteName = 'Envelopes.com';
				} else if (websiteId == 'folders') {
					websiteName = 'Folders.com';
				}
			}

			$('.jqs-country[name="shipping_countryGeoId"]').parent().before(
				$('<div />').css('font-size', '11px').attr('bns-statemessage', '').html(websiteName + ' has collected the simplified sellers use tax on this transaction for Alabama customers and the tax will be remitted on the customer’s behalf to the Alabama Department of Revenue.  Seller’s program account number is SSU-R009861599.')
			)
		} else {
			$('[bns-statemessage]').remove();
		}
	});

    $('[name="shipping_stateProvinceGeoId"]').trigger('change.specialMessage');

	$('.jqs-likeUsAndSave .fa-times').on('click', function() {
		$('.jqs-likeUsAndSave').hide();
	});
});

function toggleFBPromo() {
	if($('.jqs-likeUsAndSave').length > 0 && $('.jqs-likeUsAndSave').is(':hidden')) {
		$('.jqs-likeUsAndSave').show();
	} else if($('.jqs-likeUsAndSave').length > 0 && !$('.jqs-likeUsAndSave').is(':hidden')) {
		$('.jqs-likeUsAndSave').hide();
	}
}

//FACEBOOK CODE
window.fbAsyncInit = function() {
	FB.init({
		appId      : '380284282029687',
		channelUrl : 'https://www.envelopes.com/channel.html',
		status     : true,
		cookie     : true,
		oauth      : true,
		xfbml      : true,
		version    : 'v2.6'
	});
	FB.getLoginStatus(function(response) {
		if(response.status === 'connected') {
			if(response.authResponse) {
				FB.api('/me/likes/85458613795', function(response) {
					if(response.data[0]) {
						//do nothing if the person already likes the page
					} else {
						//toggleFBPromo();
						//log(true, "FB1");
					}
				});
			}
		} else {
			//if they are not logged in show the coupon
			//toggleFBPromo();
			//log(true, "FB2");
		}
	}, true);
	FB.Event.subscribe('edge.create', function(response) {
		if(document.getElementsByClassName('jqs-likeUsAndSave').length > 0) {
			$.applyCouponCode('FBSAVE1', 'checkout');
			//toggleFBPromo();
			//log(true, "FB3");
		}
	});
};
(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) return;
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));