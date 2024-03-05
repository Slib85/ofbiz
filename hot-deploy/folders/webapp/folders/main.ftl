<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/util/bigNameCarousel.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/folders/main2.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<div class="foldersContainer foldersContainerLimiter main noPadding">
    <#if (currentTimestamp?default(now?datetime) gte "2021-09-30 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-10-27 23:59:59.000"?datetime)>
    <div bns-carousel="desktopHPImage" class="marginTop10 tabletDesktop-block" >
        <div bns-carouse-image-container>
            <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>" title="Fall Clearance | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-fall-clearance-homepage-desktop-banner-2021-v1?fmt=png-alpha&wid=1200&hei=275</@ofbizScene7Url>" alt="Fall Clearance | Folders.com" />
            </a>
            <a bns-image_index="1" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-desktop-banner?fmt=png-alpha&wid=1200&hei=275</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
            </a>
            <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foil-stamped-folders-office-theme-homepage-desktop-banner?fmt=png-alpha&wid=1200&hei=275</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
            </a>
        </div>
    </div>
    <div bns-carousel="mobileHPImage" class="marginTop10 mobile-block" >
        <div bns-carouse-image-container>
            <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>" title="Fall Clearance | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-fall-clearance-homepage-mobile-banner-2021-v1??fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Fall Clearance | Folders.com" />
            </a>
            <a bns-image_index="1" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
            </a>
            <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foil-stamped-folders-office-theme-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
            </a>
        </div>
    </div>
    <#elseif (currentTimestamp?default(now?datetime) gte "2021-10-28 00:00:00.000"?datetime)>
    <div bns-carousel="desktopHPImage" class="marginTop10 tabletDesktop-block" >
        <div bns-carouse-image-container>
            <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>" title="Custom Printing Service | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-custom-printing-service-marketing-homepage-desktop-banner?fmt=png-alpha&wid=1200&hei=275</@ofbizScene7Url>" alt=" | Folders.com" />
            </a>
            <a bns-image_index="1" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>" title="Fall Clearance | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-fall-clearance-homepage-desktop-banner-2021-v1?fmt=png-alpha&wid=1200&hei=275</@ofbizScene7Url>" alt="Fall Clearance | Folders.com" />
            </a>
            <a bns-image_index="2" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-desktop-banner?fmt=png-alpha&wid=1200&hei=275</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
            </a>
        </div>
    </div>
    <div bns-carousel="mobileHPImage" class="marginTop10 mobile-block" >
        <div bns-carouse-image-container>
            <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>" title="Custom Printing Service | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-custom-printing-service-marketing-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt=" | Folders.com" />
            </a>
            <a bns-image_index="1" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>" title="Fall Clearance | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-fall-clearance-homepage-mobile-banner-2021-v1?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Fall Clearance | Folders.com" />
            </a>
            <a bns-image_index="2" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
            </a>
        </div>
    </div>
    <#else>
    <div bns-carousel="desktopHPImage" class="marginTop10 tabletDesktop-block" >
        <div bns-carouse-image-container>
            <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>" title="Fall Clearance | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-fall-clearance-homepage-desktop-banner-2021-v1?fmt=png-alpha&wid=1200&hei=275</@ofbizScene7Url>" alt="Fall Clearance | Folders.com" />
            </a>
            <a bns-image_index="1" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-desktop-banner?fmt=png-alpha&wid=1200&hei=275</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
            </a>
            <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foil-stamped-folders-office-theme-homepage-desktop-banner?fmt=png-alpha&wid=1200&hei=275</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
            </a>
        </div>
    </div>
    <div bns-carousel="mobileHPImage" class="marginTop10 mobile-block" >
        <div bns-carouse-image-container>
            <a bns-image_active bns-image_index="0" href="<@ofbizUrl>/search?w=*&af=sale:clearance%20sale:sale</@ofbizUrl>" title="Fall Clearance | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/folders-fall-clearance-homepage-mobile-banner-2021-v1??fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Fall Clearance | Folders.com" />
            </a>
            <a bns-image_index="1" href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" title="9x12 Presentation Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-four-color-printed-9x12-presentation-folders-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="9x12 Presentation Folders | Folders.com" />
            </a>
            <a bns-image_index="2" href="<@ofbizUrl>/printMethod?id=foilStamp</@ofbizUrl>" title="Foil Stamp Folders | Folders.com">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/foil-stamped-folders-office-theme-homepage-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Foil Stamp Folders | Folders.com" />
            </a>
        </div>
    </div>
    </#if>
    <div class="foldersRow">
        <div class="foldersColumn large3 medium3 small12">
            <div class="squareProductContent padding10 textCenter fbc-white">
                <p class="marginBottom10">Four Color<br /> 9x12 Presentation Folders <span style="display:block;color:#a5a5a5; font-size:12px;">SKU: 08-28-505-C</span></p>
                <img style="padding: 0 10px 10px 10px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/four-color-9x12-presentation-folders-thumbnail?fmt=png-alpha&amp;wid=236&amp;hei=165</@ofbizScene7Url>" alt="Customized Pocket Folders" />
                <div class="foldersRow homePageCustomBlankButtonsContainer">
                    <div class="foldersColumn large12">
                        <a href="<@ofbizUrl>/product/~category_id=STANDARD_FOLDER/~product_id=08-28-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR</@ofbizUrl>" class="foldersButton"><div>SHOP NOW</div></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="foldersColumn large3 medium3 small12">
            <div class="squareProductContent padding10 textCenter fbc-white">
                <p class="marginBottom10">Standard Pocket Folders</p>
                <img style="padding: 10px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/pocketFoldersHomePage?fmt=png-alpha&amp;wid=226&amp;hei=168</@ofbizScene7Url>" alt="Customized Pocket Folders" />
                <div class="foldersRow homePageCustomBlankButtonsContainer">
                    <div class="foldersColumn large6">
                        <a href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>" class="foldersButton"><div>Custom</div></a>
                    </div>
                    <div class="foldersColumn large6">
                        <a href="<@ofbizUrl>/blankCategory?category_id=9x12_FOLDERS</@ofbizUrl>" class="foldersButton"><div>Blank</div></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="foldersColumn large3 medium3 small12">
            <div class="squareProductContent padding10 textCenter fbc-white">
                <p>Certificate Holders <br />&amp; Diploma Covers</p>
                <img style="padding: 12px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/certificateHoldersHomePage?fmt=png-alpha&amp;wid=236&amp;hei=151</@ofbizScene7Url>" alt="Customized Certificate Holders" />
                <div class="foldersRow homePageCustomBlankButtonsContainer">
                    <div class="foldersColumn large6">
                        <a href="<@ofbizUrl>/customFolders?categoryId=Certificate%20Holders</@ofbizUrl>" class="foldersButton"><div>Custom</div></a>
                    </div>
                    <div class="foldersColumn large6">
                        <a href="<@ofbizUrl>/blankCategory?category_id=CERTIFICATE_HOLDERS</@ofbizUrl>" class="foldersButton"><div>Blank</div></a>
                    </div>
                </div>
            </div>
        </div>
        <#--  <div class="foldersColumn large3 medium3 small12">
            <div class="squareProductContent padding10 textCenter fbc-white">
                <p class="marginBottom10">Tax Folders</p>
                <img style="padding: 10.5px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-taxFolders?fmt=png-alpha&amp;wid=211&amp;hei=180&amp;ts=1</@ofbizScene7Url>" alt="Customized Binders" />
                <div class="foldersRow homePageCustomBlankButtonsContainer">
                    <div class="foldersColumn large6">
                        <a href="<@ofbizUrl>/taxShop</@ofbizUrl>" class="foldersButton"><div>Custom</div></a>
                    </div>
                    <div class="foldersColumn large6">
                        <a href="<@ofbizUrl>/taxShop#blankPreprintedFolders</@ofbizUrl>" class="foldersButton"><div>Blank</div></a>
                    </div>
                </div>
            </div>
        </div>  -->
        <div class="foldersColumn large3 medium3 small12">
            <div class="squareProductContent padding10 textCenter fbc-white">
                <p class="marginBottom10">Card Holders &amp; Small Folders</p>
                <img style="padding: 15px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/cardHoldersHomePage?fmt=png-alpha&amp;wid=218&amp;hei=171</@ofbizScene7Url>" alt="Customized Card Holders" />
                <div class="foldersRow homePageCustomBlankButtonsContainer">
                    <div class="foldersColumn large6">
                        <a href="<@ofbizUrl>/customFolders?categoryId=Small%20Folders</@ofbizUrl>" class="foldersButton"><div>Custom</div></a>
                    </div>
                    <div class="foldersColumn large6">
                        <a href="<@ofbizUrl>/blankCategory?category_id=SMALL_FOLDERS</@ofbizUrl>" class="foldersButton"><div>Blank</div></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#if (currentTimestamp?default(now?datetime) gte "2021-03-08 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-03-10 23:59:59.000"?datetime)>
    <div class="topCustomQuotedProductBanner">
        <p class="topCustomQuotedProductBannerText">Top Custom Quoted Products</p>
        <div class="topCustomQuotedProductsContainer">
            <a class="topCustomQuotedProducts" href="<@ofbizUrl>/product/~category_id=ST_PR_FOLDERS/~product_id=SF-101</@ofbizUrl>">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/SF-101?fmt=png-alpha&amp;wid=200</@ofbizScene7Url>">
                <p>9x12 Presentation Folders <br />Standard Two Pocket</p>
                <div class="topCustomQuotedProductButton">Get a Quote <i class="fa fa-caret-right"></i></div>
            </a>
            <a class="topCustomQuotedProducts" href="<@ofbizUrl>/product/~category_id=CERTIFICATE_HOLDERS/~product_id=CHEL-185</@ofbizUrl>">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/CHEL-185?fmt=png-alpha&amp;wid=200</@ofbizScene7Url>">
                <p>Long Hinge Landscape <br />Certificate Holders</p>
                <div class="topCustomQuotedProductButton">Get a Quote <i class="fa fa-caret-right"></i></div>
            </a>
            <a class="topCustomQuotedProducts" href="<@ofbizUrl>/product/~product_id=CKH-2711</@ofbizUrl>">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/CKH-2711?fmt=png-alpha&amp;wid=200</@ofbizScene7Url>">
                <p>Card Holders <br />Right Pocket (3 3/8" x 6")</p>
                <div class="topCustomQuotedProductButton">Get a Quote <i class="fa fa-caret-right"></i></div>
            </a>
            <a class="topCustomQuotedProducts" href="<@ofbizUrl>/product/~category_id=LS_FOLDERS/~product_id=LF-118</@ofbizUrl>">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/LF-118?fmt=png-alpha&amp;wid=200</@ofbizScene7Url>">
                <p>Legal Size <br />Folder</p>
                <div class="topCustomQuotedProductButton">Get a Quote <i class="fa fa-caret-right"></i></div>
            </a>
            <a class="topCustomQuotedProducts" href="<@ofbizUrl>/product/~product_id=PDCL-8x10</@ofbizUrl>">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/PDCL-8x10?fmt=png-alpha&amp;wid=200</@ofbizScene7Url>">
                <p>Padded Diploma Cover <br />8" x 10" Landscape Orientation</p>
                <div class="topCustomQuotedProductButton">Get a Quote <i class="fa fa-caret-right"></i></div>
            </a>
        </div>
    </div>
    <#else>
    <div class="foldersRow textCenter marginBottom20 marginTop20">
        <a href="<@ofbizUrl>/specialtyProducts</@ofbizUrl>" title="Top Custom Quoted Products | Folders.com">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/new-homepage-desktop-content-banner-custom-quote-product-blue-background-theme?fmt=png-alpha&wid=1200&hei=285</@ofbizScene7Url>" alt="Top Custom Quoted Products | Folders.com" />
        </a>
    </div>
    </#if>
    <div bns-certonalisthp class=""></div>
    <div class="fbc-white padding20 noPaddingBottom">
        <h1 class="textCenter textUnderline">Custom Folders and Certificate Holders</h1>
        <div class="foldersFlexRow homepageLinkList customProductLinks">
            <a href="<@ofbizUrl>/custom9x12Folders</@ofbizUrl>">Standard 9 x 12 Folders <i class="fa fa-caret-right"></i></a>
            <a href="<@ofbizUrl>/customFolders?af=st:quickshipfoilstampedfolders%20st:quickshipfourcolorfolders</@ofbizUrl>">Quick Ship Folders <i class="fa fa-caret-right"></i></a>
            <a href="<@ofbizUrl>/search?w=Legal%20Folders</@ofbizUrl>">Legal Size Folders <i class="fa fa-caret-right"></i></a>
            <a href="<@ofbizUrl>/search?w=reinforced%20folders</@ofbizUrl>">Reinforced Folders <i class="fa fa-caret-right"></i></a>
            <a href="<@ofbizUrl>/customFolders?categoryId=Specialty%209%20x%2012%20Presentation%20Folder</@ofbizUrl>#ExtraCapacityFolder">Extra Capacity Folders <i class="fa fa-caret-right"></i></a>
            <a href="<@ofbizUrl>/search?w=Certificate%20Holders</@ofbizUrl>">Certificate Holders <i class="fa fa-caret-right"></i></a>
            <a href="<@ofbizUrl>/customFolders?categoryId=Small%20Folders</@ofbizUrl>">Small Folders <i class="fa fa-caret-right"></i></a>
            <a href="<@ofbizUrl>/search?w=card%20holders&af=customizable:y</@ofbizUrl>">Card Holders <i class="fa fa-caret-right"></i></a>
        </div>
        <div class="foldersRow textCenter moreCustomizedProductImages">
            <div class="foldersColumn large3 noMarginBottom">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-moreCustomProducts1?fmt=png-alpha&amp;wid=236&amp;hei=222</@ofbizScene7Url>" alt="Custom Products"/>
            </div>
            <div class="foldersColumn large3 noMarginBottom">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-moreCustomProducts2?fmt=png-alpha&amp;wid=236&amp;hei=222</@ofbizScene7Url>" alt="Custom Products"/>
            </div>
            <div class="foldersColumn large3 noMarginBottom">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-moreCustomProducts3?fmt=png-alpha&amp;wid=236&amp;hei=222</@ofbizScene7Url>" alt="Custom Products"/>
            </div>
            <div class="foldersColumn large3 noMarginBottom">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-moreCustomProducts4?fmt=png-alpha&amp;wid=236&amp;hei=222</@ofbizScene7Url>" alt="Custom Products"/>
            </div>
        </div>
    </div>
    <h2 class="textCenter marginBottom20 marginTop20">Stock up Your Office With Blank Products<br /><span class="ftc-orange">In-Stock and Ready to Ship!</span></h2>
    <div class="foldersRow marginTop10 showMobileOnly">
        <div class="foldersColumn large3 medium6 small12">
            <a href="<@ofbizUrl>/search?w=*&af=st:blankstandardpresentationfolders</@ofbizUrl>" class="fbc-white textCenter squareProductContent padding10">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-blankPocketFolders?fmt=png-alpha&amp;wid=247&amp;hei=225</@ofbizScene7Url>" alt="Blank Pocket Folders" />
                <p class="marginTop10">Blank Standard Folders <i class="fa fa-caret-right"></i></p>
            </a>
        </div>
        <div class="foldersColumn large3 medium6 small12">
            <a href="<@ofbizUrl>/search?w=*&af=st:folderswithfasteners</@ofbizUrl>" class="fbc-white textCenter squareProductContent padding10">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-blankPocketFoldersWithBrads?fmt=png-alpha&amp;wid=247&amp;hei=225</@ofbizScene7Url>" alt="Blank Folders with Brads" />
                <p>Blank Folders with Fasteners <i class="fa fa-caret-right"></i></p>
            </a>
        </div>
        <div class="foldersColumn large3 medium6 small12">
            <a href="<@ofbizUrl>/search?w=*&af=st:blankcertificateholders</@ofbizUrl>" class="fbc-white textCenter squareProductContent padding10">
                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-blankCertificateHolders?fmt=png-alpha&amp;wid=247&amp;hei=225</@ofbizScene7Url>" alt="Blank Certificate Holders" />
                <p>Blank Certificate Holders <i class="fa fa-caret-right"></i></p>
            </a>
        </div>
        <div class="foldersColumn large3 medium6 small12">
            <a href="<@ofbizUrl>/search?w=card%20holder</@ofbizUrl>" class="fbc-white textCenter squareProductContent padding10">
                <img class="padding30" src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-blankCardHolders?fmt=png-alpha&amp;wid=187&amp;hei=165</@ofbizScene7Url>" alt="Blank Card Holders" />
                <p>Blank Card Holders <i class="fa fa-caret-right"></i></p>
            </a>
        </div>
    </div>
    <div class="blankProductsList">
        <div class="backgroundWhite padding10">
            <h2 class="textCenter textUnderline">Blank Products</h2>
            <div class="foldersFlexRow homepageLinkList moreCustomizedProductLinks">
                <a href="<@ofbizUrl>/topBlanks</@ofbizUrl>">Most Popular Blank Products <i class="fa fa-caret-right"></i></a>
                <a href="<@ofbizUrl>/search?w=*&af=st:welcomefolders</@ofbizUrl>">Welcome Folders <i class="fa fa-caret-right"></i></a>
                <a href="<@ofbizUrl>/search?w=*&af=st:preprintedtaxfolders</@ofbizUrl>">Pre Printed Tax Folders <i class="fa fa-caret-right"></i></a>
                <a href="<@ofbizUrl>/blankCategory?category_id=POLY_FOLDERS</@ofbizUrl>">Poly Folders <i class="fa fa-caret-right"></i></a>
                <a href="<@ofbizUrl>/search?af=st:blankpolybinders</@ofbizUrl>">Poly Binders <i class="fa fa-caret-right"></i></a>
                <a href="<@ofbizUrl>/search?w=*&af=st:blankextracapacityfolders</@ofbizUrl>">Extra Capacity Folders <i class="fa fa-caret-right"></i></a>
                <a href="<@ofbizUrl>/search?w=*&af=st:blanksmallfolders</@ofbizUrl>">Small Folders <i class="fa fa-caret-right"></i></a>
                <a href="<@ofbizUrl>/search?w=*&af=st:blanklegalfolders</@ofbizUrl>">Legal Size Folders <i class="fa fa-caret-right"></i></a>
                <a href="<@ofbizUrl>/search?w=*&af=st:preprintedpapercertificates</@ofbizUrl>">Pre Printed Certificates <i class="fa fa-caret-right"></i></a>
                <a href="<@ofbizUrl>/search?af=st:blankpaddeddiplomacovers</@ofbizUrl>">Padded Diploma Covers <i class="fa fa-caret-right"></i></a>
            </div>
        </div>
        <div class="foldersRow marginTop10 hideOnMobile">
            <div class="foldersColumn large3 medium6 small12">
                <a href="<@ofbizUrl>/search?w=*&af=st:blankstandardpresentationfolders</@ofbizUrl>" class="fbc-white textCenter squareProductContent padding10">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-blankPocketFolders?fmt=png-alpha&amp;wid=247&amp;hei=225</@ofbizScene7Url>" alt="Blank Pocket Folders" />
                    <p class="marginTop10">Blank Standard Folders <i class="fa fa-caret-right"></i></p>
                </a>
            </div>
            <div class="foldersColumn large3 medium6 small12">
                <a href="<@ofbizUrl>/search?w=*&af=st:folderswithfasteners</@ofbizUrl>" class="fbc-white textCenter squareProductContent padding10">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-blankPocketFoldersWithBrads?fmt=png-alpha&amp;wid=247&amp;hei=225</@ofbizScene7Url>" alt="Blank Folders with Brads" />
                    <p>Blank Folders with Fasteners <i class="fa fa-caret-right"></i></p>
                </a>
            </div>
            <div class="foldersColumn large3 medium6 small12">
                <a href="<@ofbizUrl>/search?w=*&af=st:blankcertificateholders</@ofbizUrl>" class="fbc-white textCenter squareProductContent padding10">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-blankCertificateHolders?fmt=png-alpha&amp;wid=247&amp;hei=225</@ofbizScene7Url>" alt="Blank Certificate Holders" />
                    <p>Blank Certificate Holders <i class="fa fa-caret-right"></i></p>
                </a>
            </div>
            <div class="foldersColumn large3 medium6 small12">
                <a href="<@ofbizUrl>/search?w=card%20holder</@ofbizUrl>" class="fbc-white textCenter squareProductContent padding10">
                    <img class="padding30" src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-blankCardHolders?fmt=png-alpha&amp;wid=187&amp;hei=165</@ofbizScene7Url>" alt="Blank Card Holders" />
                    <p>Blank Card Holders <i class="fa fa-caret-right"></i></p>
                </a>
            </div>
        </div>
        <h3 class="textCenter padding20">
            <a href="<@ofbizUrl>/search?w=blank</@ofbizUrl>" class="ftc-green">Browse All Blank In-Stock Products <i class="fa fa-caret-right"></i></a>
        </h3>
    </div>
    <div class="foldersRow fbc-white textCenter foldersGuarantee">
        <div class="foldersColumn large6 medium12 small12 padding20">
            <div class="textLeft">
                <h2 class="textUnderline">Folders.com Guarantees</h2>
                <ul class="checkMark noPadding">
                    <li>The Absolute Best Customer Service</li>
                    <li>Expert Industry Professionals</li>
                    <li>Pride in Excellence and Innovation</li>
                    <li>Low Minimum Quantity</li>
                    <li>Free Shipping on ALL custom Orders</li>
                </ul>
            </div>
        </div>
        <div class="foldersColumn large6 medium12 small12 noPaddingRight noMargin">
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/f_hp-foldersGuarantee?fmt=jpeg&amp;qlt=100&amp;wid=678&amp;hei=422</@ofbizScene7Url>" alt="Folders.com Guarantee" />
        </div>
    </div>
    <div class="foldersRow textCenter exclusionsApply">
        *Some exclusions apply. Click <a data-bnreveal="fship">here</a> for details.
    </div>
    <div class="textCenter marginTop20 bottomHelpBar">
        <h2 class="padding20">Have Questions or Need Help Placing Your Order?</h2>
        <div class="foldersRow marginTop20">
            <div class="foldersColumn large4 medium4 small12">
                <a href="tel:1-800-296-4321" class="ftc-green textBold"><i class="fa fa-phone-square ftc-green"></i> Call 1-800-296-4321</a>
            </div>
            <div class="foldersColumn large4 medium4 small12">
                <a href="http://support.folders.com/customer/portal/emails/new" class="ftc-green textBold"><i class="fa fa-envelope ftc-green"></i> Email Us</a>
            </div>
            <div class="foldersColumn large4 medium4 small12">
                <p href="#" bns-driftOpenChat class="ftc-green textBold"><i class="fa fa-user-circle ftc-green"></i> Chat Live with Support</p>
            </div>
        </div>
    </div>
    <div class="textParagraph textCenter paddingLeft20 paddingRight20 paddingBottom20">
        <h5>Custom and Blank Presentation Folders, Certificate Holders for every business</h5>
        <p>When it comes to creating a professional presentation, Folders.com is the top destination. We make it as easy as possible for customers like you to find affordable folders and office supplies that don’t cut any corners on quality. We proudly supply an extensive selection of products for home and office use. We also offer an array of customization options and printing services that can help businesses both big and small stand out from the competition. Whether you are looking for a way to attract new clients to your accounting firm, make better-looking business proposals, or save on the supplies you already use, we have something for everyone in our selection. We also offer free shipping over $299!</p>
        <h6>Custom Products</h6>
        <div class="line"></div>
        <p>No one knows your business like you do. Help your brand get noticed by going custom with printed folders. By choosing us as your go-to source for personalized folders, you receive total creative freedom from start to finish. Our selection of customizable products includes a wide variety of legal-size folders, presentation folders, certificate holders, and so much more. Using our online design tool, you can upload your own custom artwork or company logo. If you have shopped with us before, you can even re-order with the same design or make additional changes to a previous project.</p>
        <p>We Also Offer Folder Printing Services: Embossing, Foil Stamping, Full-Color Printing, One-Color Printing, Spot Color Printing</p>
        <p>Can’t seem to find the specific solution you have been looking for? With a wide range of printing methods, paper weights, colors, and textures at our disposal, we can collaborate with you to create printed folders that are as utterly unique as your brand. Contact us to submit any special requests and receive a quote for the quantity you need.</p>
        <p>Don’t let the upcoming tax season stress you out. In The Tax Shop, you will find a vast selection of custom presentation folders as well as promotional folders that can help you project a professional image and establish your credibility. Need extra space to store a large volume of paperwork? Our extra-capacity expansion pockets may just do the trick. Custom certificate holders are perfect for companies, schools, and other organizations that take the time to recognize employees, students, or volunteers. Our custom key card holders are ideal for country clubs, hotels, and other venues. </p>
        <p>Want to make the most of your budget? Get a custom quote today! Simply describe your custom project, select a quantity that fits your needs, and submit your contact information to receive an estimate from one of our folder experts. If you need any help finding a specific product or would like to learn more about our presentation folder printing services, we are standing by to assist you in any way we can. Call, email, or chat with us online for further assistance.</p>
        <h6>Blank Products</h6>
        <div class="line"></div>
        <p>Shopping on a tight budget or in a rush to receive your materials? We offer a wide selection of blank products that eliminate the hassle of branding every piece of print collateral. Our most popular blank products include legal size, pocket, and presentation folders, but we also carry an assortment of blank certificate holders, card holders, and more.</p>
        <p><a href="<@ofbizUrl>/search?w=*&af=customizable:n</@ofbizUrl>">Browse All Blank In-Stock Products</a></p>
        <p>We guarantee to each and every customer: the highest quality customer service, the highest level of professionalism, pride in excellence and innovation, low minimum quantity and bulk prices and free shipping on ALL custom orders.</p>
    </div>
</div>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/util/bigNameCarousel.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>