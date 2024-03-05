$(document).ready(function() {
    $('.holiday_shop .hs-nav .nav-options > a > div').on('mouseover', function() {
        $(this).children('p').css('border-width', '0px');
    }).on('mouseout', function() {
        $(this).children('p').css('border-width', '1px');
    });
});