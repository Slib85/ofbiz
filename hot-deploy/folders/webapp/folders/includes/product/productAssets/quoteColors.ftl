<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/util/bigNameToolTip.css</@ofbizContentUrl>" />
<div>
    <h5 data-bigNameValidateId="quoteOutsideColor" class="ftc-blue positionRelative">Exterior Covers &amp; Inner Pockets: <#--<i bns-bignametooltip title="this is a tooltip" class="fa fa-info-circle"></i>--></h5>
    <div class="selectListParent sidebarToggle jqs-sidebarToggle jqs-scrollToSelected" data-sidebar-name="sidebar-quoteOutsideColor" selection-selectlistname="quoteOutsideColor" name="quoteOutsideColor" data-bigNameValidate="quoteOutsideColor" data-bigNameValidateType="quoteRequestCustomization" data-bigNameValidateErrorTitle="Outside Color">
        <div class="foldersTabularRow folderDisplayTable">
            <div>Select a Printing Method</div>
        </div>
    </div>
</div> 
<#if product.getId() != "PDCL-8x10" && product.getId() != "PDCL-8.5x14" && product.getId() != "PDCL-8.5x11" && product.getId() != "PDCP-8.5x14">
<div>
    <h5 bns-hideforsample data-bigNameValidateId="quoteInsideColor" class="ftc-blue positionRelative">Inside Panels: <#--<i bns-bignametooltip class="fa fa-info-circle"></i>--></h5>
    <div bns-hideforsample class="selectListParent sidebarToggle jqs-sidebarToggle jqs-scrollToSelected" data-sidebar-name="sidebar-quoteInsideColor" selection-selectlistname="quoteInsideColor" name="quoteInsideColor" data-bigNameValidate="quoteInsideColor" data-bigNameValidateType="quoteRequestCustomization" data-bigNameValidateErrorTitle="Inside Color">
        <div class="foldersTabularRow folderDisplayTable">
            <div>Select a Printing Method</div>
        </div>      
    </div>
</div>  
</#if>

<div id="sidebar-quoteOutsideColor" class="sidebarPanel cornerList jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Exterior Covers &amp; Inner Pockets</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="colorTextureBody">
        <div class="colorTextureBodyInner" style="padding: 0;">
            <div class="selectListItem" bns-selection selection-target="quoteOutsideColor" selection-name="quoteOutsideColor" selection-value="None">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>None</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteOutsideColor" selection-name="quoteOutsideColor" selection-value="1 Color Printing">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>1 Color Printing</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteOutsideColor" selection-name="quoteOutsideColor" selection-value="2 Color Printing">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>2 Color Printing</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteOutsideColor" selection-name="quoteOutsideColor" selection-value="Full Color Printing">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>Full Color Printing</div>
                </div>
            </div>
            <#if !requestParameters.printingType?exists || requestParameters.printingType != "Printed Folders">
            <div class="selectListItem" bns-selection selection-target="quoteOutsideColor" selection-name="quoteOutsideColor" selection-value="Foil Stamped">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>Foil Stamped</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteOutsideColor" selection-name="quoteOutsideColor" selection-value="Embossing">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>Embossing</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteOutsideColor" selection-name="quoteOutsideColor" selection-value="Blind Embossed">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>Blind Embossed</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteOutsideColor" selection-name="quoteOutsideColor" selection-value="Foil and Embossed">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>Foil and Embossed</div>
                </div>
            </div>
            </#if>
        </div>
    </div>
</div>

<div bns-hideforsample id="sidebar-quoteInsideColor" class="sidebarPanel cornerList jqs-sampleReadableList jqs-scrollable">
    <div class="colorTextureHeading">
        <div class="stickyTextureHeading">
            <h4><i class="fa fa-times"></i>Inside Panels</h4>
        </div>
        <div class="foldersTabularRow"><div style="padding: 0;"></div></div>
    </div>
    <div class="colorTextureBody">
        <div class="colorTextureBodyInner" style="padding: 0;">
            <div class="selectListItem" bns-selection selection-target="quoteInsideColor" selection-name="quoteInsideColor" selection-value="None">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>None</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteInsideColor" selection-name="quoteInsideColor" selection-value="1 Color Printing">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>1 Color Printing</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteInsideColor" selection-name="quoteInsideColor" selection-value="2 Color Printing">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>2 Color Printing</div>
                </div>
            </div>
            <div class="selectListItem" bns-selection selection-target="quoteInsideColor" selection-name="quoteInsideColor" selection-value="Full Color Printing">
                <div class="foldersTabularRow folderDisplayTable">
                    <div selection-removeonselect><span class="selectCheckbox"></span></div>
                    <div>Full Color Printing</div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<@ofbizContentUrl>/html/js/util/bigNameToolTip.js</@ofbizContentUrl>"></script>