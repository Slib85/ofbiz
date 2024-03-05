<div class="row">
	<!-- Profile Info and Notifications -->
	<div class="col-md-6 col-sm-8 clearfix">
		<ul class="user-info pull-left pull-none-xsm">
			<!-- Profile Info -->
			<li class="profile-info dropdown"><!-- add class "pull-right" if you want to place this from right -->
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					<img src="<#if session.getAttribute("externalUserLogin")?exists && session.getAttribute("externalUserLogin").photo?has_content>${session.getAttribute("externalUserLogin").photo}<#else><@ofbizContentUrl>/html/img/admin/thumb-1@2x.png</@ofbizContentUrl></#if>" alt="" class="img-circle" width="44">
					${(session.getAttribute("userLogin").userLoginId)?if_exists} (${(session.getAttribute("userLogin").partyId)?if_exists})
				</a>
			</li>
		</ul>
	</div>
	<div class="col-md-6 col-sm-4 clearfix hidden-xs">
		<ul class="list-inline links-list pull-right">
			<li>
				<a href="<@ofbizUrl>/logout</@ofbizUrl>"> Log Out <i class="entypo-logout right"></i></a>
			</li>
		</ul>
	</div>
</div>