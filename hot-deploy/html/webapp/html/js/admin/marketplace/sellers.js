(function($) {
    $.extend({
        syncSellers: function() {
            $.ajax({
                type: 'GET',
                url: '/admin/control/syncSellers',
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    $.renderSellers(data.sellers);
                    alert('Syncing sellers completed.')
                } else {
                    alert('Syncing sellers failed.')
                }
            }).fail(function() {
                alert('An error occurred while syncing sellers');
            });
        },
        renderSellers: function(sellers) {
            var html = '';
            $.each(sellers, function(i, value){
                html += $.buildGridRow(value);
            });
            $('#marketplace-sellers').find('tbody').html(html);
        },
        buildGridRow: function(seller) {
            return '<tr><td>' + seller.marketplaceSellerId + '</td><td>' + seller.sellerName + '</td><td>OPEN</td><td>ACTIVE</td><td>' + seller.miraklLastUpdatedDate + '</td><td>' + seller.miraklCreatedDate + '</td></tr>';
        }
    });
})(jQuery);

$(function() {
    $('.jqs-sync').on('click', function() {$.syncSellers()});
});