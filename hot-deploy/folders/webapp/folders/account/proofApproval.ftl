<link href="<@ofbizContentUrl>/html/css/folders/account/proofApproval.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/addons/jquery.minicolors.min.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/texel/texel.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/addressing/grid1.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/addressing/slideout.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />

<script>
    var reloadOnLogin = true;

    $(function() {
        // Trigger closest file upload
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
                var fileDiv = $('<div/>').addClass('relative design-file inprogress').append(
                        $('<div/>').addClass('absolute progress')
                ).append(
                        $('<span/>').addClass('relative').append(
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

<div class="foldersContainer foldersNewLimiter paddingTop30 proofApproval">
    <div class="foldersBreadcrumbs">
        <a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Proof Approval
    </div>
    <div class="foldersContainerContent">
        <div class="accountHeader padding10">
            <h1 class="ftc-blue">Proof Approval</h1>
        </div>
        <div class="foldersTabularRow proofApprovalContent">
            <div class="proofApprovalContentLeft">
                <div class="proofApprovalOutline padding20 marginBottom20">
                    <h3 class="ftc-blue">View Your Proof</h3>
                    <p>View the details of your proof.</p>
                </div>
                <div class="proofApprovalOutline padding20 marginBottom20">
                    <#if orderItemsForUser?has_content>
                    <#list orderItemsForUser as orderItem>
                    <div class="proofParent jqs-proofParent marginBottom20" data-orderItemSeqId="${orderItem.orderItemSeqId?if_exists}" data-orderId="${orderItem.orderId?if_exists}" data-designId="${orderItem.scene7DesignId?if_exists}" data-designParentId="${orderItem.scene7DesignParentId?if_exists}">
                        <h3 class="ftc-blue">Approve Your Proof</h3>
                        <p>
                            <strong>Order Number:</strong> ${orderItem.orderId} <a href="<@ofbizUrl>/order-detail</@ofbizUrl>?orderId=${orderItem.orderId}" class="orderDetails ftc-orange">View Order Details &raquo;</a><br>
                            <strong>Date Ordered:</strong> ${orderItem.orderDate?string("MM/dd/YYYY")}
                        </p>
                        <#assign orderItemArtwork = orderItem.orderItemArtwork?if_exists />
                        <#assign orderItemContent = orderItem.orderItemContent?if_exists />
                        <#if orderItemContent?exists>
                        <a href="<@ofbizUrl>/serveFileForStream</@ofbizUrl>?filePath=${orderItemContent.contentPath?if_exists}&amp;fileName=${orderItemContent.contentName?if_exists}&amp;downLoad=Y" class="foldersButton buttonGold viewProof marginRight5 noMarginBottom">View Proof (PDF)</a>
                        </#if>
                        <#if orderItemArtwork.itemCustomerComments?has_content>
                        <div class="envelopesComments">
                            <div class="commentHead">Read Comments from Folders.com</div>
                            <div>${orderItemArtwork.itemCustomerComments?if_exists}</div>
                        </div>
                        </#if>
                        <div class="approveOrRejectButtons">
                            <a class="foldersButton approveProof marginRight5 noMarginBottom jqs-approveOrRejectButton" data-do="approve">Approve</a>
                            <a class="foldersButton rejectProof noMarginBottom jqs-approveOrRejectButton" data-do="reject">Reject</a>
                        </div>
                        <div class="approve-proof">
                            <div class="jqs-approveProof proofApprovalGray marginTop20 padding20 hidden">
                                <h3>Approve</h3>
                                <p>You must agree to all of the details to approve your proof.</p>
                                <p><strong>I Agree</strong></p>
                                <label class="marginBottom10">
                                    <input type="checkbox" name="ink-color" class="no-margin" /> Ink Color - ${Static["com.envelopes.order.OrderHelper"].getOrderItemInkList(orderItemArtwork)}<br>
                                    <input type="checkbox" name="terms" class="no-margin" /> <a target="_blank" href="<@ofbizUrl>/privacy</@ofbizUrl>">Privacy Policy</a>
                                </label>
                                <#if orderItemArtwork.itemCustomerComments?has_content>
                                <input type="checkbox" name="comments" class="no-margin" />
                                <a href="#" data-reveal-id="envelopes_comments">Read Comments from Folders.com</a><br />
                                <div id="envelopes_comments" class="reveal-modal reveal-modal-limiter" data-reveal>
                                    <div class="popup-border-fade">
                                        <div>
                                            <div class="padding-bottom-xxs popup-title">
                                                <h3 class="padding-left-xxs">Comments from Folders.com</h3>
                                            </div>
                                            <div class="padding-xs">
                                            ${orderItemArtwork.itemCustomerComments}
                                            </div>
                                        </div>
                                        <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
                                    </div>
                                </div>
                                </#if>
                                <textarea bns-usercomment name="messageApprove" placeholder="Enter any comments here."></textarea>
                                <div data-action="approve" data-order-item-artwork-id="${orderItemArtwork.orderItemArtworkId}" data-order-id="${orderItem.orderId}" data-order-item-seq-id="${orderItem.orderItemSeqId}" data-value="true" class="jqs-submit foldersButton approveProof marginRight5 noMarginBottom">Approve</div> <div class="jqs-goBack foldersButton buttonDarkGray noMarginBottom">Go Back</div>
                            </div>
                            <div class="jqs-rejectProof proofApprovalGray marginTop20 padding20 hidden">
                                <h3>Reject/Make Changes</h3>
                                <p>You must select one or more reasons for rejecting your proof.</p>
                                <p><strong>I Want To</strong></p>
                                <#if orderItem.addressBookId?has_content && (!orderItem.lockScene7Design?has_content || (orderItem.lockScene7Design?has_content && orderItem.lockScene7Design != "Y"))>
                                <label class="marginBottom10"><input type="checkbox" name="change-address" class="no-margin jqs-modify-address" /> Edit Address Book</label>
                                <div class="hidden jqs-modify-address-content">
                                    <div class="upload-file-button foldersButton buttonGold jqs-calltexel jqs-variabledatagrid noMarginTop marginBottom10">Edit Address Book</div>
                                </div>
                                </#if>
                                <label class="marginBottom10"><input type="checkbox" name="upload-new-artwork" class="no-margin jqs-upload-new-artwork" /> Upload New Artwork - N/A</label>
                                <div class="margin-top-xxs hidden jqs-upload-new-artwork-content">
                                    <form method="POST" action="<@ofbizUrl>/uploadScene7Files</@ofbizUrl>" enctype="multipart/form-data" class="jqs-upload margin-top-xxs margin-bottom-xxs">
                                        <input type="hidden" name="orderId" value="${orderItem.orderId}" />
                                        <input type="hidden" name="orderItemSeqId" value="${orderItem.orderItemSeqId}" />
                                        <input type="file" name="upl" multiple style="position:absolute;top:-100000px;display:block"/>
                                        <div class="foldersTabularRow artworkRow marginBottom10">
                                            <div class="noPadding">
                                                <div class="foldersButton buttonGold noMargin upload-file-button">Upload File</div>
                                            </div>
                                            <div id="drop" class="dropzone noPadding">
                                                <div id="files" class="design-files padding10 jqs-designFiles">
                                                    <div class="placeHolder" style="text-align: center;">DRAG FILES HERE</div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <textarea bns-usercomment name="messageReject" placeholder="Enter any comments here."></textarea>
                                <div data-action="reject" data-order-item-artwork-id="${orderItemArtwork.orderItemArtworkId}" data-order-id="${orderItem.orderId}" data-order-item-seq-id="${orderItem.orderItemSeqId}" data-value="false" class="jqs-submit foldersButton rejectProof marginRight5 noMarginBottom">Save and Reject</div>
                                <div class="jqs-goBack foldersButton buttonDarkGray marginRight5 noMarginBottom">Go Back</div>
                            </div>
                        </div>
                    </div>
                    </#list>
                    </#if>
                </div>           
                <div class="section approve-proof-message <#if orderItemsForUser?has_content>hidden</#if>">You currently do not have any orders waiting to be proofed.</div>
            </div>
            <div class="proofApprovalContentRight">
                <div class="proofApprovalGray padding20 marginBottom20">
                    <h3>About Your Proof</h3>
                    <p>Before your order goes into production, you must review and accept your proof. The proof is designed to show what you can expect to be printed.</p>
                    <p>Your proof shows: approximate ink color, print position, fonts, content, scale of printing on envelope, and clarity.</p>
                    <p>Your proof DOES NOT show: envelope color, exact ink color, exact flap shape, and how paper color may affect ink color.</p>
                </div>
                <div class="proofApprovalGray padding20">
                    <h3>Having Trouble?</h3>
                    <p>Try these steps:</p>
                    <ol>
                        <li>Right click on "View your proof".</li>
                        <li>Choose "Save As" or "Save Target As".</li>
                        <li>Save the file to your desktop.</li>
                    </ol>
                    <p>The file will download and you will be able to view it.</p>
                </div>
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