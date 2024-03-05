
(function($) {
    var addressTemplate = $('.jqs-address:first');
    $.extend({
        getAddressType: function() {
            return $('#edit-address').find('.jqs-address-type').text();
        },

        saveAddress: function(layerElement, updateFlag) {
            $.ajax({
                type: 'POST',
                url: addAddressURL,
                data: layerElement.find('form[name="addressForm"]').serialize(),
                dataType:'json',
                cache: false
            }).done(function(data) {
                if(data.success) {
                    $.renderAddressElement(layerElement, data.address, updateFlag);
                } else {
                    $.showErrorNotification(layerElement,'An error occurred while ' +  (updateFlag ? 'updating' : 'adding') +' the address');
                }
            });
        },

        removeAddress: function(contactMechId) {
            $.ajax({
                type: 'POST',
                url: removeAddressURL,
                data: ($.getAddressType() == 'addressing' ? 'customerAddressId=' : 'contactMechId=') + contactMechId,
                dataType:'json',
                cache: false
            }).done(function(data) {
                if(data.success) {
                    $.removeAddressElement(contactMechId);
                } else {
                    $.showErrorNotification(undefined, 'An error occurred while deleting the address');
                }
            });
        },

        renderAddressElement : function(layerElement, address, updateFlag) {
            updateFlag = $.populateAddress(address, updateFlag);
            $.showSuccessNotification(updateFlag == true ? 'Address has been successfully updated' : 'Address has been successfully added');
            $.closeAddressLayer(layerElement);
            $.resizeAddressList();
        },

        removeAddressElement: function(contactMechId) {
            $('#address-' + contactMechId).remove();
            $.showSuccessNotification("Address has been successfully removed");
            $.resizeAddressList();
        },

        populateAddress : function(address, updateFlag) {
            if($.getAddressType() == 'addressing') {
                var addressElement = $('#address-' + address.customerAddressId);
                if (addressElement.length == 0) {
                    addressElement = addressTemplate.clone();
                } else {
                    updateFlag = true;
                }

                $(addressElement).attr('id', 'address-' + address.customerAddressId);

                $(addressElement).find('.jqs-name').html(address['name']);
                $(addressElement).find('.jqs-name2').html(address['name2']);
                $(addressElement).find('.jqs-address1').html(address['address1']);
                $(addressElement).find('.jqs-address2').html(address['address2']);
                $(addressElement).find('.jqs-city').html(address['city']);
                $(addressElement).find('.jqs-state').html(address['state']);
                $(addressElement).find('.jqs-zip').html(address['zip']);
                $(addressElement).find('.jqs-country').html(address['country']);
                $(addressElement).find('.jqs-phone').html(address['contactNumber']);
                $(addressElement).find('.jqs-contact-mech-id').html(address['contactMechId']);

                var editLink = $(addressElement).find('.jqs-address-data');
                editLink.data('address', {});
                editLink.data('address', JSON.parse(address['jsonData']));
                editLink.off().on("click", function () {
                    $.populateAddressForm($('#edit-address'), $(this).data('address'));
                });

                if (updateFlag === undefined) {
                    $('#jqs-address-list').prepend(addressElement);
                }

                return updateFlag;
            } else {
                var addressElement = $('#address-' + address.contactMechId);
                if (addressElement.length == 0) {
                    addressElement = addressTemplate.clone();
                } else {
                    updateFlag = true;
                }
                var defaults = address.defaults;
                if (defaults == 'default-shipping default-billing') {
                    $('.default-shipping.default-billing').removeClass('default-shipping').removeClass('default-billing');
                } else if (defaults == 'default-shipping') {
                    $('.default-shipping').removeClass('default-shipping');
                    $(addressElement).find('.jqs-defaults').removeClass('default-billing');
                } else if (defaults == 'default-billing') {
                    $('.default-billing').removeClass('default-billing');
                    $(addressElement).find('.jqs-defaults').removeClass('default-shipping');
                } else if (defaults == 'none') {
                    $(addressElement).find('.jqs-defaults').removeClass('default-shipping').removeClass('default-billing');
                }

                $(addressElement).attr('id', 'address-' + address.contactMechId);
                $(addressElement).find('.jqs-defaults').addClass(defaults);

                $(addressElement).find('.jqs-first-name').html(address['firstName']);
                $(addressElement).find('.jqs-last-name').html(address['lastName']);
                $(addressElement).find('.jqs-company').html(address['companyName']);
                $(addressElement).find('.jqs-address1').html(address['address1']);
                $(addressElement).find('.jqs-address2').html(address['address2']);
                $(addressElement).find('.jqs-city').html(address['city']);
                $(addressElement).find('.jqs-state').html(address['stateProvinceGeoId']);
                $(addressElement).find('.jqs-postal-code').html(address['postalCode']);
                $(addressElement).find('.jqs-country').html(address['countryGeoId']);
                $(addressElement).find('.jqs-phone').html(address['contactNumber']);
                $(addressElement).find('.jqs-contact-mech-id').html(address['contactMechId']);
                var contactMechPurposeType = !address['contactMechPurposeTypeId'] ? '' : address['contactMechPurposeTypeId'] == 'SHIPPING_LOCATION' ? 'Shipping Address' : address['contactMechPurposeTypeId'] == 'BILLING_LOCATION' ? 'Billing Address' : 'Shipping &amp; Billing Address';
                $(addressElement).find('.jqs-contact-mech-purpose-type').html(contactMechPurposeType);
                var editLink = $(addressElement).find('.jqs-address-data');
                editLink.data('address', {});
                editLink.data('address', JSON.parse(address['jsonData']));
                editLink.off().on("click", function () {
                    $.populateAddressForm($('#edit-address'), $(this).data('address'));
                });

                if (updateFlag === undefined) {
                    $('#jqs-address-list').append(addressElement);
                }

                return updateFlag;
            }
        },

        populateAddressForm : function(layerElement, address) {

            if(address) {
                var formElement = $(layerElement).find('form[name="addressForm"]');
                if($.getAddressType() == 'addressing') {
                    $(formElement).find("[name='name']").val(address ? address['name'] : '');
                    $(formElement).find("[name='name2']").val(address ? address['name2'] : '');
                    $(formElement).find("[name='address1']").val(address ? address['address1'] : '');
                    $(formElement).find("[name='address2']").val(address ? address['address2'] : '');
                    $(formElement).find("[name='city']").val(address ? address['city'] : '');
                    $(formElement).find("[name='state']").val(address ? address['state'] : '');
                    $(formElement).find("[name='zip']").val(address ? address['zip'] : '');
                    $(formElement).find("[name='country']").val(address ? address['country'] : '');
                    $(formElement).find("[name='customerAddressId']").val(address ? address['customerAddressId'] : '');
                } else {
                    $(formElement).find("[name='firstName']").val(address ? address['firstName'] : '');
                    $(formElement).find("[name='lastName']").val(address ? address['lastName'] : '');
                    $(formElement).find("[name='companyName']").val(address ? address['companyName'] : '');
                    $(formElement).find("[name='address1']").val(address ? address['address1'] : '');
                    $(formElement).find("[name='address2']").val(address ? address['address2'] : '');
                    $(formElement).find("[name='city']").val(address ? address['city'] : '');
                    $(formElement).find("[name='stateProvinceGeoId']").val(address ? address['stateProvinceGeoId'] : '');
                    $(formElement).find("[name='postalCode']").val(address ? address['postalCode'] : '');
                    $(formElement).find("[name='countryGeoId']").val(address ? address['countryGeoId'] : '');
                    $(formElement).find("[name='contactNumber']").val(address ? address['contactNumber'] : '');
                    $(formElement).find("[name='contactMechId']").val(address ? address['contactMechId'] : '');
                    $(formElement).find('[name=defaultShipping]').prop('checked', false);
                    $(formElement).find('[name=defaultBilling]').prop('checked', false);

                    var addressTypeRadioElement =  $(formElement).find('[name=contactMechPurposeTypeId][value=' + address['contactMechPurposeTypeId'] +']');
                    $(addressTypeRadioElement).prop('checked', true);
                    $.toggleDefaultCheckbox(addressTypeRadioElement);

                    if(address.defaultShipping == 'true') {
                        $(formElement).find('[name=defaultShipping]').prop('checked', true);
                    }

                    if(address.defaultBilling == 'true') {
                        $(formElement).find('[name=defaultBilling]').prop('checked', true);
                    }
                }
            }
        },

        showErrorNotification: function(layerElement, message) {
            if(message) {
                if(layerElement) {
                    $(layerElement).find('.jqs-address-error').html(message).removeClass('hidden').show();
                } else {
                    $('#global-address-error').html(message).removeClass('hidden').show();
                }
            } else {
                if(layerElement) {
                    $(layerElement).find('.jqs-address-error').removeClass('hidden').show();
                } else {
                    $('#global-address-error').removeClass('hidden').show();
                }
            }

        },

        showSuccessNotification: function(message) {
            $('#address-message').removeClass('hidden').html(message).show();
        },

        resetAddressForm: function() {
            var formElement = $('#add-address').find('form[name="addressForm"]');
            $(formElement).find("[name='name']").val('');
            $(formElement).find("[name='name2']").val('');
            $(formElement).find("[name='address1']").val('');
            $(formElement).find("[name='address2']").val('');
            $(formElement).find("[name='city']").val('');
            $(formElement).find("[name='state']").val('');
            $(formElement).find("[name='zip']").val('');
            $(formElement).find("[name='country']").val('');
            $(formElement).find("[name='customerAddressId']").val('');
        },

        resetMessages: function() {
            $('#address-message').addClass('hidden');
            $('#global-address-error').addClass('hidden');
            $('.jqs-address-error').hide();
            $('small.error').parent().removeClass('error');
        },

        closeAddressLayer: function(layerElement) {
            $(layerElement).foundation('reveal', 'close');
        },

        bindDefaultToggle: function() {
            $('[name=contactMechPurposeTypeId]').on('change', function() {
                $.toggleDefaultCheckbox($(this));
            });
        },

        toggleDefaultCheckbox: function(element) {
            var layerElement = $(element).closest('.jqs-parent');
            var mechPurposeType = $(element).val();
            if(mechPurposeType == 'SHIPPING_LOCATION') {
                $(layerElement).find('.jqs-default-shipping').show();
                $(layerElement).find('.jqs-default-billing').hide();
            } else if(mechPurposeType == 'BILLING_LOCATION') {
                $(layerElement).find('.jqs-default-shipping').hide();
                $(layerElement).find('.jqs-default-billing').show();
            } else if(mechPurposeType == 'SHIPPING_AND_BILLING_LOCATION') {
                $(layerElement).find('.jqs-default-shipping').show();
                $(layerElement).find('.jqs-default-billing').show();
            }
        },

        bindAddressSubmitEvent: function() {

            $('#add-address').find('.jqs-submit').on('click', function() {
                $('#add-address').find('form[name="addressForm"]').submit();
            });

            $('#add-address').find('form[name="addressForm"]')
                .on('submit', function() {

                })
                .on('invalid.fndtn.abide', function () {
                    $.showErrorNotification($('#add-address'));
                })
                .on('valid.fndtn.abide', function () {
                    $.saveAddress($('#add-address'));
                });

            $('#edit-address').find('.jqs-submit').on('click', function() {
                $('#edit-address').find('form[name="addressForm"]').submit();
            });

            $('#edit-address').find('form[name="addressForm"]')
                .on('submit', function() {

                })
                .on('invalid.fndtn.abide', function () {
                    $.showErrorNotification($('#edit-address'));
                })
                .on('valid.fndtn.abide', function () {
//                    console.log('valid!!');
                   $.saveAddress($('#edit-address'), true);
                });
        },

        formatPhoneNumber: function() {
            var pattern = '/(^[1-9][0-9]{2})([0-9]{3})([0-9]{4})/';
            $('#add-address').find('[name=contactNumber]').on('keyup', function() {
                var number = ($(this).val()).replace(/(extn\d*$|ext\d*$|ex\d*$|e\d*$|x\d*$)|[^\d]/g, function(a, b) { return b || ''});
                if(number.match(/(.*)((extn|ext|ex|e|x)[\d]+$)/)) {
                    number = number.replace(/[^\d^x]/g, '');
                }

                if(number.length > 10) {
                    if(number.match(/(^[0-9]{3})([0-9]{3})([0-9]{4})(.*)/)) {
                        number = RegExp.$1 + '-' + RegExp.$2 + '-' + RegExp.$3 + (RegExp.$4 ? ' ' + RegExp.$4 : '');
                    }
                } else if(number.length > 6) {
                    number.match(/(^[0-9]{3})([0-9]{3})([0-9]*)/);
                    number = RegExp.$1 + '-' + RegExp.$2 + '-' + RegExp.$3;
                } else if(number.length >3) {
                    number.match(/(^[0-9]{3})([0-9]*)/);
                    number = RegExp.$1 + '-' + RegExp.$2;
                }
                $(this).val(number);
            });
        },
        resizeAddressList: function() {
            var element = $(".address-list");
            var total_width = element.innerWidth();
            var min_width = parseInt(element.children(":first-child").css("min-width")) + 13;
            var total_children_per_row = parseInt(total_width / min_width);
            var width_percent = 0;

            // Adjust the width
            element.children().each(function() {
                var new_width;
                width_percent = parseInt(100 / (total_children_per_row <= element.children().length ? total_children_per_row : element.children().length));

                new_width = total_children_per_row == 1 ? "100%" : (Math.floor(total_width * (width_percent / 100)) - 11) + "px";

                $(this).css({
                    "width":new_width
                });
            });

            var max_height = 0;


            // Adjust the height;
            element.children().each(function() {
            	$(this).css({
                    "height":"none"
                });

                if ($(this).outerHeight() > max_height) {
                    max_height = $(this).outerHeight();
                }
            });

            element.children().each(function() {
                $(this).css({
                    "height":max_height + "px"
                });
            });
        }
    });

})(jQuery);

if (navigator.appVersion.indexOf('MSIE 8.') == -1) {
	$(document).foundation({
		reveal: {
			close_on_background_click: false,
			close_on_esc: false
		}
	});
}

$(document).ready(function() {
    $.resizeAddressList();
    $.bindAddressSubmitEvent();
    $.bindDefaultToggle();
    $.formatPhoneNumber();

    $("[data-reveal-id='add-address']").on("click", function() {
        $.resetMessages();
        $.resetAddressForm();
        $.populateAddressForm($('#add-address'));
    });
	$("[data-reveal-id='edit-address']").on("click", function() {
        $.resetMessages();
        $.populateAddressForm($('#edit-address'), $(this).data('address'));
	});

    $(".jqs-delete").on("click", function() {
        $.resetMessages();
        $.removeAddress($(this).data('contact-mech-id'));
    });

	$(window).on("resize", function() {
		if (!ignoreIE()) {
            $.resizeAddressList();
		}
	});

	$(".jqs-letterFilter").on("change", function() {
		var filter = $(this).val();

		var element = $(".address-list");

		element.children().each(function() {
			if (filter == "all") {
				$(this).css("display", "table-cell");
			}
			else {
				if ($(this).find(($.getAddressType() == 'addressing' ? '.jqs-name' : '.jqs-first-name')).html().substring(0, 1).toLowerCase() != filter) {
					$(this).css("display", "none");
				}
				else {
					$(this).css("display", "table-cell");
				}
			}
		});

	});


});
