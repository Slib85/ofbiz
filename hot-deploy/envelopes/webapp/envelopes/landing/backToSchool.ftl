<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/landing/backToSchool-redesign.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/landing/bnc_collapsible.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<div class="bnc_bts_shop">
	<div class="tablet-desktop-only margin-top-xs margin-bottom-xs">
		<#include "../includes/breadcrumbs.ftl" />
	</div>
    <#-- TOP BANNER -->
	<div class="bnc_bts_top_banner">
        <div class="bts_banner_wrap">
            <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/back-to-school-shop-chalkboard-theme-homepage-mobile-banner-v2?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="Back to School Shop | Envelopes.com" />
            <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/back-to-school-shop-chalkboard-theme-homepage-desktop-banner-v2?wid=1360&fmt=png-alpha</@ofbizScene7Url>" alt="Back to School Shop | Envelopes.com" />
            <div class="bts_banner_content">
				<#if (currentTimestamp?default(now?datetime) gte "2021-08-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-09-06 23:59:59.000"?datetime)>
                <div class="bts-top-line">Up to 30% Off!</div>
				<#elseif (currentTimestamp?default(now?datetime) gte "2021-09-07 00:00:00.000"?datetime)>
				<div class="bts-top-line">Welcome Back!</div>
				</#if>
                <div class="bts-middle-line">
                    <h1><span>the</span> Back to School <span>shop</span></h1>
                </div>
                <p>Let Envelopes.com help you start the school year off right! It's time to stock up on envelope and stationery essentials you need to stay organized and succeed. Browse our large selection of envelopes, paper, notebooks, planners &amp; more! </p>
            </div>
        </div>
	</div>
	<#-- CUSTOM NAVIGATION -->
    <nav role="bnc_bts_nav1">
        <input type="checkbox" id="bts-button">
        <label for="bts-button" onclick></label>
        <ul>
            <li><a href="#ENV_bts_BestSellers" title="Best Sellers">Best Sellers</a></li>
            <li><a href="#ENV_bts_InTheClassroom" title="In the Classroom">In the Classroom</a></li>
            <li><a href="#ENV_bts_TeachersCorner" title="Teacher's Corner">Teacher's Corner</a></li>
            <li><a href="<@ofbizUrl>/designoptions</@ofbizUrl>" title="Custom Printing" target="_blank">Custom Printing</a></li>
            <#--  <li style="background-color:#cdd5d8;"><a href="#" title="Rachel Hale Shop">Rachel Hale Shop</a></li>  -->
            <li style="background-color:#9cc2e4;"><a href="https://www.folders.com/folders/control/backToSchool" title="Folders.com Back to School" style="color: #fff;" target="_blank">Folders.com Back to School</a></li>
        </ul>
    </nav>
    <#---- FULL WIDTH SECTIONS ---->
    <div id="ENV_bts_BestSellers" class="bnc_section_block">
		<div bns-collapse_content="ENV_bts_BestSellers_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Best Sellers</h2></div>
			<p class="bnc_section_desc">Start the year off right with our most popular back to school items! Shop a variety of envelopes, 9x12 presentation folders, certificates and more. </p>
		</div>
		<div id="ENV_bts_BestSellers_content" class="bnc_bts_product_wrap margin-top-xs flex-row flex-center">
			<div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/category?af=si:514x714</@ofbizUrl>" title="A7 Invitation Envelopes | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/a7-invitation-envelopes-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="A7 Invitation Envelopes | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>A7 Invitation Envelopes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
			<div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=folders</@ofbizUrl>" title="9x12 Presentation Folders | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/nine-twelve-presentation-folders-back-to-school-product-thumbnail-v3?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="9x12 Presentation Folders | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>9x12 Presentation Folders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=certificates</@ofbizUrl>" title="Certificates | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/certificates-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Certificates | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Certificates</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=certificate+holders</@ofbizUrl>" title="Certificate Holders | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/certificate-holders-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Certificate Holders | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Certificate Holders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
		</div>
	</div>
    <div id="ENV_bts_InTheClassroom" class="bnc_section_block">
		<div bns-collapse_content="ENV_bts_InTheClassroom_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>In the Classroom</h2></div>
			<p class="bnc_section_desc">All school essentials you need to succeed in the classroom. Shop our assortment of paper and cardstock, pencils, notebooks, planners and more.</p>
		</div>
		<div id="ENV_bts_InTheClassroom_content" class="bnc_bts_product_wrap margin-top-xs flex-row flex-center">
			<div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/category?af=st:paper st:cardstock</@ofbizUrl>" title="Paper &amp; Cardstock | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/paper-cardstock-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paper &amp; Cardstock | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Paper &amp; Cardstock</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=variety+packs</@ofbizUrl>" title="Paper &amp; Cardstock Variety Packs | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/paper-cardstock-variety-packs-back-to-school-product-thumbnail-v2?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paper &amp; Cardstock Variety Packs | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Paper &amp; Cardstock Variety Packs</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=notebooks</@ofbizUrl>" title="Notebooks / Journals | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/notebooks-journals-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Notebooks / Journals | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Notebooks / Journals</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=pencils</@ofbizUrl>" title="Pencils | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/pencils-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Pencils | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Pencils</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=binders</@ofbizUrl>" title="Binders | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/binders-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Binders | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Binders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=notepads</@ofbizUrl>" title="Notepads | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/notepads-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Notepads | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Notepads</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=sticky+notes</@ofbizUrl>" title="Sticky Notes | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/sticky-notes-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Sticky Notes | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Sticky Notes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=Flat%20cards</@ofbizUrl>" title="Flat Cards | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/flat-cards-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Flat Cards | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Flat Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <#--  <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>#</@ofbizUrl>" title="Interior Printed Envelopes | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/interior-printed-envelopes-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Interior Printed Envelopes | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Interior Printed Envelopes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>  -->
            <#--  <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>#</@ofbizUrl>" title="Interior Printed Folders | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/interior-printed-folders-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Interior Printed Folders | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Interior Printed Folders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>  -->
		</div>
	</div>
    <div id="ENV_bts_TeachersCorner" class="bnc_section_block">
		<div bns-collapse_content="ENV_bts_TeachersCorner_content" bns-collapsed="false" class="bnc_section_content">
			<div class="bnc_section_header"><h2>Teacher's Corner</h2></div>
			<p class="bnc_section_desc">Your one-stop-shop for all of your classroom needs. Stay organized and professional with teacher essentials, like planners, padfolios, folders, DIY flashcards and more.</p>
		</div>
		<div id="ENV_bts_TeachersCorner_content" class="bnc_bts_product_wrap margin-top-xs flex-row flex-center">
            <div class="bnc_bts_teachers_banner_wrap">
                <div class="bnc_bts_teachers_banner_content">
                    <div class="bnc_bts_left_column">
                        <h3>Teachers are the Best!</h3>
                        <p>And since you're the best teacher for our students, you deserve the best school supplies for your classroom! Let <a href="/" title="Envelopes.com">Envelopes.com</a> make your school year easier and more organized with essentials that will help you and your students succeed.</p>
                        <div class="quote-text">Thank You for All You Do!</div>
                    </div>
                    <div class="bnc_bts_right_column">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/teachers-are-the-best-marketing-banner?fmt=png-alpha&amp;wid=542</@ofbizScene7Url>" alt="" border="0" />
                    </div>
                </div>
            </div>
			<div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=planners</@ofbizUrl>" title="Planners | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/planners-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Planners | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Planners</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=file+folders</@ofbizUrl>" title="File Folders / Dividers | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/file-folders-dividers-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="File Folders / Dividers | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>File Folders / Dividers</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=poly+folders</@ofbizUrl>" title="Poly Folders | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-folders-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Poly Folders | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Poly Folders</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <#--  <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>#</@ofbizUrl>" title="Bags | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/bags-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Bags | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Bags</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>  -->
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=padfolios</@ofbizUrl>" title="Padfolios | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/portfolios-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Padfolios | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Padfolios</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=hole+punchers</@ofbizUrl>" title="Hole Punchers | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hole-punchers-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Hole Punchers | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Hole Punchers</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=poly+snap+envelopes</@ofbizUrl>" title="Poly Envelopes | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-envelopes-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Poly Envelopes | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Poly Envelopes</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=Flat%20cards</@ofbizUrl>" title="DIY Flash Cards | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/diy-flash-cards-back-to-school-product-thumbnail-v3?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="DIY Flash Cards | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>DIY Flash Cards</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>
            <div class="bnc_bts_prod glow">
				<a href="<@ofbizUrl>/search?w=embossed+foil+seals</@ofbizUrl>" title="Embossed Foil Seals | Envelopes.com" target="_blank">
					<div class="bnc_bts_prod_content">
						<div class="bnc_bts_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/embossed-foil-seals-back-to-school-product-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Embossed Foil Seals | Envelopes.com" /></div>
						<div class="bnc_bts_prod_name"><h4>Embossed Foil Seals</h4> <i class="fa fa-caret-right"></i></div>
					</div>
				</a>
			</div>

		</div>
	</div>
    <div class="ENV_bts_RachaelHale margin-top-md margin-bottom-md">
        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/rachael-hale-marketing-desktop-banner-v3?fmt=png-alpha&amp;wid=1360</@ofbizScene7Url>" alt="" />
    </div>
</div> 
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/landing/bnc_collapsible.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>