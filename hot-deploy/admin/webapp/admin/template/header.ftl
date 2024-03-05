<#assign currentView = requestAttributes._CURRENT_VIEW_ />
<html class="no-js css-menubar" lang="en">
<head>
	${screens.render("component://admin/widget/CommonScreens.xml#head")}
</head>
<body>
	<!-- nav -->
	${screens.render("component://admin/widget/CommonScreens.xml#leftnav")}

	<!-- Page -->
	<div class="page">
		<#if currentView != "main" && currentView != 'productionDashboard'>
			<div class="page-header">
				<h1 class="page-title">${titleProperty?if_exists}</h1>
				<div class="page-header-actions">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a href="<@ofbizUrl>/main</@ofbizUrl>">Home</a></li>
						<li class="breadcrumb-item active">${titleProperty?if_exists}</li>
					</ol>
				</div>
			</div>
		</#if>
		<div class="page-content">