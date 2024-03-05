<link href="<@ofbizContentUrl>/html/css/landing/summer-shop.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/util/bigNameCarousel.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />

<div class="bnc_summer_shop">
	
	<div class="tablet-desktop-only margin-top-xs margin-bottom-xs">
		<#include "../includes/breadcrumbs.ftl" />
	</div>

    <#-- TOP BANNER -->
	<div class="bnc_summer_shop_top_banner">
		<img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/the-summer-shop-mobile-banner-v1?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="The Summer Shop | Envelopes.com" />

        <div class="summer_shop_banner_wrap">

            <div class="summer-shop-banner-image">
                <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/the-summer-shop-desktop-banner-v1?wid=670&fmt=png-alpha</@ofbizScene7Url>" alt="The Summer Shop | Envelopes.com" />
            </div>

            <div class="summer_shop_banner_content">
                <h1>The Summer Shop</h1>
                <h2>Trending Colors for 2021</h2>
            </div>
        </div>

	</div>

	<#-- CUSTOM NAVIGATION -->
    <nav role="bnc_summershop_nav1">
        <input type="checkbox" id="summer-shop-button">
        <label for="summer-shop-button" onclick></label>
        <ul>
            <li><a href="<@ofbizUrl>/shopByColor</@ofbizUrl>" title="Shop All Colors" target="_blank">Shop All Colors</a></li>
            <li><a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" title="Shop All Sizes" target="_blank">Shop All Sizes</a></li>
            <li><a href="<@ofbizUrl>/weddingShop</@ofbizUrl>" title="The Wedding Shop" target="_blank">The Wedding Shop</a></li>
            <#--  <li><a href="<@ofbizUrl>#</@ofbizUrl>" title="Back To School Shop">Back To School Shop</a></li>  -->
        </ul>
    </nav>

    <#-- CUSTOM SLIDESHOW -->
    <div bns-carousel="desktopHPImage" class="desktopBanners text-center relative updatedEnvHpBanner tablet-desktop-only">
        <div bns-carouse-image-container>
            <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/shopByColor/~category_id=BLUE_BABYBLUE</@ofbizUrl>" title="LUX Baby Blue&trade; | Envelopes.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/luxpaper-baby-blue-theme-summer-shop-desktop-banner-1360x350?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="LUX Baby Blue&trade; | Envelopes.com" />
            </a>
            <a bns-image_index="1" href="<@ofbizUrl>/shopByColor/~category_id=GREEN_LIMELIGHT</@ofbizUrl>" title="LUX Limelight&trade; | Envelopes.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/luxpaper-limelight-theme-summer-shop-desktop-banner-1360x350?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="LUX Limelight&trade; | Envelopes.com" />
            </a>
            <a bns-image_index="2" href="<@ofbizUrl>/shopByColor/~category_id=YELLOW_LEMONADE</@ofbizUrl>" title="LUX Lemonade&trade; | Envelopes.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/luxpaper-lemonade-theme-summer-shop-desktop-banner-1360x350?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="LUX Lemonade&trade; | Envelopes.com" />
            </a>
        </div>
    </div>
    <div bns-carousel="mobileHPImage" class="mobile-only">
        <div bns-carouse-image-container>
            <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/shopByColor/~category_id=BLUE_BABYBLUE</@ofbizUrl>" title="LUX Baby Blue&trade; | Envelopes.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/luxpaper-baby-blue-theme-summer-shop-mobile-banner-768x220?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="LUX Baby Blue&trade; | Envelopes.com" />
            </a>
            <a bns-image_index="1" href="<@ofbizUrl>/shopByColor/~category_id=GREEN_LIMELIGHT</@ofbizUrl>" title="LUX Limelight&trade; | Envelopes.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/luxpaper-limelight-theme-summer-shop-mobile-banner-768x220?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="LUX Limelight&trade; | Envelopes.com" />
            </a>
            <a bns-image_index="2" href="<@ofbizUrl>/shopByColor/~category_id=YELLOW_LEMONADE</@ofbizUrl>" title="LUX Lemonade&trade; | Envelopes.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/luxpaper-lemonade-theme-summer-shop-mobile-banner-768x220?fmt=png-alpha&amp;wid=768&amp;ts=3</@ofbizScene7Url>" alt="LUX Lemonade&trade; | Envelopes.com" />
            </a>
        </div>
    </div>

    <#-- CUSTOM PRODUCT GRID -->
    <section class="summer-prod-grid">
        <ul>
            <li class="glow-theme">
                <a href="<@ofbizUrl>/search?af=cog2:yellow_lemonade cog2:blue_babyblue cog2:green_limelight use:invitation</@ofbizUrl>" title="Invitation Envelopes">
                    <figure>
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/summer-shop-invitation-envelopes-thumbnail?wid=570&fmt=png-alpha</@ofbizScene7Url>" alt="Invitation Envelopes">
                    </figure>
                    <h3>Invitation Envelopes</h3>
                    <p>Find just the right style for your invitation envelopes in our standout LUX summer hues. Choose from Baby Blue, Lemonade or Limelight to create a stylish and high quality invitation suite.</p>
                    <button type="button">Shop Now</button>
                </a>
            </li>
            <li class="glow-theme">
                <a href="<@ofbizUrl>/search?af=cog2:yellow_lemonade cog2:green_limelight cog2:blue_babyblue si:418x912</@ofbizUrl>" title="#10 Envelopes">
                    <figure>
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/summer-shop-number-ten-envelopes-thumbnail?wid=570&fmt=png-alpha</@ofbizScene7Url>" alt="#10 Envelopes">
                    </figure>
                    <h3>#10 Envelopes</h3>
                    <p>Shop our most popular business envelope in bright summer hues! For checks, statements, letters, invoices and more, #10 envelopes are the perfect fit. Available in a variety of #10 styles.</p>
                    <button type="button">Shop Now</button>
                </a>
            </li>
            <li class="glow-theme">
                <a href="<@ofbizUrl>/search?af=cog2:yellow_lemonade cog2:blue_babyblue cog2:green_limelight st:cardstock</@ofbizUrl>" title="Cardstock">
                    <figure>
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/summer-shop-cardstock-envelopes-thumbnail-v2?wid=570&fmt=png-alpha</@ofbizScene7Url>" alt="Cardstock">
                    </figure>
                    <h3>Cardstock</h3>
                    <p>Whether it's for arts &amp; crafts or invitations for your event - stand out with Baby Blue, Lemonade and Limelight cardstock! Available in a variety of popular sizes.</p>
                    <button type="button">Shop Now</button>
                </a>
            </li>
            <li class="glow-theme">
                <a href="<@ofbizUrl>/search?af=cog2:blue_babyblue cog2:yellow_lemonade cog2:green_limelight st:paper</@ofbizUrl>" title="Paper">
                    <figure>
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/summer-shop-paper-envelopes-thumbnail-v2?wid=570&fmt=png-alpha</@ofbizScene7Url>" alt="Paper">
                    </figure>
                    <h3>Paper</h3>
                    <p>Whether you're in need of stationery for your business or bright colored paper for flyers - stand out with Baby Blue, Lemonade and Limelight paper! Available in a variety of popular sizes.</p>
                    <button type="button">Shop Now</button>
                </a>
            </li>
            <li class="glow-theme">
                <a href="<@ofbizUrl>/search?af=cog2:blue_babyblue cog2:green_limelight cog2:yellow_lemonade st:foldedcards</@ofbizUrl>" title="Folded Cards">
                    <figure>
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/summer-shop-folded-cards-thumbnail?wid=570&fmt=png-alpha</@ofbizScene7Url>" alt="Folded Cards">
                    </figure>
                    <h3>Folded Cards</h3>
                    <p>Let your invitations and notecards stand out with our Baby Blue, Lemonade and Limelight folded cards! Available in a variety of popular sizes.</p>
                    <button type="button">Shop Now</button>
                </a>
            </li>
            <li class="glow-theme">
                <a href="<@ofbizUrl>/search?w=flat cards&af=cog2:yellow_lemonade cog2:green_limelight cog2:blue_babyblue&cnt=50</@ofbizUrl>" title="Flat Cards">
                    <figure>
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/summer-shop-flat-cards-thumbnail?wid=570&fmt=png-alpha</@ofbizScene7Url>" alt="Flat Cards">
                    </figure>
                    <h3>Flat Cards</h3>
                    <p>Perfect for business cards, personal stationery, invitations and more - our bold and bright Baby Blue, Lemonade and Limelight flat cards are the perfect choice.</p>
                    <button type="button">Shop Now</button>
                </a>
            </li>
        </ul>
    </section>

</div>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/util/bigNameCarousel.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>