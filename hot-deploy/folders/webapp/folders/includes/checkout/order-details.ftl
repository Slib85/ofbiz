<#if pageType == 'cart' || pageType == 'checkout' || pageType == 'receipt'>
	<script>
		var itemsData = $('<div/>').html('${orderInfo.cartStateData.cartItems?default('')}').text();
		$('.releaseCart').on('click', function(e) {
			$.ajax({
				type: 'POST',
				url: '/' + websiteId + '/control/releaseCart',
				data: {},
				dataType: 'text',
				async: false,
				cache: false
			}).done(function(text){
				window.location = gCartUrl;
			});
		});

		$(function() {
			//analytics for cross sell
			$('.jqs-cross-sells').find('a').on('click', function (e) {
				e.preventDefault();
				GoogleAnalytics.trackEvent('Cart', 'Cross-Sell', $(this).attr('data-sku'));
				window.location.href = $(this).attr('href');
			});
		});
	</script>
</#if>
<#if pageType="cart">
<#assign isCartEmpty = (orderInfo.lineItems?size == 0)?string('true', 'false') />
	<#assign hasPrintableInCart = false />
	<div class="line-items section-container text-size-md padding-left-xxs padding-right-xxs">
		<#if isCartEmpty == "false">
		<#list orderInfo.lineItems as lineItem>
		<#if (lineItem.printing?size > 0) >
			<#assign hasPrintableInCart = true />
		</#if>
		<#assign editUrl = "" />
		<#assign plainItem = lineItem.envPriceCalcAttributes.colorsBack?default(0)?c == "0" && lineItem.envPriceCalcAttributes.colorsFront?default(0)?c == "0" && lineItem.envPriceCalcAttributes.isFullBleed?default(false)?c != 'true' />
		<#if lineItem_index gt 0><hr class="no-margin margin-bottom-xxs" /></#if>

		<#if lineItem.envArtworkAttributes.scene7ParentId?has_content>
			<#assign projectId = lineItem.envArtworkAttributes.scene7ParentId />
			<#assign mainDesign = Static["com.envelopes.scene7.Scene7Helper"].getMainProjectDesign(delegator, lineItem.envArtworkAttributes.scene7ParentId)?if_exists />
			<#if lineItem.envArtworkAttributes.isProduct == "true">
				<#assign editUrl = "/product/~category_id=" + lineItem.primaryProductCategoryId?default("null") + "/~product_id=" + lineItem.productId />
			<#else>
				<#assign editUrl = "/product/~designId=" + mainDesign />
			</#if>
		</#if>

		<#if editUrl?has_content>
			<#assign prodUrl><@ofbizUrl>${editUrl}</@ofbizUrl>#${projectId}</#assign>
		<#else>
			<#assign prodUrl><@ofbizUrl>/product/~category_id=${lineItem.primaryProductCategoryId?default("null")}/~product_id=${lineItem.productId}</@ofbizUrl></#assign>
		</#if>
		<div class="margin-bottom-xs" data-item-comment="${lineItem.itemComment?if_exists}">
			<div class="line-item-header row no-margin">
				<span class="jqs-remove-item right h3" data-index="${lineItem_index}"><i class="fa fa-times margin-left-xs margin-right-xxs"></i></span>
				<span data-index="${lineItem_index}" class="h3 right"></span>
			</div>
			<div class="jqs-line-item-row line-item-body row no-margin">
				<div class="item-image-container">
					<div class="item-image text-center">
						<a href="${prodUrl}">
							<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
								<img class="border-gray-1" src="<@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${lineItem.envArtworkAttributes.scene7DesignId}&setWidth=100&setSide=0" alt="${lineItem.productName}" />
								<img class="border-gray-1 margin-top-xxs" src="<@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${lineItem.envArtworkAttributes.scene7DesignId}&setWidth=100&setSide=1" alt="${lineItem.productName}" />
							<#else>
								<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha" alt="${lineItem.productName}" />
							</#if>
						</a>
					</div>
				</div>
				<div class="row no-margin item-info-container">
					<div class="small-12 medium-4 large-3 columns">
						<div class="item-name h5b black"><a href="${prodUrl}"><#if lineItem.quantity lte 5 && lineItem.hasSample?exists && lineItem.hasSample != "N">Samples of </#if>${lineItem.productName}</a></div>
						<div class="item-size">${lineItem.productSize?if_exists}</div>
						<#if lineItem.productTypeId?exists && lineItem.productTypeId != "STAMP"><div class="item-color">${lineItem.productColor}</div></#if>
						<div class="sku">Item #: ${lineItem.productId}</div>
						<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
						<div class="tablet-only">
							<div class="jqs-edit-item button-regular edit-item button-non-cta" data-index="${lineItem_index}" data-id="${lineItem.productId}" data-editUrl="${prodUrl}" data-projectid="${(lineItem.envArtworkAttributes.scene7ParentId)?if_exists}" data-isproduct="${(lineItem.envArtworkAttributes.isProduct)?if_exists}" data-designid="${(lineItem.envArtworkAttributes.designId)?if_exists}">Edit</div>
						</div>
						</#if>
					</div>
					<div class="customization-container large-3 columns">
						<div class="caption h7b">Customization</div>
						<#if plainItem || lineItem.productTypeId == "STAMP">
							<div class="printing">None</div>
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
							<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
								<div class="desktop-only">
									<div class="button-regular edit-item button-non-cta" data-index="${lineItem_index}" data-id="${lineItem.productId}" data-editUrl="${prodUrl}" data-projectid="${(lineItem.envArtworkAttributes.scene7ParentId)?if_exists}" data-isproduct="${(lineItem.envArtworkAttributes.isProduct)?if_exists}" data-designid="${(lineItem.envArtworkAttributes.designId)?if_exists}">Edit</div>
								</div>
							</#if>
						</#if>
					</div>
					<div class="production-and-quantity small-12 medium-8 large-6 columns">
						<div class="qty-price-container">
							<div class="caption h7b">Qty &amp; Price</div>
							<span class="quantity">
								<div class="envelope-select no-padding">
									<select class="jqs-quantity" name="quantity" data-index="${lineItem_index}">
										<#if lineItem.productId != "SWATCHBOOK" && lineItem.productId != "LUX-SWATCHBOOK">
										<option value="custom">Custom Quantity </option>
										</#if>
										<#list lineItem.envQuantityAttributes.entrySet() as entry>
										<#assign isSelected = (entry.key == lineItem.quantity?string)?string('selected ', '')/>
										<option ${isSelected}value="${entry.key}">${entry.key?replace('\\B(?=(\\d{3})+(?!\\d))', ',', 'r')} Qty - <@ofbizCurrency amount=entry.value/></option>
										</#list>
									</select>
								</div>
								<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
								<div class="mobile-only">
									<div class="button-regular edit-item button-non-cta">Edit</div>
								</div>
								</#if>
								<#if lineItem.isCustomQuantity?c == 'true'>
									<input class="jqs-number" type="text" placeholder="Qty" name="customQuantity" data-index="${lineItem_index}" size="1" value="${lineItem.quantity}">
									<script>
										$('input[name=customQuantity]:eq(' + ${lineItem_index} +')').focus().caret(-1);
									</script>
								<#else>
									<input type="text" class="jqs-number hidden" placeholder="Qty" name="customQuantity" data-index="${lineItem_index}" size="1" value="">
								</#if>
							</span>
						</div>
						<div class="production-time-container <#if plainItem>hidden</#if>">
							<div class="caption h7b">Production Time</div>
							<span class="production-time">
								<div class="envelope-select no-padding">
									<select class="jqs-production-time" name="production_time" data-index="${lineItem_index}">
										<option ${(lineItem.productionTime == "Rush")?string('selected ', '')} value="Rush">Rush (${lineItem.leadTime.leadTimeRushPrinted} Days)</option>
										<option ${(lineItem.productionTime == "Standard")?string('selected ', '')}value="Standard">Standard (${lineItem.leadTime.leadTimeStandardPrinted} Days)</option>
									</select>
								</div>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		</#list>
		<#else>
		<div class="text-center no-items">
			<span style="display: block; color: #ff0000;">There are currently no items in your cart.</span>
			<div class="jqs-continue-shopping button-regular button-cta" style="padding: 15px 10px;">Continue Shopping</div>
		</div>
		</#if>
	</div>
	<#if isCartEmpty == "false">
	<#--Coupons, Shipping and Price Section-->
	<div class="coupons-shipping-price row">
	<#--Coupons Section-->
		<div id="apply-coupon" class="coupons text-center">
			<form id="applyCouponForm" name="applyCouponForm" data-abide="ajax" style="position:relative">
			<div class="section-container">
				<div class="caption h7b">Apply Coupon</div>
				<div class="coupon-code margin-top-xxs">
					<input class="jqs-abide" type="text" required="" data-abide-validator="validateAndApplyCouponCode" data-page-type="${pageType}" size="1" name="couponCode" placeholder="Coupon Code" />
					<div class="margin-left-xxs">
						<div id="jqs-apply-coupon" class="button-regular add-coupon button-non-cta jqs-submit">Submit</div>
					</div>
					<small style="margin-top: 15px" class="jqs-coupon-error error">Coupon Code is required.</small>
				</div>
			</div>
			</form>
		</div>
	<#--Coupons Section END-->
	<#--Shipping Section-->
		<div id="calculate-shipping" class="shipping text-center">
			<form id="shippingOptionsForm" name="shippingOptionsForm" data-abide="ajax">
			<div class="section-container">
				<div class="caption h7b">Select Shipping Method</div>
				<div class="postal-code">
					<input class="jqs-abide" type="text" size="1" name="postalCode" required="" data-abide-validator="validatePostalCode" value="${orderInfo.shippingDetails.shippingPostalCode?default('')}" placeholder="Zip Code"/>
					<div class="margin-left-xxs">
						<div class="button-regular calculate-shipping button-non-cta jqs-submit">Calculate</div>
					</div>
					<small style="margin-top: 15px" class="jqs-postal-code-error error">Postal Code is required.</small>
				</div>
				<div id="jqs-shipping-options">
					${screens.render("component://envelopes/widget/CheckoutScreens.xml#cart-shipping-options")}
				</div>
			</div>
			</form>
		</div>
	<#--Shipping Section END-->
	<#--Price Summary-->
		<div class="cart-price">
			<div class="row no-margin cart-price-info">
				<div class="right subtotals">
					<div><span class="block">Subtotal:</span><span class="block price-color" id="cartSubTotal"><@ofbizCurrency amount=orderInfo.subTotal/></span></div>
					<div><span class="block">Sales Tax:</span><span class="block price-color"><@ofbizCurrency amount=orderInfo.taxTotal/></span></div>
					<div><span class="block">Shipping:</span><span class="block price-color"><#if shippingOptions.shipmentMethodTypeId?exists><@ofbizCurrency amount=orderInfo.shipTotal/><#else>--</#if></span></div>
					<#list orderInfo.discounts.entrySet() as entry>
						<div><span class="block">${entry.key}:</span><span class="block price-discounts"><@ofbizCurrency amount=entry.value/></span></div>
					</#list>
				</div>
			</div>
			<div class="row no-margin cart-price-total">
				<div class="h3b text-right padding-top-xxs padding-bottom-xxs grandtotal">
					<span>Total:</span>
					<span class="price-color"><@ofbizCurrency amount=orderInfo.grandTotal/></span>
				</div>
				<div class="h5b text-right padding-top-xxs">
					<a href="<@ofbizUrl>/checkout</@ofbizUrl>"><div class="jqs-checkout button-regular checkout button-cta">Checkout</div></a>
				</div>
                <div class="h5b text-right ">
                    <a href="<@ofbizUrl>/checkout</@ofbizUrl>"><div class="button-regular payWithAmazon <#if hasPrintableInCart == true>hidden</#if>" style="padding: 15px 10px;"><i class="fa fa-amazon"></i> Pay with Amazon</div></a>
                </div>
			</div>
		</div>
	<#--Price Summary END-->
	</div>
	<#--Coupons, Shipping and Price  Section-->
	</#if>
    <#if (crossSells.size() > 0)>
    <div class="jqs-cross-sells section no-margin margin-top-sm other_interests" style="border: 2px solid #DADADA;">
        <div class="row">
            <div class="large-12 columns padding-left-md">
                <h2>Customers that purchased these items also purchased:</h2>
            </div>
        </div>
        <!-- Envelopes -->
        <div class="row padding-top-xxs padding-bottom-xxs">
            <div class="slideIt-container" style="height: 220px">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt text-left">
                    <div>
                        <#list crossSells as crossSell>
                            <div>
                                <a data-sku="${crossSell.getId()}" href="<@ofbizUrl>${crossSell.getUrl(false)?default("")?replace("&#x2f;", "/")?replace("&#x7e;", "~")?replace("&#x3d;", "=")}</@ofbizUrl>">
                                    <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${crossSell.getId()}?wid=100&amp;hei=100&amp;fmt=png-alpha" alt="${crossSell.getName()} - ${crossSell.getColor()}" />
                                    <p class="margin-top-xxs prod_name limit-text">${crossSell.getName()}</p>
                                    <p class="prod_color">${crossSell.getColor()}</p>
                                    <div class="prod_ratings">
                                        <div class="rating-${crossSell.getRating()}"></div>
                                        <span><#if crossSell.getReviews()?has_content>(${crossSell.getReviews().reviews?size} Reviews)<#else>&nbsp;</#if></span><br/>
                                        <div style="text-align: center"><div class="button-regular button-cta jqs-submit">Add to Cart</div></div>
                                    </div>
                                </a>
                            </div>
                        </#list>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
        </div>
    </div>
    </#if>
<#else>
	<div class="row margin-top-xxs padding-bottom-xxs summary-header">
		<div class="small-12 medium-<#if pageType="order">4<#else>5</#if> large-4 columns cart-item-header">
			<span>Item</span>
		</div>
		<div class="small-12 medium-<#if pageType="order">3<#else>4</#if> large-<#if pageType="order">2<#else>3</#if> columns customization-header">
			<span>Customization</span>
		</div>
		<div class="small-12 medium-12 large-2 columns production-header">
			<span>Production Time</span>
		</div>
		<div class="small-12 medium-3 large-3 columns price-header">
			<span>Quantity &amp; Price</span>
		</div>
		<#if pageType="order">
		<div class="small-12 medium-2 large-1 columns reorder-header">
			<span>Reorder</span>
		</div>
		</#if>
	</div>
	<div class="summary-body">
		<#list orderInfo.lineItems as lineItem>
		<div class="row padding-top-xxs padding-bottom-xxs <#if pageType == 'order'><#if lineItem?index % 2 == 0>even<#else>odd</#if></#if>">
			<div class="small-12 medium-<#if pageType="order">4<#else>5</#if> large-4 columns cart-item">
				<div>
					<div class="text-center item-image">
						<#if pageType == 'checkout'>
							<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
								<img class="border-gray-1" src="<@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${lineItem.envArtworkAttributes.scene7DesignId}&setWidth=100&setSide=0" alt="${lineItem.productName}" />
								<img class="border-gray-1" src="<@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${lineItem.envArtworkAttributes.scene7DesignId}&setWidth=100&setSide=1" alt="${lineItem.productName}" />
							<#else>
								<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha" alt="${lineItem.productName}" />
							</#if>
						<#else>
							<img src="<#if lineItem.scene7DesignId?has_content><@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${lineItem.scene7DesignId}&setWidth=100<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha</#if>" alt="${lineItem.productName}" />
						</#if>
					</div>
					<div class="padding-left-xs item-info">
						<span class="item-title"><#if lineItem.quantity lte 5 && lineItem.hasSample?exists && lineItem.hasSample != "N">Samples of </#if>${lineItem.productName}</span>
						<span>${lineItem.productSize?if_exists}</span>
						<span>${lineItem.productColor}</span>
						<span>Item #: ${lineItem.productId}</span>
						<span class="alternate-price">(Qty. ${lineItem.quantity}) - <@ofbizCurrency amount=lineItem.totalPrice/></span>
					</div>
				</div>
                <#if pageType == 'order' && lineItem.contents?has_content>
                <h6>Artwork:</h6>
                <div class="padding-left-xs">
                    <#list lineItem.contents as content>
                        <#if content.contentName?has_content && content.contentPurpose == 'OIACPRP_PDF'>
                            <div><a href="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=${content.contentPath}&downLoad=Y" class="design-name">${content.contentName}</a></div>
                        </#if>
                    </#list>
                </div>
                </#if>
			</div>
			<div class="small-12 medium-<#if pageType="order">3<#else>4</#if> large-<#if pageType="order">2<#else>3</#if> columns customization">
				<#if (lineItem.printing?size > 0)>
					<span>Printing:
						<#list lineItem.printing as printing><#if printing_index != 0>,</#if> ${printing}</#list>
					</span>
				<#else>
					<span>No Printing</span>
				</#if>

				<#if lineItem.addresses == 1>
					<span>Addressing: ${lineItem.addresses} Address</span>
				<#elseif lineItem.addresses gt 1>
					<span>Addressing: ${lineItem.addresses} Addresses</span>
				</#if>
				<#if lineItem.productTypeId?has_content && lineItem.productTypeId == "PAPER">
					<#if lineItem.cuts == 0>
						<span>No Cutting</span>
					<#else>
						<span>Cutting: ${lineItem.cuts}  Cuts</span>
					</#if>
					<#if lineItem.isFolded?c == 'true'>
						<span>Folded</span>
					<#else>
						<span>No Folding</span>
					</#if>
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
			</div>
			<div class="small-12 medium-12 large-2 columns production">
				<span>${lineItem.productionTime}</span>
			</div>
			<div class="small-12 medium-3 large-3 columns price">
				<span>${lineItem.quantity?string[",##0"]} Qty. - <@ofbizCurrency amount=lineItem.totalPrice/></span>
			</div>
			<#if pageType="order">
			<div class="small-12 medium-2 large-1 columns reorder">
				<span class="mobile-only-inline-block">Reorder: </span> <input type="checkbox" name="reorder" data-order-id="${orderInfo.orderId}" data-item-sequence-id="${lineItem.orderItemSeqId}" />
			</div>
			<#if lineItem.contents?has_content>
			<div id="${orderInfo.orderId}-${lineItem.orderItemSeqId}-reorder-options" class="small-12 medium-12 large-12 columns reorder_options hidden margin-top-xxs">
				<div>
					<#list lineItem.contents as content>
					<#if content.contentPath?ends_with('.png')>
						<img style="width:100px;border:1px solid #DADADA" src="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=${content.contentPath}&amp;downLoad=Y" alt="Product Image" />
					</#if>
					</#list>
				</div>
				<div>
					<div>
						<input type="radio" name="options-${orderInfo.orderId}-${lineItem.orderItemSeqId}" value="approved" class="jqs-proof-approved" id="with-no-change-${orderInfo.orderId}-${lineItem.orderItemSeqId}"><label for="with-no-change-${orderInfo.orderId}-${lineItem.orderItemSeqId}">Reorder with no Changes</label>
					</div>
					<div>
						<input type="radio" name="options-${orderInfo.orderId}-${lineItem.orderItemSeqId}" value="with-change" id="with-change-${orderInfo.orderId}-${lineItem.orderItemSeqId}"><label for="with-change-${orderInfo.orderId}-${lineItem.orderItemSeqId}">Reorder with Changes</label>
					</div>
				</div>
				<div><span class="jqs-artwork-comment-error error hidden margin-bottom-xs">Please enter the reorder comments</span><textarea class="jqs-artwork-comment hidden no-margin" name="artwork-comment-${orderInfo.orderId}-${lineItem.orderItemSeqId}" placeholder="Reorder Comments"></textarea></div>
			</div>
			</#if>
			</#if>
		</div>
		</#list>
		<div class="row price-list padding-bottom-xxs margin-top-xxs">
			<div class="right padding-left-xxs text-right">
				<span class="block price-color"><@ofbizCurrency amount=orderInfo.subTotal/></span>
				<span class="block price-color"><@ofbizCurrency amount=orderInfo.taxTotal/></span>
				<span class="block price-color"><#if pageType == 'checkout'><#if orderInfo.shippingDetails.shippingPostalCode?exists><@ofbizCurrency amount=orderInfo.shipTotal/><#else><a href="#calcShipping">Calculate Shipping</a></#if><#else><@ofbizCurrency amount=orderInfo.shipTotal/></#if></span>
                <#if pageType != 'checkout'>
                <#list orderInfo.manualFees as manualFee>
                    <#list manualFee.entrySet() as entry>
                        <span class="block price"><@ofbizCurrency amount=entry.value/></span>
                    </#list>
                </#list>
                </#if>
				<#list orderInfo.discounts.entrySet() as entry>
					<span class="block price-discounts"><@ofbizCurrency amount=entry.value/></span>
				</#list>
                <#if pageType != 'checkout'>
                <#list orderInfo.manualDiscounts as manualDiscount>
                    <#list manualDiscount.entrySet() as entry>
                        <span class="block price-discounts"><@ofbizCurrency amount=entry.value/></span>
                    </#list>
                </#list>
                </#if>
			</div>
			<div class="right text-right">
				<span>Subtotal:</span>
				<span>Sales Tax:</span>
				<span>Shipping:</span>
                <#if pageType != 'checkout'>
                <#list orderInfo.manualFees as manualFee>
                    <#list manualFee.entrySet() as entry>
                        <span>${entry.key?default('Fee')}</span>
                    </#list>
                </#list>
                </#if>
				<#list orderInfo.discounts.entrySet() as entry>
					<span>${entry.key?default('Discount')}</span>
				</#list>
                <#if pageType != 'checkout'>
                <#list orderInfo.manualDiscounts as manualDiscount>
                    <#list manualDiscount.entrySet() as entry>
                        <span>${entry.key?default('Discount')}</span>
                    </#list>
                </#list>
                </#if>
			</div>
		</div>
		<div class="row total-cost-and-coupon margin-top-xxs">
			<#if pageType == 'checkout'>
			<div class="coupon">
				<div class="row margin-left-xxs">
					<input name="couponCode" data-page-type="${pageType}" type="text" value="" placeholder="<#if orderInfo.enteredPromos?has_content>Applied ${orderInfo.enteredPromos}<#else>Enter Coupon Code</#if>" />
					<div id="jqs-apply-coupon" class="button-regular button-non-cta">Apply Code</div>
				</div>
			</div>
			</#if>
			<div class="total-cost">
				<span>
					Total: <span><@ofbizCurrency amount=orderInfo.grandTotal/> </span>
				</span>
			</div>
		</div>
	</div>
</#if>