<div class="jqs-cartContentData" bns-hasquote="${hasQuote?c}" bns-hasprinted="${hasPrinted?c}">
    <script type="text/javascript">
        var itemsData = $('<div/>').html('${orderInfo.cartStateData.cartItems?default('')}').text();
        myCartSubTotal = ${orderInfo.subTotal?default()};

		if (typeof cartDataLayerObject == 'function') {
			cartDataLayerObject([
			<#if orderInfo?has_content>
				<#list orderInfo.lineItems as lineItem>
					{
						'name': '${lineItem.productName?js_string}',
						'id': '${lineItem.productId}',
						'price': ${lineItem.unitPrice?if_exists},
						'quantity': ${lineItem.quantity?default("")?replace(",","")}
					}<#if lineItem_has_next>,</#if>
				</#list>
			</#if>]);
		}
    </script>
	<#if orderInfo.cartStateData.cartItems?has_content>
	<#list orderInfo.lineItems as lineItem>

	<#assign editUrl = "" />
	<#assign plainItem = lineItem.envPriceCalcAttributes.colorsBack?default(0)?c == "0" && lineItem.envPriceCalcAttributes.colorsFront?default(0)?c == "0" && lineItem.envPriceCalcAttributes.isFullBleed?default(false)?c != 'true' />

	<#if lineItem.envArtworkAttributes.scene7ParentId?has_content>
		<#assign projectId = lineItem.envArtworkAttributes.scene7ParentId />
		<#assign editUrl = "/product/~category_id=" + lineItem.primaryProductCategoryId?default("null") + "/~product_id=" + lineItem.productId />
	</#if>

	<#if editUrl?has_content>
		<#assign prodUrl><@ofbizUrl>${editUrl}</@ofbizUrl>#${projectId}</#assign>
	<#else>
		<#assign prodUrl><@ofbizUrl>/product/~category_id=${lineItem.primaryProductCategoryId?default("null")}/~product_id=${lineItem.productId}</@ofbizUrl></#assign>
	</#if>

	<div class="foldersTabularRow productRow jqs-productRow" data-index="${lineItem.index}">
		<a <#if lineItem.productId == "CUSTOM-P">href="#" onclick="return false;"<#else>href="<@ofbizUrl>/product/~product_id=${lineItem.productId}</@ofbizUrl>"</#if> class="productImageColumn">
			<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha" alt="${lineItem.productName}" />
		</a>
		<div class="productInfoColumn">
			<div class="foldersTabularRow">
				<div class="productSpecColumn">
					<a <#if lineItem.productId == "CUSTOM-P">href="#" onclick="return false;"<#else>href="<@ofbizUrl>/product/~product_id=${lineItem.productId}</@ofbizUrl>"</#if> class="columnHeader ftc-blue"><#if lineItem.quantity lte 5 && lineItem.hasSample?exists && lineItem.hasSample != "N">Samples of </#if>${lineItem.productName}</a>
					<div>${lineItem.productSize?if_exists}</div>
					<div>${lineItem.productColor}</div>
					<div>Item #: ${lineItem.productId}</div>
				</div>
				<div class="productCustomizationColumn desktop-tableCell">
					<div class="columnHeader ftc-blue">Customization</div>
					<div class="columnResult">
					<#if plainItem || lineItem.productTypeId == "STAMP">
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
						<#if lineItem.addOns?has_content>
							<div style="white-space: pre;">${lineItem.addOns}</div>
						</#if>
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
						<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
							<div class="desktop-only">
								<div class="foldersButton edit-item" data-index="${lineItem_index}" data-id="${lineItem.productId}" data-editUrl="${prodUrl?if_exists}" data-projectid="${(lineItem.envArtworkAttributes.scene7ParentId)?if_exists}" data-isproduct="${(lineItem.envArtworkAttributes.isProduct)?if_exists}" data-designid="${(lineItem.envArtworkAttributes.designId)?if_exists}">Edit</div>
							</div>
						</#if>
					</#if>
					</div>
				</div>
				<div class="productQuantityAndPriceColumn">
					<div class="columnHeader ftc-blue">Qty &amp; Price</div>
					<div class="columnResult">
						<#if plainItem || lineItem.productTypeId == "STAMP">
						<label class="bigNameSelect marginTop10 autoWidth">
							<select class="jqs-quantityUpdate" name="quantity-${lineItem.productId}">
								<#list lineItem.envQuantityAttributes.keySet() as key>
									<#if (lineItem.productId != "CUSTOM-P" || (lineItem.productId == "CUSTOM-P" && key != "1")) && (!lineItem.productId?matches("^912\\-\\d+\\-C$") || (lineItem.productId?matches("^912\\-\\d+\\-C$") && lineItem.quantity?replace('\\.\\d+$', '', 'r') == key)) >
										<option value="${key}" data-price="${lineItem.envQuantityAttributes.get(key)}" <#if lineItem.quantity?replace('\\.\\d+$', '', 'r') == key>selected</#if>>Qty ${key} - <@ofbizCurrency amount=lineItem.envQuantityAttributes.get(key) /></option>
									</#if>
								</#list>
							</select>
						</label>
						<#else>
						Qty ${lineItem.quantity?replace('\\.\\d+$', '', 'r')} - <strike class="marginRight10"><@ofbizCurrency amount=lineItem.totalPrice / 0.85 /></strike> <span class="ftc-orange textBold"><@ofbizCurrency amount=lineItem.totalPrice /></span>
						</#if>
					</div>
				</div>
			</div>
		</div>
		<#--  <i class="fa fa-times removeProduct jqs-removeProduct" sku="${lineItem.productId}"></i>  -->
		<i class="fa fa-trash-o removeProduct jqs-removeProduct" sku="${lineItem.productId}"></i>
	</div>
	</#list>
	<#else>
	<div class="padding20 textCenter">
		<div class="ftc-green">There are currently no items in your cart.</div>
		<a href="<@ofbizUrl>/main</@ofbizUrl>" class="foldersButton buttonGreen marginTop10">Continue Shopping</a>
	</div>
	</#if>
</div>
<div class="jqs-orderPricesData">
	<div class="columnHeader ftc-blue">Order Summary</div>
	<div class="columnOrderSummary">
		<div class="FOL_orderSummaryContent">
			<div class="FOL_orderPriceText">Subtotal:</div>
			<div class="FOL_orderPriceText">Sales Tax:</div>
			<div class="FOL_orderPriceText">Shipping:</div>
			<#list orderInfo.discounts.entrySet() as entry>
				<div class="FOL_orderPriceText price-discounts">${entry.key}:</div>
			</#list>
			<div class="FOL_orderPriceText">Total:</div>
		</div>
		<div class="FOL_orderSummaryContent textRight">
			<div class="FOL_orderPriceNumbers ftc-orange subtotal"><@ofbizCurrency amount=(orderInfo.subTotal)?default(0) /></div>
			<div class="FOL_orderPriceNumbers ftc-orange"><@ofbizCurrency amount=(orderInfo.taxTotal)?default(0) /></div>
			<div class="FOL_orderPriceNumbers ftc-orange"><#if orderInfo.shipTotal?has_content><@ofbizCurrency amount=orderInfo.shipTotal /><#else>--</#if></div>
			<#list orderInfo.discounts.entrySet() as entry>
				<div class="FOL_orderPriceNumbers ftc-orange price-discounts"><@ofbizCurrency amount=(entry.value)?default(0) /></div>
			</#list>
			<div class="FOL_orderPriceNumbers ftc-orange"><@ofbizCurrency amount=(orderInfo.grandTotal)?default(0) /></div>
		</div>
	</div>
</div>
