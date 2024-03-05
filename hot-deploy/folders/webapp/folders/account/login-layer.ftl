<head>
    <link href='//fonts.googleapis.com/css?family=Lato:300,400,700' rel='stylesheet' type='text/css'>
	<link href="<@ofbizContentUrl>/html/css/top.css</@ofbizContentUrl>" rel="stylesheet">
	<link href="<@ofbizContentUrl>/html/css/global.css</@ofbizContentUrl>?ts=2" rel="stylesheet" />
	<script src="<@ofbizContentUrl>/html/js/addons/jquery-2.2.0.min.js</@ofbizContentUrl>"></script>
    <style>
        #jqs-forget-password #reset-password-error, #jqs-forget-password .error-message, #jqs-forget-password.error .on-no-error {
            display: none;
        }

        #jqs-forget-password.error #reset-password-error, #jqs-forget-password.error .error-message, #jqs-forget-password .on-no-error {
            display: block;
        }
        @media only screen and (max-width: 500px) {
        	.siteJE {
        		right: 20px !important;
        	}
        }
    </style>
	<!-- Listrak Analytics – Javascript Framework -->
	<script type="text/javascript">
			var biJsHost = (("https:" == document.location.protocol) ? "https://" : "http://");
			(function (d, s, id, tid, vid) {
			var js, ljs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) return; js = d.createElement(s); js.id = id;
			js.src = biJsHost + "cdn.listrakbi.com/scripts/script.js?m=" + tid + "&v=" + vid;
			ljs.parentNode.insertBefore(js, ljs);
			})(document, 'script', 'ltkSDK', 'kjQlh1wZirX5', '1');
	</script>
</head>
<body>
	<div id="jqs-login-screen" style="<#if forgotPasswordFromCheckout?default('false') == 'true'>display: none</#if>">
		<form id="loginForm" name="loginForm" onsubmit="return false;">
			<div class="padding-left-xxs popup-title" style="background: none; background-color: #063b63;">
				<h3 style="top:6px!important">Login</h3>
			</div>
			<div class="padding-sm">
				<div class="padding-bottom-xxs">
					Please log in using your email address and password.
				</div>
				<div id="jqs-login-error" class="hidden">
					<div data-alert class="alert-box alert radius margin-bottom-xxs padding-xxs">
						Invalid e-mail address or password. Please try again.
					</div>
				</div>
				<div class="row padding-top-xxs">
					<div>
						<input name="USERNAME" type="text" value="" placeholder="E-mail Address" />
					</div>
					<div>
						<input name="PASSWORD" type="password" value="" placeholder="Password" />
					</div>
					<div class="small-4 medium-4 large-4 columns no-padding">
						<div id="login-btn" class="button-regular button-cta" style="background-color: #76cf49 !important;" onclick="$.loginAction();">Sign In</div>
					</div>
					<div class="small-8 medium-8 large-8 columns no-padding text-right">
						<a href="javascript:$.showForgotPassword()">Forgot Password?</a>
					</div>
				</div>
                <div class="padding-top-xxs" style="color: #00a4e4;">
                    <strong>Don't have an account? An account will be automatically created for you upon checkout. Your password will be emailed to you after checkout and you can change it at any time.</strong>
                </div>
			</div>
			<div class="form-group">
				<a class="btn btn-default btn-lg btn-block btn-icon icon-left google-button siteJE janrainEngage">
					Or login using <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/social_login_button_v2?fmt=png-alpha&amp;wid=170&amp;ts=1</@ofbizScene7Url>" alt="Social Login" />
					<i class="entypo-gplus"></i>
				</a>
			</div>
		</form>
	</div>
	<div id="jqs-forget-password" style="<#if forgotPasswordFromCheckout?default('false') == 'false'>display: none</#if>">
		<form id="forgetPasswordForm" name="forgetPasswordForm" onsubmit="return false;">
			<div class="padding-left-xxs popup-title" style="background: none; background-color: #063b63;">
				<h3>Forgot Password</h3>
			</div>
			<div class="padding-xs">
				<div id="reset-password-error" data-alert class="alert-box warning radius">We are sorry, unable to find the email address in our system.</div>
				<div>
					<span class="on-no-error padding-top-xs padding-bottom-xs">
					Enter your e-mail address below to have your password reset.
					You will receive an e-mail with a link to rest the password.
					</span>
					<span class="error-message padding-top-xxs">
						If you think this is a mistake, please contact customer service at <a href="mailto:service@folders.com">service@folders.com</a>.
					</span>
				</div>
				<div>
					<span class="on-no-error">Please enter your email address:</span>
				</div>
				<div class="row padding-top-xxs">
					<div>
						<input name="USERNAME" type="text" value="" placeholder="E-mail Address" />
					</div>
					<div class="text-right">
						<div class="button-regular button-cta" style="background-color: #76cf49 !important;" onclick="$.forgotPasswordAction($('#forgetPasswordForm'));">Submit</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div id="jqs-reset-password-success" style="display: none">
		<form id="forgetPasswordForm" name="forgetPasswordForm" onsubmit="return false;">
			<div class="padding-left-xxs popup-title" style="background: none; background-color: #063b63;">
				<h3>Reset Password</h3>
			</div>
			<div class="padding-xs">
				<div id="reset-password-success" data-alert class="alert-box success radius">A reset password request has been sent successfully.</div>
				<div>
					You will receive an e-mail with a link to rest the password for the given e-mail address.
				</div>
				<div class="padding-top-xxs">
					<div>
						<input name="USERNAME" type="text" value="" disabled placeholder="E-mail Address" />
					</div>
					<div class="text-right">
						<div class="button-regular button-cta" style="background-color: #76cf49 !important;" onclick="$.closeLayerAction()">Close</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<script>
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

			closeLoginDialog: function(sameOriginFlag) {
                if(sameOriginFlag) {
                    parent.closeSecureLayer();
                } else {
                    window.history.back();
                }
			},

			showResetConfirmation: function() {
				$('#jqs-forget-password').hide();
				$('#jqs-reset-password-success').toggle();
				$('#jqs-reset-password-success').find('input[name=USERNAME]').val($('#jqs-forget-password').find('input[name=USERNAME]').val());
			},

			showResetError: function() {
				$('#jqs-forget-password').addClass('error');
			},

			forgotPasswordAction: function(passwordResetForm) {
				$.ajax({
					type: 'POST',
					url: '/folders/control/requestPasswordReset',
					async: false,
					data: {
					    'userLoginId' : passwordResetForm.find('input[name=USERNAME]').val(),
                        'website': 'folders'
					},
					cache: false,
					dataType : 'json'
				}).done(function(data) {
					if(data.success) {
						$.showResetConfirmation();
					} else {
						$.showResetError();
					}
				});
			},

			loginAction: function(token) {
				var dataSet = $('#loginForm').serialize();
				if(typeof token != 'undefined') {
					dataSet = dataSet + "&token=" + token;
				}
				$.ajax({
					type: 'POST',
					url: '/${globalContext.webSiteId?default("folders")}/control/loginUser',
					data: dataSet,
					dataType: 'json',
					cache: false
				}).done(function (data) {
					if(data.success == true) {
						$('#jqs-login-error').addClass('hidden');

						var emailAddress = data.parameterMap.USERNAME;

						if (typeof emailAddress == "null" || emailAddress == null) {
							emailAddress = data.messages.message.match(/\'(.*?)\'/);
							emailAddress = emailAddress == null ? undefined : emailAddress[1];
						}
						
						// Capture Email for Listrak						
						(function(){if(typeof _ltk == 'object'){ltkCode();}else{(function (d) { if (document.addEventListener) document.addEventListener('ltkAsyncListener', d); else { e = document.documentElement; e.ltkAsyncProperty = 0; e.attachEvent('onpropertychange', function (e) { if (e.propertyName == 'ltkAsyncProperty') { d(); } }); } })(function(){ltkCode();});}function ltkCode(){_ltk_util.ready(function(){
							_ltk.SCA.CaptureEmail(emailAddress);
						})}})();
					} else {
                        var errorMessage = 'Invalid e-mail address or password. Please try again.';
                        if(data.errors && data.errors.error && data.errors.error != '') {
                            errorMessage = data.errors.error;
                        }
                        $('#jqs-login-error').removeClass('hidden').find('div').html(errorMessage);
					}
				});
				return false;
			},

            closeLayerAction: function() {
                $.ajax({
                    type: 'POST',
                    url: '<@ofbizUrl>/closeLoginLayer</@ofbizUrl>',
                    data: $('#loginForm').serialize(),
                    dataType: 'json',
                    cache: false
                }).done(function(data) {

                }).fail(function() {
                    $.closeLoginDialog();
                })
            }
		});
	})(jQuery);

	/* JANRAIN */
	(function() {
		if (typeof window.janrain !== 'object') window.janrain = {};
		if (typeof window.janrain.settings !== 'object') window.janrain.settings = {};

		<#if globalContext.webSiteId?default("envelopes") == "envelopes">
			janrain.settings.tokenUrl = 'https://www.envelopes.com/${globalContext.webSiteId?default("envelopes")}/control/getJanRainToken';
		<#else>
			//janrain.settings.tokenUrl = 'https://www.actionenvelope.com/${globalContext.webSiteId?default("envelopes")}/control/getJanRainToken';
            janrain.settings.tokenUrl = 'https://localhost/${globalContext.webSiteId?default("envelopes")}/control/getJanRainToken';
		</#if>
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
			$.loginAction(response.token);
		});
	}

	//# sourceURL=login-layer.ftl.js
</script>
