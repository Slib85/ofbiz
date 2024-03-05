(function($) {
    $.extend({
        bindSaveColorDetails: function() {
            $('.jqs-color-details').find('.jqs-save-item').on('click', function(){
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
                        $.notify($('.jqs-color-details'), 'SUCCESS', 'Color Details updated successfully');

                    } else {
                        $.notify($('.jqs-color-details'), 'ERROR', 'An error occurred while updating Color Details');
                    }

                }).fail(function () {
                    $.notify($('.jqs-color-details'), 'ERROR', 'An error occurred while updating Color Details');
                });
            });
        }
    });
})(jQuery);
$(function(){
    $.bindSaveColorDetails();
});