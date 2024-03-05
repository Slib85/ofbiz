<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/customerService/paperStocks.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" />

<div class="foldersContainer foldersContainerLimiter paperStocks">
	<div class="foldersHeaderBanner foldersRow textRight">
		<div class="textLeft padding20">
			<h1>Vinyl Material Guide:</h1>
			<p class="tabletDesktop-inlineBlock">Our vinly material are available in a range of vivid colors to perfectly suit your needs. Protection and durability are ensured using the best quality vinyl for your binders and folders. Contact us if you need any help designing your beautiful vinyl binders.</p>
			<div class="foldersTabularRow freeShippingHeader marginTop20 tabletDesktop-inlineBlock">
                <i class="fa fa-truck"></i><p> Free Shipping Available on all Custom Orders!</p>
            </div>
		</div>
		<img class="tabletDesktop-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/vinylMaterialsBanner?fmt=jpeg&amp;wid=400</@ofbizScene7Url>" alt="Vinyl Materials Banner">
		<img class="mobile-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/vinylMaterialsMobileBanner?fmt=png-alpha&amp;wid=185</@ofbizScene7Url>" alt="Vinyl Materials Banner">
	</div>
	<div class="mobile-block textCenter freeShippingHeaderMobile">
        <p><i class="fa fa-truck"></i> Free Shipping Available on all Custom Orders!</p>
    </div>
	<div class="foldersContainerBody padding20">
		<div class="foldersRow">
			<div class="foldersColumn small12 large9">
				<img class="paperBinderContentImg mobile-block" src="<@ofbizScene7Url>/is/image/ActionEnvelope/vinylMaterialsContentBanner?fmt=png-alpha&amp;wid=265</@ofbizScene7Url>" alt="Foil Color Guide Banner">
				<div id="polyethyleneStocks" class="foldersRow">
					<h3 class="ftc-blue marginTop20">Vinyl Material Colors:</h3>
					<p class="marginTop10">A stunning assortment of colors to get your creative juices flowing. Whether you want your vinyl binders to make a dramatic, or a subtle impact, you’ll find plenty of options in our vinyl color guide.
					</p>
				</div>	
			</div>
			<div class="foldersColumn large3">
				<img class="tabletDesktop-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/vinylMaterialsContentBanner?fmt=png-alpha&amp;wid=345</@ofbizScene7Url>" alt="Paper Color Guide Banner">
			</div>
		</div>
		<div class="foldersRow">
			<div class="slideIt-container marginTop20" style="height: 186px;">
				<div class="slideIt-left">
					<i class="fa fa-caret-left ftc-blue"></i>
				</div>
				<div class="slideIt textLeft">
					<div>
						<#list vinylMaterialList as material><div>
							<img class="materialImage" src="${material["img"]?if_exists}" alt="${material["color"]?if_exists}" />
							<div class="ftc-blue marginTop10 textCenter">${material["color"]?if_exists}</div>
						</div></#list>
					</div>
				</div>
				<div class="slideIt-right">
					<i class="fa fa-caret-right ftc-blue"></i>
				</div>
			</div>
		</div>
	</div>
</div>

