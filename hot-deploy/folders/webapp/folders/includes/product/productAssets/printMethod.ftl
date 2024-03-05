<h5>Print Method</h5>
<div class="selectListParent sidebarToggle jqs-sidebarToggle printMethodSelection jqs-scrollToSelected quantitySelection" data-sidebar-name="sidebar-printMethodSelection" selection-selectlistname="printMethodSelection">
    <div class="foldersTabularRow folderDisplayTable">
        <div class="iconImageContainer"><img src="<@ofbizContentUrl>/html/img/folders/icon/printingFoilStamping.png</@ofbizContentUrl>" alt="Foil Stamping"/></div>
        <div>
            <p class="textBold">Foil Stamping</p>
        </div>
    </div>
</div>
<h5 bns-spotColors class="hidden">Number of Ink Colors</h5>
<div bns-spotColors class="hidden">
    <input bns-radioselection id="spotColor1" type="radio" name="spotColor" value="1" checked />
    <label for="spotColor1" class="marginLeft5">1 Color</label>
    <input bns-radioselection id="spotColor2" class="marginLeft20" type="radio" name="spotColor" value="2" />
    <label for="spotColor2" class="marginLeft5">2 Color</label>
    <input bns-radioselection id="spotColor3" class="marginLeft20" type="radio" name="spotColor" value="3" />
    <label for="spotColor3" class="marginLeft5">3 Color</label>
</div>
<h5 bns-spotColors class="hidden">Enter Ink Color</h5>
<div bns-spotColors class="foldersFlexRow hidden">
    <input bns-textinput type="text" name="spotColorText1" class="padding10" style="border: 1px solid #e1e1e1; width: 30%;" placeholder="Color 1" />
    <input bns-textinput type="text" name="spotColorText2" class="padding10 hidden" style="border: 1px solid #e1e1e1; width: 30%;" placeholder="Color 2" />
    <input bns-textinput type="text" name="spotColorText3" class="padding10 hidden" style="border: 1px solid #e1e1e1; width: 30%;" placeholder="Color 3" />
</div>

<div id="sidebar-printMethodSelection" class="sidebarPanel cornerList jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Select Print Method</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="colorTextureBody">
        <div class="colorTextureBodyInner" style="padding: 0;">
            <div class="selectListItem<#if product.hasImprintMethod("FOIL")?c != 'true'> hiddenSoft</#if><#if product.getId()?exists && product.getId()?matches("^08-96-[^FOIL].*?$")> hidden</#if>" bns-imprintname="FOIL" bns-selection selection-target="printMethodSelection" selection-name="printMethod" selection-value="foilStamping">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="iconImageContainer"><img src="<@ofbizContentUrl>/html/img/folders/icon/printingFoilStamping.png</@ofbizContentUrl>" alt="Foil Stamping"/></div>
                    <div>
                        <p class="textBold">Foil Stamping</p>
                        <div selection-removeonselect>
                            Foil stamping is one of the methods we use to apply artwork to our paper folders
                            and padded certificate holder products. It's the best method available for decorating
                            colored paper. Artwork for foil stamping should be "line art" using only solid fills
                            and lines without half-tones or screen tints. The artwork is applied to the paper using
                            heat and pressure, and will generally leave a slight flat indentation of the foil into
                            the paper.
                        </div>
                    </div>
                </div>
            </div>
            <div class="selectListItem<#if product.hasImprintMethod("SPOT")?c != 'true'> hiddenSoft</#if><#if product.getId()?exists && (product.getId()?matches("^08-96-FOIL.*?$") || product.getId()?matches("^08-96-[^FOIL].*?$"))> hidden</#if>" bns-imprintname="SPOT" bns-selection selection-target="printMethodSelection" selection-name="printMethod" selection-value="PMS">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="iconImageContainer"><img src="<@ofbizContentUrl>/html/img/folders/icon/printingSpotColor.png</@ofbizContentUrl>" alt="Spot Color"/></div>
                    <div>
                        <p class="textBold">Spot Color Printing</p>
                        <div selection-removeonselect>
                            Uses premixed inks to create a design using fields of solid
                            color (and gradients of those colors). Can include metallic inks. Your quote
                            accounts for light ink coverage. After receiving artwork, we'll contact you
                            if your design calls for heavy coverage.
                        </div>
                    </div>
                </div>
            </div>
            <div class="selectListItem<#if product.hasImprintMethod("FOUR_COLOR")?c != 'true'> hiddenSoft</#if><#if product.getId()?exists && product.getId()?matches("^08-96-FOIL.*?$")> hidden</#if>" bns-imprintname="FOUR_COLOR" bns-selection selection-target="printMethodSelection" selection-name="printMethod" selection-value="FOUR_COLOR">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="iconImageContainer"><img src="<@ofbizContentUrl>/html/img/folders/icon/printingFullColor.png</@ofbizContentUrl>" alt="Full Color"/></div>
                    <div>
                        <p class="textBold">Four Color Process</p>
                        <div class="activeDescription" selection-removeonselect>
                            Full-color (or CMYK) printing blends cyan, magenta, yellow & black ink
                            during the print process for a full, flexible spectrum of color. Best for
                            color photography as well as designs that contain more than three inks.
                        </div>
                        <div class="inactiveDescription" selection-removeonselect>
                            Four Color Process is only available on White and Natural Stocks, not
                            colored stocks.  <span class="textBold">Select a White or Natural Paper Color to activate
                            this option.</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="selectListItem<#if product.hasImprintMethod("EMBOSS")?c != 'true'> hiddenSoft</#if><#if product.getId()?exists && (product.getId()?matches("^08-96-FOIL.*?$") || product.getId()?matches("^08-96-[^FOIL].*?$"))> hidden</#if>" bns-imprintname="EMBOSS" bns-selection selection-target="printMethodSelection" selection-name="printMethod" selection-value="embossing">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div class="iconImageContainer"><img src="<@ofbizContentUrl>/html/img/folders/icon/printingEmbossing.png</@ofbizContentUrl>" alt="Embossing"/></div>
                    <div>
                        <p class="textBold">Embossing</p>
                        <div selection-removeonselect>
                            Embossing is used to create a raised effect resulting in the image being
                            3-dimensional on the folder. Embossing without ink, so that the image is
                            raised but not colored, is called "blind embossing."
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>