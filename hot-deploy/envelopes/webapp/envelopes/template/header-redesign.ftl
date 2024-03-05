<#assign isSignedIn = request.getSession().getAttribute("userLogin")?has_content?string('true', 'false')/>
<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<header id="main-header" class="site_header">
    <div class="tabular_row header_content-1">
        <a href="https://www.folders.com" target="_blank" class="tablet_desktop-table_cell" style="width: 106px;">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foldersWhite</@ofbizScene7Url>?fmt=png-alpha&amp;wid=65" alt="Folders.com">
        </a>
        <a href="https://www.bigname.com" target="_blank" class="tablet_desktop-table_cell" style="width: 139px;">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/bigNameWhite</@ofbizScene7Url>?fmt=png-alpha&amp;wid=98" alt="Big Name" title="Big Name">
        </a>
        <#if (currentTimestamp?default(now?datetime) gte "2021-12-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-24 23:59:59.000"?datetime)>
        <div class="header_promotion" data-reveal-id="holidayDeadlines2020">
            <i class="fa fa-gift tablet_desktop-inline_block" style="color: #fe0000; margin: 0px 5px; font-size: 25px;"></i>
            <span class="solid_text">GET YOUR HOLIDAY ORDERS BEFORE CHRISTMAS</span>
            <i class="fa fa-circle tablet_desktop-inline_block" style="color: #fe0000; margin: 0px 10px; font-size: 7px;"></i>
            <span class="tablet_desktop-inline_block" style="text-transform:uppercase; text-decoration:underline;">View Order Deadlines</span>
        </div>
        <#else>
        <div class="header_promotion" data-reveal-id="fship-promo2">
            <i class="fa fa-truck tablet_desktop-inline_block" style="color: #00A7E0; margin: 0px 5px; font-size: 25px;"></i>
            <span class="solid_text">FREE SHIPPING ON ALL ORDERS OVER $${freeShippingAmount?default("99")}</span>
            <i class="fa fa-circle tablet_desktop-inline_block" style="color: #7ECDEE; margin: 0px 10px; font-size: 7px;"></i>
            <span class="tablet_desktop-inline_block">Use Code <strong style="font-size: 14px;">FREE${freeShippingAmount?default("99")}</strong> at Checkout</span>
        </div>
        </#if>
        <#--SAVE TEN BLOCK-->
        <div class="tablet_desktop-table_cell" style="width: 134px;" onclick="_ltk.Popup.openManualByName('Popup2021-Envelopes Holiday-Manual');">
            <span class="solid_text">SAVE $10</span>
        </div>
    </div>
    <div class="header_content-2 tablet_desktop-block">
        <a href="<@ofbizUrl>/customEnvelopes</@ofbizUrl>">
            Get a Quote
        </a>
        <a href="http://support.envelopes.com/">
            Contact Us
        </a>
        <#--  <a href="<@ofbizUrl>/open-orders</@ofbizUrl>">
            Track Order
        </a>  -->
        <a href="<@ofbizUrl>/tradePro</@ofbizUrl>">
            Trade Discount
        </a>
    </div>
    <div class="tabular_row header_content-3 border-line-bottom">
        <div class="mobile-table_cell" style="width: 60px;" data-dropdown-target="dropdown-menuItems" data-dropdown-options="click shadowed(main-header) ignore-reverse-dropdown">
            <i class="fa fa-bars tablet-desktop-only"></i>
            <img class="mobile-only" src="<@ofbizContentUrl>/html/img/icon/bars-new.svg</@ofbizContentUrl>" />
        </div>
        <div class="mobile-table_cell" data-dropdown-target="dropdown-mobile-search" data-dropdown-options="click mobile shadowed(main-header)" data-dropdown-alternate-parent="main-header" style="width: 60px;" >
            <i class="fa fa-search tablet-desktop-only"></i>
            <img class="mobile-only" src="<@ofbizContentUrl>/html/img/icon/search-new.svg</@ofbizContentUrl>" />
        </div>
        <a class="site_logo_container" href="<@ofbizUrl>/main</@ofbizUrl>">
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-22 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-logo-holiday-theme</@ofbizScene7Url>?fmt=png-alpha&amp;wid=200" alt="${siteName?if_exists} Logo" title="${siteName?if_exists} Logo" />
            <#else>
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envSiteLogoNewHP</@ofbizScene7Url>?fmt=png-alpha&amp;wid=179" alt="${siteName?if_exists} Logo" title="${siteName?if_exists} Logo" />
            </#if>
        </a>
        <div class="tablet_desktop-table_cell">
            <div class="search_input">
                <div id="sli-search" class="tabular_row">
                    <i class="fa fa-search"></i>
                    <form name="globalsearch" method="GET" action="<@ofbizUrl>/search</@ofbizUrl>" style="padding: 0px;">
                        <input data-dropdown-target="dropdown-sli-search-results" data-dropdown-alternate-parent="sli-search" data-dropdown-options="focus shadowed(main-header) ignore-reverse-dropdown ignore-scroll" type="text" name="w" value=""  placeholder="What can we help you find?" autocomplete="off" />
                    </form>
                </div>
            </div>
            <div class="env_row desktop-block" style="margin-top: 10px; text-align: center;">
                <div>Shop by Color:</div>
                <div class="color_list">
                    <#if (currentTimestamp?default(now?datetime) gte "2021-10-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/holidayShop</@ofbizUrl>"><span class="hoverUIElementText hoverUIElementTop">HOLIDAY</span><i class="fa fa-gift" style="color: red; font-size: 18px; padding: 0; vertical-align: top;"></i></span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=RED</@ofbizUrl>" style="background-color: #f10013;"><span class="hoverUIElementText hoverUIElementTop">RED</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=GREEN</@ofbizUrl>" style="background-color: #39a657;"><span class="hoverUIElementText hoverUIElementTop">GREEN</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" style="border: 1px solid #CACACA; background-color: #FFFFFF;"><span class="hoverUIElementText hoverUIElementTop">WHITE</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=SILVER_SILVERMETALLIC</@ofbizUrl>" style="border: 1px solid #CACACA; background: rgb(240,239,239); background: linear-gradient(0deg, rgb(197 197 197) 0%, rgba(255,255,255,1) 55%, rgb(179 179 179) 100%);"><span class="hoverUIElementText hoverUIElementTop">SILVER</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=GOLD</@ofbizUrl>" style="border: 1px solid #CACACA; background: rgb(240,228,198); background: linear-gradient(0deg, rgb(202 167 81) 0%, rgba(255,255,255,1) 55%, rgb(224 182 74) 100%);"><span class="hoverUIElementText hoverUIElementTop">GOLD</span></a>
                    </#if>
                    <#if (currentTimestamp?default(now?datetime) gte "2022-01-01 00:00:00.000"?datetime)>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" style="border: 1px solid #CACACA; background-color: #FFFFFF;"><span class="hoverUIElementText hoverUIElementTop">WHITE</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=SILVER_SILVERMETALLIC</@ofbizUrl>" style="border: 1px solid #CACACA; background: rgb(240,239,239); background: linear-gradient(0deg, rgb(197 197 197) 0%, rgba(255,255,255,1) 55%, rgb(179 179 179) 100%);"><span class="hoverUIElementText hoverUIElementTop">SILVER</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=GOLD</@ofbizUrl>" style="border: 1px solid #CACACA; background: rgb(240,228,198); background: linear-gradient(0deg, rgb(202 167 81) 0%, rgba(255,255,255,1) 55%, rgb(224 182 74) 100%);"><span class="hoverUIElementText hoverUIElementTop">GOLD</span></a>
                    </#if>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=YELLOW</@ofbizUrl>" style="background-color: #FDC316;"><span class="hoverUIElementText hoverUIElementTop">YELLOW</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=ORANGE</@ofbizUrl>" style="background-color: #FF7531;"><span class="hoverUIElementText hoverUIElementTop">ORANGE</span></a>
                    <#if (currentTimestamp?default(now?datetime) gte "2022-01-01 00:00:00.000"?datetime)>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=RED</@ofbizUrl>" style="background-color: #f10013;"><span class="hoverUIElementText hoverUIElementTop">RED</span></a>
                    </#if>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=PINK</@ofbizUrl>" style="background-color: #ec559e;"><span class="hoverUIElementText hoverUIElementTop">PINK</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=BLUE</@ofbizUrl>" style="background-color: #0d96c5;"><span class="hoverUIElementText hoverUIElementTop">BLUE</span></a>
                    <#if (currentTimestamp?default(now?datetime) gte "2022-01-01 00:00:00.000"?datetime)>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=GREEN</@ofbizUrl>" style="background-color: #39a657;"><span class="hoverUIElementText hoverUIElementTop">GREEN</span></a>
                    </#if>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=PURPLE</@ofbizUrl>" style="background-color: #a48ab7;"><span class="hoverUIElementText hoverUIElementTop">PURPLE</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=NATURAL</@ofbizUrl>" style="background-color: #e5deca;"><span class="hoverUIElementText hoverUIElementTop">NATURAL</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/search?w=grocery+bag</@ofbizUrl>" style="background-color: #b59771;"><span class="hoverUIElementText hoverUIElementTop grocerybag">GROCERY BAG</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=BROWN</@ofbizUrl>" style="background-color: #623224;"><span class="hoverUIElementText hoverUIElementTop brown">BROWN</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=GRAY</@ofbizUrl>" style="background-color: #797977;"><span class="hoverUIElementText hoverUIElementTop">GRAY</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=BLACK</@ofbizUrl>" style="background-color: #1b1b1b;"><span class="hoverUIElementText hoverUIElementTop">BLACK</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/shopByColor/~category_id=CLEAR</@ofbizUrl>" style="background-color: #e5e5e5;"><span class="hoverUIElementText hoverUIElementTop">CLEAR</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" style="background-color: #cdc0b8;"><span class="hoverUIElementText hoverUIElementTop">METALLIC</span></a>
                    <a class="glow hoverUIElement" href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK</@ofbizUrl>" style="background: rgb(251,148,56); background: linear-gradient(90deg, rgba(251,148,56,1) 0%, rgba(217,216,43,1) 25%, rgba(138,216,79,1) 50%, rgba(43,199,221,1) 75%, rgba(61,154,246,1) 100%);"><span class="hoverUIElementText hoverUIElementTop swatchbook">SWATCHBOOK</span></a>
                </div>
                <div style="border-left: 2px solid #c4c4c4; padding-left: 5px;">
                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" class="solid_blue_text">SHOP BY SIZE</a>
                </div>
            </div>
        </div>
        <a href="<@ofbizUrl>/all-orders</@ofbizUrl>" class="desktop-table_cell reorder">
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-06 23:59:59.000"?datetime)>
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-reorder-icon-holiday-theme-v2?fmt=png-alpha&amp;wid=41</@ofbizScene7Url>" alt="Re-Order Icon" />
            <#elseif (currentTimestamp?default(now?datetime) gte "2021-11-22 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-reorder-icon-holiday-theme-v1?fmt=png-alpha&amp;wid=41</@ofbizScene7Url>" alt="Re-Order Icon" />
            <#else>
            <i class="fa fa-refresh"></i>
            </#if>
            <span class="logged-in-text"></span>
            <span class="tablet_desktop-inline_block solid_blue_text">REORDER</span>
        </a>
        <a href="#" id="jqs-login-container" class="account jqs-login-button" data-reveal-id="secure-layer" data-dest="login-layer" >
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-06 23:59:59.000"?datetime)>
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-myaccount-icon-holiday-theme-v2?fmt=png-alpha&amp;wid=41</@ofbizScene7Url>" alt="My Account Icon" />
            <#elseif (currentTimestamp?default(now?datetime) gte "2021-11-22 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-myaccount-icon-holiday-theme-v1?fmt=png-alpha&amp;wid=41</@ofbizScene7Url>" alt="My Account Icon" />
            <#else>
            <i class="fa fa-user-circle-o tablet_desktop-inline_block"></i>
            <img class="mobile-only" src="<@ofbizContentUrl>/html/img/icon/account-new.svg</@ofbizContentUrl>" />
            </#if>
            <span class="tablet_desktop-inline_block solid_blue_text">LOG IN</span>
        </a>
        <#--Hidden Login button for triggerring Login Layer programatically across the site-->
        <div id="jqs-hidden-login-handle" class="hidden">
			<div class="jqs-hidden-login-button button-regular button-blue" data-reveal-id="secure-layer" data-dest="login-layer">Log In / Reorder</div>
		</div>
        <#-- /hidden login button -->
        <a id="cartContainer" href="<@ofbizUrl>/cart</@ofbizUrl>" class="cart" data-dropdown-target="dropdown-nav-cart" data-dropdown-options="hover reverse-horizontal ignore-reverse-dropdown">
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-22 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-cart-icon-holiday-theme-v1?fmt=png-alpha&amp;wid=40</@ofbizScene7Url>" alt="Cart Icon" />
            <#else>
            <i class="fa fa-shopping-cart tablet_desktop-inline_block"></i>
            <img class="mobile-only" src="<@ofbizContentUrl>/html/img/icon/cart-new.svg</@ofbizContentUrl>" />
            </#if>
            <span id="jqs-mini-cart-count">0</span>
            <span class="tablet_desktop-inline_block solid_blue_text">CART</span>
        </a>
    </div>
    <#--  <div class="header_content-4 mobile-block">
        <a href="<@ofbizUrl>/holidayShop</@ofbizUrl>">
            <i class="fa fa-gift" style="font-size: 24px;"></i>
            The Holiday Shop is open!
            <i class="fa fa-caret-right" style="font-size: 18px;"></i>
        </a>
    </div>  -->
    <div class="header_content-5 tablet_desktop-block">
        <div id="mega_menu_container" class="tabular_row mega_menu_container-1">
            <div class="dropdown-hover-navigation" style="width: 11%;" data-dropdown-target="dropdown-Envelopes" data-dropdown-alternate-parent="mega_menu_container" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-200">
                Envelopes <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            <div class="dropdown-hover-navigation" style="width: 16%;" data-dropdown-target="dropdown-Paper__Cardstock" data-dropdown-alternate-parent="mega_menu_container" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-200">
                Paper &amp; Cardstock <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            <div class="dropdown-hover-navigation" style="width: 15%;" data-dropdown-target="dropdown-Custom_Printing" data-dropdown-alternate-parent="mega_menu_container" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-200">
                Custom Printing <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            <div class="dropdown-hover-navigation" style="width: 17%;" data-dropdown-target="dropdown-Mailroom_Supplies" data-dropdown-alternate-parent="mega_menu_container" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-200">
                Packaging &amp; Mailers <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            <div class="dropdown-hover-navigation" style="width: 17%;" data-dropdown-target="dropdown-Folders" data-dropdown-alternate-parent="mega_menu_container" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-200">
                Folders <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            <#--
            <div class="dropdown-hover-navigation" style="width: 14%;" data-dropdown-target="dropdown-Ways_To_Shop" data-dropdown-alternate-parent="mega_menu_container" data-dropdown-options="hover shadowed(main-header) ignore-reverse-dropdown delayed-200">
                Ways to Shop <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            -->
            <a href="<@ofbizUrl>/new-arrivals</@ofbizUrl>" class="dropdown-hover-navigation" style="width: 12%;">
                New Arrivals
            </a>
            <#--  <a href="<@ofbizUrl>/search?w=*&af=onsale:sale%20onsale:clearance</@ofbizUrl>" class="dropdown-hover-navigation" style="width: 15%;">
                Hot Buys
            </a>  -->
            <#if (currentTimestamp?default(now?datetime) gte "2021-01-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-03-10 23:59:59.000"?datetime)>
            <a href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>" class="dropdown-hover-navigation" style="width: 12%; color: #ffffff; background-color: #638c36;">
                Tax Shop <i class="fa fa-calculator desktop-inline_block" style="font-size: 16px;"></i>
            </a>
            <#elseif (currentTimestamp?default(now?datetime) gte "2021-03-11 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-10-27 23:59:59.000"?datetime)>
            <a href="<@ofbizUrl>/weddingShop</@ofbizUrl>" class="dropdown-hover-navigation" style="width: 12%; color: #ffffff; background-color: #681C1C;">
                Wedding Shop <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop-white?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" />
            </a>
            <#elseif (currentTimestamp?default(now?datetime) gte "2021-10-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <a href="<@ofbizUrl>/holidayShop</@ofbizUrl>" style="width: 12%; color: #ffffff; background-color: #e00303;">
                Holiday Shop <i class="fa fa-gift desktop-inline_block" style="font-size: 16px;"></i>
            </a>
            </#if>
        </div>
    </div>

    <#if (currentTimestamp?default(now?datetime) gte "2021-08-16 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-19 23:59:59.000"?datetime) && currentView != "main">
    <div class="Envelopes_UNT_Wrap">
        <div class="Envelopes_UNT_Content">
            <a href="<@ofbizUrl>/applyPromo</@ofbizUrl>?productPromoCodeId=FREE50&saveResponse=true">
                <span class="mobileNextLine" style="text-transform: uppercase;">
                    <span class="tablet-desktop-only-inline-block">Celebrate <span style="color: #c79d50;">Envelopes.com</span> 50th Birthday with</span> <span style="color: #c79d50; font-weight: bold;">FREE SHIPPING</span>
                </span>
                <span class="mobileNextLine">
                    ORDERS $50+ <span class="tablet-desktop-only-inline-block">|</span> 
                </span>
                <span class="mobileNextLine">
                    CODE: <span style="color: #c79d50;">FREE50</span>
                </span>
            </a>
        </div>
    </div>
    <#elseif (currentTimestamp?default(now?datetime) gte "2021-08-20 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-25 23:59:59.000"?datetime) && currentView != "main">
    <div class="Envelopes_UNT_Wrap">
        <div class="Envelopes_UNT_Content">
            <a href="<@ofbizUrl>/applyPromo</@ofbizUrl>?productPromoCodeId=ENV50&saveResponse=true">
                <span class="mobileNextLine" style="text-transform: uppercase;">
                    <span class="tablet-desktop-only-inline-block">Celebrate <span style="color: #c79d50;">Envelopes.com</span> 50th Birthday with</span> <span style="color: #c79d50;">$50 OFF</span>
                </span>
                <span class="mobileNextLine">
                    + <span style="color: #c79d50;">FREE SHIPPING</span> ON $250+ <span class="tablet-desktop-only-inline-block">|</span> 
                </span>
                <span class="mobileNextLine">
                    CODE: <span style="color: #c79d50;">ENV50</span>
                </span>
            </a>
        </div>
    </div>
    <#elseif (currentTimestamp?default(now?datetime) gte "2021-08-26 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-09-10 23:59:59.000"?datetime) && currentView != "main">
    <div class="Envelopes_UNT_Wrap">
        <div class="Envelopes_UNT_Content">
            <a href="<@ofbizUrl>/applyPromo</@ofbizUrl>?productPromoCodeId=READY15&saveResponse=true">
                <span class="mobileNextLine" style="text-transform: uppercase;">
                    <span>Ready, Set, SAVE. <span style="color: #c79d50;font-weight: 700;">15% OFF</span>
                </span>
                <span class="mobileNextLine">
                    + <span style="color: #c79d50;font-weight: 700;">FREE SHIPPING</span> ends TOMORROW <span class="tablet-desktop-only-inline-block">|</span> 
                </span>
                <span class="mobileNextLine">
                    CODE: <span style="color: #c79d50;font-weight: 700;">READY15</span>
                </span>
            </a>
        </div>
    </div>
    </#if>
    
    <#if currentView == "main"> 
        <#if (currentTimestamp?default(now?datetime) gte "2020-12-05 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-17 23:59:59.000"?datetime) && currentView == "main">
            <div class="header_promotion" data-reveal-id="fship-promo2" style="display: block; background-color: #000000; color: #ffffff; font-size: 18px; text-align: center; padding: 10px; letter-spacing: 1px;">
                <i class="fa fa-truck tablet_desktop-inline_block" style="color: #00A7E0; margin: 0px 5px; font-size: 25px;"></i>
                <span class="solid_text">FREE SHIPPING ON ALL ORDERS OVER $${freeShippingAmount?default("99")}</span>
                <i class="fa fa-circle tablet_desktop-inline_block" style="color: #7ECDEE; margin: 0px 10px; font-size: 7px;"></i>
                <span class="tablet_desktop-inline_block">Use Code <strong style="font-size: 14px;">FREE${freeShippingAmount?default("99")}</strong> at Checkout</span>
            </div>
        </#if>
        <div class="tabular_row swatchbook-top-banner">
            <a data-ga-promo data-ga-promo-id="hp_thin_banner_swatchbook" data-ga-promo-name="Swatchbook" data-ga-promo-creative="hp_thin_banner_swatchbook" data-ga-promo-position="Site Wide Banner" href="<@ofbizUrl>/product/~category_id=PAPER/~product_id=SWATCHBOOK</@ofbizUrl>" class="jqs-trackHeaderBanner swatchbook-link">
                <div class="img-thumbnail">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/swatchbook-thumbnail?fmt=png-alpha</@ofbizScene7Url>" alt="Swatchbook - Premium Envelopes &amp; Paper" />
                </div>
                <div class="text-block">Our Swatchbook Ships Free!</div>
            </a>
            <div class="clsbtn-wrap">
                <div class="close-x-button">&times;</div>
            </div>
        </div>
    </#if>
    <script>
        function formatDateTimeString(val) {
            return ("" + val).length > 1 ? val : "0" + val;
        }

        $("[bns-promo_timer]").each(function() {
            var element = $(this);
            var startTime = element.attr("bns-promo_timer_start");
            var endTime = element.attr("bns-promo_timer_end");

            window.setInterval(function() {
                var myDateObj = new Date();

                var dateTime = myDateObj.getFullYear() + "-" + formatDateTimeString(myDateObj.getMonth() + 1) + "-" + formatDateTimeString(myDateObj.getDate()) + "T" + formatDateTimeString(myDateObj.getHours()) + ":" + formatDateTimeString(myDateObj.getMinutes()) + ":" + formatDateTimeString(myDateObj.getSeconds()) + "." + formatDateTimeString(myDateObj.getMilliseconds());

                if (new Date(dateTime) > new Date(startTime) && new Date(dateTime) < new Date(endTime)) {
                    var hours = (Math.floor((new Date(endTime) - new Date(dateTime)) / 60 / 60 / 1000) % 24);
                    var minutes = (Math.floor((new Date(endTime) - new Date(dateTime)) / 60 / 1000) % 60);
                    var seconds = (Math.floor((new Date(endTime) - new Date(dateTime)) / 1000) % 60);
                    var milliseconds = (Math.floor((new Date(endTime) - new Date(dateTime))) % 1000);

                    $(element).html("Hurry! Offer Ends: <span style=\"font-weight: bold; color: #ffff00;\">" + hours + ":" + (("" + minutes).length == 1 ? "0" : "") + minutes + ":" + (("" + seconds).length == 1 ? "0" : "") + seconds) + "</span>";
                } else {
                    $(element).html("");
                }
            }, 1000)
        });
    </script>
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
                            <#--  <h2>Shop By Brand</h2>
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
                            </div>  -->
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
                            <#if (currentTimestamp?default(now?datetime) gte "2021-06-10 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-04 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/graduationShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-gradShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Graduation Shop" />Graduation Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-08-05 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-10-27 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/backToSchool</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/apple-with-ruler-icon-v3?fmt=png-alpha&amp;wid=17&amp;ts=1</@ofbizScene7Url>" alt="The Back To School Shop" /> Back To School Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-10-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>">&nbsp;<i class="fa fa-gift desktop-inline_block" style="font-size: 16px; color:red;"></i>&nbsp;&nbsp;Holiday Shop</a>
                            </div>
                            <#else>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                            </div>
                            </#if>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/rachael-hale-mini-logo?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Rachael Hale&reg; Shop" /> Rachael Hale<sup>&reg;</sup> Shop</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
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
                            <#if (currentTimestamp?default(now?datetime) gte "2020-03-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-31 23:59:59.000"?datetime)>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                                </div>
                            </#if>
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
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-19 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img class="tablet_desktop-inline_block" src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-plant-decor-banner?fmt=jpeg&amp;wid=1440&amp;hei=105</@ofbizScene7Url>" alt="Holiday"/>
            </#if>
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
                            <#--  <h2>Shop By Brand</h2>
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
                            </div>  -->
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
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=FOLDED_CARD_SETS</@ofbizUrl>">Folded Card Box Sets</a>
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
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=planners</@ofbizUrl>">Planners</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=journals</@ofbizUrl>">Journals</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=notepads</@ofbizUrl>">Notepads</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=sticky+notes</@ofbizUrl>">Sticky Notes</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=padfolios</@ofbizUrl>">Padfolios</a>
                            </div>
                            <div class="seeAll">
                                <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=notebooks</@ofbizUrl>">All Notebooks &amp; more <i class="fa fa-caret-right"></i></a>
                            </div>
                            <h2>Shop by Collection</h2>
                            <div class="row">
                                <div class="columns large-12">
                                    <div>
                                        <a bns-mega-menu-level="4" href="<@ofbizUrl>/luxpaper</@ofbizUrl>">LUX Paper</a>
                                    </div>
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
                            <#if (currentTimestamp?default(now?datetime) gte "2021-06-10 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-04 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/graduationShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-gradShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Graduation Shop" />Graduation Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-08-05 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-10-27 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/backToSchool</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/apple-with-ruler-icon-v3?fmt=png-alpha&amp;wid=17&amp;ts=1</@ofbizScene7Url>" alt="The Back To School Shop" /> Back To School Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-10-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>">&nbsp;<i class="fa fa-gift desktop-inline_block" style="font-size: 16px; color:red;"></i>&nbsp;&nbsp;Holiday Shop</a>
                            </div>
                            <#else>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                            </div>
                            </#if>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/rachael-hale-mini-logo?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Rachael Hale&reg; Shop" /> Rachael Hale<sup>&reg;</sup> Shop</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
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
                            <#if (currentTimestamp?default(now?datetime) gte "2020-03-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-31 23:59:59.000"?datetime)>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                                </div>
                            </#if>
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
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-19 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img class="tablet_desktop-inline_block" src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-plant-decor-banner?fmt=jpeg&amp;wid=1440&amp;hei=105</@ofbizScene7Url>" alt="Holiday"/>
            </#if>
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
                            <#if (currentTimestamp?default(now?datetime) gte "2021-06-10 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-04 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/graduationShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-gradShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Graduation Shop" />Graduation Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-08-05 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-10-27 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/backToSchool</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/apple-with-ruler-icon-v3?fmt=png-alpha&amp;wid=17&amp;ts=1</@ofbizScene7Url>" alt="The Back To School Shop" /> Back To School Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-10-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>">&nbsp;<i class="fa fa-gift desktop-inline_block" style="font-size: 16px; color:red;"></i>&nbsp;&nbsp;Holiday Shop</a>
                            </div>
                            <#else>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                            </div>
                            </#if>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/rachael-hale-mini-logo?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Rachael Hale&reg; Shop" /> Rachael Hale<sup>&reg;</sup> Shop</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
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
                            <#if (currentTimestamp?default(now?datetime) gte "2020-03-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-31 23:59:59.000"?datetime)>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                                </div>
                            </#if>
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
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-19 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img class="tablet_desktop-inline_block" src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-plant-decor-banner?fmt=jpeg&amp;wid=1440&amp;hei=105</@ofbizScene7Url>" alt="Holiday"/>
            </#if>
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
                            <#if (currentTimestamp?default(now?datetime) gte "2021-01-01 00:00:00.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                            </div>
                            </#if>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
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
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-19 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img class="tablet_desktop-inline_block" src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-plant-decor-banner?fmt=jpeg&amp;wid=1440&amp;hei=105</@ofbizScene7Url>" alt="Holiday"/>
            </#if>
        </div>
    </div>
    <div id="dropdown-Mailroom_Supplies" class="drop-down">
        <div class="megaMenuDropdownContent">
            <div class="row">
                <#--  <div class="columns large-2 no-padding megaMenuFirstColumns">
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
                </div>  -->
                <div class="columns large-2 no-padding megaMenuFirstColumns">
                    <h2>Mailers</h2>
                    <div class="row">
                        <div class="columns large-12">
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=BUBBLE_LINED</@ofbizUrl>">Bubble Padded Mailers</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=PLASTIC_MAILERS</@ofbizUrl>">Poly Mailers</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=PAPERBOARD_MAILERS</@ofbizUrl>">Paperboard &amp; Rigid Mailers</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=tyvek+mailing+envelopes</@ofbizUrl>">Tyvek Mailers</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=tyvek+mailers</@ofbizUrl>">Pre-Printed Mailers</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=custom+boxes</@ofbizUrl>">Custom Printed Boxes</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>">Custom Printed Mailers</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=mailing+tubes</@ofbizUrl>">Mailing Tubes</a>
                            </div>
                            <div class="seeAll">
                                <a bns-mega-menu-level="5" href="<@ofbizUrl>/packagingAndMailersShop</@ofbizUrl>">All Mailers <i class="fa fa-caret-right"></i></a>
                            </div>
                            <h2>Boxes</h2>
                            <div class="row">
                                <div class="columns large-12">
                                    <div>
                                        <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=corrugated+boxes</@ofbizUrl>">Shipping Boxes</a>
                                    </div>
                                    <div>
                                        <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+boxes&c=0</@ofbizUrl>">Gift Boxes</a>
                                    </div>
                                    <div>
                                        <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=clear+boxes</@ofbizUrl>">Product Boxes</a>
                                    </div>
                                    <div>
                                        <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=pillow+boxes</@ofbizUrl>">Pillow Boxes</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="columns large-2 no-padding megaMenuFirstColumns">
                    <h2>Mailing Envelopes</h2>
                    <div class="row">
                        <div class="columns large-12">
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
                        </div>
                    </div>
                    <h2 style="margin-top: 36px !important;">Bags</h2>
                    <div class="row">
                        <div class="columns large-12">
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>">Gift Bags</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search</@ofbizUrl>?w=door+knob+bags">Specialty Bags</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search</@ofbizUrl>?w=tote+bags">Tote Bags</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="columns large-2 no-padding megaMenuFirstColumns">
                    <h2>Hanging Pouches</h2>
                    <div class="row">
                        <div class="columns large-12">
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=barrier+bags</@ofbizUrl>">Flat Pouch</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=stand+up+pouch</@ofbizUrl>">Stand Up Pouch</a>
                            </div>
                            <div class="seeAll">
                                <a bns-mega-menu-level="5" href="<@ofbizUrl>/category/~category_id=HANGING_BAGS</@ofbizUrl>">All Hanging Pouches <i class="fa fa-caret-right"></i></a>
                            </div>
                        </div>
                    </div>
                    <h2 style="margin-top: 40px !important;">Packaging Accessories</h2>
                    <div class="row">
                        <div class="columns large-12">
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=wrapping+paper</@ofbizUrl>">Wrapping Paper</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=gift+tags</@ofbizUrl>">Hang Tags</a>
                            </div>
                            <#--  <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>#</@ofbizUrl>">Tape</a>
                            </div>  -->
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/category/~category_id=LABELS</@ofbizUrl>">Labels</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="columns large-2 no-padding megaMenuFirstColumns">
                    <h2>Features</h2>
                    <div class="row">
                        <div class="columns large-12">
                            <#if (currentTimestamp?default(now?datetime) gte "2021-06-10 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-04 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/graduationShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-gradShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Graduation Shop" />Graduation Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-08-05 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-10-27 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/backToSchool</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/apple-with-ruler-icon-v3?fmt=png-alpha&amp;wid=17&amp;ts=1</@ofbizScene7Url>" alt="The Back To School Shop" /> Back To School Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-10-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>">&nbsp;<i class="fa fa-gift desktop-inline_block" style="font-size: 16px; color:red;"></i>&nbsp;&nbsp;Holiday Shop</a>
                            </div>
                            <#else>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                            </div>
                            </#if>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/rachael-hale-mini-logo?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Rachael Hale&reg; Shop" /> Rachael Hale<sup>&reg;</sup> Shop</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
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
                            <#if (currentTimestamp?default(now?datetime) gte "2020-03-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-31 23:59:59.000"?datetime)>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                                </div>
                            </#if>
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
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-19 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img class="tablet_desktop-inline_block" src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-plant-decor-banner?fmt=jpeg&amp;wid=1440&amp;hei=105</@ofbizScene7Url>" alt="Holiday"/>
            </#if>
        </div>
    </div>

    <div id="dropdown-Folders" class="drop-down">
        <div class="megaMenuDropdownContent">
            <div class="row">
                <div class="columns large-3 no-padding megaMenuFirstColumns">
                    <h2>Folders</h2>
                    <div class="row">
                        <div class="columns large-6">
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
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=file+folders</@ofbizUrl>">File Folders</a>
                            </div>
                        </div>
                        <div class="columns large-6">
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=certificate+holders</@ofbizUrl>">Certificate Holders</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=certificates</@ofbizUrl>">Paper Certificates</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=photo+holders</@ofbizUrl>">Photo Holders</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="columns large-3 no-padding megaMenuFirstColumns">
                    <h2>Features</h2>
                    <div class="row">
                        <div class="columns large-6">
                            <#if (currentTimestamp?default(now?datetime) gte "2021-06-10 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-04 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/graduationShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-gradShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Graduation Shop" />Graduation Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-08-05 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-10-27 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/backToSchool</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/apple-with-ruler-icon-v3?fmt=png-alpha&amp;wid=17&amp;ts=1</@ofbizScene7Url>" alt="The Back To School Shop" /> Back To School Shop</a>
                            </div>
                            <#elseif (currentTimestamp?default(now?datetime) gte "2021-10-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 11:59:59.000"?datetime)>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/holidayShop</@ofbizUrl>">&nbsp;<i class="fa fa-gift desktop-inline_block" style="font-size: 16px; color:red;"></i>&nbsp;&nbsp;Holiday Shop</a>
                            </div>
                            <#else>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                            </div>
                            </#if>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/rachaelHaleShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/rachael-hale-mini-logo?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="Rachael Hale&reg;" /> Rachael Hale<sup>&reg;</sup> Shop</a>
                            </div>
                            <div>
                                <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
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
                        </div>
                        <div class="columns large-6">
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
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-19 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img class="tablet_desktop-inline_block" src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-plant-decor-banner?fmt=jpeg&amp;wid=1440&amp;hei=105</@ofbizScene7Url>" alt="Holiday"/>
            </#if>
        </div>
    </div>

    <div id="dropdown-Ways_To_Shop" class="drop-down">
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
                            <#if (currentTimestamp?default(now?datetime) gte "2020-01-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-02-29 23:59:59.000"?datetime)>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                                </div>
                            <#else>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/weddingShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-weddingShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Wedding Shop" /> The Wedding Shop</a>
                                </div>
                            </#if>
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
                            <#if (currentTimestamp?default(now?datetime) gte "2020-03-01 00:00:00.000"?datetime)>
                                <div>
                                    <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxEnvelopesShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eicon-taxShop?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>
                <a bns-track_event="Header|Mega Menu|Folders Shop Now" bns-mega-menu-level="4" href="" onClick="window.open('https://www.folders.com'); return false;"><img class="margin-top-xxs waysToShopFoldersImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/megaMenuFoldersImgHorizontal?fmt=jpeg&amp;wid=280</@ofbizScene7Url>" alt="Folders.com"/></a>
            </div>
        </div>
    </div>

    <div id="dropdown-Envelopes-Category" class="drop-down" style="border-top: 0px;">
        <div class="row mega_menu_content">
            <div class="columns large-7">
                <div class="row">
                    <div class="columns large-4">
                        <a class="column_header" href="#">
                            Business Envelopes <i class="fa fa-chevron-right"></i>
                        </a>
                        <a href="#">
                            Regular Envelopes
                        </a>
                        <a href="#">
                            Window Envelopes
                        </a>
                        <a href="#">
                            Peel &amp; Press Envelopes
                        </a>
                        <a href="#">
                            Square Flap Envelopes
                        </a>
                        <a href="#">
                            Booklet Envelopes
                        </a>
                        <a href="#">
                            Open End Envelopes
                        </a>
                        <a href="#">
                            Clasp Envelopes
                        </a>
                        <a href="#">
                            Remittance Envelopes
                        </a>
                        <a href="#">
                            Coin Envelopes
                        </a>
                        <a href="#">
                            Jumbo Envelopes
                        </a>
                        <a href="#">
                            Expansion Envelopes
                        </a>
                        <a href="#">
                            Paperboard Envelopes
                        </a>
                        <a href="#">
                            Tyvek Envelopes
                        </a>
                        <a href="#">
                            Shop All Business Envelopes
                        </a>
                    </div>
                    <div class="columns large-4">
                        <a class="column_header" href="#">
                            Invitation Envelopes <i class="fa fa-chevron-right"></i>
                        </a>
                        <a href="#">
                            Square Flap Envelopes
                        </a>
                        <a href="#">
                            Contour Flap Envelopes
                        </a>
                        <a href="#">
                            Pointed Flap Envelopes
                        </a>
                        <a href="#">
                            Lined Envelopes
                        </a>
                        <a href="#">
                            Square Envelopes
                        </a>
                        <a href="#">
                            Mini Envelopes
                        </a>
                        <a href="#">
                            Photo Greeting Envelopes
                        </a>
                        <a href="#">
                            Photo Holders
                        </a>
                        <a href="#">
                            Shop All Invitation Envelopes
                        </a>
                    </div>
                    <div class="columns large-4">
                        <a class="column_header" href="#">
                            Specialty Envelopes <i class="fa fa-chevron-right"></i>
                        </a>
                        <a href="#">
                            Airmail Envelopes
                        </a>
                        <a href="#">
                            First Class Envelopes
                        </a>
                        <a href="#">
                            Coin Envelopes
                        </a>
                        <a href="#">
                            Gift Card Envelopes
                        </a>
                        <a href="#">
                            Credit Card Sleeves
                        </a>
                        <a href="#">
                            Currency Envelopes
                        </a>
                        <a href="#">
                            Expansion Envelopes
                        </a>
                        <a href="#">
                            Tyvek Envelopes
                        </a>
                        <a href="#">
                            Conformer&reg; Mailers
                        </a>
                        <a href="#">
                            W-2 Envelopes
                        </a>
                        <a href="#">
                            Full Face Window Envelopes
                        </a>
                        <a href="#">
                            Button &amp; String Envelopes
                        </a>
                        <a href="#">
                            Photo Holders
                        </a>
                        <a href="#">
                            Shop All Specialty Envelopes
                        </a>
                    </div>
                </div>
            </div>
            <div class="columns large-5">
                <img src="https://via.placeholder.com/470x420?text=IMAGE" alt="" title="" />
            </div> 
        </div>
    </div>
</header>


<!-- MOBILE NAV DROPDOWN -->
<div id="dropdown-menuItems" class="drop-down padding-xxs" style="width: 100%; left: 0px;">
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
				<a href="<@ofbizUrl>/checkout</@ofbizUrl>"><div class="round-btn jqs-checkout button-regular checkout button-cta">Checkout</div></a>
				<a href="<@ofbizUrl>/cart</@ofbizUrl>"><div class="round-btn button-regular button-non-cta">Edit Cart</div></a>
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