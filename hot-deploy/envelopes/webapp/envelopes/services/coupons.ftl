<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>

<link href="<@ofbizContentUrl>/html/css/services/coupons.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<div class="content coupons">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/main</@ofbizUrl>">Home</a> > Coupons &amp; Promotional Codes
	</div>
	<div class="genericPageHeader">
		<div class="section no-margin margin-top-xxs no-padding" style="background-color: #ffffff; height: initial;">
			<div class="pageHeaderText tablet-desktop-only">
				<h1 style="color:#1a345f !important;">Coupons &amp; Promotional Codes</h1>
				<h3>All of our special offers in one place!</h3>
				<p>We know our customers love a great deal (especially Free Shipping), so all of our coupons codes can be found here in one convenient location.</p>
			</div>
			<div class="headerImage tablet-desktop-only">
				<#--<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/p_s_c_coupons_desktop?wid=288&amp;fmt=jpeg" alt="Envelopes.com Coupon and Promotional Codes" />-->
				<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/002_2018_ENV_Coupon-Page-SLI-Banner-Images?fmt=png-alpha&amp;wid=407</@ofbizScene7Url>" alt="Envelopes.com Coupon and Promotional Codes" />
			</div>
			<div class="headerImage mobile-only">
				<#--<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/p_s_c_coupons_mobile?wid=200&amp;fmt=jpeg" alt="Envelopes.com Coupon and Promotional Codes" />-->
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/002_2018_ENV_Coupon-Page-SLI-Mobile-Banner-Updated?fmt=jpg</@ofbizScene7Url>" alt="Envelopes.com Coupon and Promotional Codes" />
			</div>
		</div>
	</div>
	${screens.render("component://envelopes/widget/ServicesScreens.xml#couponList")}
</div>