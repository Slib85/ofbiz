$(function() {
	function setCartAbandonCookie() {
		var d = new Date();
		d.setTime(d.getTime() + (180 * 24 * 60 * 60 * 1000));
		var expires = "expires=" + d.toGMTString();
		document.cookie = "cabandon-popup=true; " + expires;
	}

	$(document).mouseleave(function(e) {
		if(e.clientY < 0) {
			if(!window.mobilecheck() && (document.cookie.indexOf("cabandon-popup") == -1)) {
				$('#beforeYouLeave').foundation('reveal', 'open');
				setCartAbandonCookie();
				GoogleAnalytics.trackEvent('Cart', 'Abandon Popup', 'Shown');
			}
		}
	});

	$('#beforeYouLeave .jqs-submit_email').on('click', function() {
		var parent = $('#beforeYouLeave');
		var emailSource = 'Cart PopUp';
		var emailAddress = $('.beforeYouGo [name="email_address"]').val();

		if(validateEmailAddress(emailAddress)) {
			$().addOrUpdateBrontoEmail(emailAddress, function(success) {
				if(success) {
					$(parent).find('.jqs-errorBox').removeClass('hidden').addClass('hidden');
					$(parent).find('.jqs-successBox').append(
						'Use Code: '
					).append(
						$('<span />').addClass('couponCode').html('TAKE10')
					).append(
						$('<div />').addClass('infoText').append(
							$('<span />').addClass('infostrix').html('*')
						).append(
							'Order SUBTOTAL must equal $50 or more'
						)
					).removeClass('hidden');

					$(parent).find('.jqs-options').addClass('hidden');
					GoogleAnalytics.trackEvent('Cart', 'Abandon Popup', 'Success');
				} else {
					$(parent).find('.jqs-errorBox').html('An error occurred while subscribing.').removeClass('hidden');
					GoogleAnalytics.trackEvent('Cart', 'Abandon Popup', 'Error');
				}
			}, emailSource);
		} else {
			$(parent).find('.jqs-errorBox').html('Invalid Email Address.').removeClass('hidden');
		}
	});
});
