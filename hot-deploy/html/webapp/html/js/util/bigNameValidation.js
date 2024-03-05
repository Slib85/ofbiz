function BigNameValidate(formElement, formName) {
	var self = this;
	var styleData = {
		alertColor: 'ff0000',
		errorMessagePadding: '0px 0px 10px 0px',
		errorMessageFontSize: 'inherit',
    };
    
    formElement.find('[data-bignamevalidateoptions]').each(function() {
        var options = $(this).attr('data-bignamevalidateoptions');
        if (options.match('disallowSpaces') != null) {
            $(this).off('change.disallowSpaces').on('change.disallowSpaces', function() {
                $(this).val($(this).val().replace(/\s/g, ''));
            });
        }
    });

    this.countryGeoIdValidation = function(postalCodeElement, stateElement) {
        var type = undefined;
        var postalCode = '';
        if(postalCodeElement != null) {
            postalCode = $(postalCodeElement).val();
            type = $(postalCodeElement).data('type');
        } else if(stateElement != null) {
            type = $(stateElement).data('type');
        }
        var stateType = type == 'shipping' || type == 'billing' ? $('[name=' + type +'_stateProvinceGeoId] option:selected').data('type') : undefined;

        if (typeof stateType != 'undefined') {
            if(stateType == 'AUS') {
                $('[name=' + type + '_countryGeoId]').val('AUS');
                return true;
            } else if(stateType == 'BEL') {
                $('[name=' + type +'_countryGeoId]').val('BEL');
                return true;
            } else if(stateType == 'DEU') {
                $('[name=' + type +'_countryGeoId]').val('DEU');
                return true;
            } else if(stateType == 'FRA') {
                $('[name=' + type +'_countryGeoId]').val('FRA');
                return true;
            } else if(stateType == 'HUN') {
                $('[name=' + type +'_countryGeoId]').val('HUN');
                return true;
            } else if(stateType == 'IND') {
                $('[name=' + type +'_countryGeoId]').val('IND');
                return true;
            } else if(stateType == 'IRL') {
                $('[name=' + type +'_countryGeoId]').val('IRL');
                return true;
            } else if(stateType == 'ITA') {
                $('[name=' + type +'_countryGeoId]').val('ITA');
                return true;
            } else if(stateType == 'FIN') {
                $('[name=' + type +'_countryGeoId]').val('FIN');
                return true;
            } else if(stateType == 'MEX') {
                $('[name=' + type +'_countryGeoId]').val('MEX');
                return true;
            } else if(stateType == 'NLD') {
                $('[name=' + type +'_countryGeoId]').val('NLD');
                return true;
            } else if(stateType == 'POL') {
                $('[name=' + type +'_countryGeoId]').val('POL');
                return true;
            } else if(stateType == 'PRT') {
                $('[name=' + type +'_countryGeoId]').val('PRT');
                return true;
            } else if(stateType == 'ESP') {
                $('[name=' + type +'_countryGeoId]').val('ESP');
                return true;
            } else if(stateType == 'CHE') {
                $('[name=' + type +'_countryGeoId]').val('CHE');
                return true;
            } else if(stateType == 'GBR') {
                $('[name=' + type +'_countryGeoId]').val('GBR');
                return true;
            } else if(stateType == 'SWE') {
                $('[name=' + type +'_countryGeoId]').val('SWE');
                return true;
            } else if(stateType == 'DNK') {
                $('[name=' + type +'_countryGeoId]').val('DNK');
                return true;
            } else if(stateType == 'NZL') {
                $('[name=' + type +'_countryGeoId]').val('NZL');
                return true;
            } else if((postalCode != '' && $.isCanadianPostalCode(postalCode) && (stateType == undefined || stateType == 'CAN')) || stateType == 'CAN') {
                $('[name=' + type +'_countryGeoId]').val('CAN');
                return true;
            } else if((postalCode != '' && $.isUSPostalCode(postalCode) && (stateType == undefined || stateType == 'US' || stateType == 'US-OTHERS')) || (stateType == 'US' || stateType == 'US-OTHERS')) {
                $('[name=' + type +'_countryGeoId]').val('USA');
                return true;
            }
        } else {
            return true;
        }

        if(postalCodeElement != null) {
            return false;
        } else if(stateElement != null) {
            return true;
        }

        return false;
    }

	function validateEmailAddress(email) {
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}

	function clearFormElementError(formElementInput, formElementLabel, formName, elementValidationId) {
		var validateParentElement = $('[data-bigNameValidateParent="' + elementValidationId + '"]').length > 0 ? $('[data-bigNameValidateParent="' + elementValidationId + '"]') : formElementInput;

		formElementInput.off('input.validationCheck-' + formName + '-' + elementValidationId);
		validateParentElement.removeClass('bigNameValidationBorderAlert');

		if ($(formElementLabel).length > 0) {
			formElementLabel.removeClass('bigNameValidationTextAlert');
		}

		validateParentElement.siblings('.bigNameValidationErrorMessage').remove();
	}

	$('[data-bigNameValidateSubmit="' + formName + '"]').off('click.validateSubmit').on('click.validateSubmit', function() {
		var thisForm = $('[data-bigNameValidateForm="' + $(this).attr('data-bigNameValidateSubmit') + '"]');
		var valid = true;

        thisForm.find('[bns-validationerrorheader]').remove();

        thisForm.find('[data-bigNameValidateType]').each(function() {
			var formElementInput = $(this);
			var elementValidationId = formElementInput.attr('data-bigNameValidate');
			var formElementLabel = $(formElement).find($('[data-bigNameValidateId="' + elementValidationId + '"]'));
			var formElementErrorTitle = $(formElementInput).attr('data-bigNameValidateErrorTitle');
			var formElementErrorMessage = $(formElementInput).attr('data-bignamevalidateerrormessage');
			var validationType = formElementInput.attr('data-bigNameValidateType');
			var validateParentElement = $('[data-bigNameValidateParent="' + elementValidationId + '"]').length > 0 ? $('[data-bigNameValidateParent="' + elementValidationId + '"]') : formElementInput;

			clearFormElementError(formElementInput, formElementLabel, formName, elementValidationId);

			if (!(typeof $(this).attr('data-bigNameValidateInactive') !== typeof undefined && $(this).attr('data-bigNameValidateInactive') !== false) && (typeof $(this).attr('data-bignamevalidateactive') == 'undefined' || $(this).attr('data-bignamevalidateactive') == 'true')) {
				if (validationType.match(/(?:^|\s)required(?:$|\s)/) != null && ((formElementInput.attr('type') != 'checkbox' && formElementInput.val().length <= 0) || (formElementInput.attr('type') == 'checkbox' && !formElementInput.is(':checked')))) {
					valid = false;
					validateParentElement.addClass('bigNameValidationBorderAlert');

					if ($(formElementLabel).length > 0) {
						formElementLabel.addClass('bigNameValidationTextAlert');
					}

					if (typeof formElementErrorTitle !== 'undefined' || typeof formElementErrorMessage != 'undefined') {
						validateParentElement.after(
							$('<div />').addClass('bigNameValidationErrorMessage').html((typeof formElementErrorMessage != 'undefined' ? formElementErrorMessage : formElementErrorTitle + ' is required.'))
						);
					}

					formElementInput.on('input.validationCheck-' + formName + '-' + elementValidationId, function() {
						if (formElementInput.val().length > 0) {
							clearFormElementError(formElementInput, formElementLabel, formName, elementValidationId);
						}
					});
				} else if (validationType.match(/(?:^|\s)email(?:$|\s)/) != null) {
                    if (!validateEmailAddress(formElementInput.val())) {
                        valid = false;
                        validateParentElement.addClass('bigNameValidationBorderAlert');

                        if ($(formElementLabel).length > 0) {
                            formElementLabel.addClass('bigNameValidationTextAlert');
                        }

                        if (typeof formElementErrorTitle !== 'undefined' || typeof formElementErrorMessage != 'undefined') {
                            validateParentElement.after(
                                $('<div />').addClass('bigNameValidationErrorMessage').html((typeof formElementErrorMessage != 'undefined' ? formElementErrorMessage : formElementErrorTitle + ' needs to be valid.'))
                            );
                        }

                        formElementInput.off('input.validationCheck-' + formName + '-' + elementValidationId).on('input.validationCheck-' + formName + '-' + elementValidationId, function() {
                            var emailValidation = validateEmailAddress(formElementInput.val());

                            if (emailValidation.success) {
                                clearFormElementError(formElementInput, formElementLabel, formName, elementValidationId);
                            }
                        });
                    }
				} else if (validationType.match(/(?:^|\s)selectList(?:$|\s)/) && $('#' + formElementInput.attr('data-dropdown-target')).find('.slSelected').length == 0) {
					valid = false;
					validateParentElement.addClass('bigNameValidationBorderAlert');
					
					if ($(formElementLabel).length > 0) {
						formElementLabel.addClass('bigNameValidationTextAlert');
					}

                    if (typeof formElementErrorTitle !== 'undefined' || typeof formElementErrorMessage != 'undefined') {
                        validateParentElement.after(
                            $('<div />').addClass('bigNameValidationErrorMessage').html((typeof formElementErrorMessage != 'undefined' ? formElementErrorMessage : formElementErrorTitle + ' needs to be selected.'))
                        );
                    }
					
					$('#' + formElementInput.attr('data-dropdown-target')).find('.selectListItem').on('click.validationCheck-' + formName + '-' + elementValidationId, function() {
						if ($('#' + formElementInput.attr('data-dropdown-target')).find('.slSelected').length > 0) {
							clearFormElementError(formElementInput, formElementLabel, formName, elementValidationId);
						}
					});
				} else if (validationType.match(/(?:^|\s)quoteRequestCustomization(?:$|\s)/) && (typeof $(formElementInput).attr('selected-userselection') == 'undefined' || (typeof $(formElementInput).attr('selected-userselection') != "undefined" && $(formElementInput).attr('selected-userselection') != 'true'))) {
					valid = false;
					validateParentElement.addClass('bigNameValidationBorderAlert');
					
					if ($(formElementLabel).length > 0) {
						formElementLabel.addClass('bigNameValidationTextAlert');
					}

                    if (typeof formElementErrorTitle !== 'undefined' || typeof formElementErrorMessage != 'undefined') {
                        validateParentElement.after(
                            $('<div />').addClass('bigNameValidationErrorMessage').html((typeof formElementErrorMessage != 'undefined' ? formElementErrorMessage : formElementErrorTitle + ' needs to be selected.'))
                        );
                    }

					if(formElementInput.attr('selected-userselection') == 'true') {
						clearFormElementError(formElementInput, formElementLabel, formName, elementValidationId);
					}
                } else if (validationType.match(/(?:^|\s)creditCard(?:$|\s)/) != null) {
					if (getCCTypeDetailed(formElementInput.val(), true) == '') {
                        valid = false;

                        validateParentElement.addClass('bigNameValidationBorderAlert');

                        if ($(formElementLabel).length > 0) {
                            formElementLabel.addClass('bigNameValidationTextAlert');
                        }

                        if (typeof formElementErrorTitle !== 'undefined') {
                            validateParentElement.after(
                                $('<div />').addClass('bigNameValidationErrorMessage').html('Valid Card Number is required.')
                            );
                        }

                        formElementInput.off('input.validationCheck-' + formName + '-' + elementValidationId).on('input.validationCheck-' + formName + '-' + elementValidationId, function() {
                            if (getCCTypeDetailed(formElementInput.val(), true) == '') {
                                clearFormElementError(formElementInput, formElementLabel, formName, elementValidationId);
                            }
                        });
					}
                } else if (validationType.match(/(?:^|\s)(?:postalCode|state)(?:$|\s)/) != null) {
					var type = validationType.match(/(?:^|\s)(postalCode|state)(?:$|\s)/)[1]
					if (!self.countryGeoIdValidation((type == 'postalCode' ? formElementInput : null), (type == 'state' ? formElementInput : null))) {
                        valid = false;

                        validateParentElement.addClass('bigNameValidationBorderAlert');

                        if ($(formElementLabel).length > 0) {
                            formElementLabel.addClass('bigNameValidationTextAlert');
                        }

                        if (typeof formElementErrorTitle !== 'undefined') {
                            validateParentElement.after(
                                $('<div />').addClass('bigNameValidationErrorMessage').html((type == 'postalCode' ? 'Postal Code format is incorrect.' : 'Geo location is incorrect.'))
                            );
                        }

                        formElementInput.off('input.validationCheck-' + formName + '-' + elementValidationId).on('input.validationCheck-' + formName + '-' + elementValidationId, function() {
                            if (getCCTypeDetailed(formElementInput.val(), true) == '') {
                                clearFormElementError(formElementInput, formElementLabel, formName, elementValidationId);
                            }
                        });
					}
                }
			}
		});

		var validateAction = $(this).attr('data-bigNameValidateAction');

		var validateActionInterval = setInterval(function() {
            if (valid && typeof window[validateAction] === 'function') {
                window[validateAction]();
            } else {
                thisForm.prepend(
                    $('<div />').attr('bns-validationerrorheader', '').html('Please correct the errors shown below.')
                );

                $(window).scrollTop(thisForm.offset().top);
            }
            clearInterval(validateActionInterval);
        }, 25);
	});

	formElement.find('[data-bignamevalidatetype*="phone"]').each(function() {
		var caretPosition;
		$(this).off('keydown.validatePhoneNumber').on('keydown.validatePhoneNumber', function(e) {
			caretPosition = $(this).caret();
			if ($(this).val().length > 21) {
				e.preventDefault();
			}
		});
		$(this).off('input.validatePhoneNumber').on('input.validatePhoneNumber', function(e) {
			if ($(this).val().match(/[^0-9]/g, '')) {
				$(this).val($(this).val().replace(/[^0-9]/g, ''));
				$(this).caret(caretPosition);
			}
		});
	});
}

function getCCTypeDetailed(ccNumber, validateMode) {
    var strippedCCNum = ccNumber.replace(/\D/g,'');

    var result = typeof validateMode == 'undefined' && !validateMode ? 'CCT_VISA' : '';

    //check for Visa detailed
    if (/^4[0-9]{12}(?:[0-9]{3})?$/.test(strippedCCNum)) {
        result = 'CCT_VISA';
    } else if (/^5[1-5][0-9]{14}/.test(strippedCCNum)) {
        result = 'CCT_MASTERCARD';
    } else if (/^3[47][0-9]{13}$/.test(strippedCCNum)) {
        result = 'CCT_AMERICANEXPRESS';
    } else if (/^6(?:011|5[0-9]{2})[0-9]{12}$/.test(strippedCCNum))  {
        result = 'CCT_DISCOVER';
    }

    return result;
}

function initBigNameFormValidation() {
    $('[data-bigNameValidateForm]').each(function() {
        var validateFormName = $(this).attr('data-bigNameValidateForm');
        window[validateFormName] = new BigNameValidate($(this), validateFormName);
    });
}

initBigNameFormValidation();