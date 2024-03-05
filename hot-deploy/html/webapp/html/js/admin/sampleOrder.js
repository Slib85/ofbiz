$(function() {
    $('select[name="printingMethod"]').multiselect({});
    $('select[name="nonMetallicFoils"]').multiselect({});
    $('select[name="metallicFoils"]').multiselect({});
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
		$.each($(self).closest('.wizard-pane').find('select, textarea, input'), function(i) {
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
		$.each($(self).closest('.wizard-pane').find('select, textarea, input'), function(i) {
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

    $('.jqs-productCategoryIdList').on('change', function() {
    	$.ajax({
			url: '/admin/control/getFolderStyleList',
			data: {
				categoryId: $(this).val()
			}
		}).done(function(response) {
			if (response.success){
                var productStyleElement = $('select[name="id"]');
				productStyleElement.children().remove();
                productStyleElement.append(
                	$('<option />').val('').html('Select a Folder Style')
				)

				for (var i = 0; i < response.data.length; i++) {
					productStyleElement.append(
						$('<option />').val('SAMPLE').attr('data-styleId', response.data[i].productId).html(response.data[i].productId + (typeof response.data[i].productName != 'undefined' ? ' (' + response.data[i].productName + ')' : ''))
					)
				}

				$('.jqs-productIdList').trigger('change');
            }
            else {
                alert('There was an issue getting style data.');
            }
        });
	});

    $('.jqs-productIdList').on('change', function() {
        var materialElement = $('select[name="material"]');
        var materialElementVal = materialElement.val();
        materialElement.prop('disabled', true).children('option').remove();
        materialElement.append(
            $('<option />').attr('value', '').html('Select a Material')
        );

        if ($(this).find('option:selected').attr('data-styleId') != '') {
            $.ajax({
                url: '/admin/control/getProductMaterials',
                data: {
                    productId: $(this).find('option:selected').attr('data-styleId')
                }
            }).done(function(response) {
                if (response.success) {
                    for (var i = 0; i < response.data.basicAndPremium.length; i++) {
                        materialElement.append(
                            $('<option />').attr('value', response.data.basicAndPremium[i].materialId).prop('selected', (response.data.basicAndPremium[i].materialId == materialElementVal ? true : false)).html(response.data.basicAndPremium[i].description)
                        );
                    }

                    for (var i = 0; i < response.data.nonBasicAndPremium.length; i++) {
                        materialElement.append(
                            $('<option />').attr('value', response.data.nonBasicAndPremium[i].materialId).prop('selected', (response.data.nonBasicAndPremium[i].materialId == materialElementVal ? true : false)).html(response.data.nonBasicAndPremium[i].description)
                        );
                    }

                    if (materialElement.find('option').length > 1) {
                    	materialElement.prop('disabled', false);
                    }
                }
                else {
                    alert('There was an issue getting material data.');
                }
            });
        }
    });

    $('.jqs-listOption').on('change input', function() {
        var prodDescription = '';

        if($('select[name="id"]').val() != '') {
            prodDescription += ((prodDescription == '') ? '' : ', ') + $('select[name="id"] option:selected').text();
        }
        if($('select[name="material"]').val() != '') {
            prodDescription += ((prodDescription == '') ? '' : ', ') + $('select[name="material"] option:selected').text();
        }
        if($('input[name="customFoilColorNumber"]').val() != '') {
            prodDescription += ((prodDescription == '') ? '' : ', ') + 'Custom Foil Color # - ' + $('input[name="customFoilColorNumber"]').val();
        }
        if($('select[name="offsetColorPrinting"]').val() != '') {
            prodDescription += ((prodDescription == '') ? '' : ', ') + $('select[name="offsetColorPrinting"] option:selected').text();
        }
        if($('select[name="offsetPrintingCoatings"]').val() != '') {
            prodDescription += ((prodDescription == '') ? '' : ', ') + $('select[name="offsetPrintingCoatings"] option:selected').text();
        }
		if($('select[name="printingMethod"]').val() != null && $('select[name="printingMethod"]').val().length > 0) {
            for (var i = 0; i < $('select[name="printingMethod"]').val().length; i++) {
                prodDescription += ((prodDescription == '') ? '' : ', ') + 'Printing Method - ' + $('select[name="printingMethod"]').val()[i];
            }
        }
        if($('select[name="metallicFoils"]').val() != null && $('select[name="metallicFoils"]').val().length > 0) {
            for (var i = 0; i < $('select[name="metallicFoils"]').val().length; i++) {
                prodDescription += ((prodDescription == '') ? '' : ', ') + 'Metallic - ' + $('select[name="metallicFoils"]').val()[i];
            }
        }
        if($('select[name="nonMetallicFoils"]').val() != null && $('select[name="nonMetallicFoils"]').val().length > 0) {
        	for (var i = 0; i < $('select[name="nonMetallicFoils"]').val().length; i++) {
                prodDescription += ((prodDescription == '') ? '' : ', ') + 'Non Metallic - ' + $('select[name="nonMetallicFoils"]').val()[i];
			}
        }

        $('input[name="name"]').val(prodDescription);
    });
});