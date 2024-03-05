<script>
    var reloadOnLogin = true;;
</script>

<link rel="stylesheet" href="//cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<link href="<@ofbizContentUrl>/html/css/account/account-redesign.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/account/account-home-redesign.css</@ofbizContentUrl>" rel="stylesheet" />

<#assign isLoggedIn = signedInUser?default(false)?string('true', 'false')/>
<#assign isExternalLink = (externalReferrer?default('') != '')?string('true', 'false')/>
<#if isLoggedIn == 'true'>
    <script>$.checkStatus();</script>
</#if>
<div class="content account">
	<div class="container account-home">
		<div class="row margin-bottom-xs page-header">
			<div class="small-12 medium-6 large-6 columns">
				<h2>
					Your Account
				</h2>
			</div>
			<#if isLoggedIn == 'true'>
			<div class="small-12 medium-6 large-6 columns logout_container">
				<a href="<@ofbizUrl>/logout</@ofbizUrl>"><div class="no-margin button button-non-cta round-btn">Log Out</div></a>
			</div>
			</#if>
		</div>
        <div>
            <div id="account-error" data-alert class="alert-box alert radius hidden">
                <span></span>
                <a href="#" class="close">&times;</a>
            </div>
            <div id="account-message" data-alert class="alert-box success radius hidden">
                <span></span>
                <a href="#" class="close">&times;</a>
            </div>
        </div>
		<div class="row account-body">
			<div class="main-content-left">
				<div class="padding-xxs">
					<div class="sub-content-left">
						<h3>Orders</h3>
						<i class="fa fa-shopping-cart margin-top-sm margin-bottom-xs"></i>
					</div>
					<div class="padding-left-xs sub-content-right">
						<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/open-orders</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/open-orders</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> View Open Orders</a>
						<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/all-orders</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/all-orders</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> View All Orders</a>
                        <a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/all-orders</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/all-orders</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> Reorder Center</a>
						<#--<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/return-orders</@ofbizUrl>"<#else>href="javascript:$.promptLogin();"</#if>>Return an Order</a>-->
					</div>
				</div>
				<div class="padding-xxs margin-top-xs">
					<div class="sub-content-left">
						<h3>Account</h3>
						<i class="fa fa-user margin-top-sm margin-bottom-xs"></i>
					</div>
					<div class="padding-left-xs sub-content-right">
                        <a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/addressBooks</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/addressBooks</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> Manage Address Book</a>
						<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/addresses</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/addresses</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> Manage Shipping Addresses</a>
						<a <#if isLoggedIn == 'true'>href="javascript:void(0)" data-reveal-id="change-email-password"<#else>href="javascript:$.validateAndOpen(function(){$('#change-email-password').foundation('reveal', 'open');});"</#if>><i class="fa fa-caret-right"></i> Change Password</a>
						<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/users</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/users</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> Account Users</a>
						<#--<a>Manage E-mail Subscription</a>-->
					</div>
                    <div id="change-email-password" class="reveal-modal no-padding reveal-modal-limiter" data-reveal>
                        <div>
                            <form name="change-email-password" data-bigNameValidateForm="envAccountPageChangePassword" method="POST">
                                <div class="padding-bottom-xxs margin-bottom-xxs popup-title padding-left-xxs">
                                    <h3>Change Password</h3>
                          			<a class="close-reveal-modal"><i class="fa fa-times"></i></a>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-12 large-12 columns" data-bigNameValidateId="changePasswordEmail">
                                        <input type="email" name="email" value="${emailAddress?if_exists}" readonly placeholder="E-mail Address" data-bigNameValidate="changePasswordEmail" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="E-mail Address"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-12 large-12 columns" data-bigNameValidateId="changePasswordCurrentPassword">
                                        <input type="password" name="currentPassword" class="jqs-clearable" value="" placeholder="Current Password" data-bigNameValidate="changePasswordCurrentPassword" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Current Password"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-12 large-12 columns" data-bigNameValidateId="changePasswordNewPassword">
                                        <input type="password" name="password" class="jqs-clearable" value="" placeholder="New Password" data-bigNameValidate="changePasswordNewPassword" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="New Password"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 medium-12 large-12 columns" data-bigNameValidateId="changePasswordConfirmPassword">
                                        <input type="password" name="confirmPassword" class="jqs-clearable" value="" placeholder="Confirm New Password" data-bigNameValidate="changePasswordConfirmPassword" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Confirm New Password"/>
                                    </div>
                                </div>
                                <div class="row padding-bottom-xxs">
                                    <div class="small-12 medium-12 large-12 columns">
                                        <div class="button-regular button-cta padding-left-xxs padding-right-xxs jqs-submit" data-bigNameValidateSubmit="envAccountPageChangePassword" data-bigNameValidateAction="envAccountPageChangePasswordSubmit">Submit</div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
				</div>
				<div class="padding-xxs margin-top-xs">
					<div class="sub-content-left">
						<h3>Files</h3>
						<i class="fa fa-file-text-o margin-top-sm margin-bottom-xs"></i>
					</div>
					<div class="padding-left-xs sub-content-right">
						<#--<a>Recipient Address Files</a>-->
						<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/savedDesigns</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/savedDesigns</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> Saved Designs</a>
						<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/uploadedFiles</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/uploadedFiles</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> Uploaded Image/Files</a>
						<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/proofApproval</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/proofApproval</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> Proof Approval Center</a>
						<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/uploadedDirectMailingDocuments</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/uploadedDirectMailingDocuments</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> Direct Mailing Documents</a>
						<a <#if isLoggedIn == 'true'>href="<@ofbizUrl>/directMailingJobs</@ofbizUrl>"<#else>href="javascript:$.validateAndOpen('<@ofbizUrl>/directMailingJobs</@ofbizUrl>');"</#if>><i class="fa fa-caret-right"></i> Saved Direct Mailing Jobs</a>
					</div>
				</div>
			</div>
			<div class="main-content-right">
				<#if isTrade?has_content && isTrade?c == "true">
				<div class="margin-bottom-xs text-center">
					<img src="<@ofbizContentUrl>/html/img/trade/trade-discount/trade-pro-logo.jpg</@ofbizContentUrl>" alt="Trade Pro" />
				</div>
				</#if>
				<div class="text-center loyalty-points">
					<span class="lpt-1">Loyalty Points</span>
					<span class="lpt-2"><#if isLoggedIn == 'true'>${loyaltyPoints?if_exists}<#else>--<span><a href="<@ofbizUrl>/account</@ofbizUrl>">Show Points</a></span></#if></span>
					<span class="lpt-3"><#if isLoggedIn == 'true'>${discountRate?if_exists?string["0"]}%<#else>--%</#if> Discount</span>
					<span class="lpt-4">*points accumulate from orders in the past 12 months</span>
				</div>
				<div class="margin-top-xs text-center contact-us">
					<span>Contact Us</span>
					<div class="text-left margin-top-xs margin-left-sm margin-right-sm">
						<span>
							<i class="fa fa-envelope-o padding-right-xxs"></i>
							<a href="http://support.envelopes.com/customer/portal/emails/new">E-mail Us</a>
						</span>
						<span class="margin-top-xxs phone-number">
							<i class="fa fa-phone padding-right-xxs"></i>
							<a href="tel:1-877-683-5673">877-683-5673</a>
						</span>
						<span class="margin-top-xxs">
							<i class="fa fa-comment padding-right-xxs"></i>
							<a href="javascript:void(0);" onclick="olark('api.box.expand')">Live Chat</a>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/account.js</@ofbizContentUrl>"></script>
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
    $(function() {
        $.showResetPasswordLayer();
    });

    function reloadAccountsMain() {
        document.location.href='<@ofbizUrl>/account</@ofbizUrl>'
    }
</script>
<#elseif isLoggedIn == 'false'>
    <script>
        $(function() {
            $.promptLogin();
        });
    </script>
</#if>