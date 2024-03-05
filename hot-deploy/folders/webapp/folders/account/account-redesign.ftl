<script>
    var reloadOnLogin = true;;
</script>

<#assign isLoggedIn = signedInUser?default(false)?string('true', 'false')/>
<#assign isExternalLink = (externalReferrer?default('') != '')?string('true', 'false')/>
<#if isLoggedIn == 'true'>
<script>$.checkStatus();</script>
</#if>
<link href="<@ofbizContentUrl>/html/css/folders/account/account-redesign.css</@ofbizContentUrl>" rel="stylesheet">

<div class="foldersContainer foldersContainerLimiter account">
	<div class="foldersContainerContent">
		<div class="accountHeader">
			<div class="accountHeaderContent">
				<h1 class="ftc-blue">Your Account</h1>
			</div>
			<#if isLoggedIn == 'true'>
			<div class="FOL_UI_LogOut_CTA">
				<a href="<@ofbizUrl>/logout</@ofbizUrl>" class="foldersButton fbc-darkGray noMargin">Log Out</a>
			</div>
			</#if>
		</div>
		<div class="accountContent">
            <div class="FOL_Grid_Wrap">
                <div class="FOL_Grid_Box">
                    <div class="accountInfoHeader">
						<h2>Orders</h2>
						<i class="fa fa-shopping-cart"></i>
					</div>
					<div class="accountInfoBody">
						<div>
							<a href="<#if isLoggedIn == 'true'><@ofbizUrl>/openOrders</@ofbizUrl><#else>javascript:$.validateAndOpen('<@ofbizUrl>/openOrders</@ofbizUrl>');</#if>">View Open Orders</a>
						</div>
						<div>
                        	<a href="<#if isLoggedIn == 'true'><@ofbizUrl>/orderList</@ofbizUrl><#else>javascript:$.validateAndOpen('<@ofbizUrl>/orderList</@ofbizUrl>');</#if>">View All Orders</a>
                        </div>
						<div>
                        	<a href="<#if isLoggedIn == 'true'><@ofbizUrl>/orderList</@ofbizUrl><#else>javascript:$.validateAndOpen('<@ofbizUrl>/orderList</@ofbizUrl>');</#if>">Reorder Center</a>
                        </div>
					</div>    
                </div>
                <div class="FOL_Grid_Box">
                    <div class="accountInfoHeader">
						<h2>Account</h2>
						<i class="fa fa-user"></i>
					</div>
					<div class="accountInfoBody">
						<#--<div>
                        	<a href="<#if isLoggedIn == 'true'><@ofbizUrl>/addresses</@ofbizUrl><#else>javascript:$.validateAndOpen('<@ofbizUrl>/addresses</@ofbizUrl>');</#if>">Manage Shipping Addresses</a>
						</div>-->
						<div>
                        	<a href="<#if isLoggedIn == 'true'><@ofbizUrl>/myQuotes</@ofbizUrl><#else>javascript:$.validateAndOpen('<@ofbizUrl>/myQuotes</@ofbizUrl>');</#if>">My Quotes</a>
                        </div>
						<div>
							<a <#if isLoggedIn == 'true'>href="javascript:void(0)" data-bnreveal="change-email-password"<#else>href="javascript:$.validateAndOpen(function(){$('#change-email-password').foundation('reveal', 'open');});"</#if>>Change Password</a>
						</div>
						<div>
                        	<a href="<#if isLoggedIn == 'true'><@ofbizUrl>/users</@ofbizUrl><#else>javascript:$.validateAndOpen('<@ofbizUrl>/users</@ofbizUrl>');</#if>">Account Users</a>
						</div>
					</div>
                </div>
                <div class="FOL_Grid_Box">
                    <div class="accountInfoHeader">
                        <h2>Files</h2>
                        <i class="fa fa-file-text-o"></i>
                    </div>
                    <div class="accountInfoBody">
                        <div>
                            <a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/uploadedFiles</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/uploadedFiles</@ofbizUrl>');"</#if>>Uploaded Image/Files</a>
                        </div>
                        <div>
                            <a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/proofApproval</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/proofApproval</@ofbizUrl>');"</#if>>Proof Approval Center</a>
                        </div>
                        <!--<div>
                            <a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/uploadedFiles</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/uploadedFiles</@ofbizUrl>');"</#if>>Uploaded Image/Files</a>
                        </div>-->
                    </div>
                </div>
                <div class="FOL_Grid_Box">
                    <div class="accountInfoHeader">
                        <h2 class="ftc-blue textCenter">Contact Us</h2>
                    </div>
                    <div class="accountInfoBody">
                        <div>
                            <a href="tel:1-800-296-4321" class="marginTop20"><i class="fa fa-phone marginRight10"></i> 800-296-4321</a>
                        </div>
                        <div>
					        <a href="#" onclick="return false;"><i class="fa fa-comment marginRight10"></i> Live Chat</a>
                        </div>
                    </div>
                </div>
            </div>
		</div>
	</div>
</div>
<div id="change-email-password" class="bnRevealContainer">
	<div>
		<form name="change-email-password" data-bigNameValidateForm="changePassword">
			<div class="bnRevealHeader fbc-blue">
				<h3>Change Password</h3>
				<i class="fa fa-times jqs-bnRevealClose"></i>
			</div>
			<div class="bnRevealBody">
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="email" name="email" class="jqs-abide" value="${emailAddress?if_exists}" readonly placeholder="E-mail Address" data-bigNameValidate="email" data-bigNameValidateType="required email" data-bigNameValidateErrorTitle="E-Mail Address" />
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="password" name="currentPassword" class="jqs-abide jqs-clearable" value="" placeholder="Current Password" data-bigNameValidate="currentPassword" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Current Password" />
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="password" name="password" class="jqs-abide jqs-clearable" value="" placeholder="New Password" data-bigNameValidate="password" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="New Password" />
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="password" name="confirmPassword" class="jqs-abide jqs-clearable" value="" placeholder="Confirm New Password" data-bigNameValidate="confirmPassword" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Confirm New Password" />
					</div>
				</div>
				<div class="row padding-bottom-xxs">
					<div class="small-12 medium-12 large-12 columns">
						<div class="foldersButton buttonGreen" data-bigNameValidateSubmit="changePassword" data-bigNameValidateAction="changePasswordSubmit">Submit</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div id="forgotPassword" class="bnRevealContainer">
    <div id="jqs-reset-password-screen">
        <form id="passwordResetForm" name="passwordResetForm" onsubmit="return false;">
            <div class="bnRevealHeader fbc-blue">
                <h3>Reset Password</h3>
                <i class="fa fa-times jqs-bnRevealClose"></i>
            </div>
            <div class="bnRevealBody">
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
                        <div id="reset-btn" class="foldersButton buttonGreen" onclick="$.resetPasswordAction($('#passwordResetForm'));">Reset</div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/folders/account/account.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>

<#if resetPassword?default("false") == "true">

<#--###################################################################-->
<#--###################################################################-->
<#--####Hack to avoid freemarker encoding of javascript variables######-->
<#--###################################################################-->
<#--###################################################################-->
<span id="jqs-pwr-user-name" class="hidden">${requestParameters.userName?default('')}</span>
<script>var userName = $('#jqs-pwr-user-name').text();</script>
<span id="jqs-pwr-reset-token" class="hidden">${requestParameters.resetToken?default('')}</span>
<script>var resetToken = $('#jqs-pwr-reset-token').text();</script>
<#--###################################################################-->
<#--###################################################################-->
<#--###################################################################-->
<#--###################################################################-->
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
<script>
    $(document).ready(function() {
        // $.showResetPasswordLayer();
        bnRevealLoad('forgotPassword');
    });

    function reloadAccountsMain() {
        document.location.href='<@ofbizUrl>/account</@ofbizUrl>'
    }
</script>
<#elseif isLoggedIn == 'false'>
<script>
    $(document).ready(function() {
        $.promptLogin();
    });
</script>
</#if>