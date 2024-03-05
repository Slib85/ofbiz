$(function() {
	$('#submitPlate').on('click', function(e) {
		$('#createOrUpdatePlate').submit();
		e.preventDefault();
	});
});