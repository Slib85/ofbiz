var pageType = 'checkout';

(function($) {
    var populateAddress = function(data, scope) {console.log(data);
        if(data === undefined) {
            resetAddress(scope);
        } else {
            $('.jqs-address-type[value=' + data.businessOrResidence + ']', scope).prop('checked', 'checked');
            $('.jqs-email'/*, scope*/).val(data.infoString);
            $('.jqs-first-name', scope).val(data.firstName);
            $('.jqs-last-name', scope).val(data.lastName);
            $('.jqs-company-name', scope).val(data.companyName);
            $('.jqs-address1', scope).val(data.address1);
            $('.jqs-address2', scope).val(data.address2);
//        $('.jqs-address3', scope).val(data.address3);
            $('.jqs-city', scope).val(data.city);
            $('.jqs-state', scope).val(data.stateProvinceGeoId);
            $('.jqs-postal-code', scope).val(data.postalCode);
            $('.jqs-country', scope).val(data.countryGeoId);
            if(scope == '.jqs-shipping' && data.postalCode != '') {
                $.getShippingRates($('.jqs-shipping').find('input,select').serializeObject(), pageType);
            }
            $('.jqs-phone', scope).val(data.contactNumber);
            $('.jqs-blind-ship', scope).prop('checked', data.isBlindShipment == 'Y');
            resetValidationErrors(scope);
        }
    };

    var resetValidationErrors = function(scope) {
        $('.jqs-abide', scope).closest('div').removeClass('error');
    };

    var resetAddress = function(scope) {
        $('.jqs-address-type[value=BUSINESS_LOCATION]', scope).prop('checked', 'checked');
        $('.jqs-email', scope).val('');
        $('.jqs-first-name', scope).val('');
        $('.jqs-last-name', scope).val('');
        $('.jqs-company-name', scope).val('');
        $('.jqs-address1', scope).val('');
        $('.jqs-address2', scope).val('');
//        $('.jqs-address3', scope).val('');
        $('.jqs-city', scope).val('');
        $('.jqs-state', scope).val('');
        $('.jqs-postal-code', scope).val('');
        $('.jqs-phone', scope).val('');
        $('.jqs-blind-ship', scope).prop('checked', '');
    };

    var updateItem = function(data, pageType) {
        $.ajax({
            type: 'POST',
            url: '/' + websiteId + '/control/updateCartItem',
            data: data,
            cache: false
        }).done(function(data) {
            if(data.success) {
                if(pageType == 'cross-sell') {
                    //relocate to cart once all products have been added
                    window.location = '/' + websiteId + '/control/cart';
                } else {
                    $.refreshOrderSummary(pageType);
                }
            } else {
                alert('failed');
                //TODO - fail over option;
            }
        });
    };

    var geoRegex = {
        canada      : /^[A-Z]\d[A-Z]( )?\d[A-Z]\d$/i,
        usa         : /^\d{5}(-\d{4})?$/
    };

    $.ajaxSetup({
        dataType : 'json',
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        async: true
    });

    $.extend({
        populateShippingAddress: function (addressData) {
            populateAddress(addressData, '.jqs-shipping');
            if ($("[name=bill_to_shipping]").is(':checked')) {
                $.billToShippingAddress();
            }
        },

        populateBillingAddress: function (addressData) {
            populateAddress(addressData, '.jqs-billing');
        },
        billToShippingAddress: function () {
            resetAddress('.jqs-billing');
            $('.jqs-email', '.jqs-billing').val($('.jqs-email', '.jqs-shipping').val());
            $('.jqs-first-name', '.jqs-billing').val($('.jqs-first-name', '.jqs-shipping').val());
            $('.jqs-last-name', '.jqs-billing').val($('.jqs-last-name', '.jqs-shipping').val());
            $('.jqs-company-name', '.jqs-billing').val($('.jqs-company-name', '.jqs-shipping').val());
            $('.jqs-address1', '.jqs-billing').val($('.jqs-address1', '.jqs-shipping').val());
            $('.jqs-address2', '.jqs-billing').val($('.jqs-address2', '.jqs-shipping').val());
            $('.jqs-city', '.jqs-billing').val($('.jqs-city', '.jqs-shipping').val());
            $('.jqs-state', '.jqs-billing').val($('.jqs-state', '.jqs-shipping').val());
            $('.jqs-postal-code', '.jqs-billing').val($('.jqs-postal-code', '.jqs-shipping').val());
            $('.jqs-country', '.jqs-billing').val($('.jqs-country', '.jqs-shipping').val());
            $('.jqs-phone', '.jqs-billing').val($('.jqs-phone', '.jqs-shipping').val());
        },

        bindSavedBillingAddressChangeEvent: function () {
            $('#saved-billing-address').on('change', function () {
                $.populateBillingAddress($('option:selected', $(this)).data('address'));
            });
        },

        bindSavedShippingAddressChangeEvent: function() {
            $('#saved-shipping-address').on('change', function() {
                $.populateShippingAddress($('option:selected', $(this)).data('address'));
            });
        },
        getShippingRates: function(address, pageType) {
            if($.isValidPostalCode(address)) {
                $.ajax({
                    type: 'GET',
                    url: '/' + websiteId + '/control/' + pageType + '-shipping-options',
                    data: address,
                    dataType: 'html',
                    cache: false
                }).done(function(data) {
                    $.refreshOrderSummary(pageType);
                    $("#shippingOptionsForm").spinner(false);
                    $('#jqs-shipping-options').html(data);
                    $.bindShippingMethodClickEvent();

                    if(typeof pageType !== 'undefined' && pageType == 'cart') {
                        GoogleAnalytics.trackEvent('Cart', 'Calculate Shipping', 'Success');
                    } else if(typeof pageType !== 'undefined' && pageType == 'checkout') {
                        GoogleAnalytics.trackEvent('Checkout', 'Calculate Shipping', 'Success');
                    }
                    /*
					While calculating the shipping cost on the cart page, the auto shipping promotion won't calculate the shipping discount
					due to the limitation in ofbiz promotions implementation. I don't like this, but this is the quickest and safest hack
					to make auto shipping discount work without any hardcoding. DO NOT REMOVE the below duplicate ajax call
					*/
                    $.ajax({
                        type: 'GET',
                        url: '/' + websiteId + '/control/' + pageType + '-shipping-options',
                        data: address,
                        dataType: 'html',
                        cache: false
                    }).done(function(data) {
                        $.refreshOrderSummary(pageType);
                        $("#shippingOptionsForm").spinner(false);
                        $('#jqs-shipping-options').html(data);
                        $.bindShippingMethodClickEvent();
                    }).fail(function (jqXHR) {
                        window.location.reload();
                    });
                }).fail(function(jqXHR) {
                    GoogleAnalytics.trackEvent('Cart', 'Calculate Shipping', 'Fail');
                });
            }
        },

        /*bindShippingCountryChangedEvent: function () {
            var previousCountryValue = '';
            setInterval(function () {
                var currentCountryValue = $('.jqs-shipping-country').val();
                if (previousCountryValue != currentCountryValue) {
                    previousCountryValue = currentCountryValue;
                    if (currentCountryValue == '' || currentCountryValue == 'USA' || currentCountryValue == 'CAN') {
                        $(".jqs-payment-options").removeClass('hidden');
                        $(".jqs-shipping-options").removeClass('hidden');
                        $("#jqs-iparcel").removeClass('hidden').addClass('hidden');
                        $("#jqs-place-order").removeClass('hidden');
                        if (!freeOrder) {
                            $('#pay_by_cc').trigger('click');
                        }
                    } else {
                        $(".jqs-payment-options").removeClass('hidden').addClass('hidden');
                        $(".jqs-shipping-options").removeClass('hidden').addClass('hidden');
                        $("#jqs-iparcel").removeClass('hidden');
                        $("#jqs-place-order").removeClass('hidden').addClass('hidden');
                        $.toggleAbideValidation('#jqs-pay-by-card', true);
                        $.toggleAbideValidation('#jqs-pay-by-amazon', true);
                        $.toggleAbideValidation('#jqs-pay-by-check', true);
                    }
                }
            }, 100);

        },

        bindShippingPostalCodeChangeEvent: function () {
            $('input[name=shipping_postalCode],input[name=shipping_address1]').on('change', function (e) {
                waitForFinalEvent(function () {
                    $.getShippingRates($('.jqs-shipping').find('input,select').serializeObject(), pageType);
                }, 1000, 'calculateShipping');
            });
        },

        bindShowShippingOptionsEvent: function () {
            $('#jqs-show-shipping').on('click', function () {
                if ($('input[name=shippingPostalCode]').val != '') {
                    $('input[name=shipping_postalCode]').val($('input[name=shippingPostalCode]').val())
                }
                $.getShippingRates($('.jqs-shipping').find('input,select').serializeObject(), 'checkout');
            });
        },*/

        bindSubmitEvent: function () {
            $('#checkout-error').hide();
            $('#checkout-message').hide();

            $('[data-abide]').on('submit', function () {
                if ($("[name=bill_to_shipping]").is(':checked')) {
                    $.billToShippingAddress();
                }
                $('.jqs-custom-abide').each(function () {
                    var validationMessageElement = $(this).next();
                    var defaultValidationMessage = validationMessageElement.text();
                    var invalidMessage = 'Valid ' + defaultValidationMessage.substring(0, 1).toLowerCase() + defaultValidationMessage.substring(1);
                    if ($(this).val() != '') {
                        validationMessageElement.html(invalidMessage);
                    } else {
                        validationMessageElement.html(defaultValidationMessage);
                    }
                });
                $('#ship_to_business', this).focus();
            });

           /* $('#checkoutForm')
                .on('invalid.fndtn.abide', function () {
                    $('#checkout-error').text('Please correct the errors shown below.').show();
                })
                .on('valid.fndtn.abide', function () {
                    $('#checkout-error').hide();
                    $("body").spinner(true, false, 250, 6, undefined, 'Your order is being processed...');
                });*/
        },

        /*populateCreditCardType: function (ccNumberElement) {
            $('[name=cardType]').val($.getCCTypeDetailed(ccNumberElement.val()));
        },

        bindCCNumberBlurEvent: function () {
            $('[name=cardNumber]').on('blur', function () {
                $.populateCreditCardType($(this));
            });
        },

        bindBillToShippingClickEvent: function () {
            $("[name=bill_to_shipping]").on("click", function () {
                if ($(this).is(":checked")) {
                    $(".billing-content").css("display", "none");
                    $.billToShippingAddress();
                    // $.toggleAbideValidation('.jqs-billing', true);
                } else {
                    $.billToShippingAddress();
                    $(".billing-content").css("display", "block");
                    // $.toggleAbideValidation('.jqs-billing');
                }
            });
        },

        bindPayByClickEvent: function () {
            $("[name=paymentMethodTypeId]").on("click", function () {
                if ($(this).val() == "PERSONAL_CHECK") {
                    $(".pay-by-card").css("display", "none");
                    $(".pay-by-check").css("display", "block");
                    $(".pay-by-amazon").css("display", "none");
                    // $.toggleAbideValidation('#jqs-pay-by-card', true);
                    // $.toggleAbideValidation('#jqs-pay-by-amazon', true);
                    // $.toggleAbideValidation('#jqs-pay-by-check');
                } else if ($(this).val() == "EXT_AMAZON") {
                    $(".pay-by-card").css("display", "none");
                    $(".pay-by-check").css("display", "none");
                    $(".pay-by-amazon").css("display", "block");
                    // $.toggleAbideValidation('#jqs-pay-by-amazon');
                    // $.toggleAbideValidation('#jqs-pay-by-card', true);
                    // $.toggleAbideValidation('#jqs-pay-by-check', true);
                } else {
                    $(".pay-by-card").css("display", "block");
                    $(".pay-by-amazon").css("display", "none");
                    $(".pay-by-check").css("display", "none");
                    // $.toggleAbideValidation('#jqs-pay-by-card');
                    // $.toggleAbideValidation('#jqs-pay-by-check', true);
                    // $.toggleAbideValidation('#jqs-pay-by-amazon', true);
                }
            });
        },

        bindVolatileEvents: function () {
            $.bindApplyCouponButtonClickEvent();
        },*/

        bindAllEvents: function () {
            // $.bindToggleLoginPanelClickEvent();
            // $.bindOnblurEventToEmailAddress();
            $.bindSavedShippingAddressChangeEvent();
            // $.bindShippingCountryChangedEvent();
            // $.bindShippingPostalCodeChangeEvent();
            $.bindSavedBillingAddressChangeEvent();
            // $.bindBillToShippingClickEvent();
            // $.bindShippingMethodClickEvent();
            // $.bindPayByClickEvent();
            // $.bindCCNumberBlurEvent();
            // $.bindCustomAbideValidationEvent();
            // $.bindShowShippingOptionsEvent();
            $.bindSubmitEvent();
            // $.bindVolatileEvents();
            // $.toggleAbideValidation('#jqs-pay-by-amazon', true);
        }/*,

        triggerAllLoadEvents: function () {
            $("[name=bill_to_shipping]").attr('checked', false);
            if ($('#checkout-message').text() != '') {
                $('#checkout-message').removeClass('hidden').show();
            }

            if ($('#checkout-error').text() != '') {
                $('#checkout-error').removeClass('hidden').show();
            }

            if (freeOrder) {
                $.toggleAbideValidation('#jqs-pay-by-card', true);
                $.toggleAbideValidation('#jqs-pay-by-check', true);
                $.toggleAbideValidation('#jqs-pay-by-amazon', true);
                $('div.payment-options').hide();
            }
        }*/
    });
})(jQuery);

$(document).ready(function() {
    $.bindAllEvents();
    // $.triggerAllLoadEvents();
});

/* JANRAIN */
(function() {
    if (typeof window.janrain !== 'object') window.janrain = {};
    if (typeof window.janrain.settings !== 'object') window.janrain.settings = {};

    if(websiteId == "folders") {
        janrain.settings.tokenUrl = 'https://www.folders.com/folders/control/getJanRainToken';
    } else {
        janrain.settings.tokenUrl = 'https://www.envelopes.com/envelopes/control/getJanRainToken';
    }
    janrain.settings.tokenAction = 'event';

    function isReady() { janrain.ready = true; };
    if (document.addEventListener) {
        document.addEventListener("DOMContentLoaded", isReady, false);
    } else {
        window.attachEvent('onload', isReady);
    }

    var e = document.createElement('script');
    e.type = 'text/javascript';
    e.id = 'janrainAuthWidget';

    if (document.location.protocol === 'https:') {
        e.src = 'https://rpxnow.com/js/lib/envelopes/engage.js';
    } else {
        e.src = 'http://widget-cdn.rpxnow.com/js/lib/envelopes/engage.js';
    }

    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(e, s);
})();

function janrainWidgetOnload() {
    janrain.events.onProviderLoginToken.addHandler(function(response) {
        if(typeof response.token != 'undefined') {
            $('#coToken').val(response.token);
            $('#loginForm').submit();
        }
    });
}
