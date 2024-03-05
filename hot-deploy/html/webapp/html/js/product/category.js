$(function() {
	$(".jqs-product-color-link").click(function() {
		$(this).parent().append('<form action=""><input type="hidden" name="view-all-colors" value="1" /></form>');
		$(this).siblings("form").attr("action", $(this).parent().siblings("div").find(".jqs-product-url").attr("href"));
		$(this).siblings("form").submit();
	});

	$('.jqs-featured-products').find('a').on('click', function (e) {
		e.preventDefault();
		GoogleAnalytics.trackEvent('Category Page', 'Featured Products', $(this).attr('data-sku'));
		window.location.href = $(this).attr('href');
	});
});
