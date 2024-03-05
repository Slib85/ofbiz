<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/landing/rachaelHaleShop.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/landing/bnc_collapsible.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<div class="bnc_rhs_shop">
	<div class="tablet-desktop-only margin-top-xs margin-bottom-xs">
		<#include "../includes/breadcrumbs.ftl" />
	</div>
    <#-- TOP BANNER -->
	<div class="bnc_rhs_top_banner">
        <div class="rhs_banner_wrap">
            <img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/rachael-hale-shop-mobile-banner-v1?wid=768&fmt=png-alpha</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
            <img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/rachael-hale-shop-desktop-banner-v1?wid=1360&fmt=png-alpha</@ofbizScene7Url>" alt="Rachael Hale Shop | Envelopes.com" />
            <#--  <div class="rhs_banner_content">
				<div class="rhs-top-line"></div>
                <div class="rhs-middle-line"><h1></h1></div>
                <p></p>
            </div>  -->
        </div>
	</div>
	<#-- CUSTOM NAVIGATION -->
    <nav role="bnc_rhs_nav1">
        <input type="checkbox" id="rhs-button">
        <label for="rhs-button" onclick></label>
        <ul>
            <li><a href="#ENV_rhs_PaperInvitations" title="Paper &amp; Invitations">Paper &amp; Invitations</a></li>
            <li><a href="#ENV_rhs_Stationery" title="Stationery">Stationery</a></li>
            <li><a href="#ENV_rhs_PackagingMailers" title="Packaging &amp; Mailers">Packaging &amp; Mailers</a></li>
            <li style="background-color:#86b2dc;"><a href="<@ofbizUrl>/new-arrivals</@ofbizUrl>" title="New Arrivals" style="color: #fff;" target="_blank">New Arrivals</a></li>
        </ul>
    </nav>
    <#--LEFT RAIL COLUMN-->
    <div class="ENV_LeftColumn_Wrap">
        <div class="Inner_LeftRail_Content">
            <div class="siderail-banner1">
                <a href="<@ofbizUrl>/aboutRachaelHaleBrand</@ofbizUrl>" title="Get to know Rachael Hale | Envelopes.com" target="_blank">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/get-to-know-rachael-hale-siderail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Get to know Rachael Hale | Envelopes.com" border="0" />
                </a>
            </div>
            <div class="siderail-banner2 margin-top-md">
                <a href="<@ofbizUrl>/designoptions</@ofbizUrl>" title="Custom Printing | Envelopes.com" target="_blank">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/rachael-hale-custom-printing-siderail-banner?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Custom Printing | Envelopes.com" border="0" />
                </a>
            </div>
        </div>
    </div>

    <div class="ENV_RightColumn_Wrap">

        <div id="ENV_rhs_PaperInvitations" class="bnc_section_block">
            <div class="bnc_section_content">
                <div class="bnc_section_header"><h2>Paper &amp; Invitations</h2></div>
                <p class="bnc_section_desc">Invitation envelopes are ideal not only for invitations to your next party or event, but also for greeting cards, save the dates, thank you cards, photo cards, special announcements, or even a simple note.</p>
            </div>
            <div id="ENV_rhs_PaperInvitations_content" class="bnc_rhs_product_wrap margin-top-xs flex-row flex-left">
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/product/~category_id=RH_PAPER/~product_id=RH-81211-P-CAT</@ofbizUrl>" title="Paper | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/paper-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paper | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Paper</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/search?w=rachael%20hale&af=st:rachaelhalecardstock</@ofbizUrl>" title="Cardstock | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/cardstock-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Cardstock | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Cardstock</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/search?w=rachael%20hale&af=st:rachaelhalefoldedcardsets</@ofbizUrl>" title="Folded Cards | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folded-cards-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Folded Cards | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Folded Cards</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/search?w=rachael%20hale&af=st:rachaelhalesquareflapenvelopes</@ofbizUrl>" title="Invitation Envelopes | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/invitation-envelopes-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Invitation Envelopes | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Invitation Envelopes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>

        <div id="ENV_rhs_Stationery" class="bnc_section_block">
            <div bns-collapse_content="ENV_rhs_Stationery_content" bns-collapsed="false" class="bnc_section_content">
                <div class="bnc_section_header"><h2>Stationery</h2></div>
                <p class="bnc_section_desc">Whether for personal use or for the office, these notebooks, journals, planners and notepads will keep you organized and looking professional. Shop different styles and colors!</p>
            </div>
            <div id="ENV_rhs_Stationery_content" class="bnc_rhs_product_wrap margin-top-xs flex-row flex-left">
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/product/~category_id=RH_STICKY_NOTES/~product_id=RH-234-NOTECUBE-CAT</@ofbizUrl>" title="Note Cubes | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/note-cubes-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Note Cubes | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Note Cubes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/product/~category_id=BOOKS_PADS_AND_PLANNERS/~product_id=RH-NV-NTBK-CT</@ofbizUrl>" title="Notebooks | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/notebooks-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Notebooks | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Notebooks</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/product/~category_id=STICKY_NOTES/~product_id=RH-SN-3X3-CAT</@ofbizUrl>" title="Sticky Notes | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/sticky-notes-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Sticky Notes | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Sticky Notes</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/search?w=rachael%20hale&af=st:rachaelhalenotepads</@ofbizUrl>" title="Notepads | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/notepads-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Notepads | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Notepads</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/product/~category_id=FOLDERS/~product_id=RH-912-FL-CAT</@ofbizUrl>" title="Folders | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Folders | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Folders</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>

        <div id="ENV_rhs_PackagingMailers" class="bnc_section_block">
            <div bns-collapse_content="ENV_rhs_PackagingMailers_content" bns-collapsed="false" class="bnc_section_content">
                <div class="bnc_section_header"><h2>Packaging &amp; Mailers</h2></div>
                <p class="bnc_section_desc">Whether for personal use or for the office, these notebooks, journals, planners and notepads will keep you organized and looking professional. Shop different styles and colors!</p>
            </div>
            <div id="ENV_rhs_PackagingMailers_content" class="bnc_rhs_product_wrap margin-top-xs flex-row flex-left">
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/search?w=rachael%20hale&af=st:rachaelhalecustombubblemailers</@ofbizUrl>" title="Mailers | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/mailers-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Mailers | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Mailers</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/search?w=rachael%20hale&cnt=50&af=st:rachaelhalebags</@ofbizUrl>" title="Tote bags | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/tote-bags-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Tote bags | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Tote bags</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/product/~category_id=PAPER/~product_id=RH-NV-30X72-GW-CAT</@ofbizUrl>" title="Gift Wrapping Paper | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/gift-wrapping-paper-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Gift Wrapping Paper | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Gift Wrapping Paper</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>
                <#--  <div class="bnc_rhs_prod glow">
                    <a href="<@ofbizUrl>/search?w=rachael%20hale&af=st:paperbags</@ofbizUrl>" title="Paper Gift Bags | Envelopes.com" target="_blank">
                        <div class="bnc_rhs_prod_content">
                            <div class="bnc_rhs_prod_img"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/paper-gift-bags-product-rachael-hale-shop-thumbnail?fmt=png-alpha&amp;wid=295</@ofbizScene7Url>" alt="Paper Gift Bags | Envelopes.com" /></div>
                            <div class="bnc_rhs_prod_name"><h4>Paper Gift Bags</h4> <i class="fa fa-caret-right"></i></div>
                        </div>
                    </a>
                </div>  -->
            </div>
        </div>

    </div>

</div>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/landing/bnc_collapsible.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>