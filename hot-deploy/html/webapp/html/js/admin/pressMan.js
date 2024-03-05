jQuery(document).ready(function($) {
    var offsetTop = 0;

    if ($('.site-navbar').length > 0) {
        offsetTop = $('.site-navbar').eq(0).innerHeight();
    }

	var table = $('#orderTable').dataTable({
        iDisplayLength: 50,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        initComplete: function() {
            this.api().columns().every(function() {
                var column = this;
                var input = $('<input class="form-control w-full" type="text" />')
                    .appendTo($(column.footer()).empty())
                    .on('keyup', function() {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
                        column
                            .search(val, true, false, true)
                            .draw();
                    });
            });
        }
	});

	var table2 = $('#orderTable2').dataTable({
        iDisplayLength: 50,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        initComplete: function() {
            this.api().columns().every(function() {
                var column = this;
                var input = $('<input class="form-control w-full" type="text" />')
                    .appendTo($(column.footer()).empty())
                    .on('keyup', function() {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
                        column
                            .search(val, true, false, true)
                            .draw();
                    });
            });
        }
	});

    $.fn.datepicker.defaults.format = "yyyy-mm-dd";
});