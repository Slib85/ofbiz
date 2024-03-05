/**
 * Created by Manu on 4/14/2015.
 */


(function($) {
    $.extend({
        bindDeleteDesignEvent: function() {
            $('.jqs-delete-design').on('click', function() {
                var projectId = $(this).data('design-id');
                $.ajax({
                    type: 'POST',
                    url: deleteDesignEndPoint,
                    async: false,
                    data: { 'scene7DesignId' : projectId } ,
                    cache: false,
                    dataType : 'json'
                }).done(function(data) {
                    if(data.success) {
                        $('#design-' + projectId).hide();
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
    $.bindDeleteDesignEvent();
    $.bindEditDesignEvent();
});