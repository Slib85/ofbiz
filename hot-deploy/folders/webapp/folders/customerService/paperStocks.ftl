<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/customerService/paperStocks.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" />

<#if product?exists>
    <#assign variantData = Static["com.envelopes.product.ProductHelper"].getProductColors(delegator, dispatcher, product.getProduct()) />
    <#assign variantColors = variantData.get("colors") />
</#if>

<#assign currentType = "" />
<#if nonBasicAndPremiumMaterialList?has_content>
	<#list nonBasicAndPremiumMaterialList as material>
		<#if material["type"]?exists && currentType != material["type"]>
			<#if currentType != "">
			</div>
			</#if>
		<h2 class="ftc-blue paperColorAndTextures">${material["type"]}</h2>
		<div class="materialList jqs-materialList marginTop20 textCenter">

		<#assign currentType = material["type"] />
		</#if>
		<div class="listItem" data-color="${material["color"]?if_exists}" data-price="${material["price"]?if_exists}">
			<img class="itemImage" src="${material["img"]?if_exists}" alt="${material["name"]?if_exists}"/>
			<div class="colorName ftc-blue">${material["description"]?if_exists}</div>
			<#if material["coating"]?exists><div class="itemCoating ftc-blue"><#if material["coating"] != "0">Coated ${material["coating"]} Side<#else>Non-Coated</#if></div></#if>
			<#if material["price"]?exists><div class="itemPricing ftc-green"><#list 1..material["price"]?number as i>$</#list></div></#if>
		</div>
	</#list>
</#if>

<div class="foldersContainer foldersContainerLimiter paperStocks">
	<div class="foldersHeaderBanner foldersRow textRight">
		<div class="textLeft padding20">
			<h1>Paper Color Guide:</h1>
			<p class="tabletDesktop-inlineBlock">We offer an assortment of high-quality paper & index tag stock for custom folders catering to all types of businesses. Whether you wish to apply your creative genius, or adopt a more understated corporate look, there’s ample choice to help you produce beautiful results. See our paper stock guide for a broad selection of plain, vibrant, and deep colors. </p>
			<div class="foldersTabularRow freeShippingHeader2 marginTop20 tabletDesktop-inlineBlock">
                <i class="fa fa-truck"></i><p> Free Shipping Available on all Custom Orders!</p>
            </div>
		</div>
		<img class="tabletDesktop-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/paperStocksHeader?fmt=jpeg&amp;wid=400</@ofbizScene7Url>" alt="Paper Color Guide Banner">
		<img class="mobile-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/paperStocksMobileBanner?fmt=png-alpha&amp;wid=170</@ofbizScene7Url>" alt="Paper Color Guide Banner">
	</div>
	<div class="mobile-block textCenter freeShippingHeader2Mobile">
        <p><i class="fa fa-truck"></i> Free Shipping Available on all Custom Orders!</p>
    </div>
    <!-- <div class="foldersContainerBody padding20 mobileDescription mobile-block">
    	<div class="foldersRow textCenter">
	        <img class="margin20" src="<@ofbizScene7Url>/is/image/ActionEnvelope/paperStocksContentBanner?fmt=png-alpha&amp;wid=265</@ofbizScene7Url>" alt="Paper Color Guide Banner">
	        <p class="marginRight20 marginLeft20 marginBottom20">We offer an assortment of high-quality paper & index tag stock for custom folders catering to all types of businesses. Whether you wish to apply your creative genius, or adopt a more understated corporate look, there’s ample choice to help you produce beautiful results. See our paper stock guide for a broad selection of plain, vibrant, and deep colors. </p>
	    </div>
    </div>
    <div class="mobileAnchorTags mobile-block">
        <a href="#paperStocks" class="fbc-blue padding10 marginTop20">Paper Stocks</a>
		<a href="#indexStockColors" class="fbc-blue padding10 marginTop10">Index/Tag Stock Colors</a>
    </div> -->
	<div class="foldersContainerBody padding20">
		<div class="foldersRow">
			<div class="foldersColumn small12 large9">
				<div class="foldersRow textBold textCenter tabletDesktop-inlineBlock">
					<a href="#paperStocks" class="fbc-blue shortcuts padding10">Paper Stocks</a>
					<a href="#indexStockColors" class="fbc-blue shortcuts2 padding10">Index/Tag Stock Colors</a>
				</div>
				<div id="paperStocks" class="foldersRow">
					<h3 class="ftc-blue marginTop20"><#if request.getParameter("printed")?has_content && request.getParameter("printed") == "Y">Printed<#else>Plain</#if> Paper Stock Guide:</h3>
					<p class="marginTop10">Paper stock is either coated or uncoated. Coated paper stock has a matte or gloss finish. It comes in a variety of attractive styles like vellum, semi-gloss, marble, fiber, sandstone, linen, and tweed. Matte stocks are more polished than uncoated papers but without the shine of gloss papers. If you prefer eco-friendly "green" paper, our eco-kraft paper is a popular choice. Brighter-colored paper gives printed artwork a vibrant look, whereas darker paper has a subtle effect. Contact one of our dedicated Account Reps if you need paper stock advice for your custom printed folders.
					</p>
				</div>
				
				<div id="filterByColor" bns-smallcolorfilter class="foldersRow hidden">
					<h3 class="ftc-blue marginTop20">Filter by Color</h3>
					<p bns-colorfilterreset class="ftc-blue">Show All</p>
					<div class="colorSwatches">
						<div bns-filterbycolor data-color="White" class="csWhite colSearch"><div>White</div></div>
						<div bns-filterbycolor data-color="Silver" class="csSilver colSearch"><div>Silver</div></div>
						<div bns-filterbycolor data-color="Gold" class="csGold colSearch"><div>Gold</div></div>
						<div bns-filterbycolor data-color="Yellow" class="csYellow colSearch"><div>Yellow</div></div>
						<div bns-filterbycolor data-color="Orange" class="csOrange colSearch"><div>Orange</div></div>
		                <div bns-filterbycolor data-color="Red" class="csRed colSearch"><div>Red</div></div>
						<div bns-filterbycolor data-color="Pink" class="csPink colSearch"><div>Pink</div></div>
						<div bns-filterbycolor data-color="Blue" class="csBlue colSearch"><div>Blue</div></div>
		                <div bns-filterbycolor data-color="Green" class="csGreen colSearch"><div>Green</div></div>
						<div bns-filterbycolor data-color="Purple" class="csPurple colSearch"><div>Purple</div></div>
						<div bns-filterbycolor data-color="Natural" class="csNatural colSearch"><div>Natural</div></div>
						<div bns-filterbycolor data-color="Brown" class="csGroceryBag colSearch"><div>Brown</div></div>
						<div bns-filterbycolor data-color="Gray" class="csGray colSearch"><div>Gray</div></div>
						<div bns-filterbycolor data-color="Black" class="csBlack colSearch"><div>Black</div></div>
					</div>
				</div>	
			</div>
			<div class="foldersColumn large3">
				<img class="tabletDesktop-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/paperStocksContentBanner?fmt=png-alpha&amp;wid=345</@ofbizScene7Url>" alt="Paper Color Guide Banner">
			</div>
			<div bns-bigcolorfilter class="foldersRow">
				<h3 class="ftc-blue">Filter by Color</h3>
				<div class="filterColors">
					<div bns-filterbycolor data-color="White" class="csWhite colSearch"><div class="paperStockColorName">White</div></div>
					<div bns-filterbycolor data-color="Silver" class="csSilver colSearch"><div class="paperStockColorName">Silver</div></div>
					<div bns-filterbycolor data-color="Gold" class="csGold colSearch"><div class="paperStockColorName">Gold</div></div>
					<div bns-filterbycolor data-color="Yellow" class="csYellow colSearch"><div class="paperStockColorName">Yellow</div></div>
					<div bns-filterbycolor data-color="Orange" class="csOrange colSearch"><div class="paperStockColorName">Orange</div></div>
	                <div bns-filterbycolor data-color="Red" class="csRed colSearch"><div class="paperStockColorName">Red</div></div>
					<div bns-filterbycolor data-color="Pink" class="csPink colSearch"><div class="paperStockColorName">Pink</div></div>
					<div bns-filterbycolor data-color="Blue" class="csBlue colSearch"><div class="paperStockColorName">Blue</div></div>
	                <div bns-filterbycolor data-color="Green" class="csGreen colSearch"><div class="paperStockColorName">Green</div></div>
					<div bns-filterbycolor data-color="Purple" class="csPurple colSearch"><div class="paperStockColorName">Purple</div></div>
					<div bns-filterbycolor data-color="Natural" class="csNatural colSearch"><div class="paperStockColorName">Natural</div></div>
					<div bns-filterbycolor data-color="Brown" class="csGroceryBag colSearch"><div class="paperStockColorName">Brown</div></div>
					<div bns-filterbycolor data-color="Gray" class="csGray colSearch"><div class="paperStockColorName">Gray</div></div>
					<div bns-filterbycolor data-color="Black" class="csBlack colSearch"><div class="paperStockColorName">Black</div></div>
				</div>
			</div>
			<!-- <div class="foldersRow marginTop20">
				<#assign counter = 0 />
		        <#list variantColors as variant>
		            <#assign desc = variant.desc?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace(" ", "_") />
		            <#assign weight = variant.weight?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace(" ", "_") />
		            <#assign texture = variant.texture?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace(" ", "_") />

		            <#assign doogmaDesc = variant.desc?default("")?lower_case?replace("[^0-9a-z]", "", "r")?replace("x\\d{2}", "", "r") />
		            <#assign doogmaWeight = variant.weight?default("")?lower_case?replace("[^0-9a-z]", "", "r")?replace("x\\d{2}", "", "r") />
		            <#assign doogmaTexture = variant.texture?default("")?lower_case?replace("[^0-9a-z]", "", "r")?replace("x\\d{2}", "", "r") />

		            <div bns-paperstocklist class="padding10 textCenter" selection-value="${variant.get("productId")}" data-group="${variant.get("group")?if_exists}" data-product-id="${variant.get("productId")}" data-product-color="${variant.get("desc")}" data-hex="${variant.get("hex")?replace("#","")?replace("&x23;","")?replace("&35;","")}">
		                <img class="materialImage" src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/PC-<#if product.isPrintable(false)?c != "true">${variant.get("desc")?replace("[^\\w\\s]", "", "r")?replace("\\s+", "_", "r")?replace("x2f", "")}<#else><#if desc != "">${desc}</#if><#if weight != "">_${weight}</#if><#if texture != "">_${texture}</#if></#if>" />
		                <div class="ftc-blue marginTop10">${variant.get("desc")?if_exists} ${variant.weight?if_exists} ${variant.texture?if_exists}</div>
		            </div>
		            <#assign counter = counter + 1 />
		        </#list>
			</div> -->
		</div>
		<div bns-paperstocklistcontainer class="foldersRow">
			<#list productList as product> 
				<#if product?has_content>
				<div class="padding10 hidden" bns-paperstocklist>
					<img class="materialImage" data-color="${(product.color)?if_exists}" src="<@ofbizScene7Url>/is/image/ActionEnvelope/PC-${(product.name)?default("")?upper_case?replace("[^0-9A-Z\\s]", "", "r")?replace("\\s+", " ", "r")?replace("X25", "")?replace(" ", "_")}?fmt=png-alpha</@ofbizScene7Url>" alt="${(product.name)?if_exists}" />
					<div class="ftc-blue marginTop10 textCenter">${(product.name)?if_exists}</div>
				</div>
				</#if>
			</#list>
		</div>
		<div class="foldersRow">
			<h3 id="indexStockColors" class="ftc-blue marginTop20">Index/Tag Stock Colors:</h3>
			<p class="marginTop10 ftc-blue">
				Our folder index & tag stock are thicker papers that offer more strength and durability. They also produce great results for die-cutting, embossing and foil stamping. Folder tag stock is dense and hard-wearing. Folder index stock is stiffer and bulkier with a smooth finish that absorbs ink well. We offer an assortment of colors and patterns for practicality and style.
			</p>
			<div bns-indexstockcolorlist class="foldersRow paddingTop10">
				<#list vinylMaterialList as material><div class="margin10">
					<img class="materialImage" src="${material["img"]?if_exists}" alt="${material["color"]?if_exists}" />
					<div class="ftc-blue marginTop10 textCenter">${material["color"]?if_exists}</div>
				</div></#list>
			</div>
		</div>
	</div>
</div>

<script>
$('[bns-filterbycolor]').on('click', function() {
	var baseColor = $(this).attr('data-color');

	$('[bns-smallcolorfilter]').removeClass('hidden');
	$('[bns-bigcolorfilter]').addClass('hidden');

	$('[bns-paperstocklist]').each(function(){
		$(this).removeClass('hidden');
		var colorSwatches = $(this).children('img').attr('data-color');
		if(colorSwatches.indexOf(',') != -1){
			newColorSwatches = colorSwatches.split(',');
			for(var i=0; i<newColorSwatches.length; i++){
				if(baseColor != newColorSwatches[i]) {
					$(this).addClass('hidden');
				}
				if(baseColor == newColorSwatches[i]) {
					$(this).removeClass('hidden');
				}
			}
		} else if(baseColor !== colorSwatches) {
			$(this).addClass('hidden');
		}
	});
	$(window).scrollTop(parseInt($('#filterByColor').offset().top));
});

$('[bns-colorfilterreset]').on('click', function() {
	$('[bns-smallcolorfilter]').addClass('hidden');
	$('[bns-bigcolorfilter]').removeClass('hidden');

	$('[bns-paperstocklist]').each(function(){
		$(this).addClass('hidden');
	});
})

</script>

