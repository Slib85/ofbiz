jQuery(document).ready(function($) {
    var offsetTop = 0;

    if ($('.site-navbar').length > 0) {
        offsetTop = $('.site-navbar').eq(0).innerHeight();
    }

    // initialize datatable
    var table = $('#jobFixedHeader').DataTable({
        iDisplayLength: 300,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        initComplete: function() {
            $('#jobFixedHeader .replace-inputs > th').each(function(index) {
                var title = $(this).text();
                $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
            });

            $("#jobFixedHeader .replace-inputs input").on('keyup change', function () {
                table
                    .column($(this).parent().index() + ':visible' )
                    .search(this.value)
                    .draw();
            });
        }
    });

    $.fn.datepicker.defaults.format = "yyyy-mm-dd";
});
