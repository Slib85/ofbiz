<h5>Foil Color</h5>
<div class="noPaddingLeft noPaddingBottom noPaddingTop selectListParent sidebarToggle jqs-sidebarToggle foilSelection jqs-scrollToSelected alternateColor" data-sidebar-name="sidebar-foilList" selection-selectlistname="foilSelection">
    <img class="productBackgroundColor" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/FC-GOLD_METALLIC?fmt=png-alpha" alt="Product Color">
    <div class="foldersTabularRow folderDisplayTable productColorSelection">
        <div class="textBold paddingLeft10" style="color: #ffffff;">Gold Metallic</div>
    </div>
</div>

<div id="sidebar-foilList" class="sidebarPanel foilList jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Foil Color</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="colorTextureBody">
        <div class="colorTextureBodyInner" style="padding: 0;">
            <#list foilColorList as foilColor>
                <#assign doogmaFoilColor = foilColor.name?default("")?lower_case?replace("[^0-9a-z]", "", "r")?replace("x\\d{2}", "", "r") />

                <div data-doogma data-doogma-key="foilcolor" data-doogma-value="${doogmaFoilColor}" class="noPaddingLeft noPaddingTop noPaddingBottom selectListItem" bns-selection selection-target="foilSelection" selection-name="foilColor" selection-value="${foilColor.name}">
                <img class="productBackgroundColor" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/FC-${foilColor.name?upper_case?replace(" ", "_")}?fmt=png-alpha" alt="${foilColor.name?replace(" ", "_")}"/>
                <div class="foldersTabularRow folderDisplayTable productColorSelection">
                    <div selection-removeonselect class="paddingLeft10 width30"><span class="selectCheckbox"></span></div>
                    <div class="textBold paddingLeft10" style="color: #${foilColor.textColor};">${foilColor.name}</div>
                </div>
            </div>
            </#list>
        </div>
    </div>
</div>