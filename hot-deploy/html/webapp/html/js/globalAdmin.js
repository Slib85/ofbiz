$(function() {
    $('#global-search').on('submit', function(e) {
        e.preventDefault();
        var inputVal = $('#global-search-input').val().trim();
        if(inputVal.indexOf(' ') != -1) {
            inputVal = inputVal.substring(0, inputVal.indexOf(' '));
        }

        $.ajax({
            type: 'GET',
            url: '/admin/control/adminSearch',
            async: false,
            dataType: 'json',
            data: { 'query' : inputVal }
        }).done(function(data) {
            if(data.success && data.url != null) {
                window.location = '/admin/control/' + data.url;
            } else {
                alert("No Order Found.");
            }
        });
    });

    //check every 1 min if the session is expired
    var logCheckDiv = $('.isUserLoggedIn');
    setInterval(function() {
        if(logCheckDiv.hasClass('hidden')) {
            $.ajax({
                type: 'GET',
                url: '/' + websiteId + '/control/isUserLoggedIn',
                async: false,
                dataType: 'json'
            }).done(function (data) {
                if(!data.success) {
                    //check if the popup is already open
                    if(logCheckDiv.hasClass('hidden')) {
                        logCheckDiv.append($('<iframe/>').attr({'src': '/' + websiteId + '/control/blank'}).addClass('logFrame'));
                        logCheckDiv.removeClass('hidden');
                        $('.logFrame').on('load', function(e) {
                            if($(this).contents().find('body').find('.loggedIn').length > 0) {
                                $(this).remove();
                                logCheckDiv.addClass('hidden');
                            }
                        });
                    }
                } else {
                    //still logged in, do nothing
                }
            });
        }
    }, 60 * 1000); // 1minute
});