<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/folders/customerService/paperStocks.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" />

<div class="foldersContainer foldersContainerLimiter paperStocks">
	<div class="foldersHeaderBanner foldersRow textRight">
		<div class="textLeft padding20">
			<h1>Plastic Stock Guide:</h1>
			<p class="tabletDesktop-inlineBlock">Create custom plastic folders, plastic binders, vinyl binders, and paper binders. Our materials are durable, smart, and professional-looking. Binders come in assortment of colors, poly thickness (gauge) and capacity. Pockets, business card and small label holders can be added to the front, back and inside of the binder.</p>
			<div class="foldersTabularRow freeShippingHeader marginTop20 tabletDesktop-inlineBlock">
                <i class="fa fa-truck"></i><p> Free Shipping Available on all Custom Orders!</p>
            </div>
		</div>
		<img class="tabletDesktop-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/plasticStockHeader?fmt=jpeg&amp;wid=400</@ofbizScene7Url>" alt="Plastic Stock Guide Banner">
		<img class="mobile-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/plasticStocksMobileBanner?fmt=png-alpha&amp;wid=185</@ofbizScene7Url>" alt="Plastic Stock Guide Banner">
	</div>
	<div class="mobile-block textCenter freeShippingHeaderMobile">
        <p><i class="fa fa-truck"></i> Free Shipping Available on all Custom Orders!</p>
    </div>
    <div class="foldersContainerBody padding20 mobileDescription mobile-block">
    	<div class="foldersRow textCenter">
	        <img class="margin20" src="<@ofbizScene7Url>/is/image/ActionEnvelope/plasticStockContentBanner?fmt=png-alpha&amp;wid=265</@ofbizScene7Url>" alt="Plastic Stock Guide Banner">
	        <p class="marginRight20 marginLeft20 marginBottom20">Create custom plastic folders, plastic binders, vinyl binders, and paper binders. Our materials are durable, smart, and professional-looking. Binders come in assortment of colors, poly thickness (gauge) and capacity. Pockets, business card and small label holders can be added to the front, back and inside of the binder.</p>
	    </div>
    </div>
    <div class="mobileAnchorTags mobile-block">
        <a href="#polyethyleneStocks" class="fbc-blue padding10 marginTop20">Polyethylene Stocks</a>
		<a href="#polypropyleneStocks" class="fbc-blue padding10 marginTop10">Polypropylene Stocks</a>
		<a href="#printingColors" class="fbc-blue marginTop10 padding10">Printing Colors</a>
    </div>
	<div class="foldersContainerBody padding20">
		<div class="foldersRow">
			<div class="foldersColumn small12 large9">
				<div class="foldersRow textBold textCenter tabletDesktop-inlineBlock">
					<a href="#polyethyleneStocks" class="fbc-blue shortcuts padding10">Polyethylene Stocks</a>
					<a href="#polypropyleneStocks" class="fbc-blue shortcuts2 padding10">Polypropylene Stocks</a>
				</div>
				<div id="polyethyleneStocks" class="foldersRow">
					<h3 class="ftc-blue marginTop20">Polyethylene Stocks:</h3>
					<p class="marginTop10">Polyethylene colors have a smooth, matte finish and can be printed with a variety of ink colors. This plastic is very durable, with a flexible or rigid feel and a matte finish. Matte is the most popular finish for binders, plastic folders & plastic totes.
					</p>
				</div>	
			</div>
			<div class="foldersColumn large3">
				<img class="tabletDesktop-inlineBlock" src="<@ofbizScene7Url>/is/image/ActionEnvelope/plasticStockContentBanner?fmt=png-alpha&amp;wid=345</@ofbizScene7Url>" alt="Paper Color Guide Banner">
			</div>
		</div>
		<div class="foldersRow">
			<div class="slideIt-container marginTop20" style="height: 186px;">
				<div class="slideIt-left">
					<i class="fa fa-caret-left ftc-blue"></i>
				</div>
				<div class="slideIt textLeft">
					<div>
						<#list polyethyleneMaterialList as material><div>
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
		<div id="polypropyleneStocks" class="foldersRow marginTop10">
			<h3 class="ftc-blue marginTop20">Polypropylene Stocks:</h3>
			<p class="marginTop10 ftc-blue">
				Polypropylene can be produced as a pure transparent sheet with an almost optical clear quality. Available in clear matte clear smooth, and clear with embedded lines. It’s a very durable, lightweight, and flexible plastic. It’s a popular choice for custom folders. 
			</p>
			<div class="foldersRow">
				<div class="slideIt-container marginTop20" style="height: 186px;">
					<div class="slideIt-left">
						<i class="fa fa-caret-left ftc-blue"></i>
					</div>
					<div class="slideIt textLeft">
						<div>
							<#list polypropyleneMaterialList as material><div>
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
			<div>
				<h3 id="printingColors" class="ftc-blue marginTop20">Printing Colors: </h3>
				<p class="marginTop10 ftc-blue">
					White and clear materials may be printed with all ink colors
				</p>
				<p class="marginTop10 ftc-blue">
					Light color materials may be printed with most ink colors
				</p>
				<p class="marginTop10 ftc-blue marginBottom20">
					Dark color materials may be printed with more opaque colors like: white, colors containing a lot of white or black when mixed, or metallics 
					Consult with us about your project if in any doubt!
				</p>
			</div>
		</div>
	</div>
</div>