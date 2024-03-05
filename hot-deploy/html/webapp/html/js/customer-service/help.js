$(document).ready(function() {
	$('.jqs-faq-pagination > li').on('click', function() {
		var current_page = parseInt($(this).parent().find('.current').data('page'));
		var total_pages = $(this).parent().children().length - 2;

		if ($(this).hasClass('arrow-left') && !$(this).hasClass('unavailable')) {
			if (current_page != 1) {
				$(this).siblings('[data-page="' + current_page + '"]').removeClass('current');
				current_page--;
				$(this).siblings('[data-page="' + current_page + '"]').addClass('current');
			}
		}
		else if ($(this).hasClass('arrow-right') && !$(this).hasClass('unavailable')) {
			if (current_page != total_pages) {
				$(this).siblings('[data-page="' + current_page + '"]').removeClass('current');
				current_page++;
				$(this).siblings('[data-page="' + current_page + '"]').addClass('current');
			}
		}
		else if ($(this).data('page') && !$(this).hasClass('current')) {
			$(this).siblings('[data-page="' + current_page + '"]').removeClass('current');
			current_page = parseInt($(this).data('page'));
			$(this).addClass('current');
		}

		$(this).parent().find('.arrow-left').removeClass('unavailable')
		if (current_page == 1) {
			$(this).parent().find('.arrow-left').addClass('unavailable');
		}

		$(this).parent().find('.arrow-right').removeClass('unavailable');
		if (current_page == total_pages) {
			$(this).parent().find('.arrow-right').addClass('unavailable');
		}

		$('[class*="jqs-page"]').each(function() {
			$(this).removeClass('hidden');
			if (!$(this).hasClass('jqs-page' + current_page)) {
				$(this).addClass('hidden');
			}
		});

		return false;
	});
});
