(function($) {
    $.extend({
        syncAttributes: function() {
            $.ajax({
                type: 'GET',
                url: '/admin/control/syncProductAttributes',
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    $.renderAttributes(data.attributes);
                    alert('Syncing product attributes completed.')
                } else {
                    alert('Syncing product attributes failed.')
                }
            }).fail(function() {
                alert('An error occurred while syncing product attributes');
            });
        },
        exportAttributes: function() {
            $.ajax({
                type: 'GET',
                url: '/admin/control/exportProductAttributes',
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    alert('Exporting product attributes completed.')
                } else {
                    alert('Exporting product attributes failed.')
                }
            }).fail(function() {
                alert('An error occurred while exporting product attributes');
            });
        },
        renderAttributes: function(attributes) {
            $('.jqs-attributes .jqs-attribute').remove();
            $.each(attributes, function(i, value){
                $('.jqs-attributes').append($.buildGridRow(value));
            });
        },
        buildGridRow: function(attribute) {
            return '' +
                '<li class="jqs-attribute list-group-item">' +
                '<div class="row">' +
                '<div class="col-md-12"><div class="row"><div class="col-md-4">' + attribute.attributeLabel + '</div><div class="col-md-8"><span class="tag tag-round tag-dark m-l-5 m-r-5">' + attribute.attributeId +'</span><span class="tag tag-round tag-info m-l-5 m-r-5">' + attribute.type + '</span><span class="tag tag-round tag-warning m-l-5 m-r-5">' + (attribute.required == "Y" ? "REQUIRED" : "OPTIONAL") +'</span></div></div></div>' +
                '</div>' +
                '</li>';
        }
    });
})(jQuery);

$(function() {
    $('.jqs-sync').on('click', function() {$.syncAttributes()});
    $('.jqs-export').on('click', function() {$.exportAttributes()});
});