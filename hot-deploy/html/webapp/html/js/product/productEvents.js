submitQuote = function() {
    $('[bns-quote_submit_button]').addClass('hidden');

    $.ajax({
        type: "POST",
        url: "/" + websiteId + "/control/emailQuote",
        data: {
            WebSiteId: 'envelopes',
            UserEmail: $('input[name="quoteToEmailAddress"]').val(),
            FromEmail: $('input[name="quoteFromEmailAddress"]').val(),
            Message: $('textarea[name="quoteMessage"]').val(),
            ProductName: productPage.returnActiveProduct(true).name,
            ProductColor: productPage.returnActiveProduct(true).color,
            ProductStyle: productPage.returnActiveProduct(true).style.replace("_", " "),
            ColorsFront: productPage.returnActiveProduct(true).colorsFront,
            ColorsBack: productPage.returnActiveProduct(true).colorsBack,
            Quantity: productPage.returnActiveProduct(false).priceData.quantity,
            WhiteInkFront: productPage.returnActiveProduct(true).whiteInkFront,
            WhiteInkBack: productPage.returnActiveProduct(true).whiteInkBack,
            Rush: productPage.returnActiveProduct(true).isRush,
            Addresses: productPage.returnActiveProduct(true).addresses,
            Price: productPage.returnActiveProduct(true).productPriceData[productPage.returnActiveProduct(false).priceData.quantity].price
        },
        dataType:'json',
        cache: false
    }).done(function(response) {
        if (response.success) {
            $('[bns-email_this_quote="default"]').addClass('hidden');
            $('[bns-email_this_quote="success"]').removeClass('hidden');

            window.setTimeout(function() {
                $('#emailThisQuote').foundation('reveal', 'close');
                window.setTimeout(function() {
                    $('[bns-email_this_quote="default"]').removeClass('hidden');
                    $('[bns-email_this_quote="success"]').addClass('hidden');
                }, 1000);
            }, 2000);
        }

        $('[bns-quote_submit_button]').removeClass('hidden');
    });
}