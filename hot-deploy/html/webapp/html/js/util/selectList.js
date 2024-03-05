$('.jqs-selectListItem').off('click.bn-selectList').on('click.bn-selectList', function() {
	if (!$(this).hasClass('slSelected')) {
		$(this).addClass('slSelected').siblings().removeClass('slSelected');

		var parentElement = $('.selectListParent[data-selectListName="' + $(this).attr('data-selectListName') + '"]');

		if (typeof parentElement.attr('data-ignorecaret') == 'undefined') {
			parentElement.html($(this).html()).append(
				$('<i />').addClass('fa fa-caret-down')
			);
        }

		parentElement.trigger('click');

		if (typeof parentElement.attr('data-selectListAction') !== 'undefined' && typeof window[parentElement.attr('data-selectListAction')] === 'function') {
			window[parentElement.attr('data-selectListAction')]($(this));
		}
	}
});
