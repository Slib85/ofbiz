<link href="<@ofbizContentUrl>/html/css/product/category.css</@ofbizContentUrl>" rel="stylesheet">
<script src="<@ofbizContentUrl>/html/js/product/refinements/jquery.envelopes.refinements.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>

<div class="content container collections">
	<#include "../includes/breadcrumbs.ftl" />
    <div id="jqs-refinements-widget">
        <!-- Begin Filter -->
        <link href="<@ofbizContentUrl>/html/css/product/filter.css</@ofbizContentUrl>" rel="stylesheet">
        <div class="genericPageHeader">
			<div class="section no-margin margin-top-xxs no-padding jqs-category-header desktop-only">
				<div class="pageHeaderText">
					<h1 class="jqs-category-title">Shop By Collection <#if collectionName?default('') != ''> - ${collectionName}</#if></h1>
				</div>
				<div class="headerImage tablet-desktop-only jqs-category-banner">
					<img src="<@ofbizContentUrl>/html/img/category/banners/desktop/shopbycollection.jpg</@ofbizContentUrl>" alt="Shop By Collection" />
				</div>
			</div>
			<div class="headerImage text-center mobile-tablet-only margin-bottom-xxs">
				<img src="<@ofbizContentUrl>/html/img/category/banners/mobile/shopbycollection.jpg</@ofbizContentUrl>" alt="Shop By Collection" />
			</div>
			<h2>Premium Envelopes, Papers and Cardstocks</h2>
			<p>
				Shop our envelope and stationery collections, which include our premium LuxPaper, eye catching Metallics, eco-friendly 100% Recycled and elegant LuxFoil lined products.
				Each collection includes envelopes, cardstock, paper, cards and more great items. Whether your theme is modern, elegant or earth conscious, each collection is an opportunity
				to design and create everything from the perfect invitation suite to professionally branded business stationery
			</p>
		</div>
        <!-- Begin Results -->
        <link href="<@ofbizContentUrl>/html/css/product/collections.css</@ofbizContentUrl>" rel="stylesheet">


        <div class="container search">
            <div id="jqs-refinements-result" class="results">
                <ul>
                <#if mode == 'collections'>
                    <!-- Non SLI Data -->
                    <li class="collection">
                        <div>
                            <a class="product-image" href="<@ofbizUrl>/luxpaper</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/nav/products/shop_by_color/luxpaper.png</@ofbizContentUrl>" alt="LuxPaper Collection" /></a>
                            <a href="<@ofbizUrl>/luxpaper</@ofbizUrl>">
                                <h2>LUXPaper</h2>
                            </a>
                        </div>
                    </li>
                    <li class="collection">
                        <div>
                            <a class="product-image" href="<@ofbizUrl>/search?af=col:metallics&w=*</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/nav/products/shop_by_color/metallic.png</@ofbizContentUrl>" alt="Metallic Collection" /></a>
                            <a href="<@ofbizUrl>/search?af=col:metallics&w=*</@ofbizUrl>">
                                <h2>Metallics</h2>
                            </a>
                        </div>
                    </li>
                    <li class="collection">
                        <div>
                            <a class="product-image" href="<@ofbizUrl>/search?w=recycled</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/nav/products/shop_by_color/recycled.png</@ofbizContentUrl>" alt="Recycled Collection" /></a>
                            <a href="<@ofbizUrl>/search?w=recycled</@ofbizUrl>">
                                <h2>Recycled</h2>
                            </a>
                        </div>
                    </li>
                    <li class="collection">
                        <div>
                            <a class="product-image" href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined col:linedenvelopes</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/nav/products/shop_by_color/lined.png</@ofbizContentUrl>" alt="LuxFoil Lined Collection"/></a>
                            <a href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined col:linedenvelopes</@ofbizUrl>">
                                <h2>LUXFoil Lined</h2>
                            </a>
                        </div>
                    </li>
                </#if>
                </ul>
            </div>
        </div>
        <!-- End Results -->
    </div>
</div>
<script>
    $(function(){
        $('.collection').on('click', function() {
        <#if mode == 'colorGroup'>
            window.location.href = $(this).data('facet');
        <#else>
            window.location.href = '/' + websiteId + '/control/search?w=*&af=cog2:' + $(this).data('facet-id');
        </#if>
        });
    });
</script>