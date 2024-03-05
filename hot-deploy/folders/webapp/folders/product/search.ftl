<link href="<@ofbizContentUrl>/html/css/folders/product/category.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />

<div class="category margin20">
	<div class="categoryHeader">
		<h1>You've searched for: <span class="ftc-green">${searchString?if_exists}</span></h1>
	</div>
	<div class="categoryResults marginTop10">
		<#if searchItemList?exists>
		<#list searchItemList as searchItem>
		<#if searchItem["ProductInfo"]?exists>
		<a href="<@ofbizUrl>/product/~product_id=${searchItem["ProductInfo"]["Sku"]?if_exists}</@ofbizUrl>">
			<h2 class="ftc-blue">${searchItem["ProductInfo"]["Title"]?if_exists?replace("(.*?)\\s\\-\\s", "$1<br />", "r")}</h2>
			<div>
				<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${searchItem["ProductInfo"]["Sku"]?if_exists}" alt="${searchItem["ProductInfo"]["Description"]?if_exists}" />
			</div>
			<p class="ftc-blue">${searchItem["ProductInfo"]["Minimum Quantity"]?if_exists} Minimum</p>
			<h4 class="ftc-blue"><span class="ftc-green">${searchItem["ProductInfo"]["Sku"]?if_exists}</span> | Size: ${searchItem["ProductInfo"]["Dimensions / Finished Folded Size"]?if_exists}</h4>
		</a>
		</#if>
		</#list>
		</#if>
		<#if searchString?exists && searchString?lower_case?matches(".*tax.*")>
            <a href="/folders/control/blankProduct/~product_id=SF-101-546-TAX">
                <h2 class="ftc-blue">9 x 12 Presentation Folders<br>Standard Two Pocket w/ Gold Foil Tax Return</h2>
                <div>
                    <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/SF-101-546-TAX" alt="9 x 12 Presentation Folders w/ Gold Foil Tax Return in Dark Blue Linen with a standard two pocket design are perfect for holding standard letter size 8 1/2&quot; x 11&quot; paper, documents, print media, brochures, stepped inserts and other professional uses. These folders feature a gold foil stamped design on the front cover which reads, &quot;Tax Return&quot;. The two interior pockets measure 4&quot; in height and the right pocket features card slits to securely hold and display standard size business cards (3 1/2&quot; x 2&quot;). Both pockets are also die-cut in a v-split style to prevent buckling when opening and closing the covers. This folder is created from thick, durable 100lb. cover stock in a deep blue color with a high-quality linen texture. The square corners of this standard size presentation folder were expertly die-cut for a clean, professional look.">
                </div>
                <h4 class="ftc-blue"><span class="ftc-green">SF-101-546-TAX</span> | Size: 9 x 12</h4>
            </a>
		</#if>
	</div>
</div>
