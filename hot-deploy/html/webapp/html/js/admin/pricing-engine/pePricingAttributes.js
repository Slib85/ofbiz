(function($) {
    $.extend({
        bindSavePricingAttributeDetails: function() {
            $('.jqs-pricing-attribute-details').find('.jqs-save-item').on('click', function(){
                var isDialog = $(this).data('dialog');
                var formEl = $(this).closest('form');
                $.ajax({
                    type: 'POST',
                    url: $(formEl).attr("action"),
                    data: $(formEl).serialize(),
                    dataType: 'json',
                    cache: false
                }).done(function (data) {
                    if(data.success === true) {
                        $.notify($('.jqs-pricing-attribute-details'), 'SUCCESS', 'Pricing Attribute Details updated successfully');

                    } else {
                        $.notify($('.jqs-pricing-attribute-details'), 'ERROR', 'An error occurred while updating Pricing Attribute Details');
                    }

                }).fail(function () {
                    $.notify($('.jqs-pricing-attribute-details'), 'ERROR', 'An error occurred while updating Pricing Attribute Details');
                });
            });
        }
    });
})(jQuery);
$(function(){
    $.bindSavePricingAttributeDetails();
});