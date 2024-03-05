<link href="<@ofbizContentUrl>/html/css/product/category.css</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/product/shopbyuse.css</@ofbizContentUrl>" rel="stylesheet">
<ol class="breadcrumb">
	<li><a href="<@ofbizUrl>/main</@ofbizUrl>">Home</a></li>
	<li class="active">Shop by Use</li>
</ol>
<div class="content container category">
	<div class="col-xs-2 filters">
		${screens.render("component://envelopes/widget/CategoryScreens.xml#category-menu")}
	</div>
	<div class="col-xs-10 results">
		<div class="shop-by-use">
			<div class="row sbu-head">
				<div class="overlay"></div>
				<div class="col-xs-4">
					<span>Shop by Use</span>
				</div>
				<div class="col-xs-8">
					<img alt="Shop By Use" src="<@ofbizContentUrl>/html/img/category/shopbyuse/shopByUse.png</@ofbizContentUrl>">
				</div>
			</div>
			<div class="row sbu-sub-head">
				<div class="col-xs-4">
					<span>I need an envelope for...</span>
				</div>
				<div class="col-xs-8">
					<span>You can use</span>
				</div>
			</div>
			<#assign headers=['Business Uses', 'Invitations', 'Marketing']>
			<#list headers as header_name>
			<div class="head row">
				${header_name}
			</div>
			<div class="row sbu-body">
				<#assign x=4>
				<#list 1..x as i>
				<div class="col-xs-3 body-left">
					Invoices/Billing
				</div>
				<div class="col-xs-9 body-right row">
					<div class="col-xs-4">
						<a href="/business/regular/10-regular-envelopes">
							<img alt=" " src="//www.envelopes.com/images/products/small/10-display.jpg">
							#10 Regular Envelopes
						</a>
						<span>
							The most popular size<br />
							for invoices, a<br />
							"commercial" or "legal"<br />
							envelope
						</span>
						<br />
						(4 1/8 x 9 1/2)<br />
						from <span>$7.95 / 50</span>
					</div>
					<div class="col-xs-4">
						<a href="/business/regular/10-regular-envelopes">
							<img alt=" " src="//www.envelopes.com/images/products/small/10-display.jpg">
							#10 Regular Envelopes
						</a>
						<span>
							The most popular size<br />
							for invoices, a<br />
							"commercial" or "legal"<br />
							envelope
						</span>
						<br />
						(4 1/8 x 9 1/2)<br />
						from <span>$7.95 / 50</span>
					</div>
					<div class="col-xs-4">
						<a href="/business/regular/10-regular-envelopes">
							<img alt=" " src="//www.envelopes.com/images/products/small/10-display.jpg">
							#10 Regular Envelopes
						</a>
						<span>
							The most popular size<br />
							for invoices, a<br />
							"commercial" or "legal"<br />
							envelope
						</span>
						<br />
						(4 1/8 x 9 1/2)<br />
						from <span>$7.95 / 50</span>
					</div>
				</div>
				</#list>
			</div>
			</#list>
		</div>
	</div>
</div>

