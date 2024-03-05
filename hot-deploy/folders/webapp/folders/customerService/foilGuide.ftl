<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/customerService/paperStocks.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" />

<div class="foldersContainer foldersContainerLimiter paperStocks">
	<div class="foldersHeaderBanner foldersRow textRight">
		<div class="textLeft padding20">
			<h1>Foil Color Guide:</h1>
			<p class="tabletDesktop-inlineBlock">Foil adds a polished look to custom print folders. Foil printing and foil stamping for folders involves the application of heat, pressure, and metallic or non-metallic film to create stunning results. If you're looking to add a touch of flair to your artwork, foil printed folders are a great way to achieve it. </p>
			<div class="foldersTabularRow freeShippingHeader2 marginTop20 tabletDesktop-inlineBlock">
                <i class="fa fa-truck"></i><p> Free Shipping Available on all Custom Orders!</p>
            </div>
		</div>
		<img class="tabletDesktop-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/foilGuideHeader?fmt=jpeg&amp;wid=402</@ofbizScene7Url>" alt="Foil Color Guide Banner">
		<img class="mobile-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/foilGuideMobileBanner?fmt=png-alpha&amp;wid=185</@ofbizScene7Url>" alt="Foil Color Guide Banner">
	</div>
	<div class="mobile-block textCenter freeShippingHeader2Mobile">
        <p><i class="fa fa-truck"></i> Free Shipping Available on all Custom Orders!</p>
    </div>
    <div class="foldersContainerBody padding20 mobileDescription mobile-block">
    	<div class="foldersRow textCenter">
	        <img class="margin20" src="<@ofbizScene7Url>/is/image/ActionEnvelope/foilGuideContentBanner?fmt=png-alpha&amp;wid=265</@ofbizScene7Url>" alt="Foil Color Guide Banner">
	        <p class="marginRight20 marginLeft20 marginBottom20">Foil adds a polished look to custom print folders. Foil printing and foil stamping for folders involves the application of heat, pressure, and metallic or non-metallic film to create stunning results. If you're looking to add a touch of flair to your artwork, foil printed folders are a great way to achieve it. </p>
	    </div>
    </div>
    <div class="mobileAnchorTags mobile-block">
        <a href="#metallicFoil" class="fbc-blue padding10 marginTop20">Metallic Foil</a>
		<a href="#nonMetallicFoil" class="fbc-blue padding10 marginTop10">Non-Metallic Foil</a>
    </div>
	<div class="foldersContainerBody padding20">
		<div class="foldersRow">
			<div class="foldersColumn small12 large9">
				<div class="foldersRow textBold textCenter tabletDesktop-inlineBlock">
					<a href="#metallicFoil" class="fbc-blue shortcuts padding10">Metallic Foil</a>
					<a href="#nonMetallicFoil" class="fbc-blue shortcuts2 padding10">Non-Metallic Foil</a>
				</div>
				<div id="metallicFoil" class="foldersRow">
					<h3 class="ftc-blue marginTop20">Metallic Foil:</h3>
					<p class="marginTop10">Metallic foils have a dazzling shimmer and come in a variety of striking colors like Gold, Copper, Silver, Fuchsia, and Teal. They're superb for adding luminosity elements to your custom printed folders. With eye-catching appeal, they evoke a sense of chic, quality, and flawless classic elegance.
					</p>
				</div>	
			</div>
			<div class="foldersColumn large3">
				<img class="tabletDesktop-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/foilGuideContentBanner?fmt=png-alpha&amp;wid=345</@ofbizScene7Url>" alt="Paper Color Guide Banner">
			</div>
		</div>
		<div class="foldersRow">
			<div class="slideIt-container marginTop20" style="height: 186px;">
				<div class="slideIt-left">
					<i class="fa fa-caret-left ftc-blue"></i>
				</div>
				<div class="slideIt textLeft">
					<div>
						<#list metallicFoilList as foil><div class="materialImage">
							<img src="/html/img/folders/customerService/foilGuide/${foil.img?if_exists}" alt="${foil.foilId?if_exists}" />
							<div class="ftc-blue marginTop10 textCenter">${foil.foilId?if_exists}</div>
						</div></#list>
					</div>
				</div>
				<div class="slideIt-right">
					<i class="fa fa-caret-right ftc-blue"></i>
				</div>
			</div>
		</div>
		<div id="nonMetallicFoil" class="foldersRow">
			<h3 class="ftc-blue marginTop20">Non-Metallic Foil:</h3>
			<p class="marginTop10 ftc-blue">
				Non-metallic foils include Antique Pearl, Burgundy, and Pine. The use of deep, solid foil colors for folders can vibrantly enhance your print work. A subtle hint of gloss complements these intense shades. Non-metallic colors add classy opulence to your custom folders without the "pop" effect of metallic foils. 
			</p>
			<div class="foldersRow">
				<div class="slideIt-container marginTop20" style="height: 186px;">
					<div class="slideIt-left">
						<i class="fa fa-caret-left ftc-blue"></i>
					</div>
					<div class="slideIt textLeft">
						<div>
							<#list nonMetallicFoilList as nmfoil><div class="materialImage">
								<img src="/html/img/folders/customerService/foilGuide/${nmfoil.img?if_exists}" alt="${nmfoil.foilId?if_exists}" />
								<div class="ftc-blue marginTop10 textCenter">${nmfoil.foilId?if_exists}</div>
							</div></#list>
						</div>
					</div>
					<div class="slideIt-right">
						<i class="fa fa-caret-right ftc-blue"></i>
					</div>
				</div>
			</div>
			<div class="foldersFlexRow marginTop20">
				<h3 class="ftc-blue marginTop20">Don't See the Foil Color You're Looking for?</h3>
				<p class="ftc-blue marginTop10 marginBottom10">
					Call us at <a href="tel:1-800-296-4321" class="textBold">1-800-296-4321</a>. Many additional colors, shades and types of foils are
					available on a Special Order basis including Holographic Patterns, Matte Finish Non-Metallic Colors, Pastel Colors,
					Pearlescent Colors, and Tint Foils.
				</p>
			</div>
		</div>
	</div>
</div>