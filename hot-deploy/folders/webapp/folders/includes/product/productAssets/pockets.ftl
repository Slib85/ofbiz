<#if pocketStyles?has_content>
    <#assign selectedPocketStyleDesc = "STANDARD" />

    <#if requestParameters.pocket_type?exists>
        <#assign selectedPocketStyleDesc = requestParameters.pocket_type?default("STANDARD")?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r") />
    </#if>

    <div class="productSidebarSection">
        <p class="textCenter">Pocket Shape</p>
        <div class="pcsSelection textCenter jqs-sidebarToggle" data-sidebar-name="sidebar-pocketList" selection-selectlistname="pocketSelection">
            <div class="foldersTabularRow folderDisplayTable">
                <div>
                    <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getParentId()?if_exists}_P-${selectedPocketStyleDesc}?wid=170&amp;hei=100" alt="Pocket Style">
                </div>
            </div>
        </div>
    </div>

    <div id="sidebar-pocketList" class="sidebarPanel pocketList jqs-sampleReadableList jqs-scrollable">
        <div class="colorTextureHeading">
            <div class="stickyTextureHeading">
                <h4><i class="fa fa-times"></i>Pocket Shape</h4>
            </div>
            <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
        </div>
        <div class="colorTextureBody">
            <div class="colorTextureBodyInner" style="padding: 0;">
                <#list pocketStyles.keySet() as featureId>
                    <#assign doogmaPStyle = pocketStyles.get(featureId).get("desc")?default("")?lower_case?replace("[^0-9a-z]", "", "r")?replace("x\\d{2}", "", "r") />
                    <#assign imageDescription = pocketStyles.get(featureId).get("desc")?default("")?upper_case?replace("[^0-9A-Z]", "", "r")?replace("X\\d{2}", "", "r") />

                    <div data-doogma data-doogma-key="pocketshape" data-doogma-value="${doogmaPStyle}" class="selectListItem" bns-selection selection-target="pocketSelection" selection-name="pocket" selection-value="${pocketStyles.get(featureId).get("desc")?upper_case}">
                        <div class="foldersTabularRow folderDisplayTable">
                            <div selection-removeonselect><span class="selectCheckbox"></span></div>
                            <div>
                                <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getParentId()?if_exists}_P-${imageDescription}?wid=170&amp;hei=100" alt="${imageDescription}">
                            </div>
                            <div selection-removeonselect>
                                <p><strong>${pocketStyles.get(featureId).get("desc")}</strong></p>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
    <script>
        $('#sidebar-pocketList [bns-selection]').off('click.updateAsset').on('click.updateAsset', function() {
            $('[bns-productassetinside]').trigger('click');
        });
    </script>
</#if>