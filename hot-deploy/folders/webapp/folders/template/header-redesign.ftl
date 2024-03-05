<html>
<#include "../includes/template/headerHead.ftl" />
<body>
<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<div id="foldersHeader" class="foldersHeader">
    <#--  <div class="foldersDisplayTable foldersTabularRow firstNav">
        <div class="leftSideBranding noPadding">
            <div class="foldersTabularRow siteList logoHeader">
                <a href="https://www.bigname.com" target="_blank"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/bigNameLogoHeader?fmt=png-alpha&amp;wid=71</@ofbizScene7Url>" alt="Big Name" /></a>
                <a href="https://www.envelopes.com" target="_blank"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopesLogoHeader?fmt=png-alpha&amp;wid=60</@ofbizScene7Url>" alt="Envelopes.com" /></a>                
                <a class="paddingBottom5" href="https://www.folders.com" target="_blank"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foldersLogoHeader?fmt=png-alpha&amp;wid=48</@ofbizScene7Url>" alt="Folders.com" /></a>  
            </div>
        </div>
        <div class="freeShippingHeader noPadding">
            <div data-bnreveal="fship2" class="freeShippingBannerNew">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hp-freeShippingLogoBanner?fmt=png-alpha</@ofbizScene7Url>" alt="Free Shipping Banner">
                <div class="freeShippingBannerText">
                    <p class="freeShippingTextNew">free shipping on all orders over $${globalContext.freeShippingAmount?default("299")} <i class="freeShippingCodeText"> Code </i>free${globalContext.freeShippingAmount?default("299")} <span>details</span></p>
                </div>
            </div>
        </div>
        <div class="foldersEmailSubscribe ">
            <div class="foldersSave5 " onclick="_ltk.Popup.openManualByName('Popup2021-Folders-Manual');">
                <img src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/foldersSubscribeLogoHeader?fmt=png-alpha&amp;wid=24" alt="Subscribe Button"> SAVE $10
            </div>
        </div>
        <a href="tel:1-800-296-4321" class="inheritTextColor phone2 tabletDesktop-tableCell  textBold">
            <i class="fa fa-phone"></i> <span class="companyNumber2">800-296-4321</span>
        </a>
        
        <div id="jqs-login-container" class="text-center myAccount logged-in noPadding ">
            <span class="logged-in-text"></span>
            <a class="jqs-login-button foldersButton buttonDarkGray noMargin " data-bnReveal="secure-layer" data-dest="login-layer" >Log In / Reorder</a>
        </div>
    </div>  -->
    <#------------------------------------------------------->
    <#------------------------------------------------------->
    <#------------------------------------------------------->
    <div class="header_content-1 foldersDisplayTable foldersTabularRow firstNav textCenter">
        <a href="https://www.envelopes.com" target="_blank" style="width: 106px;background-color: #294A82;" class="noMobile">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopesLogoBTS</@ofbizScene7Url>?fmt=png-alpha&amp;wid=65" alt="Envelopes.com">
        </a>
        <a href="https://www.bigname.com" target="_blank" style="width: 139px;background-color: #294A82;" class="noMobile">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/bigNameWhite</@ofbizScene7Url>?fmt=png-alpha&amp;wid=98" alt="Big Name" title="Big Name">
        </a>
        <div class="header_promotion" data-bnreveal="fship2">
            <i class="fa fa-truck tablet_desktop-inline_block" style="color: #00A7E0; margin: 0px 5px; font-size: 25px;"></i>
            <span class="solid_text">FREE SHIPPING ON ALL ORDERS OVER $${freeShippingAmount?default("99")}</span>
            <i class="fa fa-circle tablet_desktop-inline_block noMobile" style="color: #7ECDEE; margin: 0px 10px; font-size: 7px;"></i>
            <span class="tablet_desktop-inline_block noMobile">Use Code <strong style="font-size: 14px;">FREE${freeShippingAmount?default("99")}</strong> at Checkout</span>
        </div>
        <div id="jqs-hidden-login-handle" class="hidden myAccount noPadding ">
            <div class="jqs-hidden-login-button foldersButton buttonDarkGray noMargin pullRight" data-bnReveal="secure-layer" data-dest="login-layer">Log In</div>
        </div>
        <#--SAVE TEN BLOCK-->
        <div style="width: 134px; background-color: #294A82;cursor: pointer;" onclick="_ltk.Popup.openManualByName('Popup2021-Folders-Manual');" class="noMobile">
            <span class="solid_text">SAVE $10</span>
        </div>
    </div>
    <#------------------------------------------------------->
    <#------------------------------------------------------->
    <#------------------------------------------------------->
    <div class="header_content-2 noMobile">
        <a href="<@ofbizUrl>/specialtyProducts</@ofbizUrl>" title="Get a Quote | Folders.com">
            Get a Quote
        </a>
        <a href="<@ofbizUrl>/contactUs</@ofbizUrl>" title="Contact Us | Folders.com">
            Contact Us
        </a>
        <a href="<@ofbizUrl>/account</@ofbizUrl>" title="Track Order | Folders.com">
            Track Order
        </a>
        <a href="<@ofbizUrl>/customSamplePackLanding</@ofbizUrl>" title="Samples | Folders.com">
            Samples
        </a>
    </div>
    <#------------------------------------------------------->
    <#------------------------------------------------------->
    <#------------------------------------------------------->
    <div class="header_content-3 secondNav foldersTabularRow">

        <div class="mobileTablet-tableCell menuDrop">
            <div class="jqs-mobileMenu" style="cursor:pointer;">
                <a href=""><i class="fa fa-bars"></i></a>    
            </div>
        </div>

        <div class="siteLogoWrapper">
            <a href="<@ofbizUrl>/main</@ofbizUrl>" class="siteLogo">
                <img src="<@ofbizContentUrl>/html/img/logo/foldersNavy.png</@ofbizContentUrl>" alt="Folders.com" title="Folders.com" />
            </a>
        </div>

        <div class="searchContainer tabletDesktop-tableCell noMobile">
            <div class="FOL_SearchUI_Wrap">
            <#if currentView?exists && currentView == "main">
                <div id="sli-search" class="search" itemscope itemtype="http://schema.org/WebSite">
                    <meta itemprop="url" content="https://${siteDomain?default("www.folders.com")}/"/>
                    <i class="fa fa-search"></i>
                    <form name="globalsearch" method="GET" action="<@ofbizUrl>/search</@ofbizUrl>" itemprop="potentialAction" itemscope itemtype="http://schema.org/SearchAction">
                        <meta itemprop="target" content="<@ofbizUrl>/search</@ofbizUrl>?w={w}"/>
                        <input itemprop="query-input" data-dropdown-target="dropdown-sli-search-results" data-dropdown-alternate-parent="sli-search" data-dropdown-options="focus shadowed(foldersHeader) ignore-reverse-dropdown ignore-scroll" type="text" name="w" value="" placeholder="What can we help you find?" autocomplete="off" />
                    </form>
                </div>
            <#else>
                <div id="sli-search" class="search">
                    <i class="fa fa-search"></i>
                    <form name="globalsearch" method="GET" action="<@ofbizUrl>/search</@ofbizUrl>">
                        <input data-dropdown-target="dropdown-sli-search-results" data-dropdown-alternate-parent="sli-search" data-dropdown-options="focus shadowed(foldersHeader) ignore-reverse-dropdown ignore-scroll" type="text" name="w" value="" placeholder="What can we help you find?" autocomplete="off" />
                    </form>
                </div>
            </#if>
            </div>
        </div>

        <#--  <a href="<@ofbizUrl>/customSamplePackLanding</@ofbizUrl>" class="inheritTextColor reorder textCenter noMobile">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foldersNavSamples?fmt=png-alpha</@ofbizScene7Url>" alt="Samples">
            <div>Samples</div>
        </a>  -->
        <#--  <a href="<@ofbizUrl>/specialtyProducts</@ofbizUrl>" class="inheritTextColor customQuote textCenter">
            <i class="fa fa-calculator"></i>
            <div>Custom Quote</div>
        </a>  -->

        <div class="siteReOrderWrapper noMobile">
            <a href="<@ofbizUrl>/account</@ofbizUrl>" class="reorder" title="Re-Order | Folders.com">
                <#--  <i class="fa fa-refresh"></i>  -->
                <img  src="<@ofbizScene7Url>/is/image/ActionEnvelope/reorder-icon</@ofbizScene7Url>?fmt=png-alpha" alt="Reorder | Folders.com"/>
                <div>Reorder</div>
            </a>
        </div>

        <div class="siteUserAccountWrapper">
            <a href="<@ofbizUrl>/account</@ofbizUrl>" class="myAccount" title="Log-In | Folders.com">
                <#--  <img  src="<@ofbizScene7Url>/is/image/ActionEnvelope/green-outline-user-icon</@ofbizScene7Url>?fmt=png-alpha" alt="Reorder | Folders.com"/>  -->
                <i class="fa fa-user ftc-green"></i>
                <div><span class="noMobile">My Account</span></div>
            </a>
        </div>

        <div class="siteCartWrapper">
            <a id="cartContainer" href="<@ofbizUrl>/cart</@ofbizUrl>" class="cart" title="Cart | Folders.com">
                <span id="jqs-mini-cart-count">0</span>
                <i class="fa fa-shopping-cart ftc-green noMargin"></i>
                <div bns-mini-cart-total id="jqs-mini-cart-total">Cart</div>
            </a>
        </div>

    </div>
    <#------------------------------------------------------->
    <#----------------- MEGA MENU CONTAINER ----------------->
    <#------------------------------------------------------->
    <div class="megaMenuContainer textCenter">
        <div id="megaMenuContainer" class="foldersTabularRow">
            <div id="FOL_MegaMenu_Tab1" class="FOL_MegaMenu_UI dropdown-hover-navigation" data-dropdown-target="dropdown-megaMenuCustomFolders" data-dropdown-alternate-parent="megaMenuContainer" data-dropdown-options="hover shadowed(foldersHeader) ignore-reverse-dropdown delayed-100">
                Custom Folders <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            <div id="FOL_MegaMenu_Tab2" class="FOL_MegaMenu_UI dropdown-hover-navigation" data-dropdown-target="dropdown-megaMenuBlankFolders" data-dropdown-alternate-parent="megaMenuContainer" data-dropdown-options="hover shadowed(foldersHeader) ignore-reverse-dropdown delayed-100">
                Blank Folders <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            <div id="FOL_MegaMenu_Tab3" class="FOL_MegaMenu_UI dropdown-hover-navigation" data-dropdown-target="dropdown-megaMenuCertificateHoldersDiplomaCovers" data-dropdown-alternate-parent="megaMenuContainer" data-dropdown-options="hover shadowed(foldersHeader) ignore-reverse-dropdown delayed-100">
                Certificate Holders &amp; Diploma Covers <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            <div id="FOL_MegaMenu_Tab4" class="FOL_MegaMenu_UI dropdown-hover-navigation" data-dropdown-target="dropdown-megaMailersAndEnvelopes" data-dropdown-options="hover shadowed(foldersHeader) ignore-reverse-dropdown delayed-100  reverse-horizontal">
                Mailers &amp; Envelopes <i class="fa fa-chevron-down desktop-inline_block"></i>
            </div>
            <div id="FOL_MegaMenu_Tab5" class="FOL_MegaMenu_UI noBorderRight dropdown-hover-navigation">
                <a href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>" style="color:#00a4e4">Clearance</a>
            </div>
            <#--  <div id="FOL_MegaMenu_Tab6" class="FOL_MegaMenu_UI noBorderRight FOL_SpecialtyLink">
                <a href="<@ofbizUrl>/backToSchool</@ofbizUrl>" class="dropdown-hover-navigation">Back To School Shop</a>
            </div>  -->
        </div>
    </div>
    <#if currentView != "main">
        <#if (currentTimestamp?default(now?datetime) gte "2021-01-15 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-01-28 23:59:59.000"?datetime)>
        <div data-bnreveal="FOLShipDelayPopUp" style="display: block; background-color: #000000; color: #ffffff; font-size: 18px; text-align: center; padding: 3px; letter-spacing: 1px;">
            <div style="margin:5px 0;display:inline-block;">
                <div class="mobileNextLine">Please Bear With Us: We are experiencing slight shipping delays.</div>
                <div class="mobileNextLine">Get <span style="font-weight: bold; color: #ffff00;">10% OFF</span> &amp; <span style="font-weight: bold; color: #ffff00;">Free Shipping</span> on $250+ | Code <strong style="color: #ffff00;">ENJOYTEN</strong></div>
            </div>
        </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-02-26 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-03-24 23:59:59.000"?datetime)>
        <div data-bnreveal="GenericDisclaimer" class="FOL_TopUI_TextBanner">
            <div class="FOL_TopUI_TextBanner_Content">
                <div>
                    <span>Limited Time! <span class="FOL_Highlighted">20% OFF</span> Custom Orders</span>
                    <span class="mobileNextLine">- OR -</span> 
                    <span><span class="FOL_Highlighted">10% OFF</span> Select Blank Products</span>
                </div>
            </div>
        </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-03-25 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-04-11 23:59:59.000"?datetime)>
        <div data-bnreveal="GenericDisclaimer" class="FOL_TopUI_TextBanner">
            <div class="FOL_TopUI_TextBanner_Content">
                <div>
                    <span>Limited Time! <span class="FOL_Highlighted">20% OFF</span> ALL CUSTOM ORDERS</span>
                </div>
            </div>
        </div>
        <#elseif (currentTimestamp?default(now?datetime) gte "2021-05-17 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-05-31 23:59:59.000"?datetime)>
        <div data-bnreveal="GenericDisclaimer" class="FOL_TopUI_TextBanner">
            <div class="FOL_TopUI_TextBanner_Content">
                <div>
                    <span>LIMITED TIME! <span class="FOL_Highlighted">20% OFF</span> </span>
                    <span class="mobileNextLine">ALL CUSTOM ORDERS | </span>
                    <span>CODE:</span> <span class="FOL_Highlighted">MAYCUST20</span>
                </div>
            </div>
        </div>
        </#if>
    </#if>

    <#--  <div class="foldersRow headerBanner">
        <div class="foldersColumn large4 medium6 small12 freeShipping">
            <div class="textCenter" data-bnreveal="fship">
                <i class="fa fa-truck"></i>
                <p><span>Free Shipping</span> over $${globalContext.freeShippingAmount?default("299")} - <span class="freeShippingCode">Code: FREE${globalContext.freeShippingAmount?default("299")}</span></p>
            </div>         
        </div>
        <div class="foldersColumn large4 medium6 small12 quickShip">
            <a href="<@ofbizUrl>/customFolders?af=st:quickshipfoilstampedfolders%20st:quickshipfourcolorfolders</@ofbizUrl>" class="textCenter">
                <i class="fa fa-bolt"></i>
                <p><span>Quick Ship</span> Folders Available! <i class="fa fa-caret-right"></i></p>
            </a>
        </div>
        <div class="foldersColumn large4 small12 priceMatch desktop-block">
            <a href="<@ofbizUrl>/specialtyProducts</@ofbizUrl>" class="textCenter">
                <i class="fa fa-calculator"></i> 
                <p><span>Endless Options!</span> Get a Custom Quote <i class="fa fa-caret-right"></i></p>
            </a>         
        </div>
    </div>  -->
    
    <div class="fourthNav mobileTablet-block">
        <form name="mobilesearch" method="GET" action="<@ofbizUrl>/search</@ofbizUrl>">
            <div class="foldersTabularRow">
                <i class="fa fa-search noPadding ftc-darkGray"></i>
                <input class="noMargin ftc-darkGray" type="text" name="w" placeholder="What are you looking for?" value="${requestParameters.w?default("")}" />
            </div>
        </form>
    </div>
</div>
<div bns-sitewidetabcontent class="desktop-block siteWideTabContent">
    <div bns-sitewidetabbuttoncontainer class="siteWideTabButtonContainer">
        <div bns-sitewidetabbutton="siteWideTodaysDeals" class="siteWideTabButton" data-ga-promo data-ga-promo-id="ws_todays_deal" data-ga-promo-name="Todays Deals" data-ga-promo-creative="ws_tab" data-ga-promo-position="bottom">
            <i class="fa fa-usd"></i> TODAY'S DEALS
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
        <p class="recentlyViewedPtag">Want to pick up where you left off?  Here are a few of your recently viewed items.</p>
        <div bns-recentlyviewedcontent></div>
    </div>
</div>

<#--
===========================
===== BEGIN DROPDOWNS =====
===========================
-->
<#-- DROPDOWN CUSTOM FOLDERS -->
<div id="dropdown-megaMenuCustomFolders" class="drop-down" style="width: 100%; max-width: 1300px;">
    <div class="megaMenuDropdownContent">
        <div class="foldersRow">
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Shop by Style</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>">9 x 12 Presentation Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?af=st:quickshipfoilstampedfolders%20st:quickshipfourcolorfolders</@ofbizUrl>">9 x 12 Quick Ship Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Legal%20Size%20Folders</@ofbizUrl>">Legal Size Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Specialty%209%20x%2012%20Presentation%20Folder</@ofbizUrl>">Specialty Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Reinforced%20Folders</@ofbizUrl>">Reinforced Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Small%20Folders</@ofbizUrl>">Small Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Extra%20Capacity%20Folders</@ofbizUrl>">Extra Capacity Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=vertical%20pocket</@ofbizUrl>">Vertical Pocket Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Tri-Panel%20Folder</@ofbizUrl>">Tri-Panel Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=File%20Tab%20Folders</@ofbizUrl>">File Tab Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Small%20Folders</@ofbizUrl>">Card Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Photo%20Holders</@ofbizUrl>">Photo Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Portfolios</@ofbizUrl>">Portfolios</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Binders</@ofbizUrl>">Binders</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=customizable:y use:folders</@ofbizUrl>">All Custom Styles <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Shop by Size</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>">9 x 12 Presentation Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?af=st:quickshipfoilstampedfolders%20st:quickshipfourcolorfolders</@ofbizUrl>">9 x 12 Quick Ship Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=912-546-C/~pocket_type=STANDARD/~print_method=FOIL;</@ofbizUrl>">9 x 12 Foil Stamped Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=SPOT</@ofbizUrl>">9 x 12 Printed Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=EMBOSS</@ofbizUrl>">9 x 12 Embossed Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Specialty%209%20x%2012%20Presentation%20Folder</@ofbizUrl>">9 x 12 Specialty Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Legal%20Size%20Folders</@ofbizUrl>">Legal Size Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=reinforced+folders</@ofbizUrl>">9 3/4 x 11 3/4 Reinforced Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=6%20x%209%20folders&af=customizable:y</@ofbizUrl>">6 x 9 Small Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=4%20x%209%20folders&af=customizable:y</@ofbizUrl>">4 x 9 Small Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=card%20holders&af=customizable:y%20s:338x6</@ofbizUrl>">3 3/8 x 6 Card Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=card%20holders&af=customizable:y%20s:234x334&page=0</@ofbizUrl>">2 3/4 x 3 3/4 Card Holders</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=customizable:y use:folders</@ofbizUrl>">All Sizes <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Shop By Color</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" bns-shopByColor="white">White</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=NATURAL</@ofbizUrl>" bns-shopByColor="natural">Natural</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=GROCERYBAG</@ofbizUrl>" bns-shopByColor="groceryBag">Grocery Bag</a>
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
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=ASSORTED</@ofbizUrl>" bns-shopByColor="assorted">Assorted</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/shopByColor</@ofbizUrl>">All Colors <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Features</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=*&af=new:y</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/newArrivalsFoldersMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="New Arrivals" /> New Arrivals</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/backToSchool</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/apple-with-ruler-icon-v3?fmt=png-alpha&amp;wid=17&amp;ts=1</@ofbizScene7Url>" alt="The Back To School Shop" /> Back To School Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/graduationShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-gradShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Graduation Shop" /> The Graduation Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-taxShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customSamplePackLanding</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-customSamples?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="Custom Samples" /> Custom Samples</a>
                        </div>
                        <h2>Accessories</h2>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:fasteners</@ofbizUrl>">Fasteners</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:holepunchers</@ofbizUrl>">Hole Punchers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=fasteners,%20notebooks,%20hole%20punchers&af=st:fasteners%20st:holepunchers%20st:notebooksnotepadsplanners</@ofbizUrl>">All Accessories <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Guides</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>">Foil Stamping Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=spotColor</@ofbizUrl>">Spot Color Printing Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=fourColor</@ofbizUrl>">Four Color Printing Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=embossing</@ofbizUrl>">Embossing Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/paperStocks?printed=Y</@ofbizUrl>">Paper Stock Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/paperBinderStockColors</@ofbizUrl>">Paper Binder Stock Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/plasticStocks</@ofbizUrl>">Plastic Stock Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/paddedDiplomaMaterials</@ofbizUrl>">Padded Diploma Materials</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/artworkChecklist</@ofbizUrl>">Art Info Checklist</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <div bns-tagInfo="Custom-Folder-Email-Subscribe" class="textCenter padding20 signUpBlock">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-signUp?fmt=png-alpha&amp;wid=121&amp;ts=1</@ofbizScene7Url>" alt="Sign Up!" />
                    <h3>Sign Up!</h3>
                    <p>Be the first to know about special promotions, product launches and more!</p>
                    <input bns-subscribeemailinput="headerCustomFolders" type="text" name="email_address" placeholder="Enter Your Email Address" />
                    <div bns-subscribeemailbutton="headerCustomFolders" class="foldersButton">Submit <i class="fa fa-caret-right"></i></div>
                    <span bns-subscribeemailerror="headerCustomFolders" class="error" style="background-color: transparent;color: #ff0000 !important;display: none">Invalid Email address</span>
                    <span bns-subscribeemailmessage="headerCustomFolders" style="background-color: transparent; color: #0060b6 !important; display: none">Successfully subscribed to email newsletter</span>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="dropdown-megaMenuBlankFolders" class="drop-down" style="width: 100%; max-width: 1300px;">
    <div class="megaMenuDropdownContent">
        <div class="foldersRow">
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Shop by Style</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="/blank-9-x-12-folders">9 x 12 Presentation Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="/welcome-folders">Welcome Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="/blank-legal-folders">Legal Size Presentation Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="/blank-small-folders">Small Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:blankextracapacityfolders</@ofbizUrl>">Extra Capacity Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:folderswithfasteners</@ofbizUrl>">Folders w/ Fasteners</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:preprintedtaxfolders</@ofbizUrl>">Pre Printed Tax Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="/poly-folders">Poly Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=photo+holders</@ofbizUrl>">Photo Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="/blank-binders">Binders</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=customizable:n use:folders</@ofbizUrl>">All Styles <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Shop by Size</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="/blank-9-x-12-folders">9 x 12 Presentation Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="/blank-legal-folders">Legal Size Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:blankextracapacityfolders</@ofbizUrl>">9 1/2 x 12 Capacity Folders</a>
                        </div>                      
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:blanksmallfolders%20s:6x9</@ofbizUrl>">6 x 9 Small Presentation Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:blanksmallfolders%20s:534x834</@ofbizUrl>">5 3/4 x 8 3/4 Small Presentation Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="/welcome-folders">5 3/4 x 8 3/4 Welcome Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=4%20x%209%20folders&af=customizable:n%20st:blanksmallfolders</@ofbizUrl>">4 x 9 Mini Folders</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=customizable:n use:folders</@ofbizUrl>">All Sizes <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Shop By Color</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" bns-shopByColor="white">White</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=NATURAL</@ofbizUrl>" bns-shopByColor="natural">Natural</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=GROCERYBAG</@ofbizUrl>" bns-shopByColor="groceryBag">Grocery Bag</a>
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
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/shopByColor/~category_id=ASSORTED</@ofbizUrl>" bns-shopByColor="assorted">Assorted</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/shopByColor</@ofbizUrl>">All Colors <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Features</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=*&af=new:y</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/newArrivalsFoldersMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="New Arrivals" /> New Arrivals</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/backToSchool</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/apple-with-ruler-icon-v3?fmt=png-alpha&amp;wid=17&amp;ts=1</@ofbizScene7Url>" alt="The Back To School Shop" /> Back To School Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/graduationShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-gradShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Graduation Shop" /> The Graduation Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-taxShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customSamplePackLanding</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-customSamples?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="Custom Samples" /> Custom Samples</a>
                        </div>
                        <h2>Accessories</h2>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:fasteners</@ofbizUrl>">Fasteners</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:holepunchers</@ofbizUrl>">Hole Punchers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=fasteners,%20notebooks,%20hole%20punchers&af=st:fasteners%20st:holepunchers%20st:notebooksnotepadsplanners</@ofbizUrl>">All Accessories <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <h2>Clearance</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div class="seeAll noPadding noMargin textRed">
                            <a class="ftc-red" bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>">Up to 75% Off <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Guides</h2>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>">Foil Stamping Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=spotColor</@ofbizUrl>">Spot Color Printing Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=fourColor</@ofbizUrl>">Four Color Printing Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=embossing</@ofbizUrl>">Embossing Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/paperStocks</@ofbizUrl>">Paper Stock Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/paperBinderStockColors</@ofbizUrl>">Paper Binder Stock Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/plasticStocks</@ofbizUrl>">Plastic Stock Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/paddedDiplomaMaterials</@ofbizUrl>">Padded Diploma Materials</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/artworkChecklist</@ofbizUrl>">Art Info Checklist</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large2 no-padding megaMenuFirstColumns">
                <div bns-tagInfo="Blank-Folder-Email-Subscribe" class="textCenter padding20 signUpBlock">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-signUp?fmt=png-alpha&amp;wid=121&amp;ts=1</@ofbizScene7Url>" alt="Sign Up!" />
                    <h3>Sign Up!</h3>
                    <p>Be the first to know about special promotions, product launches and more!</p>
                    <input bns-subscribeemailinput="headerBlankFolders" type="text" name="email_address" placeholder="Enter Your Email Address" />
                    <div bns-subscribeemailbutton="headerBlankFolders" class="foldersButton">Submit <i class="fa fa-caret-right"></i></div>
                    <span bns-subscribeemailerror="headerBlankFolders" class="error" style="background-color: transparent;color: #ff0000 !important;display: none">Invalid Email address</span>
                    <span bns-subscribeemailmessage="headerBlankFolders" style="background-color: transparent; color: #0060b6 !important; display: none">Successfully subscribed to email newsletter</span>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="dropdown-megaMenuCertificateHoldersDiplomaCovers" class="drop-down" style="width: 100%; max-width: 900px;">
    <div class="megaMenuDropdownContent">
        <div class="foldersRow">
            <div class="foldersColumn large3 no-padding megaMenuFirstColumns">
                <h2>Custom</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Certificate%20Holders</@ofbizUrl>">Certificate Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Padded%20Diploma%20Covers</@ofbizUrl>">Diploma Covers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customFolders?categoryId=Report%20Covers</@ofbizUrl>">Report Covers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=st:blankcertificateholders%20st:blankpaddeddiplomacovers%20st:preprintedpapercertificates%20customizable:y%20st:certificateholders%20st:paddeddiplomacovers%20st:reportcovers&page=0</@ofbizUrl>">All Custom Styles <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Guides</h2>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>">Foil Stamping Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=spotColor</@ofbizUrl>">Spot Color Printing Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=fourColor</@ofbizUrl>">Four Color Printing Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/printMethod?id=embossing</@ofbizUrl>">Embossing Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/paperStocks</@ofbizUrl>">Paper Stock Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/paperBinderStockColors</@ofbizUrl>">Paper Binder Stock Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/plasticStocks</@ofbizUrl>">Plastic Stock Guide</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/paddedDiplomaMaterials</@ofbizUrl>">Padded Diploma Materials</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/artworkChecklist</@ofbizUrl>">Art Info Checklist</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large3 no-padding megaMenuFirstColumns">
                <h2>Blank</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/blankCategory?category_id=CERTIFICATE_HOLDERS</@ofbizUrl>">Certificate Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=single%20certificate%20holders</@ofbizUrl>">Single Certificate Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/blankCategory?category_id=PRE_PRINTED_CERTIFICATE_HOLDERS</@ofbizUrl>">Pre-Printed Certificate Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/blankCategory?category_id=DIPLOMA_COVERS</@ofbizUrl>">Padded Diploma Covers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/blankCategory?category_id=REPORT_COVERS</@ofbizUrl>">Report Covers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/blankCategory?category_id=CERTIFICATES</@ofbizUrl>">Paper Certificates</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=notebooks</@ofbizUrl>">Notebooks &amp; Padfolios</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=customizable:n%20st:blankcertificateholders%20st:blankpaddeddiplomacovers%20st:certificateholders%20st:paddeddiplomacovers%20st:preprintedpapercertificates%20st:reportcovers&page=1</@ofbizUrl>">All Blank Styles <i class="fa fa-caret-right"></i></a>
                        </div>
                        <h2>Accessories</h2>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:fasteners</@ofbizUrl>">Fasteners</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?af=st:holepunchers</@ofbizUrl>">Hole Punchers</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=fasteners,%20notebooks,%20hole%20punchers&af=st:fasteners%20st:holepunchers%20st:notebooksnotepadsplanners</@ofbizUrl>">All Accessories <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large3 no-padding megaMenuFirstColumns">
                <h2>Features</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=*&af=new:y</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/newArrivalsFoldersMegaMenu?fmt=png-alpha&amp;wid=20</@ofbizScene7Url>" alt="New Arrivals" /> New Arrivals</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/backToSchool</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/apple-with-ruler-icon-v3?fmt=png-alpha&amp;wid=17&amp;ts=1</@ofbizScene7Url>" alt="The Back To School Shop" /> Back To School Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/graduationShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-gradShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Graduation Shop" /> The Graduation Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/taxShop</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-taxShop?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="The Tax Shop" /> The Tax Shop</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/customSamplePackLanding</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-customSamples?fmt=png-alpha&amp;wid=20&amp;ts=1</@ofbizScene7Url>" alt="Custom Samples" /> Custom Samples</a>
                        </div>
                        <h2>Clearance</h2>
                        <div class="seeAll noPadding noMargin textRed">
                            <a class="ftc-red" bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>">Up to 75% Off <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large3 no-padding megaMenuFirstColumns">
                <div bns-tagInfo="Certificate-Holder-Email-Subscribe" class="textCenter padding20 signUpBlock">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-signUp?fmt=png-alpha&amp;wid=121&amp;ts=1</@ofbizScene7Url>" alt="Sign Up!" />
                    <h3>Sign Up!</h3>
                    <p>Be the first to know about special promotions, product launches and more!</p>
                    <input bns-subscribeemailinput="headercertificateHolders" type="text" name="email_address" placeholder="Enter Your Email Address" />
                    <div bns-subscribeemailbutton="headercertificateHolders" class="foldersButton">Submit <i class="fa fa-caret-right"></i></div>
                    <span bns-subscribeemailerror="headercertificateHolders" class="error" style="background-color: transparent;color: #ff0000 !important;display: none">Invalid Email address</span>
                    <span bns-subscribeemailmessage="headercertificateHolders" style="background-color: transparent; color: #0060b6 !important; display: none">Successfully subscribed to email newsletter</span>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="dropdown-megaMailersAndEnvelopes" class="drop-down" style="width: 100%; max-width: 450px;">
    <div class="megaMenuDropdownContent">
        <div class="foldersRow">
            <div class="foldersColumn large6 no-padding megaMenuFirstColumns">
                <h2>Mailers &amp; Envelopes</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=mailers</@ofbizUrl>">Mailers</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/blankCategory?category_id=ENVELOPES</@ofbizUrl>">Envelopes</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/blankCategory?category_id=PAPERS</@ofbizUrl>">Paper &amp; Cardstock</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large6 no-padding megaMenuFirstColumns">
                <div bns-tagInfo="New-Arrivals-Email-Subscribe" class="textCenter padding20 signUpBlock">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-signUp?fmt=png-alpha&amp;wid=121&amp;ts=1</@ofbizScene7Url>" alt="Sign Up!" />
                    <h3>Sign Up!</h3>
                    <p>Be the first to know about special promotions, product launches and more!</p>
                    <input bns-subscribeemailinput="headerNewArrivals" type="text" name="email_address" placeholder="Enter Your Email Address" />
                    <div bns-subscribeemailbutton="headerNewArrivals" class="foldersButton">Submit <i class="fa fa-caret-right"></i></div>
                    <span bns-subscribeemailerror="headerNewArrivals" class="error" style="background-color: transparent;color: #ff0000 !important;display: none">Invalid Email address</span>
                    <span bns-subscribeemailmessage="headerNewArrivals" style="background-color: transparent; color: #0060b6 !important; display: none">Successfully subscribed to email newsletter</span>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="dropdown-megaWaysToShop" class="drop-down" style="width: 100%; max-width: 450px;">
    <div class="megaMenuDropdownContent">
        <div class="foldersRow">
            <div class="foldersColumn large6 no-padding megaMenuFirstColumns">
                <h2>Clearance</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale%20use:folders</@ofbizUrl>">Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale%20use:certificates</@ofbizUrl>">Certificate Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/product/~category_id=BLANK_POLY_BINDERS/~product_id=PB-TUFFY</@ofbizUrl>">Binders</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale&page=0</@ofbizUrl>">All Clearance <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
                <h2>New Arrivals</h2>
                <div class="foldersRow">
                    <div class="foldersColumn large12 noPadding">
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=*&af=new:y%20use:folders</@ofbizUrl>">Folders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=*&af=new:y%20use:certificates%20st:blankcertificateholders</@ofbizUrl>">Certificate Holders</a>
                        </div>
                        <div>
                            <a bns-mega-menu-level="4" href="<@ofbizUrl>/search?w=*&af=new:y%20st:blankpolybinders</@ofbizUrl>">Binders</a>
                        </div>
                        <div class="seeAll">
                            <a bns-mega-menu-level="5" href="<@ofbizUrl>/search?w=*&af=new:y</@ofbizUrl>">All New Arrivals <i class="fa fa-caret-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foldersColumn large6 no-padding megaMenuFirstColumns">
                <div bns-tagInfo="Clearance-Email-Subscribe" class="textCenter padding20 signUpBlock">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ficon-signUp?fmt=png-alpha&amp;wid=121&amp;ts=1</@ofbizScene7Url>" alt="Sign Up!" />
                    <h3>Sign Up!</h3>
                    <p>Be the first to know about special promotions, product launches and more!</p>
                    <input bns-subscribeemailinput="headerClearance" type="text" name="email_address" placeholder="Enter Your Email Address" />
                    <div bns-subscribeemailbutton="headerClearance" class="foldersButton">Submit <i class="fa fa-caret-right"></i></div>
                    <span bns-subscribeemailerror="headerClearance" class="error" style="background-color: transparent;color: #ff0000 !important;display: none">Invalid Email address</span>
                    <span bns-subscribeemailmessage="headerClearance" style="background-color: transparent; color: #0060b6 !important; display: none">Successfully subscribed to email newsletter</span>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="dropdown-myAccount" class="drop-down">
    <div style="display: table; width: 100%; font-size: 12px;">
        <div class="my-account-right">
            <h4 style="font-size: 15px;">Hello, <span class="jqs-firstName"></span>!</h4>
            <a href="<@ofbizUrl>/account</@ofbizUrl>" style="display: block;">View Your Account</a>
            <h5 class="marginTop10" style="font-size: 12px;">Orders:</h5>
            <a href="<@ofbizUrl>/openOrders</@ofbizUrl>" style="display: block;">View Open Orders</a>
            <a href="<@ofbizUrl>/orderList</@ofbizUrl>" style="display: block;">View All Orders</a>
            <a href="<@ofbizUrl>/orderList</@ofbizUrl>" style="display: block;">Reorder Center</a>
            <h5 class="marginTop10" style="font-size: 12px;">Account:</h5>
            <a href="<@ofbizUrl>/users</@ofbizUrl>" style="display: block;">Account Users</a>
            <h5 class="marginTop10" style="font-size: 12px;">Files:</h5>
            <a href="<@ofbizUrl>/uploadedFiles</@ofbizUrl>" style="display: block;">Uploaded Images/Files</a>
            <a href="<@ofbizUrl>/proofApproval</@ofbizUrl>" style="display: block;">Proof Approval Center</a>
            <a href="#" class="jqs-logout-button" data-dest="logout" style="position: absolute; bottom: 5px; right: 10px;">Log Out</a>
        </div>
    </div>
</div>

<div id="dropdown-sli-search-results" class="drop-down sli-search-results"></div>

<#--
=========================
===== END DROPDOWNS =====
=========================
-->

<#--
=============================
===== BEGIN MOBILE MENU =====
=============================
-->

<div id="mobileMenu" class="mobileMenu hidden">
    <div class="menuDropDownOddContainer marginBottom20">
        <div class="textBold menuDropDownOdd noMargin jqs-menuDropDown">
            Custom Printed <i class="fa fa-caret-right pullRight"></i>
        </div>
        <div class="menuDropDownEvenContainer hidden">            
            <div class="textBold menuDropDownEven jqs-menuDropDown">
                Presentation Folders <i class="fa fa-caret-right pullRight"></i>
            </div>
            <div class="menuDropDownLinks hidden">
                <a href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>">9 x 12 Presentation Folders</a>
                <a href="<@ofbizUrl>/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C</@ofbizUrl>">Legal Size Presentation Folders</a>
                <a href="<@ofbizUrl>/customFolders?categoryId=Small%20Folder</@ofbizUrl>">Small Folders</a>
                <a href="<@ofbizUrl>/customFolders?categoryId=Specialty%209%20x%2012%20Presentation%20Folder</@ofbizUrl>">Specialty Folders</a>
            </div>
            <div class="textBold menuDropDownEven jqs-menuDropDown">
                Certificate Holders &amp; Report Covers <i class="fa fa-caret-right pullRight"></i>
            </div>
            <div class="menuDropDownLinks hidden">
                <a href="<@ofbizUrl>/customFolders?categoryId=Certificate%20Holder</@ofbizUrl>">Certificate Holders</a>
            </div>
            <div class="textBold menuDropDownEven jqs-menuDropDown">
                Portfolios <i class="fa fa-caret-right pullRight"></i>
            </div>
            <div class="menuDropDownLinks hidden">
                <a href="<@ofbizUrl>/search?w=Portfolios&af=st:portfolios</@ofbizUrl>">Portfolios</a>
            </div>
            <div class="textBold menuDropDownEven jqs-menuDropDown">
                Small Folders <i class="fa fa-caret-right pullRight"></i>
            </div>
            <div class="menuDropDownLinks hidden">
                <a href="<@ofbizUrl>/customFolders?categoryId=Small%20Folder</@ofbizUrl>">Small Folders</a>
                <a href="<@ofbizUrl>/customFolders?categoryId=Small%20Folder</@ofbizUrl>#CardHolder">Card Holders</a>
            </div>
        </div>
        <div class="textBold menuDropDownOdd jqs-menuDropDown">
            Blank Products <i class="fa fa-caret-right pullRight"></i>
        </div>
        <div class="menuDropDownEvenContainer hidden">            
            <div class="textBold menuDropDownEven jqs-menuDropDown">
                Presentation Folders <i class="fa fa-caret-right pullRight"></i>
            </div>
            <div class="menuDropDownLinks hidden">
                <a href="<@ofbizUrl>/search?af=st:blankstandardpresentationfolders</@ofbizUrl>">9 x 12 Presentation Folders</a>
                <a href="<@ofbizUrl>/search?af=st:blanklegalfolders</@ofbizUrl>">Legal Size Presentation Folders</a>
                <a href="<@ofbizUrl>/search?af=st:blanksmallfolders</@ofbizUrl>">Small Folders</a>
            </div>
            <div class="textBold menuDropDownEven jqs-menuDropDown">
                Certificate Holders &amp; Report Covers <i class="fa fa-caret-right pullRight"></i>
            </div>
            <div class="menuDropDownLinks hidden">
                <a href="<@ofbizUrl>/search?af=st:blankcertificateholders</@ofbizUrl>">Certificate Holders</a>
            </div>
            <div class="textBold menuDropDownEven jqs-menuDropDown">
                Binders <i class="fa fa-caret-right pullRight"></i>
            </div>
            <div class="menuDropDownLinks hidden">
                <a href="<@ofbizUrl>/search?af=st:blankpolybinders</@ofbizUrl>">Poly Binders</a>
            </div>
            <div class="textBold menuDropDownEven jqs-menuDropDown">
                Small Folders <i class="fa fa-caret-right pullRight"></i>
            </div>
            <div class="menuDropDownLinks hidden">
                <a href="<@ofbizUrl>/search?af=st:blanksmallfolders</@ofbizUrl>">Small Folders</a>
                <a href="<@ofbizUrl>/search?w=card%20holder&af=st:blankcardholders</@ofbizUrl>">Card Holders</a>
            </div>
        </div>
        <div class="textBold menuDropDownOdd jqs-menuDropDown">
            Resources <i class="fa fa-caret-right pullRight"></i>
        </div>
        <div class="menuDropDownLinks hidden">
            <a href="<@ofbizUrl>/paperStocks</@ofbizUrl>">Paper Stock Guide</a>
            <a href="<@ofbizUrl>/foilGuide</@ofbizUrl>">Foil Color Guide</a>
            <a href="<@ofbizUrl>/paperBinderStockColors</@ofbizUrl>">Paper Binder Stock Guide</a>
            <a href="<@ofbizUrl>/plasticStocks</@ofbizUrl>">Plastic Stock Guide</a>
            <a href="<@ofbizUrl>/paddedDiplomaMaterials</@ofbizUrl>">Padded Diploma Materials</a>
            <a href="<@ofbizUrl>/artworkChecklist</@ofbizUrl>">Art Info Cheklist</a>
        </div>
        <a href="<@ofbizUrl>/cart</@ofbizUrl>" class="textBold menuDropDownOdd" style="display: block;">
            <i class="fa fa-shopping-cart"></i> Shopping Cart <i class="fa fa-caret-right pullRight"></i>
        </a>
        <a href="<@ofbizUrl>/account</@ofbizUrl>" class="textBold menuDropDownOdd" style="display: block;">
            <i class="fa fa-user"></i> My Account <i class="fa fa-caret-right pullRight"></i>
        </a>
        <a href="<@ofbizUrl>/account</@ofbizUrl>" class="textBold menuDropDownOdd" style="display: block;">
            <i class="fa fa-refresh"></i> Reorder <i class="fa fa-caret-right pullRight"></i>
        </a>
        <a href="tel:1-800-296-4321" class="textBold menuDropDownOdd noMargin ftc-green menuPhone">
            <i class="fa fa-phone"></i> 800-296-4321
        </a>
    </div>
</div>

<!-- Data Reveal ID for Free Shipping -->
<#macro freeShippingContent>
    <div class="bnRevealHeader fbc-blue">
        <h3>Free Shipping Offer</h3>
        <i class="fa fa-times jqs-bnRevealClose"></i>
    </div>
    <div class="bnRevealBody">
        <div class="padding20 jqs-form_info" style="font-size:15px;">
            Free Standard Shipping on all orders of $${globalContext.freeShippingAmount?default("299")} or more with code FREE${globalContext.freeShippingAmount?default("299")}. Offer not valid for Alaska, Hawaii, United States Territories or International destinations. Offer is not redeemable on previous orders and cannot be combined with any other offer. Terms of offer are subject to change at any time. 
        </div>
        <div class="ftc-blue paddingLeft20 paddingRight20 jqs-form_info">
            <h4>Join Our Mailing List:</h4>
            <p class="marginTop5">Sign up for discount offerings, new product launches &amp; more!</p>
        </div>
        <div class="freeShippingPopUpMailingList padding20 stayInTheLoopColumns jqs-form_info">
            <p>Email:</p>
            <input type="text" name="email_address">
            <div class="stayInTheLoopButton jqs-submit_email">Submit <i class="fa fa-caret-right"></i></div>
            <div class="jqs-sitl_error sitl_error hidden"></div>
        </div>
        <div class="jqs-response_text margin20"></div>
    </div>
</#macro>
<div id="fship" class="bnRevealContainer foldersMain" bns-additionalsource="Free Shipping Popup">
    <@freeShippingContent />
</div>
<div id="fship2" class="bnRevealContainer foldersMain" bns-additionalsource="Free Shipping Popup Top Header">
    <@freeShippingContent />
</div>

<!-- Data Reveal ID for Price Matching -->
<div id="priceMatch" class="bnRevealContainer foldersMain">
    <div class="bnRevealHeader fbc-blue">
        <h3>Price Match</h3>
        <i class="fa fa-times jqs-bnRevealClose"></i>
    </div>
    <div class="bnRevealBody">
        <div class="paddingLeft20 paddingRight20 paddingBottom20" style="font-size:15px;">
            Please email a competitor’s quote using a PDF, ScreenShot, or Forwarded Email to service@folders.com. We will respond promptly and work to match or beat a competitor price. <br /><br />IMPORTANT: This applies to future orders and cannot be applied to orders placed in the past.
        </div>
    </div>
</div>

<div id="stayInTheLoop-mobile" class="stay_in_the_loop-mobile bnRevealContainer foldersSitl">
    <a class="close-reveal-modal top jqs-decline_email"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/sitlCloseButton?fmt=png-alpha</@ofbizScene7Url>" alt="Envelopes.com" /></a>
    <div class="foldersSitlMobileContainer">
        <div style="font-size: 31px; font-style: italic; font-weight: bold; color: #ffffff; line-height: 37px; -webkit-text-stroke-width: 1px; display: inline-block; padding-right: 5px; text-shadow: 0px 3px 2px rgba(0, 0, 0, 0.2); position: relative; top: -5px;">$10 OFF</div>
		<span bns-sitlShowOnSuccess class="hidden" style="font-size: 15px; -webkit-text-stroke-width: 1px; color: #ffffff; font-weight: bold; font-style: italic; position: relative; text-shadow: 0px 3px 2px rgba(0, 0, 0, 0.2); top: -5px;">ANY ORDER OVER $50</span>
		<span bns-sitlHideOnSuccess style="width: 170px; display: inline-block; color: #ffffff; font-size: 12px; line-height: 14px; padding: 3px 0px 0px 5px; text-align: left;">Sign up to receive<br />special promotions,<br />product launches &amp; more!</span>
        <div bns-sitlHideOnSuccess class="jqs-sitl_error sitl_error hidden"></div>
        <div bns-sitlHideOnSuccess class="row jqs-submission submission">
            <input type="text" name="email_address" value="" placeholder="Email" class="no-margin" />
            <div class="jqs-submit_email foldersSitlMobileBtn">Submit</div>
        </div>
    </div>
    <p bns-sitlShowOnSuccess class="sitlTextSuccess hidden">Use Code: <span class="sitlCouponCode">SAVE10</span></p>
    <div bns-sitlShowOnSuccess class="jqs-decline_email foldersSitlSuccessBtn hidden">Shop Now</div>
</div>

<#macro stayInTheLoopContent>
    <div class="textCenter sitlContainer">
        <a class="close-reveal-modal top jqs-bnRevealClose"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/sitlCloseButton?fmt=png-alpha</@ofbizScene7Url>" alt="Envelopes.com" /></a>
        <div bns-sitlHideOnSuccess class="sitlHeader">WELCOME TO</div>
        <img style="width: 200px;" src="<@ofbizContentUrl>/html/img/logo/foldersWhiteLarge.png</@ofbizContentUrl>" alt="Folders.com" title="Folders.com" />
        <div style="position: relative; left: -15px; font-size: 110px; font-style: italic; font-weight: bold; color: #ffffff; line-height: 112px; -webkit-text-stroke-width: 5px; text-shadow: 2px 8px 4px rgba(0, 0, 0, 0.2);">$10 OFF</div>
        <p bns-sitlHideOnSuccess class="sitlText">Sign up to receive special promotions, product launches &amp; more!</p>
		<div bns-sitlShowOnSuccess class="hidden" style="text-transform: uppercase; font-weight: bold; font-size: 35px; color: #ffffff; -webkit-text-stroke-width: 2px; line-height: 42px; font-style: italic; text-shadow: 1px 4px 3px rgba(0, 0, 0, 0.2);">Any Order Over $50</div>
        <p class="sitlTextSuccess">Use Code: <span class="sitlCouponCode">SAVE10</span></p>
        <div class="jqs-sitl_error sitl_error hidden"></div>
        <input bns-sitlHideOnSuccess type="text" name="email_address" value="" placeholder="Email"/>
        <div bns-sitlHideOnSuccess class="stayInTheLoopButton jqs-submit_email">Submit</div>
        <div bns-sitlShowOnSuccess class="stayInTheLoopButton close-reveal-modal jqs-bnRevealClose hidden">Shop Now</div>
    </div>
</#macro>

<div id="stayInTheLoop" class="stay_in_the_loop bnRevealContainer emailSignUp" data-reveal data-limit="true">
    <@stayInTheLoopContent />
</div>
<div id="todaysDealEmailSignUp" class="stay_in_the_loop bnRevealContainer emailSignUp" data-reveal data-limit="true" bns-additionalsource="Today's Deals Email Sign Up">
    <@stayInTheLoopContent />
</div>

<script>
    $('.jqs-mobileMenu').on('click', function(e) {
        e.preventDefault();
        if ($('#mobileMenu').hasClass('hidden')) {
            $(window).scrollTop(0);
            $('#mobileMenu').removeClass('hidden');
        } else {
            $('#mobileMenu').addClass('hidden');
        }
    });

    $('.jqs-menuDropDown').on('click', function() {
        if ($(this).next().hasClass('hidden')) {
            $(this).next().removeClass('hidden');
            $(this).find('i.fa-caret-right').removeClass('fa-caret-right').addClass('fa-caret-down');
        } else {
            $(this).next().addClass('hidden');
            $(this).find('i.fa-caret-down').removeClass('fa-caret-down').addClass('fa-caret-right');
        }
    });
</script>

<#--
===========================
===== END MOBILE MENU =====
===========================
-->

<script>
    $().updateHeaderLogin();
    $().updateMiniCart();
    $.checkAuthenticated();

</script>