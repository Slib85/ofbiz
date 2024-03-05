    <#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
    <link href="<@ofbizContentUrl>/html/css/util/bigNameCarousel-redesign.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
    <link href="<@ofbizContentUrl>/html/css/folders/main-redesign.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
    <div class="foldersContainer foldersNewLimiter main">
        <#-------- HOMEPAGE BANNERS / SLIDER -------->
        <div class="foldersRow foldersNewLimiter">
            <#if (currentTimestamp?default(now?datetime) gte "2021-11-23 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-03 23:59:59.000"?datetime)>
                <div bns-carousel="desktopHPImage" class="marginTop10 tabletDesktop-block" >
                    <div bns-carouse-image-container>
                        <div bns-image_active bns-image_index="0">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders_p_hp_folders-cyber_deals-desktop?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Cyber Deals | Folders.com" />
                        </div>
                        <a bns-image_index="1" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-custom-folder-homepage-desktop-banner-1360x300?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
                        </a>
                        <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-foil-stamped-homepage-desktop-banner-1360x300?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
                        </a>
                    </div>
                </div>
                <div bns-carousel="mobileHPImage" class="marginTop10 mobile-block">
                    <div bns-carouse-image-container>
                        <div bns-image_active bns-image_index="0">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders_p_hp-cyber_deals-mobile?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Cyber Deals | Folders.com" />
                        </div>
                        <a bns-image_index="1" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
                        </a>
                        <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foil-stamped-folders-office-theme-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
                        </a>
                    </div>
                </div>
            <#elseif (currentTimestamp?default(now?datetime) gte "2021-09-15 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-12 23:59:59.000"?datetime)>
                <div bns-carousel="desktopHPImage" class="marginTop10 tabletDesktop-block">
                    <div bns-carouse-image-container>
                        <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-custom-folder-homepage-desktop-banner-1360x300?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
                        </a>
                        <a bns-image_index="1" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>" title="The Back To School Shop | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-fall-clearance-homepage-desktop-banner-2021-v1?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="The Back To School Shop | Folders.com" />
                        </a>
                        <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-foil-stamped-homepage-desktop-banner-1360x300?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
                        </a>
                    </div>
                </div>
                <div bns-carousel="mobileHPImage" class="marginTop10 mobile-block">
                    <div bns-carouse-image-container>
                        <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
                        </a>
                        <a bns-image_index="1" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>" title="The Back To School Shop | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-fall-clearance-homepage-mobile-banner-2021-v1?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="The Back To School Shop | Folders.com" />
                        </a>
                        <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foil-stamped-folders-office-theme-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
                        </a>
                    </div>
                </div>
            <#elseif (currentTimestamp?default(now?datetime) gte "2021-12-13 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
                <div bns-carousel="desktopHPImage" class="marginTop10 tabletDesktop-block">
                    <div bns-carouse-image-container>
                        <div bns-image_active bns-image_index="0">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-end-of-year-2021-promotion-homepage-desktop-banner?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Say Bye to 2021! | Folders.com" />
                        </div>
                        <a  bns-image_index="1" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-custom-folder-homepage-desktop-banner-1360x300?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
                        </a>
                        <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-foil-stamped-homepage-desktop-banner-1360x300?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
                        </a>
                    </div>
                </div>
                <div bns-carousel="mobileHPImage" class="marginTop10 mobile-block">
                    <div bns-carouse-image-container>
                        <div bns-image_active bns-image_index="0">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-end-of-year-2021-promotion-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Say Bye to 2021! | Folders.com" />
                        </div>
                        <a bns-image_index="1" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
                        </a>
                        <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foil-stamped-folders-office-theme-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
                        </a>
                    </div>
                </div>
            <#else>
                <div bns-carousel="desktopHPImage" class="marginTop10 tabletDesktop-block">
                    <div bns-carouse-image-container>
                        <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-custom-folder-homepage-desktop-banner-1360x300?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
                        </a>
                        <a bns-image_index="1" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-foil-stamped-homepage-desktop-banner-1360x300?fmt=png-alpha&amp;wid=1360&amp;ts=3</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
                        </a>
                    </div>
                </div>
                <div bns-carousel="mobileHPImage" class="marginTop10 mobile-block">
                    <div bns-carouse-image-container>
                        <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
                        </a>
                        <a bns-image_index="1" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foil-stamped-folders-office-theme-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
                        </a>
                    </div>
                </div>
            </#if>
        </div>
        <#-- ENDLESS OPTIONS / CUSTOM QUOTE -->
        <div class="foldersRow foldersNewLimiter marginTop50 marginBottom50">
            <div class="foldersColumn large12 medium12 small12">
                <div class="new-custom-quote-hp-wrapper">  
                    <a href="<@ofbizUrl>/specialtyProducts</@ofbizUrl>" title="Get A Custom Quote | Folders.com">
                        <div class="foldersTabularRow">
                            <div class="cq-box1">
                                <div class="left-content">
                                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-redesign-endless-options-for-all-your-folders-needs-homepage-banner?fmt=png-alpha&wid=517</@ofbizScene7Url>" alt="">
                                </div>
                            </div>
                            <div class="cq-box2">
                                <div class="right-content">
                                    <div class="custom-quote-column">
                                        <h2>Get a <span style="color:#76cf49;">FREE CUSTOM</span> quote!</h2>
                                        <p>Get A Custom Quote Now </p>
                                        <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <#-- TOP CATEGORIES / 4-BOX GRID -->
        <div class="foldersRow foldersNewLimiter">
            <div class="foldersTopCategories">
                <div class="folders-top-category-box">
                    <div class="ftc-thumbnail-container">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-redesign-standard-pocket-folders-hp-banner-thumbnail?fmt=png-alpha&wid=553</@ofbizScene7Url>" alt="Standard Pocket Folders | Folders.com" class="">    
                    </div>    
                    <div class="ftc-description">
                        <h3 class="ftc-title">Standard Pocket Folders</h3>
                        <p class="ftc-text">Stay on brand with premium custom 9x12 Presentation Folders. Instantly checkout, get a fast and easy quote or choose our quick ship options!</p>
                        <div class="ftc-cta">
                            <a href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>" title="Shop Custom Standard Pocket Folders">Shop Custom <div class="arrow-ui thin-right no-margin"></div></a>
                            <a href="<@ofbizUrl>/blankCategory?category_id=9x12_FOLDERS</@ofbizUrl>" title="Shop Blank Standard Pocket Folders">Shop Blank <div class="arrow-ui thin-right no-margin"></div></a>
                        </div>
                    </div>
                </div>
                <div class="folders-top-category-box">
                    <div class="ftc-thumbnail-container">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-redesign-certificate-holders-diploma-covers-hp-banner-thumbnail?fmt=png-alpha&wid=553</@ofbizScene7Url>" alt="Certificate Holders &amp; Diploma Covers | Folders.com" class="">    
                    </div>
                    <div class="ftc-description">
                        <h3 class="ftc-title">Certificate Holders &amp; Diploma Covers</h3>
                        <p class="ftc-text">Stay on brand with premium custom 9x12 Presentation Folders. Instantly checkout, get a fast and easy quote or choose our quick ship options!</p>
                        <div class="ftc-cta">
                            <a href="<@ofbizUrl>/customFolders?categoryId=Certificate%20Holders</@ofbizUrl>" title="Shop Custom Certificate Holders &amp; Diploma Covers">Shop Custom <div class="arrow-ui thin-right no-margin"></div></a>
                            <a href="<@ofbizUrl>/blankCategory?category_id=CERTIFICATE_HOLDERS</@ofbizUrl>" title="Shop Blank Certificate Holders &amp; Diploma Covers">Shop Blank <div class="arrow-ui thin-right no-margin"></div></a>
                        </div>
                    </div>
                </div>
                <div class="folders-top-category-box">
                    <div class="ftc-thumbnail-container">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-redesign-tax-holders-hp-banner-thumbnail?fmt=png-alpha&wid=553</@ofbizScene7Url>" alt="Tax Holders | Folders.com" class="">    
                    </div>                            
                    <div class="ftc-description">
                        <h3 class="ftc-title">Tax Folders</h3>
                        <p class="ftc-text">Stay on brand with premium custom 9x12 Presentation Folders. Instantly checkout, get a fast and easy quote or choose our quick ship options!</p>
                        <div class="ftc-cta">
                            <a href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>" title="Shop Custom Tax Holders">Shop Custom <div class="arrow-ui thin-right no-margin"></div></a>
                            <a href="<@ofbizUrl>/search?af=st:preprintedtaxfolders</@ofbizUrl>" title="Shop Blank Tax Holders">Shop Blank <div class="arrow-ui thin-right no-margin"></div></a>
                        </div>
                    </div>
                </div>
                <div class="folders-top-category-box">
                    <div class="ftc-thumbnail-container">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-redesign-card-holders-small-folders-hp-banner-thumbnail?fmt=png-alpha&wid=553</@ofbizScene7Url>" alt="Card Holders &amp; Small Folders | Folders.com" class="">    
                    </div>    
                    <div class="ftc-description">
                        <h3 class="ftc-title">Card Holders &amp; Small Folders</h3>
                        <p class="ftc-text">Stay on brand with premium custom 9x12 Presentation Folders. Instantly checkout, get a fast and easy quote or choose our quick ship options!</p>
                        <div class="ftc-cta">
                            <a href="<@ofbizUrl>/customFolders?categoryId=Small%20Folders</@ofbizUrl>" title="Shop Custom Card Holders &amp; Small Folders">Shop Custom <div class="arrow-ui thin-right no-margin"></div></a>
                            <a href="<@ofbizUrl>/blankCategory?category_id=SMALL_FOLDERS</@ofbizUrl>" title="Shop Blank Card Holders &amp; Small Folders">Shop Blank <div class="arrow-ui thin-right no-margin"></div></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <#-- CUSTOM-QUICK / 2-BOX GRID -->
        <div class="foldersRow foldersNewLimiter">
            <div id="FOL_CustomPrint" class="foldersColumn large6 medium6 small12">
                <a href="<@ofbizUrl>/specialtyProducts</@ofbizUrl>" title="Top Custom Quoted Products | Folders.com">
                    <div class="new-custom-printing-content">
                        <img class="folders-hp-image" src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-redesign-top-custom-quoted-products-hp-banner-thumbnail?wid=668&amp;hei=242&amp;fmt=png-alpha</@ofbizScene7Url>" alt="">
                        <div class="block-title">Top Custom Quoted Products</div>
                        <div class="block-desc">Presentation folders, certificate holders, legal folders &amp; more. <br />Get a quote today on our top custom products!</div>
                        <div class="block-link" type="button"><span>Get Started</span> <div class="arrow-ui thin-right no-margin"></div></div>
                    </div>
                </a>
            </div>
            <div id="FOL_QuickShip" class="foldersColumn large6 medium6 small12">
                <a href="<@ofbizUrl>/customFolders?af=st:quickshipfoilstampedfolders%20st:quickshipfourcolorfolders</@ofbizUrl>" title="Quick Ship Folders | Folders.com">
                    <div class="new-quick-ship-content">
                        <img class="folders-hp-image" src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-redesign-quick-ship-folders-hp-banner-thumbnail?wid=668&amp;hei=242&amp;fmt=png-alpha</@ofbizScene7Url>" alt="">
                        <div class="block-title">Quick Ship Folders</div>
                        <div class="block-desc">Need your folders quick? Get your folders shipped in <br />as little as 1 business day with our quick ship options!</div>
                        <div class="block-link" type="button"><span>Learn More</span> <div class="arrow-ui thin-right no-margin"></div></div>
                    </div>
                </a>
            </div>
        </div>
        <#-- TOP CUSTOM PRODUCTS / SLIDER -->
        <div class="foldersNewLimiter marginTop50 marginBottom50">
            <div class="section-header foldersTabularRow">
                <div class="section-title">
                    <h2>Top Custom Products</h2>
                </div>
                <div class="section-cta">
                    <a href="<@ofbizUrl>/search?w=*&af=customizable:y%20use:folders</@ofbizUrl>" title="Shop All">
                        <span>Shop All</span>
                        <div class="arrow-ui thin-right no-margin"></div>
                    </a> 
                </div>
            </div>
            <div class="mailingEnvelopes">
                <div class="slideIt-container homePageSlider">
                    <div class="slideIt-left">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                    <div class="slideIt">
                        <div>
                            <div>
                                <a bns-track_event="Homepage|TopCustomProducts|STANDARD 9 X 12 FOLDERS" class="text-center" href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/standard-9x12-folders-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">STANDARD 9 X 12 FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopCustomProducts|QUICK SHOP FOLDERS" class="text-center" href="<@ofbizUrl>/customFolders?af=st:quickshipfoilstampedfolders%20st:quickshipfourcolorfolders</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/quick-ship-folders-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">QUICK SHOP FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopCustomProducts|LEGAL SIZE FOLDERS" class="text-center" href="<@ofbizUrl>/search?w=Legal%20Folders</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/legal-size-folders-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">LEGAL SIZE FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopCustomProducts|CERTIFICATE HOLDERS" class="text-center" href="<@ofbizUrl>/customFolders?categoryId=Certificate%20Holder</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/certificate-holder-folders-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">CERTIFICATE HOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <#--  <div>
                                <a bns-track_event="Homepage|TopCustomProducts|REINFORCED FOLDERS" class="text-center" href="<@ofbizUrl>/search?w=reinforced%20folders</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/reinforced-folders-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">REINFORCED FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>  -->
                            <div>
                                <a bns-track_event="Homepage|TopCustomProducts|EXTRA CAPACITY FOLDERS" class="text-center" href="<@ofbizUrl>/customFolders?categoryId=Specialty%209%20x%2012%20Presentation%20Folder</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/extra-capacity-folders-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">EXTRA CAPACITY FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopCustomProducts|SMALL FOLDERS" class="text-center" href="<@ofbizUrl>/customFolders?categoryId=Small%20Folders</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/small-folders-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">SMALL FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopCustomProducts|CARD HOLDERS" class="text-center" href="<@ofbizUrl>/customFolders?categoryId=Small%20Folder</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/card-holders-folders-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">CARD HOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                </div>
            </div>
        </div>
        <#-- FREE SHIP / TEXT BANNER -->
        <div data-bnreveal="fship2" class="foldersRow foldersNewLimiter">
            <div class="foldersFreeShipTextBanner">
                <h2>Free Shipping on all orders over $${globalContext.freeShippingAmount?default("299")}*</h2>
                <p>Use Code <strong>FREE${globalContext.freeShippingAmount?default("299")}</strong> at Checkout</p>
            </div>
        </div>
        <#-- TOP BLANK PRODUCTS / SLIDER -->
        <div class="foldersNewLimiter marginTop50 marginBottom50">
            <div class="section-header foldersTabularRow">
                <div class="section-title">
                    <h2>Top Blank Products</h2>
                </div>
                <div class="section-cta">
                    <a href="<@ofbizUrl>/search?w=*&af=customizable:n%20use:folders</@ofbizUrl>" title="Shop All">
                        <span>Shop All</span>
                        <div class="arrow-ui thin-right no-margin"></div>
                    </a> 
                </div>
            </div>
            <div class="mailingEnvelopes">
                <div class="slideIt-container homePageSlider">
                    <div class="slideIt-left">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                    <div class="slideIt">
                        <div>
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|MOST POPULAR BLANK PRODUCTS" class="text-center" href="<@ofbizUrl>/topBlanks</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/most-popular-blank-products-folders-blank-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">MOST POPULAR <br />BLANK PRODUCTS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|WELCOME FOLDERS" class="text-center" href="<@ofbizUrl>/search?w=*&af=st:welcomefolders</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/welcome-folders-folders-new-blank-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">WELCOME FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|PRE-PRINTED TAX FOLDERS" class="text-center" href="<@ofbizUrl>/search?w=*&af=st:preprintedtaxfolders</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/pre-printed-tax-folders-new-blank-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">PRE-PRINTED TAX FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|POLY FOLDERS" class="text-center" href="<@ofbizUrl>/blankCategory?category_id=POLY_FOLDERS</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-folders-new-blank-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">POLY FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|BLANK CERTIFICATE HOLDERS" class="text-center" href="<@ofbizUrl>/blankCategory?category_id=CERTIFICATE_HOLDERS</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/blank-certificate-holders-blank-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">BLANK CERTIFICATE HOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <#--  <div>
                                <a bns-track_event="Homepage|TopBlankProducts|EXTRA CAPACITY FOLDERS" class="text-center" href="<@ofbizUrl>/search?af=st:blankextracapacityfolders</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/extra-capacity-folders-new-blank-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">EXTRA CAPACITY FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>  -->
                            <#--  <div>
                                <a bns-track_event="Homepage|TopBlankProducts|POLY BINDERS" class="text-center" href="<@ofbizUrl>#</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/poly-binders-folders-new-blank-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">POLY BINDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>  -->
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|SMALL FOLDERS" class="text-center" href="<@ofbizUrl>/blankCategory?category_id=SMALL_FOLDERS</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/small-folders-new-blank-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">SMALL FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|LEGAL FOLDERS" class="text-center" href="<@ofbizUrl>/blankCategory?category_id=LEGAL_FOLDERS</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/legal-folders-new-blank-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">LEGAL FOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|PRE-PRINTED CERTIFICATE" class="text-center" href="<@ofbizUrl>/blankCategory?category_id=PRE_PRINTED_CERTIFICATE_HOLDERS</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/pre-printed-certificates-blank-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">PRE-PRINTED CERTIFICATE</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|PADDED DIPLOMA COVERS" class="text-center" href="<@ofbizUrl>/blankCategory?category_id=DIPLOMA_COVERS</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/padded-diploma-covers-blank-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">PADDED DIPLOMA COVERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                            <#--  <div>
                                <a bns-track_event="Homepage|TopBlankProducts|BLANK CARD HOLDERS" class="text-center" href="<@ofbizUrl>#</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/blank-card-holders-blank-new-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">BLANK CARD HOLDERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>  -->
                            <div>
                                <a bns-track_event="Homepage|TopBlankProducts|BLANK FOLDERS WITH FASTENERS" class="text-center" href="<@ofbizUrl>/search?af=st:folderswithfasteners</@ofbizUrl>">
                                    <img class="glow lightGrayBckgrd" src="<@ofbizScene7Url>/is/image/ActionEnvelope/blank-folders-with-fasteners-new-blank-thumbnails?fmt=png-alpha&wid=265&hei=265</@ofbizScene7Url>" alt="" />
                                    <div class="sliderTextWrap">
                                        <div class="block-link textFontSizeOverwrite">BLANK FOLDERS WITH FASTENERS</div> <div class="arrow-ui thin-right no-margin"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
                    </div>
                </div>
            </div>
        </div>
        <#-- ENDLESS OPTIONS / CUSTOM QUOTE -->
        <div class="foldersRow foldersNewLimiter marginTop50 marginBottom50">
            <div class="foldersColumn large12 medium12 small12">
                <div class="new-guarantee-box-wrapper">  
                    <a href="javascript:void(0)" title="">
                        <div class="foldersTabularRow">
                            <div class="cq-box1">
                                <div class="left-content">
                                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-redesign-product-quality-guarantees-hp-banner-thumbnail?fmt=png-alpha&wid=531</@ofbizScene7Url>" alt="">
                                </div>
                            </div>
                            <div class="cq-box2">
                                <div class="right-content">
                                    <div class="custom-quote-column">
                                        <h2>FOLDERS.COM GUARANTEES</h2>
                                        <div class="guarantee-box">
                                            <ul>
                                                <li>&bull; The Absolute Best Customer Service</li>
                                                <li>&bull; Expert Industry Professionals</li>
                                                <li>&bull; Pride in Excellence and Innovation</li>
                                                <li>&bull; Low Minimum Quantity</li>
                                                <li>&bull; Free Shipping on ALL custom Orders</li>
                                            </ul>
                                            <p>*Some exclusions apply. Click here for details.</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div class="foldersRow foldersNewLimiter textCenter">
            <h4>Have Questions or Need Help Placing Your Order?</h4>
            <div class="foldersColumn large12 medium12 small12">
                <ul class="FOL_HP_NeedHelp_UI foldersFlexRow flexCenter">
                    <li>
                        <div class="uibox">
                            <div class="foldersTabularRow">
                                <div>
                                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/phone-icon-green?fmt=png-alpha&amp;wid=24</@ofbizScene7Url>">
                                    <span><a class="navyblue boldText" href="tel:1-877-683-5673">800.296.4321</a></span>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="uibox">
                            <div class="foldersTabularRow">
                                <div>
                                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/mail-icon-green?fmt=png-alpha&amp;wid=24</@ofbizScene7Url>">
                                    <span><a class="navyblue boldText" href="http://support.folders.com/customer/portal/emails/new">Email Us</a></span>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="uibox">
                            <div class="foldersTabularRow">
                                <div>
                                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/chat-icon-green?fmt=png-alpha&amp;wid=24</@ofbizScene7Url>">
                                    <span><a class="navyblue boldText" href="" bns-driftOpenChat>Live Chat</a></span>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="foldersLineBreak"></div>
    <#---------- SEO FOOTER / HOMEPAGE ---------->
    <div id="FOL_HP_SEO_FOOTER" class="foldersRow foldersNewLimiter marginTop50 marginBottom50">
        <div class="foldersColumn large6 medium12 small12">
            <h5 class="marginBottom10">Custom and Blank Presentation Folders, Certificate Holders for every business</h5>
            <p>When it comes to creating a professional presentation, Folders.com is the top destination. We make it as easy as possible for customers like you to find affordable folders and office supplies that don’t cut any corners on quality. We proudly supply an extensive selection of products for home and office use. We also offer an array of customization options and printing services that can help businesses both big and small stand out from the competition. Whether you are looking for a way to attract new clients to your accounting firm, make better-looking business proposals, or save on the supplies you already use, we have something for everyone in our selection. We also offer free shipping over $299!</p>
            <h6 class="noMargin marginTop30 marginBottom10">Custom Products</h6>
            <p>No one knows your business like you do. Help your brand get noticed by going custom with printed folders. By choosing us as your go-to source for personalized folders, you receive total creative freedom from start to finish. Our selection of customizable products includes a wide variety of legal-size folders, presentation folders, certificate holders, and so much more. Using our online design tool, you can upload your own custom artwork or company logo. If you have shopped with us before, you can even re-order with the same design or make additional changes to a previous project.</p>
            <p>We Also Offer Folder Printing Services: Embossing, Foil Stamping, Full-Color Printing, One-Color Printing, Spot Color Printing</p>
            <p>Can't seem to find the specific solution you have been looking for? With a wide range of printing methods, paper weights, colors, and textures at our disposal, we can collaborate with you to create printed folders that are as utterly unique as your brand. Contact us to submit any special requests and receive a quote for the quantity you need.</p>
        </div>
        <div class="foldersColumn large6 medium12 small12">
            <p>Don't let the upcoming tax season stress you out. In The Tax Shop, you will find a vast selection of custom presentation folders as well as promotional folders that can help you project a professional image and establish your credibility. Need extra space to store a large volume of paperwork? Our extra-capacity expansion pockets may just do the trick. Custom certificate holders are perfect for companies, schools, and other organizations that take the time to recognize employees, students, or volunteers. Our custom key card holders are ideal for country clubs, hotels, and other venues. </p>
            <p>Want to make the most of your budget? Get a custom quote today! Simply describe your custom project, select a quantity that fits your needs, and submit your contact information to receive an estimate from one of our folder experts. If you need any help finding a specific product or would like to learn more about our presentation folder printing services, we are standing by to assist you in any way we can. Call, email, or chat with us online for further assistance.</p>
            <h6 class="noMargin marginTop30 marginBottom10">Blank Products</h6>
            <p>Shopping on a tight budget or in a rush to receive your materials? We offer a wide selection of blank products that eliminate the hassle of branding every piece of print collateral. Our most popular blank products include legal size, pocket, and presentation folders, but we also carry an assortment of blank certificate holders, card holders, and more.</p>
            <p><a href="<@ofbizUrl>/search?w=*&af=customizable:n</@ofbizUrl>">Browse All Blank In-Stock Products</a></p>
            <p>We guarantee to each and every customer: the highest quality customer service, the highest level of professionalism, pride in excellence and innovation, low minimum quantity and bulk prices and free shipping on ALL custom orders.</p>
        </div>
    </div>

</div><#-- DO NOT DELETE THIS DIV -->

<script type='text/javascript' src="<@ofbizContentUrl>/html/js/util/bigNameCarousel.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>