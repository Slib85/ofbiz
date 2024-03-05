$(function(){
    var offsetTop = 0;

    if ($('.site-navbar').length > 0) {
        offsetTop = $('.site-navbar').eq(0).innerHeight();
    }

    // initialize datatable
    var table = $('#openOrderListFixedHeader').DataTable({
        iDisplayLength: 25,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        initComplete: function() {
            $('#openOrderListFixedHeader .replace-inputs > th').each(function(index) {
                var title = $(this).text();
                $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
            });

            $("#openOrderListFixedHeader .replace-inputs input").on('keyup change', function () {
                table
                    .column($(this).parent().index() + ':visible' )
                    .search(this.value)
                    .draw();
            });
        }
    });

    // initialize datatable
    var table = $('#cancelledOrderListFixedHeader').DataTable({
        iDisplayLength: 25,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        initComplete: function() {
            $('#cancelledOrderListFixedHeader .replace-inputs > th').each(function(index) {
                var title = $(this).text();
                $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
            });

            $("#cancelledOrderListFixedHeader .replace-inputs input").on('keyup change', function () {
                table
                    .column($(this).parent().index() + ':visible' )
                    .search(this.value)
                    .draw();
            });
        }
    });

    // initialize datatable
    var table = $('#changedOrderListFixedHeader').DataTable({
        iDisplayLength: 25,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        initComplete: function() {
            $('#changedOrderListFixedHeader .replace-inputs > th').each(function(index) {
                var title = $(this).text();
                $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
            });

            $("#changedOrderListFixedHeader .replace-inputs input").on('keyup change', function () {
                table
                    .column($(this).parent().index() + ':visible' )
                    .search(this.value)
                    .draw();
            });
        }
    });

    // initialize datatable
    var table = $('#pendingOrderListFixedHeader').DataTable({
        iDisplayLength: 25,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        initComplete: function() {
            $('#pendingOrderListFixedHeader .replace-inputs > th').each(function(index) {
                var title = $(this).text();
                $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
            });

            $("#pendingOrderListFixedHeader .replace-inputs input").on('keyup change', function () {
                table
                    .column($(this).parent().index() + ':visible' )
                    .search(this.value)
                    .draw();
            });
        }
    });
})