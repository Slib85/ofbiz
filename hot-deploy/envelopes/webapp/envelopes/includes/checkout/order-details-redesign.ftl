<#if pageType == 'cart' || pageType == 'checkout' || pageType == 'receipt'>
<script>
	var itemsData = $('<div/>').html('${orderInfo.cartStateData.cartItems?default('')}').text();
	myCartSubTotal = ${orderInfo.subTotal?default()};

	isCartEmpty = ${(orderInfo.lineItems?size == 0)?string('true', 'false')};
	if (isCartEmpty == true) {
		localStorage.removeItem('addToCartData');
		$().updateMiniCart();
	}
</script>
</#if>

<#if pageType="cart">
<div id="bnc_header_bar" class="summary-header margin-top-xxs tabular_row">
	<div class="cart-item-header">
		<span>Item</span>
	</div>
	<div class="customization-header">
		<span>Customization</span>
	</div>
	<div class="production-header">
		<span>Production Time</span>
	</div>
	<div class="price-header">
		<span>Quantity &amp; Price</span>
	</div>
	<#if pageType="order">
	<div class="reorder-header">
		<span>Reorder</span>
	</div>
	</#if>
</div>
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
		<#elseif lineItem.envArtworkAttributes.directMailJobId?has_content>
			<#assign projectId = lineItem.envArtworkAttributes.directMailJobId />
			<#assign editUrl = "/directMailingProduct?productId=" + lineItem.productId + '&jobNumber=' + projectId />
		</#if>

		<#if editUrl?has_content>
			<#assign prodUrl><@ofbizUrl>${editUrl}</@ofbizUrl>#${projectId}</#assign>
		<#else>
			<#assign prodUrl><@ofbizUrl>/product/~category_id=${lineItem.primaryProductCategoryId?default("null")}/~product_id=${lineItem.productId}</@ofbizUrl></#assign>
		</#if>
		<div class="margin-bottom-xs" data-item-comment="${lineItem.itemComment?if_exists}">
			<div class="line-item-header row no-margin">
				<#--  <span class="jqs-remove-item right h3" data-sku="${lineItem.productId}" data-index="${lineItem_index}"><i class="fa fa-times margin-left-xs margin-right-xxs"></i></span>  -->
				<span class="jqs-remove-item right h3" data-sku="${lineItem.productId}" data-index="${lineItem_index}"><i class="fa fa-trash-o"></i></span>
				<span data-index="${lineItem_index}" class="h3 right"></span>
			</div>
			<div class="jqs-line-item-row line-item-body row no-margin margin-top-xxs">
				<#--  <div class="item-image-container">
					<div class="item-image text-center">
						<a href="${prodUrl}">
							<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
								<img class="border-gray-1" src="<@ofbizUrl>/serveFileForStream?filePath=uploads/texel/designs/${lineItem.envArtworkAttributes.scene7DesignId}_front.png</@ofbizUrl>" alt="${lineItem.productName}" />
								<img class="border-gray-1 margin-top-xxs" src="<@ofbizUrl>/serveFileForStream?filePath=uploads/texel/designs/${lineItem.envArtworkAttributes.scene7DesignId}_back.png</@ofbizUrl>" alt="${lineItem.productName}" />
							<#else>
								<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha" alt="${lineItem.productName}" />
							</#if>
						</a>
					</div>
				</div>  -->
				<div class="item-info-container no-margin tabular_row">
					<div class="item-image-container">
						<div class="item-image">
							<a href="${prodUrl}">
								<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
									<img class="border-gray-1" src="<@ofbizUrl>/serveFileForStream?filePath=uploads/texel/designs/${lineItem.envArtworkAttributes.scene7DesignId}_front.png</@ofbizUrl>" alt="${lineItem.productName}" />
									<img class="border-gray-1 margin-top-xxs" src="<@ofbizUrl>/serveFileForStream?filePath=uploads/texel/designs/${lineItem.envArtworkAttributes.scene7DesignId}_back.png</@ofbizUrl>" alt="${lineItem.productName}" />
								<#else>
									<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha" alt="${lineItem.productName}" />
								</#if>
							</a>
						</div>
						<div class="item-name-container">
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
					</div>
					
					<div class="customization-container">
						<div class="caption mobile-only">Customization</div>
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
								<div class="proof-copy"><b>You will receive a proof within 1 business day</b></div>
							</#if>
							<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
								<div class="desktop-only">
									<div class="button-regular edit-item button-non-cta" data-index="${lineItem_index}" data-id="${lineItem.productId}" data-editUrl="${prodUrl}" data-projectid="${(lineItem.envArtworkAttributes.scene7ParentId)?if_exists}" data-isproduct="${(lineItem.envArtworkAttributes.isProduct)?if_exists}" data-designid="${(lineItem.envArtworkAttributes.designId)?if_exists}">Edit</div>
								</div>
							</#if>
						</#if>
					</div>
					<div class="production-container">
						<#--  <div class="production-time-container <#if plainItem>hidden</#if>">  -->
						<div class="production-time-container">
							<div class="caption mobile-only">Production Time</div>
							<#if plainItem || lineItem.productTypeId == "STAMP">
							<div class="printing">${lineItem.productionTime}</div>
							<#else>
							<span class="production-time">
								Standard (${lineItem.leadTime.leadTimeStandardPrinted} Days)
							</span>
							</#if>
						</div>
					</div>
					<div class="production-and-quantity">
						<div class="qty-price-container">
							<div class="caption mobile-only">Qty &amp; Price</div>
							<span class="quantity">
								<div class="envelope-select no-padding">
									<select class="jqs-quantity" name="quantity" data-index="${lineItem_index}">
										<#if lineItem.hasCustomQty?c == "true" && lineItem.productId != "SWATCHBOOK" && lineItem.productId != "LUX-SWATCHBOOK">
										<option value="custom">Custom Quantity </option>
										</#if>
										<#assign sampleQuantity = Static["com.envelopes.util.EnvConstantsUtil"].FREE_SAMPLES />
										<#assign hasSample = Static["com.envelopes.product.ProductHelper"].hasSample(delegator, lineItem.product, lineItem.productId) />
										<#list lineItem.envQuantityAttributes.entrySet() as entry>
										<#assign isSelected = (entry.key == lineItem.quantity?string)?string('selected ', '')/>
										<option <#if hasSample && entry.key?number lte sampleQuantity?number>data="sample"</#if> ${isSelected}value="${entry.key}">${entry.key?replace('\\B(?=(\\d{3})+(?!\\d))', ',', 'r')} Qty - <@ofbizCurrency amount=entry.value/></option>
										</#list>
									</select>
								</div>
								<#--  <#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
								<div class="mobile-only">
									<div class="button-regular edit-item button-non-cta">Edit</div>
								</div>
								</#if>  -->
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
						
					</div>
				</div>
			</div>
		</div>
		</#list>
		<#else>
		<div class="text-center no-items">
			<span style="display: block; color: #ff0000;">There are currently no items in your cart.</span>
			<div class="jqs-continue-shopping button-regular button-cta round-btn" style="padding: 15px 10px;">Continue Shopping</div>
		</div>
		</#if>
	</div>
	<#if isCartEmpty == "false">

		<#--Coupons, Shipping and Price Section-->
		<div class="coupons-shipping-price">
			<#--Coupons Section-->
				<div id="apply-coupon" class="coupons text-center">
					<form id="applyCouponForm" name="applyCouponForm" data-abide="ajax" style="position:relative">
					<div class="">
						<div class="caption h7b bnc_checkout_column_ui">Apply Coupon</div>
						<div class="coupon-code margin-top-xxs">
							<input class="jqs-abide" type="text" required="" data-abide-validator="validateAndApplyCouponCode" data-page-type="${pageType}" size="1" name="couponCode" placeholder="Coupon Code" />
							<div>
								<#--  <div id="jqs-apply-coupon" class="button-regular add-coupon button-non-cta jqs-submit">Submit</div>  -->
								<div id="jqs-apply-coupon" class="add-coupon jqs-submit">Submit</div>
							</div>
							<small style="margin-top: 15px" class="jqs-coupon-error error">Coupon Code is required.</small>
						</div>
					</div>
					</form>
				</div>
			<#--Coupons Section END-->
			<#--Shipping Section-->
				<div id="calculate-shipping" class="shipping text-center">
					<form id="shippingOptionsForm" name="shippingOptionsForm" data-bigNameValidateForm="shippingOptionsForm" onsubmit="return false;">
						<div>
							<div class="caption h7b bnc_checkout_column_ui">Shipping Method</div>
							<div class="row padding-top-xxs">
								<div class="small-12 medium-12 large-6 columns">
									<div class="padding-left-xxs horizontal-input">
										<div>
											<input id="ship_to_business" name="ship_to" type="radio" value="BUSINESS_LOCATION" <#if !orderInfo.shippingDetails.shipTo?has_content || (orderInfo.shippingDetails.shipTo?has_content && orderInfo.shippingDetails.shipTo == "BUSINESS_LOCATION")>checked</#if> />
										</div>
										<div>
											<span class="margin-left-xxs">
												<label for="ship_to_business">Business</label>
											</span>
										</div>
									</div>
								</div>
								<div class="small-12 medium-12 large-6 columns">
									<div class="padding-left-xxs horizontal-input">
										<div>
											<input id="ship_to_residential" name="ship_to" type="radio" value="RESIDENTIAL_LOCATION" <#if orderInfo.shippingDetails.shipTo?has_content && orderInfo.shippingDetails.shipTo == "RESIDENTIAL_LOCATION">checked</#if> />
										</div>
										<div>
											<span class="margin-left-xxs">
												<label for="ship_to_residential">Residential</label>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="small-6 medium-12 large-12 columns margin-top-xxs" data-bignamevalidateid="shipping_countryGeoId">
									<label class="envelope-select no-margin" data-bigNameValidateParent="shipping_countryGeoId">
										<select name="shipping_countryGeoId" data-bignamevalidate="shipping_countryGeoId" data-bignamevalidatetype="required" data-bignamevalidateerrortitle="Country">
											<option value="">Country</option>
											${screens.render("component://envelopes/widget/CommonScreens.xml#countries")}
										</select>
									</label>
									<script>
										$('[name=shipping_countryGeoId]').val('${orderInfo.shippingDetails.shippingCountryGeoId?default("USA")}');
									</script>
								</div>
								<div class="small-6 medium-12 large-12 columns margin-top-xxs" data-bignamevalidateid="shipping_postalCode">
									<input id="shipping-postal-code" name="shipping_postalCode" data-type="shipping" type="text" value="${orderInfo.shippingDetails.shippingPostalCode?default('')}" placeholder="Zip" data-bignamevalidate="shipping_postalCode" data-bignamevalidatetype="required postalCode" data-bignamevalidateerrortitle="Zip" data-bignamevalidateoptions="disallowSpaces" />
								</div>
							</div>
							<div>
								<div class="button-regular calculate-shipping button-non-cta jqs-submit" data-bigNameValidateSubmit="shippingOptionsForm" data-bigNameValidateAction="shippingOptionsFormSubmit">Calculate</div>
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
					<div class="caption h7b bnc_checkout_column_ui">Order Summary</div>
					<div class="row no-margin cart-price-info">
						<div class="right subtotals">
							<div><span class="block">Subtotal:</span><span class="price-color" id="cartSubTotal"><@ofbizCurrency amount=orderInfo.subTotal/></span></div>
							<div><span class="block">Sales Tax:</span><span class="price-color"><@ofbizCurrency amount=orderInfo.taxTotal/></span></div>
							<div><span class="block">Shipping:</span><span class="price-color"><#if shippingOptions.shipmentMethodTypeId?exists><@ofbizCurrency amount=orderInfo.shipTotal/><#else>--</#if></span></div>
							<#list orderInfo.discounts.entrySet() as entry>
								<div><span class="block">${entry.key}:</span><span class="block price-discounts"><@ofbizCurrency amount=entry.value/></span></div>
							</#list>
						</div>
					</div>
					<div class="row no-margin cart-price-total">
						<div class="h3b padding-top-xxs padding-bottom-xxs grandtotal">
							<span>Total:</span>
							<span class="price-color"><@ofbizCurrency amount=orderInfo.grandTotal/></span>
						</div>
						<div class="h5b text-right padding-top-xxs">
							<a href="<@ofbizUrl>/checkout</@ofbizUrl>"><div class="jqs-checkout button-regular checkout button-cta">Checkout</div></a>
						</div>
					</div>
				</div>
			<#--Price Summary END-->
		</div>
		<#--Coupons, Shipping and Price  Section-->
		
		<#--PRODUCT BLOCKS-->
			<#--  <div id="bnc_cart_products">
				<div class="bnc_cartbox_content tabular_row">
					<div class="bnc_cartProd">
						<div class="bnc_cart_holiday_item">
							<div class="ribbon bnc_holiday_product_name">
								<span class="ribbon-text">Letter to Santa Kit <br />(9 1/2 x 6 1/4) - Red/Green</span>
							</div>
							<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/SANTAKIT2_GROUP?wid=210&fmt=png-alpha</@ofbizScene7Url>">
							<div class="bnc_holiday_product_price">
								<div>$4.95</div>
							</div>
							<div bns-add_to_cart="SANTAKIT2" class="bnc_holiday_product_AddToCart">Add to Cart</div>
						</div>
					</div>
					<div class="bnc_cartProd">
						<div class="bnc_cart_holiday_item">
							<div class="ribbon bnc_holiday_product_name">
								<span class="ribbon-text">Letter to Santa Kit <br />(9 1/2 x 6 1/4) - Blue</span>
							</div>
							<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/SANTAKIT3_GROUP?wid=210&fmt=png-alpha</@ofbizScene7Url>">
							<div class="bnc_holiday_product_price">
								<div>$4.95</div>
							</div>
							<div bns-add_to_cart="SANTAKIT3" class="bnc_holiday_product_AddToCart">Add to Cart</div>
						</div>
					</div>
				</div>
			</div>  -->
			<#if swatchBook?exists>
				<div class="swatchbook jqs-cart-swatchbook tabular_row">
					<div class="swatchbookImage">
						<div class="swatchbookEllipse">
							<span>Ships Free!</span>
						</div>
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/swatchbook-cart-thumbnail?fmt=png-alpha&amp;wid=136</@ofbizScene7Url>">
					</div>
					<div class="swatchbookContent">
						<h5>The Swatchbook</h5>
						<p>Over 100 paper options at your fingertips.</p>
						<p>Gives designers, printers &amp; industry professional alike, the all-important ability to touch and feel the paper and cardstock before making key decisions.</p>
						<div class="swatchbookButton jqs-add-swatchbook tablet-desktop-only">Add to Cart</div>
					</div>
					<div class="swatchbookPrice">
						<h4><#list swatchBook.getPrices().keySet() as quantity><@ofbizCurrency amount=swatchBook.getPrices().get(quantity).get("price") /><#break></#list></h4>
					</div>
					<div class="swatchbookButton jqs-add-swatchbook mobile-only">Add to Cart</div>
				</div>
			</#if>
	</#if>

    <!--<#if (crossSells.size() > 0)>
    <div class="jqs-cross-sells section no-margin margin-top-sm other_interests" style="border: 2px solid #DADADA;">
        <div class="row">
            <div class="large-12 columns padding-left-md">
                <h2>Customers that purchased these items also purchased:</h2>
            </div>
        </div>
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
    </#if>-->
<#else>
	<div class="row margin-top-xxs summary-header navyblue-bckgrd">
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
						<#if webSiteId == 'folders' && pageType == 'checkout'>
							<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha" alt="${lineItem.productName}" />
						<#else> 
							<#if pageType == 'checkout'>
								<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
									<img class="border-gray-1" src="<@ofbizUrl>/serveFileForStream?filePath=uploads/texel/designs/${lineItem.envArtworkAttributes.scene7DesignId}_front.png</@ofbizUrl>" alt="${lineItem.productName}" />
									<img class="border-gray-1 margin-top-xxs" src="<@ofbizUrl>/serveFileForStream?filePath=uploads/texel/designs/${lineItem.envArtworkAttributes.scene7DesignId}_back.png</@ofbizUrl>" alt="${lineItem.productName}" />
								<#else>
									<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha" alt="${lineItem.productName}" />
								</#if>
							<#else>
								<#if lineItem.scene7DesignId?has_content>
									<img src="<@ofbizUrl>/serveFileForStream?filePath=uploads/texel/designs/${lineItem.scene7DesignId}_front.png</@ofbizUrl>" alt="${lineItem.productName}" />
									<img src="<@ofbizUrl>/serveFileForStream?filePath=uploads/texel/designs/${lineItem.scene7DesignId}_back.png</@ofbizUrl>" alt="${lineItem.productName}" />
								<#else>
									<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha" alt="${lineItem.productName}" />
								</#if>
							</#if>
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
				<#if request.getAttribute("showLineItemStatus")?exists && lineItem.statusId?has_content>
					<#if lineItem.statusId == "ITEM_CREATED" || lineItem.statusId == "ITEM_BACKORDERED" || lineItem.statusId == "ART_OUTSOURCED" || lineItem.statusId == "SENT_PURCHASE_ORDER" || lineItem.statusId == "ART_SYRACUSE" || lineItem.statusId == 'ART_GOOD_TO_PROOF' || lineItem.statusId == 'ART_PROD_CHECK' || lineItem.statusId == 'ART_PRINTED' || lineItem.statusId == 'ART_RDY_FOR_INTRNL_REVIEW'>
                        Status: Processing
					<#else>
                        Status: ${Static["com.envelopes.order.OrderHelper"].getStatusDesc(delegator, lineItem.statusId)}
						<#if pageType="order" && lineItem.trackingNumber?has_content>
							<br />
							<#assign trackingUrl = Static["com.envelopes.util.EnvUtil"].getTrackingURL(lineItem.trackingNumber)?if_exists />
							Tracking: <a target="_blank" href="${trackingUrl?if_exists}">${lineItem.trackingNumber}</a>
						</#if>
					</#if>
				</#if>
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
				<span>Sales Tax: <i class="fa fa-question-circle" data-reveal-id="salesTaxInfo"></i></span>
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

	<div id="salesTaxInfo" class="reveal-modal reveal-modal-limiter infoPopup" data-reveal>
		<div>
			<div class="padding-bottom-xxs popup-title">
				<h3 class="padding-left-xxs">Sales Tax Information</h3>
				<a class="close-reveal-modal"><i class="fa fa-times"></i></a>
			</div>
			<div class="infoPopupBody padding-xs">
				<p>
					*If you are tax-exempt, please checkout and send your tax-certificate to <a href="mailto:tax@<#if webSiteId?default("envelopes") == "ae">envelopes<#else>${webSiteId?default("envelopes")}</#if>.com">tax@<#if webSiteId?default("envelopes") == "ae">envelopes<#else>${webSiteId?default("envelopes")}</#if>.com</a>.  Once we have received your certificate and verify tax exemption, we will remove the tax from your order and mark your account tax-exempt.<br />
                    <strong>Applicable to the following states only:</strong><br />
                    ${Static["com.envelopes.util.EnvConstantsUtil"].TAXABLE_STATES?if_exists}
				</p>
			</div>
		</div>
	</div>

	<script>
		if (typeof $(document).foundation() != 'undefined') {
        	$(document).foundation();
        }
	</script>
</#if>

<#if pageType == 'cart'>
<script>
	//persistent cart assistance and release
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

    //close/hide swatchbook
    if(typeof(Storage) !== 'undefined' && (typeof localStorage.getItem('showSwatchBookInCart') == 'undefined' || (typeof localStorage.getItem('showSwatchBookInCart') != 'undefined' && localStorage.getItem('showSwatchBookInCart') == 'false'))) {
        $('.jqs-cart-swatchbook').addClass('hidden');
    }
    $('.jqs-close-swatchbook').on('click', function(e) {
        $(this).closest('.jqs-cart-swatchbook').addClass('hidden');
        if(typeof(Storage) !== 'undefined') {
            localStorage.setItem('showSwatchBookInCart', false);
        }
    });

	//analytics for cross sell
	$('.jqs-cross-sells').find('a').on('click', function (e) {
		e.preventDefault();
		GoogleAnalytics.trackEvent('Cart', 'Cross-Sell', $(this).attr('data-sku'));
		window.location.href = $(this).attr('href');
	});

	$('.jqs-add-swatchbook').on('click', function(e) {
		GoogleAnalytics.trackEvent('Cart', 'Cross-Sell', 'SWATCHBOOK');

		var attr = {};
		attr.add_product_id = 'SWATCHBOOK';
		attr.quantity = 1;
		attr.isProduct = true;

		$.ajax({
			type: 'POST',
			url: gAddToCartUrl,
			timeout: 5000,
			async: false,
			data: attr,
			cache: false
		}).done(function(data) {
			ga('ec:addProduct', {
				'id': attr.add_product_id,
				'name': 'SwatchBook',
				'category': '',
				'brand': '',
				'variant': 'Premium Envelopes & Paper',
				'price': '',
				'quantity': attr.quantity
			});
			ga('ec:setAction', 'add');
			ga('send', 'event', 'UX', 'click', 'add to cart');
			stringifiedCartData = JSON.stringify(data);
			$().updateMiniCart(stringifiedCartData);
			var crossSell = $('<form/>').attr('id', 'crossSell').attr('action', gCartUrl).attr('method', 'POST').append('<input type="hidden" name="lastVisitedURL" value="' + document.referrer + '" />').append('<input type="hidden" name="fromAddToCart" value="true" />');
			$('body').append(crossSell);
			$('#crossSell').submit();
		});
	});

	$("[bns-add_to_cart]").on("click", function() {
		var productId = $(this).attr("bns-add_to_cart");

		if (typeof productId !== "undefined") {
			$.ajax({
				type: 'POST',
				url: gAddToCartUrl,
				timeout: 5000,
				async: false,
				data: {
					"add_product_id": productId,
					"quantity": 1,
					"isProduct": true
				},
				cache: false
			}).done(function(data) {
				stringifiedCartData = JSON.stringify(data);
				$().updateMiniCart(stringifiedCartData);
				$(window).scrollTop(parseInt($("#jqs-order-summary").offset().top) - 50);
				location.reload();
			});
		}
	});

</script>
</#if>