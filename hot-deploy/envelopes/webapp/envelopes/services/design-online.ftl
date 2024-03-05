<link href="<@ofbizContentUrl>/html/css/product/category.css</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/services/design-online-redesign.css</@ofbizContentUrl>" rel="stylesheet">
<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>

<div class="content design-online">
	<div class="banner">
		<div class="banner-image" style="background-color: #00a7e0;">
			<#--  <div class="top-header-wrap">
				<span>Add Premium Custom Printing to your Envelopes!</span>
			</div>
			<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-printing-service-desktop-banner-v2?wid=1245&fmt=png</@ofbizScene7Url>" alt="Custom Printing Services | Envelopes.com">  -->
			<img class="tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-printing-service-desktop-banner-revised?wid=1360&fmt=png</@ofbizScene7Url>" alt="Custom Printing Services | Envelopes.com">
			<img class="mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/custom-printing-service-mobile-banner-revised?wid=768&fmt=png</@ofbizScene7Url>" alt="Custom Printing Services | Envelopes.com">
		</div>
	</div>

	<#if (currentTimestamp?default(now?datetime) gte "2021-08-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-04 11:59:59.000"?datetime)>
	<div class="even-row">
		<div class="ui-nav">
			<div>
				<div>
					<a href="#popular_template" data-ui-element>
						<div class="ui-button mobile-left">
							<div class="left-icon">
								<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/mail-icon?fmt=png-alpha&amp;wid=30</@ofbizScene7Url>" />
							</div>
							<div class="right-text">Popular Templates</div>
						</div>
					</a>
				</div>
				<div>
					<a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" target="_blank" data-ui-element>
						<div class="ui-button mobile-left">
							<div class="left-icon">
								<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/resizing-square-icon?fmt=png-alpha&amp;wid=30</@ofbizScene7Url>" />
							</div>
							<div class="right-text">Shop by <br />Size</div>
						</div>
					</a>
				</div>
				<div>
					<a href="<@ofbizUrl>/shopByColor</@ofbizUrl>" target="_blank" data-ui-element>
						<div class="ui-button mobile-left">
							<div class="left-icon">
								<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ink-drop-icon?fmt=png-alpha&amp;wid=30</@ofbizScene7Url>" />
							</div>
							<div class="right-text">Shop by <br />Color</div>
						</div>
					</a>
				</div>
				<#--  <div>
					<a href="#high_quality_printing_service" data-ui-element>
						<div class="ui-button mobile-left">
							<div class="left-icon">
								<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/square-pencil-icon?fmt=png-alpha&amp;wid=33</@ofbizScene7Url>" />
							</div>
							<div class="right-text">High Quality Printing Services</div>
						</div>
					</a>
				</div>  -->
			</div>
			<div>
				<div>
					<a href="#color_printing_options" data-ui-element>
						<div class="ui-button mobile-left">
							<div class="left-icon">
								<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eyedropper-tool-icon?fmt=png-alpha&amp;wid=30</@ofbizScene7Url>" />
							</div>
							<div class="right-text">Color Printing Options</div>
						</div>
					</a>
				</div>
				<div>
					<a href="#design_template_artwork" data-ui-element>
						<div class="ui-button mobile-right">
							<div class="left-icon">
								<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/upload-cloud-arrow-icon?fmt=png-alpha&amp;wid=37</@ofbizScene7Url>" />
							</div>
							<div class="right-text">Upload Your Own Artwork</div>
						</div>
					</a>
				</div>
				<div>
					<a href="#contact_us" data-ui-element>
						<div class="ui-button mobile-right">
							<div class="left-icon">
								<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hand-shake-icon?fmt=png-alpha&amp;wid=37</@ofbizScene7Url>" />
							</div>
							<div class="right-text">Contact Us</div>
						</div>
					</a>
				</div>
				<#--  <div>
					<a href="#" data-reveal-id="stayInTheLoop" data-ui-element>
						<div class="ui-button mobile-right navyblue-bckgrd" style="height: 62px;">
							<div class="left-icon">
								<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/dollar-money-sign-icon?fmt=png-alpha&amp;wid=30</@ofbizScene7Url>" />
							</div>
							<div class="right-text white-text">Click Here For a $10 Coupon</div>
						</div>
					</a>
				</div>  -->
			</div>

		</div>
	</div>
	<#else>
	<#-- CUSTOM NAVIGATION -->
    <nav role="bnc_customprint_nav1">
        <input type="checkbox" id="customprint-button">
        <label for="customprint-button" onclick></label>
        <ul>
            <li><a href="#popular_template" title="Popular Templates"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/mail-icon?fmt=png-alpha&amp;wid=22</@ofbizScene7Url>" /> Popular Templates</a></li>
            <li><a href="<@ofbizUrl>/shopBySize</@ofbizUrl>" title="Shop By Sizes" target="_blank"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/shop-by-size-ruler-icon?fmt=png-alpha&amp;wid=30</@ofbizScene7Url>" /> Shop By Sizes</a></li>
            <li><a href="<@ofbizUrl>/shopByColor</@ofbizUrl>" title="Shop By Color" target="_blank"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/ink-drop-icon-v2?fmt=png-alpha&amp;wid=22</@ofbizScene7Url>" /> Shop By Color</a></li>
			<li><a href="#color_printing_options" title="Color Printing Options"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/eyedropper-tool-icon?fmt=png-alpha&amp;wid=22</@ofbizScene7Url>" /> Color Printing Options</a></li>
			<li><a href="#design_template_artwork" title="Upload Your Own Artwork"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/upload-cloud-arrow-icon?fmt=png-alpha&amp;wid=24</@ofbizScene7Url>" /> Upload Your Own Artwork</a></li>
			<li><a href="#contact_us" title="Contact Us"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hand-shake-icon?fmt=png-alpha&amp;wid=24</@ofbizScene7Url>" /> Contact Us</a></li>
        </ul>
    </nav>
	</#if>
	<div class="odd-row">
		<div class="row">
			<div class="clearfix">
				<img class="right-img" src="<@ofbizScene7Url>/is/image/ActionEnvelope/high-quality-envelopes-custom-printing-services?fmt=png-alpha&amp;wid=492</@ofbizScene7Url>" alt="Custom Printing Service | Envelopes.com" />
				<h2 class="section-header navyblue">
					Make your own envelopes online with
					our easy to use design tools.
				</h2>
				<p class="section-description">Get the most out of our high-quality envelope design services; we offer a wide selection of custom-printed envelopes, cards, invitations, and so much more. You can create personalized envelopes with your own design or use one of our design templates to get started. Simply upload your own artwork or reuse a design from one of your previous orders. With a wide variety of paper stocks and sizes to choose from, your custom envelopes will definitely stand out!</p>
			</div>
		</div>
	</div>

	<div class="even-row">
		<div class="row">
			<h1 class="customH1 navyblue">Personalized Envelopes &amp; Printed Envelopes</h1>
			<h2 class="section-header popular-template navyblue" id="popular_template">Popular Templates</h2>
			<div class="rowBlock">
				<div class="topSplit">
					<div class="slider-block tablet-desktop-only">
						<div class="featuredProducts no-padding">
							<div>
								<h3 class="section-header slider-title">Popular Business Templates</h3>
							</div>
							<div class="slideIt-container margin-top-md">
								<div class="slideIt-left">
									<i class="fa fa-chevron-left"></i>
								</div>
								<div class="slideIt text-left">
									<div>
										<#list businessStaticCategoryDesigns as businessStaticCategoryDesign><div class="templateColumn">
											<#if businessStaticCategoryDesign.img?exists>
											<a href="<@ofbizUrl>/product?product_id=${businessStaticCategoryDesign.productId?if_exists}&designId=${businessStaticCategoryDesign.designId?if_exists}</@ofbizUrl>">
												<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hp_et-${businessStaticCategoryDesign.img?if_exists}?fmt=png-alpha&amp;wid=150&amp;hei=150</@ofbizScene7Url>&amp;ts=1" alt="${businessStaticCategoryDesign.name?if_exists}" class=""/>
												<div class="text-center margin-top-xxs">${businessStaticCategoryDesign.name?if_exists}<br />${businessStaticCategoryDesign.typeName?if_exists}</div>
												<#else>
												<a href="<@ofbizUrl>/product?product_id=${businessStaticCategoryDesign.productId}&amp;showUpload=1</@ofbizUrl>">
													<div class="uploadContainer">
														<div class="${businessStaticCategoryDesign.uploadClass?if_exists}">
															<i class="fa fa-upload"></i>
															<div class="upload-text">
																<span>Upload</span>
																<span>Print-Ready File</span>
															</div>
														</div>
													</div>
													<#--  <div class="uploadContainer">
														<div class="${businessStaticCategoryDesign.uploadClass?if_exists}">
															<div class="PR-topFloatingText">Upload</div>
															<div class="PR-bottomFloatingText">Print-Ready File</div>
															<i class="fa fa-upload"></i>
														</div>
													</div>  -->
													<div class="text-center margin-top-xxs">${businessStaticCategoryDesign.name?if_exists}<br /><span class="designKey">*</span>${businessStaticCategoryDesign.typeName?if_exists}</div>
													</#if>
													<#if businessStaticCategoryDesign.colorCount?has_content><div class="text-center colorAvailable">${businessStaticCategoryDesign.colorCount} Envelope Color<#if businessStaticCategoryDesign.colorCount gt 1>s</#if></div></#if>
												</a>
											</div></#list>
									</div>
								</div>
								<div class="slideIt-right">
									<i class="fa fa-chevron-right"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="topSplit">
					<div class="slider-block tablet-desktop-only">
						<div class="featuredProducts no-padding">
							<div>
								<h3 class="section-header slider-title">Popular Social Templates</h3>
							</div>
							<div class="slideIt-container margin-top-md">
								<div class="slideIt-left">
									<i class="fa fa-chevron-left"></i>
								</div>
								<div class="slideIt text-left">
									<div>
										<#list socialStaticCategoryDesigns as socialStaticCategoryDesign><div class="templateColumn">
											<#if socialStaticCategoryDesign.img?exists>
											<a href="<@ofbizUrl>/product?product_id=${socialStaticCategoryDesign.productId?if_exists}&designId=${socialStaticCategoryDesign.designId?if_exists}</@ofbizUrl>">
												<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hp_et-${socialStaticCategoryDesign.img?if_exists}?fmt=png-alpha&amp;wid=150&amp;hei=100</@ofbizScene7Url>&amp;ts=1" alt="${socialStaticCategoryDesign.name?if_exists}" class="templatePadding"/>
												<div class="text-center margin-top-xxs">${socialStaticCategoryDesign.name?if_exists}<br />${socialStaticCategoryDesign.typeName?if_exists}</div>
												<#else>
												<a href="<@ofbizUrl>/product?product_id=${socialStaticCategoryDesign.productId}&amp;showUpload=1</@ofbizUrl>">
													<div class="uploadContainer">
														<div class="${socialStaticCategoryDesign.type?if_exists}">
															<i class="fa fa-upload"></i>
															<div class="upload-text">
																<span>Upload</span>
																<span>Print-Ready File</span>
															</div>
														</div>
													</div>
													<div class="text-center margin-top-xxs">${socialStaticCategoryDesign.name?if_exists}<br /><span class="designKey">*</span>${socialStaticCategoryDesign.typeName?if_exists}</div>
													</#if>
													<#if socialStaticCategoryDesign.colorCount?has_content><div class="text-center colorAvailable">${socialStaticCategoryDesign.colorCount} Envelope Color<#if socialStaticCategoryDesign.colorCount gt 1>s</#if></div></#if>
												</a>
											</div></#list>
									</div>
								</div>
								<div class="slideIt-right">
									<i class="fa fa-chevron-right"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="rowBlock">
				<div class="topSplit">
					<div class="slider-block tablet-desktop-only">
						<div class="featuredProducts no-padding">
							<div>
								<h3 class="section-header slider-title">Popular Remittance Templates</h3>
							</div>
							<div class="slideIt-container margin-top-md">
								<div class="slideIt-left">
									<i class="fa fa-chevron-left"></i>
								</div>
								<div class="slideIt text-left">
									<div>
										<#list remittanceStaticCategoryDesigns as remittanceStaticCategoryDesign><div class="templateColumn">
											<#if remittanceStaticCategoryDesign.img?exists>
											<a href="<@ofbizUrl>/product/~category_id=REMITTANCE/~product_id=${remittanceStaticCategoryDesign.productId?if_exists}</@ofbizUrl>">
												<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hp_et-${remittanceStaticCategoryDesign.img?if_exists}?fmt=png-alpha&amp;wid=150&amp;hei=140</@ofbizScene7Url>&amp;ts=3" alt="${remittanceStaticCategoryDesign.name?if_exists}"/>
												<div class="text-center margin-top-xxs">${remittanceStaticCategoryDesign.name?if_exists}</div>
												<#if remittanceStaticCategoryDesign.colorCount?has_content><div class="text-center colorAvailable">${remittanceStaticCategoryDesign.colorCount} Envelope Color<#if remittanceStaticCategoryDesign.colorCount gt 1>s</#if></div></#if>
												<#else>
												<a href="<@ofbizUrl>/product?product_id=${remittanceStaticCategoryDesign.productId}&amp;showUpload=1</@ofbizUrl>">
													<div class="uploadContainer">
														<div class="${remittanceStaticCategoryDesign.type?if_exists}">
															<i class="fa fa-upload"></i>
															<div class="upload-text">
																<span>Upload</span>
																<span>Print-Ready File</span>
															</div>
														</div>
													</div>
													<div class="text-center margin-top-xxs">${remittanceStaticCategoryDesign.name?if_exists}<br />${remittanceStaticCategoryDesign.typeName?if_exists}</div>
													<#if remittanceStaticCategoryDesign.colorCount?has_content><div class="text-center colorAvailable">${remittanceStaticCategoryDesign.colorCount} Envelope Color<#if remittanceStaticCategoryDesign.colorCount gt 1>s</#if></div></#if>
													</#if>
												</a>
											</div></#list>
									</div>
								</div>
								<div class="slideIt-right">
									<i class="fa fa-chevron-right"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="topSplit">
					<div class="slider-block tablet-desktop-only">
						<div class="featuredProducts no-padding">
							<div>
								<h3 class="section-header slider-title">Popular Open End Templates</h3>
							</div>
							<div class="slideIt-container margin-top-md">
								<div class="slideIt-left">
									<i class="fa fa-chevron-left"></i>
								</div>
								<div class="slideIt text-left">
									<div>
										<#list openEndStaticCategoryDesigns as openEndStaticCategoryDesign><div class="templateColumn">
											<#if openEndStaticCategoryDesign.img?exists>
											<a href="<@ofbizUrl>/product?product_id=${openEndStaticCategoryDesign.productId?if_exists}&designId=${openEndStaticCategoryDesign.designId?if_exists}</@ofbizUrl>">
												<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/hp_et-${openEndStaticCategoryDesign.img?if_exists}?fmt=png-alpha&amp;wid=150&amp;hei=150</@ofbizScene7Url>&amp;ts=1" alt="${openEndStaticCategoryDesign.name?if_exists}"/>
												<div class="text-center margin-top-xxs">${openEndStaticCategoryDesign.name?if_exists}<br />${openEndStaticCategoryDesign.typeName?if_exists}</div>
												<#else>
												<a href="<@ofbizUrl>/product?product_id=${openEndStaticCategoryDesign.productId}&amp;showUpload=1</@ofbizUrl>">
													<div class="uploadContainer">
														<div class="${openEndStaticCategoryDesign.type?if_exists}">
															<i class="fa fa-upload"></i>
															<div class="upload-text">
																<span>Upload</span>
																<span>Print-Ready File</span>
															</div>
														</div>
													</div>
													<div class="text-center margin-top-xxs">${openEndStaticCategoryDesign.name?if_exists}<br /><span class="designKey">*</span>${openEndStaticCategoryDesign.typeName?if_exists}</div>
													</#if>
													<#if openEndStaticCategoryDesign.colorCount?has_content><div class="text-center colorAvailable">${openEndStaticCategoryDesign.colorCount} Envelope Color<#if openEndStaticCategoryDesign.colorCount gt 1>s</#if></div></#if>
												</a>
											</div></#list>
									</div>
								</div>
								<div class="slideIt-right">
									<i class="fa fa-chevron-right"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="odd-row">
		<div class="row">
			<h2 class="section-header navyblue" id="color_printing_options">Color Printing Options</h2>

			<div class="colorPrintingOptionsContent">
				<div>
					<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/one-color-printing-icon?fmt=png-alpha</@ofbizScene7Url>" alt="One Color Printing | Envelopes.com" />
					<h3>One Color Printing</h3>
					<p class="lp-grey">One color printing means that your design contains only one ink color. Grayscale designs, which contain a wide range of spaced tones ranging from black to white through intermediate shades of gray, are also considered one-color printing.</p>
					<div class="videoContainer">
						<iframe src="https://www.youtube.com/embed/nK_cJr0K-Yk" frameborder="0" allowfullscreen></iframe>
					</div>
				</div>
				<div>
					<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/two-color-printing-icon?fmt=png-alpha</@ofbizScene7Url>" alt="Two Color Printing | Envelopes.com" />
					<h3>Two Color Printing</h3>
					<p class="lp-grey">Two-color printing means that your design contains two different ink colors. Your 2 chosen colors can be combined to create a wide variety of hues. </p>
					<div class="videoContainer">
						<iframe src="https://www.youtube.com/embed/7ntwY57BBxY" frameborder="0" allowfullscreen></iframe>
					</div>
				</div>
				<div>
					<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/four-color-printing-icon?fmt=png-alpha</@ofbizScene7Url>" alt="Four Color Printing | Envelopes.com" />
					<h3>Four Color Printing</h3>
					<p>Designs that contain more than two colors require an ink process called full-color printing, which is also known as CMYK (Cyan, Magenta, Yellow and Key). These four ink colors can be combined to print an endless variety of colors.</p>
					<div class="videoContainer">
						<iframe src="https://www.youtube.com/embed/s5GW45MJdi4" frameborder="0" allowfullscreen></iframe>
					</div>
				</div>
			</div>
			<div class="prod-time-content padding-top-xl">
				<div>
					<h2 class="no-border navyblue">Production Time:</h2>
				</div>
				<div>
					<div>
						<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelope-mail-icon?fmt=png-alpha</@ofbizScene7Url>" alt="Envelope Icon | Envelopes.com" />
						<h2 class="no-border sub-h2 navyblue">Standard Production: <br><span>3 Business Days</span></h2>
					</div>
				</div>
				<#--  <div>
					<div>
						<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/rush-envelope-mail-icon?fmt=png-alpha</@ofbizScene7Url>" alt="Rush Envelope Icon | Envelopes.com" />
						<h2 class="no-border sub-h2 navyblue">Rush Production: <br><span>2 Business Days</span></h2>
					</div>
				</div>  -->
			</div>
		</div>
	</div>
	<div class="even-row">
		<div class="row">
			<h2 class="section-header navyblue" id="high_quality_printing_service">We Also Offer High-Quality Printing Services</h2>
			<div class="hqps-block">
				<div class="videoContainer">
					<iframe src="https://www.youtube.com/embed/4rrr5OSdpx0" frameborder="0" allowfullscreen></iframe>
				</div>
				<p><strong>White Ink Printing</strong>
					White ink is a special, unique ink that prints best on dark-colored papers and stocks. White ink can be printed with CMYK printing if the design contains white and additional color elements. For example, if your design contains red, blue, orange and white, these colors can all be printed in the same design. This is an upgrade from traditional knock-out printing, which only gives the illusion of white printing. With white ink printing, your mailings and stationery are sure to stand out. For a fresh and unique look, try printing crisp white ink on dark papers, metallics, and more. White ink printing is available on a wide variety of styles and colors.</p>
			</div>
			<div class="hqps-block">
				<div class="videoContainer">
					<iframe src="https://www.youtube.com/embed/J6-6jDgBbbg" frameborder="0" allowfullscreen></iframe>
				</div>
				<p><strong>Recipient Addressing</strong>
					Take the hassle out of addressing your own personalized envelopes. Use our recipient addressing services to create and save your address book with our easy-to-use address template or enter your list line by line. You'll be able to preview each and every address. Not yet impressed? You can also edit fonts, sizes, spacing, placement, colors, and so much more with the online design tool. <a href="<@ofbizUrl>/addressing</@ofbizUrl>" title="Recipient Addressing | Envelopes.com">Learn More</a></p>
			</div>
		</div>
	</div>

	<div class="odd-row">
		<div class="row">
			<div class="design-template-block dtb-description">
				<h2 class="section-header navyblue" id="design_template_artwork">Use Our Design Templates or Upload Your Own Artwork</h2>
				<p>Get the most out of our high-quality envelope design services; we offer a wide selection of custom-printed envelopes, cards, invitations, and so much more. You can create personalized envelopes with your own design or use one of our design templates to get started. Simply upload your own artwork or reuse a design from one of your previous orders. With a wide variety of paper stocks and sized to choose from, your custom envelopes will definitely stand out!</p>
			</div>
			<div class="design-template-block dtb-list">
				<h3 class="navyblue">Acceptable File Formats:</h3>
				<ul>
					<li class="pdf-icon">PDF</li>
					<li class="eps-icon">EPS</li>
					<li class="psd-icon">PSD</li>
					<li class="png-icon">PNG</li>
					<li class="jpg-icon">JPG</li>
					<li class="ai-icon">AI</li>
					<li class="tiff-icon">TIFF</li>
				</ul>
			</div>
			<div class="design-template-block dtb-template">
				<h2 class="section-header navyblue">Use our Online Designer and Ready-Made Templates</h2>
				<p>At Envelopes.com, we make envelope customization easier than ever before. Simply select a pre-designed template and customize the text, fonts, colors, and placement. You can even add your own images and logos to any design!</p>
				<div class="template-content">
					<h3 class="navyblue">Download our Templates to Get Started:</h3>
				</div>
				<div class="template-content">
					<div class="link-table">
						<div class="link-column1"></div>
						<div class="link-column2"></div>
						<div class="link-row row1">
							<div class="link-cell cell1"><a href="<@ofbizUrl>/product?product_id=43687&designId=14937</@ofbizUrl>" title="Business Envelopes">Business Envelopes</a></div>
							<div class="link-cell cell2"><a href="<@ofbizUrl>/product?product_id=72940&designId=15384</@ofbizUrl>" title="Invitation Envelopes">Invitation Envelopes</a></div>
						</div>
						<div class="link-row row2">
							<div class="link-cell cell1"><a href="<@ofbizUrl>/product/~category_id=REMITTANCE/~product_id=35548;</@ofbizUrl>" title="Remittance Envelopes">Remittance Envelopes</a></div>
							<div class="link-cell cell2"><a href="<@ofbizUrl>/product?product_id=8193&designId=15328</@ofbizUrl>" title="Open End Envelopes">Open End Envelopes</a></div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<div class="even-row">
		<div class="row">
			<h2 class="section-header navyblue" id="contact_us">Contact Us</h2>
			<p class="lp-grey">If you have any questions about designing custom envelopes, our experienced customer service representatives are standing by to help.
				Call, email, or chat with a representative online for additional information and further assistance.</p>

			<div class="color-highlights contact-us">
				<section>
					<div class="content white-background heightOverWrite">
						<div class="block-wrap">
							<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/phone-icon?fmt=png-alpha</@ofbizScene7Url>" alt="Contact Us | Envelopes.com" />
						</div>
						<div class="block-wrap">
							<h3 class="nomargin navyblue">Call:</h3>
							<p class="lp-grey">1-877-683-5673</p>
						</div>
					</div>
				</section>
				<section>
					<div class="content white-background heightOverWrite">
						<div class="block-wrap">
							<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelope-mail-icon?fmt=png-alpha</@ofbizScene7Url>" alt="Email | Envelopes.com" />
						</div>
						<div class="block-wrap">
							<h3 class="nomargin navyblue">Email:</h3>
							<p class="lp-grey">service@envelopes.com</p>
						</div>
					</div>
				</section>
				<section onclick="olark('api.box.expand')" data-ui-element>
					<div class="content white-background heightOverWrite">
						<div class="block-wrap">
							<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/comment-icon?fmt=png-alpha</@ofbizScene7Url>" alt="Live Chat | Envelopes.com" />
						</div>
						<div class="block-wrap">
							<h3 class="nomargin navyblue">Live Chat</h3>
						</div>
					</div>
				</section>
			</div>

		</div>
	</div>

	<div class="odd-row">
		<div class="row">
			<div class="collection-wrap">
				<div class="collection-block">
					<div class="collection-content">
						<a href="<@ofbizUrl>/category/</@ofbizUrl>~category_id=PAPER" title="Stationery" class="dis-block">
							<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/stationery-custom-printing-services?fmt=png-alpha</@ofbizScene7Url>" alt="Stationery | Envelopes.com" />
							<div>Stationery</div>
						</a>
					</div>
				</div>
				<div class="collection-block">
					<div class="collection-content">
						<a href="<@ofbizUrl>/category/</@ofbizUrl>~category_id=BUSINESS" title="Business Envelopes" class="dis-block">
							<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/business-envelopes-custom-printing-services?fmt=png-alpha</@ofbizScene7Url>" alt="Business Envelopes | Envelopes.com" />
							<div>Business Envelopes</div>
						</a>
					</div>
				</div>
				<div class="collection-block">
					<div class="collection-content">
						<a href="<@ofbizUrl>/category</@ofbizUrl>?af=st:notecards" title="Invitations" class="dis-block">
							<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/invitations-custom-printing-services?fmt=png-alpha</@ofbizScene7Url>" alt="Invitations | Envelopes.com" />
							<div>Invitations</div>
						</a>
					</div>
				</div>
				<div class="collection-block">
					<div class="collection-content">
						<a href="<@ofbizUrl>/category/</@ofbizUrl>~category_id=INVITATION" title="Invitations Envelopes" class="dis-block">
							<img class="" src="<@ofbizScene7Url>/is/image/ActionEnvelope/invitation-envelopes-custom-printing-services?fmt=png-alpha</@ofbizScene7Url>" alt="Invitations Envelopes | Envelopes.com" />
							<div>Invitations Envelopes</div>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<button onclick="topFunction()" id="backToTheTop" title="Go to top"><span>Back to Top</span></button>
<script>
	//Get the button
	var mybutton = document.getElementById("backToTheTop");

	// When the user scrolls down 20px from the top of the document, show the button
	window.onscroll = function() {scrollFunction()};

	function scrollFunction() {
		if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
			mybutton.style.display = "block";
		} else {
			mybutton.style.display = "none";
		}
	}

	// When the user clicks on the button, scroll to the top of the document
	function topFunction() {
		document.body.scrollTop = 0;
		document.documentElement.scrollTop = 0;
	}
</script>
<link href="<@ofbizContentUrl>/html/css/services/design-online-revised2.css</@ofbizContentUrl>" rel="stylesheet">