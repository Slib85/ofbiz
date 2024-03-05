/**
 * This is the global file for javascript that can load at the end of the page.
 * Example: functions that are called on dom ready
 */

/**
 * Resize text to fit into element
 * @param element
 * @param elementToCompareTo
 * @param fontSizeLimit
 */
 
function resizeText(element, elementToCompareTo, fontSizeLimit) {
    fontSizeLimit = (typeof fontSizeLimit !== 'undefined' ? fontSizeLimit : 9);
    var counter = 0;
    while(element.height() <= elementToCompareTo.height()) {
        counter++;
        var fontSize = parseInt(element.css('font-size'));

        // Fail safe for infinite loop...
        if (parseInt(element.css('font-size')) <= fontSizeLimit || counter > 100) {
            element.css('font-size', fontSizeLimit + 'px');
            break;
        } else {
            element.css('font-size', fontSize - 1 + 'px');
        }
    }
}

// Resize text to two lines with ellipsis
function twoLineFontResize(element, lineHeight, fontSizeLimit) {
    $.each($(element), function() {
        var counter = 0;
        while (getFullHeight($(this)) > lineHeight && parseInt($(this).css('font-size')) > fontSizeLimit) {
            counter++;
            $(this).css('font-size', (parseInt($(this).css('font-size')) - 1) + 'px');
            if(counter > 100) {
                break;
            }
        }
        if (getFullHeight($(this)) > lineHeight && parseInt($(this).css('font-size')) == fontSizeLimit) {
            $(this).css({
                'overflow': 'hidden',
                'text-overflow': 'ellipsis',
                'display': '-webkit-box',
                '-webkit-box-orient': 'vertical',
                '-webkit-line-clamp': '2',
                'height': '35px'
            });
        }
    });
};

//mega menu on header to track via GA
$('[bns-mega-menu-level]').on('click', function(e) {
    e.preventDefault();
    GoogleAnalytics.trackEvent('Header', 'Mega Menu', $(this).text(), parseInt($(this).attr('bns-mega-menu-level')));
    window.location.href = $(this).attr('href');
});
//mega menu on header to track via GA
$('[bns-menu-level]').on('click', function(e) {
    e.preventDefault();
    GoogleAnalytics.trackEvent('Header', 'Menu', $(this).text(), parseInt($(this).attr('bns-menu-level')));
    window.location.href = $(this).attr('href');
});

//color swatch search on header to track via GA
$('.colSearch').on('click', function(e) {
    e.preventDefault();
    GoogleAnalytics.trackEvent('Header', 'Color Swatch', $(this).find('div').html());
    window.location.href = $(this).attr('href');
});

$('.jqs-rushInfo').on('click', function(e) {
    GoogleAnalytics.trackEvent('Header', 'Mini Header', 'Rush Details');
});

// HOW TO USE...
// Category (Required), Action (Required), Label, Value all separated by a pipe.
// example: bns-track_event="Category|Action|Label|Value"
$('[bns-track_event]').on('click', function(e) {
    e.preventDefault();
    
    try {
        var parameterString = '';
        var parameterList = $(this).attr('bns-track_event').split('|');

        if (typeof parameterList == 'object') {
            for (var i = 0; i < parameterList.length; i++) {
                parameterString += '"' + parameterList[i] + '",';
            }
        }

        eval('GoogleAnalytics.trackEvent(' + parameterString.replace(/\,$/, '') + ')');
    } catch (errorMessage) {
        log(errorMessage, true);
    }

    if (typeof $(this).attr('href') != 'undefined') {
        window.location.href = $(this).attr('href');
    }
})

//google ga promo tracking
$('[data-ga-promo]').on('click', function(e) {
    e.preventDefault();
    GoogleAnalytics.trackContent($(this).attr('data-ga-promo-id'), $(this).attr('data-ga-promo-name'), $(this).attr('data-ga-promo-creative'), $(this).attr('data-ga-promo-position'));
    if($(this)[0].hasAttribute("href")) {
        window.location.href = $(this).attr('href');
    }
});

/*
 * This function should run before dom ready
 * jQuery should be available already
 */
if(typeof(Storage) !== 'undefined' && localStorageEnabled) {
    function loadIParcelFiles() {
        if(localStorage.countryGeoId != 'US' && localStorage.countryGeoId != 'CA') {
            $.ajax({
                url: '//script.i-parcel.com/JavaScript/Merchant/2001/1',
                dataType: 'script',
                cache: true,
                async: true
            }).done(function (data) {
                var cssLink = $('<link rel="stylesheet" type="text/css" href="//script.i-parcel.com/CSS/Merchant/2001/1">');
                $('head').append(cssLink);
            });
        }
    }
    if(typeof localStorage.countryGeoId == 'undefined') {
        $.ajax({
            type: 'GET',
            url: 'https://texel.envelopes.com/getCountry',
            async: true,
            cache: true,
            dataType: 'json'
        }).done(function (data) {
            if(data != null && typeof data.CountryCode !== 'undefined') {
                localStorage.countryGeoId = data.CountryCode;
            }
            loadIParcelFiles();
        });
    } else {
        loadIParcelFiles();
    }
}

function clearSiteWideTabContentShadowBox() {
    $('[bns-sitewidetabcontent]').animate({
        bottom: parseInt('-' + getFullHeight($('[bns-sitewidetabcontent]'))) + 'px',
        left: '0px'
    }, 0);

    $('[bns-sitewidetabbutton]').css({
        'top': '5px',
        'left': '0px',
        'background-color': '#f1f1f1',
        'padding': '10px',
        'z-index': '15002'
    });

    $('[bns-sitewidetabcontentshadowbox]').remove();

    $('[bns-sitewidetabbutton]').attr('bns-active', 'false');
}

if (websiteId != 'folders') {
    $.ajax({
        type: 'GET',
        url: 'https://' + rootURL + '/' + websiteId + '/control/couponList',
        cache: true,
        async: true,
        dataType: 'html'
    }).done(function(data) {
        $('.jqs-couponList').html(data);
        window.setTimeout(function() {
            $('[bns-sitewidetabcontent]').css({
                bottom: parseInt('-' + getFullHeight($('[bns-sitewidetabcontent]'))) + 'px',
                left: '0px'
            });

            $('[bns-sitewidetabbutton="siteWideTodaysDeals"]').css('display', 'inline');
        }, 500);
    });
}

$('[bns-sitewidetabbutton]').on('click', function(e) {
    e.stopPropagation();

    if (typeof $(this).attr('bns-active') == 'undefined' || $(this).attr('bns-active') == 'false') {
        $('[bns-sitewideid]').css('display', 'none');
        $('[bns-sitewideid="' + $(this).attr('bns-sitewidetabbutton') + '"]').css('display', 'block');

        $('[bns-sitewidetabcontent]').css({
            bottom: parseInt('-' + getFullHeight($('[bns-sitewidetabcontent]'))) + 'px',
            padding: '20px',
            height: '400px'
        });

        slideIt_init($('[bns-recentlyviewedcontent] .slideIt'));

        $('[bns-sitewidetabbutton]').attr('bns-active', 'false').css({
            'top': '5px',
            'left': '0px',
            'background-color': '#f1f1f1',
            'padding': '10px',
            'z-index': '15002'
        });

        $(this).prevAll().each(function() {
            $(this).css('left', '10px');
        });

        $(this).attr('bns-active', 'true').css({
            'top': '6px',
            'left': '5px',
            'background-color': '#ffffff',
            'padding': '15px 20px 10px 20px',
            'z-index': '20000'
        });

        $('[bns-sitewidetabcontent]').animate({
            bottom: '0px'
        }, 0);

        if ($('[bns-sitewidetabcontentshadowbox]').length == 0) {
            $('body').append(
                $('<div />').attr('bns-sitewidetabcontentshadowbox', '').css({
                    'background-color': '#000000',
                    'z-index': '15000',
                    'width': '100%',
                    'height': '100%',
                    'position': 'fixed',
                    'top': '0px',
                    'left': '0px',
                    'opacity': '.3'
                })
            );

            $('[bns-sitewidetabcontentshadowbox], [bns-sitewidetabbuttoncontainer]').off('click').on('click', function() {
                clearSiteWideTabContentShadowBox();
            });
        }

        $('[bns-sitewideid="siteWideTodaysDeals"] .couponList [data-reveal-id="stayInTheLoop"]').on('click', function() {
            clearSiteWideTabContentShadowBox();
        })
    } else {
        clearSiteWideTabContentShadowBox();
    }
});

if(localStorageEnabled && typeof localStorage.recentlyVisitedProducts !== 'undefined') {
    var recentlyVisitedProducts = JSON.parse(localStorage.recentlyVisitedProducts);

    var itemListDom = $('<div/>').addClass('slideIt-container').css({'height': '275px', 'margin-top': '35px'}).append(
        $('<div />').addClass('slideIt-left').append(
            $('<i />').addClass('fa fa-chevron-left')
        )
    );

    var innerItemListDom = $('<div />');

    function cleanRecentlyVisitedProducts(recentlyVisitedProducts) {
        var recentlyVisitedProductId = new Array();

        for (var index = 0; index < (recentlyVisitedProducts.productList).length; index++) {
            var obj = recentlyVisitedProducts.productList[index];
            duplicateIndex = recentlyVisitedProductId.indexOf(obj.productId);

            if (typeof obj.productId != 'undefined' && (duplicateIndex != -1)) {
                (recentlyVisitedProducts.productList).splice(index, 1);
                cleanRecentlyVisitedProducts(recentlyVisitedProducts);
                break;
            }
            recentlyVisitedProductId.push(obj.productId);
        }
    }
      
    cleanRecentlyVisitedProducts(recentlyVisitedProducts);

    for (var index = 0; index < (recentlyVisitedProducts.productList).length; index++) {
        var obj = recentlyVisitedProducts.productList[index];
        
        if(obj.productId == $('[data-productSku]').text()) {
            recentlyVisitedProducts.productList.splice(index, 1);
            recentlyVisitedProducts.productList.push(obj);
            break;
        }
    }

    for (var index = (recentlyVisitedProducts.productList).length - 1; index >= 0; index--) {
        var obj = recentlyVisitedProducts.productList[index];
        innerItemListDom.append(
            $('<div/>').css({'width': '200px', 'margin':'0 20px'}).append(
                $('<a/>').addClass('text-center wrapText recentItem').attr({'href': obj.href}).append(
                    $('<img/>').attr({'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + obj.productId + '?wid=150&hei=75&fmt=jpeg&qlt=75&bgc=ffffff'})
                ).append(
                    $('<div/>').addClass('text-left').append(
                        $('<div />').addClass('margin-top-xs name').html((obj.name).replace(/(^.*?)\s((?:\(|\&\#x28\;))/, '$1<br /><span class="recentlyViewedSize">$2'))
                    ).append(
                        $('<div/>').addClass('recentProdDetails').html(obj.productId)
                    ).append(
                        $('<div/>').addClass('recentProdDetails').html(obj.color)
                    ).append(
                        $('<div/>').addClass('jqs-starRating ' + 'rating-' + obj.rating)
                    ).append(
                        $('<div/>').html('From ' + formatCurrency(obj.price))
                    )
                )
            )
        );
    }

    itemListDom.append(
        $('<div />').addClass('slideIt text-left').append(innerItemListDom)
    ).append(
        $('<div />').addClass('slideIt-right').append(
            $('<i />').addClass('fa fa-chevron-right')
        )
    );

    $('[bns-recentlyviewedcontent]').append(itemListDom);

    //track clicks on recently viewed
    $('.recentItem').on('click', function() {
        e.preventDefault();
        GoogleAnalytics.trackEvent('Tabs', 'Your Recently Viewed Products', $(this).find('div.name').html());
        window.location.href = $(this).attr('href');
    });

    window.setTimeout(function() {
        $('[bns-sitewidetabcontent]').css({
            bottom: parseInt('-' + getFullHeight($('[bns-sitewidetabcontent]'))) + 'px',
            left: '0px'
        });

        $('[bns-sitewidetabbutton="siteWideRecentlyViewed"]').css('display', 'inline');
    }, 500);
}

/* Login Codes */
//load old order data
function updateLoginReorderMenu() {
    if (typeof(Storage) !== 'undefined' && typeof localStorage.reOrderHistory !== 'undefined') {
        var reOrderHistory = JSON.parse(localStorage.reOrderHistory);
        $('.jqs-reorderProds').remove();
        if ((new Date(reOrderHistory.expireDate)) < (new Date())) {
            localStorage.removeItem('reOrderHistory');
        }
        if (typeof reOrderHistory.items !== 'undefined' && Object.keys(reOrderHistory.items).length > 0) {
            Object.keys(reOrderHistory.items).forEach(function (key) {
                $('.jqs-recentProdHeader').after(
                    $('<a/>').addClass('jqs-reorderProds recent-product').append(
                        $('<div/>').addClass('recent-product-img').append($('<img/>').attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + reOrderHistory.items[key].productId + '?wid=93&hei=80&fmt=jpeg&qlt=75&bgc=ffffff'))
                    ).append(
                        $('<div/>').css({
                            'display': 'table-cell',
                            'width': '133px',
                            'padding-left': '5px',
                            'vertical-align': 'top'
                        }).append(
                            $('<a />').attr('bns-resizablecontent', '').attr('href', '/' + websiteId + '/control/product/~product_id=' + reOrderHistory.items[key].productId).html(reOrderHistory.items[key].itemDescription)
                        ).append(
                            $('<div/>').css({
                                'font-size': '11px',
                                'width': '133px',
                                'color': '#292b2d'
                            }).html(formatCurrency(parseInt(reOrderHistory.items[key].quantity) * parseFloat(reOrderHistory.items[key].unitPrice)) + ' - ' + parseInt(reOrderHistory.items[key].quantity) + ' Qty')
                        ).append(
                            $('<div/>').addClass('add-to-cart').attr('reorder-data', reOrderHistory.items[key].orderId + '|' + reOrderHistory.items[key].orderItemSeqId + '|' + ((reOrderHistory.items[key].artworkSource == null) ? 'undefined|undefined' : 'true|')).html('Add to Cart').on('click', function (e) {
                                $.ajax({
                                    url: '/' + websiteId + '/control/reorderItems',
                                    data: 'itemUUIDs=' + $(this).attr('reorder-data'),
                                    dataType: 'json',
                                    method: 'POST'
                                }).done(function (data) {
                                    if (data.success == true) {
                                        GoogleAnalytics.trackEvent('Header', 'ReOrder', 'Success');
                                        var cart = $('<form/>').attr('id', 'reordercart').attr('action', 'cart').attr('method', 'POST').append('<input type="hidden" name="lastVisitedURL" value="' + document.referrer + '" />').append('<input type="hidden" name="fromAddToCart" value="true" />');
                                        $('body').append(cart);
                                        $('#reordercart').submit();
                                    } else {
                                        //
                                    }
                                }).fail(function (jqXHR, textStatus) {
                                    //
                                });
                            })
                        )
                    )
                );
            });
        } else {
            $('.jqs-recentOrderHistory').remove();
            $('#dropdown-myAccount').css('width', '275px');
        }

        $('.jqs-firstName').html(localStorage.firstName);
        $('.jqs-headerLoyaltyPoints').html(reOrderHistory.loyaltyPoints);
        $('.jqs-headerLoyaltyPercent').html(reOrderHistory.discountRate + '% Discount');
        twoLineFontResize($('[bns-resizablecontent]'), 32, 12);
    } else {
        $('.jqs-recentOrderHistory').remove();
        $('#dropdown-myAccount').css('width', '275px');
    }
}

var loadReOrderHistory = function() {
    if(localStorageEnabled && typeof localStorage.firstName !== 'undefined' && typeof localStorage.reOrderHistory == 'undefined') {
        $.ajax({
            type: 'GET',
            url: '/' + websiteId + '/control/getUserOrderSummary?orderType=all',
            cache: false,
            dataType: 'json',
            async: true
        }).done(function(data) {
            if(data.success && typeof data.orders != 'undefined' && data.orders.length > 0) {
                var items = {};
                var itemCount = 0;
                for (var i = data.orders.length - 1; i >= 0; --i) {
                    for(var j = 0; j < data.orders[i].items.length; j++) {
                        var orderIdAndSeqId = data.orders[i].orderId + '_' + data.orders[i].items[j].orderItemSeqId;
                        data.orders[i].items[j].orderId = data.orders[i].orderId;
                        items[orderIdAndSeqId] = data.orders[i].items[j];
                        itemCount++;
                        if(itemCount == 3) {
                            break;
                        }
                    }
                    if(itemCount == 3) {
                        break;
                    }
                }
                var reOrderHistory = { 'expireDate': getFutureDate(1, false), 'loyaltyPoints': data.loyaltyPoints, 'discountRate': data.discountRate, 'items': items };
                localStorage.setItem('reOrderHistory', JSON.stringify(reOrderHistory));
            }
            updateLoginReorderMenu();
        });
    } else if(localStorageEnabled && typeof localStorage.firstName !== 'undefined' && typeof localStorage.reOrderHistory !== 'undefined') {
        updateLoginReorderMenu();
    }
};

loadReOrderHistory();

/*The below code snippet in the document ready function is obsolete and can be removed. */
$(function() {
    if (getIEVersion() <= 9 && getIEVersion() != 0) {
        /** Placeholder JQUERY fix for legacy browsers **/
        $('[placeholder]').focus(function() {
            var input = $(this);
            if (input.val() == input.attr('placeholder')) {
                input.val('');
                input.removeClass('placeholder');
            }
        }).blur(function() {
            var input = $(this);
            if (input.val() == '' || input.val() == input.attr('placeholder')) {
                input.addClass('placeholder');
                input.val(input.attr('placeholder'));
            }
        }).blur();
        $('[placeholder]').parents('form').submit(function() {
            $(this).find('[placeholder]').each(function() {
                var input = $(this);
                if (input.val() == input.attr('placeholder')) {
                    input.val('');
                }
            })
        });
    }

    delete Array.prototype.toJSON; //workaround for JSON stringify arrays, prototypeJS issue
    Date.prototype.toJSON = function (key) {
        function f(n) {
            // Format integers to have at least two digits.
            return n < 10 ? '0' + n : n;
        }

        return this.getUTCFullYear()   + '-' +
            f(this.getUTCMonth() + 1) + '-' +
            f(this.getUTCDate())      + 'T' +
            f(this.getUTCHours())     + ':' +
            f(this.getUTCMinutes())   + ':' +
            f(this.getUTCSeconds())   + '.' +
            f(this.getUTCMilliseconds())   + 'Z';
    };

    //prevent the browser from doing its default behavior when a file is dropped into the window, this is for drag and drop for file uploads
    $(document).on('drop dragover', function (e) {
        e.preventDefault();
    });

    $('.jqs-footer_email_subscribe').on('click', function() {
        var input_element = $('.jqs-email_subscribe_input');
        $('.jqs-subscribe-email-error, .jqs-subscribe-email-message').hide();
        var emailAddress = input_element.val();
        if(!validateEmailAddress(emailAddress)) {
            $('.jqs-subscribe-email-error').html('Invalid Email address').show();
        } else {
            try {
                _ltk.SCA.CaptureEmail(emailAddress);
                $('.jqs-subscribe-email-message').html('Successfully subscribed to email newsletter').show();
                input_element.val('');
            } catch (e) {
                $('.jqs-subscribe-email-message').html('There was an issue subscribing to the email newsletter.').show();
            }
        }
    }).on('focus', function() {
        $('.jqs-subscribe-email-error, .jqs-subscribe-email-message').hide();
    });

    $(".products-item-popup li > a").on("mouseenter mouseleave", function() {
        if ($(this).siblings("img").length > 0) {
            $(this).siblings("img").toggle();
            $(this).parentsUntil(".products-item-popup").parent().find(".parent_image").toggle();
        }
    });

    $('form[name=globalsearch]').on('submit', function() {
        var keywordElem = $(this).find('input[name=w]');
        if($(keywordElem).val() == '') {
            $(keywordElem).val('*');
        }
    });

    // Search Code
    $("form[name=globalsearch] input[name=w]").on("input", function() {
        var search_phrase = $(this).val();

        waitForFinalEvent(function() {
            if (search_phrase != '') {
                $.ajax({
                    type: "GET",
                    url: "https://texel.envelopes.com/searchAutoComplete?wsi=envelopes&w=" + encodeURIComponent(search_phrase),
                    dataType: 'json',
                    cache: false
                }).done(function (data) {
                    if (typeof data.results != 'undefined' && data.results.length > 0) {
                        $('#dropdown-sli-search-results').children().remove();

                        var searchContainer = $('<div />').addClass('popup-border-fade').append(
                            $('<div />').addClass('sli_ac_products').append(
                                $('<h4 />').addClass('sli_ac_section').html('Suggested Products')
                            ).append(
                                $('<ul />')
                            )
                        );
                        $('#dropdown-sli-search-results').append(searchContainer);

                        $.each(data.results, function (i) {
                            $('#dropdown-sli-search-results .popup-border-fade .sli_ac_products ul').append(
                                $('<li />').addClass('sli_ac_product').append(
                                    $('<a />').attr('href', 'https://www.envelopes.com/envelopes/control/' + data.results[i]._source.url).append(
                                        $('<div />').addClass('sli_ac_iwrap').append(
                                            $('<img />').addClass('sli_ac_image').attr({
                                                'src': data.results[i]._source.image + '?fmt=png-alpha&wid=50',
                                                'alt': data.results[i]._source.name
                                            })
                                        )
                                    ).append(
                                        $('<div />').addClass('searchBarResultsProductInfo').append(
                                            $('<h3 />').addClass('sli_ac_title').html(data.results[i]._source.name)
                                        ).append(
                                            $('<p />').addClass('sli_ac_excerpt').html(data.results[i]._source.color).append(
                                                $('<span />').addClass('sli_ac_price').html(formatCurrency(data.results[i]._source.baseprice))
                                            )
                                        )
                                    )
                                )
                            );
                            $('.sli_ac_product:odd').addClass('sli_ac_odd');
                            $('.sli_ac_product:even').addClass('sli_ac_even');
                        });
                    } else {
                        $('#dropdown-sli-search-results').children().remove();
                    }
                });
            } else {
                $('#dropdown-sli-search-results').children().remove();
            }
        }, 100, 'globalSearch');
    });

    //used where field should have have numerical value
    $('.jqs-number').on('input', function(e) {
        $(this).val($(this).val().replace(/[^0-9]/g, ''));
    });

    $('.reveal-modal-limiter').on('click', function(e) {
        if ($(e.target).attr('id') == $(this).attr('id')) {
            $('#' + $(this).attr('id')).foundation('reveal', 'close');
        }
    });

    setPersistentCartId();

    $('.jqs-trackHeaderBanner').on('click', function() {
        GoogleAnalytics.trackEvent((typeof pageView !== 'undefined' ? pageView : 'Anonymous Page'), 'Header Banner', 'Clicked');
    });

    createScrollableLock();

    var response = '';

    function setStayInLoopCookie() {
        var d = new Date();
        d.setTime(d.getTime()+(180*24*60*60*1000));
        var expires = "expires="+d.toGMTString();
        document.cookie = "email-popup=true; " + expires;
    }

    $('[data-reveal-id="stayInTheLoop"]').off('click.stayInTheLoopAdditionalSource').on('click.stayInTheLoopAdditionalSource', function() {
        var additionalSource = $(this).attr('bns-stayintheloop-additionalsource');

        $('#' + $(this).attr('data-reveal-id')).attr('bns-additionalsource', (typeof additionalSource != 'undefined' ? additionalSource : ''));
    });

    function doStayInTheLoop(parent, popup_name, additionalSource) {
        var loopEmail = parent.find('input[name="email_address"]').val();
        additionalSource = (typeof additionalSource != 'undefined' ? ' - ' + additionalSource : '');

        if(validateEmailAddress(loopEmail)) {
            $().addOrUpdateBrontoEmail(loopEmail, function(success) {
                if(success) {
                    response = 'success';
                    if(typeof productPage != 'undefined' && productPage.returnActiveProduct(true).productId == 'LUX-SWATCHBOOK'){
                        $('#stayInTheLoop.reveal-modal').removeAttr('bns-additionalsource');
                    }
                }
                else {
                    response = 'bronto_error';
                }
            }, popup_name + additionalSource);

            GoogleAnalytics.trackEvent('New Visitor', 'PopUp w/ Email', 'Success');
            GoogleAnalytics.trackEvent('Email Signup', 'Page', (typeof currentview != 'undefined' ? currentview : 'unknown') + additionalSource);
        } else {
            response = "error";
        }
    }

    function closeMobileStayInLoop() {
        $('#stayInTheLoop-mobile').animate({
            "bottom": '-' + getFullHeight($('#stayInTheLoop-mobile')) + 'px'
        }, function() {
            $(this).hide();
        });
    };

    $('#stayInTheLoop-mobile .jqs-decline_email').on('click', function() {
        closeMobileStayInLoop();
    });

    $('#stayInTheLoop-mobile .jqs-submit_email').on('click', function() {
        var parent = $('#stayInTheLoop-mobile');
        var emailSource = (typeof currentview != 'undefined' ? currentview : 'HomePage PopUp') + '(Mobile)';
        if(document.referrer.toLowerCase().indexOf('eatupnewyork') != -1) {
            emailSource = 'eatupny';
        }

        doStayInTheLoop(parent, emailSource);

        var doResponse = function() {
            if (response == "success") {
                parent.find("[bns-sitl-show_on_success]").removeClass("hidden");
                parent.find("[bns-sitl-hide_on_success]").removeClass("hidden").addClass("hidden");
                parent.find('.jqs-sitl_error').hide();
                parent.find('.jqs-submission').hide();
                parent.find('input').hide();
                parent.find('.jqs-response_text').removeClass('hidden').css({
                    'display': 'block',
                    'font-size': '20px',
                    'color': '#ffffff',
                    'padding': '3px 0 10px'
                }).html('Use Code: <span style="border: solid black 2px;padding: 3px 10px;">TAKE10</span>');
                parent.find('.sitlSuccessBtn').removeClass('hidden');
                window.setTimeout(function() {
                    closeMobileStayInLoop();
                }, 5000);
            }
            else if (response == "bronto_error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('There was an issue adding your email.  Please contact customer service.');
            }
            else if (response == "error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('Please enter a valid email address.');
            }
            else {
                window.setTimeout(function() {
                    doResponse();
                }, 1000);
            }
            response = '';
        };

        window.setTimeout(function() {
            doResponse();
        }, 1000);
    });

    $('#stayInTheLoop .jqs-submit_email, #fship-promo .jqs-submit_email, #fship-promo2 .jqs-submit_email').on('click', function() {
        var parent = $(this).parentsUntil('.reveal-modal').parent();
        var emailSource = (typeof currentview != 'undefined' ? currentview : 'Unknown');
        var additionalSource = parent.attr('bns-additionalsource');

        if(document.referrer.toLowerCase().indexOf('eatupnewyork') != -1) {
            emailSource = 'eatupny';
        }

        doStayInTheLoop(parent, emailSource, additionalSource);

        var doResponse = function() {
            if (response == "success") {
                parent.find('[bns-hideonsuccess="stayInTheLoop"]').addClass('hidden');
                parent.find('[bns-showonsuccess="stayInTheLoop"]').removeClass('hidden');
                parent.find('')
                parent.find('[bns-showonsuccess="stayInTheLoop"].jqs-response_text').css({
                    'line-height': '50px',
                    'display': 'block',
                    'font-size': '26px',
                    'margin': '25px auto 30px',
                    'color': '#ffffff'
                }).html('Use Code: <span style="border: solid black 2px;padding: 3px 10px;">TAKE10</span>');
            }
            else if (response == "bronto_error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('There was an issue adding your email.  Please contact customer service.');
            }
            else if (response == "error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('Please enter a valid email address.');
            }
            else {
                window.setTimeout(function() {
                    doResponse();
                }, 1000);
            }
            response = '';
        };

        window.setTimeout(function() {
            doResponse();
        }, 1000);
    });

    $('#foldersPopup .jqs-submit_email').on('click', function() {
        var parent = $('#foldersPopup');
        var emailSource = campaign;

        doStayInTheLoop(parent, emailSource);

        var doResponse = function() {
            if (response == "success") {
                parent.find('.jqs-sitl_error').hide();
                parent.find('.jqs-submit_email').hide();
                parent.find('.jqs-form_info').hide();
                parent.find('.jqs-response_text').html('Thank you for signing up, and welcome to Envelopes.com!<br />You will start receiving our e-mails shortly.')
            }
            else if (response == "bronto_error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('There was an issue adding your email.  Please contact customer service.');
            }
            else if (response == "error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('Please enter a valid email address.');
            }
            else {
                window.setTimeout(function() {
                    doResponse();
                }, 1000);
            }
            response = '';
        };

        window.setTimeout(function() {
            doResponse();
        }, 1000);
    });

    $('#remittancePopup .jqs-submit_email').on('click', function() {
        var parent = $('#remittancePopup');
        var emailSource = campaign;

        doStayInTheLoop(parent, emailSource);

        var doResponse = function() {
            if (response == "success") {
                parent.find('.jqs-sitl_error').hide();
                parent.find('.jqs-submit_email').hide();
                parent.find('.jqs-form_info').hide();
                parent.find('.jqs-response_text').html('Thank you for signing up, and welcome to Envelopes.com!<br />You will start receiving our e-mails shortly.')
            }
            else if (response == "bronto_error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('There was an issue adding your email.  Please contact customer service.');
            }
            else if (response == "error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('Please enter a valid email address.');
            }
            else {
                window.setTimeout(function() {
                    doResponse();
                }, 1000);
            }
            response = '';
        };

        window.setTimeout(function() {
            doResponse();
        }, 1000);
    });
    $('#whiteinkPopup .jqs-submit_email').on('click', function() {
        var parent = $('#whiteinkPopup');
        var emailSource = 'white_ink_customers';

        doStayInTheLoop(parent, emailSource);

        var doResponse = function() {
            if (response == "success") {
                parent.find('.jqs-sitl_error').hide();
                parent.find('.jqs-submit_email').hide();
                parent.find('.jqs-form_info').hide();
                parent.find('.jqs-response_text').html('Thank you for signing up, and welcome to Envelopes.com!<br />You will start receiving our e-mails shortly.')
            }
            else if (response == "bronto_error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('There was an issue adding your email.  Please contact customer service.');
            }
            else if (response == "error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('Please enter a valid email address.');
            }
            else {
                window.setTimeout(function() {
                    doResponse();
                }, 1000);
            }
            response = '';
        };

        window.setTimeout(function() {
            doResponse();
        }, 1000);
    });
    $('#downloadSizeGuide .jqs-submit_email').on('click', function() {
        var parent = $('#downloadSizeGuide');
        var emailSource = 'Size Guide';

        doStayInTheLoop(parent, emailSource);

        var doResponse = function() {
            if (response == "success") {
                parent.find('.jqs-sitl_error').hide();
                parent.find('.jqs-submit_email').hide();
                parent.find('.jqs-form_info').hide();
                parent.find('.jqs-response_title').html('Thank You!');
                parent.find('.jqs-response_text').removeClass('hidden').html('You will receive an email with a link to download the Envelopes.com Size Guide shortly.')
            }
            else if (response == "bronto_error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('There was an issue adding your email.  Please contact customer service.');
            }
            else if (response == "error") {
                parent.find('.jqs-sitl_error').removeClass('hidden');
                parent.find('.jqs-sitl_error').html('Please enter a valid email address.');
            }
            else {
                window.setTimeout(function() {
                    doResponse();
                }, 1000);
            }
            response = '';
        };

        window.setTimeout(function() {
            doResponse();
        }, 1000);
    });
});

$().updateMiniCart();


/*
************
* json2.js *
************
*/
;if(typeof JSON!=='object'){JSON={};}
(function(){'use strict';function f(n){return n<10?'0'+n:n;}
if(typeof Date.prototype.toJSON!=='function'){Date.prototype.toJSON=function(){return isFinite(this.valueOf())?this.getUTCFullYear()+'-'+f(this.getUTCMonth()+1)+'-'+f(this.getUTCDate())+'T'+f(this.getUTCHours())+':'+f(this.getUTCMinutes())+':'+f(this.getUTCSeconds())+'Z':null;};String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function(){return this.valueOf();};}
var cx,escapable,gap,indent,meta,rep;function quote(string){escapable.lastIndex=0;return escapable.test(string)?'"'+string.replace(escapable,function(a){var c=meta[a];return typeof c==='string'?c:'\\u'+('0000'+a.charCodeAt(0).toString(16)).slice(-4);})+'"':'"'+string+'"';}
function str(key,holder){var i,k,v,length,mind=gap,partial,value=holder[key];if(value&&typeof value==='object'&&typeof value.toJSON==='function'){value=value.toJSON(key);}
if(typeof rep==='function'){value=rep.call(holder,key,value);}
switch(typeof value){case'string':return quote(value);case'number':return isFinite(value)?String(value):'null';case'boolean':case'null':return String(value);case'object':if(!value){return'null';}
gap+=indent;partial=[];if(Object.prototype.toString.apply(value)==='[object Array]'){length=value.length;for(i=0;i<length;i+=1){partial[i]=str(i,value)||'null';}
v=partial.length===0?'[]':gap?'[\n'+gap+partial.join(',\n'+gap)+'\n'+mind+']':'['+partial.join(',')+']';gap=mind;return v;}
if(rep&&typeof rep==='object'){length=rep.length;for(i=0;i<length;i+=1){if(typeof rep[i]==='string'){k=rep[i];v=str(k,value);if(v){partial.push(quote(k)+(gap?': ':':')+v);}}}}else{for(k in value){if(Object.prototype.hasOwnProperty.call(value,k)){v=str(k,value);if(v){partial.push(quote(k)+(gap?': ':':')+v);}}}}
v=partial.length===0?'{}':gap?'{\n'+gap+partial.join(',\n'+gap)+'\n'+mind+'}':'{'+partial.join(',')+'}';gap=mind;return v;}}
if(typeof JSON.stringify!=='function'){escapable=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;meta={'\b':'\\b','\t':'\\t','\n':'\\n','\f':'\\f','\r':'\\r','"':'\\"','\\':'\\\\'};JSON.stringify=function(value,replacer,space){var i;gap='';indent='';if(typeof space==='number'){for(i=0;i<space;i+=1){indent+=' ';}}else if(typeof space==='string'){indent=space;}
rep=replacer;if(replacer&&typeof replacer!=='function'&&(typeof replacer!=='object'||typeof replacer.length!=='number')){throw new Error('JSON.stringify');}
return str('',{'':value});};}
if(typeof JSON.parse!=='function'){cx=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;JSON.parse=function(text,reviver){var j;function walk(holder,key){var k,v,value=holder[key];if(value&&typeof value==='object'){for(k in value){if(Object.prototype.hasOwnProperty.call(value,k)){v=walk(value,k);if(v!==undefined){value[k]=v;}else{delete value[k];}}}}
return reviver.call(holder,key,value);}
text=String(text);cx.lastIndex=0;if(cx.test(text)){text=text.replace(cx,function(a){return'\\u'+('0000'+a.charCodeAt(0).toString(16)).slice(-4);});}
if(/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,'@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,']').replace(/(?:^|:|,)(?:\s*\[)+/g,''))){j=eval('('+text+')');return typeof reviver==='function'?walk({'':j},''):j;}
throw new SyntaxError('JSON.parse');};}}());

/*
******************************
* jquery.rwdImageMaps.min.js *
******************************
*/
;(function(a){a.fn.rwdImageMaps=function(){var c=this;var b=function(){c.each(function(){if(typeof(a(this).attr("usemap"))=="undefined"){return}var e=this,d=a(e);a("<img />").load(function(){var g="width",m="height",n=d.attr(g),j=d.attr(m);if(!n||!j){var o=new Image();o.src=d.attr("src");if(!n){n=o.width}if(!j){j=o.height}}var f=d.width()/100,k=d.height()/100,i=d.attr("usemap").replace("#",""),l="coords";a('map[name="'+i+'"]').find("area").each(function(){var r=a(this);if(!r.data(l)){r.data(l,r.attr(l))}var q=r.data(l).split(","),p=new Array(q.length);for(var h=0;h<p.length;++h){if(h%2===0){p[h]=parseInt(((q[h]/n)*100)*f)}else{p[h]=parseInt(((q[h]/j)*100)*k)}}r.attr(l,p.toString())})}).attr("src",d.attr("src"))})};a(window).resize(b).trigger("resize");return this}})(jQuery);


/*
******************************
* cycle.js
******************************
*/
"function"!=typeof JSON.decycle&&(JSON.decycle=function(e){"use strict";var t=[],r=[];return function n(e,a){var o,i,f;if(!("object"!=typeof e||null===e||e instanceof Boolean||e instanceof Date||e instanceof Number||e instanceof RegExp||e instanceof String)){for(o=0;o<t.length;o+=1)if(t[o]===e)return{$ref:r[o]};if(t.push(e),r.push(a),"[object Array]"===Object.prototype.toString.apply(e))for(f=[],o=0;o<e.length;o+=1)f[o]=n(e[o],a+"["+o+"]");else{f={};for(i in e)Object.prototype.hasOwnProperty.call(e,i)&&(f[i]=n(e[i],a+"["+JSON.stringify(i)+"]"))}return f}return e}(e,"$")}),"function"!=typeof JSON.retrocycle&&(JSON.retrocycle=function retrocycle($){"use strict";var px=/^\$(?:\[(?:\d+|\"(?:[^\\\"\u0000-\u001f]|\\([\\\"\/bfnrt]|u[0-9a-zA-Z]{4}))*\")\])*$/;return function rez(value){var i,item,name,path;if(value&&"object"==typeof value)if("[object Array]"===Object.prototype.toString.apply(value))for(i=0;i<value.length;i+=1)item=value[i],item&&"object"==typeof item&&(path=item.$ref,"string"==typeof path&&px.test(path)?value[i]=eval(path):rez(item));else for(name in value)"object"==typeof value[name]&&(item=value[name],item&&(path=item.$ref,"string"==typeof path&&px.test(path)?value[name]=eval(path):rez(item)))}($),$});
