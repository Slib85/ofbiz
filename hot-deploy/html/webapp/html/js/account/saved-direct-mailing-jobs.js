
(function($) {
    $.extend({
        bindDeleteDocumentEvent: function() {
            $('.jqs-delete-design').on('click', function() {
                var jobNumber = $(this).data('job-number');
                $.ajax({
                    type: 'POST',
                    url: deleteJobEndPoint,
                    async: false,
                    data: { 'jobNumber' : jobNumber } ,
                    cache: false,
                    dataType : 'json'
                }).done(function(data) {
                    if(data.success) {
                        $('#design-' + jobNumber).hide();
                        $('.alert-box').addClass('hidden');
                        $('#design-message').removeClass('hidden');
                    } else {
                        $('.alert-box').addClass('hidden');
                        $('#design-error').removeClass('hidden');
                    }
                });
            });
        }

    });
})(jQuery);

$(function() {
    $.bindDeleteDocumentEvent();
});