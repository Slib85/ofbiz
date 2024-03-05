<script>
	<#if currentView == "product" && product?exists>
		dataLayer.push({
			'sku': '${product.getId()?if_exists}',
			'productName': '${product.getName()?default("")?js_string}',
			'colorName': '${product.getColor()?default("")?js_string}',
			'productType': '${product.getProductType()?default("ENVELOPE")}',
			'basePrice': '${product.getBasePrice()?string[",##0.##"]}'
		});

		GoogleAnalytics.trackProductView('${product.getId()?if_exists}', '${product.getName()?default("")?js_string}', '${product.getColor()?default("")?js_string}s');
	<#elseif currentView == "category">
		dataLayer.push({
			<#if category?exists && category.categoryName?has_content>'category': '${category.categoryName?html}',</#if>
			'productCategoryList': [
				<#if (widgetResponse.getPaginatedProducts((requestParameters.sort)?default(""))[(requestParameters.page)?default(0)?number])?exists && (widgetResponse.getPaginatedProducts((requestParameters.sort)?default(""))[(requestParameters.page)?default(0)?number])?has_content>
					<#assign products = widgetResponse.getPaginatedProducts((requestParameters.sort)?default(""))[(requestParameters.page)?default(0)?number]/>
					<#list products as product><#list product.productVariants as productVariant>'${productVariant.productVariantId?if_exists}'<#if productVariant_has_next>,</#if></#list><#if product_has_next>,</#if></#list>
				</#if>
            ]
		});
	<#elseif currentView == "search">
		<#if (widgetResponse.getProducts())?exists && (widgetResponse.getProducts())?has_content>
			dataLayer.push({
				'productSearchList': [
					<#list widgetResponse.getProducts() as productVariants><#list productVariants.productVariants as product>'${product.productVariantId?if_exists}'<#if product_has_next>,</#if></#list><#if productVariants_has_next>,</#if></#list>
				]
			});
		</#if>
	<#elseif currentView == "cross-sell">

	<#elseif currentView == "cart">
		<#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
		dataLayer.push({
			'products': [
				<#list orderInfo.lineItems as lineItem>
					{
						'name': '${lineItem.productName?js_string?replace("&#x2f;", "/")}',
						'id': '${lineItem.productId}',
						'price': ${lineItem.unitPrice?if_exists},
						'quantity': ${lineItem.quantity?default("")?replace(",","")}
					}<#if lineItem_has_next>,</#if>
				</#list>
			]
 		});
		</#if>
	<#elseif currentView == "checkout">
		<#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
			dataLayer.push({
				'isFromBronto': ${sessionAttributes.isFromBronto?default(false)?c}
			});
		</#if>

		<#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
			<#list orderInfo.lineItems as lineItem>
				ga('ec:addProduct', {
					'id': '${lineItem.productId}',
					'name': '${lineItem.productName?js_string}',
					'category': '',
					'brand': '',
					'variant':  '',
					'price': ${lineItem.unitPrice?if_exists},
					'quantity': ${lineItem.quantity?if_exists}
				});
			</#list>
			ga('ec:setAction','checkout', {
				'step': 1,
				'option': "Checkout Loaded"
			});
			ga('send', 'event', 'UX', 'click', 'checkout');
		</#if>
	<#elseif currentView == "receipt" && orderInfo?has_content>
		<#if orderInfo.isPrinted?exists && orderInfo.isPrinted?c == "true">
			<#assign estShipDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(8, true) />
			<#assign estDeliveryDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(13, true) />
		<#else>
			<#assign estShipDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(6, true) />
			<#assign estDeliveryDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(11, true) />
		</#if>

		dataLayer.push({
			'isFromBronto': ${sessionAttributes.orderPartyId?default(false)?c},
			'userId': '${orderInfo.email?if_exists?replace("&#x40;","@")}',
			'billingName': '${orderInfo.billingAddress.name?if_exists}',
			'billingFirstName': '${orderInfo.billingAddress.firstName?if_exists}',
			'billingLastName': '${orderInfo.billingAddress.lastName?if_exists}',
			'shippingName': '${orderInfo.shippingAddress.name?if_exists}',
			'shippingFirstName': '${orderInfo.shippingAddress.firstName?if_exists}',
			'shippingLastName': '${orderInfo.shippingAddress.lastName?if_exists}',
			'orderId': '${orderInfo.orderId?if_exists}',
			'orderDate': '${orderInfo.orderDate?if_exists}',
			'orderDateS': ${orderInfo.orderDateS?if_exists},
			'orderTotal': ${orderInfo.grandTotal?if_exists},
			'taxTotal': ${orderInfo.taxTotal?if_exists},
			'shipTotal': ${orderInfo.shipTotalWithDiscount?if_exists},
			'subTotal': ${orderInfo.subTotal?if_exists},
			'coupon': '${orderInfo.coupons?if_exists}',
			'plainOrPrinted': '<#if orderInfo.isPrinted?has_content && orderInfo.isPrinted?c == "true">Printed<#else>Plain</#if>',
            'estShipDate': '${estShipDate?if_exists?string("yyyy-MM-dd")}',
            'estDeliveryDate': '${estDeliveryDate?if_exists?string("yyyy-MM-dd")}',
			'products': [
				<#list orderInfo.lineItems as lineItem>
					{
						'name': '${lineItem.productName?js_string}',
						'id': '${lineItem.productId}',
						'price': ${lineItem.unitPrice?if_exists},
						'brand': '',
						'category': '',
						'variant': '',
						'quantity': ${lineItem.quantity?default("")?replace(",","")},
						'coupon': ''
					}<#if lineItem_has_next>,</#if>
				</#list>
			]
		});
	</#if>
</script>

<#-- MANUAL PIXELS -->
<#if currentView == "receipt" && orderInfo?has_content>
	<script>
		var orderTotal = ${orderInfo.grandTotal?if_exists};
	</script>

	<#-- RKG CONVERSION PIXEL -->
	<#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
		<#list orderInfo.lineItems as lineItem>
			<#assign centPrice = (lineItem.totalPrice?default(0)?number*100) />
			<img src="https://www.rkdms.com/order.gif?mid=envelopes&oid=${orderInfo.orderId?if_exists}&lid=${lineItem_index+1}&iid=${lineItem.productId?if_exists}&icent=${centPrice?if_exists}&iqty=1&iname=${lineItem.productName?if_exists}&ts=${orderInfo.orderDate?string?replace(":", "")?replace(" ", "")?replace("-", "")?replace(".", "")}<#if sessionAttributes.isFromBronto?default(false)?string('true','false') == 'true'>&cid=Email</#if>" height="1" width="1">
		</#list>
	</#if>

	<!-- START Google Customer Reviews Order -->
	<#if orderInfo.isPrinted?exists && orderInfo.isPrinted?c == "true">
		<#assign estShipDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(8, true) />
		<#assign estDeliveryDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(13, true) />
	<#else>
		<#assign estShipDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(6, true) />
		<#assign estDeliveryDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(11, true) />
	</#if>

	<script src="https://apis.google.com/js/platform.js?onload=renderOptIn" async defer></script>
	<script>
		window.renderOptIn = function() {
			window.gapi.load("surveyoptin", function() {
				window.gapi.surveyoptin.render({
					'merchant_id': 7998167,
					'order_id': '${orderInfo.orderId?if_exists}',
					'email': '${orderInfo.email?if_exists?replace("&#x40;","@")?replace("&#64;","@")}',
					'delivery_country': 'US',
					'estimated_delivery_date': '${estDeliveryDate?if_exists?string("yyyy-MM-dd")}',
					'opt_in_style': 'BOTTOM_RIGHT_DIALOG',
                    'products': [<#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content><#list orderInfo.lineItems as lineItem>{'gtin': ${(lineItem.upc)?default("")}}<#if lineItem_has_next>,</#if></#list></#if>]
				});
			});
		}
	</script>
	<!-- END Google Customer Reviews Order -->
</#if>

<#-- GOOGLE REMARKETING ALL PAGES -->
<script type="text/javascript">
	/* <![CDATA[ */
    var google_tag_params = {
		<#if currentView == "main">
			ecomm_pagetype: 'home'
		<#elseif currentView == "product" && product?exists>
			ecomm_pagetype: 'product',
			ecomm_prodid: '${product.getId()?if_exists}'
		<#elseif currentView == "cart">
			<#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
				ecomm_pagetype: 'cart',
				ecomm_prodid: [<#list orderInfo.lineItems as lineItem>'${lineItem.productId}'<#if lineItem_has_next>,</#if></#list>]
			</#if>
		<#elseif currentView == "receipt" && orderInfo?has_content>
			<#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
                ecomm_pagetype: 'purchase',
                ecomm_prodid: [<#list orderInfo.lineItems as lineItem>'${lineItem.productId}'<#if lineItem_has_next>,</#if></#list>]
			</#if>
		<#elseif currentView == "category">
			<#if (widgetResponse.getPaginatedProducts((requestParameters.sort)?default(""))[(requestParameters.page)?default(0)?number])?exists && (widgetResponse.getPaginatedProducts((requestParameters.sort)?default(""))[(requestParameters.page)?default(0)?number])?has_content>
                ecomm_pagetype: 'category',
                ecomm_prodid: [
					<#if (widgetResponse.getPaginatedProducts((requestParameters.sort)?default(""))[(requestParameters.page)?default(0)?number])?exists && (widgetResponse.getPaginatedProducts((requestParameters.sort)?default(""))[(requestParameters.page)?default(0)?number])?has_content>
						<#assign products = widgetResponse.getPaginatedProducts((requestParameters.sort)?default(""))[(requestParameters.page)?default(0)?number]/>
						<#list products as product><#list product.productVariants as productVariant>'${productVariant.productVariantId?if_exists}'<#if productVariant_has_next>,</#if></#list><#if product_has_next>,</#if></#list>
					</#if>
				]
			</#if>
		</#if>
    };
    var google_conversion_id = 1072728590;
    var google_conversion_label = "RAy2CInpngQQjpTC_wM";
    var google_custom_params = window.google_tag_params;
    var google_remarketing_only = true;
	/* ]]> */
</script>
<script type="text/javascript" src="https://www.googleadservices.com/pagead/conversion.js" async></script>
<noscript>
	<div style="display:inline;">
		<img height="1" width="1" style="border-style:none;" alt="" src="https://googleads.g.doubleclick.net/pagead/viewthroughconversion/1072728590/?value=0&amp;label=RAy2CInpngQQjpTC_wM&amp;guid=ON&amp;script=0"/>
	</div>
</noscript>

<#-- Google Customer Reviews Badge -->
<!-- BEGIN: Google Customer Reviews -->
<script src="https://apis.google.com/js/platform.js?onload=renderBadge" async defer></script>
<script>
    window.___gcfg = { lang: 'en_US' };
</script>
<!-- END: Google Customer Reviews Badge -->

<!--
###################################
###### BEGIN BRONTO TRACKING ######
###################################
-->
<#macro clearCategory>
	<script type="text/javascript">
		document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
						 '&fn=Public_DirectUpdateForm&id=aiozqsmfewzrdbqssaelkglzqucbbbp' +
						 '&field1=InterestedCategory,set,' +
						 '" width="0" height="0" border="0" alt=""/>');
	</script>
</#macro>

<#macro cartContents>
	<#if orderInfo?has_content && orderInfo.lineItems?has_content && orderInfo.lineItems?size gt 0>
		<#assign productCounter = 0 />
		<#list orderInfo.lineItems as cartLine>
			<#assign productId = cartLine.productId />
			<#assign productCounter = productCounter + 1 />
			<#assign brontoCartProd = "&field1=CartProdSKU${productCounter},set,${productId}&field2=CartProdName${productCounter},set,${cartLine.productName?default('')?replace('#','')}&field3=CartProdQty${productCounter},set,${cartLine.quantity?if_exists}&field4=CartProdPrice${productCounter},set,${cartLine.totalPrice?default(0)}" />
			<script type="text/javascript">
				document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update&fn=Public_DirectUpdateForm&id=aiozqsmfewzrdbqssaelkglzqucbbbp' +
								 '${brontoCartProd?if_exists}" width="0" height="0" border="0" alt=""/>');
			</script>
		</#list>
		<#assign productCounter = productCounter + 1 />
		<#if productCounter lte 5>
			<@cartEmptyContents num=productCounter />
		</#if>

		<#-- DATA FOR GOOGLE SHOPPING -->
		<script>
			var shoppingSkus = [<#list orderInfo.lineItems as cartLine>'${cartLine.productId}-${cartLine.quantity?if_exists}'<#if cartLine_has_next>,</#if></#list>];
		</script>
	</#if>
</#macro>

<#macro cartEmptyContents num=1>
	<script type="text/javascript">
		<#list num..5 as x>
			document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update&fn=Public_DirectUpdateForm&id=aiozqsmfewzrdbqssaelkglzqucbbbp&field1=CartProdSKU${x},set,&field2=CartProdName${x},set,&field3=CartProdImage${x},set,&field4=CartProdQty${x},set,&field5=CartProdPrice${x},set," width="0" height="0" border="0" alt=""/>');
		</#list>
	</script>
</#macro>


<#if currentView == "category" && category?exists && category.categoryName?has_content>
	<#-- we are updating the InterestedProduct / InterestedCategory  -->
	<script type="text/javascript">
		document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
						 '&fn=Public_DirectUpdateForm&id=aiozqsmfewzrdbqssaelkglzqucbbbp' +
						 '&field1=InterestedCategory,set,${category.categoryName?html}' +
						 '" width="0" height="0" border="0" alt=""/>');
	</script>
<#elseif currentView.equals("cart")>
	<@cartContents />
	<#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
		<@clearCategory />
		<#-- we are setting the ItemInCart to Yes so that we retarget -->
		<script style="border-style:none;" type="text/javascript">
			document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
							 '&fn=Public_DirectUpdateForm&id=aiozqsmfewzrdbqssaelkglzqucbbbp' +
							 '&field1=ItemInCart,set,Yes' +
							 '&field2=TotalCartValue,set,${orderInfo.subTotal?if_exists?string("0.##")}' +
							 '" width="0" height="0" border="0" alt=""/>');
		</script>
	<#else>
		<#-- we are setting the ItemInCart to N/A because the cart is showing up empty, possibly because they removed all their items  -->
		<script type="text/javascript">
			document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
							 '&fn=Public_DirectUpdateForm' +
							 '&id=aiozqsmfewzrdbqssaelkglzqucbbbp' +
							 '&field1=ItemInCart,set,' +
							 '&field2=InCheckOut,set,' +
							 '&field3=TotalCartValue,set,' +
							 '" width="0" height="0" border="0" alt=""/>');
		</script>
		<@cartEmptyContents />
	</#if>
<#elseif currentView.equals("checkout")>
	<@clearCategory />
	<@cartContents />
	<#-- we are setting the InCheckOut to Yes so that we retarget -->
	<script type="text/javascript">
		document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
						 '&fn=Public_DirectUpdateForm' +
						 '&id=aiozqsmfewzrdbqssaelkglzqucbbbp' +
						 '&field1=InCheckOut,set,Yes' +
						 '" width="0" height="0" border="0" alt=""/>');
	</script>
</#if>

<!--
###################################
####### END BRONTO TRACKING #######
###################################
-->