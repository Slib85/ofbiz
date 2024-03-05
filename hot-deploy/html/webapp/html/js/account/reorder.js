$(document).ready(function() {
	$('.reorder_options input[value="approved"]').each(function() {
		$(this).prop('checked', true);
	});

	$('.reorder input').each(function() {
		$(this).prop('checked', false);
	});

	function checkReorder() {
		$(".reorder-button").removeClass("element-inactive").removeClass('elementInactive');
		if (!($('input[name^=reorder]:checked', (typeof $("#orderList").dataTable === 'function' ? $("#orderList").dataTable().fnGetNodes() : '')).length > 0)) {
			$(".reorder-button").addClass("element-inactive elementInactive");
		}
	}

	$(document).delegate("[name^=reorder]", "click", function() {
		checkReorder();
        if($(this).prop('checked') == true) {
            $('#' + $(this).data('order-id') + '-' + $(this).data('item-sequence-id') + '-reorder-options').removeClass('hidden');
        } else {
            $('#' + $(this).data('order-id') + '-' + $(this).data('item-sequence-id') + '-reorder-options').addClass('hidden');
        }

	});

    $(document).delegate("[id^=with-change-]", "click", function() {
        $(this).parent().parent().parent().find('textarea').removeClass('hidden');
    });

    $(document).delegate("[id^=with-no-change-]", "click", function() {
        $(this).parent().parent().parent().find('textarea').val('').addClass('hidden');
        $(this).parent().parent().parent().find('.jqs-artwork-comment-error').addClass('hidden');
    });

	$(".jqs-reorder").on('click', function() {
		var itemUUIDs = '';
		var hasErrors = false;
		$("[name^=reorder]:checked", (typeof $("#orderList").dataTable === 'function' ? $("#orderList").dataTable().fnGetNodes() : '')).each(function(){
			var orderId = $(this).data('order-id');
			var itemSequenceId = $(this).data('item-sequence-id');
			var proofApproved = $('.jqs-proof-approved[name="options-' + orderId + '-' + itemSequenceId + '"]').prop('checked');
			var artworkComment = $('[name="artwork-comment-' + orderId + '-' + itemSequenceId + '"]').val();
			if(proofApproved == false && artworkComment == '') {
				$(this).parent().parent().next().find('.jqs-artwork-comment-error').removeClass('hidden');
				hasErrors = true;
			} else if(proofApproved == true) {
				$(this).parent().parent().next().find('.jqs-artwork-comment-error').addClass('hidden');
			}
			itemUUIDs += (itemUUIDs != '' ? '^' : '') + orderId + '|' + itemSequenceId + '|' + proofApproved + '|' + artworkComment;
		});
		if(hasErrors == true) {
			$('#jqs-reorderError').html('Please correct the errors shown below').show();
		} else {
			$('#jqs-reorderError').html('').hide();
		}
		if(hasErrors == false && itemUUIDs != '') {
			$.ajax({
				url: 'reorderItems',
				data: 'itemUUIDs=' + itemUUIDs,
				dataType: 'json',
				method: 'POST'
			}).done(function(data) {
				if(data.success == true) {
					var addToCartData = JSON.stringify(data);
					var cart = $('<form/>').attr('id', 'cart').attr('action', 'cart').attr('method', 'POST').append('<input type="hidden" name="lastVisitedURL" value="' + document.referrer + '" />').append('<input type="hidden" name="fromAddToCart" value="true" />');
					localStorage.addToCartData = addToCartData;					
					$('body').append(cart);
					$('#cart').submit();
					$().updateMiniCart();
				} else {
                    $('#jqs-error').show().find('span').html('An error occurred while reordering the selected items.');
				}
			}).fail(function(jqXHR, textStatus){
				alert('An error occurred while reordering the selected items.')
			});
		}
	});
});
