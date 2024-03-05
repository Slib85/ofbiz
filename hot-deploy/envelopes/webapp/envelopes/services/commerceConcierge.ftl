<link href="<@ofbizContentUrl>/html/css/trade/trade-discount.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/services/commerceConcierge.css</@ofbizContentUrl>" rel="stylesheet" />
<script type="text/javascript">
	cart_url = '<@ofbizUrl>/applyPromo</@ofbizUrl>?productPromoCodeId=SB<#if requestParameters.c?exists>${requestParameters.c}</#if>&saveResponse=true';
</script>
<input type="hidden" name="doPromo" value="<#if requestParameters.c?exists>Y</#if>" />
<div class="content">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/main</@ofbizUrl>">Home</a> > Services > ${trade_name_display} 
	</div>
	<div class="commerceConciergeHeader section no-padding">
		<h1>Envelopes.com Full-Service Concierge</h1>
		<!-- <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/conciergeHeaderImg?fmt=png-alpha&amp;wid=80&amp;hei=82</@ofbizScene7Url>" alt="Commerce Concierge" /> -->
	</div>
	<div class="commerceConciergeContent">
		<div class="section">
			<div class="row conciergeBorderBottom padding-bottom-xs">
				<div class="small-12 medium-6 large-6 columns text-center">
					<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/concierge1?fmt=png-alpha&amp;wid=572&amp;hei=325</@ofbizScene7Url>" alt="Commerce Concierge">
				</div>
				<div class="small-12 medium-6 large-6 columns padding30">
					<h3>Do you spend more than $5,000 on envelopes and stationery each year?</h3>
					<h4 class="margin-top-xxs">Make Envelopes.com Your Preferred Vendor!</h4>
					<p class="conciergeMain1Text margin-top-xxs"><span>The Envelopes.com Full-Service Concierge is Here! </span>We'll take all the work out of your ordering process! From artwork layout to order placement and everything in between -- we've got you covered.</p>
					<h5 class="margin-top-xs">Our goal is to make your job easy!</h5>
				</div>
			</div>
			<!-- <div class="row conciergeBorderBottom padding-bottom-xs conciergeRow">
				<div class="conciergeMainImg1 conciergeTableCell">
					<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/conciergeMainImg1?fmt=png-alpha&amp;wid=572&amp;hei=325</@ofbizScene7Url>">
				</div>
				<div class="conciergeRowText conciergeTableCell">
					<h3>Do you spend more than $5,000 on envelopes and stationary each year?</h3>
					<h4>Make Envelopes.com Your Preferred Vendor!</h4>
					<p class="conciergeMain1Text margin-top-xxs"><span>The Envelopes.com Full-Service Concierge is Here! </span>We'll take all the work out of your ordering process! From artwork layout to order placement and everything in between -- we've got you convered.</p>
					<h5 class="margin-top-xs">Our goal is to make your job easy!</h5>
				</div>
			</div> -->
			<div class="row conciergeBorderBottom padding-bottom-xs padding-top-xs">
				<div class="small-12 medium-6 large-6 columns padding30">
					<h3>How to Get Started</h3>
					<p class="conciergeGetStarted">All we need is a photo or sample of your current envelopes and your personal concierge will:</p>
					<div class="conciergeList margin-top-xs">
						<p><i class="fa fa-caret-right"></i> Set up your artwork</p>
						<p><i class="fa fa-caret-right"></i> Add your custom items to your personal account</p>
						<p><i class="fa fa-caret-right"></i> Create the most simplistic way for you to order again and again</p>
					</div>
				</div>
				<div class="small-12 medium-6 large-6 columns text-center">
					<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/conciergeMainImg2?fmt=png-alpha&amp;wid=569&amp;hei=325</@ofbizScene7Url>" alt="Commerce Concierge">
				</div>
			</div>
			<div class="margin-top-xs commerceConciergeForm conciergeBorderBottom padding-bottom-xs">
				<form id="commerceConciergeForm" name="commerceConciergeForm" method="post" action="" data-bigNameValidateForm="commerceConciergeForm">
					<input type="text" id="dnfField" name="dnfField" value="" style="display: none;" /> 
					<input type="hidden" name="tradeName" value="${trade_name?default('tradePro')}" />
					<div class="jqs-shipping">
						<div class="margin-bottom-xs text-center">
							<h5><span>Apply Now</span></h5>
						</div>
						<div class="row">
							<div class="small-12 medium-4 large-4 columns">
								<p data-bigNameValidateId="conciergeName">Name:</p>
								<input name="conciergeName" type="text" data-bigNameValidate="conciergeName" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Name"/>
							</div>
							<div class="small-12 medium-4 large-4 columns">
								<p data-bigNameValidateId="conciergeCompany">Company:</p>
								<input name="conciergeCompany" type="text" data-bigNameValidate="conciergeCompany" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Company"/>
							</div>
							<div class="small-12 medium-4 large-4 columns">
								<p data-bigNameValidateId="conciergeExpense">Avg Annual Spent on Envelopes/Stationery:</p>
								<input name="conciergeExpense" type="text" data-bigNameValidate="conciergeExpense" />
							</div>
						</div>
						<div class="row">
							<div class="small-12 medium-4 large-4 columns"> 
								<p data-bigNameValidateId="conciergeEmail">Email:</p>
								<input name="conciergeEmail" type="text" data-bigNameValidate="conciergeEmail" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Email"/>
							</div>
							<div class="small-12 medium-6 large-6 columns pullLeft">
								<p>Attachments: <span class="smallerFontForm">(attach samples or your artwork or envelope photos here)</span></p>
								<div class="no-margin conciergeUploadButton" data-reveal-id="startUpload">
                                    <i class="fa fa-cloud-upload"></i> Click Here to Upload
                                </div>
                                <div class="uploadedFiles hidden jqs-uploadedfiles"></div>
							</div>
						</div>
					</div>
					<div class="row padding-bottom-sm">
						<div class="small-12 medium-12 large-12 columns">
							<div class="commerceConciergeButton"  data-bigNameValidateSubmit="commerceConciergeForm" data-bigNameValidateAction="submitConciergeForm">Submit Your Application <i class="fa fa-caret-right"></i></div>
						</div>
					</div>
				</form>
				<div bns-applicationcompleted class="hidden text-center">
					<h3>Thank You for Applying to our Commerce Concierge Program!</h3>
				</div>
			</div>
			<div class="row conciergeMailUsBorder padding-bottom-xs padding-top-xs">
				<div class="small-12 medium-6 large-6 columns text-center">
					<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/conciergeMainImg3?fmt=png-alpha&amp;wid=559&amp;hei=383</@ofbizScene7Url>" alt="Commerce Concierge">
				</div>
				<div class="small-12 medium-6 large-6 columns padding30 conciergeMailUs text-center">
					<h3>Prefer to send us samples in the mail?</h3>
					<p>Send us samples of your current envelopes, and we'll take care of the rest:</p>
					
					<div class="conciergeContact margin-top-xs">
						<i class="fa fa-envelope"></i>
						<p>Envelopes Concierge <br />105 Maxess Rd.<br />Suite S215<br />Melville, NY 11747</p>
					</div>
					
				</div>
			</div>
		</div>	
	</div>

	<!-- Data Reveal for Upload -->
    <div id="startUpload" class="reveal-modal reveal-modal-limiter uploadContainer" data-reveal>
        <div>
            <div class="padding-bottom-xxs popup-title">
                <h3 class="padding-left-xxs">File Upload:</h3>
                <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
            </div>
            <div class="padding-xs">
                <form id="uploadScene7Files" method="POST" action="<@ofbizUrl>/uploadScene7Files</@ofbizUrl>" enctype="multipart/form-data">
                    <input type="file" name="fileUpload" class="jqs-fileupload" multiple />
                    <div class="fileContainer jqs-filecontainer">
                        <div class="dropzone placeholder"></div>
                    </div>
                </form>
                <div class="text-center margin-top-xxs">
                    <span>Accepted File Types:</span>
                    <img src="/html/img/product/popups/allowedFileTypes.png" alt="accepted file types" />
                </div>
                <textarea class="jqs-itemcomments margin-top-xxs" placeholder="Add Additional Comments..."></textarea>
            </div>
            <div class="row">
                <div class="columns large-8 padding-left-xs">
                    <a href="<@ofbizUrl>/printing-and-prepress-help</@ofbizUrl>">Click Here to read our Artwork Specs</a>
                </div>
                <div class="columns large-4 padding-right-xs">
                    <div class="button button-cta right padding-xxs jqs-closemodal">Upload <i class="fa fa-caret-right"></i></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.iframe-transport.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.fileupload.js</@ofbizContentUrl>"></script>
<script src="<@ofbizContentUrl>/html/js/services/commerceConcierge.js</@ofbizContentUrl>"></script>
