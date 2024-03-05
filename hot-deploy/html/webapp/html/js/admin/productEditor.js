jQuery(document).ready(function($) {

    var offsetTop = 0;

    if ($('.site-navbar').length > 0) {
        offsetTop = $('.site-navbar').eq(0).innerHeight();
    }

	$("#productTable").DataTable({
        iDisplayLength: 50,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
		searching: false
	});

	$('#addSKU .j-btn-add').on('click', function(e) {
		var self = this;
		var modal = $(this).closest('.modal');
		var productId = modal.find('#productId').val();

		//ajax call
		$.ajax({
			type: 'POST',
			url: '/admin/control/addProduct',
			dataType: 'json',
			data: { 'productId' : productId }
		}).done(function( response ) {
			if(response.success) {
				window.location = '/admin/control/productEditor?id=' + productId;
			} else {
				if(typeof response.error != 'undefined') {
					alert(response.error);
				} else {
					alert("There was an error trying to add a new product.");
				}
			}
		}).error(function( response ) {
			//todo error
		});
	});

	$('[id^=addpf_] .j-btn-add').on('click', function(e){
		var modal = $(this).closest('.modal');
		var feature = modal.find('[data-fId]');

		if(feature.val().replace(/\s/g,"") != '') {
			//ajax call
			$.ajax({
				type: 'POST',
				url: '/admin/control/createProductFeature',
				dataType: 'json',
				data: {
					'productFeatureTypeId': feature.attr('data-fId'),
					'description': feature.val()
				}
			}).done(function( response ) {
				if(response.success){
					location.reload();
				} else {
					//todo error
				}
			}).error(function( response ) {
				//todo error
			});
		} else {
			modal.modal('hide');
		}
	});

	$('#addQuantity .j-btn-add').on('click', function(e) {
		var self = this;
		var modal = $(this).closest('.modal');
		var quantity = modal.find('#quantity').val();
		var colors = modal.find('#colors').val();
		var price = modal.find('#price').val();

		//ajax call
		$.ajax({
			type: 'POST',
			url: '/admin/control/addQuantity',
			dataType: 'json',
			data: {
				"productId": $('#productId').val().toUpperCase(),
				"quantity": quantity,
				"colors": colors,
				"price": price
			}
		}).done(function( response ) {
			if(response.success) {
				window.location = '/admin/control/productEditor?id=' + $('#productId').val();
			} else {
				if(typeof response.error != 'undefined') {
					alert(response.error);
				} else {
					alert("There was an error trying to add a new quantity.");
				}
			}
		}).error(function( response ) {
			// todo error
		});
	});

	function setTotalAssets() {
		$('[name="totalAssetCount"]').val($('.assets .asset-id').length);
	}

	// Add asset events based on the DOM element and several classes.
	// Allows this function to be used for different groups (Product / Design etc.)
	function addAssetEvents(element, removeClass, moveClass, rowClass) {
		element.find('.' + removeClass).on('click', function() {
			assetId = $(this).parents('.' + rowClass).find('.asset-id').val();
			$(this).parents('.' + rowClass).remove();
			if (typeof assetId !== 'undefined') {

				var toType = function(obj) {
				  return ({}).toString.call(obj).match(/\s([a-zA-Z]+)/)[1].toLowerCase()
				}
				removedAttr = $('input[name="removedAssets"]');
				removedSoFar = removedAttr.val().split(',');
				if($.inArray(assetId, removedSoFar) === -1) {
					$(removedAttr).val(($(removedAttr).val() != '' ? $(removedAttr).val() + ',' : '') + assetId);
				}
			}
			$(this).parents('.' + rowClass).remove();
			updateAssetSort();
			setTotalAssets();
		});

		element.find('.' + moveClass).on('mousedown', function(e) {
			if ((e.which || e.button) == 1) {
				e.preventDefault();
				movingElement = $(this).parent();

				movingElement.css({
					'border': '1px solid #00a4e4',
					'background-color': '#99ff99',
					'z-index': '2'
				});
			}
		});
	}

	function updateAssetSort() {
		/*
		var counter = 0;
		var totalAssetIds = $('.assets .asset-id').length;
		$('.assets .asset-id').each(function() {
			//for (var i = 0; i < totalAssetIds; i++){
				$(this).parents('.product-asset-row, .design-asset-row').find('*[name^="asset"]').each(function(){
					var currentName = $(this).attr('name');
					var numeric = currentName.replace(/\D/g,'');
					var firstPart = currentName.substr(0, currentName.indexOf(numeric));
					var lastPart = currentName.split(numeric)[1];
					$(this).attr('name', firstPart + counter + lastPart);
				});
			//}
			counter++;
		});*/
	}

	// Clear removed asset value for both groups
	$('input[name="removedAssets"]').val('');


	// Create a new 'Product Asset' Row. Build a String and then convert it to a DOM Element.
	$('.jqs-add_asset').on('click', function() {
		var newIdx = $('.asset-id').length;
		var newElement = `
			<div class="row assetRow jqs-asset_row no-margin">
				<div class="col-md-12">
					<input type="hidden" class="asset-id" name="asset` + newIdx + `-id">
					<label class="control-label text-xs">Asset Type</label>
					<select name="asset` + newIdx + `-type">
						<option value="image" selected>Image</option>
						<option value="video">Video</option>
					</select>
				</div>
				<div class="col-md-12">
					<label class="control-label">Asset Name</label>
					<input class="asset-name" type="text" name="asset` + newIdx + `-name" placeholder="Asset Name">
				</div>
				<div class="col-xs-9">
					<label class="control-label">Asset Thumbnail</label>
					<input type="text" name="asset` + newIdx + `-thumbnail" placeholder="Asset Thumbnail">
				</div>
				<div class="col-xs-3 text-xs-right">
					<label class="control-label">Default?</label>
					<input class="jqs-assetDefault" type="checkbox" name="asset` + newIdx + `-default">
				</div>
				<i class="fa fa-times jqs-remove_asset"></i>
				<i class="fa fa-arrows jqs-moveAsset"></i>
			</div>
		`;
		newElement = $('<div/>').html(newElement).contents();

		$('.jqs-assetList').append(newElement);
		setTotalAssets();
		addAssetEvents(newElement, 'jqs-remove_asset', 'jqs-moveAsset', 'jqs-asset_row');
		updateAssetSort();
	});

	// Create a new 'Design Asset' Row. Build a String and then convert it to a DOM Element.
	$('.jqs-add_design-id').on('click', function() {
		var newIdx = $('.asset-id').length;
		var newElement = `
			<div class="row assetRow jqs-design-id_row no-margin">
				<div class="col-md-12 design-id-row">`
					+ `
					<label class="control-label">Design ID</label>`
					+ `<input type="text" class="design-id-field jqs-design-id-field" placeholder="Design ID">
					<div class="jqs-design-assetList"></div>
				</div>
				<div class="col-md-12"><a class="jqs-add_design-asset add_design-asset">Add Asset to this ID</a></div>
				<i class="fa fa-times jqs-remove_design-id"></i>
				<i class="fa fa-arrows jqs-moveDesignId"></i>
			</div>
		`;
		newElement = $('<div/>').html(newElement).contents();

		$('.jqs-design-idList').append(newElement);
		addAssetEvents(newElement, 'jqs-remove_design-asset', 'jqs-moveDesignId', 'design-asset-row');
	});

	$('.jqs-design-idList').on('click', '.jqs-add_design-asset', function() {
		var newIdx = $('.asset-id').length;
		// alert(newIdx);
		var assocId = $(this).parents('.jqs-design-id_row').find('.jqs-design-id-field').val();
		// alert(assocId);
		var newElement = `
			<div class="row design-asset-row">
			<input type="hidden" class="asset-id" name="asset` + newIdx + `-id">
			<input type="hidden" name="asset` + newIdx + `-type" value="printed">
			<input class="linked-design-id" type="hidden" name="asset` + newIdx + `-designId" value="` + assocId + `">
			<div class="col-md-12">
				<label class="control-label">Asset Name</label>
				<input class="asset-name" type="text" name="asset` + newIdx + `-name" placeholder="Asset Name">
			</div>
			<div class="col-xs-9">
				<label class="control-label">Asset Thumbnail</label>
				<input class="asset-thumb" type="text" name="asset` + newIdx + `-thumbnail" placeholder="Asset Thumbnail">
			</div>
			<div class="col-xs-3 text-xs-right">
				<label class="control-label">Default?</label>
				<input class="jqs-design-assetDefault" type="checkbox" name="asset` + newIdx + `-default">
			</div>`
			+ `
				<i class="fa fa-times jqs-remove_design-asset"></i>
				<i class="fa fa-arrows jqs-moveDesignAsset"></i>
			</div>
		`;
		newElement = $('<div/>').html(newElement).contents();
		$(this).parents('.assetRow').find('.jqs-design-assetList').append(newElement);
		setTotalAssets();
		addAssetEvents(newElement, 'jqs-remove_design-asset', 'jqs-moveDesignAsset', 'design-asset-row');
		updateAssetSort();
	});

	$('.jqs-design-idList').on('click', '.jqs-remove_design-id', function(){
		$(this).parents('.jqs-design-id_row').find('.jqs-remove_design-asset').trigger('click');
		$(this).parents('.jqs-design-id_row').remove();
	});

	$('.jqs-design-idList').on('click', '.jqs-design-assetDefault', function(){
		$(this).parents('.design-id-row').find('.jqs-design-assetDefault').not(this).attr('checked', false);
	});

	// Update child Design IDs if top level is changed
	$('.assetList').on('change keyup input paste', '.jqs-design-id-field', function() {
		var newVal = $(this).val();
		$(this).parents('.design-id-row').find('.linked-design-id').val(newVal);
	});

	// Remove commas from Inputs
	$('.jqs-asset_row input, .jqs-design-asset_row input').on('input', function() {
		$(this).val($(this).val().replace(',', ''));
	});


	// Run addAssetEvent for each groups' rows on load
	$('.jqs-assets .row').each(function() {
		addAssetEvents($(this), 'jqs-remove_asset', 'jqs-moveAsset', 'jqs-asset_row');
	});
	$('.jqs-design-id_row').each(function() {
		addAssetEvents($(this), 'jqs-remove_design-asset', 'jqs-moveDesignId', 'design-asset-row');
	});
	$('.design-asset-row').each(function() {
		addAssetEvents($(this), 'jqs-remove_design-asset', 'jqs-moveDesignAsset', 'design-asset-row');
	});


	// Only allow one asset to be the default
	function setSingleDefault(defaultClass, rowClass) {
		$(".assets").on('change', '.' + defaultClass, function() {
			var activeRow = $(this).closest('.' + rowClass).index();

			if ($(this).is(':checked')) {
				$('.' + defaultClass).each(function() {
					if ($(this).closest('.' + rowClass).index() != activeRow) {
						$(this).prop('checked', false);
					}
				});
			}
		});
	}


	// Run previous function for both groups
	setSingleDefault('jqs-assetDefault', 'jqs-asset_row');
	setSingleDefault('jqs-designAssetDefault', 'jqs-design-asset_row');

	var movingElement = null;

	$(window).on('mouseup', function(e) {
		if ((e.which || e.button) == 1 && movingElement != null) {
			movingElement.attr('style', movingElement.attr('style').replace(/background\-color.*?\;/, ''));
			movingElement.attr('style', movingElement.attr('style').replace(/border.*?\;/, ''));
			movingElement.attr('style', movingElement.attr('style').replace(/z\-index.*?\;/, ''));

			movingElement = null;
		}
	});


	// Handles updating the list when items are dragged and dropped.
	// Uses class names and array of field info for nested updateAssetSort
	function handleListUpdate(moverClass, listClass, rowClass) {

		$(window).on('mousemove', function(e) {
			if (movingElement != null && movingElement.hasClass(moverClass)) {
				// movingElement.css('background', 'brown');
				listToAppend = movingElement.parents('.' + listClass);

				// listToAppend.css('background', 'red');
				var mousePosition = $(window).scrollTop() + e.clientY;
				var movingElementHeight = parseInt(movingElement.outerHeight());
				var mousePositionInAssetList = Math.min(listToAppend.height(), Math.max(0, mousePosition - listToAppend.offset().top));

				if (!(mousePositionInAssetList >= Math.max(0, ((movingElement.index() - 1) * movingElementHeight) + (movingElementHeight / 2)) && mousePositionInAssetList < Math.min($('.' + listClass).height(), ((movingElement.index() + 1) * movingElementHeight) + (movingElementHeight / 2)))) {
					var indexCounter = 0;
					listToAppend.find('.' + rowClass).each(function() {
						if(!$(this).is(movingElement)) {
							$(this).appendTo(listToAppend);
						}
						if (indexCounter == Math.round(mousePositionInAssetList / $(listToAppend.find('.' + rowClass)).outerHeight()) - 1) {
							$(movingElement).appendTo($('.' + listClass));
						}
						indexCounter++;
					});
					updateAssetSort();
				}
			}
		});
	}

	$('[bns-removeproductprice]').off('click.removeProductPrice').on('click.removeProductPrice', function() {
		var element = $(this);
		var productId = element.attr('data-productid');
		var quantity = element.attr('data-quantity');
        var price = element.attr('data-price');
        var colors = element.attr('data-colors');

        if(typeof productId != 'undefined' && typeof quantity != 'undefined' && typeof price != 'undefined' && typeof colors != 'undefined' && !$(this)[0].hasAttribute('bns-ignoreclick')) {
            var confirmDelete = confirm("Are you sure you want to delete the following?\nProduct: " + productId + "\nColors: " + colors + "\nQuantity: " + quantity + "\nPrice: " + price);
            if (confirmDelete == true) {
                element.attr('bns-ignoreclick', '').parents('tr').css('opacity', '.5');
				$.ajax({
					type: 'POST',
					url: '/admin/control/removeProductPrice',
					dataType: 'json',
					data: {
						'productId': productId,
						'quantity': quantity,
						'price': price,
						'colors': colors
					}
				}).done(function(response) {
					if(response.success){
						element.parents('tr').remove();
					} else {
						element.removeAttr('bns-ignoreclick').parents('tr').css('opacity', '1');
					}
				});
            }
        }
	});


	// Run the below functions on initial load for both groups.
	handleListUpdate('jqs-asset_row', 'jqs-assetList', 'jqs-asset_row');
	handleListUpdate('design-asset-row', 'jqs-design-assetList', 'design-asset-row');
	handleListUpdate('jqs-design-id_row', 'jqs-design-idList', 'jqs-design-id_row');

	setTotalAssets();
	updateAssetSort();

});