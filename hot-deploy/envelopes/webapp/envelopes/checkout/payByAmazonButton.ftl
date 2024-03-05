<script type="text/javascript">
	window.onAmazonLoginReady = function() {
		amazon.Login.setClientId('amzn1.application-oa2-client.d5080b83abc24c07986ac190bbfa0de9');
	};
</script>
<script src="https://static-na.payments-amazon.com/OffAmazonPayments/us/js/Widgets.js"></script>
<div id="AmazonPayButton"/>
<script type="text/javascript">
	var authRequest;
	var websiteId = (typeof parent.websiteId !== "undefined") ? parent.websiteId : "envelopes";

	OffAmazonPayments.Button("AmazonPayButton", "ANJW8CRMGP42Z", {
		type: "PwA",
		authorization: function() {
			loginOptions = {scope: "payments:widget", popup: true};
			authRequest = amazon.Login.authorize (loginOptions, "/" + websiteId + "/control/payByAmazonWallet");
		},
		onError: function(error) {
			//console.log(error);
		}
	});
</script>