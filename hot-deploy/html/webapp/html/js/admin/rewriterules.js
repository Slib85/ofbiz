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

	$('#createRewrite').on('submit', function(e) {
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
				alert('Error saving rewrite rule.\n\n' + (typeof response.errorMessage != 'undefined' ? response.errorMessage : ''));
			}
		}).error(function (response) {
			//todo error
		});
	});

	$('.removeRewriteRule').on('click', function(e) {
		e.preventDefault();

		$.ajax({
			url : '/admin/control/removeRewriteRule',
			data: { ruleId: $(this).attr('data-rule-id'), webSiteId: $(this).attr('data-website-id') },
			cache: false,
			dataType: 'json',
			async : true
		}).done(function (response) {
			if(response.success) {
				location.reload();
			} else {
				alert('Error removing rewrite rule.');
			}
		}).error(function (response) {
			//todo error
		});
	});
});