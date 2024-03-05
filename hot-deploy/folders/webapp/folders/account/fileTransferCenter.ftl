<link href="<@ofbizContentUrl>/html/css/folders/global.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/folders/account/fileTransferCenter.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="foldersContainer fileTransferCenter">
	<div class="foldersBreadcrumbs">
		<a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > File Transfer Center
	</div>
	<div class="foldersContainerContent padding20">
	<div class="accountHeader">
        <h1 class="ftc-blue marginBottom20">File Transfer Center</h1>
    </div>
	<#if orderItems?has_content>
	<#list orderItems as orderItem>
		<#assign orderItemArtwork = Static["com.envelopes.order.OrderHelper"].getOrderItemArtwork(delegator, orderItem.orderId, orderItem.orderItemSeqId) />
		<div class="file-transfer">
			<form name="${orderItem.orderId}_${orderItem.orderItemSeqId}" class="fileTransferForm padding20" action="<@ofbizUrl>/uploadArtwork</@ofbizUrl>" method="POST" enctype="multipart/form-data" target="uploadFrame">
			<input type="hidden" name="orderId" value="${orderItem.orderId}" />
			<input type="hidden" name="orderItemSeqId" value="${orderItem.orderItemSeqId}" />
			<input type="hidden" name="contentPurposeEnumId" value="OIACPRP_FILE" />
			<div class="section">
				<h3>Upload New Files</h3>
				<p>Order ${orderItem.orderId} - ${orderItem.itemDescription} - ${orderItem.quantity} Qty</p>
				<input class="file-input" type="file" name="file1">
				<input class="file-input" type="file" name="file2">
				<input class="file-input" type="file" name="file3">
				<div class="foldersTabularRow">
					<div>
						<input class="noMarginTop marginBottom10" type="text" name="itemJobName" value="${orderItemArtwork.itemJobName?if_exists}" placeholder="Project Name" />
					</div>
				</div>
				<textarea class="marginBottom20" name="itemCustomerComments" placeholder="Please provide any helpful instructions, details, or comments below. (Optional)"></textarea>
				<div data-action="" data-order-id="${orderItem.orderId}" data-order-item-seq-id="${orderItem.orderItemSeqId}" class="foldersButton buttonGreen noMargin">Upload</div>
			</div>
			</form>
		</div>
	</#list>
	</#if>
		<h4 class="marginBottom10">After You Upload Your File:</h4>
		<ul class="marginBottom10">
			<li>You will receive a low resolution PDF proof for review, within one business day.</li>
			<li>Production will not begin until artwork is approved.</li>
		</ul>
		<p>Have Questions?  Email <a href="mailto:service@folders.com">service@folders.com</a> or Call <a href="tel:1-800-296-4321">800-296-4321</a></p>
	</div>
</div>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/file-transfer.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>