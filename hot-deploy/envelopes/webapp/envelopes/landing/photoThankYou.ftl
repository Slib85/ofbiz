<link href="<@ofbizContentUrl>/html/css/landing/photoBusiness.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<div class="content">
    <div class="tablet-desktop-only">
    <#include "../includes/breadcrumbs.ftl" />
    </div>

    <div class="section no-padding photoBusiness">
        <div class="ribbon">
            <span class="ribbon-content"><h1><strong>Thank You</strong> For Your Interest in Our Whitepaper!</h1></span>
        </div>
        <div>
            <div class="borderContainer row padding-bottom-xs">
                <div class="medium-6 columns">
                    <div class="textContainer">
                        <div class="whitepaperThankYou">
                            <img src="<@ofbizContentUrl>/html/img/landing/photoBusiness/whitepaperThankYou.png</@ofbizContentUrl>" alt="White Paper - Thank You" />
                        </div>
                    </div>
                </div>
                <div class="medium-6 columns">
                    <div class="textContainer">
                        <div class="small-12 columns no-padding">
                            <h3>5 Surefire Ways to Grow Your Photography Business</h3>
                        </div>
                        <a href="<@ofbizContentUrl>/html/files/landing/photoBusiness/106_2015_WHITE_PAPER_PHOTOGRAPHY.pdf</@ofbizContentUrl>" target="_blank">
                            <div class="small-12 columns btnOrange">
                                READ NOW! <i class="fa fa-caret-right margin-left-xxs"></i>
                            </div>
                        </a>

                        <div class="small-12 columns swatchBook swatchThankYou">
                            <div class="swatchText">
                            <p>To receive your free swatch book, visit <a href="#" class="jqs-applyCode">Envelopes.com/swatchbook</a>, add a swatch book to your cart, and enter the coupon code <strong>POPPHOTO</strong> at checkout.</p>
                            </div>
                            <div class="swatchImage">
                                <img src="<@ofbizContentUrl>/html/img/landing/photoBusiness/swatchbook.png</@ofbizContentUrl>" alt="Swatchbook" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function() {
        $('.jqs-applyCode').on('click', function() {
            $.ajax({
                type: 'POST',
                url: '<@ofbizUrl>/applyPromo</@ofbizUrl>',
                async: false,
                data: {
                    productPromoCodeId: 'POPPHOTO',
                    saveResponse: 'true'
                },
                cache: false
            }).done(function(data) {
               window.open('/swatchbook');
            });
            return false;
        });
    });
</script>