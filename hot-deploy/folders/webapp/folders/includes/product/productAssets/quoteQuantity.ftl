<div class="foldersRow">
	<h5 class="noPaddingTop" data-bigNameValidateId="quantity">Quantities</h5>
    <span bns-loadsamples class="pullRight cursorPointer hidden">Order Samples</span>
</div>
<div class="selectListParent sidebarToggle jqs-sidebarToggle jqs-scrollToSelected" data-sidebar-name="sidebar-quoteQuantitySelection" selection-selectlistname="quoteQuantitySelection" selection-multiselect="4" name="quantity" data-bigNameValidate="quantity" data-bigNameValidateType="quoteRequestCustomization" data-bigNameValidateErrorTitle="Quantity">
    <div class="foldersTabularRow folderDisplayTable">
        <div><span bns-multiselectvaluelocation >Select Quantity</span></div>
    </div>
</div>
<div id="sidebar-quoteQuantitySelection" class="sidebarPanel cornerList jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Select Up To 4 Quantities</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="colorTextureBody">
        <div class="colorTextureBodyInner" style="padding: 0;">
            <#if product.getId() == "SF-101" || product.getId() == "08-96" || product.getId() == "08-96-FOIL">
                <div class="selectListItem" bns-selection selection-target="quoteQuantitySelection" selection-name="quoteQuantitySelection" selection-value="50">
                    <div class="foldersTabularRow folderDisplayTable">
                        <div selection-removeonselect><span class="selectCheckbox"></span></div>
                        <div><span bns-multiselectvaluelocation>50</span> Qty</div>
                    </div>
                </div>
                <div class="selectListItem" bns-selection selection-target="quoteQuantitySelection" selection-name="quoteQuantitySelection" selection-value="100">
                    <div class="foldersTabularRow folderDisplayTable">
                        <div selection-removeonselect><span class="selectCheckbox"></span></div>
                        <div><span bns-multiselectvaluelocation>100</span> Qty</div>
                    </div>
                </div>
            </#if>
            <#if product.getId() != 'CKH-2709'>
            <div class="selectListItem" bns-selection selection-target="quoteQuantitySelection" selection-name="quoteQuantitySelection" selection-value="250">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div><span bns-multiselectvaluelocation>250</span> Qty</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteQuantitySelection" selection-name="quoteQuantitySelection" selection-value="500">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div><span bns-multiselectvaluelocation>500</span> Qty</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteQuantitySelection" selection-name="quoteQuantitySelection" selection-value="750">
                <div class="foldersTabularRow folderDisplayTable">
                    <div data-removeonselect><span class="selectCheckbox"></span></div>
                    <div><span bns-multiselectvaluelocation>750</span> Qty</div>
                </div>
            </div>
            </#if>
            <div class="selectListItem" bns-selection selection-target="quoteQuantitySelection" selection-name="quoteQuantitySelection" selection-value="1000">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div><span bns-multiselectvaluelocation>1,000</span> Qty</div>
                </div>
            </div>
            <input bns-customquantity type="text" name="customQuantity" value="" placeholder="Enter Custom Quantity" />
        </div>
    </div>
</div>