$(document).ready(function() {
	var color_list_default_height = getFullHeight($('.color-list'));

 	function alternateButtonFunctionality(element, class_to_change_to, class_to_change_from) {
		var current_text = element.children('div').html();
		var alternate_text = element.children('div').attr('data-alternate-text');
		element.removeClass(class_to_change_from).addClass(class_to_change_to).unbind('click').bind('click', function() {
			if (class_to_change_to.match('less')) {
				showLess($(this));
			}
			else {
				showMore($(this));
			}
		}).children('div').attr('data-alternate-text', current_text).html(alternate_text);
 	}

 	var showMore = function(element) {
 		$('.color-list').animate({'height':getFullHeight($('.color-list').children('div')) + 'px'});
		alternateButtonFunctionality(element, 'jqs-show-less', 'jqs-show-more');
 	}

 	var showLess = function(element) {
		$('.color-list').animate({'height':color_list_default_height + 'px'});
		alternateButtonFunctionality(element, 'jqs-show-more', 'jqs-show-less');
 	}

	$('.jqs-show-more').bind('click', function() {
		showMore($(this));
	});
});
