<link href="<@ofbizContentUrl>/html/css/product/category.css</@ofbizContentUrl>" rel="stylesheet">
<script src="<@ofbizContentUrl>/html/js/product/refinements/jquery.envelopes.refinements.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>
<link href="<@ofbizContentUrl>/html/css/product/filter.css</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/util/paginator.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/product/design-templates-redesign.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />

<div class="content container category design-templates">
    <#include "../../includes/breadcrumbs.ftl" />

	<div id="jqs-refinements-widget">

        <div class="banner type-category jqs-category-header">
			<div class="row no-padding">
				<div class="tablet-desktop-only" style="background-color: #f7f8fa;height:86px;">
					<div class="image-text">
						<h1 class="jqs-category-title"><#if currentView?default("_NA_") == "size-guide">Envelope Sizes<#else>Free Downloadable Envelope Templates</#if></h1>
					</div>
					<div class="jqs-category-banner"><img alt="<#if currentView?default("_NA_") == "size-guide">Envelope Size Guide</#if>" src="<@ofbizContentUrl>/html/img/category/banners/desktop/squareflap.jpg</@ofbizContentUrl>" /></div>
				</div>
				<div class="mobile-only">
					<img src="<@ofbizContentUrl>/html/img/category/banners/mobile/free_download.jpg</@ofbizContentUrl>" alt="Free Download" />
				</div>
			</div>
		</div>

        <div class="category-description no-margin ">
			<#if currentView?default("_NA_") == "size-guide">
			<div class="tabular_row">
                <div>
                    <p>Our envelopes come in many shapes and sizes, plain or with custom printing and in the largest selection of colors.</p>
                    <p>Our size guide will help you select the perfect envelopes to fit your needs. Can't find what you're looking for? We can custom make it for you!</p>
                    <div class="margin-top-xs">
                        <a href="#" data-reveal-id="downloadSizeGuide"><div class="button button-cta round-btn navyblue-bckgrd">Download Envelope Size Guide <i class="fa fa-caret-right"></i></div></a>
                    </div>
                </div>
                <div>
                    <a href="<@ofbizUrl>/shopBySize</@ofbizUrl>">
                        <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shopBySizeBlock?fmt=jpeg&amp;wid=475</@ofbizScene7Url>" alt="Shop By Size Block">
                        <div class="shopBySizeButton">Shop by Size <i class="fa fa-caret-right"></i></div>
                    </a>
                </div>
			</div>
            <div class="env_size_info">
                <p>For custom orders, call <strong>1-877-683-5673</strong></p>
				<p class=""><strong>It's best to choose an envelope that is 1/4" larger than both the height and the width of the piece you are mailing. This allows for easy insertion and controls possible damage to the enclosure.</strong></p>
				<a href="<@ofbizUrl>/product/~product_id=SWATCHBOOK</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/Swatchbook-Block?fmt=png-alpha&amp;wid=515&amp;hei=150</@ofbizScene7Url>" alt="Swatchbook"></a>
            </div>
	        <#else>
	        <div class="positionRel width50">
				<p class="margin-bottom-xxs">Envelopes.com provides the largest in-stock selection of envelope sizes, styles, and colors to ensure you find the perfect product for your unique needs. We combine this with quick shipments, quality printing, and great customer service to ensure total customer satisfaction. No matter the size, style or color you're looking for, we've got it! We also have a low 50 quantity minimum on almost every product! <a href="#" data-reveal-id="stayInTheLoop" style="font-weight: 700; white-space: nowrap; color:#1a345f;">Click Here for a $5 Coupon</a></p>
				<a href="<@ofbizUrl>/product/~product_id=SWATCHBOOK</@ofbizUrl>"><img class="headerImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/Swatchbook-Block?fmt=png-alpha&amp;wid=515&amp;hei=150</@ofbizScene7Url>" alt="Swatchbook"></a>
			</div>
	        </#if>
			
        </div>

		<#if currentView?default("_NA_") != "size-guide">
			<a href="/html/files/addressing.csv" target="_blank"><div class="button button-cta round-btn navyblue-bckgrd">Download Addressing Template</div></a>
		</#if>
		<div class="content-body">
			<div class="result-section">
                <#list templatesCategories as templateCategory>
				<div class="section">
					<#if categories[templateCategory.categoryName]?exists && categories[templateCategory.categoryName]["alternateName"]?exists>
						<#assign catTitle = categories[templateCategory.categoryName]["alternateName"] />
					<#else>
						<#assign catTitle = templateCategory.categoryName />
					</#if>
					<h2 class="text-left">${catTitle}</h2>
					<div class="section-content">
						<div class="content-image">
							<img src="<@ofbizContentUrl>/html/img/category/design-templates/${templateCategory.categoryImage}</@ofbizContentUrl>" alt="${templateCategory.categoryName?if_exists}" title="${templateCategory.categoryName?if_exists}" />
							<div class="margin-top-xs"><#if categories[templateCategory.categoryName]?exists>${categories[templateCategory.categoryName]["description"]?if_exists}</#if></div>
							<#if categories[templateCategory.categoryName]?exists && categories[templateCategory.categoryName]["link"]?exists>
								<a class="button button-cta margin-top-xs margin-bottom-xxs shop-now round-btn navyblue-bckgrd" href="<@ofbizUrl>${categories[templateCategory.categoryName]["link"]}</@ofbizUrl>">View All ${catTitle}</a>
							</#if>
						</div>
						<div class="content-info">
							<div id="dt_sectionHeader" class="info-content<#if currentView?default("_NA_") == "size-guide">-size-guide</#if>">
								<div class="left">
									<h6>Shop by Size/Dimensions</h6>
								</div>
                                <div class="left">
                                    <h6>Colors</h6>
                                </div>
	                            <#if currentView?default("_NA_") != "size-guide">
								<div class="right">
									<h6><img src="<@ofbizContentUrl>/html/img/icon/adobe_acrobat.png</@ofbizContentUrl>" alt="Adobe Acrobat" title="Adobe Acrobat" /> Download PDF Templates</h6>
								</div>
	                            </#if>
							</div>
                            <#list templateCategory.templates as template>
							<div class="info-content<#if currentView?default("_NA_") == "size-guide">-size-guide</#if>">
								<div class="left">
                                <a href="<@ofbizUrl>${template.PRODUCT_URL}</@ofbizUrl>"><#if template.SIZE_CODE?has_content && template.SIZE_CODE != template.sizeDescription>${template.SIZE_CODE} &nbsp;(</#if>${template.sizeDescription}<#if template.SIZE_CODE?has_content && template.SIZE_CODE != template.sizeDescription>)</#if> <i class="fa fa-caret-right"></i></a>
								</div>
                                <div class="left">
                                    ${template.variantCount}
                                </div>
	                            <#if currentView?default("_NA_") != "size-guide">
								<div class="right">
									<a href="${(template.FRONT_URL)?if_exists}" download>Front</a> | <a href="${(template.BACK_URL)?if_exists}" download>Back</a>
								</div>
	                            </#if>
							</div>
                            </#list>
						</div>
					</div>
				</div>
                </#list>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$('.jqs-filter-name').on('click', function() {
			if ($(this).next().css('display') == 'none') {
				$(this).next().slideToggle();
				$(this).find('.fa').removeClass('fa-caret-down').addClass('fa-caret-up');
			}
			else {
				$(this).next().slideToggle();
				$(this).find('.fa').removeClass('fa-caret-up').addClass('fa-caret-down');
			}
		});
	});
</script>

<div id="downloadSizeGuide" class="stay_in_the_loop reveal-modal reveal-modal-limiter" data-reveal>
	<div class="jqs-response"></div>
	<div class="sitl-container padding-top-sm text-center">
		<h4 class="padding-top-xxs padding-left-xs padding-right-xs padding-bottom-xxs jqs-response_title">Enter your email address to receive our easy-to-use Envelope Size Guide!</h4>
		<div class="jqs-sitl_error sitl_error hidden"></div>
		<div class="row padding-top-xxs jqs-form_info">
			<div class="columns small-12">
				<input name="email_address" value="" placeholder="Enter your Email Address" type="text">
			</div>
		</div>
		<div class="button button-cta padding-left-sm padding-right-sm jqs-submit_email">Submit</div>
		<h5 class="jqs-response_text padding-bottom-sm padding-left-md padding-right-md hidden"></h5>
		<a class="close-reveal-modal"><i class="fa fa-times"></i></a>
	</div>
</div>

<script src="<@ofbizContentUrl>/html/js/product/category.js</@ofbizContentUrl>"></script>