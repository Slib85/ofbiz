<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/customerService/indexTagStockColors.css</@ofbizContentUrl>" />

<div class="foldersContainer indexTagStockColors">
	<div class="foldersContainerContent">
		<div class="foldersContainerHeader">
			<h1 class="ftc-blue">Index/Tag Stock Guide:</h1>
		</div>
		<div class="foldersContainerBody">
			<p class="marginTop10 ftc-blue">
				Our folder index & tag stock are thicker papers that offer more strength and durability. They also produce great results for die-cutting, embossing and foil stamping. Folder tag stock is dense and hard-wearing. Folder index stock is stiffer and bulkier with a smooth finish that absorbs ink well. We offer an assortment of colors and patterns for practicality and style.
			</p>
			<h3 class="ftc-blue marginTop20">Index/Tag Stock Colors:</h3>
			<div class="foldersFlexRow">
				<#list vinylMaterialList as material><div class="margin10">
					<img class="materialImage" src="${material["img"]?if_exists}" alt="${material["color"]?if_exists}" />
					<div class="ftc-blue marginTop10 textCenter">${material["color"]?if_exists}</div>
				</div></#list>
			</div>
		</div>
	</div>
</div>
