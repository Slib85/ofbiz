$(document).ready(function() {
	$('input[name="addressing-amount"]').on('input', function() {
		$(this).val($(this).val().replace(/[^0-9]/, ''));
		var cost_per_address;
		var quantity = parseInt($(this).val());
		if (quantity) {
			if (quantity >= 0 && quantity <= 99) {
				cost_per_address = 1.15;
			}
			else if (quantity >= 100 && quantity <= 199) {
				cost_per_address = .87;
			}
			else if (quantity >= 200 && quantity <= 299) {
				cost_per_address = .75;
			}
			else if (quantity >= 300 && quantity <= 499) {
				cost_per_address = .55;
			}
			else if (quantity >= 500 && quantity <= 999) {
				cost_per_address = .48;
			}
			else {
				cost_per_address = .38;
			}

			$('.jqs-total-price-per-address').html(
				formatCurrency(
					Math.round(
						Math.ceil(
							(quantity * cost_per_address) * 100
						)
					) / 100
				)
			);
		}
		else {
			$('.jqs-total-price-per-address').html('--');
		}
	});
});
