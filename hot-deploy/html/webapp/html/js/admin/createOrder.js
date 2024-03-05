$(function() {
	//global config for ajax
	$.ajaxSetup({
		dataType : 'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8"
	});

	//$('button.additem').on('click', function(e) {
	//	e.preventDefault();
	//	var container = $(this).parent().parent().siblings('.row:eq(0)');
	//	$(this).parent().parent().before(container.clone(true));
	//});

	$('#billAsShip').on('click', function(e) {
		if($(this).is(":checked")) {
			$('#createOrder').find('input[name^=shipping]').each(function() {
				$('input[name=' + $(this).attr('name').replace('shipping', 'billing') + ']').val($(this).val());
			});
			$('#createOrder').find('select[name^=shipping]').each(function() {
				$('select[name=' + $(this).attr('name').replace('shipping', 'billing') + ']').val($(this).val());
			});
		}
	});

	$('button.calcPrice').on('click', function(e) {
		e.preventDefault();
		var self = this;

		var attr = {};
		$.each($(self).closest('.row').find('select, textarea, input'), function(i) {
			attr[$(this).attr('name')] = $(this).val();
		});

		attr.whiteInkFront = false;
		attr.whiteInkBack = false;
		attr.isRush = false;
		attr.cuts = 0;
		attr.isFolded = false;
		attr.isFullBleed = false;
		attr.addresses = 0;

		$.ajax({
			type: "GET",
			url: 'getProductPrice',
			data: attr,
			cache: true
		}).done(function(data) {
			if(data.success) {
				$(self).closest('div').find('input[name=price]').val(data.priceList[attr.quantity].price);
			}
		}).fail(function(data) {
			console.log(data);
		});
	});

	$('button.addToCart').on('click', function(e) {
		e.preventDefault();
		var self = this;

		var attr = {};
		$.each($(self).closest('.row').find('select, textarea, input'), function(i) {
			attr[$(this).attr('name')] = $(this).val();
		});

		attr.add_product_id = attr.id;
		attr.isProduct = true;
		delete attr.id;

		if(attr.colorsFront != '0' || attr.colorsBack != '0') {
			attr.whiteInkFront = false;
			attr.whiteInkBack = false;
			attr.isRush = false;
			attr.cuts = 0;
			attr.isFolded = false;
			attr.isFullBleed = false;
			attr.addresses = 0;
			attr.artworkSource = 'ART_UPLOADED_LATER';
		}

		$.ajax({
			type: "POST",
			url: 'addToCart',
			data: attr,
			cache: false
		}).done(function(data) {
			console.log(data);
			getCart();
		}).fail(function(data) {
			console.log(data);
		});
	});

	$('button.calcShip').on('click', function(e) {
		e.preventDefault();
		var zip = $('input[name=shipping_postalCode]').val();

		if(zip != '') {
			getShippingRates(zip);
		} else {
			alert('Silly rabbit, the shipping postal code is empty!');
		}
	});

	var getCart = function() {
		$.ajax({
			type: "GET",
			url: 'getCart',
			cache: false
		}).done(function(data) {
			console.log(data);
			if(typeof data !== 'undefined' && typeof data.lineItems !== 'undefined') {
				var tBody = $('.productList').find('tbody');
				tBody.empty();
				$.each(data.lineItems, function(i) {
					tBody.append(
						$('<tr/>').append(
							$('<td/>').html(data.lineItems[i].productId)
						).append(
							(typeof data.lineItems[i].itemDescription != 'undefined' && data.lineItems[i].itemDescription != null) ?  $('<td/>').html(data.lineItems[i].itemDescription) : $('<td/>').html(data.lineItems[i].productName)
						).append(
							$('<td/>').html(data.lineItems[i].productColor)
						).append(
							$('<td/>').html(data.lineItems[i].quantity)
						).append(
							$('<td/>').html(data.lineItems[i].totalPrice)
						).append(
							$('<td/>').append($('<button/>').attr('type', 'button').addClass('btn btn-danger btn-xs').append($('<i/>').addClass('wb-close')).on('click', function(index) {
								$.ajax({
									type: 'POST',
									url: 'removeFromCart',
									data: { index: i },
									cache: false
								}).done(function(data) {
									if(data.success) {
										getCart();
									}
								});
							}))
						)
					);
				});
			}
		}).fail(function(data) {
			console.log(data);
		});
	};

	var getShippingRates = function(zip) {
		$.ajax({
			type: 'POST',
			url: 'calcShippingRates',
			data: { shipping_postalCode: zip },
			cache: false
		}).done(function(data) {
			console.log(data);
			if(data.success && typeof data.estimates !== 'undefined') {
				$('.shippingOptions').empty();
				$.each(data.estimates, function(i) {
					$('.shippingOptions').append($('<div/>').addClass('radio').append($('<label/>').append('<input type="radio" name="shippingMethod" value="' + data.estimates[i].method + '" />' + data.estimates[i].genericDesc + ' $' + data.estimates[i].cost)));
				});
			}
		});
	};

    var defaults = Plugin.getDefaults('wizard');

	var options = $.extend(true, {}, defaults, {
        buttonsAppendTo: '.panel-body',
    	onFinish: function (event, currentIndex) {
			$('#createOrder').submit();
		}
    });

    $('#orderWizard').wizard(options).data('wizard');
});