<link href="<@ofbizContentUrl>/html/css/landing/whitePaper.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<div class="content">
    <div class="section no-padding photoBusiness">
        <div class="ribbon">
            <span class="ribbon-content"><h1>Thank You for Your Interest in Reading Our Whitepaper!</h1></span>
        </div>
        <div>
            <div class="borderContainer row padding-bottom-xs">
                <div class="medium-6 columns">
                    <div class="textContainer">
                        <div class="whitepaperThankYou">
                            <img src="<@ofbizContentUrl>/html/img/landing/whitePaper/thankYou.png</@ofbizContentUrl>" alt="Thank You"/>
                        </div>
                    </div>
                </div>
                <div class="medium-6 columns">
                    <div class="textContainer">
                        <div class="small-12 columns no-padding">
                            <h3>7 Easy Steps to Increase Your Direct Mail Open Rate &amp; Conversions:</h3>
                        </div>
                        <a href="<@ofbizContentUrl>/html/files/landing/whitePaper/006_2016_whitepaper_Direct_Mail.pdf</@ofbizContentUrl>" target="_blank">
                            <div class="small-12 columns btnOrange">
                                READ NOW! <i class="fa fa-caret-right margin-left-xxs"></i>
                            </div>
                        </a>

                        <div class="small-12 columns swatchBook swatchThankYou">
                            <div class="swatchText">
                                <p>To receive your free swatch book, visit <a href="#" class="jqs-applyCode">Envelopes.com/swatchbook</a>, add a swatch book to your cart, and enter the coupon code <strong>directmarketing</strong> at checkout.</p>
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
                    productPromoCodeId: 'directmarketing',
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
