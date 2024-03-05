$(document).ready(function() {
	var pixels_per_star = 22;
	var current_rating = 1;

	function checkProductQualityReason() {
		if ($('input[name="productQuality"]:checked').val() == "Exceeds Expectation" ||
			$('input[name="productQuality"]:checked').val() == "Below Expectation") {
			$('.jqs-productQualityReason-explain').show();
		}
		else {
			$('.jqs-productQualityReason-explain').hide();
		}
	}

	$("#reviewForm [class^='rating']").on("mousemove", function(e) {
		rating = Math.ceil((e.pageX - $(this).offset().left) / pixels_per_star);
		$(this).removeClass().addClass("rating-" + rating + "_0");
	}).on("mouseleave", function() {
		$(this).removeClass().addClass("rating-" + current_rating + "_0");
	}).on("click", function(e) {
		current_rating = Math.ceil((e.pageX - $(this).offset().left) / pixels_per_star)
		$("[name='productRating']").val(current_rating);
	});

	$('form[name="reviewForm"]').on('submit', function() {
		return false;
	}).on('valid.fndtn.abide', function () {
		$('.leave-review-popup > *').spinner(true);
		$.ajax({
			type: "POST",
			url: add_review_url,
			data: $('form[name="reviewForm"]').serialize(),
			dataType:'json',
			cache: false
		}).done(function(data) {
			$('.leave-review-popup > *').spinner(false);
			$(".jqs-response").removeClass("form-success").removeClass("form-error");
			if (data.success) {
				$('.jqs-review_info').hide();

				if (do_coupon) {
					//$('.jqs-offer').removeClass('hidden');
					$('#leave-a-review .popup-title h3').html('');
				}
				else {
					$('.jqs-response').addClass('form-success').html('Your review has been submitted!');
					setTimeout(function() {
						$('#leave-a-review').foundation('reveal', 'close');
					}, 3000);
				}
			}
			else {
				$(".jqs-response").addClass("form-error").html("There was an error processing your review.");
			}
		});
	});

	$('.submit-review').on('click', function() {
		$('form[name="reviewForm"]').submit();
	});

	$('input[name="productQuality"]').on('click', function() {
		checkProductQualityReason();
	});

	checkProductQualityReason();

	if (do_coupon) {
		$('#leave-a-review').foundation('reveal', 'open');
	}

	var total_allowed_per_page = 10;
	var current_page_number = 1;
	var zero_start = false;
	var item_list = [];

	var updateDisplayList = function() {
		$("#paginator-body").html("");
		var total_listed = 0;
		var total_listed_allowance = total_allowed_per_page;

		for (i = 0; i < item_list.length; i++) {
			if (item_list[i].active) {
				total_listed++;
				if (!(total_listed - 1 < (current_page_number - 1) * total_allowed_per_page) && total_listed_allowance > 0) {
					// total_listed_allowance--;
					// $("#paginator-body").append(item_list[i].content);
					// if (i % 2 == 1) {
					// 	$("#paginator-body").append(
					// 		$("<hr />").addClass('no-margin')
					// 	)
					// }
					$("#paginator-body").append(item_list[i].content);
					if (total_listed_allowance % 2 == 1) {
						$("#paginator-body").append(
							$("<hr />").addClass('no-margin')
						)
					}
					total_listed_allowance--;
				}
			}
		}
	}

	var updatePagination = function() {
		current_page_number = 1
		var total_listing = 0;

		for (i = 0; i < item_list.length; i++) {
			if (item_list[i].active) {
				total_listing++;
			}
		}

		$('.customer-reviews ul.pagination').html('');
		if (total_listing > total_allowed_per_page) {
			$('.customer-reviews ul.pagination').append(
				$('<li>').addClass('arrow unavailable arrow-left').append(
					$('<a>').attr({'href':'#reviews'}).append(
						$('<i>').addClass('fa fa-chevron-left')
					).bind('click', function() {
						pageSelection($(this));
					})
				)
			);
			for (i = 1; i <= Math.ceil(total_listing / total_allowed_per_page); i++) {
				$('.customer-reviews ul.pagination').append(
					$('<li>').addClass((i == 1 ? 'current' : '')).append(
						$('<a>').attr({'data-page':i, 'href':'#reviews'}).html(i).bind('click', function() {
							pageSelection($(this));
						})
					)
				);
			}
			$('.customer-reviews ul.pagination').append(
				$('<li>').addClass('arrow arrow-right').append(
					$('<a>').attr({'href':'#reviews'}).append(
						$('<i>').addClass('fa fa-chevron-right')
					).bind('click', function() {
						pageSelection($(this));
					})
				)
			);
		}
	}

	var pageSelection = function(element) {
		if (element.parent().hasClass('unavailable') || element.parent().hasClass("current")) {
			return false;
		}
		else if (element.parent().hasClass('arrow-right')) {
			current_page_number++;
			updateDisplayList();
			element.parent().parent().find('a[data-page=' + current_page_number + ']').parent().addClass("current").siblings(".current").removeClass("current");
		}
		else if (element.parent().hasClass('arrow-left')) {
			current_page_number--;
			updateDisplayList();
			element.parent().parent().find('a[data-page=' + current_page_number + ']').parent().addClass("current").siblings(".current").removeClass("current");
		}
		else {
			current_page_number = element.data("page");
			updateDisplayList();
			element.parent().addClass("current").siblings(".current").removeClass("current");
		}

		element.parent().parent().find('.arrow-right').removeClass('unavailable');
		element.parent().parent().find('.arrow-left').removeClass('unavailable');

		if (element.parent().siblings().length - 1 == current_page_number) {
			element.parent().parent().find('.arrow-right').addClass('unavailable');
		}
		else if (current_page_number == 1) {
			element.parent().parent().find('.arrow-left').addClass('unavailable');
		}

		$().formatPagination();
	}

	var sortItemList = function(sort_type, sort_by, old_item_list, new_item_list) {
		new_item_list = (typeof new_item_list !== 'undefined' ? new_item_list : []);
		var best_match;

		for (i = 0; i < old_item_list.length; i++) {
			if (best_match) {
				best_match = (sort_by == "lg" ?
					(old_item_list[i][sort_type] < best_match[sort_type] ? old_item_list[i] : best_match) :
					(old_item_list[i][sort_type] > best_match[sort_type] ? old_item_list[i] : best_match)
				);
			}
			else {
				best_match = old_item_list[i];
			}
		}

		if (best_match) {
			old_item_list.splice(old_item_list.indexOf(best_match), 1);
			new_item_list.push(best_match)
			return sortItemList(sort_type, sort_by, old_item_list, new_item_list);
		}
		else {
			return new_item_list;
		}
	}

	$("#paginator-body > div").each(function() {
		if ($(this).data("item-number") == 0) {
			zero_start = true;
		}

		$(this).wrap("<p/>");

		item_list.push({
			item_number: $(this).data('item-number'),
			element: $(this),
			content: $(this).parent().html(),
			active: true,
			date_sort: $("#paginator-body > div").length - $(this).data('item-number'),
			rating_sort: $(this).find('.review-rating').data('value')
		});

		$(this).unwrap();
	});

	updatePagination();
	updateDisplayList();
	$().formatPagination()
	if (item_list.length > 0) {
		$('.total-reviews').html('<a href="#reviews">(' + item_list.length + ') Review' + (item_list.length > 1 ? 's' : '') + '</a>')
	}
	if (item_list.length <= total_allowed_per_page ) {
		$('.review-filters [class*="select-review"]').hide();
	}
	$('.customer-reviews').show();


	$('.jqs-filter-type').on('change', function() {
		for (i = 0; i < item_list.length; i++) {
			if (item_list[i].element.find('.review-type').data('value') == $(this).val() || $(this).val() == "All") {
				item_list[i].active = true;
			}
			else {
				item_list[i].active = false;
			}
		}

		updatePagination();
		updateDisplayList();
	});

	$('.jqs-filter-rating').on('change', function() {
		item_list = sortItemList('rating_sort', $(this).val(), item_list);
		updateDisplayList();
	});

	$('.jqs-filter-date').on('change', function() {
		item_list = sortItemList('date_sort', $(this).val(), item_list);
		updateDisplayList();
	});
});
