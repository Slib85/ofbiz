$(document).ready(function() {
	function showErrorMessage(message) {
        $('#jqs-error').show().find('span').html(message);
    }

    $('#orderList').dataTable( {
    	"aaSorting": [[2, 'desc']],
		"aoColumnDefs": [
			{ 'bSortable': false, 'aTargets': [ 3 ] }
		],
		"fnDrawCallback": function() {
			var oTable = $("#orderList").dataTable();
			if (oTable.fnGetData().length <= 10) {
				$('#orderList_length')[0].style.display = "none";
			}
			else {
				$('#orderList_length')[0].style.display = "block";
			}

			if ($('#orderList_paginate span span.paginate_button').size()) {
				//$('#orderList_paginate')[0].style.display = "block";
			}
			else {
				//$('#orderList_paginate')[0].style.display = "none";
			}
		}
	});

	$('.view_artwork').on('click', function() {
		if ($(this).html() == 'View Artwork') {
			$(this).siblings('div').removeClass('hidden');
			$(this).html('Hide Artwork');
		}
		else {
			$(this).siblings('div').addClass('hidden');
			$(this).html('View Artwork');
		}

		return false;
	});
});
