(function($) {
    $('#loginForm input').on('keypress', function (e) {
        if (e.which == 13) {
            $('#login-btn').trigger('click');
            return false;
        }
    });
    $('#loginForm input[name=USERNAME]').focus();
    $.extend({
        showForgotPassword: function() {
            $('#jqs-forget-password').removeClass('error');
            $('#jqs-login-screen').hide();
            $('#jqs-forget-password').show();
            $('#jqs-forget-password').find('input[name=USERNAME]').val($('#jqs-login-screen').find('input[name=USERNAME]').val());
        },

        closeForgotPassword: function() {
            $('#jqs-login-screen').show();
            $('#jqs-forget-password').hide();
            $('#jqs-login-screen').find('input[name=USERNAME]').val($('#jqs-forget-password').find('input[name=USERNAME]').val());
        },

        closeLoginDialog: function() {
            //Deprecated - no longer used
            //window.history.back();
        },

        showResetRequestConfirmation: function() {
            $('#jqs-forget-password').hide();
            $('#jqs-reset-password-success').toggle();
            $('#jqs-reset-password-success').find('input[name=USERNAME]').val($('#jqs-forget-password').find('input[name=USERNAME]').val());
        },

        showResetRequestError: function() {
            $('#jqs-forget-password').addClass('error');
        },

        forgotPasswordAction: function(forgotPasswordForm) {
            $.ajax({
                type: 'POST',
                url: '/' + websiteId + '/control/requestPasswordReset',
                async: false,
                data: { 'userLoginId' : forgotPasswordForm.find('input[name=USERNAME]').val() } ,
                cache: false,
                dataType : 'json'
            }).done(function(data) {
                if(data.success) {
                    $.showResetRequestConfirmation();
                } else {
                    $.showResetRequestError();
                }
            });
        },

        showResetConfirmation: function(userName) {
            $('<form method="post" action="/' + websiteId + '/control/account"><input name="userName" value="' + userName + '"></form>').submit();
        },

        showResetError: function() {

        },

        resetPassword: function(resetPasswordForm) {
            alert('Deprecated');
            //Deprecated - no longer used
        },

        changeEmailOrPassword: function() {
            $.ajax({
                type: 'POST',
                url: '/' + websiteId + '/control/changeEmailOrPassword',
                data: $('form[name=change-email-password]').serialize(),
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if(data.success) {
                    $.showMessage(data.message, 'success');
                } else {
                    $.showMessage(data.errorMessage, 'error');
                }
                $.closeUserLayer('#change-email-password');
            });
        },

        closeUserLayer: function(layerElement) {
            $(layerElement).foundation('reveal', 'close');
            $.resetUserLayer(layerElement);
        },
        resetUserLayer: function(layerElement) {
            $(layerElement).find('.jqs-clearable').val('');
        },
        showMessage: function(message, type) {
            $.hideMessage();
            $(type == 'success' ? '#account-message' : '#account-error').removeClass('hidden').find('span').html(message);
        },
        hideMessage: function() {
            $('.alert-box').addClass('hidden');
        },

        bindFormSubmitEvent: function() {
            $('#change-email-password').find('.jqs-submit').off('click.submitPassword').on('click.submitPassword', function () {
                var bigNameValidateErrorMessageCheck = $('#change-email-password').find('.bigNameValidationErrorMessage').length == 0;
                var confirmNewPasswordCheck = $('input[name="password"]').val() === $('input[name="confirmPassword"]').val();

                if(bigNameValidateErrorMessageCheck){
                    if(confirmNewPasswordCheck){
                        $('#change-email-password').find('form[name="change-email-password"]').submit();
                        $.changeEmailOrPassword();
                    } else {
                        $('input[name="confirmPassword"]').after('<div class="bigNameValidationErrorMessage">Password and Confirm Password should match</div>');
                    }
                }    
            });
        },
        bindCustomAbideValidationEvent: function() {
            $('.jqs-abide').each(function() {
                var validationMessageElement = $(this).next();
                var defaultValidationMessage = validationMessageElement.html();
                var invalidMessage = 'Valid ' + defaultValidationMessage;
                $(this).on('blur', function(e) {
                    if($(this).val() != '' && $(this).data('abide-validator') === undefined) {
                        validationMessageElement.html(invalidMessage);
                    } else if($(this).val() == '') {
                        validationMessageElement.html(defaultValidationMessage);
                    }
                }).on('focus', function(e) {
                    $(this).parent().removeClass('error');
                });

            });
			if (navigator.appVersion.indexOf('MSIE 8.') == -1) {
				$(document).foundation({
					abide: {
						live_validate : false,
						focus_on_invalid : false,
						validate_on_blur : false,
						validators: {
							validateConfirmPassword: function (el, required, parent) {
								var password = $(el).closest('form').find('input[name=password]').val();
								if(password != $(el).val()) {
									$(el).next().html('Password and Confirm Password should match');
									return false;
								}
								return true;
							}
						}
					}
				});
            }

        },
        resetEmailPasswordChangeLayer: function() {
            $('#change-email-password').find('.jqs-clearable').val('');
        },

        resizeSecureIframe: function(height) {
            $('#jqs-login-layer').css('height', height === undefined ? parseInt($('#secure-iframe').contents().find('#jqs-reset-password-screen').innerHeight()) + 12 : height + 'px');
        },

        closeSecureLayer: function() {
            $('#secure-layer').foundation('reveal', 'close');
        }
    });
})(jQuery);

$(document).ready(function() {
    $.bindFormSubmitEvent();
    $.bindCustomAbideValidationEvent();
});