$('[bns-hidequeue]').on('click', function() {
    var element = $(this);
    $.ajax({
        type: 'POST',
        url: '/admin/control/updatePrepressQueueFilter',
        dataType: 'text',
        data: { 
            'queueName': element.attr('data-queuename'), 
            'statusId': element.attr('data-statusid'), 
            'hidden': element.attr('data-tohide')
        }
    }).done(function(response) {
        response = JSON.parse(response);
        if (response.success) {
            if (element.attr('data-tohide') == 'Y') {
                element.parents('[bns-queuerow]').addClass('hidden');
                element.attr('data-tohide', 'N');
            } else {
                element.parents('[bns-queuerow]').removeClass('hidden');
                element.attr('data-tohide', 'Y');
            }
        }
    });
});

$('[bns-showhiddenqueue]').on('change', function() {
    if ($(this).is(':checked')) {
        $(this).parents('[bns-queuecontainer]').find('[bns-queuerow]').addClass('forceDisplay');
    } else {
        $(this).parents('[bns-queuecontainer]').find('[bns-queuerow]').removeClass('forceDisplay');
    }
});