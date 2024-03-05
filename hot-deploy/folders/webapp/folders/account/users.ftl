<link rel="stylesheet" href="//cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css" />
<link href="<@ofbizContentUrl>/html/css/account/users.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="foldersContainer foldersNewLimiter paddingTop30">
	<div class="foldersBreadcrumbs">
		<a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Account Users
	</div>
    <div id="user-error" data-alert class="alert-box alert radius hidden">
        <span></span>
        <a href="#" class="close">&times;</a>
    </div>
    <div id="user-message" data-alert class="alert-box success radius hidden">
        <span></span>
        <a href="#" class="close">&times;</a>
    </div>
	<div class="foldersContainerContent">
		<div class="foldersButton buttonGreen" data-bnreveal="add-user" onclick="$.hideMessage();">Add New User</div>
		<table class="table" id="userList">
			<thead>
				<tr>
					<th class="textLeft ftc-blue">
						E-mail Address
					</th>
					<th class="textLeft ftc-blue">
						Actions
					</th>
					<th class="textLeft ftc-blue">
						Active
					</th>
				</tr>
			</thead>
			<tbody>
			<#list users as user>
				<tr id="${user.userLoginId}">
					<td class="ftc-blue">
						${user.userLoginId}
					</td>
					<td>
						<a href="#" data-bnreveal="edit-user" onclick="$('#edit-user input[name=email]').val('${user.userLoginId}');$('#edit-user input[name=active]').prop('checked', ${user.active?string('true', 'false')});">Reset Password</a>
					</td>
					<td>
						<input type="checkbox" ${user.active?string('checked ', '')} name="active" data-email="${user.userLoginId}" onclick="$.toggleActive(this)" />
					</td>
				</tr>
			</#list>
			</tbody>
		</table>
	</div>
</div>
<div id="add-user" class="bnRevealContainer">
	<div>
		<form name="add-user" data-bigNameValidateForm="addUser">
			<div class="bnRevealHeader fbc-blue">
				<h3>Add New User</h3>
				<i class="fa fa-times jqs-bnRevealClose"></i>
			</div>
			<div class="bnRevealBody">
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="email" class="jqs-abide jqs-clearable" name="email" value="" onblur="$().addOrUpdateBrontoEmail($(this).val(), function(success) {});" placeholder="E-mail Address" data-bigNameValidate="email" data-bigNameValidateType="required email" data-bigNameValidateErrorTitle="E-Mail Address" />
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="password" class="jqs-abide jqs-clearable" name="password" value="" placeholder="Password" data-bigNameValidate="password" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Password" />
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="password" class="jqs-abide jqs-clearable" name="confirmPassword" value="" placeholder="Confirm Password" data-bigNameValidate="confirmPassword" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Confirm Password" />
					</div>
				</div>
				<div class="row padding-bottom-xxs">
					<div class="small-12 medium-12 large-12 columns">
						<div class="foldersButton buttonGreen" data-bigNameValidateSubmit="addUser" data-bigNameValidateAction="addUserSubmit">Submit</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div id="edit-user" class="bnRevealContainer">
	<div class="popup-border-fade">
		<form name="edit-user" data-bigNameValidateForm="resetPassword">
			<div class="bnRevealHeader fbc-blue">
				<h3>Reset Password</h3>
				<i class="fa fa-times jqs-bnRevealClose"></i>
			</div>
			<div class="bnRevealBody">
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="text" name="email" value="" readonly placeholder="E-mail Address" data-bigNameValidate="email" data-bigNameValidateType="required email" data-bigNameValidateErrorTitle="E-Mail Address" />
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="password" class="jqs-abide jqs-clearable" name="password" value="" placeholder="New Password" data-bigNameValidate="password" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="New Password" />
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-12 large-12 columns">
						<input type="password" class="jqs-abide jqs-clearable" name="confirmPassword" value="" placeholder="Confirm New Password" data-bigNameValidate="confirmPassword" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Confirm New Password" />
					</div>
				</div>
				<div class="row padding-bottom-xxs">
					<input type="hidden" name="updateFlag" value="true">
					<div class="small-12 medium-12 large-12 columns">
						<div class="foldersButton buttonGreen" data-bigNameValidateSubmit="resetPassword" data-bigNameValidateAction="resetPasswordSubmit">Submit</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript" src="//cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/folders/account/users.js</@ofbizContentUrl>"></script>