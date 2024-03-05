<link href="<@ofbizContentUrl>/html/css/services/made-to-order-redesign.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
<script type="text/javascript">
	$(document).ready(function() {
		var form_data = {
			quantity: '',
			sealingMethod: '',
			printingRequired: '',
			inkFront: '',
			inkBack: '',
			standardSize: '',
			sizeOrientation: '',
			paperType: '',
			comment: '',
			userEmail: '',
			firstName: '',
			lastName: ''
		}

		// ADD WINDOW STUFF

		submitCustomQuote = function() {
			if (validateForm()) {
				$.ajax({
					type: "POST",
					url: "<@ofbizUrl>/add-custom-order</@ofbizUrl>",
					data: {
						quantity: (parseInt($('input[name="custom_quantity"]').val()) >= 5000 ? $('input[name="custom_quantity"]').val() : $('select[name="quantity"]').val() ? $('select[name="quantity"]').val() : 5000),
						sealingMethod: ($('input[name="sealing_method"]:checked').val() == 'Other' ? $('input[name="sealing_method_other"]').val() : $('input[name="sealing_method"]:checked').val()),
						webSiteId: 'envelopes',
						printingRequired: $('input[name="printing"]:checked').val(),
						inkFront: ($('input[name="printing"]:checked').val() == "Y" ? $('input[name="front_colors"]:checked').val() : "0"),
						inkBack: ($('input[name="printing"]:checked').val() == "Y" ? $('input[name="back_colors"]:checked').val() : "0"),
						standardSize: ($('input[name="custom_width"]').val() && $('input[name="custom_height"]').val() ? $('input[name="custom_width"]').val() + ' X ' + $('input[name="custom_height"]').val() : $('select[name="size"]').val()),
						sizeOrientation: ($('input[name="size_orientation"]:checked').val() == undefined ? '' : $('input[name="size_orientation"]:checked').val()),
						paperType: ($('input[name="paper_weight"]').val() ? $('input[name="paper_weight"]').val() + " " : "") + $('input[name="paper_color"]').val(),
						comment: $('textarea[name="project_details"]').val(),
						windowComment: $('textarea[name="window_description"]').val(),
						userEmail: $('input[name="userEmail"]').val(),
						firstName: $('input[name="firstName"]').val(),
						lastName: $('input[name="lastName"]').val(),
						companyName: $('input[name="company"]').val(),
						address1: $('input[name="address1"]').val(),
						address2: $('input[name="address2"]').val(),
						city: $('input[name="city"]').val(),
                        countryGeoId: $('select[name="country"').val(),
                        stateProvinceGeoId: $('select[name="state"]').val(),
						postalCode: $('input[name="zip"]').val(),
						phone: $('input[name="phone"]').val(),
                        industry: $('select[name="industry"]').val(),
						orderedFrom: '<#if shopName?exists && shopName == "lqo">Large Quantity Orders<#else>Custom Quote Shop</#if>'
					},
					dataType:'json',
					cache: false
				}).done(function(data) {
					$('body').append('<img height="1" width="1" src="https://micro.rkdms.com/micro.gif?mid=envelopes&type=customproductquote&valcent=0" alt="" />');
					$(".jqs-response").removeClass("form-success").removeClass("form-error");
					if (data.success) {
						GoogleAnalytics.trackPageview('/' + websiteId + '/control/madeToOrderRequest', 'Made To Order Request Submission');

						var current_step = parseInt($('.progressbar').progressbar('value'));

						updateStep(parseInt($('.progressbar').progressbar('value')) + 1);
					}
					else {
						$('.jqs-response').addClass("form-error").html("There was an error processing your request.  Please contact customer service.");
					}
				});
			}
		}

		submitEmailQuote = function() {
			if (validateForm()) {
				$('[bns-email_quote]').addClass('hidden');
				
				$.ajax({
					type: "POST",
					url: "<@ofbizUrl>/emailQuote</@ofbizUrl>",
					data: {
						webSiteId: 'envelopes',
						userEmail: $('input[name="userEmail"]').val(),
						comment: $('textarea[name="project_details"]').val(),
						standardSize: ($('input[name="custom_width"]').val() && $('input[name="custom_height"]').val() ? $('input[name="custom_width"]').val() + ' X ' + $('input[name="custom_height"]').val() : $('select[name="size"]').val()),
						quantity: (parseInt($('input[name="custom_quantity"]').val()) >= 5000 ? $('input[name="custom_quantity"]').val() : $('select[name="quantity"]').val() ? $('select[name="quantity"]').val() : 5000),
						paperType: ($('input[name="paper_weight"]').val() ? $('input[name="paper_weight"]').val() + " " : "") + $('input[name="paper_color"]').val(),
						sealingMethod: ($('input[name="sealing_method"]:checked').val() == 'Other' ? $('input[name="sealing_method_other"]').val() : $('input[name="sealing_method"]:checked').val()),
						printingRequired: $('input[name="printing"]:checked').val(),
						inkFront: ($('input[name="printing"]:checked').val() == "Y" ? $('input[name="front_colors"]:checked').val() : "0"),
						inkBack: ($('input[name="printing"]:checked').val() == "Y" ? $('input[name="back_colors"]:checked').val() : "0"),
						sizeOrientation: ($('input[name="size_orientation"]:checked').val() == undefined ? '' : $('input[name="size_orientation"]:checked').val()),
						windowComment: $('textarea[name="window_description"]').val()
					},
					dataType:'json',
					cache: false
				}).done(function(response) {
					if (response.success) {
						alert("Your quote has been emailed to: " + $('input[name="userEmail"]').val());
					}
				});
			}
		}
	});
</script>

<div class="content custom_quote_shop">
	<div class="tablet-desktop-only">
		<#include "../includes/breadcrumbs.ftl" />
	</div>
	<div class="genericPageHeader">
		<div class="section margin-top-xxs no-padding">
			<div class="pageHeaderText">
				<h1><#if shopName?exists && shopName == "lqo">Large Quantity Orders<#else>Made to Order Shop</#if></h1>
			</div>
			<div class="headerImage desktop-only">
				<img class="right tablet-desktop-only" src="/html/img/services/made-to-order/header.jpg" alt="Made to Order" />
			</div>
		</div>
	</div>
	<div class="margin-top-xxs content-body">
		<div>
			<div class="pbar">
				<div aria-valuenow="2" aria-valuemax="3" aria-valuemin="0" role="progressbar" class="progressbar ui-progressbar ui-widget ui-widget-content ui-corner-all no-margin"></div>
			</div>
			<div class="jqs-response"></div>
			<!-- STEP 1 -->
			<div data-step_id="1" class="section margin-top-xxs">
				<h2 class="section_header">Enter your custom envelope details to receive a quote for your order</h2>
				<p class="parent_text">Made to Order envelopes are the perfect option for customers in need of custom envelopes. Unique envelope sizes, windows and flaps are just a few of the options available both printed and plain, in large and small quantities. If you don't see it on our site, we'll make it for you. As the leaders in envelope design, we can help you create an envelope perfect for your needs. </p>
				<p class="child_text">Submit a custom request in just a few easy steps.</p>
				<h2 class="step_header"><span class="required">*</span> Step 1: Select Quantity</h2>
				<div class="mto_ui tabular_row">
					<div class="mto_select">
						<label class="envelope-select">
							<select class="jqs-update_on_change" name="quantity">
								<option value="">Select Quantity</option>
								<option value="5000">5,000</option>
								<option value="10000">10,000</option>
								<option value="20000">20,000</option>
								<option value="50000">50,000</option>
								<option value="100000">100,000</option>
								<option value="500000">500,000</option>
								<option value="1000000">1,000,000</option>
							</select>
						</label>
					</div>
					<div class="mto_else text-center">
						<span class="txt_ui">OR</span>
					</div>
					<div class="mto_quantity">
						<input class="jqs-update_on_change" name="custom_quantity" data-parent_name="quantity" type="text" value="" placeholder="Enter Quantity (5,000 Minimum)" />
					</div>
				</div>
				<div class="button button-cta jqs-next round-btn">
					Next <i class="fa fa-chevron-right"></i>
					<br />
					<span>Select Sealing Method</span>
				</div>
			</div>

			<!-- STEP 2 -->
			<div data-step_id="2" class="section margin-top-xxs hidden">
				<h2 class="step_header">Step 2: Select Sealing Method</h2>
				<div class="content_container tabular_row">
					<div class="mto_printing_method">
						<h3 class="parent_text margin-bottom-xxs">Printing:</h3>
						<ul class="no-bullet no-margin inline-list row">
							<li class="no-margin">
								<label class="bnc_button" for="printing_1">
									<input class="left" id="printing_1" type="radio" name="printing" value="N" checked />
									<span class="section_selector">Plain</span>
									<span class="bnc_btn_redesign"></span>
								</label>
							</li>
							<li>
								<label class="bnc_button" for="printing_2">
									<input class="left" id="printing_2" type="radio" name="printing" value="Y" />
									<span class="section_selector">Printed</span>
									<span class="bnc_btn_redesign"></span>
								</label>
							</li>
						</ul>
						<div class="jqs-printed_colors">
							<h3 class="parent_text margin-top-xxs margin-bottom-xxs">Ink Colors on Front:</h3>
							<ul class="no-bullet no-margin inline-list row">
								<li class="no-margin">
									<label class="bnc_button" for="front_colors_1">
										<input class="left" id="front_colors_1" type="radio" name="front_colors" value="0" checked />
										<span class="section_selector">None</span>
										<span class="bnc_btn_redesign"></span>
									</label>
								</li>
								<li>
									<label class="bnc_button" for="front_colors_2">
										<input class="left" id="front_colors_2" type="radio" name="front_colors" value="1" />
										<span class="section_selector">1</span>
										<span class="bnc_btn_redesign"></span>
									</label>
								</li>
								<li>
									<label class="bnc_button" for="front_colors_3">
										<input class="left" id="front_colors_3" type="radio" name="front_colors" value="2" />
										<span class="section_selector">2</span>
										<span class="bnc_btn_redesign"></span>
									</label>
								</li>
								<li>
									<label class="bnc_button" for="front_colors_4">
										<input class="left" id="front_colors_4" type="radio" name="front_colors" value="3" />
										<span class="section_selector">3 or 4</span>
										<span class="bnc_btn_redesign"></span>
									</label>
								</li>
							</ul>
							<h3 class="parent_text margin-top-md margin-bottom-xxs">Ink Colors on Back:</h3>
							<ul class="no-bullet no-margin inline-list row">
								<li class="no-margin">
									<label class="bnc_button" for="back_colors_1">
										<input class="left" id="back_colors_1" type="radio" name="back_colors" value="0" checked />
										<span class="section_selector">None</span>
										<span class="bnc_btn_redesign"></span>
									</label>
								</li>
								<li>
									<label class="bnc_button" for="back_colors_2">
										<input class="left" id="back_colors_2" type="radio" name="back_colors" value="1" />
										<span class="section_selector">1</span>
										<span class="bnc_btn_redesign"></span>
									</label>
								</li>
								<li>
									<label class="bnc_button" for="back_colors_3">
										<input class="left" id="back_colors_3" type="radio" name="back_colors" value="2" />
										<span class="section_selector">2</span>
										<span class="bnc_btn_redesign"></span>
									</label>
								</li>
								<li>
									<label class="bnc_button" for="back_colors_4">
										<input class="left" id="back_colors_4" type="radio" name="back_colors" value="3" />
										<span class="section_selector">3 or 4</span>
										<span class="bnc_btn_redesign"></span>
									</label>
								</li>
							</ul>
						</div>
					</div>
					<div class="mto_sealing_method">
						<h3 class="parent_text margin-bottom-xxs">Sealing Method:</h3>
						<ul class="no-bullet no-margin inline-list row">
							<li class="no-margin">
								<label class="bnc_button" for="sealing_method_1">
									<input class="jqs-update_on_change left" id="sealing_method_1" type="radio" name="sealing_method" value="Regular Glue" checked />
									<span class="section_selector">Regular Glue</span>
									<span class="bnc_btn_redesign"></span>
								</label>
							</li>
							<li>
								<label class="bnc_button" for="sealing_method_2">
									<input class="jqs-update_on_change left" id="sealing_method_2" type="radio" name="sealing_method" value="Peel & Press" />
									<span class="section_selector">Peel &amp; Press&#8482;</span>
									<span class="bnc_btn_redesign"></span>
								</label>
							</li>
							<li>
								<label class="bnc_button" for="sealing_method_3">
									<input class="jqs-update_on_change left" id="sealing_method_3" type="radio" name="sealing_method" value="None" />
									<span class="section_selector">None</span>
									<span class="bnc_btn_redesign"></span>
								</label>
							</li>
							<li>
								<label class="bnc_button" for="sealing_method_4">
									<input class="jqs-update_on_change left" id="sealing_method_4" type="radio" name="sealing_method" value="Other" />
									<span class="section_selector">Other</span>
									<span class="bnc_btn_redesign"></span>
								</label>
							</li>
						</ul>
						<div class="sealing_method_description-wrap">
							<h3 class="jqs-sealing_method_title parent_text margin-bottom-xxs">Regular Glue</h3>
							<p class="jqs-sealing_method_description sealing_method_description">The standard method of sealing envelopes, just moisten to securely seal your contents.</p>
						</div>
					</div>
				</div>
				<div class="row margin-top-xs">
					<div class="small-6 columns">
						<div class="button button-cta left jqs-previous round-btn">
							<i class="fa fa-chevron-left"></i> Previous
							<br />
							<span>Select Quantity</span>
						</div>
					</div>
					<div class="small-6 columns">
						<div class="button button-cta right jqs-next round-btn">
							Next <i class="fa fa-chevron-right"></i>
							<br />
							<span>Select Size</span>
						</div>
					</div>
				</div>
			</div>

			<!-- STEP 3 -->
			<div data-step_id="3" class="section margin-top-xxs hidden">
				<h2 class="step_header"><span class="required">*</span> Step 3: Select Size</h2>
				<div class="content_container">
					<div class="row margin-top-xs">
						<div class="small-12 medium-12 large-12 columns no-padding">
							<div class="mto_ui">
								<div class="mto_select">
									<label class="envelope-select">
										<select class="jqs-update_on_change" name="size" required="">
											<option value="">Choose from our list of sizes</option>
											<#assign sizeList = Static["com.envelopes.refinements.RefinementsUtil"].getSizes().sizes />
											<#if sizeList?has_content>
												<#list sizeList as size>
													<#if size.id != "15x15" && size.id != "1625" && size.id != "11116x234" && size.id != "25x1375" && size.id != "2625x1" && size.id != "4x1" && size.id != "4x2" && size.id != "4x333" && size.id != "85x15">
														<option value="${size.name}">${size.name}</option>
													</#if>
												</#list>
											</#if>
										</select>
									</label>
								</div>
							</div>
						</div>
						<div class="small-12 medium-12 large-12 columns no-padding text-center mto_step3_subheader">
							or choose your own size:
						</div>
						<div class="small-12 medium-5 large-5 columns no-padding">
							<div class="mto_ui">
								<div class="mto_select">
									<input class="jqs-update_on_change margin-top-xxs" data-parent_name="size" type="text" name="custom_width" value="" placeholder="Width" />
								</div>
							</div>
						</div>
						<div class="text-center small-12 medium-2 large-2 columns">
							<span class="txt_ui">X</span>
						</div>
						<div class="small-12 medium-5 large-5 columns no-padding">
							<div class="mto_ui">
								<div class="mto_select">
									<input class="jqs-update_on_change margin-top-xxs" data-parent_name="size" type="text" name="custom_height" value="" placeholder="Height" />
								</div>
							</div>
						</div>
						<div class="small-12 medium-6 large-6 columns margin-top-lg">
							<label class="bnc_button" for="size_orientation_1">
								<input class="margin-top-xxs" id="size_orientation_1" type="radio" name="size_orientation" value="long" />
								<span class="section_selector">Open on the Long Side</span>
								<span class="bnc_btn_redesign"></span>
							</label>
						</div>
						<div class="small-12 medium-6 large-6 columns margin-top-lg">
							<label class="bnc_button" for="size_orientation_2">
								<input class="margin-top-xxs" id="size_orientation_2" type="radio" name="size_orientation" value="short" />
								<span class="section_selector">Open on the Short Side</span>
								<span class="bnc_btn_redesign"></span>
							</label>
						</div>
					</div>
				</div>
				<div class="row margin-top-xs">
					<div class="small-6 columns">
						<div class="button button-cta left jqs-previous round-btn">
							<i class="fa fa-chevron-left"></i> Previous
							<br />
							<span>Select Sealing Method</span>
						</div>
					</div>
					<div class="small-6 columns">
						<div class="button button-cta right jqs-next round-btn">
							Next <i class="fa fa-chevron-right"></i>
							<br />
							<span>Select Window</span>
						</div>
					</div>
				</div>
			</div>

			<!-- STEP 4 -->
			<div data-step_id="4" class="section margin-top-xxs hidden">
				<h2 class="step_header">Step 4: Window Options</h2>
				<div class="content_container">
					<div class="row margin-top-xs">
						<div class="small-12 medium-4 large-6 columns">
							<label class="bnc_button" for="window_option_1">
								<input class="margin-top-xxs" id="window_option_1" type="radio" name="window_option" value="window" />
								<span class="section_selector">Window</span>
								<span class="bnc_btn_redesign"></span>
							</label>
						</div>
						<div class="small-12 medium-8 large-6 columns">
							<label class="bnc_button" for="window_option_2">
								<input class="margin-top-xxs" id="window_option_2" type="radio" name="window_option" value="no window" checked />
								<span class="section_selector">No Window</span>
								<span class="bnc_btn_redesign"></span>
							</label>
						</div>
					</div>
					<textarea class="margin-top-xxs" name="window_description" type="text" value="" placeholder="Please provide details about window size & placement"></textarea>
				</div>
				<div class="row margin-top-xs">
					<div class="small-6 columns">
						<div class="button button-cta left jqs-previous round-btn">
							<i class="fa fa-chevron-left"></i> Previous
							<br />
							<span>Select Size</span>
						</div>
					</div>
					<div class="small-6 columns">
						<div class="button button-cta right jqs-next round-btn">
							Next <i class="fa fa-chevron-right"></i>
							<br />
							<span>Select Color &amp; Weight</span>
						</div>
					</div>
				</div>
			</div>

			<!-- STEP 5 -->
			<div data-step_id="5" class="section margin-top-xxs hidden">
				<h2 class="step_header">Step 5: Select Color &amp; Weight</h2>
				<div class="content_container">
					<div class="row margin-top-xs">
						<div class="small-12 medium-5 large-5 columns">
							<div class="mto_ui">
								<div class="mto_select">
									<input class="jqs-update_on_change" type="text" name="paper_color" value="" placeholder="Enter your color" />
								</div>
							</div>
						</div>
						<div class="small-12 medium-2 large-2 columns text-center">
							<span class="txt_ui">AND</span>
						</div>
						<div class="small-12 medium-5 large-5 columns">
							<div class="mto_ui">
								<div class="mto_select">
									<input class="jqs-update_on_change" type="text" name="paper_weight" value="" placeholder="Enter your paper weight" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row margin-top-xs">
					<div class="small-6 columns">
						<div class="button button-cta left jqs-previous round-btn">
							<i class="fa fa-chevron-left"></i> Previous
							<br />
							<span>Select Window</span>
						</div>
					</div>
					<div class="small-6 columns">
						<div class="button button-cta right jqs-next round-btn">
							Next <i class="fa fa-chevron-right"></i>
							<br />
							<span>Project Details</span>
						</div>
					</div>
				</div>
			</div>

			<!-- STEP 6 -->
			<div data-step_id="6" class="section margin-top-xxs hidden">
				<h2 class="step_header">Step 6: Project Details</h2>
				<div class="content_container">
					<p class="parent_text navyblue">Tell us about your project!</p>
					<p class="child_text">Please let us know additional details regarding your custom order.</p>
					<textarea class="margin-top-xxs jqs-update_on_change" name="project_details" type="text" value="" placeholder="Enter project details"></textarea>
				</div>
				<div class="row margin-top-xs">
					<div class="small-6 columns">
						<div class="button button-cta left jqs-previous round-btn">
							<i class="fa fa-chevron-left"></i> Previous
							<br />
							<span>Color &amp; Weight</span>
						</div>
					</div>
					<div class="small-6 columns">
						<div class="button button-cta right jqs-next round-btn">
							Next <i class="fa fa-chevron-right"></i>
							<br />
							<span>Contact Information</span>
						</div>
					</div>
				</div>
			</div>

			<!-- STEP 7 -->
			<div data-step_id="7" class="section margin-top-xxs hidden">
				<h2 class="step_header">Step 7: Contact Information</h2>
				<div class="row margin-top-xs">
					<div class="small-12 medium-6 large-6 columns">
						<input class="jqs-update_on_change" name="firstName" data-parent_name="name" type="text" value="" placeholder="First Name (Required)" required="" />
					</div>
					<div class="small-12 medium-6 large-6 columns">
						<input class="jqs-update_on_change" name="lastName" data-parent_name="name" type="text" value="" placeholder="Last Name (Required)" required="" />
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-6 large-6 columns">
						<input class="jqs-update_on_change" name="company" type="text" value="" placeholder="Company" />
					</div>
					<div class="small-12 medium-6 large-6 columns">
						<label class="envelope-select">
							<select class="jqs-update_on_change" name="industry">
								<option value="">Industry</option>
								<option value="Accounting Services">Accounting Services</option>
								<option value="Banking">Banking</option>
								<option value="Bride">Bride</option>
								<option value="Ecommerce">Ecommerce</option>
								<option value="Education">Education</option>
								<option value="Event Planner">Event Planner</option>
								<option value="Florist">Florist</option>
								<option value="Government">Government</option>
								<option value="Graphic Designer">Graphic Designer</option>
								<option value="Groom">Groom</option>
								<option value="Home Improvement Services">Home Improvement Services</option>
								<option value="Homemaker">Homemaker</option>
								<option value="Legal Services">Legal Services</option>
								<option value="Medical Services">Medical Services</option>
								<option value="Non-Profit">Non-Profit</option>
								<option value="Other">Other</option>
								<option value="Photographer">Photographer</option>
								<option value="Printer">Printer</option>
								<option value="Realtor">Realtor</option>
								<option value="Religious Institution">Religious Institution</option>
								<option value="Retail Sales">Retail Sales</option>
								<option value="Stationery Designer">Stationery Designer</option>
								<option value="Student">Student</option>
								<option value="Teacher">Teacher</option>
								<option value="Web Designer">Web Designer</option>
								<option value="Other">Other</option>
							</select>
						</label>
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-6 large-6 columns">
						<input class="jqs-update_on_change" name="address1" data-parent_name="address" required="" type="text" value="" placeholder="Address Line 1 (Required)" />
					</div>
					<div class="small-12 medium-6 large-6 columns">
						<input name="address2" type="text" value="" placeholder="Address Line 2 (Optional)" />
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-3 large-3 columns">
						<input class="jqs-update_on_change" data-parent_name="address" required="" name="city" type="text" value="" placeholder="City (Required)" />
					</div>
					<div class="small-12 medium-3 large-3 columns">
						<label class="envelope-select">
							<select class="jqs-update_on_change" data-parent_name="address" name="state" required="">
								<option value="">State/Province (Required)</option>
								${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
							</select>
						</label>
					</div>
					<div class="small-12 medium-3 large-3 columns">
						<input class="jqs-update_on_change" data-parent_name="address" required="" name="zip" type="text" value="" placeholder="Zip (Required)" />
					</div>
					<div class="small-12 medium-3 large-3 columns">
						<label class="envelope-select">
							<select class="jqs-update_on_change" data-parent_name="address" name="country">
								<option value="">Country (Required)</option>
								<option value="USA">United States</option>
								<option value="CAN">Canada</option>
							</select>
						</label>
					</div>
				</div>
				<div class="row">
					<div class="small-12 medium-6 large-6 columns">
						<input class="jqs-update_on_change" name="phone" type="text" value="" required="" placeholder="Phone (Required)" />
					</div>
					<div class="small-12 medium-6 large-6 columns">
						<input class="jqs-update_on_change" name="userEmail" type="text" required="" value="${emailAddress?if_exists}" placeholder="E-mail (Required)" />
					</div>
				</div>
				<div class="row margin-top-xs">
					<div class="small-6 columns">
						<div class="button button-cta left jqs-previous round-btn">
							<i class="fa fa-chevron-left"></i> Previous
							<br />
							<span>Project Details</span>
						</div>
					</div>
					<div class="small-6 columns">
						<div class="button button-cta right jqs-next round-btn">
							Next <i class="fa fa-chevron-right"></i>
							<br />
							<span>Review &amp; Submit</span>
						</div>
					</div>
				</div>
			</div>

			<!-- STEP 8 -->
			<div data-step_id="8" class="review_and_submit section margin-top-xxs hidden">
				<h2 class="step_header">Step 8: Review &amp; Submit</h2>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Quantity:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="quantity" class="jqs-data"></span>
							<span data-step_id_to_edit="1" class="edit_button" style="">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Sealing Method:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="sealing_method" class="jqs-data"></span>
							<span data-step_id_to_edit="2" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Size:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="size" class="jqs-data"></span>
							<span data-step_id_to_edit="3" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Color:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="paper_color" class="jqs-data"></span>
							<span data-step_id_to_edit="5" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Paper Weight:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="paper_weight" class="jqs-data"></span>
							<span data-step_id_to_edit="5" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Project Details:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="project_details" class="jqs-data"></span>
							<span data-step_id_to_edit="6" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Name:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="name" class="jqs-data"></span>
							<span data-step_id_to_edit="7" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Company:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="company" class="jqs-data"></span>
							<span data-step_id_to_edit="7" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Industry:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="industry" class="jqs-data"></span>
							<span data-step_id_to_edit="7" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Address:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="address" class="jqs-data"></span>
							<span data-step_id_to_edit="7" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">Phone:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="phone" class="jqs-data"></span>
							<span data-step_id_to_edit="7" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="jqs-data_row row margin-top-xs">
					<div class="small-12 medium-4 large-3 columns">
						<p class="parent_text">E-mail:</p>
					</div>
					<div class="small-12 medium-8 large-9 columns">
						<p class="child_text">
							<span data-name="userEmail" class="jqs-data"></span>
							<span data-step_id_to_edit="7" class="edit_button">Edit <i class="fa fa-caret-right"></i></span>
						</p>
					</div>
				</div>
				<div class="row margin-top-xs">
					<div class="small-6 columns">
						<div class="button button-cta left jqs-previous round-btn">
							<i class="fa fa-chevron-left"></i> Previous
							<br />
							<span>Color &amp; Weight</span>
						</div>
					</div>
					<div class="small-6 columns">
						<div onclick="submitCustomQuote();" class="button button-cta right jqs-submit submit round-btn">
							Submit <i class="fa fa-chevron-right"></i>
						</div>
						<#--
						<div bns-email_quote onclick="submitEmailQuote();" class="button button-cta right jqs-submit submit margin-right-xs">
							Email This Quote <i class="fa fa-chevron-right"></i>
						</div>
						-->
					</div>
				</div>
			</div>

			<!-- STEP 9 -->
			<div data-step_id="9" class="section margin-top-xxs hidden">
				<h2 class="step_header">Thank You!</h2>
				<div class="content_container">
					<p class="parent_text">Your custom quote request has been successfully submitted!</p>
					<p class="child_text">You will receive a confirmation e-mail with the details of your quote shortly.</p>
					<p class="child_text">Our custom quote specialist, Lorraine, will respond to your request within 24 hours!</p>
					<p class="parent_text">If you have additional questions or concerns, please contact Customer Service at 1-877-NVELOPE.</p>
				</div>
				<div class="text-center">
					<div class="button button-cta jqs-new_quote new_quote">
						<span>Start Another Quote</span>
					</div>
				</div>
			</div>
		</div>

		<div class="cq-right">
			<div class="section">
				<h5><i class="fa fa-info-circle"></i> Help</h5>
				<ul class="no-bullet padding-left-xxs no-margin">
					<li>
						<i class="fa fa-phone"></i>
						<a href="tel:1-877-683-5673">1-877-NVELOPE</a>
					</li>
					<li class="padding-top-xxs">
						<i class="fa fa-comment"></i>
                        <a href="javascript:void(0);" onclick="olark('api.box.expand')">Live Chat</a>
					</li>
					<li class="padding-top-xxs">
						<i class="fa fa-envelope"></i>
						<a href="http://support.envelopes.com/customer/portal/emails/new">E-mail Us</a>
					</li>
				</ul>
			</div>
			<div class="section">
				<h5><i class="fa fa-info-circle"></i> <#if shopName?exists && shopName == "lqo">Why Envelopes.com?<#else>What Qualifies?</#if></h5>
				<ul class="no-bullet padding-left-xxs no-margin">
					<#if shopName?exists && shopName == "lqo">
                    <li>
                        <i class="fa fa-check"></i> Fast Turn-Around Time
                    </li>
                    <li>
                        <i class="fa fa-check"></i> Largest variety of sizes, colors and styles
                    </li>
                    <li>
                        <i class="fa fa-check"></i> Customization Available
                    </li>
                    <li>
                        <i class="fa fa-check"></i> Free Shipping over $${globalContext.freeShippingAmount?default("250")} (Code: FREE${globalContext.freeShippingAmount?default("250")})
                    </li>
                    <li>
                        <i class="fa fa-check"></i> Easy ordering
                    </li>
					<#else>
					<li>
						<i class="fa fa-check"></i> Custom Stock
					</li>
					<li>
						<i class="fa fa-check"></i> Custom Windows
					</li>
					<li>
						<i class="fa fa-check"></i> Custom Color
					</li>
					<li>
						<i class="fa fa-check"></i> Custom Size
					</li>
					<li>
						<i class="fa fa-check"></i> Custom Flap
					</li>
					<li>
						<i class="fa fa-check"></i> Custom Sealing
					</li>
					</#if>
				</ul>
			</div>
		</div>
	</div>
</div>
<script type='text/javascript' src="<@ofbizContentUrl>/html/js/services/made-to-order.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
