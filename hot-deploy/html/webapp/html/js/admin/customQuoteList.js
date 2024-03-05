jQuery(document).ready(function($) {
    var offsetTop = 0;

    if ($('.site-navbar').length > 0) {
        offsetTop = $('.site-navbar').eq(0).innerHeight();
    }

    // initialize datatable
    var table = $('#orderTableFixedHeader').DataTable({
        iDisplayLength: 50,
        responsive: true,
        fixedHeader: {
            header: true,
            headerOffset: offsetTop
        },
        bPaginate: true,
        aaSorting: [],
        searching: true,
        initComplete: function() {
            $('#orderTableFixedHeader .replace-inputs > th').each(function(index) {
                var title = $(this).text();
                $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
            });

            $("#orderTableFixedHeader .replace-inputs input").on('keyup change', function () {
                table
                    .column($(this).parent().index() + ':visible' )
                    .search(this.value)
                    .draw();
            });
        }
    });

    $.fn.datepicker.defaults.format = "yyyy-mm-dd";

    var previousAssignedTo;

    $('.jqs-assignedTo').on('focus', function() {
        previousAssignedTo = $(this).val();
    });

    $('.jqs-assignedTo').on('change', function() {
        var selfElement = $(this);

        $.ajax({
            type: "POST",
            url: "/admin/control/updateCustomOrderAssignedTo",
            data: {
                quoteId: selfElement.attr('name').replace('assignedTo-', ''),
                assignedTo: selfElement.val(),
                websiteId: $('[name="webSiteId"]').val()
            },
            dataType:'json',
            cache: false
        }).done(function(data) {
            if (data.success) {
                // Do shoabs service instead of the below...
                if (selfElement.val() != 'none') {
                    $.ajax({
                        type: "POST",
                        url: "/admin/control/updateCustomOrder",
                        data: {
                            quoteId: selfElement.attr('name').replace('assignedTo-', ''),
                            statusId: 'QUO_ASSIGNED'
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (data) {
                        if (!data.success) {
                            alert('There was an issue while updating the status.  Please try again.', 'Error', toastrOpts);
                        }
                    });
                }
            } else {
                selfElement.val(previousAssignedTo);
                alert('There was an issue while updating the quote assignment.  Please try again.');
            }
        });
    });

    $('.salesRepList').on('click', function(e) {
        e.preventDefault();
        location.href='/admin/control/foldersQuoteList?webSiteId=folders&quoteIds=' + $(this).attr('data-quoteids');
    });
});