<script>
    <#if currentView == "product" && product?exists>
        dataLayer.push({
            'sku': '${product.getId()?if_exists}',
            'productName': '${product.getName()?default("")?js_string}',
            'colorName': '${product.getColor()?default("")?js_string}',
            'productType': '${product.getProductType()?default("ENVELOPE")}'
        });

        GoogleAnalytics.trackProductView('${product.getId()?if_exists}', '${product.getName()?default("")?js_string}', '${product.getColor()?default("")?js_string}');
    <#elseif currentView == "category">
        <#if filters?has_content && filters.use?has_content>
            document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
            '&fn=Public_DirectUpdateForm&id=carezhefrxzfsfavjoinzwjhatzxbbg' +
            '&field1=InterestedCategory,set,<#list filters.use.get(0).keySet() as key>${key}<#break></#list>' +
            '" width="0" height="0" border="0" alt=""/>');
        </#if>
    <#elseif currentView == "search">
        <#if filters?has_content && filters.use?has_content>
            document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
            '&fn=Public_DirectUpdateForm&id=carezhefrxzfsfavjoinzwjhatzxbbg' +
            '&field1=InterestedCategory,set,<#list filters.use.get(0).keySet() as key>${key}<#break></#list>' +
            '" width="0" height="0" border="0" alt=""/>');
        </#if>
    <#elseif currentView == "cross-sell">

    <#elseif currentView == "cart">
        var cartDataLayerObject = function(orderInfo) {
            if (typeof orderInfo !== "undefined") {
                for (var i = 0; i < orderInfo.length; i++) {
                    if (typeof orderInfo[i].name !== "undefined") {
                        orderInfo[i].name = orderInfo[i].name.replaceAll("&#x2f;", "/");
                    }
                }
            }

            dataLayer.push({
                "products": undefined
            });

            dataLayer.push({
                "products": orderInfo,
                "event": "cartUpdate"
            });
        };
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
<#-- BRONTO CONVERSION PIXEL -->
<script type="text/javascript">
    document.write(unescape("%3Cscript src='" + ((document.location.protocol == "https:") ? "https:" : "http:") + "//p.bm23.com/bta.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script>
    var bta = new __bta('aiozqsmfewzrdbqssaelkglzqucbbbp');
    bta.setHost("app.bronto.com");
    bta.addConversion({ "order_id": "${orderInfo.orderId?if_exists}", "date": "${orderInfo.orderDate?if_exists?string("MM/dd/yyyy")}",
        "items": [
            <#escape field as field?js_string>
                <#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
                    <#list orderInfo.lineItems as lineItem>
                        { "item_id":"${lineItem.productId?if_exists}", "desc":"${lineItem.productName?if_exists}", "amount":"${lineItem.unitPrice?if_exists}", "quantity":"${lineItem.quantity?replace(",","")}" }<#if lineItem_has_next>,</#if>
                    </#list>
                </#if>
            </#escape>
        ]
    });
</script>

<#-- RKG CONVERSION PIXEL -->
    <#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
        <#list orderInfo.lineItems as lineItem>
            <#assign centPrice = (lineItem.totalPrice?default(0)?number*100) />
        <img src="https://www.rkdms.com/order.gif?mid=folders&oid=${orderInfo.orderId?if_exists}&lid=${lineItem_index+1}&iid=${lineItem.productId?if_exists}&icent=${centPrice?if_exists}&iqty=1&iname=${lineItem.productName?if_exists}&ts=${orderInfo.orderDate?string?replace(":", "")?replace(" ", "")?replace("-", "")?replace(".", "")}<#if sessionAttributes.isFromBronto?default(false)?string('true','false') == 'true'>&cid=Email</#if>" height="1" width="1">
        </#list>
    </#if>

<#-- GOOGLE TRUSTED STORES -->
<!-- START Google Trusted Stores Order -->
<div id="gts-order" style="display:none;" translate="no">
    <!-- start order and merchant information -->
    <span id="gts-o-id">${orderInfo.orderId?if_exists}</span>
    <span id="gts-o-domain">www.folders.com</span>
    <span id="gts-o-email">${orderInfo.email?if_exists}</span>
    <span id="gts-o-country">${orderInfo.shippingAddress.country?if_exists}</span>
    <span id="gts-o-currency">USD</span>
    <span id="gts-o-total">${orderInfo.grandTotal?if_exists}</span>
    <span id="gts-o-discounts">${orderInfo.totalDiscountAmount?if_exists}</span>
    <span id="gts-o-shipping-total">${orderInfo.shipTotal?if_exists}</span>
    <span id="gts-o-tax-total">${orderInfo.taxTotal?if_exists}</span>

    <#if orderInfo.isPrinted?exists && orderInfo.isPrinted?c == "true">
        <#assign estShipDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(8, true) />
        <#assign estDeliveryDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(13, true) />
    <#else>
        <#assign estShipDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(6, true) />
        <#assign estDeliveryDate = Static["com.envelopes.util.EnvUtil"].getNDaysBeforeOrAfterNow(11, true) />
    </#if>

    <span id="gts-o-est-ship-date">${estShipDate?if_exists?string("yyyy-MM-dd")}</span>
    <span id="gts-o-est-delivery-date">${estDeliveryDate?if_exists?string("yyyy-MM-dd")}</span>
    <span id="gts-o-has-preorder">N</span>
    <span id="gts-o-has-digital">N</span>
    <!-- end order and merchant information -->

    <!-- start repeated item specific information -->
    <!-- item example: this area repeated for each item in the order -->
    <#if (orderInfo.lineItems)?exists && (orderInfo.lineItems)?has_content>
        <#list orderInfo.lineItems as lineItem>
            <span class="gts-item">
					<span class="gts-i-name">${lineItem.productName?js_string}</span>
					<span class="gts-i-price">${lineItem.unitPrice?if_exists}</span>
					<span class="gts-i-quantity">${lineItem.quantity?default("")?replace(",","")}</span>
					<span class="gts-i-prodsearch-id">${lineItem.productId}</span>
					<span class="gts-i-prodsearch-store-id">115272316</span>
					<span class="gts-i-prodsearch-country">US</span>
					<span class="gts-i-prodsearch-language">en</span>
				</span>
        </#list>
    </#if>
    <!-- end item 1 example -->
    <!-- end repeated item specific information -->
</div>
<!-- END Google Trusted Stores Order -->
</#if>

<!--
###################################
###### BEGIN BRONTO TRACKING ######
###################################
-->
<#macro clearCategory>
<script type="text/javascript">
    document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
            '&fn=Public_DirectUpdateForm&id=carezhefrxzfsfavjoinzwjhatzxbbg' +
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
            document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update&fn=Public_DirectUpdateForm&id=carezhefrxzfsfavjoinzwjhatzxbbg' +
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
        document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update&fn=Public_DirectUpdateForm&id=carezhefrxzfsfavjoinzwjhatzxbbg&field1=CartProdSKU${x},set,&field2=CartProdName${x},set,&field3=CartProdImage${x},set,&field4=CartProdQty${x},set,&field5=CartProdPrice${x},set," width="0" height="0" border="0" alt=""/>');
        </#list>
</script>
</#macro>

<#if currentView == "category" && category?exists && category.categoryName?has_content>
<#-- we are updating the InterestedProduct / InterestedCategory  -->
<script type="text/javascript">
    document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
            '&fn=Public_DirectUpdateForm&id=carezhefrxzfsfavjoinzwjhatzxbbg' +
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
                '&fn=Public_DirectUpdateForm&id=carezhefrxzfsfavjoinzwjhatzxbbg' +
                '&field1=ItemInCart,set,Yes' +
                '&field2=TotalCartValue,set,${orderInfo.subTotal?if_exists?string("0.##")}' +
                '" width="0" height="0" border="0" alt=""/>');
    </script>
    <#else>
    <#-- we are setting the ItemInCart to N/A because the cart is showing up empty, possibly because they removed all their items  -->
    <script type="text/javascript">
        document.writeln('<img class="hidden" src="https://app.bronto.com/public/?q=direct_update' +
                '&fn=Public_DirectUpdateForm' +
                '&id=carezhefrxzfsfavjoinzwjhatzxbbg' +
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
            '&id=carezhefrxzfsfavjoinzwjhatzxbbg' +
            '&field1=InCheckOut,set,Yes' +
            '" width="0" height="0" border="0" alt=""/>');
</script>
</#if>

<!--
###################################
####### END BRONTO TRACKING #######
###################################
-->

<#--
########################################
######### BEGIN GCLID TRACKING #########
########################################
-->
<script type="text/javascript">
    function getParam(p){
        var match = RegExp('[?&]' + p + '=([^&]*)').exec(window.location.search);
        return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
    }
    var gclid = getParam('gclid');
    if(gclid){
        var gclsrc = getParam('gclsrc');
        if(!gclsrc || gclsrc.indexOf('aw') !== -1){
            localStorage['gclid'] = gclid;
        }
    }
</script>
<#--
########################################
########## END GCLID TRACKING ##########
########################################
-->

