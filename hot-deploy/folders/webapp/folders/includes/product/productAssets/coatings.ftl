<h5 bns-coatingselection class="hidden">Paper Coating (Free of Charge)</h5>
<div bns-coatingselection class="selectListParent sidebarToggle jqs-sidebarToggle printMethodSelection jqs-scrollToSelected hidden" data-sidebar-name="sidebar-coatingSelection" selection-selectlistname="coatingSelection">
    <div class="foldersTabularRow folderDisplayTable productColorSelection">
        <p class="textBold paddingLeft10">Matte Aqueous</p>
    </div>
</div>

<div id="sidebar-coatingSelection" class="sidebarPanel cornerList jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Select Paper Coating (Free of Charge)</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="colorTextureBody">
        <div class="colorTextureBodyInner" style="padding: 0;">
            <div class="selectListItem" bns-selection selection-target="coatingSelection" selection-name="coatingSide1" selection-value="AQUEOUS_GLOSS">
                <div class="foldersTabularRow folderDisplayTable productColorSelection">
                    <p class="textBold paddingLeft10">Gloss Aqueous</p>
                </div>
            </div>
            <#if productId?matches("^08\\-96\\-\\d{3}\\-C")?c == "false">
            <div class="selectListItem" bns-selection selection-target="coatingSelection" selection-name="coatingSide1" selection-value="AQUEOUS_MATTE">
                <div class="foldersTabularRow folderDisplayTable productColorSelection">
                    <p class="textBold paddingLeft10">Matte Aqueous</p>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="coatingSelection" selection-name="coatingSide1" selection-value="AQUEOUS_SATIN">
                <div class="foldersTabularRow folderDisplayTable productColorSelection">
                    <p class="textBold paddingLeft10">Satin Aqueous</p>
                </div>
            </div>
            </#if>
            <#--
            <div class="selectListItem" bns-selection selection-target="coatingSelection" selection-name="coatingSide1" selection-value="AQUEOUS_SOFT_TOUCH">
                <div class="foldersTabularRow folderDisplayTable productColorSelection">
                    <p class="textBold paddingLeft10">Soft Aqueous</p>
                </div>
            </div>
            -->
        </div>
    </div>
</div>