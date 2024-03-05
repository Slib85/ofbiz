<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/util/bigNameCarousel.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/main-redesign.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
<div class="container main content no-padding">
    <div class="banner reDesignWrap">
        <#if (currentTimestamp?default(now?datetime) gte "2021-11-26 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-11-27 23:59:59.000"?datetime)>
            <div bns-carousel="desktopHPImage" class="desktopBanners border-line-all relative bnc_env_desktop_hp_banner tablet-desktop-only">
                <div bns-carouse-image-container>
                    <div bns-image_active bns-image_index="0">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-feast-week-black-friday-homepage-desktop-banner-v2?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Feast Week | Black Friday Deals | Envelopes.com" />
                    </div>
                    <a bns-image_index="1" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 2 Desktop" title="Holiday Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-desktop-banner-v2?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="2" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 3 Desktop" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Desktop" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-fall-theme-desktop-banner-v2?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
            <div bns-carousel="mobileHPImage" class="mobile-only border-line-all bnc_env_mobile_hp_banner">
                <div bns-carouse-image-container>
                    <div bns-image_active bns-image_index="0">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-feast-week-black-friday-homepage-mobile-banner-v2?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Feast Week | Black Friday Deals | Envelopes.com" />
                    </div>
                    <a bns-image_index="1" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Holiday Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-mobile-banner-v2?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="2" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 2 Mobile" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Mobile" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-fall-theme-mobile-banner-v2?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-11-29 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-11-29 23:59:59.000"?datetime)>
            <div bns-carousel="desktopHPImage" class="desktopBanners border-line-all relative bnc_env_desktop_hp_banner tablet-desktop-only">
                <div bns-carouse-image-container>
                    <div bns-image_active bns-image_index="0">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes_p_hp-cyber_monday-desktop?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Cyber Monday | Envelopes.com" />
                    </div>
                    <a bns-image_index="1" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 2 Desktop" title="Holiday Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-desktop-banner-v2?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="2" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 3 Desktop" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Desktop" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-fall-theme-desktop-banner-v2?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
            <div bns-carousel="mobileHPImage" class="mobile-only border-line-all bnc_env_mobile_hp_banner">
                <div bns-carouse-image-container>
                    <div bns-image_active bns-image_index="0">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes_p_hp-cyber_monday-mobile?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Cyber Monday | Envelopes.com" />
                    </div>
                    <a bns-image_index="1" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Holiday Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-mobile-banner-v2?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="2" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 2 Mobile" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Mobile" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-fall-theme-mobile-banner-v2?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-11-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-01 23:59:59.000"?datetime)>
            <div bns-carousel="desktopHPImage" class="desktopBanners border-line-all relative bnc_env_desktop_hp_banner tablet-desktop-only">
                <div bns-carouse-image-container>
                    <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/category/~category_id=INVITATION</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayInvitationEnvelopes" data-ga-promo-name="HolidayInvitationEnvelopes" data-ga-promo-creative="hp_banner_HolidayInvitationEnvelopes" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Holiday Invitation Envelopes | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-a-formal-invitation-series-homepage-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Invitation Envelopes | Envelopes.com" />
                    </a>
                    <a bns-image_index="1" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 2 Desktop" title="Holiday Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-desktop-banner-v2?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="2" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 3 Desktop" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Desktop" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
            <div bns-carousel="mobileHPImage" class="mobile-only border-line-all bnc_env_mobile_hp_banner">
                <div bns-carouse-image-container>
                    <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/category/~category_id=INVITATION</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayInvitationEnvelopes" data-ga-promo-name="HolidayInvitationEnvelopes" data-ga-promo-creative="hp_banner_HolidayInvitationEnvelopes" data-ga-promo-position="HP Banner Spot 1 Mobile" title="Holiday Invitation Envelopes | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-a-formal-invitation-series-homepage-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Invitation Envelopes | Envelopes.com" />
                    </a>
                    <a bns-image_index="1" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Holiday Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-mobile-banner-v2?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="2" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 2 Mobile" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Mobile" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-12-02 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-05 23:59:59.000"?datetime)>
            <div bns-carousel="desktopHPImage" class="desktopBanners border-line-all relative bnc_env_desktop_hp_banner tablet-desktop-only">
                <div bns-carouse-image-container>
                    <div bns-image_active bns-image_index="0">
                        <img usemap="#ENV_HolidayColors_Desktop" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-classic-colors-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Colors | Envelopes.com" />
                    </div>
                    <a bns-image_index="1" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 2 Desktop" title="Holiday Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-desktop-banner-v2?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="2" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 3 Desktop" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Desktop" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
            <div bns-carousel="mobileHPImage" class="mobile-only border-line-all bnc_env_mobile_hp_banner">
                <div bns-carouse-image-container>
                    <div bns-image_active bns-image_index="0">
                        <img usemap="#ENV_HolidayColors_Mobile" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-classic-colors-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Colors | Envelopes.com" />
                    </div>
                    <a bns-image_index="1" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Holiday Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-mobile-banner-v2?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="2" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 2 Mobile" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Mobile" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-12-06 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-08 23:59:59.000"?datetime)>
            <div bns-carousel="desktopHPImage" class="desktopBanners border-line-all relative bnc_env_desktop_hp_banner tablet-desktop-only">
                <div bns-carouse-image-container>
                    <#--  <a bns-image_index="0" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Santa's Workshop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-santas-workshop-holiday-promo-homepage-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Santa's Workshop | Envelopes.com" />
                    </a>  -->
                    <#--  <a bns-image_index="0" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Santa's Workshop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-santas-workshop-elves-homepage-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Santa's Workshop | Envelopes.com" />
                    </a>  -->
                    <div bns-image_active bns-image_index="0">
                        <img usemap="#ENV_WrapUp2021_Desktop" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-wrap-up-homepage-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Wrap Up 2021 | Envelopes.com" />
                    </div>
                    <div bns-image_index="1">
                        <img usemap="#ENV_HolidayColors_Desktop" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-classic-colors-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Colors | Envelopes.com" />
                    </div>
                    <a bns-image_index="2" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 3 Desktop" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                    <#--  <a bns-image_index="4" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 4 Desktop" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>  -->
                    
                </div>
            </div>
            <div bns-carousel="mobileHPImage" class="mobile-only border-line-all bnc_env_mobile_hp_banner">
                <div bns-carouse-image-container>
                    <#--  <a bns-image_index="0" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Mobile" title="Santa's Workshop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-santas-workshop-holiday-promo-homepage-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Santa's Workshop | Envelopes.com" />
                    </a>  -->
                    <#--  <a bns-image_index="0" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Mobile" title="Santa's Workshop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-santas-workshop-elves-homepage-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Santa's Workshop | Envelopes.com" />
                    </a>  -->
                    <div bns-image_active bns-image_index="0">
                        <img usemap="#ENV_WrapUp2021_Mobile" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-wrap-up-homepage-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Wrap Up 2021 | Envelopes.com" />
                    </div>
                    <div bns-image_index="1">
                        <img usemap="#ENV_HolidayColors_Mobile" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-classic-colors-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Colors | Envelopes.com" />
                    </div>
                    <a bns-image_index="2" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Mobile" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                    <#--  <a bns-image_index="3" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 3 Mobile" title="Rachael Hale Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                    </a>  -->
                </div>
            </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-12-09 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-17 23:59:59.000"?datetime)>
            <div bns-carousel="desktopHPImage" class="desktopBanners border-line-all relative bnc_env_desktop_hp_banner tablet-desktop-only">
                <div bns-carouse-image-container>
                    <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/search?w=idspispopd&cnt=50</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayDeal" data-ga-promo-name="HolidayDeal" data-ga-promo-creative="hp_banner_HolidayDeal" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Holiday Deal | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-promo-fifteen-percent-off-selected-products-homepage-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Deal | Envelopes.com" />
                    </a>
                    <div bns-image_index="1">
                        <img usemap="#ENV_WrapUp2021_Desktop" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-wrap-up-homepage-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Wrap Up 2021 | Envelopes.com" />
                    </div>
                    <div bns-image_index="2">
                        <img usemap="#ENV_HolidayColors_Desktop" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-classic-colors-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Colors | Envelopes.com" />
                    </div>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 3 Desktop" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
            <div bns-carousel="mobileHPImage" class="mobile-only border-line-all bnc_env_mobile_hp_banner">
                <div bns-carouse-image-container>
                    <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/search?w=idspispopd&cnt=50</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayDeal" data-ga-promo-name="HolidayDeal" data-ga-promo-creative="hp_banner_HolidayDeal" data-ga-promo-position="HP Banner Spot 1 Mobile" title="Holiday Deal | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-promo-fifteen-percent-off-selected-products-homepage-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Deal | Envelopes.com" />
                    </a>
                    <div bns-image_index="1">
                        <img usemap="#ENV_WrapUp2021_Mobile" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-wrap-up-homepage-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Wrap Up 2021 | Envelopes.com" />
                    </div>
                    <div bns-image_index="2">
                        <img usemap="#ENV_HolidayColors_Mobile" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-classic-colors-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Colors | Envelopes.com" />
                    </div>
                    <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 3 Mobile" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-12-18 00:00:00.000"?datetime)>
            <div bns-carousel="desktopHPImage" class="desktopBanners border-line-all relative bnc_env_desktop_hp_banner tablet-desktop-only">
                <div bns-carouse-image-container>
                    <div bns-image_active bns-image_index="0">
                        <img usemap="#ENV_WrapUp2021_Desktop" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-wrap-up-homepage-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Wrap Up 2021 | Envelopes.com" />
                    </div>
                    <div bns-image_index="1">
                        <img usemap="#ENV_HolidayColors_Desktop" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-classic-colors-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Colors | Envelopes.com" />
                    </div>
                    <a bns-image_index="2" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 3 Desktop" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
            <div bns-carousel="mobileHPImage" class="mobile-only border-line-all bnc_env_mobile_hp_banner">
                <div bns-carouse-image-container>
                    <div bns-image_index="0">
                        <img usemap="#ENV_WrapUp2021_Mobile" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-wrap-up-homepage-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Wrap Up 2021 | Envelopes.com" />
                    </div>
                    <div bns-image_index="1">
                        <img usemap="#ENV_HolidayColors_Mobile" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-classic-colors-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Colors | Envelopes.com" />
                    </div>
                    <a bns-image_index="2" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 3 Mobile" title="The Wedding Shop | Envelopes.com">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-winter-theme-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    </a>
                </div>
            </div>
        <#else>
        <div bns-carousel="desktopHPImage" class="desktopBanners border-line-all relative bnc_env_desktop_hp_banner tablet-desktop-only">
            <div bns-carouse-image-container>
                <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Holiday Shop | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-desktop-banner-v2?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                </a>
                <a bns-image_index="1" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 2 Desktop" title="Rachael Hale Shop | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-desktop-banner-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                </a>
                <a bns-image_index="2" href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_FallSavings" data-ga-promo-name="FallSavings" data-ga-promo-creative="hp_banner_FallSavings" data-ga-promo-position="HP Banner Spot 3 Desktop" title="Fall Savings | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-fall-into-savings-promotional-homepage-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Fall Savings | Envelopes.com" />
                </a>
                <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Desktop" title="The Wedding Shop | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-fall-theme-desktop-banner-v2?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                </a>
            </div>
        </div>
        <div bns-carousel="mobileHPImage" class="mobile-only border-line-all bnc_env_mobile_hp_banner">
            <div bns-carouse-image-container>
                <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_HolidayShop" data-ga-promo-name="HolidayShop" data-ga-promo-creative="hp_banner_HolidayShop" data-ga-promo-position="HP Banner Spot 1 Desktop" title="Holiday Shop | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-mobile-banner-v2?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
                </a>
                <a bns-image_index="1" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_RachaelHaleShop" data-ga-promo-name="RachaelHaleShop" data-ga-promo-creative="hp_banner_RachaelHaleShop" data-ga-promo-position="HP Banner Spot 2 Mobile" title="Rachael Hale Shop | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-rachael-hale-shop-homepage-mobile-banner-v1?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
                </a>
                <a bns-image_index="2" href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_FallSavings" data-ga-promo-name="FallSavings" data-ga-promo-creative="hp_banner_FallSavings" data-ga-promo-position="HP Banner Spot 3 Mobile" title="Fall Savings | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-fall-into-savings-promotional-homepage-mobile-banner?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="Fall Savings | Envelopes.com" />
                </a>
                <a bns-image_index="3" href="<@ofbizUrl>/weddingShop</@ofbizUrl>" data-ga-promo data-ga-promo-id="hp_banner_WeddingShop" data-ga-promo-name="WeddingShop" data-ga-promo-creative="hp_banner_WeddingShop" data-ga-promo-position="HP Banner Spot 4 Mobile" title="The Wedding Shop | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-homepage-fall-theme-mobile-banner-v2?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                </a>
            </div>
        </div>
        </#if>
        <#------------------------------------------->
        <#------------ Wrap Up 2021 Banner ---------->
        <#------------------------------------------->
        <map name="ENV_WrapUp2021_Desktop">
            <area target="_blank" alt="Holiday Boxes | Envelopes.com" title="Holiday Boxes | Envelopes.com" href="<@ofbizUrl>/search?w=gift+boxes&c=0</@ofbizUrl>" coords="455,0,703,299" shape="rect">
            <area target="_blank" alt="Holiday Mailers | Envelopes.com" title="Holiday Mailers | Envelopes.com" href="<@ofbizUrl>/search?w=holiday+mailers</@ofbizUrl>" coords="704,0,903,299" shape="rect">
            <area target="_blank" alt="Wrapping Paper | Envelopes.com" title="Wrapping Paper | Envelopes.com" href="<@ofbizUrl>/search?w=wrapping+paper</@ofbizUrl>" coords="903,0,1104,299" shape="rect">
            <area target="_blank" alt="Gift Bags | Envelopes.com" title="Gift Bags | Envelopes.com" href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>" coords="1104,0,1359,299" shape="rect">
        </map>
        <map name="ENV_WrapUp2021_Mobile">
            <area target="_blank" alt="Holiday Boxes | Envelopes.com" title="Holiday Boxes | Envelopes.com" href="<@ofbizUrl>/search?w=gift+boxes&c=0</@ofbizUrl>" coords="0,221,202,486" shape="rect">
            <area target="_blank" alt="Holiday Mailers | Envelopes.com" title="Holiday Mailers | Envelopes.com" href="<@ofbizUrl>/search?w=holiday+mailers</@ofbizUrl>" coords="202,219,383,486" shape="rect">
            <area target="_blank" alt="Wrapping Paper | Envelopes.com" title="Wrapping Paper | Envelopes.com" href="<@ofbizUrl>/search?w=wrapping+paper</@ofbizUrl>" coords="383,219,567,486" shape="rect">
            <area target="_blank" alt="Gift Bags | Envelopes.com" title="Gift Bags | Envelopes.com" href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>" coords="568,219,767,486" shape="rect">
        </map>
        <#------------------------------------------->
        <#---------- Holiday Colors Banner ---------->
        <#------------------------------------------->
        <map name="ENV_HolidayColors_Desktop">
            <area target="_blank" alt="Holiday Red | Envelopes.com" title="Holiday Red | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=RED_HOLIDAYRED</@ofbizUrl>" coords="184,0,321,299" shape="rect">
            <area target="_blank" alt="White | Envelopes.com" title="White | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" coords="356,0,493,299" shape="rect">
            <area target="_blank" alt="Jupiter Metallic | Envelopes.com" title="Jupiter Metallic | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=RED_JUPITERMETALLIC</@ofbizUrl>" coords="526,0,663,299" shape="rect">
            <area target="_blank" alt="Gold Metallic | Envelopes.com" title="Gold Metallic | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=GOLD_GOLDMETALLIC</@ofbizUrl>" coords="697,0,835,299" shape="rect">
            <area target="_blank" alt="Holiday Green | Envelopes.com" title="Holiday Green | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=GREEN_HOLIDAYGREEN</@ofbizUrl>" coords="867,0,1006,299" shape="rect">
            <area target="_blank" alt="Silver Metallic | Envelopes.com" title="Silver Metallic | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=SILVER_SILVERMETALLIC</@ofbizUrl>" coords="1038,0,1176,299" shape="rect">
        </map>
        <map name="ENV_HolidayColors_Mobile">
            <area target="_blank" alt="Holiday Red | Envelopes.com" title="Holiday Red | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=RED_HOLIDAYRED</@ofbizUrl>" coords="155,179,287,311" shape="rect">
            <area target="_blank" alt="White | Envelopes.com" title="White | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" coords="326,178,459,313" shape="rect">
            <area target="_blank" alt="Jupiter Metallic | Envelopes.com" title="Jupiter Metallic | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=RED_JUPITERMETALLIC</@ofbizUrl>" coords="497,178,629,310" shape="rect">
            <area target="_blank" alt="Gold Metallic | Envelopes.com" title="Gold Metallic | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=GOLD_GOLDMETALLIC</@ofbizUrl>" coords="155,329,289,462" shape="rect">
            <area target="_blank" alt="Holiday Green | Envelopes.com" title="Holiday Green | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=GREEN_HOLIDAYGREEN</@ofbizUrl>" coords="329,329,460,462" shape="rect">
            <area target="_blank" alt="Silver Metallic | Envelopes.com" title="Silver Metallic | Envelopes.com" href="<@ofbizUrl>/shopByColor/~category_id=SILVER_SILVERMETALLIC</@ofbizUrl>" coords="498,331,631,462" shape="rect">
        </map>
        <div class="row margin-top-md">
            <div class="columns large-8 medium-12 small-12 no-padding">
                <div class="row padding-right-md padding-left-md">
                    <#---------------------------------------------------->
                    <#-------------- SHOP BY SIZE / SLIDER --------------->
                    <#---------------------------------------------------->
                    <div class="ENV_New_HP_Slider columns large-12 medium-12 small-12 no-padding margin-bottom-lg">
                        <div>
                            <div class="ENV_Slider_Header">
                                <div class="new-slider-header">
                                    <h2>Shop by Size</h2>
                                </div>
                            </div>
                            <div class="ENV_Slider_CTA margin-top-xxs margin-bottom-xxs">
                                <div>
                                    <a href="<@ofbizUrl>/envelope-sizes</@ofbizUrl>" title="View Sizing Guide">
                                        <span>View Sizing Guide</span>
                                    </a>
                                </div>
                                <div>
                                    <span> | </span>
                                </div>
                                <div>
                                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" title="Shop All Sizes">
                                        <span>Shop All Sizes</span> 
                                    </a>
                                </div> 
                            </div>
                            <div class="ENV_Slider_Content">
                                <div class="slideIt-container newHPSliderContent">
                                    <div class="slideIt-left">
                                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                                    </div>
                                    <div class="slideIt">
                                        <div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:418x912</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-ten-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:514x714</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/a7-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:438x534%20st:squareflap%20st:contourflap</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/a2-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:434x612%20st:squareflap%20st:contourflap</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/a6-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:9x12%20si:9x12&sort=MOST_POPULAR</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/9x12-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:6x9&sort=MOST_POPULAR</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/6x9-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:534x834&sort=MOST_POPULAR</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/a9-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:358x518%20st:squareflap%20st:contourflap</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/a1-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:358x612&sort=MOST_POPULAR</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/6-3-fourth-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/category?af=si:378x878&sort=SIZE_SMALL</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-nine-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/product/~category_id=OPEN_END/~product_id=94623</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-one-coin-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/product/~category_id=POINTED_FLAP/~product_id=17_MINI</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/seventeen-mini-envelopes-shop-by-size-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="slideIt-right">
                                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                    <#---------------------------------------------------->
                    <#-------------- SHOP BY COLOR / SLIDER -------------->
                    <#---------------------------------------------------->
                    <div class="ENV_New_HP_Slider columns large-12 medium-12 small-12 no-padding margin-bottom-md">
                        <div>
                            <div class="ENV_Slider_Header">
                                <div class="new-slider-header">
                                    <h2>Shop by Color</h2>
                                </div>
                            </div>
                            <div class="ENV_Slider_CTA margin-top-xxs margin-bottom-xxs">
                                <div>
                                    <a href="<@ofbizUrl>/product/~category_id=SWATCHBOOK/~product_id=SWATCHBOOK</@ofbizUrl>" title="View Sizing Guide">
                                        <span>Order a Swatchbook</span>
                                    </a>
                                </div>
                                <div>
                                    <span> | </span>
                                </div>
                                <div>
                                    <a href="<@ofbizUrl>/shopByColor</@ofbizUrl>" title="Shop All Sizes">
                                        <span>Shop All Colors</span> 
                                    </a>
                                </div> 
                            </div>
                            <div class="ENV_Slider_Content">
                                <div class="slideIt-container newHPSliderContent">
                                    <div class="slideIt-left">
                                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                                    </div>
                                    <div class="slideIt">
                                        <div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/white-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=GOLD</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/gold-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=BLUE</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/blue-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=BLACK</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/black-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=GREEN</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/green-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=RED</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/red-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/search?w=grocery+bag</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/grocery-bag-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=ORANGE</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/orange-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=GRAY</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/gray-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=NATURAL</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/natural-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=PINK</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/pink-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=PURPLE</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/purple-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=BROWN</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/brown-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=YELLOW</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/yellow-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=SILVER_SILVERMETALLIC</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/silver-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                            <div>
                                                <a href="<@ofbizUrl>/shopByColor/~category_id=CLEAR</@ofbizUrl>" title="">
                                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/clear-envelopes-shop-by-color-homepage-thumbnail-new?fmt=png-alpha&wid=95&hei=95</@ofbizScene7Url>" alt="" />
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="slideIt-right">
                                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <#---------------------------------------------------->
            <#---------- HP TIER BANNER / RIGHT COLUMN ----------->
            <#---------------------------------------------------->
            <div class="columns large-4 medium-12 small-12 no-padding">
                <div class="ENV_New_HP_Tier">
                    <#if (currentTimestamp?default(now?datetime) gte "2021-11-22 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-01 23:59:59.000"?datetime)>
                    <div>
                        <img usemap="#ENV_Holiday_Colors" class="border-line-all" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-colors-zone3-homepage-desktop-banner?wid=440&hei=530&fmt=png-alpha</@ofbizScene7Url>" atl="Elevate Your Brand | Custom Printing | Envelopes.com" border="0" />
                        <map name="ENV_Holiday_Colors">
                            <area target="_blank" alt="Holiday Red" title="Holiday Red" href="<@ofbizUrl>/shopByColor/~category_id=RED_HOLIDAYRED</@ofbizUrl>" coords="0,308,139,176" shape="rect">
                            <area target="_blank" alt="Holiday Green" title="Holiday Green" href="<@ofbizUrl>/shopByColor/~category_id=GREEN_HOLIDAYGREEN</@ofbizUrl>" coords="140,308,291,176" shape="rect">
                            <area target="_blank" alt="Silver Metallic" title="Silver Metallic" href="<@ofbizUrl>/shopByColor/~category_id=SILVER_SILVERMETALLIC</@ofbizUrl>" coords="291,310,440,176" shape="rect">
                            <area target="_blank" alt="White" title="White" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" coords="1,321,140,482" shape="rect">
                            <area target="_blank" alt="Gold Metallic" title="Gold Metallic" href="<@ofbizUrl>/shopByColor/~category_id=GOLD_GOLDMETALLIC</@ofbizUrl>" coords="140,321,292,482" shape="rect">
                            <area target="_blank" alt="Jupiter Metallic" title="Jupiter Metallic" href="<@ofbizUrl>/shopByColor/~category_id=RED_JUPITERMETALLIC</@ofbizUrl>" coords="292,320,440,482" shape="rect">
                        </map>
                    </div>
                    <#elseif (currentTimestamp?default(now?datetime) gte "2021-12-02 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
                    <a href="/print-services" title="Elevate Your Brand | Custom Printing | Envelopes.com">
                        <img class="border-line-all" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-custom-printing-zone3-homepage-desktop-banner?wid=440&amp;hei=530&amp;fmt=png-alpha</@ofbizScene7Url>" atl="Elevate Your Brand | Custom Printing | Envelopes.com" border="0">
                    </a>
                    <#else>
                    <a href="/print-services" title="Elevate Your Brand | Custom Printing | Envelopes.com">
                        <img class="border-line-all" src="<@ofbizScene7Url>/is/image/ActionEnvelope/elevate-your-brand-homepage-tier-banner?wid=440&amp;hei=530&amp;fmt=png-alpha</@ofbizScene7Url>" atl="Elevate Your Brand | Custom Printing | Envelopes.com" border="0">
                    </a>
                    </#if>
                </div>
            </div>

        </div>
        <#-----MAILING ENVELOPES------>
        <div class="row margin-top-xs margin-bottom-xs padding-xxs">
            <div class="section-header">
                <h2>Mailing Envelopes</h2>
                <div>
                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" title="Shop All Mailing Envelopes | Envelopes.com">
                        <span>Shop All Mailing Envelopes</span>
                        <div class="arrow-ui thin-right no-margin"></div>
                    </a> 
                </div>
            </div>
            <div class="mailingEnvelopes">
                <div class="slideIt-container homePageSlider">
                    <div class="slideIt-left">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                    <div class="slideIt padding-top-xxs">
                        <div>
                            <div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Top Printed Envelopes" class="text-center" href="<@ofbizUrl>/search?w=top+printed+envelopes</@ofbizUrl>" title="Top Printed Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/TopPrintedEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Top Printed Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Square Flap Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=SQUARE_FLAP</@ofbizUrl>" title="Square Flap Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/SquareFlapEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Square Flap Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Regular Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=REGULAR</@ofbizUrl>" title="Regular Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/RegularEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Regular Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Open End Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=OPEN_END</@ofbizUrl>" title="Open End Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/OpenEndEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Open End Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Square Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=SQUARE</@ofbizUrl>" title="Square Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/SquareEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Square Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Window Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=WINDOW</@ofbizUrl>" title="Window Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/WindowEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Window Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Mini Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=MINI_ENVELOPES</@ofbizUrl>" title="Mini Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/MiniEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Mini Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|9 x 12 Open End Envelopes" class="text-center" href="<@ofbizUrl>/search?w=9+x+12+Open+End+Window</@ofbizUrl>" title="9 x 12 Open End Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/9x12OpenEndEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">9 x 12 Open End Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Remittance Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=REMITTANCE</@ofbizUrl>" title="Remittance Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/RemittanceEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Remittance Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Double Window Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=BUSINESS</@ofbizUrl>?af=use:business%20si:418x918%20prodtype:products" title="Double Window Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/DoubleWindowEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Double Window Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Security Tint Envelopes" class="text-center" href="<@ofbizUrl>/search?w=security+tint</@ofbizUrl>" title="Security Tint Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/SecurityTintEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Security Tint Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|W-2 Envelopes" class="text-center" href="<@ofbizUrl>/search?w=w-2+envelopes</@ofbizUrl>" title="W-2 Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/W2EnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">W-2 Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Grocery Bag Envelopes" class="text-center" href="<@ofbizUrl>/search?w=grocery+bag+envelopes</@ofbizUrl>" title="Grocery Bag Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/GroceryBagEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Grocery Bag Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Booklet Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=BOOKLET</@ofbizUrl>" title="Booklet Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/BookletEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText"></div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Coin Envelopes" class="text-center" href="<@ofbizUrl>/category?af=si:11116x234%20si:214x312%20si:212x414%20si:278x514%20si:3x412%20si:3x478%20si:318x512%20si:338x6%20si:312x612%20si:2x2%20st:openend&sort=MOST_POPULAR</@ofbizUrl>" title="Coin Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/CoinEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Coin Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Announcement Envelopes" class="text-center" href="<@ofbizUrl>/category?af=use:invitation&amp;sort=MOST_POPULAR</@ofbizUrl>" title="Announcement Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/AnnouncementEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Announcement Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Contour Flap Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=CONTOUR_FLAP</@ofbizUrl>" title="Contour Flap Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ContourFlapEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Contour Flap Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Shipping Envelopes" class="text-center" href="<@ofbizUrl>/category?af=use:shipping</@ofbizUrl>" title="Shipping Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ShippingEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Shipping Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Peel & Press Envelopes" class="text-center" href="<@ofbizUrl>/search?af=sm:peelpress&amp;w=*</@ofbizUrl>" title="Peel &amp; Press Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/PeelAndPressEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Peel &amp; Press Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Baronial Envelopes" class="text-center" href="<@ofbizUrl>/search?af=use:invitation&amp;w=bar%20envelopes</@ofbizUrl>" title="Baronial Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/BaronialEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Baronial Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Cotton Envelopes" class="text-center" href="<@ofbizUrl>/search?w=cotton+envelopes</@ofbizUrl>" title="Cotton Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/CottonEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Cotton Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Metallic Envelopes" class="text-center" href="<@ofbizUrl>/search?w=Metallic+Envelopes</@ofbizUrl>" title="Metallic Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/MetallicEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Metallic Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Recycled Envelopes" class="text-center" href="<@ofbizUrl>/search?w=recycled</@ofbizUrl>" title="Recycled Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/RecycledEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Recycled Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Credit Card Sleeve" class="text-center" href="<@ofbizUrl>/search?w=Credit+Card+Sleeves</@ofbizUrl>" title="Credit Card Sleeve | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/CreditCardSleevesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Credit Card Sleeve</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Full Face Window Envelopes" class="text-center" href="<@ofbizUrl>/search?w=full+face+window&c=0</@ofbizUrl>" title="Full Face Window Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/FullFaceWindowEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Full Face Window Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Clear Envelopes" class="text-center" href="<@ofbizUrl>/search?af=cog1:clear&amp;w=envelopes</@ofbizUrl>" title="Clear Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ClearEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Clear Envelopes</div>
                                </a>
                            </div><div>
                                <a bns-track_event="Homepage|Mailing Envelopes|Specialty Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=SPECIALTY_USE</@ofbizUrl>" title="Specialty Envelopes | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/WindowEnvelopesThumbnail?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="margin-top-xs sliderProductNameText">Specialty Envelopes</div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                </div>
            </div>
        </div>
        <#-----POPULAR TEMPLATES------>
        <div class="row margin-top-xs margin-bottom-xs padding-xxs">
            <div class="section-header">
                <h2>Most Popular Address Templates</h2>
            </div>
            <div class="mostPopularTemplates">
                <div class="slideIt-container templateListContainer templateSlider">
                    <div class="slideIt-left">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                    <div class="slideIt padding-top-xxs">
                        <div>
                            <#list staticCategoryDesigns as staticCategoryDesign><div>
                                <a <#if staticCategoryDesign.name?exists>bns-track_event="Homepage|Most Popular Address Templates|${staticCategoryDesign.name}" </#if>class="text-center" href="<@ofbizUrl>/product?product_id=${staticCategoryDesign.productId?if_exists}&designId=${staticCategoryDesign.designId?if_exists}</@ofbizUrl>" title="${staticCategoryDesign.name?if_exists}">
                                    <img class="jqs-defer-img glow" data-src="<@ofbizScene7Url>/is/image/ActionEnvelope/${staticCategoryDesign.imgName?if_exists}?fmt=jpeg&amp;wid=265</@ofbizScene7Url>" src="data:image/png;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=" alt="${staticCategoryDesign.name?if_exists}" />
                                    <div class="template-product-wrap">
                                        <div class="template-name">${staticCategoryDesign.name?if_exists}</div>
                                        <div class="template-type">${staticCategoryDesign.type?if_exists}</div>
                                        <div class="template-availability">${staticCategoryDesign.colorCount?if_exists} Envelope Colors</div>
                                    </div>
                                </a>
                            </div></#list>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                </div>
            </div>

        </div>
        <#if (currentTimestamp?default(now?datetime) gte "2020-12-21 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-31 23:59:59.000"?datetime)>
        <#-----HOLIDAY TRENDS------>
        <div class="row margin-top-xs margin-bottom-xs padding-xxs">
            <div class="section-header">
                <h2>Holiday Trends</h2>
                <div>
                    <a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" title="Shop All Holiday Trends">
                        <span>Shop All Holiday Trends</span> <div class="arrow-ui thin-right no-margin"></div>
                    </a> 
                </div>
            </div>
            <div class="holiday_trends">
                <div class="slideIt-container homePageSlider">
                    <div class="slideIt-left">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                    <div class="slideIt padding-top-xxs">
                        <div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Gift Bags" class="text-center" href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Gift_Bags?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Gift Bags" />
                                    <div class="margin-top-xs limit-text">Gift Bags</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Gift Boxes" class="text-center" href="<@ofbizUrl>/search?w=gift+boxes&c=0</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Gift_Boxes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Gift Boxes" />
                                    <div class="margin-top-xs limit-text">Gift Boxes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Wine Bottle Bags" class="text-center" href="<@ofbizUrl>/search?w=wine+bottle+bags</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Wine_Bottle_Bags?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Wine Bottle Bags" />
                                    <div class="margin-top-xs limit-text">Wine Bottle Bags</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Wrapping Paper" class="text-center" href="<@ofbizUrl>/search?w=wrapping+paper</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Wrapping_Paper?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Wrapping Paper" />
                                    <div class="margin-top-xs limit-text">Wrapping Paper</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Coloring Books" class="text-center" href="<@ofbizUrl>/product/~category_id=COLORING_BOOK/~product_id=CLRBK-2001</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Coloring_Books?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Coloring Books" />
                                    <div class="margin-top-xs limit-text">Coloring Books</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Expansion Envelopes" class="text-center" href="<@ofbizUrl>/product/~category_id=PAPER/~product_id=SANTALTR</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Santa_Gift_And_Letter_Kit?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Santa Gift &amp; Letter Kit" />
                                    <div class="margin-top-xs limit-text">Santa Gift &amp; Letter Kit</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Holiday Mailers" class="text-center" href="<@ofbizUrl>/search?w=holiday+mailers</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Holiday_Mailers?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Holiday Mailers" />
                                    <div class="margin-top-xs limit-text">Holiday Mailers</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Notecard Sets" class="text-center" href="<@ofbizUrl>/search?w=folded+card+set</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Greeting_Card_Sets?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Notecard Sets" />
                                    <div class="margin-top-xs limit-text">Notecard Sets</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Gift Card Envelopes" class="text-center" href="<@ofbizUrl>/search?&w=gift%20cards&c=0</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Gift_Card_Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Gift Card Envelopes" />
                                    <div class="margin-top-xs limit-text">Gift Card Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Foil Lined Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined%20col:linedenvelopes</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Foil_Lined_Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Foil Lined Envelopes" />
                                    <div class="margin-top-xs limit-text">Foil Lined Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Metallic Envelopes" class="text-center" href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Metallic_Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Metallic Envelopes" />
                                    <div class="margin-top-xs limit-text">Metallic Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Remittance Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=REMITTANCE</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Remittance_Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Remittance Envelopes" />
                                    <div class="margin-top-xs limit-text">Remittance Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Trend|Square Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=SQUARE</@ofbizUrl>">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV_Holiday_HP_Carousel_Square_Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Square Envelopes" />
                                    <div class="margin-top-xs limit-text">Square Envelopes</div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                </div>
            </div>
        </div>
        <#-----HOLIDAY SHOP BANNER------>
        <div class="row margin-top-sm margin-bottom-xl">
            <div class="tablet-desktop-only">
                <a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" title="The Holiday Shop | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Holiday-Shop-Homepage-Banner-Desktop-v2?fmt=png-alpha&wid=1360</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
                </a>
            </div>
            <div class="mobile-only">
                <a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" title="The Holiday Shop | Envelopes.com">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-homepage-mobile-banner-v1?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
                </a>
            </div>
        </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-01-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-02-28 23:59:59.000"?datetime)>
        <#-----TAX SHOP CAROUSEL------>
        <div class="row margin-top-xs margin-bottom-xs padding-xxs">
            <div class="section-header">
                <h2>Most Popular in Tax Season</h2>
                <div>
                    <a href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>" title="Shop All | Tax Shop | Envelopes.com">
                        <span>Shop All</span> <div class="arrow-ui thin-right no-margin"></div>
                    </a> 
                </div>
            </div>
            <div class="tax_shop_carousel">
                <div class="slideIt-container homePageSlider">
                    <div class="slideIt-left">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                    <div class="slideIt padding-top-xxs">
                        <div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|W-2 Envelopes" class="text-center" href="<@ofbizUrl>/search?w=W-2+Envelopes</@ofbizUrl>" title="W-2 Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-W2-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="W-2 Envelopes" />
                                    <div class="margin-top-xs limit-text">W-2 Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|Double Window Envelopes" class="text-center" href="<@ofbizUrl>/search?w=double+window+envelopes</@ofbizUrl>" title="Double Window Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-Double-Window-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Double Window Envelopes" />
                                    <div class="margin-top-xs limit-text">Double Window Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|9x12 Window Envelopes" class="text-center" href="<@ofbizUrl>/product/~category_id=SINGLE_WINDOW/~product_id=1590PS</@ofbizUrl>" title="9x12 Window Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-9x12-Window-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="9x12 Window Envelopes" />
                                    <div class="margin-top-xs limit-text">9x12 Window Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|Security Tint Envelopes" class="text-center" href="<@ofbizUrl>/search?w=security+tint+envelopes</@ofbizUrl>" title="Security Tint Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-Security-Tint-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Security Tint Envelopes" />
                                    <div class="margin-top-xs limit-text">Security Tint Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|9x12 Booklet Envelopes" class="text-center" href="<@ofbizUrl>/product/~category_id=BOOKLET/~product_id=12310</@ofbizUrl>" title="9x12 Booklet Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-9x12-Booklet-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="9x12 Booklet Envelopes" />
                                    <div class="margin-top-xs limit-text">9x12 Booklet Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|Expansion Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=EXPANSION</@ofbizUrl>" title="Expansion Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-Expansion-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Expansion Envelopes" />
                                    <div class="margin-top-xs limit-text">Expansion Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|Folders" class="text-center" href="<@ofbizUrl>/search?w=folders</@ofbizUrl>" title="Folders | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-Folders?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Folders" />
                                    <div class="margin-top-xs limit-text">Folders</div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                </div>
            </div>
        </div>
        <#-----TAX SHOP BANNER------>
        <div class="row margin-top-sm margin-bottom-xl">
            <div>
                <a href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>" title="The Tax Shop | Envelopes.com">
                    <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/tax-shop-is-open-shop-now-homepage-desktop-banner-v2?fmt=png-alpha&wid=1360</@ofbizScene7Url>" alt="The Tax Shop | Envelopes.com" />
                    <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/tax-shop-is-open-shop-now-homepage-mobile-banner?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="The Tax Shop | Envelopes.com" />
                </a>
            </div>
        </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-03-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-05-18 23:59:59.000"?datetime)>
        <#-----TAX SHOP CAROUSEL------>
        <div class="row margin-top-xs margin-bottom-xs padding-xxs">
            <div class="section-header">
                <h2>Most Popular in Tax Season</h2>
                <div>
                    <a href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>" title="Shop All | Tax Shop | Envelopes.com">
                        <span>Shop All</span> <div class="arrow-ui thin-right no-margin"></div>
                    </a> 
                </div>
            </div>
            <div class="tax_shop_carousel">
                <div class="slideIt-container homePageSlider">
                    <div class="slideIt-left">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                    <div class="slideIt padding-top-xxs">
                        <div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|W-2 Envelopes" class="text-center" href="<@ofbizUrl>/search?w=W-2+Envelopes</@ofbizUrl>" title="W-2 Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-W2-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="W-2 Envelopes" />
                                    <div class="margin-top-xs limit-text">W-2 Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|Double Window Envelopes" class="text-center" href="<@ofbizUrl>/search?w=double+window+envelopes</@ofbizUrl>" title="Double Window Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-Double-Window-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Double Window Envelopes" />
                                    <div class="margin-top-xs limit-text">Double Window Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|9x12 Window Envelopes" class="text-center" href="<@ofbizUrl>/product/~category_id=SINGLE_WINDOW/~product_id=1590PS</@ofbizUrl>" title="9x12 Window Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-9x12-Window-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="9x12 Window Envelopes" />
                                    <div class="margin-top-xs limit-text">9x12 Window Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|Security Tint Envelopes" class="text-center" href="<@ofbizUrl>/search?w=security+tint+envelopes</@ofbizUrl>" title="Security Tint Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-Security-Tint-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Security Tint Envelopes" />
                                    <div class="margin-top-xs limit-text">Security Tint Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|9x12 Booklet Envelopes" class="text-center" href="<@ofbizUrl>/product/~category_id=BOOKLET/~product_id=12310</@ofbizUrl>" title="9x12 Booklet Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-9x12-Booklet-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="9x12 Booklet Envelopes" />
                                    <div class="margin-top-xs limit-text">9x12 Booklet Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|Expansion Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=EXPANSION</@ofbizUrl>" title="Expansion Envelopes | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-Expansion-Envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Expansion Envelopes" />
                                    <div class="margin-top-xs limit-text">Expansion Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Tax Shop|Folders" class="text-center" href="<@ofbizUrl>/search?w=folders</@ofbizUrl>" title="Folders | Tax Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/ENV-Tax-Shop-Carousel-Folders?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Folders" />
                                    <div class="margin-top-xs limit-text">Folders</div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                </div>
            </div>
        </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-05-19 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-11-10 23:59:59.000"?datetime)>
        <#-----WEDDING SHOP CAROUSEL------>
        <div class="row margin-top-xs margin-bottom-xs padding-xxs">
            <div class="section-header">
                <h2>Most Popular in Wedding Season</h2>
                <div>
                    <a href="<@ofbizUrl>/weddingShop</@ofbizUrl>" title="Shop All | Wedding Shop | Envelopes.com">
                        <span>Shop All</span> <div class="arrow-ui thin-right no-margin"></div>
                    </a> 
                </div>
            </div>
            <div class="wedding_shop_carousel">
                <div class="slideIt-container homePageSlider">
                    <div class="slideIt-left">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                    <div class="slideIt padding-top-xxs">
                        <div>
                            <div>
                                <a bns-track_event="Homepage|Wedding Shop|Square Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=SQUARE</@ofbizUrl>" title="Square Envelopes | Wedding Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-2021-wedding-shop-carousel-square-envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Square Envelopes" />
                                    <div class="margin-top-xs limit-text">Square Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Wedding Shop|Gift Bags" class="text-center" href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>" title="Gift Bags | Wedding Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-2021-wedding-shop-carousel-gift-bags?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Gift Bags" />
                                    <div class="margin-top-xs limit-text">Gift Bags</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Wedding Shop|Metallic" class="text-center" href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" title="Metallic | Wedding Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-2021-wedding-shop-carousel-metallic-envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Metallic" />
                                    <div class="margin-top-xs limit-text">Metallic</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Wedding Shop|DIY Invitations" class="text-center" href="<@ofbizUrl>/category?af=use:invitation%20st:zfold%20st:petals%20st:pockets%20st:gatefold%20st:diy</@ofbizUrl>" title="DIY Invitations | Wedding Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-2021-wedding-shop-carousel-diy-invitations?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="DIY Invitations" />
                                    <div class="margin-top-xs limit-text">DIY Invitations</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Wedding Shop|Grocery Bag" class="text-center" href="<@ofbizUrl>/search?w=grocery+bag</@ofbizUrl>" title="Grocery Bag | Wedding Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-2021-wedding-shop-carousel-grocery-bag-envelopes?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Grocery Bag" />
                                    <div class="margin-top-xs limit-text">Grocery Bag</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Wedding Shop|Recipient Addressing" class="text-center" href="<@ofbizUrl>/addressing</@ofbizUrl>" title="Recipient Addressing | Wedding Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-2021-wedding-shop-carousel-recipient-addressing?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Recipient Addressing" />
                                    <div class="margin-top-xs limit-text">Recipient Addressing</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Wedding Shop|Wrapping Paper" class="text-center" href="<@ofbizUrl>/search?w=wrapping+paper</@ofbizUrl>" title="Wrapping Paper | Wedding Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-2021-wedding-shop-carousel-wrapping-paper?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Wrapping Paper" />
                                    <div class="margin-top-xs limit-text">Wrapping Paper</div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                </div>
            </div>
        </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-11-11 00:00:00.000"?datetime)>
        <#-----WEDDING SHOP CAROUSEL------>
        <div class="row margin-top-xs margin-bottom-xs padding-xxs">
            <div class="section-header">
                <h2>Most Popular in Holiday Shop</h2>
                <div>
                    <a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" title="Shop All | Holiday Shop | Envelopes.com">
                        <span>Shop All</span> <div class="arrow-ui thin-right no-margin"></div>
                    </a> 
                </div>
            </div>
            <div class="holiday_shop_carousel">
                <div class="slideIt-container homePageSlider">
                    <div class="slideIt-left">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                    <div class="slideIt padding-top-xxs">
                        <div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Shop|Gift Card Sleeves" class="text-center" href="<@ofbizUrl>/search?w=holiday+credit+card+sleeves</@ofbizUrl>" title="Gift Card Sleeves | Holiday Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-card-envelopes-holiday-shop-slider-thumbnail-265px?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Gift Card Sleeves" />
                                    <div class="margin-top-xs sliderProductNameText">Gift Card Sleeves</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Shop|Foil Lined Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined%20col:linedenvelopes</@ofbizUrl>" title="Foil Lined Envelopes | Holiday Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/foil-lined-envelopes-holiday-shop-slider-thumbnail-265px?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Foil Lined Envelopes" />
                                    <div class="margin-top-xs sliderProductNameText">Foil Lined Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Shop|Metallic Envelopes" class="text-center" href="<@ofbizUrl>/search?w=holiday+metallic+envelopes</@ofbizUrl>" title="Metallic Envelopes | Holiday Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/metallic-envelopes-holiday-shop-slider-thumbnail-265px?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Metallic Envelopes" />
                                    <div class="margin-top-xs sliderProductNameText">Metallic Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Shop|Holiday Printed Envelopes" class="text-center" href="<@ofbizUrl>/search?w=holiday+printed+envelopes</@ofbizUrl>" title="Holiday Printed Envelopes | Holiday Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-printed-envelopes-holiday-shop-slider-thumbnail-265px?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Holiday Printed Envelopes" />
                                    <div class="margin-top-xs sliderProductNameText">Holiday Printed Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Shop|Remittance Envelopes" class="text-center" href="<@ofbizUrl>/category/~category_id=REMITTANCE</@ofbizUrl>" title="Remittance Envelopes | Holiday Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/remittance-envelopes-holiday-shop-slider-thumbnail-265px?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Remittance Envelopes" />
                                    <div class="margin-top-xs sliderProductNameText">Remittance Envelopes</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Shop|Holiday Mailers" class="text-center" href="<@ofbizUrl>/search?w=holiday+mailers</@ofbizUrl>" title="Holiday Mailers | Holiday Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-mailers-envelopes-holiday-shop-slider-thumbnail-265px?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Holiday Mailers" />
                                    <div class="margin-top-xs sliderProductNameText">Holiday Mailers</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Shop|Gift Bags" class="text-center" href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>" title="Gift Bags | Wedding Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-bags-holiday-shop-slider-thumbnail-265px?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Gift Bags" />
                                    <div class="margin-top-xs sliderProductNameText">Gift Bags</div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|Holiday Shop|Wrapping Paper" class="text-center" href="<@ofbizUrl>/search?w=wrapping+paper</@ofbizUrl>" title="Wrapping Paper | Wedding Shop | Envelopes.com">
                                    <img class="glow" src="<@ofbizScene7Url>/is/image/ActionEnvelope/wrapping-paper-holiday-shop-slider-thumbnail-265px?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="Wrapping Paper" />
                                    <div class="margin-top-xs sliderProductNameText">Wrapping Paper</div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                </div>
            </div>
        </div>
        </#if>
        <#-----WEDDING SHOP BANNER------>
        <#if (currentTimestamp?default(now?datetime) gte "2021-11-10 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
        <div class="row margin-top-sm margin-bottom-xl padding-xxs">
            <div>
                <a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" title="The Holiday Shop | Envelopes.com">
                    <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-content-block-homepage-desktop-banner?fmt=png-alpha&wid=1360</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
                    <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-holiday-shop-content-block-homepage-mobile-banner?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="The Holiday Shop | Envelopes.com" />
                </a>
            </div>
        </div>
        <#else>
        <div class="row margin-top-sm margin-bottom-xl padding-xxs">
            <div>
                <a href="<@ofbizUrl>/weddingShop</@ofbizUrl>" title="The Wedding Shop | Envelopes.com">
                    <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-desktop-fall-theme-homepage-content-banner-v1?fmt=png-alpha&wid=1360</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                    <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-mobile-fall-theme-homepage-content-banner-v1?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
                </a>
            </div>
        </div>
        </#if>
        <#-----CUSTOM PRINT / ORDER------>
        <div class="row margin-top-xs">
            <div id="ENV_CustomPrint" class="column large-6 medium-6 small-12">
                <a href="<@ofbizUrl>/designoptions</@ofbizUrl>" title="Custom Printing | Envelopes.com">
                    <div class="custom-printing">
                        <img class="hp-image" src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-printing-redesign-banner?wid=553&hei=200&fmt=png-alpha</@ofbizScene7Url>" alt="" />
                        <div class="block-title">Custom Printing</div>
                        <div class="block-desc">Choose from a variety of custom printing <br>options, perfect for any project or mailing!</div>
                        <div class="block-link" type="button"><span>Get Started</span> <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright?wid=27&hei=29&fmt=png-alpha</@ofbizScene7Url>" /></div>
                    </div>
                </a>
            </div>
            <div id="ENV_CustomOrder" class="column large-6 medium-6 small-12">
                <a href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>" title="Custom Orders | Envelopes.com">
                    <div class="custom-orders">
                        <img class="hp-image" src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-orders-redesign-banner-v2?wid=553&hei=200&fmt=png-alpha</@ofbizScene7Url>" alt="" />
                        <div class="block-title">Custom Orders</div>
                        <div class="block-desc">Can't find what you're looking for? <br> We can custom make it for you!</div>
                        <div class="block-link navyblue" type="button"><span>Learn More</span> <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright?wid=27&hei=29&fmt=png-alpha</@ofbizScene7Url>" /></div>
                    </div>
                </a>
            </div>
        </div>
        <#-----HP PROMOTION BANNER #1------>
        <div class="row margin-top-xl margin-bottom-xl text-center" id="hp-promo-banner1">
            <div class="tablet-desktop-only">
                <a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>" title="Mailing & Shipping Supplies For Your Business | Shop Now!">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/mailing-shipping-supplies-desktop-banner-1c?fmt=png-alpha&wid=1200</@ofbizScene7Url>" alt="Mailing & Shipping Supplies For Your Business | Shop Now!" title="Mailing & Shipping Supplies For Your Business | Shop Now!" />
                </a>
            </div>
            <div class="mobile-only">
                <a href="#" title="Mailing & Shipping Supplies For Your Business | Shop Now!">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/mailing-shipping-supplies-mobile-banner?fmt=png-alpha&amp;wid=768</@ofbizScene7Url>" alt="Mailing & Shipping Supplies For Your Business | Shop Now!" title="Mailing & Shipping Supplies For Your Business | Shop Now!" />
                </a>
            </div>
        </div>
        <#-----DESIGN SERVICE------>
        <div class="row margin-bottom-xl">
            <a class="design-service" href="<@ofbizUrl>/designoptions</@ofbizUrl>" title="High-Quality Envelopes Design Services">
                <div class="design-box tabular_row">
                    <div>
                        <div class="left-content">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/high-quality-envelopes-design-services-simply-succulent?fmt=png-alpha&wid=553&hei=286</@ofbizScene7Url>" alt="High-Quality Envelopes Design Services" />
                        </div>
                    </div>
                    <div>
                        <div class="right-content">
                            <div class="env-design-title">
                                <span>Get the most of our High-Quality</span>
                                <span>Envelopes Design Services</span>
                            </div>
                            <div class="env-design-desc">
                                <span>We offer a wide selection of custom-printed envelopes, cards, </span>
                                <span>invitations, and so much more. You can create personalized envelopes </span>
                                <span>with your own design or use one of our design templates to get started.</span>
                            </div>
                            <div class="env-design-link navyblue">
                                <span>Learn More</span>
                                <div class="arrow-ui thin-right no-margin"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </a>
        </div>
        <#-----CERTONA - RECOMMENDED FOR YOU------>
        <div class="row margin-top-xl margin-bottom-xl">
            <div class="section-header">
                <h2>Recommended for You</h2>
                <div>
                    <a href="<@ofbizUrl>#</@ofbizUrl>" title="Shop All Recommended">
                        <span>Shop All Recommended</span>
                        <div class="arrow-ui thin-right no-margin"></div>
                    </a> 
                </div>
            </div>
              <div bns-loadcertonalist class="padding-xxs"></div>  
        </div>
        <#-----CUSTOMER REVIEWS------>
        <div class="row customer-reviews margin-bottom-md padding-xxs">
            <div class="section-header">
                <h2>What Our Customers Are Saying</h2>
            </div>
            <div class="slideIt-container" style="height: 300px;">
                <div class="slideIt-left">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                </div>
                <div class="slideIt text-left">
                    <div>
                        <#list topReviewList as review>
                            <div class="review">
                                <div class="cr-header">
                                    <div class="cr-img">
                                        <img class="jqs-defer-img" data-src="<@ofbizScene7Url>/is/image/ActionEnvelope/${review.productId}?wid=75&amp;hei=75&amp;fmt=jpeg&amp;bgc=F7F8FA</@ofbizScene7Url>" src="data:image/png;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=" alt="${review.productName?if_exists}"/>
                                    </div>
                                    <div class="cr-header-right">
                                        <a href="<@ofbizUrl>/product/~category_id=${review.primaryProductCategoryId?default(null)}/~product_id=${review.productId}</@ofbizUrl>#reviews"><div class="rating-5_0"></div></a>
                                        <div class="cr-product">
                                            <a href="<@ofbizUrl>/product/~category_id=${review.primaryProductCategoryId?default(null)}/~product_id=${review.productId}</@ofbizUrl>">${review.productName?if_exists}</a>
                                        </div>
                                        <div class="cr-color">
                                            ${review.colorDescription?if_exists}
                                        </div>
                                    </div>
                                </div>
                                <div class="cr-review">
                                    <#--  <i class="fa fa-quote-left"></i>  -->
                                    <p>${review.productReview}</p>
                                    <#--  <i class="fa fa-quote-right"></i>  -->
                                </div>
                                <div class="cr-bottom-info-wrap">
                                    <div class="cr-date">
                                        <p>${review.createdStamp?date}</p>
                                    </div>
                                    <div class="cr-author text-right">
                                        <p>&mdash;${review.nickName}</p>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                </div>
            </div>
        </div>
        <hr />
        <#-----FOOTER COPY------>
        <div class="row margin-top-xl padding-xxs">
            <div class="hp-footer-content">
                <div class="column large-6 medium-12 small-12">
                    <h6>Largest Selection of Envelope Sizes, Styles, and Colors</h6>
                    <p>Looking for small or bulk quantities of mailing supplies? You have come to the right place. At Envelopes.com, we focus on providing our customers with the largest selection of envelope sizes, styles, and colors. We specialize in transforming drab stationery into custom works of art, with no shortage of charm, professionalism, or whatever you would like to convey. No matter what kind of envelopes you need or want, we have you covered in our inventory.</p>
                    <p>Combining quality printing, quick shipping, and great customer service, we strive to provide an unforgettable shopping experience each and every time you visit. With a low minimum quantity of 50 through bulk orders of over one million and up, you won't have any trouble finding ways to save big while getting excellent value on mailing materials.</p>
                    <p>Buy envelopes in bulk to get the best value.</p>
                    <h6>Available Plain or Printed</h6>
                    <p>Available plain and printed, our selection of mailing envelopes is overflowing with <a class="navyblue bold-element" href="<@ofbizUrl>/designoptions</@ofbizUrl>">ready-made envelope templates</a> that will add a distinct touch to your stationery or business mailings. Customers come to us for all types of events and needs!</p>
                    <p>Whether you have a corporate party or a big wedding bash to plan, we can help you create an envelope that will set the tone for your entire event. The #10 envelope is our most popular template for businesses, whereas the square flap envelope is our most popular template for cards, invitations, special announcements, and social correspondences. While shopping with us today, you should visit <a class="navyblue bold-element" href="<@ofbizUrl>/weddingShop</@ofbizUrl>">The Wedding Shop</a> to find everything you need for your wedding invitation suite in one convenient location: DIY invitations, envelope liners, and so much more.</p>
                    <p>Don't let the stress of tax season get the best of you. Stock up and stay prepared by browsing our assortment of W2, 1099, and federal mailing envelopes or visiting <a class="navyblue bold-element" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>">The Tax Shop</a> today. We also offer a wide variety of remittance templates, open-end templates, and specialty envelope templates that are unique to specific industries and businesses.</p>
                </div>
                <div class="column large-6 medium-12 small-12">
                    <h6>Custom printing and envelope design services</h6>
                    <p>In the market for colored paper, card stock, custom notecards, stationery, holiday cards, event invitations, or corporate letterhead? We offer a wide variety of ready-made templates that can be customized to your heart's content. Complete your printed project or customize your envelope by uploading your own artwork. See how simple it is to add printing options by taking a quick look at one of our video tutorials! <a class="navyblue bold-element" href="<@ofbizUrl>/designoptions</@ofbizUrl>">Our printing services</a> include one-color printing, two-color printing, and full-color printing. <a class="navyblue bold-element" href="<@ofbizUrl>/addressing</@ofbizUrl>">Variable Data Printing</a> is also available on most envelopes. We also offer white ink printing, which prints best on dark-colored papers and stocks.</p>
                    <h6>Direct Mail Services</h6>
                    <p>Being a business owner means wearing many hats at once, but mail shouldn't keep you up at night. Our <a class="navyblue bold-element" href="<@ofbizUrl>/directMailing</@ofbizUrl>">Direct Mailing Services</a> make it easy to target the audience you want to reach. We offer high-quality letters and notecards with addressed envelopes that we mail directly to your mailing list the next business day. Direct mail is perfect for introducing your business to potential customers, announcing special events or offers, and showing customers how much you care.</p>
                    <p>Starting from scratch or going completely custom? Check out our <a class="navyblue bold-element" href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>">Made to Order Shop</a>, where you can provide all the details about your envelope project and receive a free quote. Custom colors, flaps, sealing, sizes, stock, and windows all qualify! If you have any questions or concerns, one of our friendly customer service representatives is standing by to help in any way we can. Call, email, or live chat online with us today for additional information or further assistance.</p>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function() {
        truncateReview();
    });
</script>
<script>
    var closebtns = document.getElementsByClassName("close-x-button");
    var i;

    for (i = 0; i < closebtns.length; i++) {
        closebtns[i].addEventListener("click", function() {
            //this.parentElement.style.display = 'none';
            $('.swatchbook-top-banner').remove();

        });
    }
</script>

<script type='text/javascript' src="<@ofbizContentUrl>/html/js/util/bigNameCarousel.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/main.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>