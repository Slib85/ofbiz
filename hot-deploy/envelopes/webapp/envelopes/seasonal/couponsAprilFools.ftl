<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>

<link href="<@ofbizContentUrl>/html/css/services/coupons.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<div class="content coupons">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/main</@ofbizUrl>">Home</a> > Coupons & Promotional Codes
	</div>
	<div class="genericPageHeader">
		<div class="section no-margin margin-top-xxs no-padding" style="background-color: #e9e9e9; height: initial;">
			<div class="pageHeaderText tablet-desktop-only">
				<h1 style="color: #009fde !important;">APRIL FOOLS!</h1>
				<h3>E-Lexa is pretty cool, but we know great deals<br />(and april fool's jokes) are even cooler!</h3>
			</div>
			<div class="headerImage tablet-desktop-only">
				<#--<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/p_s_c_coupons_desktop?wid=288&amp;fmt=jpeg" alt="Envelopes.com Coupon and Promotional Codes" />-->
				<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/env_cp-aprilFoolsDesktop?fmt=png-alpha&amp;wid=237</@ofbizScene7Url>" alt="Envelopes.com Coupon and Promotional Codes" />
			</div>
			<div class="headerImage mobile-only">
				<#--<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/p_s_c_coupons_mobile?wid=200&amp;fmt=jpeg" alt="Envelopes.com Coupon and Promotional Codes" />-->
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/env_cp-aprilFoolsMobile?fmt=jpg</@ofbizScene7Url>" alt="Envelopes.com Coupon and Promotional Codes" />
			</div>
		</div>
	</div>
	${screens.render("component://envelopes/widget/ServicesScreens.xml#couponList")}
</div>