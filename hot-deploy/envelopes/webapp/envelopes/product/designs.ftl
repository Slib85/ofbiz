<link href="<@ofbizContentUrl>/html/css/product/category.css</@ofbizContentUrl>" rel="stylesheet">
<style>
    .designImage {
        display: block;
        max-width: 175px;
        max-height: 109px;
        margin: 0 auto;
        margin-bottom: 10px;
    }
</style>

<div class="content container design category">
    <div id="jqs-refinements-widget" style="margin-bottom:40px">
        <!-- Begin Filter -->
        <link href="<@ofbizContentUrl>/html/css/product/filter.css</@ofbizContentUrl>" rel="stylesheet">
        <div class="banner type-category jqs-category-header">
            <div class="row no-padding">
                <div class="tablet-desktop-only" style="background-color: #f7f8fa; height:86px;">
                    <div class="image-text">
                        <span class="jqs-category-title">Designs</span>
                    </div>
                    <div class="jqs-category-banner"><img src="<@ofbizContentUrl>/html/img/category/banners/desktop/shopbycolor.jpg</@ofbizContentUrl>" alt="Shop By Color" /></div>
                </div>
                <div class="mobile-only">
                    <img src="<@ofbizContentUrl>/html/img/category/banners/mobile/shopbycolor.jpg</@ofbizContentUrl>" alt="Shop By Color" />
                </div>
            </div>
        </div>
    </div>
<#assign page = (requestParameters.page)?default(0)?number/>
<#assign sortBy = (requestParameters.sort)?default("")/>
<#if widgetResponse.getPaginatedProducts(sortBy)?has_content>
    <#assign products = widgetResponse.getPaginatedProducts(sortBy)[page]/>
    <div id="jqs-refinements-result" class="refinements-results-set">
        <#list products as product>
            <div class="row padding-bottom-xs margin-bottom-xs jqs-product">
                <div class="row no-margin">
                    <div class="medium-8 large-8 columns">
                       <h2 class="jqs-product-name">${product.productSize}</h2>
                    </div>
                </div>
                <div class="slideIt-container margin-top-xs">
                    <div class="slideIt-left">
                        <i class="fa fa-chevron-left"></i>
                    </div>
                    <div class="slideIt text-left">
                        <div class="jqs-variants">
                            <#list product.productVariants as productVariant><div class="jqs-variant">
                                <a class="jqs-variant-url" href="${productVariant.productVariantURL}">
                                    <img class="jqs-variant-image designImage" src="//actionenvelopew2p.scene7.com/is/agm/ActionEnvelope/${productVariant.product_id}?fmt=png&amp;wid=175" onerror="" alt="${productVariant.title}" />
                                    <span class="margin-top-xxs text-center jqs-color">${productVariant.title}</span>
                                </a>
                            </div></#list>
                        </div>
                    </div>
                    <div class="slideIt-right">
                        <i class="fa fa-chevron-right"></i>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <div class="page-selection margin-right-xxs margin-top-xxs margin-bottom-xs">
        <div class="pagination-centered">
            <ul class="pagination no-margin">
                <#if (widgetResponse.getNumberOfPages() > 1) >
                    <li class="arrow arrow-left<#if page == 0> unavailable</#if>">
                    <#--<a data-page="${page - 1}" href="#"><i class="fa fa-chevron-left"></i></a>-->
                    </li><#list 0..(widgetResponse.getNumberOfPages()) - 1 as i><li class="<#if i == page>current</#if>"><a data-page="${i}" href="?page=${i}">${i + 1}</a></li></#list><li class="arrow arrow-right<#if widgetResponse.getNumberOfPages() - 1 == page> unavailable</#if>">
                <#--<a data-page="${page + 1}" href="#"><i class="fa fa-chevron-right"></i></a>-->
                </li>
                </#if>
            </ul>
        </div>
    </div>
<#else>
    <div id="jqs-refinements-result" class="refinements-results-set">
        <h2>Your filters returned no result.</h2>
        <div class="row padding-bottom-xs margin-bottom-xs jqs-product hidden">
            <div class="row no-margin">
                <div class="medium-8 large-8 columns">
                    <a class="jqs-product-url" href="">
                        <h2 class="jqs-product-name"></h2>

                        <h3 class="jqs-product-size">&nbsp; <span class="tablet-desktop-only-inline-block">-</span></h3>

                    </a>
                </div>
                <div class="medium-4 large-4 columns product-url">
                    <span class="jqs-product-color-link" style="color: #009cdd; cursor: pointer;">See All</span></span>
                </div>
            </div>
            <div class="slideIt-container margin-top-xs">
                <div class="slideIt-left">
                    <i class="fa fa-chevron-left"></i>
                </div>
                <div class="slideIt text-left">
                    <div class="jqs-variants">
                        <div class="jqs-variant">
                            <a class="jqs-variant-url" href="">
                                <img class="jqs-variant-image" src="" onerror="" alt="Product" />
                                <span class="margin-top-xxs text-center jqs-color"></span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="slideIt-right">
                    <i class="fa fa-chevron-right"></i>
                </div>
            </div>
        </div>
    </div>
    <script>
        $('.filter-sort').hide();
    </script>
</#if>
</div>
<style>
    .shop-by-size .no-results {
        font-size: 26px;
    }
    .shop-by-size .size-selection-content {
        background-color: #ffffff;
    }
    .shop-by-size .size-selection-content .border-divider {
        border-left: 1px solid #cccccc;
    }
    .shop-by-size .size-selection-content .divider-text {
        position: absolute;
        text-align: center;
        top: 63px;
        left: -10px;
        display: inline-block;
        border-radius: 10px;
        background-color: #00a4e4;
        color: #ffffff;
        font-size: 10px;
        width: 20px;
        height: 20px;
    }
    .shop-by-size .size-selection-content .selection-header {
        font-size: 16px;
    }
    .shop-by-size .size-selection-content .left-select {
        float: left;
        max-width: 175px;
    }
    .shop-by-size .size-selection-content .button {
        width: 125px;
        float: left;
        text-transform: none;
    }
    .shop-by-size .size-selection-content .select-text {
        position: relative;
        font-size: 18px;
        top: 15px;
        width: 60px;
        text-align: right;
        float: left;
        display: inline-block;
    }
    .shop-by-size .size-selection-content .select-list {
        float: left;
        max-width: 100px;
    }
    .shop-by-size .size-selection-content .select-radio {
        float: left;
        margin-top: 7px;
    }
    @media only screen and (max-width: 767px) {
        .shop-by-size .size-selection-content {
            text-align: center;
        }
        .shop-by-size .size-selection-content .left-select,
        .shop-by-size .size-selection-content .button,
        .shop-by-size .size-selection-content .select-text,
        .shop-by-size .size-selection-content .select-list,
        .shop-by-size .size-selection-content .select-radio {
            float: none;
        }
        .shop-by-size .size-selection-content .select-text {
            top: 0px;
        }
        .shop-by-size .size-selection-content .left-select,
        .shop-by-size .size-selection-content .select-list {
            max-width: none;
            margin-left: 0px !important;
        }
    }

    @media only screen and (max-width: 1023px) {
        .shop-by-size .size-selection-content .border-divider {
            border-left: none;
        }
        .shop-by-size .size-selection-content .divider-text {
            display: none;
        }
    }
</style>

