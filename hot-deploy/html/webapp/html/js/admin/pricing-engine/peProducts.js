//$('#jqs-pricing-attribute').modal().show()

(function($) {
    $.extend({
        bindAddPricingAttribute: function() {
            $('.jqs-add-pricing-attribute').on('click', function(){
                $('#jqs-pricing-attribute').modal().show();
            });
        },
        bindEditPricingAttribute: function() {
            $('.jqs-edit-pricing-attribute').each(function(){
                var $this = $(this);
                var attributeId = $this.closest('tr').attr('id');
                $(this).off().on('click', function(){
                    $.showPricingAttributeDialog(attributeId);
                });
            });
        },
        showPricingAttributeDialog: function(attributeId) {
            $.resetNotifications();
            $('#jqs-pricing-attribute').find('select[name="attributeId"]').val(attributeId);
            $('#jqs-pricing-attribute .jqs-pricing input').each(function(i, v) {
                $(this).val($('.jqs-pricing-details tr[id="' + attributeId + '"] td:eq(' + (i + 1) + ')').text());
            });
            $('#jqs-pricing-attribute').modal().show();
        },

        updatePricingAttributeGrid: function(attributeId, attributeValues) {
            if($('.jqs-pricing-details tr[id="' + attributeId + '"]').length === 0) {
                var trEl = $('.jqs-pricing-details tr:eq(1)').clone();
                $(trEl).attr('id', attributeId);
                $(trEl).find('td:eq(0)').text(attributeId);
                $(trEl).find('td:gt(0)').each(function(){
                    $(this).text('');
                });
                $('.jqs-pricing-details').prepend(trEl);
                $.bindEditPricingAttribute();
            }
            $.each(attributeValues.split('|'), function(i,v) {
                $('.jqs-pricing-details tr[id="' + attributeId + '"] td:eq(' + (i + 1) + ')').text(parseFloat(Math.round(v * 100) / 100).toFixed(2));
            });
            $('.jqs-pricing-details tr[id="' + attributeId + '"]').addClass('jqs-changed');

        },

        serializeQtyBreakValues: function() {
            var qtyBreaks = '';
            var values = '';
            $('#jqs-pricing-attribute .jqs-pricing .input-group').each(function() {
                var qtyBreak = $(this).find('.input-group-addon').text();
                var value = $(this).find('input').val();
                if(!isNaN(qtyBreak) && parseInt(qtyBreak) > 0 && value !== '' && !isNaN(value)) {
                    qtyBreaks += (qtyBreaks === '' ? '' : '|') + parseInt(qtyBreak);
                    values += (values === '' ? '' : '|') + parseFloat(value);
                }
            });
            return [qtyBreaks, values];
        },

        bindSaveProductDetails: function() {
            $('#jqs-product-details').find('.jqs-save-item').on('click', function(){
                var formEl = $(this).closest('form');
                $.ajax({
                    type: 'POST',
                    url: $(formEl).attr("action"),
                    data: $(formEl).serialize(),
                    dataType: 'json',
                    cache: false
                }).done(function (data) {
                    if(data.success === true) {
                        $.notify($('#jqs-product-details'), 'SUCCESS', 'Product Details updated successfully');
                    } else {
                        $.notify($('#jqs-product-details'), 'ERROR', 'An error occurred while updating Product Details');
                    }

                }).fail(function () {
                    $.notify($('#jqs-product-details'), 'ERROR', 'An error occurred while updating Product Details');
                });
            });
        },

        bindAddProducts: function() {
            $('.jqs-add-products').on('click', function() {
                $('#jqs-products-upload').find('form')[0].reset();
                $('#jqs-products-upload').find('.modal-footer').html('');
            });
        },

        bindSavePricingAttribute: function() {
            $('.jqs-save-pricing-attribute').on('click', function(){
                var formEl = $(this).closest('form');
                var qtyBreakValues = $.serializeQtyBreakValues();
                $('.jqs-pricing-details tr').removeClass('jqs-changed');
                $.ajax({
                    type: 'POST',
                    url: $(formEl).attr("action"),
                    data: {
                            'vendorId' : formEl.find('input[name="vendorId"]').val(),
                            'productId' : formEl.find('input[name="productId"]').val(),
                            'attributeId' : formEl.find('select[name="attributeId"]').val(),
                            'qtyBreaks' : qtyBreakValues[0],
                            'values' : qtyBreakValues[1]
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (data) {
                    if(data.success === true) {
                        $.updatePricingAttributeGrid(formEl.find('select[name="attributeId"]').val(), qtyBreakValues[1]);
                        $('#jqs-pricing-attribute').modal('hide');
                        $.resetForm($('#jqs-pricing-attribute form')[0]);
                        $.notify($('#jqs-product-pricing-details'), 'SUCCESS', 'Pricing attribute values updated successfully');
                    } else {
                        $('#jqs-pricing-attribute').modal('hide');
                        $.notify($('#jqs-product-pricing-details'), 'ERROR', 'An error occurred while updating Pricing Attribute values');
                    }

                }).fail(function () {
                    $('#jqs-pricing-attribute').modal('hide');
                    $.notify($('#jqs-product-pricing-details'), 'ERROR', 'An error occurred while updating Pricing Attribute values');
                });
            });
        }

    });

})(jQuery);

$(function() {
    $.bindAddPricingAttribute();
    $.bindSaveProductDetails();
    $.bindEditPricingAttribute();
    $.bindSavePricingAttribute();
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