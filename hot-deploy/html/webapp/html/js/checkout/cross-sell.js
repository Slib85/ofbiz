var pageType = 'cross-sell';

$(function() {
	$('.qtyUpgrade').on('click', function() {
		$.updateItemQuantity(0, $(this).attr('data-qty'), pageType, false);
	});

	$('[data-dropdown-target=dropdown-nav-cart]').trigger('mousemove'); //trigger mini cart
});