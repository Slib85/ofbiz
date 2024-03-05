var toastrOpts = {
	"closeButton": true,
	"debug": false,
	"newestOnTop": false,
	"progressBar": false,
	"positionClass": "toast-top-right",
	"preventDuplicates": false,
	"onclick": null,
	"showDuration": "300",
	"hideDuration": "1000",
	"timeOut": "5000",
	"extendedTimeOut": "1000",
	"showEasing": "swing",
	"hideEasing": "linear",
	"showMethod": "fadeIn",
	"hideMethod": "fadeOut"
};

$(document).ready(function() {
	$('.jqs-saveInternalComments').on('click', function() {
		$.ajax({
			type: 'POST',
			url: '/admin/control/updateCustomOrder',
			data: {
				quoteId: $('input[name="quoteId"]').val(),
				internalComments: $('textarea[name=internalComments').val()
			},
			dataType:'json',
			cache: false
		}).done(function(data) {
			if (data.success) {
				toastr.success('Internal Comments Successfully Updated!', 'Success', toastrOpts);
			} else {
				toastr.error('There was an issue while updating the Internal Comments.  Please try again.', 'Error', toastrOpts);
			}
		});
	});

	$('[name="customQuoteStatus"]').on('change', function() {
		$.ajax({
			type: "POST",
			url: "/admin/control/changeQuoteStatus",
			data: {
				quoteId: $('input[name="quoteId"]').val(),
				statusId: $('select[name="customQuoteStatus"]').val()
			},
			dataType:'json',
			cache: false
		}).done(function(data) {
			$(".jqs-response").removeClass("form-success").removeClass("form-error");
			if (data.success) {
				toastr.success('Status Successfully Updated!', 'Success', toastrOpts);
				location.reload();
			} else {
				toastr.error('There was an issue while updating the status.  Please try again.', 'Error', toastrOpts);
			}
		});
	});

	var previousAssignedTo = $('.jqs-assignedTo').val();

	$('.jqs-assignedTo').on('change', function() {
		var selfElement = $(this);

		$.ajax({
			type: "POST",
			url: "/admin/control/updateCustomOrderAssignedTo",
			data: {
				quoteId: $('[name="quoteId"').val(),
				assignedTo: selfElement.val(),
				websiteId: quoteWebsiteId
			},
			dataType:'json',
			cache: false
		}).done(function(data) {
			if (data.success) {
				// Eventually do shoabs service and not what's shown below..
				if (selfElement.val() != 'none') {
					$('[name="customQuoteStatus"]').val('QUO_ASSIGNED').trigger('change');
				}
				location.reload();
			} else {
				selfElement.val(previousAssignedTo);
				toastr.error('There was an issue while updating the quote assignment.  Please try again.', 'Error', toastrOpts);
			}
		});
	});

	$('.jqs-sendEmail, .jqs-sendQuoteToNetsuite').on('click', function(e) {
		$('body').spinner(true, false, 75);
		var self = $(this);
		var quoteIds = '';
		$.each($('[name=sendQuote]:checked'), function() {
			quoteIds = (quoteIds == '') ? $(this).val() : quoteIds + ',' + $(this).val();
		});

		if(self.hasClass('jqs-sendQuoteToNetsuite') && !isSalesRepEqual) {
            if(!confirm('The sales rep assigned to this quote does not match the one assigned to the customer. Are you sure you want to continue?')) {
                $('body').spinner(false);
                return;
			}
		}

		if(quoteIds != '') {
			$.ajax({
				type: 'POST',
				url: '/admin/control/' + (self.hasClass('jqs-sendEmail') ? 'sendCompletedQuoteEmail' : 'sendCompletedQuoteToNetsuite'),
				dataType: 'json',
				data: { 'quoteIds': quoteIds, 'quoteRequestId': self.attr('data-quoterequestid')}
			}).done(function( data ) {
				$('body').spinner(false);
				if (data.success) {
					toastr.success('The selected quotes were ' + (self.hasClass('jqs-sendEmail') ? 'emailed' : 'sent to netsuite') + '.', 'Success', toastrOpts);
					location.reload();
				} else {
					toastr.error(data.error, 'Error', toastrOpts);
					toastr.error(data.response, 'Error', toastrOpts);
				}
			});
		} else {
			toastr.error("Please select a quote from the list above.", 'Error', toastrOpts);
			$('body').spinner(false);
		}

		e.preventDefault();
	});

    $('.generatePDF').on('click', function(e) {
        $('body').spinner(true, false, 75);
        var self = $(this);

		$.ajax({
			type: 'POST',
			url: '/admin/control/generateQuotePDF',
			dataType: 'json',
			data: { 'quoteId': self.attr('data-qid') }
		}).done(function( data ) {
			$('body').spinner(false);
			if (data.success) {
				toastr.success('Successfully generated new PDF', 'Success', toastrOpts);
			} else {
				toastr.error('Oh Snap, there was an issue generating the PDF.', 'Error', toastrOpts);
			}
            $('body').spinner(false);
		});

        e.preventDefault();
    });
});

var endAddCommentMode = function() {
	$(".comment-add").hide();
	$(".non-add-comment").show();
	$(".comment-list").show();
};

var beginAddCommentMode = function() {
	$("textarea[name='orderNote']").val("");
	$(".comment-list").hide();
	$(".non-add-comment").hide();
	$(".comment-add").show();
};

$(".non-add-comment input[name='button-add']").click(function() {
	beginAddCommentMode();
});

$(".add-comment .jqs-button-cancel").click(function() {
	endAddCommentMode();
});

$(".add-comment .jqs-button-save").click(function() {
	$.ajax({
		type: 'POST',
		url: '/admin/control/addQuoteNote',
		data: {
			noteInfo: $('textarea[name=orderNote]').val(),
			quoteId: $('[name="quoteId"]').val()
		},
		dataType: 'json',
		cache: false
	}).done(function(data) {
		if(data.success) {
			location.reload();
		} else {
			toastr.error('OH SNAP!  There was a problem adding a quote note!', 'Error', toastrOpts);
		}
	});
});

$('.jqs-discontinueChildQuote').on('click', function() {
	var self = $(this);
	var quoteId = $(this).attr('data-quoteId');

    alertify.confirm('Are you sure you wish to delete quote '  + quoteId + '?', function() {
        $.ajax({
            type: 'POST',
            url: '/admin/control/discontinueChildQuote',
            data: {
                quoteId: quoteId
            },
            dataType: 'json',
            cache: false
        }).done(function(response) {
            if (response.success) {
                self.parentsUntil('tr').parent().remove();
            } else {
                toastr.error('There was a problem deleting the quote.', 'Error', toastrOpts);
			}
        });
	}, function() {
    	// do nothing
    });

	return false;
});