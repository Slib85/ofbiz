<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/customerService/topBlanks.css</@ofbizContentUrl>" />

<div class="foldersContainer topStyles">
	<div class="foldersContainerContent noPadding">
		<div class="foldersContainerHeader">
			<h1>MOST POPULAR BLANK STYLES:</h1>
			<p>The best-selling, most popular styles available from Folders.com!</p>
		</div>
	</div>
    <#list productList as product>
    <div class="foldersContainerContent marginTop20">
        <div class="foldersContainerBody noPadding">
            <div class="foldersTabularRow descriptionRow">
                <div>
                    <a href="<@ofbizUrl>/blankProduct/~product_id=${product.getId()}</@ofbizUrl>"><img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}" alt="${product.getDescription()?if_exists}" /></a>
                    <div class="textCenter marginTop10">Shown In: <strong>${product.getColor()?if_exists}</strong></div>
                </div>
                <div class="padding20">
                    <h3 class="ftc-blue"><a href="<@ofbizUrl>/blankProduct/~product_id=${product.getId()}</@ofbizUrl>">${product.getName()?if_exists}</a></h3>
                    <p class="marginTop10 ftc-blue">
                        ${product.getDescription()?if_exists}
                    </p>
                </div>
                <div class="padding20">
                    <p class="ftc-blue">Size: <span class="ftc-green">${product.getSize()?if_exists}</span></p>
                    <p class="ftc-blue marginTop10">Available In: <span class="ftc-green">${Static["com.envelopes.product.ProductHelper"].getProductColors(delegator, product.getProduct()).get("colors")?size} Colors</span></p>
                    <a href="<@ofbizUrl>/blankProduct/~product_id=${product.getId()}</@ofbizUrl>" class="foldersButton buttonGreen marginTop10">Shop Now <i class="fa fa-caret-right"></i></a>
                </div>
            </div>
        </div>
    </div>
    </#list>
</div>
