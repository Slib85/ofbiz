$(function() {
	$('form[name="non-profit-form"]').on('submit', function() {
		return false;
	}).on('valid.fndtn.abide', function () {
		if($('#dnfField').val() == '') {
			$.ajax({
				type: "POST",
				url: '/' + websiteId + '/control/nonProfitRequest',
				data: $('#non-profit-form').serialize(),
				dataType: 'json',
				cache: false,
				async: false
			}).done(function(data) {
				if(data.success) {
					GoogleAnalytics.trackPageview('/' + websiteId + '/control/nonProfitRequest', 'Non-Profit Request Submission');
					alert("We have received your request, we will be in touch shortly. Thank you!");
					window.location = '/';
				} else {
					//TODO throw error
					alert("There was an issue processing your form, please try again.");
				}
			});
		}
	});

	$('#nonProfitSubmit').on('click', function() {
		$('form[name="non-profit-form"]').submit();
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