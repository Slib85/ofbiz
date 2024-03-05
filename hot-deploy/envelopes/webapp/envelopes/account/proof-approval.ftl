<link href="<@ofbizContentUrl>/html/css/account/proof-approval.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/addons/jquery.minicolors.min.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/texel/texel.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/addressing/grid1.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/addressing/slideout.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />

<div class="content">
	<script>
        var reloadOnLogin = true;

		$(function() {
	        $('.upload-file-button').on('click', function(e) {
	            $(this).closest('.jqs-upload').find('input[type="file"]').trigger('click');
	        });

			// Initialize the jQuery File Upload plugin
			$('.jqs-upload').fileupload({
				url: '<@ofbizUrl>/uploadPrintOrderFile</@ofbizUrl>',
				dataType: 'json',
				dropZone: $(this).find('[id^=drop]'),
				sequentialUploads: true,
				add: function(e, data) {
					fileList = $(this).find('[id^=files]');
					fileList.find('div.placeHolder').remove();
					// Add the HTML to the File list element
					var fileDiv = $('<div/>').addClass('text-size-md relative design-file inprogress').append(
						$('<div/>').addClass('absolute progress')
					).append(
						$('<span/>').addClass('padding-left-xxs relative').append(
							$('<i/>').addClass('fa fa-file-photo-o')
						)
					).append(
						$('<span/>').addClass('relative').css({ 'margin': ' 0 5px' }).html(data.files[0].name)
					).attr('data-filename', data.files[0].name);

					data.context = fileDiv.appendTo(fileList);
					var jqXHR = data.submit();
				},
				progress: function(e, data) {
					// Calculate the completion percentage of the upload
					var progress = parseInt(data.loaded / data.total * 100, 10);
					if(progress == 100) {
						fileList.find('[data-filename="' + data.files[0].name + '"]').first().removeClass('inprogress').children('div.progress').fadeOut();
					} else {
						fileList.find('[data-filename="' + data.files[0].name + '"]').first().children('div.progress').width(progress + '%');
					}
				},
				done: function(e, data) {
					if(data.result.success) {
						//add the path to the dom data for that line
						$.each(data.result.files, function(idx) {
							fileList.find('[data-filename="' + data.result.files[idx].name + '"]').attr('data-filepath', data.result.files[idx].path);
						});

						//loop through the dom and create data for the slide
						var completedFileArray = [];
						fileList.find('.design-file').each(function(idx, el) {
							completedFileArray.push( { 'name' : $(this).attr('data-filename'), 'path' : $(this).attr('data-filepath') } );
						});
					}
				},
				fail: function (e, data) {
					$.each(data.files, function(idx, el) {
						fileList.find('[data-filename="' + data.files[idx].name + '"]').first().children('div.progress').first().find('i.fa').removeClass('fa-trash-o').addClass('fa-warning');
					});
				}
			});
		});
	</script>
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Proof Approval
	</div>
	<div class="section">
		<h5>
			View Your Proof
		</h5>
		<div>
			View the details of your proof.
		</div>
	</div>
	<div class="content-body proof-approval">
		<div>
			<#if orderItemsForUser?has_content>
			<#list orderItemsForUser as orderItem>
			<div class="proofParent jqs-proofParent" data-orderItemSeqId="${orderItem.orderItemSeqId?if_exists}" data-orderId="${orderItem.orderId?if_exists}" data-designId="${orderItem.scene7DesignId?if_exists}" data-designParentId="${orderItem.scene7DesignParentId?if_exists}">
				<div class="section">
					<div class="margin-right-xs approveOrRejectBody">
						<h5 class="jqs-proofAction">Approve Your Proof</h5>
						<div>
							Order Number: ${orderItem.orderId} <a href="<@ofbizUrl>/order-detail</@ofbizUrl>?orderId=${orderItem.orderId}">View Order Details</a>
						</div>
						<div>
							Date Ordered: ${orderItem.orderDate?string("MM/dd/YYYY")}
						</div>
						<#assign orderItemArtwork = orderItem.orderItemArtwork?if_exists />
						<#assign orderItemContent = orderItem.orderItemContent?if_exists />
						<#if orderItemContent?exists>
							<a href="<@ofbizUrl>/serveFileForStream</@ofbizUrl>?filePath=${orderItemContent.contentPath?if_exists}&amp;fileName=${orderItemContent.contentName?if_exists}&amp;downLoad=Y"><div class="margin-top-xxs margin-bottom-xxs button button-cta text-center no-margin">View Proof (PDF)</div></a>
						</#if>
						<#if orderItemArtwork.itemCustomerComments?has_content>
						<div class="envelopesComments">
							<div class="commentHead">Read Comments from Envelopes.com</div>
							<pre>${orderItemArtwork.itemCustomerComments?if_exists}</pre>
						</div>
						</#if>
						<div class="approveOrRejectButtons">
							<div class="button button-cta text-center no-margin button-yes jqs-approveOrRejectButton left" data-do="approve">Approve</div>
							<div class="button button-cta text-center no-margin button-no jqs-approveOrRejectButton right" data-do="reject">Reject</div>
						</div>
					</div>

					<div class="approve-proof">
						<div class="jqs-approveProof hidden">
							<h5>Approve</h5>
							<span>You must agree to all of the details to approve your proof.</span>
							<div class="row">
								<div class="small-2 medium-2 large-2 columns no-padding padding-top-xxs padding-bottom-xxs">
									<span>I Agree</span>
								</div>
							</div>
							<div class="row">
								<div class="small-2 medium-2 large-2 columns padding-xxs">
									<input type="checkbox" name="ink-color" class="no-margin" />
								</div>
								<div class="small-10 medium-10 large-10 columns padding-xxs">
									<span class="sub-header">Ink Color</span>
									- ${Static["com.envelopes.order.OrderHelper"].getOrderItemInkList(orderItemArtwork)}
								</div>
							</div>
							<div class="row">
								<div class="small-2 medium-2 large-2 columns padding-xxs">
									<input type="checkbox" name="terms" class="no-margin" />
								</div>
								<div class="small-10 medium-10 large-10 columns padding-xxs">
									<a href="<@ofbizUrl>/privacy</@ofbizUrl>">Privacy Policy</a>
								</div>
							</div>
							<#if orderItemArtwork.itemCustomerComments?has_content>
							<div class="row">
								<div class="small-2 medium-2 large-2 columns padding-xxs">
									<input type="checkbox" name="comments" class="no-margin" />
								</div>
								<div class="small-10 medium-10 large-10 columns padding-xxs">
									<a href="#" data-reveal-id="envelopes_comments">Read Comments from Envelopes.com</a><br />
								</div>
								<div id="envelopes_comments" class="reveal-modal reveal-modal-limiter" data-reveal>
									<div class="popup-border-fade">
										<div>
											<div class="padding-bottom-xxs popup-title">
												<h3 class="padding-left-xxs">Comments from Envelopes.com</h3>
											</div>
											<div class="padding-xs">
												${orderItemArtwork.itemCustomerComments}
											</div>
										</div>
										<a class="close-reveal-modal"><i class="fa fa-times"></i></a>
									</div>
								</div>
							</div>
							</#if>
							<textarea bns-usercomment class="no-margin margin-top-xxs hidden" name="messageApprove" style="width: 400px; height: 100px;" placeholder="Enter any comments here."></textarea>
							<div class="row padding-top-xxs end jqs-proof_options">
								<div data-action="approve" data-order-item-artwork-id="${orderItemArtwork.orderItemArtworkId}" data-order-id="${orderItem.orderId}" data-order-item-seq-id="${orderItem.orderItemSeqId}" data-value="true" class="button button-cta text-center no-margin button-yes jqs-submit left">Approve</div>
								<div class="jqs-goBack button button-non-cta text-center no-margin right">Go Back</div>
							</div>
						</div>
						<div class="jqs-rejectProof hidden">
							<h5>Reject/Make Changes</h5>
							<span>You must select one or more reasons for rejecting your proof.</span>
							<div class="row">
								<div class="small-2 medium-2 large-2 columns no-padding padding-top-xxs padding-bottom-xxs">
									<span>I Want To</span>
								</div>
							</div>
							<#if orderItem.addressBookId?has_content && (!orderItem.lockScene7Design?has_content || (orderItem.lockScene7Design?has_content && orderItem.lockScene7Design != "Y"))>
							<div class="row">
								<div class="small-2 medium-2 large-2 columns padding-xxs">
									<input type="checkbox" name="change-address" class="no-margin jqs-modify-address" />
								</div>
								<div class="small-10 medium-10 large-10 columns padding-xxs">
									<span class="sub-header">Edit Address Book</span>
								</div>
								<div class="text-center hidden jqs-modify-address-content">
									<div class="upload-file-button margin-bottom-xxs button button-cta jqs-calltexel jqs-variabledatagrid">Edit Address Book</div>
								</div>
							</div>
							</#if>
							<#--
							<#if orderItem.scene7DesignParentId?has_content>
							<div class="row">
								<div class="small-2 medium-2 large-2 columns padding-xxs">
									<input type="checkbox" name="change-design" class="no-margin jqs-modify-design" />
								</div>
								<div class="small-10 medium-10 large-10 columns padding-xxs">
									<span class="sub-header">Edit Design</span>
								</div>
								<div class="text-center hidden jqs-modify-design-content">
									<div class="upload-file-button margin-bottom-xxs button button-cta jqs-designnow jqs-edit-design1" data-tracking-name="Proof Approval">Edit Design</div>
								</div>
							</div>
							</#if>
							-->
							<div class="row">
								<div class="small-2 medium-2 large-2 columns padding-xxs">
									<input type="checkbox" name="upload-new-artwork" class="no-margin jqs-upload-new-artwork" />
								</div>
								<div class="small-10 medium-10 large-10 columns padding-xxs">
									<span class="sub-header">Upload New Artwork</span>
									<span> - N/A</span>
								</div>
								<div class="margin-top-xxs hidden jqs-upload-new-artwork-content">
									<form method="POST" action="<@ofbizUrl>/uploadScene7Files</@ofbizUrl>" enctype="multipart/form-data" class="jqs-upload margin-top-xxs margin-bottom-xxs">
										<input type="hidden" name="orderId" value="${orderItem.orderId}" />
										<input type="hidden" name="orderItemSeqId" value="${orderItem.orderItemSeqId}" />
										<input type="file" name="upl" multiple style="position:absolute;top:-100000px;display:block"/>
										<div class="text-center">
											<div class="upload-file-button margin-bottom-xxs button button-cta">Upload File</div>
										</div>
										<div id="drop" class="dropzone">
											<div id="files" class="design-files jqs-designFiles" style="padding: 6px 5px 7px 0px; border: 1px solid #e3e3e3;">
												<div class="placeHolder" style="text-align: center;">DRAG FILES HERE</div>
											</div>
										</div>
									</form>
								</div>
							</div>
							<textarea bns-usercomment  class="no-margin margin-top-xxs" name="messageReject" style="width: 400px; height: 100px;" placeholder="Enter any comments here."></textarea>
							<div class="row end padding-top-xxs">
								<div data-action="reject" data-order-item-artwork-id="${orderItemArtwork.orderItemArtworkId}" data-order-id="${orderItem.orderId}" data-order-item-seq-id="${orderItem.orderItemSeqId}" data-value="false" class="button button-cta text-center no-margin button-no jqs-submit left">Save and Reject</div>
								<div class="jqs-goBack button button-non-cta text-center no-margin right">Go Back</div>
 							</div>
						</div>
					</div>
				</div>
			</div>
			</#list>
			</#if>
			<div class="section approve-proof-message <#if orderItemsForUser?has_content>hidden</#if>">You currently do not have any orders waiting to be proofed.</div>
		</div>
		<div class="content-right">
			<div class="section">
				<h5>About Your Proof</h5>
				<div>Before your order goes into production, you must review and accept your proof. The proof is designed to show what you can expect to be printed.</div>
				<div class="padding-top-xxs">Your proof shows: approximate ink color, print position, fonts, content, scale of printing on envelope, and clarity.</div>
				<div class="padding-top-xxs">Your proof DOES NOT show: envelope color, exact ink color, exact flap shape, and how paper color may affect ink color.</div>
			</div>
			<div class="section">
				<h5>Having Trouble?</h5>
				<div class="sub-header">Try these steps:</div>
				<ol>
					<li>Right click on "View your proof".</li>
					<li>Choose "Save As" or "Save Target As".</li>
					<li>Save the file to your desktop.</li>
				</ol>
				<div>The file will download and you will be able to view it.</div>
			</div>
			<div class="section">
				<h5>About Your Proof</h5>
				<div class="padding-bottom-xxs">Adobe Reader is a free, downloadable application used to view Envelopes.com proofs. Click the "Get Adobe Reader" button below or <a href="#">click here</a> to begin downloading. If you already have Adobe Reader or have viewed PDFs on your computer before, there is no need to download it again.</div>
				<a href="http://www.adobe.com"><img src="<@ofbizContentUrl>/html/img/icon/adobe_reader.jpg</@ofbizContentUrl>" alt="Get Adobe Reader" title="Get Adobe Reader" /></a>
				<div class="padding-top-xs padding-bottom-xxs sub-header">Need Help with PDFs?</div>
				<a href="#">
					Envelopes.com PDF Frequently Asked Questions<br />
					Adobe Reader Customer Support on Adobe.com<br />
					Contact Us
				</a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.iframe-transport.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.fileupload.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/proof-approval.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/addons/jquery.minicolors.min.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/texel/jquery.env.queue.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/texel/jquery.texel.widget.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/lib/envelopes-widget-util.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.grid.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.grid.proxy.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/addressing/jquery.envelopes.variabledata.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>