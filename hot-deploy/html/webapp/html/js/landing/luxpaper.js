$(function() {
	$('span.read-more').on('click', function() {
		$(this).css('display', 'none');
		$(this).siblings('span.hidden-desc').css('display', 'inline');
	});

	$('span.read-less').on('click', function() {
		$('span.read-more').css('display', 'inline');
		$(this).parent('span.hidden-desc').css('display', 'none');
	});
});
