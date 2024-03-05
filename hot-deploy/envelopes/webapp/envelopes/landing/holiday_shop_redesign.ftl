<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/landing/holiday_shop_redesign.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/landing/bnc_collapsible.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<div class="bnc_ths_shop">
    <#-------------- BREADCRUMB ------------------->
	<div class="tablet-desktop-only margin-top-xs margin-bottom-xs">
		<#include "../includes/breadcrumbs.ftl" />
	</div>
    <#-------------- TOP BANNER -------------------->
	<div class="bnc_ths_top_banner">
        <div class="ths_banner_wrap">
            <#if (currentTimestamp?default(now?datetime) gte "2021-12-06 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-08 23:59:59.000"?datetime)>
            <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-santas-workshop-holiday-promo-landing-page-mobile-banner?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
            <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-santas-workshop-holiday-promo-landing-page-desktop-banner?wid=1360&fmt=png-alpha</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
            <#elseif (currentTimestamp?default(now?datetime) gte "2021-12-09 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
            <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-santas-workshop-elves-landing-page-mobile-banner?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
            <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-envelopes-santas-workshop-elves-landing-page-desktop-banner?wid=1360&fmt=png-alpha</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
            <#else>
            <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-mobile-banner?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
            <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/2021-holiday-shop-envelopes-homepage-desktop-banner?wid=1360&fmt=png-alpha</@ofbizScene7Url>" alt="Holiday Shop | Envelopes.com" />
            </#if>
        </div>
	</div>
	<#-------------- CUSTOM NAVIGATION ------------->
    <nav role="bnc_ths_nav1">
        <input type="checkbox" id="ths-button">
        <label for="ths-button" onclick></label>
        <ul>
            <li><a href="#ENV_ths_BestSellers" title="Best Sellers">Best Sellers</a></li>
            <li><a href="#ENV_ths_SocialInvitation" title="Social &amp; Invitation">Social &amp; Invitation</a></li>
            <li><a href="#ENV_ths_SpecialtyGifting" title="Specialty &amp; Gifting">Specialty &amp; Gifting</a></li>
            <li><a href="#ENV_ths_GiftBoxesBags" title="Gift Boxes &amp; Bags">Gift Boxes &amp; Bags</a></li>
            <li><a href="#ENV_ths_PackingShipping" title="Packing &amp; Shipping">Packing &amp; Shipping</a></li>
            <li><a href="#ENV_ths_CustomPrintServices" title="White Ink Printing">White Ink Printing</a></li>
            <li><a href="#ENV_ths_CustomPrintServices" title="Recipient Addressing">Recipient Addressing</a></li>
        </ul>
    </nav>
    <#-------------- LEFT COLUMN ------------------->
    <div class="ENV_LeftColumn_Wrap">
        <div class="Inner_LeftRail_Content">
            <div class="siderail-banner1">
                <h2 class="margin-bottom-xs">Shop by Size</h2>
                <div class="rowWrapper env_row">
                    <div class="columns large-6 medium-6 small-12">
                        <div class="smallHeader">Card Size</div>
                    </div>
                    <div class="columns large-6 medium-6 small-12">
                        <div class="smallHeader">Envelope Size</div>
                    </div>
                </div>
                <a class="env-prod-cta" href="<@ofbizUrl>/category?af=si:358x518%20st:squareflap%20st:contourflap</@ofbizUrl>" title="A1 Envelopes | Holiday Shop | Envelopes.com">
                    <div class="block-row">
                        <div class="inner-block-row">
                            <div>3 1/2 x 4 7/8</div>
                            <div><i class="fa fa-arrow-right"></i></div>
                            <div>A1</div>
                        </div>
                    </div>
                </a>
                <a class="env-prod-cta" href="<@ofbizUrl>/category?af=si:438x534%20st:squareflap%20st:contourflap</@ofbizUrl>" title="A2 Envelopes | Holiday Shop | Envelopes.com">
                <div class="block-row">
                    <div class="inner-block-row">
                        <div>4 1/4 x 5 1/2</div>
                        <div><i class="fa fa-arrow-right"></i></div>
                        <div>A2</div>
                    </div>
                </div>
                </a>
                <a class="env-prod-cta" href="<@ofbizUrl>/category?af=si:434x612%20st:squareflap%20st:contourflap</@ofbizUrl>" title="A6 Envelopes | Holiday Shop | Envelopes.com">
                    <div class="block-row">
                        <div class="inner-block-row">
                            <div>4 5/8 x 6 1/4</div>
                            <div><i class="fa fa-arrow-right"></i></div>
                            <div>A6</div>
                        </div>
                    </div>
                </a>
                <a class="env-prod-cta" href="<@ofbizUrl>/category?af=si:514x714</@ofbizUrl>" title="A7 Envelopes | Holiday Shop | Envelopes.com">
                    <div class="block-row">
                        <div class="inner-block-row">
                            <div>5 1/8 x 7</div>
                            <div><i class="fa fa-arrow-right"></i></div>
                            <div>A7</div>
                        </div>
                    </div>
                </a>
                <a class="env-prod-cta" href="<@ofbizUrl>/category?af=si:512x818&sort=PRICE_HIGH</@ofbizUrl>" title="A8 Envelopes | Holiday Shop | Envelopes.com">
                    <div class="block-row">
                        <div class="inner-block-row">
                            <div>5 1/2 x 7</div>
                            <div><i class="fa fa-arrow-right"></i></div>
                            <div>A8</div>
                        </div>
                    </div>
                </a>
                <a class="env-prod-cta" href="<@ofbizUrl>/category?af=si:534x834</@ofbizUrl>" title="A9 Envelopes | Holiday Shop | Envelopes.com">
                    <div class="block-row">
                        <div class="inner-block-row">
                            <div>5 1/2 x 8 1/2</div>
                            <div><i class="fa fa-arrow-right"></i></div>
                            <div>A9</div>
                        </div>
                    </div>
                </a>
                <a class="env-prod-cta" href="<@ofbizUrl>/category?af=si:378x878</@ofbizUrl>" title="#9 Envelopes | Holiday Shop | Envelopes.com">
                    <div class="block-row">
                        <div class="inner-block-row">
                            <div>8 1/2 x 11</div>
                            <div><i class="fa fa-arrow-right"></i></div>
                            <div>#9</div>
                        </div>
                    </div>
                </a>
                <a class="env-prod-cta" href="<@ofbizUrl>/category?af=si:418x912</@ofbizUrl>" title="#10 Envelopes | Holiday Shop | Envelopes.com">
                    <div class="block-row">
                        <div class="inner-block-row">
                            <div>8 1/2 x 11</div>
                            <div><i class="fa fa-arrow-right"></i></div>
                            <div>#10</div>
                        </div>
                    </div>
                </a>
                <a class="env-prod-cta" href="<@ofbizUrl>/category?af=si:21116x31116</@ofbizUrl>" title="17 Mini Envelopes | Holiday Shop | Envelopes.com">
                    <div class="block-row">
                        <div class="inner-block-row">
                            <div>2 9/16 x 3 9/16</div>
                            <div><i class="fa fa-arrow-right"></i></div>
                            <div>17 Mini</div>
                        </div>
                    </div>
                </a>
                <div class="env-shop-all-cta">
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" title="Shop All Sizes | Envelopes.com">
						<p>Shop All Sizes <i class="fa fa-caret-right"></i></p>
					</a>
				</div>
            </div>
            <div class="siderail-banner2">
                <h2 class="margin-bottom-xs">Shop by Color</h2>
                <div class="env-holiday-color-prods">
                    <div class="env-holiday-color-content">
                        <a class="env-prod-cta" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" title="White Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-white-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                        <a class="env-prod-cta" href="<@ofbizUrl>/shopByColor/~category_id=RED</@ofbizUrl>" title="Red Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-red-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                        <a class="env-prod-cta" href="<@ofbizUrl>/shopByColor/~category_id=GOLD</@ofbizUrl>" title="Gold Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-gold-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                        <a class="env-prod-cta" href="<@ofbizUrl>/shopByColor/~category_id=SILVER_SILVERMETALLIC</@ofbizUrl>" title="Silver Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-silver-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                        <a class="env-prod-cta" href="<@ofbizUrl>/search?w=grocery+bag</@ofbizUrl>" title="Grocery Bag Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-grocerybag-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                        <a class="env-prod-cta" href="<@ofbizUrl>/shopByColor/~category_id=BLUE</@ofbizUrl>" title="Blue Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-blue-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                        <a class="env-prod-cta" href="<@ofbizUrl>/shopByColor/~category_id=GREEN</@ofbizUrl>" title="Green Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-green-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                        <a class="env-prod-cta" href="<@ofbizUrl>/shopByColor/~category_id=BLACK</@ofbizUrl>" title="Black Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-black-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                        <a class="env-prod-cta" href="<@ofbizUrl>/shopByColor/~category_id=NATURAL</@ofbizUrl>" title="Natural Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-natural-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                        <a class="env-prod-cta" href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" title="Metallic Envelopes | Holiday Shop | Envelopes.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-holiday-shop-shop-by-color-metallic-thumbnail?fmt=png-alpha&amp;wid=118</@ofbizScene7Url>" atl="" border="0" />
                        </a>
                    </div>
                </div>
                <div class="env-shop-all-cta">
					<a href="<@ofbizUrl>/shopByColor</@ofbizUrl>" title="Shop All Colors | Envelopes.com">
						<p>Shop All Colors <i class="fa fa-caret-right"></i></p>
					</a>
				</div>
            </div>
        </div>
    </div>
    <#-------------- RIGHT COLUMN ------------------->
    <div class="ENV_RightColumn_Wrap">
        <#---------------- BEST SELLERS ----------------->
        <div id="ENV_ths_BestSellers" class="bnc_section_block">
            <div class="bnc_section_content">
                <div class="bnc_section_header"><h2>The Holiday Shop Best Sellers</h2></div>
                <p class="bnc_section_desc">Be prepared for the holidays with popular seasonal envelopes and wrapping. Send your gifts in one of our best selling items that's sure to bring cheer!</p>
            </div>
            <div id="ENV_ths_BestSellers_content" class="bnc_ths_product_wrap margin-top-xs flex-row flex-left">
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+printed+envelopes</@ofbizUrl>" title="Holiday Printed Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-printed-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Holiday Printed Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Holiday Printed Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined%20col:linedenvelopes</@ofbizUrl>" title="LUX Foil Lined | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/lux-foil-lined-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="LUX Foil Lined | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>LUX Foil Lined</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=wrapping+paper</@ofbizUrl>" title="Wrapping Paper | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-wrapping-paper-ths-prod-thumbnail-transparent-v1?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Wrapping Paper | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Wrapping Paper</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <#------------ SOCIAL & INVITATION -------------->
        <div id="ENV_ths_SocialInvitation" class="bnc_section_block">
            <div class="bnc_section_content">
                <div class="bnc_section_header"><h2>Social &amp; Invitation Envelopes</h2></div>
                <p class="bnc_section_desc">Shine bright inside and out with our collection of LUX Foil Lined Envelopes, Metallic and Glitter Envelopes or keep it traditional with envelopes in our classic holiday colors. Choose from many options like, Contour Flap, Square Flap, Photo Greeting Envelopes & more. It's the most wonderful time of the year!</p>
            </div>
            <div id="ENV_ths_SocialInvitation_content" class="bnc_ths_product_wrap margin-top-xs flex-row flex-left">
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category?af=si:514x714</@ofbizUrl>" title="A7 Invitation Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/a7-invitation-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="A7 Invitation Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>A7 Invitation Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=%2310+square+flap+envelopes</@ofbizUrl>" title="#10 Square Flap Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-ten-square-flap-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#10 Square Flap Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>#10 Square Flap Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+credit+card+sleeves</@ofbizUrl>" title="Gift Card Sleeves | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-card-sleeves-ths-prod-thumbnail-transparent-v1?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Card Sleeves | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Gift Card Sleeves</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined%20col:linedenvelopes</@ofbizUrl>" title="LUX Foil Lined | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/lux-foil-lined-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="LUX Foil Lined | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>LUX Foil Lined</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=grocery+bag</@ofbizUrl>" title="Grocery Bag Collection | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/grocery-bag-collection-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Grocery Bag Collection | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Grocery Bag Collection</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+metallic+envelopes</@ofbizUrl>" title="Metallic Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/metallic-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Metallic Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Metallic Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=mirri+sparkle</@ofbizUrl>" title="Sparkle Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/sparkle-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Sparkle Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Sparkle Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category/~category_id=SQUARE</@ofbizUrl>" title="Square Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/square-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Square Envelopess | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Square Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+contour+flap+envelopes</@ofbizUrl>" title="Contour Flaps | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/contour-flaps-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Contour Flaps | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Contour Flaps</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+pointed+flap+envelopes</@ofbizUrl>" title="Baronial Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/baronial-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Baronial Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Baronial Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=slimline+envelopes</@ofbizUrl>" title="Slimline Invitation Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/slimline-invitation-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Slimline Invitation Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Slimline Invitation Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+photo+greeting+envelopes</@ofbizUrl>" title="Photo Greeting Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/photo-greeting-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Photo Greeting Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Photo Greeting Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <#-------------- FULL WIDTH COLUMN -------------->
    <div class="ENV_FullWidthColumn_Wrap">
        <#----------------------------------------------->
        <#-------- SPECIALTY & GIFTING ENVELOPES -------->
        <#----------------------------------------------->
        <div id="ENV_ths_SpecialtyGifting" class="bnc_section_block">
            <div bns-collapse_content="ENV_ths_SpecialtyGifting_content" bns-collapsed="false" class="bnc_section_content">
                <div class="bnc_section_header"><h2>Specialty &amp; Gifting Envelopes</h2></div>
                <p class="bnc_section_desc">It's the season of giving and we make gifting easy with a full assortment of Mini Envelopes, Gift Card Sleeves, Currency Envelopes, Remittance Envelopes and more. There's a lot of giving coming your way!</p>
            </div>
            <div id="ENV_ths_SpecialtyGifting_content" class="bnc_ths_product_wrap margin-top-xs flex-row flex-left">
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+credit+card+sleeves</@ofbizUrl>" title="Gift Card Sleeves | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-card-sleeves-ths-prod-thumbnail-transparent-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Card Sleeves | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Gift Card Sleeves</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category?af=si:214x312</@ofbizUrl>" title="#1 Coin Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-one-coin-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#1 Coin Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>#1 Coin Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category?af=si:21116x31116</@ofbizUrl>" title="#17 Mini Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-seventeen-mini-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#17 Mini Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>#17 Mini Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category/~category_id=REMITTANCE</@ofbizUrl>" title="Remittance Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/remittance-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Remittance Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Remittance Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+currency+envelopes</@ofbizUrl>" title="Currency Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/currency-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Currency Envelopes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Currency Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=folded+card+set</@ofbizUrl>" title="Folded Holiday Card Sets | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folded-holiday-card-sets-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Folded Holiday Card Sets | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Folded Holiday Card Sets</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <#--  <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>#</@ofbizUrl>" title="Wrapping Paper | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-wrapping-paper-ths-prod-thumbnail-transparent-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Wrapping Paper | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Wrapping Paper</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>  -->
                <#--  <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>#</@ofbizUrl>" title="Letter to Santa Kits | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/letter-to-santa-kits-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Letter to Santa Kits | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Letter to Santa Kits</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>  -->
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category?af=st:notecards</@ofbizUrl>" title="Flat &amp; Folded Cards | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/flat-folded-cards-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Flat &amp; Folded Cards | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Flat &amp; Folded Cards</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category?af=si:12x18%20si:13x19%20si:812x11%20si:11x17%20si:12x12%20si:812x14%20st:paper%20st:cardstock&sort=SIZE_SMALL</@ofbizUrl>" title="Paper &amp; Cardstock | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/paper-cardstock-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paper &amp; Cardstock | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Paper &amp; Cardstock</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=variety+packs</@ofbizUrl>" title="Variety Packs | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/variety-packs-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Variety Packs | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Variety Packs</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=belly+bands</@ofbizUrl>" title="Belly Bands | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/belly-bands-ths-prod-thumbnail-transparent-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Belly Bands | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Belly Bands</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=liners</@ofbizUrl>" title="Drop in Liners | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/drop-in-liners-envelopes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Drop in Liners | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Drop in Liners</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <#--  <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>#</@ofbizUrl>" title="Embossed Holiday Cards | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Embossed Holiday Cards | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Embossed Holiday Cards</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>  -->
                <#--  <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=gift+tags</@ofbizUrl>" title="Gift Tags | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-tags-ths-prod-thumbnail-transparent-v1?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Tags | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Gift Tags</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>  -->
            </div>
        </div>
        <#----------------------------------------------->
        <#-------------- GIFT BAGS & BOXES -------------->
        <#----------------------------------------------->
        <div id="ENV_ths_GiftBoxesBags" class="bnc_section_block">
            <div bns-collapse_content="ENV_ths_GiftBoxesBags_content" bns-collapsed="false" class="bnc_section_content">
                <div class="bnc_section_header"><h2>Gift Bags &amp; Boxes</h2></div>
                <p class="bnc_section_desc">Wrap and pack your gifts of any size or shape in premium Wrapping Paper, Holiday Printed Gift Bags and Gift Boxes. Keep your gifts safe, secure and full of holiday cheer!</p>
            </div>
            <div id="ENV_ths_GiftBoxesBags_content" class="bnc_ths_product_wrap margin-top-xs flex-row flex-left">
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>" title="Gift Bags | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-bags-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Bags | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Gift Bags</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=wine+bottle+bags</@ofbizUrl>" title="Wine Bottle Bags | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wine-bottle-bags-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Wine Bottle Bags | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Wine Bottle Bags</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=gift+boxes&c=0</@ofbizUrl>" title="Gift Boxes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-boxes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Boxes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Gift Boxes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=magnetic+boxes</@ofbizUrl>" title="Magnetic Boxes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/magnetic-boxes-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Magnetic Boxes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Magnetic Boxes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <#----------------------------------------------->
        <#-------------- STOCKING STUFFERS -------------->
        <#----------------------------------------------->
        <div id="ENV_HolidayStockingStuffers" class="bnc_section_block">
            <div bns-collapse_content="ENV_HolidayStockingStuffers_content" bns-collapsed="false" class="bnc_section_content">
                <div class="bnc_section_header"><h2>Stocking Stuffers</h2></div>
                <p class="bnc_section_desc">Fill your stockings with these must-haves!</p>
            </div>
            <div id="ENV_HolidayStockingStuffers_content" class="bnc_weddingshop_product_wrap margin-top-xs flex-row flex-center">
                <div class="bnc_ths_prod ths_widthOverWrite2">
                    <a href="<@ofbizUrl>/search?w=santa+kit</@ofbizUrl>" title="Letters to Santa Kit | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/letter-to-santa-ths-prod-thumbnail-transparent-v2?fmt=png-alpha&amp;wid=610</@ofbizScene7Url>" alt="Letters to Santa Kit | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h5>Letters to Santa</h5> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod ths_widthOverWrite2">
                    <a href="<@ofbizUrl>/search?w=stocking+stuffer</@ofbizUrl>" title="Stocking Stuffers | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/stocking-stuffers-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=610</@ofbizScene7Url>" alt="Stocking Stuffers | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h5>Stocking Stuffers</h5> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <#----------------------------------------------->
        <#-------------- PACKING & SHIPPING ------------->
        <#----------------------------------------------->
        <div id="ENV_ths_PackingShipping" class="bnc_section_block">
            <div bns-collapse_content="ENV_ths_PackingShipping_content" bns-collapsed="false" class="bnc_section_content">
                <div class="bnc_section_header"><h2>Packing &amp; Shipping</h2></div>
                <p class="bnc_section_desc">Whether you're packaging something small or large, we've got your gifts covered. Choose from a variety of LUX Mailers, Tyvek Envelopes, Holiday Mailers, Mailing Tubes and more. Your holiday gifts will be protected &amp; arrive in style!</p>
            </div>
            <div id="ENV_ths_PackingShipping_content" class="bnc_ths_product_wrap margin-top-xs flex-row flex-left">
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+mailers</@ofbizUrl>" title="Holiday Design Mailers | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/holiday-design-mailers-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Holiday Design Mailers | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Holiday Design Mailers</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=holiday+lux+mailers</@ofbizUrl>" title="LUX Mailers | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/lux-mailers-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="LUX Mailers | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>LUX Mailers</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category/~category_id=BUBBLE_LINED</@ofbizUrl>" title="Bubble Mailers | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/bubble-mailers-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Bubble Mailers | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Bubble Mailers</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=plastic+mailers</@ofbizUrl>" title="Heavy Duty Plastic Mailers | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/heavy-duty-plastic-mailers-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Heavy Duty Plastic Mailers | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Heavy Duty Plastic Mailers</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/category/~category_id=PAPERBOARD_MAILERS</@ofbizUrl>" title="Paperboard Mailers | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/paperboard-mailers-ths-prod-thumbnail-transparent-v1?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paperboard Mailers | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Paperboard Mailers</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=conformer+mailers</@ofbizUrl>" title="Conformer Mailers | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/conformer-mailers-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Conformer Mailers | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Conformer Mailers</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=tyvek+mailers</@ofbizUrl>" title="Tyvek Mailers | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/tyvek-mailers-ths-prod-thumbnail-transparent-v1?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Tyvek Mailers | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Tyvek Mailers</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=mailing+tubes</@ofbizUrl>" title="Mailing Tubes | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/mailing-tubes-ths-prod-thumbnail-transparent-v1?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Mailing Tubes | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Mailing Tubes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <#--  <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>#</@ofbizUrl>" title="Custom Labels &amp; Stickers | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Custom Labels &amp; Stickers | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Custom Labels &amp; Stickers</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>  -->
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=tote+bags</@ofbizUrl>" title="Tote Bags | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/tote-bags-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Tote Bags | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Tote Bags</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod glow">
                    <a href="<@ofbizUrl>/search?w=crinkle+paper</@ofbizUrl>" title="Crinkle Paper | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/crinkle-paper-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Crinkle Paper | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h4>Crinkle Paper</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <#----------------------------------------------->
        <#----------- CUSTOM PRINTING SERVICE ----------->
        <#----------------------------------------------->
        <div id="ENV_ths_CustomPrintServices" class="bnc_section_block">
            <div bns-collapse_content="ENV_CustomPrintServices_content" bns-collapsed="false" class="bnc_section_content">
                <div class="bnc_section_header"><h2>Custom Printing Services</h2></div>
                <p class="bnc_section_desc">Create your own unique greetings with our Custom Printing Services. Stop addressing envelopes by hand! With our Recipient Addressing service, you simply upload your address list and your envelopes arrive to you pre-addressed. With our unique White Ink Printing service your envelopes will be sure to stand out in a pile of holiday mail. </p>
            </div>
            <div id="ENV_CustomPrintServices_content" class="bnc_weddingshop_product_wrap margin-top-xs flex-row flex-center">
                <div class="bnc_ths_prod ths_widthOverWrite">
                    <a href="<@ofbizUrl>/designoptions</@ofbizUrl>" title="Custom Printing | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-printing-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=400</@ofbizScene7Url>" alt="Custom Printing | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h5>Custom Printing</h5> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod ths_widthOverWrite">
                    <a href="<@ofbizUrl>/addressing</@ofbizUrl>" title="Recipient Addressing | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/recipient-addressing-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=400</@ofbizScene7Url>" alt="Recipient Addressing | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h5>Recipient Addressing</h5> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_ths_prod ths_widthOverWrite">
                    <a href="<@ofbizUrl>/white-ink</@ofbizUrl>" title="White Ink Printing | Envelopes.com" target="_blank">
                        <div class="bnc_ths_prod_content">
                            <div class="bnc_ths_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/white-ink-printing-ths-prod-thumbnail-transparent?fmt=png-alpha&amp;wid=400</@ofbizScene7Url>" alt="White Ink Printing | Envelopes.com" /></div>
                            <div class="bnc_ths_prod_name"><h5>White Ink Printing</h5> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>

    </div>
</div>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/landing/bnc_collapsible.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>