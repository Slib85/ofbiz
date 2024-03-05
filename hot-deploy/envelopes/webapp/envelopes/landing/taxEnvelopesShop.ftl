<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/landing/envelopes-tax-shop-redesign.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/landing/bnc_collapsible.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<div class="bnc_tax_shop_redesign">
    <div class="tablet-desktop-only margin-top-xs margin-bottom-xs">
    	<#include "../includes/breadcrumbs.ftl" />
    </div>
    <#-- TOP BANNER -->
	<div class="bnc_tax_shop_redesign_top_banner">
		<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/the-tax-shop-landing-page-desktop-banner-v2?wid=1360&fmt=png-alpha</@ofbizScene7Url>" alt="The Tax Shop | Envelopes.com" />
        <div class="bnc_taxshop_banner_wrap">
            <div class="bnc_taxshop_banner_content">
                <h1>The Tax Shop</h1>
                <p>Everything you need for Tax Season, all in one place!</p>
            </div>
        </div>
	</div>
    <#-- CUSTOM NAVIGATION -->
    <nav role="bnc_taxshop_nav1">
        <input type="checkbox" id="tax-shop-button">
        <label for="tax-shop-button" onclick></label>
        <ul>
            <li><a href="#ENV_W2_1099_Envelopes" title="W-2 &amp; 1099 Envelopes">W-2 &amp; 1099 Envelopes</a></li>
            <li><a href="#ENV_Regular_Window_Envelopes" title="Regular &amp; Window Envelopes">Regular &amp; Window Envelopes</a></li>
            <li><a href="#ENV_OpenEndExpansion_Envelopes" title="Open End &amp; Expansion Envelopes">Open End &amp; Expansion Envelopes</a></li>
            <li><a href="#ENV_Folders" title="Folders">Folders</a></li>
            <li><a href="#ENV_Binders_Folios" title="Binders &amp; Folios">Binders &amp; Folios</a></li>
            <li style="background-color: #113762;"><a href="https://www.folders.com/tax-folder-shop" target="_blank" title="Folders.com Tax Shop">Folders.com Tax Shop</a></li>
        </ul>
    </nav>
    <#-- 3-COLUMN NAVIGATION -->
    <div class="bnc_taxshop_nav2">
        <a class="taxshop_nav_border" href="<@ofbizUrl>/designoptions</@ofbizUrl>">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/customPrintingImg?fmt=png-alpha&amp;wid=142&amp;hei=46</@ofbizScene7Url>" alt="Custom Printing">
            <span>Custom Printing <i class="fa fa-caret-right"></i></span>
        </a>
        <a class="taxshop_nav_border" href="<@ofbizUrl>/addressing</@ofbizUrl>">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/recipientAddressing?fmt=png-alpha&amp;wid=142&amp;hei=46</@ofbizScene7Url>" alt="Recipient Addressing">
            <span>Recipient Addressing <i class="fa fa-caret-right"></i></span>
        </a>
        <a href="<@ofbizUrl>/white-ink</@ofbizUrl>">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/whiteInkImg?fmt=png-alpha&amp;wid=142&amp;hei=46</@ofbizScene7Url>" alt="White Ink">
            <span>White Ink Printing <i class="fa fa-caret-right"></i></span>
        </a>
    </div>
    <div class="bnc_tax_shop_content_container">
        <div id="BNC_Left_Column">
            <#--  <div class="bnc_tax_shop_media_wrap">
                <div class="bnc_tax_shop_media_content tabular_row">
                    <div>
                        <iframe title="Video Player" src="//fast.wistia.net/embed/iframe/u1i2cj2nlx" allowtransparency="true" frameborder="0" scrolling="no" class="wistia_embed" name="wistia_embed" allowfullscreen mozallowfullscreen webkitallowfullscreen oallowfullscreen msallowfullscreen width="295" height="258"></iframe>
                    </div>
                </div>
            </div>  -->
            <div class="bnc_tax_shop_section">
				<div class="bnc_tax_shop_section_header">Shop By Size</div>
				<div class="bnc_tax_shop_product_type flex-row">

                    <a href="<@ofbizUrl>/envelopes/3.5-X-6</@ofbizUrl>" title=" | Tax Shop | Envelopes.com">
                        <p>3 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <span>x</span> 6" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:31316x71316" title=" | Tax Shop | Envelopes.com">
                        <p>3 <sup class="fracDigit1">13</sup>/<sup class="fracDigit2">16</sup>" <span>x</span> 7<sup class="fracDigit1">13</sup>/<sup class="fracDigit2">16</sup>" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:31516x814" title=" | Tax Shop | Envelopes.com">
                        <p>3 <sup class="fracDigit1">15</sup>/<sup class="fracDigit2">16</sup>" <span>x</span> 8" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:378x712" title=" | Tax Shop | Envelopes.com">
                        <p>3 <sup class="fracDigit1">7</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 7<sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:378x878" title=" | Tax Shop | Envelopes.com">
                        <p>3 <sup class="fracDigit1">7</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 8<sup class="fracDigit1">7</sup>/<sup class="fracDigit2">8</sup>" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:358x858" title=" | Tax Shop | Envelopes.com">
                        <p>3 <sup class="fracDigit1">5</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 8<sup class="fracDigit1">5</sup>/<sup class="fracDigit2">8</sup>" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:418x912" title=" | Tax Shop | Envelopes.com">
                        <p>4 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 9<sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:412x912" title=" | Tax Shop | Envelopes.com">
                        <p>4 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <span>x</span> 9<sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:412x1038" title=" | Tax Shop | Envelopes.com">
                        <p>4 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <span>x</span> 10<sup class="fracDigit1">3</sup>/<sup class="fracDigit2">8</sup>" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:5x1112" title=" | Tax Shop | Envelopes.com">
                        <p>5 <sup class="fracDigit1">5</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 11<sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:534x878" title=" | Tax Shop | Envelopes.com">
                        <p>5 <sup class="fracDigit1">3</sup>/<sup class="fracDigit2">4</sup>" <span>x</span> 8<sup class="fracDigit1">7</sup>/<sup class="fracDigit2">8</sup>" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:534x9" title=" | Tax Shop | Envelopes.com">
                        <p>5 <sup class="fracDigit1">3</sup>/<sup class="fracDigit2">4</sup>" <span>x</span> 9" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:558x9" title=" | Tax Shop | Envelopes.com">
                        <p>5 <sup class="fracDigit1">5</sup>/<sup class="fracDigit2">8</sup>" <span>x</span> 9" <i class="fa fa-caret-right"></i></p>
                    </a>

                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:6x12" title=" | Tax Shop | Envelopes.com">
                        <p>6" <span>x</span> 12" <i class="fa fa-caret-right"></i></p>
                    </a>

				</div>
				<div class="bnc_tax_shop_product_type_cta">
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" title="Shop All Sizes | Tax Shop | Envelopes.com">
						<p>Shop All Sizes <i class="fa fa-caret-right"></i></p>
					</a>
				</div>
			</div>
        </div>
        <div id="BNC_Right_Column">
            <#-- W-2 & 1099 Envelopes -->
			<div id="ENV_W2_1099_Envelopes" class="bnc_section_block">
                <div class="bnc_section_content">
				    <div class="bnc_section_header">W-2 &amp; 1099 Envelopes</div>
				    <p class="bnc_section_desc">Our selection of W-2 and 1099 Envelopes are specifically designed with security and efficiency in mind. Available in several sizes, security tint and window options to meet your specific needs.</p>
                </div>
                <div class="bnc_tax_shop_product_wrap margin-top-xs flex-row flex-center">
                    <div class="bnc_ts_prod glow">
                        <a href="<@ofbizUrl>/search?w=W-2+Envelopes</@ofbizUrl>?cnt=50&af=st:windoww2double" title="W-2/1099 Double Window Envelopes | Envelopes.com">
                            <div class="bnc_ts_prod_content">
                                <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/w2-1099-double-window-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="W-2/1099 Double Window Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ts_prod_name">W-2/1099 <br />Double Window Envelopes <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_ts_prod glow">
                        <a href="<@ofbizUrl>/search?w=W-2+Envelopes</@ofbizUrl>" title="W-2/1099 Window Envelopes | Envelopes.com">
                            <div class="bnc_ts_prod_content">
                                <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/w2-1099-window-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="W-2/1099 Window Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ts_prod_name">W-2/1099 <br />Window Envelopes <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_ts_prod glow">
                        <a href="<@ofbizUrl>/product/~category_id=BOOKLET/~product_id=11874-TAX</@ofbizUrl>" title="6x9 Tax Return Envelopes | Envelopes.com">
                            <div class="bnc_ts_prod_content">
                                <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/6x9-tax-return-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="6x9 Tax Return Envelopes | Envelopes.com" /></div>
                                <div class="bnc_ts_prod_name">6x9 <br />Tax Return Envelopes <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_ts_prod glow">
                        <a href="<@ofbizUrl>/product/~category_id=W_2_DB_WINDOW/~product_id=7489-T4-TAX</@ofbizUrl>" title="T4 Double Window Envelope | Envelopes.com">
                            <div class="bnc_ts_prod_content">
                                <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/t4-double-window-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="T4 Double Window Envelope | Envelopes.com" /></div>
                                <div class="bnc_ts_prod_name">T4 <br />Double Window Envelope <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                </div>
			</div>
        </div>
    </div>
    <#-- Regular & Window Envelopes -->
    <div id="ENV_Regular_Window_Envelopes" class="bnc_section_block">
        <div bns-collapse_content="ENV_Regular_Window_Envelopes_content" bns-collapsed="false" class="bnc_section_content">
            <div class="bnc_section_header">Regular &amp; Window Envelopes</div>
            <p class="bnc_section_desc">We offer a larger variety of styles, colors, and sizes with various window, flap and security tint options to meet all of your business envelope needs.</p>
        </div>
        <div id="ENV_Regular_Window_Envelopes_content" class="bnc_tax_shop_product_wrap margin-top-xs flex-row flex-center">
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=REGULAR/~product_id=43687</@ofbizUrl>" title="#10 Regular Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-ten-regular-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#10 Regular Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#10 Regular Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=SINGLE_WINDOW/~product_id=43703</@ofbizUrl>" title="#10 Window Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-ten-window-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#10 Window Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#10 Window Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=SQUARE_FLAP/~product_id=4860-80W</@ofbizUrl>" title="#10 Square Flap Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-ten-square-flap-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#10 Square Flap Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#10 Square Flap Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=REGULAR/~product_id=43554</@ofbizUrl>" title="#9 Regular Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-nine-regular-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#9 Regular Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#9 Regular Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=SINGLE_WINDOW/~product_id=43604</@ofbizUrl>" title="#9 Window Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-nine-window-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#9 Window Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#9 Window Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/category</@ofbizUrl>?af=si:358x858 st:window" title="#8 5/8 Window Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-eight-five-eight-window-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#8 5/8 Window Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#8 5/8 Window Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=SINGLE_WINDOW/~product_id=43463</@ofbizUrl>" title="#7 Window Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-seven-window-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#7 Window Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#7 Window Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=REGULAR/~product_id=43455</@ofbizUrl>" title="#7 3/4 Regular Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-seven-three-fourth-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#7 3/4 Regular Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#7 3/4 Regular Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/category/~category_id=BUSINESS?af=si:358x612</@ofbizUrl>" title="#6 3/4 Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-six-three-fourth-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#6 3/4 Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#6 3/4 Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=REGULAR/~product_id=17897</@ofbizUrl>" title="#6 1/4 Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-six-one-fourth-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#6 1/4 Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#6 1/4 Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=REGULAR/~product_id=45179</@ofbizUrl>" title="#11 Regular Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-eleven-window-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#11 Regular Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#11 Regular Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <#--  <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl></@ofbizUrl>" title="#11 Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-eleven-regular-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#11 Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#11 Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>  -->
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>category</@ofbizUrl>?af=si:5x1112" title="#14 Regular Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-fourteen-window-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#14 Regular Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#14 Regular Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=REGULAR/~product_id=15R-W</@ofbizUrl>" title="#16 Regular Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-sixteen-regular-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#16 Regular Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">#16 Regular Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>

        </div>
    </div>
    <#-- Open End & Expansion Envelopes -->
    <div id="ENV_OpenEndExpansion_Envelopes" class="bnc_section_block">
        <div bns-collapse_content="ENV_OpenEndExpansion_Envelopes_content" bns-collapsed="false" class="bnc_section_content">
            <div class="bnc_section_header">Open End &amp; Expansion Envelopes</div>
            <p class="bnc_section_desc">Open End Envelopes, Exapansion Envelopes and Jumbo Envelopes are distinguished by thier unique flap style and extra room for keeping  larger bulky and important documents secure and flat while mailing. We offer these envelopes in a variety of colors, stocks and sizes.</p>
        </div>
        <div id="ENV_OpenEndExpansion_Envelopes_content" class="bnc_tax_shop_product_wrap margin-top-xs flex-row flex-center">
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/category</@ofbizUrl>?af=si:9x12 st:openend" title="9 x 12 Open End Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-twelve-open-end-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 x 12 Open End Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 x 12 <br />Open End Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=OPEN_END/~product_id=17962</@ofbizUrl>" title="9 1/2 x 12 1/2 Open End Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-half-twelve-half-open-end-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 1/2 x 12 1/2 Open End Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 1/2 x 12 1/2 <br />Open End Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=OPEN_END/~product_id=91867</@ofbizUrl>" title="13 x 19 Jumbo Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/thirteen-nineteen-jumbo-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="13 x 19 Jumbo Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">13 x 19 <br />Jumbo Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=OPEN_END/~product_id=78650</@ofbizUrl>" title="15 x 20 Jumbo Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/fifteen-twenty-jumbo-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="15 x 20 Jumbo Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">15 x 20 <br />Jumbo Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=OPEN_END/~product_id=82964</@ofbizUrl>" title="18 x 23 Jumbo Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eighteen-twenty-three-jumbo-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="18 x 23 Jumbo Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">18 x 23 <br />Jumbo Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=EXPANSION/~product_id=EXP-0288PL</@ofbizUrl>" title="9 x 12 x 1 Expansion Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-twelve-one-expansion-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 x 12 x 1 Expansion Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 x 12 x 1 <br />Expansion Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=EXPANSION/~product_id=EXP-1608PL</@ofbizUrl>" title="9 x 12 x 2 Expansion Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-twelve-two-expansion-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 x 12 x 2 Expansion Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 x 12 x 2 <br />Expansion Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=EXPANSION/~product_id=EXP-1615PL</@ofbizUrl>" title="10 x 15 x 2 Expansion Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ten-fifteen-two-expansion-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="10 x 15 x 2 Expansion Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">10 x 15 x 2 <br />Expansion Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div><div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=EXPANSION/~product_id=EXP-1639PL</@ofbizUrl>" title="10 x 13 x 1 Jumbo Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ten-thirteen-one-jumbo-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="10 x 13 x 1 Jumbo Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">10 x 13 x 1 <br />Jumbo Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=EXPANSION/~product_id=EXP-1677PL</@ofbizUrl>" title="10 x 13 x 1 1/2 Expansion Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ten-thirteen-one-half-expansion-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="10 x 13 x 1 1/2 Expansion Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">10 x 13 x 1 1/2 <br />Expansion Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <#--  <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl></@ofbizUrl>" title="Poly Button &amp; String Booklet Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-button-string-booklet-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Poly Button &amp; String Booklet Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Poly Button &amp; String Booklet Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>  -->
        </div>
    </div>
    <#-- Folders -->
    <div id="ENV_Folders" class="bnc_section_block">
        <div bns-collapse_content="ENV_Folders_content" bns-collapsed="false" class="bnc_section_content">
            <div class="bnc_section_header">Folders</div>
            <p class="bnc_section_desc">Stay organized with blank and preprinted tax folders. available in multiple styles, sizes, and colors.</p>
        </div>
        <div id="ENV_Folders_content" class="bnc_tax_shop_product_wrap margin-top-xs flex-row flex-center">
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/search?w=folders</@ofbizUrl>" title="9 x 12 Blank Presentation Folders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-twelve-blank-presentation-folders-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 x 12 Blank Presentation Folders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 x 12 <br />Blank Presentation Folders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/search</@ofbizUrl>?w=tax+folders" title="9 x 12 Presentation Tax Folders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-twelve-presentation-tax-folders-thumbnail-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 x 12 Presentation Tax Folders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 x 12 <br />Presentation Tax Folders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=FOLDERS/~product_id=PF-0913-BK</@ofbizUrl>" title="9 1/2 x 11 3/4 Poly Folders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-half-eleven-three-fourth-poly-folders-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 1/2 x 11 3/4 Poly Folders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 1/2 x 11 3/4 <br />Poly Folders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/product/~category_id=FOLDERS/~product_id=PF-0912-WH</@ofbizUrl>" title="9 x 11 3/4 Preprinted Poly Folders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-eleven-three-fourth-poly-preprinted-folders-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 x 11 3/4 Preprinted Poly Folders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 x 11 3/4 <br />Preprinted Poly Folders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/search</@ofbizUrl>?w=pocket+pages" title="9 x 12 Pocket Pages | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-twelve-pocket-pages-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 x 12 Pocket Pages | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 x 12 <br />Pocket Pages <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/search?w=legal+size+folders</@ofbizUrl>" title="9 1/2 x 14 Legal Size Folders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-half-fourteen-legal-size-folders-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9 1/2 x 14 Legal Size Folders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">9 1/2 x 14 <br />Legal Size Folders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <#--  <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl></@ofbizUrl>" title="Flat Letter Size Reinforced Jackets | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/need-image?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Flat Letter Size Reinforced Jackets | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Flat Letter Size Reinforced Jackets <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl></@ofbizUrl>" title="Letter Size File Folders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/need-image?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Letter Size File Folders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Letter Size File Folders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl></@ofbizUrl>" title="Legal Size Hanging Folders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/need-image?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Legal Size Hanging Folders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Legal Size Hanging Folders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>  -->
        </div>
    </div>
    <#-- Binders & Folios -->
    <div id="ENV_Binders_Folios" class="bnc_section_block">
        <div bns-collapse_content="ENV_BindersAndFolios_content" bns-collapsed="false" class="bnc_section_content">
            <div class="bnc_section_header">Binders &amp; Folios</div>
            <p class="bnc_section_desc">Stay organized with our collection of plastic tuffy and earth friendly ring binders, plastic folios and more. Perfect for holding important documents, proposals, and presentations all in one place.</p>
        </div>
        <div id="ENV_BindersAndFolios_content" class="bnc_tax_shop_product_wrap margin-top-xs flex-row flex-center">
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/search</@ofbizUrl>?w=earth+friendly+binders" title="Earth Friendly View Binders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/earth-friendly-view-binders-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Earth Friendly View Binders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Earth Friendly <br />View Binders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/search</@ofbizUrl>?w=tuffy+binders" title="Plastic 3 Ring Tuffy Binders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/plastic-three-ring-tuffy-binders-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Plastic 3 Ring Tuffy Binders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Plastic 3 Ring <br />Tuffy Binders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/search</@ofbizUrl>?w=economy+view+binders" title="Economy View Poly Binders | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/economy-view-poly-binders-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Economy View Poly Binders | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Economy View <br />Poly Binders <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/category</@ofbizUrl>?af=si:912x1212x412" title="Poly Envelope w/ Half Moon Closure | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-envelopes-with-half-moon-closure-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Poly Envelope w/ Half Moon Closure | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Poly Envelope <br />w/ Half Moon Closure <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <#--  <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl></@ofbizUrl>" title="Plastic Folio Tote w/ Handle | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/plastic-folio-tote-with-handle-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Plastic Folio Tote w/ Handle | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Plastic Folio Tote w/ Handle <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>  -->
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl>/search?w=button+%26+string+envelopes</@ofbizUrl>" title="Poly Button &amp; String Booklet Envelopes | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-button-string-booklet-envelopes-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Poly Button &amp; String Booklet Envelopes | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Poly Button &amp; String <br />Booklet Envelopes <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <#--  <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl></@ofbizUrl>" title="Easy Open Locking Bound Ring | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/need-image?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Easy Open Locking Bound Ring | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Easy Open Locking Bound Ring <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl></@ofbizUrl>" title="Easy Open Locking Round D Ring | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/need-image?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Easy Open Locking Round D Ring | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Easy Open Locking Round D Ring <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>
            <div class="bnc_ts_prod glow">
                <a href="<@ofbizUrl></@ofbizUrl>" title="Non Locking Round Ring | Envelopes.com">
                    <div class="bnc_ts_prod_content">
                        <div class="bnc_ts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/need-image?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Non Locking Round Ring | Envelopes.com" /></div>
                        <div class="bnc_ts_prod_name">Non Locking Round Ring <i class="fa fa-caret-right"></i></div>
                    </div>
                </a>
            </div>  -->
           
        </div>
    </div>
</div>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/landing/taxEnvelopesShop.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/landing/bnc_collapsible.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>