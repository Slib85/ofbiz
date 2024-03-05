<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/util/bigNameCarousel.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/main.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
<div class="container main content no-padding">
	<div class="banner">
		<!-- START HOMEPAGE BANNER -->
		<#if (currentTimestamp?default(now?datetime) gte "2020-11-02 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-02 23:59:59.000"?datetime)>
			<div bns-carousel="desktopHPImage" class="desktopBanners text-center relative updatedEnvHpBanner tablet-desktop-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_MetallicEnvelopes" data-ga-promo-name="Metallic Envelopes" data-ga-promo-creative="hp_banner_MetallicEnvelopes" data-ga-promo-position="HP Banner Desktop 1" title="Metallic Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-15-off-metallics-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Desktop 2" title="The Holiday Shop">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-name="Mailers &amp; Shipping Envelopes" data-ga-promo-creative="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-position="HP Banner Desktop 3" title="Mailers &amp; Shipping Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Limited-Time-10-Off-Mailers-Desktop-HP-Banner?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="10% Off Mailers &amp; Shipping Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="3" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Desktop 4" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
				</div>
			</div>
			<div bns-carousel="mobileHPImage" class="mobile-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_MetallicEnvelopes" data-ga-promo-name="Metallic Envelopes" data-ga-promo-creative="hp_banner_MetallicEnvelopes" data-ga-promo-position="HP Banner Mobile 1" title="Metallic Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-15-off-metallics-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Mobile 2" title="The Holiday Shop">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-name="Mailers &amp; Shipping Envelopes" data-ga-promo-creative="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-position="HP Banner Mobile 3" title="Mailers &amp; Shipping Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Limited-Time-10-Off-Mailers-Mobile-HP-Banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="10% Off Mailers &amp; Shipping Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="3" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Mobile 4" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
				</div>
			</div>
		<#elseif (currentTimestamp?default(now?datetime) gte "2020-11-03 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-04 23:59:59.000"?datetime)>
			<div bns-carousel="desktopHPImage" class="desktopBanners text-center relative updatedEnvHpBanner tablet-desktop-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_EarlyBlackFridaySale" data-ga-promo-name="EarlyBlackFridaySale" data-ga-promo-creative="hp_banner_EarlyBlackFridaySale" data-ga-promo-position="HP Banner Desktop 1" title="Early Black Friday Sale | Envelopes.com">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/early-black-friday-sale-20-percent-off-and-free-shipping-on-300-dollars-plus-desktop-banner?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Early Black Friday Sale | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_MetallicEnvelopes" data-ga-promo-name="Metallic Envelopes" data-ga-promo-creative="hp_banner_MetallicEnvelopes" data-ga-promo-position="HP Banner Desktop 2" title="Metallic Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-15-off-metallics-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Desktop 3" title="The Holiday Shop">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>" bns-image_index="3" data-ga-promo data-ga-promo-id="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-name="Mailers &amp; Shipping Envelopes" data-ga-promo-creative="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-position="HP Banner Desktop 4" title="Mailers &amp; Shipping Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Limited-Time-10-Off-Mailers-Desktop-HP-Banner?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="10% Off Mailers &amp; Shipping Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="4" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Desktop 5" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
				</div>
			</div>
			<div bns-carousel="mobileHPImage" class="mobile-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_EarlyBlackFridaySale" data-ga-promo-name="EarlyBlackFridaySale" data-ga-promo-creative="hp_banner_EarlyBlackFridaySale" data-ga-promo-position="HP Banner Mobile 1" title="Early Black Friday Sale | Envelopes.com">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/early-black-friday-sale-20-percent-off-and-free-shipping-on-300-dollars-plus-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_MetallicEnvelopes" data-ga-promo-name="Metallic Envelopes" data-ga-promo-creative="hp_banner_MetallicEnvelopes" data-ga-promo-position="HP Banner Mobile 2" title="Metallic Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-15-off-metallics-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Mobile 3" title="The Holiday Shop">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>" bns-image_index="3" data-ga-promo data-ga-promo-id="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-name="Mailers &amp; Shipping Envelopes" data-ga-promo-creative="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-position="HP Banner Mobile 4" title="Mailers &amp; Shipping Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Limited-Time-10-Off-Mailers-Mobile-HP-Banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="10% Off Mailers &amp; Shipping Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="4" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Mobile 5" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
				</div>
			</div>
		<#elseif (currentTimestamp?default(now?datetime) gte "2020-11-05 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-06 23:59:59.000"?datetime)>
			<div bns-carousel="desktopHPImage" class="desktopBanners text-center relative updatedEnvHpBanner tablet-desktop-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_EarlyBlackFridaySale" data-ga-promo-name="EarlyBlackFridaySale" data-ga-promo-creative="hp_banner_EarlyBlackFridaySale" data-ga-promo-position="HP Banner Desktop 1" title="Early Black Friday Sale | Envelopes.com">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/early-black-friday-sale-20-percent-off-and-free-shipping-on-300-dollars-plus-desktop-banner?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Early Black Friday Sale | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_MetallicEnvelopes" data-ga-promo-name="Metallic Envelopes" data-ga-promo-creative="hp_banner_MetallicEnvelopes" data-ga-promo-position="HP Banner Desktop 1" title="Metallic Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-15-off-metallics-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Desktop 2" title="The Holiday Shop">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>" bns-image_index="3" data-ga-promo data-ga-promo-id="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-name="Mailers &amp; Shipping Envelopes" data-ga-promo-creative="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-position="HP Banner Desktop 3" title="Mailers &amp; Shipping Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Limited-Time-10-Off-Mailers-Desktop-HP-Banner?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="10% Off Mailers &amp; Shipping Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="4" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Desktop 4" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
				</div>
			</div>
			<div bns-carousel="mobileHPImage" class="mobile-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_EarlyBlackFridaySale" data-ga-promo-name="EarlyBlackFridaySale" data-ga-promo-creative="hp_banner_EarlyBlackFridaySale" data-ga-promo-position="HP Banner Mobile 1" title="Early Black Friday Sale | Envelopes.com">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/early-black-friday-sale-20-percent-off-and-free-shipping-on-300-dollars-plus-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_MetallicEnvelopes" data-ga-promo-name="Metallic Envelopes" data-ga-promo-creative="hp_banner_MetallicEnvelopes" data-ga-promo-position="HP Banner Mobile 2" title="Metallic Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-15-off-metallics-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Mobile 3" title="The Holiday Shop">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>" bns-image_index="3" data-ga-promo data-ga-promo-id="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-name="Mailers &amp; Shipping Envelopes" data-ga-promo-creative="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-position="HP Banner Mobile 4" title="Mailers &amp; Shipping Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Limited-Time-10-Off-Mailers-Mobile-HP-Banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="10% Off Mailers &amp; Shipping Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="4" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Mobile 5" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
				</div>
			</div>
		<#elseif (currentTimestamp?default(now?datetime) gte "2020-11-07 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-08 23:59:59.000"?datetime)>
			<div bns-carousel="desktopHPImage" class="desktopBanners text-center relative updatedEnvHpBanner tablet-desktop-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_EarlyBlackFridaySale" data-ga-promo-name="EarlyBlackFridaySale" data-ga-promo-creative="hp_banner_EarlyBlackFridaySale" data-ga-promo-position="HP Banner Desktop 1" title="Early Black Friday Sale | Envelopes.com">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/early-black-friday-sale-20-percent-off-and-free-shipping-on-300-dollars-plus-desktop-banner?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Early Black Friday Sale | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Desktop 2" title="The Holiday Shop">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-name="Mailers &amp; Shipping Envelopes" data-ga-promo-creative="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-position="HP Banner Desktop 3" title="Mailers &amp; Shipping Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Limited-Time-10-Off-Mailers-Desktop-HP-Banner?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="10% Off Mailers &amp; Shipping Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="3" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Desktop 4" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
				</div>
			</div>
			<div bns-carousel="mobileHPImage" class="mobile-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_EarlyBlackFridaySale" data-ga-promo-name="EarlyBlackFridaySale" data-ga-promo-creative="hp_banner_EarlyBlackFridaySale" data-ga-promo-position="HP Banner Mobile 1" title="Early Black Friday Sale | Envelopes.com">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/early-black-friday-sale-20-percent-off-and-free-shipping-on-300-dollars-plus-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Mobile 2" title="The Holiday Shop">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-name="Mailers &amp; Shipping Envelopes" data-ga-promo-creative="hp_banner_Mailers&ShippingEnvelopes" data-ga-promo-position="HP Banner Mobile 3" title="Mailers &amp; Shipping Envelopes">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Limited-Time-10-Off-Mailers-Mobile-HP-Banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="10% Off Mailers &amp; Shipping Envelopes | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="3" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Mobile 4" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
				</div>
			</div>
		<#else>
			<div bns-carousel="desktopHPImage" class="desktopBanners text-center relative updatedEnvHpBanner tablet-desktop-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Desktop 1">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Mobile 2" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_hot_deals" data-ga-promo-name="Hot Deals" data-ga-promo-creative="hp_banner_hot_deals" data-ga-promo-position="HP Banner Spot 3 Desktop">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hp-hotDealsBanner-3?fmt=png-alpha&amp;wid=3000&amp;ts=3</@ofbizScene7Url>" alt="Hot Deals | Envelopes.com" title="Hot Deals | Envelopes.com" />
					</a>
					<div bns-image_index="3">
						<img usemap="#customPrintingAndNewArrivalsImageDesktpop" src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-printing-new-arrivals-desktop-banner-3?fmt=png-alpha&amp;wid=3000&amp;ts=2</@ofbizScene7Url>" alt="Custom Printing | New Arrivals" title="Custom Printing | New Arrivals" />
					</div>
				</div>
			</div>
			<div bns-carousel="mobileHPImage" class="mobile-only">
				<div bns-carouse-image-container>
					<a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" bns-image_active bns-image_index="0" data-ga-promo data-ga-promo-id="hp_banner_holidayshop" data-ga-promo-name="The Holiday Shop" data-ga-promo-creative="hp_banner_holidayshop" data-ga-promo-position="HP Banner Mobile 1" title="The Holiday Shop | Envelopes.com">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" bns-image_index="1" data-ga-promo data-ga-promo-id="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-name="Custom Printed Bubble Mailers" data-ga-promo-creative="hp_banner_CustomPrintedBubbleMailers" data-ga-promo-position="HP Banner Mobile 2" title="Custom Printed Bubble Mailers">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-bubble-mailers-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Custom Printed Bubble Mailers | Envelopes.com" />
					</a>
					<a href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>" bns-image_index="2" data-ga-promo data-ga-promo-id="hp_banner_hot_deals" data-ga-promo-name="Hot Deals" data-ga-promo-creative="hp_banner_hot_deals" data-ga-promo-position="HP Banner Spot 3 Mobile">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/e-hp-20200519-mobile?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Hot Deals | Envelopes.com" title="Hot Deals | Envelopes.com" />
					</a>
					<div bns-image_index="3">
						<img usemap="#customPrintingAndNewArrivalsImageMobile" src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-printing-new-arrivals-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=1</@ofbizScene7Url>" alt="Custom Printing | New Arrivals" title="Custom Printing | New Arrivals" />
					</div>
				</div>
			</div>
		</#if>
		<map name="customPrintingAndNewArrivalsImageDesktpop">
			<area data-ga-promo data-ga-promo-id="hp_banner_custom_printing_and_new_arrivals" data-ga-promo-name="Custom Printing and New Arrivals Banner" data-ga-promo-creative="hp_banner_custom_printing_and_new_arrivals" data-ga-promo-position="HP Banner Desktop 3 - Custom Printing" alt="Custom Printing" href="<@ofbizUrl>/designoptions</@ofbizUrl>" coords="0,0,1500,664" shape="rect" />
			<area data-ga-promo data-ga-promo-id="hp_banner_custom_printing_and_new_arrivals" data-ga-promo-name="Custom Printing and New Arrivals Banner" data-ga-promo-creative="hp_banner_custom_printing_and_new_arrivals" data-ga-promo-position="HP Banner Desktop 3 - New Arrivals" alt="New Arrivals" href="<@ofbizUrl>/new-arrivals</@ofbizUrl>" coords="1501,0,3000,664" shape="rect" />
		</map>
		<map name="customPrintingAndNewArrivalsImageMobile">
			<area data-ga-promo data-ga-promo-id="hp_banner_custom_printing_and_new_arrivals" data-ga-promo-name="Custom Printing and New Arrivals Banner" data-ga-promo-creative="hp_banner_custom_printing_and_new_arrivals" data-ga-promo-position="HP Banner Mobile 3- Custom Printing" alt="Custom Printing" href="<@ofbizUrl>/designoptions</@ofbizUrl>" coords="0,0,768,345" shape="rect" />
			<area data-ga-promo data-ga-promo-id="hp_banner_custom_printing_and_new_arrivals" data-ga-promo-name="Custom Printing and New Arrivals Banner" data-ga-promo-creative="hp_banner_custom_printing_and_new_arrivals" data-ga-promo-position="HP Banner Mobile 3 - New Arrivals" alt="New Arrivals" href="<@ofbizUrl>/new-arrivals</@ofbizUrl>" coords="0,346,768,690" shape="rect" />
		</map><!-- END HOMEPAGE BANNER -->
		
		<!-- START SLIM BANNER BELOW HOMEPAGE BANNER -->
		<div class="row bannerRow">
			<!-- CUSTOM PRINT BANNER -->	
			<div class="columns large-4 medium-12 small-12 no-padding">
				<a data-ga-promo data-ga-promo-id="hp_thin_banner_custom_printing" data-ga-promo-name="Custom Printing" data-ga-promo-creative="hp_thin_banner_custom_printing" data-ga-promo-position="Site Wide Banner Spot 1" href="<@ofbizUrl>/designoptions</@ofbizUrl>" class="tablet-desktop-only jqs-trackHeaderBanner"><div class="siteBannerText bannerNavy"><strong>Custom Printing is Easy </strong>with Envelopes.com</div></a>
			</div>
			<!-- SWATCHBOOK BANNER -->
			<div class="columns large-4 medium-12 small-12 no-padding">
				<a data-ga-promo data-ga-promo-id="hp_thin_banner_swatchbook" data-ga-promo-name="Swatchbook" data-ga-promo-creative="hp_thin_banner_swatchbook" data-ga-promo-position="Site Wide Banner Spot 2" href="<@ofbizUrl>/product/~category_id=PAPER/~product_id=SWATCHBOOK</@ofbizUrl>" class="jqs-trackHeaderBanner"><div class="siteBannerText bannerGray bannerSwatchbook"><strong class="swatchbookBlock">Our Swatchbook Ships Free!</strong> <span style="border: 1px solid #ffffff; padding: 1px;" class="tablet-desktop-only-inline-block">Get it Now <i class="fa fa-caret-right"></i></span></div></a>
			</div>
			<!-- PROMOTIONAL BANNER -->
			<div class="columns large-4 medium-12 small-12 no-padding">
				<div class="siteBannerText bannerBlue">
					<strong>Envelopes.com is open + here to continue serving you!</strong>
				</div>
			</div>
		</div>
		<!-- END SLIM BANNER BELOW HOMEPAGE BANNER -->
	</div>
	${screens.render("component://envelopes/widget/CommonScreens.xml#main-env")}
</div>

<script type='text/javascript' src="<@ofbizContentUrl>/html/js/util/bigNameCarousel.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/main.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>