$(document).ready(function() {
	$('.progressbar').progressbar({ value: 1, max: 8 });

	$('.jqs-getQuoteSubmit').on('click', function() {
		$('form[name="madeToOrderForm"]').submit();
	});

	updateStep = function(nextStep, currentStep) {
		currentStep = currentStep != undefined ? currentStep : parseInt($('.progressbar').progressbar('value'));
		$('[data-step_id="' + currentStep + '"]').addClass('hidden');
		$('.progressbar').progressbar('value', nextStep);
		$('[data-step_id="' + nextStep + '"]').removeClass('hidden');
	}

	$('.jqs-next').on('click', function() {
		updateStep(parseInt($('.progressbar').progressbar('value')) + 1);
	});

	$('.jqs-previous').on('click', function() {current_step = parseInt($('.progressbar').progressbar('value'));
		updateStep(parseInt($('.progressbar').progressbar('value')) - 1);
	});

	$('.jqs-new_quote').on('click', function() {
		updateStep(1, 9);
		$('[bns-email_quote]').removeClass('hidden');
	});

	validateForm = function() {
		var element_name = '';
		var parent_name = '';
		$('.jqs-response').hide();
		$('.jqs-response').html('');

		$('.custom_quote_shop [required]').each(function() {
			element_name = $(this).attr('name');
			parent_name = $(this).attr('data-parent_name') ? $(this).attr('data-parent_name') : '';
			$('.jqs-data[data-name="' + (parent_name ? parent_name : element_name) + '"]').closest('.jqs-data_row').find('.parent_text').css('color', 'inherit');
			if (!$(this).val()) {
				$('.jqs-response').append('<p>' + element_name + ' is Required.</p>');
				$('.jqs-data[data-name="' + (parent_name ? parent_name : element_name) + '"]').closest('.jqs-data_row').find('.parent_text').css('color', '#ff0000');
			}
		});

		if ($('.jqs-response').html() != '') {
			$('.jqs-response').show();
			return false;
		}

		return true;
	}

	function updateFinalStep(element) {
		var element_name = element.attr('name');
		var parent_name = element.attr('data-parent_name') ? element.attr('data-parent_name') : '';
		if (element_name == 'quantity' || parent_name == 'quantity') {
			$('.jqs-data[data-name="quantity"]').html(
				$('.jqs-update_on_change[name="custom_quantity"]').val() ?
				$('.jqs-update_on_change[name="custom_quantity"]').val() :
				$('.jqs-update_on_change[name="quantity"]').val()
			);
		}
		else if (element_name == 'size' || parent_name == 'size') {
			if ($('.jqs-update_on_change[name="custom_height"]').val() && $('.jqs-update_on_change[name="custom_width"]').val()) {
				var val = $('.jqs-update_on_change[name="custom_width"]').val() + ' x ' + $('.jqs-update_on_change[name="custom_height"]').val();
				if ($('.jqs-update_on_change[name="size"] option[value="' + val + '"]').length == 0) {
					$('.jqs-update_on_change').append(
						$('<option />').val(val).html(val)
					);
				}
				$('.jqs-update_on_change[name="size"]').val(val);
			}

			$('.jqs-data[data-name="size"]').html(
				$('.jqs-update_on_change[name="custom_height"]').val() && $('.jqs-update_on_change[name="custom_width"]').val() ?
				$('.jqs-update_on_change[name="custom_width"]').val() + ' X ' + $('.jqs-update_on_change[name="custom_height"]').val() :
				$('.jqs-update_on_change[name="size"]').val()
			);
		}
		else if (parent_name == 'name') {
			$('.jqs-data[data-name="name"]').html(
				$('.jqs-update_on_change[name="firstName"]').val() + ' ' + $('.jqs-update_on_change[name="lastName"]').val()
			);
		}
		else if (parent_name == 'address') {
			$('.jqs-data[data-name="address"]').html(
				$('.jqs-update_on_change[name="address1"]').val() + '<br />' +
				$('.jqs-update_on_change[name="city"]').val() + ', ' + $('.jqs-update_on_change[name="state"]').val() + ' ' + $('.jqs-update_on_change[name="zip"]').val() + '<br />' +
				$('.jqs-update_on_change[name="country"]').val()
			);
		}
		else {
			$('.jqs-data[data-name="' + element_name + '"]').html(element.val());
		}
	}

	$('.jqs-update_on_change').each(function() {
		updateFinalStep($(this));
	});

	$('.jqs-update_on_change').on('change', function() {
		updateFinalStep($(this));
	});

	var sealing_method_info = {
		sealing_method_1: {
			title: 'Regular Glue',
			description: 'The standard method of sealing envelopes, just moisten to securely seal your contents.'
		},
		sealing_method_2: {
			title: 'Peel &amp; Press&#8482;',
			description: 'A smarter, faster way to seal your envelopes. Simply peel away the label to reveal the adhesive, then press to securely seal your contents!'
		},
		sealing_method_3: {
			title: 'None',
			description: 'No sealing method'
		},
		sealing_method_4: {
			title: '',
			description: '<input type="text" name="sealing_method_other" value="" placeholder="Describe desired sealing method" />'
		}
	}

	$('[name="sealing_method"]').on('click', function() {
		if (sealing_method_info[$(this).attr('id')]) {
			$('.jqs-sealing_method_title').html(sealing_method_info[$(this).attr('id')].title);
			$('.jqs-sealing_method_description').html(sealing_method_info[$(this).attr('id')].description);
		}
		else {
			$('.jqs-sealing_method_title').html('');
			$('.jqs-sealing_method_description').html('');
		}
	});

	$('[name="printing"]').on('change', function() {
		if ($('[name="printing"]:checked').val() == 'N') {
			$('.jqs-printed_colors').hide();
		}
		else {
			$('.jqs-printed_colors').show();
		}
	});

	$('[name="window_option"]').on('change', function() {
		if ($('[name="window_option"]:checked').val() == "no window") {
			$('[name="window_description"]').hide().val('');
		}
		else {
			$('[name="window_description"]').show();
		}
	});

	$('[data-step_id_to_edit]').on('click', function() {
		var current_step = parseInt($('.progressbar').progressbar('value'));
		var goto_step = $(this).data('step_id_to_edit');
		$('[data-step_id="' + current_step + '"]').addClass('hidden');
		$('.progressbar').progressbar('value', goto_step);
		$('[data-step_id="' + goto_step + '"]').removeClass('hidden');
	});

	$('[name="printing"]').trigger('change');
	$('[name="window_option"]').trigger('change');
});

