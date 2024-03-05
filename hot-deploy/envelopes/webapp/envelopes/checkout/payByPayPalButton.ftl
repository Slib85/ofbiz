<script src="https://www.paypalobjects.com/api/checkout.js" data-version-4 log-level="warn"></script>
<script src="https://js.braintreegateway.com/web/3.37.0/js/client.min.js"></script>
<script src="https://js.braintreegateway.com/web/3.37.0/js/paypal-checkout.min.js"></script>
<div id="paypal-button"></div>

<#assign clientToken = Static["com.bigname.payments.braintree.BraintreeHelper"].getClientToken()?if_exists?replace("&#x3d;", "=") />

<script>
    // Create a client.
    braintree.client.create({
        authorization: '${clientToken}'
    }).then(function (clientInstance) {
        // Create a PayPal Checkout component.
        return braintree.paypalCheckout.create({
            client: clientInstance
        });
    }).then(function (paypalCheckoutInstance) {
        // Set up PayPal with the checkout.js library
        paypal.Button.render({
            env: '${Static["com.bigname.payments.braintree.BraintreeHelper"].BRAINTREE_ENVIRONMENT}',
            //commit: true, // Auto capture the funds right away

            style: { label: 'pay', size:  'small', shape: 'pill', color: 'gold' },

            payment: function () {
                return paypalCheckoutInstance.createPayment({
                    flow: 'checkout',
                    amount: ${grandTotal?default(0)},
                    currency: 'USD',
                    enableShippingAddress: false,
                    shippingAddressEditable: false
                });
            },

            onAuthorize: function (data, actions) {
                return paypalCheckoutInstance.tokenizePayment(data).then(function (payload) {
                    parent.setPayPalOrderReferenceDetails(payload.nonce);
                    parent.document.getElementById('paypalNotice').setAttribute('data-authed-amount', '${grandTotal?default(0)}');
                    parent.document.getElementById('paypalNotice').innerHTML = 'You have successfully authorized your PayPal account for $' + ${grandTotal?default(0)};
                });
            },

            onCancel: function (data) {
                if(window.console) {
                    console.log('checkout.js payment cancelled', JSON.stringify(data, 0, 2));
                }
            },

            onError: function (err) {
                if(window.console) {
                    console.error('checkout.js error', err);
                }
            }
        }, '#paypal-button');
    }).then(function () {
        if(window.console) {
            console.log('Paypal successfully rendered');
        }
    }).catch(function (err) {
        if(window.console) {
            console.log(err);
        }
    });
</script>