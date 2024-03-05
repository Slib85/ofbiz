<link href="<@ofbizContentUrl>/html/css/account/file-transfer-center.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="content">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > File Transfer Center
	</div>
	<div class="content-body file-transfer-center">
	<#if orderItems?has_content>
	<#list orderItems as orderItem>
		<#assign orderItemArtwork = Static["com.envelopes.order.OrderHelper"].getOrderItemArtwork(delegator, orderItem.orderId, orderItem.orderItemSeqId) />
		<div class="file-transfer">
			<form name="${orderItem.orderId}_${orderItem.orderItemSeqId}" action="<@ofbizUrl>/uploadArtwork</@ofbizUrl>" method="POST" enctype="multipart/form-data" target="uploadFrame">
			<input type="hidden" name="orderId" value="${orderItem.orderId}" />
			<input type="hidden" name="orderItemSeqId" value="${orderItem.orderItemSeqId}" />
			<input type="hidden" name="contentPurposeEnumId" value="OIACPRP_FILE" />
			<div class="section">
				<h5>Upload New Files</h5>
				<div class="sub-header">Order ${orderItem.orderId} - ${orderItem.itemDescription} - ${orderItem.quantity} Qty</div>
				<div class="margin-top-xxs">
					<input class="no-margin file-input" type="file" name="file1">
				</div>
				<div class="margin-top-xxs">
					<input class="no-margin file-input" type="file" name="file2">
				</div>
				<div class="margin-top-xxs">
					<input class="no-margin file-input" type="file" name="file3">
				</div>
				<div class="margin-top-xxs">
					<input class="no-margin other-input" type="text" name="itemJobName" value="${orderItemArtwork.itemJobName?if_exists}" placeholder="Project Name" />
				</div>
				<textarea class="margin-top-xxs textarea-input" name="itemCustomerComments" placeholder="Please provide any helpful instructions, details, or comments below. (Optional)"></textarea>
				<div data-action="" data-order-id="${orderItem.orderId}" data-order-item-seq-id="${orderItem.orderItemSeqId}" class="upload-button button button-cta text-center no-margin">Upload</div>
			</div>
			</form>
		</div>
	</#list>
	</#if>
		<div class="content-right">
			<div class="section">
                <h5>After You Upload Your File:</h5>
                <ul>
                    <li>You will receive a low resolution PDF proof for review, within one business day.</li>
                    <li>Production will not begin until artwork is approved.</li>
                </ul>
                <p>Have Questions?  Email <a href="mailto:service@envelopes.com">service@envelopes.com</a> or Call <a href="tel:1-800-296-4321">800-296-4321</a></p>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/file-transfer.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>