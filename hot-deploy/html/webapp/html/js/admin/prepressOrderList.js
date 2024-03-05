jQuery(document).ready(function($) {
    var offsetTop = 0;

    if ($('.site-navbar').length > 0) {
        offsetTop = $('.site-navbar').eq(0).innerHeight();
    }

    // initialize datatable
    var table = $('#orderListFixedHeader').DataTable({
        iDisplayLength: 50,
        responsive: false,
        fixedHeader: {
            header: false,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        initComplete: function() {
			$('#orderListFixedHeader .replace-inputs > th').each(function(index) {
				if (!$(this).hasClass('jqs-inputIgnore')) {
					var title = $(this).text();
					$(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
				}
			});

			$('#orderListFixedHeader .replace-inputs input').on('keyup change', function () {
				table
					.column($(this).parent().index() + ':visible' )
					.search(this.value)
					.draw();
			});
        }
    });

	//middle click handler
    $('.jqs-prepressmidclick').on('mousedown', function(e) {
		console.log(e.which);
        e.preventDefault();
        if(e.which == 2) {
            var form = $(this).prev().clone();
            form.attr({'id': form.attr('id') + '_COPY', 'target': '_blank'});
            $('body').append(form);
            form.submit();
            form.remove();
        } else if (e.which == 1) {
            $(this).prev().submit();
		}
    });
});

$('[name=bulkStatusUpdate]').on('submit', function(e) {
	var statusId = $(this).find('[name=statusList]').val();
	$.each($('[name=updateStatus]:checked'), function() {
		var value = $(this).val();
		var orderId = value.split("|")[0];
		var orderItemSeqId = value.split("|")[1];
		var currentStatusId = value.split("|")[2];
		$.ajax({
			type: 'POST',
			url: '/admin/control/changeOrderItemStatus',
			dataType: 'text',
			data: { 'orderId': orderId, 'orderItemSeqId': orderItemSeqId, 'fromStatusId': currentStatusId, 'statusId': statusId }
		}).done(function( response ) {
			$('#' + orderId + '_' + orderItemSeqId + '').fadeOut(300, function() { $(this).remove(); });
		});
	});

	e.preventDefault();
});
