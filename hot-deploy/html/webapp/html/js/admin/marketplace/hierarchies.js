(function($) {
    $.extend({
        syncHierarchies: function() {
            $.ajax({
                type: 'GET',
                url: '/admin/control/syncHierarchies',
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    $.renderHierarchies(data.hierarchies);
                    alert('Syncing hierarchies completed.')
                } else {
                    alert('Syncing hierarchies failed.')
                }
            }).fail(function() {
                alert('An error occurred while syncing hierarchies');
            });
        },
        exportHierarchies: function() {
            $.ajax({
                type: 'GET',
                url: '/admin/control/exportHierarchies',
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    alert('Exporting hierarchies completed.')
                } else {
                    alert('Exporting hierarchies failed.')
                }
            }).fail(function() {
                alert('An error occurred while exporting hierarchies');
            });
        },
        renderHierarchies: function(hierarchies) {
            $('.jqs-hierarchies .jqs-hierarchy').remove();
            $.each(hierarchies, function(i, value){
                $('.jqs-hierarchies').append($.buildGridRow(value));
            });
        },
        buildGridRow: function(hierarchy) {
            var levelClass = '';
            if(hierarchy.level == 2) {
                levelClass = ' m-l-20';
            } else if(hierarchy.level == 3) {
                levelClass = ' m-l-40';
            }
            return '' +
            '<li class="jqs-hierarchy list-group-item">' +
            '    <div class="row">' +
            '        <div class="col-md-10"><span class="jqs-level' + levelClass + '"><span class="icon fa-folder"></span>' + hierarchy.categoryLabel + '</span><span class="tag tag-round tag-dark m-l-5 m-r-5">' + hierarchy.categoryId + '</span></div>' +
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
    $('.jqs-sync').on('click', function() {$.syncHierarchies()});
    $('.jqs-export').on('click', function() {$.exportHierarchies()});
});