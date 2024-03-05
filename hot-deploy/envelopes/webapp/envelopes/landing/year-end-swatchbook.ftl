<link href="<@ofbizContentUrl>/html/css/landing/year-end-swatchbook.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<#assign attn = requestParameters.attn?default('') />
<div class="content">
    <div class="tablet-desktop-only">
    <#include "../includes/breadcrumbs.ftl" />
    </div>

    <div class="section no-padding swatchbook row">
        <div class="ribbon">
            <h1>GET YOUR FREE SWATCHBOOK TODAY!*</h1>
        </div>
        <div class="large-6 medium-6 small-12 columns swatchbookText padding-left-xxs hide-for-small">
            <div class="textContainer">
                <img src="<@ofbizContentUrl>/html/img/landing/year-end-swatchbook/holidayGift.png</@ofbizContentUrl>" alt="Holiday Gift" />
                <p>
                    An invaluable tool for <span class="jqs-attn"></span>.
                </p>
                <p>
                    The <a href="<@ofbizUrl>/main</@ofbizUrl>">Envelopes.com</a> Swatchbook is comprised of nearly 100 paper and cardstock options that make comparing colors and textures a cinch.
                </p>
                <small>
                    *Swatchbook must ship to one address within the 48 contiguous United States. To be eligible for the Free Swatchbook Offer, customers must enter coupon code at checkout. Offer not valid for Alaska, Hawaii, United States Territories or International destinations. Offer is not redeemable on previous orders and cannot be combined with promotional coupon codes. Offer CAN be used in conjunction with Loyalty, Trade Pro and Non-Profit Discount Program. Offer not valid on phone orders. Terms of offer are subject to change at any time.
                </small>
            </div>
        </div>
        <div class="large-6 medium-6 small-12 columns swatchbookForm">
            <div class="formContainer">
                <p class="text-left">Enter below for your FREE Swatchbook:</p>

                <form data-abide="ajax" id="swatchbookForm" name="swatchbookForm">
                    <div class="small-12 columns">
                        <input type="text" class="jqs-firstName" name="firstName" required="" placeholder="First Name" />
                        <small class="error">First Name is required.</small>
                    </div>
                    <div class="small-12 columns">
                        <input type="text" class="jqs-lastName"  name="lastName" required="" placeholder="Last Name" />
                        <small class="error">Last Name is required.</small>
                    </div>
                    <div class="small-12 columns">
                        <input type="email" class="jqs-emailAddress"  name="emailAddress" required="" placeholder="Email Address" />
                        <small class="error">Email Address is required.</small>
                    </div>
                    <div class="small-12 columns">
                        <input type="text" class="jqs-companyName" name="companyName" placeholder="Company Name" />
                    </div>
                    <div class="small-12 columns swatchButton">
                        <div class="btnOrange jqs-submit">
                            GET YOUR SWATCHBOOK TODAY <i class="fa fa-caret-right"></i>
                        </div>
                    </div>
                </form>
                <div class="small-12 columns text-center padding-bottom-xs">
                    <a href="<@ofbizUrl>/main</@ofbizUrl>" ><img src="<@ofbizContentUrl>/html/img/landing/year-end-swatchbook/clearance_cta.png</@ofbizContentUrl>" alt="Clearance" /></a>
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
                            window.location = '/' + websiteId + '/control/year-end-swatchbookThankYou?coupon=' + campaigns[attn]['coupon'];
                        }).fail(function(data) {
                            $('#global-error').removeClass('hidden');
                        });
                    }
                });
    });



</script>