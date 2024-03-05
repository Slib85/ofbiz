<#if emailType == "bulkEnvelopesContactForm"></#if>
<link href="<@ofbizContentUrl>/html/css/customer-service/contactForm.css</@ofbizContentUrl>" rel="stylesheet" />
<div class="content contactForm">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/main</@ofbizUrl>">Home</a> > Customer Service > ${emailDisplayName?if_exists} Email
	</div>
	<div class="section jqs-formSection">
		<form id="emailForm" data-abide="" name="trade-form" method="post" action="#">
			<h4 class="padding-bottom-xxs">Email Us</h4>
			<input type="hidden" value="bulk@envelopes.com" name="emailTo" />
			<input class="jqs-validateExists" type="text" value="" placeholder="Your Name (required)" name="name" />
			<input class="jqs-validateExists" type="text" value="" placeholder="Your Email Address (required)" name="email" />
			<input type="text" value="" placeholder="Order ID" name="orderId" />
			<select name="inquiry">
				<option value="">What is your inquiry about?</option>
				<#if emailType?exists && emailType == "bulkEnvelopes"><option selected value="bulkOrders">Bulk Orders (Large Quantities)</option></#if>
				<#if emailType?exists && emailType == "corporatePrinting"><option selected value="printingCapabilities">Printing Capabilities</option></#if>
				<option value="other">Other</option>
			</select>
			<input class="jqs-validateExists" type="text" value="" placeholder="Subject (required)" name="subject" />
			<textarea class="jqs-validateExists" type="text" value="" placeholder="Message (required)" name="message"></textarea>
			<div class="jqs-submit button button-cta right padding-left-xs padding-right-xs padding-top-xxs padding-bottom-xxs">Submit</div>
		</form>
	</div>
</div>

<style>
	textarea {
		resize: none;
		height: 300px;
	}
</style>

<script>
	$('.jqs-submit').on('click', function() {
		var valid = true;

		$('.jqs-validateExists').each(function() {
			if ($(this).val() == '' && $(this).css('display') != 'none') {
				valid = false;
			}
		});

		if (valid) {
			$('.jqs-submit').addClass('hidden');
			$.ajax({
				type: "POST",
				url: '/' + websiteId + '/control/doContactFormSubmission',
				data: $('#emailForm').serialize(),
				dataType: 'json',
				cache: false,
				async: false
			}).done(function(data) {
				if(data.success) {
					$('.jqs-formSection').html('Email Successfully Sent.');
					setTimeout(function() {
						window.location.href = 'https://www.envelopes.com';
					}, 3000);
					$('.jqs-submit').removeClass('hidden');
				} else {
					alert('There was an issue processing your form, please try again.');
					$('.jqs-submit').removeClass('hidden');
				}
			});
		}
		else {
			alert('Please make sure all required fields have data.');
		}
	});
</script>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/customer-service/contactForm.js</@ofbizContentUrl>"></script>
