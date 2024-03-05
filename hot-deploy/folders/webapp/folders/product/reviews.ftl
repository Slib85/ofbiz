<#if productReviews?has_content && (productReviews.totals["All"] gt 0 || productReviews.totals["Other"] gt 0)>
    <#assign reviewCounter = 0 />
    <#list productReviews["reviews"] as review>
        <#if reviewCounter % 2 == 0>
            <#if reviewCounter != 0>
            </div>
            </#if>
        <div class="foldersTabularRow reviewRow">
        </#if>

        <div class="review reviewLeft marginBottom10" data-item-number="0" id="sortable-item-0">
            <div class="foldersTabularRow reviewTabularRow">
                <div>
                    <p class="reviewTop starRating-${(review.productRating)?replace(".", "_")}"></p>
                </div>
                <div>By ${review.nickName?if_exists}  <#if review.describeYourself?has_content></#if> on <span class="review-date" data-value="${review.postedDateTime?date}">${review.postedDateTime?date}</span> <span class="marginTop5" style="color: #00A4E4; display: block;"><i class="fa fa-check-circle"></i> Verified Buyer</span></span></span></span></div>
            </div>
            <p class="marginTop10 marginBottom10">${review.productReview?if_exists}</p>
            <p><strong>Met Expectations?</strong> ${review.productQuality?if_exists}<br><strong>Usage?</strong> ${review.productUse?if_exists}<#if review.recommend?has_content><br><strong>Recommended:</strong> <#if review.recommend == "Y">Yes, I would recommend this product.<#else>No, I would not recommend this product.</#if></#if></p>
        </div>

        <#assign reviewCounter = reviewCounter + 1 />
    </#list>
</div>
<#else>
This product has no reviews.
</#if>

<div id="productReview" class="bnRevealContainer">
    <div class="bnRevealHeader fbc-blue">
        <h3>Leave a Review</h3>
        <i class="fa fa-times jqs-bnRevealClose"></i>
    </div>
    <div class="bnRevealBody">
        <div class="jqs-submitReviewResponse hidden submitReviewResponse ftc-green">Thank You</div>
        <form id="reviewForm" name="reviewForm" method="post" action="" data-bigNameValidateForm="productReview">
            <input type="hidden" name="productId" value="${productId}" />
            <input type="hidden" name="orderId" value="<#if requestParameters.orderId?exists>${requestParameters.orderId}</#if>" />
            <input type="hidden" name="salesChannelEnumId" value="FOLD_SALES_CHANNEL" />
            <div class="marginTop10">
                Overall Product Rating: <div class="rating-5_0"></div>
                <input type="hidden" name="productRating" value="5">
            </div>
            <div class="marginTop10">
                <input type="text" name="nickName" value="" placeholder="Your Name" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Nick Name" />
            </div>
            <div>
                <input type="text" name="userLoginId" value="" placeholder="Email Address (optional and will not be shared)" />
            </div>
            <div>
                <input type="text" name="productUse" value="" placeholder="What did you use this product for?" />
            </div>
            <div>
                <textarea name="productReview" value="" placeholder="Your Review" data-bigNameValidateType="required" data-bigNameValidateErrorTitle="Your Review"></textarea>
            </div>
            <div>
                <div>
                    Did the product meet your expectations?
                    <div>
                        <input id="pq-exceeds" type="radio" name="productQuality" value="Exceeds Expectation"> <label for="pq-exceeds">Exceeded</label>
                        <input class="marginLeft20" id="pq-met" type="radio" name="productQuality" value="Met Expectation"> <label for="pq-met">Met</label>
                        <input class="marginLeft20" id="pq-below" type="radio" name="productQuality" value="Below Expectation"> <label for="pq-below">Below</label>
                    </div>
                </div>
            </div>
            <div class="marginTop10">
                Would you recommend this product to a friend?
                <div>
                    <input id="r-yes" type="radio" name="recommend" value="Y"> <label for="r-yes">Yes</label>
                    <input class="marginLeft20" id="r-no" class="padding-left-xxs" type="radio" name="recommend" value="N"> <label for="r-no">No</label>
                </div>
            </div>
            <div class="foldersButton buttonGold marginTop10" data-bigNameValidateSubmit="productReview" data-bigNameValidateAction="submitProductReview">Submit Review</div>
        </form>
    </div>
</div>
<script>
    var parentStarRatingElement = $('[bns-starrating]').parent();
    $('[bns-starrating]').remove();
    parentStarRatingElement.append(
        $('<span />').attr('bns-starrating', '').addClass('starRating-${(product.getRating())?replace(".", "_")}').append(
            <#if product.getRating() == '0_0'>
                $('<a />').attr('data-bnReveal', 'productReview').attr('href', '#').html('Be the first to write a review.')
            <#else>
                $('<a />').attr('href', '#reviews').append(
                    $('<span />').html('(${product.getReviews().reviews?size}) Reviews')
                )
            </#if>
        )
    );
</script>