var pageType = 'cart';

(function($){
	$.extend({
		bindContinueShoppingButtonClickEvent: function() {
			$('.jqs-continue-shopping').off('click').on('click', function() {
				window.location.href = lastVisitedURL;
			});
		},

		calculateShipping: function() {
			var postalCode = $('input[name=shipping_postalCode]').val();
			var geoId = $('select[name="shipping_countryGeoId"]').val();
			var shipTo = $('input[name="ship_to"]:checked').val();
			if($.isValidPostalCode({'shipping_postalCode' : postalCode, 'shipping_countryGeoId' : geoId, 'ship_to' : shipTo})) {
				$('#shippingOptionsForm').spinner(true, false, 75);
				$.getShippingRates({'shipping_postalCode' : postalCode, 'shipping_countryGeoId': geoId, 'ship_to': shipTo}, pageType);
			} else {
				//
			}
		},

		bindRemoveItemClickEvent: function() {
			$('.jqs-remove-item').on('click', function() {
				$('.line-items').spinner(true, false, 100);
				var index = $(this).data('index');
				$.removeItem(index, $(this).data('sku'));
			});
		},

		bindItemQuantityChangeEvent: function() {
			$('.jqs-quantity').on('change', function() {
				var index = $(this).data('index');
				if($(this).val() == 'custom') {
					$('input[name=customQuantity]:eq(' + index +')').removeClass('hidden');
				} else {
					$('input[name=customQuantity]:eq(' + index +')').addClass('hidden');
					var quantity = $('option:selected', $(this)).val();
					$.updateItemQuantity(index, quantity, pageType, false);
				}
			});
		},

		bindItemCustomQuantityChangeEvent: function() {
			$('input[name=customQuantity]').on('keyup', function(e) {
				var index = $(this).data('index');
				var quantity = $(this).val();
				waitForFinalEvent(function () {
					if(quantity != '') {
						var increments;
						var quantityDropdown = $('select[name=quantity]:eq(' + index + ')').find('option');
						var sampleCounter = 0;
						quantityDropdown.each(function(){
							$(this).attr('data') == 'sample' ? sampleCounter++ : '';
							sampleCounter > 0 ? increments = parseInt($('select[name=quantity]:eq(' + index + ')').find('option:eq(2)').val()) : increments = parseInt($('select[name=quantity]:eq(' + index + ')').find('option:eq(1)').val());
						});
						var validationMessage = $.validateQuantity(index, quantity, increments);
						if(validationMessage == 'valid') {
							$.updateItemQuantity(index, quantity, pageType, true);
						} else {
							alert(validationMessage);
						}
					}
				}, 1000, 'customQuantity_' + index);
			});
		},

		bindItemProductionTimeChangeEvent: function() {
			$('.jqs-production-time').on('change', function() {
				var index = $(this).data('index');
				var quantity = $('option:selected', $('select[name=quantity]:eq(' + index +')')).val();
				var productionTime = $('option:selected', $(this)).val();
				$.updateItemProductionTime(index, quantity, productionTime);
			});
		},

		bindReEditOfDesigns: function() {
			$('[data-projectId]').on('click', function(e) {
				window.location.href = $(this).attr('data-editurl');
			});
		},

		bindCustomAbideValidationEvent: function() {
			$('.jqs-abide').each(function() {
				var validationMessageElement = $(this).next().next();
				var defaultValidationMessage = validationMessageElement.html();
				$(this).on('blur', function(e) {
					if($(this).val() == '') {
						validationMessageElement.html(defaultValidationMessage);
					}
				}).on('focus', function(e) {
					$(this).parent().removeClass('error');
				});
			});

			if (navigator.appVersion.indexOf('MSIE 8.') == -1) {
				$(document).foundation({
					abide: {
						live_validate : false,
						focus_on_invalid : false,
						validate_on_blur : false,
						validators: {
							validatePostalCode: function (el, required, parent) {
								var isValid = $.isValidPostalCode( { 'shipping_postalCode' : $(el).val()});
								if($(el).val() != '' && !isValid) {
									$('#shippingOptionsForm').find('.jqs-postal-code-error').html('Invalid postal code.');
								}
								return isValid;
							},
							validateAndApplyCouponCode: function(el, required, parent) {
								if(required && $(el).val() == '') {
									return false;
								}
								$("#applyCouponForm").spinner(true, false, 75);
								return $.applyCouponCode($(el).val(), $(el).data('page-type'));
							}
						}
					}
				});
			}
		},

		bindPostalCodeFocusEvent: function() {
			$('input[name="postalCode"]').off().on('focus', function() {
				$(this).parent().removeClass('error');
				$.toggleAbideValidation('#calculate-shipping', true);
			});
		},

		bindCouponCodeFocusEvent: function() {
			$('input[name="couponCode"]').off().on('focus', function() {
				$(this).parent().removeClass('error');
				$.toggleAbideValidation('#apply-coupon', true);
			});
		},

		bindCalculateShippingEvent: function() {
			$('#calculate-shipping').find('.jqs-submit').off().on('click', function () {
				$('#jqs-shipping-options').html('');
			});

			shippingOptionsFormSubmit = function() {
				$.calculateShipping();
			}
		},

		bindApplyCouponEvent: function() {
			$('#apply-coupon').find('.jqs-submit').off().on('click', function () {
				$.toggleAbideValidation('#apply-coupon');
				$('#apply-coupon').find('form[name="applyCouponForm"]').submit();
			});

			$('#apply-coupon').find('form[name="applyCouponForm"]')
				.on('submit', function () {

				})
				.on('invalid.fndtn.abide', function () {

				})
				.on('valid.fndtn.abide', function () {

				});
		},

		bindVolatileEvents: function() {
			$.bindRemoveItemClickEvent();
			$.bindItemQuantityChangeEvent();
			$.bindItemCustomQuantityChangeEvent();
			$.bindItemProductionTimeChangeEvent();
			$.bindCustomAbideValidationEvent();
			$.bindPostalCodeFocusEvent();
			$.bindCouponCodeFocusEvent();
			$.bindApplyCouponEvent();
			$.bindCalculateShippingEvent();
			$.bindShippingMethodClickEvent();
			$.bindReEditOfDesigns();
			initBigNameFormValidation();
		},

		bindAllEvents: function() {
			$.bindContinueShoppingButtonClickEvent();
			$.bindVolatileEvents();
		}
	});
})(jQuery);

$(function() {
	$.bindAllEvents();
	$.checkSubtotal();
});