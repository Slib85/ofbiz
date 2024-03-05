<link href="<@ofbizContentUrl>/html/css/landing/whitePaper.css</@ofbizContentUrl>" rel="stylesheet">
<div class="content">
    <div class="section no-padding photoBusiness">
        <div class="ribbon">
            <strong class="ribbon-content"><h1>7 Easy Steps to Increase Your Direct Mail Open Rate &amp; Conversions</h1></strong>
        </div>
        <div>
            <div class="borderContainer row">
                <div class="medium-6 columns">
                    <div class="textContainer">
                        <div class="show-for-medium-up">
                            <p>Make a few simple changes today, which can increase your direct mail open and conversion rates.</p>
                            <p class="headline">Learn about:</p>
                            <div class="small-12 columns">
                                <ul>
                                	<li>How to increase your lead generation success rates.</li>
                                	<li>Drive direct conversions through better mailings.</li>
                                	<li>Increase the chances of having your mail opened.</li>
                                	<li>Making your insert materials stand out.</li>
                                	<li>Creating strong brand awareness through marketing campaigns.</li>
                                </ul>
                            </div>
                        </div>
                        <div class="small-12 columns show-for-medium-up">
                            <p>Download our quick guide, <strong>Increase Your Direct Mail Open Rate and Conversions - in 7 Easy Steps!</strong> and learn what you can do to increase the open rate of your mailings, regardless of past results.</p>
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
                                <p><em>FREE Swatchbook with<br />your whitepaper download.</em></p>
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
                    var emailSource = 'directmarketing';
                    if(validateEmailAddress(emailAddress)) {
                        $.ajax({
                            type: 'POST',
                            url: '/' + websiteId + '/control/addOrUpdateContact',
                            data: 'email=' + emailAddress + '&toName=' + firstName + ' ' + lastName + '&companyName=' + companyName + '&emailSource=' + emailSource,
                            dataType: 'json',
                            cache: false
                        }).done(function(data) {
                            window.location = '/' + websiteId + '/control/whitePaperThankYou';
                        }).fail(function(data) {
                            $('#global-error').removeClass('hidden');
                        });
                    }
                });
    });



</script>
