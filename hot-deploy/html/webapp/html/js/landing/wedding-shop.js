$(document).ready(function() {
	var adjustLists = function() {
		var parent_element;
		var width;
		var largest_height;
		$('.wedding-shop .jqs-set').each(function() {
			largest_height = 0;
			parent_element = $(this);
			parent_element.find('.section').each(function() {
				width = ($('.wedding-shop-content').width() / (parent_element.children('div').css('display') == 'block' ? 1 : parent_element.children('div').length)) - (parseInt($(this).css('margin-left')) * 2) - (parseInt($(this).css('padding-left')) * 2) - (parseInt($(this).css('border-left-width')) * 2);
				column_count = String(parseInt(width / (getFullWidth($(this).children('div.content-list').children('div:first')) + ($(this).find('.border-gray-1').length > 0 ? 2 : 0))));
				$(this).children('div.content-list').css({
					'-moz-column-count':column_count,
					'-webkit-column-count':column_count,
					'column-count':column_count,
				});
				$(this).css({'height':'auto'});
				largest_height = (($(this).innerHeight()) + 2 > largest_height ? ($(this).innerHeight()) + 2 : largest_height);
			}).each(function() {
				$(this).css({'height':largest_height + 'px'});
			});
		});
	}
	$(window).on('resize', function() {
		if (!ignoreIE()) {
			adjustLists();
		}
	}).on('load', function() {
		adjustLists();
	});

	fixedContent();
});
