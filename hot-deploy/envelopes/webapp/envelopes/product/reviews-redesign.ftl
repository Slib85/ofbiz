<script type="text/javascript">
	add_review_url = "<@ofbizUrl>/add-review</@ofbizUrl>";
	var do_coupon = <#if requestParameters.co?if_exists == "1">true<#else>false</#if>;
	if (navigator.appVersion.indexOf('MSIE 8.') == -1) {
		$(document).foundation();
	}
</script>
<#assign productReviewerTypes = (productReviews.totals)?if_exists />
<#assign j = 0>
<#if product?exists>
	<#assign pRating = (product.getRating())?default("0") />
<#else>
	<#assign pRating = (productRating)?default("0") />
</#if>
<#assign reviews = (productReviews.reviews)?if_exists />
<div <#if reviews?size gt 0>property="aggregateRating" typeof="AggregateRating" </#if>class="<#if reviews?size gt 0>hreview-aggregate </#if>head reviewsTop">
	<div class="no-padding">
		<h4 class="margin-bottom-xxs section-name item">Customer Reviews <#if product?exists> for <span class="fn">${(product.getName())?if_exists}</span></#if></h4>
        <#if reviews?size gt 0><h5>Average Rating: <span class="rating" property="ratingValue">${pRating?replace("_",".")?replace("0.0", "0")}</span> (<span class="count" property="ratingCount">${reviews?size}</span> Reviews)</h5></#if>
	</div>
	<div class="review-filters no-padding">
		<div class="envelope-select select-review-type">
			<select name="review-type" id="review-type" class="jqs-filter-type">
				<option value="All" selected="selected">By Reviewer Type</option>
				<#if productReviewerTypes?exists && (productReviewerTypes.keySet())?exists>
				<#list productReviewerTypes.keySet() as types>
					<option value="${types}">${types} (${productReviewerTypes.get(types)})</option>
				</#list>
				</#if>
			</select>
		</div><div class="envelope-select select-review-rating">
			<select name="review-rating" id="review-rating" class="jqs-filter-rating">
				<option value="gl" selected="selected">By Rating</option>
				<option value="gl">Highest to Lowest</option>
				<option value="lg">Lowest to Highest</option>
			</select>
		</div><div class="envelope-select select-review-date">
			<select name="review-date" id="review-date" class="jqs-filter-date">
				<option value="gl" selected="selected">By Date</option>
				<option value="gl">Newest to Oldest</option>
				<option value="lg">Oldest to Newest</option>
			</select>
		</div>
		<div data-reveal-id="leave-a-review" class="button-regular button-cta text-center leave-review">Leave A Review</div>
	</div>
</div>
<div id="leave-a-review" class="leave-review-popup reveal-modal small no-padding" data-reveal>
    <div>
        <form id="reviewForm" data-abide="" name="reviewForm" method="post" action="">
            <input type="hidden" name="salesChannelEnumID" value="${salesChannelEnumID?default("ENV_SALES_CHANNEL")}" />
            <input type="hidden" name="productId" value="${productId?if_exists}" />
            <input type="hidden" name="orderId" value="<#if requestParameters.orderId?exists>${requestParameters.orderId}</#if>" />
            <input type="hidden" name="salesChannelEnumId" value="<#if globalContext.webSiteId?default("envelopes") == "ae">AE<#else>ENV</#if>_SALES_CHANNEL" />
            <div class="padding-bottom-xxs popup-title">
                <h3 class="padding-left-xxs">Leave a Review</h3>
                <a class="close-reveal-modal"><i class="fa fa-times"></i></a>
            </div>
            <div class="jqs-response"></div>
            <div class="jqs-offer offer text-center hidden">
                <div class="padding-xxs offer-container">
                    <h4>Thanks for your review!</h4>
                    <p class="offer_text-1">Here is your $10* Coupon Code</p>
                    <div class="offer_code">REV515QRT</div>
                    <p class="offer_text-2">We love hearing what our customers think of us!</p>
                    <p class="offer_text-3">Keep in touch with Envelopes.com on social media, there's always plenty to see.</p>
                    <ul class="inline-list">
                        <li>
                            <a href="//www.facebook.com/Envelopes"><i class="fa fa-facebook-square"></i></a>
                        </li>
                        <li>
                            <a href="//twitter.com/Envelopes_com"><i class="fa fa-twitter-square"></i></a>
                        </li>
                        <li>
                            <a href="//plus.google.com/117470908993913292412/"><i class="fa fa-google-plus-square"></i></a>
                        </li>
                        <li>
                            <a href="//pinterest.com/EnvelopesDotCom/"><i class="fa fa-pinterest-square"></i></a>
                        </li>
                        <li>
                            <a href="//instagram.com/envelopes"><i class="fa fa-instagram"></i></a>
                        </li>
                        <li>
                            <a href="//www.linkedin.com/company/envelopes.com"><i class="fa fa-linkedin-square"></i></a>
                        </li>
                        <li>
                            <a href="//www.envelopes.com/blog/"><i class="fa fa-wordpress"></i></a>
                        </li>
                    </ul>
                    <p class="offer_text-4">
                        *Order must total $100 or more before applicable taxes. Enter the coupon code above at checkout. Offer is not redeemable on
                        previous orders and cannot be combined with promotional coupon codes. Offer CAN be used in conjuction with Loyalty, Trade Pro
                        and Non-Profit Discount Programs. Terms of offer are subject to change at any time.
                    </p>
                </div>
            </div>
            <div class="jqs-review_info row padding-xxs margin-top-xxs">
                <div class="margin-bottom-xs">
                    Overall Envelope Rating: <div class="rating-5_0"></div>
                    <input type="hidden" name="productRating" value="5" />
                </div>
                <div>
                    <input type="text" name="nickName" required="" value="" placeholder="Your Name" />
                    <small class="error">Nick Name is Required</small>
                </div>
                <div>
                    <input type="text" name="userLoginId" value="${emailAddress?if_exists}" placeholder="Email Address (optional and will not be shared)" />
                </div>
                <div>
                    <label class="envelope-select">
                        <select name="describeYourself">
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
                <div>
                    <input type="text" name="productUse" value="" placeholder="What did you use this product for?" />
                </div>
                <div>
                    <textarea class="vertical" name="productReview" required="" value="" placeholder="Your Review"></textarea>
                    <small class="error">Your Review is Required</small>
                </div>
                <div>
                    Did the product meet your expectations?
                    <div>
                        <input id="pq-exceeds" type="radio" name="productQuality" value="Exceeds Expectation" /> <label for="pq-exceeds">Exceeded</label>
                        <input id="pq-met" type="radio" name="productQuality" value="Met Expectation" /> <label for="pq-met">Met</label>
                        <input id="pq-below" type="radio" name="productQuality" value="Below Expectation" /> <label for="pq-below">Below</label>
                    </div>
                </div>
                <div class="jqs-productQualityReason-explain">
                    <input type="text" name="productQualityReason" value="" placeholder="Please explain why?" />
                </div>
                <div>
                    Would you recommend this product to a friend?
                    <div>
                        <input id="r-yes" type="radio" name="recommend" value="Y" /> <label for="r-yes">Yes</label>
                        <input id="r-no" class="padding-left-xxs" type="radio" name="recommend" value="N" /> <label for="r-no">No</label>
                    </div>
                </div>
                <div>
                    <div class="submit-review button-regular button-cta padding-left-xxs padding-right-xxs">Submit Review</div>
                </div>
            </div>
        </form>
    </div>
</div>
<#if !reviews?has_content>
	<div class="no_reviews">
		<a data-reveal-id="leave-a-review" href="#">This product currently has no reviews, be the first to review it by clicking here!</a>
	</div>
</#if>
<div id="paginator-body">
<#if reviews?has_content>
	<#list reviews as review>
		<#--  <div id="sortable-item-${j}" data-item-number="${j}" class="review review<#if j % 2 == 0>Left<#else>Right</#if>">  -->
        <div id="sortable-item-${j}" data-item-number="${j}" class="review">
			<ul class="inline-list margin-top-xxs">
				<li>
					<ul class="inline-list no-margin ratings review-rating" data-value="${review.productRating?if_exists}">
						<li><i class="fa <#if review.productRating?if_exists == 0>fa-star-o<#elseif review.productRating?if_exists?string == '.5'>fa-star-half-o<#else>fa-star</#if>"></i></li>
						<li><i class="fa <#if review.productRating?if_exists <= 1>fa-star-o<#elseif review.productRating?if_exists?string == '1.5'>fa-star-half-o<#else>fa-star</#if>"></i></li>
						<li><i class="fa <#if review.productRating?if_exists <= 2>fa-star-o<#elseif review.productRating?if_exists?string == '2.5'>fa-star-half-o<#else>fa-star</#if>"></i></li>
						<li><i class="fa <#if review.productRating?if_exists <= 3>fa-star-o<#elseif review.productRating?if_exists?string == '3.5'>fa-star-half-o<#else>fa-star</#if>"></i></li>
						<li><i class="fa <#if review.productRating?if_exists <= 4>fa-star-o<#elseif review.productRating?if_exists?string == '4.5'>fa-star-half-o<#else>fa-star</#if>"></i></li>
					</ul>
				</li>
				<li>
					<span>By <#if review.nickName?has_content>${review.nickName}<#else>Anonymous</#if> <#if review.describeYourself?exists>(<span class="review-type" data-value="${review.describeYourself}">Industry: ${review.describeYourself}<span>)</#if> on <span class="review-date" data-value="${review.postedDateTime?date}">${review.postedDateTime?if_exists?date}</span> <#if review.orderId?has_content><span style="color: #00A4E4; display: block;"> <i class="fa fa-check-circle"></i> Verified Buyer</span></#if></span>
				</li>
			</ul>
			<span class="text">${review.productReview?chop_linebreak}</span>
			<span class="question">
				<strong>Met Expectations?</strong>
				${review.productQuality?if_exists} <#if review.productQualityReason?has_content> - ${review.productQualityReason?if_exists}</#if>
			</span>
			<span class="question">
				<strong>Usage?</strong>
				${review.productUse?if_exists}
			</span>
			<span>Recommended: <#if review.recommend?has_content && review.recommend == "Y">Yes, I would recommend this product.<#else>No, I would not recommend this product.</#if></span>
		</div>
		<#if j % 2 == 1><hr class="no-margin" /></#if>

		<#assign j = j + 1>
	</#list>
</#if>
</div>
<#assign page_count = (reviews?size / 10)?floor + 1 />
<#if (page_count > 1)>
<div class="page-selection margin-right-xxs margin-top-xxs margin-bottom-xs">
	<div class="pagination-centered">
		<ul class="pagination no-margin"></ul>
	</div>
</div>
</#if>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/product/reviews.js</@ofbizContentUrl>"></script>
