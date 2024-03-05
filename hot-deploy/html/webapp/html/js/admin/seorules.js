$(function() {
    var offsetTop = 0;

    if ($('.site-navbar').length > 0) {
        offsetTop = $('.site-navbar').eq(0).innerHeight();
    }

	var table = $("#ruleTable").dataTable({
        iDisplayLength: 50,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: []
	});

	$('#createSEO').on('submit', function(e) {
		e.preventDefault();

		var form = e.target;
		var formData = new FormData(form);

		$.ajax({
			url: form.action,
			method: form.method,
			processData: false,
			contentType: false,
			data: formData,
			dataType: 'json'
		}).done(function (response) {
			if(response.success) {
				location.reload();
			} else {
				alert('Error saving seo rule.');
			}
		}).error(function (response) {
			//todo error
		});
	});

	$('.removeSEORule').on('click', function(e) {
		e.preventDefault();

		$.ajax({
			url : '/admin/control/removeSEORule',
			data: { ruleId: $(this).attr('data-rule-id') },
			cache: false,
			dataType: 'json',
			async : true
		}).done(function (response) {
			if(response.success) {
				location.reload();
			} else {
				alert('Error removing seo rule.');
			}
		}).error(function (response) {
			//todo error
		});
	});

	$('#seoH1Color input').on('click', function(){
		$('#seoH1Color input').attr('checked', false);
		$(this).prop('checked', true);
	});
});