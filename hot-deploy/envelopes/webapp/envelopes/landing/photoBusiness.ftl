<link href="<@ofbizContentUrl>/html/css/landing/photoBusiness.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<div class="content">
    <div class="tablet-desktop-only">
    <#include "../includes/breadcrumbs.ftl" />
    </div>

    <div class="section no-padding photoBusiness">
        <div class="ribbon">
            <strong class="ribbon-content"><h1>5 Surefire Ways to Grow Your Photography Business</h1></strong>
        </div>
        <div>
            <div class="borderContainer row">
                <div class="medium-6 columns">
                    <div class="textContainer">
                        <div class="show-for-medium-up">
                            <p>There are simple, practical steps you can act on today in order to expand your audience and take your photography business to the next level.</p>
                            <p class="headline">Learn about:</p>
                            <div class="small-6 columns">
                                <ul>
                                    <li>Online Communities</li>
                                    <li>Social Networks</li>
                                    <li>Developing a Theme</li>
                                </ul>
                            </div>
                            <div class="small-6 columns">
                                <ul>
                                    <li>Brand Awareness</li>
                                    <li>Building Your Online Store</li>
                                    <li>And more...</li>
                                </ul>
                            </div>
                        </div>
                        <div class="small-12 columns show-for-medium-up">
                            <p>Download our quick guide, <strong>5 Surefire Ways to Grow Your Photography Business</strong><span> and learn what you can do to grow your business, regardless of your level of tech proficiency</span>.</p>
                        </div>
                    </div>
                </div>
                <div class="medium-6 columns">
                    <div class="textContainer">
                        <div id="global-error" data-alert class="alert-box alert radius margin-top-xxs hidden">An error occurred while signing up.</div>
                        <div class="small-12 columns swatchBook hide-for-medium-up">
                            <div class="swatchText">
                                <p><em>FREE Swatchbook <br>with your download.</em></p>
                            </div>
                            <div class="swatchImage">
                                <img src="<@ofbizContentUrl>/html/img/landing/photoBusiness/swatchbook.png</@ofbizContentUrl>" alt="Swatchbook" />
                            </div>
                        </div>
                        <p class="formHeadline">Enter the info below for your FREE copy:</p>
                        <form data-abide="ajax" id="photoForm" name="photoForm">
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
                            <div class="small-12 columns btnOrange jqs-submit">
                                GET YOUR COPY TODAY <i class="fa fa-caret-right"></i>
                            </div>
                        </form>
                        <div class="small-12 columns swatchBook show-for-medium-up">
                            <div class="swatchText">
                                <p><em>FREE Swatchbook <br>with your download.</em></p>
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
            $('form[name="photoForm"]').submit();
        });

        $('form[name="photoForm"]')
                .on('valid.fndtn.abide', function () {
                    var firstName = $('.jqs-firstName').val();
                    var lastName = $('.jqs-lastName').val();
                    var emailAddress = $('.jqs-emailAddress').val();
                    var companyName = $('.jqs-companyName').val();
                    var emailSource = 'POPPHOTO';
                    if(validateEmailAddress(emailAddress)) {
                        $.ajax({
                            type: 'POST',
                            url: '/' + websiteId + '/control/addOrUpdateContact',
                            data: 'email=' + emailAddress + '&toName=' + firstName + ' ' + lastName + '&companyName=' + companyName + '&emailSource=' + emailSource,
                            dataType: 'json',
                            cache: false
                        }).done(function(data) {
                            window.location = '/' + websiteId + '/control/photoThankYou';
                        }).fail(function(data) {
                            $('#global-error').removeClass('hidden');
                        });
                    }
                });
    });



</script>