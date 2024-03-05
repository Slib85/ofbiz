// Sortable List Variables
var item_list = [];
var sort_order = [];
var items_total_per_list = $("[id^=sortable-list]:first").children(":not('hr')").length;

function sortList(selected_value, data_target, data_type, update_sort_order) {
	var review_id = "";
	var previous_value = "";
	item_list = [];

	for (var i = 0; i < $("[id^=sortable-list]").children("[id^=sortable-item]").length; i++) {
		$("[id^=sortable-list]").children("[id^=sortable-item]").each(function() {
			current_value = (data_type == "date" ? Date.parse($(this).find("." + data_target).data("value")) : $(this).find("." + data_target).data("value"));
			if (((selected_value == "ascending" ? current_value < previous_value : current_value > previous_value) || previous_value == "") && $.inArray($(this).attr("id"), item_list) == -1) {
				previous_value = current_value;
				review_id = $(this).attr("id");
			}
		});

		item_list.push(review_id);
		previous_value = "";
		review_id = "";
	}

	if (update_sort_order) {
		removeArrayValue(data_target, sort_order);
		sort_order.push(data_target);
	}

	restructureList();
}

function restructureList() {
	$("[id^=sortable-list]").each(function() {
		$(this).find("hr").remove();
	});

	for (var i = 0; i <= item_list.length; i++) {
		if (!parseInt(i % items_total_per_list) == 0 && item_list.length != i) {
			$("#sortable-list" + (parseInt(i / items_total_per_list + 1))).append("<hr class='margin-top-md margin-bottom-md' />");
		}

		$("#" + item_list[i]).appendTo($("#sortable-list" + (parseInt(i / items_total_per_list + 1))));
	}
}

$(document).ready(function() {
	// Initialize Sortable List.
	$("[id^=sortable-list]").children("[id^=sortable-item]").each(function() {
		item_list.push($(this).attr("id"));
	});

	// Do list sorting
	$("[data-function*=f-sort]").on("click", function() {
		sortList($(this).data("value").toLowerCase(), $(this).data("target"), $(this).data("type"), true);
	});

	// Do list item elimination and addition, then sort.
	$("[data-function*=f-search]").on("click", function() {
		var value = $(this).data("value").toLowerCase();
		var target = $(this).data("target");
		item_list = [];

		$("[id^=sortable-item]").each(function() {
			if (value != "" && $(this).find("." + target).data("value").toLowerCase() != value ) {
				$(this).appendTo("#hidden-sortable-lists");
			}
			else {
				item_list.push($(this).attr("id"));
			}
		});

		restructureList();

		if (sort_order.length > 0) {
			for (var i = 0; i < sort_order.length; i++) {
				sortList($("[data-target=" + sort_order[i] + "].f-selected").data("value"), sort_order[i], $("[data-target=" + sort_order[i] + "]").data("type"), false);
			}
		}

		if (typeof restructurePageList !== 'undefined') {
			restructurePageList("sortable-list", $("[data-target=sortable-list]").children(".current").data("page"));
		}
	});

	// Do single selection.
	$("[data-function*=f-single]").on("click", function() {
		$("#" + $(this).data("target")).html($(this).data("value"));
	});
})
