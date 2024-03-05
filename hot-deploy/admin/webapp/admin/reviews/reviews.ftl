<link href="/html/css/admin/reviews/reviews.css" rel="stylesheet">
<link rel="stylesheet" href="/html/themes/global/vendor/toastr/toastr.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/advanced/toastr.css">

<#if reviews?has_content>
	<#list reviews as review>
		<form name="${review.productReviewId}_approveReview" method="POST" action="<@ofbizUrl>/approveReview</@ofbizUrl>">
		<input type="hidden" name="id" value="${review.productReviewId}" />
		<#assign product = Static["com.envelopes.product.ProductHelper"].getProduct(delegator, review.productId) />
		<#assign color = Static["com.envelopes.product.ProductHelper"].getProductFeatures(delegator, product, Static["org.apache.ofbiz.base.util.UtilMisc"].toList("COLOR")) />
		<div class="panel panel-primary panel-line panel-bordered">
			<div class="panel-body">
				<header class="row">
					<div class="col-md-2 product_image text-center">
						<a href="#" class="profile-picture">
							<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product.productId}?wid=60&amp;hei=60&amp;fmt=jpeg</@ofbizScene7Url>" class="img-responsive" />
						</a>
					</div>
					<div class="col-md-10">
						<div class="row">
							<div class="col-md-2">
								<strong>${product.productName?if_exists}</strong>
								<span>${color.COLOR?if_exists}</span>
							</div>
							<div class="col-md-2">
								<span><a href="#">Avg Rating</a></span>
                                <h4>${product.productRating?if_exists}</h4>
							</div>
							<div class="col-md-2">
								<span><a href="#">Order ID</a></span>
                                <h4>${review.orderId?default("...")}</h4>
							</div>
							<div class="col-md-2">
								<span><a href="#">Nick Name</a></span>
                                <h4>${review.nickName?default("...")}</h4>
							</div>
							<div class="col-md-2">
								<span><a href="#">Recommended</a></span>
                                <h4>${review.recommend?default("N")}</h4>
							</div>
							<div class="col-md-2">
								<span><a href="#">Anonymous</a></span>
                                <h4>${review.postedAnonymous?default("N")}</h4>
							</div>
						</div>
					</div>
				</header>
				<div class="row" style="border-top: 1px solid #F5F5F6; padding-top: 10px;">
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label">Rating</label><br />
							<input type="text" class="form-control input-lg" name="productRating" placeholder="Product Rating" value="${review.productRating?if_exists}">
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label">Description</label>
							<input type="text" class="form-control input-lg" name="describeYourself" placeholder="Describe Yourself" value="${review.describeYourself?if_exists}">
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label">Used For</label>
							<input type="text" class="form-control input-lg" name="productUse" placeholder="Used For" value="${review.productUse?if_exists}">
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label">Product Quality</label>
							<input type="text" class="form-control input-lg" name="productQuality" placeholder="Product Quality" value="${review.productQuality?if_exists}">
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label">Product Quality Reason</label>
							<input type="text" class="form-control input-lg" name="productQualityReason" placeholder="Product Quality Reason" value="${review.productQualityReason?if_exists}">
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label">Status</label>
							<select name="statusId" class="form-control" data-allow-clear="false" data-placeholder="Status ID">
								<option value="PRR_PENDING" selected>Pending</option>
								<option value="PRR_APPROVED">Approve</option>
								<option value="PRR_DELETED">Delete</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<blockquote class="blockquote-gold">
							<p><strong>Product Review</strong></p>
							<textarea class="form-control review-rating-box" name="productReview" placeholder="Customer's Review">${review.productReview?if_exists}</textarea>
						</blockquote>
					</div>
					<div class="col-md-6">
						<blockquote class="blockquote-blue">
							<p><strong>Response</strong></p>
							<textarea class="form-control review-response-box" name="reviewResponse" placeholder="Enter a response here if you have one, this will be publishd on the site to show that a response was posted against the review.">${review.reviewResponse?if_exists}</textarea>
						</blockquote>
					</div>
				</div>
				<#if review.orderId?has_content>
					<div class="row pull-xs-left">
						<button type="button" data-reviewid="${review.productReviewId}" class="btn btn-squared btn-outline btn-primary btn-sm sendemail">SEND EMAIL</button>
					</div>
				</#if>
                <div class="row pull-xs-right">
					<button type="submit" class="btn btn-squared btn-outline btn-primary btn-sm">SAVE</button>
                </div>
            </div>
		</div>
		</form>
		<hr/>
	</#list>
<#else>
	No new reviews to approve!
</#if>

<script src="/html/themes/global/vendor/toastr/toastr.js"></script>
<script>
    var toastrOpts = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

	$('.sendemail').on('click', function(e) {
	    e.preventDefault();

        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/admin/control/sendBusinessReviewEmail',
            data: { 'reviewId' : $(this).attr('data-reviewid') },
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                toastr.success('Successfully sent email', 'Success', toastrOpts);
            } else {
                toastr.error('Error sending email', 'Error', toastrOpts);
            }
        });
	})
</script>