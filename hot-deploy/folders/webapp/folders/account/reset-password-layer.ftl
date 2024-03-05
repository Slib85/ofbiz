<head>
    <link href='//fonts.googleapis.com/css?family=Lato:300,400,700' rel='stylesheet' type='text/css'>
    <link href="<@ofbizContentUrl>/html/css/addons/foundation/foundation.css</@ofbizContentUrl>" rel="stylesheet" />
    <link href="<@ofbizContentUrl>/html/css/global.css</@ofbizContentUrl>" rel="stylesheet">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.js"></script>
    <style>
        #jqs-forget-password #reset-password-error, #jqs-forget-password .error-message, #jqs-forget-password.error .on-no-error {
            display: none;
        }

        #jqs-forget-password.error #reset-password-error, #jqs-forget-password.error .error-message, #jqs-forget-password .on-no-error {
            display: block;
        }
    </style>
</head>
<body>
<div id="jqs-reset-password-screen">
    <form id="passwordResetForm" name="passwordResetForm" onsubmit="return false;">
        <div class="padding-left-xxs popup-title">
            <h3>Reset Password</h3>
        </div>
        <div class="padding-sm">
            <div id="jqs-reset-password-error" style="display:none">
                <div data-alert class="alert-box alert radius margin-bottom-xxs padding-xxs">
                    An error occurred while resetting the password.
                </div>
            </div>
            <div class="padding-bottom-xxs">
                Please reset the password by entering the new password and confirming it.
            </div>
            <div class="row padding-top-xxs">
                <div>
                    <input name="PASSWORD" type="password" value="" placeholder="New Password"/>
                </div>
                <div>
                    <input name="PASSWORD_CONFIRM" type="password" value="" placeholder="Confirm New Password"/>
                </div>
                <div class="small-4 medium-4 large-4 columns no-padding">
                    <input type="hidden" name="USERNAME"/>
                    <input type="hidden" name="resetToken"/>
                    <div id=reset-btn" class="button-regular button-cta" onclick="$.resetPasswordAction($('#passwordResetForm'));">Reset</div>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="jqs-reset-password-token-request" style="display: none">
    <form id="forgetPasswordForm" name="forgetPasswordForm" onsubmit="return false;">
        <div class="padding-left-xxs popup-title">
            <h3>Reset Password</h3>
        </div>
        <div class="padding-xs">
            <div id="jqs-reset-password-token-error">
                <div data-alert class="alert-box alert radius">Your password reset link has been expired.</div>
            </div>
            <div>
					<div class="on-no-error padding-bottom-xxs">
                        The password reset link has been expired, please send a new password reset request by clicking the button below.
                        You will receive an e-mail with a new link to rest the password.
					</div>
					<div class="error-message padding-top-xxs padding-bottom-xxs">
						If you have difficulties resetting the password, please contact customer service at <a href="mailto:service@envelopes.com">service@envelopes.com</a>.
					</div>
            </div>
            <div class="padding-top-xxs">
                <span class="on-no-error">Your email address:</span>
            </div>
            <div class="row">
                <div>
                    <input name="USERNAME" type="text" readonly value="" placeholder="E-mail Address" />
                </div>
                <div class="text-right">
                    <div class="button-regular button-cta" onclick="$.resetPasswordRequestLinkAction($('#forgetPasswordForm').find('input[name=USERNAME]').val());">Submit</div>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="jqs-reset-password-token-confirmation" style="display: none">
    <form id="forgetPasswordForm" name="forgetPasswordForm" onsubmit="return false;">
        <div class="padding-left-xxs popup-title">
            <h3>Reset Password</h3>
        </div>
        <div class="padding-xs">
            <div id="reset-password-token-success">
                <div data-alert class="alert-box success radius">A reset password request has been sent successfully.</div>
            </div>
            <div>
                You will receive an e-mail with a link to rest the password for the given e-mail address.
            </div>
            <div class="padding-top-xxs">
                <div>
                    <input name="USERNAME" type="text" value="" disabled placeholder="E-mail Address" />
                </div>
                <div class="text-right">
                    <div class="button-regular button-cta" onclick="parent.$.closeSecureLayer()">Close</div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
<script>
    (function($) {
        var EC_TOKEN_EXPIRED = "token_expired";
        var EC_USER_NOT_FOUND = "user_not_found";
        var EC_PASSWORD_EMPTY = "password_empty";
        var EC_CONFIRM_PASSWORD_MISMATCH = "confirm_password_mismatch";
        var EC_ERROR_RESETTING_PASSWORD = "error_resetting_password";
        $('#passwordResetForm input').on('keypress', function (e) {
            if (e.which == 13) {
                $('#reset-btn').trigger('click');
                return false;
            }
        });

        $('#passwordResetForm input[name=USERNAME]').val(window.parent.userName);
        $('#forgetPasswordForm input[name=USERNAME]').val(window.parent.userName);
        $('#passwordResetForm input[name=resetToken]').val(window.parent.resetToken);
        $('#passwordResetForm input[name=PASSWORD]').focus();
        $.extend({
            showResetError: function() {
                $('#jqs-forget-password').addClass('error');
            },

            resetPasswordAction: function(resetPasswordForm) {
                $.ajax({
                    type: 'POST',
                    url: '<@ofbizUrl>/updatePassword</@ofbizUrl>',
                    async: false,
                    data: $(resetPasswordForm).serialize(),
                    cache: false,
                    dataType : 'json'
                }).done(function(data) {
                    if(data.success) {
                        window.parent.reloadAccountsMain();
                    } else {
                        if(data.error == EC_TOKEN_EXPIRED)  {
                            $.showLinkExpiredMessage();
                        } else if(data.error == EC_PASSWORD_EMPTY)  {
                            $.showErrorMessage($('#jqs-reset-password-error'),'Please enter a password and try again.');
                        } else if(data.error == EC_CONFIRM_PASSWORD_MISMATCH)  {
                            $.showErrorMessage($('#jqs-reset-password-error'),'Password and Confirm Password does not match. Please try again.');
                        } else {
                            $.showErrorMessage($('#jqs-reset-password-error'), 'An error occurred while resetting the password. Please try again.');
                        }
                    }
                });
            },

            resetPasswordRequestLinkAction: function(userName) {
                $.ajax({
                    type: 'POST',
                    url: '<@ofbizUrl>/requestPasswordResetSecure</@ofbizUrl>',
                    async: false,
                    data: { 'userLoginId' : userName } ,
                    cache: false,
                    dataType : 'json'
                }).done(function(data) {
                    if(data.success) {
                        $.showResetLinkRequestConfirmation();
                    } else {
                        $.showErrorMessage($('#jqs-reset-password-token-error'), 'An error occurred while sending a new password reset link.');
                    }
                });
            },

            showLinkExpiredMessage: function() {
                $('#jqs-reset-password-screen').hide();
                $('#jqs-reset-password-token-request').show();
                parent.$.resizeSecureIframe(405);
            },

            showResetLinkRequestConfirmation: function() {
                $('#jqs-reset-password-token-request').hide();
                $('#jqs-reset-password-token-confirmation').show();
                parent.$.resizeSecureIframe();
            },

            showErrorMessage: function(layerElement, message) {
                $(layerElement).show().find('div').html(message);
                parent.$.resizeSecureIframe();
            }
        });
    })(jQuery);
    //# sourceURL=reset-password-layer.js
</script>
