(function($) {
	var updateItem = function(data, pageType) {
		var productIndex = data.index;
		var newQuantity = data.quantity;
		$.ajax({
			type: 'POST',
			url: '/' + websiteId + '/control/updateCartItem',
			data: data,
			cache: false
		}).done(function(data) {
			if(data.success) {
				if(pageType == 'cross-sell') {
					//relocate to cart once all products have been added
					window.location = '/' + websiteId + '/control/cart';
				} else {
					if (typeof localStorage.addToCartData != 'undefined') {
						var addToCartData = JSON.parse(localStorage.addToCartData);
						if (typeof addToCartData.cartItems[productIndex] != 'undefined' && typeof data.priceList[newQuantity] != 'undefined') {
							addToCartData.subTotal = myCartSubTotal;
							addToCartData.cartItems[productIndex].quantity = newQuantity;
							addToCartData.cartItems[productIndex].unitPrice = Math.round(data.priceList[newQuantity].price / newQuantity * 100) / 100;
							addToCartData.cartItems[productIndex].totalPrice = data.priceList[newQuantity].price;
							$.refreshOrderSummary(pageType, addToCartData);
						}
					}
				}
			} else {
				alert('failed');
				//TODO - fail over option;
			}
		});
	};

	var geoRegex = {
		canada      : /^[A-Z]\d[A-Z]( )?\d[A-Z]\d$/i,
		usa         : /^\d{5}(-\d{4})?$/
	};

	$.ajaxSetup({
		dataType : 'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true
	});

	$.extend({
        toggleAbideValidation: function(scope, disableFlag) {
            $('[data-bignamevalidate]', scope).each(function() {
                var required, type, pattern, validator;

                if(disableFlag && (typeof $(this).attr('data-bignamevalidateactive') == 'undefined' || $(this).attr('data-bignamevalidateactive') == 'true')) {
                    $(this).attr('data-bignamevalidateactive', 'false');
                } else if (!disableFlag) {
                    $(this).attr('data-bignamevalidateactive', 'true');
                }
            });
        },
		bindApplyCouponButtonClickEvent: function() {
			$('#jqs-apply-coupon').on('click', function() {
                var couponCodeElement = $('input[name=couponCode]');
                $.applyCouponCode($(couponCodeElement).val(), $(couponCodeElement).data('page-type'));
			});
		},

		bindShippingMethodClickEvent: function() {
			$('.jqs-shipping-method').off('click').on('click', function(e) {
				$.setShippingMethod($(this).val(), $(this).data('party-id'), $(this).data('cost'), $(this).data('page-type'));
			});
		},

		isCanadianPostalCode: function(postalCode) {
			return new RegExp(geoRegex.canada).test(postalCode)
		},

		isUSPostalCode: function(postalCode) {
			return new RegExp(geoRegex.usa).test(postalCode)
		},

		isValidPostalCode: function(address) {
			return typeof address != 'undefined' && typeof address.shipping_postalCode != 'undefined' && address.shipping_postalCode != '' && typeof address.shipping_countryGeoId != 'undefined' && address.shipping_countryGeoId != '';
		},

        getCCTypeDetailed: function(ccNumber, validateMode) {
			/*$('.ccValMessage').hide();*/
            var strippedCCNum = ccNumber.replace(/\D/g,'');

            var result = typeof validateMode == 'undefined' ? "CCT_VISA" : "";

            //check for Visa detailed
            if (/^4[0-9]{12}(?:[0-9]{3})?$/.test(strippedCCNum)) {
                result = "CCT_VISA";
            } else if (/^5[1-5][0-9]{14}/.test(strippedCCNum)) {
                result = "CCT_MASTERCARD";
            } else if (/^3[47][0-9]{13}$/.test(strippedCCNum)) {
                result = "CCT_AMERICANEXPRESS";
            } else if (/^6(?:011|5[0-9]{2})[0-9]{12}$/.test(strippedCCNum))  {
                result = "CCT_DISCOVER";
            } /*else {
				$('.ccValMessage').show();
			}*/

            return result;
        },

		refreshOrderSummary: function(pageType, addToCartLocalData) {
			$.ajax({
				type: 'GET',
				url: '/' + websiteId + '/control/' + pageType + '-items',
				dataType: 'html',
				cache: false,
				async: false
			}).done(function(data) {
				if($(data).find('.jqs-continue-shopping').length > 0 ) {
                	if(addToCartLocalData) {
	                	addToCartLocalData.subTotal = 0;
	                	addToCartLocalData.grandTotal = 0;
	                	localStorage.addToCartData = JSON.stringify(addToCartLocalData);
	                	$().updateMiniCart();
	                }
                    location.reload();
                } else {
                    $('#jqs-order-summary').html(data);

                    if(pageType == 'cart' || pageType == 'checkout') {
                    	if(addToCartLocalData) {
                    		var newSubTotal = myCartSubTotal;
	                    	addToCartLocalData.subTotal = newSubTotal;
	                    	localStorage.addToCartData = JSON.stringify(addToCartLocalData);

							if (typeof GTMCartUpdate == "function") {
								GTMCartUpdate();
							}
                    	}
                        $().updateMiniCart();
                        $.bindVolatileEvents();

                        //PAYPAL CHECKOUT SPECIFIC CODE
                        if(pageType == 'checkout') {
                            validatePayPalAmount();
						}
                    }

                    $('#jqs-order-summary .slideIt').each(function() {
						slideIt_init($(this));
                    });

					$.checkSubtotal();
                }
			});
		},

		checkSubtotal: function() {
			if($('.subtotals > div:first-child .price-color').length > 0) {
				var subtotal = parseFloat($('.subtotals > div:first-child .price-color').html().replace('$', ''));
				$('.jqs-fship_warning').remove();

				var freeShipAmount = (typeof cartFreeShipAmount != 'undefined' ? cartFreeShipAmount : 250);
				var fshipApplied = false;

				$('.price-discounts').each(function() {
					if ($($(this).siblings()[0]).html() == 'FREE Standard Shipping:') {
						fshipApplied = true;
					}
				});

				if (subtotal >= (freeShipAmount / 2) && !fshipApplied) {
					$('<div class="jqs-fship_warning" style="width: 100%; background-color: #00A1E0; font-size: 20px; cursor: pointer; color: rgb(255, 255, 255); text-align: center; padding: 10px; margin-top: 15px;" data-reveal-id="fship-promo">' + (subtotal >= freeShipAmount ? 'Your order now qualifies for Free Shipping. <strong>Use code FREE' + freeShipAmount + '</strong>' : 'Spend An Additional <strong>$' + (freeShipAmount - subtotal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + '</strong> For <strong>FREE</strong> Shipping! <strong>Code: FREE' + freeShipAmount + '</strong>*') + '</div>').insertBefore($('#jqs-order-summary'));
				}
			}
		},

		getShippingRates: function(address, pageType) {
			if($.isValidPostalCode(address)) {
				$.ajax({
					type: 'GET',
					url: '/' + websiteId + '/control/' + pageType + '-shipping-options',
					data: address,
					dataType: 'html',
					cache: false
				}).done(function(data) {
					$.refreshOrderSummary(pageType);
					$("#shippingOptionsForm").spinner(false);
					$('#jqs-shipping-options').html(data);
					$.bindShippingMethodClickEvent();

					if(typeof pageType !== 'undefined' && pageType == 'cart') {
						GoogleAnalytics.trackEvent('Cart', 'Calculate Shipping', 'Success');
					} else if(typeof pageType !== 'undefined' && pageType == 'checkout') {
						GoogleAnalytics.trackEvent('Checkout', 'Calculate Shipping', 'Success');
					}
					/*
					While calculating the shipping cost on the cart page, the auto shipping promotion won't calculate the shipping discount
					due to the limitation in ofbiz promotions implementation. I don't like this, but this is the quickest and safest hack
					to make auto shipping discount work without any hardcoding. DO NOT REMOVE the below duplicate ajax call
					*/
					$.ajax({
						type: 'GET',
						url: '/' + websiteId + '/control/' + pageType + '-shipping-options',
						data: address,
						dataType: 'html',
						cache: false
					}).done(function(data) {
						$.refreshOrderSummary(pageType);
						$("#shippingOptionsForm").spinner(false);
						$('#jqs-shipping-options').html(data);
						$.bindShippingMethodClickEvent();
					}).fail(function (jqXHR) {
						window.location.reload();
					});
				}).fail(function(jqXHR) {
					GoogleAnalytics.trackEvent('Cart', 'Calculate Shipping', 'Fail');
				});
			}
		},

		applyCouponCode: function(couponCode, pageType) {
			if(couponCode!== undefined) {
				$.ajax({
					type: 'GET',
					url: '/' + websiteId + '/control/applyPromoAjax',
					data: {
						productPromoCodeId: couponCode.toUpperCase()
					},
                    async: false,
					cache: false
				}).done(function(data) {
					waitForFinalEvent(function() {
						$('.coupon-response').addClass('hidden').html('');
					}, 10000, "coupon_application_reset");

                    $("#applyCouponForm").spinner(false);
                    $('.coupon-response').removeClass('success').removeClass('alert').removeClass('hidden');
					if(!data.error) {
						$('.coupon-response').addClass('success').html('Coupon Code has been applied successfully!');
						$.refreshOrderSummary(pageType);
                        return true;
					} else {
						$('.coupon-response').addClass('alert').html(data.error);
						return false;
					}
				});

				if(typeof pageType !== 'undefined' && pageType == 'cart') {
					GoogleAnalytics.trackEvent('Cart', 'Coupon', couponCode.toUpperCase());
				} else if(typeof pageType !== 'undefined' && pageType == 'checkout') {
					GoogleAnalytics.trackEvent('Checkout', 'Coupon', couponCode.toUpperCase());
				}
			}
		},

		setShippingMethod: function(shippingMethod, partyId, cost, pageType) {
			$.ajax({
				type: 'POST',
				url: '/' + websiteId + '/control/setShipping',
				data: {
					shippingTotal: cost,
					shipmentMethodTypeId: shippingMethod,
					carrierPartyId: partyId
				},
				cache: false
			}).done(function(data) {
				if(data.success) {
					$.refreshOrderSummary(pageType);
				} else {
					alert('failed');
				   //TODO - fail over option;
				}
			});
		},

		updateItemProductionTime: function(index, quantity, productionTime) {
			var data = {};
			data['index'] = index;
			data['quantity'] = quantity;
			data['productionTime'] = productionTime;
			updateItem(data, pageType);
		},

		validateQuantity: function(index, customQuantity, increments) {
			if(customQuantity > 0 && customQuantity%increments == 0 && customQuantity <= 500000) {
				return "valid";
			} else if(customQuantity > 0 && customQuantity < increments){
				return 'You must enter a quantity higher than ' + increments + '.'
			} else if(customQuantity > 500000) {
				return 'Please call customer service to get a quote.'
			} else {
				return 'Your quantity must be in increments of ' + increments + '.'
			}
		},

		updateItemQuantity: function(index, quantity, pageType, isCustomQuantity) {
			var data = {};
			data['index'] = index;
			data['quantity'] = quantity;
			data['isCustomQuantity'] = isCustomQuantity? isCustomQuantity : false;
			updateItem(data, pageType);
		},

		removeItem: function(index, sku) {
			$.ajax({
				type: 'POST',
				url: '/' + websiteId + '/control/removeFromCart',
				data: {
					index: index
				},
				cache: false
			}).done(function(data) {
				if(data.success) {
					if(localStorageEnabled) {
						var addToCartData = JSON.parse(localStorage.addToCartData);

						dataLayer.push({
						    "removedSku": addToCartData.cartItems[index].productId,
						    "addedQty": addToCartData.cartItems[index].quantity,
						    "basePrice": addToCartData.cartItems[index].unitPrice,
						    "productName": addToCartData.cartItems[index].itemDescription,
                            "event": "removeitem"
						});

						addToCartData.cartItems.splice(index, 1);

						$.refreshOrderSummary(pageType, addToCartData);
					} else {
						$.refreshOrderSummary(pageType);

					    if(typeof sku != 'undefined' && sku != '') {
                            dataLayer.push({
                                'removedSku': sku,
                                'event': 'removeitem'
                            });
                        }
					}
				} else {
					alert('failed');
					//TODO - fail over option;
				}
				$(".line-items").spinner(false);
			});
		}
	});

})(jQuery);

/* JANRAIN */
(function() {
	if (typeof window.janrain !== 'object') window.janrain = {};
	if (typeof window.janrain.settings !== 'object') window.janrain.settings = {};

	if(websiteId == "envelopes") {
		janrain.settings.tokenUrl = 'https://www.envelopes.com/envelopes/control/getJanRainToken';
	} else {
		janrain.settings.tokenUrl = 'https://www.actionenvelope.com/ae/control/getJanRainToken';
	}
	janrain.settings.tokenAction = 'event';

	function isReady() { janrain.ready = true; };
	if (document.addEventListener) {
		document.addEventListener("DOMContentLoaded", isReady, false);
	} else {
		window.attachEvent('onload', isReady);
	}

	var e = document.createElement('script');
	e.type = 'text/javascript';
	e.id = 'janrainAuthWidget';

	if (document.location.protocol === 'https:') {
		e.src = 'https://rpxnow.com/js/lib/envelopes/engage.js';
	} else {
		e.src = 'http://widget-cdn.rpxnow.com/js/lib/envelopes/engage.js';
	}

	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(e, s);
})();

function janrainWidgetOnload() {
	janrain.events.onProviderLoginToken.addHandler(function(response) {
		if(typeof response.token != 'undefined') {
			$('#coToken').val(response.token);
			$('#loginForm').submit();
		}
	});
}