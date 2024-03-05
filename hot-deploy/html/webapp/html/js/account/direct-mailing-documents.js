
(function($) {
    $.extend({
        bindDeleteDocumentEvent: function() {
            $('.jqs-delete-design').on('click', function() {
                var directMailingcontentId = $(this).data('document-id');
                $.ajax({
                    type: 'POST',
                    url: deleteDocumentEndPoint,
                    async: false,
                    data: { 'directMailingcontentId' : directMailingcontentId } ,
                    cache: false,
                    dataType : 'json'
                }).done(function(data) {
                    if(data.success) {
                        $('#document-' + directMailingcontentId).hide();
                        $('.alert-box').addClass('hidden');
                        $('#design-message').removeClass('hidden');
                    } else {
                        $('.alert-box').addClass('hidden');
                        $('#design-error').removeClass('hidden');
                    }
                });
            });
        },
        bindEditDesignEvent: function() {
            $('.jqs-edit-design').on('click', function() {
                window.location.href = $(this).data('edit-url');
            });
        }
    });
})(jQuery);

$(function() {
    $.bindDeleteDocumentEvent();
    $.bindEditDesignEvent();
});