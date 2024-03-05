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
                data: {
                    'userLoginId' : forgotPasswordForm.find('input[name=USERNAME]').val(),
                    'website': 'folders'
                },
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
            // $(layerElement).foundation('reveal', 'close');
            $('.bnRevealShadowedBackground').trigger('click');
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
        },
        resetEmailPasswordChangeLayer: function() {
            $('#change-email-password').find('.jqs-clearable').val('');
        },

        resizeSecureIframe: function(height) {
            $('#jqs-login-layer').css('height', height === undefined ? parseInt($('#secure-iframe').contents().find('#jqs-reset-password-screen').innerHeight()) + 12 : height + 'px');
        },

        closeSecureLayer: function() {
            //$('#secure-layer').foundation('reveal', 'close');
            $('.bnRevealShadowedBackground').trigger('click');
        }
    });
})(jQuery);

$(document).ready(function() {
    $.bindCustomAbideValidationEvent();
});

function changePasswordSubmit() {
	$.changeEmailOrPassword();
}
