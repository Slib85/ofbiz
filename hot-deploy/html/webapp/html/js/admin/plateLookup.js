$(function() {
	$('.orderSchedule').on('click focus', function() {
		$(this).val('');
	});
	$('#plateOrderLookup').on('submit', function(e) {
		e.preventDefault();
		var orderLookup = $('.orderSchedule').val().trim().replace(' ','_');
		if(orderLookup.indexOf('-') != -1) {
			orderLookup = orderLookup.substring(0, orderLookup.indexOf('-'));
		}
		$.ajax({
			'url': '/admin/control/getOrderPlate',
			'type': 'POST',
			'data': { order: orderLookup },
			'dataType': 'json',
			'async': false,
			'cache': false
		}).done(function(data, textStatus, jqXHR) {
			if(data.success) {
				if(typeof data.isRemoveFromSchedule !== 'undefined' && data.isRemoveFromSchedule == 'Y') {
					alert('This order has been removed from the schedule.');
				} else {
					window.location = '/admin/control/plateSchedule?printPressId=' + data.pressId + '&orderId=' + data.orderId + '&orderItemSeqId=' + data.orderItemSeqId;
				}
			} else {
				alert('Error, the specified order could not be found.');
			}
		}).fail(function(jqXHR) {
			//
		}).always(function(data, textStatus, jqXHR) {
			//
		});
	});

    $.fn.datepicker.defaults.format = "yyyy-mm-dd";
});