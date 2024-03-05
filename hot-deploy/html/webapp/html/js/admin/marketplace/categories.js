(function($) {
    $.extend({
        syncCategories: function() {
            $.ajax({
                type: 'GET',
                url: '/admin/control/syncCategories',
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    $.renderCategories(data.categories);
                    alert('Syncing categories completed.')
                } else {
                    alert('Syncing categories failed.')
                }
            }).fail(function() {
                alert('An error occurred while syncing categories');
            });
        },
        exportCategories: function() {
            $.ajax({
                type: 'GET',
                url: '/admin/control/exportCategories',
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    alert('Exporting categories completed.')
                } else {
                    alert('Exporting categories failed.')
                }
            }).fail(function() {
                alert('An error occurred while exporting categories');
            });
        },
        renderCategories: function(categories) {
            $('.jqs-categories .jqs-category').remove();
            $.each(categories, function(i, value){
                $('.jqs-categories').append($.buildGridRow(value));
            });
        },
        buildGridRow: function(category) {
            var levelClass = '';
            if(category.level == 2) {
                levelClass = ' m-l-20';
            } else if(category.level == 3) {
                levelClass = ' m-l-40';
            }
            return '' +
                '<li class="jqs-category list-group-item">' +
                '    <div class="row">' +
                '        <div class="col-md-10"><span class="jqs-level' + levelClass + '"><span class="icon fa-folder"></span>' + category.categoryLabel + '</span><span class="tag tag-round tag-dark m-l-5 m-r-5">' + category.categoryId + '</span></div>' +
                '        <div class="col-md-2">' +
                '            <div class="pull-xs-right">' +
                '            </div>' +
                '        </div>' +
                '    </div>' +
                '</li>';
        }
    });
})(jQuery);

$(function() {
    $('.jqs-sync').on('click', function() {$.syncCategories()});
    $('.jqs-export').on('click', function() {$.exportCategories()});
});