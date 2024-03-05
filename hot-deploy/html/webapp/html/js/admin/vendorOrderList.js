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

jQuery(document).ready(function($) {
    // initialize datatable
    var defaults = Plugin.getDefaults("dataTable");

    var options = $.extend(true, {}, defaults, {
        'search': true,
        'bInfo': false,
        'iDisplayLength': 100
    });

    $('#vendorArtworkList').DataTable(options);
    $('#vendorOrderList').DataTable(options);

    $('.uploadProofFile').click(function(e) {
        e.preventDefault();
        var data = new FormData($(this).parent()[0]);
        $(this).prop('disabled', true);

        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: '/' + websiteId + '/control/processSwitchProof',
            data: data,
            processData: false,
            dataType: 'json',
            contentType: false,
            cache: false,
            timeout: 600000
        }).done(function(data) {
            if (data.success) {
                toastr.success('Uploaded proof file for review.', 'Success', toastrOpts);
                location.reload();
            } else {
                toastr.error('There was an error uploading the proof file. Please try again.', 'Error', toastrOpts);
            }
        });
    });

    $('.addTracking').click(function(e) {
        e.preventDefault();
        var data = {
            'orderId': $(this).closest('tr').attr('data-orderid'),
            'orderItemSeqId': $(this).closest('tr').attr('data-orderitemseqid'),
            'trackingNumber': $(this).parent().find('input[name="trackingNumber"]').val(),
            'packageWeight': $(this).parent().find('input[name="packageWeight"]').val(),
            'doFulfillment': 'Y'
        };
        $(this).prop('disabled', true);

        $.ajax({
            type: 'POST',
            url: '/' + websiteId + '/control/addVendorTracking',
            data: data,
            dataType: 'json',
            cache: false,
            timeout: 600000
        }).done(function(data) {
            if (data.success) {
                toastr.success('Tracking data saved successfully.', 'Success', toastrOpts);
                location.reload();
            } else {
                toastr.error('There was an error saving the tracking information. Please try again.', 'Error', toastrOpts);
            }
        });
    });

    $('.generatePDF').on('click', function(e) {
        $('body').spinner(true, false, 75);
        var self = $(this);

        $.ajax({
            type: 'POST',
            url: '/' + websiteId + '/control/generatePurchaseOrderPDF',
            dataType: 'json',
            data: { 'orderId': self.attr('data-orderid'), 'orderItemSeqId': self.attr('data-orderitemseqid'), 'purchaseOrderId': self.attr('data-purchaseorderid') }
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

    $.fn.datepicker.defaults.format = "yyyy-mm-dd";
});