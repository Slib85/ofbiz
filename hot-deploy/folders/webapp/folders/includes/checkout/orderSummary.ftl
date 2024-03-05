<link href="<@ofbizContentUrl>/html/css/folders/include/checkout/orderSummary.css</@ofbizContentUrl>" rel="stylesheet" />
<div class="foldersContainerBody orderSummary">
	<div class="foldersTabularRow orderSummaryHeader">
		<div class="ftc-blue imageColumn">Item</div>
		<div class="ftc-blue infoColumn"></div>
		<div class="ftc-blue customizationColumn tabletDesktop-tableCell">Customization</div>
		<div class="ftc-blue productionTimeColumn desktop-tableCell">Production Time</div>
		<div class="ftc-blue quantityAndPriceColumn tabletDesktop-tableCell">Quantity &amp; Price</div>
	</div>
	<#if orderInfo?has_content>
		<#list orderInfo.lineItems as lineItem>
			<#if lineItem.envPriceCalcAttributes?has_content>
				<#assign plainItem = lineItem.envPriceCalcAttributes.colorsBack?default(0)?c == "0" && lineItem.envPriceCalcAttributes.colorsFront?default(0)?c == "0" && lineItem.envPriceCalcAttributes.isFullBleed?default(false)?c != 'true' />
			<#else>
				<#assign plainItem = lineItem.isPrinted?c == 'false' />
			</#if>
			<div class="foldersTabularRow orderSummaryItem">
				<div class="imageColumn">
                    <img src="<#if lineItem.scene7DesignId?has_content><@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${lineItem.scene7DesignId}&setWidth=90<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=90&amp;fmt=png-alpha</#if>" alt="${lineItem.productName}" />
				</div>
				<div class="infoColumn">
					<div class="ftc-blue itemName">${lineItem.productName}</div>
                    <div>${lineItem.productSize?if_exists}</div>
                    <div>${lineItem.productColor?if_exists}</div>
                    <div>Item #: ${lineItem.productId?if_exists}</div>
					<div class="mobile-block">(${lineItem.quantity} Qty.) - <@ofbizCurrency amount=(lineItem.quantity*lineItem.unitPrice) /></div>
				</div>
				<div class="customizationColumn tabletDesktop-tableCell">
					<#if plainItem>
                        None
					<#else>
                        <div class="printing"><#if (lineItem.printing?size > 0)>Printing:<#list lineItem.printing as printing><#if printing_index != 0>,</#if> ${printing}</#list><#else>No Printing</#if></div>
                        <div class="addressing">
							<#if lineItem.addresses == 1>
                                Addressing: ${lineItem.addresses} Address
							<#elseif lineItem.addresses gt 1>
                                Addressing: ${lineItem.addresses} Addresses
							</#if>
                        </div>
						<#if lineItem.productTypeId?has_content && lineItem.productTypeId == "PAPER">
                            <div class="cutting">
								<#if lineItem.cuts == 0>
                                    No Cutting
								<#else>
                                    Cutting: ${lineItem.cuts} Cuts
								</#if>
                            </div>
                            <div class="folding">
								<#if lineItem.isFolded?c == 'true'>
                                    Folded
								<#else>
                                    No Folding
								</#if>
                            </div>
						</#if>
						<#if lineItem.files?has_content>
                            <div class="files">
                                <span style="display: block;">Attached Files:</span>
								<#list lineItem.files as file>
									<#if file?has_content>
									${file}<br />
									</#if>
								</#list>
                            </div>
						</#if>
						<#if lineItem.envArtworkAttributes?has_content && lineItem.envArtworkAttributes.artworkSource?has_content && lineItem.envArtworkAttributes.artworkSource == "ART_UPLOADED">
                            You will receive a proof within 1 business day
						</#if>
						<#if lineItem.envArtworkAttributes?has_content && lineItem.envArtworkAttributes.scene7DesignId?has_content>
                            <div class="desktop-only">
                                <div class="button-regular edit-item button-non-cta" data-index="${lineItem_index}" data-id="${lineItem.productId}" data-editUrl="${prodUrl}" data-projectid="${(lineItem.envArtworkAttributes.scene7ParentId)?if_exists}" data-isproduct="${(lineItem.envArtworkAttributes.isProduct)?if_exists}" data-designid="${(lineItem.envArtworkAttributes.designId)?if_exists}">Edit</div>
                            </div>
						</#if>
					</#if>
				</div>
				<div class="productionTimeColumn desktop-tableCell">
					${lineItem.productionTime}

					<#if request.getAttribute("showLineItemStatus")?exists && lineItem.statusId?has_content>
						<br />
						<#if lineItem.statusId == "ITEM_CREATED">
						<#elseif lineItem.statusId == "ITEM_BACKORDERED">
							Status: Pending
						<#elseif lineItem.statusId == "ART_OUTSOURCED">
							Status: Printed
						<#elseif lineItem.statusId == "SENT_PURCHASE_ORDER">
							Status: Processing
						<#else>
							Status: ${Static["com.envelopes.order.OrderHelper"].getStatusDesc(delegator, lineItem.statusId)}
							<#if lineItem.trackingNumber?has_content>
								<br />
								<#assign trackingUrl = Static["com.envelopes.util.EnvUtil"].getTrackingURL(lineItem.trackingNumber)?if_exists />
								Tracking: <a target="_blank" href="${trackingUrl?if_exists}">${lineItem.trackingNumber}</a>
							</#if>
						</#if>
						<#if !lineItem.trackingNumber?has_content && lineItem.dueDate?has_content>
							<br /><br />
							<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
							<#if lineItem.dueDate?datetime lte now?datetime>
								Estimated Ship Date: ${now?date?string.medium}
							<#else>
								Estimated Ship Date: ${lineItem.dueDate?date?string.medium}
							</#if>
						</#if>
					</#if>
				</div>
				<div class="quantityAndPriceColumn tabletDesktop-tableCell">
                    ${lineItem.quantity} Qty. - <@ofbizCurrency amount=(lineItem.quantity*lineItem.unitPrice) />
				</div>
			</div>
		</#list>
	</#if>
	<div class="foldersRow orderSummarySubtotal">
		<div class="foldersRow orderPrices">
			<div class="pullRight">
				<div class="ftc-orange"><@ofbizCurrency amount=(orderInfo.subTotal)?default(0) /></div>
				<div class="ftc-orange"><@ofbizCurrency amount=(orderInfo.taxTotal)?default(0) /></div>
				<div class="ftc-orange"><@ofbizCurrency amount=(orderInfo.shipTotal)?default(0) /></div>
			</div>
			<div class="pullRight marginRight10">
				<div>Subtotal:</div>
				<div>Sales Tax:</div>
				<div>Shipping:</div>
			</div>
		</div>
		<div class="foldersRow orderCouponAndTotal paddingTop10 marginTop10">
			<div class="orderTotal">
				Total: <span class="ftc-orange"><@ofbizCurrency amount=(orderInfo.grandTotal)?default(0) /></span>
			</div>
			<#if currentView == "checkout">
			<div class="couponCodeContainer">
				<div class="foldersRow">
					<div>
						<input name="couponCode" type="text" value="" placeholder="Enter Coupon Code">
					</div>
					<div id="jqs-show-shipping" class="foldersButton fbc-darkGray marginLeft10">APPLY CODE</div>
				</div>
			</div>
			</#if>
		</div>
	</div>
</div>
