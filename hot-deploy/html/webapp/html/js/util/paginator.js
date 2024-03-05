// Paginator Variables;
var page = 1;
var total_pages_to_show = 5;
var total_page_numbers = $(".pagination > li[data-page]").length;
var separator = "&hellip;" // "..."

function restructurePageList(target, page_number) {
	total_page_numbers = 0;

	$("[id^=" + target + "]").each(function() {
		$(this).css("display", "none");

		if ($(this).children().length > 0) {
			total_page_numbers++
		}
	});

	if (page_number > total_page_numbers) {
		page_number = total_page_numbers;
	}

	$("#" + target + page_number).css("display", "block");

	loadPageNumber(page_number);

	var first_selectable_page = page_number - Math.floor(total_pages_to_show / 2);

	if (first_selectable_page + total_pages_to_show - 1 > total_page_numbers) {
		first_selectable_page = total_page_numbers - total_pages_to_show + 1;
	}

	if (first_selectable_page < 1) {
		first_selectable_page = 1;
	}

	var last_selectable_page = first_selectable_page + total_pages_to_show - 1;
	last_selectable_page = last_selectable_page > total_page_numbers ? total_page_numbers : last_selectable_page;

	$(".pagination > li").each(function() {
		if (!$(this).hasClass("arrow")) {
			if ($(this).children("a").data("page") >= first_selectable_page && $(this).children("a").data("page") <= last_selectable_page) {
				$(this).removeClass("hidden");
			}
			else {
				$(this).addClass("hidden");
			}
		}
	});
}

function arrowMovement(element) {
	if (!element.hasClass("unavailable")) {
		restructurePageList(element.parent().data("target"), (element.hasClass("arrow-left") ? $(".pagination").find(".current > a").data("page") - 1 : $(".pagination").find(".current > a").data("page") + 1));
	}
	else {
		// Prevents the page from shifting when clicked
		return false;
	}
}

function loadPageNumber(page_number) {
	element = $("[data-page=" + page_number + "]").parent();
	// Updated the selected page number
	element.addClass("current").siblings("li").removeClass("current");

	// Re-adjust the arrows incase we are no longer at the beginning or end.
	(page_number == 1 ? element.siblings(".arrow-left").addClass("unavailable") : element.siblings(".arrow-left").removeClass("unavailable"));
	(page_number == total_page_numbers ? element.siblings(".arrow-right").addClass("unavailable") : element.siblings(".arrow-right").removeClass("unavailable"));

}

$(document).ready(function() {
	// Paginator
	$(".pagination > li").on("click", function() {
		if ($(this).children("a").data("page")) {
			restructurePageList($(this).parent().data("target"), $(this).children("a").data("page"));
		}
	});

	$(".pagination > li.arrow-left, .pagination > li.arrow-right").on("click", function() {
		arrowMovement($(this));
	});
});
