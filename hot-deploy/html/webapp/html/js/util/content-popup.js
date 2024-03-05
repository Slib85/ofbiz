$(document).ready(function() {
	var popup_element = "";

	$("[data-content-popup]").on("click", function() {
		popup_element = $(this);
		// Append Content
		$("#" + popup_element.data("content-popup")).appendTo("body");
		$("body").append("<div id='background-popup'></div>");

		// Style the Content
		$("#background-popup").css("height", parseInt($(window).height() + 200) + "px"); // Required addition for IPhone Mobile
		$("#" + popup_element.data("content-popup")).css({top: (-10 - parseInt($("#" + popup_element.data("content-popup")).outerHeight())) + "px", display: "block"});

		// Begin Display
		$("#background-popup").fadeIn("fast", function() {
			$("#" + popup_element.data("content-popup")).animate({top:"100px"}, 200);
		});
	});

	$(document.body).on("click", "#background-popup", function() {
		$("#" + popup_element.data("content-popup")).animate({top: (-10 - parseInt($("#" + popup_element.data("content-popup")).outerHeight())) + "px"}, 200, function() {
			$(this).css("display", "none");
			$("#background-popup").fadeOut("fast", function() {
				$(this).remove();
			});
		});
	});
});
