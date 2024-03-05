/*
	select-list.js extends dropdown.js
*/

function doSelect(element) {
	if (element.html().length >= $("[data-dropdown-target=" + element.parent().attr("id") + "]").data("owl")) {
		percentage = $("[data-dropdown-target=" + element.parent().attr("id") + "]").data("owl") / element.html().length;
		new_phrase = "";
		ongoing = "...";
		char_list = element.html().split("");

		for (var i = 0; i <= Math.floor(char_list.length * percentage) - ongoing.length + 1; i++) {
			new_phrase += char_list[i];
		}

		new_phrase.match(/^(?:(.*)?(?:\s$|(?:\s|\-)[^\s]+$)|(.{3}))/);
		new_phrase = (RegExp.$1 ? RegExp.$1 : RegExp.$2) + ongoing;

		$("[data-dropdown-target=" + element.parent().attr("id") + "]").find("span").html(new_phrase);
	}
	else {
		$("[data-dropdown-target=" + element.parent().attr("id") + "]").find("span").html(element.html());
	}

	element.siblings().removeClass("f-selected");
	element.addClass("f-selected");

	doDropDown(element.closest($(".drop-down")), false, 0);
}

$("body").delegate(".f-select-list > div", "click", function(e) {
	if (!$(this).hasClass("f-selected")) {
		e.stopPropagation();
		doSelect($(this));

		if ($(this).parent().data("custom-select")) {
			$(this).parent().trigger("change");
		}
	}
});
