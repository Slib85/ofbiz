function getCartData(addToCartLocalData) {
	$.ajax({
		type: 'GET',
		url: '/' + websiteId + '/control/getCartData',
		cache: false,
		async: false
	}).done(function(data) {
		$('.jqs-requiredCartContent').removeClass('hidden');
		$('.jqs-cartContent').html($(data).filter('.jqs-cartContentData').html());

		if($(data).filter('.jqs-cartContentData').attr('bns-hasquote') == 'false' && $(data).filter('.jqs-cartContentData').attr('bns-hasprinted') == 'true') {
			$('[bns-checkoutbutton]').addClass('hidden');
			$('.creditCardRelax').removeClass('hidden');
			$('.preOrderPromiseBlock').removeClass('hidden');
		} else {
			$('[bns-checkoutbutton]').removeClass('hidden');
			$('.creditCardRelax').addClass('hidden');
			$('.preOrderPromiseBlock').addClass('hidden');
		}
		if ($(data).filter('.jqs-cartContentData').find('.jqs-productRow').length > 0) {
			$('.jqs-orderPrices').html($(data).filter('.jqs-orderPricesData').html());
			checkSubtotal();

			if(addToCartLocalData) {
        		var newSubTotal = myCartSubTotal;
            	addToCartLocalData.subTotal = newSubTotal;
            	// localStorage.addToCartData = JSON.stringify(addToCartLocalData);
            	$().updateMiniCart(JSON.stringify(addToCartLocalData));
        	}

			/* Quantity Update */
			$('.jqs-quantityUpdate').on('change', function() {
				$.ajax({
					type: 'POST',
					url: '/folders/control/updateCartItem',
					timeout: 5000,
					async: false,
					data: {
						index: $(this).closest('.jqs-productRow').attr('data-index'),
						quantity: $(this).val()
						//isCustomQuantity: true or false
						//productionTime: $(this).closest('.jqs-productRow').attr('data-productionTime')
					},
					cache: false
				});

				if (localStorageEnabled) {
					var addToCartData = JSON.parse(localStorage.addToCartData);
					addToCartData.subTotal = myCartSubTotal;
					getCartData(addToCartData);
				} else {
					getCartData();
				}
				checkSubtotal();
			});

			$('.jqs-removeProduct').on('click', function(e) {
				var index = $(this).parent().data('index');
				var sku = $(this).attr('sku');
				$.ajax({
					type: 'POST',
					url: '/' + websiteId + '/control/removeFromCart',
					data: {
						index: $(this).parent().attr('data-index')
					},
					dataType: 'json',
					cache: false
				}).done(function(data) {
					if(data.success) {
						if(localStorageEnabled) {
							var addToCartData = JSON.parse(localStorage.addToCartData);
							addToCartData.cartItems.splice(index, 1);
							getCartData(addToCartData);
						} else {
							getCartData();
						}
						if(typeof sku != 'undefined' && sku != '') {
							dataLayer.push({
								'removedSku': sku,
								'event': 'removeitem'
							});
						}
					} else {
						alert('Error Removing Your Item');
					}					
					checkSubtotal();
				});
			});

            $('[data-projectId]').on('click', function(e) {
                window.location.href = $(this).attr('data-editurl');
            });
		}
		else {
			$('.jqs-requiredCartContent').addClass('hidden');
			if(addToCartLocalData) {
        		addToCartLocalData.subTotal = 0;
	        	addToCartLocalData.grandTotal = 0;
	        	localStorage.addToCartData = JSON.stringify(addToCartLocalData);
	        	$().updateMiniCart();
        	}
		}
	});
}

$('.jqs-submitCouponCode').off().on('click', function () {
	$('.jqs-couponErrorNotification').remove();

	var couponCode = $(this).siblings('input').val();

	if(typeof couponCode !== 'undefined' && couponCode != '') {
		$.ajax({
			type: 'GET',
			url: '/' + websiteId + '/control/applyPromo',
			data: { productPromoCodeId : couponCode },
			dataType: 'json',
			cache: false
		}).done(function(data) {
			if (data.success) {
				if (typeof data.error != 'undefined' && data.error != null) {
					$('.jqs-cartContent').before(
						$('<div />').addClass('fbc-orange padding20 jqs-couponErrorNotification').html(data.error)
					)
				} else if (typeof data.error != 'undefined' && data.error == null) {
					$('.jqs-cartContent').before(
						$('<div />').addClass('fbc-green padding20 jqs-couponErrorNotification').html('The promotion code [' + couponCode + '] has been applied successfully!')
					)
				}

                getCartData();
			}
		});
	}
});

shippingOptionsFormSubmit = function() {
	var zipCode = $('#shippingOptionsForm input[name="shipping_postalCode"]').val();
	var geoId = $('#shippingOptionsForm select[name="shipping_countryGeoId"]').val();
	var shipTo = $('#shippingOptionsForm input[name="ship_to"]:checked').val();
	if(typeof zipCode !== 'undefined' && zipCode != '') {
		$.ajax({
			type: 'GET',
			url: '/' + websiteId + '/control/cart-shipping-options',
			data: { 
				'shipping_postalCode': zipCode,
				'shipping_countryGeoId': geoId,
				'ship_to': shipTo
			},
			dataType: 'html',
			cache: false
		}).done(function(data) {
			$('#jqs-shipping-options').html(data);

			$('.jqs-shipping-method').on('click', function() {
				var self = this;
				$.ajax({
					type: 'POST',
					url: '/' + websiteId + '/control/setShipping',
					data: {
						shippingTotal: $(self).attr('data-cost'),
						shipmentMethodTypeId: $(self).val(),
						carrierPartyId: $(self).attr('data-party-id')
					},
					cache: false
				}).done(function(data) {
					getCartData();
				});
			});
		});
	}
}

function checkSubtotal() {
	if($('.orderPrices > div .subtotal').length > 0) {
		var subtotal = parseFloat($('.orderPrices > div .subtotal').html().replace('$', ''));
		$('.jqs-fship_warning').remove();

		var freeShipAmount = (typeof cartFreeShipAmount != 'undefined' ? cartFreeShipAmount : 299);
		var fshipApplied = false;

		$('.price-discounts').each(function() {
			if ($(this).html() == 'Free Standard Shipping:') {
				fshipApplied = true;
			}
		});

		if (subtotal >= (freeShipAmount / 2) && !fshipApplied) {
			$('<div class="jqs-fship_warning" style="width: 100%; background-color: #063b63; font-size: 20px; cursor: pointer; color: rgb(255, 255, 255); text-align: center; padding: 10px; margin:15px 0 20px 0;" data-bnreveal="fship">' + (subtotal >= freeShipAmount ? 'Your order now qualifies for Free Shipping. <strong>Use code FREE' + freeShipAmount + '</strong>' : 'Spend An Additional <strong>$' + (freeShipAmount - subtotal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + '</strong> For <strong>FREE</strong> Shipping! <strong>Code: FREE' + freeShipAmount + '</strong>*') + '</div>').insertBefore($('#foldersMainCartContent'));
		}
/*
		if (subtotal >= 70) {
			$('<div class="jqs-fship_warning" style="width: 100%; background-color: #000000; font-size: 20px; cursor: pointer; color: rgb(255, 255, 255); text-align: center; padding: 10px; margin-top: 15px;" data-reveal-id="fship-promo">' + (subtotal >= 125 ? 'Your order now qualifies for Free Shipping. <strong>Use code FREE125</strong>' : '<span style="text-decoration: underline;">TODAY ONLY</span>: Spend An Additional <strong>$' + (125 - subtotal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + '</strong> For <strong>FREE</strong> Shipping! <strong style="color: #fe02a2;">Code: FREE125</strong>*') + '</div>').insertBefore($('#jqs-order-summary'));
		}
*/
	}
}

$('[bns-closePreOrderPromise]').on('click', function() {
	$('.preOrderPromiseBlock').addClass('hidden');
})

$(document).ready(function() {
	getCartData();
});
