<#if productAttributes?has_content && productAttributes.selectionData?has_content && productAttributes.selectionData.paperColor?has_content && productAttributes.selectionData.pocket?has_content>
    <#assign artLocationImage = productAttributes.selectionData.paperColor?upper_case + "_" + productAttributes.selectionData.pocket?upper_case + "_FRONT" />
</#if>

<h5 class="jqs-designMethod">Choose your design method.</h5>
<div class="selectListParent sidebarToggle jqs-sidebarToggle jqs-scrollToSelected" data-sidebar-name="sidebar-designMethod" selection-selectlistname="designMethodSelection">
	<div class="foldersTabularRow folderDisplayTable">
		<div class="textBold">Online Designer</div>
	</div>
</div>

<div bns-hideFoilColorsContent="<#if productAttributes?has_content && productAttributes.selectionData.printMethod?default("") != "foilStamping">true</#if>">
    <#include "foilColors.ftl" />
</div>

<div id="dmOnlineDesigner" class="jqs-dmSection jqs-designLayout">
    <h5 class="jqs-designMethod jqs-onlineDesigner">Choose Art Location.</h5>
    <div class="jqs-onlineDesigner selectListParent sidebarToggle jqs-sidebarToggle jqs-scrollToSelected" data-sidebar-name="sidebar-editLayout" selection-selectlistname="editLayoutSelection">
        <div class="foldersTabularRow folderDisplayTable">
            <div class="hideOnSelectListItem artLocationImage">
                <div>
                    <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50" alt="${artLocationImage?if_exists}"/>
                    <img class="noMargin" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getParentId()}_topCenter15sq?wid=45&fmt=png-alpha" alt="Top Center"/>
                </div>
            </div>
            <div class="textBold">
                <div>Top Center</div>
            </div>
        </div>
    </div>

    <#-- POSSIBLE TEMP CODE -->
    <#-- This seems temporary, so we will just have this code here and remove when done. -->
    <h5 class="jqs-designMethod jqs-uploadDesign">Choose your art size.</h5>
    <div class="jqs-uploadDesign">
        <div class="foldersTabularRow folderDisplayTable">
            <label for="artSizeSmall">
                <input id="artSizeSmall" class="marginRight10" type="radio" name="artSize" value="15" checked />
                Small (Up to 15")
            </label>
            <label for="artSizeLarge">
                <input id="artSizeLarge" class="marginRight10" type="radio" name="artSize" value="36" />
                Large (Up to 36")
            </label>
        </div>
    </div>
    <script>
        $('[name="artSize"]').on('change', function() {
            productPage.getProducts()[productPage.activeProductIndex].setProductAttributes({'selectionData': {'imageSize': $(this).val()}});
            productPage.getProductPrice();
        });

        $('[name="artSize"][value="' + productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.artSize + '"]').prop('checked', 'true');
    </script>
    <#-- END TEMP CODE -->
    <div class="jqs-onlineDesigner">
        <div class="foldersRow">
            <div class="foldersColumn large6">
                <div bns-edittextbutton data-sidebar-name="sidebar-editText" class="foldersButton buttonBlue noMarginBottom padding10 fullWidth jqs-sidebarToggle"><i class="fa fa-file-text-o"></i> Edit Text</div>
            </div>
            <div class="foldersColumn large6">
                <div data-sidebar-name="sidebar-editImage" class="foldersButton buttonBlue noMarginBottom padding10 fullWidth jqs-sidebarToggle"><i class="fa fa-picture-o"></i> Image Help</div>
                <!-- <div class="marginTop10 fileUploadButton">
                    <div class="foldersButton buttonBlue fullWidth noMarginBottom">PROOF NOTE:</div>
                    <div class="uploadedFiles jqs-uploadedfiles noMargin relative">
                        <div class="marginTop10">After your file has been uploaded and your order is placed, you'll receive am emailed proof to approve within 1 business day.</div>
                    </div>
                </div> -->
            </div>
        </div>
        <div class="foldersRow">
            <div class="foldersColumn large12">
                <div class="fileUploadButton">
                    <div class="foldersButton buttonBlue fullWidth noMarginBottom">PROOF NOTE:</div>
                    <div class="uploadedFiles jqs-uploadedfiles noMargin relative">
                        <div class="marginTop10">After your file has been uploaded and your order is placed, you'll receive an emailed proof to approve within 1 business day.</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="jqs-emailArtworkLater hidden ">
    Email artwork to <a href="mailto:prepress@folders.com" target="_blank" class="textBold">prepress@folders.com</a>
</div>

<div id="dmUploadComplete" class="jqs-dmSection jqs-uploadDesign hidden">
	<h5 class="noBold marginTop20">You will receive a free design proof to approve before your order goes to print.</h5>
    <h5 class="noBold">We will <span>not charge your card</span> until your proof has been approved!</h5>
	<p class="contactText">Please contact us at 800.296.4321 with any questions.</p>
    <div class="marginTop20 fileUploadButton" data-options="uploadFile">
        <div data-bnreveal="startUpload" class="foldersButton buttonBlue fullWidth noMarginBottom"><i class="fa fa-upload"></i> Upload File</div>
        <div class="uploadedFiles hidden jqs-uploadedfiles noMargin relative"></div>
    </div>
    <textarea name="fileUploadComments" bns-textinput class="marginTop10" placeholder="Add Additional Comments..."></textarea>
	<p class="textCenter marginTop20 marginBottom10"><a bns-downloadtemplate class="downloadPdf" href="#">Download PDF Template</a></p>
    <p class="textCenter" style="font-size: 12px;">
        Please use the template provided for artwork placement.<br />
        If you can't use the template, we will reach out to you for placement after checkout.
    </p>
</div>
<div id="dmReuseArtwork" class="jqs-dmSection jqs-reuseArtwork hidden">
	<h5 class="marginTop20">Select Previous Artwork.</h5>
	<div class="folderDesignWindow" bns-previousartworkcontainer></div>
</div>
<div id="dmUseOurServices" class="jqs-dmSection jqs-designServices hidden">
    <h5 class="marginTop20">Submit Your Design Needs</h5>
    <input type="text" placeholder="Date Folders Needed By" id="datepicker">
    <textarea placeholder="Design Request"></textarea>
    <div data-bnreveal="startUpload" class="foldersButton buttonBlue fullWidth noMarginBottom">Upload File</div>
    <div class="uploadedFiles hidden jqs-uploadedfiles noMargin relative"></div>
</div>


<div id="sidebar-reuseLogin" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Login</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
        <div class="padding15">
            <h5>Log In to Select Previous Artwork</h5>
            <input type="email" class="marginTop20" placeholder="Email Address">
            <input type="password" placeholder="Password">
            <div class="foldersButton buttonGreen marginTop20 noMarginBottom padding10 fullWidth">Log In</div>
        </div>
    </div>
</div>

<div id="sidebar-editText" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Edit Text</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
        <div class="padding15">
            <textarea bns-textinput data-doogmainput data-doogma-key="textline1" name="doogmaTextLine1" class="marginTop10" type="email" placeholder="Your Text Here"></textarea>
            <div class="foldersTabularRow directionalPad">
                <label class="bigNameSelect">
                    <select data-doogmachange data-doogma-key="textline1font" name="font">
                        <option value="adobecaslonpro">Adobe Caslon Pro</option>
                        <option value="adobecaslonprobold">Adobe Caslon Pro Bold</option>
                        <option value="adobecaslonproitalic">Adobe Caslon Pro Italic</option>
                        <option value="adobefangsongstdr">Adobe Fangsong Std R</option>
                        <option value="adobegaramondpro">Adobe Garamond Pro</option>
                        <option value="adobegaramondprobold">Adobe Garamond Pro Bold</option>
                        <option value="adobegaramondproitalic">Adobe Garamond Pro Italic</option>
                        <option value="adobekaitistdr">Adobe Kaiti Std R</option>
                        <option value="adobemingstdl">Adobe Ming Std L</option>
                        <option value="adobemyungjostdm">Adobe Myungjo Std M</option>
                        <option value="adobesongstdl">Adobe Song Std L</option>
                        <option value="adstiennormal">Adstien Normal</option>
                        <option value="allura">Allura</option>
                        <option value="altehaasgrotesk">Alte Haas Grotesk</option>
                        <option value="ambient">Ambient</option>
                        <option value="applemyungjo">AppleMyungjo</option>
                        <option value="arnoprodisplay">Arno Pro Display</option>
                        <option value="arnprior">Arnprior</option>
                        <option value="babelsans">BabelSans</option>
                        <option value="babelsansitalic">BabelSans Italic</option>
                        <option value="bakersignetbt">BakerSignet BT</option>
                        <option value="batang">Batang</option>
                        <option value="batikregular">Batik Regular</option>
                        <option value="bauderiescriptssi">Bauderie Script SSi</option>
                        <option value="bauhaus93">Bauhaus 93</option>
                        <option value="bickhamscriptprobold">Bickham Script Pro Bold</option>
                        <option value="bickhamscriptproregular">Bickham Script Pro Regular</option>
                        <option value="birchstd">Birch Std</option>
                        <option value="blackchancery">BlackChancery</option>
                        <option value="brandongrotesqueregular">Brandon Grotesque Regular</option>
                        <option value="brushscriptstd">Brush Script Std</option>
                        <option value="cabletv">Cabletv</option>
                        <option value="calibri">Calibri</option>
                        <option value="calibribold">Calibri Bold</option>
                        <option value="calibriitalic">Calibri Italic</option>
                        <option value="calligrapher">Calligrapher</option>
                        <option value="cambria">Cambria</option>
                        <option value="cambriabold">Cambria Bold</option>
                        <option value="cambriaitalic">Cambria Italic</option>
                        <option value="candara">Candara</option>
                        <option value="celticbold">Celtic Bold</option>
                        <option value="cezanne">Cezanne</option>
                        <option value="chalkboard">Chalkboard</option>
                        <option value="chalkboardbold">Chalkboard Bold</option>
                        <option value="chaparralpro">Chaparral Pro</option>
                        <option value="chaucer">Chaucer</option>
                        <option value="chisel">Chisel</option>
                        <option value="chopinscript">ChopinScript</option>
                        <option value="comicsansms">Comic Sans MS</option>
                        <option value="consolas">Consolas</option>
                        <option value="constantia">Constantia</option>
                        <option value="copperplate">Copperplate</option>
                        <option value="corbel">Corbel</option>
                        <option value="cornerstone">Cornerstone</option>
                        <option value="couriernew">Courier New</option>
                        <option value="couriernewbold">Courier New Bold</option>
                        <option value="couriernewitalic">Courier New Italic</option>
                        <option value="crillee">Crillee</option>
                        <option value="d3archism">D3 Archism</option>
                        <option value="diploma">Diploma</option>
                        <option value="dreamorphans">Dream Orphans</option>
                        <option value="eccentricstd">Eccentric Std</option>
                        <option value="echelon">Echelon</option>
                        <option value="echelonitalic">Echelon Italic</option>
                        <option value="eleganza">Eleganza</option>
                        <option value="elisia">Elisia</option>
                        <option value="emblem">Emblem</option>
                        <option value="english">English</option>
                        <option value="franklingothicmedium">Franklin Gothic Medium</option>
                        <option value="freehand591">Freehand591</option>
                        <option value="fritzquad">Fritz-Quad</option>
                        <option value="garamondpremrpro">Garamond Premr Pro</option>
                        <option value="georgiaitalic">Georgia Italic</option>
                        <option value="geosanslight">GeosansLight</option>
                        <option value="giddyupstd">Giddyup Std</option>
                        <option value="goodfish">Goodfish</option>
                        <option value="gothamblack">Gotham Black</option>
                        <option value="handscript">HandScript</option>
                        <option value="helsinkilight">Helsinki Light</option>
                        <option value="hobostd">Hobo Std</option>
                        <option value="horatiodlig">HoratioDLig</option>
                        <option value="janeausten">JaneAusten</option>
                        <option value="labtop">Labtop</option>
                        <option value="lariat">Lariat</option>
                        <option value="latinosamba">LatinoSamba</option>
                        <option value="lettergothicstd">Letter Gothic Std</option>
                        <option value="lettergothicstdbold">Letter Gothic Std Bold</option>
                        <option value="lithosproregular">Lithos Pro Regular</option>
                        <option value="magicalwands">Magical Wands</option>
                        <option value="mesquitestd">Mesquite Std</option>
                        <option value="microgrammadbolext">MicrogrammaDBolExt</option>
                        <option value="microsoftsansserif">Microsoft Sans Serif</option>
                        <option value="minionpro" selected>Minion Pro</option>
                        <option value="mrseavesot">Mrs Eaves OT</option>
                        <option value="myriadpro">Myriad Pro</option>
                        <option value="myriadprobold">Myriad Pro Bold</option>
                        <option value="myriadproconditalic">Myriad Pro CondItalic</option>
                        <option value="nuevastdcond">Nueva Std Cond</option>
                        <option value="ocrastd">OCR A Std</option>
                        <option value="oratorstd">Orator Std</option>
                        <option value="plantagenetcherokee">Plantagenet Cherokee</option>
                        <option value="poorrichard">Poor Richard</option>
                        <option value="poplarstd">Poplar Std</option>
                        <option value="pushkin">Pushkin</option>
                        <option value="radial">Radial</option>
                        <option value="radiatedpancake">Radiated Pancake</option>
                        <option value="radii">Radii</option>
                        <option value="renaissance">Renaissance</option>
                        <option value="riesling">Riesling</option>
                        <option value="rosewoodstdregular">Rosewood Std Regular</option>
                        <option value="sabadoo">Sabadoo</option>
                        <option value="sablelion">Sable Lion</option>
                        <option value="saccule">Saccule</option>
                        <option value="sachem">Sachem</option>
                        <option value="sachembold">Sachem Bold</option>
                        <option value="sansation">Sansation</option>
                        <option value="sansationbold">Sansation Bold</option>
                        <option value="silomregular">Silom Regular</option>
                        <option value="steiner">Steiner</option>
                        <option value="stencilstd">Stencil Std</option>
                        <option value="t4cbeaulieux">T4C Beaulieux</option>
                        <option value="taglet">Tag LET</option>
                        <option value="tahoma">Tahoma</option>
                        <option value="tahomabold">Tahoma Bold</option>
                        <option value="timesnewroman">Times New Roman</option>
                        <option value="timesnewromanbold">Times New Roman Bold</option>
                        <option value="timesnewromanitalic">Times New Roman Italic</option>
                        <option value="trajanpro">Trajan Pro</option>
                        <option value="trajanprobold">Trajan Pro Bold</option>
                        <option value="tycho">Tycho</option>
                        <option value="verdana">Verdana</option>
                        <option value="verdanabold">Verdana Bold</option>
                        <option value="verdanaitalic">Verdana Italic</option>
                        <option value="virtue">Virtue</option>
                        <option value="vivacious">Vivacious</option>
                        <option value="walkwaybold">Walkway Bold</option>
                        <option value="walkwayoblique">Walkway Oblique</option>
                        <option value="walkwayrevoblique">Walkway RevOblique</option>
                        <option value="windsong">Windsong</option>
                    </select>
                </label>
            </div>
            <div class="foldersTabularRow directionalPad">
            <#--
                <div style="width: 55%; padding-right: 22px;">
                    <p>Text Position:</p>
                    <div class="foldersTabularRow">
                        <div style="width: 40%;">&nbsp;</div>
                        <div>
                            <div data-doogmaclick="" data-direction="up" data-doogma-key="textline1y" data-doogma-value="148" class="directionalButton directionalButtonLg"><i class="fa fa-caret-up"></i></div>
                        </div>
                        <div style="width: 40%;">&nbsp;</div>
                    </div>
                    <div class="foldersTabularRow">
                        <div style="width: 28%;">&nbsp;</div>
                        <div>
                            <div data-doogmaclick="" data-direction="left" data-doogma-key="textline1x" data-doogma-value="282" class="directionalButton directionalButtonLg"><i class="fa fa-caret-left"></i></div>
                        </div>
                        <div>
                            <div data-doogmaclick="" data-direction="right" data-doogma-key="textline1x" data-doogma-value="282" class="directionalButton directionalButtonLg"><i class="fa fa-caret-right"></i></div>
                        </div>
                        <div style="width: 28%;">&nbsp;</div>
                    </div>
                    <div class="foldersTabularRow">
                        <div style="width: 40%;">&nbsp;</div>
                        <div>
                            <div data-doogmaclick="" data-direction="down" data-doogma-key="textline1y" data-doogma-value="148" class="directionalButton directionalButtonLg"><i class="fa fa-caret-down"></i></div>
                        </div>
                        <div style="width: 40%;">&nbsp;</div>
                    </div>
                </div>
                -->
                <div style="vertical-align: top; width: 55%; padding-right: 22px;">
                    <p>Align Text:</p>
                    <div class="foldersTabularRow">
                        <div>
                            <div data-doogmaclick="" data-doogma-key="textline1alignment" data-doogma-value="left" class="directionalButton directionalButtonSm"><i class="fa fa-align-left"></i></div>
                        </div>
                        <div>
                            <div data-doogmaclick="" data-doogma-key="textline1alignment" data-doogma-value="center" class="directionalButton directionalButtonSm"><i class="fa fa-align-center"></i></div>
                        </div>
                        <div>
                            <div data-doogma="" data-doogma-key="textline1alignment" data-doogma-value="right" class="directionalButton directionalButtonSm"><i class="fa fa-align-right"></i></div>
                        </div>
                    </div>
                    <p>Adjust Font Size:</p>
                    <div class="foldersTabularRow">
                        <div>
                            <div data-doogmaclick="" data-doogma-key="textline1fontsize" data-direction="positive" data-doogma-value="36" class="directionalButton directionalButtonSm"><i class="fa fa-plus"></i></div>
                        </div>
                        <div>
                            <div data-doogma="" data-doogma-key="textline1fontsize" data-direction="negative" data-doogma-value="36" class="directionalButton directionalButtonSm"><i class="fa fa-minus"></i></div>
                        </div>
                        <div>
                            <div data-doogmacleartext class="directionalButton directionalButtonSm"><i class="fa fa-trash"></i></div>
                        </div>
                    </div>
                </div>
                <div></div>
            </div>
        ${screens.render("component://folders/widget/ProductScreens.xml#foilColorList")}
        <!-- <#include "foilColors.ftl" /> -->
            <#--
            <p class="textCenter marginTop10"><a class="addAdditional">+ Add Additional Text</a></p>
            -->
        </div>
    </div>
</div>

<div id="sidebar-editImage" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Image Help</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
        <div class="padding20">
            <div class="textCenter" style="font-size: 18px;">Depending On Your File Type,<br />There Are Two Different Upload Options</div>
            <div class="padding10 fbc-lightGray marginTop20">
                <h5 class="textCenter">Choose Your Upload Option</h5>
                <div style="font-size: 18px;" class="textCenter marginTop10">Option 1:</div>
                <div class="textBold textCenter fbc-white noMarginBottom padding10 fullWidth marginTop10" style="font-size: 17px; border: 2px solid #073b63;">
                    <i class="fa fa-file-image-o" style="font-size: 30px;"></i>
                    <br />
                    <span class="ftc-orange" style="font-size:18px">.PNG, .JPG Files</span>
                    <br />
                    Click the image icon <span class="textUnderline">on the folder</span> to upload
                    <br />
                    <span style="font-size: 13px; font-weight: 400;">These file-types are <span class="textBold" style="white-space: nowrap;">avail. for online preview</span></span>
                </div>
                <div style="font-size: 18px;" class="textCenter marginTop10">Option 2:</div>
                <div data-bnreveal="startUpload" class="foldersButton fbc-white padding10 fullWidth marginTop10" style="font-size: 17px; border: 2px solid #073b63;">
                    <i class="fa fa-cloud-upload" style="font-size: 30px;"></i>
                    <br />
                    <span class="ftc-lightBlue" style="font-size:18px">.EPS, .PDF, .AI Files</span>
                    <br />
                    Upload Artwork Here <i class="fa fa-caret-right" style="position: relative; font-size: 24px; top: 3px; width: initial; height: initial; padding: 0px;"></i>
                    <br />
                    <span style="font-size: 13px; font-weight: 400;">A proof will be <span class="textBold" style="white-space: nowrap;">emailed within 1 business day</span></span>
                </div>
            </div>
        </div>
        <#--
        <div class="dragAndDrop padding15">
            <i class="fa fa-file-image-o" aria-hidden="true"></i>
            <h5 class="textCenter">Drag and drop image file to add</h5>
            <h5 class="textCenter marginTop5">Browse Files</h5>
        </div>
        <div class="padding15 editImage" style="display: none;">
            <div class="foldersTabularRow directionalPad">
                <div>
                    <div class="foldersTabularRow">
                        <div class="directionalButton directionalButtonFile">filename.png</div>
                    </div>
                </div>
            </div>
            <h5 class="marginTop20">Size</h5>
            <div id="sizeSlider" class="marginTop20 marginBottom30"></div>
            <div class="foldersTabularRow directionalPad">
                <div style="width: 60%; padding-right: 22px; padding-bottom: 0;">
                    <h5>Move</h5>
                </div>
                <div style="padding-bottom: 0;">
                    <h5>Rotate</h5>
                </div>
            </div>
            <div class="foldersTabularRow directionalPad">
                <div style="width: 60%; padding-right: 22px;">
                    <div class="foldersTabularRow">
                        <div>&nbsp;</div>
                        <div>
                            <div class="directionalButton directionalButtonLg"><i class="fa fa-caret-up"></i></div>
                        </div>
                        <div>&nbsp;</div>
                    </div>
                    <div class="foldersTabularRow">
                        <div>
                            <div class="directionalButton directionalButtonLg"><i class="fa fa-caret-left"></i></div>
                        </div>
                        <div>
                            <div class="directionalButton directionalButtonLg"><i class="fa fa-caret-down"></i></div>
                        </div>
                        <div>
                            <div class="directionalButton directionalButtonLg"><i class="fa fa-caret-right"></i></div>
                        </div>
                    </div>
                </div>
                <div style="vertical-align: top;">
                    <div class="foldersTabularRow">
                        <div>
                            <div class="directionalButton directionalButtonSm"><i class="fa fa-undo"></i></div>
                        </div>
                        <div>
                            <div class="directionalButton directionalButtonSm"><i class="fa fa-repeat"></i></div>
                        </div>
                    </div>
                </div>
            </div>
            <p class="textCenter marginTop10"><a class="addAdditional">+ Add Additional Image</a></p>
        </div>
        -->
    </div>
</div>

<div id="sidebar-designMethod" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Select Design Method</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="colorTextureBody">
        <div class="colorTextureBodyInner" style="padding: 0;">
            <div class="selectListItem" bns-selection selection-target="designMethodSelection" selection-name="designMethod" selection-value="onlineDesigner">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="textBold">Online Designer</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="designMethodSelection" selection-name="designMethod" selection-value="uploadDesign">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="textBold">Upload Complete Design File</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="designMethodSelection" selection-name="designMethod" selection-value="reuseArtwork">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="textBold">Reorder</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="designMethodSelection" selection-name="designMethod" selection-value="designServices">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="textBold">Use our Design Services</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="designMethodSelection" selection-name="designMethod" selection-value="emailArtworkLater">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="textBold">Email Artwork Later<span selection-removeonselect> (prepress@folders.com)</span></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="sidebar-editLayout" class="sidebarPanel jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Choose Template Type</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
        <span bns-hiddencontent="editLayout" class="marginLeft10 hidden ftc-orange">Template Type:</span>
        <label bns-hiddencontent="editLayout" class="bigNameSelect hidden" style="width: auto;">
            <select bns-textinput name="filterArtLocation">
                <option value="image" selected>Image Only</option>
                <option value="imageAndText">Images and Text</option>
            </select>
        </label>
    </div>
    <div class="colorTextureBody">
        <div class="foldersRow textCenter marginTop20">
            <div class="templateTypeSelection">
                <p class="ftc-blue">Image Only</p>
                <div bns-templatetypesimplifier="image">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                    <img class="noMargin" style="position: absolute;top: 20px;left: 20px;max-width: calc(100% - 40px);" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter15sq-imageOnly?fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Top Center"/>
                </div>
            </div>
            <div class="templateTypeSelection">
                <p class="ftc-blue">Images and Text</p>
                <div bns-templatetypesimplifier="imageAndText">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}</@ofbizScene7Url>"  alt="${artLocationImage?if_exists}"/>
                    <img class="noMargin" style="position: absolute;top: 20px;left: 20px;max-width: calc(100% - 40px);" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter15sq?fmt=png-alpha&ts=3</@ofbizScene7Url>"  alt="Top Center"/>
                </div>
            </div>
        </div>
        <div bns-hiddencontent="editLayout" class="colorTextureBodyInner hidden" style="padding: 0;">
            <#if !product.getMaxImageSize()?exists || product.getMaxImageSize() gte 15>
            <div class="selectListItem" bns-filterartlocation-value="imageAndText" bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Top Center 15sq w/ Text">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter15sq?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Top Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter15sq?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Top Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Top Center (Small 15 Square Inches)</div>
                    </div>
                </div>
            </div>
            <div class="selectListItem" bns-filterartlocation-value="imageAndText" bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Center 15sq w/ Text">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage"> 
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_center15sq?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_center15sq?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Center (Small 15 Square Inches)</div>
                    </div>
                </div>
            </div>
            <div class="selectListItem" bns-filterartlocation-value="imageAndText" bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Bottom Center 15sq w/ Text">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_bottomCenter15sq?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Bottom Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_bottomCenter15sq?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Bottom Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Bottom Center (Small 15 Square Inches)</div>
                    </div>
                </div>
            </div>
            </#if>
            <#if !product.getMaxImageSize()?exists || product.getMaxImageSize() gte 36>
            <div class="selectListItem" bns-filterartlocation-value="imageAndText" bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Top Center 36sq w/ Text">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter36sq?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Top Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter36sq?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Top Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Top Center (Large 36 Square Inches)</div>
                    </div>
                </div>
            </div>
            <div class="selectListItem" bns-filterartlocation-value="imageAndText" bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Center 36sq w/ Text">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_center36sq?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_center36sq?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Center (Large 36 Square Inches)</div>
                    </div>
                </div>
            </div>
            <div class="selectListItem" bns-filterartlocation-value="imageAndText" bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Bottom Center 36sq w/ Text">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_bottomCenter36sq?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Bottom Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_bottomCenter36sq?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Bottom Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Bottom Center (Large 36 Square Inches)</div>
                    </div>
                </div>
            </div>
            </#if>
            <#if !product.getMaxImageSize()?exists || product.getMaxImageSize() gte 15>
            <div class="selectListItem" bns-filterartlocation-value="image" bns-filterartlocation bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Top Center 15sq">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter15sq-imageOnly?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Top Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter15sq-imageOnly?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Top Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Top Center (Small 15 Square Inches) - Image Only</div>
                    </div>
                </div>
            </div>
            <div class="selectListItem" bns-filterartlocation-value="image" bns-filterartlocation bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Center 15sq">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_center15sq-imageOnly?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_center15sq-imageOnly?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Center (Small 15 Square Inches) - Image Only</div>
                    </div>
                </div>
            </div>
            <div class="selectListItem" bns-filterartlocation-value="image" bns-filterartlocation bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Bottom Center 15sq">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_bottomCenter15sq-imageOnly?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Bottom Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_bottomCenter15sq-imageOnly?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Bottom Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Bottom Center (Small 15 Square Inches) - Image Only</div>
                    </div>
                </div>
            </div>
            </#if>
            <#if !product.getMaxImageSize()?exists || product.getMaxImageSize() gte 36>
            <div class="selectListItem" bns-filterartlocation-value="image" bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Top Center 36sq">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter36sq-imageOnly?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Top Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_topCenter36sq-imageOnly?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Top Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Top Center (Large 36 Square Inches) - Image Only</div>
                    </div>
                </div>
            </div>
            <div class="selectListItem" bns-filterartlocation-value="image" bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Center 36sq">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_center36sq-imageOnly?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_center36sq-imageOnly?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Center (Large 36 Square Inches) - Image Only</div>
                    </div>
                </div>
            </div>
            <div class="selectListItem" bns-filterartlocation-value="image" bns-selection selection-target="editLayoutSelection" selection-name="editLayout" selection-value="Bottom Center 36sq">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="hideOnSelectListItem artLocationImage">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=50</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_bottomCenter36sq-imageOnly?wid=45&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Bottom Center"/>
                        </div>
                    </div>
                    <div selection-removeonselect class="artLocationImageLarge">
                        <div>
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${artLocationImage?default("${product.getId()}_STANDARD_FRONT")}?wid=100</@ofbizScene7Url>" alt="${artLocationImage?if_exists}"/>
                            <img class="noMargin" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.getParentId()}_bottomCenter36sq-imageOnly?wid=90&fmt=png-alpha&ts=3</@ofbizScene7Url>" alt="Bottom Center"/>
                        </div>
                    </div>
                    <div class="textBold">
                        <div>Bottom Center (Large 36 Square Inches) - Image Only</div>
                    </div>
                </div>
            </div>
            </#if>
        </div>
    </div>
</div>

<script>
    if (typeof productPage != 'undefined') { productPage.loadReuseData(); }
    $('[bns-downloadtemplate]').attr('href', '/html/files/folders/templates/' + productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.vendorProductId + '.zip');
</script>