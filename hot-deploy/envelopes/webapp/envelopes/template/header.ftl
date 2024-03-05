<#assign isSignedIn = request.getSession().getAttribute("userLogin")?has_content?string('true', 'false')/>
<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<div id="main-header" class="container header" style="background-color: #ffffff;">
    <!-- START MESSAGE -->
    <link href="<@ofbizContentUrl>/html/css/util/bigNameTextRotator.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
    <a class="bncRotator" href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>" title="HOT BUYS">
        <div class="envMessage">
            <#if currentTimestamp?default(now?datetime) lt "2020-05-30 00:00:00.000"?datetime>
                HOT DEALS: UP TO 75% OFF | <span data-reveal-id="AprilOffer2020">PLUS 10% OFF $750+, CODE: SAVEBIG</span> - SHOP NOW
            <#else>
                HOT DEALS: UP TO 75% OFF - LIMITED TIME ONLY - SHOP NOW
            </#if>
        </div>
    </a>
    <a class="bncRotator" href="<@ofbizUrl>/search?w=wedding+discount</@ofbizUrl>" title="Wedding Shop Clearance">
        <div class="envMessage">
            WEDDING SHOP CLEARANCE - UP TO 50% OFF - SAVE NOW
        </div>
    </a>
    <script type='text/javascript' src="<@ofbizContentUrl>/html/js/util/bigNameTextRotator.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>
    <!-- END MESSAGE -->
	<div id="helpBar" class="row help-bar">
		<div class="logoHeader">
			<a href="https://www.bigname.com" target="_blank">
                <img class="tabletDesktopImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/bigNameNewHPLogo?fmt=png-alpha&amp;wid=98</@ofbizScene7Url>" alt="Big Name" />
                <img class="mobileImg" src="https://actionenvelope3.scene7.com/is/image/ActionEnvelope/bigNameLogoHeader?fmt=png-alpha&wid=71" alt="Big Name" />
            </a>
            <a href="https://www.envelopes.com" target="_blank">
                <img class="tabletDesktopImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envNewHPLogo?fmt=png-alpha&amp;wid=80</@ofbizScene7Url>" alt="Envelopes.com" />
                <img class="mobileImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopesLogoHeader?fmt=png-alpha&wid=60</@ofbizScene7Url>" alt="Envelopes.com" />
            </a>
            <a href="https://www.folders.com" target="_blank">
                <img class="paddingBottom5 tabletDesktopImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/foldersNewHPLogo?fmt=png-alpha&amp;wid=65</@ofbizScene7Url>" alt="Folders.com" />
                <img class="mobileImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/foldersLogoHeader?fmt=png-alpha&wid=48</@ofbizScene7Url>" alt="Folders.com" />
            </a>
		</div>
        <div class="freeShippingHeader">
            <div class="freeShippingBanner">
                <img data-reveal-id="fship-promo2" src="<@ofbizScene7Url>/is/image/ActionEnvelope/hp-freeShippingLogoBanner?fmt=png-alpha</@ofbizScene7Url>" alt="Free Shipping Logo Banner">
                <div class="freeShippingBannerText" data-reveal-id="fship-promo2">
                    <p class="freeShippingText">free shipping on all orders over $${globalContext.freeShippingAmount?default("299")}</p>
                    <p class="freeShippingCode">Use Code <span class="textBold">FREE${globalContext.freeShippingAmount?default("299")} </span>at Checkout <span class="textUnderline">Details</span></p>
                </div>
            </div>
		</div>
		<div class="subscribeHeader">
			<a class="save5NavButton" data-reveal-id="stayInTheLoop" bns-stayintheloop-additionalsource="navigationSubscribe" data-key="navigationSubscribe">SAVE $10</a>
		</div>
		<div class="contactUs tablet-desktop-only-cell" >
            <div class="contactNavButton" data-dropdown-target="dropdown-helpBar" data-dropdown-options="hover shadowed(helpBar) ignore-reverse-dropdown">Contact Us <i class="fa fa-caret-down"></i></div>
		</div>
        <div id="dropdown-helpBar" class="drop-down" style="width: 200px">
			<a href="tel:1-877-683-5673"><i class="fa fa-phone"></i> 877-683-5673</a>
			<a href="javascript:void(0);" onclick="olark('api.box.expand')"><i class="fa fa-comment"></i> Click to Chat</a>
			<a href="http://support.envelopes.com/"><i class="fa fa-question-circle"></i> Support</a>
        </div>
		<div id="jqs-login-container" class="text-center logged-in">
			<span class="logged-in-text"></span>
			<a href="#" data-reveal-id="secure-layer" data-dest="login-layer" class="jqs-login-button button-regular">Log In / Reorder</a>
		</div>
    <#--Hidden Login button for triggerring Login Layer programatically across the site-->
		<div id="jqs-hidden-login-handle" class="hidden">
			<div class="jqs-hidden-login-button button-regular button-blue" data-reveal-id="secure-layer" data-dest="login-layer">Log In / Reorder</div>
		</div>
        <div class="cart_container cart">
            <a id="cartContainer" data-dropdown-target="dropdown-nav-cart" data-dropdown-options="hover reverse-horizontal ignore-reverse-dropdown" href="<@ofbizUrl>/cart</@ofbizUrl>">
                <span id="jqs-mini-cart-count">0</span>
                <i class="fa fa-shopping-cart"></i>
            </a>
        </div>
	</div>

	<div class="nav_2">
		<div class="menu_container">
			<div class="menu_icon" data-dropdown-target="dropdown-menuItems" data-dropdown-options="click shadowed(main-header) ignore-reverse-dropdown">
				<i class="fa fa-bars"></i><span style="position: absolute; left: -99999px">a</span>
			</div>
		</div>
		<div class="logo_container">
			<a href="<@ofbizUrl>/main</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envSiteLogoNewHP?fmt=png-alpha&amp;wid=179</@ofbizScene7Url>" alt="${siteName?if_exists} Logo" /></a>
		</div>
		<div class="search_container tablet-desktop-only">
			<#if currentView?exists && currentView == "main">
				<div id="sli-search" class="search" itemscope itemtype="http://schema.org/WebSite">
					<meta itemprop="url" content="https://${siteDomain?default("www.envelopes.com")}/"/>
					<i class="fa fa-search"></i>
					<form name="globalsearch" method="GET" action="<@ofbizUrl>/search</@ofbizUrl>" itemprop="potentialAction" itemscope itemtype="http://schema.org/SearchAction">
						<meta itemprop="target" content="<@ofbizUrl>/search</@ofbizUrl>?w={w}"/>
						<input itemprop="query-input" data-dropdown-target="dropdown-sli-search-results" data-dropdown-alternate-parent="sli-search" data-dropdown-options="focus shadowed(main-header) ignore-reverse-dropdown ignore-scroll" type="text" name="w" value="" placeholder="Search" autocomplete="off" />
					</form>
				</div>
			<#else>
				<div id="sli-search" class="search">
					<i class="fa fa-search"></i>
					<form name="globalsearch" method="GET" action="<@ofbizUrl>/search</@ofbizUrl>">
						<input data-dropdown-target="dropdown-sli-search-results" data-dropdown-alternate-parent="sli-search" data-dropdown-options="focus shadowed(main-header) ignore-reverse-dropdown ignore-scroll" type="text" name="w" value="" placeholder="Search" autocomplete="off" />
					</form>
				</div>
			</#if>
            <div class="popularSearches">
                <span>Popular:</span>
                <a href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>">Mailers</a>,
                <a href="<@ofbizUrl>/search?w=wedding+discount</@ofbizUrl>">Wedding Envelopes</a>,
                <a href="<@ofbizUrl>/backToBusinessShop</@ofbizUrl>">Back to Business</a>,
                <a href="<@ofbizUrl>/category?af=si:12x18%20si:13x19%20si:812x11%20si:11x17%20si:12x12%20si:812x14%20st:paper%20st:cardstock&sort=SIZE_SMALL</@ofbizUrl>">Paper &amp; Cardstock</a>
            </div>
            <div class="colorSwatches">
                <a title="Shop All Holiday Products" href="<@ofbizUrl>/holidayShop</@ofbizUrl>" class="csHoliday colSearch firstSwatch"><img src="<@ofbizContentUrl>/html/img/nav/holidayShopText.png</@ofbizContentUrl>" /><div class="hidden">Holiday</div></a>
                <a title="Shop All Red Products" href="<@ofbizUrl>/shopByColor/~category_id=RED</@ofbizUrl>" class="csRed colSearch"><div>Red</div></a>
                <a title="Shop All Green Products" href="<@ofbizUrl>/shopByColor/~category_id=GREEN</@ofbizUrl>" class="csGreen colSearch"><div>Green</div></a>
                <a title="Shop All White Products" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" class="csWhite colSearch"><div class="">White</div></a>
                <a title="Shop All Silver Products" href="<@ofbizUrl>/shopByColor/~category_id=SILVER_SILVERMETALLIC</@ofbizUrl>" class="csSilver colSearch"><div>Silver</div></a>
                <a title="Shop All Gold Products" href="<@ofbizUrl>/shopByColor/~category_id=GOLD</@ofbizUrl>" class="csGold colSearch"><div>Gold</div></a>
                <a title="Shop All Yellow Products" href="<@ofbizUrl>/shopByColor/~category_id=YELLOW</@ofbizUrl>" class="csYellow colSearch"><div>Yellow</div></a>
                <a title="Shop All Orange Products" href="<@ofbizUrl>/shopByColor/~category_id=ORANGE</@ofbizUrl>" class="csOrange colSearch"><div>Orange</div></a>
                <a title="Shop All Pink Products" href="<@ofbizUrl>/shopByColor/~category_id=PINK</@ofbizUrl>" class="csPink colSearch"><div>Pink</div></a>
                <a title="Shop All Blue Products" href="<@ofbizUrl>/shopByColor/~category_id=BLUE</@ofbizUrl>" class="csBlue colSearch"><div>Blue</div></a>
                <a title="Shop All Purple Products" href="<@ofbizUrl>/shopByColor/~category_id=PURPLE</@ofbizUrl>" class="csPurple colSearch"><div>Purple</div></a>
                <a title="Shop All Natural Products" href="<@ofbizUrl>/shopByColor/~category_id=NATURAL</@ofbizUrl>" class="csNatural colSearch"><div>Natural</div></a>
                <a title="Shop All Grocery Bag Products" href="<@ofbizUrl>/search?w=grocery+bag</@ofbizUrl>" class="csGroceryBag colSearch"><div>Grocery Bag</div></a>
                <a title="Shop All Gray Products" href="<@ofbizUrl>/shopByColor/~category_id=GRAY</@ofbizUrl>" class="csGray colSearch"><div>Gray</div></a>
                <a title="Shop All Black Products" href="<@ofbizUrl>/shopByColor/~category_id=BLACK</@ofbizUrl>" class="csBlack colSearch"><div>Black</div></a>
                <a title="Shop All Clear Products" href="<@ofbizUrl>/shopByColor/~category_id=CLEAR</@ofbizUrl>" class="csClear colSearch"><div>Clear</div></a>
                <a title="Shop All Metallic Products" href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" class="csMetallics colSearch"><div>Metallics</div></a>
                <a bns-optimizely="showNavigation" title="Shop All Swatchbook Products" href="<@ofbizUrl>/product/~category_id=PAPER/~product_id=SWATCHBOOK</@ofbizUrl>" class="csSwatchbook colSearch lastSwatch"><div class="csSwatchbookText">Swatchbook</div></a>
            </div>
		</div>
		<div class="headerRight" id="cart-account-section">
            <div class="quickLinks">
				<div class="search_button_container" data-dropdown-target="dropdown-mobile-search" data-dropdown-options="click mobile shadowed(main-header)" data-dropdown-alternate-parent="main-header">
						<i class="fa fa-search"></i>
					</div><div class="trade_pro_container">
					<a href="<@ofbizUrl>/tradePro</@ofbizUrl>">
						<img src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/tradeDiscountNewHP?wid=15&amp;fmt=png-alpha" alt="Trade Pro" title="Trade Pro" />
						<div>Trade Discount</div>
					</a>
				</div><div class="custom_quote_container">
					<a href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>">
						<i class="fa fa-calculator"></i>
						<div>Get a Quote</div>
					</a>
				</div><div class="sizeGuideContainer">
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>">
						<img src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/sizeGuideNewHP?wid=20&amp;fmt=png-alpha" alt="Size Guide" title="Size Guide" />
						<div>Shop by Size</div>
					</a>
				</div><div class="cart_container cart">
                    <a id="cartContainer" data-dropdown-target="dropdown-nav-cart" data-dropdown-options="hover reverse-horizontal ignore-reverse-dropdown" href="<@ofbizUrl>/cart</@ofbizUrl>">
                        <span id="jqs-mini-cart-count">0</span>
                        <i class="fa fa-shopping-cart"></i>
                    </a>
                </div>
            </div>
			<div class="freeShip desktop-only">
                <div class="navSliderText1">Most Products Available Plain or Printed</div>
                <div class="navSliderText2">3-Day Rush Production Available</div>
                <div class="navSliderText3">Low Minimum Quantities</div>
                <div class="navSliderText4">Largest Variety of Sizes & Styles</div>
            </div>
		</div>
	</div>
    <div bns-optimizely="showNavigation" class="row text-center megaMenuContainer">
        <div id="megaMenuContainer">
            <div class="dropdown-hover-navigation" data-dropdown-target="dropdown-Envelopes" data-dropdown-alternate-parent="megaMenuContainer" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-100">
                Envelopes <i class="fa fa-caret-down"></i>
            </div><div class="dropdown-hover-navigation" data-dropdown-target="dropdown-Paper__Cardstock" data-dropdown-alternate-parent="megaMenuContainer" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-100">
                Paper &amp; Cardstock <i class="fa fa-caret-down"></i>
            </div><div class="dropdown-hover-navigation" data-dropdown-target="dropdown-Custom_Printing" data-dropdown-alternate-parent="megaMenuContainer" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-100">
                Custom Printing <i class="fa fa-caret-down"></i>
            </div><div class="dropdown-hover-navigation" data-dropdown-target="dropdown-Mailroom_Supplies" data-dropdown-alternate-parent="megaMenuContainer" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-100">
                Mailers &amp; Folders <i class="fa fa-caret-down"></i>
            </div><#--<div class="dropdown-hover-navigation" data-dropdown-target="dropdown-clearance" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-100">
                Ways To Shop <i class="fa fa-caret-down"></i>
            </div>--><div>
                <a href="<@ofbizUrl>/new-arrivals</@ofbizUrl>">New Arrivals</a>
            </div><div>
                <a href="<@ofbizUrl>/backToBusinessShop</@ofbizUrl>">Back To Business</a>
            </div><#if currentTimestamp?default(now?datetime) gte "2020-10-08 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-31 23:59:59.000"?datetime><div class="holidayShop">
                    <a href="<@ofbizUrl>/holidayShop</@ofbizUrl>">Holiday Shop <i class="fa fa-gift"></i></a>
                </div><#else><div>
                    <a href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>">Hot Deals <i class="fa fa-caret-right"></i></a>
                </div>
            </#if>
        </div>
    </div>
    <#if currentView != "main">
        <#if (currentTimestamp?default(now?datetime) gte "2020-11-03 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-08 23:59:59.000"?datetime)>
            <div data-reveal-id="EBFSale2020" style="display: block; background-color: #000000; color: #ffffff; font-size: 18px; text-align: center; padding: 10px; letter-spacing: 1px;">
                <div style="margin:5px 0;display:inline-block;">
                    LIMITED TIME! <span style="font-weight: bold; color: #ffff00;">20% OFF</span> $300+ AND <span style="font-weight: bold; color: #ffff00;">FREE SHIPPING</span> | CODE: <span style="font-weight: bold; color: #ffff00;">EBF20</span>
                </div>
            </div>
        </#if>
    </#if>
	<#if currentView != "main" && currentView != "designoptions">
	   <div class="row bannerRow" >
            <!-- CUSTOM PRINTING BANNER -->
	        <div class="columns large-4 medium-12 small-12 no-padding">
				<a data-ga-promo data-ga-promo-id="hp_thin_banner_custom_printing" data-ga-promo-name="Custom Printing" data-ga-promo-creative="hp_thin_banner_custom_printing" data-ga-promo-position="Site Wide Banner Spot 1" href="<@ofbizUrl>/designoptions</@ofbizUrl>" class="tablet-desktop-only jqs-trackHeaderBanner"><div class="siteBannerText bannerNavy"><strong>Custom Printing is Easy </strong>with Envelopes.com</div></a>
			</div>
            <!-- SWACHBOOK SITEWIDE BANNER -->
            <div class="columns large-4 medium-12 small-12 no-padding">
                <a data-ga-promo data-ga-promo-id="hp_thin_banner_swatchbook" data-ga-promo-name="Swatchbook" data-ga-promo-creative="hp_thin_banner_swatchbook" data-ga-promo-position="Site Wide Banner Spot 2" href="<@ofbizUrl>/product/~category_id=PAPER/~product_id=SWATCHBOOK</@ofbizUrl>" class="jqs-trackHeaderBanner"><div class="siteBannerText bannerGray bannerSwatchbook"><strong class="swatchbookBlock">Our Swatchbook Ships Free!</strong> <span style="border: 1px solid #ffffff; padding: 1px;" class="tablet-desktop-only-inline-block">Get it Now <i class="fa fa-caret-right"></i></span></div></a>
            </div>
            <!-- FREESHIP PROMO BANNER -->
			<div class="columns large-4 medium-12 small-12 no-padding">
                <div class="siteBannerText bannerBlue">
                    <strong>Envelopes.com is open + here to continue serving you!</strong>
                </div>
			</div>
	   </div>
	</#if>
</div>

<!-- MOBILE NAV DROPDOWN -->
<div id="dropdown-menuItems" class="drop-down padding-xxs" style="width: 100%; left: 0px; top: 114px;">
	<a href="<@ofbizUrl>/account</@ofbizUrl>" class="row padding-xxs mobileContentReorderRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/e_icon-myAccount?fmt=png-alpha</@ofbizScene7Url>" alt="My Account/Re-order" />
		<div class="mobileNavFont">My Account/Re-order <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
	<div bns-mobilenavdropdown class="row margin-top-xxs padding-xxs mobileContentRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/e_icon-invitationEnvelopes?fmt=png-alpha</@ofbizScene7Url>" alt="Invitation Envelopes" />
		<div class="mobileNavFont">Envelopes <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</div>
	<div class="mobileNavDropdown hidden">
		<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" class="row margin-top-xxs padding-xxs ">
			<div class="mobileNavFont">Shop By Size <i class="fa fa-caret-right"></i></div>
		</a>
		<a href="<@ofbizUrl>/shopByColor</@ofbizUrl>" class="row margin-top-xxs padding-xxs ">
			<div class="mobileNavFont">Shop By Color <i class="fa fa-caret-right"></i></div>
		</a>
		<a href="<@ofbizUrl>/category/~category_id=BUSINESS</@ofbizUrl>" class="row margin-top-xxs padding-xxs ">
			<div class="mobileNavFont">Business Envelopes <i class="fa fa-caret-right"></i></div>
		</a>
		<a href="<@ofbizUrl>/category/~category_id=INVITATION</@ofbizUrl>" class="row margin-top-xxs padding-xxs ">
			<div class="mobileNavFont">Invitation Envelopes <i class="fa fa-caret-right"></i></div>
		</a>
		<a href="<@ofbizUrl>/category/~category_id=SPECIALTY_USE</@ofbizUrl>" class="row margin-top-xxs padding-xxs ">
			<div class="mobileNavFont">Specialty Envelopes <i class="fa fa-caret-right"></i></div>
		</a>
		<a href="<@ofbizUrl>/search?w=accessories&c=0</@ofbizUrl>" class="row margin-top-xxs padding-xxs ">
			<div class="mobileNavFont">Accessories <i class="fa fa-caret-right"></i></div>
		</a>
	</div>
	<a href="<@ofbizUrl>/category?af=si:12x18%20si:13x19%20si:812x11%20si:11x17%20si:12x12%20si:812x14%20st:paper%20st:cardstock&sort=SIZE_SMALL</@ofbizUrl>" class="row margin-top-xxs padding-xxs mobileContentRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/e_icon-paperAndCardstock?fmt=png-alpha</@ofbizScene7Url>" alt="Paper &amp; Cardstock" />
		<div class="mobileNavFont">Paper &amp; Cardstock <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
	<a href="<@ofbizUrl>/category?af=use:invitation%20st:zfold%20st:petals%20st:pockets%20st:gatefold%20st:diy</@ofbizUrl>" class="row margin-top-xxs padding-xxs mobileContentRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/e_icon-invitationsAndDIY?fmt=png-alpha</@ofbizScene7Url>" alt="Invitations &amp; DIY" />
		<div class="mobileNavFont">DIY Invitations <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
	<div bns-mobilenavdropdown class="row margin-top-xxs padding-xxs mobileContentRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/mailroomSuppliesMobileNav?fmt=png-alpha&amp;wid=41&amp;hei=41</@ofbizScene7Url>" alt="Invitation Envelopes" />
		<div class="mobileNavFont">Mailers <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</div>
	<div class="mobileNavDropdown hidden">
		<a href="<@ofbizUrl>/category?af=use:shipping%20st:bubblelined%20st:expansion%20st:paperboardmailers%20st:specialtyuse%20st:tyvek%20st:plasticmailers%20st:luxmailers&sort=mOSt_pOpULAR</@ofbizUrl>" class="row margin-top-xxs padding-xxs ">
			<div class="mobileNavFont">Mailroom Envelopes <i class="fa fa-caret-right"></i></div>
		</a>
		<a href="<@ofbizUrl>/search?w=mailing+tubes</@ofbizUrl>" class="row margin-top-xxs padding-xxs ">
			<div class="mobileNavFont">Shipping &amp; Packaging <i class="fa fa-caret-right"></i></div>
		</a>
	</div>
	<a href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>" class="row margin-top-xxs padding-xxs mobileContentClearanceRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/e_icon-clearance?fmt=png-alpha</@ofbizScene7Url>" alt="Clearance" />
		<div class="mobileNavFont">Hot Deals <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
	<a href="<@ofbizUrl>/new-arrivals</@ofbizUrl>" class="row margin-top-xxs padding-xxs mobileContentHolidayShopRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/e_icon-newArrivals?fmt=png-alpha</@ofbizScene7Url>" alt="New Arrivals" />
		<div class="mobileNavFont">New Arrivals <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
	<a href="<@ofbizUrl>/weddingShop</@ofbizUrl>" class="row margin-top-xxs padding-xxs mobileContentWeddingShopRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/e_icon-weddingShop?fmt=png-alpha</@ofbizScene7Url>" alt="Wedding Shop" />
		<div class="mobileNavFont">Wedding Shop <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
	<a href="<@ofbizUrl>/envelope-sizes</@ofbizUrl>" class="row margin-top-xxs padding-xxs mobileContentRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopeSizeGuideMobileNav?fmt=png-alpha&amp;wid=41&amp;hei=41</@ofbizScene7Url>" alt="Envelope Size Guide" />
		<div class="mobileNavFont">Envelope Size Guide <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
	<a href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>" class="row margin-top-xxs padding-xxs mobileContentRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/quoteCalcMobileNav?fmt=png-alpha&amp;wid=41&amp;hei=41</@ofbizScene7Url>" alt="Presentation Folders" />
		<div class="mobileNavFont">Custom Quotes <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
	<a href="<@ofbizUrl>/tradePro</@ofbizUrl>" class="row margin-top-xxs padding-xxs mobileContentRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/tradeDiscountMobileNav?fmt=png-alpha&amp;wid=41&amp;hei=41</@ofbizScene7Url>" alt="Notecards" />
		<div class="mobileNavFont">Trade Discount Program <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
	<a href="<@ofbizUrl>/coupons</@ofbizUrl>" class="row margin-top-xxs padding-xxs mobileContentRow">
		<img class="left margin-right-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/todaysDealsMobileNav?fmt=png-alpha&amp;wid=41&amp;hei=41</@ofbizScene7Url>" alt="Labels" />
		<div class="mobileNavFont">Today's Deals <i class="fa fa-angle-right mobileNavRightArrows"></i></div>
	</a>
</div>

<div id="dropdown-sli-search-results" class="drop-down sli-search-results"></div>

<div id="dropdown-nav-cart" class="drop-down nav-cart">
	<div class="popup-border-fade">
		<div class="nav-cart-header popup-title">
			<i class="fa fa-shopping-cart padding-right-xxs"></i> Your Cart
		</div>
		<div id="jqs-mini-cart-items" class="nav-cart-items"></div>
		<div class="row nav-cart-footer padding-xxs">
			<div>
				<a href="<@ofbizUrl>/checkout</@ofbizUrl>"><div class="jqs-checkout button-regular checkout button-cta">Checkout</div></a>
				<a href="<@ofbizUrl>/cart</@ofbizUrl>"><div class="button-regular button-non-cta">Edit Cart</div></a>
			</div>
			<div class="nav-total text-right padding-right-xxs">
				Subtotal: <span bns-mini-cart-total class="jqs-mini-cart-total">$0.00</span>
			</div>
		</div>
	</div>
</div>

<div id="dropdown-mobile-search" class="drop-down padding-right-xxs padding-left-xxs">
	<form name="globalsearch" method="GET" action="<@ofbizUrl>/search</@ofbizUrl>"><input data-dropdown-target="dropdown-sli-search-results" data-dropdown-alternate-parent="dropdown-mobile-search" data-dropdown-parent="dropdown-mobile-search" data-dropdown-options="focus shadowed(main-header) ignore-reverse-dropdown" class="padding-top-xxs padding-bottom-xxs margin-top-xxs" type="text" name="w" value="" placeholder="What are you looking for?" /></form>
</div>

<div bns-sitewidetabcontent class="desktop-only siteWideTabContent">
	<div bns-sitewidetabbuttoncontainer class="siteWideTabButtonContainer">
		<div bns-sitewidetabbutton="siteWideTodaysDeals" class="siteWideTabButton" data-ga-promo data-ga-promo-id="ws_todays_deal" data-ga-promo-name="Todays Deals" data-ga-promo-creative="ws_tab" data-ga-promo-position="bottom">
            <i class="fa fa-usd"></i>
            TODAY'S DEALS
		</div>
        <div bns-sitewidetabbutton="siteWideRecentlyViewed" class="siteWideTabButton" data-ga-promo data-ga-promo-id="ws_recently_viewed" data-ga-promo-name="Recently Viewed" data-ga-promo-creative="ws_tab" data-ga-promo-position="bottom">
            RECENTLY VIEWED
        </div>
	</div>
	<div bns-sitewideid="siteWideTodaysDeals" class="siteWideBodyContent entireCouponList">
		<h4>All of Our Special Offers in One Place!</h4>
		<div class="jqs-couponList"></div>
	</div>
    <div bns-sitewideid="siteWideRecentlyViewed" class="siteWideBodyContent recentlyViewedItemList">
        <h4>Your Recently Viewed Items</h4>
        <p>Want to pick up where you left off?  Here are a few of your recently viewed items.</p>
        <div bns-recentlyviewedcontent></div>
    </div>
</div>

<div id="dropdown-Envelopes" class="drop-down">
    <div class="megaMenuDropdownContent">
        <div class="row">
            <div class="columns large-3 no-padding megaMenuFirstColumns">
                <h2>Shop by Size</h2>
                <div class="row">
                    <div class="columns large-6">
                        <h3>
                            <a bns-mega-menu-level="3" href="<@ofbizUrl>/shopBySize</@ofbizUrl>">Shop All Sizes</a>
                        </h3>
                        <h3 bns-mega-menu-level="3">
                            Business Envelopes
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:418x912</@ofbizUrl>">#10 (4 1/8 x 9 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:378x878</@ofbizUrl>">#9 (3 7/8 x 8 7/8)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=BUSINESS?af=si:358x612</@ofbizUrl>">#6 3/4 (3 5/8 x 6 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:6x9</@ofbizUrl>">6 x 9</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:9x12</@ofbizUrl>">9 x 12</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:10x13</@ofbizUrl>">10 x 13</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=BUSINESS</@ofbizUrl>">All Business Envelopes <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h3 bns-mega-menu-level="3">
                            Mini Envelopes
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:214x312</@ofbizUrl>">#1 Coin (2 1/4 x 3 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:21116x31116</@ofbizUrl>">#17 Mini (2 11/16 x 3 11/16)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=credit+card+sleeve</@ofbizUrl>">Credit Card Sleeves (2 3/8 x 3 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:312x612</@ofbizUrl>">#7 Coin (3 1/2 x 6 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:338x6%20st:openend</@ofbizUrl>">#6 Coin (3 3/8 x 6)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:318x512</@ofbizUrl>">#5 1/2 Coin (3 1/8 x 5 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:278x514</@ofbizUrl>">#5 Coin (2 7/8 x 5 1/4)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:3x478</@ofbizUrl>">#4 1/2 Coin (3 x 4 7/8)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:3x412</@ofbizUrl>">#4 Coin (3 x 4 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:212x414</@ofbizUrl>">#3 Coin (2 1/2 x 4 1/4)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:2x2%20use:business</@ofbizUrl>">2 x 2 Square Coin </a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:11116x234</@ofbizUrl>">#00 Coin (1 11/16 x 2 3/4)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=MINI_ENVELOPES?af=st:minienvelopes%20use:invitation%20si:218x358</@ofbizUrl>">#3 Mini (2 1/8 x 3 5/8)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=%2356+mini</@ofbizUrl>">#56 Mini (3 x 4 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=%2363+mini</@ofbizUrl>">#63 Mini (2 1/2 x 4 1/4)</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=MINI_ENVELOPES</@ofbizUrl>">All Mini Envelopes <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                    <div class="columns large-6">
                        <h3 bns-mega-menu-level="3">
                            Invitation Envelopes
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:358x518%20st:squareflap%20st:contourflap</@ofbizUrl>">A1 (3 5/8 x 5 1/8)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:438x534%20st:squareflap%20st:contourflap</@ofbizUrl>">A2 (4 3/8 x 5 3/4)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:414x614%20st:squareflap%20st:contourflap</@ofbizUrl>">A4 (4 1/4 x 6 1/4)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:434x612%20st:squareflap%20st:contourflap</@ofbizUrl>">A6 (4 3/4 x 6 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:514x714</@ofbizUrl>">A7 (5 1/4 x 7 1/4)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:512x818&sort=PRICE_HIGH</@ofbizUrl>">A8 (5 1/2 x 8 1/8)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:534x834</@ofbizUrl>">A9 (5 3/4 x 8 3/4)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:6x912&sort=MOST_POPULAR</@ofbizUrl>">A10 (6 x 9 1/2)</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=INVITATION</@ofbizUrl>">All Invitation Envelopes <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h3 bns-mega-menu-level="3">
                            Square Envelopes
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:2x2%20use:business</@ofbizUrl>">2 x 2 Square Coin </a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:314x314%20st:square</@ofbizUrl>">3 1/4 x 3 1/4</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:4x4%20st:square</@ofbizUrl>">4 x 4</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:5x5%20st:square</@ofbizUrl>">5 x 5</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:514x514%20st:square</@ofbizUrl>">5 1/4 x 5 1/4</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:512x512%20st:square</@ofbizUrl>">5 1/2 x 5 1/2</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:534x534%20st:square</@ofbizUrl>">5 3/4 x 5 3/4</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:6x6%20st:square</@ofbizUrl>">6 x 6</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:614x614%20st:square</@ofbizUrl>">6 1/4 x 6 1/4</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:612x612%20st:square</@ofbizUrl>">6 1/2 x 6 1/2</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:7x7%20st:square</@ofbizUrl>">7 x 7</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:712x712%20st:square</@ofbizUrl>">7 1/2 x 7 1/2</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:8x8%20st:square</@ofbizUrl>">8 x 8</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:812x812%20st:square</@ofbizUrl>">8 1/2 x 8 1/2</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:9x9%20st:square</@ofbizUrl>">9 x 9</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:912x912%20st:square</@ofbizUrl>">9 1/2 x 9 1/2</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:10x10%20st:square</@ofbizUrl>">10 x 10</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:1212x1212%20st:square</@ofbizUrl>">12 1/2 x 12 1/2</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:13x13%20st:square</@ofbizUrl>">13 x 13</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=SQUARE</@ofbizUrl>">All Square Envelopes <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-3 no-padding megaMenuFirstColumns">
                <h2>Shop by Category</h2>
                <div class="row">
                    <div class="columns large-6">
                        <h3 bns-mega-menu-level="3">
                            Business Envelopes
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=REGULAR</@ofbizUrl>">Regular Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=WINDOW</@ofbizUrl>">Window Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=peel%20and%20press</@ofbizUrl>">Peel &amp; Press Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=SQUARE_FLAP</@ofbizUrl>">Square Flap Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=BOOKLET</@ofbizUrl>">Booklet Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=OPEN_END</@ofbizUrl>">Open End Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=CLASP</@ofbizUrl>">Clasp Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=REMITTANCE</@ofbizUrl>">Remittance Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:11116x234%20si:214x312%20si:212x414%20si:278x514%20si:3x412%20si:3x478%20si:318x512%20si:338x6%20si:312x612%20si:2x2%20st:openend&sort=MOST_POPULAR</@ofbizUrl>">Coin Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Jumbo+Envelopes</@ofbizUrl>">Jumbo Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=EXPANSION</@ofbizUrl>">Expansion Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=PAPERBOARD_MAILERS</@ofbizUrl>">Paperboard Mailers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=TYVEK</@ofbizUrl>">Tyvek Envelopes</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=BUSINESS</@ofbizUrl>">All Business Envelopes <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h3 bns-mega-menu-level="3">
                            Invitation Envelopes
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=SQUARE_FLAP</@ofbizUrl>">Square Flap Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=CONTOUR_FLAP</@ofbizUrl>">Contour Flap Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=POINTED_FLAP</@ofbizUrl>">Pointed Flap Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined%20col:linedenvelopes</@ofbizUrl>">Lined Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=SQUARE</@ofbizUrl>">Square Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=MINI_ENVELOPES</@ofbizUrl>">Mini Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=photo+greeting+envelopes</@ofbizUrl>">Photo Greeting Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=photo+holders</@ofbizUrl>">Photo Holders</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=INVITATION</@ofbizUrl>">All Invitation Envelopes <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                    <div class="columns large-6">
                        <h3 bns-mega-menu-level="3">
                            Specialty Envelopes
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Airmail+Envelopes</@ofbizUrl>">Airmail Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=First+class+Envelopes</@ofbizUrl>">First Class Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=si:11116x234%20si:214x312%20si:212x414%20si:278x514%20si:3x412%20si:3x478%20si:318x512%20si:338x6%20si:312x612%20si:2x2%20st:openend&sort=MOST_POPULAR</@ofbizUrl>">Coin Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?&w=gift%20cards&c=0</@ofbizUrl>">Gift Card Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=credit+card+sleeve</@ofbizUrl>">Credit Card Sleeves</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=currency+envelopes</@ofbizUrl>">Currency Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=EXPANSION</@ofbizUrl>">Expansion Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=TYVEK</@ofbizUrl>">Tyvek Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=conformer+mailers</@ofbizUrl>">Conformer&reg; Mailers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=W-2+Envelopes</@ofbizUrl>">W-2 Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=full+face+window&c=0</@ofbizUrl>">Full Face Window Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=button+%26+string+envelopes</@ofbizUrl>">Button &amp; String Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=photo+holders</@ofbizUrl>">Photo Holders</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=SPECIALTY_USE</@ofbizUrl>">All Specialty Envelopes <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Shop By Brand</h2>
                        <div class="row">
                            <div class="columns large-12">
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/luxpaper</@ofbizUrl>">LUXPaper&trade;</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=reich+paper</@ofbizUrl>">Reich Paper</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=neenah</@ofbizUrl>">Neenah Paper</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=fedrigoni</@ofbizUrl>">Fedrigoni</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=strathmore</@ofbizUrl>">Strathmore&reg;</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=conformer+mailers</@ofbizUrl>">Conformer&reg;</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=tops</@ofbizUrl>">Tops&trade;</a>
                                </div>
                                <div class="seeAll">
                                    <a bns-mega-menu-level="5" href="<@ofbizUrl>/shopByBrand</@ofbizUrl>">All Brands <i class="fa fa-caret-right"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Shop By Color</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" bns-shopByColor="white">White</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=NATURAL</@ofbizUrl>" bns-shopByColor="natural">Natural</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=grocery+bag</@ofbizUrl>" bns-shopByColor="groceryBag">Grocery Bag</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=GRAY</@ofbizUrl>" bns-shopByColor="grey">Grey</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=BLACK</@ofbizUrl>" bns-shopByColor="black">Black</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=CLEAR</@ofbizUrl>" bns-shopByColor="clear">Clear</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=SILVER</@ofbizUrl>" bns-shopByColor="silver">Silver</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=GOLD</@ofbizUrl>" bns-shopByColor="gold">Gold</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=YELLOW</@ofbizUrl>" bns-shopByColor="yellow">Yellow</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=ORANGE</@ofbizUrl>" bns-shopByColor="orange">Orange</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=RED</@ofbizUrl>" bns-shopByColor="red">Red</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=PINK</@ofbizUrl>" bns-shopByColor="pink">Pink</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=BLUE</@ofbizUrl>" bns-shopByColor="blue">Blue</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=GREEN</@ofbizUrl>" bns-shopByColor="green">Green</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=PURPLE</@ofbizUrl>" bns-shopByColor="purple">Purple</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=BROWN</@ofbizUrl>" bns-shopByColor="brown">Brown</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/shopByColor</@ofbizUrl>">All Colors <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Shop by Collection</h2>
						<div class="row">
							<div class="columns large-6">
								<div>
									<a bns-mega-menu-level="4" href="<@ofbizUrl>/luxpaper</@ofbizUrl>">LUXPaper</a>
								</div>
								<div>
									<a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>">Metallics</a>
								</div>
								<div>
									<a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=recycled</@ofbizUrl>">Recycled</a>
								</div>
								<div>
									<a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined%20col:linedenvelopes</@ofbizUrl>">LUXFoil Lined</a>
								</div>
								<div>
									<a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=linens</@ofbizUrl>">Linen</a>
								</div>
							</div>
							<div class="columns large-6">
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=neons</@ofbizUrl>">Neon</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=tyvek</@ofbizUrl>">Tyvek</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=woodgrain</@ofbizUrl>">Woodgrain</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>">Tax Envelopes</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=holiday%20printed%20envelopes</@ofbizUrl>">Holiday Prints</a>
                                </div>
							</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Features</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>"><i class="fa fa-gift" style="color: #fba044; font-size: 23px; vertical-align: middle;"></i> Holiday Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/new-arrivals</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-newArrivals?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="New Arrivals" /> New Arrivals</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopBySize</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopBySizeMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Size" /> Shop By Size</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopByColorMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Color" /> Shop By Color</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/directMailing</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-directMail?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Direct Mail Service" /> Direct Mail Service</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=folders</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-folders?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Folders" /> Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/envelope-sizes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-envelopeSizeGuide?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Envelope Size Guide" /> Envelope Size Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-swatchbook?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Swatchbook" /> Swatchbook</a>
                        </div> 
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-madeToOrder?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Made to Order" /> Made to Order</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
                        </div>
                        <#--  <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                        </div>  -->
                        <h2>Accessories</h2>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=liners</@ofbizUrl>">Envelope Liners</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+tags</@ofbizUrl>">Gift Tags</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=hole+punchers</@ofbizUrl>">Hole Punchers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=accessories</@ofbizUrl>">All Accessories <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Hot Deals</h2>
                        <div class="seeAll">
                            <a class="textRed" bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>">Up to 75% Off <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Folders.com</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-track_event="Header|Mega Menu|Folders Shop Now" onClick="window.open('https://www.folders.com'); return false;" href=""><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/megaMenuFoldersImg?fmt=jpeg&amp;wid=174&amp;hei=325</@ofbizScene7Url>" alt="New Arrivals"/></a>
                        </div>
                    </div>
                </div>
            </div>    
        </div>
    </div>
</div>

<div id="dropdown-Paper__Cardstock" class="drop-down">
    <div class="megaMenuDropdownContent">
        <div class="row">
            <div class="columns large-4 no-padding megaMenuFirstColumns">
                <h2>Shop by Size</h2>
                <div class="row">
                    <div class="columns large-6">
                        <h3 bns-mega-menu-level="3">
                            Paper
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=st:paper%20use:paperandmore%20si:812x11</@ofbizUrl>">8 1/2 x 11 Paper</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=use:paperandmore%20si:11x17%20st:paper</@ofbizUrl>">11 x 17 Paper</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=st:paper%20use:paperandmore%20si:12x12</@ofbizUrl>">12 x 12 Paper</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=st:paper%20use:paperandmore%20si:12x18</@ofbizUrl>">12 x 18 Paper</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=st:paper%20use:paperandmore%20si:13x19</@ofbizUrl>">13 x 19 Paper</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=PAPER</@ofbizUrl>">All Paper <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h3 bns-mega-menu-level="3">
                            Notecards
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=use:paperandmore%20si:518x7%20st:notecards%20prodtype:products</@ofbizUrl>">A7 (5 1/8 x 7)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=use:paperandmore%20si:458x614%20st:notecards%20prodtype:products</@ofbizUrl>">A6 (4 5/8 x 6 1/4)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=use:paperandmore%20si:414x512%20st:notecards%20prodtype:products&sort=SIZE_SMALL</@ofbizUrl>">A2 (4 1/4 x 5 1/2)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=use:paperandmore%20si:312x478%20st:notecards%20prodtype:products&sort=SIZE_SMALL</@ofbizUrl>">A1 (3 1/2 x 4 7/8)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=mini+notecards</@ofbizUrl>">#17 Mini (2 9/16 x 3 9/16)</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=si:2x312&w=Flat%20cards</@ofbizUrl>">#3 Mini (2 x 3 1/2)</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category?af=st:notecards</@ofbizUrl>">All Notecards <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
					<div class="columns large-6">
                        <h3 bns-mega-menu-level="3">
                            Cardstock
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=st:cardstock%20use:paperandmore%20si:812x11</@ofbizUrl>">8 1/2 x 11 Cardstock</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=use:paperandmore%20si:11x17%20st:cardstock</@ofbizUrl>">11 x 17 Cardstock</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=st:cardstock%20use:paperandmore%20si:12x12</@ofbizUrl>">12 x 12 Cardstock</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=st:cardstock%20use:paperandmore%20si:12x18</@ofbizUrl>">12 x 18 Cardstock</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category?af=st:cardstock%20use:paperandmore%20si:13x19</@ofbizUrl>">13 x 19 Cardstock</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=CARDSTOCK</@ofbizUrl>">All Cardstock <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Shop By Brand</h2>
                        <div class="row">
                            <div class="columns large-12">
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/luxpaper</@ofbizUrl>">LUXPaper&trade;</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=reich+paper</@ofbizUrl>">Reich Paper</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=neenah</@ofbizUrl>">Neenah Paper</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=fedrigoni</@ofbizUrl>">Fedrigoni</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=strathmore</@ofbizUrl>">Strathmore&reg;</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=conformer+mailers</@ofbizUrl>">Conformer&reg;</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=tops</@ofbizUrl>">Tops&trade;</a>
                                </div>
                                <div class="seeAll">
                                    <a bns-mega-menu-level="5" href="<@ofbizUrl>/shopByBrand</@ofbizUrl>">All Brands <i class="fa fa-caret-right"></i></a>
                                </div>
                            </div>
                        </div>
					</div>
                </div>
            </div>
			<div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Shop By Color</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" bns-shopByColor="white">White</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=NATURAL</@ofbizUrl>" bns-shopByColor="natural">Natural</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=grocery+bag</@ofbizUrl>" bns-shopByColor="groceryBag">Grocery Bag</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=GRAY</@ofbizUrl>" bns-shopByColor="grey">Grey</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=BLACK</@ofbizUrl>" bns-shopByColor="black">Black</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=CLEAR</@ofbizUrl>" bns-shopByColor="clear">Clear</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=SILVER</@ofbizUrl>" bns-shopByColor="silver">Silver</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=GOLD</@ofbizUrl>" bns-shopByColor="gold">Gold</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=YELLOW</@ofbizUrl>" bns-shopByColor="yellow">Yellow</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=ORANGE</@ofbizUrl>" bns-shopByColor="orange">Orange</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=RED</@ofbizUrl>" bns-shopByColor="red">Red</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=PINK</@ofbizUrl>" bns-shopByColor="pink">Pink</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=BLUE</@ofbizUrl>" bns-shopByColor="blue">Blue</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=GREEN</@ofbizUrl>" bns-shopByColor="green">Green</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=PURPLE</@ofbizUrl>" bns-shopByColor="purple">Purple</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=BROWN</@ofbizUrl>" bns-shopByColor="brown">Brown</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/shopByColor</@ofbizUrl>">All Colors <i class="fa fa-caret-right"></i></a>
                        </div>
					</div>
				</div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Shop by Category</h2>
                <div class="row">
                    <div class="columns large-12">
                        <h3 bns-mega-menu-level="3">
                            Notecards
                        </h3>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Flat%20cards</@ofbizUrl>">Flatcards</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Folded+cards</@ofbizUrl>">Folded Cards</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=mini+notecards</@ofbizUrl>">Mini Cards</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category?af=st:notecards</@ofbizUrl>">All Notecards <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h3 bns-mega-menu-level="3">
                            DIY Invitations
                        </h3>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category?af=use:invitation%20st:zfold%20st:petals%20st:pockets%20st:gatefold%20st:diy</@ofbizUrl>">All DIY Invitations <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h3 bns-mega-menu-level="3">
                            Notebooks &amp; Padfolios
                        </h3>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=BOOKS_PADS_AND_PLANNERS</@ofbizUrl>">All Notebooks &amp; Padfolios <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Shop by Collection</h2>
                        <div class="row">
                            <div class="columns large-12">
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/?af=use:paperandmore%20col:brights</@ofbizUrl>">Brights</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/?af=use:paperandmore%20col:cotton</@ofbizUrl>">Cotton</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=recycled+paper</@ofbizUrl>">Recycled</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/?af=use:paperandmore%20col:metallics</@ofbizUrl>">Metallics</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=linens</@ofbizUrl>">Linen</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/?af=use:paperandmore%20col:parchments</@ofbizUrl>">Parchment</a>
                                </div>
                                <div class="seeAll">
                                    <a bns-mega-menu-level="5" href="<@ofbizUrl>/category?af=si:12x18%20si:13x19%20si:812x11%20si:11x17%20si:12x12%20si:812x14%20st:paper%20st:cardstock&sort=SIZE_SMALL</@ofbizUrl>">All Paper &amp; Cardstock <i class="fa fa-caret-right"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Features</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>"><i class="fa fa-gift" style="color: #fba044; font-size: 23px; vertical-align: middle;"></i> Holiday Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/new-arrivals</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-newArrivals?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="New Arrivals" /> New Arrivals</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopBySize</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopBySizeMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Size" /> Shop By Size</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopByColorMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Color" /> Shop By Color</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/directMailing</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-directMail?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Direct Mail Service" /> Direct Mail Service</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=folders</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-folders?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Folders" /> Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/envelope-sizes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-envelopeSizeGuide?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Envelope Size Guide" /> Envelope Size Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-swatchbook?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Swatchbook" /> Swatchbook</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-madeToOrder?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Made to Order" /> Made to Order</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
                        </div>
                        <#--  <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                        </div>  -->
                        <h2>Accessories</h2>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=liners</@ofbizUrl>">Envelope Liners</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=belly+bands</@ofbizUrl>">Belly Bands</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+tags</@ofbizUrl>">Gift Tags</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=hole+punchers</@ofbizUrl>">Hole Punchers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=accessories</@ofbizUrl>">All Accessories <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Hot Deals</h2>
                        <div class="seeAll">
                            <a class="textRed" bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>">Up to 75% Off <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Folders.com</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-track_event="Header|Mega Menu|Folders Shop Now" bns-mega-menu-level="4" href="" onClick="window.open('https://www.folders.com'); return false;"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/megaMenuFoldersImg?fmt=jpeg&amp;wid=174&amp;hei=325</@ofbizScene7Url>" alt="New Arrivals"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dropdown-Custom_Printing" class="drop-down">
    <div class="megaMenuDropdownContent">
        <div class="row">
            <div class="columns large-3 no-padding megaMenuFirstColumns">
                <h2>Print Services</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/designoptions</@ofbizUrl>">Custom Printing</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/addressing</@ofbizUrl>">Recipient Addressing</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/white-ink</@ofbizUrl>">White Ink Printing</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/directMailing</@ofbizUrl>"><img class="margin-top-xxs" src="<@ofbizScene7Url>/is/image/ActionEnvelope/directMailMegaMenu?fmt=jpeg</@ofbizScene7Url>" alt="Direct Mail Promo"></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-3 no-padding megaMenuFirstColumns">
                <h2>Features</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>"><i class="fa fa-gift" style="color: #fba044; font-size: 23px; vertical-align: middle;"></i> Holiday Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/new-arrivals</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-newArrivals?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="New Arrivals" /> New Arrivals</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopBySize</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopBySizeMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Size" /> Shop By Size</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopByColorMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Color" /> Shop By Color</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/directMailing</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-directMail?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Direct Mail Service" /> Direct Mail Service</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=folders</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-folders?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Folders" /> Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/envelope-sizes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-envelopeSizeGuide?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Envelope Size Guide" /> Envelope Size Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-swatchbook?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Swatchbook" /> Swatchbook</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-madeToOrder?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Made to Order" /> Made to Order</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
                        </div>
                        <#--  <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                        </div>  -->
                    </div>
                </div>
            </div>
            <div class="columns large-3 no-padding megaMenuFirstColumns">
                <h2>Accessories</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=liners</@ofbizUrl>">Envelope Liners</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=belly+bands</@ofbizUrl>">Belly Bands</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+tags</@ofbizUrl>">Gift Tags</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=hole+punchers</@ofbizUrl>">Hole Punchers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=accessories</@ofbizUrl>">All Accessories <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Hot Deals</h2>
                        <div class="seeAll">
                            <a class="textRed" bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>">Up to 75% Off <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-3 no-padding megaMenuFirstColumns">
                <h2>Folders.com</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div class="text-center">
                            <a bns-track_event="Header|Mega Menu|Folders Shop Now" bns-mega-menu-level="4" href="" onClick="window.open('https://www.folders.com'); return false;"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/megaMenuFoldersImg?fmt=jpeg&amp;wid=174&amp;hei=325</@ofbizScene7Url>" alt="New Arrivals"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dropdown-DIY_Invitations" class="drop-down">
    <div class="megaMenuDropdownContent">
        <div class="row">
            <div class="columns large-4 no-padding megaMenuFirstColumns">
                <h2>Shop by Style</h2>
                <div class="row">
                    <div class="columns large-6">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Petal+Invitations</@ofbizUrl>">Petal Invitations</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Pocket+Invitations</@ofbizUrl>">Pocket Invitations</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Flat%20cards</@ofbizUrl>">Flat Cards</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Folded+cards</@ofbizUrl>">Folded Cards</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Square+Cards</@ofbizUrl>">Square Cards</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=mini+cards</@ofbizUrl>">Mini Cards</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gatefold</@ofbizUrl>">Gatefold Cards</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=zfold</@ofbizUrl>">Z-Fold Cards</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category?af=use:invitation%20st:zfold%20st:petals%20st:pockets%20st:gatefold%20st:diy</@ofbizUrl>">See all DIY Invitations <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-4 no-padding megaMenuFirstColumns">
                <h2>Features</h2>
                <div class="row">
                    <div class="columns large-6">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>"><i class="fa fa-gift" style="color: #fba044; font-size: 23px; vertical-align: middle;"></i> Holiday Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
                        </div>
                        <#--  <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                        </div>  -->
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/new-arrivals</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-newArrivals?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="New Arrivals" /> New Arrivals</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopBySize</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopBySizeMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Size" /> Shop By Size</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopByColorMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Color" /> Shop By Color</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/directMailing</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-directMail?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Direct Mail Service" /> Direct Mail Service</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=folders</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-folders?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Folders" /> Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/envelope-sizes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-envelopeSizeGuide?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Envelope Size Guide" /> Envelope Size Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-swatchbook?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Swatchbook" /> Swatchbook</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/made-to-order</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-madeToOrder?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Made to Order" /> Made to Order</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Accessories</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=liners</@ofbizUrl>">Envelope Liners</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=belly+bands</@ofbizUrl>">Belly Bands</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+tags</@ofbizUrl>">Gift Tags</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=hole+punchers</@ofbizUrl>">Hole Punchers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=accessories</@ofbizUrl>">All Accessories <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Hot Deals</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div class="seeAll">
                            <a class="textRed" bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>">Up to 75% Off <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dropdown-Mailroom_Supplies" class="drop-down">
    <div class="megaMenuDropdownContent">
        <div class="row">
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Envelopes</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Cello&c=0</@ofbizUrl>">Cello Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=cog1:clear%20cog2:clear_30lbglassine&w=glassine&c=0</@ofbizUrl>">Glassine Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=DOCUMENT</@ofbizUrl>">Document Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=EXPANSION</@ofbizUrl>">Expansion Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=Jumbo+Envelopes</@ofbizUrl>">Jumbo Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=TYVEK</@ofbizUrl>">Tyvek Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=tamper+Evident</@ofbizUrl>">Tamper Evident Envelopes</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>">All Mailroom Envelopes <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Mailers</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=BUBBLE_LINED</@ofbizUrl>">Bubble Mailers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=PADDED_MAILERS</@ofbizUrl>">Padded Mailers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=PLASTIC_MAILERS</@ofbizUrl>">Plastic Mailers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=PAPERBOARD_MAILERS</@ofbizUrl>">Paperboard Mailers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=conformer+mailers</@ofbizUrl>">Conformer&reg; Mailers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=SHIPPING</@ofbizUrl>">All Mailers <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Shipping and Packaging</h2>
                        <div class="row">
                            <div class="columns large-12">
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=corrugated+boxes</@ofbizUrl>">Corrugated Boxes</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+boxes&c=0</@ofbizUrl>">Gift Boxes</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=mailing+tubes</@ofbizUrl>">Mailing Tubes</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Folders</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=folders</@ofbizUrl>">Presentation Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=legal+size+folders</@ofbizUrl>">Legal Size Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=small+folders</@ofbizUrl>">Small &amp; Welcome Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=certificate+holders</@ofbizUrl>">Certificate Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=certificates</@ofbizUrl>">Paper Certificates</a>
                        </div>
                        <h2>Bags</h2>
                        <div class="row">
                            <div class="columns large-12">
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>">Gift Bags</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=clear+bags</@ofbizUrl>">Clear Bags</a>
                                </div>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=wine+bottle+bags</@ofbizUrl>">Wine Bottle Bags</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Features</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>"><i class="fa fa-gift" style="color: #fba044; font-size: 23px; vertical-align: middle;"></i> Holiday Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/new-arrivals</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-newArrivals?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="New Arrivals" /> New Arrivals</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopBySize</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopBySizeMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Size" /> Shop By Size</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopByColorMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Color" /> Shop By Color</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/directMailing</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-directMail?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Direct Mail Service" /> Direct Mail Service</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=folders</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-folders?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Folders" /> Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/envelope-sizes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-envelopeSizeGuide?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Envelope Size Guide" /> Envelope Size Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-swatchbook?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Swatchbook" /> Swatchbook</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-madeToOrder?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Made to Order" /> Made to Order</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
                        </div>
                        <#--  <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                        </div>  -->
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Accessories</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=liners</@ofbizUrl>">Envelope Liners</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=belly+bands</@ofbizUrl>">Belly Bands</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+tags</@ofbizUrl>">Gift Tags</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=hole+punchers</@ofbizUrl>">Hole Punchers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=accessories</@ofbizUrl>">All Accessories <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-1 no-padding megaMenuFirstColumns">
                <h2>Hot Deals</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div class="seeAll">
                            <a class="textRed" bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>">Up to 75% Off <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-2 no-padding megaMenuFirstColumns">
                <h2>Folders.com</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-track_event="Header|Mega Menu|Folders Shop Now" bns-mega-menu-level="4" href="" onClick="window.open('https://www.folders.com'); return false;"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/megaMenuFoldersImg?fmt=jpeg&amp;wid=174&amp;hei=325</@ofbizScene7Url>" alt="New Arrivals"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dropdown-clearance" class="drop-down">
    <div class="megaMenuDropdownContent">
        <div class="row">
            <div class="columns large-6 no-padding megaMenuFirstColumns">
                <h2>Ways To Shop</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopBySize</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopBySizeMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Size" /> Shop By Size</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopByColorMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Shop By Color" /> Shop By Color</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/new-arrivals</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-newArrivals?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="New Arrivals" /> New Arrivals</a>
                        </div>
                    </div>
                </div>
                <h2 class="margin-top-xs">Hot Deals</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>">Envelopes <i class="fa fa-caret-right"></i></a>
                        </div>
                        <div>
                            <a href="<@ofbizUrl>/search?page=2&w=clearance%20paper</@ofbizUrl>">Paper &amp; Cardstock <i class="fa fa-caret-right"></i></a>
                        </div>
                        <div>
                            <a href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>">All Hot Deals <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="columns large-6 no-padding megaMenuFirstColumns">
                <h2>Features</h2>
                <div class="row">
                    <div class="columns large-12">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>"><i class="fa fa-gift" style="color: #fba044; font-size: 23px; vertical-align: middle;"></i> Holiday Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/directMailing</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-directMail?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Direct Mail Service" /> Direct Mail Service</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=folders</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-folders?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Folders" /> Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/envelope-sizes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-envelopeSizeGuide?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Envelope Size Guide" /> Envelope Size Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-swatchbook?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Swatchbook" /> Swatchbook</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-madeToOrder?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Made to Order" /> Made to Order</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
                        </div>
                        <#--  <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                        </div>  -->
                    </div>
                </div>
            </div>
            <a bns-track_event="Header|Mega Menu|Folders Shop Now" bns-mega-menu-level="4" href="" onClick="window.open('https://www.folders.com'); return false;"><img class="margin-top-xxs waysToShopFoldersImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/megaMenuFoldersImgHorizontal?fmt=jpeg&amp;wid=280</@ofbizScene7Url>" alt="Folders.com"/></a>
        </div>
    </div>
</div>
<div id="dropdown-myAccount" class="drop-down">
    <div style="display: table; width: 100%; font-size: 12px;">
        <div class="jqs-recentOrderHistory my-account-left">
            <a class="jqs-recentProdHeader recentOrderHistoryHeader" href="<@ofbizUrl>/all-orders</@ofbizUrl>">Re-Order</a>
            <div class="row recent-order-bottom">
                <div class="columns small-7">
                    <a href="<@ofbizUrl>/all-orders</@ofbizUrl>" style="font-size: 15px; color: #f4712b; display: inline-block; padding: 7px;">View All Past <span style="white-space: nowrap; display: inline-block;">Orders <i class="fa fa-caret-right"></span></i></a>
                </div>
            </div>
        </div>
        <div class="my-account-right">
            <h4 style="font-size: 15px; color: #858d93;">Hello, <span class="jqs-firstName"></span>!</h4>
            <a href="<@ofbizUrl>/account</@ofbizUrl>" style="display: block;">View Your Account</a>
            <h5 class="margin-top-xxs" style="font-size: 12px;">Orders:</h5>
            <a href="<@ofbizUrl>/open-orders</@ofbizUrl>" style="display: block;">View Open Orders</a>
            <a href="<@ofbizUrl>/all-orders</@ofbizUrl>" style="display: block;">View All Orders</a>
            <a href="<@ofbizUrl>/all-orders</@ofbizUrl>" style="display: block;">Reorder Center <i class="fa fa-refresh"></i></a>
            <h5 class="margin-top-xxs" style="font-size: 12px;">Account:</h5>
            <a href="<@ofbizUrl>/addressBooks</@ofbizUrl>" style="display: block;">Manage Address Book</a>
            <a href="<@ofbizUrl>/addresses</@ofbizUrl>" style="display: block;">Manage Shipping Address</a>
            <a href="<@ofbizUrl>/users</@ofbizUrl>" style="display: block;">Account Users</a>
            <h5 class="margin-top-xxs" style="font-size: 12px;">Files:</h5>
            <a href="<@ofbizUrl>/savedDesigns</@ofbizUrl>" style="display: block;">Saved Designs</a>
            <a href="<@ofbizUrl>/uploadedFiles</@ofbizUrl>" style="display: block;">Uploaded Images/Files</a>
            <a href="<@ofbizUrl>/proofApproval</@ofbizUrl>" style="display: block;">Proof Approval Center</a>
            <h5 class="margin-top-xs" style="font-size: 12px;">Loyalty Points:</h5>
            <div class="jqs-headerLoyaltyPoints" style="font-size: 28px; line-height: 30px; color: #f4712b;">0</div>
            <div class="jqs-headerLoyaltyPercent">0% Discount*</div>
            <p class="points-disclaimer">* Points accumulate from orders in the past 12 months.</p>
            <a href="#" class="jqs-logout-button" data-dest="logout" style="position: absolute; bottom: 5px; right: 10px;">Log Out</a>
        </div>
    </div>
</div>
<script>$().updateHeaderLogin();</script>