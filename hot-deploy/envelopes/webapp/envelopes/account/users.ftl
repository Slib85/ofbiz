<link rel="stylesheet" href="//cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<link href="<@ofbizContentUrl>/html/css/account/account.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/account/users.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="content account">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Account Users
	</div>
	<div class="container users padding-xs">
        <div id="user-error" data-alert class="alert-box alert radius hidden">
            <span></span>
            <a href="#" class="close">&times;</a>
        </div>
        <div id="user-message" data-alert class="alert-box success radius hidden">
            <span></span>
            <a href="#" class="close">&times;</a>
        </div>
		<div class="margin-bottom-xxs add-new-user">
			<div class="button-regular button-cta padding-left-xxs padding-right-xxs" data-reveal-id="add-user" onclick="$.hideMessage();">Add New User</div>
		</div>

		<div id="add-user" class="reveal-modal no-padding reveal-modal-limiter" data-reveal>
			<div>
				<form name="add-user" data-abide="ajax">
					<div class="padding-bottom-xxs margin-bottom-xxs popup-title padding-left-xxs">
						<h3>Add New User <a class="close-reveal-modal"><i class="fa fa-times"></i></a></h3>
					</div>
					<div class="row">
						<div class="small-12 medium-12 large-12 columns">
							<input type="email" class="jqs-abide jqs-clearable" required="" name="email" value="" onblur="$().addOrUpdateBrontoEmail($(this).val(), function(success) {});" placeholder="E-mail Address" />
                            <small style="margin-top: 15px" class="jqs-email-error error">Email address is required.</small>
                        </div>
					</div>
					<div class="row">
						<div class="small-12 medium-12 large-12 columns">
							<input type="password" class="jqs-abide jqs-clearable" required="" name="password" value="" placeholder="Password" />
                            <small style="margin-top: 15px" class="jqs-password-error error">Password is required.</small>
                        </div>
					</div>
					<div class="row">
						<div class="small-12 medium-12 large-12 columns">
							<input type="password" class="jqs-abide jqs-clearable" required="" data-abide-validator="validateConfirmPassword" name="confirmPassword" value="" placeholder="Confirm Password" />
                            <small style="margin-top: 15px" class="jqs-confirm-password-error error">Confirm Password is required.</small>
                        </div>
					</div>
					<div class="row padding-bottom-xxs">
						<div class="small-12 medium-12 large-12 columns">
							<div class="button-regular button-cta padding-left-xxs padding-right-xxs jqs-submit">Submit</div>
						</div>
					</div>
				</form>
			</div>
		</div>

		<table class="table" id="userList">
			<thead>
				<tr>
					<th>
						E-mail Address
					</th>
					<th>
						Actions
					</th>
                    <th>
                        Active
                    </th>
				</tr>
			</thead>
			<tbody>
            <#list users as user>
                <tr id="${user.userLoginId}">
                    <td>
                        ${user.userLoginId}
                    </td>
                    <td>
                        <a href="#" data-reveal-id="edit-user" onclick="$('#edit-user input[name=email]').val('${user.userLoginId}');$('#edit-user input[name=active]').prop('checked', ${user.active?string('true', 'false')});">Reset Password</a>
                    </td>
                    <td>
                        <input type="checkbox" ${user.active?string('checked ', '')} name="active" data-email="${user.userLoginId}" onclick="$.toggleActive(this)" />
                    </td>
                </tr>
            </#list>
			</tbody>
		</table>

        <div id="edit-user" class="reveal-modal no-padding reveal-modal-limiter" data-reveal>
            <div class="popup-border-fade">
                <form name="edit-user" data-abide="">
                    <div class="padding-bottom-xxs margin-bottom-xxs popup-title padding-left-xxs">
                        <h3>Reset Password</h3>
                    </div>
                    <div class="row">
                        <div class="small-12 medium-12 large-12 columns">
                            <input type="text" name="email" value="" readonly placeholder="E-mail Address" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="small-12 medium-12 large-12 columns">
                            <input type="password" class="jqs-abide jqs-clearable" required="" name="password" value="" placeholder="New Password" />
                            <small style="margin-top: 15px" class="jqs-coupon-error error">Password is required.</small>
                        </div>
                    </div>
                    <div class="row">
                        <div class="small-12 medium-12 large-12 columns">
                            <input type="password" class="jqs-abide jqs-clearable" required="" data-abide-validator="validateConfirmPassword" name="confirmPassword" value="" placeholder="Confirm New Password" />
                            <small style="margin-top: 15px" class="jqs-coupon-error error">Confirm password is required.</small>
                        </div>
                    </div>
                    <div class="row padding-bottom-xxs">
                        <input type="hidden" name="updateFlag" value="true">
                        <div class="small-12 medium-12 large-12 columns">
                            <div class="button-regular button-cta padding-left-xxs padding-right-xxs jqs-submit">Submit</div>
                        </div>
                    </div>
                </form>
                <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
            </div>
        </div>
	</div>
</div>

<script type="text/javascript" src="//cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/users.js</@ofbizContentUrl>"></script>

