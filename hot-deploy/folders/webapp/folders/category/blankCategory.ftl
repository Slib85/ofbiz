<style>
    .foldersCategory .categoryHeader {
        margin-top: 10px;
        height: 210px;
        position: relative;
        overflow: hidden;
    }
    .categoryHeader .headerText {
        position: absolute;
        text-align: left;
        height: 100%;
        left: 20px;
        max-width: 600px;
    }
    .categoryHeader .headerImage {
        text-align: right;
    }
    .categoryHeader .headerImage img {
        max-width: none;
    }
    .categoryBody .categoryContent {
        margin-top: 10px;
    }
    .categoryBody .categoryContent .categoryContentHeader {
        background-color: #e5e5e5;
        padding: 15px;
    }
    .categoryBody .categoryContent .categoryContentBody {
        background-color: #ffffff;
    }
    .categoryBody .categoryContent .categoryContentBody > div:first-child {
        width: 450px;
        vertical-align: top;
    }
    .categoryBody .categoryContent .categoryContentBody .slideIt-container {
        height: 230px;
    }
    .categoryBody .categoryContent .categoryContentBody .slideIt-container .slideIt > div > div {
        width: 150px;
    }
    @media only screen and (max-width: 767px) {
        .categoryHeader .headerText {
            left: 10px;
            width: calc(100% - 20px);
        }   
        .categoryBody .categoryContent .categoryContentBody {
            display: block !important;
        }
        .categoryBody .categoryContent .categoryContentBody > div {
            display: block;
        }
        .categoryBody .categoryContent .categoryContentBody > div:first-child {
            width: 300px;
            margin: auto;
        }
        .categoryBody .categoryContent .categoryContentBody .firstResult > div:first-child {
            width: 100px;
        }
        .categoryBody .categoryContent .categoryContentBody .slideIt-container {
            height: 160px;
        }
        .categoryBody .categoryContent .categoryContentBody .slideIt-container .slideIt > div > div {
            width: 100px;
        }
    }
</style>

<div class="foldersContainer foldersCategory foldersNewLimiter">
    <div class="tabletDesktop-inlineBlock marginTop20">
        <#include "../includes/breadcrumbs.ftl" />
    </div>
    <div class="categoryHeader" style="background-color: #56a5e7;">
        <div class="headerText foldersTabularRow">
            <div>
                <h1>${seoH1}</h1>
                <p style="color: #ffffff;">${seoH2}</p>
            </div>
        </div>
        <div class="headerImage tabletDesktop-block">
<#--            <img src="<@ofbizContentUrl>/html/folders/img/category/blank9x12FoldersCoverStock.png</@ofbizContentUrl>" alt="${seoH1}" />-->
        </div>
    </div>
    <div class="categoryBody">
        <#list subCategories.entrySet() as subCategory>
            <#assign categoryId = subCategory.getValue().get("categoryId")/>
            <#assign products = subCategory.getValue().get("products")/>
        <div class="categoryContent">
            <h3 class="categoryContentHeader">${subCategory.getKey()}</h3>
            <div class="categoryContentBody foldersTabularRow">
                <div>
                    <div class="foldersTabularRow firstResult">
                        <div class="textCenter noPadding">
                            <img style="margin-top: 25px" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${products[0]}?fmt=png-alpha&wid=190&hei=170</@ofbizScene7Url>" alt="" />
                        </div>
                        <div>
                            <ul>
                                <#if subCategory.getValue().get("bullet1")?has_content>
                                    <li>${subCategory.getValue().get("bullet1")}</li>
                                </#if>
                                <li>${products.size()} Colors Available</li>
                            </ul>
                            <#if subCategory.getValue().get("min") gt 0>
                                <div>Minimum Quantity ${subCategory.getValue().get("min")}</div>
                            </#if>
                            <a href="<@ofbizUrl>/product/~category_id=${categoryId}/~product_id=${products[0]}</@ofbizUrl>">
                                <div class="foldersButton buttonGreen foldersRow">Shop Now<i class="fa fa-caret-right" style="margin:3px 0 0 5px;"></i></div>
                            </a>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="slideIt-container">
                        <div class="slideIt-left">
                            <i class="fa fa-chevron-left"></i>
                        </div>
                        <div class="slideIt">
                            <div style="height: 75%; margin-top: 45px; text-align: left">
                                <#list products as product>

                                <div>
                                    <a href="<@ofbizUrl>/product/~category_id=${categoryId}/~product_id=${product}</@ofbizUrl>"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${product}?fmt=png-alpha&wid=190&hei=170</@ofbizScene7Url>" alt="${product}" /></a>
                                </div>
                                </#list>
                            </div>
                        </div>
                        <div class="slideIt-right">
                            <i class="fa fa-chevron-right"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </#list>

    </div>

</div>
