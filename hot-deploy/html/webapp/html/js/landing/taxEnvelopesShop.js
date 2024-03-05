$(document).ready(function() {
	function updatePageContentHeights() {
		$('.jqs-pageContent > a').css('height', 'auto');
		$('.jqs-pageContent img').css('position', 'initial');

		$('.jqs-pageContent').each(function() {
			var maxContentHeight = 0;
			$(this).find('a').each(function() {
				if ($(this).outerHeight() > maxContentHeight) {
					maxContentHeight = $(this).outerHeight();
				}
			});
			$(this).find('a').css('height', (maxContentHeight + 10) + 'px');
		});

		$('.jqs-pageContent img').css('position', 'absolute');
		$('.jqs-pageContent img').each(function() {
			$(this).css('left', (Math.ceil($(this).parent().outerWidth() - $(this).outerWidth()) / 2) + 'px');
		});
	}

	updatePageContentHeights();

	$(window).on('resize', function() {
		updatePageContentHeights();
	});
	// custom play button
	var bnc_ts_btn = $(".bnc_media_button");
	bnc_ts_btn.click(function() {
		bnc_ts_btn.toggleClass("bnc_paused");
		return false;
	});
});
