<link href="<@ofbizContentUrl>/html/css/landing/wedding-shop-redesign.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/landing/bnc_collapsible.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">

<div class="bnc_wedding_shop_redesign">
	
	<div class="tablet-desktop-only margin-top-xs margin-bottom-xs">
		<#include "../includes/breadcrumbs.ftl" />
	</div>

	<#-- TOP BANNER -->
	<div class="bnc_wedding_shop_redesign_top_banner">
		<img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-desktop-banner-winter-theme?wid=1360&fmt=png-alpha</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
		<img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-wedding-shop-mobile-banner-winter-theme?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="The Wedding Shop | Envelopes.com" />
        <div class="bnc_weddingshop_banner_wrap">
            <#--  <div class="bnc_weddingshop_banner_content">
                <h1>The Wedding Shop</h1>
                <p>Everything for your Wedding Invitations Suite &amp; more, in one convenient location!</p>
            </div>  -->
        </div>
	</div>

	<#-- CUSTOM NAVIGATION -->
    <nav role="bnc_weddingshop_nav1">
        <input type="checkbox" id="wedding-shop-button">
        <label for="wedding-shop-button" onclick></label>
        <ul>
            <li><a href="#ENV_Wedding_Envelopes" title="Wedding Envelopes">Wedding Envelopes</a></li>
            <li><a href="#ENV_Invitation_Stationery" title="Invitation &amp; Stationery">Invitation &amp; Stationery</a></li>
            <li><a href="#ENV_Wedding_Planning" title="Wedding Planning">Wedding Planning</a></li>
            <li><a href="#ENV_Gifting_Packaging" title="Gifting &amp; Packaging">Gifting &amp; Packaging</a></li>
            <#--  <li style="background-color: #cdbd8c;"><a href="#ENV_WeddingShop_Experience" title="The Wedding Shop Experience">The Wedding Shop Experience</a></li>  -->
        </ul>
    </nav>

	<#-- 3-COLUMN NAVIGATION -->
    <div class="bnc_weddingshop_nav2">
        <a class="weddingshop_nav_border" href="<@ofbizUrl>/designoptions</@ofbizUrl>">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/weddingShopCP?fmt=png-alpha&amp;wid=142&amp;hei=46</@ofbizScene7Url>" alt="Wedding Shop - Custom Printing">
            <span>Custom Printing <i class="fa fa-caret-right"></i></span>
        </a>
        <a class="weddingshop_nav_border" href="<@ofbizUrl>/addressing</@ofbizUrl>">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/weddingShopRA?fmt=png-alpha&amp;wid=142&amp;hei=46</@ofbizScene7Url>" alt="Wedding Shop - Recipient Addressing">
            <span>Recipient Addressing <i class="fa fa-caret-right"></i></span>
        </a>
        <a href="<@ofbizUrl>/white-ink</@ofbizUrl>">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/weddingShopWIP?fmt=png-alpha&amp;wid=142&amp;hei=46</@ofbizScene7Url>" alt="Wedding Shop - White Ink Printing">
            <span>White Ink Printing <i class="fa fa-caret-right"></i></span>
        </a>
    </div>

	<div class="bnc_wedding_shop_content_container">
		<#---- LEFT COLUMN ---->
		<div id="BNC_Left_Column">
			<#------ SHOP BY SIZE ------>
			<div class="bnc_weddingshop_section">
				<div class="bnc_weddingshop_section_hdr"><h3>Envelope &amp; Invitation Size Guide</h3></div>
				<div class="bnc_weddingshop_prod_type">
					<div class="bnc_ws_prod_category">
						<div class="ws_prod_cat_hdr">Save the Date</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Envelope Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=si:358x518%20st:squareflap%20st:contourflap</@ofbizUrl>" title=" | Wedding Shop | Envelopes.com" target="_blank">
									<p>A1 - 3 <sup class="fracDigit1">5</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 5 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">8</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Card Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=use:paperandmore%20si:312x478%20st:notecards%20prodtype:products&sort=SIZE_SMALL</@ofbizUrl>" title=" | Wedding Shop | Envelopes.com" target="_blank">
									<p>A1 - 3 <sup class="fracDigit1">5</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 5 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">8</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
					</div>
					<div class="bnc_ws_prod_category">
						<div class="ws_prod_cat_hdr">Standard Invitation</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Envelope Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=si:514x714</@ofbizUrl>" title="Envelope Size | Standard Invitation | Wedding Shop | Envelopes.com" target="_blank">
									<p>A7 - 5 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">4</sup>" <span>x</span> 7 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">4</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Card Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=use:paperandmore%20si:518x7%20st:notecards%20prodtype:products</@ofbizUrl>" title="Card Size | Standard Invitation | Wedding Shop | Envelopes.com" target="_blank">
									<p>A7 - 5 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 7" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
					</div>
					<div class="bnc_ws_prod_category">
						<div class="ws_prod_cat_hdr">RSVP</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Envelope Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=si:358x518%20st:squareflap%20st:contourflap</@ofbizUrl>" title="Envelope Size | RSVP | Wedding Shop | Envelopes.com" target="_blank">
									<p>A1 - 3 <sup class="fracDigit1">5</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 5 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">8</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Card Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=use:paperandmore%20si:312x478%20st:notecards%20prodtype:products&sort=SIZE_SMALL</@ofbizUrl>" title="Card Size | RSVP | Wedding Shop | Envelopes.com" target="_blank">
									<p>A1 - 3 <sup class="fracDigit1">5</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 5 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">8</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
					</div>
					<div class="bnc_ws_prod_category">
						<div class="ws_prod_cat_hdr">Thank You</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Envelope Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=si:438x534%20st:squareflap%20st:contourflap</@ofbizUrl>" title="Envelope Size | Thank You | Wedding Shop | Envelopes.com" target="_blank">
									<p>A2 - 4 <sup class="fracDigit1">3</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 5 <sup class="fracDigit1">3</sup>/<sup class="fracDigit2">4</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Card Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=use:paperandmore%20si:414x512%20st:notecards%20prodtype:products&sort=SIZE_SMALL</@ofbizUrl>" title="Card Size | Thank You | Wedding Shop | Envelopes.com" target="_blank">
									<p>A2 - 4 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">4</sup>" <span>x</span> 5 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
					</div>
					<div class="bnc_ws_prod_category">
						<div class="ws_prod_cat_hdr">Small Invitations</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Envelope Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=si:434x612%20st:squareflap%20st:contourflap</@ofbizUrl>" title="Envelope Size | Small Invitations | Wedding Shop | Envelopes.com" target="_blank">
									<p>A6 - 4 <sup class="fracDigit1">3</sup>/<sup class="fracDigit2">4</sup>" <span>x</span> 6 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Card Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=use:paperandmore%20si:458x614%20st:notecards%20prodtype:products</@ofbizUrl>" title="Card Size | Small Invitations | Wedding Shop | Envelopes.com" target="_blank">
									<p>A6 - 4 <sup class="fracDigit1">5</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 6 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">4</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
					</div>
					<div class="bnc_ws_prod_category">
						<div class="ws_prod_cat_hdr">Large Invitations</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Envelope Size:</div>
							<div>
								<a href="<@ofbizUrl>/category?af=si:534x834</@ofbizUrl>" title="Envelope Size | Large Invitations | Wedding Shop | Envelopes.com" target="_blank">
									<p>A9 - 5 <sup class="fracDigit1">3</sup>/<sup class="fracDigit2">4</sup>" <span>x</span> 8 <sup class="fracDigit1">3</sup>/<sup class="fracDigit2">4</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Card Size:</div>
							<div>
								<a href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=4060-SW</@ofbizUrl>" title="Card Size | Large Invitations | Wedding Shop | Envelopes.com" target="_blank">
									<p>A9 - 5 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <span>x</span> 8 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
					</div>
					<div class="bnc_ws_prod_category">
						<div class="ws_prod_cat_hdr">Place Cards</div>
						<div class="wedding-shop-sizes tabular_row">
							<div>Card Size:</div>
							<div>
								<a href="<@ofbizUrl>/search?w=mini+notecards</@ofbizUrl>" title="Place Cards | Card Size | Wedding Shop | Envelopes.com" target="_blank">
									<p>#3 Mini - 3 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <span>x</span> 2" <i class="fa fa-caret-right"></i></p>
								</a>
							</div>
						</div>
					</div>
				</div>
				<div class="bnc_weddingshop_viewall">
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" title="Shop All Sizes | Wedding Shop | Envelopes.com" target="_blank">
						<p>Shop All <i class="fa fa-caret-right"></i></p>
					</a>
				</div>
			</div>
			<#------ SHOP BY COLOR ------>
			<div class="bnc_weddingshop_section margin-top-lg">
				<div class="bnc_weddingshop_section_hdr"><h3>Shop by Color</h3></div>
				<div class="bnc_weddingshop_prod_type">

					<div class="bnc_ws_color_wrap">
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/shopByColor/~category_id=WHITE</@ofbizUrl>" title="White Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-white"></div>
								<div class="ws-color-text">White</div>
							</a>
						</div>
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/shopByColor/~category_id=NATURAL</@ofbizUrl>" title="Natural Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-natural"></div>
								<div class="ws-color-text">Natural</div>
							</a>
						</div>
					</div>

					<div class="bnc_ws_color_wrap">
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/shopByColor/~category_id=PINK</@ofbizUrl>" title="Pink Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-pink"></div>
								<div class="ws-color-text">Pink</div>
							</a>
						</div>
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/search?w=grocery+bag</@ofbizUrl>" title="Grocery Bag Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-grocery"></div>
								<div class="ws-color-text">Grocery Bag</div>
							</a>
						</div>
					</div>

					<div class="bnc_ws_color_wrap">
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/shopByColor/~category_id=GREEN</@ofbizUrl>" title="Green Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-green"></div>
								<div class="ws-color-text">Green</div>
							</a>
						</div>
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/shopByColor/~category_id=GRAY</@ofbizUrl>" title="Gray Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-gray"></div>
								<div class="ws-color-text">Gray</div>
							</a>
						</div>
					</div>

					<div class="bnc_ws_color_wrap">
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/shopByColor/~category_id=BLUE</@ofbizUrl>" title="Blue Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-blue"></div>
								<div class="ws-color-text">Blue</div>
							</a>
						</div>
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/shopByColor/~category_id=BLACK</@ofbizUrl>" title="Black Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-black"></div>
								<div class="ws-color-text">Black</div>
							</a>
						</div>
					</div>

					<div class="bnc_ws_color_wrap">
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" title="Metallic Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-metallic"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/metallic-ellipse?fmt=png-alpha&amp;wid=43&amp;ts=3</@ofbizScene7Url>" alt="" /></div>
								<div class="ws-color-text">Metallic</div>
							</a>
						</div>
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/search?w=glitter+envelopes&c=0</@ofbizUrl>" title="Sparkle Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-sparkle"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/sparkle-ellipse?fmt=png-alpha&amp;wid=43&amp;ts=3</@ofbizScene7Url>" alt="" /></div>
								<div class="ws-color-text">Sparkle</div>
							</a>
						</div>
					</div>

					<div class="bnc_ws_color_wrap">
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/shopByColor/~category_id=CLEAR_CLEARTRANSLUCENT</@ofbizUrl>" title="Clear Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-clear"></div>
								<div class="ws-color-text">Clear</div>
							</a>
						</div>
						<div class="bnc_ws_color_content">
							<a class="removeStyles" href="<@ofbizUrl>/shopByColor/~category_id=PURPLE</@ofbizUrl>" title="Purple Envelopes | Wedding Shop | Envelopes.com" target="_blank">
								<div class="ws-color-icon ws-purple"></div>
								<div class="ws-color-text">Purple</div>
							</a>
						</div>
					</div>
				</div>
				<div class="bnc_weddingshop_viewall">
					<a href="<@ofbizUrl>/shopByColor</@ofbizUrl>" title="Shop All Sizes | Wedding Shop | Envelopes.com" target="_blank">
						<p>Shop All <i class="fa fa-caret-right"></i></p>
					</a>
				</div>
				<div class="ENV_WS_SwatchBoook margin-top-sm">
					<a href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK</@ofbizUrl>" title="Swatchbook | Envelopes.com">
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/swatchbook-siderail-banner-winter-theme?fmt=png-alpha&amp;wid=320</@ofbizScene7Url>" alt="Swatchbook | Envelopes.com" />
					</a>
				</div>
			</div>
		</div>
		<#---- RIGHT COLUMN ---->
		<div id="BNC_Right_Column">

			<div id="ENV_Wedding_Envelopes" class="bnc_section_block">
                <div class="bnc_section_content">
				    <div class="bnc_section_header"><h1>Wedding Invitation Envelopes</h1></div>
				    <p class="bnc_section_desc">First impressions are everything! Easily find the right size, style and colored envelopes to deliver your wedding invitations.</p>
                </div>
                <div class="bnc_weddingshop_product_wrap margin-top-xs flex-row flex-center">
                    <div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=INVITATION</@ofbizUrl>" title="Invitation Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-invitation-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Invitation Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Invitation Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=SQUARE</@ofbizUrl>" title="Square Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-square-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Square Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Square Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=CONTOUR_FLAP</@ofbizUrl>" title="Contour Flap Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-contour-flap-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Contour Flap Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Contour Flap Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/search?w=metallics&c=0</@ofbizUrl>" title="Metallic Envelope | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-metallic-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Metallic Envelope | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Metallic Envelope</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=POINTED_FLAP</@ofbizUrl>" title="Baronial Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-baronial-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Baronial Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Baronial Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined%20col:linedenvelopes</@ofbizUrl>" title="Lined Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-lined-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Lined Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Lined Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/product/~category_id=VERTICAL_SQUARE_FLAP/~product_id=4880V-GB</@ofbizUrl>" title="Vertical A7 Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-vertical-a7-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Vertical A7 Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Vertical A7 Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=INNER_OUTER</@ofbizUrl>" title="Inner/Outer Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-inner-outer-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Inner/Outer Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Inner/Outer Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/product/~category_id=SQUARE_FLAP/~product_id=4980-201</@ofbizUrl>" title="A7 1/2 Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-a7-half-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="A7 1/2 Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>A7 1/2 Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/product/~category_id=CONTOUR_FLAP/~product_id=FL4891-WHBK</@ofbizUrl>" title="Foil Lined Contour Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-foil-lined-contour-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Foil Lined Contour Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Foil Lined Contour Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/search?w=deckle+edge</@ofbizUrl>" title="Deckle Edge Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-deckle-edge-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Deckle Edge Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Deckle Edge Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/search?w=woodgrain</@ofbizUrl>" title="Woodgrain Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-woodgrain-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Woodgrain Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Woodgrain Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/search?w=glitter+envelopes&c=0</@ofbizUrl>" title="Sparkle Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-sparkle-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Sparkle Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Sparkle Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/search?w=linens</@ofbizUrl>" title="Linen Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-linen-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Linen Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Linen Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/shopByColor/~category_id=CLEAR_CLEARTRANSLUCENT</@ofbizUrl>" title="Translucent Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-translucent-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Translucent Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>Translucent Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/search?w=rsvp+envelopes</@ofbizUrl>" title="RSVP Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-rsvp-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="RSVP Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>RSVP Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/category?af=si:21116x31116</@ofbizUrl>" title="&#x23;17 Mini Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-seventeen-mini-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="&#x23;17 Mini Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>&#x23;17 Mini Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
					<div class="bnc_ws_prod glow">
                        <a href="<@ofbizUrl>/category?af=si:214x312</@ofbizUrl>" title="&#x23;1 Coin Envelopes | Envelopes.com" target="_blank">
                            <div class="bnc_ws_prod_content">
                                <div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-one-coin-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="&#x23;1 Coin Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ws_prod_name"><h4>&#x23;1 Coin Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                </div>
			</div>

        </div>

	</div>

	<#---- FULL WIDTH SECTIONS ---->

	<div id="ENV_Invitation_Stationery" class="bnc_section_block">
		<div bns-collapse_content="ENV_Invitation_Stationery_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Invitations &amp; Stationery</h2></div>
			<p class="bnc_section_desc">Create a unique presentation for your wedding invitations using coordinating pre-printed invitations, folded cards, invitation pocket pouches & more DIY options!</p>
		</div>
		<div id="ENV_Invitation_Stationery_content" class="bnc_weddingshop_product_wrap margin-top-xs flex-row flex-center">
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/product/~category_id=INVITATIONS/~product_id=57INV-GRGLD</@ofbizUrl>" title="Pre-printed Invitations | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-pre-printed-invitations-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Pre-printed Invitations | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Pre-printed Invitations</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=embossed+folded+cards</@ofbizUrl>" title="Embossed Folded Cards | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-embossed-folded-cards-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Embossed Folded Cards | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Embossed Folded Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=WS-7015</@ofbizUrl>" title="Embossed Flat Cards | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-embossed-flat-cards-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Embossed Flat Cards | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Embossed Flat Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=invitation+pocket+pouch</@ofbizUrl>" title="Invitation Pocket Pouch | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-invitation-pocket-pouch-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Invitation Pocket Pouch | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Invitation Pocket Pouch</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=Folded+cards</@ofbizUrl>" title="Folded Cards | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-folded-cards-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Folded Cards | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Folded Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=Flat%20cards</@ofbizUrl>" title="Flat Cards | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-flat-cards-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Flat Cards | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Flat Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=petal+invitations</@ofbizUrl>" title="Petal Invitations | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-petal-invitations-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Petal Invitations | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Petal Invitations</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=pocket+invitations</@ofbizUrl>" title="Pocket Invitations | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-pocket-invitations-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Pocket Invitations | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Pocket Invitations</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=gatefold</@ofbizUrl>" title="Gatefold Invitations | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-gatefold-invitations-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gatefold Invitations | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Gatefold Invitations</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=belly+bands</@ofbizUrl>" title="Belly Bands | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-belly-bands-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Belly Bands | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Belly Bands</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/category?af=si:12x18%20si:13x19%20si:812x11%20si:11x17%20si:12x12%20si:812x14%20st:paper%20st:cardstock&sort=SIZE_SMALL</@ofbizUrl>" title="Paper &amp; Cardstock | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-paper-and-cardstock-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paper &amp; Cardstock | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Paper &amp; Cardstock</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=liners</@ofbizUrl>" title="Envelope Liners | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-envelope-liners-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Envelope Liners | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Envelope Liners</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=z-fold+invitations</@ofbizUrl>" title="Z-Fold Invitations | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-z-fold-invitations-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Z-Fold Invitations | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Z-Fold Invitations</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/category?af=use:paperandmore%20si:414x512%20st:notecards%20prodtype:products&sort=SIZE_SMALL</@ofbizUrl>" title="A2 (4 1/4 x 5 1/2) Thank You Cards | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-a2-thank-you-cards-liners-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="A2 (4 1/4 x 5 1/2) Thank You Cards | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>A2 (4 1/4 x 5 1/2) Thank You Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=gift+cards</@ofbizUrl>" title="Gift Card Holders | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-gift-cards-holders-envelopes-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Card Holders | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Gift Card Holders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=photo+booth</@ofbizUrl>" title="Photo Booth Photo Holders | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-photo-booth-photo-holders-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Photo Booth Photo Holders | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Photo Booth Photo Holders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/product/~category_id=REGULAR/~product_id=PHINV</@ofbizUrl>" title="Photo Strip Holder Envelopes | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-photo-strip-holder-envelopes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Photo Strip Holder Envelopes | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Photo Strip Holder Envelopes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=photo+holders</@ofbizUrl>" title="Photo Holders | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-photo-holders-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Photo Holders | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Photo Holders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>

	<div id="ENV_Wedding_Planning" class="bnc_section_block">
		<div bns-collapse_content="ENV_Wedding_Planning_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Wedding Planning</h2></div>
			<p class="bnc_section_desc">Keep your important dates, notes and all planning for your big day organized with our variety of notebooks, journals, notepads, planners &amp; more!</p>
		</div>
		<div id="ENV_Wedding_Planning_content" class="bnc_weddingshop_product_wrap margin-top-xs flex-row flex-center">
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=planners</@ofbizUrl>" title="Weekly Planners | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-weekly-planners-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Weekly Planners | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Weekly Planners</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=notebooks</@ofbizUrl>" title="Notebooks &amp; Journals | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-notebooks-and-journals-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Notebooks &amp; Journals | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Notebooks &amp; Journals</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=blank+notepads</@ofbizUrl>" title="Ruled &amp; Blank Notepads | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-ruled-and-blank-notepads-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Ruled &amp; Blank Notepads | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Ruled &amp; Blank Notepads</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<#--  <div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>#</@ofbizUrl>" title="Blank Sticky Notes | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-blank-sticky-notes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Blank Sticky Notes | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Blank Sticky Notes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>  -->
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=small+folders</@ofbizUrl>" title="Small &amp; Welcome Folders | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-small-and-welcome-folders-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Small &amp; Welcome Folders | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Small &amp; Welcome Folders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>

	<div id="ENV_Gifting_Packaging" class="bnc_section_block">
		<div bns-collapse_content="ENV_Gifting_Packaging_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Gifting &amp; Packaging</h2></div>
			<p class="bnc_section_desc">Perfect for gifting or packaging favors for any event. Shop a variety of boxes, gift bags, wrapping paper and more.</p>
		</div>
		<div id="ENV_Gifting_Packaging_content" class="bnc_weddingshop_product_wrap margin-top-xs flex-row flex-center">
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>" title="Gift Bags | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-gift-bags-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Bags | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Gift Bags</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=wine+bottle+bags</@ofbizUrl>" title="Wine Bottle Bags | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-wine-bottles-bags-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Wine Bottle Bags | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Wine Bottle Bags</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=wrapping+paper</@ofbizUrl>" title="Wrapping Paper | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-wrapping-paper-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Wrapping Paper | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Wrapping Paper</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=clear+boxes</@ofbizUrl>" title="Clear Boxes | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-clear-boxes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Clear Boxes | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Clear Boxes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=magnetic+boxes</@ofbizUrl>" title="Magnetic Boxes | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-magnetic-boxes-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Magnetic Boxes | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Magnetic Boxes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" title="Custom Printed Mailers | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-custom-printed-mailers-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Custom Printed Mailers | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Custom Printed Mailers</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/search?w=door+knob+bags</@ofbizUrl>" title="Clear Bags | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-clear-bags-thumbnail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Clear Bags | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Clear Bags</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod glow">
				<a href="<@ofbizUrl>/product/~category_id=PLAYING_CARDS/~product_id=PC-2001</@ofbizUrl>" title="Playing Cards | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-playing-cards-thumbnail-banner-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Playing Cards | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name"><h4>Playing Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>

	<div id="ENV_Printing_Services" class="bnc_section_block">
		<div bns-collapse_content="ENV_Printing_Services_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Printing Services</h2></div>
			<p class="bnc_section_desc">Add the finishing touches to your invitations with our time-saving printing services.</p>
		</div>
		<div id="ENV_Printing_Services_content" class="bnc_weddingshop_product_wrap margin-top-xs flex-row flex-center">
			<div class="bnc_ws_prod WS_widthOverWrite glow">
				<a href="<@ofbizUrl>/addressing</@ofbizUrl>" title="Recipient Addressing | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-recipient-addressing-thumbnail-banner-v2?fmt=png-alpha&amp;wid=608</@ofbizScene7Url>" alt="Recipient Addressing | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name WS_defaultOverWrite"><h5>Recipient Addressing</h5> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_ws_prod WS_widthOverWrite glow">
				<a href="<@ofbizUrl>/white-ink</@ofbizUrl>" title="White Ink Printing | Envelopes.com" target="_blank">
					<div class="bnc_ws_prod_content">
						<div class="bnc_ws_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wedding-shop-white-ink-printing-thumbnail-banner-v2?fmt=png-alpha&amp;wid=608</@ofbizScene7Url>" alt="White Ink Printing | Envelopes.com" /></div>
						<div class="bnc_ws_prod_name WS_defaultOverWrite"><h5>White Ink Printing</h5> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>

</div>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/landing/bnc_collapsible.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>