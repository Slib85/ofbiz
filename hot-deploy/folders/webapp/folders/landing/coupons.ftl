<link href="<@ofbizContentUrl>/html/css/services/coupons.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/folders/product/coupons.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<div class="folderCoupons coupons foldersNewLimiter paddingTop20 paddingBottom20">
	<div class="couponsHeader">
		<div class="couponsHeaderText padding20">
			<h1>Coupons &amp; Promotional Codes</h1>
			<h3>All of our special offers in one place!</h3>
			<p>We know our customers love a great deal (especially Free Shipping), so all of our coupons codes can be found here in one convenient location.</p>
		</div>
		<div class="headerImage">
			<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foldersCouponBanner?fmt=png-alpha&amp;wid=345</@ofbizScene7Url>" alt="Envelopes.com Coupon and Promotional Codes" />
		</div>
	</div>
	${screens.render("component://folders/widget/LandingScreens.xml#couponList")}
</div>