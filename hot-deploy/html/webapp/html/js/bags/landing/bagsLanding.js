function validateEmailAddress(email) {
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}

$.fn.addOrUpdateBrontoEmail = function(emailAddress, callback, source) {
	if(validateEmailAddress(emailAddress)) {
		$.ajax({
			type: 'POST',
			url: '/bags/control/addOrUpdateContact',
			data: 'email=' + emailAddress + '&mode=subscribe&emailSource=Bags',
			dataType: 'json',
			cache: false
		}).done(function(data) {
			callback(data.success);
		}).fail(function(data) {
			callback(false);
		});
	} else {
		callback(false);
	}
};

$('.jqs-footer_email_subscribe').on('click', function() {
	var input_element = $('.jqs-email_subscribe_input');
	var emailAddress = input_element.val();
	if(!validateEmailAddress(emailAddress)) {
		$('.jqs-subscribe-email-error').html('Invalid Email address').show();
	} else {
		$().addOrUpdateBrontoEmail(emailAddress, function(success) {
			if(success) {
				alert('Successfully subscribed to email newsletter');
				input_element.val('')
			} else {
				alert('An error occurred while subscribing to newsletter');
			}
		});
	}
}).on('focus', function() {
	$('.jqs-subscribe-email-error, .jqs-subscribe-email-message').hide();
});
