<#list pocketStyles.keySet() as featureId>
    <#if pocketStyles.get(featureId).get("desc")?upper_case == requestParameters.pocketDescription?default("STANDARD")>
        <#assign featureIdForCardSlits = featureId />
        <#break />
    </#if>
</#list>
<#if featureIdForCardSlits?exists && pocketStyles.get(featureIdForCardSlits).get("assocs").get("CARD_SLITS")?exists>
    <span bns-pocketjs class="hidden">${pocketStylesJS}</span>
    <script>
        window.pocketFeatureId = '${featureIdForCardSlits}';
        window.productFeatureData = JSON.parse($('[bns-pocketjs]').text());
    </script>
    <div class="productSidebarSection jqs-sidebarToggle">
        <p class="textCenter">Card Slit Location</p>
        <div class="pcsSelection textCenter jqs-sidebarToggle" data-sidebar-name="sidebar-cardSlitsList" selection-selectlistname="cardSlitsSelection">
            <div class="foldersTabularRow folderDisplayTable">
                <div>
                    <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderTemplate-blank?wid=170&amp;hei=100" alt="Folder Template">
                </div>
            </div>
        </div>
    </div>

    <div id="sidebar-cardSlitsList" class="sidebarPanel cardSlitsList jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Card Slit Location</h4>
            </div>
            <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
        </div>
        <div class="colorTextureBody">
            <div class="colorTextureBodyInner" style="padding: 0;">
                <div class="cardSlitOptions">
                    <div data-doogma data-doogma-key="pocketslits" data-doogma-value="noneslit" class="selectListItem" bns-selection selection-target="cardSlitsSelection" selection-name="cardSlits" selection-value="">
                        <div class="foldersTabularRow folderDisplayTable">
                            <div selection-removeonselect><span class="selectCheckbox"></span></div>
                            <div>
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderTemplate-blank?wid=170&amp;hei=100" alt="Blank Folder Template">
                            </div>
                            <div selection-removeonselect>
                                <p><strong>None</strong></p>
                            </div>
                        </div>
                    </div>
                    <#list pocketStyles.get(featureIdForCardSlits).get("assocs").get("CARD_SLITS") as cardSlits>
                        <#assign doogmaSStyle = cardSlits.get("desc")?default("")?lower_case?replace("[^0-9a-z]", "", "r")?replace("x\\d{2}", "", "r") />
                        <#assign imageDescription = pocketStyles.get(featureIdForCardSlits).get("desc")?default("")?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r") + "_" + cardSlits.get("desc")?default("")?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r") />

                        <div data-doogma data-doogma-key="pocketslits" data-doogma-value="${doogmaSStyle}slit" class="selectListItem" bns-selection selection-target="cardSlitsSelection" selection-name="cardSlits" selection-value="${cardSlits.get("desc")}">
                            <div class="foldersTabularRow folderDisplayTable">
                                <div selection-removeonselect><span class="selectCheckbox"></span></div>
                                <div>
                                    <img bns-cardslitimage src="//actionenvelope.scene7.com/is/image/ActionEnvelope/CS-${product.getParentId()?if_exists}-${imageDescription}?wid=170&amp;hei=100" alt="${imageDescription}">
                                </div>
                                <div selection-removeonselect>
                                    <p><strong>${cardSlits.get("desc")}</strong> </p>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
    <script>
        $('#sidebar-cardSlitsList [bns-selection]').off('click.updateAsset').on('click.updateAsset', function() {
            $('[bns-productassetinside]').trigger('click');
        });
    </script>
</#if>