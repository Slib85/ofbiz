(function($) {
    $.extend({
        /*bindAddPricingAttribute: function() {
            $('.jqs-add-pricing-attribute').on('click', function(){
                $('#jqs-pricing-attribute').modal().show();
            });
        },*/
        bindAddProducts: function() {
            $('.jqs-add-products').on('click', function() {
                $('#jqs-products-upload').find('form')[0].reset();
                $('#jqs-products-upload').find('.modal-footer').html('');
            });
        },
        bindSaveVendorDetails: function() {
            $('#jqs-vendor-details').find('.jqs-save-item').on('click', function(){
                var formEl = $(this).closest('form');
                $.ajax({
                    type: 'POST',
                    url: $(formEl).attr("action"),
                    data: $(formEl).serialize(),
                    dataType: 'json',
                    cache: false
                }).done(function (data) {
                    if(data.success === true) {
                        $.notify($('#jqs-vendor-details'), 'SUCCESS', 'Vendor Details updated successfully');
                    } else {
                        $.notify($('#jqs-vendor-details'), 'ERROR', 'An error occurred while updating Vendor Details');
                    }

                }).fail(function () {
                    $.notify($('#jqs-vendor-details'), 'ERROR', 'An error occurred while updating Vendor Details');
                });
            });
        },
        bindUploadVendorProducts: function() {
            $('.upload-file-button').on('click', function() {
                $(this).closest('.jqs-upload').find('input[type="file"]').trigger('click');
            });
        }

    });
})(jQuery);
$(function(){
    $.bindSaveVendorDetails();
    $.bindAddProducts();
    $('#fileupload').fileupload({
        dataType: 'json',
        add: function (e, data) {
            var fileName = data.files[0].name;
            var tok = fileName.split('.');
            var extension = tok.length > 1 ? tok[tok.length - 1] : '';
            if(extension.toUpperCase() == 'XLS' || extension.toUpperCase() == 'XLSX') {
                $('#jqs-fileName').val(fileName);
                $('#jqs-products-upload .modal-footer').html('');
                data.context = $('<button type="button" class="btn btn-dark"><i class="icon wb-upload" aria-hidden="true"></i> Upload</button>')
                    .appendTo($('#jqs-products-upload .modal-footer'))
                    .click(function () {
                        data.context = $('<p/>').text('Uploading...').replaceAll($(this));
                        data.submit();
                    });
            } else {
                $('#jqs-products-upload .modal-footer').html('<span class="alert-danger">Unsupported file format</span>');
            }

        },
        done: function (e, data) {
            // data.context.text('Upload finished.');
            if(data.result.success == true) {
                $('#jqs-products-upload .modal-footer').html('<span class="alert-success">Upload finished</span>');
            } else {
                $('#jqs-products-upload .modal-footer').html('<span class="alert-danger">Upload failed</span>');
            }
        }
    });


});