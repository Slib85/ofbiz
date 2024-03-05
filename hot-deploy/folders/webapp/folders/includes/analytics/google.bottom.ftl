<#if currentView?default("_NA_") == "receipt" && orderInfo?has_content>
<!-- Google Analytics -->
<script>
    <#-- Set the data here to determine if the order is plain or printed -->
    <#if orderInfo.isPrinted?has_content && orderInfo.isPrinted?c == "true">
        GoogleAnalytics.setCustomDimension('${orderInfo.orderId?if_exists}', 1);
    <#else>
        GoogleAnalytics.setCustomDimension('${orderInfo.orderId?if_exists}', 2);
    </#if>

    <#if (orderInfo.paymentInfo.paymentMethodTypeIdDesc)?has_content>
        GoogleAnalytics.setCustomDimension('${orderInfo.paymentInfo.paymentMethodTypeIdDesc?if_exists}', 11);
    </#if>

    <#if orderInfo.isScene7?has_content && orderInfo.isScene7?c == "true">
        GoogleAnalytics.setCustomDimension('Scene7', 12);
    <#elseif orderInfo.isPrinted?has_content && orderInfo.isPrinted?c == "true">
        GoogleAnalytics.setCustomDimension('Printed', 12);
    <#else>
        GoogleAnalytics.setCustomDimension('Plain', 12);
    </#if>

    <#if orderInfo.clearanceOrSale?has_content>
        GoogleAnalytics.setCustomDimension('${orderInfo.clearanceOrSale}', 13);
    </#if>

    <#if orderInfo.isReorder?has_content && orderInfo.isReorder?c == "true">
        GoogleAnalytics.setCustomDimension('Yes', 14);
    <#else>
        GoogleAnalytics.setCustomDimension('No', 14);
    </#if>

    <#escape field as field?js_string>
        <#list orderInfo.lineItems as lineItem>
        ga('ec:addProduct', {
            'id': '${lineItem.productId?if_exists}',
            'name': '${lineItem.productName?default("_NA_")?js_string}',
            'category': '',
            'brand': '',
            'variant': '',
            'price': '${lineItem.unitPrice?if_exists}',
            'quantity': '${lineItem.quantity?default("")?replace(",","")}'
        });
        </#list>
        ga('ec:setAction', 'purchase', {
            'id': '${orderInfo.orderId?if_exists}',
            'affiliation': 'Envelopes.com',
            'revenue': '${orderInfo.grandTotal?if_exists}',
            'tax': '${orderInfo.taxTotal?if_exists}',
            'shipping': '${orderInfo.shipTotalWithDiscount?if_exists}',
            'coupon': '${orderInfo.coupons?if_exists}'
        });
        ga('send', 'pageview');     // Send transaction data with initial pageview.
    </#escape>

    $(function() {
        setTimeout(function() {
            InspectletTagging.tagSession('OrderID', '${orderInfo.orderId?if_exists}');
            InspectletTagging.tagSession('PlainOrPrinted', '<#if orderInfo.isPrinted?has_content && orderInfo.isPrinted?c == "true">Printed<#else>Plain</#if>');
        }, 2000);
    });
</script>
<!-- End Google Analytics -->
</#if>