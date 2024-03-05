$('[bns-subscribeemailbutton]').on('click', function() {
    var subscribeLocation = $(this).attr('bns-subscribeemailbutton');
    var inputElement = $('[bns-subscribeemailinput="' + subscribeLocation + '"]');
    var errorElement = $('[bns-subscribeemailerror="' + subscribeLocation + '"]');
    var messageElement = $('[bns-subscribeemailmessage="' + subscribeLocation + '"]');

    $(errorElement).hide();
    $(messageElement).hide();
    
    var emailAddress = inputElement.val();
    // var parent = $('[bns-footersubscribe]');
    var parent = $(this).parent();
    var tagInfo = parent.attr('bns-taginfo');

    if(!validateEmailAddress(emailAddress)) {
        $(errorElement).html('Invalid Email address').show();
    } else {
        if (doStayInTheLoop(parent, tagInfo, tagInfo)) {
            $(messageElement).html('Successfully subscribed to email newsletter').show();
            inputElement.val('');
        }
    }
}).on('focus', function() {
    $('[bns-subscribeemailerror="' + $(this).attr('bns-subscribeemailbutton') + '"]').hide();
    $('[bns-subscribeemailmessage="' + $(this).attr('bns-subscribeemailbutton') + '"]').hide();
});

// Carousel on Main Page
var i = 0;
var currentCarouselImageIndex = 0;
var totalCarouselImages = $('.hpCarouselContainer > a').length;
function homepageCarousel() {
    $('.hpCarouselContainer > a').css('z-index', '1');
    $($('.hpCarouselContainer > a')[(currentCarouselImageIndex + 1) % totalCarouselImages]).css('z-index', '2');
    currentCarouselImageIndex = (currentCarouselImageIndex + 1) % totalCarouselImages;

    var sliderDotCheck = $('.sliderDots i').removeClass('fa-circle').addClass('fa-circle-thin');
    $(sliderDotCheck[currentCarouselImageIndex]).removeClass('fa-circle-thin').addClass('fa-circle');
}

sliderInterval = setInterval(homepageCarousel, 3000);

$('.sliderDots i').on('click', function() {
    $('.hpCarouselContainer > a').css('z-index','1');
    var imageIndex = $(this).attr('data-key');
    $('.hpCarouselContainer').find('[data-key="' + imageIndex + '"]').css('z-index','2');

    var sliderDotCheck = $('.sliderDots i').removeClass('fa-circle').addClass('fa-circle-thin');
    $(sliderDotCheck[imageIndex]).removeClass('fa-circle-thin').addClass('fa-circle');

});

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
            }
            else {
                response = 'bronto_error';
            }
        }, popup_name + additionalSource);

        GoogleAnalytics.trackEvent('New Visitor', 'PopUp w/ Email', 'Success');
        GoogleAnalytics.trackEvent('Email Signup', 'Page', (typeof currentview != 'undefined' ? currentview : 'unknown') + additionalSource);
        return true;
    } else {
        response = "error";
    }
    return false;
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
            parent.find('.jqs-sitl_error').hide();
            parent.find('[bns-sitlHideOnSuccess]').addClass('hidden');
            parent.find('[bns-sitlShowOnSuccess]').removeClass('hidden');
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

$('#stayInTheLoop .jqs-submit_email, #fship .jqs-submit_email, #fship2 .jqs-submit_email, #todaysDealEmailSignUp .jqs-submit_email').on('click', function() {
    var parent = $(this).parents('.bnRevealContainer');
    var emailSource = (typeof currentview != 'undefined' ? currentview : 'Unknown');
    var additionalSource = parent.attr('bns-additionalsource');

    if(document.referrer.toLowerCase().indexOf('eatupnewyork') != -1) {
        emailSource = 'eatupny';
    }

    if(parent.is('#fship') || parent.is('#fship2') || parent.is('#todaysDealEmailSignUp')){
        doStayInTheLoop(parent, additionalSource, additionalSource);
    } else {
        doStayInTheLoop(parent, emailSource, additionalSource);
    }
    
    var doResponse = function() {
        if (response == "success") {
            parent.find('.jqs-sitl_error').hide();
            parent.find('[bns-sitlHideOnSuccess]').addClass('hidden');
            parent.find('[bns-sitlShowOnSuccess]').removeClass('hidden');
            parent.find('.sitlTextSuccess').css('display', 'block');
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

if (websiteId == 'folders') {
    $.ajax({
        type: 'GET',
        url: '/' + websiteId + '/control/couponList',
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
            'top': '6px',
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

        if($(this).attr('bns-sitewidetabbutton') == 'siteWideTodaysDeals' && $('[bns-sitewideid="siteWideTodaysDeals"]').find('.signUp').data('bnreveal') == 'todaysDealEmailSignUp') {
            bnRevealInit($('[data-bnReveal]'));
        }
        twoLineFontResize($('[bns-recentlyviewedcontentname]'), 44, 14)
    } else {
        clearSiteWideTabContentShadowBox();
    }
});

if(typeof localStorage.recentlyVisitedProducts !== 'undefined') {
    var recentlyVisitedProducts = JSON.parse(localStorage.recentlyVisitedProducts);

    var itemListDom = $('<div/>').addClass('slideIt-container').css({'height': '300px', 'margin-top': '15px'}).append(
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
            $('<div/>').css({'width': '235px', 'padding':'10px', 'border': '1px solid #c2c2c2', 'height': '292px', 'position': 'relative'}).append(
                $('<a/>').addClass('textLeft wrapText recentItem').attr({'href': obj.href}).append(
                    $('<img/>').attr({'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + obj.productId + '?wid=150&hei=125&fmt=jpeg&qlt=75&bgc=ffffff'})
                ).append(
                    $('<div/>').append(
                        $('<div />').attr('bns-recentlyviewedcontentname', '').addClass('marginTop10 name').html((obj.name).replace(/(^.*?)\s((?:\(|\&\#x28\;))/, '$1<br /><span class="recentlyViewedSize">$2'))
                    ).append(
                        $('<div/>').addClass('recentProdDetailsSKU').html('SKU: ' + obj.productId)
                    ).append(
                        $('<div/>').addClass('recentProdDetails').html('Color: ' + obj.color)
                    ).append(
                        $('<div/>').addClass('recentProdDetailsMinQuantity').html('Minimum Quantity: ' + obj.minimumQuantity)
                    ).append(
                        $('<div/>').addClass('desktop-inlineBlock jqs-starRating recentProdDetailsRating ' + 'rating-' + obj.rating)
                    ).append(
                        $('<div/>').addClass('desktop-inlineBlock recentProdDetailsMinQuantity recentProdDetailsPrice').html('From ' + formatCurrency(obj.price))
                    )
                )
            )
        );
    }

    itemListDom.append(
        $('<div />').addClass('slideIt textLeft').append(innerItemListDom)
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
                url: "https://texel.envelopes.com/searchAutoComplete?wsi=folders&w=" + encodeURIComponent(search_phrase),
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
                                $('<a />').attr('href', 'https://www.folders.com/folders/control/' + data.results[i]._source.url).append(
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
                                            $('<span />').addClass('sli_ac_price').html(data.results[i]._source.baseprice == 0.00 ? 'Instant Pricing' : formatCurrency(data.results[i]._source.baseprice))
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