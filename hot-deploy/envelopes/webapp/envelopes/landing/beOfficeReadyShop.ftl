<link href="<@ofbizContentUrl>/html/css/landing/beOfficeReadyShop.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/landing/bnc_collapsible.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">

<div class="bnc_bors_shop">

	<div class="tablet-desktop-only margin-top-xs margin-bottom-xs">
		<#include "../includes/breadcrumbs.ftl" />
	</div>

    <#-- TOP BANNER -->
	<div class="bnc_bors_top_banner">
        <div class="bors_banner_wrap">
            <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-be-office-ready-mobile-banner-v3?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="Be Office Ready Shop | Envelopes.com" />
            <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-be-office-ready-desktop-banner-v3?wid=1360&fmt=png-alpha</@ofbizScene7Url>" alt="Be Office Ready Shop | Envelopes.com" />
            <#--  <div class="bors_banner_content">
                <h1>Be <span>Office</span> Ready</h1>
                <p>Preparation Is Everything.</p>
            </div>  -->
        </div>
	</div>
	<#-- CUSTOM NAVIGATION -->
    <nav role="bnc_bors_nav1">
        <input type="checkbox" id="bors-button">
        <label for="bors-button" onclick></label>
        <ul>
            <li><a href="#ENV_BORS_BestSellers" title="Best Sellers">Best Sellers</a></li>
            <li><a href="#ENV_BORS_BusinessEssentials" title="Business Essentials">Business Essentials</a></li>
            <li><a href="#ENV_BORS_CustomPrinting" title="Custom Printing">Custom Printing</a></li>
            <li><a href="<@ofbizUrl>/backToSchool</@ofbizUrl>" title="Back To School Shop" target="_blank">Back To School Shop</a></li>
            <li><a href="<@ofbizUrl>/new-arrivals</@ofbizUrl>" title="New Arrivals" target="_blank">New Arrivals</a></li>
        </ul>
    </nav>

    <#---- FULL WIDTH SECTIONS ---->
    <div id="ENV_BORS_BestSellers" class="bnc_section_block">
		<div bns-collapse_content="ENV_BORS_BestSellers_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Best Sellers</h2></div>
			<p class="bnc_section_desc">Be office ready with our most popular office items. Shop from a variety of #10 envelopes, 9x12 presentation folders, business cards and more.</p>
		</div>
		<div id="ENV_BORS_BestSellers_content" class="bnc_bors_product_wrap margin-top-xs flex-row flex-center">
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/category?af=si:418x912</@ofbizUrl>" title="#10 Envelopes | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-ten-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#10 Envelopes | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>#10 Envelopes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/category/~category_id=OPEN_END</@ofbizUrl>" title="9x12 Open End Envelopes | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/9x12-open-end-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9x12 Open End Envelopes | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>9x12 Open End Envelopes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/category/~category_id=BOOKLET</@ofbizUrl>" title="9x12 Booklet Envelopes | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/9x12-booklet-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9x12 Booklet Envelopes | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>9x12 Booklet Envelopes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/search?w=folders</@ofbizUrl>" title="9x12 Presentation Folders | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/9x12-presentation-folders-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9x12 Presentation Folders | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>9x12 Presentation Folders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/search?w=file+folders</@ofbizUrl>" title="File Folder | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/file-folders-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="File Folder | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>File Folder</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/category/~category_id=PAPERBOARD_MAILERS</@ofbizUrl>" title="Paperboard Mailers | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/paperboard-mailers-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paperboard Mailers | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Paperboard Mailers</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/search?af=si:2x312&w=Flat%20cards</@ofbizUrl>" title="Business Cards | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/business-cards-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Business Cards | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Business Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/search?w=poly+folders</@ofbizUrl>" title="Poly Folders | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-folders-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Poly Folders | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Poly Folders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>
    <div id="ENV_BORS_BusinessEssentials" class="bnc_section_block">
		<div bns-collapse_content="ENV_BORS_BusinessEssentials_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Business Essentials</h2></div>
			<p class="bnc_section_desc">All business essentials you need to keep organized and succeed. Shop from a large variety of planners, notebooks, notepads and more.</p>
		</div>
		<div id="ENV_BORS_BusinessEssentials_content" class="bnc_bors_product_wrap margin-top-xs flex-row flex-center">
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/category/~category_id=BUBBLE_LINED</@ofbizUrl>" title="Bubble Mailers | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-and-bubble-mailers-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Bubble Mailers | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Bubble Mailers</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/category/~category_id=EXPANSION</@ofbizUrl>" title="Expansion Envelopes | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/expansion-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Expansion Envelopes | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Expansion Envelopes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/category/~category_id=TYVEK</@ofbizUrl>" title="Tyvek Envelopes | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/tyvek-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Tyvek Envelopes | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Tyvek Envelopes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/search?w=planners</@ofbizUrl>" title="Planners | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/weekly-planners-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Planners | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Planners</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/search?w=notepads</@ofbizUrl>" title="Notepads &amp; Sticky Notes | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/notepads-and-sticky-notes-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Notepads &amp; Sticky Notes | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Notepads &amp; Sticky Notes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/search?w=binders</@ofbizUrl>" title="Binders | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/binders-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Binders | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Binders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/category?af=use:paperandmore%20si:518x7%20st:notecards%20prodtype:products</@ofbizUrl>" title="A7 Folded Cards | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/a7-folded-cards-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="A7 Folded Cards | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>A7 Folded Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/search?af=si:2x312&w=Flat%20cards</@ofbizUrl>" title="#3 Mini Flat Cards | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/number-three-mini-flat-cards-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="#3 Mini Flat Cards | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>#3 Mini Flat Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/category/~category_id=PAPER</@ofbizUrl>" title="Paper &amp; Cardstock | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/paper-and-cardstock-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paper &amp; Cardstock | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Paper &amp; Cardstock</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
        </div>
    </div>
    <div id="ENV_BORS_CustomPrinting" class="bnc_section_block">
		<div bns-collapse_content="ENV_BORS_CustomPrinting_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Custom Printing Service</h2></div>
			<p class="bnc_section_desc">Add your company's logo, return address & much more to nearly any product we have with custom printing.</p>
		</div>
		<div id="ENV_BORS_CustomPrinting_content" class="bnc_bors_product_wrap margin-top-xs flex-row flex-center">
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/designoptions</@ofbizUrl>" title="Custom Printing | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-printing-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Custom Printing | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Custom Printing</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/addressing</@ofbizUrl>" title="Recipient Addressing | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/recipient-address-printing-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Recipient Addressing | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>Recipient Addressing</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bors_prod glow">
				<a href="<@ofbizUrl>/white-ink</@ofbizUrl>" title="White Ink Printing | Envelopes.com" target="_blank">
					<div class="bnc_bors_prod_content">
						<div class="bnc_bors_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/white-ink-printing-envelopes-be-office-ready-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="White Ink Printing | Envelopes.com" /></div>
						<div class="bnc_bors_prod_name"><h4>White Ink Printing</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
        </div>
    </div>

</div>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/landing/bnc_collapsible.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>