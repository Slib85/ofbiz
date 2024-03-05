<link href="<@ofbizContentUrl>/html/css/landing/productReviews.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet">
<#assign reviews = (productReviews)?if_exists />
<div class="updatedEnvHpBanner">
    <div class="productReviewsBannerText">
        <h1>Our Customers Rule.</h1>
        <p>Envelopes.com has what you need for any mailing or project - but don’t take our word for it. Browse through our variety of product reviews from customers like you and see for yourself!</p>
    </div>
    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/productReviewsBannerRight?fmt=png-alpha&amp;wid=685</@ofbizScene7Url>" alt="Product Reviews Banner"/>
</div>
<div class="content productReviewsContainer">
    <h3>New Featured Reviews</h3>
    <div class="slideIt-container margin-top-xs reviewGradient">
        <div class="slideIt-left">
            <i class="fa fa-chevron-left"></i>
        </div>
        <div class="slideIt text-left">
            <div>
            <#if reviews?has_content>
                <#list reviews as review>
                <div class="featuredReviewBlock">
                ${productRating?if_exists}
                    <div class="reviewImg">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${review.productId}?fmt=png-alpha&amp;wid=130</@ofbizScene7Url>" alt="Product Reviews"/>
                    </div>
                    <div class="reviewData">
                        <div class="reviewBlockTip"></div>
                        <div class="rating-5_0"></div>
                        <p class="reviewTitle">${review.productName}</p>
                        <p class="reviewWeightColor">${review.colorDescription}</p>
                        <div class="reviewContent">
                            <i class="fa fa-quote-left"></i>
                            <p>${review.productReview}</p>
                            <i class="fa fa-quote-right"></i>
                        </div>
                        <p class="reviewDate">${review.createdStamp?date}</p>
                        <p class="reviewName">${review.nickName}</p>
                    </div>
                </div>
                </#list>
            </#if>
            </div>
        </div>
        <div class="slideIt-right">
            <i class="fa fa-chevron-right"></i>
        </div>
    </div>
    <h3 class="reviewsTitle">Product Reviews</h3>
    <#if reviews?has_content>
        <#list reviews as review>
            <div class="reviewBlock">
                <div class="reviewBlockTip"></div>
                <div class="reviewImg">
                    <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${review.productId}?fmt=png-alpha&amp;wid=120&amp;hei=100</@ofbizScene7Url>" alt="Product Reviews"/>
                </div>
                <div class="reviewData">
                    <div class="rating-5_0"></div>
                    <p class="reviewTitle">${review.productName}</p>
                    <p class="reviewWeightColor">${review.colorDescription}</p>
                    <div class="reviewContent">
                        <i class="fa fa-quote-left"></i>
                        <p>${review.productReview}</p>
                        <i class="fa fa-quote-right"></i>
                    </div>
                    <div class="reviewContactInfo">
                        <p class="reviewDate">${review.createdStamp?date}</p>
                        <p class="reviewName">${review.nickName}</p>
                    </div>
                </div>
                <div class="reviewShopNowBtn">Shop Now</div>
            </div>
        </#list>
    </#if>
</div>
