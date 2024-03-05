<style>
#walletWidgetDiv {
	width: 400px;
	height: 228px;
}
</style>
<script type='text/javascript'>
	window.onAmazonLoginReady = function() {
		amazon.Login.setClientId('amzn1.application-oa2-client.d5080b83abc24c07986ac190bbfa0de9');
	};
</script>
<script src="https://static-na.payments-amazon.com/OffAmazonPayments/us/js/Widgets.js"></script>
<div id="walletWidgetDiv"></div>
<script type="text/javascript">
	new OffAmazonPayments.Widgets.Wallet({
		sellerId: 'ANJW8CRMGP42Z',
		onOrderReferenceCreate: function(orderReference) {
			parent.setAmazonOrderReferenceDetails(orderReference.getAmazonOrderReferenceId());
		},
		design: {
			designMode: 'responsive'
		},
		onPaymentSelect: function(orderReference) {
			// Replace this code with the action that you want to perform
			// after the payment method is selected.
		},
		onError: function(error) {
			// your error handling code
		}
	}).bind("walletWidgetDiv");
</script>