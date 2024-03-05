<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>

<link rel="stylesheet" href="<@ofbizContentUrl>/html/css/customer-service/about.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" type="text/css" />

<div class="content about">
	<div class="content-breadcrumbs">
		<a href="<@ofbizUrl>/main</@ofbizUrl>">Home</a> > Customer Service > About Us
	</div>
	<div class="section about-title">
		<div>
			<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutUsEnvLogo?fmt=png-alpha</@ofbizScene7Url>" alt="About Envelopes.com" />
			<h5>The Largest Selection of Styles, Sizes &amp; Colors</h5>
			<span>Always find the perfect product for your unique needs!</span>
		</div>
		<img class="bannerImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutUsHeaderEnv?fmt=png-alpha</@ofbizScene7Url>" alt="About Envelopes.com" />
	</div>
	<#if (currentTimestamp?default(now?datetime) gte "2021-08-16 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-19 23:59:59.000"?datetime)>
	<div class="section no-padding">
		<a href="<@ofbizUrl>/applyPromo</@ofbizUrl>?productPromoCodeId=FREE50&saveResponse=true">
			<img class="bannerImg tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-about_us-free50_offer_banner?fmt=png-alpha&wid=1320&ts=2</@ofbizScene7Url>" alt="Envelopes.com is 50 Years Young - Free Shipping on Orders $50+ - CODE: FREE50" />
			<img class="bannerImg mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-about_us-free50_offer_banner-mobile?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Envelopes.com is 50 Years Young - Free Shipping on Orders $50+ - CODE: FREE50" />
		</a>
	</div>
	<#elseif (currentTimestamp?default(now?datetime) gte "2021-08-20 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-08-25 23:59:59.000"?datetime)>
	<div class="section no-padding">
		<a href="<@ofbizUrl>/applyPromo</@ofbizUrl>?productPromoCodeId=ENV50&saveResponse=true">
			<img class="bannerImg tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-about_us-offer_banner?fmt=png-alpha&wid=1320</@ofbizScene7Url>" alt="Envelopes.com is 50 Years Young - $50 OFF + Free Shipping on orders of $250+ - CODE: ENV50" />
			<img class="bannerImg mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-about_us-offer_banner-mobile?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Envelopes.com is 50 Years Young - $50 OFF + Free Shipping on orders of $250+ - CODE: ENV50" />
		</a>
	</div>
	<#elseif (currentTimestamp?default(now?datetime) gte "2021-08-26 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-09-10 23:59:59.000"?datetime)>
	<div class="section no-padding">
		<a href="<@ofbizUrl>/applyPromo</@ofbizUrl>?productPromoCodeId=READY15&saveResponse=true">
			<img class="bannerImg tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-be-office-ready-about-us-desktop-banner-v1?fmt=png-alpha&wid=1320</@ofbizScene7Url>" alt="Be Office Ready - Save 15% + Free Ship on Orders Of $150+" />
			<img class="bannerImg mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-be-office-ready-about-us-mobile-banner-v1?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Be Office Ready - Save 15% + Free Ship on Orders Of $150+" />
		</a>
	</div>
	<#elseif (currentTimestamp?default(now?datetime) gte "2021-09-11 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-09-29 23:59:59.000"?datetime)>
	<div class="section no-padding">
		<img class="bannerImg tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-fity-years-old-company-age-desktop-gold-letters-banner?fmt=png-alpha&wid=1360</@ofbizScene7Url>" alt="Envelopes.com is 50 Years Young" />
		<img class="bannerImg mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-fity-years-old-company-age-mobile-gold-letters-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Envelopes.com is 50 Years Young" />
	</div>
	<#elseif (currentTimestamp?default(now?datetime) gte "2021-09-30 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2021-12-31 23:59:59.000"?datetime)>
	<div class="section no-padding">
		<img class="bannerImg tablet-desktop-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-company-age-white-background-sparkle-theme-desktop-banner?fmt=png-alpha&wid=1360</@ofbizScene7Url>" alt="Envelopes.com is 50 Years Young" />
		<img class="bannerImg mobile-only" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-company-age-white-background-sparkle-theme-mobile-banner?fmt=png-alpha&wid=768</@ofbizScene7Url>" alt="Envelopes.com is 50 Years Young" />
	</div>
	</#if>
	<div class="section no-padding aboutBackground">
		<img class="aboutBackgroundImg" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envAboutUsSideImgN?fmt=png-alpha&amp;wid=420</@ofbizScene7Url>" alt="About Us" />
		<div class="padding-top-xs about-story padding-left-xs">
			<h1>About Us:</h1>
			<p>
				From our start in 1971 as Action Envelope, a Queens, NY envelope printing company, founder Ken Newman served a local clientele of 
				about 100. Today we are Envelopes.com; a leading ecommerce business touching millions.
			</p>
			<p class="margin-top-xxs">
				For 50 years our focus has been on one thing - to provide the largest in-stock selection of sizes, styles, and colors to ensure 
				customers find the perfect product for their unique needs. We combine this with quick shipments, quality printing and services 
				for a top-notch customer experience.
			</p>
			<p class="margin-top-xxs">
				The company experienced many changes before its huge expansion and exciting success. Upon Ken's sudden death in 1993, his wife 
				Sharon Newman had to quickly take  over the company and became one of very few women in the industry to own her own business at 
				the time. With 3 young children, her goal was to support her family and give her son, Seth, the opportunity to join the family 
				business as he always wanted. Seth Newman officially joined the company in 1998 and shifted the focus of the company to the 
				internet -  launching the company's first website soon after. 
			</p>
			<p class="margin-top-xxs">
				Today, Envelopes.com is a leading ecommerce business offering a large in-stock selection of sizes, styles and colors to serve 
				millions of consumers and businesses alike. Through years of ever-changing communication and technology, Envelopes.com has 
				remained at the top of its industry through constant innovation, product expansion and always putting customer needs first. 
			</p>
			<p class="margin-top-xxs">
				Envelopes.com has grown into the ecommerce collective <a class="aboutUsLink" href="https://www.bigname.com/">BIGNAME Commerce</a> 
				- which includes our newest brand <a class="aboutUsLink" href="https://www.folders.com/">Folders.com</a> and will continue to grow with other new brands, like 
				<a class="aboutUsLink" href="https://www.bags.com/">Bags.com.</a>
			</p>
			<div class="brands">
				<a href="https://www.bigname.com"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutBigNameLogo?fmt=png-alpha&amp;wid=169</@ofbizScene7Url>" alt="About Us" /></a>
				<a href="https://www.envelopes.com"><img style="padding-bottom:3px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutEnvLogo?fmt=png-alpha&amp;wid=159</@ofbizScene7Url>" alt="About Us" /></a>
				<a href="https://www.folders.com"><img style="padding-bottom:10px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutFoldersLogo?fmt=png-alpha&amp;wid=135</@ofbizScene7Url>" alt="About Us" /></a>
				<a href="https://www.bags.com"><img style="padding-bottom:3px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutBagsLogo?fmt=png-alpha&amp;wid=95</@ofbizScene7Url>" alt="About Us" /></a>
				<a href="https://www.envelopes.com/luxpaper"><img style="padding-bottom:10px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutLuxLogo?fmt=png-alpha&amp;wid=160</@ofbizScene7Url>" alt="About Us" /></a>
			</div>
		</div>
	</div>
	<div class="section through-the-years">
		<div id="see"></div>
		<h5 style="color:#1d5783;">Envelopes.com: Through the Years</h5>
		<div class="row padding-top-xs">
			<div class="columns small-12 medium-6 large-5 text-center">
				<img src="<@ofbizContentUrl>/html/img/customer-service/about/envThroughTheYearsNow.png</@ofbizContentUrl>" alt="Current Company Website" />
			</div>
			<div class="columns small-12 medium-6 large-7">
				<h5>NOW</h5>
				<p>
					At Envelopes.com, we are always trying to keep things new, fresh and the absolute best for our customers. The homepage was 
					updated to feature all of our popular services, and we made the product pages more convenient to allow customers access to 
					all product sizes, styles and colors without leaving the page. We are also excited to now offer - 
					<a class="aboutUsLink" href="<@ofbizUrl>/directMailing</@ofbizUrl>">Direct Mail Services</a> - a one stop solution for your mail campaigns.
				</p>
			</div>
		</div>
		<hr>
		<div class="row padding-top-xs">
			<div class="columns small-12 medium-6 large-5 text-center">
				<img src="<@ofbizContentUrl>/html/img/customer-service/about/now.png</@ofbizContentUrl>" alt="Current Company Website" />
			</div>
			<div class="columns small-12 medium-6 large-7">
				<h5>2016-2017</h5>
				<p>
					We made browsing the site and purchasing easier with a newly designed website. Envelopes.com was also very 
					excited to officially offer a mobile friendly experience for browising and placing plain orders!
				</p>
			</div>
		</div>
		<hr>
		<div class="row padding-top-xs">
			<div class="columns small-12 medium-6 large-5 text-center">
				<img src="<@ofbizContentUrl>/html/img/customer-service/about/2014.jpg</@ofbizContentUrl>" alt="2014 Company Website" />
			</div>
			<div class="columns small-12 medium-6 large-7">
				<h5>2014-2015</h5>
				<p>
					Envelopes.com has become the leading online supplier of envelopes, paper and coverstock products. Customers can now easily
					purchase hard to find products such as digital sheet sizes, or made to order items. Along with unique product offerings and
					cutting edge services such as White Ink and Variable Data Printing, we were ecstatic to launch a brand new, user-friendly online
					design tool with hundreds of templates. The online designer allows customers to customize just about anything from invitations to
					personalized notecards.
				</p>
			</div>
		</div>
		<hr>
		<div class="row padding-top-xs">
			<div class="columns small-12 medium-6 large-5 text-center">
				<img src="<@ofbizContentUrl>/html/img/customer-service/about/2010.jpg</@ofbizContentUrl>" alt="2010 Company Website" />
			</div>
			<div class="columns small-12 medium-6 large-7">
				<h5>2011 - 2013</h5>
				<p>
					Action Envelope became Envelopes.com to better reflect our focus and leadership of selling Envelopes online. A newly designed page
					and color scheme accompanied the name change along with product and service expansion.
				</p>
			</div>
		</div>
		<hr>
		<div class="row padding-top-xs">
			<div class="columns small-12 medium-6 large-5 text-center">
				<img src="<@ofbizContentUrl>/html/img/customer-service/about/2007.png</@ofbizContentUrl>" alt="2007 Company Website" />
			</div>
			<div class="columns small-12 medium-6 large-7">
				<h5>2008 - 2010</h5>
				<p>
					Our latest website release is really pushing the envelope. Sorry, we couldn't resist. It is packed with new features, and even
					more new products. New functions like shop by color, and shop by collection really give you a full view of our entire product
					line. We are also excited to offer our new custom envelope creator. Creating that perfect envelope has never been easier. Enjoy
					the new site, and of course let us know what we can do to make it better!
				</p>
			</div>
		</div>
		<hr>
		<div class="row padding-top-xs">
			<div class="columns small-12 medium-6 large-5 text-center">
				<img src="<@ofbizContentUrl>/html/img/customer-service/about/2004.png</@ofbizContentUrl>" alt="2004 Company Website" />
			</div>
			<div class="columns small-12 medium-6 large-7">
				<h5>2004 - 2007</h5>
				<p>
					Shopping on-line became the way of business. We stocked even more sizes and window configurations. New trends in shapes, such as
					square envelopes, and exotic finishes, such as metallics, became important to consumers. Our customers' creativity blossomed as we
					offered a huge selection of papers and full color printing on envelopes.
				</p>
			</div>
		</div>
		<hr>
		<div class="row padding-top-xs">
			<div class="columns small-12 medium-6 large-5 text-center">
				<img src="<@ofbizContentUrl>/html/img/customer-service/about/2003.png</@ofbizContentUrl>" alt="2003 Company Website" />
			</div>
			<div class="columns small-12 medium-6 large-7">
				<h5>2003 - 2004</h5>
				<p>
					Action Envelope continued to grow as customers had easy access to products that were previously hard to find. You asked and we
					listened, adding more sizes of envelopes and a wide range of colors to bring attention to your mailings. The explosion of small
					businesses inspired our lower printed minimums, design-on-line features and full line of shipping and packaging envelopes.
				</p>
			</div>
		</div>
		<hr>
		<div class="row padding-top-xs">
			<div class="columns small-12 medium-6 large-5 text-center">
				<img src="<@ofbizContentUrl>/html/img/customer-service/about/2000.png</@ofbizContentUrl>" alt="2000 Company Website" />
			</div>
			<div class="columns small-12 medium-6 large-7">
				<h5>2000 - 2002</h5>
				<p>
					Action Envelope was incorporated in 1971 and operated as a local envelope printing company in the New York metropolitan area. We
					specialized in basic business envelopes printed in 1 or 2 colors for mailings and general office use. The advent of e-commerce
					opened exciting new doors, and we were able to offer our products and services nationwide.
				</p>
			</div>
		</div>
	</div>
	<#--  <div style="background-color: white;border: 1px solid black;">
	<img style="float: right;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/envAboutUsSideImgN?fmt=png-alpha&amp;wid=420</@ofbizScene7Url>" alt="About Us" />
		<div class="">
			<h1>About Us:</h1>
			<p>
				From our start in 1971 as Action Envelope, a Queens, NY envelope printing company, founder Ken Newman served a local clientele of
				about 100. Today we are Envelopes.com; a leading ecommerce business touching millions.
			</p>
			<p class="margin-top-xxs">
				We focus on one thing: to provide the largest in-stock selection of sizes, styles, and colors to ensure customers find the perfect
				product for their unique needs. We combine this with quick shipments, quality printing, and great customer service to ensure total
				customer satisfaction.
			</p>
			<p class="margin-top-xxs">
				The company experienced many changes before its' huge expansion and exciting sucess - but it wasn't always good times. Upon Ken's
				sudden death in 1993, his wife Sharon Newman took over the company. With 3 young children, her goal was to support her family and 
				give her son, Seth, the opportunity to join the family business as he always wanted. Seth Newman officially joined the company in 
				1998 and shifted the focus of the company to the internet, launching the company's first website soon after. These changes 
				(and many others), combined with the expanding team of dedicated and well-trained employees have given Envelopes.com the power 
				to become the No. 1 envelope company - being recognized as a Top 1000 Internet Retailer, and Inc. 5000 fastest growing company 
				for multiple years.
			</p>
			<p class="margin-top-xxs">
				Today, the 48-year young Envelopes.com has grown into the ecommerce collective <a class="aboutUsLink" href="https://www.bigname.com/">BIGNAME Commerce</a> 
				- which includes our newest brand <a class="aboutUsLink" href="https://www.folders.com/">Folders.com</a> and will continue to grow with other new brands, like 
				<a class="aboutUsLink" href="https://www.bags.com/">Bags.com.</a>
			</p>
			<div class="">
				<a href="https://www.bigname.com"><img src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutBigNameLogo?fmt=png-alpha&amp;wid=169</@ofbizScene7Url>" alt="About Us" /></a>
				<a href="https://www.envelopes.com"><img style="padding-bottom:3px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutEnvLogo?fmt=png-alpha&amp;wid=159</@ofbizScene7Url>" alt="About Us" /></a>
				<a href="https://www.folders.com"><img style="padding-bottom:10px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutFoldersLogo?fmt=png-alpha&amp;wid=135</@ofbizScene7Url>" alt="About Us" /></a>
				<a href="https://www.bags.com"><img style="padding-bottom:3px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutBagsLogo?fmt=png-alpha&amp;wid=95</@ofbizScene7Url>" alt="About Us" /></a>
				<a href="https://www.envelopes.com/luxpaper"><img style="padding-bottom:10px;" src="<@ofbizScene7Url>/is/image/ActionEnvelope/aboutLuxLogo?fmt=png-alpha&amp;wid=160</@ofbizScene7Url>" alt="About Us" /></a>
			</div>
		</div>
	</div>  -->
</div>