<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/landing/envelopes-package-mailers-shop.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/landing/bnc_collapsible.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<div class="bnc_package_mailers_shop">
    <div class="tablet-desktop-only margin-top-xs margin-bottom-xs">
    	<#include "../includes/breadcrumbs.ftl" />
    </div>
    <#-- TOP BANNER -->
	<div class="bnc_package_mailers_top_banner">
		<img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/packaging-and-mailers-shop-desktop-banner-v1?fmt=png-alpha&wid=1360</@ofbizScene7Url>" alt="The Tax Shop | Envelopes.com" />
        <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/packaging-and-mailers-shop-mobile-banner-v1?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="The Tax Shop | Envelopes.com" />
        <div class="bnc_package_mailers_banner_wrap">
            <div class="bnc_package_mailers_banner_content">
                <h1><span>Packaging</span> <span>&amp; Mailers</span></h1>
                <p>Your one-stop-shop for all packaging & shipping solutions!</p>
                <div class="bnc_env_icon-wrap">
                    <#--  <img src="<@ofbizContentUrl>/html/img/icon/small-envelope.svg</@ofbizContentUrl>" alt="Envelope Icon | Envelopes.com" />  -->
                    <svg id="bnc_env_icon" data-name="Layer 1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 11"><path d="M14.5,0H1.5A1.44,1.44,0,0,0,0,1.38V9.63A1.44,1.44,0,0,0,1.5,11h13A1.44,1.44,0,0,0,16,9.62V1.38A1.44,1.44,0,0,0,14.5,0Zm0,.92a.41.41,0,0,1,.19,0L8,6.27,1.31,1a.58.58,0,0,1,.19,0Zm0,9.16H1.5A.48.48,0,0,1,1,9.62V1.92l6.67,5.3A.51.51,0,0,0,8,7.33a.57.57,0,0,0,.33-.11L15,1.92v7.7A.48.48,0,0,1,14.5,10.08Z" fill="#a7916f"/></svg>
                </div>
            </div>
        </div>
	</div>
    <#-- CUSTOM NAVIGATION -->
    <nav role="bnc_package_mailers_shop_nav1">
        <input type="checkbox" id="tax-shop-button">
        <label for="tax-shop-button" onclick></label>
        <ul>
            <li><a href="#ENV_Mailers" title="Mailers">Mailers</a></li>
            <li><a href="#ENV_Boxes" title="Boxes">Boxes</a></li>
            <li><a href="#ENV_Bags" title="Bags">Bags</a></li>
            <li><a href="#ENV_Pouches" title="Pouches">Pouches</a></li>
            <li><a href="#ENV_Packaging_Accessories" title="Packaging Accessories">Packaging Accessories</a></li>
            <li><a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" target="_blank" title="Custom Printed Mailers">Custom Printed Mailers</a></li>
        </ul>
    </nav>
    <#-- MAIN CONTENT -->
    <div class="bnc_package_mailers_content_container">

		<#---- LEFT COLUMN ---->
		<div id="BNC_Left_Column">

            <#------ SHOP BY SIZE ------>
			<div class="bnc_package_mailers_section">
				<div class="bnc_package_mailers_section_hdr"><h3>Size Guide</h3></div>

				<div class="bnc_package_mailers_prod_type">
                    <div class="pm_prod_cat_hdr">Mailers</div>
                    <div class="bnc_package_mailers_prod_category flex-row">
						<div>
							<a href="<@ofbizUrl>/search</@ofbizUrl>?w=mailers&cnt=50&af=si:6x8 " title="6&quot; &times; 8&quot; Mailers | Packaging &amp; Mailers | Envelopes.com">
								<p>6" <span>x</span> 8" <i class="fa fa-caret-right"></i></p>
							</a>
						</div>
						<div>
							<a href="<@ofbizUrl>/search</@ofbizUrl>?w=mailers&cnt=50&af=si:6x9" title="6&quot; &times; 9&quot; Mailers | Packaging &amp; Mailers | Envelopes.com">
								<p>6" <span>x</span> 9" <i class="fa fa-caret-right"></i></p>
							</a>
						</div>
                        <div>
							<a href="<@ofbizUrl>/product/~category_id=PAPERBOARD_MAILERS/~product_id=BX-RMU97W</@ofbizUrl>" title="7&quot; &times; 9&quot; Mailers | Packaging &amp; Mailers | Envelopes.com">
								<p>7" <span>x</span> 9" <i class="fa fa-caret-right"></i></p>
							</a>
						</div>
						<div>
							<a href="<@ofbizUrl>/search</@ofbizUrl>?w=mailers&cnt=50&af=si:9x1112" title="9&quot; &times; 11 1/2&quot; Mailers | Packaging &amp; Mailers | Envelopes.com">
								<p>9" <span>x</span> 11 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
							</a>
						</div>
                        <div>
							<a href="<@ofbizUrl>/search</@ofbizUrl>?w=mailers&cnt=50&af=si:912x1212" title="9 1/2&quot; &times; 12 1/2&quot; Mailers | Packaging &amp; Mailers | Envelopes.com">
								<p>9 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <span>x</span> 12 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
							</a>
						</div>
						<div>
							<a href="<@ofbizUrl>/search</@ofbizUrl>?w=mailers&cnt=50&af=si:10x13" title="10&quot; &times; 13&quot; Mailers | Packaging &amp; Mailers | Envelopes.com">
								<p>10" <span>x</span> 13" <i class="fa fa-caret-right"></i></p>
							</a>
						</div>
                        <div>
							<a href="<@ofbizUrl>/search</@ofbizUrl>?w=mailers&cnt=50&af=si:6x912" title="6&quot; &times; 9 1/2&quot; Mailers | Packaging &amp; Mailers | Envelopes.com">
								<p>6" <span>x</span> 9 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
							</a>
						</div>
						<div>
							<a href="<@ofbizUrl>/search?w=mailers&cnt=50&af=si:11x1512</@ofbizUrl>" title="11&quot; &times; 15 1/2&quot; Mailers | Packaging &amp; Mailers | Envelopes.com">
								<p>11" <span>x</span> 15 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">2</sup>" <i class="fa fa-caret-right"></i></p>
							</a>
						</div>
					</div>
                    <div class="pm_prod_cat_hdr">Boxes</div>
                    <div class="bnc_package_mailers_prod_category flex-row">
						<div>
							<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:4x4x4" title="4&quot; &times; 4&quot; &times; 4&quot; Boxes | Packaging &amp; Mailers | Envelopes.com">
								<p>4" <span>x</span> 4" <span>x</span> 4"<i class="fa fa-caret-right"></i></p>
							</a>
						</div>
						<div>
							<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>?af=si:6x6x6" title="6&quot; &times; 6&quot; &times; 6&quot; Boxes | Packaging &amp; Mailers | Envelopes.com">
								<p>6" <span>x</span> 6" <span>x</span> 6"<i class="fa fa-caret-right"></i></p>
							</a>
						</div>
                        <div>
							<a href="<@ofbizUrl>/search</@ofbizUrl>?w=mailers&cnt=50&af=si:7x4x2" title="7&quot; &times; 4&quot; &times; 2&quot; Boxes | Packaging &amp; Mailers | Envelopes.com">
								<p>7" <span>x</span> 4" <span>x</span> 2"<i class="fa fa-caret-right"></i></p>
							</a>
						</div>
						<div>
							<a href="<@ofbizUrl>/product/~category_id=BOX/~product_id=MGB-MMB921</@ofbizUrl>" title="9&quot; &times; 6&quot; &times; 1&quot; Boxes | Packaging &amp; Mailers | Envelopes.com">
								<p>9" <span>x</span> 6 <sup class="fracDigit1">1</sup>/<sup class="fracDigit2">4</sup>" <span>x</span> 2 <sup class="fracDigit1">3</sup>/<sup class="fracDigit2">4</sup>" <i class="fa fa-caret-right"></i></p>
							</a>
						</div>
                        <div>
							<a href="<@ofbizUrl>/product/~category_id=BOX/~product_id=BP-101010W</@ofbizUrl>" title="10&quot; &times; 10&quot; &times; 10&quot; Boxes | Packaging &amp; Mailers | Envelopes.com">
								<p>10" <span>x</span> 10" <span>x</span> 10"<i class="fa fa-caret-right"></i></p>
							</a>
						</div>
						<div>
							<a href="<@ofbizUrl>/product/~category_id=BOX/~product_id=JGB-JMB924</@ofbizUrl>" title="12&quot; &times; 5&quot; &times; 10&quot; Boxes | Packaging &amp; Mailers | Envelopes.com">
								<p>12" <span>x</span> 5" <span>x</span> 10"<i class="fa fa-caret-right"></i></p>
							</a>
						</div>
                        <div>
							<a href="<@ofbizUrl>/product/~category_id=BOX/~product_id=MB-MB249</@ofbizUrl>" title="12&quot; &times; 9&quot; &times; 6&quot; Boxes | Packaging &amp; Mailers | Envelopes.com">
								<p>12" <span>x</span> 9" <span>x</span> 6"<i class="fa fa-caret-right"></i></p>
							</a>
						</div>
						<div>
							<a href="<@ofbizUrl>/product/~category_id=BOX/~product_id=BP-141414W</@ofbizUrl>" title="14&quot; &times; 14&quot; &times; 14&quot; Boxes | Packaging &amp; Mailers | Envelopes.com">
								<p>14" <span>x</span> 14" <span>x</span> 14"<i class="fa fa-caret-right"></i></p>
							</a>
						</div>
					</div>
                </div>

                <div class="bnc_package_mailers_viewall margin-top-xs">
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" title="Shop All Sizes | Packaging &amp; Mailers | Envelopes.com">
						<p>Shop All <i class="fa fa-caret-right"></i></p>
					</a>
				</div>

            </div>
        
        </div>

        <#---- RIGHT COLUMN ---->
		<div id="BNC_Right_Column">

			<div id="ENV_Mailers" class="bnc_section_block">
                <div class="bnc_section_content">
				    <div class="bnc_section_header"><h2>Mailers</h2></div>
				    <p class="bnc_section_desc">Safely ship any item with our variety of durable mailers. Shop padded bubble mailers, tyvek mailing envelopes, paperboard or poly mailers &amp; more!</p>
                </div>
                <div class="bnc_package_mailers_product_wrap margin-top-xs flex-row flex-left">
                    <div class="bnc_pm_prod glow">
                        <a href="<@ofbizUrl>/search?w=custom+bubble+mailers</@ofbizUrl>" title="Custom Mailers | Envelopes.com">
                            <div class="bnc_pm_prod_content">
                                <div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-mailers-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Custom Mailers | Envelopes.com" /></div>
                                <div class="bnc_pm_prod_name"><h4>Custom Mailers</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_pm_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=BUBBLE_LINED</@ofbizUrl>" title="Bubble Mailers | Envelopes.com">
                            <div class="bnc_pm_prod_content">
                                <div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/bubble-mailers-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Bubble Mailers | Envelopes.com" /></div>
                                <div class="bnc_pm_prod_name"><h4>Bubble Mailers</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_pm_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=PLASTIC_MAILERS</@ofbizUrl>" title="Poly Mailers | Envelopes.com">
                            <div class="bnc_pm_prod_content">
                                <div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-mailers-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Poly Mailers | Envelopes.com" /></div>
                                <div class="bnc_pm_prod_name"><h4>Poly Mailers</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_pm_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=PAPERBOARD_MAILERS</@ofbizUrl>" title="Paper &amp; Paperboard Mailers | Envelopes.com">
                            <div class="bnc_pm_prod_content">
                                <div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/paper-paperboard-mailers-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paper &amp; Paperboard Mailers | Envelopes.com" /></div>
                                <div class="bnc_pm_prod_name"><h4>Paper &amp; Paperboard Mailers</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_pm_prod glow">
                        <a href="<@ofbizUrl>/search?w=tyvek</@ofbizUrl>" title="Tyvek Mailers | Envelopes.com">
                            <div class="bnc_pm_prod_content">
                                <div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/tyvek-mailers-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Tyvek Mailers | Envelopes.com" /></div>
                                <div class="bnc_pm_prod_name"><h4>Tyvek Mailers</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_pm_prod glow">
                        <a href="<@ofbizUrl>/category/~category_id=OPEN_END</@ofbizUrl>" title="Open End Envelopes | Envelopes.com">
                            <div class="bnc_pm_prod_content">
                                <div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/open-end-envelopes-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Open End Envelopes | Envelopes.com" /></div>
                                <div class="bnc_pm_prod_name"><h4>Open End Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                    <div class="bnc_pm_prod glow">
                        <a href="<@ofbizUrl>/search?w=mailing+tubes</@ofbizUrl>" title="Tubes | Envelopes.com">
                            <div class="bnc_pm_prod_content">
                                <div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/tubes-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Tubes | Envelopes.com" /></div>
                                <div class="bnc_pm_prod_name"><h4>Mailing Tubes</h4> <i class="fa fa-caret-right"></i></div>
                            </div>
                        </a>
                    </div>
                </div>
			</div>

        </div>

    </div>

    <#---- FULL WIDTH SECTIONS ---->

	<div id="ENV_Boxes" class="bnc_section_block">
		<div bns-collapse_content="ENV_Boxes_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Boxes</h2></div>
			<p class="bnc_section_desc">Whether small or large, fragile or bulky, we have boxes to fit every mailing or gifting need! Shop pre-printed gift boxes, corrugated mailing boxes, clear boxes and more.</p>
		</div>
		<div id="ENV_Boxes_content" class="bnc_package_mailers_product_wrap margin-top-xs flex-row flex-left">
			<div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/search?w=gift+boxes&c=0</@ofbizUrl>" title="Gift Boxes | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-boxes-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Boxes | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Gift Boxes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/search?w=corrugated+boxes</@ofbizUrl>" title="Mailing Boxes | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/mailing-boxes-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Mailing Boxes | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Mailing Boxes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/search?w=clear+boxes</@ofbizUrl>" title="Product Boxes | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/product-boxes-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Product Boxes | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Product Boxes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>

    <div id="ENV_Bags" class="bnc_section_block">
		<div bns-collapse_content="ENV_Bags_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Bags</h2></div>
			<p class="bnc_section_desc">Our selection of bags are perfect for any retail packaging or gifting need. Shop a variety of gift and bottle bags, specialty retail bags and more.</p>
		</div>
		<div id="ENV_Bags_content" class="bnc_package_mailers_product_wrap margin-top-xs flex-row flex-left">
			<div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/search?w=gift+bags</@ofbizUrl>" title="Gift Bags | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-bags-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Bags | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Gift Bags</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/search</@ofbizUrl>?w=door+knob+bags" title="Specialty Bags | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/specialty-bags-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Specialty Bags | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Specialty Bags</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/search?w=wine+bottle+bags</@ofbizUrl>" title="Bottle Bags | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/bottle-bags-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Bottle Bags | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Bottle Bags</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>

    <div id="ENV_Pouches" class="bnc_section_block">
		<div bns-collapse_content="ENV_Pouches_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Pouches</h2></div>
			<p class="bnc_section_desc">Perfect for displaying your product for retail or distribution, our flat and stand-up pouches come in a variety of sizes and colors for any product need.</p>
		</div>
		<div id="ENV_Pouches_content" class="bnc_package_mailers_product_wrap margin-top-xs flex-row flex-left">
			<div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/category/~category_id=HANGING_BAGS</@ofbizUrl>" title="Flat Pouch | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/flat-pouch-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Flat Pouch | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Flat Pouch</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/category/~category_id=HANGING_BAGS</@ofbizUrl>" title="Stand Up Pouch | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/stand-up-pouch-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Stand Up Pouch | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Stand Up Pouch</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>

    <div id="ENV_Packaging_Accessories" class="bnc_section_block">
		<div bns-collapse_content="ENV_Packaging_Accessories_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Packaging Accessories</h2></div>
			<p class="bnc_section_desc">Add finishing touches to your gifts or packages with our variety of packing acessories. Shop wrapping paper, gift tags, labels and more!</p>
		</div>
		<div id="ENV_Packaging_Accessories_content" class="bnc_package_mailers_product_wrap margin-top-xs flex-row flex-left">
			<div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/search?w=wrapping+paper</@ofbizUrl>" title="Wrapping Paper | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/wrapping-paper-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Wrapping Paper | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Wrapping Paper</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_pm_prod glow">
				<a href="<@ofbizUrl>/search?w=gift+tags</@ofbizUrl>" title="Hang Tags | Envelopes.com">
					<div class="bnc_pm_prod_content">
						<div class="bnc_pm_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hang-tags-thumbnails-packaging-and-mailers?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Hang Tags | Envelopes.com" /></div>
						<div class="bnc_pm_prod_name"><h4>Hang Tags</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>

</div>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/landing/bnc_collapsible.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>