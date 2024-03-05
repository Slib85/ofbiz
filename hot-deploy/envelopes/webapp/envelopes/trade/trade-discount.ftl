<link href="<@ofbizContentUrl>/html/css/trade/trade-discount-redesign.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<script type="text/javascript">
	cart_url = '<@ofbizUrl>/applyPromo</@ofbizUrl>?productPromoCodeId=SB<#if requestParameters.c?exists>${requestParameters.c}</#if>&saveResponse=true';
</script>
<input type="hidden" name="doPromo" value="<#if requestParameters.c?exists>Y</#if>" />
<div class="content trade-discount">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/main</@ofbizUrl>">Home</a> > Customer Service > ${trade_name_display} Discounts
	</div>
	<div class="trade-header section no-padding">
		<img class="trade-pro-logo" src="<@ofbizContentUrl>/html/img/logo/trade-${trade_name}.png</@ofbizContentUrl>" alt="Trade Pro" />
		<#if trade_name == "tradePro">
		<img class="right tablet-desktop-only trade-pro-banner" src="<@ofbizContentUrl>/html/img/trade/trade-discount/trade-pro-banner.png</@ofbizContentUrl>" alt="Trade Pro" />
		</#if>
	</div>
	<div class="trade-top">
		<div class="apply-now">
			<div class="section jqs-application_content">
				<div class="row">
					<div class="small-12 medium-push-7 medium-5 large-push-7 large-5 columns">
						<div class="row mobile-only margin-top-xs margin-bottom-xs">
							<div class="small-6 columns">
								<h5 class="text-right"><span style="white-space: nowrap;">Already Have</span><br><span style="white-space: nowrap;">an Account?</span></h5>
							</div>
							<div class="small-6 columns">
								<a class="margin-top-xxs" style="display: inline-block;" href="<@ofbizUrl>/account</@ofbizUrl>"><div class="no-margin button button-cta" style="padding: 15px 20px !important;">Click Here to Login</div></a>
							</div>
						</div>
						<div class="mobile-only" style="background: #00a4e4;">
							<img class="banner-mobile" src="<@ofbizContentUrl>/html/img/trade/trade-discount/trade-pro-banner-small.png</@ofbizContentUrl>" class="margin-top-xs margin-bottom-xxs" alt="Trade Pro" />
						</div>
						<div bns-benefitsinfo class="section benefits-info mobile-only">
							<h5>Who Can Join?</h5>
							<div>
								<span>
									<i class="fa fa-check"></i>Printing, Mailing and Design Firms
								</span>
								<span>
									<i class="fa fa-check"></i>Stationery and Greeting Card Designers
								</span>
								<span>
									<i class="fa fa-check"></i>Marketing and Advertising Agencies
								</span>
								<span>
									<i class="fa fa-check"></i>Photographers and Event Planners
								</span>
								<span>
									<i class="fa fa-check"></i>Advertising Specialty Institue Members (ASI)
								</span>
							</div>
						</div>
						<div class="section benefits-info tablet-desktop-only alreadyAccount">
							<h5>Already Have an Account?</h5>
							<div>
								<a href="<@ofbizUrl>/account</@ofbizUrl>"><div class="no-margin button button-cta profitButton round-btn navyblue-bckgrd">Click Here to Login <i class="fa fa-caret-right"></i></div></a>
							</div>
						</div>
						<div bns-benefitsinfo class="section benefits-info">
							<h5>Purchasing and Delivery Benefits</h5>
							<div>
								<span class="padding-top-xxs">
									<i class="fa fa-check"></i>Save up to ${amount_saved?number + 15}% off
								</span>
								<span>
									<i class="fa fa-check"></i>100+ colors and 200+ sizes ready to ship
								</span>
								<span>
									<i class="fa fa-check"></i>Pay by credit card or check
								</span>
							</div>
						</div>
						<div bns-benefitsinfo class="section benefits-info">
							<h5>Drop Shipping Benefits</h5>
							<div>
								<span class="padding-top-xxs">
									<i class="fa fa-check"></i>Receive 2 free printed samples
								</span>
								<span>
									<i class="fa fa-check"></i>Blind shipping available - Sent with no Envelopes.com markings &amp; your company name showing on the shipping label
								</span>
							</div>
						</div>
						<div bns-benefitsinfo class="section benefits-info">
							<h5>Printing Benefits</h5>
							<div>
								<span class="padding-top-xxs">
									<i class="fa fa-check"></i>Easy-to-use downloadable templates
								</span>
								<span>
									<i class="fa fa-check"></i>Free PDF proof sent for approval
								</span>
								<span>
									<i class="fa fa-check"></i>No charges for standard Pantone inks*
								</span>
								<span>
									<i class="fa fa-check"></i>5-day standard or 3-day rush production
								</span>
							</div>
						</div>
					</div>
					<div class="small-12 medium-6 medium-pull-6 large-6 large-pull-6 columns">
						<#if trade_name == "tradePro">
						<div class="benefits-info padding-bottom-xs tablet-desktop-only">
							<h5>Who Can Join?</h5>
							<div>
								<span>
									<i class="fa fa-check"></i>Printing, Mailing and Design Firms
								</span>
								<span>
									<i class="fa fa-check"></i>Stationery and Greeting Card Designers
								</span>
								<span>
									<i class="fa fa-check"></i>Marketing and Advertising Agencies
								</span>
								<span>
									<i class="fa fa-check"></i>Photographers and Event Planners
								</span>
								<span>
									<i class="fa fa-check"></i>Advertising Specialty Institue Members (ASI)
								</span>
							</div>
						</div>
						</#if>
						<form id="trade-form" data-abide="" name="trade-form" method="post" action="">
							<input type="text" id="dnfField" name="dnfField" value="" style="display: none;" /> <#-- DO NOT FILL THIS FIELD, USED FOR SPAM DETECTION -->
							<input type="hidden" name="tradeName" value="${trade_name?default('tradePro')}" />
							<div class="jqs-shipping">
								<div class="margin-bottom-xs">
									<h5>Apply Now and <strong>Save ${amount_saved}% Today:</strong></h5>
								</div>
								<div class="row">
									<div class="small-10 medium-6 large-6 columns">
										<input name="billing_firstName" required="" type="text" value="" placeholder="First Name" />
										<small class="error">First Name is required.</small>
									</div>
									<div class="small-10 medium-6 large-6 columns">
										<input name="billing_lastName" required="" type="text" value="" placeholder="Last Name" />
										<small class="error">Last Name is required.</small>
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-6 large-6 columns">
										<input name="billing_companyName" required="" type="text" value="" placeholder="Company" />
										<small class="error">Company is required.</small>
									</div>
									<div class="small-12 medium-6 large-6 columns">
										<input name="company_website" required="" type="text" value="" placeholder="Company Website" />
										<small class="error">Please enter a valid Company Website Name.</small>
									</div>
								</div>
								<#-- <div class="row">
									<div class="small-10 medium-6 large-6 columns">
										<input name="billing_contactNumber" required="" type="text" value="" placeholder="Phone" />
										<small class="error">Phone is required.</small>
									</div>
								</div> -->
								<div class="row">
									<div class="small-12 medium-10 large-9 columns">
										<input name="billing_address1" required="" type="text" value="" placeholder="Address" />
										<small class="error">Address is required.</small>
									</div>
								</div>
								<#-- <div class="row">
									<div class="small-12 medium-10 large-9 columns">
										<input name="billing_address2" type="text" value="" placeholder="Address Line 2 (Optional)" />
									</div>
								</div> -->
								<div class="row">
									<div class="small-12 medium-3 large-3 columns">
										<input name="billing_city" required="" type="text" value="" placeholder="City" />
										<small class="error">City is required.</small>
									</div>
									<div class="small-12 medium-6 large-6 columns">
										<label class="envelope-select">
											<select required="" name="billing_stateProvinceGeoId">
												<option value="">State/Province</option>
												${screens.render("component://envelopes/widget/CommonScreens.xml#states")}
											</select>
										</label>
										<small class="error">Please select a state.</small>
									</div>
									<div class="small-12 medium-3 large-3 columns">
										<input id="shipping-postal-code" name="billing_postalCode" required="" type="text" value="" placeholder="Zip" />
										<small class="error">Zip is required.</small>
									</div>
								</div>
								<#-- <div class="row">
									<div class="small-12 medium-12 large-6 columns">
										<label class="envelope-select">
											<select name="industry">
												<option value="">Industry (Optional)</option>
												<option value="Printing">Printing</option>
												<option value="Mailing">Mailing</option>
												<option value="Marketing/Advertising">Marketing/Advertising</option>
												<option value="Graphic Design">Graphic Design</option>
											</select>
										</label>
									</div>
									<div class="small-10 medium-12 large-4 columns" style="float: left;">
										<input name="federal_tax_id" required="" type="text" value="" placeholder="Federal Tax ID" />
										<small class="error">Please enter a valid Federal Tax ID.</small>
									</div>
								</div> -->
								<div class="row">
									<div class="small-12 medium-10 large-9 columns">
										<input name="emailAddress" required="" type="text" value="" placeholder="Email" />
										<small class="error">A valid email is required.</small>
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-6 large-6 columns">
										<input name="password" required="" type="password" value="" placeholder="Password" />
										<small class="error">Password is required.</small>
									</div>
									<div class="small-12 medium-6 large-6 columns">
										<input name="verify_password" required="" type="password" value="" placeholder="Re-Enter Password" />
										<small class="error">Password Verification is required.</small>
									</div>
								</div>
								<div class="row">
									<div class="small-12 medium-12 large-12 columns">
										<textarea name="comments" type="text" value="" placeholder="Comments"></textarea>
									</div>
								</div>
							</div>
							<div class="row padding-bottom-sm">
								<div class="small-12 medium-12 large-12 columns small-text-center medium-text-right">
									<div id="tradeSubmit" class="button-regular button-cta round-btn">Submit</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="margin-top-xs trade-bottom tablet-desktop-only">
					<a id="save25"></a>
					<h5>How To Save Up To ${amount_saved?number + 15}% on Every Order</h5>
					<div class="padding-xs trade-discount-chart">
						<div class="bottom-header">
							<div class="padding-xxs cell-large">
								<span>
									${trade_name_display} Discount<br />
									Program
								</span>
							</div>
							<div class="desktop-only-cell padding-xxs">
								<span>
									Loyalty Points<br />
									<span>$1 = 1 Point</span>
								</span>
							</div>
							<div class="desktop-only-cell padding-xxs">
								<span>
									Loyalty<br />
									Discount
								</span>
							</div>
							<div class="desktop-only-cell padding-xxs">
								<span>
									${trade_name_display}<br />
									Discount
								</span>
							</div>
							<div class="desktop-only-cell padding-xxs">
								<span>
									Combined<br />
									Discount
								</span>
							</div>
						</div>
						<div>
							<div class="padding-xs cell-large bottom-save">
								<span class="save-percent">
									Save ${amount_saved}%
								</span>
								<span class="save-what">
									on every order<br />
									when you sign up
								</span>
							</div>
							<div class="desktop-only-cell pixel-pad-left">
								<div class="padding-xs bottom-point-save-first">
									<span class="point-name">
										Bronze
									</span>
									<span class="point-amount">
										5,001 - 7,500
									</span>
								</div>
								<div class="padding-xs bottom-point-save-second">
									<span class="point-name">
										Silver
									</span>
									<span class="point-amount">
										7,501 - 10,000
									</span>
								</div>
							</div>
							<div class="desktop-only-cell pixel-pad-left">
								<div class="padding-xs bottom-point-save-first">
									<span>
										5%
									</span>
								</div>
								<div class="padding-xs bottom-point-save-second">
									<span>
										7%
									</span>
								</div>
							</div>
							<div class="desktop-only-cell pixel-pad-left">
								<div class="padding-xs bottom-point-save-first">
									<span>
										${amount_saved}%
									</span>
								</div>
								<div class="padding-xs bottom-point-save-second">
									<span>
										${amount_saved}%
									</span>
								</div>
							</div>
							<div class="desktop-only-cell pixel-pad-left">
								<div class="padding-xs bottom-point-save-first">
									<span>
										${amount_saved?number + 5}%
									</span>
								</div>
								<div class="padding-xs bottom-point-save-second">
									<span>
										${amount_saved?number + 7}%
									</span>
								</div>
							</div>
						</div>
						<div>
							<div class="padding-xs cell-large bottom-info">
								<span>
									Once your ${trade_name_display} Discount application is approved&#134;,
									<span>you instantly Save ${amount_saved}% on all online orders!</span>
									Plus, your Loyalty Points are combined with your ${trade_name_display} Discount to increase your savings.
									<span>Start shopping and earn up to ${amount_saved?number + 15}% off savings!</span>
								</span>
							</div>
							<div class="desktop-only-cell pixel-pad-left">
								<div class="padding-xs bottom-point-save-third">
									<span class="point-name">
										Gold
									</span>
									<span class="point-amount">
										10,001 - 32,000
									</span>
								</div>
								<div class="padding-xs bottom-point-save-fourth">
									<span class="point-name">
										Platinum
									</span>
									<span class="point-amount">
										32,001+
									</span>
								</div>
							</div>
							<div class="desktop-only-cell pixel-pad-left">
								<div class="padding-xs bottom-point-save-third">
									<span>
										10%
									</span>
								</div>
								<div class="padding-xs bottom-point-save-fourth">
									<span>
										15%
									</span>
								</div>
							</div>
							<div class="desktop-only-cell pixel-pad-left">
								<div class="padding-xs bottom-point-save-third">
									<span>
										${amount_saved}%
									</span>
								</div>
								<div class="padding-xs bottom-point-save-fourth">
									<span>
										${amount_saved}%
									</span>
								</div>
							</div>
							<div class="desktop-only-cell pixel-pad-left">
								<div class="padding-xs bottom-point-save-third">
									<span>
										${amount_saved?number + 10}%
									</span>
								</div>
								<div class="padding-xs bottom-point-save-fourth">
									<span>
										${amount_saved?number + 15}%
									</span>
								</div>
							</div>
						</div>
						<div>
							<div class="desktop-only-cell cell-large bottom-line-left">
								<div></div>
							</div>
							<div class="desktop-only-cell bottom-line-center">
								<div></div>
							</div>
							<div class="desktop-only-cell bottom-line-center">
								<div></div>
							</div>
							<div class="desktop-only-cell bottom-line-center">
								<div></div>
							</div>
							<div class="desktop-only-cell bottom-line-right">
								<div></div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	<div class="mobile-only text-center margin-top-xs">
		<a class="learn-how-to-save" data-html-switch="<strong>Save 10%</strong> on every order when you sign up.">Click Here to Learn How to <strong>Save up to 25%</strong> on Every Order</a>
		<div id="trade-discount-chart-mobile" class="hidden">
			<div class="intro-paragraph">
				<p>Once your Trade Discount application is approved, you <strong>instantly save 10% on all online orders!</strong></p>
				<p>Plus, your Loyalty Points are combined with your Trade Discount to increase your savings. <strong>Start shopping and earn up to 25% off savings!</strong></p>
			</div>
			<div class="tint-bronze">
				<h4>1 point = $1</h4>
				<div>
					<h5>BRONZE<br>
					<span>5,001 - 7,500 pts.</span></h5>
					<hr>
					<div class="padding-xs">
						<div class="row">
							<div class="small-7 columns">
								<p>Loyalty Discount:</p>
							</div>
							<div class="small-5 columns">
								<p class="text-right">5%</p>
							</div>
						</div>
						<div class="row">
							<div class="small-7 columns text-left">
								<p>Trade Discount:</p>
							</div>
							<div class="small-5 columns text-right">
								<p class="text-right">10%</p>
							</div>
						</div>
						<div class="row">
							<div class="small-7 columns text-left">
								<p>Combined Discount:</p>
							</div>
							<div class="small-5 columns text-right">
								<p class="text-right">15%</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="tint-silver">
				<div>
					<h5>SILVER<br>
					<span>7,501 - 10,000 pts.</span></h5>
					<hr>
					<div class="padding-xs">
						<div class="row">
							<div class="small-7 columns">
								<p>Loyalty Discount:</p>
							</div>
							<div class="small-5 columns">
								<p class="text-right">7%</p>
							</div>
						</div>
						<div class="row">
							<div class="small-7 columns text-left">
								<p>Trade Discount:</p>
							</div>
							<div class="small-5 columns text-right">
								<p class="text-right">10%</p>
							</div>
						</div>
						<div class="row">
							<div class="small-7 columns text-left">
								<p>Combined Discount:</p>
							</div>
							<div class="small-5 columns text-right">
								<p class="text-right">17%</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="tint-gold">
				<div>
					<h5>GOLD<br>
					<span>10,001 - 32,000 pts.</span></h5>
					<hr>
					<div class="padding-xs">
						<div class="row">
							<div class="small-7 columns">
								<p>Loyalty Discount:</p>
							</div>
							<div class="small-5 columns">
								<p class="text-right">10%</p>
							</div>
						</div>
						<div class="row">
							<div class="small-7 columns text-left">
								<p>Trade Discount:</p>
							</div>
							<div class="small-5 columns text-right">
								<p class="text-right">10%</p>
							</div>
						</div>
						<div class="row">
							<div class="small-7 columns text-left">
								<p>Combined Discount:</p>
							</div>
							<div class="small-5 columns text-right">
								<p class="text-right">20%</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="tint-platinum">
				<div>
					<h5>PLATINUM<br>
					<span>32,000+ pts.</span></h5>
					<hr>
					<div class="padding-xs">
						<div class="row">
							<div class="small-7 columns">
								<p>Loyalty Discount:</p>
							</div>
							<div class="small-5 columns">
								<p class="text-right">15%</p>
							</div>
						</div>
						<div class="row">
							<div class="small-7 columns text-left">
								<p>Trade Discount:</p>
							</div>
							<div class="small-5 columns text-right">
								<p class="text-right">10%</p>
							</div>
						</div>
						<div class="row">
							<div class="small-7 columns text-left">
								<p>Combined Discount:</p>
							</div>
							<div class="small-5 columns text-right">
								<p class="text-right">25%</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="tablet-desktop-only">
		<span class="padding-left-md padding-top-sm trade-legend">
			<span>*</span>
			Please see General Artwork and Pre-Press Guidelines for specifics. Download our free guide.
		</span>
		<span class="padding-left-md trade-legend">
			<span>&#134;</span>
			${trade_name_display} Discount Applications are reviewed within 1 business day.
		</span>
	</div>
</div>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/trade/trade-request.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>