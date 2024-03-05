<link href="<@ofbizContentUrl>/html/css/landing/year-end-swatchbook.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<#assign coupon = requestParameters.coupon?default('') />
<div class="content">
    <div class="tablet-desktop-only">
    <#include "../includes/breadcrumbs.ftl" />
    </div>

    <div class="section no-padding swatchbook row">
        <div class="ribbon">
            <h1>Thank You For Your Interest In Our Swatchbook</h1>
        </div>
        <div class="large-6 medium-6 small-12 columns swatchbookText padding-left-xxs hide-for-small">
            <div class="textContainer">
                <img src="<@ofbizContentUrl>/html/img/landing/year-end-swatchbook/thankYouLeft.png</@ofbizContentUrl>" alt="Thank You" />
                <small>
                    *Swatchbook must ship to one address within the 48 contiguous United States. To be eligible for the Free Swatchbook Offer, customers must enter the coupon code (${coupon}) at checkout. Offer not valid for Alaska, Hawaii, United States Territories or International destinations. Offer is not redeemable on previous orders and cannot be combined with promotional coupon codes. Offer CAN be used in conjunction with Loyalty, Trade Pro and Non-Profit Discount Program. Offer not valid on phone orders. Terms of offer are subject to change at any time.
                </small>
            </div>
        </div>
        <div class="large-6 medium-6 small-12 columns swatchbookForm">
            <div class="formContainer thankyou">
                <div class="small-8 columns text-left">
                    <p>
                        To receive your FREE Swatchbook*,
                        visit <a href="<@ofbizUrl>/product/~category_id=PAPER/~product_id=LUX-SWATCHBOOK</@ofbizUrl>" style="color:#F4712B">Envelopes.com/swatchbook</a>,
                        add a swatchbook to your cart,
                        and enter the coupon code
                        <span class="couponCode">${coupon}</span> at checkout.
                    </p>
                </div>
                <div class="small-4 columns no-padding padding-top-xs">
                    <img src="<@ofbizContentUrl>/html/img/landing/photoBusiness/swatchbook.png</@ofbizContentUrl>" alt="Swatchbook" />
                </div>
                <div class="small-12 columns text-center padding-bottom-xs dealPromo">
                    <p>Our biggest sale of the year is happening right now!</p>
                </div>
                <div class="small-12 columns swatchButton">
                    <a href="<@ofbizUrl>/main</@ofbizUrl>" >
                        <div class="btnOrange jqs-submit">
                            REVEAL THE DEAL! <i class="fa fa-caret-right"></i>
                        </div>
                    </a>
                </div>
                <div class="small-12 columns">
                    <div class="show-for-small">
                        <small>
                            *Swatchbook must ship to one address within the 48 contiguous United States. To be eligible for the Free Swatchbook Offer, customers must enter coupon code at checkout. Offer not valid for Alaska, Hawaii, United States Territories or International destinations. Offer is not redeemable on previous orders and cannot be combined with promotional coupon codes. Offer CAN be used in conjunction with Loyalty, Trade Pro and Non-Profit Discount Program. Offer not valid on phone orders. Terms of offer are subject to change at any time.
                        </small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){

        var attn = '${attn}';
        if (attn != '') {
            var campaigns = {
                'graphic' : {
                    'title' : 'Graphic Designers',
                    'source' : 'HOWHolidaySwatch',
                    'coupon' : 'HOWSWATCH'
                },
                'printers' : {
                    'title' : 'Printers',
                    'source' : 'PrintingNewsHolidaySwatch',
                    'coupon' : 'PRINTSWATCH'
                }
            };
            $('.jqs-attn').html(campaigns[attn].title);
        }



        if (navigator.appVersion.indexOf('MSIE 8.') == -1) {
            $(document).foundation({
                abide: {
                    live_validate : false,
                    focus_on_invalid : false
                }
            });
        }

        $('.jqs-submit').on('click', function() {
            $('form[name="swatchbookForm"]').submit();
        });

        $('form[name="swatchbookForm"]')
                .on('valid.fndtn.abide', function () {
                    var firstName = $('.jqs-firstName').val();
                    var lastName = $('.jqs-lastName').val();
                    var emailAddress = $('.jqs-emailAddress').val();
                    var companyName = $('.jqs-companyName').val();
                    var emailSource = campaigns[attn]['source'];
                    if(validateEmailAddress(emailAddress)) {
                        $.ajax({
                            type: 'POST',
                            url: '/' + websiteId + '/control/addOrUpdateContact',
                            data: 'email=' + emailAddress + '&toName=' + firstName + ' ' + lastName + '&companyName=' + companyName + '&emailSource=' + emailSource,
                            dataType: 'json',
                            cache: false
                        }).done(function(data) {
                            window.location = '/' + websiteId + '/control/swatchbookThankYou?coupon=' + campaigns[attn]['coupon'];
                        }).fail(function(data) {
                            $('#global-error').removeClass('hidden');
                        });
                    }
                });
    });



</script>