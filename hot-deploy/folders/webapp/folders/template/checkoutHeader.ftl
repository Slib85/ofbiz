<html>
	<#include "../includes/template/headerHead.ftl" />
	<body>
		<div class="foldersCheckoutHeader">
			<div class="foldersTabularRow firstNav">
				<a href="/folders/control/main" class="siteLogo">
					<img src="<@ofbizContentUrl>/html/img/logo/foldersNavy.png</@ofbizContentUrl>" alt="Folders.com" title="Folders.com" />
				</a>
				<div class="geoTrust">
					<img src="<@ofbizContentUrl>/html/img/temp/geotrust.gif</@ofbizContentUrl>" alt="Geo Trust" />
				</div>
				<a id="cartContainer" href="<@ofbizUrl>/cart</@ofbizUrl>" class="cart">
					<span id="jqs-mini-cart-count">(0)</span>
					<i class="fa fa-shopping-cart"></i>
				</a>
			</div>
			<script>$().updateMiniCart();</script>
			<div class="secondNav fbc-blue">
				Need help? Call <a href="tel:1-800-296-4321" class="inheritTextColor">800-296-4321</a>
			</div>
		</div>
