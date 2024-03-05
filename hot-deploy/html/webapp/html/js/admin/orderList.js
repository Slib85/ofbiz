jQuery(document).ready(function($) {
	var offsetTop = 0;

	if ($('.site-navbar').length > 0) {
		offsetTop = $('.site-navbar').eq(0).innerHeight();
	}

	// initialize datatable
	var table = $('#orderListFixedHeader').DataTable({
		iDisplayLength: 50,
		responsive: true,
		fixedHeader: {
			header: true,
			headerOffset: offsetTop
		},
		bPaginate: true,
		aaSorting: [],
		initComplete: function() {
			$('#orderListFixedHeader .replace-inputs > th').each(function(index) {
				var title = $(this).text();
				$(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
			});

			$("#orderListFixedHeader .replace-inputs input").on('keyup change', function () {
				table
					.column($(this).parent().index() + ':visible' )
					.search(this.value)
					.draw();
			});
        }
	});

	$('.exportOrder').on('click', function(e) {
		e.preventDefault();
		var oid = $(this).attr('data-oid');
		$('.navbar').spinner(true, false, 75);
		$.ajax({
			type: 'POST',
			url: '/admin/control/exportOrder',
			dataType: 'json',
			data: { 'orderId' : oid, 'ignoreValidity': true },
			cache: false
		}).done(function( response ) {
			if(response.success) {
				alert(response.message.replace(/{(.*)}/,''));
			}
			$('.navbar').spinner(false);
		});
	});

    $.fn.datepicker.defaults.format = "yyyy-mm-dd";
});
