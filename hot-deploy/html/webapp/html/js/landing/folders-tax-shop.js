$(document).ready(function() {
	// custom play button
	var bnc_ts_btn = $(".bnc_media_button");
	bnc_ts_btn.click(function() {
		bnc_ts_btn.toggleClass("bnc_paused");
		return false;
	});
});
