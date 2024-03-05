(function($) {
    var notifyEl = '<div class="jqs-alert alert dark alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">x</span></button><span class="jqs-message"></span></div>'
    var itemTable;
    $.extend({
        initDataTable: function() {
            var offsetTop = 0;
            itemTable = $('#items-grid').DataTable({
                iDisplayLength: 25,
                responsive: true,
                fixedHeader: {
                    header: true,
                    headerOffset: offsetTop
                },
                bPaginate: true,
                aaSorting: [],
                initComplete: function() {
                    $('#items-grid .replace-inputs > th').each(function(index) {
                        var title = $(this).text();
                        $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
                    });

                    $("#items-grid .replace-inputs input").on('keyup change', function () {
                        itemTable
                            .column($(this).parent().index() + ':visible' )
                            .search(this.value)
                            .draw();
                    });
                }
            });
            $('#items-grid').find('tbody').show();
            itemTable.on('draw.dt', function(){$.bindShowDetails(_entityHelper);});
        },
        notify: function(containerEl, messageType, message) {
            $('.jqs-alert').remove();
            var el = $(notifyEl).clone().addClass(messageType === 'ERROR' ? 'alert-danger' : 'alert-success');
            el.find('.jqs-message').text(message);
            $(containerEl).prepend(el);
            $.closeOut();
        },

        resetNotifications: function() {
            $('.jqs-alert').fadeOut('fast');
            setTimeout(function() {
                $('.jqs-changed').removeClass('jqs-changed');
            }, 250);

        },

        closeOut: function() {
            setTimeout(function() {
                $.resetNotifications();
            }, 2500);
        },
        resetForm: function(formEl) {
            $(formEl)[0].reset();
        }
    });

})(jQuery);

$(function() {
    $.initDataTable();
});