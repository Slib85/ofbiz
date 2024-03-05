jQuery(document).ready(function($) {
    var offsetTop = 0;

    if ($('.site-navbar').length > 0) {
        offsetTop = $('.site-navbar').eq(0).innerHeight();
    }

	var table = $("#productTable").DataTable({
        iDisplayLength: 50,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        initComplete: function() {
            $('#productTable .replace-inputs > th').each(function(index) {
                var title = $(this).text();
                $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
            });

            $("#productTable .replace-inputs input").on('keyup change', function () {
                table
                    .column($(this).parent().index() + ':visible' )
                    .search(this.value)
                    .draw();
            });
        }
	});

    var table = $("#productTable2").DataTable({
        iDisplayLength: 50,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: false,
        aaSorting: [],
        initComplete: function() {
            $('#productTable2 .replace-inputs > th').each(function(index) {
                var title = $(this).text();
                $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
            });

            $("#productTable2 .replace-inputs input").on('keyup change', function () {
                table
                    .column($(this).parent().index() + ':visible' )
                    .search(this.value)
                    .draw();
            });
        }
    });

	$('#addQuantity .j-btn-add').on('click', function(e) {
		var self = this;
		var modal = $(this).closest('.modal');
		var quantity = modal.find('#quantity').val();
		var price = modal.find('#price').val();
		var colorsFront = modal.find('#colorsFront').val();
		var isFrontBlack = modal.find('#isFrontBlack').prop('checked') ? 'Y' : 'N';
		var colorsBack = modal.find('#colorsBack').val();
		var isBackBlack = modal.find('#isBackBlack').prop('checked') ? 'Y' : 'N';

		//ajax call
		$.ajax({
			type: 'POST',
			url: '/admin/control/addVendorQuantity',
			dataType: 'json',
			data: {
				"vendorProductId": $('#vendorProductId').val().toUpperCase(),
				"partyId": $('#partyId').val().toUpperCase(),
				"quantity": quantity,
				"price": price,
				"colorsFront": colorsFront,
				"isFrontBlack": isFrontBlack,
				"colorsBack": colorsBack,
				"isBackBlack": isBackBlack
			}
		}).done(function( response ) {
			if(response.success) {
				window.location = '/admin/control/productVendor?id=' + $('#productId').val().toUpperCase() + "&partyId=" + $('#partyId').val().toUpperCase();
			} else {
				if(typeof response.error != 'undefined') {
					alert(response.error);
				} else {
					alert("There was an error trying to add a new quantity.");
				}
			}
		}).error(function( response ) {
			//todo error
		});
	});

	$('#addSKU .j-btn-add').on('click', function(e) {
		var self = this;
		var modal = $(this).closest('.modal');
		var vendorPartyId = modal.find('#vendorPartyId').val();
		var productId = modal.find('#productId').val();
		var vendorProductId = modal.find('#vendorProductId').val();
        var quantity = modal.find('#quantity').val();

		var skuData = {
			"vendorPartyId": vendorPartyId,
			"productId": productId,
			"vendorProductId": vendorProductId,
            "quantity": quantity,
			"productStoreGroupId": '_NA_'
		};

		//hardcoded rule for dupli
		if(vendorPartyId.indexOf('V_DUPLI') != -1) {
			skuData['vendorParentProductId'] = '36538';
		}

		//ajax call
		$.ajax({
			type: 'POST',
			url: '/admin/control/addVendorProduct',
			dataType: 'json',
			data: skuData
		}).done(function( response ) {
			if(response.success) {
				window.location = '/admin/control/productVendor?id=' + productId + "&partyId=" + vendorPartyId;
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
});