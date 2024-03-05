$(function() {
	/* BEGIN TEST MODE */
	test_mode = false;
	var code = '';

	$(window).on('keydown', function(e) {
		if (e.keyCode == 27) {
			code = '';
			test_mode = false;
		} else {
			code += e.keyCode;
		}

		if (code == '192697886192') {
			$('[name="billing_firstName"]').val('test');
			$('[name="billing_lastName"]').val('test');
			$('[name="billing_companyName"]').val('test');
			$('[name="company_website"]').val('test');
			$('[name="billing_address1"]').val('test');
			// $('[name="billing_address2"]').val('test');
			$('[name="billing_city"]').val('test');
			$('[name="billing_stateProvinceGeoId"]').val('NY');
			$('[name="billing_postalCode"]').val('12345');
			// $('[name="billing_contactNumber"]').val('1231231234');
			// $('[name="industry"]').val('Printing');
			// $('[name="interest"]').val('Business Choices');
			// $('[name="federal_tax_id"]').val('test');
			$('[name="emailAddress"]').val('mike@envelopes.com');
			$('[name="password"]').val('test');
			$('[name="verify_password"]').val('test');
			$('[name="comments"]').val('test');
			test_mode = true;
		}
	});
	/* END TEST MODE */

	function doResponse() {
		$('.jqs-application_content').html('');
		$('.jqs-application_content').append(
			$('<p />').addClass('response_text').html('Thank you for your application.  We have received your request, we will be in touch shortly.')
		)

		if ($('[name="doPromo"]').val() == "Y") {
			$('.jqs-application_content').append(
				$('<p />').addClass('response_text margin-top-xs').html('Your free Swatchbook has been added to your cart!')
			).after(
				$('<img />').addClass('jqs-free_swatchbook_image free_swatchbook_image').attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/SWATCHBOOK?wid=400&fmt=png-alpha')
			);

			var sb_top = parseInt($('.jqs-free_swatchbook_image').offset().top);
			var sb_left = parseInt($('.jqs-free_swatchbook_image').offset().left);
			var target_top = parseInt($('.cart_container').offset().top) + parseInt($('.cart_container').outerHeight() / 2);
			var target_left = parseInt($('.cart_container').offset().left) + parseInt($('.cart_container').outerWidth() / 2);
			var spin_amount = 2340;
			var delay = 2000;

			$(window).scrollTop(0);

			$('.jqs-free_swatchbook_image').css({
				position: 'absolute',
				top: sb_top,
				left: sb_left
			}).appendTo('body');

			setTimeout(function() {
				$('.jqs-free_swatchbook_image').animate({
					width:'0px',
					top: target_top,
					left: target_left
				}, {
					duration: delay,
					queue: false
				});

				$('.jqs-free_swatchbook_image').animate({
					borderSpacing: spin_amount
				}, {
					step: function(now,fx) {
						$(this).css({
							'-webkit-transform':'rotate(' + now + 'deg)',
							'-moz-transform':'rotate(' + now + 'deg)',
							'transform':'rotate(' + now + 'deg)'
						});
					},
					queue: false,
					duration: delay
				}, 'linear');

				// Fail safe incase it doesn't get to 0 pixels width
				setTimeout(function() {
					$('.jqs-free_swatchbook_image').hide();
				}, delay);

				var prodData = {
					add_product_id: 'SWATCHBOOK',
					quantity: 1
				}

				$.ajax({
					type: 'POST',
					url: 'https://www.envelopes.com/addToCart',
					async: false,
					data: prodData,
					cache: false
				}).done(function(data) {
					setTimeout(function() {
						window.location.href = cart_url;
					}, delay);
				});
			}, delay);
		} else {
			setTimeout(function() {
				window.location = '/';
			}, 3000);
		}
	}

	$('form[name="trade-form"]').on('submit', function() {
		return false;
	}).on('valid.fndtn.abide', function () {
		if (test_mode) {
			doResponse();
		}
		else {
			var valid = true;
			if (typeof $('input[name="company_website"]').val() != 'undefined' && $('input[name="company_website"]').val().match(/^.*?\..*?$/) == null) {
				valid = false;
				$('input[name="company_website"]').next().css('display', 'block');
				$('input[name="company_website"]').parent().removeClass('error').addClass('error');
			}
			else {
				$('input[name="company_website"]').next().css('display', 'none');
				$('input[name="company_website"]').parent().removeClass('error');
			}

			if(!validateEmailAddress($('[name="emailAddress"]').val())){
				valid = false;
				$('input[name="emailAddress"]').next().css('display', 'block');
				$('input[name="emailAddress"]').parent().removeClass('error').addClass('error');
			} else {
				$('input[name="emailAddress"]').next().css('display', 'none');
				$('input[name="emailAddress"]').parent().removeClass('error');
			}

			// if ($('input[name="federal_tax_id"]').val().replace(/\-/g, '').length != 9) {
			// 	valid = false;
			// 	$('input[name="federal_tax_id"]').next().css('display', 'block');
			// 	$('input[name="federal_tax_id"]').parent().removeClass('error').addClass('error');
			// }
			// else {
			// 	$('input[name="federal_tax_id"]').next().css('display', 'none');
			// 	$('input[name="federal_tax_id"]').parent().removeClass('error')
			// }

			if($('#dnfField').val() == '' && valid) {
				$.ajax({
					type: "POST",
					url: '/' + websiteId + '/control/tradeRequest',
					data: $('#trade-form').serialize(),
					dataType: 'json',
					cache: false,
					async: false
				}).done(function(data) {
					if(data.success) {
						GoogleAnalytics.trackPageview('/' + websiteId + '/control/tradeRequest', 'Trade Request Submission');
						dataLayer.push({
							'event': 'tradepro_submit'
						});
						doResponse();
					} else {
						//TODO throw error
						alert("There was an issue processing your form, please try again.");
					}
				});
			}
		}
	});

	$('#tradeSubmit').on('click', function(){
		$('form[name="trade-form"]').submit();
	});

	// Accordion Control
	var accordionAction = function(){
		if($('.mobile-only').is(':visible')){
			$('.benefits-info > div').hide();
		} else {
			$('.benefits-info > div').show();
		}
	}
	$(window).on('load resize', function(){
		accordionAction();
	});

	$('[bns-benefitsinfo]').on('click', function() {
		$('[bns-benefitsinfo] > div').slideUp();
		if (typeof $(this).attr('data-active') == 'undefined' || $(this).attr('data-active') == 'false') {
			$('[bns-benefitsinfo]').attr('data-active', 'false');
			$(this).attr('data-active', 'true').children('div').slideToggle();
		} else {
			$(this).attr('data-active', 'false');
		}
	});

	// Switch Discount Chart on Button Click (Mobile)
	$('.learn-how-to-save').click(function(){
		var currentHtml = $(this).html();
		var switchHtml = $(this).data('html-switch');
		$(this).html(switchHtml);
		$(this).data('html-switch', currentHtml);
		$('#trade-discount-chart-mobile').toggleClass('hidden');
	});

});
