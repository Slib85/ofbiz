<link href="<@ofbizContentUrl>/html/css/landing/swatchbook.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<div class="content">
    <div class="tablet-desktop-only">
    <#include "../includes/breadcrumbs.ftl" />
    </div>

    <div class="section no-padding swatchbook row">
        <div class="ribbon">
            <h1>Graphic Designers, Win a <span class="free">FREE</span> Swatchbook from Envelopes.com!*</h1>
        </div>
        <div class="large-12 medium-12 small-7 columns headline">
            <h4>An Invaluable tool to inspire creativity for you and your customers.</h4>
        </div>
        <div class="large-4 medium-4 small-5 columns swatchbookImg padding-left-xxs">
            <img src="/html/img/landing/swatchbook/swatchbook.png" alt="Swatchbook" />
        </div>
        <div class="large-8 medium-8 small-12 columns swatchbookForm">
            <div class="formContainer">
                <p class="text-right">Enter the information below to become eligible for this FREE giveaway:</p>

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
                            SIGN UP <i class="fa fa-caret-right"></i>
                        </div>
                    </div>
                </form>
                <div class="small-12 columns disclaimer">
                    <small>
                        *Swatchbook must ship to one address within the 48 contiguous United States. To be eligible for the Free Swatchbook Offer, customers must enter coupon code at checkout. Offer not valid for Alaska, Hawaii, United States Territories or International destinations. Offer is not redeemable on previous orders and cannot be combined with promotional coupon codes. Offer CAN be used in conjunction with Loyalty, Trade Pro and Non-Profit Discount Program. Offer not valid on phone orders. Terms of offer are subject to change at any time.
                    </small>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
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
                    var emailSource = 'Graphic Designer Swatchbook Giveaway';
                    if(validateEmailAddress(emailAddress)) {
                        $.ajax({
                            type: 'POST',
                            url: '/' + websiteId + '/control/addOrUpdateContact',
                            data: 'email=' + emailAddress + '&toName=' + firstName + ' ' + lastName + '&companyName=' + companyName + '&emailSource=' + emailSource,
                            dataType: 'json',
                            cache: false
                        }).done(function(data) {
                            window.location = '/' + websiteId + '/control/swatchbookThankYou';
                        }).fail(function(data) {
                            $('#global-error').removeClass('hidden');
                        });
                    }
                });
    });



</script>