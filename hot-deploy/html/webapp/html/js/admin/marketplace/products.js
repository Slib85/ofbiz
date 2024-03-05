//$('.jqs-pending-products').find('input:checkbox:checked').each(function(i, value) {$(value).data('id')})

(function($) {
    $.extend({
        selectedIds: function(container) {
            var ids = '';
            $(container).find('input:checkbox:checked').each(function(i, value) {
                ids = ids + (ids == '' ? '' : ',') + $(value).data('id');
            });
            return ids;
        },
        holdProducts: function(container) {
            var ids = $.selectedIds(container);
            $.ajax({
                type: 'POST',
                url: '/admin/control/holdSellerProducts',
                data: {'productIds' : ids},
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    window.location.reload();
                } else {
                    alert('Holding products failed.')
                }
            }).fail(function() {
                alert('An error occurred while holding products');
            });
        },
        importedProducts: function(container) {
            var ids = $.selectedIds(container);
            $.ajax({
                type: 'POST',
                url: '/admin/control/importedSellerProducts',
                data: {'productIds' : ids},
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    window.location.reload();
                } else {
                    alert('Importing products failed.')
                }
            }).fail(function() {
                alert('An error occurred while importing products');
            });
        },
        publishProducts: function(container) {
            var ids = $.selectedIds(container);
            $.ajax({
                type: 'POST',
                url: '/admin/control/publishSellerProducts',
                data: {'productIds' : ids},
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    window.location.reload();
                } else {
                    alert('Publishing products failed.')
                }
            }).fail(function() {
                alert('An error occurred while publishing products');
            });
        }
    });
})(jQuery);

$(function() {
    $('.jqs-hold').on('click', function() {$.holdProducts($(this).closest('.jqs-pending-products'));});
    $('.jqs-import').on('click', function() {$.importedProducts($(this).closest('.jqs-pending-products'));});
    $('.jqs-import-held').on('click', function() {$.importedProducts($(this).closest('.jqs-held-products'));});
    $('.jqs-publish').on('click', function() {$.publishProducts($(this).closest('.jqs-imported-products'));});
});