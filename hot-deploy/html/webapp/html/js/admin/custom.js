$('.jqs-wbSearch').on('click', function() {
	window.setTimeout(function() {
		$('#global-search-input').focus();
	}, 250);
});
function getIEVersion() {
	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");

	if(msie > 0) {
		// Return Version of IE
		return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
	} else {
		return 0;
	}
}
var waitForFinalEvent = (function () {
	var timers = {};
	return function (callback, ms, uniqueId) {
		if(!uniqueId) {
			uniqueId = "Don't call this twice without a uniqueId.";
		}
		if(timers[uniqueId]) {
			clearTimeout (timers[uniqueId]);
		}
		timers[uniqueId] = setTimeout(callback, ms);
	};
})();

function getFullHeight(element) {
	var margin_top;
	var margin_bottom;

	if (element.css("margin-top")) {
		margin_top = (element.css("margin-top").match("px") ? parseInt(element.css("margin-top")) : 0);
	}

	if (element.css("margin-bottom")) {
		margin_bottom = (element.css("margin-bottom").match("px") ? parseInt(element.css("margin-bottom")) : 0);
	}

	return element.outerHeight() + margin_top + margin_bottom;
}

function getFullWidth(element) {
	var margin_right;
	var margin_left;

	if (element.css("margin-right")) {
		margin_right = (element.css("margin-right").match("px") ? parseInt(element.css("margin-right")) : 0);
	}

	if (element.css("margin-left")) {
		margin_left = (element.css("margin-left").match("px") ? parseInt(element.css("margin-left")) : 0);
	}

	return element.outerWidth() + margin_left + margin_right;
}