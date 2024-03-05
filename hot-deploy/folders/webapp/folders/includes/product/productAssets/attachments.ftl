
<div class="productSidebarSection attachments <#if product.allowTangStrips()?c == "false">hidden</#if>">
    <p class="textCenter ftc-blue textBold">More Folder Options</p>
    <div class="pcsSelection textCenter jqs-sidebarToggle noPadding" data-sidebar-name="sidebar-attachmentsList" selection-selectlistname="attachmentsSelection">
        <div class="foldersTabularRow folderDisplayTable">
            <div>
                <p><strong>None</strong></p>
            </div>
        </div>
    </div>
</div>

<div id="sidebar-attachmentsList" class="sidebarPanel cornerList jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>More Folder Options</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="colorTextureBody">
        <div class="colorTextureBodyInner" style="padding: 0;">
            <div data-doogma data-doogma-key="tangstrip" data-doogma-value="none" class="selectListItem" bns-selection selection-selected selection-target="attachmentsSelection" selection-name="attachments" selection-value="">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div selection-removeonselect>
                        <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderTemplate-blank?wid=89&amp;hei=100">
                    </div>
                    <div>
                        <p><strong>None</strong></p>
                    </div>
                </div>
            </div>
            <div data-doogma data-doogma-key="tangstrip" data-doogma-value="white" class="selectListItem" bns-selection selection-target="attachmentsSelection" selection-name="attachments" selection-value="TANGSTRIP_WHITE">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div selection-removeonselect>
                        <#--
                        <img class="hideOnSelectListItem" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderAttachmentTemplate-tang?wid=89&amp;hei=100" alt="Folder Template">
                        <img selection-removeonselect src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderAttachmentTemplate-tang-full?wid=170&amp;hei=100" alt="Folder Template">
                        -->
                        <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderAttachmentTemplate-tang-full?wid=170&amp;hei=100" alt="Folder Template">
                    </div>
                    <div>
                        <p><strong>White Tang Strip</strong></p>
                    </div>
                </div>
            </div>
            <div data-doogma data-doogma-key="tangstrip" data-doogma-value="colored" class="selectListItem" bns-selection selection-target="attachmentsSelection" selection-name="attachments" selection-value="TANGSTRIP_MATCHING">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div selection-removeonselect>
                    <#--
                    <img class="hideOnSelectListItem" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderAttachmentTemplate-tang?wid=89&amp;hei=100" alt="Folder Template">
                    <img selection-removeonselect src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderAttachmentTemplate-tang-full?wid=170&amp;hei=100" alt="Folder Template">
                    -->
                        <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/folderAttachmentTemplate-tang-full?wid=170&amp;hei=100" alt="Folder Template">
                    </div>
                    <div>
                        <p><strong>Matching Folder Colored Tang Strip</strong></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $('#sidebar-attachmentsList [bns-selection]').off('click.updateAsset').on('click.updateAsset', function() {
        $('[bns-productassetinside]').trigger('click');
    });
</script>